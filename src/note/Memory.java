/*
 * Memory for a note!
 */

package note;

/**
 *
 * @author blue
 */
public class Memory {

    private int relativeLocationInHalfSteps;

    public Memory() {
        relativeLocationInHalfSteps = 0;
    }

    public void changeRelativeLocationInHalfSteps(int delta) {
        relativeLocationInHalfSteps = relativeLocationInHalfSteps + delta;
    }

    public int relativeLocationInHalfSteps() {
        return relativeLocationInHalfSteps;
    }

    @Override
    public String toString() {
        return "[Memory: rel-loc-in-hs=" + relativeLocationInHalfSteps + "]";
    }

}
