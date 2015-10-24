package com.rider.jbencode.types;

import java.util.ArrayList;

/**
 * Class representing a bencoded list (Extends ArrayList)
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
}
