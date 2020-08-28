package com.nhhsgroup.naturalhairhotspot.Exceptions;

public class NhhsException extends RuntimeException {
	public NhhsException(String exMessage, Exception exception) {
		super(exMessage, exception);
	}

	public NhhsException(String exMessage) {
		super(exMessage);
	}
}
