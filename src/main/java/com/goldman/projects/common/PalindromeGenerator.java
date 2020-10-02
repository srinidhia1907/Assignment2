package com.goldman.projects.common;

public class PalindromeGenerator {
	public static String generatePalindrome(String userInput) {

		int length = userInput.length();
		int longestSoFar = 0, begin = 0, end = 0;
		boolean[][] palindrom = new boolean[length][length];

		for (int j = 0; j < length; j++) {
			palindrom[j][j] = true;
			for (int i = 0; i < j; i++) {
				if (userInput.charAt(i) == userInput.charAt(j) && (j - i <= 2 || palindrom[i + 1][j - 1])) {
					palindrom[i][j] = true;
					if (j - i + 1 > longestSoFar) {
						longestSoFar = j - i + 1;
						begin = i;
						end = j;
					}
				}
			}
		}
		String longestPalindrome = userInput.substring(begin, end + 1);

		return longestPalindrome;

	}

}
