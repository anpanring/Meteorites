package project5;

/**
 * The MeteoriteLinkedList class is an implementation of a singly linked list.
 * It uses Nodes as basic building blocks to store data and reference to other Nodes, creating
 * a reference-based collection.
 * Nodes are defined in a subclass within MeteoriteLinkedList.
 * The class also includes basic add, remove, and contains methods, as well as a toString method
 * that returns a String representation of the linked list.
 *
 * @author Jack
 */

public class MeteoriteLinkedList{
    private Node head;
    private Node tail;

    /**
     * Constructor that creates empty MeteoriteLinkedList.
     */
    public MeteoriteLinkedList(){
        head = null;
        tail = null;
    }

    /**
     * One-parameter constructor that parses through a proved MeteoriteList to create
     * a MeteoriteLinkedList.
     * @param list: MeteoriteList to be parsed through for data
     * @throws IllegalArgumentException if provided list value is null
     */
    public MeteoriteLinkedList(MeteoriteList list) throws IllegalArgumentException{
        if(list == null) throw new IllegalArgumentException("Parameter cannot be null.");

        list.sort(null); //puts list in order
        head = new Node(list.get(0));
        Node current = head;

        //parses through list and adds new Nodes to MeteoriteLinkedList
        if(list.size() > 1){
            for(int i = 1; i < list.size(); i++){
                current.next = new Node(list.get(i));
                current = current.next;
            }
        }
    }

    /**
     * Given a Meteorite object, method adds a new Node to the MeteoriteLinkedList
     * @param m: Meteorite object to be added to MeteoriteLinkedList
     * @return true if MeteoriteLinkedList was modified
     * @throws IllegalArgumentException if parameter is null
     */
    public boolean add(Meteorite m) throws IllegalArgumentException{
        if(m == null) throw new IllegalArgumentException("Parameter cannot be null.");

        if(contains(m)) return false;

        Node newNode = new Node(m);

        try{
            //adding to empty list
            if(head.data == null){
                head = newNode;
                tail = newNode;
            }

            //adding to front of list
            else if(head.data.compareTo(m) > 0){
                newNode.next = head;
                head = newNode;
            }

            //adding to end of list
            else if(tail.data.compareTo(m) < 0){
                tail.next = newNode;
                tail = tail.next;
            }

            //adding anywhere else in list
            else {
                Node current = head;
                while(current.next.data.compareTo(m) < 0){
                    current = current.next;
                }
                newNode.next = current.next;
                current.next = newNode;
            }
        } catch (NullPointerException e){

        }
        return true;
    }

    /**
     * Method to check if provided Meteorite object is present in MeteoriteLinkedList.
     * @param m: Meteorite object that is checked for in list
     * @return true if Meteorite is present in MeteoriteLinkedList
     */
    private boolean contains(Meteorite m){
        if(m == null) return false;
        Node current = head;
        while(current != null && current.data != null){  //&& current.data != null
            if(current.data.equals(m)){
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Removes Meteorite object of matching name and id from MeteoriteLinkedList(if matching
     * element is present)
     * @param name: desired name for element
     * @param id: desired id for element
     * @return Meteorite object of matching name and id or null if doesn't exist
     */
    public Meteorite remove(String name, int id){
        if(head == null) return null;
        Node current = head;
        Meteorite met = new Meteorite(name, id);
        if(current.data.equals(met)) return met;
        while(current.next.data.compareTo(met) != 0){
            if(current.next.data.equals(met)){
                Meteorite pHolder = current.next.data;
                current.next = current.next.next;
                return pHolder;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Returns a string representation of MeteoriteLinkedList object.
     * @return string representation of MeteoriteLinkedList object
     */
    public String toString(){
        String stringData = "";
        Node current = head;
        while(current != null && current.data != null ){
            stringData += current.data.toString() + "\n";
            current = current.next;
        }
        return stringData;
    }

    /**
     * Code copied from Project2 page (https://cs.nyu.edu/~joannakl/cs102_f20/projects/project2.html)
     * Inner private class for Node object.
     * Nodes have a Meteorite type variable to store data and Node type variable to store reference to
     * next Node in list. Also includes toString, equals, and compareTo methods.
     */
    private class Node implements Comparable<Node> {
        Meteorite data;
        Node next;

        Node ( Meteorite data ) {
            this.data = data;
        }

        public String toString () {
            return data.toString();
        }

        public boolean equals( Object o ) {
            if (this == o) return true;
            if (!(o instanceof Node)) {
                return false;
            }
            Node other = (Node) o;
            if (!this.data.equals(other.data)) {
                return false;
            }
            return true;
        }

        public int compareTo (Node n ) {
            return data.compareTo(n.data);
        }
    }
}
