/*
 * Converters.java of utilities package of Music project.
 */

package utilities;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Conversion utilities.
 * @author blue
 */
public class Converters {

    public static String[] isolateStrings(String command) {
        StringTokenizer s = new StringTokenizer(command);
        int c = s.countTokens();
        String[] result = new String[c];
        int x = 0;
        while ( s.hasMoreTokens() ) {
            result[x] = s.nextToken();
            x++;
        }
        return result;
    }

    public static String removeSpecialCharacters(String s) {
        String result = "";
        String specials = ">:";
        for ( int i = 0; i < s.length(); i++ ) {
            String x = s.substring(i,i+1);
            if ( specials.indexOf(x) < 0 ) {
                result = result + x;
            }
        }
        return result;
    }

    static public String round(Double d) {
        String s = d.toString();
        if ( s.length() == 3 ) {
            return s;
        } else if ( s.length() == 4 ) {
            return s.substring(0,4);
        } else {
            return s.substring(0,5);
        }
    }

    public static ArrayList<String> stringToList(String[] a) {
       ArrayList<String> al = new ArrayList<String>();
       for ( String s : a ) {
           al.add(s);
       }
       return al;
    }

    /**
     * Convert an int array to a space delimited string.
     * @param a the int array
     * @return the space delimited string corresponding to the int array
     */
    static public String toString(int[] a) {
        String result = "";
        for ( int i = 0; i < a.length; i++ ) {
            result = result + a[i] + " ";
        }
        return result.trim();
    }

    static public boolean member(String s, String[] a) {
        for ( int i = 0; i < a.length; i++ ) {
            if ( s.equalsIgnoreCase(a[i]) ) {
                return true;
            }
        }
        return false;
    }

    static public String reduceSpaces(String term) {
        String result = term;
        boolean done = false;
        while ( ! done ) {
            result = result.replaceAll("  "," ");
            if ( result.indexOf("  ") < 0 ) { done = true; }
        }
        return result;
    }

    static public String removeWhiteSpace(String term) {
        String result = term;
        boolean done = false;
        while ( ! done ) {
            result = result.replaceAll(" ","");
            if ( result.indexOf(" ") < 0 ) { done = true; }
        }
        return result;
    }

    static public String removeMostWhiteSpace(String term) {
        // System.out.println("term = " + term);
        String result = "";
        boolean reducing = true;
        for ( int i = 0; i < term.length(); i++ ) {
            String s = term.substring(i,i+1);
            if ( s.equals("'") & reducing ) {
                reducing = false;
                result = result + s;
            } else if ( s.equals("'") & ( ! reducing ) ) {
                reducing = true;
                result = result + s;
            } else if ( reducing ) {
                if ( ! s.equals(" ") ) {
                   result = result + s;
                } else {

                }
            } else {
                result = result + s;
            }
        }
        // System.out.println("result = " + result);
        return result;
    }

    static public String[] separate(String input, String ch) {
        StringTokenizer st = new StringTokenizer(input,ch);
        String[] result = new String[st.countTokens()];
        int x = 0;
        while ( st.hasMoreTokens() ) {
            String token = st.nextToken();
            result[x] = token; x++;
        }
        return result;
    }


}
