/*
 * MidiMap.java of the note package of the AlphaMxM project.
 */

package note;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Used to create instances of a Midi information storage device.  This is
 * a map from blue midi names to midi numbers.  By a blue midi name I
 * mean a name which references "black keys" by the single letters v (cd),
 * w (de), x (fg), y(ga), z(ab) rather than by sharps and flats.  
 * <pre>
 * Name    Nr    Notes
 * C0      0     bottom midi note
 * V0      1
 * D0      2
 * W0      3
 * E0      4
 * F0      5
 * X0      6
 * G0      7
 * Y0      8
 * A0      9
 * Z0      10
 * B0      11
 * c1      12
 * v1      13
 * d1      14
 * w1      15
 * e1      16
 * f1      17
 * x1      18
 * g1      19
 * y1      20
 * a1      21    bottom piano note
 * z1      22
 * b1      23
 * c2      24
 * v2      25
 * d2      26
 * w2      27
 * e2      28
 * f2      29
 * x2      30
 * g2      31
 * y2      32
 * a2      33
 * z2      34
 * b2      35
 * c3      36
 * v3      37
 * d3      38
 * w3      39
 * e3      40
 * f3      41
 * x3      42
 * g3      43
 * y3      44
 * a3      45
 * z3      46
 * b3      47
 * c4      48
 * v4      49
 * d4      50
 * w4      51
 * e4      52
 * f4      53
 * x4      54
 * g4      55
 * y4      56
 * a4      57    concert A
 * z4      58
 * b4      59
 * c5      60    middle C
 * v5      61
 * d5      62
 * w5      63
 * e5      64
 * f5      65
 * x5      66
 * g5      67
 * y5      68
 * a5      69
 * z5      70
 * b5      71
 * c6      72
 * v6      73
 * d6      74
 * w6      75
 * e6      76
 * f6      77
 * x6      78
 * g6      79
 * y6      80
 * a6      81
 * z6      82
 * b6      83
 * c7      84
 * v7      85
 * d7      86
 * w7      87
 * e7      88
 * f7      89
 * x7      90
 * g7      91
 * y7      92
 * a7      93
 * z7      94
 * b7      95
 * c8      96
 * v8      97
 * d8      98
 * w8      99
 * e8      100
 * f8      101
 * x8      102
 * g8      103
 * y8      104
 * a8      105
 * z8      106
 * b8      107
 * c9      108   top piano note
 * v9      109
 * d9      110
 * w9      111
 * e9      112
 * f9      113
 * x9      114
 * g9      115
 * y9      116
 * a9      117
 * z9      118
 * b9      119
 * c10     120
 * v10     121
 * d10     122
 * w10     123
 * e10     124
 * f10     125
 * x10     126
 * g10     127   top midi note
 * </pre>
 *
 * @author blue
 */

public class MidiMap {

    /**
     * Reserved for future uses involving scores and scoring music
     * @param curmidi is a midi number
     * @return true if the note corresponds to a LINE on a staff
     */
    static public boolean isLine(int curmidi) {
        return midiLSMap.get(curmidi).equals("L");
    }

    /**
     * Reserved for future uses involving scores and scoring music
     * @param curmidi is a midi number
     * @return true if the note corresponds to a SPACE on a staff
     */
    static public boolean isSpace(int curmidi) {
        return midiLSMap.get(curmidi).equals("S");
    }

    static private Map<String,Integer> midiMap;
    static private Map<Integer,String> midiLSMap;

    /**
     * Used to create instances of a Midi information storage device.
     */
    public MidiMap() {
        establishMap();
        establishLSMap();
    }

