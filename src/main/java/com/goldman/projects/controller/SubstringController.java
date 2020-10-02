package com.goldman.projects.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.goldman.projects.model.InputRequestBody;
import com.goldman.projects.repository.SubstringRepository;

@RestController
@RequestMapping("/palindrome")
public class SubstringController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SubstringController.class);
	private static final String MISSINGPARAMS = "Missing Request parameters";
	private static final String EXCEEDSLENGTH = "Enter String whose length is less than 256 characters";
	private static final String NOCONTENT = "Not Existing in the database";
	@Autowired
	SubstringRepository substringRepo;

	@RequestMapping(value = "/store", method = RequestMethod.POST)
	public ResponseEntity<String> storeLongestSubstring(@RequestBody InputRequestBody inputRequestBody) {
		String responseResult = null;
		if (inputRequestBody.getInputString() != "null" && !"null".equals(inputRequestBody.getInputString())) {
			responseResult = substringRepo.generateAndStoreSubstring(inputRequestBody.getInputString());
		} else if (inputRequestBody.getInputString().length() > 1000) {
			LOGGER.error("Input String Exceeds 1000 characters");
			return new ResponseEntity<>(EXCEEDSLENGTH, HttpStatus.NOT_ACCEPTABLE);
		} else {
			LOGGER.error("Request failed due to null pointer exception");
			return new ResponseEntity<>(MISSINGPARAMS, HttpStatus.BAD_REQUEST);
		}
		
		if(responseResult.equals("Insertion failed to Database")) {
			return new ResponseEntity<>(responseResult, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(responseResult, HttpStatus.OK);
	}

	@RequestMapping(value = "/retrieve/{string}", method = RequestMethod.GET)
	public ResponseEntity<String> retrieveLongestSubstring(@PathVariable("string") String inputString) {
		String responseResult = null;
		if (inputString != "null" && !"null".equals(inputString)) {
			responseResult = substringRepo.retrievePalindrome(inputString);
		} else if (inputString.length() > 1000) {
			LOGGER.error("Input String Exceeds 1000 characters");
			return new ResponseEntity<>(EXCEEDSLENGTH, HttpStatus.NOT_ACCEPTABLE);
		} else {
			LOGGER.error("Request failed due to null pointer exception");
			return new ResponseEntity<>(MISSINGPARAMS, HttpStatus.BAD_REQUEST);
		}
		return responseResult.equalsIgnoreCase(NOCONTENT) ? new ResponseEntity<>(NOCONTENT, HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(responseResult, HttpStatus.OK);

	}

}
