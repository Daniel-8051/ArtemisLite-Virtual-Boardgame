/**
 * 
 */
package artemislite;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Daniel McAuley, Helder Santos, Gareth Alexander, Joseph Collins. 
 * The main class of Artemis Lite which contains the functionality for the
 *  game to run.
 *
 */
public class Game {
	//constants
	private static final int FREETILE1NUMBER = 0;
	private static final int FREETILE2NUMBER = 6;
	private static final int MINNUMBEROFTILES = 2;
	private static final int MAXNUMBEROFTILES =12;

	/**
	 * List of players
	 */
	private ArrayList<Player> players;
	/**
	 * List of game Tiles (game board)
	 */
	private ArrayList<Tile> gameBoard;
	/**
	 * Max number of players in a game
	 */
	private int maxNumberOfPlayers;
	/**
	 * Max number of tiles in a game
	 */
	private int maxNumberOfTiles;

	/**
	 * Creates a game with no arguments
	 */
	public Game() {
	}

	/**
	 * Creates a new Game with all arguments
	 * 
	 * @param maxNumberOfPlayers - {@value #maxNumberOfPlayers}
	 * @param maxNumberOfTiles   - {@value #maxNumberOfTiles}
	 */
	public Game(int maxNumberOfPlayers, int maxNumberOfTiles) throws IllegalArgumentException{
		this.players = new ArrayList<>();
		this.gameBoard = new ArrayList<>();
		this.setMaxNumberOfPlayers(maxNumberOfPlayers);
		this.setMaxNumberOfTiles(maxNumberOfTiles);
	}

	/**
	 * Adds a tile to the game board
	 * 
	 * @param tile - tile to add
	 * @throws IllegalArgumentException - if max number of tiles has been reached
	 */
	public void addTile(Tile tile) throws IllegalArgumentException {
		if (gameBoard.size() <= maxNumberOfTiles) {
			gameBoard.add(tile);
		} else {
			throw new IllegalArgumentException("Max number of tiles reached. No more can be added");
		}
	}

	/**
	 * Adds a player to the list of players in the game
	 * 
	 * @param player - player to add
	 * @throws IllegalArgumentException - if max number of players has been reached
	 */
	public void addPlayer(Player player) throws IllegalArgumentException {
		if (players.size() <= maxNumberOfPlayers) {
			players.add(player);
		} else {
			throw new IllegalArgumentException("Max number of players rached. No more can be added");
		}
	}

	/**
	 * Displays the information of all the tiles within the game
	 */
	public void displayAllTiles() {
		System.out.println("\nTILE INFORMATION");
		System.out.println("--------------------");
		System.out.printf("%-30s %-20s %-25s %-25s %-20s %-20s %-20s\n", "Tile Name", "Tile Number",
				"Workforce cost to Buy", "Equipment cost to Buy", "Development Level", "Owned By","System");
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------");
		for (Tile tile : gameBoard) {
			if (tile.getTileNumber() == FREETILE1NUMBER || tile.getTileNumber() == FREETILE2NUMBER) {
				System.out.printf("%-30s %-20d %-25s %-25s %-20s %-20s %-20s\n", tile.getTileName(), tile.getTileNumber(),
						"N/A", "N/A", "N/A", "N/A", "N/A");
				System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------");
			} else {
				System.out.printf("%-30s %-20d %-25d %-25d %-20d %-20s %-20s\n", tile.getTileName(), tile.getTileNumber(),
						tile.getWorkforceCostToBuy(), tile.getEquipmentCostToBuy(), tile.getDevelopmentLevel(),
						tile.getOwnedBy(),tile.getSyztem());
				System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------");
			}
		}
		
	}

	/**
	 * Displays the information of all the players within the game
	 */
	public void displayAllPlayers() {
		System.out.println("\nPLAYER INFORMATION");
		System.out.printf("%-20s %-20s %-20s %-20s %-20s\n", "Number", "Name", "Board Position", "Workforce",
				"Equipment");
		for (Player player : players) {
			System.out.printf("%-20d %-20s %-20d %-20d %-20d\n", player.getPlayerNumber(), player.getPlayerName(),
					player.getBoardPosition(), player.getPlayerWorkforce(), player.getEquipment());
		}
		System.out.println();
	}

