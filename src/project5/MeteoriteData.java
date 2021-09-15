package project5;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * MeteoriteData class stores Meteorite objects using three BSTs. The class includes many methods to
 * add, remove, and find and return collection of Meteorite objects based on year, mass, or Location.
 */
public class MeteoriteData {
    //private instance variables
    private BST<Meteorite> massTree;
    private BST<Meteorite> naturalTree;
    private BST<Meteorite> yearTree;

    //Default constructor - initializes three BSTs
    public MeteoriteData(){
        massTree = new BST<Meteorite>(new MassComparator());
        naturalTree = new BST<Meteorite>();
        yearTree = new BST<Meteorite>(new YearComparator());
    }

    /**
     * This method should add the given Meteorite object to this collection and return true if an
     * equal Meteorite object is not already present. If this collection already contains an object
     * equal to m, the method should return false. The method should throw an instance of
     * NullPointerException if m is null. Operation is O(H) where H is the height of the tree.
     * @param m - Meteorite object to be added.
     * @return - true if m is succesfully added
     * @throws NullPointerException - if m is null
     */
    public boolean add (Meteorite m) throws NullPointerException{
        //checks if m is null
        if(m == null) throw new NullPointerException();

        //returns false if collection already includes m
        if(naturalTree.contains(m) || massTree.contains(m) || yearTree.contains(m)) return false;

        //adds m to three trees
        return (massTree.add(m) && naturalTree.add(m) && yearTree.add(m));
    }

    /**
     * This method should compare this collection to obj. The two collections are equal if they
     * are both MeteoriteData objects and if they contain exactly the same elements. Operation is
     * O(N).
     * @param obj - obj to be compared
     * @return true if objects are equal
     */
    public boolean equals (Object obj){
        //checks if obj is null
        if(obj == null) return false;

        //checks if obj is of type MeteoriteData
        if(!(obj instanceof MeteoriteData)) return false;
        MeteoriteData m = (MeteoriteData)(obj);

        //checks if objects share elements
        return(toString().equals(m.toString()));
    }

    /**
     * This method should return a collection of all Meteorite objects with mass within delta grams
     * of the specified mass. Both values are specified in grams. The returned collection should
     * be organized based on the mass from smallest to largest, and for meteorite objects with equal
     * mass according to the natural ordering of the elements (i.e., dictated by the comparaTo
     * method defined in the Mereorite class). This method should perform in O(K+H) in which K is
     * the number of Meteorite objects in the returned collection and H is the height of the tree
     * representing this collection (not O(N) where N is the total number of all Meteorite objects).
     * @param mass - specified mass
     * @param delta - acceptable range around mass
     * @return - MeteoriteData object with elements within range
     * @throws IllegalArgumentException - mass or delta is under zero
     */
    public MeteoriteData getByMass (int mass, int delta) throws IllegalArgumentException{
        //checks for errors
        if(mass < 0 || delta < 0) throw new IllegalArgumentException("Invalid arguments.");
        MeteoriteData massData = new MeteoriteData();

        //dummy Meteorite objects to use for getRange function
        Meteorite m1 = new Meteorite("!!!", 1);
        Meteorite m2 = new Meteorite("~~~", Integer.MAX_VALUE);
        if(mass-delta <= 0) m1.setMass(1);
        else m1.setMass(mass-delta);
        m2.setMass(mass+delta);

        //calls getRange function on dummy Meteorite objects
        ArrayList<Meteorite> masses = massTree.getRange​(m1, m2);
        if(masses.size() == 0) return null;

        //copies Meteorite objects from ArrayList to MeteoriteData
        for (Meteorite meteorite : masses) {
            massData.add(meteorite);
        }
        return massData;
    }

    /**
     * This method returns a Meteorite object that is closest to the given Location. It should throw
     * and IllegalArgumentException if loc is null and return null if the BST is empty. Uses iterator
     * to find Location. Operation is O(N).
     * @param loc - Location value to be compared
     * @return Meteorite object with closest Location value to loc
     * @throws IllegalArgumentException if loc is null
     */
    public Meteorite getByLocation (Location loc) throws IllegalArgumentException{
        //checks if loc is null
        if(loc == null) throw new IllegalArgumentException("Invalid argument.");
        //checks if tree is empty
        if(naturalTree.isEmpty()) return null;

        //uses iterator to parse thru list and find closest Meteorite
        Iterator<Meteorite> m = iterator();
        Meteorite closest = m.next();
        for(int i = 0; i < naturalTree.size()-1; i++){
            Meteorite tmp = m.next();
            if(tmp.getLocation().getDistance(loc) <
                    closest.getLocation().getDistance(loc)) closest = tmp;
        }
        return closest;
    }

    /**
     * Returns collection of Meteorite objects that landed within provided year. Objects are stored
     * in MeteoriteData collection. Uses getRange function from BST class to find desired elements.
     * Operation is O(K+H).
     * @param year - year in which desired Meteorites landed
     * @return MeteoriteData collection of all Meteorites that landed during provided year
     * @throws IllegalArgumentException if year is under zero or over 2020
     */
    public MeteoriteData getByYear (int year) throws IllegalArgumentException{
        //checks for errors
        if(year < 0 || year > 2020) throw new IllegalArgumentException("Invalid argument.");
        MeteoriteData yearData = new MeteoriteData();

        //dummy Meteorite objects
        Meteorite m1 = new Meteorite("!!!", 1);
        Meteorite m2 = new Meteorite("~~~", Integer.MAX_VALUE);
        m1.setYear(year);
        m2.setYear(year);

        //uses getRange function to find desired elements
        ArrayList<Meteorite> years = yearTree.getRange​(m1, m2);
        if(years.size() == 0) return null;
        for (Meteorite meteorite: years) {
            yearData.add(meteorite);
        }
        return yearData;
    }

    /**
     * Iterator method for MeteoriteData. Iterates over Meteorite objects according to their natural
     * order. Method uses iterator method of BST class.
     * @return Iterator for MeteoriteData
     */
    public Iterator<Meteorite> iterator(){
        //simply calls iterator method of BST
        return naturalTree.iterator();
    }

    /**
     * This method should remove an object equal to the given Meteorite object m from this collection
     * and return true such an object was present. If m is not in this collection, the method should
     * return false. The method should throw an instance of NullPointerException if m is null. Method is
     * O(H).
     * @param m - Meteorite object to be returned
     * @return true if m is succesfully removed
     * @throws NullPointerException if m is null
     */
    public boolean remove (Meteorite m) throws NullPointerException{
        //checks for errors
        if(m == null) throw new NullPointerException();

        //checks if MeteoriteData already contains m
        if(!naturalTree.contains(m) || !massTree.contains(m) || !yearTree.contains(m)) return false;

        //removes m from all three trees
        return(massTree.remove(m) && naturalTree.remove(m) && yearTree.remove(m));
    }

    /**
     * toString method for MeteoriteData. Uses iterator to parse thru data.
     * @return Sting representation of MeteoriteData colleciton
     */
    public String toString(){
        //uses iterator of MeteoriteData to parse thru elements and add to String object
        Iterator<Meteorite> itr = iterator();
        String stuff = "";
        for(int i = 0; i < naturalTree.size(); i++){
            stuff = stuff + itr.next() + "\n";
        }
        return stuff;
    }

}
