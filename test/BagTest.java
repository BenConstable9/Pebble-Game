//Used for the JUnit tests
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * The Bag Testing Class
 *
 * @version 1.0
 * @since 2019-24-10
 */

public class BagTest {
    /**
     * Test the bag constructors with an empty bag.
     */
    @Test
    public void testBagConstructorEmptyBag() {
        //create an empty bag
        Bag testEmptyBag = new Bag("Empty");

        //check the size of it which should be 0
        assertEquals(testEmptyBag.bagPebbles.size(), 0);
    }

    /**
     * Test the bag constructors with a bag of size 50.
     */
    @Test
    public void testBagConstructorFullBag() {
        //create the bag from the file
        Bag testFullBag = new Bag("Full", "testBagConstructorDuplicatesBag.txt", 4);

        //check it contains 50 items as expected
        assertEquals(testFullBag.bagPebbles.size(), 50);
    }

    /**
     * Test the bag constructor with a bag that doesn't exist.
     * Should produce an IllegalArgumentException.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testBagConstructorInvalidFile() {
        //try and create the bag
        Bag testFullBag = new Bag("Full", "randomBag.txt", 4);
    }

    /**
     * Test the bag constructor with a bag that contains negative numbers.
     * Should produce an IllegalArgumentException.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testBagConstructorNegativeNumbers() {
        //try and create the bag
        Bag testFullBag = new Bag("Full", "negativeNumbersBag.txt", 4);
    }

    /**
     * Test the bag constructor with a bag that contains letters.
     * Should produce an IllegalArgumentException.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testBagConstructorLetters() {
        //try and create the bag
        Bag testFullBag = new Bag("Full", "invalidNumbersBag.txt", 4);
    }

    /**
     * Test the bag constructor with a bag that contains less pebbles than 11 x players.
     * Should produce an IllegalArgumentException.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testBagConstructorInvalidLength() {
        //try and create the bag
        Bag testFullBag = new Bag("Full", "testBagConstructorDuplicatesBag.txt", 10);
    }

    /**
     * Test the pickPebble method.
     * Test by using a bag only full of 1s.
     */
    @Test
    public void testPickPebbleFullBag() {
        //create the bag full of 1s
        Bag testFullBag = new Bag("Full", "testPickPebbleBag.txt", 1);

        //check the returned value is a 1
        assertEquals(testFullBag.pickPebble(), 1);
    }

    /**
     * Test the pickPebble method.
     * Test by using an empty bag to see if it returns the expected error of -1000.
     */
    @Test
    public void testPickPebbleEmptyBag() {
        //create the empty bag.
        Bag testEmptyBag = new Bag("Empty");

        //check the returned value
        assertEquals(testEmptyBag.pickPebble(), -1000);
    }

    /**
     * Test the swapBags function.
     * Test by using an empty bag and a full bag and seeing if the sizes match before and after.
     */
    @Test
    public void testSwapBags() {
        //create two bags one empty and one full.
        Bag emptyBlackBag = new Bag("Y");
        Bag fullWhiteBag = new Bag("X", "testBagConstructorDuplicatesBag.txt", 4);

        //store the size
        int fullWhiteBagSize = fullWhiteBag.bagPebbles.size();

        //swap the bags with the static method
        Bag.swapBags(emptyBlackBag, fullWhiteBag);

        //check the sizes now equal
        assertEquals(emptyBlackBag.bagPebbles.size(), fullWhiteBagSize);
    }

    /**
     * Test the get name method.
     */
    @Test
    public void testGetName() {
        //create an empty bag
        Bag testBag = new Bag("TestBag");

        //check that the name is TestBag
        assertEquals(testBag.getName(), "TestBag");
    }
}