package com.myproject.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.dao.RoundDaoDB.RoundMapper;
import com.myproject.entity.Game;
import com.myproject.entity.Round;

@Repository
public class GameDaoDB implements GameDao {

	// turn the database data into a Game object:
	public static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game myGame = new Game();
            myGame.setGameId(rs.getInt("gameId"));
            myGame.setAnswer(rs.getString("answer"));
            myGame.setGameFinished(rs.getBoolean("isGameFinished"));
            return myGame;
        }
    }

	@Autowired
    JdbcTemplate jdbc;

	@Transactional
    @Override
    public Game addGame(Game game) {
        final String ADD_GAME = "INSERT INTO game (answer, isGameFinished) VALUES(?, ?)";
        jdbc.update(ADD_GAME,
                game.getAnswer(),
                game.isGameFinished());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        game.setGameId(newId);
        return game;
    }

    @Transactional
    @Override
    public void deleteGameById(int id) {

        final String DELETE_GAME = "DELETE FROM game WHERE gameId = ?";
        jdbc.update(DELETE_GAME, id);

    }

    @Override
    public List<Game> getAllGames() {
        final String GET_ALL_GAMES = "SELECT * FROM game";
        return jdbc.query(GET_ALL_GAMES, new GameDaoDB.GameMapper());
    }

    @Override
    public Game getGameById(int id) {
        try {
            final String GET_GAME_BY_ID = "SELECT * FROM game WHERE gameId = ?";
            return jdbc.queryForObject(GET_GAME_BY_ID, new GameDaoDB.GameMapper(), id);
        } catch(DataAccessException ex) {
            return null;
        }
    }
    //Get all rounds for game
	@Override
	public List<Round> getRoundsForAGame(int id) {
		  final String SELECT_ROUNDS_BY_GAMEID = "SELECT * FROM round WHERE gameId = ? ORDER BY guessTime";
	        List<Round> rounds = jdbc.query(SELECT_ROUNDS_BY_GAMEID, new RoundMapper(), id);
	        return rounds;
	}


    @Override
    public void updateGame(Game game) {

        final String UPDATE_GAME = "UPDATE game SET answer = ?, isGameFinished = ? WHERE gameId = ?";
        jdbc.update(UPDATE_GAME,
                game.getAnswer(),
                game.isGameFinished(),
                game.getGameId());

    }

    

}
