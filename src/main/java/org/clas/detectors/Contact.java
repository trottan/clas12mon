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
    
    public Contact() {
        this.add("Monitoring Contacts", this.monitoringPanel);
        JTextPane monitoringContacts = new JTextPane();
        monitoringContacts.setText("\nContact for the online monitoring:\n\n"
                + "Stefan Diehl,   sdiehl@jlab.org,   757-269-570\n\n"
                + "Contact persons for the expert monitoring:\n\n"
                + "CND:            Daniel Carman,            carman@jlab.org,                   757-344-7204\n"
                + "CTOF:           Daniel Carman,            carman@jlab.org,                   757-344-7204\n"
                + "DC:               Mac Mesteyer,             mestayer@jlab.org,                757-584-5509\n"
                + "ECAL:           Cole Smith,                  lcsmith@jlab.org,                    757-810-1489\n"
                + "FTOF:           Daniel Carman,            carman@jlab.org,                   757-344-7204\n"
                + "FTTRK:          xx xx,                         xx@jlab.org,                           757-xxx-xxx\n"
                + "FTHODO:      xx xx,                         xx@jlab.org,                           757-xxx-xxx\n"
                + "FTCAL:          Raffaella De Vita,         Raffaella.Devita@ge.infn.it,     757-xxx-xxx\n"
                + "HTTC:            Youri Sharabian,          youris@jlab.org,                    757-344-7174\n"
                + "LTTC:             Maurizio Ungaro,        ungaro@jlab.org,                    757-329-4846\n"
                + "BMT+FMT:     Guillaume Christiaens, guillaume.christiaens@cea.fr, 757-xxx-xxx\n"
                + "                      Maxim Defurne,          mdefurne@jlab.org,                757-xxx-xxx\n"
                + "SVT:               Yuri Gotra,                  gotra@jlab.org,                      757-541-7539\n"
                + "DAQ:              Sergey Boiarinov,        boiarino@jlab.org,                 757-869-2188\n"
                + "Slow Control:  Nathan Baltzell,           baltzell@jlab.org,                   757-748-6922\n"
                + "Beamline:        Rafayel Paremuzyan,  xx@jlab.org,                          757-303-3996");
        this.monitoringPanel.add(monitoringContacts,BorderLayout.CENTER);
        monitoringContacts.setFont(new Font("Avenir",Font.PLAIN,16));
        monitoringContacts.setEditable(false);
    }
      
}
