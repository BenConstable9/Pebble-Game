import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class PebbleGame {
    private int numberOfPlayers;
    private Bag[] blackBags = new Bag[3];
    private Bag[] whiteBags = new Bag[3];

    public PebbleGame(int numberOfPlayers, String[] bagLocations) {
        //generate number of players in different threads
        this.numberOfPlayers = numberOfPlayers;

        //load the bag files
        for (int i = 0; i < 3; i ++) {
            blackBags[i] = new Bag("Black " + i ,bagLocations[i], numberOfPlayers);
            whiteBags[i] = new Bag("White " + i , numberOfPlayers);
        }

        //generate the players
        for (int j = 0; j < numberOfPlayers; j ++) {
            Runnable runnable = new Player(j);

            Thread thread = new Thread(runnable);
            thread.start();
            thread.setName("Player " + j);
            System.out.println(thread.getName());
            //todo build intiial picks
        }
    }

    //todo EXCEPTION HANDLING UNTIL INPUTS ARE LEGAL
    public static void main(String args []) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Running");

        System.out.println("Please enter the number of players:");
        boolean hasNextInt = scanner.hasNextInt();

        if (hasNextInt) {
            int numberOfPlayers = scanner.nextInt();
            scanner.nextLine();

            boolean allFiles = true;
            //set up our array
            String[] bagLocations = new String[3];
            for (int i = 0; i  < 3; i ++) {
                System.out.println("Please enter a location of bag number " + i + " to load: ");
                //check it is a csv / txt
                String bagLocation = scanner.nextLine();

                //todo must be a file
                if (bagLocation.length() > 0) {
                    bagLocations[i] = bagLocation;
                } else {
                    allFiles = false;
                    break;
                }
            }

            if (allFiles) {
                //pass to constructor
                System.out.println("Generating");

                PebbleGame game = new PebbleGame(numberOfPlayers, bagLocations);
            } else {
                System.out.println("Unable to detect all of the files.");
            }
        } else {
            System.out.println("Unable to detect number of players");
        }

        //close my scanner
        scanner.close();

    }

    class Player implements Runnable {
        private ArrayList<Integer> playerHand = new ArrayList<>();
        private int playerNumber;

        public Player (int playerNumber) {
            this.playerNumber = playerNumber;
        }

        public ArrayList<Integer> getPlayerHand() {
            return playerHand;
        }

        public int getPlayerNumber() {
            return playerNumber;
        }

        //chooses the bag - not atomic
        private void pickBagAndPebble() {
            boolean picked = false;
            while (!picked) {
                Random rand = new Random();

                int n = rand.nextInt(3);

                Bag choosenBag = blackBags[n];
                //todo finish
                int newPebble = choosenBag.pickPebble();

                if (newPebble == -1000) {
                    //repick
                    picked = false;
                } else {
                    this.playerHand.add(newPebble);
                    picked = true;

                    //See if the bags need to be swapped
                    Bag.swapBags(blackBags[n], whiteBags[n]);
                }
            }
        }

        @Override
        public void run() {
            System.out.println("Test");
            for (int i = 0; i < 10; i ++) {
                this.pickBagAndPebble();
            }
            System.out.println(this.playerHand);
        }
    }
}
