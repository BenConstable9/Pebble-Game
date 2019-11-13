import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//todo change bag numbers ot letters
//todo add additional features

/**
 * The PebbleGame Paired-Programming coursework.
 *
 * @version 1.0
 * @since 2019-24-10
 */

public class PebbleGame {
    private Bag[] blackBags = new Bag[3];
    private Bag[] whiteBags = new Bag[3];
    private int numberOfPlayers;

    protected boolean gameWon = false;

    /**
     * Constructor for the PebbleGame Class
     * Creates the black and white bags and puts them in two different arrays
     *
     * @param numberOfPlayers - the number of players to play the game
     * @param bagLocations - a string array of bagLocations
     * @throws IllegalArgumentException //todo
     */
    public PebbleGame(int numberOfPlayers, String[] bagLocations) throws IllegalArgumentException {
        //handle the players being less than 1
        if (numberOfPlayers < 1) {
            throw new IllegalArgumentException();
        } else {
            //generate number of players in different threads
            this.numberOfPlayers = numberOfPlayers;

            //load the bag files
            for (int i = 0; i < 3; i++) {
                //if there is an exception, this will be thrown back upwards
                blackBags[i] = new Bag("Black " + i, bagLocations[i], numberOfPlayers);
                whiteBags[i] = new Bag("White " + i);
            }
        }
    }

    /**
     * Synchronized method to check whether a player has won (i.e. their hand sums to 100 with 10 items)
     * It checks their hand which is stored in an array list
     *
     * @param playerHand
     * @param playerNumber
     * @return true if player has won, otherwise false
     */
    protected synchronized boolean hasWon(ArrayList<Integer> playerHand, int playerNumber){
        if (this.gameWon) {
            return this.gameWon;
        } else {
            int playerHandValue = 0;
            for (Integer integer : playerHand) {
                playerHandValue += integer;
            }
            if (playerHandValue == 100){
                this.gameWon = true;
                System.out.println("Player: " + playerNumber + " has won with " + playerHand);
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Test method for use in our Unit Tests so that we can initialise the constructor in the nested class
     *
     * @param playerNumber
     * @return the constructor of the Player class with parameter playerNumber which is a nested class
     */
    public Runnable returnPlayer(int playerNumber) {
        return new Player(playerNumber);
    }

    /**
     * This method starts the "j" number of threads, based on how many players are playing
     * It also sets the name of the threads
     */
    public void startGame() {
        //generate the players
        for (int j = 0; j < this.numberOfPlayers; j ++) {
            Runnable runnable = new Player(j);

            Thread thread = new Thread(runnable);
            thread.setName("Player " + j); //todo - we can name the threads here
            thread.start();
        }
        System.out.println("Running the game...");
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Running");

        boolean success = false;

        while (!success) {
            System.out.println("Please enter the number of players:");
            String numberOfPlayersString = scanner.nextLine();

            int numberOfPlayers = 0;
            if (numberOfPlayersString.equals("E")) {
                break;
            } else {
                try {
                    numberOfPlayers = Integer.parseInt(numberOfPlayersString);
                } catch (NumberFormatException e) {
                    System.out.println("Unable to detect number of players");
                }
            }

            if (numberOfPlayers > 0) {
                boolean allFiles = true;
                //set up our array
                String[] bagLocations = new String[3];
                for (int i = 0; i < 3; i++) {
                    System.out.println("Please enter a location of bag number " + i + " to load: ");
                    //check it is a csv / txt
                    String bagLocation = scanner.nextLine();

                    if (bagLocation.length() > 0) {
                        if (bagLocation.equals("E")) {
                            allFiles = false;
                            success = true;
                            break;
                        } else {
                            bagLocations[i] = bagLocation;
                        }
                    } else {
                        allFiles = false;
                        break;
                    }
                }

                if (allFiles) {
                    //pass to constructor
                    try {
                        PebbleGame game = new PebbleGame(numberOfPlayers, bagLocations);
                        game.startGame();
                        success = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Unable to initiate all of the bags.");
                    }
                } else {
                    System.out.println("Unable to detect all of the files.");
                }
            }
        }
        //close my scanner
        scanner.close();
    }

    class Player implements Runnable {
        private ArrayList<Integer> playerHand = new ArrayList<>();
        private int playerNumber;
        private int previousBag;
        private ArrayList<String> playerLog = new ArrayList<>();

        /**
         * Constructor for the Player nested class
         *
         * @param playerNumber
         */
        Player(int playerNumber) {
            this.playerNumber = playerNumber;
        }

        /**
         * This method randomly picks a black bag and randomly picks a pebble from the bag
         * It ensures that there is a pebble in the bag to pick from
         * If there is a valid pick, then it sets the previous
         */
        //chooses the bag - not atomic
        private void pickBagAndPebble() {
            boolean picked = false;
            while (!picked) {
                Random rand = new Random();

                int n = rand.nextInt(3);

                Bag choosenBag = blackBags[n];
                int newPebble = choosenBag.pickPebble();

                if (newPebble == -1000) {
                    //repick
                    picked = false;
                } else {
                    this.playerHand.add(newPebble);
                    picked = true;
                    this.previousBag = n;

                    //See if the bags need to be swapped
                    Bag.swapBags(blackBags[n], whiteBags[n]);
                    System.out.println("Player " + playerNumber + " has drawn " + newPebble + " from bag " + n + "\nPlayer " + playerNumber + " hand is " + playerHand);
                    this.playerLog.add("Player " + playerNumber + " has drawn " + newPebble + " from bag " + n + "\nPlayer " + playerNumber + " hand is " + playerHand);

                }
            }
        }

        private void discardPebble(){
            Random rand = new Random();
            int i = rand.nextInt(9);

            Bag choosenBag = whiteBags[this.previousBag];
            int removedPebble = this.playerHand.remove(i);
            choosenBag.bagPebbles.add(removedPebble);

            //System.out.println("Player " + playerNumber + " has discarded " + removedPebble + " from bag " + i + "\nPlayer " + playerNumber + " hand is " + playerHand);
            this.playerLog.add("Player " + playerNumber + " has discarded " + removedPebble + " from bag " + i + "\nPlayer " + playerNumber + " hand is " + playerHand);
        }

        private void saveLog() {
            String filename = "player" + this.playerNumber + "_output.txt";
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
                String output = String.join("\n", this.playerLog);
                writer.write(output);
                writer.close();
            } catch (IOException e) {
                System.out.println("Unable to write to file");
            }
        }


        @Override
        public void run() {
            for (int i = 0; i < 10; i ++) {
                this.pickBagAndPebble();
            }

            while (!hasWon(this.playerHand, this.playerNumber)){
                //System.out.println(this.playerHand);
                this.discardPebble();
                this.pickBagAndPebble();
            }

            //save everything to a file
            //System.out.println(this.playerLog);
            this.saveLog();
        }
    }
}
