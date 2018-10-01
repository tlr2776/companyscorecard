/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package companyscorecard;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tara
 */
public class WriteCard {

    private FileWriter w;

    public WriteCard() {
        try {
            w = new FileWriter("weekly.txt");
        } catch (IOException ex) {
            Logger.getLogger(WriteCard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void dumpContents(int s) {
        try {
            w.append(s + "\n");
        } catch (IOException ex) {
            Logger.getLogger(WriteCard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void dumpContents() {
        try {
            w.append("\n");
        } catch (IOException ex) {
            Logger.getLogger(WriteCard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void endFile() {
        try {
            w.close();
        } catch (IOException ex) {
            Logger.getLogger(WriteCard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