	/**
	 * Allows a player to take their turn
	 * 
	 * @param player - the player who's turn it is
	 * @return returns true if player wants to keep playing / return false otherwise
	 */
	public boolean takeTurn(Player player) {
		// asks player if they want to keep playing
		Scanner scanner = new Scanner(System.in);
		String takeTurnChoice;
		System.out.println(player.getPlayerName() + " keep playing? - Yes / No");
		takeTurnChoice = scanner.nextLine();	
		while(!takeTurnChoice.equalsIgnoreCase("yes") && !takeTurnChoice.equalsIgnoreCase("no")){
			System.out.println("That was not a valid input try again. Keep playing? Yes / No");
			takeTurnChoice = scanner.nextLine();
		}

		// if they decide - yes
		if (takeTurnChoice.equalsIgnoreCase("Yes")) {
			
			// display current board position
			System.out.println("\n" + player.getPlayerName().toUpperCase());
			System.out.printf("%-20s %-10d\n", "Current position", player.getBoardPosition());

			// roll dice
			Dice dice = new Dice();
			int diceRoll = dice.rollDice();

			// update player position
			player.updatePlayerBoardPosition(player, diceRoll);

			// show tile details
			Tile tile = getCurrentPlayerTile(player);
			

			// display players board position
			System.out.printf("%-20s %-10d\n", "New position", player.getBoardPosition());

			// if not owned - display cost to buy
			if (!tile.isOwned()) {
				System.out.println("\nTILE INFO...");
				System.out.printf("%-30s %-25s %-25s %-25s\n", "Tile Name", "Workforce cost to buy",
						"Equipment cost to buy", "Owned By");
				System.out.printf("%-30s %-25d %-25d %-25s\n\n", tile.getTileName(), tile.getWorkforceCostToBuy(),
						tile.getEquipmentCostToBuy(), tile.getOwnedBy());

				// if owned - display rent cost
			} else {
				System.out.println("\nTILE INFO...");
				System.out.printf("%-30s %-25s %-25s %-25s\n", "Tile Name", "Workforce cost to rent",
						"Equipment cost to rent", "Owned By");
				System.out.printf("%-30s %-25d %-25d %-25s\n\n", tile.getTileName(), tile.getWorkforceRentCost(),
						tile.getEquipmentRentCost(), tile.getOwnedBy());

			}
			;
			
			System.out.println("Tile options are..");

			// check BUY condition
			if (tile.isAvailableToBuy()) {
				if (tile.canBuy(player, players, scanner)) {
					offerToBuy(player, tile, scanner, players);
				}
			}

			// check DEVELOP status
			// a list of tiles that the player can develop within a system
			List<Tile> checkedSystemTiles = new ArrayList<>();
			checkedSystemTiles = checkSystem(player);
			
			if(!checkedSystemTiles.isEmpty()) {
				System.out.printf("%-30s %-20s %-20s %-28s %-28s\n", "TileName", "TileNumber",
						"Development Level", "Workforce cost to Develop", "Equipment cost to Develop");
			}
			
			// output the details of the tiles to develop
			for (Tile t : checkedSystemTiles) {
				
				System.out.println( t.developmentInfo());
			}

			// if the list of tiles to develop is NOT empty
			// ask to develop
			if (!checkedSystemTiles.isEmpty()) {
				System.out.println("Do you want to develop?");
				String playerChoice;
				playerChoice = scanner.nextLine();
				while(!playerChoice.equalsIgnoreCase("yes") && !playerChoice.equalsIgnoreCase("no")){
					System.out.println("That was not a valid input try again. Keep playing? Yes / No");
					playerChoice = scanner.nextLine();
				}

				if (playerChoice.equalsIgnoreCase("yes")) {
					// ask which tile to develop
					System.out.println("Which tile do you want to develop? Enter number...");
					int tileToDevelop;
					while(!scanner.hasNextInt()) {
						String token = scanner.next();
						System.out.println(token+ " is not a valid number");
						System.out.println("please try again");
					}
					tileToDevelop = scanner.nextInt();
					scanner.nextLine();

					for (int loop = 0; loop < checkedSystemTiles.size(); loop++) {
						if (tileToDevelop == checkedSystemTiles.get(loop).getTileNumber()) {
							// develop method
							gameBoard.get(tileToDevelop).developTile(player);
						}
					}

				} else {
					// end turn
				}

			}

			// check is bought to see if tile was bought that turn
			//if false ask owner if they want to collect rent 
			if (!tile.isBought()) {
				if (tile.haveToPayRent(player, tile, scanner)) {
					// pay rent
					tile.payRent(player, tile);
					// end turn
				}
			}else {
				//reset the isBought to false
				tile.setBought(false);
			}

			System.out.println("Turn over");
			System.out.println(
					"_____________________________________________________________________________________________________________________________");
			return true;

		} else {
			// if player decides to not continue playing
			System.out.println("Player " + player.getPlayerName() + " has quit the game");
			displayAllTiles();
			scanner.close();
			return false;
		}

	}

