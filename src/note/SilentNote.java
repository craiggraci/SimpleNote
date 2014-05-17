/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package note;

import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.Instrument;
import numerics.Rational;
import utilities.Converters;
import utilities.Random;

/**
 *
 * @author blue
 */
public class SilentNote implements Note {

    // Default values for SilentNote instance variables
    static private Scale defaultScale = Scale.C_MAJOR;
    static private int defaultDegree = 1;
    static private int defaultLocation = 5;
    static private Rational defaultDuration = new Rational(1);
    static private int defaultVolume = 12000;
    static private int defaultDeltaVolume = 10;
    static protected double defaultNrSecondsPerBeat = 0.24;

    // SilentNote instance variables
    protected Scale scale = defaultScale;
    protected int degree = defaultDegree;
    protected int location = defaultLocation;
    protected Rational duration = defaultDuration;
    protected int volume = defaultVolume;
    protected int deltaVolume = defaultDeltaVolume;
    protected double nrSecondsPerBeat = defaultNrSecondsPerBeat;
    // protected String infoMode = "physical";  // physical or functional or midi
    protected Instrument timbre;
    protected int instrumentNumber = 0;
    protected Memory memory = new Memory();

    protected String pdppcSeq = ""; //

    public int previousMidiNumber = -1;

    // stacks
    protected Stack<Mode> stackMode = new Stack<Mode>();
    protected Stack<Scale> scaleStack = new Stack<Scale>();
    protected Stack<SilentNoteComponents> noteStackForAlphabetics = new Stack<SilentNoteComponents>();

    protected NoteFrame mcf;

    protected ArrayList<Double> durationSeq;

    public SilentNote() {
       // resetScale();
        previousmidiNumber = -1;
        rpCount = 0; lpCount = 0;
        pushscaleCount = 0; popscaleCount = 0;
        playCount = 0; restCount = 0;
        scaleStack.push(Scale.C_MAJOR);
        noteStack = new Stack<SilentNoteComponents>();
        SilentNoteComponents snc = new SilentNoteComponents(scale,degree,location,duration,volume);
        noteStack.push(snc);
        durationSeq = new ArrayList<Double>(); // for nPVI computation
    }

    public SilentNote(NoteFrame f) {
        previousmidiNumber = -1;
        mcf = f;
        // resetScale();
        rpCount = 0; lpCount = 0;
        pushscaleCount = 0; popscaleCount = 0;
        playCount = 0; restCount = 0;
        scaleStack.push(Scale.C_MAJOR);
        noteStack = new Stack<SilentNoteComponents>();
        SilentNoteComponents snc = new SilentNoteComponents(scale,degree,location,duration,volume);
        noteStack.push(snc);
        durationSeq = new ArrayList<Double>(); // for nPVI computation
    }

    // protected MxMControlFrame mcf = null;

    @Override
    public String toString() {
        return "Note: " +
                " scale=" + scale.toString() +
                " degree=" + degree +
                " location=" + location +
                " duration=" + duration +
                " volume=" + volume +
                " tempo=" + nrSecondsPerBeat +
                " deltaVolume=" + deltaVolume +
                " memory=" + memory +
                "";

    }

    private int currentmidiNumber;
    private int previousmidiNumber;
    private int lpCount = 0;
    private int rpCount = 0;
    private int s2Count = 0;
    private int s3Count = 0;
    private int s5Count = 0;
    private int s7Count = 0;
    private int s11Count = 0;
    private int s13Count = 0;
    private int s17Count = 0;
    private int s19Count = 0;
    private int x2Count = 0;
    private int x3Count = 0;
    private int x5Count = 0;
    private int x7Count = 0;
    private int x11Count = 0;
    private int x13Count = 0;
    private int x17Count = 0;
    private int x19Count = 0;
    protected int playCount = 0;
    protected int restCount = 0;
    protected int pushscaleCount = 0;
    protected int popscaleCount = 0;

    public void statshotOn() {
        statshotFlag = true;
        rpCount = 0; lpCount = 0;
        pushscaleCount = 0; popscaleCount = 0;
        playCount = 0; restCount = 0;
        s2Count = 0; x2Count = 0;
        s3Count = 0; x3Count = 0;
        s5Count = 0; x5Count = 0;
        s7Count = 0; x7Count = 0;
    }

    public void statshotOff() {
        statshotFlag = false;
    }

    public int playCount() {
        return playCount;
    }

    public int restCount() {
        return restCount;
    }

    public int rpCount() {
        return rpCount;
    }

    public int lpCount() {
        return lpCount;
    }

    public int s2Count() {
        return s2Count;
    }

    public int s3Count() {
        return s3Count;
    }

    public int s5Count() {
        return s5Count;
    }

    public int s7Count() {
        return s7Count;
    }

    public int x2Count() {
        return x2Count;
    }

    public int x3Count() {
        return x3Count;
    }

    public int x5Count() {
        return x5Count;
    }

    public int x7Count() {
        return x7Count;
    }

    public int degree() {
        return degree;
    }

    public Note copy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void display() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected boolean statshotFlag = false;

    public void play() {
        currentmidiNumber = scale.computeMidiNumber(degree, location);
        String infoMode = "physical";
        durationSeq.add(duration.value()); // for nPVI computation
        if ( infoMode.equalsIgnoreCase("physical") ) {
            physicalPlay();
        } else if ( infoMode.equalsIgnoreCase("functional") ) {
            functionalPlay();
        } else if ( infoMode.equalsIgnoreCase("midi") ) {
            midiPlay();
        }
        previousmidiNumber = currentmidiNumber;
        playCount++;
        pdppcSeq = pdppcSeq + ppc() + " ";
        if ( statshotFlag ) {
            mcf.ta().append("\n" + statshot()+"\n");
        }
//        if ( pcodeFlag ) { pcode = pcode + "PLAY "; }
    }

    private String ppc() {
        String physicalPitchClass = scale.computePitchClass(degree);
        // physicalPitchClass = pitchSpell(physicalPitchClass);
        // String rep = physicalPitchClass + formatDuration(duration);
        String rep = physicalPitchClass + duration.value();
        return rep;
    }

    public String pdppcSeq() {
        return pdppcSeq.trim();
    }

    private boolean ikkFlag = false;
    private String ikkData;

    public void ikkOn() {
        ikkFlag = true;
        ikkData = "";
    }

    public void ikkOff() {
        ikkFlag = false;
    }

    public String ikk() {
        return ikkData;
    }

    protected String formatDuration(double duration) {
        // String result = "";
        String result = Converters.round(new Double(duration));
//        if (duration == 1.0) {
//            result = "";
//        } else if (duration == 2.0) {
//            result = "2";
//        } else if (duration == 3.0) {
//            result = "3";
//        } else if (duration == 4.0) {
//            result = "4";
//        } else if (duration == 5.0) {
//            result = "5";
//        } else if (duration == 6.0) {
//            result = "6";
//        } else if (duration == 7.0) {
//            result = "7";
//        } else if (duration == 8.0) {
//            result = "8";
//        } else if (duration == 9.0) {
//            result = "9";
//        } else if (duration == 1 / 4.0) {
//            result = ".25";
//        } else if (duration == 1 / 3.0) {
//            result = ".33";
//        } else if (duration == 1 / 2.0) {
//            result = ".5";
//        } else if (duration == 3 / 4.0) {
//            result = ".75";
//        } else if (duration == 5 / 4.0) {
//            result = "1.25";
//        } else if (duration == 4 / 3.0) {
//            result = "1.33";
//        } else if (duration == 3 / 2.0) {
//            result = "1.5";
//        } else if (duration == 5 / 3.0) {
//            result = "1.67";
//        } else if (duration == 7 / 4.0) {
//            result = "7/4";
//        } else if (duration == 5 / 2.0) {
//            result = "2.5";
//        } else if (duration == 7 / 2.0) {
//            result = "7/2";
//        } else if (duration == 1 / 3.0) {
//            result = "1/3";
//        } else if (duration == 2 / 3.0) {
//            result = "2/3";
//        } else if (duration == 5 / 3.0) {
//            result = "5/3";
//        } else if (duration == 1 / 5.0) {
//            result = "1/5";
//        } else if (duration == 2 / 5.0) {
//            result = "2/5";
//        } else if (duration == 3 / 5.0) {
//            result = "3/5";
//        } else if (duration == 4 / 5.0) {
//            result = "4/5";
//        } else if (duration == 1 / 7.0) {
//            result = "1/7";
//        } else if (duration == 2 / 7.0) {
//            result = "2/7";
//        } else if (duration == 3 / 7.0) {
//            result = "3/7";
//        } else if (duration == 4 / 7.0) {
//            result = "4/7";
//        } else if (duration == 5 / 7.0) {
//            result = "5/7";
//        } else if (duration == 6 / 7.0) {
//            result = "6/7";
//        } else if (duration == 1 / 9.0) {
//            result = "1/9";
//        } else if (duration == 2 / 9.0) {
//            result = "2/9";
//        } else if (duration == 3 / 9.0) {
//            result = "3/9";
//        } else if (duration == 4 / 9.0) {
//            result = "4/9";
//        } else if (duration == 5 / 9.0) {
//            result = "5/9";
//        } else if (duration == 6 / 9.0) {
//            result = "6/9";
//        } else if (duration == 7 / 9.0) {
//            result = "7/9";
//        } else if (duration == 8 / 9.0) {
//            result = "8/9";
//        } else {
//            result = duration + "";
//        }
        if ( result.length() > 3 ) {
           if ( result.substring(0,4).equals("0.33" ) ) { return ".33"; }
           if ( result.substring(0,4).equals("0.66" ) ) { return ".66"; }
        }
        if ( result.equals("0.25" ) ) { return ".25"; }
        if ( result.equals("0.5" ) ) { return ".5"; }
        if ( result.equals("1.0" ) ) { return "1"; }
        if ( result.equals("2.0" ) ) { return "2"; }
        if ( result.equals("3.0" ) ) { return "3"; }
        if ( result.equals("4.0" ) ) { return "4"; }
        if ( result.equals("5.0" ) ) { return "5"; }
        if ( result.equals("6.0" ) ) { return "6"; }
        if ( result.equals("7.0" ) ) { return "7"; }
        if ( result.equals("8.0" ) ) { return "8"; }
        if ( result.equals("9.0" ) ) { return "9"; }
        if ( result.equals("10.0" ) ) { return "10"; }
        if ( result.equals("11.0" ) ) { return "11"; }
        if ( result.equals("12.0" ) ) { return "12"; }
        return result;
    }

    public void rest() {
        String infoMode = "physical";
        if ( infoMode.equalsIgnoreCase("physical") ) {
            physicalRest();
        } else if ( infoMode.equalsIgnoreCase("functional") ) {
            functionalRest();
        } else if ( infoMode.equalsIgnoreCase("midi") ) {
            midiRest();
        }
        restCount++;
//        if ( pcodeFlag ) { pcode = pcode + "REST "; }
    }

    public Scale getScale() {
        return scale;
    }

    public int getDegree() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setDegree(int newDegree) {
        degree = 1;
    }

    public void resetDegree() {
        degree = defaultDegree;
    }

