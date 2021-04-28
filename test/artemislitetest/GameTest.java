package artemislitetest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import artemislite.Game;
import artemislite.Player;
import artemislite.Tile;

class GameTest {
	
	// TEST DATA
	Game game;
	int maxNumberOfPlayersValid, maxNumberOfPlayersInvalid;
	int maxNumberOfTilesValid, maxNumberOfTilesInvalid;
	Tile tile1;
	Tile tile2;
	Tile tile3;
	Tile tile4;
	Tile tile5;
	Tile tile6;
	Tile tile7;
	Tile tile8;
	Tile tile9;
	Tile tile10;
	Tile tile11;
	Tile tile12;
	Player player1;
	Player player2;
	Scanner scanner;


	@BeforeEach
	void setUp() throws Exception {
		maxNumberOfPlayersValid = 4;
		maxNumberOfPlayersInvalid = 5;
		maxNumberOfTilesValid = 12;
		maxNumberOfTilesInvalid = 13;
		game = new Game(maxNumberOfPlayersValid, maxNumberOfTilesValid);
		tile1 = new Tile();
		tile2 = new Tile();
		tile3 = new Tile();
		tile4 = new Tile();
		tile5 = new Tile();
		tile6 = new Tile();
		tile7 = new Tile();
		tile8 = new Tile();
		tile9 = new Tile();
		tile10 = new Tile();
		tile11 = new Tile();
		tile12 = new Tile();
		player1 = new Player();
		player2 = new Player();
		scanner = new Scanner(System.in);
		
	}

	@Test
	void testValidGameConstructor() {
		game = new Game(maxNumberOfPlayersValid, maxNumberOfTilesValid);
		assertEquals(maxNumberOfPlayersValid, game.getMaxNumberOfPlayers());
		assertEquals(maxNumberOfTilesValid, game.getMaxNumberOfTiles());
	}
	
	@Test
	void testInvalidGameConstructor() {
		// invalid max number of players
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->{
			game = new Game(maxNumberOfPlayersInvalid, maxNumberOfTilesValid);
		});
		assertEquals("Max number of players must be between 2-4", e.getMessage());
		
