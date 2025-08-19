package com.example.arena.exception;

/**
 * Исключение, для обозначения повторения логина
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
