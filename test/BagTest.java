import org.junit.Test;

import static org.junit.Assert.*;

public class BagTest {
    @Test
    public void testBagConstructor() {
        int desiredSize = 0;
        Bag testEmptyBag = new Bag("Empty");
        assertEquals(testEmptyBag.bagPebbles.size(), desiredSize);
    }

    @Test
    public void testBagConstructorDuplicates() {
        int desiredSize = 50;
        Bag testFullBag = new Bag("Full", "testBagConstructorDuplicatesBag.txt", 4);
        assertEquals(testFullBag.bagPebbles.size(), desiredSize);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testBagConstructorDuplicates2() {
        Bag testFullBag = new Bag("Full", "randomBag.txt", 4);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testBagConstructorDuplicates3() {
        Bag testFullBag = new Bag("Full", "negativeNumbersBag.txt", 4);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testBagConstructorDuplicates4() {
        Bag testFullBag = new Bag("Full", "invalidNumbersBag.txt", 4);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testBagConstructorDuplicates5() {
        Bag testFullBag = new Bag("Full", "testBagConstructorDuplicatesBag.txt", 10);
    }

    @Test
    public void testPickPebble() {
        int expectedValue = 1;
        Bag testFullBag = new Bag("Full", "testPickPebbleBag.txt", 1);
        assertEquals(testFullBag.pickPebble(), expectedValue);
    }

    @Test
    public void testPickPebbleDuplicates() {
        int expectedValue = -1000;
        Bag testEmptyBag = new Bag("Empty");
        assertEquals(testEmptyBag.pickPebble(), expectedValue);
    }

    @Test
    public void testSwapBags() {
        Bag emptyBlackBag = new Bag("Y");
        Bag fullWhiteBag = new Bag("Y", "testBagConstructorDuplicatesBag.txt", 4);
        int fullWhiteBagSize = fullWhiteBag.bagPebbles.size();
        Bag.swapBags(emptyBlackBag, fullWhiteBag);

        assertEquals(emptyBlackBag.bagPebbles.size(), fullWhiteBagSize);
    }
}