	/**
	 * Offers a player to buy a tile. If they choose not to, the offer is passed on
	 * to the next person in the list of players
	 * 
	 * @param player  - player that is offered to buy
	 * @param tile    - the tile to buy
	 * @param scanner - scanner object to accept input from keyboard
	 * @param players - list of players
	 * @return true if player decides to buy, as well as if tile is offered to next player
	 */
	public boolean offerToBuy(Player player, Tile tile, Scanner scanner, ArrayList<Player> players) {
		String buyChoice;
		System.out.println("Buy tile? - Yes / No");
		buyChoice = scanner.nextLine();
		while(!buyChoice.equalsIgnoreCase("yes") && !buyChoice.equalsIgnoreCase("no")) {
			System.out.println("Input not recognized. Try again ... Yes / No");
			buyChoice = scanner.nextLine();
		}

		// check options
		if (buyChoice.equalsIgnoreCase("Yes")) {
			// buy tile
			tile.buyTile(player);
			return true;
		} else {
			// offer to other players
			tile.offerToOtherPlayer(player, players, scanner);
			return true;
		} 
	}

	/**
	 * Gets the tile of the current player
	 * 
	 * @param player -
	 * @return - returns the tile the current player is on
	 */
	public Tile getCurrentPlayerTile(Player player) {
		return gameBoard.get(player.getBoardPosition());
	}

	/**
	 * Checks if a player owns all tiles within Systems 1-4 and returns the tiles
	 * available to develop
	 * 
	 * @param player - player to check
	 * @return - a list of tile objects available to develop
	 */
	public List<Tile> checkSystem(Player player) {

		List<Tile> searchList = new ArrayList<>();

		// check system 1
		// check if tile 1 & 2 is owned
		if (gameBoard.get(1).isOwned() && gameBoard.get(2).isOwned()) {
			// who is it owned by
			if (gameBoard.get(1).getOwnedBy().equals(player.getPlayerName())
					&& gameBoard.get(2).getOwnedBy().equals(player.getPlayerName())) {

				// check tile status - can be developed?
				if (!gameBoard.get(1).isCanBeDeveloped() && gameBoard.get(2).isCanBeDeveloped()) {
					gameBoard.get(1).updateTileDevelopStatus();
					gameBoard.get(2).updateTileDevelopStatus();
				}
				// check if development level is less than 4
				if (gameBoard.get(1).getDevelopmentLevel() < 4) {
					searchList.add(gameBoard.get(1));
				}
				if (gameBoard.get(2).getDevelopmentLevel() < 4) {
					searchList.add(gameBoard.get(2));
				}

			}
		}

		// check system 2
		// check if tile 3, 4 & 5 is owned
		if (gameBoard.get(3).isOwned() && gameBoard.get(4).isOwned() && gameBoard.get(5).isOwned()) {
			// who is it owned by
			if (gameBoard.get(3).getOwnedBy().equals(player.getPlayerName())
					&& gameBoard.get(4).getOwnedBy().equals(player.getPlayerName())
					&& gameBoard.get(5).getOwnedBy().equals(player.getPlayerName())) {

				// check tile status - can be developed?
				if (!gameBoard.get(3).isCanBeDeveloped() && gameBoard.get(4).isCanBeDeveloped()
						&& gameBoard.get(5).isCanBeDeveloped()) {
					gameBoard.get(3).updateTileDevelopStatus();
					gameBoard.get(4).updateTileDevelopStatus();
					gameBoard.get(5).updateTileDevelopStatus();
				}
				// check if development level is less than 4
				if (gameBoard.get(3).getDevelopmentLevel() < 4) {
					searchList.add(gameBoard.get(3));
				}
				if (gameBoard.get(4).getDevelopmentLevel() < 4) {
					searchList.add(gameBoard.get(4));
				}
				if (gameBoard.get(5).getDevelopmentLevel() < 4) {
					searchList.add(gameBoard.get(5));
				}

			}
		}

		// check system 3
		// check if tile 7, 8 & 9 is owned
		if (gameBoard.get(7).isOwned() && gameBoard.get(8).isOwned() && gameBoard.get(9).isOwned()) {
			// who is it owned by
			if (gameBoard.get(7).getOwnedBy().equals(player.getPlayerName())
					&& gameBoard.get(8).getOwnedBy().equals(player.getPlayerName())
					&& gameBoard.get(9).getOwnedBy().equals(player.getPlayerName())) {

				// check tile status - can be developed?
				if (!gameBoard.get(7).isCanBeDeveloped() && gameBoard.get(8).isCanBeDeveloped()
						&& gameBoard.get(9).isCanBeDeveloped()) {
					gameBoard.get(7).updateTileDevelopStatus();
					gameBoard.get(8).updateTileDevelopStatus();
					gameBoard.get(9).updateTileDevelopStatus();
				}
				// check if development level is less than 4
				if (gameBoard.get(7).getDevelopmentLevel() < 4) {
					searchList.add(gameBoard.get(7));
				}
				if (gameBoard.get(8).getDevelopmentLevel() < 4) {
					searchList.add(gameBoard.get(8));
				}
				if (gameBoard.get(9).getDevelopmentLevel() < 4) {
					searchList.add(gameBoard.get(9));
				}
			}
		}

		// check system 4
		// check if tile 10 & 11 is owned
		if (gameBoard.get(10).isOwned() && gameBoard.get(11).isOwned()) {
			// who is it owned by
			if (gameBoard.get(10).getOwnedBy().equals(player.getPlayerName())
					&& gameBoard.get(11).getOwnedBy().equals(player.getPlayerName())) {

				if (!gameBoard.get(10).isCanBeDeveloped() && gameBoard.get(11).isCanBeDeveloped()) {
					gameBoard.get(10).updateTileDevelopStatus();
					gameBoard.get(11).updateTileDevelopStatus();
				}
				// check if development level is less than 4
				if (gameBoard.get(10).getDevelopmentLevel() < 4) {
					searchList.add(gameBoard.get(10));
				}
				if (gameBoard.get(11).getDevelopmentLevel() < 4) {
					searchList.add(gameBoard.get(11));
				}

			}

		}

		// return list
		return searchList;
	}

