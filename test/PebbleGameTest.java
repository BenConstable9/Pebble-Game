import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PebbleGameTest {

    private PebbleGame game;
    @Before
    public void setUp() throws Exception {
        String[] bagLocations = new String[]{"testPebbleGameConstructor.txt", "testPebbleGameConstructor.txt", "testPebbleGameConstructor.txt"};
        this.game = new PebbleGame(4, bagLocations);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testPebbleGameConstructor() {
        //empty
    }

    @Test
    public void testPlayerConstructor() {
        Runnable runnable = new PebbleGame.Player(0);

        Thread thread = new Thread(runnable);
        thread.setName("Player " + 0);
        thread.start();
    }
}