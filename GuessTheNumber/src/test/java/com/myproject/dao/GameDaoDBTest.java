/**
 * 
 */
package com.myproject.dao;

import static org.junit.jupiter.api.Assertions.*;

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
class GameDaoDBTest {
	
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
	   public void testAddGame() {
		 //Arrange the data to set it up for testing.
	        Game game = new Game();
	        game.setAnswer("2523");
	        game.setGameFinished(false);
	        
	      //Act on the data through  method that performs an action.
	        game = gameDao.addGame(game);
	        Game fromDao = gameDao.getGameById(game.getGameId());
	     //Assert that the result from acting on that data is what we expect it to be.
	        assertEquals(game, fromDao);
	    }

	 @Test
	   public void testAllGames() {
		 //Arrange the data to set it up for testing.
	        Game gameOne = new Game();
	        gameOne.setAnswer("4151");
	        gameOne.setGameFinished(true);
	        
	        Game gameTwo = new Game();
	        gameTwo.setAnswer("5678");
	        gameTwo.setGameFinished(true);
	        
	      //Act on the data through  method that performs an action.
	        gameOne = gameDao.addGame(gameOne);
	        Game fromDao = gameDao.getGameById(gameOne.getGameId());
	        
	        gameTwo = gameDao.addGame(gameTwo);
	        Game fromDaoTwo = gameDao.getGameById(gameTwo.getGameId());
	        
	        
	     //Assert that the result from acting on that data is what we expect it to be.
	        assertEquals(gameOne, fromDao);
	        assertEquals(gameTwo, fromDaoTwo);
	    }

	 @Test
	   public void testUpdateGame() {
		 //Arrange the data to set it up for testing.
	        Game game = new Game();
	        game.setAnswer("8878");
	        game.setGameFinished(false);
	        
	      //Act on the data through  method that performs an action.
	        game = gameDao.addGame(game);
	        
	        // Arrange
	        game.setAnswer("2222");
	        gameDao.updateGame(game);
	        Game fromDao = gameDao.getGameById(game.getGameId());
	    
	        //Assert that the result from acting on that data is what we expect it to be.
	        assertEquals(fromDao, game);
	        //Updated answer
	        //Act on the data through  method that performs an action.
	        fromDao = gameDao.getGameById(game.getGameId());

	        //Assert
	        //Should not have same answer of 8878, new value should be updated.
	        assertNotEquals(fromDao.getAnswer(),"8878"); 
	    
	    }
	 
	 
	 @Test
	   public void testDeleteGame() {
		//Arrange the data to set it up for testing.
	        Game gameOne = new Game();
	        gameOne.setAnswer("9874");
	        gameOne.setGameFinished(true);
	        
	        Game gameTwo = new Game();
	        gameTwo.setAnswer("5353");
	        gameTwo.setGameFinished(true);
	        
	      //Act on the data through  method that performs an action.
	        gameOne = gameDao.addGame(gameOne);
	        gameTwo = gameDao.addGame(gameTwo);
	        
	        //Game two should be deleted
	        gameDao.deleteGameById(gameTwo.getGameId());

	        
	        //Assert
	        List<Game> games = gameDao.getAllGames();
	        //Should only contain one entry of game one
	        assertEquals(1, games.size());
	        //Should only have game one data
	        assertTrue(games.contains(gameOne));

	    }

}