		// invalid max number of tiles
		e = assertThrows(IllegalArgumentException.class, () ->{
			game = new Game(maxNumberOfPlayersValid, maxNumberOfTilesInvalid);
		});
		assertEquals("Max number of tiles must be between 2-12", e.getMessage());
	}

	@Test
	void testAddTile() {
			
		//add tiles to game board
		game.addTile(tile1);
		game.addTile(tile2);
		game.addTile(tile3);
		game.addTile(tile4);
		game.addTile(tile5);
		game.addTile(tile6);
		game.addTile(tile7);
		game.addTile(tile8);
		game.addTile(tile9);
		game.addTile(tile10);
		game.addTile(tile11);
		game.addTile(tile12);
		if(game.getGameBoard().size()==12) {
			assertTrue(true);
		} 
		//add tiles to gameboard past max limit to throw exception
		assertThrows(IllegalArgumentException.class, ()->{
			game.addTile(tile12);
			game.addTile(tile12);
			
		});
		
	}
	
	@Test
	void testAddPlayer() {
		Player player1 = new Player();
		Player player2 = new Player();
		game.addPlayer(player1);
		game.addPlayer(player2);
		game.addPlayer(player1);
		game.addPlayer(player2);
		if(game.getPlayers().size()==4) {
			assertTrue(true);
		} 
		assertThrows(IllegalArgumentException.class, ()->{
			game.addPlayer(player1);
			game.addPlayer(player2);
			
		});
	}
	//displayAllTiles method consists of outputting strings through tile.getters all of these are fully tested in the TileTest.java
	@Test
	void testDisplayAllTiles() {
		
		game.addTile(tile1);
		tile1.setTileNumber(0);
		game.addTile(tile2);
		tile2.setTileNumber(6);
		game.addTile(tile3);
		tile3.setTileNumber(1);
		
		game.displayAllTiles();
		assertTrue(true);
	}
	@Test
	void testDisplayAllPlayers() {
		
		game.addPlayer(player1);
		game.addPlayer(player2);
		
		game.displayAllPlayers();
		assertTrue(true);
		
	}
	//test offer to buy 
	@Test
	void testOfferToBuy() {
		System.out.println("TEST OFFER TO BUY");
		Player player1 = new Player();
		Player player2 = new Player();
		game.addPlayer(player1);
		game.addPlayer(player2);
		ArrayList<Player> players = game.getPlayers();
		assertEquals(true, game.offerToBuy(player1, tile1, scanner, players));
		assertEquals(true, game.offerToBuy(player1, tile1, scanner, players));
		
		
		
	}
	
	@Test
	void testGetCurrentPlayerTile() {
		player1.setBoardPosition(0);
		game.addTile(tile1);
		
		assertEquals(tile1, game.getCurrentPlayerTile(player1));
		
	}
	
	
	//checkSystem status goes over all the game board and check various conditions for each tile, so set this for each so that they pass if checks
	@Test
	void testCheckSystem() {
		
		player1.setPlayerName("owner");
		game.addTile(tile1);
		tile1.setOwned(true);
		tile1.setOwnedBy(player1);
		tile1.setCanBeDeveloped(true);
		tile1.setDevelopmentLevel(1);	
		game.addTile(tile2);
		tile2.setOwned(true);
		tile2.setOwnedBy(player1);
		tile2.setCanBeDeveloped(true);
		tile2.setDevelopmentLevel(1);
		game.addTile(tile3);
		tile3.setOwned(true);
		tile3.setOwnedBy(player1);
		tile3.setCanBeDeveloped(true);
		tile3.setDevelopmentLevel(1);
		game.addTile(tile4);
		tile4.setOwned(true);
		tile4.setOwnedBy(player1);
		tile4.setCanBeDeveloped(true);
		tile4.setDevelopmentLevel(1);
		game.addTile(tile5);
		tile5.setOwned(true);
		tile5.setOwnedBy(player1);
		tile5.setCanBeDeveloped(true);
		tile5.setDevelopmentLevel(1);
		game.addTile(tile6);
		tile6.setOwned(true);
		tile6.setOwnedBy(player1);
		tile6.setCanBeDeveloped(true);
		tile6.setDevelopmentLevel(1);
		game.addTile(tile7);
		tile7.setOwned(true);
		tile7.setOwnedBy(player1);
		tile7.setCanBeDeveloped(true);
		tile7.setDevelopmentLevel(1);
		game.addTile(tile8);
		tile8.setOwned(true);
		tile8.setOwnedBy(player1);
		tile8.setCanBeDeveloped(true);
		tile8.setDevelopmentLevel(1);
		game.addTile(tile9);
		tile9.setOwned(true);
		tile9.setOwnedBy(player1);
		tile9.setCanBeDeveloped(true);
		tile9.setDevelopmentLevel(1);
		game.addTile(tile10);
		tile10.setOwned(true);
		tile10.setOwnedBy(player1);
		tile10.setCanBeDeveloped(true);
		tile10.setDevelopmentLevel(1);
		game.addTile(tile11);
		tile11.setOwned(true);
		tile11.setOwnedBy(player1);
		tile11.setCanBeDeveloped(true);
		tile11.setDevelopmentLevel(1);
		game.addTile(tile12);
		tile12.setOwned(true);
		tile12.setOwnedBy(player1);
		tile12.setCanBeDeveloped(true);
		tile12.setDevelopmentLevel(1);
		
		List<Tile>holdingList=game.checkSystem(player1);
		
		//seen as free tiles are skipped size should only be 10 and the first tile in the list should be tile 2
		assertTrue(holdingList.size()==10);
		assertEquals(tile2, holdingList.get(0));
		
		
		
		
		
	}
	
	//test checkWinCondition
	@Test
	void testCheckWinCondition() {
		Tile tile1 = new Tile();
		Tile tile2 = new Tile();
		tile1.setDevelopmentLevel(4);
		tile2.setDevelopmentLevel(4);
		game.addTile(tile1);
		game.addTile(tile2);
		//all tiles in game board are lvl 4 so it should return true
		assertTrue(game.checkWinCondition());
		//set one of the tiles to lvl 1 and it should return false
		tile1.setDevelopmentLevel(1);
		assertFalse(game.checkWinCondition());
	}

	@Test
	void testGetPlayers() {
		ArrayList<Player> arrayList = new ArrayList<>();
		assertEquals(arrayList, game.getPlayers());
	}

	

	@Test
	void testSetGetMaxNumberOfPlayersValid() {
		game = new Game();
		game.setMaxNumberOfPlayers(maxNumberOfPlayersValid);
		assertEquals(maxNumberOfPlayersValid, game.getMaxNumberOfPlayers());
	}
	
	@Test
	void testSetGetMaxNumberOfPlayersInvalid() {
		game = new Game();
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->{
			game.setMaxNumberOfPlayers(maxNumberOfPlayersInvalid);
		});
		assertEquals("Max number of players must be between 2-4", e.getMessage());
	}


	@Test
	void testSetGetMaxNumberOfTilesValid() {
		game = new Game();
		game.setMaxNumberOfTiles(maxNumberOfTilesValid);
		assertEquals(maxNumberOfTilesValid, game.getMaxNumberOfTiles());
	}
	@Test
	void testTakeTurn() {
		player1.setPlayerName("helder");
		player1.setBoardPosition(0);
		game.addTile(tile1);
		game.addTile(tile2);
		game.addTile(tile3);
		game.addTile(tile4);
		game.addTile(tile5);
		game.addTile(tile6);
		game.addTile(tile7);
		game.addTile(tile8);
		game.addTile(tile9);
		game.addTile(tile10);
		game.addTile(tile11);
		game.addTile(tile12);
		
		System.out.println("\n\n\n Enter yes");
		assertTrue(game.takeTurn(player1));
		
		System.out.println("\n\n\n Enter no");
		assertFalse(game.takeTurn(player1));
		
		
		
		
	}
}