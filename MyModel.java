package Version2;

import java.util.*;



public class MyModel {
  private ArrayList<Hotel> hotels; //transferred from HotelSystem class
  private int numHotel; //transferred from HotelSystem class
  
  public MyModel() {
    this.hotels = new ArrayList<Hotel>();
	this.numHotel = 0;
  }
  
  
  public ArrayList<Hotel> getHotels() {
	  return hotels;
  }
  public String[] getHNames() {
	  String[] HNames = new String[numHotel];
	  for (int i = 0; i < numHotel; i++)
		  HNames[i] = hotels.get(i).getName();
	  return HNames;
  }
  public int getNumHotel(){ //transferred from HotelSystem class
	  return numHotel;
  }
  public void setNumHotel(int numHotel) { //transferred from HotelSystem class
	  this.numHotel = numHotel;
  }
  
  
  
  
  /* sortHotel() sorts all created hotels based on alphabetical order.
  	  It is called from listHotel() */
  public void sortHotel() { //transferred and edited from HotelSystem class
	  Hotel temp;
	  int min;

	  for (int i = 0; i < numHotel; i++) {
		  min = i;
		  for (int j = i + 1; j < numHotel; j++)
			  if (this.hotels.get(min).getName().compareTo(this.hotels.get(j).getName()) > 0)
				  min = j;

		  if (i != min) {
			  temp = this.hotels.get(i);
			  this.hotels.set(i, this.hotels.get(min));
			  this.hotels.set(min, temp);
		  }
	  }
  }
  
  /* addHotel() simply means adding instance of hotel to arrayList once
  the condition from MyController class is satisfied */
  public void addHotel(String strInput) {
	  hotels.add(new Hotel(strInput));
	  setNumHotel(this.numHotel + 1);
  }
  
  /* removeHotel() is removing of hotel whenever user wishes to. Like
      in addHotel(), it is called from MyController class and there
      must be confirmation prompt
      @param selectedHotel - index to be deleted */
  public void removeHotel(int selectedHotel) {
	  hotels.remove(selectedHotel);
	  setNumHotel(this.numHotel - 1);
  }
  
  

  /* checkChangeName() adapted and edited from MCO1 determines if name of hotel to be changed is
  		valid or not. Will change the content of model and name if valid. Otherwise, it will
  		display error message
  		@param indxHotel: index of hotel to be changed 
  		@param strInput: name to be replaced to assigned index of hotel */
  public void changeName(int indxHotel, String strInput) { //transferred and edited from HotelSystem class
	  	System.out.println("   " + hotels.get(indxHotel).getName() + " -> " + strInput);
	  	hotels.get(indxHotel).setName(strInput);
  }

  /* updateBasePrice() changes base price of hotel which affects the monthly computation 
      @param indxHotel: index of hotel to be changed
      @param fInput: base price of hotel to be updated */
  public void updateBasePrice(int indxHotel, float fInput) {
	  System.out.println("   " + hotels.get(indxHotel).getBasePrice() + " -> " + fInput);
	  hotels.get(indxHotel).setBasePrice(fInput);
  }
  
  /* addRoom() adds rooms of hotel based on user input following the parameter 
       @param indxHotel: index of hotel to be added
       @param roomType: index type of room to be added (standard, deluxe, executive)
       @param number: how many room to be added */
  public void addRoom(int indxHotel, int type, int number) { //transferred and edited from Hotel class
	  int numRoom = hotels.get(indxHotel).getNumRoom(); //naming convention of room
	  int rTotalCreated = hotels.get(indxHotel).getTotalRCreated(); //total number of rooms created
	  
	  for (int i = 0; i < number; i++) {
		  if (type == 0) //Standard
			  hotels.get(indxHotel).getRooms().add(new RStandard(100 + rTotalCreated + 1));
		  if (type == 1) //Deluxe
			  hotels.get(indxHotel).getRooms().add(new RDeluxe(100 + rTotalCreated + 1));
		  if (type ==2) //Executive
			  hotels.get(indxHotel).getRooms().add(new RExecutive(100 + rTotalCreated + 1));
		  hotels.get(indxHotel).setNumRoom(numRoom+1);
		  hotels.get(indxHotel).setTotalRCreated(rTotalCreated+1);
	      numRoom = hotels.get(indxHotel).getNumRoom();
	      rTotalCreated = hotels.get(indxHotel).getTotalRCreated();
	  }	
  }
  
  /* removeRoom() removes room based on assigned index and hotel 
       @param indxHotel: index of hotel
       @param indxRoom: index of room */
  public void removeRoom(int indxHotel, int indxRoom) {
	  int numRoom = hotels.get(indxHotel).getNumRoom(); //number of rooms
	  hotels.get(indxHotel).getRooms().remove(indxRoom);
	  hotels.get(indxHotel).setNumRoom(numRoom-1);
	  numRoom = hotels.get(indxHotel).getNumRoom();
  }
  
