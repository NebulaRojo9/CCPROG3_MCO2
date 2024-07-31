package Version2;

public class RStandard extends Room {
	public RStandard(int roomNo) {
		name = String.valueOf(roomNo);
	    numReserve = 0;
	    roomType = "Standard";
	}
}
