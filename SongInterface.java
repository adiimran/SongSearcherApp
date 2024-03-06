// --== CS400 Fall 2023 File Header Information ==--
// Name: Ahmad Adi Imran Zuraidi
// Email: zuraidi@wisc.edu
// Group: A02
// TA: Grant Waldow
// Lecturer: Gary Dahl

/**
 * Interface for an individual song
 * @author Adish Jain, Learoy Daryl Joseph, Ahmad Adi Imran
 */
public interface SongInterface<T>  {

//  /**
//   * Constructor for creating a song with the specified properties.
//   * @param artist The artist of the song.
//   * @param title The title of the song.
//   * @param year The year the song was released.
//   * @param genre The genre of the song.
//   * @param danceability The danceability score of the song.
//   */
//  SongInterface(String artist, String title, int year, String genre, int danceability);

  /**
   * Sets the song's title
   * @param title title of the song
   */
  public void setTitle(String title);

  /**
   * Sets the song's artist
   * @param artist name of the song's artist
   */
  public void setArtist(String artist);

  /**
   * Sets the song's release year
   * @param year release year for the song
   */
  public void setYear(int year);

  /**
   * Sets the song's genre
   * @param genre genre of the song
   */
  public void setGenre(String genre);

  /**
   * Sets the song's danceability score
   * @param danceability danceability score of the song
   */
  public void setDanceability(int danceability);

  /**
   * Get the song's title
   * @return Song title
   */
  public String getTitle();

  /**
   * Get the song's artist
   * @return Song artist
   */
  public String getArtist();

  /**
   * Get the song's release year
   * @return Song release year
   */
  public int getYear();

  /**
   * Get the song's genre
   * @return Song genre
   */
  public String getGenre();

  /**
   * Get the song's danceability score
   * @return Song danceability score
   */
  public int getDanceability();

  /**
   * Converts the song to a string
   * @return String representation of the song
   */
  public String toString();

}

