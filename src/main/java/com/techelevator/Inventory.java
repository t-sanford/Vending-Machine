package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class Inventory {
	protected TreeMap<String, ArrayList<Snack>> snackMap = new TreeMap<>();
	
	public void populateInventory() {
		
		File inputFile = new File("vendingmachine.csv"); {
			try(Scanner fileScanner = new Scanner(inputFile)) { //define a Scanner object using file object created above
				while(fileScanner.hasNextLine()) {
					String line = fileScanner.nextLine();
					ArrayList<Snack> temp = new ArrayList<>();
					String[] tempSnack = line.split("\\|");
					BigDecimal cost = new BigDecimal(tempSnack[2].toString());
					for(int i = 0; i < 6; i++) {
						temp.add(new Snack(cost, tempSnack[1], tempSnack[3]));
					}
					this.snackMap.put(tempSnack[0], temp);
				}
				
			} catch (FileNotFoundException e) {
					e.printStackTrace();
			}
		}
			
	}
}
