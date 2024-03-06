import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains tests for functionalities provided by the frontend of the application,
 * simulating a user's interactions and ensuring the frontend behaves as expected.
 */
public class FrontendDeveloperTests {
    // storing file path in a string
    private final String PATH = "src/songs.csv";
    private final String SUBPATH = "src/songSubset.csv";

    /**
     * Test the functionality of the load file menu. This method simulates a user trying to load a
     * file named 'data.csv'. It checks if the frontend prompts the user for the file path and
     * attempts to load it.
     */
    @Test
    public void testLoadFile() {

        // Create a scanner object
        Scanner userInputScanner = new Scanner("1\n" + SUBPATH + "\n4\n");
        // Create a backend object
        BackendInterface backend = new Backend(new IterableMultiKeyRBT());
        // Instantiate and run the frontend object
        Frontend songSearcher = new Frontend(userInputScanner, backend);

        // Call the loadFile method that prompts the user for the file path
        songSearcher.mainMenuLoop();


        // Validate the output for successful file load
        Assertions.assertTrue(songSearcher.log.contains("Loaded the file successfully!"));
    }

    /**
     * Test the functionality to display the average danceability score of all songs. It ensures the
     * frontend correctly displays the average score.
     */
    @Test
    public void testAvgScore() {
        IterableMultiKeyRBT placeholder = new IterableMultiKeyRBT(); // placeholder object
        BackendInterface data = new Backend(placeholder); // backend object

        Scanner scnr = new Scanner("1\n" + "src/songSubset.csv" + "\n3\n4\n"); // scanner object
        Frontend frontend = new Frontend(scnr, data); // frontend object

        frontend.mainMenuLoop(); // main menu loop

        assertTrue(frontend.log.contains("The average danceability score is:")); // check if
        // average score
        // is correct
    }

    /**
     * Test the functionality for prompting the user to list songs based on a specific score. This
     * method simulates a user trying to list songs with a danceability score of 100.
     */
    @Test
    public void testListSongsByScore() {
        // Create a scanner object
        Scanner userInputScanner = new Scanner("1\n" + SUBPATH + "\n2\n80\n4\n");
        // Create a backend object
        BackendInterface backend = new Backend(new IterableMultiKeyRBT());
        // Instantiate and run the frontend object
        Frontend songSearcher = new Frontend(userInputScanner, backend);

        // Call the listSongsByScore method that prompts the user for the score
        songSearcher.mainMenuLoop();

        // Validate the output for the expected song score
        Assertions.assertTrue((songSearcher.log.contains("Bad Romance")));
    }


    /**
     * Test the functionality of the main menu loop. This method ensures that the frontend's main menu
     * is behaving as expected and responds appropriately without crashing.
     */
    @Test
    public void testMainMenuLoop() {

        // Create a scanner object
        Scanner userInputScanner = new Scanner("1\n" + SUBPATH + "\n4\n");
        // Create a backend object
        BackendInterface backend = new Backend(new IterableMultiKeyRBT());
        // Instantiate and run the frontend object
        Frontend songSearcher = new Frontend(userInputScanner, backend);

        // Call the mainMenuLoop method that prompts the user for the main menu option
        songSearcher.mainMenuLoop();
        // Validate the output for an invalid input message
        Assertions.assertTrue(songSearcher.log.startsWith("Song Searcher app welcomes you!"));
    }

    /**
     * Test the functionality of exiting the application. This method ensures that the frontend can
     * handle an exit command and provide a message to the user.
     */
    @Test
    public void testExit() {

        // Create a scanner object
        Scanner userInputScanner = new Scanner("4\n");
        // Create a backend object
        Backend backend = new Backend(new IterableMultiKeyRBT());
        // Instantiate and run the frontend object
        Frontend songSearcher = new Frontend(userInputScanner, backend);

        // Call the mainMenuLoop method that prompts the user for the main menu option, in this case
        // exit
        // the program
        songSearcher.mainMenuLoop();

        // Validate the output for an exit message
        Assertions.assertTrue(songSearcher.log.contains("Thanks for using Song Searcher!"));
    }

    /**
     * This method checks the functionality of the load data of the integrated backend class to make
     * sure the app works better
     */
    @Test
    public void testIntergrationLoadData() {
        // create an object for IterableMultiKeyRBT
        IterableMultiKeyRBT placeholder = new IterableMultiKeyRBT();

        // instantiating a new backend
        BackendInterface backend = new Backend(placeholder);

        // a valid data path
        String validPath = SUBPATH;

        // checking if the loadData returns true and hence is working correctly
        assertTrue(backend.loadData(validPath), "Unable to load valid file path!");

    }

    /**
     * This method checks the functionality of the load data of the integrated backend class to make
     * sure the app works better
     */
    @Test
    public void testIntegrationGetSongsAboveThreshold() {
        IterableMultiKeyRBT placeholder = new IterableMultiKeyRBT();

        // instantiating a new backend
        BackendInterface backend = new Backend(placeholder);

        // loading the data
        backend.loadData(PATH);

        int threshold = 64;

        ArrayList<SongInterface> songs = (ArrayList<SongInterface>) backend.getSongsAboveThreshold(threshold);

        // goes through every song in songs, and makes sure it is above or equal to the threshold
        for (SongInterface song : songs) {
            assertTrue(song.getDanceability() >= threshold);
        }
    }
}