package com.rider.jbencode.types;

/**
 * Class representing a bencoded number
 *
 * @author Ciaron Rider
 */
public final class BencodingNumber implements BencodingElement {
    /**
     * The long value (long to allows large numbers)
     */
    private long value;

    /**
     * Constructor for this class
     */
    public BencodingNumber() {
        // Do nothing
    }

    /**
     * Constructor for this class
     *
     * @param value The long value to set
     */
    public BencodingNumber(final long value) {
        setValue(value);
    }

    /**
     * Copy constructor
     *
     * @param bencodingNumber The object to copy from
     */
    public BencodingNumber(final BencodingNumber bencodingNumber) {
        setValue(bencodingNumber.getValue());
    }

    /**
     * Get the long value
     *
     * @return The long value
     */
    public long getValue() {
        return value;
    }

    /**
     * Set the long value
     *
     * @param value The long value to set
     */
    public void setValue(final long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(final Object other) {
        if (other == null) {
            return false;
        }

        if (!(other instanceof BencodingNumber)) {
            return false;
        }

        return (getValue() == ((BencodingNumber) other).getValue());
    }
}
