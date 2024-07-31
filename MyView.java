package Version2;


import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


public class MyView extends JFrame{

	private JFrame mainFrame;
	
	private JPanel panelTop, panelMenu, panelCenter, panelBottom;
	private JSplitPane splitPane;
	private JScrollPane scrollPane;
	
	//main buttons and labels
	private JButton createBtn, viewBtn, manageBtn, simulateBtn;
	private JLabel currLbl;
	
	//Found at splitPane
	private JList<String> hotelList;
	private DefaultListModel<String> listModel;
	
	//found at bottom panel
	private JLabel promptLbl, feedbackLbl;
	private JTextField textField;
	private JButton enterBtn;
	
	
	//Manage components
	private JComboBox<String> manageMenu;
	private String[] manageMenuList = {"--Select manage--", "Change hotel name", 
	    "Add rooms", "Remove rooms", "Update base price",
	    "Remove reservation", "Modify date price", "Remove hotel"};
	private JComboBox<String> datePriceComBox;
	
	private JComboBox<String> roomComBox;
	private String[] roomList;
	private JComboBox<String> roomTypeComBox;
	private String[] roomTypeList = {"Standard", "Deluxe", "Executive"};
	
	
	//View Components
	private JComboBox<String> viewMenuComBox;
	private String[] viewMenuList = {"Select View", "View High", "View Low: Availability", "View Low: Room", "View Low: Reservation"};
	private JDialog viewDialog;
	private JTextArea viewTextArea;
	private JComboBox<String> viewDayComBox; //for view availability
	
	
	//Simulate Components
	private JLabel checkInLbl;
	private JComboBox<Integer> checkInComBox;
	private JLabel checkOutLbl;
	private JComboBox<Integer> checkOutComBox;
	private JPanel dateReservePanel;
	private JTextField discountField;
	
	
	//Reservation Components
	private JComboBox<String> reserveListComBox;
	
	
	public MyView() {
		mainFrame = new JFrame("Hotel Reservation System");
		
	    //All panes, split panes, scrollpanes
	    panelTop = new JPanel();
	    panelTop.setBackground(new Color(0, 112, 60)); //Official DLSU Green
	    panelTop.setBounds(0,20,420,50); //y = 20-70
	    panelMenu = new JPanel();
	    panelMenu.setBackground(new Color(8, 207, 114));
	    panelMenu.setBounds(0, 70, 420, 30); //y = 70-100  
	    splitPane = new JSplitPane();
	    splitPane.setBounds(0, 100, 410, 350); //y = 100-450
	    splitPane.setBackground(new Color(230, 230, 230)); //light gray
	    panelCenter = new JPanel(new GridLayout(5, 1));
	    panelCenter.setBackground(new Color(232, 217, 237)); //pearl white
	    panelBottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    panelBottom.setBackground(new Color(170, 170, 170));
	    panelBottom.setBounds(0,450,420,50); //450-500


	    //All Central Components
	    hotelList = new JList<>();
	    scrollPane = new JScrollPane(hotelList);
	    listModel = new DefaultListModel<>();
	    hotelList.setModel(listModel);
	    splitPane.setLeftComponent(scrollPane);
	    scrollPane.setBackground(new Color(200, 230, 200)); //idk what color is this
	    scrollPane.setViewportView(hotelList);
	    splitPane.setRightComponent(panelCenter);
	    manageMenu = new JComboBox<String>(manageMenuList);
	    

	   datePriceComBox = new JComboBox<String>();
	   datePriceComBox.addItem("--Select Date--");
	   discountField = new JTextField();
	   
	    
	    roomList = new String[51];
	    roomList[0] = "--Room List--";
	    roomComBox = new JComboBox<String>(roomList);
	    roomTypeComBox = new JComboBox<String>(roomTypeList);
	    
		checkInLbl = new JLabel("CheckIn");	
		checkOutLbl = new JLabel("CheckOut");
		checkInComBox = new JComboBox<Integer>();
		checkOutComBox = new JComboBox<Integer>();
		for (int i = 1; i <= 31; i++) {
			checkInComBox.addItem(i);
			checkOutComBox.addItem(i);
		}
		dateReservePanel = new JPanel();
		dateReservePanel.add(checkInLbl);
		dateReservePanel.add(checkInComBox);
		dateReservePanel.add(checkOutLbl);
		dateReservePanel.add(checkOutComBox);
		
		reserveListComBox = new JComboBox<String>();
		reserveListComBox.addItem("--[Booked List]--");
	    
		viewMenuComBox = new JComboBox<String>(viewMenuList);
		viewTextArea = new JTextArea();
		viewDayComBox = new JComboBox<String>();
		viewDayComBox.addItem("--Select Day--");

		//All main buttons and labels
		createBtn = new JButton("Create");
	    viewBtn = new JButton("View");
	    manageBtn = new JButton("Manage");
	    simulateBtn = new JButton("Simulate");
	    currLbl = new JLabel("[Select Menu]");
	    
	    
	    //all bottom input components
	    promptLbl = new JLabel("Input: ");
	    feedbackLbl = new JLabel("--------");
	    textField = new JTextField();
	    enterBtn = new JButton("Enter");
	    
	    
	    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    mainFrame.setLayout(null);
	    mainFrame.setResizable(false);
	    mainFrame.setSize(420, 600);
	    mainFrame.add(panelTop);
	    mainFrame.add(panelMenu);;
	    mainFrame.add(splitPane);
	    mainFrame.add(panelBottom);
	    panelTop.add(createBtn);
	    panelTop.add(viewBtn);
	    panelTop.add(manageBtn);
	    panelTop.add(simulateBtn);
	    panelMenu.add(currLbl);
	    
/*
	    panelCenter.add(manageMenu);
	    manageMenu.setVisible(false);
	    panelCenter.add(datePriceScroll);
	    datePriceScroll.setVisible(false);
	    panelCenter.add(roomComBox);
	    roomComBox.setVisible(false);
	    panelCenter.add(roomTypeComBox);
	    roomTypeComBox.setVisible(false);
		*/
		panelBottom.add(promptLbl);
		panelBottom.add(textField);
		panelBottom.add(enterBtn);
		panelBottom.add(feedbackLbl);
		textField.setColumns(10);
		allowBottomInputs('0');
	    
	    
	    mainFrame.setVisible(true);
		
	}
	
	
	
