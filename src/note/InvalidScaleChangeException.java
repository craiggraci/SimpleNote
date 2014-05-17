/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package note;

/**
 *
 * @author blue
 */
public class InvalidScaleChangeException extends Exception {

    /**
     * Creates a new instance of <code>InvalidScaleChangeException</code> without detail message.
     */
    public InvalidScaleChangeException() {
    }


    /**
     * Constructs an instance of <code>InvalidScaleChangeException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public InvalidScaleChangeException(String msg) {
        super(msg);
    }
}
