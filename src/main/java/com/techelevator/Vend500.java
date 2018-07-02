package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import com.techelevator.view.Menu;

public class Vend500 {
	private BigDecimal userBalance;
	private ArrayList<Snack> purchased = new ArrayList<>();
	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
	private Inventory machineSnacks = new Inventory();
	private Date date = new Date();
	private StringBuffer logText = new StringBuffer();
	private StringBuffer salesReport = new StringBuffer();
	private DecimalFormat money = new DecimalFormat("##.00");

	public void startInventory() {
		machineSnacks.populateInventory();
	}

	public Vend500() {
		this.userBalance = new BigDecimal("0.00");
	}

	public void displayUserBalance() {
		System.out.println("Remaining Balance: " + userBalance);
	}

	public BigDecimal getUserBalance() {
		return userBalance;
	}

	public void displayItems() {
		System.out.println("Location\t\t     Name\t\t\tPrice $\t  Left");
		System.out.println("----------------------------------------------------------------");
		for(String item : machineSnacks.snackMap.keySet()) {
			try {
				Snack temp = machineSnacks.snackMap.get(item).get(0);
				System.out.println("  " + item + "\t\t" +  String.format("%-20s", temp.getSnackName()) + "\t\t$" + temp.getSnackPrice() +
					String.format("%8s", machineSnacks.snackMap.get(item).size() - 1));
			} catch (IndexOutOfBoundsException e) {
				System.out.println("  " + item + "\t\t" +  String.format("%-20s", "OUT OF STOCK") + "\t\t$" + "0.00" + 
						String.format("%8s", 0));
			}
		}
	}

	public void purchaseMenu() {

		final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money/Check Balance"; // Define constant for menu text
		final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
		final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
		final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY, // Define an array with menu choices
				PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION };
		Menu menu = new Menu(System.in, System.out);
		logText.append("\tDate/Time\t\t\t\tAction/Item\t\tPrevious $\tCurrent Balance\n");

		while (true) { // invoke getChoiceFromOptiins method with
			String choice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS); // with [] of options

			if (choice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
				Scanner userEntry = new Scanner(System.in);
				System.out.println("Enter amount to feed (in whole dollars) or 0 to check balance: ");
				BigDecimal moneyIn = userEntry.nextBigDecimal();
				feedMoney(moneyIn);
				System.out.println("Current Balance: $" + getUserBalance());

			} else if (choice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
				purchaseSnack();

			} else if (choice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
				Change.makeChange(userBalance);
				System.out.println("Your change is " + Change.getNumOfQuarters() + " Quarters, " 
				+ Change.getNumOfDimes() + " Dimes, " + Change.getNumOfNickels() + " Nickels. Totalling: $" + userBalance);
				for(Snack item : purchased) {
					System.out.println(item.snackConsumed());
				}
				logText.append(dateFormat.format(date) + "\t" + String.format("%-23s", "GIVE CHANGE:") + "$" + userBalance + "\t\t" + String.format("$%-8.2f", userBalance.subtract(userBalance)) + "\n");
				userBalance = (new BigDecimal("0.00"));
				File newFile = new File("log.txt");
				try(PrintWriter writer = new PrintWriter(newFile)){
					writer.println(logText);
				} catch (FileNotFoundException e) {
					System.out.println("Oops");
				}
				return;
			}
		}

	}

	private void feedMoney(BigDecimal moneyIn) {
		if(moneyIn.compareTo(new BigDecimal("10.00")) <= 0) {
			userBalance = userBalance.add(moneyIn);
			logText.append(dateFormat.format(date) + "\t" + String.format("%-23s", "FEED MONEY:") + String.format("$%3.2f", moneyIn) + "\t\t" + String.format("$%-8.2f", userBalance) + "\n");
		} else {
			System.out.println("Maximum deposit is $10.00");
		}
	}

	private void purchaseSnack() {
		String selection;
		Scanner userEntry = new Scanner(System.in);
		System.out.println("Please make a selection: ");
		selection = userEntry.nextLine().toUpperCase();
		try {
				if(machineSnacks.snackMap.get(selection).size() > 1) {
					Snack selectedSnack = machineSnacks.snackMap.get(selection).get(0);
					ArrayList<Snack> slot = machineSnacks.snackMap.get(selection);
					if((selectedSnack.getSnackPrice().compareTo(userBalance) <= 0) && (!(slot.isEmpty()))) {
						userBalance = userBalance.subtract(selectedSnack.getSnackPrice());
						slot.remove(0);
						purchased.add(selectedSnack);
						logText.append(dateFormat.format(date) + "\t" + String.format("%-18s", selectedSnack.getSnackName()) + " " + selection.toUpperCase() 
						+ "  $" + userBalance.add(selectedSnack.getSnackPrice()) + " \t" + String.format("\t$%-8.2f", userBalance) + "\n");
					} else if(selectedSnack.getSnackPrice().compareTo(userBalance) > 0) {
						System.out.println("Insufficient funds, please feed money...");
					}
					} else {
						System.out.println("Selected item is out of stock, please choose a different item...");
					}
			} catch (NullPointerException e) {
				System.out.println("Selection does not exist!");
				return;
			}
		}
	
	public void generateSalesReport() {
		BigDecimal totalSales = new BigDecimal("0.00");
		for(String item : machineSnacks.snackMap.keySet()) {
			try {
				Snack temp = machineSnacks.snackMap.get(item).get(0);
				salesReport.append(temp.getSnackName() + "|" + (6-(machineSnacks.snackMap.get(item).size())) + "\n");
				BigDecimal sold = new BigDecimal((6-(machineSnacks.snackMap.get(item).size())));
				BigDecimal itemSales = (sold.multiply(temp.getSnackPrice()));
				totalSales = totalSales.add(itemSales);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		salesReport.append("\n");
		salesReport.append("***Total Sales***  $" + totalSales + "\n");
		File newFile2 = new File("SalesReport.txt");
		try(PrintWriter writer = new PrintWriter(newFile2)){
			writer.println(salesReport);
		} catch (FileNotFoundException e) {
			System.out.println("Oops");
		}
	}
}