	public JTextField getTextField() {
		return this.textField;
	}
	public void clearTFText() {
		this.textField.setText("");
	}
	public JList<String> getHotelList() {
		return this.hotelList;
	}
	public JComboBox<String> getManageMenu() {
		return this.manageMenu;
	}
	public JComboBox<String> getDatePriceComBox() {
		return this.datePriceComBox;
	}
	public JComboBox<String> getRoomComBox() {
		return this.roomComBox;
	}
	public JComboBox<String> getRoomTypeComBox() {
		return this.roomTypeComBox;
	}
	public JPanel getDateReservePanel(){
		return this.dateReservePanel;
	}
	public JComboBox<Integer> getCheckInComBox() {
		return this.checkInComBox;
	}
	public JComboBox<Integer> getCheckOutComBox() {
		return this.checkOutComBox;
	}
	public JComboBox<String> getReserveListComBox() {
		return this.reserveListComBox;
	}
	public JComboBox<String> getViewMenuComBox() {
		return this.viewMenuComBox;
	}
	public JComboBox<String> getViewDayComBox() {
		return this.viewDayComBox;
	}
	public JTextField getDiscountField() {
		return this.discountField;
	}
	
	
	/*------------------------[LISTENERS]------------------------*/
 	public void setCreateBtnListener(ActionListener actionListener) {
		this.createBtn.addActionListener(actionListener);
	}
	public void setViewBtnListener(ActionListener actionListener) {
		this.viewBtn.addActionListener(actionListener);
	}
	public void setManageBtnListener(ActionListener actionListener) {
		this.manageBtn.addActionListener(actionListener);
	}
	public void setSimulateBtnListener(ActionListener actionListener) {
		this.simulateBtn.addActionListener(actionListener);
	}
	public void setTextFieldListener(KeyListener keyListener) {
		this.textField.addKeyListener(keyListener);
	}
	public void setEnterBtnListener(ActionListener actionListener) {
		this.enterBtn.addActionListener(actionListener);
	}
	public void setHotelListListener(ListSelectionListener listSelectionListener) {
        this.hotelList.addListSelectionListener(listSelectionListener);
	}
	public void setManageMenuListener(ActionListener actionListener) {
		this.manageMenu.addActionListener(actionListener);
	}
	public void setDatePriceComBoxListener(ActionListener actionListener) {
		this.datePriceComBox.addActionListener(actionListener);
	}
	public void setRoomListListener(ActionListener actionListener) {
		this.roomComBox.addActionListener(actionListener);
	}
	public void setRoomTypeListener(ActionListener actionListener) {
		this.roomTypeComBox.addActionListener(actionListener);
	}
	public void setCheckOutListener(ActionListener actionListener) {
		this.checkOutComBox.addActionListener(actionListener);
	}
	public void setReserveListComBoxListener(ActionListener actionListener) {
		this.reserveListComBox.addActionListener(actionListener);
	}
	public void setViewMenuComBoxListener(ActionListener actionListener) {
		this.viewMenuComBox.addActionListener(actionListener);
	}
	public void setViewDayComBoxListener(ActionListener actionListener) {
		this.viewDayComBox.addActionListener(actionListener);
	}
	public void setDiscountFieldListener(KeyListener keyListener) {
		this.discountField.addKeyListener(keyListener);
	}
	/*-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x--x-x-x-x-x-x-x*/

	
	
	
	public void setCurrLblText(String text) {
		this.currLbl.setText(text);
	}
	public void setFeedbackLblText(String text) {
		this.feedbackLbl.setText(text);
	}
	public void setPromptLblText(String text) {
		this.promptLbl.setText(text);
	}
	public void focusTextField() {
		this.textField.grabFocus();
	}
	
	
	
