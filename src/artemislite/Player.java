/**
 * 
 */
package artemislite;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Daniel McAuley, Helder Santos, Gareth Alexander, Joseph Collins
 *         Represents a player within the game.
 */



public class Player {
	//constants
	private static final int STARTINGWORKFORCE = 300;
	private static final int STARTINGEQUIPMENT = 400;
	private static final int BOARDSIZE=12;
	private static final int COLLECTGOWORKFORCE=300;
	private static final int COLLECTGOEQUIPMENT=280;
	public static final int MINNUMBERPLAYERS =2;
	public static final int MAXNUMBERPLAYERS = 4;
	private static final int GAMEOVERRESOURCEVALUE = -1;
	/**
	 * Name of player
	 */
	private String playerName;
	/**
	 * Number of player
	 */
	private int playerNumber;
	/**
	 * Board position of player
	 */
	private int boardPosition;
	/**
	 * Player resources
	 */
	private int workforce;
	/**
	 * Player equipment
	 */
	private int equipment;
	
	/**
	 * Creates a player object with no arguments
	 */
	public Player() {
	}

	/**
	 * Creates a Player with all arguments
	 * 
	 * @param playerName
	 * @param playerNumber
	 * @param boardPosition
	 * @param workForce
	 */
	public Player(String playerName, int playerNumber, int boardPosition, int workForce, int equipment)
			throws IllegalArgumentException {
		this.setPlayerName(playerName);
		this.playerNumber = playerNumber;
		this.boardPosition = boardPosition;
		this.workforce = workForce;
		this.equipment = equipment;
	}

	/**
	 * Gets number of players and returns the number of players as an in
	 * @param scanner
	 * @return gets number of players and returns an int
	 */
	public static int numOfPlayers(Scanner scanner) {
		
		
		int numOfPlayers;
		System.out.println("How many players? (Must be between "+MINNUMBERPLAYERS+" and "+MAXNUMBERPLAYERS+")");
		
		//handle for string inputs into nextInt() scanner
		while(!scanner.hasNextInt()) {
			String token = scanner.next();
			System.out.println(token+ " is not a valid number");
			System.out.println("please try again");
		}
		numOfPlayers = scanner.nextInt();
		
		
		while (numOfPlayers < MINNUMBERPLAYERS || numOfPlayers > MAXNUMBERPLAYERS) {
			System.out.println("Incorrect number of players, Please try again.");
			numOfPlayers = scanner.nextInt();
		}
		return numOfPlayers;	
	}

	/**
	 * Returns an arraylist of player names of the length matching the argument numberOfPlayers
	 * Gets the player's names and returns a list 
	 * @return an arraylist of playernames
	 */
	public static ArrayList<String> playerNames(int numberOfPlayers, Scanner scanner, int loopStart) {
		ArrayList<String> playerNames = new ArrayList<String>();
		
		for (int loop = loopStart; loop <= numberOfPlayers; loop++) {
			String playerName;
			
			while(loop<=numberOfPlayers) {
				System.out.println("Enter player " + loop + " name");
				playerName = scanner.next();
				
				//handle for duplicate names
				if (!playerNames.contains(playerName)) {
					playerNames.add(playerName);
					loop++;
				} else {
					System.out.println("Player names cant be the same");
					System.out.println("Enter player " + loop + " name");
					playerName = scanner.next();
					playerNames.add(playerName);
					loop++;
				}
			}
			

		}
		return playerNames;
	}

	/**
	 * Returns an array list contain the players 
	 * @param playerNames
	 * @return arrayList of objects of type Player
	 */ 
	public static ArrayList<Player> createPlayer(ArrayList<String> playerNames) {
		ArrayList<Player> players = new ArrayList<>();
		for (int loop = 1; loop <= playerNames.size(); loop++) {
			Player player = new Player(playerNames.get(loop - 1), loop, 0, STARTINGWORKFORCE, STARTINGEQUIPMENT);
			players.add(player);
		}
		return players;
	}

	/**
	 * Updates the tile position of a player on the game board
	 * 
	 * @param player              - player which position is to be updated
	 * @param numberOfTilesToMove - number of moves the player position should be
	 *                            updated (resets to 0 after 12)
	 */
	public void updatePlayerBoardPosition(Player player, int numberOfTilesToMove) {
		int currentPosition = this.boardPosition;
		int newPosition = currentPosition + numberOfTilesToMove;

		if (newPosition >= BOARDSIZE) {
			// method to update player resources (pass go)
			player.collectGoResources();
			newPosition -= BOARDSIZE;
			this.boardPosition = newPosition;

		} else {
			this.boardPosition = newPosition;
		}
	}

