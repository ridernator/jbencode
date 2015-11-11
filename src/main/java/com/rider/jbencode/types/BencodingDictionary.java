package com.rider.jbencode.types;

import com.rider.jbencode.exceptions.BencodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Class representing a bencoded dictionary (Extends HashMap).
 *
 * @author Ciaron Rider
 */
public class BencodingDictionary extends LinkedHashMap<String, BencodingElement> implements BencodingElement {
    /**
     * Constructor for this class
     */
    public BencodingDictionary() {
        super();
    }

    /**
     * Constructor which takes a HashMap of Objects. The Objects in the HashMap
     * must be capable of being converted to Bencoded objects.
     *
     * @param values The HashMap of objects to create the BencodingDictionary
     * from.
     * @throws BencodingException If there is a problem converting one of the
     * elements in the HashMap to a BencodingDictionary.
     */
    public BencodingDictionary(final HashMap<String, Object> values) throws BencodingException {
        Object object;

        for (final String key : values.keySet()) {
            object = values.get(key);

            if (object == null) {
                throw new BencodingException("Unable to convert null to a BencodingElement");
            } else {
                if (object instanceof String) {
                    put(key, new BencodingString((String) object));
                } else if (object instanceof Long) {
                    put(key, new BencodingNumber((Long) object));
                } else if (object instanceof Integer) {
                    put(key, new BencodingNumber((Integer) object));
                } else if (object instanceof Short) {
                    put(key, new BencodingNumber((Short) object));
                } else if (object instanceof Byte) {
                    put(key, new BencodingNumber((Byte) object));
                } else if (object instanceof List) {
                    put(key, new BencodingList((List) object));
                } else if (object instanceof HashMap) {
                    put(key, new BencodingDictionary((HashMap) object));
                } else {
                    throw new BencodingException("Unable to convert object of type \"" + object.getClass().getName() + "\" to a BencodingElement");
                }
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        for (final String string : this.keySet()) {
            builder.append(string).append(" : ").append(get(string)).append('\n');
        }

        return builder.toString();
    }
}