	/* updateHList() updates list all of names of hotel in alphabetical order
	     whenever new hotel is created or removed, this method is called from
	     MyController class
	     @param numHotel: number of hotels created 
	     @param hoteks: ArrayList of hotels*/
	public void updateHList(int numHotel, ArrayList<Hotel> hotels) { //transferred and edited from HotelSystem class
		this.listModel.removeAllElements(); //reset
		for(int i = 0; i < numHotel; i++) {
			listModel.addElement(hotels.get(i).getName());
		}
			
		this.hotelList.setAutoscrolls(true);
		this.hotelList.setVisible(true);
		this.hotelList.setEnabled(true);
	}
	
	/* updateRList() updates list of names of rooms whenever room
	    is created or removed, this is called from MyController class
	    @param numRoom: number of rooms created
	    @param rooms: ArrayList of rooms*/
	public void updateRList(int numRoom, ArrayList<Room> rooms) {
		this.roomComBox.addItem("placeholder");
		this.roomComBox.removeAllItems();
		this.roomComBox.addItem("--Select Room--");
		for (int i = 1; i <= numRoom; i++) {
			if (rooms.get(i-1).getRoomType() == "Standard")
				this.roomComBox.addItem("S|"+String.valueOf(rooms.get(i-1).getNumReserve())+" "+rooms.get(i-1).getName());
			else if (rooms.get(i-1).getRoomType() == "Deluxe")
				this.roomComBox.addItem("D|"+String.valueOf(rooms.get(i-1).getNumReserve())+" "+rooms.get(i-1).getName());
			else if (rooms.get(i-1).getRoomType() == "Executive")
				this.roomComBox.addItem("E|"+String.valueOf(rooms.get(i-1).getNumReserve())+" "+rooms.get(i-1).getName());
		}
		this.roomComBox.setSelectedIndex(0);
	}
	
	/* updateReserveList() updates list of reservations. Each reservation must
	    show check-in, check-out, and guest name
	    @param numReserve: number of reservations in specified room
	    @param reservations: ArrayList of reservations*/
	public void updateReserveList(int numReserve, ArrayList<Reservation> reservations) {
		String checkIn;
		String checkOut;
		String guestName;
		
		this.reserveListComBox.addItem("placeholder");
		this.reserveListComBox.removeAllItems();
		this.reserveListComBox.addItem("--[Booked List]--");
		for (int i = 0; i < numReserve; i++) {
			checkIn = String.valueOf(reservations.get(i).getCheckIn());
			checkOut = String.valueOf(reservations.get(i).getCheckOut());
			guestName = reservations.get(i).getGName();
			this.reserveListComBox.addItem(checkIn+"-"+checkOut+": " + guestName);
		}
		this.reserveListComBox.setSelectedIndex(0);
	}
	
	/* updateDatePrice() updates date price modifier.
	    @param Hotel: specific hotel  */
	public void updateDatePrice(Hotel hotel) {
		String currentRate = String.valueOf(1.0f);
		float[] fCurrent = new float[30];
		fCurrent = hotel.getDatePriceRate();
		
		this.datePriceComBox.addItem("placeholder");
		this.datePriceComBox.removeAllItems();
		this.datePriceComBox.addItem("--[Select Date]--");
		for (int i = 1; i <= 30; i++) {
			currentRate = String.valueOf(fCurrent[i-1]);
			this.datePriceComBox.addItem("Days " + String.valueOf(i)+"-"+String.valueOf(i+1)+": " + currentRate);
		}
		this.datePriceComBox.setSelectedIndex(0);
	}
	
	
	
