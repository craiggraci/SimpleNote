/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package note;

import numerics.Rational;

/**
 *
 * @author blue
 */
public class SilentNoteComponents {

    private Scale scale;
    private int degree;
    private Rational duration;
    private int volume;
    private int location;

    public SilentNoteComponents(Scale sca, int deg, int loc, Rational dur, int vol) {
        scale = sca;
        degree = deg;
        duration = dur;
        volume = vol;
        location = loc;
    }

    @Override
    public String toString() {
        return "SilentNoteComponents: scale=" + scale + " | degree=" + degree +
                " | location=" + location + " | duration=" + duration + " | volume=" + volume;
    }

    public Scale scale() { return scale; }
    public int degree() { return degree; }
    public int location() { return location; }
    public Rational duration() { return duration; }
    public int volume() { return volume; }

}