    private void establishMap() {
        midiMap = new HashMap();

        midiMap.put("C0",0);
        midiMap.put("V0",1);
        midiMap.put("D0",2);
        midiMap.put("W0",3);
        midiMap.put("E0",4);
        midiMap.put("F0",5);
        midiMap.put("X0",6);
        midiMap.put("G0",7);
        midiMap.put("Y0",8);
        midiMap.put("A0",9);
        midiMap.put("Z0",10);
        midiMap.put("B0",11);

        midiMap.put("C1",12);
        midiMap.put("V1",13);
        midiMap.put("D1",14);
        midiMap.put("W1",15);
        midiMap.put("E1",16);
        midiMap.put("F1",17);
        midiMap.put("X1",18);
        midiMap.put("G1",19);
        midiMap.put("Y1",20);
        midiMap.put("A1",21);
        midiMap.put("Z1",22);
        midiMap.put("B1",23);

        midiMap.put("C2",24);
        midiMap.put("V2",25);
        midiMap.put("D2",26);
        midiMap.put("W2",27);
        midiMap.put("E2",28);
        midiMap.put("F2",29);
        midiMap.put("X2",30);
        midiMap.put("G2",31);
        midiMap.put("Y2",32);
        midiMap.put("A2",33);
        midiMap.put("Z2",34);
        midiMap.put("B2",35);

        midiMap.put("C3",36);
        midiMap.put("V3",37);
        midiMap.put("D3",38);
        midiMap.put("W3",39);
        midiMap.put("E3",40);
        midiMap.put("F3",41);
        midiMap.put("X3",42);
        midiMap.put("G3",43);
        midiMap.put("Y3",44);
        midiMap.put("A3",45);
        midiMap.put("Z3",46);
        midiMap.put("B3",47);

        midiMap.put("C4",48);
        midiMap.put("V4",49);
        midiMap.put("D4",50);
        midiMap.put("W4",51);
        midiMap.put("E4",52);
        midiMap.put("F4",53);
        midiMap.put("X4",54);
        midiMap.put("G4",55);
        midiMap.put("Y4",56);
        midiMap.put("A4",57);
        midiMap.put("Z4",58);
        midiMap.put("B4",59);

        midiMap.put("C5",60);
        midiMap.put("V5",61);
        midiMap.put("D5",62);
        midiMap.put("W5",63);
        midiMap.put("E5",64);
        midiMap.put("F5",65);
        midiMap.put("X5",66);
        midiMap.put("G5",67);
        midiMap.put("Y5",68);
        midiMap.put("A5",69);
        midiMap.put("Z5",70);
        midiMap.put("B5",71);

        midiMap.put("C6",72);
        midiMap.put("V6",73);
        midiMap.put("D6",74);
        midiMap.put("W6",75);
        midiMap.put("E6",76);
        midiMap.put("F6",77);
        midiMap.put("X6",78);
        midiMap.put("G6",79);
        midiMap.put("Y6",80);
        midiMap.put("A6",81);
        midiMap.put("Z6",82);
        midiMap.put("B6",83);

        midiMap.put("C7",84);
        midiMap.put("V7",85);
        midiMap.put("D7",86);
        midiMap.put("W7",87);
        midiMap.put("E7",88);
        midiMap.put("F7",89);
        midiMap.put("X7",90);
        midiMap.put("G7",91);
        midiMap.put("Y7",92);
        midiMap.put("A7",93);
        midiMap.put("Z7",94);
        midiMap.put("B7",95);

        midiMap.put("C8",96);
        midiMap.put("V8",97);
        midiMap.put("D8",98);
        midiMap.put("W8",99);
        midiMap.put("E8",100);
        midiMap.put("F8",101);
        midiMap.put("X8",102);
        midiMap.put("G8",103);
        midiMap.put("Y8",104);
        midiMap.put("A8",105);
        midiMap.put("Z8",106);
        midiMap.put("B8",107);

        midiMap.put("C9",108);
        midiMap.put("V9",109);
        midiMap.put("D9",110);
        midiMap.put("W9",111);
        midiMap.put("E9",112);
        midiMap.put("F9",113);
        midiMap.put("X9",114);
        midiMap.put("G9",115);
        midiMap.put("Y9",116);
        midiMap.put("A9",117);
        midiMap.put("Z9",118);
        midiMap.put("B9",119);

        midiMap.put("C10",120);
        midiMap.put("V10",121);
        midiMap.put("D10",122);
        midiMap.put("W10",123);
        midiMap.put("E10",124);
        midiMap.put("F10",125);
        midiMap.put("X10",126);
        midiMap.put("G10",127);

}