	/* allowBottomInputs() allows to display inputs found on bottom panel. Has two modes
	     @param mode: '1' if inputs are to be displayed and '0 if not */
	public void allowBottomInputs(char mode) {
		switch (mode) {
			case '0':
				this.promptLbl.setVisible(false);
				this.promptLbl.setText("Input: ");
				this.textField.setVisible(false);
				this.enterBtn.setVisible(false);
				this.promptLbl.setText("Enter");
				this.feedbackLbl.setVisible(false);
				this.feedbackLbl.setText("--------");
				break;
			case '1':
				this.promptLbl.setVisible(true);
				this.promptLbl.setText("Input: ");
				this.textField.setVisible(true);
				this.enterBtn.setVisible(true);
				this.promptLbl.setText("Enter");
				this.feedbackLbl.setVisible(true);
				this.feedbackLbl.setText("--------");
				break;
			case '2':
				this.promptLbl.setVisible(true);
				this.promptLbl.setText("Input: ");
				this.textField.setVisible(false);
				this.enterBtn.setVisible(false);
				this.promptLbl.setText("Enter");
				this.feedbackLbl.setVisible(false);
				this.feedbackLbl.setText("--------");
		}	
	}
	
	
	

	
	/* showManageMenu() gives all possible options for manage after hotel is
	     already selected, It has the following options followed from MCO1
      		1) Change name of hotel
      		2) Add rooms
      		3) Remove rooms
      		4) Update base price of each room
      		5) Remove reservation
      		6) Modify date price (MCO2)     
      		7) Remove hotel 
      	@param isOn: tell if on or off*/
	public void onManageMenu(boolean isOn) {
		if (isOn == false) {
			this.manageMenu.setVisible(false);
			try {
				this.panelCenter.remove(manageMenu);
			} catch (Exception e) {}
		}
		else {
			try {
				this.panelCenter.add(manageMenu);
			} catch (Exception e) {}
			this.manageMenu.setVisible(true);
		}
		manageMenu.setSelectedIndex(0);
	}
	public void onDatePriceComBox(boolean isOn) {
		if (isOn == false) {
			this.getDatePriceComBox().setVisible(false);
			try {
				this.panelCenter.add(datePriceComBox);
			} catch (Exception e) {}
			this.panelCenter.remove(datePriceComBox);
		}
		else {
			try {
				this.panelCenter.add(datePriceComBox);
			} catch (Exception e) {}
			datePriceComBox.setVisible(true);
		}
			
		this.datePriceComBox.setSelectedIndex(0);
	}
	public void onRoomList(boolean isOn) {
		if (isOn == false) {
			this.roomComBox.setVisible(false);
			try {
				this.panelCenter.remove(roomComBox);
			} catch (Exception e) {}
			
		}
		else {
			try {
				this.panelCenter.add(roomComBox);
			} catch (Exception e) {}
			this.roomComBox.setVisible(true);
		}	
		this.roomComBox.setSelectedIndex(0);
	}
	public void onRoomTypeList(boolean isOn) {
		if (isOn == false) {
			this.roomTypeComBox.setVisible(false);
			try {
				this.panelCenter.remove(roomTypeComBox);
			} catch (Exception e) {}
		}
			
		else {
			try {
				this.panelCenter.add(roomTypeComBox);
			} catch (Exception e) {}
			this.roomTypeComBox.setVisible(true);
		}
			
		this.roomTypeComBox.setSelectedIndex(0);
	}
	public void onDateReservePanel(boolean isOn) {
		if (isOn == false) {
			this.dateReservePanel.setVisible(false);
			try {
				this.panelCenter.remove(dateReservePanel);
			} catch (Exception e) {}
			
		}
		else {
			try {
				this.panelCenter.add(dateReservePanel);
			} catch (Exception e) {}
			this.dateReservePanel.setVisible(true);
		}
	}
	public void onReserveListComBox(boolean isOn) {
		if (isOn == false) {
			this.reserveListComBox.setVisible(false);
			try {
				this.panelCenter.remove(reserveListComBox);
			} catch (Exception e) {}
		}		
		else {
			try {
				this.panelCenter.add(reserveListComBox);
			} catch (Exception e) {}
			this.reserveListComBox.setVisible(true);
		}
			
		this.reserveListComBox.setSelectedIndex(0);
	}
	public void onViewMenuComBox(boolean isOn) {
		if (isOn == false) {
			this.viewMenuComBox.setVisible(false);
			try {
				this.panelCenter.remove(viewMenuComBox);
			} catch (Exception e) {}
		}		
		else {
			try {
				this.panelCenter.add(viewMenuComBox);
			} catch (Exception e) {}
			this.viewMenuComBox.setVisible(true);
		}
		this.viewMenuComBox.setSelectedIndex(0);
	}

