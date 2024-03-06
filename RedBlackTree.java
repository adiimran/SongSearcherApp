// --== CS400 Fall 2023 File Header Information ==--
// Name: Ahmad Adi Imran Zuraidi
// Email: zuraidi@wisc.edu
// Group: A02
// TA: Grant Waldow
// Lecturer: Gary Dahl

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Red-Black Tree implementation that extends BinarySearchTree.
 * This class is generic and accepts a type parameter with the same restrictions as BinarySearchTree,
 * passing it on to the BinarySearchTree class it extends.
 */
public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    /**
     * Extended node class for Red-Black Tree.
     * Stores the color for each node in addition to the node's parent, children, and data.
     */
    protected static class RBTNode<T> extends Node<T> {
        public int blackHeight = 0;

        public RBTNode(T data) {
            super(data);
        }

        public RBTNode<T> getUp() {
            return (RBTNode<T>) this.up;
        }

        public RBTNode<T> getDownLeft() {
            return (RBTNode<T>) this.down[0];
        }

        public RBTNode<T> getDownRight() {
            return (RBTNode<T>) this.down[1];
        }
    }

    // Constructor for RedBlackTree
    public RedBlackTree() {
        // Call the constructor of the superclass (BinarySearchTree)
        super();
    }

    /**
     * Enforces Red-Black Tree properties after inserting a new red node.
     * Resolves any red property violations introduced by the insertion.
     * @param newNode a reference to the newly added red node
     */
    /**
     * Enforces the Red-Black Tree properties after an insertion.
     * @param newNode The newly inserted node
     */
    protected void enforceRBTreePropertiesAfterInsert(RBTNode<T> newNode) {
        // Ensure that the root has a black height of 1
        if (newNode == this.root) {
            newNode.blackHeight = 1; // Set the root to black
            return;
        }

        RBTNode<T> parent = newNode.getUp(); // Parent of the new node
        if (parent == null || parent.blackHeight == 1) {
            return;
        }
        RBTNode<T> grandparent = parent.getUp(); // Grandparent of the new node
        if (grandparent == null) {
            return;
        }
        RBTNode<T> uncle; // Uncle of the new node
        if (grandparent.getDownLeft() == parent) {
            uncle = grandparent.getDownRight();
        } else {
            uncle = grandparent.getDownLeft();
        }

        // Case 1: The parent and uncle of the new node are both red
        if (uncle != null && uncle.blackHeight == 0) {
            parent.blackHeight = 1; // Set the parent to black
            uncle.blackHeight = 1; // Set the uncle to black
            grandparent.blackHeight = 0; // Set the grandparent to red
            enforceRBTreePropertiesAfterInsert(grandparent); // Recursively enforce the properties for the grandparent
            return;
        }

        // Left side
        if (grandparent.getDownLeft() == parent) {
            if (parent.getDownLeft() == newNode) {
                // Case 2: The parent is red and the uncle is black, and the new node is the left child of the parent
                if (parent.getUp() == grandparent) {
                    this.rotate(parent, grandparent); // Rotate the parent and grandparent
                    parent.blackHeight = 1; // Set the parent to black
                    grandparent.blackHeight = 0; // Set the grandparent to red
                }
            } else if (parent.getDownRight() == newNode) {
                // Left-right case
                if (newNode.getUp() == parent && parent.getUp() == grandparent) {
                    this.rotate(newNode, parent); // Rotate the new node and its parent
                    this.rotate(newNode, grandparent); // Rotate the new node and its grandparent
                    newNode.blackHeight = 1; // Set the new node to black
                    grandparent.blackHeight = 0; // Set the grandparent to red
                }
            }
        }
        // Right side
        else if (grandparent.getDownRight() == parent){
            if (parent.getDownRight() == newNode){
                // Right-right case
                if (parent.getUp() == grandparent) {
                    this.rotate(parent, grandparent); // Rotate the parent and grandparent
                    parent.blackHeight = 1; // Set the parent to black
                    grandparent.blackHeight = 0; // Set the grandparent to red
                }
            }
            // Left side
            else if (parent.getDownLeft() == newNode) {
                // Right-left case
                if (newNode.getUp() == parent && parent.getUp() == grandparent) {
                    this.rotate(newNode, parent); // Rotate the new node and its parent
                    this.rotate(newNode, grandparent); // Rotate the new node and its grandparent
                    newNode.blackHeight = 1; // Set the new node to black
                    grandparent.blackHeight = 0; // Set the grandparent to red
                }
            }
        }
    }

    /**
     * Overrides the insert method of BinarySearchTree for Red-Black Tree.
     * Instantiates a new RBTNode<T> with the key passed in and inserts it into the tree.
     * After the node is successfully inserted, calls enforceRBTreePropertiesAfterInsert for this new node.
     * After the call to enforceRBTreePropertiesAfterInsert, sets the root node of the tree to black.
     * @param data to be added into this red-black tree
     * @return true if the value was inserted, false if it was in the tree already
     * @throws NullPointerException when the provided data argument is null
     */
    @Override
    public boolean insert(T data) throws NullPointerException {
        if (data == null)
            throw new NullPointerException("Cannot insert data value null into the tree.");

        RBTNode<T> newNode = new RBTNode<>(data); // Create a new node with the data
        boolean inserted = super.insertHelper(newNode); // Insert the new node into the tree

        // Enforce Red-Black Tree properties after insertion
        if (inserted) {
            enforceRBTreePropertiesAfterInsert(newNode);
            // Set the root node to black after enforcing Red-Black Tree properties
            if (root instanceof RBTNode) {
                ((RBTNode<T>) root).blackHeight = 1; // Set the root node to black
            }
        }

        return inserted;
    }

    @Test
    public void testNodeColorAfterInsertCase1() {
        // Case 1: Parent and uncle are both red
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(7);

        // Verify node colors
        assertEquals(1, ((RedBlackTree.RBTNode<Integer>) tree.root).blackHeight);
        assertEquals(1, ((RedBlackTree.RBTNode<Integer>) tree.root.down[0]).blackHeight);
        assertEquals(1, ((RedBlackTree.RBTNode<Integer>) tree.root.down[1]).blackHeight);
        assertEquals(0, ((RedBlackTree.RBTNode<Integer>) tree.root.down[0].down[1]).blackHeight);
    }

    @Test
    public void testNodeColorAfterInsertCase2() {
        // Case 2: Parent is black, uncle is black, and newNode is the right child
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(7);

        // Verify node colors
        assertEquals(1, ((RedBlackTree.RBTNode<Integer>) tree.root).blackHeight);
        assertEquals(1, ((RedBlackTree.RBTNode<Integer>) tree.root.down[0]).blackHeight);
        assertEquals(1, ((RedBlackTree.RBTNode<Integer>) tree.root.down[1]).blackHeight);
        assertEquals(0, ((RedBlackTree.RBTNode<Integer>) tree.root.down[0].down[1]).blackHeight);
    }

    @Test
    public void testNodeColorAfterInsertCase3() {
        // Case 3: Parent is black, uncle is black, and newNode is the left child
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.insert(10);
        tree.insert(7);
        tree.insert(13);
        tree.insert(15);

        // Verify node colors
        assertEquals(1, ((RedBlackTree.RBTNode<Integer>) tree.root).blackHeight);
        assertEquals(1, ((RedBlackTree.RBTNode<Integer>) tree.root.down[0]).blackHeight);
        assertEquals(1, ((RedBlackTree.RBTNode<Integer>) tree.root.down[1]).blackHeight);
        assertEquals(0, ((RedBlackTree.RBTNode<Integer>) tree.root.down[1].down[1]).blackHeight);
    }
}

