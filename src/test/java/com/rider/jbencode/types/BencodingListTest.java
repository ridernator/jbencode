package com.rider.jbencode.types;

import com.rider.jbencode.exceptions.BencodingException;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test of the BencodingList class.
 *
 * @author Ciaron Rider
 */
public class BencodingListTest {
    /**
     * Test of constructor, of class BencodingList.
     */
    @Test
    public void testConstructor() {
        final ArrayList<Object> subObjects = new ArrayList<>();
        subObjects.add("subTest");
        subObjects.add(123);

        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("a", "dictA");
        hashMap.put("b", 456);

        final ArrayList<Object> objects = new ArrayList<>();
        objects.add("test");
        objects.add((long) 1);
        objects.add((int) 2);
        objects.add((short) 3);
        objects.add((byte) 4);
        objects.add(subObjects);
        objects.add(hashMap);

        try {
            final BencodingList bencodingList = new BencodingList(objects);

            Assert.assertTrue("Error in \"constructor\" of \"BencodingList\"", bencodingList.get(0) instanceof BencodingString);
            Assert.assertEquals("Error in \"constructor\" of \"BencodingList\"", "test", ((BencodingString) bencodingList.get(0)).getValue());

            Assert.assertTrue("Error in \"constructor\" of \"BencodingList\"", bencodingList.get(1) instanceof BencodingNumber);
            Assert.assertEquals("Error in \"constructor\" of \"BencodingList\"", 1, ((BencodingNumber) bencodingList.get(1)).getValue());

            Assert.assertTrue("Error in \"constructor\" of \"BencodingList\"", bencodingList.get(2) instanceof BencodingNumber);
            Assert.assertEquals("Error in \"constructor\" of \"BencodingList\"", 2, ((BencodingNumber) bencodingList.get(2)).getValue());

            Assert.assertTrue("Error in \"constructor\" of \"BencodingList\"", bencodingList.get(3) instanceof BencodingNumber);
            Assert.assertEquals("Error in \"constructor\" of \"BencodingList\"", 3, ((BencodingNumber) bencodingList.get(3)).getValue());

            Assert.assertTrue("Error in \"constructor\" of \"BencodingList\"", bencodingList.get(4) instanceof BencodingNumber);
            Assert.assertEquals("Error in \"constructor\" of \"BencodingList\"", 4, ((BencodingNumber) bencodingList.get(4)).getValue());

            Assert.assertTrue("Error in \"constructor\" of \"BencodingList\"", bencodingList.get(5) instanceof BencodingList);

            Assert.assertEquals("Error in \"constructor\" of \"BencodingList\"", 2, ((BencodingList) bencodingList.get(5)).size());

            Assert.assertTrue("Error in \"constructor\" of \"BencodingList\"", ((BencodingList) bencodingList.get(5)).get(0) instanceof BencodingString);
            Assert.assertEquals("Error in \"constructor\" of \"BencodingList\"", "subTest", ((BencodingString) ((BencodingList) bencodingList.get(5)).get(0)).getValue());

            Assert.assertTrue("Error in \"constructor\" of \"BencodingList\"", ((BencodingList) bencodingList.get(5)).get(1) instanceof BencodingNumber);
            Assert.assertEquals("Error in \"constructor\" of \"BencodingList\"", 123, ((BencodingNumber) ((BencodingList) bencodingList.get(5)).get(1)).getValue());

            Assert.assertTrue("Error in \"constructor\" of \"BencodingList\"", bencodingList.get(6) instanceof BencodingDictionary);

            Assert.assertEquals("Error in \"constructor\" of \"BencodingList\"", 2, ((BencodingDictionary) bencodingList.get(6)).size());

            Assert.assertTrue("Error in \"constructor\" of \"BencodingList\"", ((BencodingDictionary) bencodingList.get(6)).get("a") instanceof BencodingString);
            Assert.assertEquals("Error in \"constructor\" of \"BencodingList\"", "dictA", ((BencodingString) ((BencodingDictionary) bencodingList.get(6)).get("a")).getValue());

            Assert.assertTrue("Error in \"constructor\" of \"BencodingList\"", ((BencodingDictionary) bencodingList.get(6)).get("b") instanceof BencodingNumber);
            Assert.assertEquals("Error in \"constructor\" of \"BencodingList\"", 456, ((BencodingNumber) ((BencodingDictionary) bencodingList.get(6)).get("b")).getValue());
        } catch (final BencodingException exception) {
            Assert.fail("\"constructor\" of \"BencodingList\" threw an exception : " + exception.getMessage());
        }
    }
}