	public void onViewDayComBox(boolean isOn) {
		if (isOn == false) {
			this.viewDayComBox.setVisible(false);
			try {
				this.panelCenter.remove(viewDayComBox);
			} catch (Exception e) {}
		}		
		else {
			try {
				this.panelCenter.add(viewDayComBox);
			} catch (Exception e) {}
			this.viewDayComBox.setVisible(true);
		}
		this.viewDayComBox.setSelectedIndex(0);
	}
	public void onDiscountField(boolean isOn) {
		if (isOn == false) {
			this.discountField.setVisible(false);
			try {
				this.panelCenter.remove(discountField);
			} catch (Exception e) {}
		}		
		else {
			try {
				this.panelCenter.add(discountField);
			} catch (Exception e) {}
			this.discountField.setVisible(true);
		}
		this.discountField.setText("Enter discount");
	}
	
	
	
	
	/* resetFromSwitching() reverts back and clears all selections of each
	     element whenever user clicked any one of the main buttons
	     @param mode: how is it going to be reverted*/
	public void resetFromSwitching(String mode) {
		
		if (mode == "Button" || mode == "Hotel") {
			if (mode == "Button")
				this.hotelList.clearSelection();
			this.manageMenu.setSelectedIndex(0);
			this.datePriceComBox.setSelectedIndex(0);
			this.roomComBox.setSelectedIndex(0);
			this.checkInComBox.setSelectedIndex(0);
			this.checkOutComBox.setSelectedIndex(0);
			this.reserveListComBox.setSelectedIndex(0);
			this.viewMenuComBox.setSelectedIndex(0);
			this.viewDayComBox.setSelectedIndex(0);
			this.discountField.setText("Enter discount");
			
			this.manageMenu.setVisible(false);
			this.datePriceComBox.setVisible(false);
			this.roomComBox.setVisible(false);
			this.roomTypeComBox.setVisible(false);
			this.dateReservePanel.setVisible(false);
			this.reserveListComBox.setVisible(false);
			this.viewMenuComBox.setVisible(false);
			this.panelCenter.removeAll();	
			

			allowBottomInputs('0');
				
		}	
	}
	
	
	/* viewHigh() views overall info about the hotel
    	@param hotels: hotel to be displayed */
	public void viewHigh(Hotel hotel) {
		viewDialog = new JDialog(mainFrame);
		viewDialog.setTitle(hotel.getName()+"|View high");
		viewDialog.setSize(400, 200);
		viewTextArea = new JTextArea();
		viewTextArea.setFont(new Font("Arial", Font.PLAIN, 20));
		
		viewTextArea.append("Hotel name:       " + hotel.getName() + "\n");
		viewTextArea.append("# of Rooms:       " + hotel.getNumRoom() + "\n");
		viewTextArea.append("Monthly Earnings: " + String.valueOf(hotel.getMEarnings())+"\n");
		viewTextArea.setEditable(false);
		
		viewDialog.add(viewTextArea);
		viewDialog.setVisible(true);
	}
	
	
	/* viewLowAvailability() views list and number of rooms available and
	    booked for selected date 
	    @param selectedDay: day to see list of available rooms and booked rooms
	    @param hotel: hotel to be displayed */
	public void viewLowAvailability(int selectedDay, Hotel hotel) {
		int nBooked = 0;
		int nAvailable = 0;
		String roomName;
		String roomType;
		
		viewDialog = new JDialog(mainFrame);
		viewDialog.setTitle(hotel.getName() + "|View Low: Availability on Day " + String.valueOf(selectedDay));
		viewDialog.setSize(300, 400);
		viewTextArea = new JTextArea();
		viewTextArea.setFont(new Font("Arial", Font.PLAIN, 20));
		
		viewTextArea.append("[Room List]\n");
		for (int i = 0; i < hotel.getNumRoom(); i++) {
			roomName = hotel.getRooms().get(i).getName();
			roomType = String.valueOf(hotel.getRooms().get(i).getRoomType().charAt(0));
			if (isOccupied(selectedDay, hotel.getRooms().get(i)) ) {
				viewTextArea.append(" " + roomType + roomName + "|\n");
				nBooked++;
			}
			else {
				viewTextArea.append(" " + roomType + roomName + "| Available\n");
				nAvailable++;
			}
		}
		viewTextArea.append("\n");
		viewTextArea.append("Booked rooms = " + String.valueOf(nBooked) + "\n");
		viewTextArea.append("Available rooms = " + String.valueOf(nAvailable) + "\n");
		viewTextArea.append("Total rooms = " + String.valueOf(nAvailable + nBooked) + "\n");
		
		viewTextArea.setEditable(false);
		viewDialog.add(viewTextArea);
		viewDialog.setVisible(true);
	}
	
	
	/* viewLowRoom() views info about the rooms for selected hotel
	    @param hotelName: name of hotel
    	@param room: room to be displayed */
	public void viewLowRoom(String hotelName, Room room) { //adapted from MCO1
		String roomName = String.valueOf(room.getRoomType().charAt(0)) + room.getName();
		viewDialog = new JDialog(mainFrame);
		viewDialog.setTitle(hotelName+"|View Low: Room " + roomName);
		viewDialog.setSize(300, 900);
		viewTextArea = new JTextArea();
		viewTextArea.setFont(new Font("Arial", Font.PLAIN, 20));
		
		viewTextArea.append("[Room " + roomName + "]\n");
		viewTextArea.append(" Price per night: " + String.valueOf(room.getReservations().get(0).getNPrice()) + "\n");
		
		viewTextArea.append("Availability: " + "\n");
		for (int i = 1; i<= 31; i++) {
			viewTextArea.append(" day " + String.valueOf(i) + ": ");
			if(!isOccupied(i, room))
				viewTextArea.append("--vacant--");
			viewTextArea.append("\n");
		}
		
		
		viewTextArea.setEditable(false);
		viewDialog.add(viewTextArea);
		viewDialog.setVisible(true);
	}
	
