package ru.nik.testtask.exceptions;

/**
 * @author nik_aleks
 * @version 1.0
 */

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
