/**
 * 
 */
package com.myproject.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
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
import com.myproject.dao.GameDao;
import com.myproject.dao.RoundDao;
import com.myproject.entity.Game;
import com.myproject.entity.Round;

/**
 * @author Margin
 *
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
class GameServiceTest {
	
	 @Autowired
	 GameService service;
	 @Autowired
	  GameDao gameDao;
    

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	
	
	@AfterEach
	void tearDown() throws Exception {
	}

	  @Test
	    public void testGameOne() {
	        String guess = "1625";
	        String answer = "1678";
	        String result = service.getResult(guess, answer);

	        assertEquals("e:2:p:0", result);
	    }
	
	@Test
    public void testGetResult() {
        String result = service.getResult("1412", "1425");
        assertEquals("e:2:p:1", result);
    }
	
	 @Test
	    public void testGenerateRandomAnswer() {
	        String answer = service.generateRandomAnswer();
	        assertEquals(4, answer.length());         //Checking that the length is 4
	        assertEquals(true, testisUniqueCharacters(answer));

	        List<String> list = new ArrayList<String>();
	        for (String item: answer.split("")) {
	            assertFalse(list.contains(item));
	            list.add(item);
	        }
	    }
	 
	 @Test
	    public void testGetGameTrue() {
	        
	        Game game = new Game();
	        game.setAnswer("3876");
	        game.setGameFinished(true);
	        game = gameDao.addGame(game);
	        
	        Game newGame = service.getGameById(game.getGameId());
	        assertEquals("3876", newGame.getAnswer());
	        
	    }
	 
	 
	 //helper function for above test
	    boolean testisUniqueCharacters(String str){
	        // If at any time we encounter 2 same
	        // characters, return false
	        for (int i = 0; i < str.length(); i++)
	            for (int j = i + 1; j < str.length(); j++)
	                if (str.charAt(i) == str.charAt(j))
	                    return false;
	 
	        // If no duplicate characters encountered,
	        // return true
	        return true;
	    }
	    
	
}
