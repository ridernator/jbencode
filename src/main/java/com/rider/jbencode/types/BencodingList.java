package com.rider.jbencode.types;

import com.rider.jbencode.exceptions.BencodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class representing a bencoded list (Extends ArrayList).
 *
 * @author Ciaron Rider
 */
public class BencodingList extends ArrayList<BencodingElement> implements BencodingElement {
    /**
     * Constructor for this class
     */
    public BencodingList() {
        super();
    }

    /**
     * Constructor which takes a list of Objects. The Objects in the list must
     * be capable of being converted to Bencoded objects.
     *
     * @param values The list of objects to create the BencodingList from.
     * @throws BencodingException If there is a problem converting one of the
     * elements in the list to a BencodingElement.
     */
    public BencodingList(final List<Object> values) throws BencodingException {
        for (final Object object : values) {
            if (object == null) {
                throw new BencodingException("Unable to convert null to a BencodingElement");
            } else {
                if (object instanceof String) {
                    add(new BencodingString((String) object));
                } else if (object instanceof Long) {
                    add(new BencodingNumber((Long) object));
                } else if (object instanceof Integer) {
                    add(new BencodingNumber((Integer) object));
                } else if (object instanceof Short) {
                    add(new BencodingNumber((Short) object));
                } else if (object instanceof Byte) {
                    add(new BencodingNumber((Byte) object));
                } else if (object instanceof List) {
                    add(new BencodingList((List) object));
                } else if (object instanceof HashMap) {
                    add(new BencodingDictionary((HashMap) object));
                } else {
                    throw new BencodingException("Unable to convert object of type \"" + object.getClass().getName() + "\" to a BencodingElement");
                }
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        for (final BencodingElement element : this) {
            builder.append(element).append('\n');
        }

        return builder.toString();
    }
}
