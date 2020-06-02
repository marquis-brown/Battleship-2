import java.util.*;

public class Battleship {

	private Coordinate shipHead; //Starting coordinate for each ship
	private ArrayList<Coordinate> shipCoords; //Set of ship coordinates
	private String orientation; //Whether vertical or horizontal 
	private int size; //Amount of coordinates this ship contains
	private boolean status; //False = sunk, true = afloat 
	
	public Battleship(Coordinate c, String vertOrHoriz, int length) {
		shipHead = c;
		orientation = vertOrHoriz;
		size = length;
		status = true;
		shipCoords = buildShip(shipHead, orientation, size);
	}
	
	// Builds the arraylist of coordinates of the ship
	public ArrayList<Coordinate> buildShip(Coordinate c, String vertOrHoriz, int length) {
		ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
		if(vertOrHoriz.equalsIgnoreCase("VERTICAL")) {
			//Prevents the ship from having y-coordinates beyond 10
			if(c.getYPos() >= length) {
				for(int i = 0; i < length; i++) {
					Coordinate temp = new Coordinate(c.getXPos(), (c.getYPos()-i));
					coords.add(temp);
				}
			} else {
				for(int i = 0; i < length; i++) {
					Coordinate temp = new Coordinate(c.getXPos(), (c.getYPos()+i));
					coords.add(temp);
				}
			}
		} else {
			//Prevents the ship from having x-coordinates beyond 10
			if(c.getXPos() >= length) {
				for(int i = 0; i < length; i++) {
					Coordinate temp = new Coordinate((c.getXPos()-i), c.getYPos());
					coords.add(temp);
				}
			} else {
				for(int i = 0; i < length; i++) {
					Coordinate temp = new Coordinate((c.getXPos()+i), (c.getYPos()));
					coords.add(temp);
				}
			}
		}
		return coords;
	}
	
	//Checks if a Coordinate c matches any of this ships coordinates
	// If it does it removes that coordinate and records the HIT
	public boolean hitOrMiss(Coordinate c) {
		for(int i = 0; i < shipCoords.size(); i++) {
		 	if(shipCoords.get(i).equals(c)) {
				shipCoords.remove(i);
				size--;
				if(shipCoords.isEmpty()) {
					status = false;
					System.out.println("THIS SHIP HAS SUNK!");
					return true;
				}
				return true;
			}
		}
		return false;
	}
	// Prints out THIS battleship
	public String toString() {
		String ship = "{";
		for(int i = 0; i < shipCoords.size(); i++) {
			ship += " " + shipCoords.get(i);
		}
		ship += "}";
		return ship;
	}
	// Tells us the ship head of THIS battleship
	public Coordinate getShipHead() {
		return shipHead;
	}
	// Tells us if THIS battleship is afloat or sunk
	public boolean getStatus() {
		return status;
	}
	// Tells us the size of THIS battleship
	public int getSize() {
		return size;
	}
	// Tells us if THIS battleship is vertical or horizontal
	public String getOrientation() {
		return orientation;
	}
	
	// Delivers THIS battleship's coordinates
	public ArrayList<Coordinate> getShipCoords() {
		return shipCoords;
	}
	
	// Checks if THIS battleship is on coordinate C 
	public boolean contains(Coordinate c) {
		for(int i = 0; i < shipCoords.size(); i++) {
			if(c.equals(shipCoords.get(i)))
				return true;
		}
		return false;
	}
	// Checks if THIS battleship shares a coordinate with ship2
	public boolean validShip(Battleship ship2){
		ArrayList<Coordinate> checkCoords = ship2.getShipCoords();
		for(int i = 0; i < checkCoords.size(); i++){
			if(this.contains(checkCoords.get(i)))
				return false;
		}
		return true;
	}
}

