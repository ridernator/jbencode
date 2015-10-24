package com.rider.jbencode.exceptions;

/**
 * An exception to do with encoding / decoding bencoded data
 *
 * @author Ciaron Rider
 */
public class BencodingException extends Throwable {
    /**
     * The error message
     */
    private final String errorMessage;

    /**
     * The constructor for this class
     *
     * @param errorMessage The error message
     */
    public BencodingException(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
