package project5;
import java.util.ArrayList;

/**
 * The MeteoriteList class is used to store a collection of Meteorite objects.
 * It inherits all of its properties from an ArrayList<Meteorite>.
 * The class also contains methods to create MeteoriteLinkedLists based off of mass or year as
 * well as a method that returns a single Meteorite closest to a specified location.
 * The class does not store Meteorites in any specific order.
 *
 * @author Jack
 */
public class MeteoriteList extends ArrayList<Meteorite> {

    /**
     * Default constructor: creates empty MeteoriteList object
     */
    public MeteoriteList(){
        super();
    }

    /**
     * This method returns a MeteoriteLinkedList of Meteorites within delta grams of specified mass
     * @param mass: specific mass value to be compared to
     * @param delta: allowed range of distance from specified mass
     * @return MeteoriteLinkedList of Meteorites within delta grams of specified mass
     * @throws IllegalArgumentException when either parameter value is less than zero
     */
    public MeteoriteLinkedList getByMass(int mass, int delta) throws IllegalArgumentException{
        if(mass <= 0 || delta <= 0) throw new IllegalArgumentException("Parameter values cannot be less than zero.");
        MeteoriteList massLList = new MeteoriteList();
        if(this.size() == 0) return null;
        for(Meteorite m : this){
            if(m.getMass() == 0){
                continue;
            }
            if(Math.abs(mass - m.getMass()) <= delta){
                massLList.add(m);
            }
        }
        if(massLList.size() == 0) return null;
        return new MeteoriteLinkedList(massLList);
    }

    /**
     * This method returns the Meteorite with the closest Location to the specified value.
     * @param loc: the location value to be compared to
     * @return Meteorite object that is closest to provided location value.
     * @throws IllegalArgumentException if parameter value is null
     */
    public Meteorite getByLocation(Location loc) throws IllegalArgumentException{
        if(loc == null) throw new IllegalArgumentException("Parameter cannot be null.");
        if(this.size() == 0) return null;
        Meteorite met = this.get(0);
        for(Meteorite m : this){
            if(m.getLocation().getDistance(loc) < met.getLocation().getDistance(loc)){
                met = m;
            }
        }
        return met;
    }

    /**
     * This method returns a MeteoriteLinkedList containing all Meteorites that match the year specified.
     * @param year: specific year value for Meteorites to be compared to
     * @return MeteoriteLinkedList containing all Meteorites that match the year specified
     * @throws IllegalArgumentException if provided year value is less than zero
     */
    public MeteoriteLinkedList getByYear(int year) throws IllegalArgumentException{
        if(year < 0) throw new IllegalArgumentException("Value of year parameter cannot be below zero.");
        MeteoriteList yearLList = new MeteoriteList();
        for(Meteorite m: this){
            if(m.getYear() == year){
                yearLList.add(m);
            }
        }
        if(yearLList.size() == 0) return null;
        return new MeteoriteLinkedList(yearLList);
    }
}
