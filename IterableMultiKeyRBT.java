// --== CS400 Fall 2023 File Header Information ==--
// Name: Ahmad Adi Imran Zuraidi
// Email: zuraidi@wisc.edu
// Group: A02
// TA: Grant Waldow
// Lecturer: Gary Dahl

import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import java.util.Stack;

public class IterableMultiKeyRBT<T extends Comparable<T>> extends RedBlackTree<KeyListInterface<T>> implements IterableMultiKeySortedCollectionInterface<T> {
    public Comparable<T> startPoint; // Starting point for iterations

    public int numKeys; // Number of keys in the tree

    /**
     * Inserts value into tree that can store multiple objects per key by keeping
     * lists of objects in each node of the tree.
     * This method creates a KeyList with the new key, then check if the tree already contains a node with duplicates
     *
     * @param key object to insert
     * @return true if a new node was inserted, false if the key was added into an existing node
     */
    @Override
    public boolean insertSingleKey(T key) {
        KeyListInterface<T> keyList = new KeyList<>(key); // Create a new KeyList with the new key
        if (findNode(keyList) != null) { // If the tree already contains a node with duplicates
            findNode(keyList).data.addKey(key); // Add the new key to the KeyLis(
            numKeys++; // Increment numKeys
            return false;
        }
        else { // If the tree does not contain a node with duplicates
            insert(keyList); // Insert the new KeyList into the tree
            numKeys++; // Increment numKeys
            return true;
        }
    }

    /**
     * @return the number of values in the tree.
     */
    @Override
    public int numKeys() {
        return this.numKeys; // Return numKeys
    }

