import java.util.*; 

public class PlayerCPU {

	private ArrayList<Coordinate> CPUfield = buildCPUfield(); //The CPU's target board 
	private ArrayList<Battleship> battleships = new ArrayList<Battleship>();
	private String[] vertOrHoriz = {"Vertical", "Horizontal"}; //Used to help randomize the CPU's ship orientation
	private ArrayList<Coordinate> coordGuess = new ArrayList<Coordinate>(); //Keeps track of CPU's guesses 
	private Stack<Coordinate> potentialTargets = new Stack<Coordinate>(); //CPU stores optimal targets
	private ArrayList<Coordinate> CPUHits = new ArrayList<Coordinate>(); //Keeps track of CPU's hits
	private int numOfShips = 5;
	
	public PlayerCPU() {		
		Random rand = new Random(); 
		int length = 6;
		int count = 0;
		do {
			Coordinate shiphead = randomCoord();
			Battleship ship = new Battleship(shiphead, vertOrHoriz[rand.nextInt(2)], length);
			if(battleships.isEmpty()){
				battleships.add(ship);
				count++;
				length--;
			} else {
				int validCounter = 0; 
				for(int i = 0; i < battleships.size(); i++){
					if(battleships.get(i).validShip(ship)){
						validCounter++;
					}
					if(validCounter == battleships.size()){
						//The ship doesn't have conflicting coordinates
						battleships.add(ship);
						count++;
						length--;
					}
				}
			}
		} while(count < numOfShips);	
	}
	// Generates a random Coordinate
	public Coordinate randomCoord() {
		Random rand = new Random();
		int randomIndex = rand.nextInt(CPUfield.size());
		Coordinate random = CPUfield.get(randomIndex);
		CPUfield.remove(random);
		return random;		
	}
	// Checks if the CPU has already used this cooridnate missle
	public boolean validMissle(Coordinate missle) {
		for(int i = 0; i < CPUfield.size(); i++) {
			if(CPUfield.get(i).equals(missle)) 
				return false;
		}
		return true;
	}
	
	public Coordinate fire() {
		Coordinate missle = randomCoord();
		if(!potentialTargets.isEmpty()){
			missle = potentialTargets.pop();
		} 
		if(coordGuess.contains(missle)){
			fire();
		}
		coordGuess.add(missle);
		return missle;
	}
	// Helps the CPU make smarter choices, by taking a previous missle hit and 
	// making its four surronding coordinate targets 
	public void nextTarget(Coordinate hit) {
		CPUHits.add(hit);
		int targetX = hit.getXPos();
		int targetY = hit.getYPos();
		Coordinate targetLeft = new Coordinate();
		Coordinate targetTop = new Coordinate();
		Coordinate targetRight = new Coordinate();
		Coordinate targetBottom = new Coordinate();
		boolean left = false;
		boolean top = false;
		boolean right = false;
		boolean bottom = false;
		if(targetX != 1) {
			targetLeft = new Coordinate(targetX-1, targetY);
			left = true;
		} else 
		if(targetY != 10) {
			targetTop = new Coordinate(targetX, targetY+1);
			top = true;
		}
		if(targetX != 10) {
			targetRight = new Coordinate(targetX+1, targetY);
			right = true;
		}
		if(targetY != 1) {
			targetBottom = new Coordinate(targetX, targetY-1);			
			bottom = true;
		}
		if(left && top && right && bottom) {
			potentialTargets.push(targetLeft);
			potentialTargets.push(targetTop);
			potentialTargets.push(targetRight);
			potentialTargets.push(targetBottom);
			//break;
		} else if(left && top && right) {
			potentialTargets.push(targetLeft);
			potentialTargets.push(targetTop);
			potentialTargets.push(targetRight);
			//break;
		} else if(top && right && bottom) {
			potentialTargets.push(targetTop);
			potentialTargets.push(targetRight);
			potentialTargets.push(targetBottom);
		} else if(left && right && bottom) {
			potentialTargets.push(targetLeft);
			potentialTargets.push(targetRight);
			potentialTargets.push(targetBottom);
		} else if(left && top && bottom) {
			potentialTargets.push(targetLeft);
			potentialTargets.push(targetTop);
			potentialTargets.push(targetBottom);
		} else if(left && top) {
			potentialTargets.push(targetLeft);
			potentialTargets.push(targetTop);
		} else if(top && right) {
			potentialTargets.push(targetTop);
			potentialTargets.push(targetRight);
		} else if(right && bottom) {
			potentialTargets.push(targetRight);
			potentialTargets.push(targetBottom);
		} else if(bottom && left) {
			potentialTargets.push(targetLeft);
			potentialTargets.push(targetBottom);
		}
		for(int i = 0; i < potentialTargets.size(); i++){
			coordGuess.add(potentialTargets.get(i));
			CPUfield.remove(potentialTargets.get(i));
		}

	}
	// Checks if a coordinate missle matches one of THIS CPU's battleship coordinates
	public boolean hitOrMiss(Coordinate missle) {
		int counter = 0;
		for(int i = 0; i < battleships.size(); i++) {
			if(battleships.get(i).hitOrMiss(missle)){
				counter++;
			}
			if(counter > 0)
				return true;
		}
	   return false;
	}
	// Builds the CPU's coordinate target field (1,1)-(10,10)
	public ArrayList<Coordinate> buildCPUfield() {
		ArrayList<Coordinate> field = new ArrayList<Coordinate>();
		for(int i = 1; i <= 10; i++) {
			for(int j = 1; j <= 10; j++) {
				Coordinate c = new Coordinate(i, j);
				field.add(c);
			}			
		}
		return field;
	}
	// Returns THIS CPU's battleships
	public ArrayList<Battleship> getBattleships() {
		return battleships;
	}
	// Removes a battleship from play
	public void removeBattleship() {
		for(int i = 0; i < battleships.size(); i++) {
			if(!battleships.get(i).getStatus()) {
				battleships.remove(i);
				numOfShips--;
			}
		}	
	}
	// Returns how many battleships THIS CPU has left in play 
	public int getNumOfShips(){
		return numOfShips;
	}
	// Returns the a set of THIS CPU's coordinate targets
	public ArrayList<Coordinate> getCoordGuesses() {
		return coordGuess;
	}
	// Displays THIS CPU's battleships
	public void printShips() {
		String ships = "";
		for(int i = 0; i < numOfShips; i++) {
			if(battleships.get(i).getSize() != 0)
				ships += battleships.get(i) + "\n";	
			else {
				this.removeBattleship();
			}	
		}
		System.out.println(ships);
	}

}
