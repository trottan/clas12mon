package org.clas.viewer;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.clas.detectors.*;
import org.jlab.detector.decode.CLASDecoder;
import org.jlab.detector.decode.CodaEventDecoder;
import org.jlab.detector.decode.DetectorEventDecoder;
import org.jlab.detector.view.DetectorListener;
import org.jlab.detector.view.DetectorPane2D;
import org.jlab.detector.view.DetectorShape2D;
import org.jlab.groot.base.GStyle;
import org.jlab.groot.data.TDirectory;
import org.jlab.groot.graphics.EmbeddedCanvasTabbed;
import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataEventType;
import org.jlab.io.evio.EvioDataEvent;
import org.jlab.io.hipo.HipoDataEvent;
import org.jlab.io.task.DataSourceProcessorPane;
import org.jlab.io.task.IDataEventListener;

/**
 *
 * @author ziegler
 */

public class EventViewer implements IDataEventListener, DetectorListener, ActionListener, ChangeListener {
    
    List<DetectorPane2D> DetectorPanels   	= new ArrayList<DetectorPane2D>();
    JTabbedPane tabbedpane           		= null;
    JPanel mainPanel 				= null;
    JMenuBar menuBar                            = null;
    DataSourceProcessorPane processorPane 	= null;
    EmbeddedCanvasTabbed CLAS12Canvas           = null;
    //EmbeddedCanvasTabbed CLAS12CDCanvas           = null;
    
    CodaEventDecoder               decoder = new CodaEventDecoder();
    CLASDecoder                clasDecoder = new CLASDecoder();
    DetectorEventDecoder   detectorDecoder = new DetectorEventDecoder();
           
    private int canvasUpdateTime   = 2000;
    private int analysisUpdateTime = 100;
    private int runNumber  = 0;
    
    // detector monitors
    DetectorMonitor[] monitors = {
        
                new BMTmonitor("BMT"),        // 0
                new BSTmonitor("BST"),        // 1
                new CNDmonitor("CND"),        // 2
                new CTOFmonitor("CTOF"),      // 3
                new DCmonitor("DC"),          // 4
                new ECmonitor("ECAL"),        // 5
                new FMTmonitor("FMT"),        // 6
                new FTCALmonitor("FTCAL"),    // 7
                new FTHODOmonitor("FTHODO"),  // 8
                new FTOFmonitor("FTOF"),      // 9
                new FTTRKmonitor("FTTRK"),    // 10
                new HTCCmonitor("HTCC"),      // 11
                new LTCCmonitor("LTCC"),      // 12
                new RICHmonitor("RICH"),      // 13
                new RECmonitor("RECON"),      // 14
             //   new TRKmonitor("TRK"),        // 15
                new RFmonitor("RF"),          // 15
                new HELmonitor("HEL"),        // 16
                new FCUPmonitor("Faraday Cup"),  // 17
                new TRIGGERmonitor("Trigger"),   // 18
     
    };
        
    public EventViewer() {    	
        		
	// create menu bar
        menuBar = new JMenuBar();
        JMenuItem menuItem;
        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_A);
        file.getAccessibleContext().setAccessibleDescription("File options");
        menuItem = new JMenuItem("Open histograms file", KeyEvent.VK_O);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Open histograms file");
        menuItem.addActionListener(this);
        file.add(menuItem);
        menuItem = new JMenuItem("Save histograms to file", KeyEvent.VK_S);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Save histograms to file");
        menuItem.addActionListener(this);
        file.add(menuItem);
        menuItem = new JMenuItem("Print histograms as png", KeyEvent.VK_B);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Print histograms as png");
        menuItem.addActionListener(this);
        file.add(menuItem);
        menuItem = new JMenuItem("Create histogram PDF", KeyEvent.VK_P);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Create historgram PDF");
        menuItem.addActionListener(this);
        file.add(menuItem);
        
