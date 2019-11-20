# Pebble Game
 Pebble Game Paired-Programming Coursework
 
 Pebble Game Implemented in Java. The game can be run using the PebbleGame.java class with the corresponding main function.
 
## Testing

A single test suite is provided for testing; testing is implemented with **JUnit 4.x**. The suite contains tests for the PebbleGame.java file and the Bag.java file. Each of the files has a separate testing file which the Test Suite runs.

###### Running the Tests
Tests can be run using a standard Java IDE such as **IntelliJ IDEA** and running the test TestSuite.

If you are using IntelliJ IDEA:
1) First, create a new project in IntelliJ using the JDK13 as the Project SDK
    a) Call the project whatever you wish
  
2) When the project has been created, expand it in the project view until you see the "src" folder

3) Within the src folder, drag and drop the following files which can be found in the pebblesTest.zip folder
    a) PebbleGame.java
    b) Bag.java
 
4) On the project details at the right, right click on the project name, click New and create a new directory called "Test"

5) You need to tell the IDE that this is the test folder - go onto the Project Structure and mark the new folder as a Test folder
    a) File -> Project Structure -> Modules -> click on the new "Test" folder and mark as Test

6) Within the folder (which should now be coloured green), drag and drop the following files (the test files) from the pebblesTest.zip folder
    a) BagTest.java
    b) PebbleGameTest.java
    c) TestSuite.java
 
7) Within the Project directory, add all of the text files (.txt) and the excel example .csv file 
    a) All of these files can be found in the pebblesTest.zip folder
    
8) Go onto any of the test files and you may find that JUnit 4 is not already in the classpath
    a) Go onto any of the annotations, for example @Before
    b) Hover over it until a pop-up appears saying "Add Junit 4 to class path"
    c) Click on the pop-up
    d) Press "continue" so that JUnit 4 is added to the classpath
    
9) To run the Test Suite, go onto the Test Suite file and right click on the page and select "Run 'TestSuite' with Coverage'
    a) This will run the TestSuite and thus the BagTest and PebbleGameTest Junit files too

10) If you wish to run the JUnit files separately, then you can right-click on their files and choose the same option of running with coverage
   
 
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
