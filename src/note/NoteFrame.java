/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package note;

import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 *
 * @author blue
 */
public class NoteFrame extends JFrame {
    JTextArea jta;
    
    public NoteFrame() 
    {
        jta = new JTextArea();
        add(jta);
    }
    
    public JTextArea ta() {
        return jta;
    }
}
