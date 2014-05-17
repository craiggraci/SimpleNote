/*
 * Key.java of the note package of the Music project
 */

package note;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Model a key in terms of "blue" ("black key") identifiers
 * <pre>
 * Key    
 * C    
 * V
 * D    
 * W
 * E  
 * F  
 * X
 * G  
 * Y
 * A
 * Z
 * B   
 * </pre>
 *
 * @author blue
 */

public class Key {

    static private Map<String,Key> nameKeyMap;

    static public void establishNameKeyMap() {
        nameKeyMap = new HashMap();
        nameKeyMap.put("A",A);
        nameKeyMap.put("B",B);
        nameKeyMap.put("C",C);
        nameKeyMap.put("D",D);
        nameKeyMap.put("E",E);
        nameKeyMap.put("F",F);
        nameKeyMap.put("G",G);
        nameKeyMap.put("V",V);
        nameKeyMap.put("W",W);
        nameKeyMap.put("X",X);
        nameKeyMap.put("Y",Y);
        nameKeyMap.put("Z",Z);
    }

    public static boolean isKeyName(String command) {
        return ( nameKeyMap.get(command) != null );
    }

    public static Key computeKeyFromName(String command) {
        return nameKeyMap.get(command);
    }

    public Key(String n) {
        name = n;
    }

    private String name;

    @Override
    public String toString() {
        return "[Key: name=" + name + "]";
    }

    public String name() {
        return name;
    }

    public boolean equals(Key k) {
        return name.equalsIgnoreCase(k.name());
    }

    static final public Key C = new Key("C");
    static final public Key V = new Key("V");
    static final public Key D = new Key("D");
    static final public Key W = new Key("W");
    static final public Key E = new Key("E");
    static final public Key F = new Key("F");
    static final public Key X = new Key("X");
    static final public Key G = new Key("G");
    static final public Key Y = new Key("Y");
    static final public Key A = new Key("A");
    static final public Key Z = new Key("Z");
    static final public Key B = new Key("B");

}
