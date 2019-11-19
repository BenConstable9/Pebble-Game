//Used for the JUnit tests
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

//Used within the testing code
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.reflect.*;

/**
 * The PebbleGame Testing Class
 *
 * @version 1.0
 * @since 2019-24-10
 * Built using Java 13
 */

public class PebbleGameTest { //CHECK THE COMMENTS HERE
    //used for remapping the outputs and inputs
    //https://stackoverflow.com/questions/1647907/junit-how-to-simulate-system-in-testing
    //we used the above site to aid us in how to run these system input/output tests
    private final InputStream systemIn = System.in; //what does it do?
    private final PrintStream systemOut = System.out;//and this

    private ByteArrayInputStream testIn; //and this
    private ByteArrayOutputStream testOut; //and this

    //used to store the generic game for testing
    private PebbleGame game;

    /**
     * Handles making a bag to be used for generic testing within the tests.
     * Redirects the input and output streams to allow us to examine it.
     * Automatically called before each test - so sets the conditions for the other tests to be able to be ran
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception { //What does setUp() do???
        //Create a new game and store it
        String[] bagLocations = new String[]{"testPebbleGameConstructor.txt", "testPebbleGameConstructor.txt", "testPebbleGameConstructor.txt"};
        String[] playerNames = new String[]{"Dave", "Bob", "Steve", "Kate"};
        this.game = new PebbleGame(4, playerNames, bagLocations);
    }

    /**
     * Will use the given input and simulate it as if it has come from the keyboard.
     * Will redirect the output to an attribute.
     * @param input - the new input for the keyboard.
     */
    private void getInput(String input) {
        //Save the output elsewhere
        this.testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(this.testOut));

