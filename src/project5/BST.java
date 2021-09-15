package project5;
import java.util.*;

/**
 * This Binary Search Tree or BST<T> class acts as the backbone of the MeteoriteData class.
 * It is built off of the provided BST class implementation provided on Ed, with 9 new methods
 * added. It extends the Comparable<T> interface in order to implement the methods that require
 * comparison between objects. It also uses the generic T parameter.
 * @param <T> - generic parameter
 */
public class BST <T extends Comparable<T>> {
    private BSTNode root;   //reference to the root node of the tree
    private int size;       //number of values stored in this tree
    private Comparator<T> comparator;   //comparator object to overwrite the
    // natural ordering of the elements

    private boolean found;  //helper variable used by the remove methods
    private boolean added ; //helper variable used by the add method

    /**
     * Constructs a new, empty tree, sorted according to the natural ordering of its elements.
     */
    public BST () {
        root = null;
        size = 0;
        comparator = null;
    }

    /**
     * Constructs a new, empty tree, sorted according to the specified comparator.
     */
    public BST(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    /**
     * Adds the specified element to this tree if it is not already present.
     * If this tree already contains the element, the call leaves the
     * tree unchanged and returns false.
     * @param data element to be added to this tree
     * @return true if this tree did not already contain the specified element
     * @throws NullPointerException if the specified element is null
     */
    public boolean add ( T data ) {
        added = false;
        if (data == null) return added;
        //replace root with the reference to the tree after the new
        //value is added
        root = add (data, root);
        //update the size and return the status accordingly
        if (added) size++;
        return added;
    }

    /*
     * Actual recursive implementation of add.
     *
     * This function returns a reference to the subtree in which
     * the new value was added.
     *
     * @param data element to be added to this tree
     * @param node node at which the recursive call is made
     */
    private BSTNode add (T data, BSTNode node ) {
        if (node == null) {
            added = true;
            return new BSTNode(data);
        }
        //decide how comparisons should be done
        int comp = 0 ;
        if (comparator == null ) //use natural ordering of the elements
            comp = node.data.compareTo(data);
        else                     //use the comparator
            comp = comparator.compare(node.data, data ) ;

        //find the location to add the new value
        if (comp > 0 ) { //add to the left subtree
            node.left = add(data, node.left);
        }
        else if (comp < 0 ) { //add to the right subtree
            node.right = add(data, node.right);
        }
        else { //duplicate found, do not add
            added = false;
            return node;
        }
        return node;
    }


    /**
     * Removes the specified element from this tree if it is present.
     * Returns true if this tree contained the element (or equivalently,
     * if this tree changed as a result of the call).
     * (This tree will not contain the element once the call returns.)
     * @param target object to be removed from this tree, if present
     * @return true if this set contained the specified element
     * @throws NullPointerException if the specified element is null
     */
    public boolean remove(T target)
    {
        //replace root with a reference to the tree after target was removed
        root = recRemove(target, root);
        //update the size and return the status accordingly
        if (found) size--;
        return found;
    }


    /*
     * Actual recursive implementation of remove method: find the node to remove.
     *
     * This function recursively finds and eventually removes the node with the target element
     * and returns the reference to the modified tree to the caller.
     *
     * @param target object to be removed from this tree, if present
     * @param node node at which the recursive call is made
     */
    private BSTNode recRemove(T target, BSTNode node)
    {
        if (node == null)  { //value not found
            found = false;
            return node;
        }

        //decide how comparisons should be done
        int comp = 0 ;
        if (comparator == null ) //use natural ordering of the elements
            comp = target.compareTo(node.data);
        else                     //use the comparator
            comp = comparator.compare(target, node.data ) ;


        if (comp < 0)       // target might be in a left subtree
            node.left = recRemove(target, node.left);
        else if (comp > 0)  // target might be in a right subtree
            node.right = recRemove(target, node.right );
        else {          // target found, now remove it
            node = removeNode(node);
            found = true;
        }
        return node;
    }

    /*
     * Actual recursive implementation of remove method: perform the removal.
     *
     * @param target the item to be removed from this tree
     * @return a reference to the node itself, or to the modified subtree
     */
    private BSTNode removeNode(BSTNode node)
    {
        T data;
        if (node.left == null)   //handle the leaf and one child node with right subtree
            return node.right ;
        else if (node.right  == null)  //handle one child node with left subtree
            return node.left;
        else {                   //handle nodes with two children
            data = getPredecessor(node.left);
            node.data = data;
            node.left = recRemove(data, node.left);
            return node;
        }
    }

    /*
     * Returns the information held in the rightmost node of subtree
     *
     * @param subtree root of the subtree within which to search for the rightmost node
     * @return returns data stored in the rightmost node of subtree
     */
    private T getPredecessor(BSTNode subtree)
    {
        if (subtree==null) //this should not happen
            throw new NullPointerException("getPredecessor called with an empty subtree");
        BSTNode temp = subtree;
        while (temp.right  != null)
            temp = temp.right ;
        return temp.data;
    }


    /**
     * Returns the number of elements in this tree.
     * @return the number of elements in this tree
     */
    public int size() {
        return size;
    }


    public String toStringTree( ) {
        StringBuffer sb = new StringBuffer();
        toStringTree(sb, root, 0);
        return sb.toString();
    }
    //uses preorder traversal to display the tree
    //WARNING: will not work if the data.toString returns more than one line
    private void toStringTree( StringBuffer sb, BSTNode node, int level ) {
        //display the node
        if (level > 0 ) {
            for (int i = 0; i < level-1; i++) {
                sb.append("   ");
            }
            sb.append("|--");
        }
        if (node == null) {
            sb.append( "->\n");
            return;
        }
        else {
            sb.append( node.data + "\n");
        }

        //display the left subtree
        toStringTree(sb, node.left, level+1);
        //display the right subtree
        toStringTree(sb, node.right, level+1);
    }


    /*
     * Node class for this BST
     */
    private class BSTNode implements Comparable < BSTNode > {

        T data;
        BSTNode  left;
        BSTNode  right;

        public BSTNode ( T data ) {
            this.data = data;
        }

        public BSTNode (T data, BSTNode left, BSTNode right ) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        public int compareTo ( BSTNode other ) {
            if (BST.this.comparator == null )
                return this.data.compareTo ( other.data );
            else
                return comparator.compare(this.data, other.data);
        }
    }

    /**
     * This is a wrapper method for the recursive method contains(T o, BSTNode n). It returns true
     * if this tree contains the specified element. More formally, returns true if and only if this
     * tree contains an element e such that Objects.equals(o, e).
     * This operation is O(H).
     * @param o - object to be checked for containment in this set
     * @return true if this tree contains the specified element
     * @throws ClassCastException - if the specified object cannot be compared with the elements
     * currently in the set
     * @throws NullPointerException - if the specified element is null and this tree uses
     * natural ordering, or its comparator does not permit null elements
     */
    public boolean contains(Object o) throws ClassCastException, NullPointerException {
        //checks if parameter is null
        if(o == null) throw new NullPointerException();

        //calls recursive function
        return contains((T)(o), root);
    }

    /**
     * Private recursive method to see if BST contains certain element o. Operation is O(H).
     * @param o - object to be checked for containment in this set
     * @param n - node at which recursio begins
     * @return true if this tree contains the specified element
     * @throws ClassCastException - if the specified object cannot be compared with the elements
     * currently in the set
     */
    private boolean contains(T o, BSTNode n) throws ClassCastException{
        //base case
        if(n == null) return false;
        int compareResult;

        //compares o to data in node
        try {
            compareResult = o.compareTo(n.data);
        } catch(Exception e) {
            throw new ClassCastException();
        }

        //calls recursive method or returns true depending on comparison
        if(compareResult < 0)
            return contains(o, n.left);
        else if(compareResult > 0)
            return contains(o, n.right);
        else
            return true;
    }

    /**
     * Returns true if this tree contains no elements.
     * This operation is O(1).
     * @return true if this tree contains no elements
     */
    public boolean isEmpty(){
        return root == null;
    }

    /**
     * Returns an iterator over the elements in this tree in ascending order.
     * This operation is O(N).
     * @return an iterator over the elements in this tree in ascending order
     */
    public Iterator<T> iterator(){
        //Method parses through BST first to ensure Iterator methods are O(1)
        ArrayList<T> elements = new ArrayList<T>();
        if(root != null) {
            BSTNode current = root;
            while (current != null) {
                if (current.left == null) {
                    elements.add(current.data);
                    current = current.right;
                }
                else {
                    BSTNode pre = current.left;
                    while (pre.right != null && pre.right != current){
                        pre = pre.right;
                    }
                    if (pre.right == null) {
                        pre.right = current;
                        current = current.left;
                    }
                    else {
                        pre.right = null;
                        elements.add(current.data);
                        current = current.right;
                    }
                }
            }
        }

        //actual iterator with implemented methods
        Iterator<T> BSTIterator = new Iterator<T>() {
            //counter for keeping track of iterative steps
            private int counter = 0;

            /**
             * Overrides hasNext method of Iterator<T> interface
             * @return true if next element is available
             */
            @Override
            public boolean hasNext() {
                if(elements.size() == 0) return false;
                int tmp = counter;
                counter++;
                return tmp <= elements.size()-1;
            }

            /**
             * Overrides next method of Iterator<T> interface
             * @return next element in BST
             */
            @Override
            public T next() {
                if(elements.size() == 0) return null;
                int tmp = counter;
                counter++;
                return elements.get(tmp);
            }
        };
        return BSTIterator;
    }

    /**
     * This is a wrapper method for the recursive function recRange(ArrayList<T> t, BSTNode n,
     * T fromElement, T toElement). It returns a collection whose elements range from fromElement,
     * inclusive, to toElement, inclusive. he returned collection/list is backed by this tree,
     * so changes in the returned list are reflected in this tree, and vice-versa (i.e., the two
     * structures share elements. The returned collection should be organized according to the
     * natural ordering of the elements (i.e., it should be sorted). This operation is O(M) where
     * M is the number of elements in the returned list.
     * @param fromElement - low endpoint (inclusive) of the returned collection
     * @param toElement - high endpoint (inclusive) of the returned collection
     * @return a collection containing a portion of this tree whose elements range from fromElement,
     * inclusive, to toElement, inclusive
     * @throws NullPointerException - if fromElement or toElement is null
     * @throws IllegalArgumentException - if fromElement is greater than toElement
     */
    public ArrayList<T> getRange​(T fromElement, T toElement){
        //checks for errors
        if(fromElement == null || toElement == null) throw new NullPointerException();
        if(fromElement.compareTo(toElement) > 0) throw new IllegalArgumentException();

        //creates arraylist and assigns result of recursive method to it
        ArrayList<T> rangeList = new ArrayList<T>();
        recRange(rangeList, root, fromElement, toElement);
        return rangeList;
    }

    /**
     * Actual recursive function that modifies provided ArrayList<T> to incude values within
     * given range
     * @param t - ArrayList<T> to be modified
     * @param n - node at which recursion begins
     * @param fromElement - low endpoint (inclusive) of the returned collection
     * @param toElement - high endpoint (inclusive) of the returned collection
     */
    public void recRange(ArrayList<T> t, BSTNode n, T fromElement, T toElement){
        //base case
        if (n == null) return;

        //variables to store results of comparisons
        int compFrom;
        int compTo;

        //compares using comparator if provided
        if(comparator == null){
            compFrom = fromElement.compareTo(n.data);
            compTo = toElement.compareTo(n.data);
        }
        else{
            compFrom = comparator.compare(fromElement, n.data);
            compTo = comparator.compare(toElement, n.data);
        }

        //recurse thru left subtree first
        if (compFrom < 0) {
            recRange(t, n.left, fromElement, toElement);
        }

        //add node's data if in range
        if (compFrom <= 0 && compTo >= 0) {
            t.add(n.data);
        }

        //recurse thru right subtree
        if (compTo > 0) {
            recRange(t, n.right, fromElement, toElement);
        }
    }

    /**
     * Returns the first (lowest) element currently in this tree. Iterative method.
     * This operation is O(H).
     * @return the first (lowest) element currently in this tree
     * @throws NoSuchElementException - if this tree is empty
     */
    public T first() throws NoSuchElementException{
        //checks if empty
        if(isEmpty()) throw new NoSuchElementException();

        //while loop to iterate towards bottom leftmost (smallest) element
        BSTNode tmp = root;
        while(tmp.left != null){
            tmp = tmp.left;
        }
        return tmp.data;
    }

    /**
     * Returns the last (highest) element currently in this tree. Iterative method.
     * This operation is O(H).
     * @return the last (highest) element currently in this tree
     * @throws NoSuchElementException - if this tree is empty
     */
    public T last(){
        //checks if empty
        if(isEmpty()) throw new NoSuchElementException();

        //while loop to iterate towards bottom rightmost (largest) element
        BSTNode tmp = root;
        while(tmp.right != null){
            tmp = tmp.right;
        }
        return tmp.data;
    }

    /**
     * Compares the specified object with this tree for equality. Returns true if the given
     * object is also a tree, the two trees have the same size, and the inorder traversal of the
     * two trees returns the same nodes in the same order.
     * This operation is O(N).
     * Overrides equals in class object.
     * @param obj - object to be compared for equality with this tree
     * @return true if the specified object is equal to this tree
     */
    public boolean equals​(Object obj){
        //checks if obj is null
        if(obj == null) return false;

        //checks if obj is of type BST
        if(!(obj instanceof BST)) return false;

        //checks size and elements to see if equal
        BST tmp = (BST)(obj);
        if(size != tmp.size) return false;
        return toString().equals(obj.toString());
    }

    /**
     * Returns a string representation of this tree. The string representation consists of a list
     * of the tree's elements in the order they are returned by its iterator (inorder traversal),
     * enclosed in square brackets ("[]"). Adjacent elements are separated by the characters ", "
     * (comma and space). Elements are converted to strings as by String.valueOf(Object).
     * This operation is O(N).
     * Overrides toString in class Object
     * @return a string representation of this collection
     */
    @Override
    public String toString(){
        //in case BST is empty
        if(size == 0) return "[]";

        //uses iterator to parse through elements and add to String
        Iterator<T> t = iterator();
        String elements = "[";
        for(int i = 0; i < size; i++){
            if(i == size-1) elements = elements + String.valueOf(t.next());
            else elements = elements + String.valueOf(t.next()) + ", ";
        }
        elements += "]";
        return elements;
    }

    /**
     * This function returns an array containing all the elements returned by this tree's iterator,
     * in the same order, stored in consecutive elements of the array, starting with index 0. The
     * length of the returned array is equal to the number of elements returned by the iterator.
     * This operation is O(N).
     * @return an array, whose runtime component type is Object, containing all of the elements in
     * this tree
     */
    public Object[] toArray(){
        //in case BST is empty
        if(size == 0) return new Object[0];

        //uses iterator to parse through elements of BST and add to array
        Object[] elements = new Object[size];
        Iterator<T> t = iterator();
        for(int i = 0; i < size; i++){
            elements[i] = t.next();
        }
        return elements;
    }


}

