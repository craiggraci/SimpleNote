/*
 * Mode.java of the note package of the Music project.
 */

package note;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Model a mode in terms of a name and a sequence of intervals.
 * @author blue
 */
public class Mode {

    /**
     * Create a Mode object
     * @param n is the name of the mode
     * @param i is the defining sequence of intervals
     */
    public Mode(String n, int[] i) {
        name = n;
        intervals = i;
    }

    private String name;
    private int[] intervals;

    /**
     * Create a textual representation of the mode
     * @return a textual representation of the mode
     */
    @Override
    public String toString() {
        return "[Mode: name=" + name + " intervals=" + format(intervals) + "]";
    }

    protected String format(int[] intervals) {
        String buffer = "<";
        for ( int i = 0; i < intervals.length-1; i++ ) {
            buffer = buffer + intervals[i] + ",";
        }
        buffer = buffer + intervals[intervals.length-1] + ">";
        return buffer;
    }

    static private Map<String,Mode> nameModeMap;

    static public void establishNameModeMap() {
        nameModeMap = new HashMap();
        nameModeMap.put("CHROMATIC",CHROMATIC);
        nameModeMap.put("DIMINISHED",DIMINISHED);
        nameModeMap.put("WHOLETONE",WHOLETONE);
        nameModeMap.put("BLUES",BLUES);
        nameModeMap.put("PENTAMAJOR",PENTAMAJOR);
        nameModeMap.put("PENTAMINOR",PENTAMINOR);
        nameModeMap.put("MAJOR",MAJOR);
        nameModeMap.put("IONIAN",IONIAN);
        nameModeMap.put("DORIAN",DORIAN);
        nameModeMap.put("PHRYGIAN",PHRYGIAN);
        nameModeMap.put("LYDIAN",LYDIAN);
        nameModeMap.put("MIXOLYDIAN",MIXOLYDIAN);
        nameModeMap.put("MINOR",MINOR);
        nameModeMap.put("AOLEAN",AOLEAN);
        nameModeMap.put("LOCRIAN",LOCRIAN);
        nameModeMap.put("MAJTRIAD",MAJTRIAD);
        nameModeMap.put("MINTRIAD",MINTRIAD);
        nameModeMap.put("AUGTRIAD",AUGTRIAD);
        nameModeMap.put("DIMTRIAD",DIMTRIAD);
        nameModeMap.put("MAJ7TH",MAJ7TH);
        nameModeMap.put("MIN7TH",MIN7TH);
        nameModeMap.put("DIM7TH",DIM7TH);
        nameModeMap.put("DOM7TH",DOM7TH);
        nameModeMap.put("MINSECOND",MINSECOND);
        nameModeMap.put("SECOND",SECOND);
        nameModeMap.put("MINTHIRD",MINTHIRD);
        nameModeMap.put("THIRD",THIRD);
        nameModeMap.put("FOURTH",FOURTH);
        nameModeMap.put("TRITONE",TRITONE);
        nameModeMap.put("FIFTH",FIFTH);
    }

    public static boolean isModeName(String command) {
        return ( nameModeMap.get(command) != null );
    }

    public static Mode computeModeFromName(String command) {
        return nameModeMap.get(command);
    }

    /**
     * Reference the name of the mode.
     * @return the name of the mode
     */
    public String name() { return name; }

    /**
     * Reference the sequence of intervals that defines the mode.
     * @return the sequence of intervals that defines the mode
     */
    public int[] intervals() { return intervals; }

    /**
     * Compare two modes for equality.
     * @param m - mode to which the dispatching mode is compared.
     * @return true/false if the two modes are/aren't equal
     */
    public boolean equals(Mode m) {
        return name.equalsIgnoreCase(m.name());
    }

    protected int lastInterval() {
        return intervals[intervals.length-1];
    }

    // Seven note scales

    static private int[] major = {2,2,1,2,2,2,1};
    /**
     * Seven note scale with interval sequence = <2,2,1,2,2,2,1>.
     * Same as IONIAN
     */
    static public final Mode MAJOR = new Mode("MAJOR",major);

