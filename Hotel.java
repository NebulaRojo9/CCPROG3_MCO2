package Version2;

import java.util.ArrayList;

public class Hotel {
    private String name; //name of hotel
    private static final int maxRoom = 50;
    private ArrayList<Room> rooms = new ArrayList<Room>();
    private float MEarnings; //monthly earnings
    private int numRoom;  
    private int totalRCreated;
    private float basePrice; //Transferred from Room in MCO1
    private float[] datePRate; //MCO2, date price modifier 
    


    public Hotel(String nameH) {
      this.name = nameH;
      this.MEarnings = 0.0f;
      this.numRoom = 0;
      this.totalRCreated = 0;
      this.basePrice = 1299.0f;
      this.datePRate = new float[30];
      
      rooms.add(new RStandard(100 + totalRCreated + 1) );
      this.totalRCreated++;
      this.numRoom++;

      //FOR MCO2: Date Price modifier default all to 100%
      for(int i = 0; i < 30; i++)
          datePRate[i] = 1.0f;
    }


    public String getName() {
      return name;
    }
    public static final int getMaxRoom() {
        return maxRoom;
    }
    public ArrayList<Room> getRooms() {
    	return rooms;
    }
    public int getNumRoom() {
        return numRoom;
    }
    public float getMEarnings() {
    	return MEarnings;
    }
    public int getTotalRCreated() {
        return totalRCreated;
    }
    public float getBasePrice() {
    	return this.basePrice;
    }
    public float[] getDatePriceRate() {
    	return this.datePRate;
    }

    public void setName(String name) {
      this.name = name;
    }
    public void setNumRoom(int numRoom) {
        this.numRoom = numRoom;
    }
    public void setTotalRCreated(int totalRCreated) {
        this.totalRCreated = totalRCreated;
    }
    public void setBasePrice(float basePrice) {
    	this.basePrice = basePrice;
    }
    public void setDatePriceRate(int index, float fRate) {
    	this.datePRate[index] = fRate;
    }
    
    /* calcMEarnings() calculates monthly earnings of whole hotel.
    	It it is calculated using total price per reservation in each
    	room   
    	@return MEarnings - monthly salary of whole hotel */ 
    public void calcMEarnings() { //from MCO1
        this.MEarnings = 0.0f;
        for (int i = 0; i < getNumRoom(); i++)
            this.MEarnings += rooms.get(i).calcTPrice();
    }
    
    

}
