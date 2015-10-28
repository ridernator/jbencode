package com.rider.jbencode.types;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test of the BencodingString class
 *
 * @author Ciaron Rider
 */
public class BencodingStringTest {
    /**
     * Test of getValue and setValue methods, of class BencodingString.
     */
    @Test
    public void testGetValue() {
        final BencodingString bencodingString = new BencodingString("test");
        Assert.assertEquals("Constructor in \"BencodingString\" does not set \"value\"", "test", bencodingString.getValue());

        bencodingString.setValue("test2");
        Assert.assertEquals("Error in \"getValue\" or \"setValue\" in \"BencodingString\"", "test2", bencodingString.getValue());
    }

    /**
     * Test of copy constructor, of class BencodingString.
     */
    @Test
    public void testCopy() {
        final BencodingString bencodingString = new BencodingString("test");
        BencodingString copyTo = new BencodingString(bencodingString);

        Assert.assertEquals("Copy constructor in \"BencodingString\" does not set \"value\"", "test", copyTo.getValue());

        try {
            copyTo = new BencodingString((BencodingString) null);
            Assert.fail("Passing null to copy constructor of \"BencodingString\" did not throw NullPointerException");
        } catch (final NullPointerException exception) {
            // Expected
        }
    }

    /**
     * Test of equals, of class BencodingString.
     */
    @Test
    public void testEquals() {
        final BencodingString bencodingString = new BencodingString("test");
        final BencodingString bencodingString2 = new BencodingString("test");
        final BencodingString bencodingString3 = new BencodingString("test2");

        Assert.assertEquals("Error in \"equals\" method of \"BencodingString\"", bencodingString, bencodingString2);
        Assert.assertNotSame("Error in \"equals\" method of \"BencodingString\"", bencodingString, bencodingString3);
    }
}
