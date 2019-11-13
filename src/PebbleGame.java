import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//todo change bag numbers to letters
//todo add additional features

/**
 * The PebbleGame Paired-Programming coursework.
 * By storing the black and white bags as Bag objects, you can call the Bag.java methods
 *
 * @version 1.0
 * @since 2019-24-10
 */

public class PebbleGame {
    //Used to store the returned bags within
    private Bag[] blackBags = new Bag[3];
    private Bag[] whiteBags = new Bag[3];

    private int numberOfPlayers;

    //Used in synchronized to determine whether or not the game has been won
    protected boolean gameWon = false;

    /**
     * Constructor for the PebbleGame Class
     * Creates the black and white bags and puts them in two different arrays
     *
     * @param numberOfPlayers - the number of players to play the game
     * @param bagLocations - a string array of bagLocations
     * @throws IllegalArgumentException
     */
    public PebbleGame(int numberOfPlayers, String[] bagLocations) throws IllegalArgumentException {
        //Handle the players being less than 1
        if (numberOfPlayers < 1) {
            throw new IllegalArgumentException();
        } else {
            //Generate number of players in different threads
            this.numberOfPlayers = numberOfPlayers;

            //Load the bag files
            for (int i = 0; i < 3; i++) {
                //If there is an exception, this will be thrown back upwards
                blackBags[i] = new Bag("Black " + i, bagLocations[i], numberOfPlayers);
                whiteBags[i] = new Bag("White " + i);
            }
        }
    }

    /**
     * Synchronized method to check whether a player has won (i.e. their hand sums to 100 with 10 items)
     * It checks their hand which is stored in an array list
     *
     * @param playerHand -
     * @param playerNumber
     * @return true if player has won, otherwise false
     */
    protected synchronized boolean hasWon(ArrayList<Integer> playerHand, int playerNumber){
        if (this.gameWon) {
            return this.gameWon;
        } else {
            int playerHandValue = 0;
            //Sums the value of the player's hand
            for (Integer sum : playerHand) {
                playerHandValue += sum;
            }

            if (playerHandValue == 100){ //If the player's hand equals 100 (thus they have won)
                this.gameWon = true;
                System.out.println("Player: " + playerNumber + " has won with " + playerHand);
                return true;
            } else { //Else, player has not won
                return false;
            }
        }
    }

    /**
     * Test method for use in our Unit Tests so that we can initialise the constructor in the nested class
     * By creating a method in the outer class accessing the inner, it can be used in the Unit tests
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
        //Generate the players and threads
        for (int j = 0; j < this.numberOfPlayers; j ++) {
            Runnable runnable = new Player(j);

            Thread thread = new Thread(runnable);
            thread.setName("Player " + j); //todo - we can name the threads here
            thread.start();
        }
        System.out.println("Running the game...");
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); //Scanner object is created for later use
        System.out.println("Running");

        boolean success = false; //Used in the while loop to make sure all inputs are correct before game progresses

        while (!success) {
            System.out.println("Please enter the number of players:");
            String numberOfPlayersString = scanner.nextLine(); //Requires String input so will be converted to Int later

            int numberOfPlayers = 0;
            if (numberOfPlayersString.equals("E")) { //If user wants to end the program
                break;
            } else {
                try {
                    numberOfPlayers = Integer.parseInt(numberOfPlayersString); //Converts String to Int
                } catch (NumberFormatException e) { //If String is unable to be converted to an Int
                    System.out.println("Unable to detect number of players");
                }
            }

            if (numberOfPlayers > 0) {
                boolean allFiles = true;
                //Sets up our bag arrays
                String[] bagLocations = new String[3]; //String array of length 3 for the 3 bag locations
                for (int i = 0; i < 3; i++) {
                    System.out.println("Please enter a location of bag number " + i + " to load: ");
                    //check it is a csv / txt todo check what happens with like a .docx file
                    String bagLocation = scanner.nextLine(); //Takes in the next line

                    if (bagLocation.length() > 0) {
                        if (bagLocation.equals("E")) { //User wants to exit
                            allFiles = false;
                            success = true;
                            break;
                        } else {
                            bagLocations[i] = bagLocation; //Places given bag location from scanner into array
                        }
                    } else {
                        allFiles = false;
                        break;
                    }
                }

                if (allFiles) { //if allFiles is true
                    //Pass the files to the constructor
                    try {
                        PebbleGame game = new PebbleGame(numberOfPlayers, bagLocations); //Creates a PebbleGame
                        game.startGame(); //Starts the new game
                        success = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Unable to initiate all of the bags.");
                    }
                } else { //If there is an error in the files
                    System.out.println("Unable to detect all of the files.");
                }
            }
        }
        //Closes the scanner
        scanner.close();
    }

    /**
     * Nested class called Player which implements the Runnable interface
     */
    class Player implements Runnable {
        private ArrayList<Integer> playerHand = new ArrayList<>(); //ArrayList to hold the players' hand
        private int playerNumber;
        private int previousBag; //Int to hold the bag the player last took from
        private ArrayList<String> playerLog = new ArrayList<>(); //ArrayList to hold the players' activity log

