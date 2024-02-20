package controllers;

import java.util.Date;

public class dbOp {
	public static Date date = new Date();
    public static java.sql.Date sqlDate = new java.sql.Date(date.getTime());
    
    public static final String login = "SELECT * FROM users WHERE username = ? AND password = ?";
    public static final String register = "INSERT INTO users (username, password) VALUES (?, ?)";
    public static final String override = "SELECT * FROM operations WHERE override = ?";
    
    
    public static final String getBook = "SELECT * FROM books (bookID, title, year, autor, lastTime, path, bookImage) VALUES (?,?,?,?,?,?,?)";
    public static final String insertBook = "INSERT INTO books (bookID, title, year, autor, lastTime, path, bookImage)";
    public static final String deleteBook = "DELETE ";
    
}
