/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demos;

import note.SNote;

/**
 *
 * @author blue
 */
public class NoteDemo2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SNote sn = new SNote();
        sn.play();
        sn.raisePitch();
        sn.play();
        sn.lowerPitch();
        sn.play();
        for ( int i = 1; i <= 8; i++ ) {
            sn.play();
            sn.raisePitch();
        }
    }
}
