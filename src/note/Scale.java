/*
 * Scale.java of the note package of the Music project.
 */

package note;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Modeling a scale in terms of a key and a mode.
 * @author blue
 */
public class Scale {

    static private Map<String,Scale> nameScaleMap;

    static public void establishNameScaleMap() {
        nameScaleMap = new HashMap();

        nameScaleMap.put("C-CHROMATIC",C_CHROMATIC);
        nameScaleMap.put("C-DIMINISHED",C_DIMINISHED);
        nameScaleMap.put("C-IONIAN",C_IONIAN);
        nameScaleMap.put("C-DORIAN",C_DORIAN);
        nameScaleMap.put("C-PHRYGIAN",C_PHRYGIAN);
        nameScaleMap.put("C-LYDIAN",C_LYDIAN);
        nameScaleMap.put("C-MIXOLYDIAN",C_MIXOLYDIAN);
        nameScaleMap.put("C-AOLEAN",C_AOLEAN);
        nameScaleMap.put("C-LOCRIAN",C_LOCRIAN);
        nameScaleMap.put("C-MAJOR",C_MAJOR);
        nameScaleMap.put("C-MINOR",C_MINOR);
        nameScaleMap.put("C-WHOLETONE",C_WHOLETONE);
        nameScaleMap.put("C-BLUES",C_BLUES);
        nameScaleMap.put("C-PENTAMAJOR",C_PENTAMAJOR);
        nameScaleMap.put("C-PENTAMINOR",C_PENTAMINOR);
        nameScaleMap.put("C-DOM7TH",C_DOM7TH);
        nameScaleMap.put("C-DIM7TH",C_DIM7TH);
        nameScaleMap.put("C-MIN7TH",C_MIN7TH);
        nameScaleMap.put("C-MAJ7TH",C_MAJ7TH);
        nameScaleMap.put("C-AUGTRIAD",C_AUGTRIAD);
        nameScaleMap.put("C-DIMTRIAD",C_DIMTRIAD);
        nameScaleMap.put("C-MINTRIAD",C_MINTRIAD);
        nameScaleMap.put("C-MAJTRIAD",C_MAJTRIAD);
        nameScaleMap.put("C-MINSECOND",C_MINSECOND);
        nameScaleMap.put("C-SECOND",C_SECOND);
        nameScaleMap.put("C-MINTHIRD",C_MINTHIRD);
        nameScaleMap.put("C-THIRD",C_THIRD);
        nameScaleMap.put("C-FOURTH",C_FOURTH);
        nameScaleMap.put("C-TRITONE",C_TRITONE);
        nameScaleMap.put("C-FIFTH",C_FIFTH);

        nameScaleMap.put("X-CHROMATIC", X_CHROMATIC);
        nameScaleMap.put("X-DIMINISHED",X_DIMINISHED);
        nameScaleMap.put("X-IONIAN",    X_IONIAN);
        nameScaleMap.put("X-DORIAN",    X_DORIAN);
        nameScaleMap.put("X-PHRYGIAN",  X_PHRYGIAN);
        nameScaleMap.put("X-LYDIAN",    X_LYDIAN);
        nameScaleMap.put("X-MIXOLYDIAN",X_MIXOLYDIAN);
        nameScaleMap.put("X-AOLEAN",    X_AOLEAN);
        nameScaleMap.put("X-LOCRIAN",   X_LOCRIAN);
        nameScaleMap.put("X-MAJOR",     X_MAJOR);
        nameScaleMap.put("X-MINOR",     X_MINOR);
        nameScaleMap.put("X-WHOLETONE", X_WHOLETONE);
        nameScaleMap.put("X-BLUES",     X_BLUES);
        nameScaleMap.put("X-PENTAMAJOR",X_PENTAMAJOR);
        nameScaleMap.put("X-PENTAMINOR",X_PENTAMINOR);
        nameScaleMap.put("X-DOM7TH",    X_DOM7TH);
        nameScaleMap.put("X-DIM7TH",    X_DIM7TH);
        nameScaleMap.put("X-MIN7TH",    X_MIN7TH);
        nameScaleMap.put("X-MAJ7TH",    X_MAJ7TH);
        nameScaleMap.put("X-AUGTRIAD",  X_AUGTRIAD);
        nameScaleMap.put("X-DIMTRIAD",  X_DIMTRIAD);
        nameScaleMap.put("X-MINTRIAD",  X_MINTRIAD);
        nameScaleMap.put("X-MAJTRIAD",  X_MAJTRIAD);
        nameScaleMap.put("X-MINSECOND", X_MINSECOND);
        nameScaleMap.put("X-SECOND",    X_SECOND);
        nameScaleMap.put("X-MINTHIRD",  X_MINTHIRD);
        nameScaleMap.put("X-THIRD",  X_THIRD);
        nameScaleMap.put("X-FOURTH",    X_FOURTH);
        nameScaleMap.put("X-TRITONE",   X_TRITONE);
        nameScaleMap.put("X-FIFTH",     X_FIFTH);

        nameScaleMap.put("D-CHROMATIC", D_CHROMATIC);
        nameScaleMap.put("D-DIMINISHED",D_DIMINISHED);
        nameScaleMap.put("D-IONIAN",    D_IONIAN);
        nameScaleMap.put("D-DORIAN",    D_DORIAN);
        nameScaleMap.put("D-PHRYGIAN",  D_PHRYGIAN);
        nameScaleMap.put("D-LYDIAN",    D_LYDIAN);
        nameScaleMap.put("D-MIXOLYDIAN",D_MIXOLYDIAN);
        nameScaleMap.put("D-AOLEAN",    D_AOLEAN);
        nameScaleMap.put("D-LOCRIAN",   D_LOCRIAN);
        nameScaleMap.put("D-MAJOR",     D_MAJOR);
        nameScaleMap.put("D-MINOR",     D_MINOR);
        nameScaleMap.put("D-WHOLETONE", D_WHOLETONE);
        nameScaleMap.put("D-BLUES",     D_BLUES);
        nameScaleMap.put("D-PENTAMAJOR",D_PENTAMAJOR);
        nameScaleMap.put("D-PENTAMINOR",D_PENTAMINOR);
        nameScaleMap.put("D-DOM7TH",    D_DOM7TH);
        nameScaleMap.put("D-DIM7TH",    D_DIM7TH);
        nameScaleMap.put("D-MIN7TH",    D_MIN7TH);
        nameScaleMap.put("D-MAJ7TH",    D_MAJ7TH);
        nameScaleMap.put("D-AUGTRIAD",  D_AUGTRIAD);
        nameScaleMap.put("D-DIMTRIAD",  D_DIMTRIAD);
        nameScaleMap.put("D-MINTRIAD",  D_MINTRIAD);
        nameScaleMap.put("D-MAJTRIAD",  D_MAJTRIAD);
        nameScaleMap.put("D-MINSECOND", D_MINSECOND);
        nameScaleMap.put("D-SECOND",    D_SECOND);
        nameScaleMap.put("D-MINTHIRD",  D_MINTHIRD);
        nameScaleMap.put("D-THIRD",  D_THIRD);
        nameScaleMap.put("D-FOURTH",    D_FOURTH);
        nameScaleMap.put("D-TRITONE",   D_TRITONE);
        nameScaleMap.put("D-FIFTH",     D_FIFTH);

        nameScaleMap.put("Y-CHROMATIC", Y_CHROMATIC);
        nameScaleMap.put("Y-DIMINISHED",Y_DIMINISHED);
        nameScaleMap.put("Y-IONIAN",    Y_IONIAN);
        nameScaleMap.put("Y-DORIAN",    Y_DORIAN);
        nameScaleMap.put("Y-PHRYGIAN",  Y_PHRYGIAN);
        nameScaleMap.put("Y-LYDIAN",    Y_LYDIAN);
        nameScaleMap.put("Y-MIXOLYDIAN",Y_MIXOLYDIAN);
        nameScaleMap.put("Y-AOLEAN",    Y_AOLEAN);
        nameScaleMap.put("Y-LOCRIAN",   Y_LOCRIAN);
        nameScaleMap.put("Y-MAJOR",     Y_MAJOR);
        nameScaleMap.put("Y-MINOR",     Y_MINOR);
        nameScaleMap.put("Y-WHOLETONE", Y_WHOLETONE);
        nameScaleMap.put("Y-BLUES",     Y_BLUES);
        nameScaleMap.put("Y-PENTAMAJOR",Y_PENTAMAJOR);
        nameScaleMap.put("Y-PENTAMINOR",Y_PENTAMINOR);
        nameScaleMap.put("Y-DOM7TH",    Y_DOM7TH);
        nameScaleMap.put("Y-DIM7TH",    Y_DIM7TH);
        nameScaleMap.put("Y-MIN7TH",    Y_MIN7TH);
        nameScaleMap.put("Y-MAJ7TH",    Y_MAJ7TH);
        nameScaleMap.put("Y-AUGTRIAD",  Y_AUGTRIAD);
        nameScaleMap.put("Y-DIMTRIAD",  Y_DIMTRIAD);
        nameScaleMap.put("Y-MINTRIAD",  Y_MINTRIAD);
        nameScaleMap.put("Y-MAJTRIAD",  Y_MAJTRIAD);
        nameScaleMap.put("Y-MINSECOND", Y_MINSECOND);
        nameScaleMap.put("Y-SECOND",    Y_SECOND);
        nameScaleMap.put("Y-MINTHIRD",  Y_MINTHIRD);
        nameScaleMap.put("Y-THIRD",  Y_THIRD);
        nameScaleMap.put("Y-FOURTH",    Y_FOURTH);
        nameScaleMap.put("Y-TRITONE",   Y_TRITONE);
        nameScaleMap.put("Y-FIFTH",     Y_FIFTH);

        nameScaleMap.put("E-CHROMATIC", E_CHROMATIC);
        nameScaleMap.put("E-DIMINISHED",E_DIMINISHED);
        nameScaleMap.put("E-IONIAN",    E_IONIAN);
        nameScaleMap.put("E-DORIAN",    E_DORIAN);
        nameScaleMap.put("E-PHRYGIAN",  E_PHRYGIAN);
        nameScaleMap.put("E-LYDIAN",    E_LYDIAN);
        nameScaleMap.put("E-MIXOLYDIAN",E_MIXOLYDIAN);
        nameScaleMap.put("E-AOLEAN",    E_AOLEAN);
        nameScaleMap.put("E-LOCRIAN",   E_LOCRIAN);
        nameScaleMap.put("E-MAJOR",     E_MAJOR);
        nameScaleMap.put("E-MINOR",     E_MINOR);
        nameScaleMap.put("E-WHOLETONE", E_WHOLETONE);
        nameScaleMap.put("E-BLUES",     E_BLUES);
        nameScaleMap.put("E-PENTAMAJOR",E_PENTAMAJOR);
        nameScaleMap.put("E-PENTAMINOR",E_PENTAMINOR);
        nameScaleMap.put("E-DOM7TH",    E_DOM7TH);
        nameScaleMap.put("E-DIM7TH",    E_DIM7TH);
        nameScaleMap.put("E-MIN7TH",    E_MIN7TH);
        nameScaleMap.put("E-MAJ7TH",    E_MAJ7TH);
        nameScaleMap.put("E-AUGTRIAD",  E_AUGTRIAD);
        nameScaleMap.put("E-DIMTRIAD",  E_DIMTRIAD);
        nameScaleMap.put("E-MINTRIAD",  E_MINTRIAD);
        nameScaleMap.put("E-MAJTRIAD",  E_MAJTRIAD);
        nameScaleMap.put("E-MINSECOND", E_MINSECOND);
        nameScaleMap.put("E-SECOND",    E_SECOND);
        nameScaleMap.put("E-MINTHIRD",  E_MINTHIRD);
        nameScaleMap.put("E-THIRD",  E_THIRD);
        nameScaleMap.put("E-FOURTH",    E_FOURTH);
        nameScaleMap.put("E-TRITONE",   E_TRITONE);
        nameScaleMap.put("E-FIFTH",     E_FIFTH);

        nameScaleMap.put("F-CHROMATIC", F_CHROMATIC);
        nameScaleMap.put("F-DIMINISHED",F_DIMINISHED);
        nameScaleMap.put("F-IONIAN",    F_IONIAN);
        nameScaleMap.put("F-DORIAN",    F_DORIAN);
        nameScaleMap.put("F-PHRYGIAN",  F_PHRYGIAN);
        nameScaleMap.put("F-LYDIAN",    F_LYDIAN);
        nameScaleMap.put("F-MIXOLYDIAN",F_MIXOLYDIAN);
        nameScaleMap.put("F-AOLEAN",    F_AOLEAN);
        nameScaleMap.put("F-LOCRIAN",   F_LOCRIAN);
        nameScaleMap.put("F-MAJOR",     F_MAJOR);
        nameScaleMap.put("F-MINOR",     F_MINOR);
        nameScaleMap.put("F-WHOLETONE", F_WHOLETONE);
        nameScaleMap.put("F-BLUES",     F_BLUES);
        nameScaleMap.put("F-PENTAMAJOR",F_PENTAMAJOR);
        nameScaleMap.put("F-PENTAMINOR",F_PENTAMINOR);
        nameScaleMap.put("F-DOM7TH",    F_DOM7TH);
        nameScaleMap.put("F-DIM7TH",    F_DIM7TH);
        nameScaleMap.put("F-MIN7TH",    F_MIN7TH);
        nameScaleMap.put("F-MAJ7TH",    F_MAJ7TH);
        nameScaleMap.put("F-AUGTRIAD",  F_AUGTRIAD);
        nameScaleMap.put("F-DIMTRIAD",  F_DIMTRIAD);
        nameScaleMap.put("F-MINTRIAD",  F_MINTRIAD);
        nameScaleMap.put("F-MAJTRIAD",  F_MAJTRIAD);
        nameScaleMap.put("F-MINSECOND", F_MINSECOND);
        nameScaleMap.put("F-SECOND",    F_SECOND);
        nameScaleMap.put("F-MINTHIRD",  F_MINTHIRD);
        nameScaleMap.put("F-THRID",  F_THIRD);
        nameScaleMap.put("F-FOURTH",    F_FOURTH);
        nameScaleMap.put("F-TRITONE",   F_TRITONE);
        nameScaleMap.put("F-FIFTH",     F_FIFTH);

        nameScaleMap.put("Z-CHROMATIC", Z_CHROMATIC);
        nameScaleMap.put("Z-DIMINISHED",Z_DIMINISHED);
        nameScaleMap.put("Z-IONIAN",    Z_IONIAN);
        nameScaleMap.put("Z-DORIAN",    Z_DORIAN);
        nameScaleMap.put("Z-PHRYGIAN",  Z_PHRYGIAN);
        nameScaleMap.put("Z-LYDIAN",    Z_LYDIAN);
        nameScaleMap.put("Z-MIXOLYDIAN",Z_MIXOLYDIAN);
        nameScaleMap.put("Z-AOLEAN",    Z_AOLEAN);
        nameScaleMap.put("Z-LOCRIAN",   Z_LOCRIAN);
        nameScaleMap.put("Z-MAJOR",     Z_MAJOR);
        nameScaleMap.put("Z-MINOR",     Z_MINOR);
        nameScaleMap.put("Z-WHOLETONE", Z_WHOLETONE);
        nameScaleMap.put("Z-BLUES",     Z_BLUES);
        nameScaleMap.put("Z-PENTAMAJOR",Z_PENTAMAJOR);
        nameScaleMap.put("Z-PENTAMINOR",Z_PENTAMINOR);
        nameScaleMap.put("Z-DOM7TH",    Z_DOM7TH);
        nameScaleMap.put("Z-DIM7TH",    Z_DIM7TH);
        nameScaleMap.put("Z-MIN7TH",    Z_MIN7TH);
        nameScaleMap.put("Z-MAJ7TH",    Z_MAJ7TH);
        nameScaleMap.put("Z-AUGTRIAD",  Z_AUGTRIAD);
        nameScaleMap.put("Z-DIMTRIAD",  Z_DIMTRIAD);
        nameScaleMap.put("Z-MINTRIAD",  Z_MINTRIAD);
        nameScaleMap.put("Z-MAJTRIAD",  Z_MAJTRIAD);
        nameScaleMap.put("Z-MINSECOND", Z_MINSECOND);
        nameScaleMap.put("Z-SECOND",    Z_SECOND);
        nameScaleMap.put("Z-MINTHIRD",  Z_MINTHIRD);
        nameScaleMap.put("Z-THIRD",  Z_THIRD);
        nameScaleMap.put("Z-FOURTH",    Z_FOURTH);
        nameScaleMap.put("Z-TRITONE",   Z_TRITONE);
        nameScaleMap.put("Z-FIFTH",     Z_FIFTH);

        nameScaleMap.put("G-CHROMATIC", G_CHROMATIC);
        nameScaleMap.put("G-DIMINISHED",G_DIMINISHED);
        nameScaleMap.put("G-IONIAN",    G_IONIAN);
        nameScaleMap.put("G-DORIAN",    G_DORIAN);
        nameScaleMap.put("G-PHRYGIAN",  G_PHRYGIAN);
        nameScaleMap.put("G-LYDIAN",    G_LYDIAN);
        nameScaleMap.put("G-MIXOLYDIAN",G_MIXOLYDIAN);
        nameScaleMap.put("G-AOLEAN",    G_AOLEAN);
        nameScaleMap.put("G-LOCRIAN",   G_LOCRIAN);
        nameScaleMap.put("G-MAJOR",     G_MAJOR);
        nameScaleMap.put("G-MINOR",     G_MINOR);
        nameScaleMap.put("G-WHOLETONE", G_WHOLETONE);
        nameScaleMap.put("G-BLUES",     G_BLUES);
        nameScaleMap.put("G-PENTAMAJOR",G_PENTAMAJOR);
        nameScaleMap.put("G-PENTAMINOR",G_PENTAMINOR);
        nameScaleMap.put("G-DOM7TH",    G_DOM7TH);
        nameScaleMap.put("G-DIM7TH",    G_DIM7TH);
        nameScaleMap.put("G-MIN7TH",    G_MIN7TH);
        nameScaleMap.put("G-MAJ7TH",    G_MAJ7TH);
        nameScaleMap.put("G-AUGTRIAD",  G_AUGTRIAD);
        nameScaleMap.put("G-DIMTRIAD",  G_DIMTRIAD);
        nameScaleMap.put("G-MINTRIAD",  G_MINTRIAD);
        nameScaleMap.put("G-MAJTRIAD",  G_MAJTRIAD);
        nameScaleMap.put("G-MINSECOND", G_MINSECOND);
        nameScaleMap.put("G-SECOND",    G_SECOND);
        nameScaleMap.put("G-MINTHIRD",  G_MINTHIRD);
        nameScaleMap.put("G-THIRD",  G_THIRD);
        nameScaleMap.put("G-FOURTH",    G_FOURTH);
        nameScaleMap.put("G-TRITONE",   G_TRITONE);
        nameScaleMap.put("G-FIFTH",     G_FIFTH);

        nameScaleMap.put("V-CHROMATIC", V_CHROMATIC);
        nameScaleMap.put("V-DIMINISHED",V_DIMINISHED);
        nameScaleMap.put("V-IONIAN",    V_IONIAN);
        nameScaleMap.put("V-DORIAN",    V_DORIAN);
        nameScaleMap.put("V-PHRYGIAN",  V_PHRYGIAN);
        nameScaleMap.put("V-LYDIAN",    V_LYDIAN);
        nameScaleMap.put("V-MIXOLYDIAN",V_MIXOLYDIAN);
        nameScaleMap.put("V-AOLEAN",    V_AOLEAN);
        nameScaleMap.put("V-LOCRIAN",   V_LOCRIAN);
        nameScaleMap.put("V-MAJOR",     V_MAJOR);
        nameScaleMap.put("V-MINOR",     V_MINOR);
        nameScaleMap.put("V-WHOLETONE", V_WHOLETONE);
        nameScaleMap.put("V-BLUES",     V_BLUES);
        nameScaleMap.put("V-PENTAMAJOR",V_PENTAMAJOR);
        nameScaleMap.put("V-PENTAMINOR",V_PENTAMINOR);
        nameScaleMap.put("V-DOM7TH",    V_DOM7TH);
        nameScaleMap.put("V-DIM7TH",    V_DIM7TH);
        nameScaleMap.put("V-MIN7TH",    V_MIN7TH);
        nameScaleMap.put("V-MAJ7TH",    V_MAJ7TH);
        nameScaleMap.put("V-AUGTRIAD",  V_AUGTRIAD);
        nameScaleMap.put("V-DIMTRIAD",  V_DIMTRIAD);
        nameScaleMap.put("V-MINTRIAD",  V_MINTRIAD);
        nameScaleMap.put("V-MAJTRIAD",  V_MAJTRIAD);
        nameScaleMap.put("V-MINSECOND", V_MINSECOND);
        nameScaleMap.put("V-SECOND",    V_SECOND);
        nameScaleMap.put("V-MINTHIRD",  V_MINTHIRD);
        nameScaleMap.put("V-THIRD",  V_THIRD);
        nameScaleMap.put("V-FOURTH",    V_FOURTH);
        nameScaleMap.put("V-TRITONE",   V_TRITONE);
        nameScaleMap.put("V-FIFTH",     V_FIFTH);

        nameScaleMap.put("A-CHROMATIC", A_CHROMATIC);
        nameScaleMap.put("A-DIMINISHED",A_DIMINISHED);
        nameScaleMap.put("A-IONIAN",    A_IONIAN);
        nameScaleMap.put("A-DORIAN",    A_DORIAN);
        nameScaleMap.put("A-PHRYGIAN",  A_PHRYGIAN);
        nameScaleMap.put("A-LYDIAN",    A_LYDIAN);
        nameScaleMap.put("A-MIXOLYDIAN",A_MIXOLYDIAN);
        nameScaleMap.put("A-AOLEAN",    A_AOLEAN);
        nameScaleMap.put("A-LOCRIAN",   A_LOCRIAN);
        nameScaleMap.put("A-MAJOR",     A_MAJOR);
        nameScaleMap.put("A-MINOR",     A_MINOR);
        nameScaleMap.put("A-WHOLETONE", A_WHOLETONE);
        nameScaleMap.put("A-BLUES",     A_BLUES);
        nameScaleMap.put("A-PENTAMAJOR",A_PENTAMAJOR);
        nameScaleMap.put("A-PENTAMINOR",A_PENTAMINOR);
        nameScaleMap.put("A-DOM7TH",    A_DOM7TH);
        nameScaleMap.put("A-DIM7TH",    A_DIM7TH);
        nameScaleMap.put("A-MIN7TH",    A_MIN7TH);
        nameScaleMap.put("A-MAJ7TH",    A_MAJ7TH);
        nameScaleMap.put("A-AUGTRIAD",  A_AUGTRIAD);
        nameScaleMap.put("A-DIMTRIAD",  A_DIMTRIAD);
        nameScaleMap.put("A-MINTRIAD",  A_MINTRIAD);
        nameScaleMap.put("A-MAJTRIAD",  A_MAJTRIAD);
        nameScaleMap.put("A-MINSECOND", A_MINSECOND);
        nameScaleMap.put("A-SECOND",    A_SECOND);
        nameScaleMap.put("A-MINTHIRD",  A_MINTHIRD);
        nameScaleMap.put("A-THIRD",  A_THIRD);
        nameScaleMap.put("A-FOURTH",    A_FOURTH);
        nameScaleMap.put("A-TRITONE",   A_TRITONE);
        nameScaleMap.put("A-FIFTH",     A_FIFTH);

        nameScaleMap.put("W-CHROMATIC", W_CHROMATIC);
        nameScaleMap.put("W-DIMINISHED",W_DIMINISHED);
        nameScaleMap.put("W-IONIAN",    W_IONIAN);
        nameScaleMap.put("W-DORIAN",    W_DORIAN);
        nameScaleMap.put("W-PHRYGIAN",  W_PHRYGIAN);
        nameScaleMap.put("W-LYDIAN",    W_LYDIAN);
        nameScaleMap.put("W-MIXOLYDIAN",W_MIXOLYDIAN);
        nameScaleMap.put("W-AOLEAN",    W_AOLEAN);
        nameScaleMap.put("W-LOCRIAN",   W_LOCRIAN);
        nameScaleMap.put("W-MAJOR",     W_MAJOR);
        nameScaleMap.put("W-MINOR",     W_MINOR);
        nameScaleMap.put("W-WHOLETONE", W_WHOLETONE);
        nameScaleMap.put("W-BLUES",     W_BLUES);
        nameScaleMap.put("W-PENTAMAJOR",W_PENTAMAJOR);
        nameScaleMap.put("W-PENTAMINOR",W_PENTAMINOR);
        nameScaleMap.put("W-DOM7TH",    W_DOM7TH);
        nameScaleMap.put("W-DIM7TH",    W_DIM7TH);
        nameScaleMap.put("W-MIN7TH",    W_MIN7TH);
        nameScaleMap.put("W-MAJ7TH",    W_MAJ7TH);
        nameScaleMap.put("W-AUGTRIAD",  W_AUGTRIAD);
        nameScaleMap.put("W-DIMTRIAD",  W_DIMTRIAD);
        nameScaleMap.put("W-MINTRIAD",  W_MINTRIAD);
        nameScaleMap.put("W-MAJTRIAD",  W_MAJTRIAD);
        nameScaleMap.put("W-MINSECOND", W_MINSECOND);
        nameScaleMap.put("W-SECOND",    W_SECOND);
        nameScaleMap.put("W-MINTHIRD",  W_MINTHIRD);
        nameScaleMap.put("W-THIRD",  W_THIRD);
        nameScaleMap.put("W-FOURTH",    W_FOURTH);
        nameScaleMap.put("W-TRITONE",   W_TRITONE);
        nameScaleMap.put("W-FIFTH",     W_FIFTH);

        nameScaleMap.put("B-CHROMATIC", B_CHROMATIC);
        nameScaleMap.put("B-DIMINISHED",B_DIMINISHED);
        nameScaleMap.put("B-IONIAN",    B_IONIAN);
        nameScaleMap.put("B-DORIAN",    B_DORIAN);
        nameScaleMap.put("B-PHRYGIAN",  B_PHRYGIAN);
        nameScaleMap.put("B-LYDIAN",    B_LYDIAN);
        nameScaleMap.put("B-MIXOLYDIAN",B_MIXOLYDIAN);
        nameScaleMap.put("B-AOLEAN",    B_AOLEAN);
        nameScaleMap.put("B-LOCRIAN",   B_LOCRIAN);
        nameScaleMap.put("B-MAJOR",     B_MAJOR);
        nameScaleMap.put("B-MINOR",     B_MINOR);
        nameScaleMap.put("B-WHOLETONE", B_WHOLETONE);
        nameScaleMap.put("B-BLUES",     B_BLUES);
        nameScaleMap.put("B-PENTAMAJOR",B_PENTAMAJOR);
        nameScaleMap.put("B-PENTAMINOR",B_PENTAMINOR);
        nameScaleMap.put("B-DOM7TH",    B_DOM7TH);
        nameScaleMap.put("B-DIM7TH",    B_DIM7TH);
        nameScaleMap.put("B-MIN7TH",    B_MIN7TH);
        nameScaleMap.put("B-MAJ7TH",    B_MAJ7TH);
        nameScaleMap.put("B-AUGTRIAD",  B_AUGTRIAD);
        nameScaleMap.put("B-DIMTRIAD",  B_DIMTRIAD);
        nameScaleMap.put("B-MINTRIAD",  B_MINTRIAD);
        nameScaleMap.put("B-MAJTRIAD",  B_MAJTRIAD);
        nameScaleMap.put("B-MINSECOND", B_MINSECOND);
        nameScaleMap.put("B-SECOND",    B_SECOND);
        nameScaleMap.put("B-MINTHIRD",  B_MINTHIRD);
        nameScaleMap.put("B-THIRD",  B_THIRD);
        nameScaleMap.put("B-FOURTH",    B_FOURTH);
        nameScaleMap.put("B-TRITONE",   B_TRITONE);
        nameScaleMap.put("B-FIFTH",     B_FIFTH);

    }

