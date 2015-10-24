package com.rider.jbencode.types;

import java.util.HashMap;

/**
 * Class representing a bencoded dictionary (Extends HashMap)
 *
 * @author Ciaron Rider
 */
public class BencodingDictionary extends HashMap<BencodingString, BencodingElement> implements BencodingElement {
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        for (final BencodingString string : this.keySet()) {
            builder.append(string).append(" : ").append(get(string)).append('\n');
        }

        return builder.toString();
    }

}
