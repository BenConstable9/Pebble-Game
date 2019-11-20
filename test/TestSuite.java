//Used for the JUnit tests
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * The TestSuite for BagTest and PebbleGameTest classes
 *
 * @version 1.0
 * @since 2019-24-10
 * Built using Java 13 and JUnit 4
 */

//Run the Suite
@RunWith(Suite.class)
@Suite.SuiteClasses({BagTest.class, PebbleGameTest.class}) //runs the JUnit test files

public class TestSuite {
    //empty as a holder
} 