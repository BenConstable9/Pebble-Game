# Pebble Game
 Pebble Game Paired-Programming Coursework
 
 Pebble Game Implemented in Java. The game can be run using the PebbleGame.java class with the corresponding main function.
 
## Testing

A single test suite is provided for testing; testing is implemented with **JUnit 4.x**. The suite contains tests for the PebbleGame.java file and the Bag.java file. Each of the files has a separate testing file which the Test Suite runs.

###### Running the Tests
Tests can be run using a standard Java IDE such as **IntelliJ IDEA** and running the test TestSuite.

If you are using InteliJ IDEA:
1) You may have to go onto Project Structure and mark the test file (which contains all of the test) as the Test folder.
    
    a) File -> Project Structure -> click on our "test" folder and mark as Tests
2) If JUnit 4 is not already in the classpath, go onto any of the annotations in any test file, and hover over it
    
    a) Click on the pop-up saying "Add JUnit 4 to class path" (alternatively press Alt + Shift + Enter)
    
    b) When the pop-up appears, press the continue button 
3) Go onto the TestSuite file and right click on the page and select 'Run 'TestSuite' with Coverage'
    
    a) This will run the TestSuite and thus the BagTest and PebbleGameTest JUnit files

 
###### Outputs:
All of the tests should pass.

**BagTest Outputs: (Only those tests that give an output)**

testBagConstructorInvalidFile --> 

    Some of the files cannot be found.

testBagConstructorNegativeNumbers --> 

    Negative numbers exist in at least bag: negativeNumbersBag.txt

testBagConstructorInvalidLength --> 

    The quantity of numbers is not big enough in at least bag: testBagConstructorDuplicatesBag.txt

testBagConstructorLetters --> 

    Invalid number formats exist in at least bag: invalidNumbersBag.txt

**PebbleGameTest Outputs: (Only those tests that give an output)**

testPickBagAndPebble -->    
    
    Dave has drawn *number* from bag *X/Y/Z*
    
    Dave hand is [*number*]                 

testStartGame --> 

    Running the game...
    
    *a game log*

testPebbleGameConstructorInvalidFile --> 

    Some of the files cannot be found

testHasWonWinningHand --> 

    Player 0 has won with [2, 4, 6, 8, 10, 10, 12, 14, 16, 18]



The TestSuite class has **100% class coverage** and **89% line coverage** from the combined files: 

The BagTest class has **100% method coverage** and **100% line coverage**.
The PebbleGameTest class has **100% method coverage** and **85% line coverage**.
