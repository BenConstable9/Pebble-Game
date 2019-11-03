import java.util.ArrayList;
import java.util.Arrays;
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
            System.out.println("making player");
            Runnable runnable = new Player();

            Thread thread = new Thread(runnable);
            thread.start();
            //todo name the threads
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

            if (allFiles == true) {
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

        public ArrayList<Integer> getPlayerHand() {
            return playerHand;
        }

        @Override
        public void run() {
            Player player = new Player();
        }
    }
}
