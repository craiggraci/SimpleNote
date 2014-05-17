/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package note;

/**
 *
 * @author blue
 */
public class NoSuchPitchClassException extends Exception {

    /**
     * Creates a new instance of <code>NoSuchPitchClassException</code> without detail message.
     */
    public NoSuchPitchClassException() {
    }


    /**
     * Constructs an instance of <code>NoSuchPitchClassException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public NoSuchPitchClassException(String msg) {
        super(msg);
    }
}
