package org.clas.viewer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import org.jlab.detector.base.DetectorOccupancy;
import org.jlab.detector.view.DetectorPane2D;
import org.jlab.groot.base.GStyle;
import org.jlab.groot.data.IDataSet;
import org.jlab.groot.data.TDirectory;
import org.jlab.groot.graphics.EmbeddedCanvasTabbed;
import org.jlab.groot.group.DataGroup;
import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataEventType;
import org.jlab.io.task.IDataEventListener;
import org.jlab.utils.groups.IndexedList;


public class DetectorMonitor implements IDataEventListener, ActionListener {    
    
    private final String           detectorName;
    private ArrayList<String>      detectorTabNames  = new ArrayList();
    private IndexedList<DataGroup> detectorData      = new IndexedList<DataGroup>(3);
    private DataGroup              detectorSummary   = null;
    private DetectorOccupancy      detectorOccupancy = new DetectorOccupancy();
    private JPanel                 detectorPanel     = null;
    private EmbeddedCanvasTabbed   detectorCanvas    = null;
    private DetectorPane2D         detectorView      = null;
    private ButtonGroup            bG1               = null;
    private int                    numberOfEvents;
    private Boolean                sectorButtons     = false;
    private int                 detectorActiveSector = 1;
    private Boolean                     detectorLogZ = true;
    private Boolean                             isTB = false;
    private JRadioButton bS1,bS2,bS3,bS4,bS5,bS6;
    private JCheckBox        tbBtn;
    
    public int bitsec = 0;
    public int trigger = 0;
    public long triggerPhase = 0;
    public int trigFD = 0;
    public int trigCD = 0;
    
    public int trig1 = 0;
    public int trig2 = 0;
    public int trig3 = 0;
    public int trig4 = 0;
    public int trig5 = 0;
    public int trig6 = 0;
    public int trig7 = 0;
    public int trig8= 0;
    public int trig9 = 0;
    public int trig10 = 0;
    public int trig11 = 0;
    public int trig12 = 0;
    public int trig13 = 0;
    public int trig14 = 0;
    public int trig15 = 0;
    public int trig16 = 0;
    public int trig17 = 0;
    public int trig18 = 0;
    public int trig19 = 0;
    public int trig20 = 0;
    public int trig21 = 0;
    public int trig22 = 0;
    public int trig23 = 0;
    public int trig24 = 0;
    public int trig25 = 0;
    public int trig26 = 0;
    public int trig27 = 0;
    public int trig28 = 0;
    public int trig29 = 0;
    public int trig30 = 0;
    public int trig31 = 0;
    public int trig32 = 0;
    
    public boolean testTrigger = false;
    
    public boolean TriggerBeam1 = false;
    public boolean TriggerBeam2 = false;
    public boolean TriggerBeam3 = false;
    public boolean TriggerBeam4 = false;
    public boolean TriggerBeam5 = false;
    public boolean TriggerBeam6 = false;
    public boolean TriggerBeam7 = false;
    public boolean TriggerBeam8 = false;
    public boolean TriggerBeam9 = false;
    public boolean TriggerBeam10 = false;
    public boolean TriggerBeam11 = false;
    public boolean TriggerBeam12 = false;
    public boolean TriggerBeam13 = false;
    public boolean TriggerBeam14 = false;
    public boolean TriggerBeam15 = false;
    public boolean TriggerBeam16 = false;
    public boolean TriggerBeam17 = false;
    public boolean TriggerBeam18 = false;
    public boolean TriggerBeam19 = false;
    public boolean TriggerBeam20 = false;
    public boolean TriggerBeam21 = false;
    public boolean TriggerBeam22 = false;
    public boolean TriggerBeam23 = false;
    public boolean TriggerBeam24 = false;
    public boolean TriggerBeam25 = false;
    public boolean TriggerBeam26 = false;
    public boolean TriggerBeam27 = false;
    public boolean TriggerBeam28 = false;
    public boolean TriggerBeam29 = false;
    public boolean TriggerBeam30 = false;
    public boolean TriggerBeam31 = false;
    public boolean TriggerBeam32 = false;
    
    public int eventResetTime_current[]=new int[19];
    public int eventResetTime_default[]=new int[19];    
    
