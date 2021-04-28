package artemislitetest;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import artemislite.Player;
import artemislite.Syztem;
import artemislite.Tile;

class TileTest {

	// TEST DATA
	Tile tile;
	Tile tile2;
	String tileName;
	int workforceCostToBuy;
	int equipmentCostToBuy;
	int workforceCostToDevelop;
	int equipmentCostToDevelop;
	int workforceRentCost;
	int equipmentRentCost;
	int tileNumber;
	boolean canBeDeveloped;
	boolean isBought;
	boolean availableToBuy;
	Syztem system;

	Player player;
	Player player2;
	String playerNameValid;
	int playerNumber;
	int boardPosition;
	int workforce;
	int noworkforce;
	int noequipment;
	int equipment;
	
	ArrayList<Player> playerList;
	Scanner scanner;

	@BeforeEach
	void setUp() throws Exception {
		playerNameValid = "Player name";
		playerNumber = 1;
		boardPosition = 0;
		workforce = 300;
		equipment = 400;
		noworkforce = 0;
		noequipment = 0;

		tileName = "Tile name";
		workforceCostToBuy = 60;
		equipmentCostToBuy = 45;
		workforceCostToDevelop = 30;
		equipmentCostToDevelop = 25;
		workforceRentCost = 20;
		equipmentRentCost = 15;
		tileNumber = 1;
		canBeDeveloped=false;
		isBought = false;
		availableToBuy = false;
		system = Syztem.EGS_SYSTEM_1;
		
		playerList = new ArrayList<Player>();
		scanner= new Scanner(System.in);

	}
	// test for overloaded constructor
	@Test
	void testTileConstructorAllArguments() {
		tile = new Tile(tileName, workforceCostToBuy, equipmentCostToBuy, workforceCostToDevelop,
				equipmentCostToDevelop, workforceRentCost, equipmentRentCost, tileNumber, system);
		assertEquals(tileName, tile.getTileName());
		assertEquals(workforceCostToBuy, tile.getWorkforceCostToBuy());
		assertEquals("Nobody", tile.getOwnedBy());
		assertEquals(false, tile.isOwned());
		assertEquals(tileNumber, tile.getTileNumber());
		assertEquals(system, tile.getSyztem());
		assertEquals(false, tile.isCanBeDeveloped());
		assertEquals(0, tile.getDevelopmentLevel());
		assertEquals(workforceCostToDevelop, tile.getWorkforceCostToDevelop());
		assertEquals(true, tile.isAvailableToBuy());
		assertEquals(workforceRentCost, tile.getWorkforceRentCost());
		assertEquals(equipmentCostToBuy, tile.getEquipmentCostToBuy());
		assertEquals(equipmentCostToDevelop, tile.getEquipmentCostToDevelop());
		assertEquals(equipmentRentCost, tile.getEquipmentRentCost());
		assertEquals(false, tile.isBought());
		
	}
	//test for second overloaded constructor with less arguments
	@Test
	void testTileConstructorLessArguments(){
		tile = new Tile(tileName, tileNumber);
		assertEquals(tileName, tile.getTileName());
		assertEquals("Nobody", tile.getOwnedBy());
		assertEquals(false, tile.isOwned());
		assertEquals(tileNumber, tile.getTileNumber());
		assertEquals(false, tile.isAvailableToBuy());
		assertEquals(4, tile.getDevelopmentLevel());
		
	}
	//test default constructor
	@Test
	void testDefaultConstructor() {
		tile = new Tile();
		assertNotNull(tile);
	}
	
