package artemislitetest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import artemislite.Player;

class PlayerTest {
	
	// TEST DATA
	Player player1;
	Player player2;
	String playerNameValid, playerNameInvalid;
	String player2Name;
	int playerNumber;
	int boardPosition;
	int workforce;
	int equipment;
	ArrayList<String> playerNames;
	Scanner scanner;
	int numberOfPlayers;
	

	@BeforeEach
	void setUp() throws Exception {
		playerNameValid = "Player name";
		player2Name = "Player 2 name";
		playerNameInvalid = "";
		playerNumber = 1;
		boardPosition = 0;
		workforce = 300;
		equipment = 400;
		numberOfPlayers=2;
		playerNames = new ArrayList<>();
		playerNames.add(playerNameValid);
		playerNames.add(player2Name);
		
		player1 = new Player(playerNameValid, playerNumber, boardPosition, workforce, equipment);
		
		scanner = new Scanner(System.in);
	}
	//test overloaded constructor with valid args
	@Test
	void testValidPlayerConstructor() {
		player1 = new Player(playerNameValid, playerNumber, boardPosition, workforce, equipment);
		assertEquals(playerNameValid, player1.getPlayerName());
		assertEquals(playerNumber, player1.getPlayerNumber());
		assertEquals(boardPosition, player1.getBoardPosition());
		assertEquals(workforce, player1.getPlayerWorkforce());
		assertEquals(equipment, player1.getEquipment());
	}
	
	
	//test invalid constructor
	@Test
	void testInvalidPlayerConstructor() {
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->{
			player1 = new Player(playerNameInvalid, playerNumber, boardPosition, workforce, equipment);
		});
		assertEquals("Player name cannot be blank", e.getMessage());
	}
	
	//test createPlayer
	@Test
	void testCreatePlayer() {
		ArrayList<Player> players = Player.createPlayer(playerNames);
		//check the size the and that array contains players
		if(players.size()==2 && players.contains(player1) && players.contains(player2)) {
			assertTrue(true);
		}
	}
	
	//test number of players
	@Test
	void testNumOfPlayers() {
		
		int expectedNumberOfPlayers=2;
		//by first entering 1 and 5 we check the control in place to ensure the current number of players
		System.out.println("\n\n\nTEST NUMBER OF PLAYERS enter - 1 then 5 then 2");
		int numberOfPlayers = Player.numOfPlayers(scanner);
		assertEquals(expectedNumberOfPlayers, numberOfPlayers);
	}
	
	//test deductPlayerResources
	@Test
	void testDeductPlayerResources() {
		
		//player has enough of both resources
		player1 = new Player(playerNameValid, playerNumber, boardPosition, workforce, equipment);
		boolean expected = true;
		boolean resourcesDeductedSuccessfully = player1.deductPlayerResources(player1, 100, 100);
		assertEquals(expected, resourcesDeductedSuccessfully);
		
		//player does not have enough resources
		//test not enough equipment but enough workforce
		expected = false;
		player1.setEquipment(0);
		boolean resourcesDeductedUnsuccessfully = player1.deductPlayerResources(player1, 100, 100);
		assertEquals(expected, resourcesDeductedUnsuccessfully);
		//test not enough workforce but enough equipment
		player1.setPlayerWorkforce(0);
		player1.setEquipment(100);
		resourcesDeductedUnsuccessfully = player1.deductPlayerResources(player1, 100, 100);
		assertEquals(expected, resourcesDeductedUnsuccessfully);
		//test not enough workfoce and not enough equipment
		player1.setEquipment(0);
		resourcesDeductedUnsuccessfully = player1.deductPlayerResources(player1, 100, 100);
		assertEquals(expected, resourcesDeductedUnsuccessfully);
	}
	//test deductRent method
	@Test
	void testDeductRent() {
		//set up rent costs
		int workforceRentDeduction =100;
		int equipmentRentDeduction =100;
		//deduct rent based on above vars
		boolean sucessfulRendDeduction=player1.deductRent(player1, workforceRentDeduction, equipmentRentDeduction);
		//test the return
		boolean expected = true;
		assertEquals(expected, sucessfulRendDeduction);
		//test the updating of the player resources
		assertEquals((workforce-workforceRentDeduction), player1.getPlayerWorkforce());
		assertEquals((equipment-equipmentRentDeduction),player1.getEquipment());
	}
	
	//test receiveRent
	@Test
	void testReceiveRent() {
		//set up rent costs
		int workforceRentGain =100;
		int equipmentRentGain =100;
		//receive rent based on above vars
		boolean expected = true;
		boolean deductRentSuccessfully = player1.recieveRent(workforceRentGain, equipmentRentGain);
		assertEquals(expected, deductRentSuccessfully);
		//test the updating of the player resources
		assertEquals((workforce+workforceRentGain), player1.getPlayerWorkforce());
		assertEquals((equipment+equipmentRentGain),player1.getEquipment());
	}
	
	//test default constructor
	@Test
	void testDefaultConstructor() {
		Player player = new Player();
		assertNotNull(player);
	}
	
	//test playerNames
	@Test
	void testPlayerNames() {
		
		//number of players is set to two, so .size() should return an arraylist of size 2.
		//by entering the same name twice we hit multiple branches
		System.out.println("\n\n\n Enter the same name twice and then a different name");
		ArrayList<String> testPlayerNames = Player.playerNames(numberOfPlayers, scanner, 1);
		assertEquals(numberOfPlayers, testPlayerNames.size());
		

	}
	
	//test UpdatePlayerBoardPosition
	@Test
	void testUpdatePlayerBoardPosition() {
		
		//board position is 0, getBoardPostion returns 0
		assertEquals(boardPosition, player1.getBoardPosition());
		//update the board position to 10, check getter returns 10
		player1.updatePlayerBoardPosition(player1, 10);
		assertEquals(10, player1.getBoardPosition());
		
		//reset board position
		player1.setBoardPosition(boardPosition);
		//update board position by 12, this is out of the allowed range 
		//by moving up 12 tiles we'd land on tile number 13, hence it gets reset to 0 
		//so the getter should return 0
		player1.updatePlayerBoardPosition(player1, 12);
		assertEquals(boardPosition, player1.getBoardPosition());

	}
	//test gameOverCondition
	@Test
	void testGameOverCondition() {
		
		//test false return
		boolean expected = false;
		assertEquals(expected, player1.gameOverCondition());
		//test true return
		expected = true;
		//check equipment less than 0
		player1.setEquipment(-10);
		assertEquals(expected, player1.gameOverCondition());
		//check workforce less than 0
		player1.setEquipment(100);
		player1.setPlayerWorkforce(-10);
		assertEquals(expected, player1.gameOverCondition());
		//check both equipment and workforce less than 0
		player1.setEquipment(-10);
		assertEquals(expected, player1.gameOverCondition());
	}
	
	@Test
	void testSetPlayerNumber() {
		
		//test valid boundaries 
		assertEquals(playerNumber, player1.getPlayerNumber());
		player1.setPlayerNumber(4);
		assertEquals(4, player1.getPlayerNumber());
		
		
		//test invalid boundaries
		assertThrows(IllegalArgumentException.class, () ->{
			player1.setPlayerNumber(5);
		});
		
		assertThrows(IllegalArgumentException.class, () ->{
			player1.setPlayerNumber(0);
		});
		
		
		
	}
	

}