    public DetectorMonitor(String name){
        GStyle.getAxisAttributesX().setTitleFontSize(18);
        GStyle.getAxisAttributesX().setLabelFontSize(14);
        GStyle.getAxisAttributesY().setTitleFontSize(18);
        GStyle.getAxisAttributesY().setLabelFontSize(14);
        this.detectorName = name;
        this.detectorPanel  = new JPanel();
        this.detectorCanvas = new EmbeddedCanvasTabbed();
        this.detectorView   = new DetectorPane2D();
        this.numberOfEvents = 0;
        
        eventResetTime_current[0]=0;
        eventResetTime_current[1]=0;
        eventResetTime_current[2]=0;
        eventResetTime_current[3]=0;
        eventResetTime_current[4]=0;
        eventResetTime_current[5]=0;
        eventResetTime_current[6]=0;
        eventResetTime_current[7]=0;
        eventResetTime_current[8]=0;
        eventResetTime_current[9]=0;
        eventResetTime_current[10]=0;
        eventResetTime_current[11]=0;
        eventResetTime_current[12]=0;
        eventResetTime_current[13]=0;
        eventResetTime_current[14]=0;
        eventResetTime_current[15]=0;
        eventResetTime_current[16]=0;
        eventResetTime_current[17]=0;
        eventResetTime_current[18]=0;
        
        eventResetTime_default[0]=10000000;   // BMT
        eventResetTime_default[1]=10000000;   // BST
        eventResetTime_default[2]=10000000;   // CND
        eventResetTime_default[3]=10000000;   // CTOF
        eventResetTime_default[4]=10000000;   // DC
        eventResetTime_default[5]=10000000;   // ECAL
        eventResetTime_default[6]=10000000;   // FMT
        eventResetTime_default[7]=10000000;   // FTCAL
        eventResetTime_default[8]=10000000;   // FTHODO
        eventResetTime_default[9]=10000000;   // FTOF
        eventResetTime_default[10]=10000000;  // FTTRK
        eventResetTime_default[11]=10000000;  // HTTC
        eventResetTime_default[12]=10000000;  // LTTC
        eventResetTime_default[13]=10000000;  // RICH
        eventResetTime_default[14]=10000000;  // RECONN
        eventResetTime_default[15]=10000000;  // RF
        eventResetTime_default[16]=10000000;  // HEL
        eventResetTime_default[17]=10000000;  // Faraday Cup
        eventResetTime_default[18]=10000000;  // Trigger
     
        for (int i=0; i<19; i++){
            eventResetTime_current[i]=eventResetTime_default[i];
        }
    }

    
    public void analyze() {
        // analyze detector data at the end of data processing
    }

    public void createHistos() {
        // initialize canvas and create histograms
    }
    
    @Override
    public void dataEventAction(DataEvent event) {
        this.setNumberOfEvents(this.getNumberOfEvents()+1);
        if (event.getType() == DataEventType.EVENT_START) {
//            resetEventListener();
            processEvent(event);
	} else if (event.getType() == DataEventType.EVENT_SINGLE) {
            processEvent(event);
            plotEvent(event);
	} else if (event.getType() == DataEventType.EVENT_ACCUMULATE) {
            processEvent(event);
	} else if (event.getType() == DataEventType.EVENT_STOP) {
            analyze();
	}
    }

    public void drawDetector() {
    
    }
    
    public void setTriggerPhase(long phase) {
    	   this.triggerPhase = phase;
    }
    
    public long getTriggerPhase() {
    	    return this.triggerPhase;
    }
    
    public void setTriggerWord(int trig) {
    	   this.trigger = trig;
    	   this.trigFD = getFDTrigger();
    	   this.trigCD = getCDTrigger();
    }
    
    public void setTestTrigger(boolean test) {
    	   this.testTrigger = test;
    }
    
    public boolean isGoodFDTrigger(int is) {return (testTrigger)? is==getFDTriggerSector():true;}    
    public boolean isGoodCDTrigger()       {return (testTrigger)? isGoodCD():true;}  
    public boolean isGoodHTCCTrigger()     {return (testTrigger)? isGoodHTCC():true;}
    public boolean isGoodFTOFTrigger()     {return (testTrigger)? isGoodFTOF():true;}
    public boolean isGoodBSTTrigger()      {return (testTrigger)? isGoodBST():true;}
    public boolean isGoodCTOFTrigger()     {return (testTrigger)? isGoodCTOF():true;}
    public boolean isGoodCNDTrigger()      {return (testTrigger)? isGoodCND():true;}
    public boolean isGoodBMTTrigger()      {return (testTrigger)? isGoodBMT():true;}
    
