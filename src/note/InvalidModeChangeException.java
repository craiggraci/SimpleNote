/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package note;

/**
 *
 * @author blue
 */
public class InvalidModeChangeException extends Exception {

    /**
     * Creates a new instance of <code>InvalidModeChangeException</code> without detail message.
     */
    public InvalidModeChangeException() {
    }


    /**
     * Constructs an instance of <code>InvalidModeChangeException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public InvalidModeChangeException(String msg) {
        super(msg);
    }
}
