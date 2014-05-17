/*
 * TextNote
 */

package note;

import javax.swing.JTextArea;

/**
 *
 * @author blue
 */
public class TextNote extends SilentNote implements Note {

    JTextArea textOutputArea;
    NoteFrame mcf;

    public TextNote(NoteFrame f) {
        super();
        mcf = f;
        textOutputArea = mcf.ta();
    }

    @Override
    public void play() {
        playCount++;
        String infoMode = "physical";
        if ( infoMode.equalsIgnoreCase("physical") ) {
            physicalPlay();
        } else if ( infoMode.equalsIgnoreCase("functional") ) {
            functionalPlay();
        } else if ( infoMode.equalsIgnoreCase("midi") ) {
            midiPlay();
        }
        if ( statshotFlag ) {
            mcf.ta().append("\n" + statshot()+"\n");
        }
    }

    private void midiPlay() {
       int midiNumber = scale.computeMidiNumber(degree, location);
       textOutputArea.append("(" + midiNumber + "," + duration + ")");
    }

    private void functionalPlay() {
        textOutputArea.append("(" + degree + "," + duration + ")");
    }

    private void physicalPlay() {
        int currentMidiNumber = scale.computeMidiNumber(degree, location);
        if ( previousMidiNumber == -1 ) {
            textOutputArea.append("");
        } else if ( previousMidiNumber == currentMidiNumber ) {
            textOutputArea.append("");
        } else {
            String di = computeDirectionIndicator(previousMidiNumber,currentMidiNumber);
            textOutputArea.append(di + " ");
        }
        previousMidiNumber = currentMidiNumber;
        String pcs = scale.computePitchClass(degree);
        textOutputArea.append("" + pcs + "" + duration + " ");
    }

    @Override
    public void rest() {
        restCount++;
        String infoMode = "physical";
        if ( infoMode.equalsIgnoreCase("physical") ) {
            physicalRest();
        } else if ( infoMode.equalsIgnoreCase("functional") ) {
            functionalRest();
        } else if ( infoMode.equalsIgnoreCase("midi") ) {
            midiRest();
        }
    }

    private void midiRest() {
       textOutputArea.append("(" + -1 + "," + duration + ")");
    }

    private void functionalRest() {
        textOutputArea.append("(" + 0 + "," + duration + ")");
    }

    private void physicalRest() {
        textOutputArea.append("" + "R" + "" + duration + " ");
    }

    @Override
    public void raisePitchX() {
        textOutputArea.append("Not supported yet.");
    }

    @Override
    public void lowerPitchX() {
        textOutputArea.append("Not supported yet.");
    }

    @Override
    public String toString() {
        return "Text" + super.toString();
    }

    @Override
    public void displayScaleStack() {
        textOutputArea.append("TOP OF SCALESTACK\n");
        for ( int i = scaleStack.size() - 1; i >= 0; i-- ) {
            Scale s = scaleStack.get(i);
            textOutputArea.append(s+"\n");
        }
        textOutputArea.append("BOTTOM OF SCALESTACK\n");
    }

    private String computeDirectionIndicator(int previousMidiNumber, int currentMidiNumber) {
        if ( previousMidiNumber == currentMidiNumber ) {
            return "";
        } else if ( previousMidiNumber < currentMidiNumber ) {
            return computeDirectionUpIndicator(previousMidiNumber,currentMidiNumber);
        } else {
            // textOutputArea.append("going down ...");
            return computeDirectionDownIndicator(previousMidiNumber,currentMidiNumber);
        }
    }

    private String computeDirectionUpIndicator(int previousMidiNumber, int currentMidiNumber) {
        if ( previousMidiNumber >= currentMidiNumber ) {
            return "";
        } else {
            return "/" + computeDirectionUpIndicator(previousMidiNumber+12, currentMidiNumber);
        }
    }

    private String computeDirectionDownIndicator(int previousMidiNumber, int currentMidiNumber) {
        if ( previousMidiNumber <= currentMidiNumber ) {
            return "";
        } else {
            return "\\" + computeDirectionDownIndicator(previousMidiNumber-12, currentMidiNumber);
        }
    }

}