  /* addReserve() adds reservation with specified index of hotel, room, checkin,
       checkout, and guest Name 
       @param indxHotel: index of hotel
       @param indxRoom: index of room
       @param checkIn: inputted checkin
       @param checkOut: inputted checkOut
       @param guestName: inputted GuestName
       @param discountCode: inputted Discount Code*/
  public void addReserve(int indxHotel, int indxRoom, int checkIn, int checkOut, String guestName, String discountCode) {
	  float[] datePrice = new float[30]; //computation
	  datePrice = hotels.get(indxHotel).getDatePriceRate(); //computation
	  float hotelbasePrice = hotels.get(indxHotel).getBasePrice(); //also includes type of room
	  float roomBasePrice = hotels.get(indxHotel).getRooms().get(indxRoom).rIncPrice(hotelbasePrice);
	  int numReserve = hotels.get(indxHotel).getRooms().get(indxRoom).getNumReserve();
	  
	  hotels.get(indxHotel).getRooms().get(indxRoom).setNumReserve(numReserve+1);
	  numReserve = hotels.get(indxHotel).getRooms().get(indxRoom).getNumReserve();
	  
	  //Computation proper
	  hotels.get(indxHotel).getRooms().get(indxRoom).getReservations().add(new Reservation(checkIn, checkOut, guestName, roomBasePrice, datePrice, discountCode));
	  hotels.get(indxHotel).calcMEarnings();
	  
	  sortReserve(indxHotel, indxRoom);
  
  }
  
  /* sortReserve() sorts all reservations based on order of checkIn, has specified parameters
      @param indxHotel: index of hotel
      @param indxRoom: index of room */
  public void sortReserve(int indxHotel, int indxRoom) {
	  Reservation temp;
	  int checkInMin, checkInJ;
	  int checkOutMin, checkOutJ;
	  int min;
	  int numReserve = this.hotels.get(indxHotel).getRooms().get(indxRoom).getNumReserve();
	  
	  for (int i = 0; i < numReserve; i++) {
		  min = i;
		  for (int j = i + 1; j < numReserve; j++) {
			  checkInMin = this.hotels.get(indxHotel).getRooms().get(indxRoom).getReservations().get(min).getCheckIn();
			  checkInJ = this.hotels.get(indxHotel).getRooms().get(indxRoom).getReservations().get(j).getCheckIn();
			  checkOutMin = this.hotels.get(indxHotel).getRooms().get(indxRoom).getReservations().get(min).getCheckOut();
			  checkOutJ = this.hotels.get(indxHotel).getRooms().get(indxRoom).getReservations().get(j).getCheckOut();;
			  if (checkInMin > checkInJ)
				  min = j;
			  else if (checkInMin == checkInJ)
				  if (checkOutMin > checkOutJ)
					  min = j;
		  }
		  if (i != min) {
			  temp = this.hotels.get(indxHotel).getRooms().get(indxRoom).getReservations().get(i);
			  this.hotels.get(indxHotel).getRooms().get(indxRoom).getReservations().set(i, this.hotels.get(indxHotel).getRooms().get(indxRoom).getReservations().get(min));
			  this.hotels.get(indxHotel).getRooms().get(indxRoom).getReservations().set(min, temp);
		  }
	  }
  }
  
  
  /*countHotelReservations() counts the total number of reservations in whole hotel. 
      In other words, it is accummulation of adding number of reservations in each room as
      part of hotel. This is used for base price reservation
      @param indxHotel: index of hotel being selected 
      @return hotelSum: total number of hotel reservations */
  public int countHotelReservations(int indxHotel) {
	  int hotelSum = 0;
	  for (int i = 0; i < hotels.get(indxHotel).getNumRoom(); i++)
		  hotelSum += countRoomReservations(indxHotel, i);
	  return hotelSum;
  }
  
  
  /*countRoomReservations() counts total number of reservations in whole room. It is
      sometimes accompanied by countHotelReservation() and used particularly in remove
      room. It is also used in basePrice 
      @param indxHotel: index of hotel being selected
      @param indxRoom: index of room
      @return roomSum: total number of room reservations */
  public int countRoomReservations(int indxHotel, int indxRoom) {
	  return hotels.get(indxHotel).getRooms().get(indxRoom).getNumReserve();
  }
  
  
  /* removeReservation() is act of removing reservation
       @param indxHotel: index of hotel being selected
       @param indxRoom: index of room being selected*/
  public void removeReservations(int indxHotel, int indxRoom, int indxReserve) {
	  int numReserve = hotels.get(indxHotel).getRooms().get(indxRoom).getNumReserve();
	  hotels.get(indxHotel).getRooms().get(indxRoom).getReservations().remove(indxReserve);
	  hotels.get(indxHotel).getRooms().get(indxRoom).setNumReserve(numReserve-1);
	  numReserve = hotels.get(indxHotel).getRooms().get(indxRoom).getNumReserve();
	  
	  //computation proper
	  hotels.get(indxHotel).calcMEarnings();
  }
  
  /* modifyDatePrice() modifies date price of hotel. Assume
      inputs coming from MyController class is correct
      @param indxHotel: index hotel being selected
      @param indxDate: index of date to be modified 
      @param fRate: rate to be modified*/
  public void modifyDatePrice(int indxHotel, int indxDate, float fRate) {
	  hotels.get(indxHotel).setDatePriceRate(indxDate, fRate);
  }
  
  
}