        menuBar.add(file);
        JMenu settings = new JMenu("Settings");
        settings.setMnemonic(KeyEvent.VK_A);
        settings.getAccessibleContext().setAccessibleDescription("Choose monitoring parameters");
        menuItem = new JMenuItem("Set GUI update interval", KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Set GUI update interval");
        menuItem.addActionListener(this);
        settings.add(menuItem);
        menuItem = new JMenuItem("Set global z-axis log scale", KeyEvent.VK_L);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Set global z-axis log scale");
        menuItem.addActionListener(this);
        settings.add(menuItem);
        menuItem = new JMenuItem("Set global z-axis lin scale", KeyEvent.VK_R);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Set global z-axis lin scale");
        menuItem.addActionListener(this);
        settings.add(menuItem);
        menuBar.add(settings);
         
           
        // create main panel
        mainPanel = new JPanel();	
	mainPanel.setLayout(new BorderLayout());
        
      	tabbedpane 	= new JTabbedPane();

        processorPane = new DataSourceProcessorPane();
        processorPane.setUpdateRate(analysisUpdateTime);

        mainPanel.add(tabbedpane);
        mainPanel.add(processorPane,BorderLayout.PAGE_END);
        
    
        GStyle.getAxisAttributesX().setTitleFontSize(18);
        GStyle.getAxisAttributesX().setLabelFontSize(14);
        GStyle.getAxisAttributesY().setTitleFontSize(18);
        GStyle.getAxisAttributesY().setLabelFontSize(14);
        
        CLAS12Canvas    = new EmbeddedCanvasTabbed("FD");
        CLAS12Canvas.getCanvas("FD").divide(3,3);
        CLAS12Canvas.getCanvas("FD").setGridX(false);
        CLAS12Canvas.getCanvas("FD").setGridY(false); 
        CLAS12Canvas.addCanvas("CD");
        CLAS12Canvas.getCanvas("CD").divide(2,2);
        CLAS12Canvas.getCanvas("CD").setGridX(false);
        CLAS12Canvas.getCanvas("CD").setGridY(false);
        CLAS12Canvas.addCanvas("FT");
        CLAS12Canvas.getCanvas("FT").divide(1,3);
        CLAS12Canvas.getCanvas("FT").setGridX(false);
        CLAS12Canvas.getCanvas("FT").setGridY(false);
        
        
        JPanel    CLAS12View = new JPanel(new BorderLayout());
        JSplitPane splitPanel = new JSplitPane();
        splitPanel.setLeftComponent(CLAS12View);
        splitPanel.setRightComponent(CLAS12Canvas);
        JTextPane clas12Text   = new JTextPane();
        clas12Text.setText("CLAS12\n monitoring plots\n V2.0\n");
        clas12Text.setEditable(false);
        StyledDocument styledDoc = clas12Text.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        styledDoc.setParagraphAttributes(0, styledDoc.getLength(), center, false);
        clas12Text.setBackground(CLAS12View.getBackground());
        clas12Text.setFont(new Font("Avenir",Font.PLAIN,20));
        JLabel clas12Design = this.getImage("https://www.jlab.org/Hall-B/clas12-web/sidebar/clas12-design.jpg",0.1);
        JLabel clas12Logo   = this.getImage("https://www.jlab.org/Hall-B/pubs-web/logo/CLAS-frame-low.jpg", 0.3);
//        CLAS12View.add(clas12Name,BorderLayout.PAGE_START);
        CLAS12View.add(clas12Design);
        CLAS12View.add(clas12Text,BorderLayout.PAGE_END);

        tabbedpane.add(splitPanel,"Summary");
        tabbedpane.addChangeListener(this);
       
        for(int k =0; k<this.monitors.length; k++) {
                this.tabbedpane.add(this.monitors[k].getDetectorPanel(), this.monitors[k].getDetectorName());
        	        this.monitors[k].getDetectorView().getView().addDetectorListener(this);
        }
        
        this.tabbedpane.add(new Contact(),"Contacts");
        this.processorPane.addEventListener(this);
        
        this.tabbedpane.add(new Acronyms(),"Acronyms");
        this.processorPane.addEventListener(this);
        
        this.setCanvasUpdate(canvasUpdateTime);
        this.plotSummaries();
        
        
    }
      
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        if(e.getActionCommand()=="Set GUI update interval") {
            this.chooseUpdateInterval();
        }
        if(e.getActionCommand()=="Set global z-axis log scale") {
        	   for(int k=0; k<this.monitors.length; k++) {this.monitors[k].setLogZ(true);this.monitors[k].plotHistos();}
        }
        if(e.getActionCommand()=="Set global z-axis lin scale") {
           for(int k=0; k<this.monitors.length; k++) {this.monitors[k].setLogZ(false);this.monitors[k].plotHistos();}
        }
        if(e.getActionCommand()=="Open histograms file") {
            String fileName = null;
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            File workingDirectory = new File(System.getProperty("user.dir"));  
            fc.setCurrentDirectory(workingDirectory);
            int option = fc.showOpenDialog(null);
            if (option == JFileChooser.APPROVE_OPTION) {
                fileName = fc.getSelectedFile().getAbsolutePath();            
            }
            if(fileName != null) this.loadHistosFromFile(fileName);
        }        
        if(e.getActionCommand()=="Print histograms as png") {
            this.printHistosToFile();
        }
        if(e.getActionCommand()=="Create histogram PDF") {
            this.createHistoPDF();
        }
        if(e.getActionCommand()=="Save histograms to file") {
            DateFormat df = new SimpleDateFormat("MM-dd-yyyy_hh.mm.ss_aa");
            String fileName = "mon12_" + this.runNumber + "_" + df.format(new Date()) + ".hipo";
            JFileChooser fc = new JFileChooser();
            File workingDirectory = new File(System.getProperty("user.dir"));   
            fc.setCurrentDirectory(workingDirectory);
            File file = new File(fileName);
            fc.setSelectedFile(file);
            int returnValue = fc.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
               fileName = fc.getSelectedFile().getAbsolutePath();            
            }
            this.saveHistosToFile(fileName);
        }
    }

    public void chooseUpdateInterval() {
        String s = (String)JOptionPane.showInputDialog(
                    null,
                    "GUI update interval (ms)",
                    " ",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "1000");
        if(s!=null){
            int time = 1000;
            try { 
                time= Integer.parseInt(s);
            } catch(NumberFormatException e) { 
                JOptionPane.showMessageDialog(null, "Value must be a positive integer!");
            }
            if(time>0) {
                this.setCanvasUpdate(time);
            }
            else {
                JOptionPane.showMessageDialog(null, "Value must be a positive integer!");
            }
        }
    }
        
    private JLabel getImage(String path,double scale) {
        JLabel label = null;
        Image image = null;
        try {
            URL url = new URL(path);
            image = ImageIO.read(url);
        } catch (IOException e) {
        	e.printStackTrace();
                System.out.println("Picture upload from " + path + " failed");
        }
        ImageIcon imageIcon = new ImageIcon(image);
        double width  = imageIcon.getIconWidth()*scale;
        double height = imageIcon.getIconHeight()*scale;
        imageIcon = new ImageIcon(image.getScaledInstance((int) width,(int) height, Image.SCALE_SMOOTH));
        label = new JLabel(imageIcon);
        return label;
    }
    
    public JPanel  getPanel(){
        return mainPanel;
    }
    
    private int getRunNumber(DataEvent event) {
        int rNum = this.runNumber;
        DataBank bank = event.getBank("RUN::config");
        if(bank!=null) {
            rNum      = bank.getInt("run", 0);
        }
        return rNum;
    }
    
    @Override
    public void dataEventAction(DataEvent event) {
    	
       // EvioDataEvent decodedEvent = deco.DecodeEvent(event, decoder, table);
        //decodedEvent.show();
        		
        HipoDataEvent hipo = null;
	if(event!=null ){
//            event.show();

            if (event.getType() == DataEventType.EVENT_START) {
                this.runNumber = this.getRunNumber(event);
            }
            if(this.runNumber != this.getRunNumber(event)) {
//                this.saveToFile("mon12_histo_run_" + runNumber + ".hipo");
                this.runNumber = this.getRunNumber(event);
                resetEventListener();
            }
            if(event instanceof EvioDataEvent){
             	hipo = (HipoDataEvent) clasDecoder.getDataEvent(event);
            } 
            else {
                hipo = (HipoDataEvent) event;
                
            }
            
            for(int k=0; k<this.monitors.length; k++) {
                this.monitors[k].dataEventAction(hipo);
            }      
	}
    }

    public void loadHistosFromFile(String fileName) {
        // TXT table summary FILE //
        System.out.println("Opening file: " + fileName);
        TDirectory dir = new TDirectory();
        dir.readFile(fileName);
        System.out.println(dir.getDirectoryList());
        dir.cd();
        dir.pwd();
        
        for(int k=0; k<this.monitors.length; k++) {
            this.monitors[k].readDataGroup(dir);
        }
        this.plotSummaries();
    }

    public void plotSummaries() {
        
        /////////////////////////////////////////////////
        /// FD:
        
        // DC
        this.CLAS12Canvas.getCanvas("FD").cd(0);
        if(this.monitors[4].getDetectorSummary()!=null) this.CLAS12Canvas.getCanvas("FD").draw(this.monitors[4].getDetectorSummary().getH1F("summary")); 
        // HTTC
        this.CLAS12Canvas.getCanvas("FD").cd(1);
        if(this.monitors[11].getDetectorSummary()!=null) this.CLAS12Canvas.getCanvas("FD").draw(this.monitors[11].getDetectorSummary().getH1F("summary"));
        // LTTC
        this.CLAS12Canvas.getCanvas("FD").cd(2);
        if(this.monitors[12].getDetectorSummary()!=null) this.CLAS12Canvas.getCanvas("FD").draw(this.monitors[12].getDetectorSummary().getH1F("summary"));
        // RICH
        this.CLAS12Canvas.getCanvas("FD").cd(3);
        this.CLAS12Canvas.getCanvas("FD").getPad(3).getAxisZ().setLog(true);
        if(this.monitors[13].getDetectorSummary()!=null) this.CLAS12Canvas.getCanvas("FD").draw(this.monitors[13].getDetectorSummary().getH2F("summary"));
        
        // ECAL 
        this.CLAS12Canvas.getCanvas("FD").cd(4);
        if(this.monitors[5].getDetectorSummary()!=null) this.CLAS12Canvas.getCanvas("FD").draw(this.monitors[5].getDetectorSummary().getH1F("sumPCAL"));
        this.CLAS12Canvas.getCanvas("FD").cd(5);
        if(this.monitors[5].getDetectorSummary()!=null) this.CLAS12Canvas.getCanvas("FD").draw(this.monitors[5].getDetectorSummary().getH1F("sumECin"));
   
        // FMT:
        this.CLAS12Canvas.getCanvas("FD").cd(6);
        this.CLAS12Canvas.getCanvas("FD").getPad(6).getAxisZ().setLog(true);
        if(this.monitors[6].getDetectorSummary()!=null) this.CLAS12Canvas.getCanvas("FD").draw(this.monitors[6].getDetectorSummary().getH2F("summary"));
        
        // FTOF:
        this.CLAS12Canvas.getCanvas("FD").cd(7);
        if(this.monitors[9].getDetectorSummary()!=null) this.CLAS12Canvas.getCanvas("FD").draw(this.monitors[9].getDetectorSummary().getH1F("sum_p1"));
        this.CLAS12Canvas.getCanvas("FD").cd(8);
        if(this.monitors[9].getDetectorSummary()!=null) this.CLAS12Canvas.getCanvas("FD").draw(this.monitors[9].getDetectorSummary().getH1F("sum_p2"));
        
        //////////////////////////////////////////////////
        ///  CD:
        
        // CND
        this.CLAS12Canvas.getCanvas("CD").cd(0);
        if(this.monitors[2].getDetectorSummary()!=null) this.CLAS12Canvas.getCanvas("CD").draw(this.monitors[2].getDetectorSummary().getH1F("summary"));
        // CTOF
        this.CLAS12Canvas.getCanvas("CD").cd(1);
        if(this.monitors[3].getDetectorSummary()!=null) this.CLAS12Canvas.getCanvas("CD").draw(this.monitors[3].getDetectorSummary().getH1F("summary"));
        // BVT
        this.CLAS12Canvas.getCanvas("CD").cd(2);
        this.CLAS12Canvas.getCanvas("CD").getPad(2).getAxisZ().setLog(true);
        if(this.monitors[0].getDetectorSummary()!=null) this.CLAS12Canvas.getCanvas("CD").draw(this.monitors[0].getDetectorSummary().getH2F("summary"));
        // BST
        this.CLAS12Canvas.getCanvas("CD").cd(3);
        this.CLAS12Canvas.getCanvas("CD").getPad(3).getAxisZ().setLog(true);
        if(this.monitors[1].getDetectorSummary()!=null) this.CLAS12Canvas.getCanvas("CD").draw(this.monitors[1].getDetectorSummary().getH2F("summary"));
        
        
        
        ///////////////////////////////////////////////////
        // FT:
        
        // FTCAL
        this.CLAS12Canvas.getCanvas("FT").cd(0);
        if(this.monitors[7].getDetectorSummary()!=null) this.CLAS12Canvas.getCanvas("FT").draw(this.monitors[7].getDetectorSummary().getH1F("summary"));
        // FTHODO
        this.CLAS12Canvas.getCanvas("FT").cd(1);
        if(this.monitors[8].getDetectorSummary()!=null) this.CLAS12Canvas.getCanvas("FT").draw(this.monitors[8].getDetectorSummary().getH1F("summary"));
        // FTTRK
        this.CLAS12Canvas.getCanvas("FT").cd(2);
        if(this.monitors[0].getDetectorSummary()!=null) this.CLAS12Canvas.getCanvas("FT").draw(this.monitors[10].getDetectorSummary().getH1F("summary"));
        
        ////////////////////////////////////////////////////
        
  
 
    }
    
    public void printHistosToFile() {
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy_hh.mm.ss_aa");
        String data = System.getProperty("user.dir") + "/output" + "/clas12mon_" + this.runNumber + "_" + df.format(new Date());        
        File theDir = new File(data);
        // if the directory does not exist, create it
        if (!theDir.exists()) {
            boolean result = false;
            try{
                theDir.mkdir();
                result = true;
            } 
            catch(SecurityException se){
                //handle it
            }        
            if(result) {    
            System.out.println("Created directory: " + data);
            }
        }
        String fileName = data + "/clas12_canvas.png";
        System.out.println(fileName);
       // this.CLAS12Canvas.getCanvas("CLAS12-summary").save(fileName);
        for(int k=0; k<this.monitors.length; k++) {
            this.monitors[k].printCanvas(data);
        }
    }
    
    
    public void createHistoPDF() {
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy_hh.mm.ss_aa");
        String data = System.getProperty("user.dir") + "/output" + "/clas12mon_" + this.runNumber + "_" + df.format(new Date());        
        File theDir = new File(data);
        // if the directory does not exist, create it
        if (!theDir.exists()) {
            boolean result = false;
            try{
                theDir.mkdir();
                result = true;
            } 
            catch(SecurityException se){
                //handle it
            }        
            if(result) {    
            System.out.println("Created directory: " + data);
            }
        }
        
        String fileName = data + "/clas12_canvas.pdf";
        System.out.println(fileName);
        
       // this.CLAS12Canvas.getCanvas("CLAS12-summary").save(fileName);
        for(int k=0; k<this.monitors.length; k++) {
            this.monitors[k].printCanvas(data);
        }
        
    }
    
    

    @Override
    public void processShape(DetectorShape2D shape) {
        System.out.println("SHAPE SELECTED = " + shape.getDescriptor());
    }
    
    @Override
    public void resetEventListener() {
        for(int k=0; k<this.monitors.length; k++) {
            this.monitors[k].resetEventListener();
            this.monitors[k].timerUpdate();
        }      
        this.plotSummaries();
    }
    
    public void saveHistosToFile(String fileName) {
        // TXT table summary FILE //
        TDirectory dir = new TDirectory();
        for(int k=0; k<this.monitors.length; k++) {
            this.monitors[k].writeDataGroup(dir);
        }
        System.out.println("Saving histograms to file " + fileName);
        dir.writeFile(fileName);
    }
        
    public void setCanvasUpdate(int time) {
        System.out.println("Setting " + time + " ms update interval");
        this.canvasUpdateTime = time;
        this.CLAS12Canvas.getCanvas("FD").initTimer(time);
        this.CLAS12Canvas.getCanvas("FD").update();
        this.CLAS12Canvas.getCanvas("CD").initTimer(time);
        this.CLAS12Canvas.getCanvas("CD").update();
        this.CLAS12Canvas.getCanvas("FT").initTimer(time);
        this.CLAS12Canvas.getCanvas("FT").update();
        for(int k=0; k<this.monitors.length; k++) {
            this.monitors[k].setCanvasUpdate(time);
        }
    }

    public void stateChanged(ChangeEvent e) {
        this.timerUpdate();
    }
    
    @Override
    public void timerUpdate() {
//        System.out.println("Time to update ...");
        for(int k=0; k<this.monitors.length; k++) {
            this.monitors[k].timerUpdate();
        }
   }

    public static void main(String[] args){
        JFrame frame = new JFrame("CLAS12Mon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        EventViewer viewer = new EventViewer();
        //frame.add(viewer.getPanel());
        frame.add(viewer.mainPanel);
        frame.setJMenuBar(viewer.menuBar);
        frame.setSize(1400, 800);
        frame.setVisible(true);
    }
   
}