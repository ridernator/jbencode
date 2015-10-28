package com.rider.jbencode.types;

import com.rider.jbencode.Bencoder;
import com.rider.jbencode.exceptions.BencodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test of the Bencoder class
 *
 * @author Ciaron Rider
 */
public class BencoderTest {
    /**
     * Test of read method of class Bencoder.
     */
    @Test
    public void testRead() {
        final Bencoder bencoder = new Bencoder();
        List<BencodingElement> elements;

        try {
            elements = bencoder.read("i1e");
            Assert.assertNotNull("Bencoder returned null when trying to parse a number", elements);
            Assert.assertEquals("Bencoder returned an wrongly sized list when trying to parse a number", 1, elements.size());
            Assert.assertTrue("Bencoder returned something which was not a BencodingNumber when trying to parse a number", elements.get(0) instanceof BencodingNumber);
            Assert.assertEquals("Bencoder failed to parse a number", 1, ((BencodingNumber) elements.get(0)).getValue());
        } catch (final BencodingException exception) {
            Assert.fail("Bencoder threw an exception when trying to parse a number");
        }

        try {
            elements = bencoder.read("4:test0:");
            Assert.assertNotNull("Bencoder returned null when trying to parse a string", elements);
            Assert.assertEquals("Bencoder returned an wrongly sized list when trying to parse a string", 2, elements.size());
            Assert.assertTrue("Bencoder returned something which was not a BencodingNumber when trying to parse a string", elements.get(0) instanceof BencodingString);
            Assert.assertTrue("Bencoder returned something which was not a BencodingNumber when trying to parse a string", elements.get(1) instanceof BencodingString);
            Assert.assertEquals("Bencoder failed to parse a string", "test", ((BencodingString) elements.get(0)).getValue());
            Assert.assertEquals("Bencoder failed to parse a string", "", ((BencodingString) elements.get(1)).getValue());
        } catch (final BencodingException exception) {
            Assert.fail("Bencoder threw an exception when trying to parse a string");
        }

        try {
            elements = bencoder.read("li1e4:teste");
            Assert.assertNotNull("Bencoder returned null when trying to parse a list", elements);
            Assert.assertEquals("Bencoder returned an wrongly sized list when trying to parse a list", 1, elements.size());
            Assert.assertTrue("Bencoder returned something which was not a BencodingList when trying to parse a list", elements.get(0) instanceof BencodingList);
            Assert.assertEquals("Bencoder failed to parse a list", 1, ((BencodingNumber) ((List<BencodingElement>) elements.get(0)).get(0)).getValue());
            Assert.assertEquals("Bencoder failed to parse a list", "test", ((BencodingString) ((List<BencodingElement>) elements.get(0)).get(1)).getValue());
        } catch (final BencodingException exception) {
            Assert.fail("Bencoder threw an exception when trying to parse a list");
        }

        try {
            elements = bencoder.read("d4:val1i1e4:val24:teste");
            Assert.assertNotNull("Bencoder returned null when trying to parse a dictionary", elements);
            Assert.assertEquals("Bencoder returned an wrongly sized list when trying to parse a dictionary", 1, elements.size());
            Assert.assertTrue("Bencoder returned something which was not a BencodingList when trying to parse a dictionary", elements.get(0) instanceof BencodingDictionary);
            Assert.assertEquals("Bencoder failed to parse a dictionary", 1, ((BencodingNumber) ((Map<String, BencodingElement>) elements.get(0)).get("val1")).getValue());
            Assert.assertEquals("Bencoder failed to parse a dictionary", "test", ((BencodingString) ((Map<String, BencodingElement>) elements.get(0)).get("val2")).getValue());
        } catch (final BencodingException exception) {
            Assert.fail("Bencoder threw an exception when trying to parse a dictionary");
        }

        try {
            elements = bencoder.read("i0ei1ei-1ei" + Long.MAX_VALUE + "ei" + Long.MIN_VALUE + "e");
            Assert.assertNotNull("Bencoder returned null when trying to parse a number", elements);
            Assert.assertEquals("Bencoder returned an wrongly sized list when trying to parse a number", 5, elements.size());
            Assert.assertTrue("Bencoder returned something which was not a BencodingNumber when trying to parse a number", elements.get(0) instanceof BencodingNumber);
            Assert.assertTrue("Bencoder returned something which was not a BencodingNumber when trying to parse a number", elements.get(1) instanceof BencodingNumber);
            Assert.assertTrue("Bencoder returned something which was not a BencodingNumber when trying to parse a number", elements.get(2) instanceof BencodingNumber);
            Assert.assertTrue("Bencoder returned something which was not a BencodingNumber when trying to parse a number", elements.get(3) instanceof BencodingNumber);
            Assert.assertTrue("Bencoder returned something which was not a BencodingNumber when trying to parse a number", elements.get(4) instanceof BencodingNumber);
            Assert.assertEquals("Bencoder failed to parse a number", 0, ((BencodingNumber) elements.get(0)).getValue());
            Assert.assertEquals("Bencoder failed to parse a number", 1, ((BencodingNumber) elements.get(1)).getValue());
            Assert.assertEquals("Bencoder failed to parse a number", -1, ((BencodingNumber) elements.get(2)).getValue());
            Assert.assertEquals("Bencoder failed to parse a number", Long.MAX_VALUE, ((BencodingNumber) elements.get(3)).getValue());
            Assert.assertEquals("Bencoder failed to parse a number", Long.MIN_VALUE, ((BencodingNumber) elements.get(4)).getValue());
        } catch (final BencodingException exception) {
            Assert.fail("Bencoder threw an exception when trying to parse a number");
        }

        try {
            elements = bencoder.read("lllli1eeeee");
            Assert.assertNotNull("Bencoder returned null when trying to parse a list", elements);
            Assert.assertEquals("Bencoder returned an wrongly sized list when trying to parse a list", 1, elements.size());
            Assert.assertTrue("Bencoder returned something which was not a BencodingNumber when trying to parse a list", elements.get(0) instanceof BencodingList);
            Assert.assertEquals("Bencoder failed to parse a list", 1, ((BencodingNumber) ((List<BencodingElement>) ((List<BencodingElement>) ((List<BencodingElement>) ((List<BencodingElement>) elements.get(0)).get(0)).get(0)).get(0)).get(0)).getValue());
        } catch (final BencodingException exception) {
            Assert.fail("Bencoder threw an exception when trying to parse a list");
        }

        try {
            elements = bencoder.read("d4:val1d4:val2d4:val3d4:val4i1eeeee");
            Assert.assertNotNull("Bencoder returned null when trying to parse a dictionary", elements);
            Assert.assertEquals("Bencoder returned an wrongly sized list when trying to parse a dictionary", 1, elements.size());
            Assert.assertTrue("Bencoder returned something which was not a BencodingNumber when trying to parse a dictionary", elements.get(0) instanceof BencodingDictionary);
            Assert.assertEquals("Bencoder failed to parse a dictionary", 1, ((BencodingNumber) ((Map<String, BencodingElement>) ((Map<String, BencodingElement>) ((Map<String, BencodingElement>) ((Map<String, BencodingElement>) elements.get(0)).get("val1")).get("val2")).get("val3")).get("val4")).getValue());
        } catch (final BencodingException exception) {
            Assert.fail("Bencoder threw an exception when trying to parse a dictionary");
        }

        String testString = "i";

        try {
            bencoder.read(testString);
            Assert.fail("Bencoder didn't throw an exception when parsing \"" + testString + "\"");
        } catch (final BencodingException exception) {
            // Do nothing
        }

        testString = "ii";

        try {
            bencoder.read(testString);
            Assert.fail("Bencoder didn't throw an exception when parsing \"" + testString + "\"");
        } catch (final BencodingException exception) {
            // Do nothing
        }

        testString = "a";

        try {
            bencoder.read(testString);
            Assert.fail("Bencoder didn't throw an exception when parsing \"" + testString + "\"");
        } catch (final BencodingException exception) {
            // Do nothing
        }

        testString = "i--e";

        try {
            bencoder.read(testString);
            Assert.fail("Bencoder didn't throw an exception when parsing \"" + testString + "\"");
        } catch (final BencodingException exception) {
            // Do nothing
        }

        testString = "";

        try {
            elements = bencoder.read(testString);
            Assert.assertNotNull("Bencoder returned null when trying to parse an empty string", elements);
            Assert.assertTrue("Bencoder didn't return an empty list when parsing \"" + testString + "\"", elements.isEmpty());
        } catch (final BencodingException exception) {
            Assert.fail("Bencoder threw an exception when parsing \"" + testString + "\"");
        }
    }

