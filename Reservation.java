package Version2;


public class Reservation {
  private String guestName;
  private int checkIn;
  private int checkOut;
  private String link;
  private float totalPrice;
  private float costPerNight;
  private float discount = 0.0f;


  
  public Reservation() { //for removeReserve()
    guestName = "";
    checkIn = -1;
    checkOut = -1;
    link = "Room Link";
    totalPrice = 0.0f;
    costPerNight = 0.0f;
  }
  public Reservation(int checkIn, int checkOut, String guestName, float basePrice, float[] datePRate, String discount) { //for MCO2
	 this.guestName = guestName;
	 this.checkIn = checkIn;
	 this.checkOut = checkOut;
	 this.link = "Room Link";
	 this.totalPrice = 0.0f;
	 this.costPerNight = 0.0f;
	 calcCost(basePrice, datePRate);
	 inputDisc(discount);
  }


  public String getGName() {
    return guestName;
  }
  public int getCheckIn() {
    return checkIn;
  }
  public int getCheckOut() {
    return checkOut;
  }
  public String getLink() {
    return link;
  }
  public float getTPrice() {
    return totalPrice;
  }
  public float getNPrice() {
    return costPerNight;
  } 
  public float getDiscount() {
    return discount;
  }

  public void setGName(String guestName) {
    this.guestName = guestName;
  }
  public void setCheckIn(int checkIn) {
    this.checkIn = checkIn;
  }
  public void setCheckOut(int checkOut) {
    this.checkOut = checkOut;
  }
  public void setLink(String link) {
    this.link = link;
  }
  public void setDiscount(float discount) {
    this.discount = discount;
  }



  /* calcCost() called from simulateReserve() calculates the
      total price of the booking and breakdown of price per
      night while also updating cost per night.  
      @param basePrice - base price for each room in entire hotel called from rIncPrice 
      @param datePRate - array of date price rate for each day in the goetl
      @return totalPrice - total price of whole reservation*/
  public void calcCost(float basePrice, float[] datePRate) { //simulateReserve()
    this.totalPrice = 0.0f;
    for (int i = getCheckIn(); i < getCheckOut(); i++) 
        this.totalPrice += basePrice * datePRate[i-1];
    this.costPerNight = this.totalPrice / (1 + getCheckOut() - getCheckIn());
  }

  /*MCO2: inputDisc() grants discount for the total price 
     if code inputted is correct. Has 3 discount codes with 
     varying conditions and grants. Besides, also displays
     reservation fee of customer */
  public void inputDisc(String discountCode) {
    if (discountCode.equals("I_WORK_HERE")) {
      setDiscount(this.totalPrice*0.1f);
      this.totalPrice -= getDiscount();
    }
    else if (discountCode.equals("STAY4_GET1")) {
        setDiscount(getNPrice());
        this.totalPrice -= getNPrice();
    }
    else if (discountCode.equals("PAYDAY")) {
        setDiscount(this.totalPrice*0.07f);
        this.totalPrice -= getDiscount();
    }
  }



}