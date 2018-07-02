package com.techelevator;

import com.techelevator.view.Menu;

public  class  VendingMachineCLI {
	
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";	// Define constant for menu text
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase Item";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit Vendo-Matic 500";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS,			// Define an array with menu choices
													    MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT };				
	private Menu menu;
	private Vend500 machine = new Vend500();
	
	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}
	
	// ALL code will be entered below
	public void run() {
		machine.startInventory();
		while(true) {
			String choice = (String)menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);				//invoke getChoiceFromOptiins method with
			
																							//with [] of options
			if(choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				machine.displayItems();
				
			}  else if(choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				machine.purchaseMenu();
				
			} else if(choice.equals(MAIN_MENU_OPTION_EXIT)) {	
				machine.generateSalesReport();
				System.exit(0);
			}
																							
		}																					
																							
	}//END OF RUN METHOD- DON'T change anything below!				
	
	public static void main(String[] args) {
		
		Menu menu = new Menu(System.in, System.out);					// Instantiate menu object (input source, output source)
		VendingMachineCLI cli = new VendingMachineCLI(menu);			// 
		cli.run();
		
	}
	
}  
