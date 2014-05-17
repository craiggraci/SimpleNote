/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package note;

/**
 *
 * @author blue
 */
public class NoSuchDegreeException extends Exception {

    /**
     * Creates a new instance of <code>NoSuchDegreeException</code> without detail message.
     */
    public NoSuchDegreeException() {
    }


    /**
     * Constructs an instance of <code>NoSuchDegreeException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public NoSuchDegreeException(String msg) {
        super(msg);
    }
}