	/* viewLowReserve() views info about the selected reservation
	    @param hotelName: name of hotel
		@param reservation: reservation to be displayed */
	public void viewLowReserve(String hotelName, Reservation reservation) { //adapted from MCO1
		viewDialog = new JDialog(mainFrame);
		viewDialog.setTitle(hotelName+"|View Low: Reservation " + reservation.getGName());
		viewDialog.setSize(300, 400);
		viewTextArea = new JTextArea();
		viewTextArea.setFont(new Font("Arial", Font.PLAIN, 20));
		
		viewTextArea.append("[Reservation Info]"+"\n");
		viewTextArea.append("  guest name:      "+reservation.getGName()+"\n");
		viewTextArea.append("  room info:       "+reservation.getLink()+"\n");
		viewTextArea.append("  check-in:        day "+String.valueOf(reservation.getCheckIn())+"\n");
		viewTextArea.append("  check-out:       day"+String.valueOf(reservation.getCheckOut())+"\n");
		viewTextArea.append("  price per night: "+String.valueOf(reservation.getNPrice())+"\n");
		viewTextArea.append("  total price:     "+String.valueOf(reservation.getTPrice())+"\n");
		viewTextArea.append("  discounted:      "+String.valueOf(reservation.getDiscount())+"\n");
		viewTextArea.append("--------------------------"+"\n");
		
		viewTextArea.setEditable(false);
		viewDialog.add(viewTextArea);
		viewDialog.setVisible(true);
	
	}
	
	
	/* isOcuppied() is a Boolean method telling whether the given room indexed
    	from particular date is avaiable or not.
    	@param nSDate: date selected (1-31)
    	@param room: room to check its reservation content
    	@return isOccupied - whether room is avaialable or not  */
	public boolean isOccupied(int nSDate, Room room) { //Edited and fromFrom MCO1 Room Class
		boolean isOccupied;
		isOccupied = false;
		for (int i = 0; i < room.getNumReserve() && !isOccupied; i++) {
			if ((room.getReservations().get(i).getCheckIn() <= nSDate) && (nSDate <= room.getReservations().get(i).getCheckOut()) )
				isOccupied = true;
		}
		return isOccupied;
	}
	
}