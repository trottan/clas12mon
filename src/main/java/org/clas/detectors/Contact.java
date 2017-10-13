/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.clas.detectors;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author devita
 */
public class Contact  extends JTabbedPane {        
    JPanel monitoringPanel =  new JPanel(new BorderLayout());
    JPanel calibrationPanel = new JPanel(new BorderLayout());
    
    public Contact() {
        this.add("Monitoring Contacts", this.monitoringPanel);
        this.add("Calibration Contacts", this.calibrationPanel);
        JTextPane monitoringContacts = new JTextPane();
        monitoringContacts.setText("HTCC contact name name xx@jlab.org\nBlah blah blah.\nblah");
        this.monitoringPanel.add(monitoringContacts,BorderLayout.CENTER);
        monitoringContacts.setFont(new Font("Avenir",Font.PLAIN,20));
        monitoringContacts.setEditable(false);
        JTextPane calibrationContacts = new JTextPane();
        calibrationContacts.setText("HTCC contact name name xx@jlab.org\nBlah blah blah.\nblah");
        this.calibrationPanel.add(calibrationContacts,BorderLayout.CENTER);
        calibrationContacts.setFont(new Font("Avenir",Font.PLAIN,20));
        calibrationContacts.setEditable(false);
    }


}