    public boolean isGoodFD()              {return  this.trigFD>=256&&this.trigFD<=8196;}    
    public boolean isGoodCD()              {return  isGoodBST()||isGoodCTOF()||isGoodCND()||isGoodBMT();}
    public boolean isGoodHTCC()            {return  this.trigFD==1;}    
    public boolean isGoodFTOF()            {return  isGoodFD();}   
    public boolean isGoodBST()             {return  this.trigCD==256;}    
    public boolean isGoodCTOF()            {return  this.trigCD==512;}
    public boolean isGoodCND()             {return  this.trigCD==1024;}   
    public boolean isGoodBMT()             {return  this.trigCD==2048;}   
    
    public int     getFDTriggerSector()    {return (int) (isGoodFD() ? Math.log10(this.trigFD>>8)/0.301+1:0);}       
    public int     getFDTrigger()          {return (this.trigger>>16)&0x0000ffff;}      
    public int     getCDTrigger()          {return this.trigger&0x00000fff;} 
    
    
    
    public void setTriggerBeam1(boolean test) {this.TriggerBeam1 = test;}
    public void setTriggerBeam2(boolean test) {this.TriggerBeam2 = test;}
    public void setTriggerBeam3(boolean test) {this.TriggerBeam3 = test;}
    public void setTriggerBeam4(boolean test) {this.TriggerBeam4 = test;}
    public void setTriggerBeam5(boolean test) {this.TriggerBeam5 = test;}
    public void setTriggerBeam6(boolean test) {this.TriggerBeam6 = test;}
    public void setTriggerBeam7(boolean test) {this.TriggerBeam7 = test;}
    public void setTriggerBeam8(boolean test) {this.TriggerBeam8 = test;}
    public void setTriggerBeam9(boolean test) {this.TriggerBeam9 = test;}
    public void setTriggerBeam10(boolean test) {this.TriggerBeam10 = test;}
    public void setTriggerBeam11(boolean test) {this.TriggerBeam11 = test;}
    public void setTriggerBeam12(boolean test) {this.TriggerBeam12 = test;}
    public void setTriggerBeam13(boolean test) {this.TriggerBeam13 = test;}
    public void setTriggerBeam14(boolean test) {this.TriggerBeam14 = test;}
    public void setTriggerBeam15(boolean test) {this.TriggerBeam15 = test;}
    public void setTriggerBeam16(boolean test) {this.TriggerBeam16 = test;}
    public void setTriggerBeam17(boolean test) {this.TriggerBeam17 = test;}
    public void setTriggerBeam18(boolean test) {this.TriggerBeam18 = test;}
    public void setTriggerBeam19(boolean test) {this.TriggerBeam19 = test;}
    public void setTriggerBeam20(boolean test) {this.TriggerBeam20 = test;}
    public void setTriggerBeam21(boolean test) {this.TriggerBeam21 = test;}
    public void setTriggerBeam22(boolean test) {this.TriggerBeam22 = test;}
    public void setTriggerBeam23(boolean test) {this.TriggerBeam23 = test;}
    public void setTriggerBeam24(boolean test) {this.TriggerBeam24 = test;}
    public void setTriggerBeam25(boolean test) {this.TriggerBeam25 = test;}
    public void setTriggerBeam26(boolean test) {this.TriggerBeam26 = test;}
    public void setTriggerBeam27(boolean test) {this.TriggerBeam27 = test;}
    public void setTriggerBeam28(boolean test) {this.TriggerBeam28 = test;}
    public void setTriggerBeam29(boolean test) {this.TriggerBeam29 = test;}
    public void setTriggerBeam30(boolean test) {this.TriggerBeam30 = test;}
    public void setTriggerBeam31(boolean test) {this.TriggerBeam31 = test;}
    public void setTriggerBeam32(boolean test) {this.TriggerBeam32 = test;}
    
    
    public boolean isGoodTrigger1()     {return (TriggerBeam1)? isGood1():true;}    
    public boolean isGoodTrigger2()     {return (TriggerBeam2)? isGood2():true;}  
    public boolean isGoodTrigger3()     {return (TriggerBeam3)? isGood3():true;}
    public boolean isGoodTrigger4()     {return (TriggerBeam4)? isGood4():true;}
    public boolean isGoodTrigger5()     {return (TriggerBeam5)? isGood5():true;}    
    public boolean isGoodTrigger6()     {return (TriggerBeam6)? isGood6():true;}  
    public boolean isGoodTrigger7()     {return (TriggerBeam7)? isGood7():true;}
    public boolean isGoodTrigger8()     {return (TriggerBeam8)? isGood8():true;}
    public boolean isGoodTrigger9()     {return (TriggerBeam9)? isGood9():true;}    
    public boolean isGoodTrigger10()    {return (TriggerBeam10)? isGood10():true;}  
    public boolean isGoodTrigger11()    {return (TriggerBeam11)? isGood11():true;}
    public boolean isGoodTrigger12()    {return (TriggerBeam12)? isGood12():true;}
    public boolean isGoodTrigger13()    {return (TriggerBeam13)? isGood13():true;}    
    public boolean isGoodTrigger14()    {return (TriggerBeam14)? isGood14():true;}  
    public boolean isGoodTrigger15()    {return (TriggerBeam15)? isGood15():true;}
    public boolean isGoodTrigger16()    {return (TriggerBeam16)? isGood16():true;}
    public boolean isGoodTrigger17()    {return (TriggerBeam17)? isGood17():true;}    
    public boolean isGoodTrigger18()    {return (TriggerBeam18)? isGood18():true;}  
    public boolean isGoodTrigger19()    {return (TriggerBeam19)? isGood19():true;}
    public boolean isGoodTrigger20()    {return (TriggerBeam20)? isGood20():true;}
    public boolean isGoodTrigger21()    {return (TriggerBeam21)? isGood21():true;}    
    public boolean isGoodTrigger22()    {return (TriggerBeam22)? isGood22():true;}  
    public boolean isGoodTrigger23()    {return (TriggerBeam23)? isGood23():true;}
    public boolean isGoodTrigger24()    {return (TriggerBeam24)? isGood24():true;}
    public boolean isGoodTrigger25()    {return (TriggerBeam25)? isGood25():true;}    
    public boolean isGoodTrigger26()    {return (TriggerBeam26)? isGood26():true;}  
    public boolean isGoodTrigger27()    {return (TriggerBeam27)? isGood27():true;}
    public boolean isGoodTrigger28()    {return (TriggerBeam28)? isGood28():true;}
    public boolean isGoodTrigger29()    {return (TriggerBeam29)? isGood29():true;}    
    public boolean isGoodTrigger30()    {return (TriggerBeam30)? isGood30():true;}  
    public boolean isGoodTrigger31()    {return (TriggerBeam31)? isGood31():true;}
    public boolean isGoodTrigger32()    {return (TriggerBeam32)? isGood32():true;}
    
    
    public boolean isGood1() {return  this.trigger == 1;} 
    public boolean isGood2() {return  this.trigger == 2;} 
    public boolean isGood3() {return  this.trigger == 3;} 
    public boolean isGood4() {return  this.trigger == 4;} 
    public boolean isGood5() {return  this.trigger == 5;} 
    public boolean isGood6() {return  this.trigger == 6;} 
    public boolean isGood7() {return  this.trigger == 7;} 
    public boolean isGood8() {return  this.trigger == 8;} 
    public boolean isGood9() {return  this.trigger == 9;} 
    public boolean isGood10() {return  this.trigger == 10;} 
    public boolean isGood11() {return  this.trigger == 11;} 
    public boolean isGood12() {return  this.trigger == 12;} 
    public boolean isGood13() {return  this.trigger == 13;} 
    public boolean isGood14() {return  this.trigger == 14;} 
    public boolean isGood15() {return  this.trigger == 15;} 
    public boolean isGood16() {return  this.trigger == 16;} 
    public boolean isGood17() {return  this.trigger == 17;} 
    public boolean isGood18() {return  this.trigger == 18;} 
    public boolean isGood19() {return  this.trigger == 19;} 
    public boolean isGood20() {return  this.trigger == 20;} 
    public boolean isGood21() {return  this.trigger == 21;} 
    public boolean isGood22() {return  this.trigger == 22;} 
    public boolean isGood23() {return  this.trigger == 23;} 
    public boolean isGood24() {return  this.trigger == 24;} 
    public boolean isGood25() {return  this.trigger == 25;} 
    public boolean isGood26() {return  this.trigger == 26;} 
    public boolean isGood27() {return  this.trigger == 27;} 
    public boolean isGood28() {return  this.trigger == 28;} 
    public boolean isGood29() {return  this.trigger == 29;} 
    public boolean isGood30() {return  this.trigger == 30;} 
    public boolean isGood31() {return  this.trigger == 31;} 
    public boolean isGood32() {return  this.trigger == 32;} 

    
    
    
    public EmbeddedCanvasTabbed getDetectorCanvas() {
        return detectorCanvas;
    }
    
