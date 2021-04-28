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
public class GameRunner {

	/**
	 * Starting point for Artemis Lites
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// create game
		Game game = new Game(4, 12);

		// create tiles
		Tile tile1 = new Tile("Pass Go", 0); // pass go
		Tile tile2 = new Tile("Crawler Support", 60, 45, 30, 35, 20, 15, 1, Syztem.EGS_SYSTEM_1);
		Tile tile3 = new Tile("Mobile Launcher", 80, 60, 45, 50, 25, 20, 2, Syztem.EGS_SYSTEM_1);
		Tile tile4 = new Tile("Crew Module", 100, 95, 60, 40, 35, 35, 3, Syztem.OCM_SYSTEM_2);
		Tile tile5 = new Tile("Service Module", 120, 100, 70, 60, 40, 35, 4, Syztem.OCM_SYSTEM_2);
		Tile tile6 = new Tile("Launch Abort", 120, 120, 65, 55, 30, 35, 5, Syztem.OCM_SYSTEM_2);
		Tile tile7 = new Tile("Free Tile", 6); // free tile
		Tile tile8 = new Tile("Cargo Hold Development", 150, 130, 75, 60, 45, 40, 7, Syztem.SLS_SYSTEM_3);
		Tile tile9 = new Tile("Exploration & Core Stage", 160, 155, 85, 70, 50, 75, 8, Syztem.SLS_SYSTEM_3);
		Tile tile10 = new Tile("Booster Development", 170, 155, 80, 95, 55, 50, 9, Syztem.SLS_SYSTEM_3);
		Tile tile11 = new Tile("Leave Earth Maneuverer", 210, 225, 100, 95, 60, 55, 10, Syztem.PO_SYSTEM_4);
		Tile tile12 = new Tile("Deep Space Travel Checks", 240, 235, 125, 120, 70, 70, 11, Syztem.PO_SYSTEM_4);

		// add the tiles to the board
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

		// print out tile details
		game.displayAllTiles();
		
		Scanner scanner = new Scanner(System.in);
		
		
		
		// get number of players and names
		ArrayList<String> playerNames = new ArrayList<String>();
		
		do {
		try {
			playerNames = Player.playerNames(Player.numOfPlayers(scanner), scanner, 1);
		} catch (Exception e) {
			System.out.println("Try again");
		}
		}while(playerNames.isEmpty());
		
		
		// creates players
		ArrayList<Player> gamePlayers = Player.createPlayer(playerNames);
		for (Player p : gamePlayers) {
			game.addPlayer(p);
		}

		// print out player details
		game.displayAllPlayers();

		// instructions for game
		game.welcomeMessage();

		boolean keepContinuingGame = true;

		// loop for each player to keep taking their turn
		do {
			for (Player p : gamePlayers) {

				// if player wants to keep playing
				if (!game.takeTurn(p)) {
					keepContinuingGame = false;
					break;
				}
				
				// if player resources have hit zero
				if (p.gameOverCondition()) {
					game.displayGameOverMessage();	
					keepContinuingGame = false;
					break;
				}

				// if player has won the game
				if (game.checkWinCondition()) {
					// display player stats
					game.displayWinGameMessage();
					keepContinuingGame = false;
					break;
				} else {
					System.out.println("GAME CONTINUES");
					
					// shows game board and player details at the end of each turn
					game.displayAllTiles();
					game.displayAllPlayers();

				}
			}

		} while (keepContinuingGame);

		System.out.println("END OF GAME");
		game.displayAllTiles();
		game.displayAllPlayers();
	}

}
