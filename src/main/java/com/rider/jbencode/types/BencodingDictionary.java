package com.rider.jbencode.types;

import java.util.LinkedHashMap;

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
}