    /**
     * Returns an iterator that does an in-order iteration over the tree.
     */
    @Override
    public Iterator<T> iterator() {
        Iterator<T> iterator = new Iterator<T>() {
            Stack<Node<KeyListInterface<T>>> stack = getStartStack(); // Create a new stack and set it to the start stack
            Iterator<T> currentIterator = !stack.isEmpty() ? stack.peek().data.iterator() : null; // Create a new iterator and set it to the iterator of the top of the stack
            @Override
            public boolean hasNext() {
                // If the current iterator is null or the current iterator does not have a next element and the stack is empty
                if (currentIterator == null || !currentIterator.hasNext() && stack.isEmpty()){
                    return false;
                }
                // Iterate through the stack
                while (!currentIterator.hasNext()) {
                    if (stack.peek().down[1] != null){ // If the right child of the top of the stack is not null
                        Node<KeyListInterface<T>> currentNode = stack.pop().down[1]; // Pop the top of the stack and set it to the current node
                        while (currentNode != null){ // While the current node is not null
                            stack.push(currentNode); // Push the current node to the stack
                            currentNode = currentNode.down[0]; // Set the current node to the left child of the current node
                        }
                    }
                    else if (stack.size() <= 1) {
                        stack.pop();
                        return false;
                    }
                    else {
                        stack.pop();
                    }
                    currentIterator = stack.peek().data.iterator(); // Set the current iterator to the iterator of the top of the stack
                }
                return true;
            }
            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more elements in the tree"); // Throw a NoSuchElementException
                }
                return currentIterator.next(); // Return the next element of the current iterator
            }
        };
        return iterator; // Return the iterator
    }

    /**
     * helper method for iterator
     * If no iteration start point is set (the field that stores the start point is set to null), the stack is
     * initialized with the nodes on the path from the root node to (and including) the node with the smallest key in
     * the tree. If the iteration start point is set, then the stack is initialized with all the nodes with keys equal
     * to or larger than the start point along the path of the search for the start point.
     * @return
     */
    public Stack getStartStack() {
        Stack<Node<KeyListInterface<T>>> stack = new Stack<>(); // Create a new stack
        Stack<Node<KeyListInterface<T>>> tempStack = new Stack<>(); // Create a new temporary stack
        Node<KeyListInterface<T>> currentNode = root; // Set the current node to the root

        if (root == null) {
            return stack; // Return the stack
        }
        if (startPoint == null) {
            while (currentNode != null) {
                stack.push(currentNode); // Push the current node to the temporary stack
                currentNode = currentNode.down[0]; // Set the current node to the left child of the current node
            }
            return stack; // Return the stack
        }
        else {
            while (currentNode != null || tempStack.size() > 0) {
                while (currentNode != null) {
                    tempStack.push(currentNode); // Push the current node to the temporary stack
                    if (startPoint.compareTo(currentNode.data.iterator().next()) < 0){
                        stack.push(currentNode);
                    }
                    else if (startPoint.compareTo(currentNode.data.iterator().next()) == 0){
                        stack.push(currentNode);
                        return stack;
                    }
                    currentNode = currentNode.down[0]; // Set the current node to the left child of the current node
                }
                currentNode = tempStack.pop(); // Pop the top of the temporary stack and set it to the current node
                if (startPoint.compareTo(currentNode.data.iterator().next()) < 0) {
                    return stack;
                }
                currentNode = currentNode.down[1]; // Set the current node to the right child of the current node
            }
        }
        return stack; // Return the stack
    }

    /**
     * Sets the starting point for iterations. Future iterations will start at the
     * starting point or the key closest to it in the tree. This setting is remembered
     * until it is reset. Passing in null disables the starting point.
     *
     * @param startPoint the start point to set for iterations
     */
    @Override
    public void setIterationStartPoint(Comparable<T> startPoint) {
        this.startPoint = startPoint; // Set the starting point for iterations
    }

    /**
     * Removes all keys and values from the tree.
     */
    @Override
    public void clear() {
        super.clear(); // Call the clear method from RedBlackTree
        numKeys = 0; // Reset numKeys
    }
    /**
     * Junit test for insertSingleKey
     */
    @Test
    public void testInsertSingleKey(){
        IterableMultiKeyRBT<Integer> tree = new IterableMultiKeyRBT<>(); // Create a new IterableMultiKeyRBT
        tree.insertSingleKey(1); // Insert 1
        Iterator<Integer> iterator = tree.iterator(); // Create a new iterator
        assertEquals(1, iterator.next()); // Check if the next element is 1
        assertFalse(iterator.hasNext()); // Check if the iterator has a next element
        assertEquals(1, tree.numKeys()); // Check if numKeys is 1
        assertEquals(1, tree.size()); // Check if size is 1
    }

    /**
     * Junit test for numKeys
     */
    @Test
    public void testNumKeys(){
        IterableMultiKeyRBT<Integer> tree = new IterableMultiKeyRBT<>(); // Create a new IterableMultiKeyRBT
        assertEquals(0, tree.numKeys()); // Check if numKeys is 0
        tree.insertSingleKey(1); // Insert 1
        assertEquals(1, tree.numKeys()); // Check if numKeys is 1
        tree.insertSingleKey(2); // Insert 2
        assertEquals(2, tree.numKeys()); // Check if numKeys is 2
        tree.insertSingleKey(3); // Insert 3
        assertEquals(3, tree.numKeys()); // Check if numKeys is 3
    }

    /**
     * Junit test for setIterationStartPoint
     */
    @Test
    public void testSetIterationStartPoint(){
        IterableMultiKeyRBT<Integer> tree = new IterableMultiKeyRBT<>(); // Create a new IterableMultiKeyRBT
        tree.insertSingleKey(1);
        tree.insertSingleKey(2);
        tree.insertSingleKey(3);
        tree.insertSingleKey(4);

        tree.setIterationStartPoint(2); // Set the starting point to 2
        assertEquals(2, tree.iterator().next()); // Check if the next element is 2
        tree.setIterationStartPoint(3); // Set the starting point to 3
        assertEquals(3, tree.iterator().next()); // Check if the next element is 3
        tree.setIterationStartPoint(4); // Set the starting point to 4
        assertEquals(4, tree.iterator().next()); // Check if the next element is 4
    }
}