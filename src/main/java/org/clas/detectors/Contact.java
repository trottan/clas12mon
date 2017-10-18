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


public class Contact  extends JTabbedPane {        
    JPanel monitoringPanel =  new JPanel(new BorderLayout());
    JPanel calibrationPanel = new JPanel(new BorderLayout());
    
    public Contact() {
        this.add("Monitoring Contacts", this.monitoringPanel);
        this.add("Calibration Contacts", this.calibrationPanel);
        JTextPane monitoringContacts = new JTextPane();
        monitoringContacts.setText("\nContact for the online monitoring:\n\n"
                + "Stefan Diehl,   sdiehl@jlab.org,   757-269-570\n\n"
                + "Contact persons for the expert monitoring:\n\n"
                + "CND:         xx xx,    xx@jlab.org,   757-xxx-xxx\n"
                + "CTOF:        xx xx,    xx@jlab.org,   757-xxx-xxx\n"
                + "DC:            xx xx,    xx@jlab.org,   757-xxx-xxx\n"
                + "ECAL:        Cole Smith, lcsmith@jlab.org,   757-xxx-xxx\n"
                + "FTOF:         xx xx,    xx@jlab.org,   757-xxx-xxx\n"
                + "FTTRK:       xx xx,    xx@jlab.org,   757-xxx-xxx\n"
                + "FTHODO:   xx xx,    xx@jlab.org,   757-xxx-xxx\n"
                + "FTCAL:       Raffaella De Vita,      Raffaella.Devita@ge.infn.it\n"
                + "HTTC:         xx xx,    xx@jlab.org,   757-xxx-xxx\n"
                + "LTTC:          xx xx,    xx@jlab.org,   757-xxx-xxx\n"
                + "BMT+FMT:  Guillaume Christiaens,    guillaume.christiaens@cea.fr\n"
                + "                   Maxim Defurne,    mdefurne@jlab.org\n"
                + "SVT:            Yuri Gotra,    xx@jlab.org,   757-xxx-xxx");
        this.monitoringPanel.add(monitoringContacts,BorderLayout.CENTER);
        monitoringContacts.setFont(new Font("Avenir",Font.PLAIN,16));
        monitoringContacts.setEditable(false);
        JTextPane calibrationContacts = new JTextPane();
        calibrationContacts.setText("\nCalibration coordinators:\n\n"
                + "xx xx    xx@jlab.org,   757-xxx-xxx\n\n"
                + "Contact persons for the calibration of the subsystems:\n\n"
                + "CND:         xx xx,   xx@jlab.org,   757-xxx-xxx\n"
                + "CTOF:        xx xx,   xx@jlab.org,   757-xxx-xxx\n"
                + "DC:            xx xx,   xx@jlab.org,   757-xxx-xxx\n"
                + "EC:            xx xx,   xx@jlab.org,   757-xxx-xxx\n"
                + "FTOF:        xx xx,   xx@jlab.org,   757-xxx-xxx\n"
                + "FTTRK:       xx xx,   xx@jlab.org,   757-xxx-xxx\n"
                + "FTHODO:   xx xx,   xx@jlab.org,   757-xxx-xxx\n"
                + "FTCAL:       xx xx,   xx@jlab.org,   757-xxx-xxx\n"
                + "HTTC:         xx xx,   xx@jlab.org,   757-xxx-xxx\n"
                + "LTTC:         xx xx,   xx@jlab.org,   757-xxx-xxx\n"
                + "BMT+FMT:  xx xx,   xx@jlab.org,   757-xxx-xxx\n"
                + "SVT:           xx xx,   xx@jlab.org,   757-xxx-xxx");
        this.calibrationPanel.add(calibrationContacts,BorderLayout.CENTER);
        calibrationContacts.setFont(new Font("Avenir",Font.PLAIN,16));
        calibrationContacts.setEditable(false);
    }
      
}
