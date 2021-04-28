package artemislitetest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import artemislite.Dice;

class DiceTest {
	
	// TEST DATA
	Dice dice;

	@BeforeEach
	void setUp() throws Exception {
		dice = new Dice();
	}

	@Test
	void testDice() {
		assertNotNull(dice);
	}

	@Test
	void testRollDice() {
		
		
		//loop to execute multiple times ensuring that the rolls dont fall outside the valid boundaries 
		for(int loop=0; loop<100;loop++) {
			assertTrue(dice.rollDice()>=2&dice.rollDice()<=12);
		}
		
	}

}
