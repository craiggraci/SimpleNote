/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package note;

import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 *
 * @author blue
 */
public class ListOfAvailableSonicNotes extends ListOfAvailableNotes {

    private LinkedList<SonicNote> avail;
    NoteFrame mcf;

    public LinkedList<SonicNote> avail() {
        return avail;
    }

    public ListOfAvailableSonicNotes(NoteFrame f) {
        mcf = f;
        avail = new LinkedList<SonicNote>();
        avail.add(new SonicNote(f, "V0 "));
        avail.add(new SonicNote(f, "V1 "));
        avail.add(new SonicNote(f, "V2 "));
        avail.add(new SonicNote(f, "V3 "));
        avail.add(new SonicNote(f, "V4 "));
        avail.add(new SonicNote(f, "V5 "));
        avail.add(new SonicNote(f, "V6 "));
        avail.add(new SonicNote(f, "V7 "));
        avail.add(new SonicNote(f, "V8 "));
        avail.add(new SonicNote(f, "V10 "));
        avail.add(new SonicNote(f, "V11 "));
        avail.add(new SonicNote(f, "V12 "));
        avail.add(new SonicNote(f, "V13 "));
        avail.add(new SonicNote(f, "V14 "));
        avail.add(new SonicNote(f, "V15 "));
        // avail.add(new SonicNote(f, "V15 I[PIANO] "));
    }

    public SonicNote get() {
        SonicNote sn = avail.removeFirst();
        //mcf.ta().append("--- get: ");
        //mcf.ta().append(sn.id()+"\n");
        return sn;
    }

    public void save(SonicNote sn) {
        //mcf.ta().append("--- save: ");
        //mcf.ta().append(sn.id()+"\n");
        avail.addLast(sn);
    }

    public void display() {
        mcf.ta().append("BEGIN LIST OF AVAILABLE NOTES ...\n");
        for ( SonicNote sn : avail ) {
            mcf.ta().append(sn.musicString() + "\n");
        }
        mcf.ta().append("... END LIST OF AVAILABLE NOTES\n");
    }

    public void displayNotes() {
        for ( SonicNote sn : avail ) {
            mcf.ta().append(sn.toString() + "\n");
        }
    }

    void resetMusicStrings() {
        for ( SonicNote sn : avail ) {
            sn.musicString = sn.prefix;
        }
    }

}