    private void establishLSMap() {
        midiLSMap = new HashMap();

        midiLSMap.put(0,"?");
        midiLSMap.put(1,"?");
        midiLSMap.put(2,"?");
        midiLSMap.put(3,"?");
        midiLSMap.put(4,"?");
        midiLSMap.put(5,"?");
        midiLSMap.put(6,"?");
        midiLSMap.put(7,"?");
        midiLSMap.put(8,"?");
        midiLSMap.put(9,"?");
        midiLSMap.put(10,"?");
        midiLSMap.put(11,"?");

        midiLSMap.put(12,"?");
        midiLSMap.put(13,"?");
        midiLSMap.put(14,"?");
        midiLSMap.put(15,"?");
        midiLSMap.put(16,"?");
        midiLSMap.put(17,"?");
        midiLSMap.put(18,"?");
        midiLSMap.put(19,"?");
        midiLSMap.put(20,"?");
        midiLSMap.put(21,"?");
        midiLSMap.put(22,"?");
        midiLSMap.put(23,"?");

        midiLSMap.put(24,"?");
        midiLSMap.put(25,"?");
        midiLSMap.put(26,"?");

        midiLSMap.put(27,"?");
        midiLSMap.put(28,"?");
        midiLSMap.put(29,"?");
        midiLSMap.put(30,"?");
        midiLSMap.put(31,"?");
        midiLSMap.put(32,"?");
        midiLSMap.put(33,"?");
        midiLSMap.put(34,"?");
        midiLSMap.put(35,"?");

        midiLSMap.put(36,"?");
        midiLSMap.put(37,"?");
        midiLSMap.put(38,"?");
        midiLSMap.put(39,"?");
        midiLSMap.put(40,"?");
        midiLSMap.put(41,"?");
        midiLSMap.put(42,"?");
        midiLSMap.put(43,"?");
        midiLSMap.put(44,"?");
        midiLSMap.put(45,"?");
        midiLSMap.put(46,"?");
        midiLSMap.put(47,"?");

        midiLSMap.put(48,"?");
        midiLSMap.put(49,"?");
        midiLSMap.put(50,"?");
        midiLSMap.put(51,"?");
        midiLSMap.put(52,"?");
        midiLSMap.put(53,"?");
        midiLSMap.put(54,"?");
        midiLSMap.put(55,"?");
        midiLSMap.put(56,"?");
        midiLSMap.put(57,"?");
        midiLSMap.put(58,"?");
        midiLSMap.put(59,"?");

        midiLSMap.put(60,"L");
        midiLSMap.put(61,"?");
        midiLSMap.put(62,"S");
        midiLSMap.put(63,"?");
        midiLSMap.put(64,"L");
        midiLSMap.put(65,"S");
        midiLSMap.put(66,"?");
        midiLSMap.put(67,"L");
        midiLSMap.put(68,"?");
        midiLSMap.put(69,"S");
        midiLSMap.put(70,"?");
        midiLSMap.put(71,"L");

        midiLSMap.put(72,"S");
        midiLSMap.put(73,"?");
        midiLSMap.put(74,"L");
        midiLSMap.put(75,"?");
        midiLSMap.put(76,"S");
        midiLSMap.put(77,"L");
        midiLSMap.put(78,"?");
        midiLSMap.put(79,"S");
        midiLSMap.put(80,"?");
        midiLSMap.put(81,"L");
        midiLSMap.put(82,"?");
        midiLSMap.put(83,"S");

        midiLSMap.put(84,"L");
        midiLSMap.put(85,"?");
        midiLSMap.put(86,"S");
        midiLSMap.put(87,"?");
        midiLSMap.put(88,"L");
        midiLSMap.put(89,"S");
        midiLSMap.put(90,"?");
        midiLSMap.put(91,"L");
        midiLSMap.put(92,"?");
        midiLSMap.put(93,"S");
        midiLSMap.put(94,"?");
        midiLSMap.put(95,"L");

        midiLSMap.put(96,"S");
        midiLSMap.put(97,"?");
        midiLSMap.put(98,"L");
        midiLSMap.put(99,"?");
        midiLSMap.put(100,"S");
        midiLSMap.put(101,"L");
        midiLSMap.put(102,"?");
        midiLSMap.put(103,"S");
        midiLSMap.put(104,"?");
        midiLSMap.put(105,"L");
        midiLSMap.put(106,"?");
        midiLSMap.put(107,"S");

        midiLSMap.put(108,"L");
        midiLSMap.put(109,"?");
        midiLSMap.put(110,"?");
        midiLSMap.put(111,"?");
        midiLSMap.put(112,"?");
        midiLSMap.put(113,"?");
        midiLSMap.put(114,"?");
        midiLSMap.put(115,"?");
        midiLSMap.put(116,"?");
        midiLSMap.put(117,"?");
        midiLSMap.put(118,"?");
        midiLSMap.put(119,"?");
    }

    /**
     * Midi number to blue midi name converter.
     * @param blueMidiName is a blue midi name
     * @return the midi number corresponding to the given blue midi name
     */
    public int midiNumber(String midiName) {
        return midiMap.get(midiName);
    }

    /**
     * Referencer for the set of all blue midi names.
     * @return the set of all blue midi names: {C0, V0, ..., G10}
     */
    public Set<String> midiNameSet() {
        return midiMap.keySet();
    }

    /**
     * Number of the highest "register"
     */
    static public int HIGHLOCATION = 10;
    /**
     * Number of the lowest "register"
     */
    static public int LOWLOCATION = 0;

}