	/**
	 * Collects resources every time a player passes go
	 */
	public void collectGoResources() {
		this.workforce += COLLECTGOWORKFORCE;
		this.equipment += COLLECTGOEQUIPMENT;
		System.out.println("Resources collected");
		System.out.println("New workforce : " + this.workforce + " New equipment : " + this.equipment);
	}

	/**
	 * Deducts resources from a player
	 * 
	 * @param player             - player to deduct resources from
	 * @param workforceToDeduct, int equipmentToDeduct - amount of resources to take
	 *                           away
	 * @return - true if the transaction is successful
	 */
	public boolean deductPlayerResources(Player player, int workforceToDeduct, int equipmentToDeduct) {
		if (player.workforce >= workforceToDeduct && player.equipment >= equipmentToDeduct) {
			this.workforce -= workforceToDeduct;
			this.equipment -= equipmentToDeduct;
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Allows a player to receive rent which increases their Workforce & Equipment
	 * resources
	 * 
	 * @param rentCostWorkforce - amount of Workforce resources to receive
	 * @param rentCostEquipment - amount of Equipment resources to receive
	 * @return returns true once resources updated
	 */
	public boolean recieveRent(int rentCostWorkforce, int rentCostEquipment) {
		this.workforce += rentCostWorkforce;
		this.equipment += rentCostEquipment;
		return true;
	}

	/**
	 * Deducts rent costs from a player which decreases their Workforce & Equipment
	 * resources
	 * 
	 * @param player            - player to deduct rent from
	 * @param rentCostWorkforce - amount of Workforce resources to deduct
	 * @param rentCostEquipment - amount of Workforce resources to deduct
	 * @return - returns true once resources have been deducted
	 */
	public boolean deductRent(Player player, int rentCostWorkforce, int rentCostEquipment) {
		player.workforce -= rentCostWorkforce;
		player.equipment -= rentCostEquipment;
		return true;

	}

	/**
	 * Checks if the a player has run of of resources and the game is over
	 * 
	 * @return - true if the players resources (workforce & equipment) have went
	 *         below zero
	 */
	public boolean gameOverCondition() {
		if (this.workforce >= GAMEOVERRESOURCEVALUE && this.equipment >= GAMEOVERRESOURCEVALUE) {
			return false;
		}
		return true;

	}

	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @param playerName the playerName to set
	 */
	public void setPlayerName(String playerName) throws IllegalArgumentException {
		if (playerName.trim().length() == 0) {
			throw new IllegalArgumentException("Player name cannot be blank");
		} else {
			this.playerName = playerName;
		}
	}

	/**
	 * @return the playerNumber
	 */
	public int getPlayerNumber() {
		return playerNumber;
	}

	/**
	 * @param playerNumber the playerNumber to set
	 */
	public void setPlayerNumber(int playerNumber) throws IllegalArgumentException {
		if (playerNumber >= MINNUMBERPLAYERS && playerNumber <= MAXNUMBERPLAYERS) {
			this.playerNumber = playerNumber;
		} else {
			throw new IllegalArgumentException("Player number must be between"+MINNUMBERPLAYERS+"-"+MAXNUMBERPLAYERS);
		}

	}

	/**
	 * @return the boardPosition
	 */
	public int getBoardPosition() {
		return boardPosition;
	}

	/**
	 * @param boardPosition the boardPosition to set
	 */
	public void setBoardPosition(int boardPosition) {
		this.boardPosition = boardPosition;
	}

	/**
	 * @return the playerResources
	 */
	public int getPlayerWorkforce() {
		return workforce;
	}

	/**
	 * @param workforce the playerResources to set
	 */
	public void setPlayerWorkforce(int workforce) {
		this.workforce = workforce;
	}

	/**
	 * @return the playerEquipment
	 */
	public int getEquipment() {
		return equipment;
	}

	/**
	 * @param equipment the playerEquipment to set
	 */
	public void setEquipment(int equipment) {
		this.equipment = equipment;
	}

}
