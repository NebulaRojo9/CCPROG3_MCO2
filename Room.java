package Version2;

import java.util.ArrayList;

public abstract class Room {
  protected String name = "---";
  protected float basePrice = 1299.0f;
  protected float fTotalPrice = 0.0f; //total price summed from each reservation
  protected int numReserve = 0; //aka booked
  protected ArrayList<Reservation> reservations = new ArrayList<Reservation>();
  protected String roomType;


  /*public Room(int roomNo) {
        name = String.valueOf(roomNo);
        numReserve = 0;
  } REMOVED FOR MCO2 */

  public ArrayList<Reservation> getReservations() {
	  return reservations;
  }
  public String getName() {
    return name;
  }
  public float getBasePrice() {
    return basePrice;
  }
  public int getNumReserve() {
    return numReserve;
  }
  public float getTotalPrice() {
    return fTotalPrice;
  }
  public String getRoomType() {
    return roomType;
  }

  public void setName(String input) {
    this.name = input;
  }
  public void setBasePrice(float basePrice) {
    this.basePrice = basePrice;
  }
  public void setNumReserve(int numReserve) {
    this.numReserve = numReserve;
  }




  /* isOcuppied() is a Boolean method telling whether the given room indexed
       from particular date is avaiable or not.
       @param nSDate: date selected (1-31)
       @return isOccupied - whether room is avaialable or not  */
  public boolean isOccupied(int nSDate) {
    boolean isOccupied;
    isOccupied = false;
    for (int i = 0; i < getNumReserve() && !isOccupied; i++) {
      if ( (reservations.get(i).getCheckIn() <= nSDate) && (nSDate <= reservations.get(i).getCheckOut()) )
         isOccupied = true;
      }
    return isOccupied;
  }
  

  /* checkOverlap() called from simulateReserve() checks whether inputted
       check-in and check-out overlaps all other timeslot for other reservations
       within the same room.
       @param nStart: inputted check-in
       @param nEnd: inputted checkout 
       @return isOverlap - if it inputted date overlaps any dates  */
  public boolean checkOverlap(int nStart, int nEnd) { //for simulateReserve()
    boolean isOverlap;
    isOverlap = false;

      // nStart CheckIn CheckOut nEnd
      // CheckIn nStart CheckOut nEnd
      // CheckIn nStart nEnd CheckOut
      for (int i = 0; i < getNumReserve() && !isOverlap; i++) {
        if (nStart <= reservations.get(i).getCheckIn()
             && nEnd > reservations.get(i).getCheckIn())
          isOverlap = true;
        if (nStart < reservations.get(i).getCheckOut() 
            && nEnd >= reservations.get(i).getCheckOut())
          isOverlap = true;
        if (reservations.get(i).getCheckIn() <= nStart
             && nEnd <= reservations.get(i).getCheckOut())
          isOverlap = true;
      }
    return isOverlap;
  }

  /* calcTPrice() called from simulateReserve() sums up all reservations
       in one room. This is useful when calculating monthly earnings in a
       whole hotel itself.
       @return fTotalPrice - total price of whole room*/
  public float calcTPrice() {
    this.fTotalPrice = 0.0f;
    for (int i = 0; i < getNumReserve(); i++)
      fTotalPrice += reservations.get(i).getTPrice();
    return this.fTotalPrice;
  }

  /* MCO2: rIncPrice() further updates base price of the room based on its type:
        standard = basePrice
        deluxe = basePrice * 1.20
        executive = basePrice * 1.35 
      @return res - updated based price  */
  public float rIncPrice(float hotelPrice) {
    float res = hotelPrice;
    if (getRoomType().equals("Standard"))
      res = hotelPrice;
    else if (getRoomType().equals("Deluxe"))
      res = hotelPrice*1.20f;
    else if (getRoomType().equals("Executive"))
      res = hotelPrice*1.35f;
    basePrice = res;
    return res;
  }

}