import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class KeyListSong implements KeyListInterface {

    //data is a list of type Song
    ArrayList<Song> data;

    //the danceability score that this KeyListSong is for
    int danceability;

    public KeyListSong(Song song) {

        //when a new KeyListSong is created, we create a new data and add song to it
        this.data = new ArrayList<>();
        this.data.add(song);

        //we also set the danceability, this is only set once at the start
        this.danceability = song.getDanceability();
    }

    /**
     * Accessor method for danceability
     * @return the danceability score of this KeyListSong
     */
    public int getDanceability(){
        return this.danceability;
    }

    /**
     * @param newKey new object that maps to the same key as all objects in the list
     */
    @Override
    public void addKey(Comparable newKey) {

        //we only add the song if it is an instance of Song
        if (newKey instanceof Song) {
            this.data.add((Song) newKey);
        } else {
            throw new IllegalArgumentException("Not a Song!");
        }
    }

    /**
     * @param key the key object to check for
     * @return
     */
    @Override
    public boolean containsKey(Comparable key) {

        //if key is of type Song, we use contains(), otherwise we return false straight away
        if (key instanceof Song) {
            return data.contains(key);
        }
        return false;
    }

    /**
     * Comparing the danceability score of this keylistsong and another KeyListSong passed
     * @param o
     * @return comparison value > 0 if this is greaater, = 0 if same,  < 0 if this is smaller
     */
    @Override
    public int compareTo(Object o) {
        if (!(o instanceof KeyListSong)) {
            throw new IllegalArgumentException("Not a Song!");
        }

        KeyListSong song = (KeyListSong) o;

        //comparing the danceability values
        return this.danceability - song.getDanceability();
    }

    /**
     * @return
     */
    @Override
    public Iterator iterator() {
        return new KeyListSongIterator();
    }

    /**
     * KeyListSong iterator
     */
    private class KeyListSongIterator implements Iterator<Song> {

        //keep track of current index
        private int index = 0;

        //there is a next element only if the current index is less than the size
        @Override
        public boolean hasNext() {
            return index < data.size();
        }

        //get the next song, increment index, and return it
        @Override
        public Song next() {
            if (hasNext()) {
                return data.get(index++);
            } else {
                throw new NoSuchElementException();
            }
        }
    }
}
