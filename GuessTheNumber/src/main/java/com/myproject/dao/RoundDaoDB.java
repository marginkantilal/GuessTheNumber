package com.myproject.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.entity.Round;

@Repository
public class RoundDaoDB  implements RoundDao{

	// turn the database data into a Game object:
	public static final class RoundMapper implements RowMapper<Round> {

	    @Override
	    public Round mapRow(ResultSet rs, int index) throws SQLException {
	    	Round round = new Round();
	    	round.setRoundId(rs.getInt("roundId"));
	    	round.setGuess(rs.getString("guess"));
	    	round.setGuessTime(rs.getTimestamp("guessTime").toLocalDateTime());
	        round.setResult(rs.getString("result"));
	        
	     // parse the result to integers.
            round.parseResult();
	        round.setGameId(rs.getInt("gameId"));

	        
	        return round;

	    }
	}


	@Autowired
		JdbcTemplate jdbc;

	@Override
    @Transactional
	public Round addRound(Round round) {

	    final String INSERT_ROUND = "INSERT INTO round(guess, guessTime, result, gameId) VALUES(?, ?, ?, ?)";
        jdbc.update(INSERT_ROUND,
                round.getGuess(),
                round.getGuessTime(),
                round.getResult(),
                round.getGameId());
        		round.parseResult();


        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setRoundId(newId);
        return round;

	}

	@Transactional
	@Override
	public void deleteRoundById(int id) {

    	final String DELETE_ROUND = "DELETE FROM round WHERE roundId = ? ";
        jdbc.update(DELETE_ROUND, id);

	}

	@Override
	public List<Round> getAllRounds() {
		 final String SELECT_ALL_ROUNDS = "SELECT * FROM round";
	        List<Round> rounds = jdbc.query(SELECT_ALL_ROUNDS, new RoundMapper());
	        return rounds;
}
    @Override
	public Round getRoundById(int id) {

		 try {
	            final String SELECT_ROUND_BY_ID = "SELECT * FROM round WHERE roundId = ?";
	            Round round = jdbc.queryForObject(SELECT_ROUND_BY_ID,
	                    new RoundMapper(), id);
	            return round;
	        } catch(DataAccessException ex) {
	            return null;
	        }
	}
    @Transactional
	@Override
	public void updateRound(Round round) {

    	 final String UPDATE_ROUND = "UPDATE round SET guess =?, guessTime =?, result = ?, gameId =?  where roundId = ?";
         jdbc.update(UPDATE_ROUND,
                 round.getGuess(),
                 Timestamp.valueOf(round.getGuessTime()),
                 round.getResult(),
                 round.getGameId(),
                 round.getRoundId());
         
    }

}
