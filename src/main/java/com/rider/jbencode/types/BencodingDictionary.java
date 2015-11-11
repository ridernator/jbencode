package com.rider.jbencode.types;

import com.rider.jbencode.exceptions.BencodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Class representing a bencoded dictionary (Extends HashMap)
 *
 * @author Ciaron Rider
 */
public class BencodingDictionary extends LinkedHashMap<String, BencodingElement> implements BencodingElement {
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        for (final String string : this.keySet()) {
            builder.append(string).append(" : ").append(get(string)).append('\n');
        }

        return builder.toString();
    }

    /**
     * Create a new instance of a BencodingDictionary from a HashMap of Objects. The
     * Objects in the HashMap must be capable of being converted to Bencoded
     * objects
     *
     * @param value The HashMap of objects to create the BencodingDictionary from
     * @return The newly created BencodingDictionary
     * @throws BencodingException If there is a problem converting one of the
     * elements in the HashMap to a BencodingDictionary
     */
    public static BencodingDictionary create(final HashMap<String, Object> value) throws BencodingException {
        final BencodingDictionary returnVal = new BencodingDictionary();
        Object object;        

        for (final String key : value.keySet()) {
            object = value.get(key);
            
            if (object == null) {
                throw new BencodingException("Unable to convert null to a BencodingElement");
            } else {
                if (object instanceof String) {
                    returnVal.put(key, BencodingString.create((String) object));
                } else if (object instanceof Long) {
                    returnVal.put(key, BencodingNumber.create((Long) object));
                } else if (object instanceof List) {
                    returnVal.put(key, BencodingList.create((List) object));
                } else if (object instanceof HashMap) {
                    returnVal.put(key, BencodingDictionary.create((HashMap) object));
                } else {
                    throw new BencodingException("Unable to convert object of type \"" + object.getClass().getName() + "\" to a BencodingElement");
                }
            }
        }

        return returnVal;
    }
}