	//test the buytile method
	@Test
	void testBuyTile() {
		//test true return
		player = new Player(playerNameValid, playerNumber, boardPosition, workforce, equipment);
		tile = new Tile();

		boolean buyTileSuccessful = tile.buyTile(player);

		boolean expectedTrue = true;
		assertEquals(expectedTrue, buyTileSuccessful);
		
		//test false return 
		player2 = new Player(playerNameValid, playerNumber, boardPosition, noworkforce, noequipment);
		tile2 = new Tile();
		tile2.setWorkforceCostToBuy(200);
		tile2.setEquipmentCostToBuy(200);
		boolean buyTileUnSucessful = tile2.buyTile(player2);
		boolean expectedFalse = false;
		assertEquals(expectedFalse, buyTileUnSucessful);
		
		
	}
	
	
	//test the canDevelop method
	@Test
	void testCanDevelop() {
		//set tile to buy and player 
		tile = new Tile();
		player = new Player(playerNameValid, playerNumber, boardPosition, workforce, equipment);
		
		//check if can develop if no tile owner
		boolean canDevelopSuccessful = tile.canDevelop(player);
		assertEquals(false, canDevelopSuccessful);
		
		//check if can develop if player is the owner
		tile.setOwnedBy(player);
		canDevelopSuccessful = tile.canDevelop(player);
		assertEquals(true, canDevelopSuccessful);
		player2 = new Player(playerNameValid, playerNumber, boardPosition, workforce, equipment);
		tile.setOwnedBy(player2);
		canDevelopSuccessful = tile.canDevelop(player);
		assertEquals(true, canDevelopSuccessful);
		
	}
	
	//test the canBuy Method
	@Test
	void testCanBuy() {
		tile = new Tile(tileName, workforceCostToBuy, equipmentCostToBuy, workforceCostToDevelop,
				equipmentCostToDevelop, workforceRentCost, equipmentRentCost, tileNumber, system);
		player = new Player(playerNameValid, playerNumber, boardPosition, workforce, equipment);
		
		
		//return true if not owned and player has more resources than the cost
		boolean canBuy = tile.canBuy(player, playerList, scanner);
		assertEquals(true, canBuy);
		
		//return false if player is short in either resource and tile is not owned
		
		//player short on work force
		player.setPlayerWorkforce(0);
		canBuy =tile.canBuy(player, playerList, scanner);
		assertEquals(false, canBuy);
		
		
		//player short on equipment
		player.setPlayerWorkforce(400);
		player.setEquipment(0);
		canBuy =tile.canBuy(player, playerList, scanner);
		assertEquals(false, canBuy);
		
		
		//player short on both resources
		player.setPlayerWorkforce(0);
		canBuy =tile.canBuy(player, playerList, scanner);
		assertEquals(false, canBuy);
		
		
		//return false is the tile is owned 
		tile.setOwned(true);
		player.setPlayerWorkforce(400);
		player.setEquipment(400);
		canBuy =tile.canBuy(player, playerList, scanner);
		assertEquals(false, canBuy);
		
		
		
		
	}
	
	//test the payRent method
	@Test
	void testPayRent() {
		player = new Player(playerNameValid, playerNumber, boardPosition, workforce, equipment);
		player2 = new Player(playerNameValid, playerNumber, boardPosition, workforce, equipment);
		tile = new Tile();
		//set rent costs
		tile.setWorkforceRentCost(100);
		tile.setEquipmentRentCost(100);
		//set player2 as the tile owner
		tile.setOwnedBy(player2);
		
		
		//call the method, this always returns true
		boolean payRentSuccessful = tile.payRent(player, tile);
		
		//tile owner's resources go up by the rent cost
		assertEquals(workforce+100, player2.getPlayerWorkforce());
		assertEquals(equipment+100, player2.getEquipment());
		
		//player that pays rent resources go down by the rent cost
		assertEquals(workforce-100, player.getPlayerWorkforce());
		assertEquals(equipment-100, player.getEquipment());
		
		//test the return
		assertEquals(true, payRentSuccessful);
		
		
		
	}
	
	
	//test the developTile method
	@Test
	void testDevelopTile() {
		player = new Player(playerNameValid, playerNumber, boardPosition, workforce, equipment);

		tile = new Tile();
		//set developing costs to less than the player has returns true
		tile.setWorkforceCostToDevelop(100);
		tile.setEquipmentCostToDevelop(100);
		
		boolean developTileSuccessful = tile.developTile(player);
		assertEquals(true, developTileSuccessful);
		
		//set the develop costs to more than the player has returns false
		tile.setWorkforceCostToDevelop(1000);
		tile.setEquipmentCostToDevelop(1000);
		boolean developTileUnsuccessful = tile.developTile(player);
		assertEquals(false, developTileUnsuccessful);

	}
	
	

