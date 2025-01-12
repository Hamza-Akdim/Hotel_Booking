package org.example.hotelbook.exceptions;

/**
 * @author akdim
 */

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
