// --== CS400 Fall 2023 File Header Information ==--
// Name: Ahmad Adi Imran Zuraidi
// Email: zuraidi@wisc.edu
// Group: A02
// TA: Grant Waldow
// Lecturer: Gary Dahl

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BackendDeveloperTests {

    /**
     * This method tests the Song constructor using the getter methods.
     */
    @Test
    public void testSongInterface() {
        // Creating a song object
        Song s1 = new Song( "Hey, Soul Sister","Train", 2010, "neo mellow", 67);

        // Testing the getters of the song
        assertEquals("Train", s1.getArtist(), "Artist name is not correct");
        assertEquals("Hey, Soul Sister", s1.getTitle(), "Song title is not correct");
        assertEquals(2010, s1.getYear(), "Song year is not correct");
        assertEquals("neo mellow", s1.getGenre(), "Song genre is not correct");
        assertEquals(67, s1.getDanceability(), "Song danceability score is not correct");

        // Creating a second song object
        Song s2 = new Song( "Love The Way You Lie","Eminem", 2010, "detroit hip hop", 75);

        // Testing the getters of the song
        assertEquals("Eminem", s2.getArtist(), "Artist name is not correct");
        assertEquals("Love The Way You Lie", s2.getTitle(), "Song title is not correct");
        assertEquals(2010, s2.getYear(), "Song year is not correct");
        assertEquals("detroit hip hop", s2.getGenre(), "Song genre is not correct");
        assertEquals(75, s2.getDanceability(), "Song danceability score is not correct");

        // Creating a third song object
        Song s3 = new Song( "TiK ToK","Kesha", 2010, "dance pop", 76);

        // Testing the getters of the song
        assertEquals("Kesha", s3.getArtist(), "Artist name is not correct");
        assertEquals("TiK ToK", s3.getTitle(), "Song title is not correct");
        assertEquals(2010, s3.getYear(), "Song year is not correct");
        assertEquals("dance pop", s3.getGenre(), "Song genre is not correct");
        assertEquals(76, s3.getDanceability(), "Song danceability score is not correct");

        // Testing the compareTo method
        assertTrue(s3.compareTo(s2) == 1, "CompareTo method is broken");
        assertTrue(s1.compareTo(s2) == -8, "CompareTo method is broken");

        // Testing equals method
        Song s3Duplicate = new Song("TiK ToK","Kesha", 2010, "dance pop", 76);
        assertEquals(true, s3.equals(s3Duplicate));

        Song s2Wrong = new Song ("Bob", "Bob", 2023, "dance pop", 75);
        assertEquals(false, s2.equals(s2Wrong));
    }

    /**
     *Test the loadData method of BackendInterface
     */
    @Test
    public void testloadData(){
        IterableMultiKeyRBT placeholder = new IterableMultiKeyRBT(); //placeholder object
        BackendInterface data = new Backend(placeholder); //backend object
        String validPath = "src/songs.csv"; //valid file path
        assertTrue(data.loadData(validPath), "Unable to load valid file path!"); //check if loadData returns true
    }

    /**
     *Test the getAverageDanceability method of BackendInterface
     */
    @Test
    public void testGetAverageDanceability(){
        IterableMultiKeyRBT placeholder = new IterableMultiKeyRBT(); //placeholder object
	    BackendInterface data = new Backend(placeholder); //backend object
        data.loadData("src/songSubset.csv"); //load data
        double average = data.getAverageDanceability(); //get average danceability
        double expectedAverage = 65.2; //expected average
        assertEquals(average, expectedAverage); //check if average is equal to expected average
    }

    /**
     *Test the getSongsAboveThreshold method of BackendInterface
     */
    @Test
    public void testGetSongsAboveThreshold(){
        IterableMultiKeyRBT placeholder = new IterableMultiKeyRBT(); //placeholder object
        BackendInterface data = new Backend(placeholder); //backend object
        data.loadData("src/songs.csv"); //load data
        int threshold = 70; //threshold
        List<SongInterface> songs = data.getSongsAboveThreshold(threshold); //get songs above threshold
        for (SongInterface song : songs) { //for each song in songs
            assertTrue(song.getDanceability() >= threshold); //check if danceability is above threshold
        }
    }

    /**
     * test if getSongsAboveThreshold() returns an empty list when given an invalid threshold
     */
    @Test
    public void testGetSongsAboveInvalidThreshold() {
        IterableMultiKeyRBT placeholder = new IterableMultiKeyRBT();

        //instantiating a new backend
        Backend backend = new Backend(placeholder);

        //loading the data from a subset with no songs equal to or above 95
        backend.loadData("src/SmallSampleData.csv");

        int invalidThreshold = 95;

        ArrayList<SongInterface> songs = backend.getSongsAboveThreshold(invalidThreshold);

        //checking if it is empty
        assertTrue(songs.isEmpty());
    }

    /**
     * Test if loadData() returns false when given an invalid file path
     */
    @Test
    public void testLoadDataInvalidPath(){
        IterableMultiKeyRBT placeholder = new IterableMultiKeyRBT(); //placeholder object
        BackendInterface data = new Backend(placeholder); //backend object
        assertFalse(data.loadData("invalidPath.csv"), "Loaded invalid file path!"); //check if loadData returns false
    }

    /**
     * Test if showAvgScore() returns the correct average score
     */
    @Test
    public void testShowAvgScoreIntegration(){
        IterableMultiKeyRBT placeholder = new IterableMultiKeyRBT(); //placeholder object
        BackendInterface data = new Backend(placeholder); //backend object

        Scanner scnr = new Scanner("1\n" + "src/songSubset.csv" + "\n3\n4\n"); //scanner object
        Frontend frontend = new Frontend(scnr, data); //frontend object

        frontend.mainMenuLoop(); //main menu loop

        assertTrue(frontend.log.contains("The average danceability score is: 65.2")); //check if average score is correct
    }

    /**
     * Test if listSongsByScore() returns the correct list of songs
     **/
    @Test
    public void testLoadFileIntegration(){
        IterableMultiKeyRBT placeholder = new IterableMultiKeyRBT(); //placeholder object
        BackendInterface data = new Backend(placeholder); //backend object

        Scanner scnr = new Scanner("1\n" + "src/songSubset.csv" + "\n4\n"); //scanner object
        Frontend frontend = new Frontend(scnr, data); //frontend object

        frontend.mainMenuLoop(); //main menu loop

        assertTrue(frontend.log.contains("Loaded the file successfully!")); //check if file is loaded successfully
    }
}