	//test haveToPayRent
	@Test
	void haveToPayRent() {
		//yes input will return true
		player = new Player(playerNameValid, playerNumber, boardPosition, workforce, equipment);
		player2 = new Player("joe", playerNumber,boardPosition,workforce,equipment);
		
		tile = new Tile();
		tile.setOwnedBy(player2);
		tile.setOwned(true);
		
		System.out.println("\n\n\nEnter 'yes' into the console");
		boolean haveToPayRent =tile.haveToPayRent(player, tile, scanner);
		assertEquals(true, haveToPayRent);
	
		
		//any other input will return false
		
		System.out.println("\n\n\nEnter 'no' into the console");
		haveToPayRent =tile.haveToPayRent(player, tile, scanner);
		assertEquals(false, haveToPayRent);
		
		
		//if is owned by same player it will return false
		tile.setOwnedBy(player);
		haveToPayRent =tile.haveToPayRent(player, tile, scanner);
		assertEquals(false, haveToPayRent);
		
		//if is not owned it will return false
		
		tile.setOwned(false);
		haveToPayRent =tile.haveToPayRent(player, tile, scanner);
		assertEquals(false, haveToPayRent);
			
		
	}
	
	//test canBeDeveloped and setCanBeDeveloped 
	@Test
	public void testCanBeDevelopedAndSetCanBeDevelopped() {
		
		//cant be developed
		tile=new Tile();
		assertEquals(false,tile.isCanBeDeveloped());
		
		//can be developed, updateTileDevelopStatus sets it to true
		tile.updateTileDevelopStatus();
		assertEquals(true,tile.isCanBeDeveloped());
		
		//development level is over 4 so can't be developed
		tile.setDevelopmentLevel(5);
		assertEquals(false,tile.isCanBeDeveloped());
		
		//set the boolean to false and check the getter
		tile.setCanBeDeveloped(false);
		assertEquals(false,tile.isCanBeDeveloped());
		
	}
	
	//test setter and get for isBought
	@Test
	public void testSetGetBought() {
		tile = new Tile();
		//starting bool value is false
		assertEquals(false, tile.isBought());
		//set the bool to true and check the return
		tile.setBought(true);
		assertEquals(true, tile.isBought());
		
	}
	//test setter and getter for equipmentRentCost
	@Test
	public void testSetGetEquipmentRentCost() {
		tile = new Tile(tileName, workforceCostToBuy, equipmentCostToBuy, workforceCostToDevelop,
				equipmentCostToDevelop, workforceRentCost, equipmentRentCost, tileNumber, system);
		//check the getter against the argument in the overloaded constructor
		assertEquals(equipmentRentCost, tile.getEquipmentRentCost());
		//set the rent cost a different value and check that the getter returns the same value passed in the setter
		tile.setEquipmentRentCost(500);
		assertEquals(500, tile.getEquipmentRentCost());
	}
	//test setter and getter for workforceRentCost
	@Test
	public void testSetGetWorkforceRentCost() {
		tile = new Tile(tileName, workforceCostToBuy, equipmentCostToBuy, workforceCostToDevelop,
				equipmentCostToDevelop, workforceRentCost, equipmentRentCost, tileNumber, system);
		//check the getter against the argument in the overloaded constructor
		assertEquals(workforceRentCost, tile.getWorkforceRentCost());
		//set the rent cost a different value and check that the getter returns the same value passed in the setter
		tile.setWorkforceRentCost(500);
		assertEquals(500, tile.getWorkforceRentCost());
	}
	//test getter and setter for availableToBuy
	@Test
	public void testSetGetAvailableToBuy() {
		tile = new Tile();
		//check the getter against the default value
		assertEquals(false, tile.isAvailableToBuy());
		//use the setter to change the boolean value and check the return on the getter
		tile.setAvailableToBuy(true);
		assertEquals(true, tile.isAvailableToBuy());
		
			
	}
	
	//test getter and setter for workforceCostToBuy
	@Test
	public void testSetGetWorkforceCostToBuy() {
		//default constructor
		tile = new Tile();
		//set a workforce cost to buy
		tile.setCostToBuy(workforceCostToBuy);
		//check the getter value against the passed in argument in the setter
		assertEquals(workforceCostToBuy, tile.getWorkforceCostToBuy());
		
	}
	
