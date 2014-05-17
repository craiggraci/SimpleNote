/*
 * SonicNote
 */

package note;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import org.jfugue.*;

/**
 *
 * @author blue
 */
public class SNote extends SilentNote implements Note {

    static boolean padFlag = true;

    public static void setPadFlag(boolean b) {
        padFlag = b;
    }

    JTextArea textOutputArea;
    Player player;
    String musicString;
    String prefix;
    String id;
    private double localTime;

//    public SonicNote(MxMControlFrame f) {
//        super();
//        mcf = f;
//        textOutputArea = mcf.ta();
//        musicString = "";
//    }
//
    public SNote(NoteFrame f, String prefix) {
        super();
        mcf = f;
        textOutputArea = mcf.ta();
        player = new Player();
        this.prefix = prefix;
        musicString = prefix;
        id = prefix.substring(1);
        localTime = 0.0;
    }

    public SNote() {
        super();
        player = new Player();
        this.prefix = "V0 ";
        musicString = prefix;
        id = prefix.substring(1);
        localTime = 0.0;
    }

    public void appendToMusicString(String s) {
        musicString = musicString + s + " ";
    }

    public double localTime() {
        return localTime;
    }

    public String id() {
        return id;
    }

    public void resetMusicString() {
        musicString = prefix;
    }

    public String musicString() {
        return musicString;
    }

    public void playMusicString() {
        System.out.println(">>> playMusicString() in SimpleNote\n");
        System.out.println("--- musicString=" + musicString);
        player = new Player();
        player.play(musicString);
        resetMusicString();
    }

    @Override
    public String toString() {
        return "Sonic" + super.toString() +
            " id=" + id + " musicString=" + musicString;
    }

//    @Override
//    public void play() {
//        musicString = musicString + jfuguelNote() + " ";
//        textOutputArea.append("MUSICSTRING=" + musicString+"\n");
//        if ( ! padFlag ) { return; }
//        String filler = "R" + durationInJFuguel();
//        for ( SonicNote sn : completeSonicNoteSet() ) {
//            if ( isInactiveP(sn) ) {
//                sn.musicString = sn.musicString + filler + " ";
//            }
//        }
//    }

    /**
     * play the note 
     * -- proper pitch for the proper duration at proper amplitude
     * -- possibly perform a trace or display a textual representation
     * -- also, gather statistics
     */
    @Override
    public void play() {
        playCount++;
        durationSeq.add(duration.value()); // for nPVI computation
        //double globalTime = globalTime();
        //textOutputArea.append("localTime="+localTime+" | globalTime=" + globalTime + "\n");
        //if ( globalTime > localTime ) {
        //    double diff = globalTime - localTime;
        //    String theRest = "R" + "/" + diff;
        //    musicString = musicString + theRest + " ";
        //}
        musicString = jfuguelNote() + " ";
        ptrace();
        ptext();
        playMusicString();
        System.out.println("MUSICSTRING in play of SNote of SimpleNote = " + musicString+"\n");
        //mcf.ta().append("MUSICSTRING in SNote of SimpleNote =" + musicString+"\n");
        localTime = localTime + rtdurationInJFuguel();
//        if ( ! padFlag ) { return; }
//        String filler = "R" + durationInJFuguel();
//        for ( SonicNote sn : completeSonicNoteSet() ) {
//            if ( isInactiveP(sn) ) {
//                sn.musicString = sn.musicString + filler + " ";
//            }
//        }
        if ( statshotFlag ) {
            mcf.ta().append(statshot()+"\n");
        }
    }
    
    public void playLong() {
        x2(); play(); s2();
    }

    public void playLong(int n) {
        for ( int i = 1; i <= n; i++ ) {
            x2(); play(); s2();
        }
    }

    public void playShort() {
        s2(); play(); x2();
    }

    public void playShort(int n) {
        for ( int i = 1; i <= n; i++ ) {
            s2(); play(); x2();
        }
    }

    public void playDot() {
        x3(); s2(); play(); x2(); s3();
    }

    public void playDot(int n) {
        for ( int i = 1; i <= n; i++ ) {
            x3(); s2(); play(); x2(); s3();
        }
    }

    public void playDit() {
        x2(); s3(); play(); x3(); s2();
    }

    public void playDit(int n) {
        for ( int i = 1; i <= n; i++ ) {
            x2(); s3(); play(); x3(); s2();
        }
    }

    public void playLongDot() {
        x2(); playDot(); s2();
    }

    public void playLongDot(int n) {
        for ( int i = 1; i <= n; i++ ) {
            x2(); playDot(); s2();
        }
    }

    public void playShortDot() {
        s2(); playDot(); x2();
    }

    public void playShortDot(int n) {
        for ( int i = 1; i <= n; i++ ) {
            s2(); playDot(); x2();
        }
    }

    public void playLongDit() {
        x2(); playDit(); s2();
    }

    public void playLongDit(int n) {
        for ( int i = 1; i <= n; i++ ) {
            x2(); playDit(); s2();
        }
    }

    public void playShortDit() {
        s2(); playDit(); x2();
    }

    public void playShortDit(int n) {
        for ( int i = 1; i <= n; i++ ) {
            s2(); playDit(); x2();
        }
    }

    public void playLongLong() {
        x2(); playLong(); s2();
    }

    public void playLongLong(int n) {
        for ( int i = 1; i <= n; i++ ) {
            x2(); playLong(); s2();
        }
    }

    public void playShortShort() {
        s2(); playShortShort(); x2();
    }

    public void playShortShort(int n) {
        for ( int i = 1; i <= n; i++ ) {
            s2(); playShortShort(); x2();
        }
    }