    public ArrayList<String> getDetectorTabNames() {
        return detectorTabNames;
    }
    
    public IndexedList<DataGroup>  getDataGroup(){
        return detectorData;
    }

    public String getDetectorName() {
        return detectorName;
    }
    
    public DetectorOccupancy getDetectorOccupancy() {
        return detectorOccupancy;
    }
    
    public JPanel getDetectorPanel() {
        return detectorPanel;
    }
    
    public DataGroup getDetectorSummary() {
        return detectorSummary;
    }
    
    public DetectorPane2D getDetectorView() {
        return detectorView;
    }
    
    public void useSectorButtons(boolean flag) {
    	    this.sectorButtons = flag;
    }
    
    public int getActiveSector() {
    	    return detectorActiveSector;
    }
    
    public int getNumberOfEvents() {
        return numberOfEvents;
    }
    
    public void setLogZ(boolean flag) {
	    this.detectorLogZ = flag;
    }
    
    public Boolean getLogZ() {
	    return this.detectorLogZ;
    }

    public void init(boolean flagDetectorView) {
        // initialize monitoring application
        // detector view is shown if flag is true
        getDetectorPanel().setLayout(new BorderLayout());
        drawDetector();
        JSplitPane   splitPane = new JSplitPane();
        splitPane.setLeftComponent(getDetectorView());
        splitPane.setRightComponent(getDetectorCanvas());
        if(flagDetectorView) {
            getDetectorPanel().add(splitPane,BorderLayout.CENTER);  
        }
        else {
            getDetectorPanel().add(getDetectorCanvas(),BorderLayout.CENTER); 
            if (sectorButtons) getDetectorPanel().add(getButtonPane(),BorderLayout.PAGE_END);  
        }
        createHistos();
        plotHistos(); 
        if (sectorButtons) bS2.doClick();
    }
    
