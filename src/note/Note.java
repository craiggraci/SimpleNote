/*
 * Note interface.
 */

package note;

import javax.sound.midi.Instrument;
import numerics.Rational;

/**
 *
 * @author blue
 */
public interface Note {

    public Note copy();
    @Override
    public String toString();
    public void display();

    public void play();
    public void rest();

    public void displayScaleStack();
    // public void establishScale(Scale scale);
    public Scale getScale();
    // public void setScale(Scale newScale)  throws ChangeScaleException;
    public void resetScale();
    public void pushScale(Scale scale) throws InvalidScaleChangeException;
    public void popScale() throws InvalidScaleChangeException;


    public int getDegree();
    public void setDegree(int newDegree);
    public void resetDegree();
    public int computeDegree(Scale s);

    public int getLocation();
    public void setLocation(int newLocation);
    public void resetLocation();

    public Rational getDuration();
    public void setDuration(Rational newDuration);
    public void resetDuration();

    public int getVolume();
    public int getNumericVolume();
    public void setVolume(String newVolume);
    public void setVolume(Integer volume);
    public void resetVolume();
    public int getDeltaVolume();
    public void setDeltaVolume(int newDeltaVolume);

    public Instrument getTimbre();
    public void setTimbre(Instrument i);
    public void resetTimbre();

    public String getTempo();
    public int getNumericTempo();
    public void setTempo(String string);
    public void setTempo(int i);
    public void resetTempo();
    public void incTempo();
    public void decTempo();
    public void doubleTempo();
    public void halveTempo();
    public void tripleTempo();
    public void thirdTempo();
    public void incTempo10PC();
    public void decTempo10PC();
    public void incTempo5PC();
    public void decTempo5PC();
    public void incTempo1PC();
    public void decTempo1PC();

    public void resetState();

    public double getTime(); // MidiModule time
    public void setTime(double newTime); // MidiModule time

    public void lowerPitch();
    public void lowerPitch(int n);
    public void raisePitch();
    public void raisePitch(int n);
    public void changePitch();
    public void raisePitchX();
    public void lowerPitchX();

    public void s2();
    public void sdot();
    public void s3();
    public void s5();
    public void s7();
    public void x2();
    public void xdot();
    public void x3();
    public void x5();
    public void x7();

    public void reset();

    public void pushScalePlus(Scale s);

    public void popScaleStar();

    public void stopThePlaying();

    public void push();

    public void pop();

    public void changeVolume(String command);

    public void statshotOn();

    public void statshotOff();

    public void presentStatshotIfDesired(String s);

    public void initStatsForStatshot();

    public void pcodeOn();

    public void pcodeOff();

    public String pcode();

    public void addPcode(String command);

    public void x11();

    public void x13();

    public void x17();

    public void x19();

    public void s11();

    public void s13();

    public void s17();

    public void s19();

    // public void pushScalePrime(Scale s);

    // public void changeInfoMode(String infoMode);
    
    //public double getTime(MidiModule mm);
    //public void setTime(MidiModule mm, double newTime);
}