    public int computeDegree(Scale s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getLocation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setLocation(int newLocation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void resetLocation() {
        location = defaultLocation;
    }

    public Rational getDuration() {
        return duration;
    }

    public void setDuration(Rational newDuration) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void resetDuration() {
        duration = new Rational(1); // defaultDuration;
    }

    public int getVolume() {
        return volume;
    }

    public int getNumericVolume() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setVolume(String newVolume) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setVolume(Integer volume) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void resetVolume() {
        volume = defaultVolume;
        deltaVolume = defaultDeltaVolume;
    }

    public int getDeltaVolume() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setDeltaVolume(int newDeltaVolume) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Instrument getTimbre() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setTimbre(Instrument i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void resetTimbre() {
        // ?
    }

    public String getTempo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getNumericTempo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setTempo(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setTempo(int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void resetTempo() {
        nrSecondsPerBeat = defaultNrSecondsPerBeat;
    }

    public void incTempo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void decTempo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void doubleTempo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void halveTempo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void tripleTempo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void thirdTempo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void incTempo10PC() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void decTempo10PC() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void incTempo5PC() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void decTempo5PC() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void incTempo1PC() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void decTempo1PC() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void resetState() {
        reset();
    }

    public double getTime() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setTime(double newTime) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void raisePitch() {
        String pcBefore = scale.computePitchClass(degree);
        int interval = scale.mode().intervals()[degree-1];
        memory.changeRelativeLocationInHalfSteps(interval);
        if (degree == scale.size()) {
            degree = 1;
            // location++;
        } else {
            degree++;
        }
        String pcAfter = scale.computePitchClass(degree);
        location(pcBefore,pcAfter);
        rpCount++;
        // if ( pcodeFlag ) { pcode = pcode + "RP "; }
        // put this stuff into the notes memory!
        // direction = "UP";
        // rplpSequence = rplpSequence + "RP ";
        // rplpSeq = rplpSeq + "RP ";
    }
    
    public void xrp(int n) {
        Mode m = Mode.CHROMATIC;
        Key k = getScale().key();
        String scaleName = k.name() + "-" + m.name();
        Scale s = Scale.computeScaleFromName(scaleName);
        //pushScale(s);
        scale = s;
    }

    public void rp() {
        raisePitch(1);
    }

    public void lp() {
        lowerPitch(1);
    }

    public void rp(int n) {
        raisePitch(n);
    }

    public void lp(int n) {
        lowerPitch(n);
    }

   public void raisePitch(int n) {
        for ( int i = 1; i <= n; i++ ) {
            raisePitch();
        }
    }

    public void lowerPitch(int n) {
        for ( int i = 1; i <= n; i++ ) {
            lowerPitch();
        }
    }

    /**
     * Lower the pitch of the note by advancing the degree by 1 within the.
     * scale.  First, the degree is decremented.  If the resulting degree
     * becomes less than 0, it is set to the scale size and the location
     * property is decremented.  If the  resulting location value becomes
     * less than 0, it is set to 9.
     */
    public void lowerPitch() {
        //mcf.ta().append(">>> lowerPitch()\n");
        //mcf.ta().append("location=" + location + "\n");
        String pcBefore = scale.computePitchClass(degree);
        int interval;
        //mcf.ta().append("degree=" + degree + "\n");
        //mcf.ta().append("intervals=" + scale.mode().intervals().toString() + "\n");
        if ( degree == 1 ) {
            interval = scale.mode().intervals()[scale.size()-1];
        } else {
            interval = scale.mode().intervals()[degree-2];
        }
        memory.changeRelativeLocationInHalfSteps(-1*interval);
        if (degree == 1) {
            degree = scale.size();
        } else {
            degree--;
        }
        //mcf.ta().append("degree="+degree+ " interval=" + interval);
        String pcAfter = scale.computePitchClass(degree);
        locationAfterLoweringPitch(pcBefore,pcAfter);
        lpCount++;
        // if ( pcodeFlag ) { pcode = pcode + "LP "; }
        //mcf.ta().append("location=" + location + "\n");
        //mcf.ta().append("<<< lowerPitch()\n");
        // WORK ON INVARIANCE
        // put this stuff into the note's memory!!!!!
        // direction = "DOWN";
        // rplpSequence = rplpSequence + "LP ";
        // rplpSeq = rplpSeq + "LP ";
    }

    static private String[] bPostSet = {"C","V","D","W","E","F","X","G","Y","A","Z"};
    static private String[] abPostSet = {"C","V","D","W","E","F","X","G","Y","A"};
    static private String[] aPostSet = {"C","V","D","W","E","F","X","G","Y"};
    static private String[] gaPostSet = {"C","V","D","W","E","F","X","G"};
    static private String[] gPostSet = {"C","V","D","W","E","F","X"};
    static private String[] fgPostSet = {"C","V","D","W","E","F"};
    static private String[] fPostSet = {"C","V","D","W","E"};
    static private String[] ePostSet = {"C","V","D","W"};
    static private String[] dePostSet = {"C","V","D"};
    static private String[] dPostSet = {"C","V"};
    static private String[] cdPostSet = {"C"};

    private void location(String pcBefore, String pcAfter) {
        //ta.append(">>> location\n");
        //ta.append("pcBefore="+pcBefore+"|pcAfter="+pcAfter+"|location="+location+"\n");
        if ( pcBefore.equals("B") ) {
            if ( Converters.member(pcAfter,bPostSet ) ) {
                location++;
            }
        } else  if ( pcBefore.equals("Z") ) {
            if ( Converters.member(pcAfter,abPostSet ) ) {
                location++;
            }
        } else if ( pcBefore.equals("A") ) {
            if ( Converters.member(pcAfter,aPostSet ) ) {
                location++;
            }
        } else if ( pcBefore.equals("Y") ) {
            if ( Converters.member(pcAfter,gaPostSet ) ) {
                location++;
            }
        } else if ( pcBefore.equals("G") ) {
            if ( Converters.member(pcAfter,gPostSet ) ) {
                location++;
            }
        } else if ( pcBefore.equals("X") ) {
            if ( Converters.member(pcAfter,fgPostSet ) ) {
                location++;
            }
        } else if ( pcBefore.equals("F") ) {
            if ( Converters.member(pcAfter,fPostSet ) ) {
                location++;
            }
        } else if ( pcBefore.equals("E") ) {
            if ( Converters.member(pcAfter,ePostSet ) ) {
                location++;
            }
        } else if ( pcBefore.equals("W") ) {
            if ( Converters.member(pcAfter,dePostSet ) ) {
                location++;
            }
        } else if ( pcBefore.equals("D") ) {
            if ( Converters.member(pcAfter,dPostSet ) ) {
                location++;
            }
        } else if ( pcBefore.equals("V") ) {
            if ( Converters.member(pcAfter,cdPostSet ) ) {
                location++;
            }
        }
        //ta.append("pcBefore="+pcBefore+"|pcAfter="+pcAfter+"|location="+location+"\n");
        if (outOfRangeHigh(location)) {
            // wrap around keyboard
            location = 0;
        }
        //ta.append("pcBefore="+pcBefore+"|pcAfter="+pcAfter+"|location="+location+"\n");
        //ta.append("<<< location\n");
    }

    static private String[] cPreSet = {"B","Z","A","Y","G","X","F","E","W","D","V"};
    static private String[] cdPreSet = {"B","Z","A","Y","G","X","F","E","W","D"};
    static private String[] dPreSet = {"B","Z","A","Y","G","X","F","E","W"};
    static private String[] dePreSet = {"B","Z","A","Y","G","X","F","E"};
    static private String[] ePreSet = {"B","Z","A","Y","G","X","F"};
    static private String[] fPreSet = {"B","Z","A","Y","G","X"};
    static private String[] fgPreSet = {"B","Z","A","Y","G"};
    static private String[] gPreSet = {"B","Z","A","Y"};
    static private String[] gaPreSet = {"B","Z","A"};
    static private String[] aPreSet = {"B","Z"};
    static private String[] abPreSet = {"B"};

    private void locationAfterLoweringPitch(String pcBefore, String pcAfter) {
        //mcf.ta().append("pcBefore=" + pcBefore + "\n");
        //mcf.ta().append("pcAfter=" + pcAfter + "\n");
        if ( pcBefore.equals("C") ) {
            if ( Converters.member(pcAfter,cPreSet ) ) {
                location--;
            }
        } else if ( pcBefore.equals("V") ) {
            if ( Converters.member(pcAfter,cdPreSet ) ) {
                location--;
            }
        } else  if ( pcBefore.equals("D") ) {
            if ( Converters.member(pcAfter,dPreSet ) ) {
                location--;
            }
        } else  if ( pcBefore.equals("W") ) {
            if ( Converters.member(pcAfter,dePreSet ) ) {
                location--;
            }
        } else  if ( pcBefore.equals("E") ) {
            if ( Converters.member(pcAfter,ePreSet ) ) {
                location--;
            }
        } else  if ( pcBefore.equals("F") ) {
            if ( Converters.member(pcAfter,fPreSet ) ) {
                location--;
            }
        } else  if ( pcBefore.equals("X") ) {
            if ( Converters.member(pcAfter,fgPreSet ) ) {
                location--;
            }
        } else  if ( pcBefore.equals("G") ) {
            if ( Converters.member(pcAfter,gPreSet ) ) {
                location--;
            }
        } else  if ( pcBefore.equals("Y") ) {
            if ( Converters.member(pcAfter,gaPreSet ) ) {
                location--;
            }
        } else  if ( pcBefore.equals("A") ) {
            if ( Converters.member(pcAfter,aPreSet ) ) {
                location--;
            }
        } else  if ( pcBefore.equals("Z") ) {
            if ( Converters.member(pcAfter,abPreSet ) ) {
                location--;
            }
        }
        if (outOfRangeLow(location)) {
            // wrap around keyboard
            location = 9;
        }
    }

    private boolean outOfRangeHigh(int location) {
        return (location > MidiMap.HIGHLOCATION);
    }

    private boolean outOfRangeLow(int location) {
        return (location < MidiMap.LOWLOCATION);
    }

    public void s2() {
        duration.divide(2); s2Count++;
//        if ( pcodeFlag ) { pcode = pcode + "S2 "; }
    }

    public void s3() {
        duration.divide(3); s3Count++;
//        if ( pcodeFlag ) { pcode = pcode + "S3 "; }
    }

    public void s5() {
        duration.divide(5); s5Count++;
//        if ( pcodeFlag ) { pcode = pcode + "S5 "; }
    }

    public void s7() {
        duration.divide(7); s7Count++;
//        if ( pcodeFlag ) { pcode = pcode + "S7 "; }
    }

    public void s11() {
        duration.divide(11); s11Count++;
//        if ( pcodeFlag ) { pcode = pcode + "S7 "; }
    }

    public void s13() {
        duration.divide(13); s13Count++;
//        if ( pcodeFlag ) { pcode = pcode + "S7 "; }
    }

    public void s17() {
        duration.divide(17); s17Count++;
//        if ( pcodeFlag ) { pcode = pcode + "S7 "; }
    }

    public void s19() {
        duration.divide(19); s19Count++;
//        if ( pcodeFlag ) { pcode = pcode + "S7 "; }
    }

    public void x2() {
        duration.times(2); x2Count++;
//        if ( pcodeFlag ) { pcode = pcode + "X2 "; }
    }

    public void x3() {
        duration.times(3); x3Count++;
//        if ( pcodeFlag ) { pcode = pcode + "X3 "; }
    }

    public void x5() {
        duration.times(5); x5Count++;
//        if ( pcodeFlag ) { pcode = pcode + "X5 "; }
    }

    public void x7() {
        duration.times(7); x7Count++;
//        if ( pcodeFlag ) { pcode = pcode + "X7 "; }
    }

    public void x11() {
        duration.times(11); x11Count++;
//        if ( pcodeFlag ) { pcode = pcode + "X7 "; }
    }

    public void x13() {
        duration.times(13); x13Count++;
//        if ( pcodeFlag ) { pcode = pcode + "X7 "; }
    }

    public void x17() {
        duration.times(17); x17Count++;
//        if ( pcodeFlag ) { pcode = pcode + "X7 "; }
    }

    public void x19() {
        duration.times(19); x19Count++;
//        if ( pcodeFlag ) { pcode = pcode + "X7 "; }
    }

    private void midiPlay() {
       int midiNumber = scale.computeMidiNumber(degree, location);
       //System.out.println("(" + midiNumber + "," + duration + ")");
    }

    private void functionalPlay() {
        //System.out.println("(" + degree + "," + duration + ")");
    }

    private void physicalPlay() {
        if ( previousmidiNumber != -1 ) {
            if ( previousmidiNumber < currentmidiNumber ) {
                System.out.println(" / ");
            } else if ( previousmidiNumber > currentmidiNumber ) {
                System.out.println(" \\ ");
            }
        }
        String pcs = scale.computePitchClass(degree);
        System.out.print("(" + pcs + "," + duration + ") ");
    }
    
    public String computPhysicalPitchClass() {
        String pcs = scale.computePitchClass(degree);
        return pcs;
    }

    private void midiRest() {
       //System.out.println("(" + -1 + "," + duration + ")");
    }

    private void functionalRest() {
        //System.out.println("(" + 0 + "," + duration + ")");
    }

    private void physicalRest() {
        //System.out.println("(" + "R" + "," + duration + ")");
    }

    public void sdot() {
        duration.times(2);
        duration.divide(3);
    }

    public void xdot() {
        duration.times(3);
        duration.divide(2);
    }

    public void changePitch() {
        if ( Random.integer(1,2) == 1 ) {
            raisePitch();
        } else {
            lowerPitch();
        }
    }

    // C-major with degree = 2 (D)
    // C-chromatic with degree = 3 (D)
    // rp
    // C-chromatic with degree = 4 (D#)
    // play
    // lp
    // C-chromatic with degree = 3
    // C-major with degree = 2
    // push-mode change to chromatic rp play lp pop-mode
    public void raisePitchX() {
        try {
            Mode m = Mode.CHROMATIC;
            Key k = getScale().key();
            String scaleName = k.name() + "-" + m.name();
            Scale s = Scale.computeScaleFromName(scaleName);
            pushScale(s);
            raisePitch();
            play();
            lowerPitch();
            popScale();
        } catch (InvalidScaleChangeException ex) {
            Logger.getLogger(SilentNote.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void lowerPitchX() {
        try {
            Mode m = Mode.CHROMATIC;
            Key k = getScale().key();
            String scaleName = k.name() + "-" + m.name();
            Scale s = Scale.computeScaleFromName(scaleName);
            pushScale(s);
            lowerPitch();
            play();
            raisePitch();
            popScale();
        } catch (InvalidScaleChangeException ex) {
            Logger.getLogger(SilentNote.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void reset() {
        resetDegree();
        resetLocation();
        resetDuration();
        resetTimbre();
        resetVolume();
        resetScale();
        resetTempo();
        resetMemory();
        previousMidiNumber = -1;
    }

    private void resetMemory() {
        memory = new Memory();
    }

    private int mapDegree(int degree, Scale currentScale, Scale nextScale) throws InvalidScaleChangeException {
       Key csKey = currentScale.key();
       Key nsKey = nextScale.key();
       Mode csMode = currentScale.mode();
       Mode nsMode = nextScale.mode();
       if ( csKey.name().equals(nsKey.name())) {
           try {
               return mapDegree(degree, csMode, nsMode);
           } catch (InvalidModeChangeException ex) {
               throw new InvalidScaleChangeException();
           }
       } else if ( csMode.equals(Mode.CHROMATIC) & ( nsMode.equals(Mode.CHROMATIC) ) ) {
           return mapChromaticDegree(degree, csKey, nsKey);
       } else if ( csMode.equals(Mode.CHROMATIC) & ( nsMode.equals(Mode.MAJOR) ) ) {
           return mapDegreeFromChromaticToMajor(degree, csKey, nsKey);
       } else {
           // map csKey/csMode to csKey/chromatic
           Scale s1 = new Scale(csKey,Mode.CHROMATIC);
           int d1 = mapDegree(degree,currentScale,s1);
           //System.out.println("POINT1:");
           //System.out.println("s1:  " + s1.toString());
           //System.out.println("d1=" + d1);
           // map csKey/chromatic to nsKey/chromatic
           Scale s2 = new Scale(nsKey,Mode.CHROMATIC);
           int d2 = mapDegree(d1,s1,s2);
           //System.out.println("POINT2:");
           //System.out.println("s2:  " + s2.toString());
           //System.out.println("d2=" + d2);
           // map nsKey/chromatic to nsKey/nsMode
           Scale s3 = new Scale(nsKey,nsMode);
           int d3 = mapDegree(d2,s2,s3);
           //System.out.println("POINT3:");
           //System.out.println("s3:  " + s3.toString());
           //System.out.println("d3=" + d3);
          return d3;
       }
    }

    public int mapChromaticDegree(int degree, Key csKey, Key nsKey) {

        if ( csKey.equals(Key.C) & nsKey.equals(Key.C) ) { return degree; }
        if ( csKey.equals(Key.C) & nsKey.equals(Key.V) ) { return xdp(degree,1); }
        if ( csKey.equals(Key.C) & nsKey.equals(Key.D) ) { return xdp(degree,2); }
        if ( csKey.equals(Key.C) & nsKey.equals(Key.W) ) { return xdp(degree,3); }
        if ( csKey.equals(Key.C) & nsKey.equals(Key.E) ) { return xdp(degree,4); }
        if ( csKey.equals(Key.C) & nsKey.equals(Key.F) ) { return xdp(degree,5); }
        if ( csKey.equals(Key.C) & nsKey.equals(Key.X) ) { return xdp(degree,6); }
        if ( csKey.equals(Key.C) & nsKey.equals(Key.G) ) { return xdp(degree,7); }
        if ( csKey.equals(Key.C) & nsKey.equals(Key.Y) ) { return xdp(degree,8); }
        if ( csKey.equals(Key.C) & nsKey.equals(Key.A) ) { return xdp(degree,9); }
        if ( csKey.equals(Key.C) & nsKey.equals(Key.Z) ) { return xdp(degree,10); }
        if ( csKey.equals(Key.C) & nsKey.equals(Key.B) ) { return xdp(degree,11); }

        if ( csKey.equals(Key.V) & nsKey.equals(Key.V) ) { return degree; }
        if ( csKey.equals(Key.V) & nsKey.equals(Key.D) ) { return xdp(degree,1); }
        if ( csKey.equals(Key.V) & nsKey.equals(Key.W) ) { return xdp(degree,2); }
        if ( csKey.equals(Key.V) & nsKey.equals(Key.E) ) { return xdp(degree,3); }
        if ( csKey.equals(Key.V) & nsKey.equals(Key.F) ) { return xdp(degree,4); }
        if ( csKey.equals(Key.V) & nsKey.equals(Key.X) ) { return xdp(degree,5); }
        if ( csKey.equals(Key.V) & nsKey.equals(Key.G) ) { return xdp(degree,6); }
        if ( csKey.equals(Key.V) & nsKey.equals(Key.Y) ) { return xdp(degree,7); }
        if ( csKey.equals(Key.V) & nsKey.equals(Key.A) ) { return xdp(degree,8); }
        if ( csKey.equals(Key.V) & nsKey.equals(Key.Z) ) { return xdp(degree,9); }
        if ( csKey.equals(Key.V) & nsKey.equals(Key.B) ) { return xdp(degree,10); }
        if ( csKey.equals(Key.V) & nsKey.equals(Key.C) ) { return xdp(degree,11); }

        if ( csKey.equals(Key.D) & nsKey.equals(Key.D) ) { return degree; }
        if ( csKey.equals(Key.D) & nsKey.equals(Key.W) ) { return xdp(degree,1); }
        if ( csKey.equals(Key.D) & nsKey.equals(Key.E) ) { return xdp(degree,2); }
        if ( csKey.equals(Key.D) & nsKey.equals(Key.F) ) { return xdp(degree,3); }
        if ( csKey.equals(Key.D) & nsKey.equals(Key.X) ) { return xdp(degree,4); }
        if ( csKey.equals(Key.D) & nsKey.equals(Key.G) ) { return xdp(degree,5); }
        if ( csKey.equals(Key.D) & nsKey.equals(Key.Y) ) { return xdp(degree,6); }
        if ( csKey.equals(Key.D) & nsKey.equals(Key.A) ) { return xdp(degree,7); }
        if ( csKey.equals(Key.D) & nsKey.equals(Key.Z) ) { return xdp(degree,8); }
        if ( csKey.equals(Key.D) & nsKey.equals(Key.B) ) { return xdp(degree,9); }
        if ( csKey.equals(Key.D) & nsKey.equals(Key.C) ) { return xdp(degree,10); }
        if ( csKey.equals(Key.D) & nsKey.equals(Key.V) ) { return xdp(degree,11); }

        if ( csKey.equals(Key.W) & nsKey.equals(Key.W) ) { return 1; }
        if ( csKey.equals(Key.W) & nsKey.equals(Key.E) ) { return xdp(degree,1); }
        if ( csKey.equals(Key.W) & nsKey.equals(Key.F) ) { return xdp(degree,2); }
        if ( csKey.equals(Key.W) & nsKey.equals(Key.X) ) { return xdp(degree,3); }
        if ( csKey.equals(Key.W) & nsKey.equals(Key.G) ) { return xdp(degree,4); }
        if ( csKey.equals(Key.W) & nsKey.equals(Key.Y) ) { return xdp(degree,5); }
        if ( csKey.equals(Key.W) & nsKey.equals(Key.A) ) { return xdp(degree,6); }
        if ( csKey.equals(Key.W) & nsKey.equals(Key.Z) ) { return xdp(degree,7); }
        if ( csKey.equals(Key.W) & nsKey.equals(Key.B) ) { return xdp(degree,8); }
        if ( csKey.equals(Key.W) & nsKey.equals(Key.C) ) { return xdp(degree,9); }
        if ( csKey.equals(Key.W) & nsKey.equals(Key.V) ) { return xdp(degree,10); }
        if ( csKey.equals(Key.W) & nsKey.equals(Key.D) ) { return xdp(degree,11); }

        if ( csKey.equals(Key.E) & nsKey.equals(Key.E) ) { return 1; }
        if ( csKey.equals(Key.E) & nsKey.equals(Key.F) ) { return xdp(degree,1); }
        if ( csKey.equals(Key.E) & nsKey.equals(Key.X) ) { return xdp(degree,2); }
        if ( csKey.equals(Key.E) & nsKey.equals(Key.G) ) { return xdp(degree,3); }
        if ( csKey.equals(Key.E) & nsKey.equals(Key.Y) ) { return xdp(degree,4); }
        if ( csKey.equals(Key.E) & nsKey.equals(Key.A) ) { return xdp(degree,5); }
        if ( csKey.equals(Key.E) & nsKey.equals(Key.Z) ) { return xdp(degree,6); }
        if ( csKey.equals(Key.E) & nsKey.equals(Key.B) ) { return xdp(degree,7); }
        if ( csKey.equals(Key.E) & nsKey.equals(Key.C) ) { return xdp(degree,8); }
        if ( csKey.equals(Key.E) & nsKey.equals(Key.V) ) { return xdp(degree,9); }
        if ( csKey.equals(Key.E) & nsKey.equals(Key.D) ) { return xdp(degree,10); }
        if ( csKey.equals(Key.E) & nsKey.equals(Key.W) ) { return xdp(degree,11); }

        if ( csKey.equals(Key.F) & nsKey.equals(Key.F) ) { return 1; }
        if ( csKey.equals(Key.F) & nsKey.equals(Key.X) ) { return xdp(degree,1); }
        if ( csKey.equals(Key.F) & nsKey.equals(Key.G) ) { return xdp(degree,2); }
        if ( csKey.equals(Key.F) & nsKey.equals(Key.Y) ) { return xdp(degree,3); }
        if ( csKey.equals(Key.F) & nsKey.equals(Key.A) ) { return xdp(degree,4); }
        if ( csKey.equals(Key.F) & nsKey.equals(Key.Z) ) { return xdp(degree,5); }
        if ( csKey.equals(Key.F) & nsKey.equals(Key.B) ) { return xdp(degree,6); }
        if ( csKey.equals(Key.F) & nsKey.equals(Key.C) ) { return xdp(degree,7); }
        if ( csKey.equals(Key.F) & nsKey.equals(Key.V) ) { return xdp(degree,8); }
        if ( csKey.equals(Key.F) & nsKey.equals(Key.D) ) { return xdp(degree,9); }
        if ( csKey.equals(Key.F) & nsKey.equals(Key.W) ) { return xdp(degree,10); }
        if ( csKey.equals(Key.F) & nsKey.equals(Key.E) ) { return xdp(degree,11); }

        if ( csKey.equals(Key.X) & nsKey.equals(Key.X) ) { return 1; }
        if ( csKey.equals(Key.X) & nsKey.equals(Key.G) ) { return xdp(degree,1); }
        if ( csKey.equals(Key.X) & nsKey.equals(Key.Y) ) { return xdp(degree,2); }
        if ( csKey.equals(Key.X) & nsKey.equals(Key.A) ) { return xdp(degree,3); }
        if ( csKey.equals(Key.X) & nsKey.equals(Key.Z) ) { return xdp(degree,4); }
        if ( csKey.equals(Key.X) & nsKey.equals(Key.B) ) { return xdp(degree,5); }
        if ( csKey.equals(Key.X) & nsKey.equals(Key.C) ) { return xdp(degree,6); }
        if ( csKey.equals(Key.X) & nsKey.equals(Key.V) ) { return xdp(degree,7); }
        if ( csKey.equals(Key.X) & nsKey.equals(Key.D) ) { return xdp(degree,8); }
        if ( csKey.equals(Key.X) & nsKey.equals(Key.W) ) { return xdp(degree,9); }
        if ( csKey.equals(Key.X) & nsKey.equals(Key.E) ) { return xdp(degree,10); }
        if ( csKey.equals(Key.X) & nsKey.equals(Key.F) ) { return xdp(degree,11); }

        if ( csKey.equals(Key.G) & nsKey.equals(Key.G) ) { return 1; }
        if ( csKey.equals(Key.G) & nsKey.equals(Key.Y) ) { return xdp(degree,1); }
        if ( csKey.equals(Key.G) & nsKey.equals(Key.A) ) { return xdp(degree,2); }
        if ( csKey.equals(Key.G) & nsKey.equals(Key.Z) ) { return xdp(degree,3); }
        if ( csKey.equals(Key.G) & nsKey.equals(Key.B) ) { return xdp(degree,4); }
        if ( csKey.equals(Key.G) & nsKey.equals(Key.C) ) { return xdp(degree,5); }
        if ( csKey.equals(Key.G) & nsKey.equals(Key.V) ) { return xdp(degree,6); }
        if ( csKey.equals(Key.G) & nsKey.equals(Key.D) ) { return xdp(degree,7); }
        if ( csKey.equals(Key.G) & nsKey.equals(Key.W) ) { return xdp(degree,8); }
        if ( csKey.equals(Key.G) & nsKey.equals(Key.E) ) { return xdp(degree,9); }
        if ( csKey.equals(Key.G) & nsKey.equals(Key.F) ) { return xdp(degree,10); }
        if ( csKey.equals(Key.G) & nsKey.equals(Key.X) ) { return xdp(degree,11); }

        if ( csKey.equals(Key.Y) & nsKey.equals(Key.Y) ) { return 1; }
        if ( csKey.equals(Key.Y) & nsKey.equals(Key.A) ) { return xdp(degree,1); }
        if ( csKey.equals(Key.Y) & nsKey.equals(Key.Z) ) { return xdp(degree,2); }
        if ( csKey.equals(Key.Y) & nsKey.equals(Key.B) ) { return xdp(degree,3); }
        if ( csKey.equals(Key.Y) & nsKey.equals(Key.C) ) { return xdp(degree,4); }
        if ( csKey.equals(Key.Y) & nsKey.equals(Key.V) ) { return xdp(degree,5); }
        if ( csKey.equals(Key.Y) & nsKey.equals(Key.D) ) { return xdp(degree,6); }
        if ( csKey.equals(Key.Y) & nsKey.equals(Key.W) ) { return xdp(degree,7); }
        if ( csKey.equals(Key.Y) & nsKey.equals(Key.E) ) { return xdp(degree,8); }
        if ( csKey.equals(Key.Y) & nsKey.equals(Key.F) ) { return xdp(degree,9); }
        if ( csKey.equals(Key.Y) & nsKey.equals(Key.X) ) { return xdp(degree,10); }
        if ( csKey.equals(Key.Y) & nsKey.equals(Key.G) ) { return xdp(degree,11); }

        if ( csKey.equals(Key.A) & nsKey.equals(Key.A) ) { return 1; }
        if ( csKey.equals(Key.A) & nsKey.equals(Key.Z) ) { return xdp(degree,1); }
        if ( csKey.equals(Key.A) & nsKey.equals(Key.B) ) { return xdp(degree,2); }
        if ( csKey.equals(Key.A) & nsKey.equals(Key.C) ) { return xdp(degree,3); }
        if ( csKey.equals(Key.A) & nsKey.equals(Key.V) ) { return xdp(degree,4); }
        if ( csKey.equals(Key.A) & nsKey.equals(Key.D) ) { return xdp(degree,5); }
        if ( csKey.equals(Key.A) & nsKey.equals(Key.W) ) { return xdp(degree,6); }
        if ( csKey.equals(Key.A) & nsKey.equals(Key.E) ) { return xdp(degree,7); }
        if ( csKey.equals(Key.A) & nsKey.equals(Key.F) ) { return xdp(degree,8); }
        if ( csKey.equals(Key.A) & nsKey.equals(Key.X) ) { return xdp(degree,9); }
        if ( csKey.equals(Key.A) & nsKey.equals(Key.G) ) { return xdp(degree,10); }
        if ( csKey.equals(Key.A) & nsKey.equals(Key.Y) ) { return xdp(degree,11); }

        if ( csKey.equals(Key.Z) & nsKey.equals(Key.Z) ) { return 1; }
        if ( csKey.equals(Key.Z) & nsKey.equals(Key.B) ) { return xdp(degree,1); }
        if ( csKey.equals(Key.Z) & nsKey.equals(Key.C) ) { return xdp(degree,2); }
        if ( csKey.equals(Key.Z) & nsKey.equals(Key.V) ) { return xdp(degree,3); }
        if ( csKey.equals(Key.Z) & nsKey.equals(Key.D) ) { return xdp(degree,4); }
        if ( csKey.equals(Key.Z) & nsKey.equals(Key.W) ) { return xdp(degree,5); }
        if ( csKey.equals(Key.Z) & nsKey.equals(Key.E) ) { return xdp(degree,6); }
        if ( csKey.equals(Key.Z) & nsKey.equals(Key.F) ) { return xdp(degree,7); }
        if ( csKey.equals(Key.Z) & nsKey.equals(Key.X) ) { return xdp(degree,8); }
        if ( csKey.equals(Key.Z) & nsKey.equals(Key.G) ) { return xdp(degree,9); }
        if ( csKey.equals(Key.Z) & nsKey.equals(Key.Y) ) { return xdp(degree,10); }
        if ( csKey.equals(Key.Z) & nsKey.equals(Key.A) ) { return xdp(degree,11); }

        if ( csKey.equals(Key.B) & nsKey.equals(Key.B) ) { return 1; }
        if ( csKey.equals(Key.B) & nsKey.equals(Key.C) ) { return xdp(degree,1); }
        if ( csKey.equals(Key.B) & nsKey.equals(Key.V) ) { return xdp(degree,2); }
        if ( csKey.equals(Key.B) & nsKey.equals(Key.D) ) { return xdp(degree,3); }
        if ( csKey.equals(Key.B) & nsKey.equals(Key.W) ) { return xdp(degree,4); }
        if ( csKey.equals(Key.B) & nsKey.equals(Key.E) ) { return xdp(degree,5); }
        if ( csKey.equals(Key.B) & nsKey.equals(Key.F) ) { return xdp(degree,6); }
        if ( csKey.equals(Key.B) & nsKey.equals(Key.X) ) { return xdp(degree,7); }
        if ( csKey.equals(Key.B) & nsKey.equals(Key.G) ) { return xdp(degree,8); }
        if ( csKey.equals(Key.B) & nsKey.equals(Key.Y) ) { return xdp(degree,9); }
        if ( csKey.equals(Key.B) & nsKey.equals(Key.A) ) { return xdp(degree,10); }
        if ( csKey.equals(Key.B) & nsKey.equals(Key.Z) ) { return xdp(degree,11); }

        return -1;
    }

    private int xdp(int d) {
        if ( d == 1 ) {
            return 12;
        } else {
            return d - 1;
        }
    }

    private int xdp(int d, int n) {
        int v = d;
        for ( int i = 1; i <= n; i++ ) {
            v = xdp(v);
        }
        return v;
    }

    public int mapDegree(int degree, Mode currentMode, Mode nextMode) throws InvalidModeChangeException {
        //mcf.ta().append("got to mapDegree\n");
        //mcf.ta().append("nextMode = " + nextMode + "\n");
        if ( currentMode.equals(Mode.CHROMATIC) ) {
            return mapDegreeFromChromaticTo(degree,nextMode);
        } else if ( currentMode.equals(Mode.DIMINISHED) ) {
            return mapDegreeFromDiminishedTo(degree,nextMode);
        } else if ( currentMode.equals(Mode.IONIAN) ) {
            return mapDegreeFromIonianTo(degree,nextMode);
        } else if ( currentMode.equals(Mode.DORIAN) ) {
            return mapDegreeFromDorianTo(degree,nextMode);
        } else if ( currentMode.equals(Mode.PHRYGIAN) ) {
            return mapDegreeFromPhrygianTo(degree,nextMode);
        } else if ( currentMode.equals(Mode.LYDIAN) ) {
            return mapDegreeFromLydianTo(degree,nextMode);
        } else if ( currentMode.equals(Mode.MIXOLYDIAN) ) {
            return mapDegreeFromMixolydianTo(degree,nextMode);
        } else if ( currentMode.equals(Mode.AOLEAN) ) {
            return mapDegreeFromAoleanTo(degree,nextMode);
        } else if ( currentMode.equals(Mode.LOCRIAN) ) {
            return mapDegreeFromLocrianTo(degree,nextMode);
        } else if ( currentMode.equals(Mode.MAJOR) ) {
            return mapDegreeFromMajorTo(degree,nextMode);
        } else if ( currentMode.equals(Mode.MINOR) ) {
            return mapDegreeFromMinorTo(degree,nextMode);
        } else if ( currentMode.equals(Mode.BLUES) ) {
            return mapDegreeFromBluesTo(degree,nextMode);
        } else if ( currentMode.equals(Mode.WHOLETONE) ) {
            return mapDegreeFromWholetoneTo(degree,nextMode);
        } else if ( currentMode.equals(Mode.PENTAMAJOR) ) {
            return mapDegreeFromPentamajorTo(degree,nextMode);
        } else if ( currentMode.equals(Mode.PENTAMINOR) ) {
            return mapDegreeFromPentaminorTo(degree, nextMode);
        } else if ( currentMode.equals(Mode.MAJ7TH) ) {
            return mapDegreeFromMaj7thTo(degree, nextMode);
        } else if ( currentMode.equals(Mode.MIN7TH) ) {
            return mapDegreeFromMin7thTo(degree,nextMode);
        } else if ( currentMode.equals(Mode.DIM7TH) ) {
            return mapDegreeFromDim7thTo(degree,nextMode);
        } else if ( currentMode.equals(Mode.DOM7TH) ) {
            return mapDegreeFromDom7thTo(degree,nextMode);
        } else if ( currentMode.equals(Mode.MAJTRIAD) ) {
            return mapDegreeFromMajtriadTo(degree,nextMode);
        } else if ( currentMode.equals(Mode.MINTRIAD) ) {
            return mapDegreeFromMintriadTo(degree,nextMode);
        } else if ( currentMode.equals(Mode.DIMTRIAD) ) {
            return mapDegreeFromDimtriadTo(degree,nextMode);
        } else if ( currentMode.equals(Mode.AUGTRIAD) ) {
            return mapDegreeFromAugtriadTo(degree,nextMode);
        } else if ( currentMode.equals(Mode.MINSECOND) ) {
            return mapDegreeFromMinsecondTo(degree,nextMode);
        } else if ( currentMode.equals(Mode.SECOND) ) {
            return mapDegreeFromSecondTo(degree,nextMode);
        } else if ( currentMode.equals(Mode.MINTHIRD) ) {
            return mapDegreeFromMinthirdTo(degree,nextMode);
        } else if ( currentMode.equals(Mode.THIRD) ) {
            return mapDegreeFromThirdTo(degree,nextMode);
        } else if ( currentMode.equals(Mode.FOURTH) ) {
            return mapDegreeFromFourthTo(degree,nextMode);
        } else if ( currentMode.equals(Mode.TRITONE) ) {
            return mapDegreeFromTritoneTo(degree,nextMode);
        } else if ( currentMode.equals(Mode.FIFTH) ) {
            return mapDegreeFromFifthTo(degree,nextMode);
        }
        throw new InvalidModeChangeException();
    }

    public void pushScale(Scale s) throws InvalidScaleChangeException {
        pushscaleCount++;
        //System.out.println(">>> PUSH SCALE");
        Scale currentScale = getScale();
        Scale nextScale = s;
        degree = mapDegree(degree, currentScale, nextScale);
        scaleStack.push(s);
        scale = s;
        //System.out.println("<<< PUSH SCALE");
    }

//    public void pushScalePrime(Scale s) {
//        //System.out.println(">>> PUSH SCALE");
//        Scale currentScale = getScale();
//        Scale nextScale = s;
//        // degree = mapDegree(degree, currentScale, nextScale);
//        scaleStack.push(s);
//        scale = s;
//        pushNoteForAlphabetics();
//        //System.out.println("<<< PUSH SCALE");
//    }
//
    public void stopThePlaying() {
        //System.out.println("Stop the playint?");
    }

    public void pushScalePlus(Scale s) {
        pushscaleCount++;
        // Scale currentScale = getScale();
        // cale nextScale = s;
        // degree = mapDegree(degree, currentScale, nextScale);
        pushNoteForAlphabetics();
        scaleStack.push(s);
        scale = s;
        degree = 1;
    }

    public void popScale() throws InvalidScaleChangeException {
        //System.out.println(">>> POP SCALE");
        popscaleCount++;
        Scale currentScale = getScale();
        if ( scaleStack.size() < 1 ) {
            mcf.ta().append("### invalid stack pop operation\n");
            //System.out.println("### invalid stack pop operation\n");
            return;
        }
        //System.out.println("size="+scaleStack.size());
        Scale nextScale = scaleStack.elementAt(scaleStack.size()-2);
        //System.out.println("nextScale = " + nextScale.toString() + "\n");
        int d = mapDegree(degree, currentScale, nextScale);
        scaleStack.pop();
        if ( scaleStack.isEmpty() ) {
            scale = defaultScale;
        } else {
            scale = scaleStack.peek();
        }
        //System.out.println("scale = " + scale.toString() + "\n");
        degree = d;
        //System.out.println("<<< POP SCALE");
    }

    public void popScaleStar() { // throws InvalidScaleChangeException
        System.out.println(">>> POP SCALE STAR 1\n");
        popscaleCount++;
        if ( scaleStack.size() < 1 ) {
            mcf.ta().append("### invalid stack pop operation\n");
            //System.out.println("### invalid stack pop operation\n");
            return;
        }
        Scale currentScale = getScale();
        //System.out.println("size="+scaleStack.size());
        Scale nextScale = scaleStack.elementAt(scaleStack.size()-2);
        //System.out.println("nextScale = " + nextScale.toString() + "\n");
        ////int d = mapDegree(degree, currentScale, nextScale);
        scaleStack.pop();
        if ( scaleStack.isEmpty() ) {
            scale = defaultScale;
        } else {
            scale = scaleStack.peek();
        }
        //System.out.println("scale = " + scale.toString() + "\n");
        ////degree = d;
        System.out.println("--- POP SCALE STAR 2\n");
        popNoteForAlphabetics();
        System.out.println("<<< POP SCALE STAR 3\n");
    }

    protected String statshot() {
        // PR(5,5) RL(11,0) XS2357(2,2,2,2,5,5,5,5) PUPO(3,3)
        int eventCount = playCount+restCount;
        String result = "E(" + eventCount + ") PR(";
        result = result + playCount + ",";
        result = result + restCount + ") RL(";
        result = result + rpCount + ",";
        result = result + lpCount + ") XS2357(";
        result = result + x2Count + ",";
        result = result + s2Count + ",";
        result = result + x3Count + ",";
        result = result + s3Count + ",";
        result = result + x5Count + ",";
        result = result + s5Count + ",";
        result = result + x7Count + ",";
        result = result + s7Count + ") PUPO(";
        result = result + pushscaleCount + ",";
        result = result + popscaleCount + ")";
        return result;
    }

    public void resetScale() {
        scaleStack.clear();
        scaleStack.push(Scale.C_MAJOR);
        scale = defaultScale;
        degree = 1;
    }

//    public void displayScaleStack() {
//        System.out.println("TOP OF SCALESTACK");
//        for ( int i = scaleStack.size() - 1; i >= 0; i-- ) {
//            Scale s = scaleStack.get(i);
//            System.out.println(s);
//        }
//        System.out.println("BOTTOM OF SCALESTACK");
//    }

    public void displayScaleStack() {
        // System.out.println("TOP OF SCALESTACK");
        for ( int i = scaleStack.size() - 1; i >= 0; i-- ) {
            Scale s = scaleStack.get(i);
            mcf.ta().append(s+"\n");
        }
        // System.out.println("BOTTOM OF SCALESTACK");
    }



    //
    // SCALE MAPPING STARTS HERE
    //

    private int mapDegreeFromChromaticTo(int degree,Mode nextMode) throws InvalidModeChangeException {
        if ( nextMode.equals(Mode.CHROMATIC) ) {
            return mapDegreeFromChromaticToChromatic(degree);
        } else if ( nextMode.equals(Mode.DIMINISHED) ) {
            return mapDegreeFromChromaticToDiminished(degree);
        } else if ( nextMode.equals(Mode.IONIAN) ) {
            return mapDegreeFromChromaticToIonian(degree);
        } else if ( nextMode.equals(Mode.DORIAN) ) {
            return mapDegreeFromChromaticToDorian(degree);
        } else if ( nextMode.equals(Mode.PHRYGIAN) ) {
            return mapDegreeFromChromaticToPhrygian(degree);
        } else if ( nextMode.equals(Mode.LYDIAN) ) {
            return mapDegreeFromChromaticToLydian(degree);
        } else if ( nextMode.equals(Mode.MIXOLYDIAN) ) {
            return mapDegreeFromChromaticToMixolydian(degree);
        } else if ( nextMode.equals(Mode.AOLEAN) ) {
            return mapDegreeFromChromaticToAolean(degree);
        } else if ( nextMode.equals(Mode.LOCRIAN) ) {
            return mapDegreeFromChromaticToLocrian(degree);
        } else if ( nextMode.equals(Mode.MAJOR) ) {
            return mapDegreeFromChromaticToMajor(degree);
        } else if ( nextMode.equals(Mode.MINOR) ) {
            return mapDegreeFromChromaticToMinor(degree);
        } else if ( nextMode.equals(Mode.WHOLETONE) ) {
            return mapDegreeFromChromaticToWholetone(degree);
        } else if ( nextMode.equals(Mode.BLUES) ) {
            return mapDegreeFromChromaticToBlues(degree);
        } else if ( nextMode.equals(Mode.PENTAMAJOR) ) {
            return mapDegreeFromChromaticToPentamajor(degree);
        } else if ( nextMode.equals(Mode.PENTAMINOR) ) {
            return mapDegreeFromChromaticToPentaminor(degree);
        } else if ( nextMode.equals(Mode.MAJ7TH) ) {
            return mapDegreeFromChromaticToMaj7th(degree);
        } else if ( nextMode.equals(Mode.MIN7TH) ) {
            return mapDegreeFromChromaticToMin7th(degree);
        } else if ( nextMode.equals(Mode.DIM7TH) ) {
            return mapDegreeFromChromaticToDim7th(degree);
        } else if ( nextMode.equals(Mode.DOM7TH) ) {
            return mapDegreeFromChromaticToDom7th(degree);
        } else if ( nextMode.equals(Mode.MAJTRIAD) ) {
            return mapDegreeFromChromaticToMajtriad(degree);
        } else if ( nextMode.equals(Mode.MINTRIAD) ) {
            return mapDegreeFromChromaticToMintriad(degree);
        } else if ( nextMode.equals(Mode.DIMTRIAD) ) {
            return mapDegreeFromChromaticToDimtriad(degree);
        } else if ( nextMode.equals(Mode.AUGTRIAD) ) {
            return mapDegreeFromChromaticToAugtriad(degree);
        } else if ( nextMode.equals(Mode.MINSECOND) ) {
            return mapDegreeFromChromaticToMinsecond(degree);
        } else if ( nextMode.equals(Mode.SECOND) ) {
            return mapDegreeFromChromaticToSecond(degree);
        } else if ( nextMode.equals(Mode.MINTHIRD) ) {
            return mapDegreeFromChromaticToMinthird(degree);
        } else if ( nextMode.equals(Mode.THIRD) ) {
            return mapDegreeFromChromaticToThird(degree);
        } else if ( nextMode.equals(Mode.FOURTH) ) {
            return mapDegreeFromChromaticToFourth(degree);
        } else if ( nextMode.equals(Mode.TRITONE) ) {
            return mapDegreeFromChromaticToTritone(degree);
        } else if ( nextMode.equals(Mode.FIFTH) ) {
            return mapDegreeFromChromaticToFifth(degree);
        }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromDiminishedTo(int degree, Mode nextMode) throws InvalidModeChangeException {
        if ( nextMode.equals(Mode.CHROMATIC) ) {
            return mapDegreeFromDiminishedToChromatic(degree);
        } else if ( nextMode.equals(Mode.DIMINISHED) ) {
            return mapDegreeFromDiminishedToDiminished(degree);
        } else if ( nextMode.equals(Mode.IONIAN) ) {
            return mapDegreeFromDiminishedToIonian(degree);
        } else if ( nextMode.equals(Mode.DORIAN) ) {
            return mapDegreeFromDiminishedToDorian(degree);
        } else if ( nextMode.equals(Mode.PHRYGIAN) ) {
            return mapDegreeFromDiminishedToPhrygian(degree);
        } else if ( nextMode.equals(Mode.LYDIAN) ) {
            return mapDegreeFromDiminishedToLydian(degree);
        } else if ( nextMode.equals(Mode.MIXOLYDIAN) ) {
            return mapDegreeFromDiminishedToMixolydian(degree);
        } else if ( nextMode.equals(Mode.AOLEAN) ) {
            return mapDegreeFromDiminishedToAolean(degree);
        } else if ( nextMode.equals(Mode.LOCRIAN) ) {
            return mapDegreeFromDiminishedToLocrian(degree);
        } else if ( nextMode.equals(Mode.MAJOR) ) {
            return mapDegreeFromDiminishedToMajor(degree);
        } else if ( nextMode.equals(Mode.MINOR) ) {
            return mapDegreeFromDiminishedToMinor(degree);
        } else if ( nextMode.equals(Mode.WHOLETONE) ) {
            return mapDegreeFromDiminishedToWholetone(degree);
        } else if ( nextMode.equals(Mode.BLUES) ) {
            return mapDegreeFromDiminishedToBlues(degree);
        } else if ( nextMode.equals(Mode.PENTAMAJOR) ) {
            return mapDegreeFromDiminishedToPentamajor(degree);
        } else if ( nextMode.equals(Mode.PENTAMINOR) ) {
            return mapDegreeFromDiminishedToPentaminor(degree);
        } else if ( nextMode.equals(Mode.MAJ7TH) ) {
            return mapDegreeFromDiminishedToMaj7th(degree);
        } else if ( nextMode.equals(Mode.MIN7TH) ) {
            return mapDegreeFromDiminishedToMin7th(degree);
        } else if ( nextMode.equals(Mode.DIM7TH) ) {
            return mapDegreeFromDiminishedToDim7th(degree);
        } else if ( nextMode.equals(Mode.DOM7TH) ) {
            return mapDegreeFromDiminishedToDom7th(degree);
        } else if ( nextMode.equals(Mode.MAJTRIAD) ) {
            return mapDegreeFromDiminishedToMajtriad(degree);
        } else if ( nextMode.equals(Mode.MINTRIAD) ) {
            return mapDegreeFromDiminishedToMintriad(degree);
        } else if ( nextMode.equals(Mode.DIMTRIAD) ) {
            return mapDegreeFromDiminishedToDimtriad(degree);
        } else if ( nextMode.equals(Mode.AUGTRIAD) ) {
            return mapDegreeFromDiminishedToAugtriad(degree);
        } else if ( nextMode.equals(Mode.MINSECOND) ) {
            return mapDegreeFromDiminishedToMinsecond(degree);
        } else if ( nextMode.equals(Mode.SECOND) ) {
            return mapDegreeFromDiminishedToSecond(degree);
        } else if ( nextMode.equals(Mode.MINTHIRD) ) {
            return mapDegreeFromDiminishedToMinthird(degree);
        } else if ( nextMode.equals(Mode.THIRD) ) {
            return mapDegreeFromDiminishedToThird(degree);
        } else if ( nextMode.equals(Mode.FOURTH) ) {
            return mapDegreeFromDiminishedToFourth(degree);
        } else if ( nextMode.equals(Mode.TRITONE) ) {
            return mapDegreeFromDiminishedToTritone(degree);
        } else if ( nextMode.equals(Mode.FIFTH) ) {
            return mapDegreeFromDiminishedToFifth(degree);
        }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromWholetoneTo(int degree, Mode nextMode) throws InvalidModeChangeException {
        if ( nextMode.equals(Mode.CHROMATIC) ) {
            return mapDegreeFromWholetoneToChromatic(degree);
        } else if ( nextMode.equals(Mode.DIMINISHED) ) {
            return mapDegreeFromWholetoneToDiminished(degree);
        } else if ( nextMode.equals(Mode.IONIAN) ) {
            return mapDegreeFromWholetoneToIonian(degree);
        } else if ( nextMode.equals(Mode.DORIAN) ) {
            return mapDegreeFromWholetoneToDorian(degree);
        } else if ( nextMode.equals(Mode.PHRYGIAN) ) {
            return mapDegreeFromWholetoneToPhrygian(degree);
        } else if ( nextMode.equals(Mode.LYDIAN) ) {
            return mapDegreeFromWholetoneToLydian(degree);
        } else if ( nextMode.equals(Mode.MIXOLYDIAN) ) {
            return mapDegreeFromWholetoneToMixolydian(degree);
        } else if ( nextMode.equals(Mode.AOLEAN) ) {
            return mapDegreeFromWholetoneToAolean(degree);
        } else if ( nextMode.equals(Mode.LOCRIAN) ) {
            return mapDegreeFromWholetoneToLocrian(degree);
        } else if ( nextMode.equals(Mode.MAJOR) ) {
            return mapDegreeFromWholetoneToMajor(degree);
        } else if ( nextMode.equals(Mode.MINOR) ) {
            return mapDegreeFromWholetoneToMinor(degree);
        } else if ( nextMode.equals(Mode.WHOLETONE) ) {
            return mapDegreeFromWholetoneToWholetone(degree);
        } else if ( nextMode.equals(Mode.BLUES) ) {
            return mapDegreeFromWholetoneToBlues(degree);
        } else if ( nextMode.equals(Mode.PENTAMAJOR) ) {
            return mapDegreeFromWholetoneToPentamajor(degree);
        } else if ( nextMode.equals(Mode.PENTAMINOR) ) {
            return mapDegreeFromWholetoneToPentaminor(degree);
        } else if ( nextMode.equals(Mode.MAJ7TH) ) {
            return mapDegreeFromWholetoneToMaj7th(degree);
        } else if ( nextMode.equals(Mode.MIN7TH) ) {
            return mapDegreeFromWholetoneToMin7th(degree);
        } else if ( nextMode.equals(Mode.DIM7TH) ) {
            return mapDegreeFromWholetoneToDim7th(degree);
        } else if ( nextMode.equals(Mode.DOM7TH) ) {
            return mapDegreeFromWholetoneToDom7th(degree);
        } else if ( nextMode.equals(Mode.MAJTRIAD) ) {
            return mapDegreeFromWholetoneToMajtriad(degree);
        } else if ( nextMode.equals(Mode.MINTRIAD) ) {
            return mapDegreeFromWholetoneToMintriad(degree);
        } else if ( nextMode.equals(Mode.DIMTRIAD) ) {
            return mapDegreeFromWholetoneToDimtriad(degree);
        } else if ( nextMode.equals(Mode.AUGTRIAD) ) {
            return mapDegreeFromWholetoneToAugtriad(degree);
        } else if ( nextMode.equals(Mode.MINSECOND) ) {
            return mapDegreeFromWholetoneToMinsecond(degree);
        } else if ( nextMode.equals(Mode.SECOND) ) {
            return mapDegreeFromWholetoneToSecond(degree);
        } else if ( nextMode.equals(Mode.MINTHIRD) ) {
            return mapDegreeFromWholetoneToMinthird(degree);
        } else if ( nextMode.equals(Mode.THIRD) ) {
            return mapDegreeFromWholetoneToThird(degree);
        } else if ( nextMode.equals(Mode.FOURTH) ) {
            return mapDegreeFromWholetoneToFourth(degree);
        } else if ( nextMode.equals(Mode.TRITONE) ) {
            return mapDegreeFromWholetoneToTritone(degree);
        } else if ( nextMode.equals(Mode.FIFTH) ) {
            return mapDegreeFromWholetoneToFifth(degree);
        }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromBluesTo(int degree, Mode nextMode) throws InvalidModeChangeException {
        if ( nextMode.equals(Mode.CHROMATIC) ) {
            return mapDegreeFromBluesToChromatic(degree);
        } else if ( nextMode.equals(Mode.DIMINISHED) ) {
            return mapDegreeFromBluesToDiminished(degree);
        } else if ( nextMode.equals(Mode.IONIAN) ) {
            return mapDegreeFromBluesToIonian(degree);
        } else if ( nextMode.equals(Mode.DORIAN) ) {
            return mapDegreeFromBluesToDorian(degree);
        } else if ( nextMode.equals(Mode.PHRYGIAN) ) {
            return mapDegreeFromBluesToPhrygian(degree);
        } else if ( nextMode.equals(Mode.LYDIAN) ) {
            return mapDegreeFromBluesToLydian(degree);
        } else if ( nextMode.equals(Mode.MIXOLYDIAN) ) {
            return mapDegreeFromBluesToMixolydian(degree);
        } else if ( nextMode.equals(Mode.AOLEAN) ) {
            return mapDegreeFromBluesToAolean(degree);
        } else if ( nextMode.equals(Mode.LOCRIAN) ) {
            return mapDegreeFromBluesToLocrian(degree);
        } else if ( nextMode.equals(Mode.MAJOR) ) {
            return mapDegreeFromBluesToMajor(degree);
        } else if ( nextMode.equals(Mode.MINOR) ) {
            return mapDegreeFromBluesToMinor(degree);
        } else if ( nextMode.equals(Mode.WHOLETONE) ) {
            return mapDegreeFromBluesToWholetone(degree);
        } else if ( nextMode.equals(Mode.BLUES) ) {
            return mapDegreeFromBluesToBlues(degree);
        } else if ( nextMode.equals(Mode.PENTAMAJOR) ) {
            return mapDegreeFromBluesToPentamajor(degree);
        } else if ( nextMode.equals(Mode.PENTAMINOR) ) {
            return mapDegreeFromBluesToPentaminor(degree);
        } else if ( nextMode.equals(Mode.MAJ7TH) ) {
            return mapDegreeFromBluesToMaj7th(degree);
        } else if ( nextMode.equals(Mode.MIN7TH) ) {
            return mapDegreeFromBluesToMin7th(degree);
        } else if ( nextMode.equals(Mode.DIM7TH) ) {
            return mapDegreeFromBluesToDim7th(degree);
        } else if ( nextMode.equals(Mode.DOM7TH) ) {
            return mapDegreeFromBluesToDom7th(degree);
        } else if ( nextMode.equals(Mode.MAJTRIAD) ) {
            return mapDegreeFromBluesToMajtriad(degree);
        } else if ( nextMode.equals(Mode.MINTRIAD) ) {
            return mapDegreeFromBluesToMintriad(degree);
        } else if ( nextMode.equals(Mode.DIMTRIAD) ) {
            return mapDegreeFromBluesToDimtriad(degree);
        } else if ( nextMode.equals(Mode.AUGTRIAD) ) {
            return mapDegreeFromBluesToAugtriad(degree);
        } else if ( nextMode.equals(Mode.MINSECOND) ) {
            return mapDegreeFromBluesToMinsecond(degree);
        } else if ( nextMode.equals(Mode.SECOND) ) {
            return mapDegreeFromBluesToSecond(degree);
        } else if ( nextMode.equals(Mode.MINTHIRD) ) {
            return mapDegreeFromBluesToMinthird(degree);
        } else if ( nextMode.equals(Mode.THIRD) ) {
            return mapDegreeFromBluesToThird(degree);
        } else if ( nextMode.equals(Mode.FOURTH) ) {
            return mapDegreeFromBluesToFourth(degree);
        } else if ( nextMode.equals(Mode.TRITONE) ) {
            return mapDegreeFromBluesToTritone(degree);
        } else if ( nextMode.equals(Mode.FIFTH) ) {
            return mapDegreeFromBluesToFifth(degree);
        }
        throw new InvalidModeChangeException();
    }

    // more work to do

    private int mapDegreeFromIonianTo(int degree, Mode nextMode) throws InvalidModeChangeException {
        if ( nextMode.equals(Mode.CHROMATIC) ) {
            return mapDegreeFromIonianToChromatic(degree);
        } else if ( nextMode.equals(Mode.DIMINISHED) ) {
            return mapDegreeFromIonianToDiminished(degree);
        } else if ( nextMode.equals(Mode.IONIAN) ) {
            return mapDegreeFromIonianToIonian(degree);
        } else if ( nextMode.equals(Mode.DORIAN) ) {
            return mapDegreeFromIonianToDorian(degree);
        } else if ( nextMode.equals(Mode.PHRYGIAN) ) {
            return mapDegreeFromIonianToPhrygian(degree);
        } else if ( nextMode.equals(Mode.LYDIAN) ) {
            return mapDegreeFromIonianToLydian(degree);
        } else if ( nextMode.equals(Mode.MIXOLYDIAN) ) {
            return mapDegreeFromIonianToMixolydian(degree);
        } else if ( nextMode.equals(Mode.AOLEAN) ) {
            return mapDegreeFromIonianToAolean(degree);
        } else if ( nextMode.equals(Mode.LOCRIAN) ) {
            return mapDegreeFromIonianToLocrian(degree);
        } else if ( nextMode.equals(Mode.IONIAN) ) {
            return mapDegreeFromIonianToIonian(degree);
        } else if ( nextMode.equals(Mode.MINOR) ) {
            return mapDegreeFromIonianToMinor(degree);
        } else if ( nextMode.equals(Mode.WHOLETONE) ) {
            return mapDegreeFromIonianToWholetone(degree);
        } else if ( nextMode.equals(Mode.BLUES) ) {
            return mapDegreeFromIonianToBlues(degree);
        } else if ( nextMode.equals(Mode.PENTAMAJOR) ) {
            return mapDegreeFromIonianToPentamajor(degree);
        } else if ( nextMode.equals(Mode.PENTAMINOR) ) {
            return mapDegreeFromIonianToPentaminor(degree);
        } else if ( nextMode.equals(Mode.MAJ7TH) ) {
            return mapDegreeFromIonianToMaj7th(degree);
        } else if ( nextMode.equals(Mode.MIN7TH) ) {
            return mapDegreeFromIonianToMin7th(degree);
        } else if ( nextMode.equals(Mode.DIM7TH) ) {
            return mapDegreeFromIonianToDim7th(degree);
        } else if ( nextMode.equals(Mode.DOM7TH) ) {
            return mapDegreeFromIonianToDom7th(degree);
        } else if ( nextMode.equals(Mode.MAJTRIAD) ) {
            return mapDegreeFromIonianToMajtriad(degree);
        } else if ( nextMode.equals(Mode.MINTRIAD) ) {
            return mapDegreeFromIonianToMintriad(degree);
        } else if ( nextMode.equals(Mode.DIMTRIAD) ) {
            return mapDegreeFromIonianToDimtriad(degree);
        } else if ( nextMode.equals(Mode.AUGTRIAD) ) {
            return mapDegreeFromIonianToAugtriad(degree);
        } else if ( nextMode.equals(Mode.MINSECOND) ) {
            return mapDegreeFromIonianToMinsecond(degree);
        } else if ( nextMode.equals(Mode.SECOND) ) {
            return mapDegreeFromIonianToSecond(degree);
        } else if ( nextMode.equals(Mode.MINTHIRD) ) {
            return mapDegreeFromIonianToMinthird(degree);
        } else if ( nextMode.equals(Mode.THIRD) ) {
            return mapDegreeFromIonianToThird(degree);
        } else if ( nextMode.equals(Mode.FOURTH) ) {
            return mapDegreeFromIonianToFourth(degree);
        } else if ( nextMode.equals(Mode.TRITONE) ) {
            return mapDegreeFromIonianToTritone(degree);
        } else if ( nextMode.equals(Mode.FIFTH) ) {
            return mapDegreeFromIonianToFifth(degree);
        }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromDorianTo(int degree, Mode nextMode) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private int mapDegreeFromPhrygianTo(int degree, Mode nextMode) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private int mapDegreeFromLydianTo(int degree, Mode nextMode) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private int mapDegreeFromMixolydianTo(int degree, Mode nextMode) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private int mapDegreeFromAoleanTo(int degree, Mode nextMode) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private int mapDegreeFromLocrianTo(int degree, Mode nextMode) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private int mapDegreeFromMajorTo(int degree, Mode nextMode) throws InvalidModeChangeException {
        if ( nextMode.equals(Mode.CHROMATIC) ) {
            return mapDegreeFromMajorToChromatic(degree);
        } else if ( nextMode.equals(Mode.DIMINISHED) ) {
            return mapDegreeFromMajorToDiminished(degree);
        } else if ( nextMode.equals(Mode.IONIAN) ) {
            return mapDegreeFromMajorToIonian(degree);
        } else if ( nextMode.equals(Mode.DORIAN) ) {
            return mapDegreeFromMajorToDorian(degree);
        } else if ( nextMode.equals(Mode.PHRYGIAN) ) {
            return mapDegreeFromMajorToPhrygian(degree);
        } else if ( nextMode.equals(Mode.LYDIAN) ) {
            return mapDegreeFromMajorToLydian(degree);
        } else if ( nextMode.equals(Mode.MIXOLYDIAN) ) {
            return mapDegreeFromMajorToMixolydian(degree);
        } else if ( nextMode.equals(Mode.AOLEAN) ) {
            return mapDegreeFromMajorToAolean(degree);
        } else if ( nextMode.equals(Mode.LOCRIAN) ) {
            return mapDegreeFromMajorToLocrian(degree);
        } else if ( nextMode.equals(Mode.MAJOR) ) {
            return mapDegreeFromMajorToMajor(degree);
        } else if ( nextMode.equals(Mode.MINOR) ) {
            return mapDegreeFromMajorToMinor(degree);
        } else if ( nextMode.equals(Mode.WHOLETONE) ) {
            return mapDegreeFromMajorToWholetone(degree);
        } else if ( nextMode.equals(Mode.BLUES) ) {
            return mapDegreeFromMajorToBlues(degree);
        } else if ( nextMode.equals(Mode.PENTAMAJOR) ) {
            return mapDegreeFromMajorToPentamajor(degree);
        } else if ( nextMode.equals(Mode.PENTAMINOR) ) {
            return mapDegreeFromMajorToPentaminor(degree);
        } else if ( nextMode.equals(Mode.MAJ7TH) ) {
            return mapDegreeFromMajorToMaj7th(degree);
        } else if ( nextMode.equals(Mode.MIN7TH) ) {
            return mapDegreeFromMajorToMin7th(degree);
        } else if ( nextMode.equals(Mode.DIM7TH) ) {
            return mapDegreeFromMajorToDim7th(degree);
        } else if ( nextMode.equals(Mode.DOM7TH) ) {
            return mapDegreeFromMajorToDom7th(degree);
        } else if ( nextMode.equals(Mode.MAJTRIAD) ) {
            return mapDegreeFromMajorToMajtriad(degree);
        } else if ( nextMode.equals(Mode.MINTRIAD) ) {
            return mapDegreeFromMajorToMintriad(degree);
        } else if ( nextMode.equals(Mode.DIMTRIAD) ) {
            return mapDegreeFromMajorToDimtriad(degree);
        } else if ( nextMode.equals(Mode.AUGTRIAD) ) {
            return mapDegreeFromMajorToAugtriad(degree);
        } else if ( nextMode.equals(Mode.MINSECOND) ) {
            return mapDegreeFromMajorToMinsecond(degree);
        } else if ( nextMode.equals(Mode.SECOND) ) {
            return mapDegreeFromMajorToSecond(degree);
        } else if ( nextMode.equals(Mode.MINTHIRD) ) {
            return mapDegreeFromMajorToMinthird(degree);
        } else if ( nextMode.equals(Mode.THIRD) ) {
            return mapDegreeFromMajorToThird(degree);
        } else if ( nextMode.equals(Mode.FOURTH) ) {
            return mapDegreeFromMajorToFourth(degree);
        } else if ( nextMode.equals(Mode.TRITONE) ) {
            return mapDegreeFromMajorToTritone(degree);
        } else if ( nextMode.equals(Mode.FIFTH) ) {
            return mapDegreeFromMajorToFifth(degree);
        }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromMinorTo(int degree, Mode nextMode) throws InvalidModeChangeException {
        if ( nextMode.equals(Mode.CHROMATIC) ) {
            return mapDegreeFromMinorToChromatic(degree);
        } else if ( nextMode.equals(Mode.DIMINISHED) ) {
            return mapDegreeFromMinorToDiminished(degree);
        } else if ( nextMode.equals(Mode.IONIAN) ) {
            return mapDegreeFromMinorToIonian(degree);
        } else if ( nextMode.equals(Mode.DORIAN) ) {
            return mapDegreeFromMinorToDorian(degree);
        } else if ( nextMode.equals(Mode.PHRYGIAN) ) {
            return mapDegreeFromMinorToPhrygian(degree);
        } else if ( nextMode.equals(Mode.LYDIAN) ) {
            return mapDegreeFromMinorToLydian(degree);
        } else if ( nextMode.equals(Mode.MIXOLYDIAN) ) {
            return mapDegreeFromMinorToMixolydian(degree);
        } else if ( nextMode.equals(Mode.AOLEAN) ) {
            return mapDegreeFromMinorToAolean(degree);
        } else if ( nextMode.equals(Mode.LOCRIAN) ) {
            return mapDegreeFromMinorToLocrian(degree);
        } else if ( nextMode.equals(Mode.MAJOR) ) {
            return mapDegreeFromMinorToMajor(degree);
        } else if ( nextMode.equals(Mode.MINOR) ) {
            return mapDegreeFromMinorToMinor(degree);
        } else if ( nextMode.equals(Mode.WHOLETONE) ) {
            return mapDegreeFromMinorToWholetone(degree);
        } else if ( nextMode.equals(Mode.BLUES) ) {
            return mapDegreeFromMinorToBlues(degree);
        } else if ( nextMode.equals(Mode.PENTAMAJOR) ) {
            return mapDegreeFromMinorToPentamajor(degree);
        } else if ( nextMode.equals(Mode.PENTAMINOR) ) {
            return mapDegreeFromMinorToPentaminor(degree);
        } else if ( nextMode.equals(Mode.MAJ7TH) ) {
            return mapDegreeFromMinorToMaj7th(degree);
        } else if ( nextMode.equals(Mode.MIN7TH) ) {
            return mapDegreeFromMinorToMin7th(degree);
        } else if ( nextMode.equals(Mode.DIM7TH) ) {
            return mapDegreeFromMinorToDim7th(degree);
        } else if ( nextMode.equals(Mode.DOM7TH) ) {
            return mapDegreeFromMinorToDom7th(degree);
        } else if ( nextMode.equals(Mode.MAJTRIAD) ) {
            return mapDegreeFromMinorToMajtriad(degree);
        } else if ( nextMode.equals(Mode.MINTRIAD) ) {
            return mapDegreeFromMinorToMintriad(degree);
        } else if ( nextMode.equals(Mode.DIMTRIAD) ) {
            return mapDegreeFromMinorToDimtriad(degree);
        } else if ( nextMode.equals(Mode.AUGTRIAD) ) {
            return mapDegreeFromMinorToAugtriad(degree);
        } else if ( nextMode.equals(Mode.MINSECOND) ) {
            return mapDegreeFromMinorToMinsecond(degree);
        } else if ( nextMode.equals(Mode.SECOND) ) {
            return mapDegreeFromMinorToSecond(degree);
        } else if ( nextMode.equals(Mode.MINTHIRD) ) {
            return mapDegreeFromMinorToMinthird(degree);
        } else if ( nextMode.equals(Mode.THIRD) ) {
            return mapDegreeFromMinorToThird(degree);
        } else if ( nextMode.equals(Mode.FOURTH) ) {
            return mapDegreeFromMinorToFourth(degree);
        } else if ( nextMode.equals(Mode.TRITONE) ) {
            return mapDegreeFromMinorToTritone(degree);
        } else if ( nextMode.equals(Mode.FIFTH) ) {
            return mapDegreeFromMinorToFifth(degree);
        }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromPentamajorTo(int degree, Mode nextMode) throws InvalidModeChangeException {
        if ( nextMode.equals(Mode.CHROMATIC) ) {
            return mapDegreeFromPentamajorToChromatic(degree);
        } else if ( nextMode.equals(Mode.DIMINISHED) ) {
            return mapDegreeFromPentamajorToDiminished(degree);
        } else if ( nextMode.equals(Mode.IONIAN) ) {
            return mapDegreeFromPentamajorToIonian(degree);
        } else if ( nextMode.equals(Mode.DORIAN) ) {
            return mapDegreeFromPentamajorToDorian(degree);
        } else if ( nextMode.equals(Mode.PHRYGIAN) ) {
            return mapDegreeFromPentamajorToPhrygian(degree);
        } else if ( nextMode.equals(Mode.LYDIAN) ) {
            return mapDegreeFromPentamajorToLydian(degree);
        } else if ( nextMode.equals(Mode.MIXOLYDIAN) ) {
            return mapDegreeFromPentamajorToMixolydian(degree);
        } else if ( nextMode.equals(Mode.AOLEAN) ) {
            return mapDegreeFromPentamajorToAolean(degree);
        } else if ( nextMode.equals(Mode.LOCRIAN) ) {
            return mapDegreeFromPentamajorToLocrian(degree);
        } else if ( nextMode.equals(Mode.MAJOR) ) {
            return mapDegreeFromPentamajorToMajor(degree);
        } else if ( nextMode.equals(Mode.MINOR) ) {
            return mapDegreeFromPentamajorToMinor(degree);
        } else if ( nextMode.equals(Mode.WHOLETONE) ) {
            return mapDegreeFromPentamajorToWholetone(degree);
        } else if ( nextMode.equals(Mode.BLUES) ) {
            return mapDegreeFromPentamajorToBlues(degree);
        } else if ( nextMode.equals(Mode.PENTAMAJOR) ) {
            return mapDegreeFromPentamajorToPentamajor(degree);
        } else if ( nextMode.equals(Mode.PENTAMINOR) ) {
            return mapDegreeFromPentamajorToPentaminor(degree);
        } else if ( nextMode.equals(Mode.MAJ7TH) ) {
            return mapDegreeFromPentamajorToMaj7th(degree);
        } else if ( nextMode.equals(Mode.MIN7TH) ) {
            return mapDegreeFromPentamajorToMin7th(degree);
        } else if ( nextMode.equals(Mode.DIM7TH) ) {
            return mapDegreeFromPentamajorToDim7th(degree);
        } else if ( nextMode.equals(Mode.DOM7TH) ) {
            return mapDegreeFromPentamajorToDom7th(degree);
        } else if ( nextMode.equals(Mode.MAJTRIAD) ) {
            return mapDegreeFromPentamajorToMajtriad(degree);
        } else if ( nextMode.equals(Mode.MINTRIAD) ) {
            return mapDegreeFromPentamajorToMintriad(degree);
        } else if ( nextMode.equals(Mode.DIMTRIAD) ) {
            return mapDegreeFromPentamajorToDimtriad(degree);
        } else if ( nextMode.equals(Mode.AUGTRIAD) ) {
            return mapDegreeFromPentamajorToAugtriad(degree);
        } else if ( nextMode.equals(Mode.MINSECOND) ) {
            return mapDegreeFromPentamajorToMinsecond(degree);
        } else if ( nextMode.equals(Mode.SECOND) ) {
            return mapDegreeFromPentamajorToSecond(degree);
        } else if ( nextMode.equals(Mode.MINTHIRD) ) {
            return mapDegreeFromPentamajorToMinthird(degree);
        } else if ( nextMode.equals(Mode.THIRD) ) {
            return mapDegreeFromPentamajorToThird(degree);
        } else if ( nextMode.equals(Mode.FOURTH) ) {
            return mapDegreeFromPentamajorToFourth(degree);
        } else if ( nextMode.equals(Mode.TRITONE) ) {
            return mapDegreeFromPentamajorToTritone(degree);
        } else if ( nextMode.equals(Mode.FIFTH) ) {
            return mapDegreeFromPentamajorToFifth(degree);
        }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromPentaminorTo(int degree, Mode nextMode) throws InvalidModeChangeException {
        if ( nextMode.equals(Mode.CHROMATIC) ) {
            return mapDegreeFromPentaminorToChromatic(degree);
        } else if ( nextMode.equals(Mode.DIMINISHED) ) {
            return mapDegreeFromPentaminorToDiminished(degree);
        } else if ( nextMode.equals(Mode.IONIAN) ) {
            return mapDegreeFromPentaminorToIonian(degree);
        } else if ( nextMode.equals(Mode.DORIAN) ) {
            return mapDegreeFromPentaminorToDorian(degree);
        } else if ( nextMode.equals(Mode.PHRYGIAN) ) {
            return mapDegreeFromPentaminorToPhrygian(degree);
        } else if ( nextMode.equals(Mode.LYDIAN) ) {
            return mapDegreeFromPentaminorToLydian(degree);
        } else if ( nextMode.equals(Mode.MIXOLYDIAN) ) {
            return mapDegreeFromPentaminorToMixolydian(degree);
        } else if ( nextMode.equals(Mode.AOLEAN) ) {
            return mapDegreeFromPentaminorToAolean(degree);
        } else if ( nextMode.equals(Mode.LOCRIAN) ) {
            return mapDegreeFromPentaminorToLocrian(degree);
        } else if ( nextMode.equals(Mode.MAJOR) ) {
            return mapDegreeFromPentaminorToMajor(degree);
        } else if ( nextMode.equals(Mode.MINOR) ) {
            return mapDegreeFromPentaminorToMinor(degree);
        } else if ( nextMode.equals(Mode.WHOLETONE) ) {
            return mapDegreeFromPentaminorToWholetone(degree);
        } else if ( nextMode.equals(Mode.BLUES) ) {
            return mapDegreeFromPentaminorToBlues(degree);
        } else if ( nextMode.equals(Mode.PENTAMAJOR) ) {
            return mapDegreeFromPentaminorToPentamajor(degree);
        } else if ( nextMode.equals(Mode.PENTAMINOR) ) {
            return mapDegreeFromPentaminorToPentaminor(degree);
        } else if ( nextMode.equals(Mode.MAJ7TH) ) {
            return mapDegreeFromPentaminorToMaj7th(degree);
        } else if ( nextMode.equals(Mode.MIN7TH) ) {
            return mapDegreeFromPentaminorToMin7th(degree);
        } else if ( nextMode.equals(Mode.DIM7TH) ) {
            return mapDegreeFromPentaminorToDim7th(degree);
        } else if ( nextMode.equals(Mode.DOM7TH) ) {
            return mapDegreeFromPentaminorToDom7th(degree);
        } else if ( nextMode.equals(Mode.MAJTRIAD) ) {
            return mapDegreeFromPentaminorToMajtriad(degree);
        } else if ( nextMode.equals(Mode.MINTRIAD) ) {
            return mapDegreeFromPentaminorToMintriad(degree);
        } else if ( nextMode.equals(Mode.DIMTRIAD) ) {
            return mapDegreeFromPentaminorToDimtriad(degree);
        } else if ( nextMode.equals(Mode.AUGTRIAD) ) {
            return mapDegreeFromPentaminorToAugtriad(degree);
        } else if ( nextMode.equals(Mode.MINSECOND) ) {
            return mapDegreeFromPentaminorToMinsecond(degree);
        } else if ( nextMode.equals(Mode.SECOND) ) {
            return mapDegreeFromPentaminorToSecond(degree);
        } else if ( nextMode.equals(Mode.MINTHIRD) ) {
            return mapDegreeFromPentaminorToMinthird(degree);
        } else if ( nextMode.equals(Mode.THIRD) ) {
            return mapDegreeFromPentaminorToThird(degree);
        } else if ( nextMode.equals(Mode.FOURTH) ) {
            return mapDegreeFromPentaminorToFourth(degree);
        } else if ( nextMode.equals(Mode.TRITONE) ) {
            return mapDegreeFromPentaminorToTritone(degree);
        } else if ( nextMode.equals(Mode.FIFTH) ) {
            return mapDegreeFromPentaminorToFifth(degree);
        }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromMaj7thTo(int degree, Mode nextMode) throws InvalidModeChangeException {
        if ( nextMode.equals(Mode.CHROMATIC) ) {
            return mapDegreeFromMaj7thToChromatic(degree);
        } else if ( nextMode.equals(Mode.DIMINISHED) ) {
            return mapDegreeFromMaj7thToDiminished(degree);
        } else if ( nextMode.equals(Mode.IONIAN) ) {
            return mapDegreeFromMaj7thToIonian(degree);
        } else if ( nextMode.equals(Mode.DORIAN) ) {
            return mapDegreeFromMaj7thToDorian(degree);
        } else if ( nextMode.equals(Mode.PHRYGIAN) ) {
            return mapDegreeFromMaj7thToPhrygian(degree);
        } else if ( nextMode.equals(Mode.LYDIAN) ) {
            return mapDegreeFromMaj7thToLydian(degree);
        } else if ( nextMode.equals(Mode.MIXOLYDIAN) ) {
            return mapDegreeFromMaj7thToMixolydian(degree);
        } else if ( nextMode.equals(Mode.AOLEAN) ) {
            return mapDegreeFromMaj7thToAolean(degree);
        } else if ( nextMode.equals(Mode.LOCRIAN) ) {
            return mapDegreeFromMaj7thToLocrian(degree);
        } else if ( nextMode.equals(Mode.MAJOR) ) {
            return mapDegreeFromMaj7thToMajor(degree);
        } else if ( nextMode.equals(Mode.MINOR) ) {
            return mapDegreeFromMaj7thToMinor(degree);
        } else if ( nextMode.equals(Mode.WHOLETONE) ) {
            return mapDegreeFromMaj7thToWholetone(degree);
        } else if ( nextMode.equals(Mode.BLUES) ) {
            return mapDegreeFromMaj7thToBlues(degree);
        } else if ( nextMode.equals(Mode.PENTAMAJOR) ) {
            return mapDegreeFromMaj7thToPentamajor(degree);
        } else if ( nextMode.equals(Mode.PENTAMINOR) ) {
            return mapDegreeFromMaj7thToPentaminor(degree);
        } else if ( nextMode.equals(Mode.MAJ7TH) ) {
            return mapDegreeFromMaj7thToMaj7th(degree);
        } else if ( nextMode.equals(Mode.MIN7TH) ) {
            return mapDegreeFromMaj7thToMin7th(degree);
        } else if ( nextMode.equals(Mode.DIM7TH) ) {
            return mapDegreeFromMaj7thToDim7th(degree);
        } else if ( nextMode.equals(Mode.DOM7TH) ) {
            return mapDegreeFromMaj7thToDom7th(degree);
        } else if ( nextMode.equals(Mode.MAJTRIAD) ) {
            return mapDegreeFromMaj7thToMajtriad(degree);
        } else if ( nextMode.equals(Mode.MINTRIAD) ) {
            return mapDegreeFromMaj7thToMintriad(degree);
        } else if ( nextMode.equals(Mode.DIMTRIAD) ) {
            return mapDegreeFromMaj7thToDimtriad(degree);
        } else if ( nextMode.equals(Mode.AUGTRIAD) ) {
            return mapDegreeFromMaj7thToAugtriad(degree);
        } else if ( nextMode.equals(Mode.MINSECOND) ) {
            return mapDegreeFromMaj7thToMinsecond(degree);
        } else if ( nextMode.equals(Mode.SECOND) ) {
            return mapDegreeFromMaj7thToSecond(degree);
        } else if ( nextMode.equals(Mode.MINTHIRD) ) {
            return mapDegreeFromMaj7thToMinthird(degree);
        } else if ( nextMode.equals(Mode.THIRD) ) {
            return mapDegreeFromMaj7thToThird(degree);
        } else if ( nextMode.equals(Mode.FOURTH) ) {
            return mapDegreeFromMaj7thToFourth(degree);
        } else if ( nextMode.equals(Mode.TRITONE) ) {
            return mapDegreeFromMaj7thToTritone(degree);
        } else if ( nextMode.equals(Mode.FIFTH) ) {
            return mapDegreeFromMaj7thToFifth(degree);
        }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromMin7thTo(int degree, Mode nextMode) throws InvalidModeChangeException {
        if ( nextMode.equals(Mode.CHROMATIC) ) {
            return mapDegreeFromMin7thToChromatic(degree);
        } else if ( nextMode.equals(Mode.DIMINISHED) ) {
            return mapDegreeFromMin7thToDiminished(degree);
        } else if ( nextMode.equals(Mode.IONIAN) ) {
            return mapDegreeFromMin7thToIonian(degree);
        } else if ( nextMode.equals(Mode.DORIAN) ) {
            return mapDegreeFromMin7thToDorian(degree);
        } else if ( nextMode.equals(Mode.PHRYGIAN) ) {
            return mapDegreeFromMin7thToPhrygian(degree);
        } else if ( nextMode.equals(Mode.LYDIAN) ) {
            return mapDegreeFromMin7thToLydian(degree);
        } else if ( nextMode.equals(Mode.MIXOLYDIAN) ) {
            return mapDegreeFromMin7thToMixolydian(degree);
        } else if ( nextMode.equals(Mode.AOLEAN) ) {
            return mapDegreeFromMin7thToAolean(degree);
        } else if ( nextMode.equals(Mode.LOCRIAN) ) {
            return mapDegreeFromMin7thToLocrian(degree);
        } else if ( nextMode.equals(Mode.MAJOR) ) {
            return mapDegreeFromMin7thToMajor(degree);
        } else if ( nextMode.equals(Mode.MINOR) ) {
            return mapDegreeFromMin7thToMinor(degree);
        } else if ( nextMode.equals(Mode.WHOLETONE) ) {
            return mapDegreeFromMin7thToWholetone(degree);
        } else if ( nextMode.equals(Mode.BLUES) ) {
            return mapDegreeFromMin7thToBlues(degree);
        } else if ( nextMode.equals(Mode.PENTAMAJOR) ) {
            return mapDegreeFromMin7thToPentamajor(degree);
        } else if ( nextMode.equals(Mode.PENTAMINOR) ) {
            return mapDegreeFromMin7thToPentaminor(degree);
        } else if ( nextMode.equals(Mode.MAJ7TH) ) {
            return mapDegreeFromMin7thToMaj7th(degree);
        } else if ( nextMode.equals(Mode.MIN7TH) ) {
            return mapDegreeFromMin7thToMin7th(degree);
        } else if ( nextMode.equals(Mode.DIM7TH) ) {
            return mapDegreeFromMin7thToDim7th(degree);
        } else if ( nextMode.equals(Mode.DOM7TH) ) {
            return mapDegreeFromMin7thToDom7th(degree);
        } else if ( nextMode.equals(Mode.MAJTRIAD) ) {
            return mapDegreeFromMin7thToMajtriad(degree);
        } else if ( nextMode.equals(Mode.MINTRIAD) ) {
            return mapDegreeFromMin7thToMintriad(degree);
        } else if ( nextMode.equals(Mode.DIMTRIAD) ) {
            return mapDegreeFromMin7thToDimtriad(degree);
        } else if ( nextMode.equals(Mode.AUGTRIAD) ) {
            return mapDegreeFromMin7thToAugtriad(degree);
        } else if ( nextMode.equals(Mode.MINSECOND) ) {
            return mapDegreeFromMin7thToMinsecond(degree);
        } else if ( nextMode.equals(Mode.SECOND) ) {
            return mapDegreeFromMin7thToSecond(degree);
        } else if ( nextMode.equals(Mode.MINTHIRD) ) {
            return mapDegreeFromMin7thToMinthird(degree);
        } else if ( nextMode.equals(Mode.THIRD) ) {
            return mapDegreeFromMin7thToThird(degree);
        } else if ( nextMode.equals(Mode.FOURTH) ) {
            return mapDegreeFromMin7thToFourth(degree);
        } else if ( nextMode.equals(Mode.TRITONE) ) {
            return mapDegreeFromMin7thToTritone(degree);
        } else if ( nextMode.equals(Mode.FIFTH) ) {
            return mapDegreeFromMin7thToFifth(degree);
        }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromDim7thTo(int degree, Mode nextMode) throws InvalidModeChangeException {
        if ( nextMode.equals(Mode.CHROMATIC) ) {
            return mapDegreeFromDim7thToChromatic(degree);
        } else if ( nextMode.equals(Mode.DIMINISHED) ) {
            return mapDegreeFromDim7thToDiminished(degree);
        } else if ( nextMode.equals(Mode.IONIAN) ) {
            return mapDegreeFromDim7thToIonian(degree);
        } else if ( nextMode.equals(Mode.DORIAN) ) {
            return mapDegreeFromDim7thToDorian(degree);
        } else if ( nextMode.equals(Mode.PHRYGIAN) ) {
            return mapDegreeFromDim7thToPhrygian(degree);
        } else if ( nextMode.equals(Mode.LYDIAN) ) {
            return mapDegreeFromDim7thToLydian(degree);
        } else if ( nextMode.equals(Mode.MIXOLYDIAN) ) {
            return mapDegreeFromDim7thToMixolydian(degree);
        } else if ( nextMode.equals(Mode.AOLEAN) ) {
            return mapDegreeFromDim7thToAolean(degree);
        } else if ( nextMode.equals(Mode.LOCRIAN) ) {
            return mapDegreeFromDim7thToLocrian(degree);
        } else if ( nextMode.equals(Mode.MAJOR) ) {
            return mapDegreeFromDim7thToMajor(degree);
        } else if ( nextMode.equals(Mode.MINOR) ) {
            return mapDegreeFromDim7thToMinor(degree);
        } else if ( nextMode.equals(Mode.WHOLETONE) ) {
            return mapDegreeFromDim7thToWholetone(degree);
        } else if ( nextMode.equals(Mode.BLUES) ) {
            return mapDegreeFromDim7thToBlues(degree);
        } else if ( nextMode.equals(Mode.PENTAMAJOR) ) {
            return mapDegreeFromDim7thToPentamajor(degree);
        } else if ( nextMode.equals(Mode.PENTAMINOR) ) {
            return mapDegreeFromDim7thToPentaminor(degree);
        } else if ( nextMode.equals(Mode.MAJ7TH) ) {
            return mapDegreeFromDim7thToMaj7th(degree);
        } else if ( nextMode.equals(Mode.MIN7TH) ) {
            return mapDegreeFromDim7thToMin7th(degree);
        } else if ( nextMode.equals(Mode.DIM7TH) ) {
            return mapDegreeFromDim7thToDim7th(degree);
        } else if ( nextMode.equals(Mode.DOM7TH) ) {
            return mapDegreeFromDim7thToDom7th(degree);
        } else if ( nextMode.equals(Mode.MAJTRIAD) ) {
            return mapDegreeFromDim7thToMajtriad(degree);
        } else if ( nextMode.equals(Mode.MINTRIAD) ) {
            return mapDegreeFromDim7thToMintriad(degree);
        } else if ( nextMode.equals(Mode.DIMTRIAD) ) {
            return mapDegreeFromDim7thToDimtriad(degree);
        } else if ( nextMode.equals(Mode.AUGTRIAD) ) {
            return mapDegreeFromDim7thToAugtriad(degree);
        } else if ( nextMode.equals(Mode.MINSECOND) ) {
            return mapDegreeFromDim7thToMinsecond(degree);
        } else if ( nextMode.equals(Mode.SECOND) ) {
            return mapDegreeFromDim7thToSecond(degree);
        } else if ( nextMode.equals(Mode.MINTHIRD) ) {
            return mapDegreeFromDim7thToMinthird(degree);
        } else if ( nextMode.equals(Mode.THIRD) ) {
            return mapDegreeFromDim7thToThird(degree);
        } else if ( nextMode.equals(Mode.FOURTH) ) {
            return mapDegreeFromDim7thToFourth(degree);
        } else if ( nextMode.equals(Mode.TRITONE) ) {
            return mapDegreeFromDim7thToTritone(degree);
        } else if ( nextMode.equals(Mode.FIFTH) ) {
            return mapDegreeFromDim7thToFifth(degree);
        }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromDom7thTo(int degree, Mode nextMode) throws InvalidModeChangeException {
        if ( nextMode.equals(Mode.CHROMATIC) ) {
            return mapDegreeFromDom7thToChromatic(degree);
        } else if ( nextMode.equals(Mode.DIMINISHED) ) {
            return mapDegreeFromDom7thToDiminished(degree);
        } else if ( nextMode.equals(Mode.IONIAN) ) {
            return mapDegreeFromDom7thToIonian(degree);
        } else if ( nextMode.equals(Mode.DORIAN) ) {
            return mapDegreeFromDom7thToDorian(degree);
        } else if ( nextMode.equals(Mode.PHRYGIAN) ) {
            return mapDegreeFromDom7thToPhrygian(degree);
        } else if ( nextMode.equals(Mode.LYDIAN) ) {
            return mapDegreeFromDom7thToLydian(degree);
        } else if ( nextMode.equals(Mode.MIXOLYDIAN) ) {
            return mapDegreeFromDom7thToMixolydian(degree);
        } else if ( nextMode.equals(Mode.AOLEAN) ) {
            return mapDegreeFromDom7thToAolean(degree);
        } else if ( nextMode.equals(Mode.LOCRIAN) ) {
            return mapDegreeFromDom7thToLocrian(degree);
        } else if ( nextMode.equals(Mode.MAJOR) ) {
            return mapDegreeFromDom7thToMajor(degree);
        } else if ( nextMode.equals(Mode.MINOR) ) {
            return mapDegreeFromDom7thToMinor(degree);
        } else if ( nextMode.equals(Mode.WHOLETONE) ) {
            return mapDegreeFromDom7thToWholetone(degree);
        } else if ( nextMode.equals(Mode.BLUES) ) {
            return mapDegreeFromDom7thToBlues(degree);
        } else if ( nextMode.equals(Mode.PENTAMAJOR) ) {
            return mapDegreeFromDom7thToPentamajor(degree);
        } else if ( nextMode.equals(Mode.PENTAMINOR) ) {
            return mapDegreeFromDom7thToPentaminor(degree);
        } else if ( nextMode.equals(Mode.MAJ7TH) ) {
            return mapDegreeFromDom7thToMaj7th(degree);
        } else if ( nextMode.equals(Mode.MIN7TH) ) {
            return mapDegreeFromDom7thToMin7th(degree);
        } else if ( nextMode.equals(Mode.DIM7TH) ) {
            return mapDegreeFromDom7thToDim7th(degree);
        } else if ( nextMode.equals(Mode.DOM7TH) ) {
            return mapDegreeFromDom7thToDom7th(degree);
        } else if ( nextMode.equals(Mode.MAJTRIAD) ) {
            return mapDegreeFromDom7thToMajtriad(degree);
        } else if ( nextMode.equals(Mode.MINTRIAD) ) {
            return mapDegreeFromDom7thToMintriad(degree);
        } else if ( nextMode.equals(Mode.DIMTRIAD) ) {
            return mapDegreeFromDom7thToDimtriad(degree);
        } else if ( nextMode.equals(Mode.AUGTRIAD) ) {
            return mapDegreeFromDom7thToAugtriad(degree);
        } else if ( nextMode.equals(Mode.MINSECOND) ) {
            return mapDegreeFromDom7thToMinsecond(degree);
        } else if ( nextMode.equals(Mode.SECOND) ) {
            return mapDegreeFromDom7thToSecond(degree);
        } else if ( nextMode.equals(Mode.MINTHIRD) ) {
            return mapDegreeFromDom7thToMinthird(degree);
        } else if ( nextMode.equals(Mode.THIRD) ) {
            return mapDegreeFromDom7thToThird(degree);
        } else if ( nextMode.equals(Mode.FOURTH) ) {
            return mapDegreeFromDom7thToFourth(degree);
        } else if ( nextMode.equals(Mode.TRITONE) ) {
            return mapDegreeFromDom7thToTritone(degree);
        } else if ( nextMode.equals(Mode.FIFTH) ) {
            return mapDegreeFromDom7thToFifth(degree);
        }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromMajtriadTo(int degree, Mode nextMode) throws InvalidModeChangeException {
        if ( nextMode.equals(Mode.CHROMATIC) ) {
            return mapDegreeFromMajtriadToChromatic(degree);
        } else if ( nextMode.equals(Mode.DIMINISHED) ) {
            return mapDegreeFromMajtriadToDiminished(degree);
        } else if ( nextMode.equals(Mode.IONIAN) ) {
            return mapDegreeFromMajtriadToIonian(degree);
        } else if ( nextMode.equals(Mode.DORIAN) ) {
            return mapDegreeFromMajtriadToDorian(degree);
        } else if ( nextMode.equals(Mode.PHRYGIAN) ) {
            return mapDegreeFromMajtriadToPhrygian(degree);
        } else if ( nextMode.equals(Mode.LYDIAN) ) {
            return mapDegreeFromMajtriadToLydian(degree);
        } else if ( nextMode.equals(Mode.MIXOLYDIAN) ) {
            return mapDegreeFromMajtriadToMixolydian(degree);
        } else if ( nextMode.equals(Mode.AOLEAN) ) {
            return mapDegreeFromMajtriadToAolean(degree);
        } else if ( nextMode.equals(Mode.LOCRIAN) ) {
            return mapDegreeFromMajtriadToLocrian(degree);
        } else if ( nextMode.equals(Mode.MAJOR) ) {
            return mapDegreeFromMajtriadToMajor(degree);
        } else if ( nextMode.equals(Mode.MINOR) ) {
            return mapDegreeFromMajtriadToMinor(degree);
        } else if ( nextMode.equals(Mode.WHOLETONE) ) {
            return mapDegreeFromMajtriadToWholetone(degree);
        } else if ( nextMode.equals(Mode.BLUES) ) {
            return mapDegreeFromMajtriadToBlues(degree);
        } else if ( nextMode.equals(Mode.PENTAMAJOR) ) {
            return mapDegreeFromMajtriadToPentamajor(degree);
        } else if ( nextMode.equals(Mode.PENTAMINOR) ) {
            return mapDegreeFromMajtriadToPentaminor(degree);
        } else if ( nextMode.equals(Mode.MAJ7TH) ) {
            return mapDegreeFromMajtriadToMaj7th(degree);
        } else if ( nextMode.equals(Mode.MIN7TH) ) {
            return mapDegreeFromMajtriadToMin7th(degree);
        } else if ( nextMode.equals(Mode.DIM7TH) ) {
            return mapDegreeFromMajtriadToDim7th(degree);
        } else if ( nextMode.equals(Mode.DOM7TH) ) {
            return mapDegreeFromMajtriadToDom7th(degree);
        } else if ( nextMode.equals(Mode.MAJTRIAD) ) {
            return mapDegreeFromMajtriadToMajtriad(degree);
        } else if ( nextMode.equals(Mode.MINTRIAD) ) {
            return mapDegreeFromMajtriadToMintriad(degree);
        } else if ( nextMode.equals(Mode.DIMTRIAD) ) {
            return mapDegreeFromMajtriadToDimtriad(degree);
        } else if ( nextMode.equals(Mode.AUGTRIAD) ) {
            return mapDegreeFromMajtriadToAugtriad(degree);
        } else if ( nextMode.equals(Mode.MINSECOND) ) {
            return mapDegreeFromMajtriadToMinsecond(degree);
        } else if ( nextMode.equals(Mode.SECOND) ) {
            return mapDegreeFromMajtriadToSecond(degree);
        } else if ( nextMode.equals(Mode.MINTHIRD) ) {
            return mapDegreeFromMajtriadToMinthird(degree);
        } else if ( nextMode.equals(Mode.THIRD) ) {
            return mapDegreeFromMajtriadToThird(degree);
        } else if ( nextMode.equals(Mode.FOURTH) ) {
            return mapDegreeFromMajtriadToFourth(degree);
        } else if ( nextMode.equals(Mode.TRITONE) ) {
            return mapDegreeFromMajtriadToTritone(degree);
        } else if ( nextMode.equals(Mode.FIFTH) ) {
            return mapDegreeFromMajtriadToFifth(degree);
        }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromMintriadTo(int degree, Mode nextMode) throws InvalidModeChangeException {
        if ( nextMode.equals(Mode.CHROMATIC) ) {
            return mapDegreeFromMintriadToChromatic(degree);
        } else if ( nextMode.equals(Mode.DIMINISHED) ) {
            return mapDegreeFromMintriadToDiminished(degree);
        } else if ( nextMode.equals(Mode.IONIAN) ) {
            return mapDegreeFromMintriadToIonian(degree);
        } else if ( nextMode.equals(Mode.DORIAN) ) {
            return mapDegreeFromMintriadToDorian(degree);
        } else if ( nextMode.equals(Mode.PHRYGIAN) ) {
            return mapDegreeFromMintriadToPhrygian(degree);
        } else if ( nextMode.equals(Mode.LYDIAN) ) {
            return mapDegreeFromMintriadToLydian(degree);
        } else if ( nextMode.equals(Mode.MIXOLYDIAN) ) {
            return mapDegreeFromMintriadToMixolydian(degree);
        } else if ( nextMode.equals(Mode.AOLEAN) ) {
            return mapDegreeFromMintriadToAolean(degree);
        } else if ( nextMode.equals(Mode.LOCRIAN) ) {
            return mapDegreeFromMintriadToLocrian(degree);
        } else if ( nextMode.equals(Mode.MAJOR) ) {
            return mapDegreeFromMintriadToMajor(degree);
        } else if ( nextMode.equals(Mode.MINOR) ) {
            return mapDegreeFromMintriadToMinor(degree);
        } else if ( nextMode.equals(Mode.WHOLETONE) ) {
            return mapDegreeFromMintriadToWholetone(degree);
        } else if ( nextMode.equals(Mode.BLUES) ) {
            return mapDegreeFromMintriadToBlues(degree);
        } else if ( nextMode.equals(Mode.PENTAMAJOR) ) {
            return mapDegreeFromMintriadToPentamajor(degree);
        } else if ( nextMode.equals(Mode.PENTAMINOR) ) {
            return mapDegreeFromMintriadToPentaminor(degree);
        } else if ( nextMode.equals(Mode.MAJ7TH) ) {
            return mapDegreeFromMintriadToMaj7th(degree);
        } else if ( nextMode.equals(Mode.MIN7TH) ) {
            return mapDegreeFromMintriadToMin7th(degree);
        } else if ( nextMode.equals(Mode.DIM7TH) ) {
            return mapDegreeFromMintriadToDim7th(degree);
        } else if ( nextMode.equals(Mode.DOM7TH) ) {
            return mapDegreeFromMintriadToDom7th(degree);
        } else if ( nextMode.equals(Mode.MAJTRIAD) ) {
            return mapDegreeFromMintriadToMajtriad(degree);
        } else if ( nextMode.equals(Mode.MINTRIAD) ) {
            return mapDegreeFromMintriadToMintriad(degree);
        } else if ( nextMode.equals(Mode.DIMTRIAD) ) {
            return mapDegreeFromMintriadToDimtriad(degree);
        } else if ( nextMode.equals(Mode.AUGTRIAD) ) {
            return mapDegreeFromMintriadToAugtriad(degree);
        } else if ( nextMode.equals(Mode.MINSECOND) ) {
            return mapDegreeFromMintriadToMinsecond(degree);
        } else if ( nextMode.equals(Mode.SECOND) ) {
            return mapDegreeFromMintriadToSecond(degree);
        } else if ( nextMode.equals(Mode.MINTHIRD) ) {
            return mapDegreeFromMintriadToMinthird(degree);
        } else if ( nextMode.equals(Mode.THIRD) ) {
            return mapDegreeFromMintriadToThird(degree);
        } else if ( nextMode.equals(Mode.FOURTH) ) {
            return mapDegreeFromMintriadToFourth(degree);
        } else if ( nextMode.equals(Mode.TRITONE) ) {
            return mapDegreeFromMintriadToTritone(degree);
        } else if ( nextMode.equals(Mode.FIFTH) ) {
            return mapDegreeFromMintriadToFifth(degree);
        }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromDimtriadTo(int degree, Mode nextMode) throws InvalidModeChangeException {
        if ( nextMode.equals(Mode.CHROMATIC) ) {
            return mapDegreeFromDimtriadToChromatic(degree);
        } else if ( nextMode.equals(Mode.DIMINISHED) ) {
            return mapDegreeFromDimtriadToDiminished(degree);
        } else if ( nextMode.equals(Mode.IONIAN) ) {
            return mapDegreeFromDimtriadToIonian(degree);
        } else if ( nextMode.equals(Mode.DORIAN) ) {
            return mapDegreeFromDimtriadToDorian(degree);
        } else if ( nextMode.equals(Mode.PHRYGIAN) ) {
            return mapDegreeFromDimtriadToPhrygian(degree);
        } else if ( nextMode.equals(Mode.LYDIAN) ) {
            return mapDegreeFromDimtriadToLydian(degree);
        } else if ( nextMode.equals(Mode.MIXOLYDIAN) ) {
            return mapDegreeFromDimtriadToMixolydian(degree);
        } else if ( nextMode.equals(Mode.AOLEAN) ) {
            return mapDegreeFromDimtriadToAolean(degree);
        } else if ( nextMode.equals(Mode.LOCRIAN) ) {
            return mapDegreeFromDimtriadToLocrian(degree);
        } else if ( nextMode.equals(Mode.MAJOR) ) {
            return mapDegreeFromDimtriadToMajor(degree);
        } else if ( nextMode.equals(Mode.MINOR) ) {
            return mapDegreeFromDimtriadToMinor(degree);
        } else if ( nextMode.equals(Mode.WHOLETONE) ) {
            return mapDegreeFromDimtriadToWholetone(degree);
        } else if ( nextMode.equals(Mode.BLUES) ) {
            return mapDegreeFromDimtriadToBlues(degree);
        } else if ( nextMode.equals(Mode.PENTAMAJOR) ) {
            return mapDegreeFromDimtriadToPentamajor(degree);
        } else if ( nextMode.equals(Mode.PENTAMINOR) ) {
            return mapDegreeFromDimtriadToPentaminor(degree);
        } else if ( nextMode.equals(Mode.MAJ7TH) ) {
            return mapDegreeFromDimtriadToMaj7th(degree);
        } else if ( nextMode.equals(Mode.MIN7TH) ) {
            return mapDegreeFromDimtriadToMin7th(degree);
        } else if ( nextMode.equals(Mode.DIM7TH) ) {
            return mapDegreeFromDimtriadToDim7th(degree);
        } else if ( nextMode.equals(Mode.DOM7TH) ) {
            return mapDegreeFromDimtriadToDom7th(degree);
        } else if ( nextMode.equals(Mode.MAJTRIAD) ) {
            return mapDegreeFromDimtriadToMajtriad(degree);
        } else if ( nextMode.equals(Mode.MINTRIAD) ) {
            return mapDegreeFromDimtriadToMintriad(degree);
        } else if ( nextMode.equals(Mode.DIMTRIAD) ) {
            return mapDegreeFromDimtriadToDimtriad(degree);
        } else if ( nextMode.equals(Mode.AUGTRIAD) ) {
            return mapDegreeFromDimtriadToAugtriad(degree);
        } else if ( nextMode.equals(Mode.MINSECOND) ) {
            return mapDegreeFromDimtriadToMinsecond(degree);
        } else if ( nextMode.equals(Mode.SECOND) ) {
            return mapDegreeFromDimtriadToSecond(degree);
        } else if ( nextMode.equals(Mode.MINTHIRD) ) {
            return mapDegreeFromDimtriadToMinthird(degree);
        } else if ( nextMode.equals(Mode.THIRD) ) {
            return mapDegreeFromDimtriadToThird(degree);
        } else if ( nextMode.equals(Mode.FOURTH) ) {
            return mapDegreeFromDimtriadToFourth(degree);
        } else if ( nextMode.equals(Mode.TRITONE) ) {
            return mapDegreeFromDimtriadToTritone(degree);
        } else if ( nextMode.equals(Mode.FIFTH) ) {
            return mapDegreeFromDimtriadToFifth(degree);
        }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromAugtriadTo(int degree, Mode nextMode) throws InvalidModeChangeException {
        if ( nextMode.equals(Mode.CHROMATIC) ) {
            return mapDegreeFromAugtriadToChromatic(degree);
        } else if ( nextMode.equals(Mode.DIMINISHED) ) {
            return mapDegreeFromAugtriadToDiminished(degree);
        } else if ( nextMode.equals(Mode.IONIAN) ) {
            return mapDegreeFromAugtriadToIonian(degree);
        } else if ( nextMode.equals(Mode.DORIAN) ) {
            return mapDegreeFromAugtriadToDorian(degree);
        } else if ( nextMode.equals(Mode.PHRYGIAN) ) {
            return mapDegreeFromAugtriadToPhrygian(degree);
        } else if ( nextMode.equals(Mode.LYDIAN) ) {
            return mapDegreeFromAugtriadToLydian(degree);
        } else if ( nextMode.equals(Mode.MIXOLYDIAN) ) {
            return mapDegreeFromAugtriadToMixolydian(degree);
        } else if ( nextMode.equals(Mode.AOLEAN) ) {
            return mapDegreeFromAugtriadToAolean(degree);
        } else if ( nextMode.equals(Mode.LOCRIAN) ) {
            return mapDegreeFromAugtriadToLocrian(degree);
        } else if ( nextMode.equals(Mode.MAJOR) ) {
            return mapDegreeFromAugtriadToMajor(degree);
        } else if ( nextMode.equals(Mode.MINOR) ) {
            return mapDegreeFromAugtriadToMinor(degree);
        } else if ( nextMode.equals(Mode.WHOLETONE) ) {
            return mapDegreeFromAugtriadToWholetone(degree);
        } else if ( nextMode.equals(Mode.BLUES) ) {
            return mapDegreeFromAugtriadToBlues(degree);
        } else if ( nextMode.equals(Mode.PENTAMAJOR) ) {
            return mapDegreeFromAugtriadToPentamajor(degree);
        } else if ( nextMode.equals(Mode.PENTAMINOR) ) {
            return mapDegreeFromAugtriadToPentaminor(degree);
        } else if ( nextMode.equals(Mode.MAJ7TH) ) {
            return mapDegreeFromAugtriadToMaj7th(degree);
        } else if ( nextMode.equals(Mode.MIN7TH) ) {
            return mapDegreeFromAugtriadToMin7th(degree);
        } else if ( nextMode.equals(Mode.DIM7TH) ) {
            return mapDegreeFromAugtriadToDim7th(degree);
        } else if ( nextMode.equals(Mode.DOM7TH) ) {
            return mapDegreeFromAugtriadToDom7th(degree);
        } else if ( nextMode.equals(Mode.MAJTRIAD) ) {
            return mapDegreeFromAugtriadToMajtriad(degree);
        } else if ( nextMode.equals(Mode.MINTRIAD) ) {
            return mapDegreeFromAugtriadToMintriad(degree);
        } else if ( nextMode.equals(Mode.DIMTRIAD) ) {
            return mapDegreeFromAugtriadToDimtriad(degree);
        } else if ( nextMode.equals(Mode.AUGTRIAD) ) {
            return mapDegreeFromAugtriadToAugtriad(degree);
        } else if ( nextMode.equals(Mode.MINSECOND) ) {
            return mapDegreeFromAugtriadToMinsecond(degree);
        } else if ( nextMode.equals(Mode.SECOND) ) {
            return mapDegreeFromAugtriadToSecond(degree);
        } else if ( nextMode.equals(Mode.MINTHIRD) ) {
            return mapDegreeFromAugtriadToMinthird(degree);
        } else if ( nextMode.equals(Mode.THIRD) ) {
            return mapDegreeFromAugtriadToThird(degree);
        } else if ( nextMode.equals(Mode.FOURTH) ) {
            return mapDegreeFromAugtriadToFourth(degree);
        } else if ( nextMode.equals(Mode.TRITONE) ) {
            return mapDegreeFromAugtriadToTritone(degree);
        } else if ( nextMode.equals(Mode.FIFTH) ) {
            return mapDegreeFromAugtriadToFifth(degree);
        }
        throw new InvalidModeChangeException();
    }

    // MORE WORK TO DO

    private int mapDegreeFromMinsecondTo(int degree, Mode nextMode) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private int mapDegreeFromSecondTo(int degree, Mode nextMode) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private int mapDegreeFromMinthirdTo(int degree, Mode nextMode) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private int mapDegreeFromThirdTo(int degree, Mode nextMode) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private int mapDegreeFromFourthTo(int degree, Mode nextMode) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private int mapDegreeFromTritoneTo(int degree, Mode nextMode) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private int mapDegreeFromFifthTo(int degree, Mode nextMode) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // CHROMATIC --> ALL THE REST

    private int mapDegreeFromChromaticToChromatic(int degree) {
        return degree;
    }

    private int mapDegreeFromChromaticToDiminished(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { throw new InvalidModeChangeException(); }
        if ( degree == 3 ) { return 2; }
        if ( degree == 4 ) { return 3; }
        if ( degree == 5 ) { throw new InvalidModeChangeException(); }
        if ( degree == 6 ) { return 4; }
        if ( degree == 7 ) { return 5; }
        if ( degree == 8 ) { throw new InvalidModeChangeException(); }
        if ( degree == 9 ) { return 6; }
        if ( degree == 10 ) { return 7; }
        if ( degree == 11 ) { throw new InvalidModeChangeException(); }
        if ( degree == 12 ) { return 8; }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromChromaticToMaj7th(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { throw new InvalidModeChangeException(); }
        if ( degree == 3 ) { throw new InvalidModeChangeException(); }
        if ( degree == 4 ) { throw new InvalidModeChangeException(); }
        if ( degree == 5 ) { return 2; }
        if ( degree == 6 ) { throw new InvalidModeChangeException();  }
        if ( degree == 7 ) { throw new InvalidModeChangeException(); }
        if ( degree == 8 ) { return 3; }
        if ( degree == 9 ) { throw new InvalidModeChangeException(); }
        if ( degree == 10 ) { throw new InvalidModeChangeException();  }
        if ( degree == 11 ) { return 4; }
        if ( degree == 12 ) { throw new InvalidModeChangeException();  }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromChromaticToMajtriad(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { throw new InvalidModeChangeException(); }
        if ( degree == 3 ) { throw new InvalidModeChangeException(); }
        if ( degree == 4 ) { throw new InvalidModeChangeException(); }
        if ( degree == 5 ) { return 2; }
        if ( degree == 6 ) { throw new InvalidModeChangeException();  }
        if ( degree == 7 ) { throw new InvalidModeChangeException(); }
        if ( degree == 8 ) { return 3; }
        if ( degree == 9 ) { throw new InvalidModeChangeException(); }
        if ( degree == 10 ) { throw new InvalidModeChangeException();  }
        if ( degree == 11 ) { throw new InvalidModeChangeException(); }
        if ( degree == 12 ) { throw new InvalidModeChangeException();  }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromChromaticToIonian(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { throw new InvalidModeChangeException(); }
        if ( degree == 3 ) { return 2; }
        if ( degree == 4 ) { throw new InvalidModeChangeException(); }
        if ( degree == 5 ) { return 3; }
        if ( degree == 6 ) { return 4; }
        if ( degree == 7 ) { throw new InvalidModeChangeException(); }
        if ( degree == 8 ) { return 5; }
        if ( degree == 9 ) { throw new InvalidModeChangeException(); }
        if ( degree == 10 ) { return 6; }
        if ( degree == 11 ) { throw new InvalidModeChangeException(); }
        if ( degree == 12 ) { return 7; }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromChromaticToDorian(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { throw new InvalidModeChangeException(); }
        if ( degree == 3 ) { return 2; }
        if ( degree == 4 ) { return 3; }
        if ( degree == 5 ) { throw new InvalidModeChangeException(); }
        if ( degree == 6 ) { return 4; }
        if ( degree == 7 ) { throw new InvalidModeChangeException(); }
        if ( degree == 8 ) { return 5; }
        if ( degree == 9 ) { throw new InvalidModeChangeException(); }
        if ( degree == 10 ) { return 6; }
        if ( degree == 11 ) { return 7; }
        if ( degree == 12 ) { throw new InvalidModeChangeException(); }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromChromaticToPhrygian(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { return 2; }
        if ( degree == 3 ) { throw new InvalidModeChangeException(); }
        if ( degree == 4 ) { return 3; }
        if ( degree == 5 ) { throw new InvalidModeChangeException(); }
        if ( degree == 6 ) { return 4; }
        if ( degree == 7 ) { throw new InvalidModeChangeException(); }
        if ( degree == 8 ) { return 5; }
        if ( degree == 9 ) { return 6; }
        if ( degree == 10 ) { throw new InvalidModeChangeException(); }
        if ( degree == 11 ) { throw new InvalidModeChangeException(); }
        if ( degree == 12 ) { return 7; }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromChromaticToLydian(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { throw new InvalidModeChangeException(); }
        if ( degree == 3 ) { return 2; }
        if ( degree == 4 ) { throw new InvalidModeChangeException(); }
        if ( degree == 5 ) { return 3; }
        if ( degree == 6 ) { return 4; }
        if ( degree == 7 ) { throw new InvalidModeChangeException(); }
        if ( degree == 8 ) { return 5; }
        if ( degree == 9 ) { throw new InvalidModeChangeException(); }
        if ( degree == 10 ) { return 6; }
        if ( degree == 11 ) { return 7; }
        if ( degree == 12 ) { throw new InvalidModeChangeException(); }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromChromaticToMixolydian(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { throw new InvalidModeChangeException(); }
        if ( degree == 3 ) { return 2; }
        if ( degree == 4 ) { throw new InvalidModeChangeException(); }
        if ( degree == 5 ) { return 3; }
        if ( degree == 6 ) { return 4; }
        if ( degree == 7 ) { throw new InvalidModeChangeException(); }
        if ( degree == 8 ) { return 5; }
        if ( degree == 9 ) { throw new InvalidModeChangeException(); }
        if ( degree == 10 ) { return 6; }
        if ( degree == 11 ) { return 7; }
        if ( degree == 12 ) { throw new InvalidModeChangeException(); }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromChromaticToAolean(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { throw new InvalidModeChangeException(); }
        if ( degree == 3 ) { return 2; }
        if ( degree == 4 ) { throw new InvalidModeChangeException(); }
        if ( degree == 5 ) { return 3; }
        if ( degree == 6 ) { return 4; }
        if ( degree == 7 ) { throw new InvalidModeChangeException(); }
        if ( degree == 8 ) { return 5; }
        if ( degree == 9 ) { throw new InvalidModeChangeException(); }
        if ( degree == 10 ) { return 6; }
        if ( degree == 11 ) { throw new InvalidModeChangeException(); }
        if ( degree == 12 ) { return 7; }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromChromaticToLocrian(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { return 2; }
        if ( degree == 3 ) { throw new InvalidModeChangeException(); }
        if ( degree == 4 ) {return 3; }
        if ( degree == 5 ) { throw new InvalidModeChangeException(); }
        if ( degree == 6 ) { return 4; }
        if ( degree == 7 ) { return 5; }
        if ( degree == 8 ) { throw new InvalidModeChangeException(); }
        if ( degree == 9 ) { return 6; }
        if ( degree == 10 ) { throw new InvalidModeChangeException(); }
        if ( degree == 11 ) { return 7; }
        if ( degree == 12 ) { throw new InvalidModeChangeException(); }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromChromaticToMajor(int degree) throws InvalidModeChangeException {
        // mcf.ta().append("got to mapDegreeFromChromaticToMajor\n");
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { throw new InvalidModeChangeException(); }
        if ( degree == 3 ) { return 2; }
        if ( degree == 4 ) { throw new InvalidModeChangeException(); }
        if ( degree == 5 ) { return 3; }
        if ( degree == 6 ) { return 4; }
        if ( degree == 7 ) { throw new InvalidModeChangeException(); }
        if ( degree == 8 ) { return 5; }
        if ( degree == 9 ) { throw new InvalidModeChangeException(); }
        if ( degree == 10 ) { return 6; }
        if ( degree == 11 ) { throw new InvalidModeChangeException(); }
        if ( degree == 12 ) { return 7; }
        // mcf.ta().append("error in mapDegreeFromChromaticToMajor\n");
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromChromaticToMinor(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { throw new InvalidModeChangeException(); }
        if ( degree == 3 ) { return 2; }
        if ( degree == 4 ) { return 3; }
        if ( degree == 5 ) { throw new InvalidModeChangeException(); }
        if ( degree == 6 ) { return 4; }
        if ( degree == 7 ) { throw new InvalidModeChangeException(); }
        if ( degree == 8 ) { return 5; }
        if ( degree == 9 ) { return 6; }
        if ( degree == 10 ) { throw new InvalidModeChangeException(); }
        if ( degree == 11 ) { return 7; }
        if ( degree == 12 ) { throw new InvalidModeChangeException(); }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromChromaticToWholetone(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { throw new InvalidModeChangeException(); }
        if ( degree == 3 ) { return 2; }
        if ( degree == 4 ) { throw new InvalidModeChangeException(); }
        if ( degree == 5 ) { return 3; }
        if ( degree == 6 ) { throw new InvalidModeChangeException(); }
        if ( degree == 7 ) { return 4; }
        if ( degree == 8 ) { throw new InvalidModeChangeException(); }
        if ( degree == 9 ) { return 5; }
        if ( degree == 10 ) { throw new InvalidModeChangeException(); }
        if ( degree == 11 ) { return 6; }
        if ( degree == 12 ) { throw new InvalidModeChangeException(); }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromChromaticToBlues(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { throw new InvalidModeChangeException(); }
        if ( degree == 3 ) { throw new InvalidModeChangeException(); }
        if ( degree == 4 ) { return 2; }
        if ( degree == 5 ) { throw new InvalidModeChangeException(); }
        if ( degree == 6 ) { return 3; }
        if ( degree == 7 ) { return 4; }
        if ( degree == 8 ) { return 5; }
        if ( degree == 9 ) { throw new InvalidModeChangeException(); }
        if ( degree == 10 ) { throw new InvalidModeChangeException(); }
        if ( degree == 11 ) { return 6; }
        if ( degree == 12 ) { throw new InvalidModeChangeException(); }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromChromaticToPentamajor(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { throw new InvalidModeChangeException(); }
        if ( degree == 3 ) { return 2; }
        if ( degree == 4 ) { throw new InvalidModeChangeException(); }
        if ( degree == 5 ) { return 3; }
        if ( degree == 6 ) { throw new InvalidModeChangeException(); }
        if ( degree == 7 ) { throw new InvalidModeChangeException(); }
        if ( degree == 8 ) { return 4; }
        if ( degree == 9 ) { throw new InvalidModeChangeException(); }
        if ( degree == 10 ) { return 5; }
        if ( degree == 11 ) { throw new InvalidModeChangeException(); }
        if ( degree == 12 ) { throw new InvalidModeChangeException(); }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromChromaticToPentaminor(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { throw new InvalidModeChangeException(); }
        if ( degree == 3 ) { throw new InvalidModeChangeException(); }
        if ( degree == 4 ) { return 2; }
        if ( degree == 5 ) { throw new InvalidModeChangeException(); }
        if ( degree == 6 ) { return 3; }
        if ( degree == 7 ) { throw new InvalidModeChangeException(); }
        if ( degree == 8 ) { throw new InvalidModeChangeException(); }
        if ( degree == 9 ) { return 4; }
        if ( degree == 10 ) { throw new InvalidModeChangeException(); }
        if ( degree == 11 ) { return 5; }
        if ( degree == 12 ) { throw new InvalidModeChangeException(); }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromChromaticToMin7th(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { throw new InvalidModeChangeException(); }
        if ( degree == 3 ) { throw new InvalidModeChangeException(); }
        if ( degree == 4 ) { return 2;  }
        if ( degree == 5 ) { throw new InvalidModeChangeException(); }
        if ( degree == 6 ) { throw new InvalidModeChangeException(); }
        if ( degree == 7 ) { throw new InvalidModeChangeException(); }
        if ( degree == 8 ) { return 3; }
        if ( degree == 9 ) { throw new InvalidModeChangeException(); }
        if ( degree == 10 ) { throw new InvalidModeChangeException(); }
        if ( degree == 11 ) { return 4;  }
        if ( degree == 12 ) { throw new InvalidModeChangeException(); }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromChromaticToDim7th(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { throw new InvalidModeChangeException(); }
        if ( degree == 3 ) { throw new InvalidModeChangeException(); }
        if ( degree == 4 ) { return 2; }
        if ( degree == 5 ) { throw new InvalidModeChangeException(); }
        if ( degree == 6 ) { throw new InvalidModeChangeException(); }
        if ( degree == 7 ) { return 3; }
        if ( degree == 8 ) { throw new InvalidModeChangeException(); }
        if ( degree == 9 ) { throw new InvalidModeChangeException(); }
        if ( degree == 10 ) { return 4; }
        if ( degree == 11 ) { throw new InvalidModeChangeException(); }
        if ( degree == 12 ) { throw new InvalidModeChangeException(); }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromChromaticToDom7th(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { throw new InvalidModeChangeException(); }
        if ( degree == 3 ) { throw new InvalidModeChangeException(); }
        if ( degree == 4 ) { throw new InvalidModeChangeException(); }
        if ( degree == 5 ) { return 2; }
        if ( degree == 6 ) { throw new InvalidModeChangeException(); }
        if ( degree == 7 ) { throw new InvalidModeChangeException(); }
        if ( degree == 8 ) { return 3; }
        if ( degree == 9 ) { throw new InvalidModeChangeException(); }
        if ( degree == 10 ) { throw new InvalidModeChangeException(); }
        if ( degree == 11 ) { return 4; }
        if ( degree == 12 ) { throw new InvalidModeChangeException(); }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromChromaticToMintriad(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { throw new InvalidModeChangeException(); }
        if ( degree == 3 ) { throw new InvalidModeChangeException(); }
        if ( degree == 4 ) { return 2; }
        if ( degree == 5 ) { throw new InvalidModeChangeException(); }
        if ( degree == 6 ) { throw new InvalidModeChangeException(); }
        if ( degree == 7 ) { throw new InvalidModeChangeException(); }
        if ( degree == 8 ) { return 3; }
        if ( degree == 9 ) { throw new InvalidModeChangeException(); }
        if ( degree == 10 ) { throw new InvalidModeChangeException(); }
        if ( degree == 11 ) { throw new InvalidModeChangeException(); }
        if ( degree == 12 ) { throw new InvalidModeChangeException(); }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromChromaticToDimtriad(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { throw new InvalidModeChangeException(); }
        if ( degree == 3 ) { throw new InvalidModeChangeException(); }
        if ( degree == 4 ) { return 2; }
        if ( degree == 5 ) { throw new InvalidModeChangeException(); }
        if ( degree == 6 ) { throw new InvalidModeChangeException(); }
        if ( degree == 7 ) { return 3; }
        if ( degree == 8 ) { throw new InvalidModeChangeException(); }
        if ( degree == 9 ) { throw new InvalidModeChangeException(); }
        if ( degree == 10 ) { throw new InvalidModeChangeException(); }
        if ( degree == 11 ) { throw new InvalidModeChangeException(); }
        if ( degree == 12 ) { throw new InvalidModeChangeException(); }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromChromaticToAugtriad(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { throw new InvalidModeChangeException(); }
        if ( degree == 3 ) { throw new InvalidModeChangeException(); }
        if ( degree == 4 ) { throw new InvalidModeChangeException(); }
        if ( degree == 5 ) { return 2; }
        if ( degree == 6 ) { throw new InvalidModeChangeException(); }
        if ( degree == 7 ) { throw new InvalidModeChangeException(); }
        if ( degree == 8 ) { throw new InvalidModeChangeException(); }
        if ( degree == 9 ) { return 3; }
        if ( degree == 10 ) { throw new InvalidModeChangeException(); }
        if ( degree == 11 ) { throw new InvalidModeChangeException(); }
        if ( degree == 12 ) { throw new InvalidModeChangeException(); }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromChromaticToMinsecond(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { return 2; }
        if ( degree == 3 ) { throw new InvalidModeChangeException(); }
        if ( degree == 4 ) { throw new InvalidModeChangeException(); }
        if ( degree == 5 ) { throw new InvalidModeChangeException(); }
        if ( degree == 6 ) { throw new InvalidModeChangeException(); }
        if ( degree == 7 ) { throw new InvalidModeChangeException(); }
        if ( degree == 8 ) { throw new InvalidModeChangeException(); }
        if ( degree == 9 ) { throw new InvalidModeChangeException(); }
        if ( degree == 10 ) { throw new InvalidModeChangeException(); }
        if ( degree == 11 ) { throw new InvalidModeChangeException(); }
        if ( degree == 12 ) { throw new InvalidModeChangeException(); }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromChromaticToSecond(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { throw new InvalidModeChangeException(); }
        if ( degree == 3 ) { return 2; }
        if ( degree == 4 ) { throw new InvalidModeChangeException(); }
        if ( degree == 5 ) { throw new InvalidModeChangeException(); }
        if ( degree == 6 ) { throw new InvalidModeChangeException(); }
        if ( degree == 7 ) { throw new InvalidModeChangeException(); }
        if ( degree == 8 ) { throw new InvalidModeChangeException(); }
        if ( degree == 9 ) { throw new InvalidModeChangeException(); }
        if ( degree == 10 ) { throw new InvalidModeChangeException(); }
        if ( degree == 11 ) { throw new InvalidModeChangeException(); }
        if ( degree == 12 ) { throw new InvalidModeChangeException(); }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromChromaticToMinthird(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { throw new InvalidModeChangeException(); }
        if ( degree == 3 ) { throw new InvalidModeChangeException(); }
        if ( degree == 4 ) { return 2; }
        if ( degree == 5 ) { throw new InvalidModeChangeException(); }
        if ( degree == 6 ) { throw new InvalidModeChangeException(); }
        if ( degree == 7 ) { throw new InvalidModeChangeException(); }
        if ( degree == 8 ) { throw new InvalidModeChangeException(); }
        if ( degree == 9 ) { throw new InvalidModeChangeException(); }
        if ( degree == 10 ) { throw new InvalidModeChangeException(); }
        if ( degree == 11 ) { throw new InvalidModeChangeException(); }
        if ( degree == 12 ) { throw new InvalidModeChangeException(); }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromChromaticToThird(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { throw new InvalidModeChangeException(); }
        if ( degree == 3 ) { throw new InvalidModeChangeException(); }
        if ( degree == 4 ) { throw new InvalidModeChangeException(); }
        if ( degree == 5 ) { return 2; }
        if ( degree == 6 ) { throw new InvalidModeChangeException(); }
        if ( degree == 7 ) { throw new InvalidModeChangeException(); }
        if ( degree == 8 ) { throw new InvalidModeChangeException(); }
        if ( degree == 9 ) { throw new InvalidModeChangeException(); }
        if ( degree == 10 ) { throw new InvalidModeChangeException(); }
        if ( degree == 11 ) { throw new InvalidModeChangeException(); }
        if ( degree == 12 ) { throw new InvalidModeChangeException(); }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromChromaticToFourth(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { throw new InvalidModeChangeException(); }
        if ( degree == 3 ) { throw new InvalidModeChangeException(); }
        if ( degree == 4 ) { throw new InvalidModeChangeException(); }
        if ( degree == 5 ) { throw new InvalidModeChangeException(); }
        if ( degree == 6 ) { throw new InvalidModeChangeException(); }
        if ( degree == 7 ) { return 2; }
        if ( degree == 8 ) { throw new InvalidModeChangeException(); }
        if ( degree == 9 ) { throw new InvalidModeChangeException(); }
        if ( degree == 10 ) { throw new InvalidModeChangeException(); }
        if ( degree == 11 ) { throw new InvalidModeChangeException(); }
        if ( degree == 12 ) { throw new InvalidModeChangeException(); }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromChromaticToTritone(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { throw new InvalidModeChangeException(); }
        if ( degree == 3 ) { throw new InvalidModeChangeException(); }
        if ( degree == 4 ) { throw new InvalidModeChangeException(); }
        if ( degree == 5 ) { throw new InvalidModeChangeException(); }
        if ( degree == 6 ) { throw new InvalidModeChangeException(); }
        if ( degree == 7 ) { throw new InvalidModeChangeException(); }
        if ( degree == 8 ) { return 2; }
        if ( degree == 9 ) { throw new InvalidModeChangeException(); }
        if ( degree == 10 ) { throw new InvalidModeChangeException(); }
        if ( degree == 11 ) { throw new InvalidModeChangeException(); }
        if ( degree == 12 ) { throw new InvalidModeChangeException(); }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromChromaticToFifth(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { throw new InvalidModeChangeException(); }
        if ( degree == 3 ) { throw new InvalidModeChangeException(); }
        if ( degree == 4 ) { throw new InvalidModeChangeException(); }
        if ( degree == 5 ) { throw new InvalidModeChangeException(); }
        if ( degree == 6 ) { throw new InvalidModeChangeException(); }
        if ( degree == 7 ) { throw new InvalidModeChangeException(); }
        if ( degree == 8 ) { throw new InvalidModeChangeException(); }
        if ( degree == 9 ) { return 2; }
        if ( degree == 10 ) { throw new InvalidModeChangeException(); }
        if ( degree == 11 ) { throw new InvalidModeChangeException(); }
        if ( degree == 12 ) { throw new InvalidModeChangeException(); }
        throw new InvalidModeChangeException();
    }

    // MAJOR --> ALL THE REST


    private int mapDegreeFromMajorToChromatic(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { return 3; }
        if ( degree == 3 ) { return 5; }
        if ( degree == 4 ) { return 6; }
        if ( degree == 5 ) { return 8; }
        if ( degree == 6 ) { return 10; }
        if ( degree == 7 ) { return 12; }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromMajorToDiminished(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDiminished(mapDegreeFromMajorToChromatic(degree));
    }

    private int mapDegreeFromMajorToIonian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToIonian(mapDegreeFromMajorToChromatic(degree));
    }

    private int mapDegreeFromMajorToMaj7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMaj7th(mapDegreeFromMajorToChromatic(degree));
    }

    private int mapDegreeFromMajorToMajtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajtriad(mapDegreeFromMajorToChromatic(degree));
    }


    private int mapDegreeFromMajorToDorian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDorian(mapDegreeFromMajorToChromatic(degree));
    }

    private int mapDegreeFromMajorToPhrygian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPhrygian(mapDegreeFromMajorToChromatic(degree));
    }

    private int mapDegreeFromMajorToLydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLydian(mapDegreeFromMajorToChromatic(degree));
    }

    private int mapDegreeFromMajorToMixolydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMixolydian(mapDegreeFromMajorToChromatic(degree));
    }

    private int mapDegreeFromMajorToAolean(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAolean(mapDegreeFromMajorToChromatic(degree));
    }

    private int mapDegreeFromMajorToLocrian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLocrian(mapDegreeFromMajorToChromatic(degree));
    }

    private int mapDegreeFromMajorToMajor(int degree) {
        return degree;
    }

    private int mapDegreeFromMajorToMinor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinor(mapDegreeFromMajorToChromatic(degree));
    }

    private int mapDegreeFromMajorToWholetone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToWholetone(mapDegreeFromMajorToChromatic(degree));
    }

    private int mapDegreeFromMajorToBlues(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToBlues(mapDegreeFromMajorToChromatic(degree));
    }

    private int mapDegreeFromMajorToPentamajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentamajor(mapDegreeFromMajorToChromatic(degree));
    }

    private int mapDegreeFromMajorToPentaminor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentaminor(mapDegreeFromMajorToChromatic(degree));
    }

    private int mapDegreeFromMajorToMin7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMin7th(mapDegreeFromMajorToChromatic(degree));
    }

    private int mapDegreeFromMajorToDim7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDim7th(mapDegreeFromMajorToChromatic(degree));
    }

    private int mapDegreeFromMajorToDom7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDom7th(mapDegreeFromMajorToChromatic(degree));
    }

    private int mapDegreeFromMajorToMintriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMintriad(mapDegreeFromMajorToChromatic(degree));
    }

    private int mapDegreeFromMajorToDimtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDimtriad(mapDegreeFromMajorToChromatic(degree));
    }

    private int mapDegreeFromMajorToAugtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAugtriad(mapDegreeFromMajorToChromatic(degree));
    }

    private int mapDegreeFromMajorToMinsecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinsecond(mapDegreeFromMajorToChromatic(degree));
    }

    private int mapDegreeFromMajorToSecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToSecond(mapDegreeFromMajorToChromatic(degree));
    }

    private int mapDegreeFromMajorToMinthird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinthird(mapDegreeFromMajorToChromatic(degree));
    }

    private int mapDegreeFromMajorToThird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToThird(mapDegreeFromMajorToChromatic(degree));
    }

    private int mapDegreeFromMajorToFourth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFourth(mapDegreeFromMajorToChromatic(degree));
    }

    private int mapDegreeFromMajorToTritone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToTritone(mapDegreeFromMajorToChromatic(degree));
    }

    private int mapDegreeFromMajorToFifth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFifth(mapDegreeFromMajorToChromatic(degree));
    }

    // MAJTRIAD --> ALL THE REST

    private int mapDegreeFromMajtriadToChromatic(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { return 5; }
        if ( degree == 3 ) { return 8; }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromMajtriadToMajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajor(mapDegreeFromMajtriadToChromatic(degree));
    }


    private int mapDegreeFromMajtriadToDiminished(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDiminished(mapDegreeFromMajtriadToChromatic(degree));
    }

    private int mapDegreeFromMajtriadToIonian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToIonian(mapDegreeFromMajtriadToChromatic(degree));
    }

    private int mapDegreeFromMajtriadToDorian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDorian(mapDegreeFromMajtriadToChromatic(degree));
    }

    private int mapDegreeFromMajtriadToPhrygian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPhrygian(mapDegreeFromMajtriadToChromatic(degree));
    }

    private int mapDegreeFromMajtriadToLydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLydian(mapDegreeFromMajtriadToChromatic(degree));
    }

    private int mapDegreeFromMajtriadToMixolydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMixolydian(mapDegreeFromMajtriadToChromatic(degree));
    }

    private int mapDegreeFromMajtriadToAolean(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAolean(mapDegreeFromMajtriadToChromatic(degree));
    }

    private int mapDegreeFromMajtriadToLocrian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLocrian(mapDegreeFromMajtriadToChromatic(degree));
    }

    private int mapDegreeFromMajtriadToMinor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinor(mapDegreeFromMajtriadToChromatic(degree));
    }

    private int mapDegreeFromMajtriadToWholetone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToWholetone(mapDegreeFromMajtriadToChromatic(degree));
    }

    private int mapDegreeFromMajtriadToBlues(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToBlues(mapDegreeFromMajtriadToChromatic(degree));
    }

    private int mapDegreeFromMajtriadToPentamajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentamajor(mapDegreeFromMajtriadToChromatic(degree));
    }

    private int mapDegreeFromMajtriadToPentaminor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentaminor(mapDegreeFromMajtriadToChromatic(degree));
    }

    private int mapDegreeFromMajtriadToMaj7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMaj7th(mapDegreeFromMajtriadToChromatic(degree));
    }

    private int mapDegreeFromMajtriadToMin7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMin7th(mapDegreeFromMajtriadToChromatic(degree));
    }

    private int mapDegreeFromMajtriadToDim7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDom7th(mapDegreeFromMajtriadToChromatic(degree));
    }

    private int mapDegreeFromMajtriadToDom7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDom7th(mapDegreeFromMajtriadToChromatic(degree));
    }

    private int mapDegreeFromMajtriadToMajtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajtriad(mapDegreeFromMajtriadToChromatic(degree));
    }

    private int mapDegreeFromMajtriadToMintriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMintriad(mapDegreeFromMajtriadToChromatic(degree));
    }

    private int mapDegreeFromMajtriadToDimtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDimtriad(mapDegreeFromMajtriadToChromatic(degree));
    }

    private int mapDegreeFromMajtriadToAugtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAugtriad(mapDegreeFromMajtriadToChromatic(degree));
    }

    private int mapDegreeFromMajtriadToMinsecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinsecond(mapDegreeFromMajtriadToChromatic(degree));
    }

    private int mapDegreeFromMajtriadToSecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToSecond(mapDegreeFromMajtriadToChromatic(degree));
    }

    private int mapDegreeFromMajtriadToMinthird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinthird(mapDegreeFromMajtriadToChromatic(degree));
    }

    private int mapDegreeFromMajtriadToThird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToThird(mapDegreeFromMajtriadToChromatic(degree));
    }

    private int mapDegreeFromMajtriadToFourth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFourth(mapDegreeFromMajtriadToChromatic(degree));
    }

    private int mapDegreeFromMajtriadToTritone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToTritone(mapDegreeFromMajtriadToChromatic(degree));
    }

    private int mapDegreeFromMajtriadToFifth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFifth(mapDegreeFromMajtriadToChromatic(degree));
    }

    // MINOR --> ALL THE REST


    private int mapDegreeFromMinorToChromatic(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { return 3; }
        if ( degree == 3 ) { return 4; }
        if ( degree == 4 ) { return 6; }
        if ( degree == 5 ) { return 8; }
        if ( degree == 6 ) { return 9; }
        if ( degree == 7 ) { return 11; }
        throw new InvalidModeChangeException();

    }

    private int mapDegreeFromMinorToDiminished(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDiminished(mapDegreeFromMinorToChromatic(degree));
    }

    private int mapDegreeFromMinorToIonian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToIonian(mapDegreeFromMinorToChromatic(degree));
    }

    private int mapDegreeFromMinorToDorian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDorian(mapDegreeFromMinorToChromatic(degree));
    }

    private int mapDegreeFromMinorToPhrygian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPhrygian(mapDegreeFromMinorToChromatic(degree));
    }

    private int mapDegreeFromMinorToLydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLydian(mapDegreeFromMinorToChromatic(degree));
    }

    private int mapDegreeFromMinorToMixolydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMixolydian(mapDegreeFromMinorToChromatic(degree));
    }

