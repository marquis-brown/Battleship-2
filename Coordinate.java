public class Coordinate {
	
	private int xPos;
	private int yPos;
	
	public Coordinate(int x, int y) {
		xPos = x;
		yPos = y;
	}
	public Coordinate(){
		xPos = 0;
		yPos = 0;
	}
	public int getXPos() {
		return xPos;
	}
	public int getYPos() {
		return yPos;
	}
	public void setXPos(int x) {
		xPos = x;
	}
	public void setYPos(int y) {
		yPos = y;
	}
	// Checks if this coordinate is the same as another coordinate 
	public boolean equals(Coordinate c) {
		if(xPos == c.getXPos() && yPos == c.getYPos())
			return true;
		else
			return false;
	}
	// Prints the coordinate in (x,y) format
	public String toString() {
		return "(" + xPos + ", " + yPos + ")"; 
	}
}
