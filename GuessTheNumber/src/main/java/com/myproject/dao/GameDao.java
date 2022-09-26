package com.myproject.dao;

import java.util.List;

import com.myproject.entity.Game;
import com.myproject.entity.Round;

public interface GameDao {

	 	Game addGame(Game game);
	    void deleteGameById(int id);
	    List<Game> getAllGames();
	    Game getGameById(int id);
	    List<Round> getRoundsForAGame(int id);
	    void updateGame(Game game);


}