    public JPanel getButtonPane() {
    	    bG1 = new ButtonGroup();
        JPanel buttonPane = new JPanel();
        bS1 = new JRadioButton("Sector 1"); buttonPane.add(bS1); bS1.setActionCommand("1"); bS1.addActionListener(this);
        bS2 = new JRadioButton("Sector 2"); buttonPane.add(bS2); bS2.setActionCommand("2"); bS2.addActionListener(this); 
        bS3 = new JRadioButton("Sector 3"); buttonPane.add(bS3); bS3.setActionCommand("3"); bS3.addActionListener(this); 
        bS4 = new JRadioButton("Sector 4"); buttonPane.add(bS4); bS4.setActionCommand("4"); bS4.addActionListener(this); 
        bS5 = new JRadioButton("Sector 5"); buttonPane.add(bS5); bS5.setActionCommand("5"); bS5.addActionListener(this);  
        bS6 = new JRadioButton("Sector 6"); buttonPane.add(bS6); bS6.setActionCommand("6"); bS6.addActionListener(this); 
        bG1.add(bS1);bG1.add(bS2);bG1.add(bS3);bG1.add(bS4);bG1.add(bS5);bG1.add(bS6);
        //tbBtn = new JCheckBox("TrigBit");
        //tbBtn.addItemListener(new ItemListener() {
        //    public void itemStateChanged(ItemEvent e) {
        //    	  isTB = e.getStateChange()==ItemEvent.SELECTED;
        //    }
        //});         
        //tbBtn.setSelected(false);        
        //buttonPane.add(tbBtn);       
        return buttonPane;
    } 
    
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        this.detectorActiveSector   = Integer.parseInt(bG1.getSelection().getActionCommand());
        plotHistos();
    } 
    
    public void processEvent(DataEvent event) {
        // process event
    }
    
    public void plotEvent(DataEvent event) {
        // process event
    }
    
    public void plotHistos() {

    }
    
    public void printCanvas(String dir) {
        // print canvas to files
        for(int tab=0; tab<this.detectorTabNames.size(); tab++) {
            String fileName = dir + "/" + this.detectorName + "_canvas" + tab + ".png";
            System.out.println(fileName);
            this.detectorCanvas.getCanvas(this.detectorTabNames.get(tab)).save(fileName);
        }
    }
    
    @Override
    public void resetEventListener() {
        System.out.println("Resetting " + this.getDetectorName() + " histogram");
        this.createHistos();
        this.plotHistos();
    }
    
    public void setCanvasUpdate(int time) {
        for(int tab=0; tab<this.detectorTabNames.size(); tab++) {
            this.detectorCanvas.getCanvas(this.detectorTabNames.get(tab)).initTimer(time);
        }
    }
    
    public void setDetectorCanvas(EmbeddedCanvasTabbed canvas) {
        this.detectorCanvas = canvas;
    }
    
    public void setDetectorTabNames(String... names) {
        for(String name : names) {
            this.detectorTabNames.add(name);
        }
        EmbeddedCanvasTabbed canvas = new EmbeddedCanvasTabbed(names);
        this.setDetectorCanvas(canvas);
    }
 
    public void setDetectorSummary(DataGroup group) {
        this.detectorSummary = group;
    }
    
    public void setNumberOfEvents(int numberOfEvents) {
        this.numberOfEvents = numberOfEvents;
    }

    @Override
    public void timerUpdate() {
        
    }
 
    public void readDataGroup(TDirectory dir) {
        String folder = this.getDetectorName() + "/";
        System.out.println("Reading from: " + folder);
        DataGroup sum = this.getDetectorSummary();
        int nrows = sum.getRows();
        int ncols = sum.getColumns();
        int nds   = nrows*ncols;
        DataGroup newSum = new DataGroup(ncols,nrows);
        for(int i = 0; i < nds; i++){
            List<IDataSet> dsList = sum.getData(i);
            for(IDataSet ds : dsList){
                System.out.println("\t --> " + ds.getName());
                newSum.addDataSet(dir.getObject(folder, ds.getName()),i);
            }
        }            
        this.setDetectorSummary(newSum);
        Map<Long, DataGroup> map = this.getDataGroup().getMap();
        for( Map.Entry<Long, DataGroup> entry : map.entrySet()) {
            Long key = entry.getKey();
            DataGroup group = entry.getValue();
            nrows = group.getRows();
            ncols = group.getColumns();
            nds   = nrows*ncols;
            DataGroup newGroup = new DataGroup(ncols,nrows);
            for(int i = 0; i < nds; i++){
                List<IDataSet> dsList = group.getData(i);
                for(IDataSet ds : dsList){
                    System.out.println("\t --> " + ds.getName());
                    newGroup.addDataSet(dir.getObject(folder, ds.getName()),i);
                }
            }
            map.replace(key, newGroup);
        }
        this.plotHistos();
    }
    
    public void writeDataGroup(TDirectory dir) {
        String folder = "/" + this.getDetectorName();
        dir.mkdir(folder);
        dir.cd(folder);
        DataGroup sum = this.getDetectorSummary();
        int nrows = sum.getRows();
        int ncols = sum.getColumns();
        int nds   = nrows*ncols;
        for(int i = 0; i < nds; i++){
            List<IDataSet> dsList = sum.getData(i);
            for(IDataSet ds : dsList){
                System.out.println("\t --> " + ds.getName());
                dir.addDataSet(ds);
            }
        }            
        Map<Long, DataGroup> map = this.getDataGroup().getMap();
        for( Map.Entry<Long, DataGroup> entry : map.entrySet()) {
            DataGroup group = entry.getValue();
            nrows = group.getRows();
            ncols = group.getColumns();
            nds   = nrows*ncols;
            for(int i = 0; i < nds; i++){
                List<IDataSet> dsList = group.getData(i);
                for(IDataSet ds : dsList){
                    System.out.println("\t --> " + ds.getName());
                    dir.addDataSet(ds);
                }
            }
        }
    }
        
}
