/**
 * 
 */
package artemislite;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Daniel McAuley, Helder Santos, Gareth Alexander, Joseph Collins
 *
 */
public class Tile {
	
	//constants
	private static final int MINDEVELOPMENTLEVEL=0;
	public static final int MAXDEVELOPMENTLEVEL=4;

	/**
	 * Name of tile
	 */
	private String tileName;
	/**
	 * Number of tile
	 */
	private int tileNumber;
	/**
	 * Workfoce cost to buy a tile
	 */
	private int workforceCostToBuy;
	/**
	 * Workforce cost to develop a tile
	 */
	private int workforceCostToDevelop;
	/**
	 * Workforce cost to rent a tile
	 */
	private int workforceRentCost;
	/**
	 * Equipment cost to buy a tile
	 */
	private int equipmentCostToBuy;
	/**
	 * Equipment cost to develop a tile
	 */
	private int equipmentCostToDevelop;
	/**
	 * Equipment cost to rent a tile
	 */
	private int equipmentRentCost;
	/**
	 * Who the player is owned by
	 */
	private Player ownedBy;
	/**
	 * Is the tile owned
	 */
	private boolean isOwned;
	/**
	 * Which system the tile belongs to
	 */
	private Syztem syztem;
	/**
	 * Can the tile be developed
	 */
	private boolean canBeDeveloped;
	/**
	 * The development level of the tile
	 */
	private int developmentLevel;
	/**
	 * Is the tile available to buy
	 */
	private boolean availableToBuy;
	/**
	 * Has the tile been bought
	 */
	private boolean isBought;
	
	
	
	
	/**
	 * default constructor
	 */
	public Tile() {
	}

	/**
	 * Creates a tile object that can be bought, developed and accept rent
	 * ownedBy, isOwned, canBedDeveloped,DevelopmentLevel, availableToBuy, isBought are set to default starting values
	 * 
	 * @param tileName
	 * @param workforceCostToBuy
	 * @param equipmentCostToBuy
	 * @param workforceCostToDevelop
	 * @param equipmentCostToDevelop
	 * @param workforceRentCost
	 * @param equipmentRentCost
	 * @param tileNumber
	 * @param system
	 */
	public Tile(String tileName, int workforceCostToBuy, int equipmentCostToBuy, int workforceCostToDevelop,
			int equipmentCostToDevelop, int workforceRentCost, int equipmentRentCost, int tileNumber, Syztem system) {
		this.tileName = tileName;
		this.workforceCostToBuy = workforceCostToBuy;
		this.ownedBy = null;
		this.isOwned = false;
		this.tileNumber = tileNumber;
		this.syztem = system;
		this.canBeDeveloped = false;
		this.developmentLevel = MINDEVELOPMENTLEVEL;
		this.workforceCostToDevelop = workforceCostToDevelop;
		this.availableToBuy = true;
		this.workforceRentCost = workforceRentCost;
		this.equipmentCostToBuy = equipmentCostToBuy;
		this.equipmentCostToDevelop = equipmentCostToDevelop;
		this.equipmentRentCost = equipmentRentCost;
		this.isBought = false;
		
	}

	/**
	 * Creates a tile object than cannot be bought (Pass go and free square)
	 * ownedBy, isOwned, availableToBuy, developmentLevel are set to default values
	 * @param tileName
	 * @param tileNumber
	 */
	public Tile(String tileName, int tileNumber) {
		this.tileName = tileName;
		this.ownedBy = null;
		this.isOwned = false;
		this.tileNumber = tileNumber;
		this.availableToBuy = false;
		this.developmentLevel = MAXDEVELOPMENTLEVEL;
	}

	/**
	 * Allows a player to buy a tile
	 * 
	 * @param player - player who is buying the tile
	 * @return true if buy title successful, else returns false
	 */
	public boolean buyTile(Player player) {
		if (player.deductPlayerResources(player, workforceCostToBuy, equipmentCostToBuy)) {
			System.out.println("New resources for " + player.getPlayerName() + " = " + player.getPlayerWorkforce()
					+ " & " + player.getEquipment());
			this.ownedBy = player;
			this.isOwned = true;
			this.isBought = true;
			return true;
		} else {
			System.out.println("Not enough resources to buy");
			return false;
		}
	}

