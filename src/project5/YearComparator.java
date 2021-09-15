package project5;

import java.util.Comparator;

/**
 * Comparator class to define how Meteorites can be compared based on year. Implemented in MeteoriteData
 * class.
 *
 * @author Jack
 */
public class YearComparator implements Comparator<Meteorite> {
    public int compare(Meteorite m1, Meteorite m2) {
        if (m1.getYear() == m2.getYear())
            return m1.compareTo(m2);
        else
            return m1.getYear() - m2.getYear();
    }
}