        //use a byte array stream to use for the data
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
    }

    /**
     * Get the given output that would be printed.
     * @return - the printed lines.
     */
    private String getOutput() {
        //redirect                                 -REDIRECT WHERE TO?????
        System.setIn(systemIn);
        System.setOut(systemOut);
        return this.testOut.toString();
    }

    /**
     * Test the start game function to see if it produces the correct amount of threads.
     */
    @Test
    public void testStartGame() {
        this.game.startGame();

        //count the number of threads which are correctly named - CAN WE NOT JUST USE: java.lang.Thread.activeCount()
        int running = 0;
        //put the threads into a set and iterate through it
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getName().contains("Player ")) {
                running++;
            }
        }

        //check if it is 4 (the number produced by setUp)
        assertEquals(4, running);
    }

    /**
     * Test the constructor for the game.
     */
    @Test
    public void testPebbleGameConstructor() {
        //create the string of files with a false file
        String[] bagLocations = new String[]{"testPebbleGameConstructor.txt", "testPebbleGameConstructor.txt", "testPebbleGameConstructor.txt"};
        String[] playerNames = new String[]{"Dave", "Bob", "Steve", "Kate"};
        new PebbleGame(4, playerNames, bagLocations);
    }

    /**
     * Test the constructor for the game.
     * It should produce an exception as one of the files does not exist.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testPebbleGameConstructorInvalidFile() {
        //create the string of files with a false file
        String[] bagLocations = new String[]{"testPebbleGameConstructor.txt", "randomFile.txt", "testPebbleGameConstructor.txt"};
        String[] playerNames = new String[]{"Dave", "Bob", "Steve", "Kate"};
        new PebbleGame(4, playerNames, bagLocations);
    }

    /**
     * Test the constructor with a negative number of players.
     * Should produce exception as this is not allowed.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testPebbleGameConstructorNegativePlayers() {
        //Pass the files into the constructor
        String[] bagLocations = new String[]{"testPebbleGameConstructor.txt", "testPebbleGameConstructor.txt", "testPebbleGameConstructor.txt"};
        String[] playerNames = new String[]{"Dave", "Bob", "Steve", "Kate"};
        new PebbleGame(-1, playerNames, bagLocations);
    }

    /**
     * Test the constructor with an a different number of names to players
     * Should produce exception as this is not allowed.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testPebbleGameConstructorMismatchedPlayers() {
        //Pass the files into the constructor
        String[] bagLocations = new String[]{"testPebbleGameConstructor.txt", "testPebbleGameConstructor.txt", "testPebbleGameConstructor.txt"};
        String[] playerNames = new String[]{"Dave", "Bob", "Steve", "Kate"};
        new PebbleGame(3, playerNames, bagLocations);
    }

    /**
     * Test the has won function.
     * The player should not have one with this result.
     */
    @Test
    public void testHasWonInvalidHand() {
        //create an hand to use for the test which does not add up to 100
        ArrayList<Integer> hand = new ArrayList<>();
        hand.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9,10)); //sums to 55

        //find the result and assert it
        boolean result = this.game.hasWon(hand, "Player 0");

        assertFalse(result);
    }

    /**
     * Test the has won function.
     * The player should win with this hand.
     */
    @Test
    public void testHasWonWinningHand() {
        //create a hand for them
        ArrayList<Integer> hand = new ArrayList<>();
        hand.addAll(Arrays.asList(2, 4, 6, 8, 10, 10, 12, 14, 16, 18)); //sums to 100

        //get the result of the game and assert
        boolean result = this.game.hasWon(hand, "Player 0");

        assertTrue(result);
    }

    /**
     * Test the has won function.
     * The player should not win with this hand even though it adds to 100 as someone else has won first.
     */
    @Test
    public void testHasWonOtherWinner() {
        //create winning hand
        ArrayList<Integer> hand = new ArrayList<>();
        hand.addAll(Arrays.asList(12,16,24,5,1,26,6,5,1,4));

        //set that the game has already been won
        this.game.gameWon = true;

        //get the result
        boolean result = this.game.hasWon(hand, "Player 0");

        assertTrue(result);
    }

    /**
     * Test the saveLog function
     * See if you get an exception when saving a file
     */
    @Test
    public void testSaveLog() {
        //get the instance of a player
        Runnable runnable = this.game.new Player("Dave", 0);

        //handle the exceptions
        try {
            //use reflection to access the methods of the player as they cannot be accessed
            Method method = null;
            method = runnable.getClass().getDeclaredMethod("saveLog", null); //gets the saveLog() method
            method.setAccessible(true);

            //invoke the method savelog through runnable
            method.invoke(runnable, null);

            //check if the file exists
            File tempFile = new File("player0_output.txt");
            boolean exists = tempFile.exists();

            assertTrue(exists);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test the pick bag and pebble method
     * Check this by picking a pebble and checking the size of the file.
     */
    @Test
    public void testPickBagAndPebble() {
        //get an instance of a player
        Runnable runnable = this.game.new Player("Dave", 0);

        try {
            //get the method pickBagAndPebble
            Method method = null;
            method = runnable.getClass().getDeclaredMethod("pickBagAndPebble", null); //get the pickBagAndPebble() method
            method.setAccessible(true);

            //invoke this method which will update the player hand
            method.invoke(runnable, null);

            //now get the player's hand
            Field field = null;
            field = runnable.getClass().getDeclaredField("playerHand"); //get the playerHand attribute
            field.setAccessible(true);

            //cast this to an ArrayList as this is what will be returned
            ArrayList<Integer> result = (ArrayList<Integer>) field.get(runnable);

            //check the size of it is 1
            assertEquals(result.size(), 1);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test the constructor for a new player to see if it produces any exceptions.
     */
    @Test
    public void testPlayerConstructor() {
        //get an instance of the player and see if the threads starts
        Runnable runnable = this.game.new Player("Dave", 0);

        Thread thread = new Thread(runnable);
        thread.setName("Player " + 0);
        thread.start();
    }

    /*The next two test's code for redirecting system inputs was created using the responses to this StackOverflow question:
    https://stackoverflow.com/questions/1647907/junit-how-to-simulate-system-in-testing.
    We have adapted the code for our use in the two tests.
     */
    /**
     * Tests the main and the keyboard inputs.
     * Uses the input redirection to test this.
     */
    @Test
    public void testMainInvalidPlayers() {
        //set the new input for the keyboard
        final String testString = "HJJH\nE";

        //add this to the correct stream
        getInput(testString);

        //calls the main function
        PebbleGame.main(new String[0]);

        //check the output is an error message saying the number of players cannot be detected as letters were passed in
        assertTrue(getOutput().contains("Unable to detect number of players"));
    }

    /**
     * Test the main method using keyboard inputs.
     * Uses redirection to test this.
     */
    @Test
    public void testMainInvalidFile() {
        //set up our new input with an invalid file
        final String testString = "20\ntextfile.txt\ntextfile.txt\nrandomTextFile.txt\nE";

        //add it to the correct stream
        getInput(testString);

        //call the main function
        PebbleGame.main(new String[0]);

        //check the output is an error message as the file cannot be found
        assertTrue(getOutput().contains("Unable to initiate all of the bags."));
    }
}