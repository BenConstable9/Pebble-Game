import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Bag {
    private String name;
    private ArrayList<Integer> bagPebbles = new ArrayList<>();
    private int numberOfPlayers;

    public Bag(String name, int numberOfPlayers) {
        this.name = name;
        this.numberOfPlayers = numberOfPlayers;
    }

    public Bag(String name, String bagLocation, int numberOfPlayers) {
        this.name = name;
        this.numberOfPlayers = numberOfPlayers;

        //use this
        String[] bagPebblesString = {};
        try {
            BufferedReader reader = new BufferedReader(new FileReader(bagLocation));
            bagPebblesString = reader.readLine().split(",");
        } catch (IOException e) {
            //todo handle this better
            e.printStackTrace();
        }

        //todo put 11* back in
        if (bagPebblesString.length >= (1 * numberOfPlayers)) {
            //iterate through
            for (String bagPebbleString : bagPebblesString) {
                try {
                    int bagPebbleInt = Integer.parseInt(bagPebbleString);
                    //handle minus
                    if (bagPebbleInt < 0){
                        System.out.println("The following minus number " + bagPebbleInt + " is in bag: " + name + ".");
                    }
                    this.bagPebbles.add(bagPebbleInt);

                } catch (NumberFormatException e) {
                    //todo handle nicely
                    e.printStackTrace();
                }
            }
        } else {
            //todo work out this
            System.out.println("Bag not big enough");
        }
    }

    public ArrayList<Integer> getBagPebbles() {
        return bagPebbles;
    }

    //todo make synchronized
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

    synchronized static void swapBags(Bag blackBag, Bag whiteBag) {
        if (blackBag.bagPebbles.size() == 0) {
            blackBag.bagPebbles.addAll(whiteBag.bagPebbles);
            whiteBag.bagPebbles.clear();
        }
    }
}
