/**
 * 
 */
package com.myproject.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.myproject.TestApplicationConfiguration;
import com.myproject.entity.Game;
import com.myproject.entity.Round;

/**
 * @author Margin
 *
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
class RoundDaoDBTest {
	
	//DAOs
	
		@Autowired
	    GameDao gameDao;
	    
	    @Autowired
	    RoundDao roundDao;

	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	
	@BeforeEach
	void setUp() throws Exception {
		List<Game> gameList = gameDao.getAllGames();
	    for(Game game : gameList) {
	    	gameDao.deleteGameById(game.getGameId());
	    }
	    
	    List<Round> roundList = roundDao.getAllRounds();
	    for(Round round : roundList) {
	    	roundDao.deleteRoundById(round.getRoundId());
	    }
	  
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	 @Test
	   public void testGetAddRound() {
		 //Arrange the data to set it up for testing.		 
	        Game game = new Game();
	        game.setGameId(1);
	        game.setAnswer("7515");
	        game.setGameFinished(true);
	        game = gameDao.addGame(game); //Act
	        
	        
	        Round round = new Round();
	        round.setGuess("4153");
	        round.setGuessTime(LocalDateTime.now().withNano(0));
	        round.setResult("e:7:p:3");
	        round.setNumberOfMatch(7);
	        round.setNumberOfPartialMatch(3);
	        round.setGameId(game.getGameId());


	        
	      //Act on the data through  method that performs an action.
	        round = roundDao.addRound(round);
	        Round fromDao = roundDao.getRoundById(round.getRoundId());
		  
	        //Assert that the result from acting on that data is what we expect it to be.
	        assertEquals(round, fromDao);
	 }


	 @Test
	   public void testGetAllRounds() {
		 //Arrange the data to set it up for testing.		 
	        Game gameOne = new Game();
	        gameOne.setGameId(1);
	        gameOne.setAnswer("1478");
	        gameOne.setGameFinished(true);
	        gameOne = gameDao.addGame(gameOne); //Act

	   
	        Round round = new Round();
	        round.setGuess("6985");
	        round.setGuessTime(LocalDateTime.now().withNano(0));
	        round.setResult("e:2:p:3");
	        round.setNumberOfMatch(2);
	        round.setNumberOfPartialMatch(3);
	        round.setGameId(gameOne.getGameId());
	        round = roundDao.addRound(round); //Act

	        
	        Game gameTwo = new Game();
	        gameTwo.setGameId(1);
	        gameTwo.setAnswer("1532");
	        gameTwo.setGameFinished(true);
	        gameTwo = gameDao.addGame(gameTwo); //Act

	              
	        Round roundTwo = new Round();
	        roundTwo.setGuess("7581");
	        roundTwo.setGuessTime(LocalDateTime.now().withNano(0));
	        roundTwo.setResult("e:1:p:3");
	        roundTwo.setNumberOfMatch(1);
	        roundTwo.setNumberOfPartialMatch(3);
	        roundTwo.setGameId(gameTwo.getGameId());

	      //Act on the data through  method that performs an action.
	        roundTwo = roundDao.addRound(roundTwo);
	        List<Round> roundList = roundDao.getAllRounds();

	        //Assert that the result from acting on that data is what we expect it to be.
	        assertEquals(2, roundList.size()); //Should only have 2 rounds
	        assertTrue(roundList.contains(round)); //Should contain record of round
	        assertTrue(roundList.contains(roundTwo));//Should have record of Round Two


	    }

	 @Test
	   public void testUpdateRound() {
		 
		 //Arrange the data to set it up for testing.		 
	        Game game = new Game();
	        game.setGameId(1);
	        game.setAnswer("5142");
	        game.setGameFinished(true);
	        game = gameDao.addGame(game); //Act
	        
	        
	        Round round = new Round();
	        round.setGuess("4153");
	        round.setGuessTime(LocalDateTime.now().withNano(0));
	        round.setResult("e:2:p:3");
	        round.setNumberOfMatch(2);
	        round.setNumberOfPartialMatch(3);
	        round.setGameId(game.getGameId());


	        
	      //Act on the data through  method that performs an action.
	        round = roundDao.addRound(round);
	        Round fromDao = roundDao.getRoundById(round.getRoundId());
		  
	        //Assert that the result from acting on that data is what we expect it to be.
	        assertEquals(round, fromDao);

	        //Arrange
	        round.setGuess("5412");
	        //Act
	        roundDao.updateRound(round);
	        

	        //Assert - should not be equal to 4153
	        assertNotEquals(round, fromDao);
	        fromDao = roundDao.getRoundById(round.getRoundId());
	        assertEquals(round, fromDao);
		
	    }
	 
	 
	 @Test
	   public void testDeleteRound() {
		//Arrange the data to set it up for testing.		 
	        Game game = new Game();
	        game.setGameId(1);
	        game.setAnswer("6521");
	        game.setGameFinished(true);
	        game = gameDao.addGame(game); //Act
	        
	        
	        Round round = new Round();
	        round.setGuess("4892");
	        round.setGuessTime(LocalDateTime.now().withNano(0));
	        round.setResult("e:7:p:3");
	        round.setNumberOfMatch(7);
	        round.setNumberOfPartialMatch(3);
	        round.setGameId(game.getGameId());

		    //Act on the data through  method that performs an action.
	        roundDao.deleteRoundById(round.getRoundId());

	        Round fromDao = roundDao.getRoundById(round.getRoundId());
	        //Assert that the result from acting on that data is what we expect it to be.
	        assertNull(fromDao); //should be null 
	        
	      

	    }

}
