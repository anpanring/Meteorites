package project5;
/**
 * This class represents a meteorite.
 * It is initialized with two variables: the name, which can be any non-empty string, and the id,
 * which can be any integer larger than zero. The object can also store values for the mass, year,
 * and Location of the meteorite.
 *
 * @author Jack
 */
public class Meteorite implements Comparable<Meteorite>{
    private String name;
    private int id;
    private int mass;
    private int year;
    private Location mLoc;

    /**
     * Two-parameter constructor that validates and sets the name and id of the Meteorite object.
     * @param name: any non-empty string is valid
     * @param id: any int larger than zero
     * @throws IllegalArgumentException when name is not a valid string or if id less than zero
     */
    public Meteorite(String name, int id) throws IllegalArgumentException{
        if(id <= 0 || name.length() == 0) throw new IllegalArgumentException("Meteorite object cannot be initialized with" +
                "id value less than zero.");
        this.name = name;
        this.id = id;
    }

    /**
     * Validates and sets value for mass of Meteorite object
     * @param mass: mass value to be examined and set (value must be larger than zero)
     * @throws IllegalArgumentException when mass is less than zero
     */
    public void setMass(int mass){
        if(mass < 0) throw new IllegalArgumentException("Value of parameter must be larger than zero.");
        this.mass = mass;
    }

    /**
     * Returns the mass value of the Meteorite object
     * @return mass value of Meteorite object
     */
    public int getMass(){
        return mass;
    }

    /**
     * Validates and sets value for year of Meteorite object
     * @param year: year value to be examined and set (must be positive integer smaller than current year)
     * @throws IllegalArgumentException when year is less than zero or larger than current year
     */
    public void setYear(int year) throws IllegalArgumentException{
        if(year < 0 || year > 2020) throw new IllegalArgumentException("Value of parameter must" +
                "be larger than zero and smaller than current year.");
        this.year = year;
    }

    /**
     * Returns the year value of the Meteorite object
     * @return year value of Meteorite object
     */
    public int getYear(){
        return year;
    }

    /**
     * Validates and sets value for location of Meteorite object
     * @param loc: location value to be examined and set
     */
    public void setLocation(Location loc){
        mLoc = loc;
    }

    /**
     * Returns the location value of the Meteorite object
     * @return location value of the Meteorite object
     */
    public Location getLocation(){
        return mLoc;
    }

    /* (non-Javadoc)
    * @see java.lang.Object#equals(java.lang.Object)
    */
    public boolean equals(Object obj){
        if(obj == null) return false;
        if(this == obj) return true;
        if(!(obj instanceof Meteorite)) return false;
        Meteorite m = (Meteorite) obj;
        return(this.compareTo(m) == 0);
    }

    /**
     * Returns a string representation of Meteorite object.
     * @return string representation of Meteorite object
     */
    public String toString(){
        String lat, lon;
        String year1 = "";
        String mass1 = "";
        if(year > 0) year1 = Integer.toString(year);
        if(mass > 0) mass1 = Integer.toString(mass);
        if (mLoc == null) {
            lat = "";
            lon = "";
        } else{
            lat = String.format("%.5f", mLoc.getLatitude());
            lon = String.format("%.5f", mLoc.getLongitude());
        } return String.format("%-20s %4s %4s %6s %10s %10s",
                name, id, year1, mass1, lat, lon);
    }

    /* (non-Javadoc)
    * @see java.lang.Comparable#compareTo(java.lang.Object)
    */
    @Override
    public int compareTo(Meteorite met){
        if(this.name.equals(met.name)) {
            if(this.id == met.id) return 0;
            else if(this.id > met.id) return 1;
            else return -1;
        }
        return this.name.compareToIgnoreCase(met.name);
    }

}
