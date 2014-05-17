/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package note;

/**
 *
 * @author blue
 */
public class EmptyScaleStackException extends Exception {

    /**
     * Creates a new instance of <code>EmptyScaleStackException</code> without detail message.
     */
    public EmptyScaleStackException() {
    }


    /**
     * Constructs an instance of <code>EmptyScaleStackException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public EmptyScaleStackException(String msg) {
        super(msg);
    }
}
