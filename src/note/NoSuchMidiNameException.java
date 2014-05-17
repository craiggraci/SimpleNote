/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package note;

/**
 *
 * @author blue
 */
public class NoSuchMidiNameException extends Exception {

    /**
     * Creates a new instance of <code>NoSuchMidiNameException</code> without detail message.
     */
    public NoSuchMidiNameException() {
    }


    /**
     * Constructs an instance of <code>NoSuchMidiNameException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public NoSuchMidiNameException(String msg) {
        super(msg);
    }
}