    /**
     * Test of write method of class Bencoder.
     */
    @Test
    public void testWrite() {
        final Bencoder bencoder = new Bencoder();
        List<BencodingElement> elements = new ArrayList<>();
        String stringVal;

        elements.add(new BencodingNumber(1));
        stringVal = bencoder.write(elements);

        Assert.assertNotNull("Bencoder returned null when trying to write a number", stringVal);
        Assert.assertEquals("Bencoder failed to write a number", "i1e", stringVal);

        elements.clear();
        elements.add(new BencodingString("test"));
        elements.add(new BencodingString(""));
        stringVal = bencoder.write(elements);

        Assert.assertNotNull("Bencoder returned null when trying to write a string", stringVal);
        Assert.assertEquals("Bencoder failed to write a number", "4:test0:", stringVal);

        elements.clear();
        elements.add(new BencodingList());
        ((Collection<BencodingElement>) elements.get(0)).add(new BencodingNumber(1));
        ((Collection<BencodingElement>) elements.get(0)).add(new BencodingString("test"));
        stringVal = bencoder.write(elements);

        Assert.assertNotNull("Bencoder returned null when trying to write a list", stringVal);
        Assert.assertEquals("Bencoder failed to write a list", "li1e4:teste", stringVal);

        elements.clear();
        elements.add(new BencodingDictionary());
        ((Map<String, BencodingElement>) elements.get(0)).put("val1", new BencodingNumber(1));
        ((Map<String, BencodingElement>) elements.get(0)).put("val2", new BencodingString("test"));
        stringVal = bencoder.write(elements);

        Assert.assertNotNull("Bencoder returned null when trying to write a dictionary", stringVal);
        Assert.assertEquals("Bencoder failed to write a dictionary", "d4:val1i1e4:val24:teste", stringVal);

        elements.clear();
        elements.add(new BencodingNumber(0));
        elements.add(new BencodingNumber(1));
        elements.add(new BencodingNumber(-1));
        elements.add(new BencodingNumber(Long.MAX_VALUE));
        elements.add(new BencodingNumber(Long.MIN_VALUE));
        stringVal = bencoder.write(elements);

        Assert.assertNotNull("Bencoder returned null when trying to write a number", stringVal);
        Assert.assertEquals("Bencoder failed to write a number", "i0ei1ei-1ei9223372036854775807ei-9223372036854775808e", stringVal);

        elements.clear();
        elements.add(new BencodingList());
        ((Collection<BencodingElement>) elements.get(0)).add(new BencodingList());
        ((Collection<BencodingElement>) ((List<BencodingElement>) elements.get(0)).get(0)).add(new BencodingList());
        ((Collection<BencodingElement>) ((List<BencodingElement>) ((List<BencodingElement>) elements.get(0)).get(0)).get(0)).add(new BencodingList());
        ((Collection<BencodingElement>) ((List<BencodingElement>) ((List<BencodingElement>) ((List<BencodingElement>) elements.get(0)).get(0)).get(0)).get(0)).add(new BencodingNumber(1));
        stringVal = bencoder.write(elements);

        Assert.assertNotNull("Bencoder returned null when trying to write a list", stringVal);
        Assert.assertEquals("Bencoder failed to write a list", "lllli1eeeee", stringVal);

        elements.clear();
        elements.add(new BencodingDictionary());
        ((Map<String, BencodingElement>) elements.get(0)).put("val1", new BencodingDictionary());
        ((Map<String, BencodingElement>) ((Map<String, BencodingElement>) elements.get(0)).get("val1")).put("val2", new BencodingDictionary());
        ((Map<String, BencodingElement>) ((Map<String, BencodingElement>) ((Map<String, BencodingElement>) elements.get(0)).get("val1")).get("val2")).put("val3", new BencodingDictionary());
        ((Map<String, BencodingElement>) ((Map<String, BencodingElement>) ((Map<String, BencodingElement>) ((Map<String, BencodingElement>) elements.get(0)).get("val1")).get("val2")).get("val3")).put("val4", new BencodingNumber(1));
        stringVal = bencoder.write(elements);

        Assert.assertNotNull("Bencoder returned null when trying to write a dictionary", stringVal);
        Assert.assertEquals("Bencoder failed to write a dictionary", "d4:val1d4:val2d4:val3d4:val4i1eeeee", stringVal);

        elements.clear();
        elements.add(new BencodingString((String) null));
        stringVal = bencoder.write(elements);

        Assert.assertNotNull("Bencoder returned null when trying to write a null string", stringVal);
        Assert.assertEquals("Bencoder failed to write a dictionary", "0:", stringVal);
    }
}
