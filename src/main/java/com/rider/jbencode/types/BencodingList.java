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
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        for (final BencodingElement element : this) {
            builder.append(element).append('\n');
        }

        return builder.toString();
    }

    /**
     * Create a new instance of a BencodingList from a list of Objects. The
     * Objects in the list must be capable of being converted to Bencoded
     * objects.
     *
     * @param value The list of objects to create the BencodingList from.
     * @return The newly created BencodingList.
     * @throws BencodingException If there is a problem converting one of the
     * elements in the list to a BencodingElement.
     */
    public static BencodingList create(final List<Object> value) throws BencodingException {
        final BencodingList returnVal = new BencodingList();

        for (final Object object : value) {
            if (object == null) {
                throw new BencodingException("Unable to convert null to a BencodingElement");
            } else {
                if (object instanceof String) {
                    returnVal.add(BencodingString.create((String) object));
                } else if (object instanceof Long) {
                    returnVal.add(BencodingNumber.create((Long) object));
                } else if (object instanceof List) {
                    returnVal.add(BencodingList.create((List) object));
                } else if (object instanceof HashMap) {
                    returnVal.add(BencodingDictionary.create((HashMap) object));
                } else {
                    throw new BencodingException("Unable to convert object of type \"" + object.getClass().getName() + "\" to a BencodingElement");
                }
            }
        }

        return returnVal;
    }
}
