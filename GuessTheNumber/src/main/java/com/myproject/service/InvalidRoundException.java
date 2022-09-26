package com.myproject.service;

public class InvalidRoundException extends Exception {

	 public InvalidRoundException(String message) {
	        super(message);
	    }

	    public InvalidRoundException(String message, Throwable cause) {
	        super(message, cause);
	    }
}
