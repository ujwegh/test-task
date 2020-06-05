package ru.nik.testtask.exceptions;

/**
 * @author nik_aleks
 * @version 1.0
 */

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
