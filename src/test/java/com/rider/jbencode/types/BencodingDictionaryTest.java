package com.rider.jbencode.types;

import com.rider.jbencode.exceptions.BencodingException;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test of the BencodingDictionary class.
 *
 * @author Ciaron Rider
 */
public class BencodingDictionaryTest {
    /**
     * Test of constructor, of class BencodingDictionary.
     */
    @Test
    public void testConstructor() {
        final ArrayList<Object> subObjects = new ArrayList<>();
        subObjects.add("subTest");
        subObjects.add(123);

        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("a", "dictA");
        hashMap.put("b", 456);

        final HashMap<String, Object> objects = new HashMap<>();
        objects.put("a", "test");
        objects.put("b", (long) 1);
        objects.put("c", (int) 2);
        objects.put("d", (short) 3);
        objects.put("e", (byte) 4);
        objects.put("f", subObjects);
        objects.put("g", hashMap);

        try {
            final BencodingDictionary bencodingDict = new BencodingDictionary(objects);

            Assert.assertTrue("Error in \"constructor\" of \"BencodingDictionary\"", bencodingDict.get("a") instanceof BencodingString);
            Assert.assertEquals("Error in \"constructor\" of \"BencodingDictionary\"", "test", ((BencodingString) bencodingDict.get("a")).getValue());

            Assert.assertTrue("Error in \"constructor\" of \"BencodingDictionary\"", bencodingDict.get("b") instanceof BencodingNumber);
            Assert.assertEquals("Error in \"constructor\" of \"BencodingDictionary\"", 1, ((BencodingNumber) bencodingDict.get("b")).getValue());

            Assert.assertTrue("Error in \"constructor\" of \"BencodingDictionary\"", bencodingDict.get("c") instanceof BencodingNumber);
            Assert.assertEquals("Error in \"constructor\" of \"BencodingDictionary\"", 2, ((BencodingNumber) bencodingDict.get("c")).getValue());

            Assert.assertTrue("Error in \"constructor\" of \"BencodingDictionary\"", bencodingDict.get("d") instanceof BencodingNumber);
            Assert.assertEquals("Error in \"constructor\" of \"BencodingDictionary\"", 3, ((BencodingNumber) bencodingDict.get("d")).getValue());

            Assert.assertTrue("Error in \"constructor\" of \"BencodingDictionary\"", bencodingDict.get("e") instanceof BencodingNumber);
            Assert.assertEquals("Error in \"constructor\" of \"BencodingDictionary\"", 4, ((BencodingNumber) bencodingDict.get("e")).getValue());

            Assert.assertTrue("Error in \"constructor\" of \"BencodingDictionary\"", bencodingDict.get("f") instanceof BencodingList);

            Assert.assertEquals("Error in \"constructor\" of \"BencodingDictionary\"", 2, ((BencodingList) bencodingDict.get("f")).size());

            Assert.assertTrue("Error in \"constructor\" of \"BencodingDictionary\"", ((BencodingList) bencodingDict.get("f")).get(0) instanceof BencodingString);
            Assert.assertEquals("Error in \"constructor\" of \"BencodingDictionary\"", "subTest", ((BencodingString) ((BencodingList) bencodingDict.get("f")).get(0)).getValue());

            Assert.assertTrue("Error in \"constructor\" of \"BencodingDictionary\"", ((BencodingList) bencodingDict.get("f")).get(1) instanceof BencodingNumber);
            Assert.assertEquals("Error in \"constructor\" of \"BencodingDictionary\"", 123, ((BencodingNumber) ((BencodingList) bencodingDict.get("f")).get(1)).getValue());

            Assert.assertTrue("Error in \"constructor\" of \"BencodingDictionary\"", bencodingDict.get("g") instanceof BencodingDictionary);

            Assert.assertEquals("Error in \"constructor\" of \"BencodingDictionary\"", 2, ((BencodingDictionary) bencodingDict.get("g")).size());

            Assert.assertTrue("Error in \"constructor\" of \"BencodingDictionary\"", ((BencodingDictionary) bencodingDict.get("g")).get("a") instanceof BencodingString);
            Assert.assertEquals("Error in \"constructor\" of \"BencodingDictionary\"", "dictA", ((BencodingString) ((BencodingDictionary) bencodingDict.get("g")).get("a")).getValue());

            Assert.assertTrue("Error in \"constructor\" of \"BencodingDictionary\"", ((BencodingDictionary) bencodingDict.get("g")).get("b") instanceof BencodingNumber);
            Assert.assertEquals("Error in \"constructor\" of \"BencodingDictionary\"", 456, ((BencodingNumber) ((BencodingDictionary) bencodingDict.get("g")).get("b")).getValue());
        } catch (final BencodingException exception) {
            Assert.fail("\"constructor\" of \"BencodingList\" threw an exception : " + exception.getMessage());
        }
    }
}
