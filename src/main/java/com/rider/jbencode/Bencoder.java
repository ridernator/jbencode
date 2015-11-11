package com.rider.jbencode;

import com.rider.jbencode.exceptions.BencodingException;
import com.rider.jbencode.types.BencodingDictionary;
import com.rider.jbencode.types.BencodingElement;
import com.rider.jbencode.types.BencodingList;
import com.rider.jbencode.types.BencodingNumber;
import com.rider.jbencode.types.BencodingString;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for parsing bencoding strings and files and also creating them.
 *
 * @author Ciaron Rider
 */
public class Bencoder {
    /**
     * Start tag for a bencoded number.
     */
    private static final char START_NUMBER = 'i';

    /**
     * Start tag for a bencoded list.
     */
    private static final char START_LIST = 'l';

    /**
     * Start tag for a bencoded dictionary.
     */
    private static final char START_DICTIONARY = 'd';

    /**
     * End tag for bencoded numbers, lists and dictionaries.
     */
    private static final char TERMINATION_CHARACTER = 'e';

    /**
     * Constructor for this class.
     */
    public Bencoder() {
        super();
    }

    /**
     * Read bencoded data from a string and output a list of bencoded elements.
     *
     * @param string The string to parse.
     * @return A list of bencoded elements.
     * @throws BencodingException If there is a problem parsing the string's
     * contents.
     */
    public List<BencodingElement> read(final String string) throws BencodingException {
        return read(CharBuffer.wrap(string));
    }

    /**
     * Read bencoded data from a file and output a list of bencoded elements.
     *
     * @param file The file to parse.
     * @return A list of bencoded elements.
     * @throws FileNotFoundException If the file cannot be found.
     * @throws IOException If there is a problem reading of closing the file.
     * @throws BencodingException If there is a problem parsing the file's
     * contents.
     */
    public List<BencodingElement> read(final File file) throws FileNotFoundException, IOException, BencodingException {
        // Allocate a big enough buffer
        final CharBuffer buffer = CharBuffer.allocate((int) file.length());
        final FileReader reader = new FileReader(file);

        // Read the entire file and rewind the buffer
        reader.read(buffer);
        reader.close();
        buffer.rewind();

        return read(buffer);
    }

    /**
     * Read bencoded data from a CharBuffer and output a list of bencoded
     * elements.
     *
     * @param buffer The CharBuffer to parse.
     * @return A list of bencoded elements.
     * @throws BencodingException If there is a problem parsing the CharBuffer's
     * contents.
     */
    private List<BencodingElement> read(final CharBuffer buffer) throws BencodingException {
        final List<BencodingElement> returnVal = new ArrayList<>();

        // While there is still characters in the buffer : read elements
        while (buffer.hasRemaining()) {
            returnVal.add(readElement(buffer));
        }

        // Delete the last element in the list if it is null. This happends if
        // there is trailing whitespace in the file
        if ((!returnVal.isEmpty()) && (returnVal.get(returnVal.size() - 1) == null)) {
            returnVal.remove(returnVal.size() - 1);
        }

        return returnVal;
    }

    /**
     * Write a list of elements to a string as bencoded data.
     *
     * @param elements The elements to write.
     * @return The bencoded representation of the data.
     */
    public String write(final List<BencodingElement> elements) {
        final StringBuilder builder = new StringBuilder();

        // Write out each element in the list
        for (final BencodingElement element : elements) {
            writeElement(element, builder);
        }

        return builder.toString();
    }

    /**
     * Write a list of elements to a file as bencoded data.
     *
     * @param elements The elements to write.
     * @param file The file to output to.
     * @throws IOException If there is a problem finding the file or closing it.
     */
    public void write(final List<BencodingElement> elements,
                      final File file) throws IOException {
        final BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        // Write to a string, then write that string to file
        writer.write(write(elements));
        writer.close();
    }

