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
public class LittleTune {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SNote note = new SNote();
        phrase1(note);
        phrase2(note);
        phrase3(note);
        phrase4(note);
    }

    private static void phrase1(SNote note) {
        note.rp(2); note.play(); note.lp(2);
        note.rp(1); note.play(); note.lp(1);
        note.rp(2); note.play(); note.lp(2);
        note.rp(0); note.play(); note.lp(0);
        note.rp(1); note.play(); note.lp(1);
        note.rp(0); note.play(); note.lp(0);
        note.rp(1); note.x2(); note.play(); note.s2(); note.lp(1);
    }

    private static void phrase2(SNote note) {
        note.rp(2); note.play(); note.lp(2);
        note.rp(1); note.play(); note.lp(1);
        note.rp(2); note.play(); note.lp(2);
        note.rp(0); note.play(); note.lp(0);
        note.rp(1); note.x2(); note.play(); note.s2(); note.lp(1);
        note.rp(1); note.x2(); note.play(); note.s2(); note.lp(1);
    }

    private static void phrase3(SNote note) {
        phrase1(note);
    }

    private static void phrase4(SNote note) {
        note.rp(2); note.play(); note.lp(2);
        note.rp(1); note.play(); note.lp(1);
        note.rp(2); note.play(); note.lp(2);
        note.rp(1); note.play(); note.lp(1);
        note.rp(0); note.x2(); note.play(); note.s2(); note.lp(0);
        note.rp(0); note.x2(); note.play(); note.s2(); note.lp(0);
    }
    
}
