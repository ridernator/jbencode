package com.rider.jbencode.types;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test of the BencodingNumber class
 *
 * @author Ciaron Rider
 */
public class BencodingNumberTest {
    /**
     * Test of getValue and setValue methods, of class BencodingNumber.
     */
    @Test
    public void testGetValue() {
        final BencodingNumber bencodingNumber = new BencodingNumber(1);
        Assert.assertEquals("Constructor in \"BencodingNumber\" does not set \"value\"", 1, bencodingNumber.getValue());

        bencodingNumber.setValue(2);
        Assert.assertEquals("Error in \"getValue\" or \"setValue\" in \"BencodingNumber\"", 2, bencodingNumber.getValue());
    }

    /**
     * Test of copy constructor, of class BencodingNumber.
     */
    @Test
    public void testCopy() {
        final BencodingNumber bencodingNumber = new BencodingNumber(1);
        BencodingNumber copyTo = new BencodingNumber(bencodingNumber);

        Assert.assertEquals("Copy constructor in \"BencodingNumber\" does not set \"value\"", 1, copyTo.getValue());

        try {
            copyTo = new BencodingNumber(null);
            Assert.fail("Passing null to copy constructor of \"BencodingNumber\" did not throw NullPointerException");
        } catch (final NullPointerException exception) {
            // Expected
        }
    }

    /**
     * Test of equals, of class BencodingNumber.
     */
    @Test
    public void testEquals() {
        final BencodingNumber bencodingNumber = new BencodingNumber(1);
        final BencodingNumber bencodingNumber2 = new BencodingNumber(1);
        final BencodingNumber bencodingNumber3 = new BencodingNumber(2);

        Assert.assertEquals("Error in \"equals\" method of \"BencodingNumber\"", bencodingNumber, bencodingNumber2);
        Assert.assertNotSame("Error in \"equals\" method of \"BencodingNumber\"", bencodingNumber, bencodingNumber3);
    }
}