    /**
     * Read a bencoded element from a CharBuffer.
     *
     * @param buffer The buffer to read from.
     * @return The bencoded element or null if none was found.
     * @throws BencodingException If there is a problem parsing the CharBuffer's
     * contents.
     */
    private BencodingElement readElement(final CharBuffer buffer) throws BencodingException {
        BencodingElement element = null;

        // Peek at the next character
        switch (peekSingleCharacter(buffer)) {
            case START_NUMBER: {
                element = readNumberElement(buffer);

                break;
            }

            // A number means a string is coming
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9': {
                element = readStringElement(buffer);

                break;
            }

            case START_LIST: {
                element = readListElement(buffer);

                break;
            }

            case START_DICTIONARY: {
                element = readDictionaryElement(buffer);

                break;
            }

            default: {
                throw new BencodingException("Error parsing bencoded data at index \"" + buffer.position() + '\"');
            }
        }

        return element;
    }

    /**
     * Write an element to a StringBuilder as bencoded data.
     *
     * @param element The element to write.
     * @param builder The StringBuilder to write to.
     */
    private void writeElement(final BencodingElement element,
                              final StringBuilder builder) {
        // Call a different method depending on the type of element
        if (element instanceof BencodingNumber) {
            writeNumberElement((BencodingNumber) element, builder);
        } else if (element instanceof BencodingString) {
            writeStringElement((BencodingString) element, builder);
        } else if (element instanceof BencodingList) {
            writeListElement((BencodingList) element, builder);
        } else if (element instanceof BencodingDictionary) {
            writeDictionaryElement((BencodingDictionary) element, builder);
        }
    }

    /**
     * Read a bencoded number element from a CharBuffer.
     *
     * @param buffer The buffer to read from.
     * @return The bencoded number element or null if none was found.
     * @throws BencodingException If there is a problem parsing the CharBuffer's
     * contents.
     */
    private BencodingNumber readNumberElement(final CharBuffer buffer) throws BencodingException {
        final BencodingNumber returnVal;

        readSingleCharacter(buffer); // Read 'i'

        returnVal = new BencodingNumber(readNumber(buffer)); // Read number

        readSingleCharacter(buffer); // Read 'e'

        return returnVal;
    }

    /**
     * Read a bencoded string element from a CharBuffer.
     *
     * @param buffer The buffer to read from.
     * @return The bencoded string element or null if none was found.
     * @throws BencodingException If there is a problem parsing the CharBuffer's
     * contents.
     */
    private BencodingString readStringElement(final CharBuffer buffer) throws BencodingException {
        final long stringLength = readNumber(buffer); // Read size

        readSingleCharacter(buffer); // Read ':'

        return new BencodingString(readString(buffer, stringLength)); // Read string
    }

    /**
     * Read a bencoded list element from a CharBuffer.
     *
     * @param buffer The buffer to read from.
     * @return The bencoded list element or null if none was found.
     * @throws BencodingException If there is a problem parsing the CharBuffer's
     * contents.
     */
    private BencodingList readListElement(final CharBuffer buffer) throws BencodingException {
        final BencodingList returnVal = new BencodingList();

        readSingleCharacter(buffer); // Read 'l'

        // Keep reading elements until we hit the termination character
        while (peekSingleCharacter(buffer) != TERMINATION_CHARACTER) {
            returnVal.add(readElement(buffer));
        }

        readSingleCharacter(buffer); // Read 'e'

        return returnVal;
    }

    /**
     * Read a bencoded dictionary element from a CharBuffer.
     *
     * @param buffer The buffer to read from.
     * @return The bencoded dictionary element or null if none was found.
     * @throws BencodingException If there is a problem parsing the CharBuffer's
     * contents.
     */
    private BencodingDictionary readDictionaryElement(final CharBuffer buffer) throws BencodingException {
        final BencodingDictionary returnVal = new BencodingDictionary();

        readSingleCharacter(buffer); // Read 'd'

        // Keep reading elements until we hit the termination character
        while (peekSingleCharacter(buffer) != TERMINATION_CHARACTER) {
            returnVal.put(readStringElement(buffer).getValue(), readElement(buffer));
        }

        readSingleCharacter(buffer); // Read 'e'

        return returnVal;
    }

    /**
     * Write a number element to a StringBuilder as bencoded data.
     *
     * @param element The number element to write.
     * @param builder The StringBuilder to write to.
     */
    private void writeNumberElement(final BencodingNumber element,
                                    final StringBuilder builder) {
        builder.append(START_NUMBER).append(element.getValue()).append(TERMINATION_CHARACTER);
    }

    /**
     * Write a string element to a StringBuilder as bencoded data.
     *
     * @param element The string element to write.
     * @param builder The StringBuilder to write to.
     */
    private void writeStringElement(final BencodingString element,
                                    final StringBuilder builder) {
        // Treat null string as empty string
        if (element.getValue() == null) {
            builder.append("0:");
        } else {
            builder.append(element.getValue().length()).append(':').append(element.getValue());
        }
    }

    /**
     * Write a list element to a StringBuilder as bencoded data.
     *
     * @param element The list element to write.
     * @param builder The StringBuilder to write to.
     */
    private void writeListElement(final BencodingList element,
                                  final StringBuilder builder) {
        builder.append(START_LIST);

        for (final BencodingElement subElement : element) {
            writeElement(subElement, builder);
        }

        builder.append(TERMINATION_CHARACTER);
    }

    /**
     * Write a dictionary element to a StringBuilder as bencoded data.
     *
     * @param element The dictionary element to write.
     * @param builder The StringBuilder to write to.
     */
    private void writeDictionaryElement(final BencodingDictionary element,
                                        final StringBuilder builder) {
        builder.append(START_DICTIONARY);

        for (final String subElement : element.keySet()) {
            writeStringElement(new BencodingString(subElement), builder);
            writeElement(element.get(subElement), builder);
        }

        builder.append(TERMINATION_CHARACTER);
    }

    /**
     * Read a long from a CharBuffer.
     *
     * @param buffer The buffer to read the long from.
     * @return The long read.
     */
    private long readNumber(final CharBuffer buffer) throws BencodingException {
        final StringBuilder builder = new StringBuilder();
        boolean firstIteration = true;

        // If the character is a digit or if it is the minus symbol and this is 
        // the first time around the loop.
        //
        // This lets us read negative numbers
        //
        // This does not allow negative string lengths as string sizes must begin
        // with a digit to make it this far
        while ((Character.isDigit(peekSingleCharacter(buffer)))
                || (('-' == peekSingleCharacter(buffer))) && (firstIteration)) {
            builder.append(readSingleCharacter(buffer));

            firstIteration = false;
        }

        try {
            return Long.valueOf(builder.toString());
        } catch (final NumberFormatException exception) {
            throw new BencodingException("Error converting string to number \"" + builder.toString() + "\"");
        }
    }

    /**
     * Read a string from a CharBuffer.
     *
     * @param buffer The buffer to read the string from.
     * @return The string read.
     */
    private String readString(final CharBuffer buffer,
                              final long size) throws BencodingException {
        final StringBuilder builder = new StringBuilder();

        // Read size characters into the StringBuilder
        for (long index = 0; index < size; index++) {
            builder.append(readSingleCharacter(buffer));
        }

        return builder.toString();
    }

    /**
     * Read a single character from a CharBuffer.
     *
     * @param buffer The buffer to read the character from.
     * @return The character read.
     */
    private char readSingleCharacter(final CharBuffer buffer) throws BencodingException {
        try {
            return buffer.get();
        } catch (final IndexOutOfBoundsException exception) {
            throw new BencodingException("Unexpected end of string at " + buffer.position());
        }
    }

    /**
     * Peek at the next character in a CharBuffer without incrementing the
     * buffer's position.
     *
     * @param buffer The buffer to peek at.
     * @return The character seen.
     */
    private char peekSingleCharacter(final CharBuffer buffer) throws BencodingException {
        try {
            return buffer.get(buffer.position());
        } catch (final IndexOutOfBoundsException exception) {
            throw new BencodingException("Unexpected end of string at " + buffer.position());
        }
    }
}