	//test getter and setter for tileNumber
	@Test
	public void testSetGetTileNumber() {
		//default constructor
		tile = new Tile();
		//set a tileNumber
		tile.setTileNumber(tileNumber);
		//check the getter value against the passed in argument in the setter
		assertEquals(tileNumber, tile.getTileNumber());
	}
	//test getter and setter for tileName
	@Test
	public void testSetGetTileName() {
		//default constructor
		tile = new Tile();
		//set a tileName
		tile.setTileName(tileName);
		//check the getter value against the passed in argument in the setter
		assertEquals(tileName, tile.getTileName());
	}
	//test setCostoDevelop
	@Test
	public void TestSetCostToDevelop() {
		tile = new Tile();
		tile.setDevelopmentLevel(1);
		
		//if less than 3 = cost to develop + (development level *100)
		//set cost to 0 so it should update the values to 100
		tile.setEquipmentCostToDevelop(0);
		tile.setWorkforceCostToDevelop(0);
		tile.setCostToDevelop(tile.getWorkforceCostToDevelop(), tile.getEquipmentCostToDevelop(), tile.getDevelopmentLevel());
		assertEquals(100, tile.getEquipmentCostToDevelop());
		assertEquals(100, tile.getWorkforceCostToDevelop());
		
		//if not less than 3 = cost to develop + (development level *200)
		//set cost to 0 so it should update the values to 800
		tile.setDevelopmentLevel(4);
		tile.setEquipmentCostToDevelop(0);
		tile.setWorkforceCostToDevelop(0);
		tile.setCostToDevelop(tile.getWorkforceCostToDevelop(), tile.getEquipmentCostToDevelop(), tile.getDevelopmentLevel());
		assertEquals(800, tile.getEquipmentCostToDevelop());
		assertEquals(800, tile.getWorkforceCostToDevelop());
		

	}
	//test offerToOtherPlayer
	@Test
	public void testOfferToOtherPlayer() {
		//create a tile, 2 players, and add two the playerlist
		tile = new Tile();
		player2 = new Player();
		player = new Player();
		playerList.add(player2);
		playerList.add(player);
		
		//test yes input when player is in the last index of the player list
		System.out.println("\n\n\nEnter Yes");
		boolean yesInput = tile.offerToOtherPlayer(player, playerList, scanner);
		assertEquals(true, yesInput);
		//test yes input when player is not in the last index of the player list
		System.out.println("\n\n\nEnter Yes");
		yesInput = tile.offerToOtherPlayer(player2, playerList, scanner);
		assertEquals(true, yesInput);
		
		//test negative input
		System.out.println("\n\n\nEnter No");
		boolean noInput = tile.offerToOtherPlayer(player, playerList, scanner);
		assertEquals(false, noInput);
		
		
		
	}
	//test developmentInfo
	@Test
	public void testDevelopmentInfo() {
		//create tile and set up setters for fields used 
		tile = new Tile();
		tile.setTileName(tileName);
		tile.setTileNumber(tileNumber);
		tile.setDevelopmentLevel(1);
		tile.setWorkforceCostToDevelop(workforceCostToDevelop);
		tile.setEquipmentCostToDevelop(equipmentCostToDevelop);
		
		//create String using the exact format used in the method with the arguments used in the setters
		String testString = String.format("%-20s %-20d %-20d %-20d %-20d\n", tileName, tileNumber,
				1, workforceCostToDevelop, equipmentCostToDevelop);
		//check that they match
		assertEquals(testString, tile.developmentInfo());
		
	}
	
	//test that increaseDevelopmentLevel does not increment past a certain level
	@Test
	public void testIncreaseDevLevelInvalid() {
		tile = new Tile();
		//upper limit is for 4, so set to 4
		tile.setDevelopmentLevel(4);
		//try to increment
		tile.increaseDevelopmentLevel();
		//check that development level is still 4
		assertEquals(4, tile.getDevelopmentLevel());
	}
	
	//test setter and getter for Syztem
	@Test
	public void testSetGetSyztem() {
		//default constructor
		tile = new Tile();
		//use the setter to set a syztem
		tile.setSyztem(system);
		//check the getter return against the argument passed
		assertEquals(system, tile.getSyztem());
	}
	
	
}
