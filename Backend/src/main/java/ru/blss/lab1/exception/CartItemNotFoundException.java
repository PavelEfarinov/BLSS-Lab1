package ru.blss.lab1.exception;

public class CartItemNotFoundException extends Exception{
    public CartItemNotFoundException(String message) {
        super(message);
    }

}