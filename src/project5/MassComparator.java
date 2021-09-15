package project5;

import java.util.Comparator;

/**
 * Comparator class to define how Meteorites can be compared based on mass. Implemented in MeteoriteData
 * class.
 */
public class MassComparator implements Comparator<Meteorite> {
    public int compare(Meteorite m1, Meteorite m2) {
        if (m1.getMass() == m2.getMass())
            return m1.compareTo(m2);
        else
            return m1.getMass() - m2.getMass();
    }
}