    public static boolean isScaleName(String command) {
        return ( nameScaleMap.get(command) != null );
    }

    public static Scale computeScaleFromName(String command) {
        return nameScaleMap.get(command);
    }

    public static boolean isSetScaleName(String command) {
        String last = command.substring(command.length()-1);
        //System.out.println("last="+last+"\n");
        String butlast = command.substring(0,command.length()-1);
        //System.out.println("butlast="+butlast+"\n");
        if ( last.equals("$") ) {
            if ( isScaleName(butlast) ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Scale() {
    }

    /**
     * Create a scale.
     * @param k the key of the scale
     * @param m the mode of the scale
     */
    public Scale(Key k, Mode m) {
        key = k;
        mode = m;
    }

    private Key key;
    private Mode mode;

    /**
     * Create a textual representation of a scale.
     * @return the textual representation of the scale
     */
    @Override
    public String toString() {
        return "[Scale: " + name() + " " + mode.format(intervals()) + "]";
    }

    /**
     * Reference the key of the scale.
     * @return the key of the node.
     */
    public Key key() { return key; }

    /**
     * Reference the intervals variable
     * @return the intervals array
     */
    public int[] intervals() {
        return mode.intervals();
    }

    /**
     * Reference the mode of the scale
     * @return the mode of the scale
     */
    public Mode mode() { return mode; }

    /**
     * Determine the size (number of intervals) of the scale.
     * @return the number of intervals in the scale.
     */
    public int size() { return mode.intervals().length; }

    /**
     * Compare two scales for equality.
     * @param s one of the scales (the other one)
     * @return true/false depending on the result of the equality test.
     */
    public boolean equals(Scale s) {
        return key.equals(s.key()) && mode.equals(s.mode());
    }

    /**
     * Creates an "absolute" name of the scale (EG, X-MAJOR)
     * @return an "absolute" name of the scale.
     */
    public String name () {
        return key.name() + "-" + mode.name();
    }

    /**
     * Compute the pitch class associated with a given degree of the scale
     * @param some degree
     * @return the pitch class associated with the given degree of the scale
     */
    public String computePitchClass(int degree) {
        degree = degree % mode.intervals().length;
        if ( degree == 0 ) { degree = mode.intervals().length; }
        String base = key.name();
        int nrSemitones;
        nrSemitones = nrSemitones(degree, mode.intervals());
        String x = base;
        for ( int i = 1; i <= nrSemitones; i++ ) {
            x = successor(x);
        }
        return x;
    }

//    public String computePitchClass(int degree,JTextArea ta) {
//        // ta.append(">>> computePitchClass" + "\n");
//        //ta.append("degree = " + degree + "\n");
//        String base = key.name();
//        //ta.append("base = " + base + "\n");
//        int nrSemitones = nrSemitones(degree,mode.intervals());
//        //ta.append("nrSemitones = " + nrSemitones + "\n");
//        String x = base;
//        for ( int i = 1; i <= nrSemitones; i++ ) {
//            x = successor(x);
//        }
//        //ta.append("x = " + x + "\n");
//        return x;
//    }
//
    static private MidiMap mm = new MidiMap();

    /**
     * Compute the midi number corresponding to the given degree and location
     * in the scale.
     * @param degree the degree of the note
     * @param location the location of the note
     * @return the midi number for the degree/location pair in the scale
     */
    public int computeMidiNumber(int degree, int location) {
        String pc;
        pc = computePitchClass(degree);
        String it = pc + location;
        int mn = mm.midiNumber(it);
        return mn;
    }

    private int nrSemitones(int degree, int[] intervals) {
        int acc = 0;
        for ( int i = 0; i < degree-1; i++ ) {
            acc = acc + intervals[i];
        }
        return acc;
    }

    /**
     *
     * @param x is a pitch class name
     * @return the successor pitch class name
     */
    private String successor(String x) {
        if ( x.equalsIgnoreCase("C") ) { return "V"; }
        else if ( x.equalsIgnoreCase("V") ) { return "D"; }
        else if ( x.equalsIgnoreCase("D") ) { return "W"; }
        else if ( x.equalsIgnoreCase("W") ) { return "E"; }
        else if ( x.equalsIgnoreCase("E") ) { return "F"; }
        else if ( x.equalsIgnoreCase("F") ) { return "X"; }
        else if ( x.equalsIgnoreCase("X") ) { return "G"; }
        else if ( x.equalsIgnoreCase("G") ) { return "Y"; }
        else if ( x.equalsIgnoreCase("Y") ) { return "A"; }
        else if ( x.equalsIgnoreCase("A") ) { return "Z"; }
        else if ( x.equalsIgnoreCase("Z") ) { return "B"; }
        else if ( x.equalsIgnoreCase("B") ) { return "C"; }
        else return null;
    }

    /**
     * references the last interval of the mode of the scale
     * @return the last interval of the mode of the scale
     */
    public int lastInterval() {
        return mode.lastInterval();
    }

    /**
     * Set the mode of the scale
     * @param mode is the mode to which the scale mode is set
     */
    public void setMode(Mode mode) {
        this.mode = mode;
    }

    Scale copy() {
        Scale c = new Scale(key,mode);
        return c;
    }

    /**
     * compute the degree corresponding to the given pitch class symbol
     * @param pcs is the given pitch class symbol
     * @return the degree corresponding to the pitch class symbol, or -1
     */
    public int computeDegree(String pcs) throws NoSuchDegreeException {
        int i = 1;
        while ( i <= size() ) {
            String pcsi;
            pcsi = computePitchClass(i);
            if ( pcs.equalsIgnoreCase(pcsi)) {
                return i;
            }
            i = i + 1;
        }
        throw new NoSuchDegreeException();
    }

    // chromatic rp play lp r

    public void enterScale(Mode mode) {
        // push the scale onto a scale stack

        // create new scale (temp var) with old key and new mode

        // check to see if current pc exists in new scale
        // if not, throw an exception
        // if so, determine degree of pc in new scale

        // change the scale of the note to one with same key but new mode

        // change the degree of the note to degree within the new scale

        // eg.  if C-major and degree = 2
        //      then new scale will be C-chromatic and degree = 3

        // eg.  if G-major and degree = 5
        //      then G-chromatic and degree = 8

        // eg.  if C-major and degree = 3
        //      the C-minor would throw an exception!
        
        // eg.  if C-chromatic and degree = 2
        //      then throw an exception

        // eg.  if C-chromatic and degree = 5
        //      then C-major and degree = 3

    }

    public void leaveScale() {
        // if stack is empty
        // then throw an exception

        // peek at the scale on top of the stack; call it s

        // check to see if current pc exists in s
        // if not, then throw an exception
        // if so, then determine the degree of pc in s

        // change the scale to s

        // change the degree of the note to the degree in s

    }

    // the Scale constants

    static public final Scale C_MAJOR = new Scale(Key.C,Mode.MAJOR);
    static public final Scale C_IONIAN = new Scale(Key.C,Mode.IONIAN);
    static public final Scale C_DORIAN = new Scale(Key.C,Mode.DORIAN);
    static public final Scale C_PHRYGIAN = new Scale(Key.C,Mode.PHRYGIAN);
    static public final Scale C_LYDIAN = new Scale(Key.C,Mode.LYDIAN);
    static public final Scale C_MIXOLYDIAN = new Scale(Key.C,Mode.MIXOLYDIAN);
    static public final Scale C_MINOR = new Scale(Key.C,Mode.MINOR);
    static public final Scale C_AOLEAN = new Scale(Key.C,Mode.AOLEAN);
    static public final Scale C_LOCRIAN = new Scale(Key.C,Mode.LOCRIAN);
    static public final Scale C_WHOLETONE = new Scale(Key.C,Mode.WHOLETONE);
    static public final Scale C_BLUES = new Scale(Key.C,Mode.BLUES);
    static public final Scale C_CHROMATIC = new Scale(Key.C,Mode.CHROMATIC);
    static public final Scale C_DIMINISHED = new Scale(Key.C,Mode.DIMINISHED);
    static public final Scale C_MAJTRIAD = new Scale(Key.C,Mode.MAJTRIAD);
    static public final Scale C_MINTRIAD = new Scale(Key.C,Mode.MINTRIAD);
    static public final Scale C_DIMTRIAD = new Scale(Key.C,Mode.DIMTRIAD);
    static public final Scale C_AUGTRIAD = new Scale(Key.C,Mode.AUGTRIAD);
    static public final Scale C_MAJ7TH = new Scale(Key.C,Mode.MAJ7TH);
    static public final Scale C_DOM7TH = new Scale(Key.C,Mode.DOM7TH);
    static public final Scale C_MIN7TH = new Scale(Key.C,Mode.MIN7TH);
    static public final Scale C_DIM7TH = new Scale(Key.C,Mode.DIM7TH);
    static public final Scale C_PENTAMAJOR = new Scale(Key.C,Mode.PENTAMAJOR);
    static public final Scale C_PENTAMINOR = new Scale(Key.C,Mode.PENTAMINOR);
    static public final Scale C_MINSECOND = new Scale(Key.C,Mode.MINSECOND);
    static public final Scale C_SECOND = new Scale(Key.C,Mode.SECOND);
    static public final Scale C_MINTHIRD = new Scale(Key.C,Mode.MINTHIRD);
    static public final Scale C_THIRD = new Scale(Key.C,Mode.THIRD);
    static public final Scale C_FOURTH = new Scale(Key.C,Mode.FOURTH);
    static public final Scale C_TRITONE = new Scale(Key.C,Mode.TRITONE);
    static public final Scale C_FIFTH = new Scale(Key.C,Mode.FIFTH);

    static public final Scale V_MAJOR = new Scale(Key.V,Mode.MAJOR);
    static public final Scale V_IONIAN = new Scale(Key.V,Mode.IONIAN);
    static public final Scale V_DORIAN = new Scale(Key.V,Mode.DORIAN);
    static public final Scale V_PHRYGIAN = new Scale(Key.V,Mode.PHRYGIAN);
    static public final Scale V_LYDIAN = new Scale(Key.V,Mode.LYDIAN);
    static public final Scale V_MIXOLYDIAN = new Scale(Key.V,Mode.MIXOLYDIAN);
    static public final Scale V_MINOR = new Scale(Key.V,Mode.MINOR);
    static public final Scale V_AOLEAN = new Scale(Key.V,Mode.AOLEAN);
    static public final Scale V_LOCRIAN = new Scale(Key.V,Mode.LOCRIAN);
    static public final Scale V_WHOLETONE = new Scale(Key.V,Mode.WHOLETONE);
    static public final Scale V_BLUES = new Scale(Key.V,Mode.BLUES);
    static public final Scale V_CHROMATIC = new Scale(Key.V,Mode.CHROMATIC);
    static public final Scale V_DIMINISHED = new Scale(Key.V,Mode.DIMINISHED);
    static public final Scale V_MAJTRIAD = new Scale(Key.V,Mode.MAJTRIAD);
    static public final Scale V_MINTRIAD = new Scale(Key.V,Mode.MINTRIAD);
    static public final Scale V_DIMTRIAD = new Scale(Key.V,Mode.DIMTRIAD);
    static public final Scale V_AUGTRIAD = new Scale(Key.V,Mode.AUGTRIAD);
    static public final Scale V_MAJ7TH = new Scale(Key.V,Mode.MAJ7TH);
    static public final Scale V_DOM7TH = new Scale(Key.V,Mode.DOM7TH);
    static public final Scale V_MIN7TH = new Scale(Key.V,Mode.MIN7TH);
    static public final Scale V_DIM7TH = new Scale(Key.V,Mode.DIM7TH);
    static public final Scale V_PENTAMAJOR = new Scale(Key.V,Mode.PENTAMAJOR);
    static public final Scale V_PENTAMINOR = new Scale(Key.V,Mode.PENTAMINOR);
    static public final Scale V_MINSECOND = new Scale(Key.V,Mode.MINSECOND);
    static public final Scale V_SECOND = new Scale(Key.V,Mode.SECOND);
    static public final Scale V_MINTHIRD = new Scale(Key.V,Mode.MINTHIRD);
    static public final Scale V_THIRD = new Scale(Key.V,Mode.THIRD);
    static public final Scale V_FOURTH = new Scale(Key.V,Mode.FOURTH);
    static public final Scale V_TRITONE = new Scale(Key.V,Mode.TRITONE);
    static public final Scale V_FIFTH = new Scale(Key.V,Mode.FIFTH);

    static public final Scale D_MAJOR = new Scale(Key.D,Mode.MAJOR);
    static public final Scale D_IONIAN = new Scale(Key.D,Mode.IONIAN);
    static public final Scale D_DORIAN = new Scale(Key.D,Mode.DORIAN);
    static public final Scale D_PHRYGIAN = new Scale(Key.D,Mode.PHRYGIAN);
    static public final Scale D_LYDIAN = new Scale(Key.D,Mode.LYDIAN);
    static public final Scale D_MIXOLYDIAN = new Scale(Key.D,Mode.MIXOLYDIAN);
    static public final Scale D_MINOR = new Scale(Key.D,Mode.MINOR);
    static public final Scale D_AOLEAN = new Scale(Key.D,Mode.AOLEAN);
    static public final Scale D_LOCRIAN = new Scale(Key.D,Mode.LOCRIAN);
    static public final Scale D_WHOLETONE = new Scale(Key.D,Mode.WHOLETONE);
    static public final Scale D_BLUES = new Scale(Key.D,Mode.BLUES);
    static public final Scale D_CHROMATIC = new Scale(Key.D,Mode.CHROMATIC);
    static public final Scale D_DIMINISHED = new Scale(Key.D,Mode.DIMINISHED);
    static public final Scale D_MAJTRIAD = new Scale(Key.D,Mode.MAJTRIAD);
    static public final Scale D_MINTRIAD = new Scale(Key.D,Mode.MINTRIAD);
    static public final Scale D_DIMTRIAD = new Scale(Key.D,Mode.DIMTRIAD);
    static public final Scale D_AUGTRIAD = new Scale(Key.D,Mode.AUGTRIAD);
    static public final Scale D_MAJ7TH = new Scale(Key.D,Mode.MAJ7TH);
    static public final Scale D_DOM7TH = new Scale(Key.D,Mode.DOM7TH);
    static public final Scale D_MIN7TH = new Scale(Key.D,Mode.MIN7TH);
    static public final Scale D_DIM7TH = new Scale(Key.D,Mode.DIM7TH);
    static public final Scale D_PENTAMAJOR = new Scale(Key.D,Mode.PENTAMAJOR);
    static public final Scale D_PENTAMINOR = new Scale(Key.D,Mode.PENTAMINOR);
    static public final Scale D_MINSECOND = new Scale(Key.D,Mode.MINSECOND);
    static public final Scale D_SECOND = new Scale(Key.D,Mode.SECOND);
    static public final Scale D_MINTHIRD = new Scale(Key.D,Mode.MINTHIRD);
    static public final Scale D_THIRD = new Scale(Key.D,Mode.THIRD);
    static public final Scale D_FOURTH = new Scale(Key.D,Mode.FOURTH);
    static public final Scale D_TRITONE = new Scale(Key.D,Mode.TRITONE);
    static public final Scale D_FIFTH = new Scale(Key.D,Mode.FIFTH);

    static public final Scale W_MAJOR = new Scale(Key.W,Mode.MAJOR);
    static public final Scale W_IONIAN = new Scale(Key.W,Mode.IONIAN);
    static public final Scale W_DORIAN = new Scale(Key.W,Mode.DORIAN);
    static public final Scale W_PHRYGIAN = new Scale(Key.W,Mode.PHRYGIAN);
    static public final Scale W_LYDIAN = new Scale(Key.W,Mode.LYDIAN);
    static public final Scale W_MIXOLYDIAN = new Scale(Key.W,Mode.MIXOLYDIAN);
    static public final Scale W_MINOR = new Scale(Key.W,Mode.MINOR);
    static public final Scale W_AOLEAN = new Scale(Key.W,Mode.AOLEAN);
    static public final Scale W_LOCRIAN = new Scale(Key.W,Mode.LOCRIAN);
    static public final Scale W_WHOLETONE = new Scale(Key.W,Mode.WHOLETONE);
    static public final Scale W_BLUES = new Scale(Key.W,Mode.BLUES);
    static public final Scale W_CHROMATIC = new Scale(Key.W,Mode.CHROMATIC);
    static public final Scale W_DIMINISHED = new Scale(Key.W,Mode.DIMINISHED);
    static public final Scale W_MAJTRIAD = new Scale(Key.W,Mode.MAJTRIAD);
    static public final Scale W_MINTRIAD = new Scale(Key.W,Mode.MINTRIAD);
    static public final Scale W_DIMTRIAD = new Scale(Key.W,Mode.DIMTRIAD);
    static public final Scale W_AUGTRIAD = new Scale(Key.W,Mode.AUGTRIAD);
    static public final Scale W_MAJ7TH = new Scale(Key.W,Mode.MAJ7TH);
    static public final Scale W_DOM7TH = new Scale(Key.W,Mode.DOM7TH);
    static public final Scale W_MIN7TH = new Scale(Key.W,Mode.MIN7TH);
    static public final Scale W_DIM7TH = new Scale(Key.W,Mode.DIM7TH);
    static public final Scale W_PENTAMAJOR = new Scale(Key.W,Mode.PENTAMAJOR);
    static public final Scale W_PENTAMINOR = new Scale(Key.W,Mode.PENTAMINOR);
    static public final Scale W_MINSECOND = new Scale(Key.W,Mode.MINSECOND);
    static public final Scale W_SECOND = new Scale(Key.W,Mode.SECOND);
    static public final Scale W_MINTHIRD = new Scale(Key.W,Mode.MINTHIRD);
    static public final Scale W_THIRD = new Scale(Key.W,Mode.THIRD);
    static public final Scale W_FOURTH = new Scale(Key.W,Mode.FOURTH);
    static public final Scale W_TRITONE = new Scale(Key.W,Mode.TRITONE);
    static public final Scale W_FIFTH = new Scale(Key.W,Mode.FIFTH);

    static public final Scale E_MAJOR = new Scale(Key.E,Mode.MAJOR);
    static public final Scale E_IONIAN = new Scale(Key.E,Mode.IONIAN);
    static public final Scale E_DORIAN = new Scale(Key.E,Mode.DORIAN);
    static public final Scale E_PHRYGIAN = new Scale(Key.E,Mode.PHRYGIAN);
    static public final Scale E_LYDIAN = new Scale(Key.E,Mode.LYDIAN);
    static public final Scale E_MIXOLYDIAN = new Scale(Key.E,Mode.MIXOLYDIAN);
    static public final Scale E_MINOR = new Scale(Key.E,Mode.MINOR);
    static public final Scale E_AOLEAN = new Scale(Key.E,Mode.AOLEAN);
    static public final Scale E_LOCRIAN = new Scale(Key.E,Mode.LOCRIAN);
    static public final Scale E_WHOLETONE = new Scale(Key.E,Mode.WHOLETONE);
    static public final Scale E_BLUES = new Scale(Key.E,Mode.BLUES);
    static public final Scale E_CHROMATIC = new Scale(Key.E,Mode.CHROMATIC);
    static public final Scale E_DIMINISHED = new Scale(Key.E,Mode.DIMINISHED);
    static public final Scale E_MAJTRIAD = new Scale(Key.E,Mode.MAJTRIAD);
    static public final Scale E_MINTRIAD = new Scale(Key.E,Mode.MINTRIAD);
    static public final Scale E_DIMTRIAD = new Scale(Key.E,Mode.DIMTRIAD);
    static public final Scale E_AUGTRIAD = new Scale(Key.E,Mode.AUGTRIAD);
    static public final Scale E_MAJ7TH = new Scale(Key.E,Mode.MAJ7TH);
    static public final Scale E_DOM7TH = new Scale(Key.E,Mode.DOM7TH);
    static public final Scale E_MIN7TH = new Scale(Key.E,Mode.MIN7TH);
    static public final Scale E_DIM7TH = new Scale(Key.E,Mode.DIM7TH);
    static public final Scale E_PENTAMAJOR = new Scale(Key.E,Mode.PENTAMAJOR);
    static public final Scale E_PENTAMINOR = new Scale(Key.E,Mode.PENTAMINOR);
    static public final Scale E_MINSECOND = new Scale(Key.E,Mode.MINSECOND);
    static public final Scale E_SECOND = new Scale(Key.E,Mode.SECOND);
    static public final Scale E_MINTHIRD = new Scale(Key.E,Mode.MINTHIRD);
    static public final Scale E_THIRD = new Scale(Key.E,Mode.THIRD);
    static public final Scale E_FOURTH = new Scale(Key.E,Mode.FOURTH);
    static public final Scale E_TRITONE = new Scale(Key.E,Mode.TRITONE);
    static public final Scale E_FIFTH = new Scale(Key.E,Mode.FIFTH);

    static public final Scale F_MAJOR = new Scale(Key.F,Mode.MAJOR);
    static public final Scale F_IONIAN = new Scale(Key.F,Mode.IONIAN);
    static public final Scale F_DORIAN = new Scale(Key.F,Mode.DORIAN);
    static public final Scale F_PHRYGIAN = new Scale(Key.F,Mode.PHRYGIAN);
    static public final Scale F_LYDIAN = new Scale(Key.F,Mode.LYDIAN);
    static public final Scale F_MIXOLYDIAN = new Scale(Key.F,Mode.MIXOLYDIAN);
    static public final Scale F_MINOR = new Scale(Key.F,Mode.MINOR);
    static public final Scale F_AOLEAN = new Scale(Key.F,Mode.AOLEAN);
    static public final Scale F_LOCRIAN = new Scale(Key.F,Mode.LOCRIAN);
    static public final Scale F_WHOLETONE = new Scale(Key.F,Mode.WHOLETONE);
    static public final Scale F_BLUES = new Scale(Key.F,Mode.BLUES);
    static public final Scale F_CHROMATIC = new Scale(Key.F,Mode.CHROMATIC);
    static public final Scale F_DIMINISHED = new Scale(Key.F,Mode.DIMINISHED);
    static public final Scale F_MAJTRIAD = new Scale(Key.F,Mode.MAJTRIAD);
    static public final Scale F_MINTRIAD = new Scale(Key.F,Mode.MINTRIAD);
    static public final Scale F_DIMTRIAD = new Scale(Key.F,Mode.DIMTRIAD);
    static public final Scale F_AUGTRIAD = new Scale(Key.F,Mode.AUGTRIAD);
    static public final Scale F_MAJ7TH = new Scale(Key.F,Mode.MAJ7TH);
    static public final Scale F_DOM7TH = new Scale(Key.F,Mode.DOM7TH);
    static public final Scale F_MIN7TH = new Scale(Key.F,Mode.MIN7TH);
    static public final Scale F_DIM7TH = new Scale(Key.F,Mode.DIM7TH);
    static public final Scale F_PENTAMAJOR = new Scale(Key.F,Mode.PENTAMAJOR);
    static public final Scale F_PENTAMINOR = new Scale(Key.F,Mode.PENTAMINOR);
    static public final Scale F_MINSECOND = new Scale(Key.F,Mode.MINSECOND);
    static public final Scale F_SECOND = new Scale(Key.F,Mode.SECOND);
    static public final Scale F_MINTHIRD = new Scale(Key.F,Mode.MINTHIRD);
    static public final Scale F_THIRD = new Scale(Key.F,Mode.THIRD);
    static public final Scale F_FOURTH = new Scale(Key.F,Mode.FOURTH);
    static public final Scale F_TRITONE = new Scale(Key.F,Mode.TRITONE);
    static public final Scale F_FIFTH = new Scale(Key.F,Mode.FIFTH);

    static public final Scale X_MAJOR = new Scale(Key.X,Mode.MAJOR);
    static public final Scale X_IONIAN = new Scale(Key.X,Mode.IONIAN);
    static public final Scale X_DORIAN = new Scale(Key.X,Mode.DORIAN);
    static public final Scale X_PHRYGIAN = new Scale(Key.X,Mode.PHRYGIAN);
    static public final Scale X_LYDIAN = new Scale(Key.X,Mode.LYDIAN);
    static public final Scale X_MIXOLYDIAN = new Scale(Key.X,Mode.MIXOLYDIAN);
    static public final Scale X_MINOR = new Scale(Key.X,Mode.MINOR);
    static public final Scale X_AOLEAN = new Scale(Key.X,Mode.AOLEAN);
    static public final Scale X_LOCRIAN = new Scale(Key.X,Mode.LOCRIAN);
    static public final Scale X_WHOLETONE = new Scale(Key.X,Mode.WHOLETONE);
    static public final Scale X_BLUES = new Scale(Key.X,Mode.BLUES);
    static public final Scale X_CHROMATIC = new Scale(Key.X,Mode.CHROMATIC);
    static public final Scale X_DIMINISHED = new Scale(Key.X,Mode.DIMINISHED);
    static public final Scale X_MAJTRIAD = new Scale(Key.X,Mode.MAJTRIAD);
    static public final Scale X_MINTRIAD = new Scale(Key.X,Mode.MINTRIAD);
    static public final Scale X_DIMTRIAD = new Scale(Key.X,Mode.DIMTRIAD);
    static public final Scale X_AUGTRIAD = new Scale(Key.X,Mode.AUGTRIAD);
    static public final Scale X_MAJ7TH = new Scale(Key.X,Mode.MAJ7TH);
    static public final Scale X_DOM7TH = new Scale(Key.X,Mode.DOM7TH);
    static public final Scale X_MIN7TH = new Scale(Key.X,Mode.MIN7TH);
    static public final Scale X_DIM7TH = new Scale(Key.X,Mode.DIM7TH);
    static public final Scale X_PENTAMAJOR = new Scale(Key.X,Mode.PENTAMAJOR);
    static public final Scale X_PENTAMINOR = new Scale(Key.X,Mode.PENTAMINOR);
    static public final Scale X_MINSECOND = new Scale(Key.X,Mode.MINSECOND);
    static public final Scale X_SECOND = new Scale(Key.X,Mode.SECOND);
    static public final Scale X_MINTHIRD = new Scale(Key.X,Mode.MINTHIRD);
    static public final Scale X_THIRD = new Scale(Key.X,Mode.THIRD);
    static public final Scale X_FOURTH = new Scale(Key.X,Mode.FOURTH);
    static public final Scale X_TRITONE = new Scale(Key.X,Mode.TRITONE);
    static public final Scale X_FIFTH = new Scale(Key.X,Mode.FIFTH);

    static public final Scale G_MAJOR = new Scale(Key.G,Mode.MAJOR);
    static public final Scale G_IONIAN = new Scale(Key.G,Mode.IONIAN);
    static public final Scale G_DORIAN = new Scale(Key.G,Mode.DORIAN);
    static public final Scale G_PHRYGIAN = new Scale(Key.G,Mode.PHRYGIAN);
    static public final Scale G_LYDIAN = new Scale(Key.G,Mode.LYDIAN);
    static public final Scale G_MIXOLYDIAN = new Scale(Key.G,Mode.MIXOLYDIAN);
    static public final Scale G_MINOR = new Scale(Key.G,Mode.MINOR);
    static public final Scale G_AOLEAN = new Scale(Key.G,Mode.AOLEAN);
    static public final Scale G_LOCRIAN = new Scale(Key.G,Mode.LOCRIAN);
    static public final Scale G_WHOLETONE = new Scale(Key.G,Mode.WHOLETONE);
    static public final Scale G_BLUES = new Scale(Key.G,Mode.BLUES);
    static public final Scale G_CHROMATIC = new Scale(Key.G,Mode.CHROMATIC);
    static public final Scale G_DIMINISHED = new Scale(Key.G,Mode.DIMINISHED);
    static public final Scale G_MAJTRIAD = new Scale(Key.G,Mode.MAJTRIAD);
    static public final Scale G_MINTRIAD = new Scale(Key.G,Mode.MINTRIAD);
    static public final Scale G_DIMTRIAD = new Scale(Key.G,Mode.DIMTRIAD);
    static public final Scale G_AUGTRIAD = new Scale(Key.G,Mode.AUGTRIAD);
    static public final Scale G_MAJ7TH = new Scale(Key.G,Mode.MAJ7TH);
    static public final Scale G_DOM7TH = new Scale(Key.G,Mode.DOM7TH);
    static public final Scale G_MIN7TH = new Scale(Key.G,Mode.MIN7TH);
    static public final Scale G_DIM7TH = new Scale(Key.G,Mode.DIM7TH);
    static public final Scale G_PENTAMAJOR = new Scale(Key.G,Mode.PENTAMAJOR);
    static public final Scale G_PENTAMINOR = new Scale(Key.G,Mode.PENTAMINOR);
    static public final Scale G_MINSECOND = new Scale(Key.G,Mode.MINSECOND);
    static public final Scale G_SECOND = new Scale(Key.G,Mode.SECOND);
    static public final Scale G_MINTHIRD = new Scale(Key.G,Mode.MINTHIRD);
    static public final Scale G_THIRD = new Scale(Key.G,Mode.THIRD);
    static public final Scale G_FOURTH = new Scale(Key.G,Mode.FOURTH);
    static public final Scale G_TRITONE = new Scale(Key.G,Mode.TRITONE);
    static public final Scale G_FIFTH = new Scale(Key.G,Mode.FIFTH);

    static public final Scale Y_MAJOR = new Scale(Key.Y,Mode.MAJOR);
    static public final Scale Y_IONIAN = new Scale(Key.Y,Mode.IONIAN);
    static public final Scale Y_DORIAN = new Scale(Key.Y,Mode.DORIAN);
    static public final Scale Y_PHRYGIAN = new Scale(Key.Y,Mode.PHRYGIAN);
    static public final Scale Y_LYDIAN = new Scale(Key.Y,Mode.LYDIAN);
    static public final Scale Y_MIXOLYDIAN = new Scale(Key.Y,Mode.MIXOLYDIAN);
    static public final Scale Y_MINOR = new Scale(Key.Y,Mode.MINOR);
    static public final Scale Y_AOLEAN = new Scale(Key.Y,Mode.AOLEAN);
    static public final Scale Y_LOCRIAN = new Scale(Key.Y,Mode.LOCRIAN);
    static public final Scale Y_WHOLETONE = new Scale(Key.Y,Mode.WHOLETONE);
    static public final Scale Y_BLUES = new Scale(Key.Y,Mode.BLUES);
    static public final Scale Y_CHROMATIC = new Scale(Key.Y,Mode.CHROMATIC);
    static public final Scale Y_DIMINISHED = new Scale(Key.Y,Mode.DIMINISHED);
    static public final Scale Y_MAJTRIAD = new Scale(Key.Y,Mode.MAJTRIAD);
    static public final Scale Y_MINTRIAD = new Scale(Key.Y,Mode.MINTRIAD);
    static public final Scale Y_DIMTRIAD = new Scale(Key.Y,Mode.DIMTRIAD);
    static public final Scale Y_AUGTRIAD = new Scale(Key.Y,Mode.AUGTRIAD);
    static public final Scale Y_MAJ7TH = new Scale(Key.Y,Mode.MAJ7TH);
    static public final Scale Y_DOM7TH = new Scale(Key.Y,Mode.DOM7TH);
    static public final Scale Y_MIN7TH = new Scale(Key.Y,Mode.MIN7TH);
    static public final Scale Y_DIM7TH = new Scale(Key.Y,Mode.DIM7TH);
    static public final Scale Y_PENTAMAJOR = new Scale(Key.Y,Mode.PENTAMAJOR);
    static public final Scale Y_PENTAMINOR = new Scale(Key.Y,Mode.PENTAMINOR);
    static public final Scale Y_MINSECOND = new Scale(Key.Y,Mode.MINSECOND);
    static public final Scale Y_SECOND = new Scale(Key.Y,Mode.SECOND);
    static public final Scale Y_MINTHIRD = new Scale(Key.Y,Mode.MINTHIRD);
    static public final Scale Y_THIRD = new Scale(Key.Y,Mode.THIRD);
    static public final Scale Y_FOURTH = new Scale(Key.Y,Mode.FOURTH);
    static public final Scale Y_TRITONE = new Scale(Key.Y,Mode.TRITONE);
    static public final Scale Y_FIFTH = new Scale(Key.Y,Mode.FIFTH);

    static public final Scale A_MAJOR = new Scale(Key.A,Mode.MAJOR);
    static public final Scale A_IONIAN = new Scale(Key.A,Mode.IONIAN);
    static public final Scale A_DORIAN = new Scale(Key.A,Mode.DORIAN);
    static public final Scale A_PHRYGIAN = new Scale(Key.A,Mode.PHRYGIAN);
    static public final Scale A_LYDIAN = new Scale(Key.A,Mode.LYDIAN);
    static public final Scale A_MIXOLYDIAN = new Scale(Key.A,Mode.MIXOLYDIAN);
    static public final Scale A_MINOR = new Scale(Key.A,Mode.MINOR);
    static public final Scale A_AOLEAN = new Scale(Key.A,Mode.AOLEAN);
    static public final Scale A_LOCRIAN = new Scale(Key.A,Mode.LOCRIAN);
    static public final Scale A_WHOLETONE = new Scale(Key.A,Mode.WHOLETONE);
    static public final Scale A_BLUES = new Scale(Key.A,Mode.BLUES);
    static public final Scale A_CHROMATIC = new Scale(Key.A,Mode.CHROMATIC);
    static public final Scale A_DIMINISHED = new Scale(Key.A,Mode.DIMINISHED);
    static public final Scale A_MAJTRIAD = new Scale(Key.A,Mode.MAJTRIAD);
    static public final Scale A_MINTRIAD = new Scale(Key.A,Mode.MINTRIAD);
    static public final Scale A_DIMTRIAD = new Scale(Key.A,Mode.DIMTRIAD);
    static public final Scale A_AUGTRIAD = new Scale(Key.A,Mode.AUGTRIAD);
    static public final Scale A_MAJ7TH = new Scale(Key.A,Mode.MAJ7TH);
    static public final Scale A_DOM7TH = new Scale(Key.A,Mode.DOM7TH);
    static public final Scale A_MIN7TH = new Scale(Key.A,Mode.MIN7TH);
    static public final Scale A_DIM7TH = new Scale(Key.A,Mode.DIM7TH);
    static public final Scale A_PENTAMAJOR = new Scale(Key.A,Mode.PENTAMAJOR);
    static public final Scale A_PENTAMINOR = new Scale(Key.A,Mode.PENTAMINOR);
    static public final Scale A_MINSECOND = new Scale(Key.A,Mode.MINSECOND);
    static public final Scale A_SECOND = new Scale(Key.A,Mode.SECOND);
    static public final Scale A_MINTHIRD = new Scale(Key.A,Mode.MINTHIRD);
    static public final Scale A_THIRD = new Scale(Key.A,Mode.THIRD);
    static public final Scale A_FOURTH = new Scale(Key.A,Mode.FOURTH);
    static public final Scale A_TRITONE = new Scale(Key.A,Mode.TRITONE);
    static public final Scale A_FIFTH = new Scale(Key.A,Mode.FIFTH);

    static public final Scale Z_MAJOR = new Scale(Key.Z,Mode.MAJOR);
    static public final Scale Z_IONIAN = new Scale(Key.Z,Mode.IONIAN);
    static public final Scale Z_DORIAN = new Scale(Key.Z,Mode.DORIAN);
    static public final Scale Z_PHRYGIAN = new Scale(Key.Z,Mode.PHRYGIAN);
    static public final Scale Z_LYDIAN = new Scale(Key.Z,Mode.LYDIAN);
    static public final Scale Z_MIXOLYDIAN = new Scale(Key.Z,Mode.MIXOLYDIAN);
    static public final Scale Z_MINOR = new Scale(Key.Z,Mode.MINOR);
    static public final Scale Z_AOLEAN = new Scale(Key.Z,Mode.AOLEAN);
    static public final Scale Z_LOCRIAN = new Scale(Key.Z,Mode.LOCRIAN);
    static public final Scale Z_WHOLETONE = new Scale(Key.Z,Mode.WHOLETONE);
    static public final Scale Z_BLUES = new Scale(Key.Z,Mode.BLUES);
    static public final Scale Z_CHROMATIC = new Scale(Key.Z,Mode.CHROMATIC);
    static public final Scale Z_DIMINISHED = new Scale(Key.Z,Mode.DIMINISHED);
    static public final Scale Z_MAJTRIAD = new Scale(Key.Z,Mode.MAJTRIAD);
    static public final Scale Z_MINTRIAD = new Scale(Key.Z,Mode.MINTRIAD);
    static public final Scale Z_DIMTRIAD = new Scale(Key.Z,Mode.DIMTRIAD);
    static public final Scale Z_AUGTRIAD = new Scale(Key.Z,Mode.AUGTRIAD);
    static public final Scale Z_MAJ7TH = new Scale(Key.Z,Mode.MAJ7TH);
    static public final Scale Z_DOM7TH = new Scale(Key.Z,Mode.DOM7TH);
    static public final Scale Z_MIN7TH = new Scale(Key.Z,Mode.MIN7TH);
    static public final Scale Z_DIM7TH = new Scale(Key.Z,Mode.DIM7TH);
    static public final Scale Z_PENTAMAJOR = new Scale(Key.Z,Mode.PENTAMAJOR);
    static public final Scale Z_PENTAMINOR = new Scale(Key.Z,Mode.PENTAMINOR);
    static public final Scale Z_MINSECOND = new Scale(Key.Z,Mode.MINSECOND);
    static public final Scale Z_SECOND = new Scale(Key.Z,Mode.SECOND);
    static public final Scale Z_MINTHIRD = new Scale(Key.Z,Mode.MINTHIRD);
    static public final Scale Z_THIRD = new Scale(Key.Z,Mode.THIRD);
    static public final Scale Z_FOURTH = new Scale(Key.Z,Mode.FOURTH);
    static public final Scale Z_TRITONE = new Scale(Key.Z,Mode.TRITONE);
    static public final Scale Z_FIFTH = new Scale(Key.Z,Mode.FIFTH);

    static public final Scale B_MAJOR = new Scale(Key.B,Mode.MAJOR);
    static public final Scale B_IONIAN = new Scale(Key.B,Mode.IONIAN);
    static public final Scale B_DORIAN = new Scale(Key.B,Mode.DORIAN);
    static public final Scale B_PHRYGIAN = new Scale(Key.B,Mode.PHRYGIAN);
    static public final Scale B_LYDIAN = new Scale(Key.B,Mode.LYDIAN);
    static public final Scale B_MIXOLYDIAN = new Scale(Key.B,Mode.MIXOLYDIAN);
    static public final Scale B_MINOR = new Scale(Key.B,Mode.MINOR);
    static public final Scale B_AOLEAN = new Scale(Key.B,Mode.AOLEAN);
    static public final Scale B_LOCRIAN = new Scale(Key.B,Mode.LOCRIAN);
    static public final Scale B_WHOLETONE = new Scale(Key.B,Mode.WHOLETONE);
    static public final Scale B_BLUES = new Scale(Key.B,Mode.BLUES);
    static public final Scale B_CHROMATIC = new Scale(Key.B,Mode.CHROMATIC);
    static public final Scale B_DIMINISHED = new Scale(Key.B,Mode.DIMINISHED);
    static public final Scale B_MAJTRIAD = new Scale(Key.B,Mode.MAJTRIAD);
    static public final Scale B_MINTRIAD = new Scale(Key.B,Mode.MINTRIAD);
    static public final Scale B_DIMTRIAD = new Scale(Key.B,Mode.DIMTRIAD);
    static public final Scale B_AUGTRIAD = new Scale(Key.B,Mode.AUGTRIAD);
    static public final Scale B_MAJ7TH = new Scale(Key.B,Mode.MAJ7TH);
    static public final Scale B_DOM7TH = new Scale(Key.B,Mode.DOM7TH);
    static public final Scale B_MIN7TH = new Scale(Key.B,Mode.MIN7TH);
    static public final Scale B_DIM7TH = new Scale(Key.B,Mode.DIM7TH);
    static public final Scale B_PENTAMAJOR = new Scale(Key.B,Mode.PENTAMAJOR);
    static public final Scale B_PENTAMINOR = new Scale(Key.B,Mode.PENTAMINOR);
    static public final Scale B_MINSECOND = new Scale(Key.B,Mode.MINSECOND);
    static public final Scale B_SECOND = new Scale(Key.B,Mode.SECOND);
    static public final Scale B_MINTHIRD = new Scale(Key.B,Mode.MINTHIRD);
    static public final Scale B_THIRD = new Scale(Key.B,Mode.THIRD);
    static public final Scale B_FOURTH = new Scale(Key.B,Mode.FOURTH);
    static public final Scale B_TRITONE = new Scale(Key.B,Mode.TRITONE);
    static public final Scale B_FIFTH = new Scale(Key.B,Mode.FIFTH);

}