        /**
         * Constructor for the Player nested class
         *
         * @param playerNumber
         */
        Player(int playerNumber) { //Generates a player when given a player number
            this.playerNumber = playerNumber;
        }

        /**
         * This method randomly picks a black bag and randomly picks a pebble from the bag (by calling Bag class)
         * It ensures that there is a pebble in the bag to pick from
         * If there is a valid pick, then it sets the previous
         * Adds the pick details onto the player's log - to output to their text-file at the end
         */
        //chooses the bag - not atomic //todo Get rid of this?
        private void pickBagAndPebble() {
            boolean picked = false;
            while (!picked) {
                Random rand = new Random(); //New random object

                int n = rand.nextInt(3); //Random number between 0 and 2

                Bag chosenBag = blackBags[n];
                int newPebble = chosenBag.pickPebble(); //Picks a pebble from the randomly chosen black bag

                if (newPebble == -1000) {
                    //Re-pick because that bag is inaccessible
                    picked = false;
                } else {
                    this.playerHand.add(newPebble); //If successful, add the pebble to the player's hand
                    picked = true;
                    this.previousBag = n; //Changes the previous bag to the bag just picked (for discarding)

                    //See if the bags need to be swapped
                    Bag.swapBags(blackBags[n], whiteBags[n]);

                    System.out.println("Player " + playerNumber + " has drawn " + newPebble + " from bag " + n + "\nPlayer " + playerNumber + " hand is " + playerHand);

                    //Adds to player's log the drawn action
                    this.playerLog.add("Player " + playerNumber + " has drawn " + newPebble + " from bag " + n + "\nPlayer " + playerNumber + " hand is " + playerHand);
                }
            }
        }

        /**
         * This method removes a random pebble from the player's hand when they reach 10 pebbles
         * It then adds the removed pebble onto the associated white bag from the last black bag picked
         * Adds the pick details onto the player's log - to output to their text-file at the end
         */
        private void discardPebble(){
            Random rand = new Random();
            int i = rand.nextInt(9); //Random int between 0 and 9 (One of the pebbles in player's hand)

            Bag chosenBag = whiteBags[this.previousBag]; //The white bag which the pebble will be placed into
            int removedPebble = this.playerHand.remove(i); //Pebble removed from player's hand
            chosenBag.bagPebbles.add(removedPebble); //Pebble added to the white bag

            //System.out.println("Player " + playerNumber + " has discarded " + removedPebble + " from bag " + i + "\nPlayer " + playerNumber + " hand is " + playerHand);

            //Adds to player's log the drawn action
            this.playerLog.add("Player " + playerNumber + " has discarded " + removedPebble + " from bag " + i + "\nPlayer " + playerNumber + " hand is " + playerHand);
        }

        /**
         * This method is initialised at the end of the game
         * It writes the player's log onto the associated player's output text file
         */
        private void saveLog() {
            String filename = "player" + this.playerNumber + "_output.txt"; //Name of text-file to be created for each player
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
                String output = String.join("\n", this.playerLog); //Makes the array-list for this player a string, with each item separated by a new line
                writer.write(output); //Writes to the text-file (overrides whatever is in the text-file prior)
                writer.close();
            } catch (IOException e) {
                System.out.println("Unable to write to file");
            }
        }

        /**
         * This is the method that is ran when you start the threads
         * Each thread will initially pick 10 pebbles each to build its hand
         * After which, it will keep running the game and follow the rules until a thread has won
         * When someone has won, each thread will start the saveLog() method to upload their hand onto the text-file
         */
        @Override
        public void run() {
            for (int i = 0; i < 10; i ++) { //For loop from 0 to 9 picking the initial 10 pebbles for a hand
                this.pickBagAndPebble(); //Builds the hand
            }

            while (!hasWon(this.playerHand, this.playerNumber)){ //While no-one has won, discard a pebble and pick another
                this.discardPebble();
                this.pickBagAndPebble();
            }

            //When outside of the while loop (when someone has won), save the logs to the respective player's text-file
            this.saveLog();
        }
    }
}
