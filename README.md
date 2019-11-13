# Pebble Game
 Pebble Game Paired-Programming Coursework
 
 Pebble Game Implemented in Java. The game can be run using the PebbleGame.java class with the corresponding main function.
 
##Testing

A single test suite is provided for testing; testing is implemented with **JUnit 4.x**. The suite contains tests for the PebbleGame.java file and the Bag.java file. Each of the files has a separate testing file which the Test Suite runs.

####Running the Tests
Tests can be run using a standard Java IDE such as **IntelliJ IDEA** and running the test TestSuite.
You can run the tests from the command line using:

*java -cp .:/usr/share/java/junit.jar org.junit.runner.JUnitCore TestSuite.class*

This will only work if JUnit is in the path of your environment and you have navigated to the correct folder. It is therefore **recommended** that you test using an IDE. 

All of the tests should pass. Some of the tests may output data as the methods they call do. 

The TestSuite class has **100% method coverage** and **91% line coverage** from the combined files: 

The BagTest class has **100% method coverage** and **100% line coverage**.
The PebbleGameTest class has **100% method coverage** and **88% line coverage**.
