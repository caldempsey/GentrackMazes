package sys;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A passage defines a coordinate of a maze which may have neighbors.
 * The implementation could be split into interfaces i.e. Coordinate and Traversable but this is not featured to preserve simplicity (of a bespoke tool).
 * Passage objects can have neighbors (which are represented by storing references to those objects).
 * Passages also have a sign (think a sign post leading to another passage), which can be used to help us meaningfully direct to other passages.
 * A goal of the object is to cache information about known passages aiming to improve overall speeds for route finding.
 */
public class Passage {
    // The 'x' coordinate of the passage.
    private final int x;
    // The 'y' coordinate of the passage.
    private final int y;
    // A set containing neighboring passages.
    private final Set<Passage> neighbors;
    // A sign pointing to another passage.
    private Passage sign;

    /**
     * Passage constructor.
     *
     * @param x As a coordinate point refers to the x coordinate of the passage (on a manifold).
     * @param y As a coordinate point refers to the y coordinate of the passage (on a manifold)
     */
    Passage(int x, int y) {
        this.x = x;
        this.y = y;
        neighbors = new HashSet<>();
    }

    /**
     * Returns a string representation of a passage.
     *
     * @return Returns to the format "Passage{x=xcoord,y=ycoord)'
     */
    @Override
    public String toString() {
        return "Passage{" + "x=" + x + ", y=" + y + '}';
    }

    /**
     * Returns a set of passage neighbours. Returns an empty set if no neighbours exist (as per object construction).
     *
     * @return Return the neighbours of the passage.
     */
    Set<Passage> getNeighbors() {
        return neighbors;
    }

    /**
     * Adds a neighbour to the passage.
     *
     * @param neighbor A passage object.
     */
    void addNeighbor(Passage neighbor) {
        neighbors.add(neighbor);
    }

    /**
     * No passage can occupy the same coordinate space on a manifold, the equals (and hashCode) methods are overridden to enforce that definition of logical equivalence.
     *
     * @param object Input object.
     * @return Returns true if the object is logically equivalent.
     */
    @Override
    public boolean equals(Object object) {
        /*
        Test for identity...
        Ensure that the object can be said true in and of itself (is reflexively equal). If so return true (is an instance of itself)
        */
        if (this == object) return true;
        /*
        Test for none-nullity...
        Use the == operator to check if the argument is null.
         */
        if (object == null) return false;
        /*
        Test for type...
        Check the object is of the correct instance.
         */
        if (!(object instanceof Passage)) {
            return false;
        }
        /*
        If the object is of the correct instance then we can perform a type conversion (or a cast) to this kind of object.
         */
        Passage passage = (Passage) object;
        // At this stage we can safely utilise the fields of the object to define logical equality.
        return x == passage.x &&
                y == passage.y;
    }


    /**
     * No passage can occupy the same coordinate space on a manifold, the hashCode (and equals) methods are overridden to enforce that definition of logical equivalence.
     *
     * @return Returns the object hashcode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * @return Returns the object at the sign.
     */
    Passage getSign() {
        return sign;
    }

    /**
     * Validates whether a sign is associated with the passage.
     *
     * @return Returns a Passage object.
     */
    boolean hasSign() {
        return sign == null;
    }

    /**
     * Sets the sign to a passage object.
     *
     * @param sign The passage object to set.
     */
    void setSign(Passage sign) {
        assert this.neighbors.contains(sign);
        this.sign = sign;
    }
}