	/**
	 * Checks the development level of the tiles within the game board
	 * 
	 * @return - true if all tiles have been fully developed, indicating a win
	 *         condition for the game
	 */
	public boolean checkWinCondition() {
		boolean checkSystemDevelopment = true;

		for (Tile tile : gameBoard) {
			if (tile.getDevelopmentLevel() != Tile.MAXDEVELOPMENTLEVEL) {
				checkSystemDevelopment = false;
			}
		}
		return checkSystemDevelopment;
	}

	/**
	 * Displays the welcome message at the start of the game, explaining the
	 * instructions and objective of the game
	 */
	public void welcomeMessage() {
		System.out.println("");
		System.out.println("\n\n\n"); 
	}
	
	/**
	 * Displays the end of game message if a player has run out of resources
	 */
	public void displayGameOverMessage() {
		displayAllTiles();
		System.out.println("RUN OUT OF RESOURCES");
	}

	/**
	 * @return the players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * @return the gameBoard
	 */
	public ArrayList<Tile> getGameBoard() {
		return gameBoard;
	}

	/**
	 * @return the maxNumberOfPlayers
	 */
	public int getMaxNumberOfPlayers() {
		return maxNumberOfPlayers;
	}

	/**
	 * @param maxNumberOfPlayers the maxNumberOfPlayers to set
	 */
	public void setMaxNumberOfPlayers(int maxNumberOfPlayers) throws IllegalArgumentException{
		if(maxNumberOfPlayers>=Player.MINNUMBERPLAYERS && maxNumberOfPlayers<=Player.MAXNUMBERPLAYERS) {
			this.maxNumberOfPlayers = maxNumberOfPlayers;
		} else {
			throw new IllegalArgumentException("Max number of players must be between "+Player.MINNUMBERPLAYERS+"-"+Player.MAXNUMBERPLAYERS);
		}
	}

	/**
	 * @return the maxNumberOfTiles
	 */
	public int getMaxNumberOfTiles() {
		return maxNumberOfTiles;
	}

	/**
	 * @param maxNumberOfTiles the maxNumberOfTiles to set
	 */
	public void setMaxNumberOfTiles(int maxNumberOfTiles) throws IllegalArgumentException{
		if(maxNumberOfTiles>=MINNUMBEROFTILES && maxNumberOfTiles<=MAXNUMBEROFTILES) {
			this.maxNumberOfTiles = maxNumberOfTiles;
		} else {
			throw new IllegalArgumentException("Max number of tiles must be between "+MINNUMBEROFTILES+"-"+MAXNUMBEROFTILES);
		}
		
	}
	
	
	/**
	 * Displays win game message when a game is won
	 */
	public void displayWinGameMessage() {
		System.out.println("It started as a dream, as a vision");
		System.out.println("And through cooperation and hard work that dream has been achieved ");
		System.out.println("Artemis has launched");
		System.out.println("GAME WON");
	}

}
