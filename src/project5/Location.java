package project5;
/**
 * This class represents a location.
 * It uses two values, latitude and longitude, to represent a location. The latitude must fit within
 * the range [-90.0, 90.0] and the longitude must fit within the range [-180.0, 180.0].
 *
 * @author Jack
 */

public class Location {
    private double latitude;
    private double longitude;

    /**
     * Constructs a new Location object with specified latitude and longitude values
     * @param latitude: should be within range of -90.0 to 90.0
     * @param longitude: should be within range of -180.0 to 180.0
     * @throws IllegalArgumentException if parameters are outside specified range
     */
    public Location(double latitude, double longitude) throws IllegalArgumentException {
        if(Math.abs(latitude) > 90) throw new IllegalArgumentException("Invalid value for latitude. " +
                "Latitude value must be within range -90.0 to 90.0.");
        if(Math.abs(longitude) > 180) throw new IllegalArgumentException("Invalid value for longitude. " +
                "Latitude value must be within range -180.0 to 180.0.");
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Returns calculated distance between this location and location specified in parameter
     * Uses Haversine formula copied from Project2 website
     * (https://cs.nyu.edu/~joannakl/cs102_f20/projects/project2.html#haversine-formula-implementation)
     * @param loc: cannot be null
     * @return double value of calculated distance between two locations
     * @throws IllegalArgumentException if parameter is null
     */
    double getDistance(Location loc) throws IllegalArgumentException{
        if(loc == null) throw new IllegalArgumentException("Parameter cannot be null.");

        double dLat = Math.toRadians(this.latitude - loc.latitude);
        double dLon = Math.toRadians(this.longitude - loc.longitude);

        // convert to radians
        double lat1 = Math.toRadians(this.latitude);
        double lat2 = Math.toRadians(loc.latitude);

        // apply formulae
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);
        double rad = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));
        return rad * c;
    }

    /**
     * Returns the latitude value of this Location object.
     * @return the latitude value of this Location object.
     */
    public double getLatitude(){
        return latitude;
    }

    /**
     * Returns the longitude value of this Location object.
     * @return the longitude value of this Location object.
     */
    public double getLongitude(){
        return longitude;
    }

    /**
     * Overrides equals method of Object class. Compares two location objects to see if equal.
     * @param obj: Object that is compared to Location object
     * @return whether provided Object in parameter is equal to the Location object
     */
    @Override
    public boolean equals(Object obj){
        if(obj == null) return false;
        Location other = (Location) obj;
        return (Math.abs(latitude - other.latitude) < 0.00001 &&
                Math.abs(longitude - other.longitude) < 0.00001);
    }
}
