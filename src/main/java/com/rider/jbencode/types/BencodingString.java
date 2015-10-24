package com.rider.jbencode.types;

/**
 * Class representing a bencoded string
 *
 * @author Ciaron Rider
 */
public final class BencodingString implements BencodingElement {
    /**
     * The string value
     */
    private String value;

    /**
     * Constructor for this class
     */
    public BencodingString() {
        // Do nothing
    }

    /**
     * Constructor for this class
     *
     * @param value The string value to set
     */
    public BencodingString(final String value) {
        setValue(value);
    }

    /**
     * Copy constructor
     *
     * @param bencodingString The object to copy from
     */
    public BencodingString(final BencodingString bencodingString) {
        setValue(bencodingString.getValue());
    }

    /**
     * The the string value
     *
     * @return The string value
     */
    public String getValue() {
        return value;
    }

    /**
     * Set the string value
     *
     * @param value The string value to set
     */
    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(final Object other) {
        if (other == null) {
            return false;
        }

        if (!(other instanceof BencodingString)) {
            return false;
        }

        final BencodingString otherBencodingString = (BencodingString) other;

        if (getValue() == null) {
            if (otherBencodingString.getValue() != null) {
                return false;
            }
        } else {
            if (!getValue().equals(otherBencodingString.getValue())) {
                return false;
            }
        }

        return true;
    }
}