    static private int[] ionian = {2,2,1,2,2,2,1};
    /**
     * Seven note scale with interval sequence = <2,2,1,2,2,2,1>.  Same
     * as MAJOR.
     */
    static public final Mode IONIAN = new Mode("IONIAN",ionian);

    static private int[] dorian = {2,1,2,2,2,1,2};
    /**
     * Seven note scale with interval sequence = <2,1,2,2,2,1,2>.
     */
    static public final Mode DORIAN = new Mode("DORIAN",dorian);

    static int[] phrygian = {1,2,2,2,1,2,2};
    /**
     * Seven note scale with interval sequence = <1,2,2,2,1,2,2>.
     */
    static public final Mode PHRYGIAN = new Mode("PHRYGIAN",phrygian);

    static int[] lydian = {2,2,2,1,2,2,1};
    /**
     * Seven note scale with interval sequence = <2,2,2,1,2,2,1>.
     */
    static public final Mode LYDIAN = new Mode("LYDIAN",lydian);

    static int[] mixolydian = {2,2,1,2,2,1,2};
    /**
     * Seven note scale with interval sequence = <2,2,1,2,2,1,2>.
     */
    static public final Mode MIXOLYDIAN = new Mode("MIXOLYDIAN",mixolydian);

    static int[] minor = {2,1,2,2,1,2,2};
    /**
     * Seven note scale with interval sequence = <2,1,2,2,1,2,2>.  Same as
     * AOLEAN.
     */
    static public final Mode MINOR = new Mode("MINOR",minor);

    static int[] aolean = {2,1,2,2,1,2,2};
    /**
     * Seven note scale with interval sequence = <2,1,2,2,1,2,2>.  Same as
     * MINOR.
     */
    static public final Mode AOLEAN = new Mode("AOLEAN",aolean);

    static int[] locrian = {1,2,2,1,2,2,2};
    /**
     * Seven note scale with interval sequence = <1,2,2,1,2,2,2>.
     */
    static public final Mode LOCRIAN = new Mode("LOCRIAN",locrian);

    // Other scales

    static int[] wholetone = {2,2,2,2,2,2};
    /**
     * Six note scale with interval sequence = <2,2,2,2,2,2>.
     */
    static public final Mode WHOLETONE = new Mode("WHOLETONE",wholetone);

    static int[] blues = {3,2,1,1,3,2};
    /**
     * Six note scale with interval sequence = <3,2,1,1,3,2>.
     */
    static public final Mode BLUES = new Mode("BLUES",blues);

    static int[] pentaMajor = {2,2,3,2,3};
    /**
     * Five note scale with interval sequence = <2,2,3,2,3>.
     */
    static public final Mode PENTAMAJOR = new Mode("PENTAMAJOR",pentaMajor);

    static int[] pentaMinor = {3,2,2,3,2};
    /**
     * Five note scale with interval sequence = <3,2,2,3,2>.
     */
    static public final Mode PENTAMINOR = new Mode("PENTAMINOR",pentaMinor);

    static int[] chromatic = {1,1,1,1,1,1,1,1,1,1,1,1};
    /**
     * Twelve note scale with interval sequence = <1,1,1,1,1,1,1,1,1,1,1,1>.
     */
    static public final Mode CHROMATIC = new Mode("CHROMATIC",chromatic);

    static int[] diminished = {2,1,2,1,2,1,2,1};
    /**
     * Eight note scale with interval sequence = <2,1,2,1,2,1,2,1>.
     */
    static public final Mode DIMINISHED = new Mode("DIMINISHED",diminished);

    static int[] major_triad = {4,3,5};
    /**
     * Three note scale with interval sequence = <4,3,5>.
     */
    static public final Mode MAJTRIAD = new Mode("MAJTRIAD",major_triad);

    static int[] minor_triad = {3,4,5};
    /**
     * Three note scale with interval sequence = <3,4,5>.
     */
    static public final Mode MINTRIAD = new Mode("MINTRIAD",minor_triad);

