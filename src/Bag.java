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

    //Synchronized list ensures any method accessing the list will never conflict
    public List<Integer> bagPebbles = Collections.synchronizedList(new ArrayList<Integer>());

    /**
     * Constructor for Bag class
     * @param name - The name of the bag e.g. Y
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
     * @param name - The name of the bag e.g. Y
     * @param bagLocation - The location of the bag to be read
     * @param numberOfPlayers - The number of players
     * @throws IllegalArgumentException
     */
    public Bag(String name, String bagLocation, int numberOfPlayers) throws IllegalArgumentException {
        this.name = name;
        boolean validBag = true;

        String[] bagPebblesString = {};
        try {
            BufferedReader reader = new BufferedReader(new FileReader(bagLocation));
            bagPebblesString = reader.readLine().split(","); //Splits whenever there is a comma
        } catch (IOException e) {
            System.out.println("Some of the files cannot be found.");
            validBag = false;
        }

        //Only proceed if bags are valid and thus found
        if (validBag) {
            if (bagPebblesString.length >= (11 * numberOfPlayers)) { //Complies with rule that pebbles > 11*numOfPlayers
                //Iterates through each pebble in the bag
                for (String bagPebbleString : bagPebblesString) {
                    try {
                        int bagPebbleInt = Integer.parseInt(bagPebbleString); //Converts the pebble into an Int from String
                        if (bagPebbleInt < 0) { //If pebble is a negative (not allowed)
                            System.out.println("Negative numbers exist in at least bag: " + bagLocation + ".");
                            validBag = false; //Thus not a valid bag
                            break; //Goes back to check if validBag is true, which it is not
                        }
                        this.bagPebbles.add(bagPebbleInt); //Adds the pebble into the bag as it is a valid pebble

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number formats exist in at least bag: " + bagLocation + ".");
                        validBag = false;
                        break;
                    }
                }
            } else { //Else (there are less pebbles than 11*numOfPlayers)
                validBag = false;
                System.out.println("The quantity of numbers is not big enough in at least bag: " + bagLocation + ".");
            }
        }

        if (!validBag) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Get the name of the bag.
     * @return - return the private attribute name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Synchronised method called from the PebbleGame.java file, which picks a random pebble from the correct bag
     * @return - the bag with the element removes, or -1000 if you cannot remove from this bag in which case
     *              the associated white bag must be emptied into that black bag
     */
    synchronized int pickPebble() { //Synchronized to ensure no conflicts
        int numberPebbles = this.bagPebbles.size();

        if (numberPebbles > 0) { //If there are pebbles in the black bag to be picked
            Random rand = new Random();
            int n = rand.nextInt(numberPebbles);

            return this.bagPebbles.remove(n);
        } else { //There are no pebbles to be picked in the black bag and so must have it's associated white bag emptied into it
            return -1000;
        }
    }

    /**
     * Swaps the contents of a white bag with its associated black bag, when the black bag is empty
     * @param blackBag - empty blackBag
     * @param whiteBag - the blackBag's associated non-empty whiteBag
     */
    synchronized static void swapBags(Bag blackBag, Bag whiteBag) {
        if (blackBag.bagPebbles.size() == 0) { //If the blackBag is empty
            blackBag.bagPebbles.addAll(whiteBag.bagPebbles); //Add all contents from the whiteBag into the blackBag
            whiteBag.bagPebbles.clear(); //Clear contents of the whiteBag (as its pebbles have moved to the blackBag)
        }
    }
}
