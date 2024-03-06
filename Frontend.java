import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

/**
 * This class serves as the user interface for the Song Searcher application, providing a
 * text-based menu for interaction with the backend.
 *
 * @author nikhil gudladana
 */
public class Frontend implements FrontendInterface {

  private File dataFile; //file containing the data
  private Scanner userInputScanner; //scanner for user input
  private BackendInterface backendReference; //reference to the backend

  //this field keeps track of the log created by the output of this file for testing
  public String log; //this field keeps track of the log created by the output of this file for testing

  /**
   * Constructor for the Frontend class.
   *
   * @param scanner   Scanner for user input
   * @param reference Reference to the backend
   */
  public Frontend(Scanner scanner, BackendInterface reference) {
    this.userInputScanner = scanner; //initializing the scanner
    this.backendReference = reference; //initializing the backend reference
    this.log = ""; //initializing the log
  }

  /**
   * Main menu loop for the Song Searcher application.
   */
  public void mainMenuLoop() {
    int userChoice; //user's choice from the main menu
    do {
      System.out.println("Song Searcher app welcomes you!");
      this.log += ("Song Searcher app welcomes you!"); //adding to the log

      System.out.println("Please choose from the following options:");
      System.out.println("1. Load data file");
      System.out.println("2. List songs by danceability score");
      System.out.println("3. Show average danceability score");
      System.out.println("4. Exit");

      System.out.print("Enter your choice (1-4): ");
      userChoice = userInputScanner.nextInt(); //getting user's choice
      userInputScanner.nextLine(); // Consume the newline character

      if (userChoice < 1 || userChoice > 4) { //if user's choice is invalid
        System.out.println("Invalid choice. Please enter a number between 1 and 4 only!");
      }
    } while (userChoice < 1 || userChoice > 4); //looping until user enters a valid choice

    System.out.println("You selected option " + userChoice + "\n"); //printing the user's choice
    processUserChoice(userChoice); //processing the user's choice
  }

  /**
   * Process the user's choice based on the main menu selection.
   *
   * @param choice User's choice from the main menu
   */
  public void processUserChoice(int choice) {
    if (choice == 1) { //if user chooses to load data
      loadFile();
    } else if (choice == 2) { //if user chooses to list songs by score
      listSongsByScore();
    } else if (choice == 3) { //if user chooses to show average score
      showAvgScore();
    } else if (choice == 4) { //if user chooses to exit
      exitApp();
    }
  }

  /**
   * Load music data from a user-specified file.
   */
  public void loadFile() { // loading the file
    String filePath;
    boolean validInput = false;

    do { //looping until user enters a valid file path
      System.out.println("Enter the file path: ");
//            filePath = "src/songs.csv"; // Placeholder file path
      filePath = this.userInputScanner.nextLine().strip(); //getting the file path from the user

      validInput = !backendReference.loadData(filePath); //loading the file and checking if it is valid
    } while (validInput); //looping until user enters a valid file path

    //adding to the log3
    this.log += "\nLoaded the file successfully!";
    System.out.println("Loaded the file successfully!");

    //restarting the main menu loop
    this.mainMenuLoop();
  }

  /**
   * List songs with danceability scores above a user-specified threshold.
   */
  public void listSongsByScore() { //listing songs by score
    int danceabilityThreshold; //threshold for danceability score
    do { //looping until user enters a valid threshold
      System.out.print("Enter the minimum danceability score (0-100): ");
      danceabilityThreshold = userInputScanner.nextInt();
      userInputScanner.nextLine(); // Consume the newline character

      if (danceabilityThreshold < 0 || danceabilityThreshold > 100) {
        this.log += "Invalid input. Please enter a numbe between 0 and 100 only!";
        System.out.println("Invalid input. Please enter a number between 0 and 100 only!");
      }
    } while (danceabilityThreshold < 0 || danceabilityThreshold > 100);

    ArrayList<SongInterface> songsAboveThreshold = (ArrayList<SongInterface>) backendReference.getSongsAboveThreshold(danceabilityThreshold);

    System.out.println("Songs with danceability scores above " + danceabilityThreshold + ":");

    //adding to the log
    this.log += ("Songs with danceability scores above " + danceabilityThreshold + ":");

    for (SongInterface song : songsAboveThreshold) {
      System.out.println(song.toString());
      this.log += song.toString();
    }

    //restarting the main menu loop
    this.mainMenuLoop();
  }

  /**
   * Display the average danceability score of all songs.
   */
  public void showAvgScore() {
    double averageDanceability = backendReference.getAverageDanceability();
    this.log += ("The average danceability score is: " + averageDanceability);
    System.out.println("The average danceability score is: " + averageDanceability);

    //restarting the main menu loop
    this.mainMenuLoop();
  }

  /**
   * Exit the Song Searcher application.
   */
  public void exitApp() {
    this.log += "\nThanks for using Song Searcher!";
    System.out.println("Thanks for using Song Searcher!");
  }
}