    private int mapDegreeFromMinorToAolean(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAolean(mapDegreeFromMinorToChromatic(degree));
    }

    private int mapDegreeFromMinorToLocrian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLocrian(mapDegreeFromMinorToChromatic(degree));
    }

    private int mapDegreeFromMinorToMajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajor(mapDegreeFromMinorToChromatic(degree));
    }

    private int mapDegreeFromMinorToMinor(int degree) {
        return degree;
    }

    private int mapDegreeFromMinorToWholetone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToWholetone(mapDegreeFromMinorToChromatic(degree));
    }

    private int mapDegreeFromMinorToBlues(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToBlues(mapDegreeFromMinorToChromatic(degree));
    }

    private int mapDegreeFromMinorToPentamajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentamajor(mapDegreeFromMinorToChromatic(degree));
    }

    private int mapDegreeFromMinorToPentaminor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentamajor(mapDegreeFromMinorToChromatic(degree));
    }

    private int mapDegreeFromMinorToMaj7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMaj7th(mapDegreeFromMinorToChromatic(degree));
    }

    private int mapDegreeFromMinorToMin7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMin7th(mapDegreeFromMinorToChromatic(degree));
    }

    private int mapDegreeFromMinorToDim7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDim7th(mapDegreeFromMinorToChromatic(degree));
    }

    private int mapDegreeFromMinorToDom7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDom7th(mapDegreeFromMinorToChromatic(degree));
    }

    private int mapDegreeFromMinorToMajtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajtriad(mapDegreeFromMinorToChromatic(degree));
    }

    private int mapDegreeFromMinorToMintriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMintriad(mapDegreeFromMinorToChromatic(degree));
    }

    private int mapDegreeFromMinorToDimtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDimtriad(mapDegreeFromMinorToChromatic(degree));
    }

    private int mapDegreeFromMinorToAugtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAugtriad(mapDegreeFromMinorToChromatic(degree));
    }

    private int mapDegreeFromMinorToMinsecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinsecond(mapDegreeFromMinorToChromatic(degree));
    }

    private int mapDegreeFromMinorToSecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToSecond(mapDegreeFromMinorToChromatic(degree));
    }

    private int mapDegreeFromMinorToMinthird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinthird(mapDegreeFromMinorToChromatic(degree));
    }

    private int mapDegreeFromMinorToThird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToThird(mapDegreeFromMinorToChromatic(degree));
    }

    private int mapDegreeFromMinorToFourth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFourth(mapDegreeFromMinorToChromatic(degree));
    }

    private int mapDegreeFromMinorToTritone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToTritone(mapDegreeFromMinorToChromatic(degree));
    }

    private int mapDegreeFromMinorToFifth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFifth(mapDegreeFromMinorToChromatic(degree));
    }

    // MINTRIAD --> ALL THE REST

    private int mapDegreeFromMintriadToChromatic(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { return 4; }
        if ( degree == 3 ) { return 8; }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromMintriadToDiminished(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDiminished(mapDegreeFromMintriadToChromatic(degree));
    }

    private int mapDegreeFromMintriadToIonian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToIonian(mapDegreeFromMintriadToChromatic(degree));
    }

    private int mapDegreeFromMintriadToMajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajor(mapDegreeFromMintriadToChromatic(degree));
    }

    private int mapDegreeFromMintriadToDorian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDorian(mapDegreeFromMintriadToChromatic(degree));
    }

    private int mapDegreeFromMintriadToPhrygian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPhrygian(mapDegreeFromMintriadToChromatic(degree));
    }

    private int mapDegreeFromMintriadToLydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLydian(mapDegreeFromMintriadToChromatic(degree));
    }

    private int mapDegreeFromMintriadToMixolydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMixolydian(mapDegreeFromMintriadToChromatic(degree));
    }

    private int mapDegreeFromMintriadToAolean(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAolean(mapDegreeFromMintriadToChromatic(degree));
    }

    private int mapDegreeFromMintriadToLocrian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLocrian(mapDegreeFromMintriadToChromatic(degree));
    }

    private int mapDegreeFromMintriadToMinor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinor(mapDegreeFromMintriadToChromatic(degree));
    }

    private int mapDegreeFromMintriadToWholetone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToWholetone(mapDegreeFromMintriadToChromatic(degree));
    }

    private int mapDegreeFromMintriadToBlues(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToBlues(mapDegreeFromMintriadToChromatic(degree));
    }

    private int mapDegreeFromMintriadToPentamajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentamajor(mapDegreeFromMintriadToChromatic(degree));
    }

    private int mapDegreeFromMintriadToPentaminor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentaminor(mapDegreeFromMintriadToChromatic(degree));
    }

    private int mapDegreeFromMintriadToMaj7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMaj7th(mapDegreeFromMintriadToChromatic(degree));
    }

    private int mapDegreeFromMintriadToMin7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMin7th(mapDegreeFromMintriadToChromatic(degree));
    }

    private int mapDegreeFromMintriadToDim7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDom7th(mapDegreeFromMintriadToChromatic(degree));
    }

    private int mapDegreeFromMintriadToDom7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDom7th(mapDegreeFromMintriadToChromatic(degree));
    }

    private int mapDegreeFromMintriadToMajtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajtriad(mapDegreeFromMintriadToChromatic(degree));
    }

    private int mapDegreeFromMintriadToMintriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMintriad(mapDegreeFromMintriadToChromatic(degree));
    }

    private int mapDegreeFromMintriadToDimtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDimtriad(mapDegreeFromMintriadToChromatic(degree));
    }

    private int mapDegreeFromMintriadToAugtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAugtriad(mapDegreeFromMintriadToChromatic(degree));
    }

    private int mapDegreeFromMintriadToMinsecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinsecond(mapDegreeFromMintriadToChromatic(degree));
    }

    private int mapDegreeFromMintriadToSecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToSecond(mapDegreeFromMintriadToChromatic(degree));
    }

    private int mapDegreeFromMintriadToMinthird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinthird(mapDegreeFromMintriadToChromatic(degree));
    }

    private int mapDegreeFromMintriadToThird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToThird(mapDegreeFromMintriadToChromatic(degree));
    }

    private int mapDegreeFromMintriadToFourth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFourth(mapDegreeFromMintriadToChromatic(degree));
    }

    private int mapDegreeFromMintriadToTritone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToTritone(mapDegreeFromMintriadToChromatic(degree));
    }

    private int mapDegreeFromMintriadToFifth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFifth(mapDegreeFromMintriadToChromatic(degree));
    }

    // DIMINISHED --> ALL THE REST

    private int mapDegreeFromDiminishedToChromatic(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { return 3; }
        if ( degree == 3 ) { return 4; }
        if ( degree == 4 ) { return 6; }
        if ( degree == 5 ) { return 7; }
        if ( degree == 6 ) { return 9; }
        if ( degree == 7 ) { return 10; }
        if ( degree == 8 ) { return 12; }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromDiminishedToDiminished(int degree) {
        return degree;
    }

    private int mapDegreeFromDiminishedToIonian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToIonian(mapDegreeFromDiminishedToChromatic(degree));
    }

    private int mapDegreeFromDiminishedToDorian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDorian(mapDegreeFromDiminishedToChromatic(degree));
    }

    private int mapDegreeFromDiminishedToPhrygian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPhrygian(mapDegreeFromDiminishedToChromatic(degree));
    }

    private int mapDegreeFromDiminishedToLydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLydian(mapDegreeFromDiminishedToChromatic(degree));
    }

    private int mapDegreeFromDiminishedToMixolydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMixolydian(mapDegreeFromDiminishedToChromatic(degree));
    }

    private int mapDegreeFromDiminishedToAolean(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAolean(mapDegreeFromDiminishedToChromatic(degree));
    }

    private int mapDegreeFromDiminishedToLocrian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLocrian(mapDegreeFromDiminishedToChromatic(degree));
    }

    private int mapDegreeFromDiminishedToMajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajor(mapDegreeFromDiminishedToChromatic(degree));
    }

    private int mapDegreeFromDiminishedToMinor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinor(mapDegreeFromDiminishedToChromatic(degree));
    }

    private int mapDegreeFromDiminishedToWholetone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToWholetone(mapDegreeFromDiminishedToChromatic(degree));
    }

    private int mapDegreeFromDiminishedToBlues(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToBlues(mapDegreeFromDiminishedToChromatic(degree));
    }

    private int mapDegreeFromDiminishedToPentamajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentamajor(mapDegreeFromDiminishedToChromatic(degree));
    }

    private int mapDegreeFromDiminishedToPentaminor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentaminor(mapDegreeFromDiminishedToChromatic(degree));
    }

    private int mapDegreeFromDiminishedToMaj7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMaj7th(mapDegreeFromDiminishedToChromatic(degree));
    }

    private int mapDegreeFromDiminishedToMin7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMin7th(mapDegreeFromDiminishedToChromatic(degree));
    }

    private int mapDegreeFromDiminishedToDim7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDim7th(mapDegreeFromDiminishedToChromatic(degree));
    }

    private int mapDegreeFromDiminishedToDom7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDom7th(mapDegreeFromDiminishedToChromatic(degree));
    }

    private int mapDegreeFromDiminishedToMajtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajtriad(mapDegreeFromDiminishedToChromatic(degree));
    }

    private int mapDegreeFromDiminishedToMintriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMintriad(mapDegreeFromDiminishedToChromatic(degree));
    }

    private int mapDegreeFromDiminishedToDimtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDimtriad(mapDegreeFromDiminishedToChromatic(degree));
    }

    private int mapDegreeFromDiminishedToAugtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAugtriad(mapDegreeFromDiminishedToChromatic(degree));
    }

    private int mapDegreeFromDiminishedToMinsecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinsecond(mapDegreeFromDiminishedToChromatic(degree));
    }

    private int mapDegreeFromDiminishedToSecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToSecond(mapDegreeFromDiminishedToChromatic(degree));
    }

    private int mapDegreeFromDiminishedToMinthird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinthird(mapDegreeFromDiminishedToChromatic(degree));
    }

    private int mapDegreeFromDiminishedToThird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToThird(mapDegreeFromDiminishedToChromatic(degree));
    }

    private int mapDegreeFromDiminishedToFourth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFourth(mapDegreeFromDiminishedToChromatic(degree));
    }

    private int mapDegreeFromDiminishedToTritone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToTritone(mapDegreeFromDiminishedToChromatic(degree));
    }

    private int mapDegreeFromDiminishedToFifth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFifth(mapDegreeFromDiminishedToChromatic(degree));
    }

    // WHOLETONE --> ALL THE REST

    private int mapDegreeFromWholetoneToChromatic(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { return 3; }
        if ( degree == 3 ) { return 5; }
        if ( degree == 4 ) { return 7; }
        if ( degree == 5 ) { return 9; }
        if ( degree == 6 ) { return 11; }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromWholetoneToDiminished(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDiminished(mapDegreeFromWholetoneToChromatic(degree));
    }

    private int mapDegreeFromWholetoneToIonian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToIonian(mapDegreeFromWholetoneToChromatic(degree));
    }

    private int mapDegreeFromWholetoneToDorian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDorian(mapDegreeFromWholetoneToChromatic(degree));
    }

    private int mapDegreeFromWholetoneToPhrygian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPhrygian(mapDegreeFromWholetoneToChromatic(degree));
    }

    private int mapDegreeFromWholetoneToLydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLydian(mapDegreeFromWholetoneToChromatic(degree));
    }

    private int mapDegreeFromWholetoneToMixolydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMixolydian(mapDegreeFromWholetoneToChromatic(degree));
    }

    private int mapDegreeFromWholetoneToAolean(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAolean(mapDegreeFromWholetoneToChromatic(degree));
    }

    private int mapDegreeFromWholetoneToLocrian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLocrian(mapDegreeFromWholetoneToChromatic(degree));
    }

    private int mapDegreeFromWholetoneToMajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajor(mapDegreeFromWholetoneToChromatic(degree));
    }

    private int mapDegreeFromWholetoneToMinor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinor(mapDegreeFromWholetoneToChromatic(degree));
    }

    private int mapDegreeFromWholetoneToWholetone(int degree) throws InvalidModeChangeException {
        return degree;
    }

    private int mapDegreeFromWholetoneToBlues(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToBlues(mapDegreeFromWholetoneToChromatic(degree));
    }

    private int mapDegreeFromWholetoneToPentamajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentamajor(mapDegreeFromWholetoneToChromatic(degree));
    }

    private int mapDegreeFromWholetoneToPentaminor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentaminor(mapDegreeFromWholetoneToChromatic(degree));
    }

    private int mapDegreeFromWholetoneToMaj7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMaj7th(mapDegreeFromWholetoneToChromatic(degree));
    }

    private int mapDegreeFromWholetoneToMin7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMin7th(mapDegreeFromWholetoneToChromatic(degree));
    }

    private int mapDegreeFromWholetoneToDim7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDim7th(mapDegreeFromWholetoneToChromatic(degree));
    }

    private int mapDegreeFromWholetoneToDom7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDom7th(mapDegreeFromWholetoneToChromatic(degree));
    }

    private int mapDegreeFromWholetoneToMajtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajtriad(mapDegreeFromWholetoneToChromatic(degree));
    }

    private int mapDegreeFromWholetoneToMintriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMintriad(mapDegreeFromWholetoneToChromatic(degree));
    }

    private int mapDegreeFromWholetoneToDimtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDimtriad(mapDegreeFromWholetoneToChromatic(degree));
    }

    private int mapDegreeFromWholetoneToAugtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAugtriad(mapDegreeFromWholetoneToChromatic(degree));
    }

    private int mapDegreeFromWholetoneToMinsecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinsecond(mapDegreeFromWholetoneToChromatic(degree));
    }

    private int mapDegreeFromWholetoneToSecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToSecond(mapDegreeFromWholetoneToChromatic(degree));
    }

    private int mapDegreeFromWholetoneToMinthird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinthird(mapDegreeFromWholetoneToChromatic(degree));
    }

    private int mapDegreeFromWholetoneToThird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToThird(mapDegreeFromWholetoneToChromatic(degree));
    }

    private int mapDegreeFromWholetoneToFourth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFourth(mapDegreeFromWholetoneToChromatic(degree));
    }

    private int mapDegreeFromWholetoneToTritone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToTritone(mapDegreeFromWholetoneToChromatic(degree));
    }

    private int mapDegreeFromWholetoneToFifth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFifth(mapDegreeFromWholetoneToChromatic(degree));
    }

    private int mapDegreeFromBluesToChromatic(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { return 4; }
        if ( degree == 3 ) { return 6; }
        if ( degree == 4 ) { return 7; }
        if ( degree == 5 ) { return 8; }
        if ( degree == 6 ) { return 11; }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromBluesToDiminished(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDiminished(mapDegreeFromBluesToChromatic(degree));
    }

    private int mapDegreeFromBluesToIonian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToIonian(mapDegreeFromBluesToChromatic(degree));
    }

    private int mapDegreeFromBluesToDorian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDorian(mapDegreeFromBluesToChromatic(degree));
    }

    private int mapDegreeFromBluesToPhrygian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPhrygian(mapDegreeFromBluesToChromatic(degree));
    }

    private int mapDegreeFromBluesToLydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLydian(mapDegreeFromBluesToChromatic(degree));
    }

    private int mapDegreeFromBluesToMixolydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMixolydian(mapDegreeFromBluesToChromatic(degree));
    }

    private int mapDegreeFromBluesToAolean(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAolean(mapDegreeFromBluesToChromatic(degree));
    }

    private int mapDegreeFromBluesToLocrian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLocrian(mapDegreeFromBluesToChromatic(degree));
    }

    private int mapDegreeFromBluesToMajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajor(mapDegreeFromBluesToChromatic(degree));
    }

    private int mapDegreeFromBluesToMinor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinor(mapDegreeFromBluesToChromatic(degree));
    }

    private int mapDegreeFromBluesToWholetone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToWholetone(mapDegreeFromBluesToChromatic(degree));
    }

    private int mapDegreeFromBluesToBlues(int degree) {
        return degree;
    }

    private int mapDegreeFromBluesToPentamajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentamajor(mapDegreeFromBluesToChromatic(degree));
    }

    private int mapDegreeFromBluesToPentaminor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentaminor(mapDegreeFromBluesToChromatic(degree));
    }

    private int mapDegreeFromBluesToMaj7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMaj7th(mapDegreeFromBluesToChromatic(degree));
    }

    private int mapDegreeFromBluesToMin7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMin7th(mapDegreeFromBluesToChromatic(degree));
    }

    private int mapDegreeFromBluesToDim7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDim7th(mapDegreeFromBluesToChromatic(degree));
    }

    private int mapDegreeFromBluesToDom7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDom7th(mapDegreeFromBluesToChromatic(degree));
    }

    private int mapDegreeFromBluesToMajtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajtriad(mapDegreeFromBluesToChromatic(degree));
    }

    private int mapDegreeFromBluesToMintriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMintriad(mapDegreeFromBluesToChromatic(degree));
    }

    private int mapDegreeFromBluesToDimtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDimtriad(mapDegreeFromBluesToChromatic(degree));
    }

    private int mapDegreeFromBluesToAugtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAugtriad(mapDegreeFromBluesToChromatic(degree));
    }

    private int mapDegreeFromBluesToMinsecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinsecond(mapDegreeFromBluesToChromatic(degree));
    }

    private int mapDegreeFromBluesToSecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToSecond(mapDegreeFromBluesToChromatic(degree));
    }

    private int mapDegreeFromBluesToMinthird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinthird(mapDegreeFromBluesToChromatic(degree));
    }

    private int mapDegreeFromBluesToThird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToThird(mapDegreeFromBluesToChromatic(degree));
    }

    private int mapDegreeFromBluesToFourth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFourth(mapDegreeFromBluesToChromatic(degree));
    }

    private int mapDegreeFromBluesToTritone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToTritone(mapDegreeFromBluesToChromatic(degree));
    }

    private int mapDegreeFromBluesToFifth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFifth(mapDegreeFromBluesToChromatic(degree));
    }

    private int mapDegreeFromMaj7thToChromatic(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { return 5; }
        if ( degree == 3 ) { return 8; }
        if ( degree == 4 ) { return 12; }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromMaj7thToMajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajor(mapDegreeFromMaj7thToChromatic(degree));
    }

    private int mapDegreeFromMaj7thToDiminished(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDiminished(mapDegreeFromMaj7thToChromatic(degree));
    }

    private int mapDegreeFromMaj7thToIonian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToIonian(mapDegreeFromMaj7thToChromatic(degree));
    }

    private int mapDegreeFromMaj7thToDorian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDorian(mapDegreeFromMaj7thToChromatic(degree));
    }

    private int mapDegreeFromMaj7thToPhrygian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPhrygian(mapDegreeFromMaj7thToChromatic(degree));
    }

    private int mapDegreeFromMaj7thToLydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLydian(mapDegreeFromMaj7thToChromatic(degree));
    }

    private int mapDegreeFromMaj7thToMixolydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMixolydian(mapDegreeFromMaj7thToChromatic(degree));
    }

    private int mapDegreeFromMaj7thToAolean(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAolean(mapDegreeFromMaj7thToChromatic(degree));
    }

    private int mapDegreeFromMaj7thToLocrian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLocrian(mapDegreeFromMaj7thToChromatic(degree));
    }

    private int mapDegreeFromMaj7thToMinor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinor(mapDegreeFromMaj7thToChromatic(degree));
    }

    private int mapDegreeFromMaj7thToWholetone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToWholetone(mapDegreeFromMaj7thToChromatic(degree));
    }

    private int mapDegreeFromMaj7thToBlues(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToBlues(mapDegreeFromMaj7thToChromatic(degree));
    }

    private int mapDegreeFromMaj7thToPentamajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentamajor(mapDegreeFromMaj7thToChromatic(degree));
    }

    private int mapDegreeFromMaj7thToPentaminor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentaminor(mapDegreeFromMaj7thToChromatic(degree));
    }

    private int mapDegreeFromMaj7thToMaj7th(int degree) {
        return degree;
    }

    private int mapDegreeFromMaj7thToMin7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMin7th(mapDegreeFromMaj7thToChromatic(degree));
    }

    private int mapDegreeFromMaj7thToDim7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDim7th(mapDegreeFromMaj7thToChromatic(degree));
    }

    private int mapDegreeFromMaj7thToDom7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDom7th(mapDegreeFromMaj7thToChromatic(degree));
    }

    private int mapDegreeFromMaj7thToMajtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajtriad(mapDegreeFromMaj7thToChromatic(degree));
    }

    private int mapDegreeFromMaj7thToMintriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMintriad(mapDegreeFromMaj7thToChromatic(degree));
    }

    private int mapDegreeFromMaj7thToDimtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDimtriad(mapDegreeFromMaj7thToChromatic(degree));
    }

    private int mapDegreeFromMaj7thToAugtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAugtriad(mapDegreeFromMaj7thToChromatic(degree));
    }

    private int mapDegreeFromMaj7thToMinsecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinsecond(mapDegreeFromMaj7thToChromatic(degree));
    }

    private int mapDegreeFromMaj7thToSecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToSecond(mapDegreeFromMaj7thToChromatic(degree));
    }

    private int mapDegreeFromMaj7thToMinthird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinthird(mapDegreeFromMaj7thToChromatic(degree));
    }

    private int mapDegreeFromMaj7thToThird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToThird(mapDegreeFromMaj7thToChromatic(degree));
    }

    private int mapDegreeFromMaj7thToFourth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFourth(mapDegreeFromMaj7thToChromatic(degree));
    }

    private int mapDegreeFromMaj7thToTritone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToTritone(mapDegreeFromMaj7thToChromatic(degree));
    }

    private int mapDegreeFromMaj7thToFifth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFifth(mapDegreeFromMaj7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToChromatic(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { return 4; }
        if ( degree == 3 ) { return 8; }
        if ( degree == 4 ) { return 11; }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromMin7thToDiminished(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDiminished(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToIonian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToIonian(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToDorian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDorian(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToPhrygian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPhrygian(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToLydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLydian(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToMixolydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMixolydian(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToAolean(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAolean(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToLocrian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLocrian(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToMajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajor(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToMinor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinor(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToWholetone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToWholetone(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToBlues(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToBlues(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToPentamajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentamajor(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToPentaminor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentaminor(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToMaj7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMaj7th(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToMin7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMin7th(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToDim7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDim7th(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToDom7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDom7th(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToMajtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajtriad(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToMintriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMintriad(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToDimtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDimtriad(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToAugtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAugtriad(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToMinsecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinsecond(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToSecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToSecond(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToMinthird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinthird(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToThird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToThird(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToFourth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFourth(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToTritone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToTritone(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromMin7thToFifth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFifth(mapDegreeFromMin7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToChromatic(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { return 4; }
        if ( degree == 3 ) { return 7; }
        if ( degree == 4 ) { return 10; }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromDim7thToDiminished(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDiminished(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToIonian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToIonian(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToDorian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDorian(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToPhrygian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPhrygian(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToLydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLydian(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToMixolydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMixolydian(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToAolean(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAolean(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToLocrian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLocrian(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToMajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajor(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToMinor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinor(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToWholetone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToWholetone(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToBlues(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToBlues(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToPentamajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentamajor(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToPentaminor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentaminor(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToMaj7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMaj7th(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToMin7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMin7th(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToDim7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDim7th(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToDom7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDom7th(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToMajtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajtriad(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToMintriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMintriad(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToDimtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDimtriad(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToAugtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAugtriad(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToMinsecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinsecond(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToSecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToSecond(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToMinthird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinthird(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToThird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToThird(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToFourth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFourth(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToTritone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToTritone(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDim7thToFifth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFifth(mapDegreeFromDim7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToChromatic(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { return 5; }
        if ( degree == 3 ) { return 8; }
        if ( degree == 4 ) { return 11; }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromDom7thToDiminished(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDiminished(mapDegreeFromDom7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToIonian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToIonian(mapDegreeFromDom7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToDorian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDorian(mapDegreeFromDom7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToPhrygian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPhrygian(mapDegreeFromDom7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToLydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLydian(mapDegreeFromDom7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToMixolydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMixolydian(mapDegreeFromDom7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToAolean(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAolean(mapDegreeFromDom7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToLocrian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLocrian(mapDegreeFromDom7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToMajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajor(mapDegreeFromDom7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToMinor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinor(mapDegreeFromDom7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToWholetone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToWholetone(mapDegreeFromDom7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToBlues(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToBlues(mapDegreeFromDom7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToPentamajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentamajor(mapDegreeFromDom7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToPentaminor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentaminor(mapDegreeFromDom7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToMaj7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMaj7th(mapDegreeFromDom7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToMin7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMin7th(mapDegreeFromDom7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToDim7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDim7th(mapDegreeFromDom7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToDom7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDom7th(mapDegreeFromDom7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToMajtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajtriad(mapDegreeFromDom7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToMintriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMintriad(mapDegreeFromDom7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToDimtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDimtriad(mapDegreeFromDom7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToAugtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAugtriad(mapDegreeFromDom7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToMinsecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinsecond(mapDegreeFromDom7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToSecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToSecond(mapDegreeFromDom7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToMinthird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinthird(mapDegreeFromDom7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToThird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToThird(mapDegreeFromDom7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToFourth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFourth(mapDegreeFromDom7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToTritone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToTritone(mapDegreeFromDom7thToChromatic(degree));
    }

    private int mapDegreeFromDom7thToFifth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFifth(mapDegreeFromDom7thToChromatic(degree));
    }

    private Stack<SilentNoteComponents> noteStack;

    public void push() {
        SilentNoteComponents snc = new SilentNoteComponents(scale,degree,location,duration,volume);
        noteStack.push(snc);
    }

    public void pop() {
        SilentNoteComponents snc = noteStack.pop();
        scale = snc.scale();
        degree = snc.degree();
        location = snc.location();
        duration = snc.duration();
        volume = snc.volume();
    }

    public void pushNoteForAlphabetics() {
        SilentNoteComponents snc = new SilentNoteComponents(scale,degree,location,duration,volume);
        noteStackForAlphabetics.push(snc);
    }

    public void popNoteForAlphabetics() {
        SilentNoteComponents snc = noteStackForAlphabetics.pop();
        scale = snc.scale();
        degree = snc.degree();
        location = snc.location();
        duration = snc.duration();
        volume = snc.volume();
    }

    public void changeVolume(String command) {
        if ( command.equals("MAXVOL") ) { volume = 16000; }
        if ( command.equals("MINVOL") ) { volume = 0; }
        if ( command.equals("MEDVOL") ) { volume = 8000; }
        if ( command.equals("INCVOL") ) {
            volume = volume + 1000;
            if ( volume > 16000 ) { volume = 16000; }
        }
        if ( command.equals("DECVOL") ) {
            volume = volume - 1000;
            if ( volume < 0 ) { volume = 0; }
        }
        if ( command.equals("INCVOL1") ) {
            volume = volume + 100;
            if ( volume > 16000 ) { volume = 16000; }
        }
        if ( command.equals("DECVOL1") ) {
            volume = volume - 100;
            if ( volume < 0 ) { volume = 0; }
        }
    }

    private int mapDegreeFromDimtriadToChromatic(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { return 4; }
        if ( degree == 3 ) { return 7; }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromDimtriadToDiminished(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDiminished(mapDegreeFromDimtriadToChromatic(degree));
    }

    private int mapDegreeFromDimtriadToIonian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToIonian(mapDegreeFromDimtriadToChromatic(degree));
    }

    private int mapDegreeFromDimtriadToDorian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDorian(mapDegreeFromDimtriadToChromatic(degree));
    }

    private int mapDegreeFromDimtriadToPhrygian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPhrygian(mapDegreeFromDimtriadToChromatic(degree));
    }

    private int mapDegreeFromDimtriadToLydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLydian(mapDegreeFromDimtriadToChromatic(degree));
    }

    private int mapDegreeFromDimtriadToMixolydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMixolydian(mapDegreeFromDimtriadToChromatic(degree));
    }

    private int mapDegreeFromDimtriadToAolean(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDiminished(mapDegreeFromDimtriadToChromatic(degree));
    }

    private int mapDegreeFromDimtriadToLocrian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLocrian(mapDegreeFromDimtriadToChromatic(degree));
    }

    private int mapDegreeFromDimtriadToMajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajor(mapDegreeFromDimtriadToChromatic(degree));
    }

    private int mapDegreeFromDimtriadToMinor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinor(mapDegreeFromDimtriadToChromatic(degree));
    }

    private int mapDegreeFromDimtriadToWholetone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToWholetone(mapDegreeFromDimtriadToChromatic(degree));
    }

    private int mapDegreeFromDimtriadToBlues(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToBlues(mapDegreeFromDimtriadToChromatic(degree));
    }

    private int mapDegreeFromDimtriadToPentamajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentamajor(mapDegreeFromDimtriadToChromatic(degree));
    }

    private int mapDegreeFromDimtriadToPentaminor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentaminor(mapDegreeFromDimtriadToChromatic(degree));
    }

    private int mapDegreeFromDimtriadToMaj7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMaj7th(mapDegreeFromDimtriadToChromatic(degree));
    }

    private int mapDegreeFromDimtriadToMin7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMin7th(mapDegreeFromDimtriadToChromatic(degree));
    }

    private int mapDegreeFromDimtriadToDim7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDim7th(mapDegreeFromDimtriadToChromatic(degree));
    }

    private int mapDegreeFromDimtriadToDom7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDom7th(mapDegreeFromDimtriadToChromatic(degree));
    }

    private int mapDegreeFromDimtriadToMajtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajtriad(mapDegreeFromDimtriadToChromatic(degree));
    }

    private int mapDegreeFromDimtriadToMintriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMintriad(mapDegreeFromDimtriadToChromatic(degree));
    }

    private int mapDegreeFromDimtriadToDimtriad(int degree) {
        return degree;
    }

    private int mapDegreeFromDimtriadToAugtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAugtriad(mapDegreeFromDimtriadToChromatic(degree));
    }

    private int mapDegreeFromDimtriadToMinsecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinsecond(mapDegreeFromDimtriadToChromatic(degree));
    }

    private int mapDegreeFromDimtriadToSecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToSecond(mapDegreeFromDimtriadToChromatic(degree));
    }

    private int mapDegreeFromDimtriadToMinthird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinthird(mapDegreeFromDimtriadToChromatic(degree));
    }

    private int mapDegreeFromDimtriadToThird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToThird(mapDegreeFromDimtriadToChromatic(degree));
    }

    private int mapDegreeFromDimtriadToFourth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFourth(mapDegreeFromDimtriadToChromatic(degree));
    }

    private int mapDegreeFromDimtriadToTritone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToTritone(mapDegreeFromDimtriadToChromatic(degree));
    }

    private int mapDegreeFromDimtriadToFifth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFifth(mapDegreeFromDimtriadToChromatic(degree));
    }

    private int mapDegreeFromAugtriadToChromatic(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { return 5; }
        if ( degree == 3 ) { return 9; }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromAugtriadToDiminished(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDiminished(mapDegreeFromAugtriadToChromatic(degree));
    }

    private int mapDegreeFromAugtriadToIonian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToIonian(mapDegreeFromAugtriadToChromatic(degree));
    }

    private int mapDegreeFromAugtriadToDorian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDorian(mapDegreeFromAugtriadToChromatic(degree));
    }

    private int mapDegreeFromAugtriadToPhrygian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPhrygian(mapDegreeFromAugtriadToChromatic(degree));
    }

    private int mapDegreeFromAugtriadToLydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLydian(mapDegreeFromAugtriadToChromatic(degree));
    }

    private int mapDegreeFromAugtriadToMixolydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMixolydian(mapDegreeFromAugtriadToChromatic(degree));
    }

    private int mapDegreeFromAugtriadToAolean(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAolean(mapDegreeFromAugtriadToChromatic(degree));
    }

    private int mapDegreeFromAugtriadToLocrian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLocrian(mapDegreeFromAugtriadToChromatic(degree));
    }

    private int mapDegreeFromAugtriadToMajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajor(mapDegreeFromAugtriadToChromatic(degree));
    }

    private int mapDegreeFromAugtriadToMinor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinor(mapDegreeFromAugtriadToChromatic(degree));
    }

    private int mapDegreeFromAugtriadToWholetone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToWholetone(mapDegreeFromAugtriadToChromatic(degree));
    }

    private int mapDegreeFromAugtriadToBlues(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToBlues(mapDegreeFromAugtriadToChromatic(degree));
    }

    private int mapDegreeFromAugtriadToPentamajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentamajor(mapDegreeFromAugtriadToChromatic(degree));
    }

    private int mapDegreeFromAugtriadToPentaminor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentaminor(mapDegreeFromAugtriadToChromatic(degree));
    }

    private int mapDegreeFromAugtriadToMaj7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMaj7th(mapDegreeFromAugtriadToChromatic(degree));
    }

    private int mapDegreeFromAugtriadToMin7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMin7th(mapDegreeFromAugtriadToChromatic(degree));
    }

    private int mapDegreeFromAugtriadToDim7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDim7th(mapDegreeFromAugtriadToChromatic(degree));
    }

    private int mapDegreeFromAugtriadToDom7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDom7th(mapDegreeFromAugtriadToChromatic(degree));
    }

    private int mapDegreeFromAugtriadToMajtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajtriad(mapDegreeFromAugtriadToChromatic(degree));
    }

    private int mapDegreeFromAugtriadToMintriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMintriad(mapDegreeFromAugtriadToChromatic(degree));
    }

    private int mapDegreeFromAugtriadToDimtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDimtriad(mapDegreeFromAugtriadToChromatic(degree));
    }

    private int mapDegreeFromAugtriadToAugtriad(int degree) {
        return degree;
    }

    private int mapDegreeFromAugtriadToMinsecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinsecond(mapDegreeFromAugtriadToChromatic(degree));
    }

    private int mapDegreeFromAugtriadToSecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToSecond(mapDegreeFromAugtriadToChromatic(degree));
    }

    private int mapDegreeFromAugtriadToMinthird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinthird(mapDegreeFromAugtriadToChromatic(degree));
    }

    private int mapDegreeFromAugtriadToThird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToThird(mapDegreeFromAugtriadToChromatic(degree));
    }

    private int mapDegreeFromAugtriadToFourth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFourth(mapDegreeFromAugtriadToChromatic(degree));
    }

    private int mapDegreeFromAugtriadToTritone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToTritone(mapDegreeFromAugtriadToChromatic(degree));
    }

    private int mapDegreeFromAugtriadToFifth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFifth(mapDegreeFromAugtriadToChromatic(degree));
    }

    private int mapDegreeFromPentamajorToChromatic(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { return 3; }
        if ( degree == 3 ) { return 5; }
        if ( degree == 4 ) { return 8; }
        if ( degree == 5 ) { return 10; }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromPentamajorToDiminished(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDiminished(mapDegreeFromPentamajorToChromatic(degree));
    }

    private int mapDegreeFromPentamajorToIonian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToIonian(mapDegreeFromPentamajorToChromatic(degree));
    }

    private int mapDegreeFromPentamajorToDorian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDorian(mapDegreeFromPentamajorToChromatic(degree));
    }

    private int mapDegreeFromPentamajorToPhrygian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPhrygian(mapDegreeFromPentamajorToChromatic(degree));
    }

    private int mapDegreeFromPentamajorToLydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLydian(mapDegreeFromPentamajorToChromatic(degree));
    }

    private int mapDegreeFromPentamajorToMixolydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMixolydian(mapDegreeFromPentamajorToChromatic(degree));
    }

    private int mapDegreeFromPentamajorToAolean(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAolean(mapDegreeFromPentamajorToChromatic(degree));
    }

    private int mapDegreeFromPentamajorToLocrian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLocrian(mapDegreeFromPentamajorToChromatic(degree));
    }

    private int mapDegreeFromPentamajorToMajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajor(mapDegreeFromPentamajorToChromatic(degree));
    }

    private int mapDegreeFromPentamajorToMinor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinor(mapDegreeFromPentamajorToChromatic(degree));
    }

    private int mapDegreeFromPentamajorToWholetone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToWholetone(mapDegreeFromPentamajorToChromatic(degree));
    }

    private int mapDegreeFromPentamajorToBlues(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToBlues(mapDegreeFromPentamajorToChromatic(degree));
    }

    private int mapDegreeFromPentamajorToPentamajor(int degree) {
        return degree;
    }

    private int mapDegreeFromPentamajorToPentaminor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentaminor(mapDegreeFromPentamajorToChromatic(degree));
    }

    private int mapDegreeFromPentamajorToMaj7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMaj7th(mapDegreeFromPentamajorToChromatic(degree));
    }

    private int mapDegreeFromPentamajorToMin7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMin7th(mapDegreeFromPentamajorToChromatic(degree));
    }

    private int mapDegreeFromPentamajorToDim7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDim7th(mapDegreeFromPentamajorToChromatic(degree));
    }

    private int mapDegreeFromPentamajorToDom7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDom7th(mapDegreeFromPentamajorToChromatic(degree));
    }

    private int mapDegreeFromPentamajorToMajtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajtriad(mapDegreeFromPentamajorToChromatic(degree));
    }

    private int mapDegreeFromPentamajorToMintriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMintriad(mapDegreeFromPentamajorToChromatic(degree));
    }

    private int mapDegreeFromPentamajorToDimtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDimtriad(mapDegreeFromPentamajorToChromatic(degree));
    }

    private int mapDegreeFromPentamajorToAugtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAugtriad(mapDegreeFromPentamajorToChromatic(degree));
    }

    private int mapDegreeFromPentamajorToMinsecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinsecond(mapDegreeFromPentamajorToChromatic(degree));
    }

    private int mapDegreeFromPentamajorToSecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToSecond(mapDegreeFromPentamajorToChromatic(degree));
    }

    private int mapDegreeFromPentamajorToMinthird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinthird(mapDegreeFromPentamajorToChromatic(degree));
    }

    private int mapDegreeFromPentamajorToThird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToThird(mapDegreeFromPentamajorToChromatic(degree));
    }

    private int mapDegreeFromPentamajorToFourth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFourth(mapDegreeFromPentamajorToChromatic(degree));
    }

    private int mapDegreeFromPentamajorToTritone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToTritone(mapDegreeFromPentamajorToChromatic(degree));
    }

    private int mapDegreeFromPentamajorToFifth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFifth(mapDegreeFromPentamajorToChromatic(degree));
    }

    private int mapDegreeFromPentaminorToChromatic(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { return 4; }
        if ( degree == 3 ) { return 6; }
        if ( degree == 4 ) { return 8; }
        if ( degree == 5 ) { return 11; }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromPentaminorToDiminished(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDiminished(mapDegreeFromPentaminorToChromatic(degree));
    }

    private int mapDegreeFromPentaminorToIonian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToIonian(mapDegreeFromPentaminorToChromatic(degree));
    }

    private int mapDegreeFromPentaminorToDorian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDorian(mapDegreeFromPentaminorToChromatic(degree));
    }

    private int mapDegreeFromPentaminorToPhrygian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPhrygian(mapDegreeFromPentaminorToChromatic(degree));
    }

    private int mapDegreeFromPentaminorToLydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLydian(mapDegreeFromPentaminorToChromatic(degree));
    }

    private int mapDegreeFromPentaminorToMixolydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMixolydian(mapDegreeFromPentaminorToChromatic(degree));
    }

    private int mapDegreeFromPentaminorToAolean(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAolean(mapDegreeFromPentaminorToChromatic(degree));
    }

    private int mapDegreeFromPentaminorToLocrian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLocrian(mapDegreeFromPentaminorToChromatic(degree));
    }

    private int mapDegreeFromPentaminorToMajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajor(mapDegreeFromPentaminorToChromatic(degree));
    }

    private int mapDegreeFromPentaminorToMinor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinor(mapDegreeFromPentaminorToChromatic(degree));
    }

    private int mapDegreeFromPentaminorToWholetone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToWholetone(mapDegreeFromPentaminorToChromatic(degree));
    }

    private int mapDegreeFromPentaminorToBlues(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToBlues(mapDegreeFromPentaminorToChromatic(degree));
    }

    private int mapDegreeFromPentaminorToPentamajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentamajor(mapDegreeFromPentaminorToChromatic(degree));
    }

    private int mapDegreeFromPentaminorToPentaminor(int degree) {
        return degree;
    }

    private int mapDegreeFromPentaminorToMaj7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMaj7th(mapDegreeFromPentaminorToChromatic(degree));
    }

    private int mapDegreeFromPentaminorToMin7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMin7th(mapDegreeFromPentaminorToChromatic(degree));
    }

    private int mapDegreeFromPentaminorToDim7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDim7th(mapDegreeFromPentaminorToChromatic(degree));
    }

    private int mapDegreeFromPentaminorToDom7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDom7th(mapDegreeFromPentaminorToChromatic(degree));
    }

    private int mapDegreeFromPentaminorToMajtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajtriad(mapDegreeFromPentaminorToChromatic(degree));
    }

    private int mapDegreeFromPentaminorToMintriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMintriad(mapDegreeFromPentaminorToChromatic(degree));
    }

    private int mapDegreeFromPentaminorToDimtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDimtriad(mapDegreeFromPentaminorToChromatic(degree));
    }

    private int mapDegreeFromPentaminorToAugtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAugtriad(mapDegreeFromPentaminorToChromatic(degree));
    }

    private int mapDegreeFromPentaminorToMinsecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinsecond(mapDegreeFromPentaminorToChromatic(degree));
    }

    private int mapDegreeFromPentaminorToSecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToSecond(mapDegreeFromPentaminorToChromatic(degree));
    }

    private int mapDegreeFromPentaminorToMinthird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinthird(mapDegreeFromPentaminorToChromatic(degree));
    }

    private int mapDegreeFromPentaminorToThird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToThird(mapDegreeFromPentaminorToChromatic(degree));
    }

    private int mapDegreeFromPentaminorToFourth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFourth(mapDegreeFromPentaminorToChromatic(degree));
    }

    private int mapDegreeFromPentaminorToTritone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToTritone(mapDegreeFromPentaminorToChromatic(degree));
    }

    private int mapDegreeFromPentaminorToFifth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFifth(mapDegreeFromPentaminorToChromatic(degree));
    }

    private int mapDegreeFromIonianToChromatic(int degree) throws InvalidModeChangeException {
        if ( degree == 1 ) { return 1; }
        if ( degree == 2 ) { return 3; }
        if ( degree == 3 ) { return 5; }
        if ( degree == 4 ) { return 6; }
        if ( degree == 5 ) { return 8; }
        if ( degree == 6 ) { return 10; }
        if ( degree == 7 ) { return 12; }
        throw new InvalidModeChangeException();
    }

    private int mapDegreeFromIonianToDiminished(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDiminished(mapDegreeFromIonianToChromatic(degree));
    }

    private int mapDegreeFromIonianToIonian(int degree) throws InvalidModeChangeException {
        return degree;
    }

    private int mapDegreeFromIonianToDorian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDorian(mapDegreeFromIonianToChromatic(degree));
    }

    private int mapDegreeFromIonianToPhrygian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPhrygian(mapDegreeFromIonianToChromatic(degree));
    }

    private int mapDegreeFromIonianToLydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLydian(mapDegreeFromIonianToChromatic(degree));
    }

    private int mapDegreeFromIonianToMixolydian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMixolydian(mapDegreeFromIonianToChromatic(degree));
    }

    private int mapDegreeFromIonianToAolean(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAolean(mapDegreeFromIonianToChromatic(degree));
    }

    private int mapDegreeFromIonianToLocrian(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToLocrian(mapDegreeFromIonianToChromatic(degree));
    }

    private int mapDegreeFromIonianToMinor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinor(mapDegreeFromIonianToChromatic(degree));
    }

    private int mapDegreeFromIonianToWholetone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToWholetone(mapDegreeFromIonianToChromatic(degree));
    }

    private int mapDegreeFromIonianToBlues(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToBlues(mapDegreeFromIonianToChromatic(degree));
    }

    private int mapDegreeFromIonianToPentamajor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentamajor(mapDegreeFromIonianToChromatic(degree));
    }

    private int mapDegreeFromIonianToPentaminor(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToPentaminor(mapDegreeFromIonianToChromatic(degree));
    }

    private int mapDegreeFromIonianToMaj7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMaj7th(mapDegreeFromIonianToChromatic(degree));
    }

    private int mapDegreeFromIonianToMin7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMin7th(mapDegreeFromIonianToChromatic(degree));
    }

    private int mapDegreeFromIonianToDim7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDim7th(mapDegreeFromIonianToChromatic(degree));
    }

    private int mapDegreeFromIonianToDom7th(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDom7th(mapDegreeFromIonianToChromatic(degree));
    }

    private int mapDegreeFromIonianToMajtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMajtriad(mapDegreeFromIonianToChromatic(degree));
    }

    private int mapDegreeFromIonianToMintriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMintriad(mapDegreeFromIonianToChromatic(degree));
    }

    private int mapDegreeFromIonianToDimtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToDimtriad(mapDegreeFromIonianToChromatic(degree));
    }

    private int mapDegreeFromIonianToAugtriad(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToAugtriad(mapDegreeFromIonianToChromatic(degree));
    }

    private int mapDegreeFromIonianToMinsecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinsecond(mapDegreeFromIonianToChromatic(degree));
    }

    private int mapDegreeFromIonianToSecond(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToSecond(mapDegreeFromIonianToChromatic(degree));
    }

    private int mapDegreeFromIonianToMinthird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToMinthird(mapDegreeFromIonianToChromatic(degree));
    }

    private int mapDegreeFromIonianToThird(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToThird(mapDegreeFromIonianToChromatic(degree));
    }

    private int mapDegreeFromIonianToFourth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFourth(mapDegreeFromIonianToChromatic(degree));
    }

    private int mapDegreeFromIonianToTritone(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToTritone(mapDegreeFromIonianToChromatic(degree));
    }

    private int mapDegreeFromIonianToFifth(int degree) throws InvalidModeChangeException {
        return mapDegreeFromChromaticToFifth(mapDegreeFromIonianToChromatic(degree));
    }

    private int mapDegreeFromChromaticToMajor(int degree, Key csKey, Key nsKey) {
        // mcf.ta().append("got to mapdegreefromchromatictomajor with three parameters\n");
        return 1;
    }

    public void transferPropertiesTo(SilentNote n) {
        n.scale = scale;
        n.degree = degree;
        n.location = location;
        n.duration = duration;
        n.volume = volume;
        n.deltaVolume = deltaVolume;
        n.nrSecondsPerBeat = nrSecondsPerBeat;
        n.timbre = timbre;
        n.instrumentNumber = instrumentNumber;
        n.memory = memory;
        n.stackMode = stackMode;
        n.scaleStack = scaleStack;
    }

    public void presentStatshotIfDesired(String prefix) {
        if ( statshotFlag ) {
            String ss = statshot();
            int x = ss.indexOf(")");
            x = x+2;
            ss = prefix + " " + ss.substring(x);
            mcf.ta().append(ss+"\n");
        }
    }

    public void initStatsForStatshot() {
        rpCount = 0; lpCount = 0;
        pushscaleCount = 0; popscaleCount = 0;
        playCount = 0; restCount = 0;
        s2Count = 0; x2Count = 0;
        s3Count = 0; x3Count = 0;
        s5Count = 0; x5Count = 0;
        s7Count = 0; x7Count = 0;
    }

    boolean pcodeFlag = false;
    private String pcode = "";

    public void pcodeOn() {
        pcodeFlag = true;
        pcode = "";
    }

    public void pcodeOff() {
        pcodeFlag = false;
        mcf.ta().append("pcodepcode=" + pcode + "\n");
        // pcode = "";
    }

    public String pcode() {
        return pcode.trim();
    }

    public void addPcode(String command) {
        pcode = pcode + command + " ";
    }

    public String npvi() {
        String s = "";
        for ( Double d : durationSeq ) {
            s = s + d + " ";
        }
        s = s.trim();

        int m = durationSeq.size() - 1;
        double leftPart = ( 100 / ( m - 1 ) );
        double rightPart = 0.0;
        for ( int k = 1; k <= m; k++ ) {
            int x = k - 1;
            double durk = durationSeq.get(x);
            double durkp1 = durationSeq.get(x+1);
            // ta().append("k = " + k + " dur(k) = " + durk + " dur(k+1) = " + durkp1 + "\n");
            double top = durk - durkp1;
            double bottom = ( ( durk + durkp1 ) / 2 );
            double quotient = top / bottom;
            double absQuotient = Math.abs(quotient);
            // ta().append("absQuotient = " + absQuotient + "\n");
            rightPart = rightPart + absQuotient;
        }
        double npvi = leftPart * rightPart;
        return s + "\n" + npvi + "";
    }

}
