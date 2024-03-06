// --== CS400 Fall 2023 File Header Information ==--
// Name: Ahmad Adi Imran Zuraidi
// Email: zuraidi@wisc.edu
// Group: A02
// TA: Grant Waldow
// Lecturer: Gary Dahl

public class Song<T> implements SongInterface, Comparable<T>{
    private String title; //song title
    private String artist; //song artist
    private int danceability; //song danceability score
    private String genre; //song genre
    private int year; //song release year

    /**
     * Constructor for creating a song with the specified properties.
     * @param title
     * @param artist
     * @param year
     * @param genre
     * @param danceability
     */
    public Song(String title, String artist, int year, String genre, int danceability){
        this.title = title; //sets the song's title
        this.artist = artist; //sets the song's artist
        this.year = year; //sets the song's release year
        this.genre = genre; //sets the song's genre
        this.danceability = danceability; //sets the song's danceability score
    }

    /**
     * Sets the song's title
     * @param title
     */
    public void setTitle(String title){
        this.title = title; //sets the song's title
    }

    /**
     * Get the song's title
     * @return
     */
    @Override
    public String getTitle(){
        return title; //returns the song's title
    }

    /**
     * Sets the song's artist
     * @param artist
     */
    @Override
    public void setArtist(String artist){
        this.artist = artist; //sets the song's artist
    }

    /**
     * Get the song's artist
     * @return
     */
    @Override
    public String getArtist(){
        return artist; //returns the song's artist
    }

    /**
     * Sets the song's danceability score
     * @param danceability
     */
    @Override
    public void setDanceability(int danceability){
        this.danceability = danceability; //sets the song's danceability score
    }

    /**
     * Get the song's danceability score
     * @return
     */
    @Override
    public int getDanceability(){
        return danceability; //returns the song's danceability score
    }

    /**
     * Sets the song's genre
     * @param genre
     */
    @Override
    public void setGenre(String genre){
        this.genre = genre; //sets the song's genre
    }

    /**
     * Get the song's genre
     * @return
     */
    @Override
    public String getGenre(){
        return genre; //returns the song's genre
    }

    /**
     * Sets the song's release year
     * @param year
     */
    @Override
    public void setYear(int year){
        this.year = year; //sets the song's release year
    }
    /**
     * Get the song's release year
     * @return Song release year
     */
    @Override
    public int getYear(){
        return year; //returns the song's release year
    }

    /**
     * Compares two songs' danceability
     * @param song
     * @return
     */
    @Override
    public int compareTo(T song){
        //checks if the song is of type song
        if(song instanceof Song){
            Song mySong = (Song) song;
            return this.danceability - mySong.danceability; //returns the difference between the two songs' danceability scores
        }
        else{
            throw new IllegalArgumentException("song is not of type song"); //throws an exception if the song is not of type song
        }
    }

    /**
     * Checks if two songs are equal
     * @param song
     * @return true if the songs are equal, false otherwise
     */
    @Override
    public boolean equals(Object song){
        //checks if the two songs' title, artist, genre, year, and danceability score are equal
        if(!(song instanceof Song)){
            throw new IllegalArgumentException("song is not instance of song");
        }

        try{
            Song mySong = (Song) song;
            //if song is null, then we return false
            if (mySong == null){
                return false;
            }

            //returning true if all of these are true, false if any of these are false
            return this.title.equals(mySong.title) &&
                    this.artist.equals(mySong.artist) &&
                    this.genre.equals(mySong.genre) &&
                    this.danceability == mySong.danceability &&
                    this.year == mySong.year;
        }
        catch(Exception e){
            return false;
        }
    }

    /**
     * Returns the song's title, artist, and release year
     * @return
     */
    @Override
    public String toString(){
        return this.title + " by " + this.artist + " (" + this.year + ")"; //returns the song's title, artist, and release year
    }
}
