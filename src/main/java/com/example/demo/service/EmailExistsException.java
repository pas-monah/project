package com.example.demo.service;

public class EmailExistsException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public EmailExistsException(String message) {
		super(message);
	}
}
