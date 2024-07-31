package Version2;

import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;

public class MyController {

  private final MyModel myModel;
  private final MyView myView;
  
  //for buttons "C": Create, "V": View, "M": Manage, "S": Simulate
  private String mainMode;
  private String subMode1;
  private String discountCode;
  
  //indexing hotels and rooms
  private int indxHotel;
  private int indxRoom;
  private int indxReserve;
  private int indxPriceDate;
  private int checkIn;
  private int checkOut;
  
  public MyController(final MyModel myModel, final MyView myView) {
    this.myModel = myModel;
    this.myView = myView;

    /*----------------[CREATE BUTTON]----------------*/
    this.myView.setCreateBtnListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
    	mainMode = "C";
    	subMode1 = "";
    	myView.resetFromSwitching("Button");
    	myView.allowBottomInputs('1');
        myView.setCurrLblText("Create Hotel menu");
        myView.setPromptLblText("Name: ");
        myView.focusTextField();
      }
    });
    
    /*----------------[VIEW BUTTON]----------------*/
    this.myView.setViewBtnListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        mainMode = "V";
        subMode1 = "";
    	myView.resetFromSwitching("Button");
    	
        myView.allowBottomInputs('2');
        myView.setCurrLblText("View Hotel menu");
    	if (myModel.getNumHotel() <= 0)
    		myView.setPromptLblText("No hotels available. Create first.");
    	else {
    		myView.allowBottomInputs('2');
    		myView.setPromptLblText("Select Hotel");
    	}
        
        
      }
    });
    
    /*----------------[MANAGE BUTTON]----------------*/
    this.myView.setManageBtnListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
    	mainMode = "M";
    	subMode1 = "";
    	myView.resetFromSwitching("Button");
    	
    	myView.allowBottomInputs('2');
    	myView.setCurrLblText("Manage Hotel menu");	
        myView.getManageMenu().setSelectedIndex(0);
      }
    });
    
    /*----------------[SIMULATE BUTTON]----------------*/
    this.myView.setSimulateBtnListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
    	mainMode = "S";
    	subMode1 = "";
    	myView.resetFromSwitching("Button");

    	myView.allowBottomInputs('2');
        myView.setCurrLblText("Simulate Hotel menu");     
        
    	if (myModel.getNumHotel() <= 0)
    		myView.setPromptLblText("No hotels available. Create first.");
    	else {
    		myView.allowBottomInputs('2');
    		myView.setPromptLblText("Select Hotel");
    	}
      }
    });
    
    
    this.myView.setTextFieldListener(new KeyListener() {
    	public void keyPressed(KeyEvent e) {	
    		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
    			if (mainMode == "C") 
        			checkAddHotel();
        		if (subMode1 == "M1")
        			checkChangeHotel(myView.getHotelList().getSelectedIndex());
        		if (subMode1 == "M2") 
        			checkAddRoom(myView.getHotelList().getSelectedIndex());
        		if (subMode1 == "M4")
        			checkUpdateBasePrice(myView.getHotelList().getSelectedIndex());
        		if (subMode1 == "M6")
        			checkDatePrice();
        		if (subMode1 == "SReserve") 
        			checkAddReserve(checkIn, checkOut);
    		}
    	}
    	public void keyReleased(KeyEvent e) {}
    	public void keyTyped(KeyEvent e) {}
    });
    
    this.myView.setEnterBtnListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    		if (mainMode == "C") {
    			checkAddHotel();
    			myView.focusTextField();
    		}
    		if (subMode1 == "M1") {
    			checkChangeHotel(myView.getHotelList().getSelectedIndex());
    			myView.focusTextField();
    		}
    		if (subMode1 == "M2") {
    			checkAddRoom(myView.getHotelList().getSelectedIndex());
    			myView.focusTextField();
    		}
    		if (subMode1 == "M4") {
    			checkUpdateBasePrice(myView.getHotelList().getSelectedIndex());
    			myView.focusTextField();
    		}
    		if (subMode1 == "M6") {
    			checkDatePrice();
    			myView.focusTextField();
    		}
    		if (subMode1 == "SReserve") {
    			checkAddReserve(checkIn, checkOut);
    			myView.focusTextField();
    		}
    	}
    });
    
    this.myView.setHotelListListener(new ListSelectionListener() {
    	public void valueChanged(ListSelectionEvent e) {
    		indxHotel = myView.getHotelList().getSelectedIndex();
    		
    		if (indxHotel >= 0) {
    			myView.updateRList(myModel.getHotels().get(indxHotel).getNumRoom(), myModel.getHotels().get(indxHotel).getRooms());
    			myView.updateDatePrice(myModel.getHotels().get(indxHotel));
    		}
    		if (mainMode != "C") //allow continuous input for creating hotels
    			myView.resetFromSwitching("Hotel");
    		if (myView.getHotelList().isSelectionEmpty() == false) {
    			subMode1 = "";
        		if (mainMode == "V") {
                    myView.setCurrLblText("View " + myView.getHotelList().getSelectedValue());
                    myView.onViewMenuComBox(true);
                    myView.allowBottomInputs('2');
                    myView.getViewMenuComBox().setSelectedIndex(0); //keep it selected
        		}
        		if (mainMode == "M") {
        			myView.setCurrLblText("Manage " + myView.getHotelList().getSelectedValue());
            		myView.onManageMenu(true);
            		myView.getManageMenu().setSelectedIndex(0);  //keep it selected
        		}
        		if (mainMode == "S") {
        			myView.setCurrLblText("Simulate " + myView.getHotelList().getSelectedValue());
        			myView.onRoomList(true);
        			myView.allowBottomInputs('2');
        			myView.setPromptLblText("Select Room");
        		}

    		}	
    	}
    });
    
	/* showManageMenu() gives all possible options for manage after hotel is
    already selected, It has the following options followed from MCO1
 		1) Change name of hotel
 		2) Add rooms
 		3) Remove rooms
 		4) Update base price of each room
 		5) Remove reservation
 		6) Modify date price (MCO2)     
 		7) Remove hotel */
    
    this.myView.setManageMenuListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    			myView.onDatePriceComBox(false);
	 			myView.onRoomList(false);
	 			myView.onRoomTypeList(false);
    		
    			switch (myView.getManageMenu().getSelectedIndex()) {
    	 			case 0: //Unselected
    	 				subMode1 = "";
    	 				myView.allowBottomInputs('2');
    	 				if (myModel.getNumHotel() == 0)
    	 					myView.setPromptLblText("No hotels available. Create first.");
    	 				else if (indxHotel == -1)
    	 					myView.setPromptLblText("Select Hotel");
    	 				else if (indxHotel != -1)
    	 					myView.setPromptLblText("Select Manage Menu");
    	 				break;
    	 			case 1: //Change name of hotel
    	 				subMode1 = "M1";
    	 				myView.allowBottomInputs('1');
    	 				myView.setPromptLblText(myView.getHotelList().getSelectedValue() + " ->");
    	 				myView.focusTextField();
    	 				break;
    	 			case 2: //Add rooms
    	 				subMode1 = "M2";
    	 				myView.allowBottomInputs('1');
    	 				myView.setPromptLblText("Add #");
    	 				myView.setFeedbackLblText(String.valueOf(myModel.getHotels().get(indxHotel).getNumRoom())+"/50");
    	 				myView.onRoomList(true);
    	 				myView.onRoomTypeList(true);
    	 				break;
    	 			case 3: //Remove rooms
    	 				subMode1 = "M3";
    	 				myView.allowBottomInputs('2');
    	 				myView.setPromptLblText("Select room to remove");
    	 				myView.onRoomList(true);
    	 				break;
    	 			case 4: //Update Base price
    	 				if (myModel.countHotelReservations(indxHotel) > 0 ) {
    	 					myView.setPromptLblText(">>! Has " + String.valueOf(myModel.countHotelReservations(indxHotel)) + " reservations !<<");
    	 				}
    	 				else {
    	 					subMode1 = "M4";
        	 				myView.allowBottomInputs('1');
        	 				myView.setPromptLblText(String.valueOf(myModel.getHotels().get(indxHotel).getBasePrice()));
    	 				}
    	 				break;
    	 			case 5: //Remove reservation
    	 				subMode1 = "M5";
    	 				myView.allowBottomInputs('1');
	    				myView.setPromptLblText("Remove reservation");
	    				myView.onRoomList(true);
	    				myView.onReserveListComBox(true);
	    				break;
    	 			case 6: //Modify date price
    	 				subMode1 = "M6";
    	 				myView.allowBottomInputs('2');
    	 				myView.onDatePriceComBox(true);
    	 				myView.updateDatePrice(myModel.getHotels().get(indxHotel));
    	 				break;
    	 			case 7: //Remove hotel
    	 				if(myModel.getNumHotel() > 0 && indxHotel != -1) {
    	 					myView.onManageMenu(false);
    	 					myView.getManageMenu().setSelectedIndex(0);
    	 					myView.allowBottomInputs('2'); //prevent null error
    	 					myView.setPromptLblText("Deleted " + myView.getHotelList().getSelectedValue() );
    	 					myModel.removeHotel(indxHotel);
    	 					myView.updateHList(myModel.getNumHotel(), myModel.getHotels());  	 					
    	 					myView.setCurrLblText("Manage Hotel menu");
    	 				} else
    	 					myView.setPromptLblText("No hotels selected");
    	 				break;
    	 		
    		}
    	}
    });
    
    
    
    this.myView.setDatePriceComBoxListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    		String fRate;
    		indxPriceDate = myView.getDatePriceComBox().getSelectedIndex();
 
    		if(subMode1 == "M6") {
    			if (indxPriceDate <= 0) {
    				myView.allowBottomInputs('2');
    				myView.setPromptLblText("Select date to modify");
    			}
    			else {
    				fRate = String.valueOf(myModel.getHotels().get(indxHotel).getDatePriceRate()[indxPriceDate-1]);
    				myView.allowBottomInputs('1');
    				myView.setPromptLblText(fRate+"->");
    				myView.focusTextField();
    			}
    		}
    		
    	}
    });
    
    this.myView.setRoomListListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    		indxRoom = myView.getRoomComBox().getSelectedIndex(); 
    		
    		if (indxRoom <= 0) 
    			myView.updateReserveList(0, null);
    		else {
    			try {
    				myView.updateReserveList(myModel.getHotels().get(indxHotel).getRooms().get(indxRoom-1).getNumReserve(), myModel.getHotels().get(indxHotel).getRooms().get(indxRoom-1).getReservations());
    			} catch (Exception d) {}
    		}	
    		
    		
    		if (subMode1 == "M3") { //delete room
    			  if (indxRoom <= 0) //selection
    				  myView.setPromptLblText("Select room to remove");
    			  else
    				  checkRemoveRoom(indxHotel, indxRoom-1); //Must include -1 since we have "select room"
    		}

    			
    			
    		if (mainMode == "S") {
    			subMode1 = "";
    			if (indxRoom <= 0) {
    				myView.getDateReservePanel().setVisible(false);
    				myView.getCheckInComBox().setSelectedIndex(0);
    				myView.getCheckOutComBox().setSelectedIndex(0);
    				myView.getDateReservePanel().setVisible(false);
    				myView.getReserveListComBox().setVisible(false);
    				myView.getDiscountField().setVisible(false);
        			myView.allowBottomInputs('2');
        			myView.setPromptLblText("Select Room");
    			}
    			else if (indxRoom >= 1) {
    				subMode1 = "SReserve";
        			myView.allowBottomInputs('2');
    				myView.onDateReservePanel(true);
    				myView.onReserveListComBox(true);
    				myView.setPromptLblText("Select date range");
    			}
    		}
    		
    		
    		if (subMode1 == "View Low Room") {
    			if (indxRoom == 0) {
    				myView.setPromptLblText("Select Room");
    			}
    			
    			else if (indxRoom > 0)
    				myView.viewLowRoom(myModel.getHotels().get(indxHotel).getName(), myModel.getHotels().get(indxHotel).getRooms().get(indxRoom-1));
    		}
    		if (subMode1 == "View Low Reservation") {
    			if (indxRoom == 0) {
    				myView.setPromptLblText("Select Room");
    			}
    			else if (indxRoom > 0) {
    				myView.onReserveListComBox(true);
    				myView.getReserveListComBox().setSelectedIndex(0);
    				myView.setPromptLblText("Select Reservation");
    			}
    		}  		
    	}
    });
    
    this.myView.setRoomTypeListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {}
    });
    
    
    this.myView.setCheckOutListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    		myView.allowBottomInputs('2');
    		checkIn = (int) myView.getCheckInComBox().getSelectedItem();
    		checkOut = (int) myView.getCheckOutComBox().getSelectedItem();
    		
    		if (subMode1 == "SReserve") {
        		if (checkIn > checkOut)
        			myView.setPromptLblText(">>! checkIn > checkOut !<<");
        		else if (checkOverlap(checkIn, checkOut))
        			myView.setPromptLblText(">>! date overlap !<<");
        		else {
        			myView.allowBottomInputs('1');
        			myView.setPromptLblText("GuestName");
        			myView.getTextField().grabFocus();
        			myView.onDiscountField(true);
        			discountCode = "";
        		}
    		}
    	}
    });
    
    this.myView.setReserveListComBoxListener(new ActionListener() {
    	String hotelName;
    	Reservation reservation;
    	public void actionPerformed(ActionEvent e) {
    		indxReserve = myView.getReserveListComBox().getSelectedIndex();
    		
    		if (indxReserve <= 0) {
        		if (subMode1 == "M5") {	
        			myView.allowBottomInputs('2');
        			myView.setPromptLblText("Select reservation to remove");
        		}
        		if (subMode1 == "View Low Reservation") {
        			myView.allowBottomInputs('2');
        			myView.setPromptLblText("Select reservation to view");
        		}
    		}
    		else {
        		hotelName = myModel.getHotels().get(indxHotel).getName();
        		reservation = myModel.getHotels().get(indxHotel).getRooms().get(indxRoom-1).getReservations().get(indxReserve-1);
    			if (subMode1 == "M5") {
    				if (indxReserve > 0) {
        				myView.setPromptLblText("Removed " + reservation.getGName());
        				myModel.removeReservations(indxHotel, indxRoom-1, indxReserve-1);
        				myView.getReserveListComBox().setSelectedIndex(0);
        				try {
        					myView.getReserveListComBox().remove(indxReserve); //still in JComboBox
        				} catch (Exception a) {}
        				myView.updateReserveList(myModel.getHotels().get(indxHotel).getRooms().get(indxRoom-1).getNumReserve(), 
        						myModel.getHotels().get(indxHotel).getRooms().get(indxRoom-1).getReservations());
    				}
    			}
           		if (subMode1 == "View Low Reservation") {
           			myView.viewLowReserve(hotelName, reservation);
        		}
    				
    		}
    	}
    });
    
    
    this.myView.setViewMenuComBoxListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    		myView.onRoomList(false);
    		myView.onReserveListComBox(false);
    		myView.onViewDayComBox(false);
    		if (myView.getViewMenuComBox().getSelectedIndex() == 0) {
    			myView.allowBottomInputs('2');
    			myView.setPromptLblText("Select View Options");
    		}
    		//View high
    		if (myView.getViewMenuComBox().getSelectedIndex() == 1) {
    			myView.viewHigh(myModel.getHotels().get(indxHotel));
    		}
    		//View Low: Availability
    		if (myView.getViewMenuComBox().getSelectedIndex() == 2) {
    			subMode1 = "View Low Availability";
    			myView.onViewDayComBox(true);
    			myView.getViewDayComBox().setSelectedIndex(0);
    		}
    		//View Low: Room
    		if (myView.getViewMenuComBox().getSelectedIndex() == 3) {
    			subMode1 = "View Low Room";
    			myView.onRoomList(true);
    			myView.getRoomComBox().setSelectedIndex(0);
    			myView.setPromptLblText("Select Room");
    		}
    		//View Low: Reservation
    		if (myView.getViewMenuComBox().getSelectedIndex() == 4) {
    			subMode1 = "View Low Reservation";
    			myView.onRoomList(true);
    			myView.getRoomComBox().setSelectedIndex(0);
    			myView.setPromptLblText("Select Room");
    		}
    	}
    });
    
    this.myView.setViewDayComBoxListener(new ActionListener() {
    	int selectedDay;
    	public void actionPerformed(ActionEvent e) {
    		selectedDay = myView.getViewDayComBox().getSelectedIndex();
    		if (subMode1 == "View Low Availability") {
    			if (selectedDay <= 0)
    				myView.setPromptLblText("Select Day");
    			else {
        			selectedDay = myView.getViewDayComBox().getSelectedIndex();
        			myView.viewLowAvailability(selectedDay, myModel.getHotels().get(indxHotel));
    			}
    		}
    	}
    });
    
    
    
    this.myView.setDiscountFieldListener(new KeyListener() {
    	public void keyPressed(KeyEvent e) {
    		String strInput;
    		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
    			strInput = myView.getDiscountField().getText();
    			if (subMode1 == "SReserve")  {
        			if (strInput.equals("I_WORK_HERE")) {
        				discountCode = strInput;
        				myView.setFeedbackLblText("discount granted");
        			}
        			else if (strInput.equals("STAY4_GET1")) {
        				checkDiscount(strInput, checkIn, checkOut);
        			}
        			else if (strInput.equals("PAYDAY")) {
        				checkDiscount(strInput, checkIn, checkOut);
        			}
        			else
        				myView.setFeedbackLblText("invalid discount");
    			}
    		}
    	}
    	public void keyReleased(KeyEvent e) {}
    	public void keyTyped(KeyEvent e) {}
    });
    
    
    
}
  

  

  /* checkUnqName() returns boolean value whether string inputted
       is unique to names of all other given hotels.
       @param strInput - name of hotel to be compared to all other hotels 
       @return bUnique - if strInput does not have same name with other hotels
           regardless of letter cases (lowercase, uppercase) */
  public boolean checkUnqName(String strInput) { //transferred and edited from HotelSystem class
	  boolean bUnique = true; //always assume true
	  for (int i = 0; i < myModel.getNumHotel() && bUnique; i++) {
		  if (strInput.equalsIgnoreCase(myModel.getHotels().get(i).getName())  )
	            bUnique = false;
	  }
	  return bUnique;
  }

  
  /*checkAddHotel() checks first if conditions is satisfied, then
     can call MyModel class */
  public void checkAddHotel() {
		System.out.println(myView.getTextField().getText());
		if (myView.getTextField().getText().isBlank()) { //no name, all whitespaces
			myView.setFeedbackLblText(">>! No name !<<");
		}
		else if (!(checkUnqName(myView.getTextField().getText()) )) { //not unique
			myView.setFeedbackLblText(">>! Already exists !<<");
		}
		else { //actual adding
			myView.setFeedbackLblText("Successfully added");
			myModel.addHotel(myView.getTextField().getText());
			myModel.sortHotel();
			myView.clearTFText();
			myView.updateHList(myModel.getNumHotel(), myModel.getHotels());
		}
  }
  
  /* checkChangeHotel() checks first if hotel name to be changed is still
       unique to all other hotels. If valid, then call MyModel class 
       @param indx: index accessing hotel to be changed */
  public void checkChangeHotel(int indxHotel) {
	  System.out.println(myView.getTextField().getText());
	  
	  if (myView.getTextField().getText().isBlank()) { //no name, all whitespaces
		  myView.setFeedbackLblText(">>! No name !<<");
	  }
	  else if (!(checkUnqName(myView.getTextField().getText()) )) { //not unique
		  myView.setFeedbackLblText(">>! Already exists !<<");
	  }
	  else { //actual changing
		  myView.setFeedbackLblText("Successfully changed");
		  myModel.changeName(indxHotel,myView.getTextField().getText());
		  myModel.sortHotel();
		  myView.updateHList(myModel.getNumHotel(), myModel.getHotels());
		  myView.getHotelList().setSelectedValue(myView.getTextField().getText(), true);
		  
		  myView.clearTFText();
		  
	  }  
  }
  
  
  /* checkUpdateBasePrice() checks first if update price is valid before
       the price can be updated 
  	@param indx: index accessing hotel to be changed */
  public void checkUpdateBasePrice(int indxHotel) {
	  System.out.println(myView.getTextField().getText());
	  
	  if (myView.getTextField().getText().isBlank()) { //no name, all whitespaces
		  myView.setFeedbackLblText(">>! No Input !<<");
	  }
	  else if (Float.parseFloat(myView.getTextField().getText()) < 100.0f) { //not unique
		  myView.setFeedbackLblText(">>! Must be >= 100.0 !<<");
	  }
	  else { //actual changing
		  myView.setFeedbackLblText("Updated");
		  myModel.updateBasePrice(indxHotel, Float.parseFloat(myView.getTextField().getText()));
		  myView.getHotelList().setSelectedIndex(indxHotel);
		  myView.setPromptLblText(String.valueOf(myModel.getHotels().get(indxHotel).getBasePrice()) + " ->");
		  
		  myView.clearTFText();
	  
	  }  
  }
  
  /* checkAddRoom() checks first if conditions are satisfied so
      that room can be properly added
      @param indx: index accessing hotel to be changed */
  public void checkAddRoom(int indxHotel) {
	  System.out.println(myView.getTextField().getText());
	  int indexHotel = myView.getHotelList().getSelectedIndex();
	  int nInput = Integer.parseInt(myView.getTextField().getText());
	  
	  if (myView.getTextField().getText() == "")
		  myView.setFeedbackLblText(String.valueOf(myModel.getHotels().get(indexHotel).getNumRoom())+"/50 no input");
	  else if (nInput <= 0 || nInput + myModel.getHotels().get(indexHotel).getNumRoom() > 50) 
		  myView.setFeedbackLblText(String.valueOf(myModel.getHotels().get(indexHotel).getNumRoom())+"/50 invalid");
	  else {
		  myModel.addRoom(indxHotel, myView.getRoomTypeComBox().getSelectedIndex(), nInput);
		  myView.updateRList(myModel.getHotels().get(indexHotel).getNumRoom(), myModel.getHotels().get(indexHotel).getRooms());
		  myView.setFeedbackLblText(String.valueOf(myModel.getHotels().get(indexHotel).getNumRoom())+"/50 added");
	  }
	  
  }
  
  /* checkRemoveRoom() checks first if condition satisfied so that room
      can be removed, such as each hotel must have always at least one room
      @param indx: index accessing room to be removed */
  public void checkRemoveRoom(int indxHotel, int indxRoom) {
	  int numReserve = myModel.getHotels().get(indxHotel).getRooms().get(indxRoom).getNumReserve();
	  if (numReserve > 0)
		  myView.setPromptLblText(">! Has " + numReserve + "reservation/s !<<");
	  else if (myModel.getHotels().get(indxHotel).getNumRoom() <= 1)
		  myView.setPromptLblText(">! Hotel must have >= 1 room !<<");
	  else {
		  myView.setPromptLblText("Deleted " + myModel.getHotels().get(indxHotel).getRooms().get(indxRoom).getName());
		  myModel.removeRoom(indxHotel, indxRoom);
		  myView.updateRList(myModel.getHotels().get(indxHotel).getNumRoom(), myModel.getHotels().get(indxHotel).getRooms());
	  }
		  
  }

  
  /* checkOverlap() called from simulateReserve() checks whether inputted
	  check-in and check-out overlaps all other timeslot for other reservations
	  within the same room.
	  @param nStart: inputted check-in
	  @param nEnd: inputted checkout 
	  @return isOverlap - if it inputted date overlaps any dates  */
  public boolean checkOverlap(int nStart, int nEnd) { //adapted and edited from Room Class
	  boolean isOverlap;
	  isOverlap = false;
	  int numReserve = myModel.getHotels().get(indxHotel).getRooms().get(indxRoom-1).getNumReserve();
	  ArrayList<Reservation> temp = myModel.getHotels().get(indxHotel).getRooms().get(indxRoom-1).getReservations();

	  // nStart CheckIn CheckOut nEnd
	  // CheckIn nStart CheckOut nEnd
	  // CheckIn nStart nEnd CheckOut
	  for (int i = 0; i < numReserve && !isOverlap; i++) {
	  		if (nStart <= temp.get(i).getCheckIn()
	  				&& nEnd > temp.get(i).getCheckIn())
	  			isOverlap = true;
	  		if (nStart < temp.get(i).getCheckOut() 
	  				&& nEnd >= temp.get(i).getCheckOut())
	  			isOverlap = true;
	  		if (temp.get(i).getCheckIn() <= nStart
	  				&& nEnd <= temp.get(i).getCheckOut())
	  			isOverlap = true;
	  }
	  return isOverlap;
}
  
  /* checkAddReserve() adds reservation using the following parameters and calls to my myModel
      @param checkIn: inputted checkIn
      @param checkOut: inputted checkOut
      @param guestName: Name of guest */
  public void checkAddReserve(int checkIn, int checkOut) {
	  String guestName = myView.getTextField().getText();
	  if (guestName.isBlank()) 
		  myView.setFeedbackLblText(">>! Blank !<<");
	  else {
		  myView.setFeedbackLblText("Reserved");
		  myModel.addReserve(indxHotel, indxRoom-1, checkIn, checkOut, guestName, discountCode); //Must include -1 since we have "select room"
		  myView.clearTFText();
		  myView.updateReserveList(myModel.getHotels().get(indxHotel).getRooms().get(indxRoom-1).getNumReserve(), myModel.getHotels().get(indxHotel).getRooms().get(indxRoom-1).getReservations());
		  myView.updateRList(myModel.getHotels().get(indxHotel).getNumRoom(), myModel.getHotels().get(indxHotel).getRooms());
	  }
	  
  }
  
  /* public checkDatePrice() determines whether input is valid before properly modifying it in
      date class */
  public void checkDatePrice() {
	  float fInput = 1.0f;
	  String strInput = myView.getTextField().getText();
	  
	  if (strInput.isBlank()) {
		  myView.setFeedbackLblText(">>! Blank !<<");
	  }
	  else {
		  fInput = Float.parseFloat(strInput);
		  if (0.5f > fInput && fInput > 1.5f)
			  myView.setFeedbackLblText(">!! 0.5-1.5 only !!<");
		  else {
			  myView.setFeedbackLblText("modified");
			  myModel.modifyDatePrice(indxHotel, indxPriceDate-1, fInput);
			  myView.updateDatePrice(myModel.getHotels().get(indxHotel));
			  myView.getDatePriceComBox().setSelectedIndex(0);
		  }
	  }
	  
  }
  
  /* checkDiscount() determines whether two are valid or not
      @param strInput: inputted recognized discount code
      @param checkIn: inputted checkin
      @param checkOut: inputted checkOut*/
  public void checkDiscount(String strInput, int checkIn, int checkOut) {
	  if (strInput.equals("STAY4_GET1")) {
		  if (checkOut - checkIn + 1 >= 5) {
			  myView.setFeedbackLblText("discount granted");
			  discountCode = strInput;
		  }
		  else {
			  myView.setFeedbackLblText("<5 days");
		  }
	  }
	  if (strInput.equals("PAYDAY")) {
		  if (checkIn <= 15 && 15 <= checkOut || checkIn <= 30 && 30 <= checkOut) {
			  myView.setFeedbackLblText("discount granted");
			  discountCode = strInput;
		  }
		  else {
			  myView.setFeedbackLblText("Not payday");
		  }
	  }
		  
  }
  

  
}
