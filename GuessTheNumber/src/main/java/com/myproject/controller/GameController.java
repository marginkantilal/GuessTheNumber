package com.myproject.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.myproject.entity.Game;
import com.myproject.entity.Round;
import com.myproject.service.GameService;
import com.myproject.service.InvalidRoundException;

@RestController
@RequestMapping("/api")
public class GameController extends ResponseEntityExceptionHandler {

	@Autowired
	 GameService service;

	//"begin" - POST – Starts a game, 
	//generates an answer, and sets the correct status. 
	// Should return a 201 CREATED message as well as the created gameId.
	@PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    //Start game
    public ResponseEntity startGame(){
        // generate Game
        Game game = new Game();
        //Setting random answer
        game.setAnswer(service.generateRandomAnswer());
        game.setGameFinished(false);
        service.addGame(game);
        String message = "New game created: Game # " + game.getGameId();
        return new ResponseEntity(message, HttpStatus.CREATED);
    }

	//GET – Returns a list of all games. Be sure in-progress games do not display their answer.
	@GetMapping("/game")
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = service.getAllGames();
        for (Game game : games) {
            if (!game.isGameFinished()) {
                game.setAnswer("Game still in-progress. Finish the game to see answer.");
            }
        }
        return ResponseEntity.ok(games); //Returns status 200 OK
    }

    //@PathVariable  tells Spring MVC to find the parameter in the URL
    //"game/{gameId}" - GET – Returns a specific game based on ID. Be sure in-progress games do not display their answer.
    @GetMapping("/game/{id}")
    public ResponseEntity getGame(@PathVariable int id) {
    	 Game game = service.getGameById(id);
         if (!(game == null )) {
	         service.getGameById(id);

        	 if(game.isGameFinished()) {
                 game.setAnswer("Game still in progress. Finish the game to see answer.");
        	 }

        	 else{
        		 return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        	 }
         }
			return ResponseEntity.ok(game);
         }
    
    
    //"rounds/{gameId} – GET – Returns a list of rounds for the specified game sorted by time.
    @GetMapping("/rounds/{gameId}")
    public ResponseEntity<List<Round>> getAllRoundsForAGame(@PathVariable int gameId) {
        Game game = service.getGameById(gameId);
        if (!(game == null)) {
        	 List<Round> numberOfRounds = service.getAllRounds();
        	 
        	 //Filters time
             List<Round> gameRounds = numberOfRounds.stream().filter(x -> x.getGameId() == gameId).sorted(
                     Comparator.comparing(Round::getGuessTime)).collect(Collectors.toList());
             return ResponseEntity.ok(gameRounds);
        }
        else {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);

        }

    }



    //"guess" – POST – Makes a guess by passing the guess and gameId in as JSON. 
    // The program must calculate the results of the guess and mark the game finished if the guess is correct. 
    // It returns the Round object with the results filled in.
    @PostMapping("/guess") //api/guess
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Round> guess(@RequestBody Round round) throws InvalidRoundException { //@RequestBody tells Spring MVC to expect the data fully serialized in the HTTP request body
        // takes guess and gameId.
        String message = "Guess :" + round.getGuess() + " for Game: " + round.getGameId();
        round.setGuessTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        Game game = service.getGameById(round.getGameId());
        if (game == null) {
            return new ResponseEntity("Game not found.", HttpStatus.NOT_FOUND);
        }

        if (game.isGameFinished()) {
            return new ResponseEntity("You cannot guess for a completed game.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        String result = service.getResult(game.getAnswer(), round.getGuess());
        round.setResult(result);
        round.parseResult();
     // if guess is correct, finish the game.
        if (round.getNumberOfMatch() == 4) {
            game.setGameFinished(true);
            // Then update game in storage.
            service.updateGame(game);
        }

        service.addRound(round);
        return ResponseEntity.ok(round);

    }

}