    static int[] diminished_triad = {3,3,6};
    /**
     * Three note scale with interval sequence = <3,3,6>.
     */
    static public final Mode DIMTRIAD = new Mode("DIMTRIAD",diminished_triad);

    static int[] augmented_triad = {4,4,4};
    /**
     * Three note scale with interval sequence = <4,4,4>.
     */
    static public final Mode AUGTRIAD = new Mode("AUGTRIAD",augmented_triad);

    static int[] dominant_seventh = {4,3,3,2};
    /**
     * Four note scale with interval sequence = <4,3,3,2>.
     */
    static public final Mode DOM7TH = new Mode("DOM7TH",dominant_seventh);

    static int[] major_seventh = {4,3,4,1};
    /**
     * Four note scale with interval sequence = <4,3,4,1>.
     */
    static public final Mode MAJ7TH = new Mode("MAJ7TH",major_seventh);

    static int[] minor_seventh = {3,4,3,2};
    /**
     * Four note scale with interval sequence = <3,4,3,2>.
     */
    static public final Mode MIN7TH = new Mode("MIN7TH",minor_seventh);

    static int[] diminished_seventh = {3,3,3,3};
    /**
     * Four note scale with interval sequence = <3,3,3,3>.
     */
    static public final Mode DIM7TH = new Mode("DIM7TH",diminished_seventh);

    static int[] minor_second = {1,11};
    /**
     * Two note scale with interval sequence = <1,11>.
     */
    static public final Mode MINSECOND = new Mode("MINSECOND",minor_second);

    static int[] second = {2,10};
    /**
     * Two note scale with interval sequence = <2,10>.
     */
    static public final Mode SECOND = new Mode("SECOND",second);

    static int[] minor_third = {3,9};
    /**
     * Two note scale with interval sequence = <3,9>.
     */
    static public final Mode MINTHIRD = new Mode("MINTHIRD",minor_third);

    static int[] third = {4,8};
    /**
     * Two note scale with interval sequence = <4,8>.
     */
    static public final Mode THIRD = new Mode("THIRD",third);

    static int[] fourth = {5,7};
    /**
     * Two note scale with interval sequence = <5,7>.
     */
    static public final Mode FOURTH = new Mode("FOURTH",fourth);

    static int[] tritone = {6,6};
    /**
     * Two note scale with interval sequence = <6,6>.
     */
    static public final Mode TRITONE = new Mode("TRITONE",tritone);

    static int[] fifth = {7,5};
    /**
     * Two note scale with interval sequence = <7,5>.
     */
    static public final Mode FIFTH = new Mode("FIFTH",fifth);

    static private void establishModeNames() {
        modes = new ArrayList<String>();
        modes.add("CHROMATIC");
        modes.add("DIMINISHED");
        modes.add("WHOLETONE");
        modes.add("BLUES");
        modes.add("PENTAMAJOR");
        modes.add("PENTAMINOR");
        modes.add("MAJOR");
        modes.add("IONIAN");
        modes.add("DORIAN");
        modes.add("PHRYGIAN");
        modes.add("LYDIAN");
        modes.add("MIXOLYDIAN");
        modes.add("MINOR");
        modes.add("AOLEAN");
        modes.add("LOCRIAN");
        modes.add("MAJTRIAD");
        modes.add("MINTRIAD");
        modes.add("AUGTRIAD");
        modes.add("DIMTRIAD");
        modes.add("MAJ7TH");
        modes.add("MIN7TH");
        modes.add("DIM7TH");
        modes.add("DOM7TH");
        modes.add("MINSECOND");
        modes.add("SECOND");
        modes.add("MINTHIRD");
        modes.add("THIRD");
        modes.add("FOURTH");
        modes.add("TRITONE");
        modes.add("FIFTH");
    }

    static public ArrayList<String> modes;

    /**
     * display the names of all known modes to the standard output stream
     */
    static public void list() {
        establishModeNames();
        for ( String n : modes ) {
            System.out.println(n);
        }
    }

}
