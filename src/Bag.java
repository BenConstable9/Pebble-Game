import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * The Bag Class
 *
 * @version 1.0
 * @since 2019-24-10
 */


public class Bag {
    private String name;
    public List<Integer> bagPebbles = Collections.synchronizedList(new ArrayList<Integer>());

    /**
     * Constructor for Bag class
     * @param name -
     */
    public Bag(String name) {
        this.name = name;
    }

    /**
     * Overloaded constructor for Bag class
     * It gains the inputs from when the Bag class is called in the PebbleGame.java
     * The method performs checks on the given bagLocations to ensure there are enough pebbles based on num of players
     * Ensures all files can be found and reads their contents to add to the Bag
     *
     * @param name - //todo Name of the bag
     * @param bagLocation - The location of the bag to be read
     * @param numberOfPlayers - The number of players
     * @throws IllegalArgumentException
     */
    public Bag(String name, String bagLocation, int numberOfPlayers) throws IllegalArgumentException {
        this.name = name;
        boolean validBag = true;

        //use this
        String[] bagPebblesString = {};
        try {
            BufferedReader reader = new BufferedReader(new FileReader(bagLocation));
            bagPebblesString = reader.readLine().split(",");
        } catch (IOException e) {
            System.out.println("Some of the files cannot be found..");
            validBag = false;
        }

        //only proceed if bag is found
        if (validBag) {
            if (bagPebblesString.length >= (11 * numberOfPlayers)) {
                //iterate through
                for (String bagPebbleString : bagPebblesString) {
                    try {
                        int bagPebbleInt = Integer.parseInt(bagPebbleString);
                        //handle minus
                        if (bagPebbleInt < 0) {
                            System.out.println("Negative numbers exist in bag: " + name + ".");
                            validBag = false;
                            break;
                        }
                        this.bagPebbles.add(bagPebbleInt);

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number formats exist in bag: " + name + ".");
                        validBag = false;
                        break;
                    }
                }
            } else {
                validBag = false;
                System.out.println("The quantity of numbers is not big enough in bag: " + name + ".");
            }
        }

        if (!validBag) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Synchronised method called from the PebbleGame.java file, which picks a random pebble from the correct bag
     * @return - the bag with the element removes, or -1000 if you cannot remove from this bag in which case
     *              the associated white bag must be emptied into that black bag
     */
    synchronized int pickPebble() {
        int numberPebbles = this.bagPebbles.size();

        if (numberPebbles > 0) {
            Random rand = new Random();
            int n = rand.nextInt(numberPebbles);

            return this.bagPebbles.remove(n);
        } else {
            return -1000;
        }
    }

    /**
     * Swaps the contents of a white bag with its associated black bag, when the black bag is empty
     * @param blackBag - empty blackBag
     * @param whiteBag - the blackBag's associated non-empty whiteBag
     */
    synchronized static void swapBags(Bag blackBag, Bag whiteBag) {
        if (blackBag.bagPebbles.size() == 0) {
            blackBag.bagPebbles.addAll(whiteBag.bagPebbles);
            whiteBag.bagPebbles.clear();
        }
    }
}
