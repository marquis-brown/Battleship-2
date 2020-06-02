import java.util.*;

public class Player {

	private ArrayList<Battleship> playerShips = new ArrayList<Battleship>();
	private ArrayList<Coordinate> coordGuesses = new ArrayList<Coordinate>(); 
	private int numOfShips = 5;

    public Player() {
		Scanner playerInput = new Scanner(System.in);
		int length = 6;
		int shipsCreated = 0; 
        do {
			System.out.println("Choose coordinate to place ship: ");
			Coordinate shipHead = new Coordinate(playerInput.nextInt(), playerInput.nextInt());
			System.out.println("Choose orientation: ");
			String vertOrHoriz = playerInput.next();
			Battleship ship = new Battleship(shipHead, vertOrHoriz, length);
			if(playerShips.isEmpty()){
				playerShips.add(ship);
				shipsCreated++;
				length--;
			} else {
				for(int i = 0; i < playerShips.size(); i++) {
					if(playerShips.get(i).validShip(ship)) {		
						playerShips.add(ship);
						shipsCreated++;
						length--;
						break;
					} else {
						System.out.println("!!!NOT A VALID SHIP!!!");
						break;
					}
				}
			}
		} while(shipsCreated < numOfShips);
	}
	
	public boolean validMissle(Coordinate missle) {
		for(int i = 0; i < coordGuesses.size(); i++) {
			if(coordGuesses.get(i).equals(missle)) 
				return false;
		}
		coordGuesses.add(missle);
		return true;
	}

	public boolean hitOrMiss(Coordinate missle) {
		int counter = 0;
		for(int i = 0; i < playerShips.size(); i++) {
			if(playerShips.get(i).hitOrMiss(missle)){
				counter++;
			}
			if(counter > 0)
				return true;
		}
	   return false;
	}
	
	public ArrayList<Battleship> getBattleships() {
		return playerShips;
	}
	
	public void removeBattleship() {
		for(int i = 0; i < playerShips.size(); i++) {
			if(!playerShips.get(i).getStatus()) {
				playerShips.remove(i);
				numOfShips--;
			}
		}	
	}

	public int getNumOfShips(){
		return numOfShips;
	}

	public ArrayList<Coordinate> getCoordGuesses() {
		return coordGuesses;
	}
	public void printShips() {
		String ships = "";
		for(int i = 0; i < numOfShips; i++) {
			if(playerShips.get(i).getSize() != 0)
				ships += playerShips.get(i) + "\n";	
			else {
				this.removeBattleship();
			}	
		}
		System.out.println(ships);
	}

}