	/**
	 * Determines if a player can buy a tile
	 * 
	 * @param player  - player which check is being done against
	 * @param players
	 * @param scanner
	 * @return true if a player is able to buy a tile, else returns false
	 */
	public boolean canBuy(Player player, ArrayList<Player> players, Scanner scanner) {
		// the tile is not owned and the player has enough resources to buy
		
		
		if (!isOwned && (player.getPlayerWorkforce() >= this.workforceCostToBuy)
				&& player.getEquipment() >= this.equipmentCostToBuy) {
			System.out.println("Owned by nobody. Buy for " + workforceCostToBuy + " workforce and " + equipmentCostToBuy
					+ " equipment");
			return true;
			// the player does not have enough resources to buy
		} else if (player.getPlayerWorkforce() < this.workforceCostToBuy
				|| player.getEquipment() < this.equipmentCostToBuy && !isOwned) {
			System.out.println("Not enough resources to buy");
			return false;
			// the tile is owned
		} else {	
			return false;
		} 
		
		
		
	}

	/**
	 * Checks if a player can develop a tile
	 * 
	 * @param player
	 * @return - returns true if the tile is available to develop
	 */
	public boolean canDevelop(Player player) {
		// if the player owns the tile and it is not owned by anyone
		if (ownedBy != null) {
			if (ownedBy.equals(player)) {
				System.out.println("Develop for ");
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if a player has to pay rent on a tile that they land on. Asks the tile
	 * owner if they would like to accept
	 * 
	 * @param player  - the player who has to pay the rent
	 * @param tile    - the tile the player lands on (used to get the owner of the
	 *                tile)
	 * @param scanner - to accept input from the console
	 * @return true if tile owner decides to collect rent, else return false
	 */
	public boolean haveToPayRent(Player player, Tile tile, Scanner scanner) {
		// if it the tile is owned and it is not owned by the current player
		
		if (isOwned && !tile.getOwnedBy().equalsIgnoreCase(player.getPlayerName())) {
			
			
			String playerOption="check";
			while(!playerOption.equalsIgnoreCase("yes") && !playerOption.equalsIgnoreCase("no")){
				System.out.println( ownedBy.getPlayerName() +" do you want to collect rent? Yes / No");
				playerOption = scanner.nextLine();
			}
			
			

			// ask owner if they would like to collect rent
			if (playerOption.equalsIgnoreCase("Yes")) {
				return true;
			} else {
				System.out.println("No rent charged");
			}
		}
		
		return false;
	}

	/**
	 * Carries out the rent process by deducting resources from one player and
	 * adding them the tile owner
	 * 
	 * @param player - the current player who's resources are being deducted
	 * @param tile   - the current tile (used to get the tile owner)
	 * @return true at end of method
	 */
	public boolean payRent(Player player, Tile tile) {
		System.out.println(player.getPlayerName() + " pay rent of " + tile.getWorkforceRentCost() + " workforce and "
				+ tile.getEquipmentRentCost() + " equipment");
		
		player.deductRent(player, tile.getWorkforceRentCost(), tile.getEquipmentRentCost());
		ownedBy.recieveRent(tile.workforceRentCost, tile.equipmentRentCost);
		
		System.out.println("Rent of " + tile.getWorkforceRentCost() + " workforce and " + tile.getEquipmentRentCost() + " equipment paid");
		return true;
		
		
	}

	/**
	 * Develops a tile that a player has landed on by deducted the appropriate
	 * resources and increasing the development level of the tile
	 * 
	 * @param player - the current player who is intending on developing the tile
	 * @return true if tile is successfully developed, else return false
	 */
	public boolean developTile(Player player) {
		// check development level
		System.out.println("Current tile development level " + this.developmentLevel);
		if (player.deductPlayerResources(player, this.workforceCostToDevelop, this.equipmentCostToDevelop)) {
			System.out.println("Resources decucted");
			increaseDevelopmentLevel();
			setCostToDevelop(this.workforceCostToDevelop,this.equipmentCostToDevelop, this.developmentLevel);
			
			System.out.println("New tile development level " + this.developmentLevel);
			return true;
		} else {
			System.out.println("Not enough resources");
			return false;
		}
		
	}

	/**
	 * Updates the status of whether a tile can be developed or not
	 */
	public void updateTileDevelopStatus() {
		this.canBeDeveloped = true;
	}

	/**
	 * Displays the tile development info when a player is offered to develop
	 * 
	 * @return development info string
	 */
	public String developmentInfo() {
		String returnString = String.format("%-30s %-20d %-20d %-28d %-28d\n", getTileName(), getTileNumber(),
				getDevelopmentLevel(), getWorkforceCostToDevelop(), getEquipmentCostToDevelop());
		return returnString;
	}
	
	/**
	 * Increases the development level of a tile, which as a result increases the
	 * rent costs
	 */
	public void increaseDevelopmentLevel() {

		if (this.developmentLevel < MAXDEVELOPMENTLEVEL) {
			this.developmentLevel++;
			this.workforceRentCost += (this.developmentLevel * 30);
			this.equipmentRentCost += (this.developmentLevel * 30);
		} else {
			System.out.println("No further development possible");
		}

	}
	
	/**
	 * Offer's the purchase of a tile to the next player in the list
	 * @param player
	 * @param players
	 * @param scanner
	 * @return true if next player decides to buy tile offered, else return false
	 */
	public boolean offerToOtherPlayer(Player player, ArrayList<Player> players, Scanner scanner) {
		int currentPlayer = 0;
		int nextPlayer = 0;

		// move along through players to get current player index
		for (int loop = 0; loop < players.size(); loop++) {

			if (player == players.get(loop)) {
				currentPlayer = loop;
			}
		}

		// figure out next player
		if (currentPlayer == (players.size() - 1)) {
			nextPlayer = 0;
		} else {
			nextPlayer = ++currentPlayer;
		}
		// offer to buy
		System.out.println(players.get(nextPlayer).getPlayerName());
		System.out.println("Buy tile? - Yes / No");
		String buyChoice = scanner.nextLine();
		
		while(!buyChoice.equalsIgnoreCase("yes") && !buyChoice.equalsIgnoreCase("no")) {
			System.out.println("Input not recognized. Try again ... Yes / No");
			buyChoice = scanner.nextLine();
		}
		// if yes, call buyTile
		if (buyChoice.equalsIgnoreCase("yes")) {
			buyTile(players.get(nextPlayer));
			return true;
		} else {
			currentPlayer = nextPlayer;
		}

		System.out.println("offer over");
		return false;

	}

	/**
	 * @return the tileName
	 */
	public String getTileName() {
		return tileName;
	}

	/**
	 * @param tileName the tileName to set
	 */
	public void setTileName(String tileName) {
		this.tileName = tileName;
	}

	/**
	 * @return the tileNumber
	 */
	public int getTileNumber() {
		return tileNumber;
	}

	/**
	 * @param tileNumber the tileNumber to set
	 */
	public void setTileNumber(int tileNumber) {
		this.tileNumber = tileNumber;
	}

	/**
	 * @return the costToBuy
	 */
	public int getWorkforceCostToBuy() {
		return workforceCostToBuy;
	}

	/**
	 * @param costToBuy the costToBuy to set
	 */
	public void setCostToBuy(int costToBuy) {
		this.workforceCostToBuy = costToBuy;
	}

	/**
	 * @return the ownedBy
	 */
	public String getOwnedBy() {
		if (this.ownedBy == null) {
			return "Nobody";
		} else {
			return this.ownedBy.getPlayerName();
		}
	}

	/**
	 * @param ownedBy the ownedBy to set
	 */
	public void setOwnedBy(Player ownedBy) {
		this.ownedBy = ownedBy;
	}

	/**
	 * @return the isOwned
	 */
	public boolean isOwned() {
		return isOwned;
	}

	/**
	 * @param isOwned the isOwned to set
	 */
	public void setOwned(boolean isOwned) {
		this.isOwned = isOwned;
	}

	/**
	 * @return the syztem
	 */
	public Syztem getSyztem() {
		return syztem;
	}

	/**
	 * @param syztem the syztem to set
	 */
	public void setSyztem(Syztem syztem) {
		this.syztem = syztem;
	}

	/**
	 * @return the canBeDeveloped
	 */
	public boolean isCanBeDeveloped() {
		if (this.developmentLevel <= 4) {
			return canBeDeveloped;
		}
		return false;
	}

	/**
	 * @return the developmentLevel
	 */
	public int getDevelopmentLevel() {
		return developmentLevel;
	}

	/**
	 * @param canBeDeveloped the canBeDeveloped to set
	 */
	public void setCanBeDeveloped(boolean canBeDeveloped) {
		this.canBeDeveloped = canBeDeveloped;
	}

	/**
	 * @return the costToDevelop
	 */
	public int getWorkforceCostToDevelop() {
		return workforceCostToDevelop;
	}

	/**
	 * @param costToDevelop the costToDevelop to set
	 */
	public void setCostToDevelop(int workforceCostToDevelop,int equipmentCostToDevelop, int developmentLevel) {
		if (developmentLevel < 3) {
			this.workforceCostToDevelop = workforceCostToDevelop + (developmentLevel * 100);
			this.equipmentCostToDevelop = equipmentCostToDevelop + (developmentLevel * 100);
			
		} else {
			this.workforceCostToDevelop = workforceCostToDevelop + (developmentLevel * 200);
			this.equipmentCostToDevelop = equipmentCostToDevelop + (developmentLevel * 200);
		}

	}

	/**
	 * @return the availableToBuy
	 */
	public boolean isAvailableToBuy() {
		return availableToBuy;
	}

	/**
	 * @param availableToBuy the availableToBuy to set
	 */
	public void setAvailableToBuy(boolean availableToBuy) {
		this.availableToBuy = availableToBuy;
	}

	/**
	 * @return the rentCost
	 */
	public int getWorkforceRentCost() {
		return workforceRentCost;
	}

	/**
	 * @param rentCost the rentCost to set
	 */
	public void setWorkforceRentCost(int rentCost) {
		this.workforceRentCost = rentCost;
	}

	/**
	 * @param costToDevelop the costToDevelop to set
	 */
	public void setWorkforceCostToDevelop(int costToDevelop) {
		this.workforceCostToDevelop = costToDevelop;
	}

	/**
	 * @param developmentLevel the developmentLevel to set
	 */
	public void setDevelopmentLevel(int developmentLevel) {
		this.developmentLevel = developmentLevel;
	}

	/**
	 * @return the equipmentCostToBuy
	 */
	public int getEquipmentCostToBuy() {
		return equipmentCostToBuy;
	}

	/**
	 * @param equipmentCostToBuy the equipmentCostToBuy to set
	 */
	public void setEquipmentCostToBuy(int equipmentCostToBuy) {
		this.equipmentCostToBuy = equipmentCostToBuy;
	}

	/**
	 * @return the equipmentCostToDevelop
	 */
	public int getEquipmentCostToDevelop() {
		return equipmentCostToDevelop;
	}

	/**
	 * @param equipmentCostToDevelop the equipmentCostToDevelop to set
	 */
	public void setEquipmentCostToDevelop(int equipmentCostToDevelop) {
		this.equipmentCostToDevelop = equipmentCostToDevelop;
	}

	/**
	 * @return the equipmentRentCost
	 */
	public int getEquipmentRentCost() {
		return equipmentRentCost;
	}

	/**
	 * @param equipmentRentCost the equipmentRentCost to set
	 */
	public void setEquipmentRentCost(int equipmentRentCost) {
		this.equipmentRentCost = equipmentRentCost;
	}

	/**
	 * @return the isBought
	 */
	public boolean isBought() {
		return isBought;
	}

	/**
	 * @param isBought the isBought to set
	 */
	public void setBought(boolean isBought) {
		this.isBought = isBought;
	}

	/**
	 * @param workforceCostToBuy the workforceCostToBuy to set
	 */
	public void setWorkforceCostToBuy(int workforceCostToBuy) {
		this.workforceCostToBuy = workforceCostToBuy;
	}

	

}
