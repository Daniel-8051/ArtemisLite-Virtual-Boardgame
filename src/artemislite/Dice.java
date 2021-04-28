/**
 * 
 */
package artemislite;

import java.util.Random;

/**
 * @author Daniel McAuley, Helder Santos, Gareth Alexander, Joseph Collins
 *
 */
public class Dice {

	/**
	 * Number of die 1 face
	 */
	private int die1;
	/**
	 * Number of die 2 face
	 */
	private int die2;

	/**
	 * Creates a dice object
	 */
	public Dice() {
		
	}

	/**
	 * Produces two random integers for two dice and returns the sum
	 * 
	 * @return - the sum of 2 random dice roll
	 */
	public int rollDice() {

		int totalRoll = 0;

		Random random = new Random();

		die1 = random.nextInt(6) + 1;

		die2 = random.nextInt(6) + 1;
		totalRoll = die1 + die2;
		System.out.printf("%-20s %-10d\n", "You have rolled", totalRoll);
		return totalRoll;
	}
}