    @Override
    public void changeVolume(String command) {
        super.changeVolume(command);
        musicString = musicString + "X[Volume]=" + volume + " ";
        // textOutputArea.append("MUSICSTRING=" + musicString+"\n");
    }
    



    @Override
    public void rest() {
        restCount++;
        durationSeq.add(duration.value()); // for nPVI computation
        //musicString = musicString + jfuguelRest() + " ";
        int vol = super.getVolume();
        musicString = "X[Volume]=0 " +  jfuguelNote() + " " + "X[Volume]=" + vol + " ";
        super.rest();
        playMusicString();
        localTime = localTime + rtdurationInJFuguel();
        //textOutputArea.append("MUSICSTRING of rest in SNote of SimpleNote =" + musicString+"\n");       
        System.out.println("MUSICSTRING of rest in SNote of SimpleNote = " + musicString+"\n");
        // textOutputArea.append("MUSICSTRING=" + musicString+"\n");
//        if ( ! padFlag ) { return; }
//        String filler = "R" + durationInJFuguel();
//        for ( SonicNote sn : completeSonicNoteSet() ) {
//            if ( isInactiveP(sn) ) {
//                sn.musicString = sn.musicString + filler + " ";
//            }
//        }
    }

    private String jfuguelNote() {
       String pcs = scale.computePitchClass(degree);
       pcs = modifyForJFugue(pcs);
       //textOutputArea.append("begin display the note\n");
       //textOutputArea.append(toString() + "\n");
       //textOutputArea.append("end display the note\n");
       String note = pcs + location + durationInJFuguel();
       //textOutputArea.append("MUSICNOTE=" + note+"\n");
       // player.play(note);
       return note;
    }

    private String jfuguelRest() {
       String pcs = "R";
       String rest = pcs + durationInJFuguel();
       // textOutputArea.append("MUSICREST=" + rest+"\n");
       // player.play(note);
       return rest;
    }

    private String modifyForJFugue(String pc) {
        if ( pc.equals("V") ) { return "Db"; }
        if ( pc.equals("W") ) { return "Eb"; }
        if ( pc.equals("X") ) { return "Gb"; }
        if ( pc.equals("Y") ) { return "Ab"; }
        if ( pc.equals("Z") ) { return "Bb"; }
        return pc;
    }

    private String durationInJFuguel() {
        if ( duration.eight() ) { return "ww"; }
        if ( duration.four() ) { return "w"; }
        if ( duration.two() ) { return "h"; }
        if ( duration.one() ) { return "q"; }
        if ( duration.onehalf() ) { return "i"; }
        if ( duration.onefourth() ) { return "s"; }
        double dur = duration.value()/4.0; // was duration.value()/4
        return "/"+dur;
        // throw new JFuguelException();
    }

    private double rtdurationInJFuguel() {
        double dur = duration.value()/4.0; // was duration.value()/4
        return dur;
        // throw new JFuguelException();
    }

//    private void displayTheMusicStrings(ListOfAvailableSonicNotes avail) {
//        avail.display();
//    }
//
    public void ready() {
        player.close();
//        LinkedList<SonicNote> csns = mcf.interpreter().getCompleteSonicNoteSet();
//        for ( SonicNote sn : csns ) {
//            sn.musicString  = sn.prefix;
//        }

    }


    private boolean memberp(String id, LinkedList<String> activeNoteIdentifiers) {
        for ( String s : activeNoteIdentifiers ) {
            if ( s.equalsIgnoreCase(id) ) {
                return true;
            }
        }
        return false;
    }

    private void displayTheMusicStrings(LinkedList<SonicNote> completeSonicNoteSet) {
        for ( SonicNote sn : completeSonicNoteSet ) {
            textOutputArea.append(sn.musicString()+"\n");
        }
    }

    private void displayTheTimes(LinkedList<SonicNote> completeSonicNoteSet) {
        // throw new UnsupportedOperationException("Not yet implemented");
    }

    private String replace(String body, String x, String n) {
        int p = body.indexOf(x);
        if ( p < 0 ) {
            return body;
        }
        String pre = body.substring(0,p);
        String mid = n;
        String pos = body.substring(p+x.length());
        String result = pre + mid + pos;
        textOutputArea.append("result="+result+"\n");
        return result;
    }

    private LinkedList<String> activeNoteIdentifiers() {
        //return activeNoteStack.top();
        return null;
    }









    public void fillTime(SonicNote parent) {
        double delta = parent.localTime() - localTime;
        String filler = "R/"+ delta;
        musicString = musicString + filler + " ";
        localTime = parent.localTime();
    }

    @Override
    public void displayScaleStack() {
        //System.out.println("TOP OF SCALESTACK");
        for ( int i = scaleStack.size() - 1; i >= 0; i-- ) {
            Scale s = scaleStack.get(i);
            textOutputArea.append(s+"\n");
            System.out.println(s);
        }
        //System.out.println("BOTTOM OF SCALESTACK");
    }

    public int pushscaleCount() {
        return pushscaleCount;
    }

    public int popscaleCount() {
        return popscaleCount;
    }

    public void transferPropertiesTo(SonicNote n) {
        super.transferPropertiesTo(n);
    }

    private boolean trace = false;
    private boolean text = false;
    
    private void ptrace() {
        if ( trace ) { super.play(); }
    }
    
    private void ptext() {
        if ( text ) { super.play(); }
    }
    
    public void trace() {
        trace = true;
        text = false;
    }

    public void untrace() {
        trace = false;
        text = false;
    }

    public void text() {
        text = true;
        trace = false;
    }

    public void untext() {
        text = false;
        trace = false;
    }


}
