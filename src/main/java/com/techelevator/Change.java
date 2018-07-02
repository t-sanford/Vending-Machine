package com.techelevator;

import java.math.BigDecimal;

public class Change {
	private static int numOfQuarters;
	private static int numOfDimes;
	private static int numOfNickels;
	
	public static void makeChange(BigDecimal userBalance) {
		BigDecimal dollar = new BigDecimal("1.00");
		BigDecimal quarter = new BigDecimal("0.25");
		BigDecimal dime = new BigDecimal("0.10");
		BigDecimal nickel = new BigDecimal("0.05");
		
		numOfQuarters = 0;
		numOfDimes = 0;
		numOfNickels = 0;
		while (userBalance.compareTo(dollar) >= 0) {
			userBalance = userBalance.subtract(dollar);
			numOfQuarters += 4;
		}
		while (userBalance.compareTo(quarter) >= 0) {
			userBalance = userBalance.subtract(quarter);
			numOfQuarters++;
		}
		while (userBalance.compareTo(dime) >= 0) {
			userBalance = userBalance.subtract(dime);
			numOfDimes++;
		}
		while (userBalance.compareTo(nickel) >= 0) {
			userBalance = userBalance.subtract(nickel);
			numOfNickels++;
		}
	}

	public static int getNumOfQuarters() {
		return numOfQuarters;
	}

	public static int getNumOfDimes() {
		return numOfDimes;
	}

	public static int getNumOfNickels() {
		return numOfNickels;
	}
}
