// --== CS400 Fall 2023 File Header Information ==--
// Name: Ahmad Adi Imran Zuraidi
// Email: zuraidi@wisc.edu
// Group: A02
// TA: Grant Waldow
// Lecturer: Gary Dahl

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Placeholder implements IterableMultiKeySortedCollectionInterface {
    private Hashtable<Integer, KeyListSong> keyLists; // map danceability score to KeyListSong objects
    int size; // keep track of total number of songs in this iterator

    /**
     * Constructs a new Placeholder object.
     */
    public Placeholder() {
        //instantiating keyLists to an empty Hashtable
        keyLists = new Hashtable<>();

        //initializing size to 0 for a new Placeholder class
        this.size = 0;
    }

    /**
     * This method is not currently implemented.
     * (Not using this rn)
     * @param key object to insert
     * @return Always returns false.
     */
    @Override
    public boolean insertSingleKey(Comparable key) {
        return false;
    }

    /**
     * This method is not currently implemented.
     * (Not using this rn)
     * @return Always returns 0.
     */
    @Override
    public int numKeys() {
        return 0;
    }

    /**
     * Returns an iterator that iterates through all songs in every KeyListSong.
     * @return An iterator for songs within this collection.
     */
    @Override
    public Iterator iterator() {
        return new PlaceholderIterator();
    }

    /**
     * Sets the start point for iterations. This method is not currently implemented.
     * @param startPoint The start point for iterations.
     */
    @Override
    public void setIterationStartPoint(Comparable startPoint) {
        //nothing for now
    }

    /**
     * Inserts data into the collection.
     * @param data The data to be inserted.
     * @return True if the insertion was successful, false otherwise.
     * @throws NullPointerException If the data is null.
     * @throws IllegalArgumentException If the data is not a Song.
     */
    @Override
    public boolean insert(Comparable data) throws NullPointerException, IllegalArgumentException {
        if (data instanceof Song) {
            //increasing the size field as a new element is inserted
            this.size += 1;

            //casting the data into a Song type song variable
            Song song = (Song) data;
            int danceability = song.getDanceability();

            //checking if the current danceability already has a relevant KeyListSong mapped to it
            KeyListSong keyList = keyLists.get(danceability);

            //if it doesn't we create a new one, add this song to it and add it into the hashtable
            if (keyList == null) {
                keyList = new KeyListSong(song);
                keyLists.put(danceability, keyList);
            } else {
                //otherwise, we just add this song to the existing keylistsong for this danceability
                keyList.addKey((Comparable) song);
            }
            return true;
        } else {
            //only type Song can be inserted
            throw new IllegalArgumentException("Not a Song!");
        }
    }

    /**
     * Checks if the collection contains the specified data.
     * @param data The data to check for.
     * @return True if the data is found in the collection, false otherwise.
     */
    @Override
    public boolean contains(Comparable data) {

        //makes sure data is of Song
        if (data instanceof Song) {
            Song song = (Song) data;
            int danceability = song.getDanceability();
            KeyListSong keyList = keyLists.get(danceability);

            if (keyList != null) {
                return keyList.containsKey((Comparable)song);
            }
        }

        //returns false if data is not of Song or the data is not there
        return false;
    }

    /**
     * Returns the number of songs in the collection.
     * @return The number of songs in the collection.
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Checks if the collection is empty.
     * @return True if the collection is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return keyLists.isEmpty();
    }

    /**
     * Clears the collection by removing all elements.
     */
    @Override
    public void clear() {
        keyLists.clear();
    }

    private class PlaceholderIterator implements Iterator<Song> {

        //enumeration of KeyListSong
        private Enumeration<KeyListSong> keyListEnum;

        //current iterator being used since we have nested iterators
        private Iterator<Song> currentIterator;

        /**
         * Constructs a new PlaceholderIterator.
         */
        public PlaceholderIterator() {

            //setting an enumeration of type KeyListSong to the elements of the hashtable
            keyListEnum = keyLists.elements();

            //setting the next required iterator
            currentIterator = getNextIterator();
        }

        /**
         * Returns the next iterator from KeyListSong
         * @return next iterator to use
         */
        private Iterator<Song> getNextIterator() {
            while (keyListEnum.hasMoreElements()) {
                KeyListSong keyList = keyListEnum.nextElement();
                Iterator<Song> iterator = keyList.iterator();
                if (iterator.hasNext()) {
                    return iterator;
                }
            }
            return null;
        }

        /**
         * Checks if there are more songs to iterate.
         * @return True if there are more songs, false otherwise.
         */
        @Override
        public boolean hasNext() {
            return currentIterator != null && currentIterator.hasNext();
        }

        /**
         * Gets the next song in the iteration.
         * @return The next song in the iteration.
         * @throws NoSuchElementException If there are no more songs to iterate.
         */
        @Override
        public Song next() {

            //throw an exception is there are no more songs to iterate over
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            //uses the current iterator from the current keylist to get the next song
            Song nextSong = currentIterator.next();

            //if the current iterator is now empty, we change up and use the next keylist's iterator
            if (!currentIterator.hasNext()) {
                currentIterator = getNextIterator();
            }

            return nextSong;
        }
    }
}
