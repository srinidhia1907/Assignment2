package com.goldman.projects.repository;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.goldman.projects.common.StringMapper;

@Repository
public class SubstringRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(SubstringRepository.class);
	private final String INSERTSQL = "INSERT INTO stringmapping (originalString,longestPalindrome) VALUES(:originalString,:palindrome)";
	private final String sqlQuery = "SELECT longestPalindrome FROM stringmapping WHERE originalString = :inputString";

	@Autowired
	protected NamedParameterJdbcTemplate namedParamJdbcTemplate;

	public String generateAndStoreSubstring(String inputString) {
		String longestPalindrome = null;

		// check if the string is already existing in the database or not
		longestPalindrome = retrievePalindrome(inputString);
		if (!longestPalindrome.equals("Not Existing in the database")) {
			return longestPalindrome;
		}
		// if not exists, calculate longest palindrome and insert into DB
		longestPalindrome = com.goldman.projects.common.PalindromeGenerator.generatePalindrome(inputString);

		// Adding Arguments into parameter map
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("originalString", inputString);
		paramMap.put("palindrome", longestPalindrome);

		// insert into DB
		int result = namedParamJdbcTemplate.update(INSERTSQL, paramMap);
		if (result == 0) {
			return "Insertion failed to Database";
		}
		return longestPalindrome;
	}

	public String retrievePalindrome(String inputString) {
		String longestPalindrome = null;

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("inputString", inputString);

		try {
			longestPalindrome = (String) namedParamJdbcTemplate.queryForObject(sqlQuery, paramMap, new StringMapper());
		} catch (EmptyResultDataAccessException e) {
			longestPalindrome = "Not Existing in the database";
			LOGGER.error("Not existing in the database for given input string :{}", inputString);

		}

		return longestPalindrome;
	}

}
