import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Bag {
    private String name;
    public List<Integer> bagPebbles = Collections.synchronizedList(new ArrayList<Integer>());
    private int numberOfPlayers;

    public Bag(String name, int numberOfPlayers) {
        this.name = name;
        this.numberOfPlayers = numberOfPlayers;
    }

    public Bag(String name, String bagLocation, int numberOfPlayers) throws IllegalArgumentException {
        this.name = name;
        this.numberOfPlayers = numberOfPlayers;
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

    synchronized int pickPebble() {
        int numberPebbles = this.bagPebbles.size();

        if (numberPebbles > 0) {
            Random rand = new Random();
            int n = rand.nextInt(numberPebbles);
            //System.out.println(n);
            //System.out.println(numberPebbles);

            return this.bagPebbles.remove(n);
        } else {
            return -1000;
        }
    }

    synchronized static void swapBags(Bag blackBag, Bag whiteBag) {
        if (blackBag.bagPebbles.size() == 0) {
            blackBag.bagPebbles.addAll(whiteBag.bagPebbles);
            whiteBag.bagPebbles.clear();
        }
    }
}
