package com.myproject.dao;

import java.util.List;

import com.myproject.entity.Round;

public interface RoundDao {

	  Round addRound(Round round);
	    void deleteRoundById(int id);
	    List<Round> getAllRounds();
	    Round getRoundById(int id);
	    void updateRound(Round round);
	}