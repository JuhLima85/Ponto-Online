package br.com.Utils;

public class CpfValidador {

	public static boolean isValid(String cpf) {

		cpf = cpf.replaceAll("\\D", "");

		if (cpf.length() != 11) {
			return false;
		}

		int[] digits = new int[11];
		for (int i = 0; i < 11; i++) {
			digits[i] = Character.getNumericValue(cpf.charAt(i));
		}

		int firstSum = 0;
		for (int i = 0; i < 9; i++) {
			firstSum += digits[i] * (10 - i);
		}

		int firstDigit = 11 - (firstSum % 11);
		if (firstDigit >= 10) {
			firstDigit = 0;
		}

		int secondSum = 0;
		for (int i = 0; i < 10; i++) {
			secondSum += digits[i] * (11 - i);
		}

		int secondDigit = 11 - (secondSum % 11);
		if (secondDigit >= 10) {
			secondDigit = 0;
		}

		return firstDigit == digits[9] && secondDigit == digits[10];
	}

}
