package com.techelevator;

import java.math.BigDecimal;

public class Snack {
	private BigDecimal snackPrice;
	private String snackName;
	private String snackType;
	
	public Snack(BigDecimal snackPrice, String snackName, String snackType) {
		this.snackPrice = snackPrice;
		this.snackName = snackName;
		this.snackType = snackType;
	}

	public String getSnackName() {
		return snackName;
	}

	public void setSnackName(String snackName) {
		this.snackName = snackName;
	}

	public BigDecimal getSnackPrice() {
		return snackPrice;
	}

	public void setSnackPrice(BigDecimal snackPrice) {
		this.snackPrice = snackPrice;
	}

	public String getSnackType() {
		return snackType;
	}

	public void setSnackType(String snackType) {
		this.snackType = snackType;
	}
	
	public String snackConsumed() {
		if (this.getSnackType().equals("Candy")) {
			return "Crunch Crunch, Yum! ";
			
		}else if (this.getSnackType().equals("Chip")) {
			return "Munch Munch, Yum! ";

		}else if (this.getSnackType().equals("Drink")) {
			return "Glug Glug, Yum! ";

		} else {
			return "Chew Chew, Yum! ";
		}
			
		
		
		
	}
	@Override
	public String toString() {
		return ("Name: " + this.getSnackName() + " Price: " + this.getSnackPrice() + "Type: " + this.getSnackType());
	}
}
