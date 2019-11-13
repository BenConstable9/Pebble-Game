//Used for the JUnit tests
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

//Used within the testing code
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.reflect.*;

/**
 * The PebbleGame Testing Class
 *
 * @version 1.0
 * @since 2019-24-10
 */

public class PebbleGameTest {
    //used for remapping the outputs and inputs
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    //used to store the generic game for testing
    private PebbleGame game;

    /**
     * Handles making a bag to be used for generic testing within the tests.
     * Redirects the input and output streams to allow us to examine it.
     * Automatically called before each test.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        //Create a new game and store it
        String[] bagLocations = new String[]{"testPebbleGameConstructor.txt", "testPebbleGameConstructor.txt", "testPebbleGameConstructor.txt"};
        this.game = new PebbleGame(4, bagLocations);

        //Save the output elsewhere
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    /**
     * Redirect the inputs and outputs back to their orginal place.
     * Automtaically called after each test.
     */
    @After
    public void tearDown() {
        //redirect
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    /**
     * Will use the given input and simulate it as if it has come from the keyboard.
     * @param data - the new input for the keyboard.
     */
    private void provideInput(String data) {
        //use a byte array stream to use for the data
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    /**
     * Get the given output that would be printed.
     * @return - the printed lines.
     */
    private String getOutput() {
        return testOut.toString();
    }

    /**
     * Test the start game function to see if it produces the correct amount of threads.
     */
    @Test
    public void testStartGame() {
        this.game.startGame();

        //count the number of threads which are correctly named
        int nbRunning = 0;
        //put the threads into a set and iterate
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getName().contains("Player ")) nbRunning++;
        }

        //check if it is 4 (the number produced by setUp)
        assertEquals(4, nbRunning);
    }

    /**
     * Test the constructor for the game.
     */
    @Test
    public void testPebbleGameConstructor() {
        //create the string of files with a false file
        String[] bagLocations = new String[]{"testPebbleGameConstructor.txt", "testPebbleGameConstructor.txt", "testPebbleGameConstructor.txt"};
        new PebbleGame(4, bagLocations);
    }

    /**
     * Test the constructor for the game.
     * It should produce an exception as one of the files does not exist.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testPebbleGameConstructorInvalidFile() {
        //create the string of files with a false file
        String[] bagLocations = new String[]{"testPebbleGameConstructor.txt", "randomFile.txt", "testPebbleGameConstructor.txt"};
        new PebbleGame(4, bagLocations);
    }

    /**
     * Test the constructor with an negative number of players.
     * Should produce exception as this is not allowed.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testPebbleGameConstructorNegativePlayers() {
        //Pass the files into the constructor
        String[] bagLocations = new String[]{"testPebbleGameConstructor.txt", "testPebbleGameConstructor.txt", "testPebbleGameConstructor.txt"};
        new PebbleGame(-1, bagLocations);
    }

    /**
     * Test the has won function.
     * The player should not have one with this result.
     */
    @Test
    public void testHasWonInvalidHand() {
        //create an hand to use for the test which does not add up to 100
        ArrayList<Integer> hand = new ArrayList<>();
        hand.addAll(Arrays.asList(1,4,5,7,3,30,5,2,9,10));

        //find the result and assert it
        boolean result = this.game.hasWon(hand, 0);

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
        hand.addAll(Arrays.asList(12,16,24,5,1,26,6,5,1,4));

        //get the result of the game and assert
        boolean result = this.game.hasWon(hand, 0);

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
        boolean result = this.game.hasWon(hand, 0);

        assertTrue(result);
    }

    /**
     * Test the saveLog function
     * See if you get an exception when saving a file
     */
    @Test
    public void testSaveLog() {
        //get the instance of a player
        Runnable runnable = this.game.returnPlayer(1);

        //handle the exceptions
        try {
            //use reflection to access the methods of the player as they cannot be accessed
            Method method = null;
            method = runnable.getClass().getDeclaredMethod("saveLog", null);
            method.setAccessible(true);

            //invoke the method savelog
            method.invoke(runnable, null);
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
        Runnable runnable = this.game.returnPlayer(1);

        try {
            //get the method pickBagAndPebble
            Method method = null;
            method = runnable.getClass().getDeclaredMethod("pickBagAndPebble", null);
            method.setAccessible(true);

            //invoke this method which will update the player hand
            method.invoke(runnable, null);

            //now get the players hand
            Field field = null;
            field = runnable.getClass().getDeclaredField("playerHand");
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
        Runnable runnable = this.game.returnPlayer(0);

        Thread thread = new Thread(runnable);
        thread.setName("Player " + 0);
        thread.start();
    }

    /**
     * Test the main and the keyboard inputs.
     * Use the input redirection to test this.
     */
    @Test
    public void testMainInvalidPlayers() {
        //set the new input for the keyboard
        final String testString = "HJJH\nE";

        //add this to the correct stream
        provideInput(testString);

        //call the main function
        PebbleGame.main(new String[0]);

        //check the output is an error message saying the number of players cannot be detected as letters were passed in
        assertTrue(getOutput().contains("Unable to detect number of players"));
    }

    /**
     * Test the main method using keyboard inputs.
     * Use redirection to test this.
     */
    @Test
    public void testMainInvalidFile() {
        //set up our new input with an invalid file
        final String testString = "20\ntextfile.txt\ntextfile.txt\nrandomTextFile.txt\nE";

        //add it to the correct stream
        provideInput(testString);

        //call the main function
        PebbleGame.main(new String[0]);

        //check the output is an error message as the file cannot be found
        assertTrue(getOutput().contains("Unable to initiate all of the bags."));
    }
}