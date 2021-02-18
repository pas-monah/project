package com.example.demo.service;

public class UsernameExistsException extends RuntimeException{
	private static final long serialVersionUID = 387910964388970478L;

	public UsernameExistsException(String msg) {
		super(msg);
	}
}
