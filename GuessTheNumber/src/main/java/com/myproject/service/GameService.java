package com.myproject.service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myproject.dao.GameDao;
import com.myproject.dao.RoundDao;
import com.myproject.entity.Game;
import com.myproject.entity.Round;

@Component
public class GameService {

	  	@Autowired
	    RoundDao roundDao;
	    @Autowired
	    GameDao gameDao;



	    //Add game
	    public Game addGame(Game game) {
			return gameDao.addGame(game);

	    }

	    //Add Round
	    public Round addRound(Round round) throws InvalidRoundException{
	        validateRound(round);

	        //before validation method is called to ensure it mets the condition
	        return roundDao.addRound(round);
	    }


	    //Delete game
	    public void deleteGameById(int gameId) {
	    	gameDao.deleteGameById(gameId);
	    }

		//Delet round by Id
	    public void deleteRoundById(int id) {
	        roundDao.deleteRoundById(id);
	    }


		//Edit Round
	    public void editRound(Round round) throws InvalidRoundException{
	        validateRound(round);
	        roundDao.updateRound(round);
	    }

	    //Generate random answers
	    public String generateRandomAnswer(){
	    	//Range from 1 to 10
	        List<Integer> pool = IntStream.range(0, 10).boxed().collect(Collectors.toList());
	        Random rand = new Random();

	        String out = "";

	        //loop 4 times since answer is going to be 4 length
	        for (int i = 1; i<=4; i++){
	            out += pool.remove(rand.nextInt(pool.size()));
	        }
	        return out;
	    }

	    //Get all games
	    public List<Game> getAllGames() {
	        return gameDao.getAllGames();
	    }

	    //Getting all the rounds
	    public List<Round> getAllRounds() {
	        return roundDao.getAllRounds();
	    }

	    //Get game by id
	    public Game getGameById(int gameId) {
	        return gameDao.getGameById(gameId);

	    }


	    //Get result
	    public String getResult(String answer, String guess){
	       //initial result =0;
	    	int exactNumberOfMatch =0;
	        int partialNumberOfMatch =0;

	        String[] ansToken = answer.split("");
	        String[] guessToken = guess.split("");

	        for (int i = 0; i < answer.length(); i++) {
	            if (ansToken[i].equals(guessToken[i])) {
	            	exactNumberOfMatch += 1;
	            }
	            else if (answer.contains(guessToken[i])) {
	            	partialNumberOfMatch += 1;
	            }
	        }
	        return "e:" + exactNumberOfMatch + ":" + "p:" + partialNumberOfMatch;
	    }



	   //Game

	    //Getting round by an ID
	    public Round getRoundById(int id) {
	        return roundDao.getRoundById(id);
	    }
	    //Gets all round for a specific game by id
		 public List<Round> getRoundsByGameID(int gameID){
		        return gameDao.getRoundsForAGame(gameID);
		    }


		//Make a guess
	    public Round makeGuess(Round round) {
	        //Gets the gameId of specific round and the correct answer
	        String answer = gameDao.getGameById(round.getGameId()).getAnswer();

	        //Determines result based of method following
	        String result = getResult(round.getGuess(), answer);

	        //Change the result
	        round.setResult(result);

	        //Change status if guess matches answer
	        if (round.getGuess().equals(answer)) {
	            Game game = getGameById(round.getGameId());
	            game.setGameFinished(true);
	            gameDao.updateGame(game);
	        }

	        return roundDao.addRound(round);
	    }

	    //Update game
	    public void updateGame(Game game) {
	    	gameDao.updateGame(game);
	    }

	    //Validating condition
		private void validateRound(Round round) throws InvalidRoundException {
			//Ensuring it has different number
			 if (Arrays.stream(round.getGuess().split("")).collect(Collectors.toSet()).size() < 4) {
		            throw new InvalidRoundException("Guess should contain 4 different numbers.");
		    }
			 if (round.getGuess().length() != 4){
		            throw new InvalidRoundException("Round object is not valid.");
		        }
		}
}
