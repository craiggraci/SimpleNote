/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demos;

import note.SilentNote;

/**
 *
 * @author blue
 */
public class NoteDemo1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SilentNote sn = new SilentNote();
        sn.play();
        sn.raisePitch();
        sn.play();
        sn.lowerPitch();
        sn.play();
        for (int i = 1; i <= 10; i = i + 1) {
            sn.raisePitch();
            sn.play();
        }
                
    }
}
