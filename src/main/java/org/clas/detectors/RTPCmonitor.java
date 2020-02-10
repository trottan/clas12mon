package org.clas.detectors;

import java.util.HashMap;
import org.clas.viewer.DetectorMonitor;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.group.DataGroup;
import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;



public class RTPCmonitor extends DetectorMonitor {
    

    public RTPCmonitor(String name) {
        super(name);
        this.setDetectorTabNames("Summary");
        this.init(false);
    }
    
    @Override
    public void createHistos() {
        // create histograms
        this.setNumberOfEvents(0);

        
        H2F Occupancy = new H2F("Occupancy","Occupancy",180,1,181,96,1,97);
        Occupancy.setTitleX("Row");
        Occupancy.setTitleY("Col");
        
      
        H2F NormOccupancyADC = new H2F("Occupancy ADC/signal","Occupancy ADC/signal",180,1,181,96,1,97);
        NormOccupancyADC.setTitleX("Row");
        NormOccupancyADC.setTitleY("Col");
        
        
        H1F TimeDistribution = new H1F("Time Distribution","Time Distribution",80,-1200,8400);
        TimeDistribution.setTitleX("Time (ns)");
        TimeDistribution.setOptStat(1110);
        
        H1F NumberHits = new H1F("Number of Hits Per Event","Number of Hits Per Event",400,1,2001);
        NumberHits.setTitleX("Number of Hits per event above threshold");
        NumberHits.setOptStat(1110);
        
        H1F ADC = new H1F("ADC","ADC",256,0,4096);
        ADC.setTitleX("ADC");
        ADC.setOptStat(1110);
        
        H1F PadsPerEvent = new H1F("Pads per Event","Pads per Event",1728,0,17281);
        PadsPerEvent.setTitleX("Pads per Event");
        PadsPerEvent.setOptStat(1110);

        
        DataGroup dg = new DataGroup(3,2);
        dg.addDataSet(Occupancy, 0);
        dg.addDataSet(NormOccupancyADC, 1);
        dg.addDataSet(TimeDistribution, 2);
        dg.addDataSet(NumberHits,3);
        dg.addDataSet(ADC, 4);
        dg.addDataSet(PadsPerEvent, 5);
       

        this.getDataGroup().add(dg,0,0,0);
        
            
        
    }
        
    @Override
    public void plotHistos() {
        // initialize canvas and plot histograms
        maxtime = 0;
        maxadc = 0;
        maxnumhits = 0;
        maxpads = 0;
        
        this.getDetectorCanvas().getCanvas("Summary").divide(2,3);
        this.getDetectorCanvas().getCanvas("Summary").setGridX(false);
        this.getDetectorCanvas().getCanvas("Summary").setGridY(false);
        this.getDetectorCanvas().getCanvas("Summary").cd(0);
        this.getDetectorCanvas().getCanvas("Summary").draw(this.getDataGroup().getItem(0,0,0).getH2F("Occupancy"));
        this.getDetectorCanvas().getCanvas("Summary").getPad(0).getAxisZ().setLog(true);
        this.getDetectorCanvas().getCanvas("Summary").cd(1);
        this.getDetectorCanvas().getCanvas("Summary").draw(this.getDataGroup().getItem(0,0,0).getH2F("Occupancy ADC/signal"));
        this.getDetectorCanvas().getCanvas("Summary").cd(2);
        //this.getDetectorCanvas().getCanvas("Summary").getPad(2).getAxisX().setRange(0,this.getDataGroup().getItem(0,0,0).getH1F("Time Distribution").getMax() + 50);
        this.getDetectorCanvas().getCanvas("Summary").draw(this.getDataGroup().getItem(0,0,0).getH1F("Time Distribution"));
        this.getDetectorCanvas().getCanvas("Summary").cd(3);
        //this.getDetectorCanvas().getCanvas("Summary").getPad(3).getAxisX().setRange(0,this.getDataGroup().getItem(0,0,0).getH1F("ADC").getMax() + 50);
        this.getDetectorCanvas().getCanvas("Summary").draw(this.getDataGroup().getItem(0,0,0).getH1F("ADC"));
        this.getDetectorCanvas().getCanvas("Summary").cd(4);
        //this.getDetectorCanvas().getCanvas("Summary").getPad(4).getAxisX().setRange(0,this.getDataGroup().getItem(0,0,0).getH1F("Number of Hits Per Event").getMax() + 50);
        this.getDetectorCanvas().getCanvas("Summary").draw(this.getDataGroup().getItem(0,0,0).getH1F("Number of Hits Per Event"));
        this.getDetectorCanvas().getCanvas("Summary").cd(5);
        //this.getDetectorCanvas().getCanvas("Summary").getPad(5).getAxisX().setRange(0,this.getDataGroup().getItem(0,0,0).getH1F("Pads per Event").getMax() + 50);
        this.getDetectorCanvas().getCanvas("Summary").draw(this.getDataGroup().getItem(0,0,0).getH1F("Pads per Event"));
        this.getDetectorCanvas().getCanvas("Summary").update();

        
        
    }

    
    private float maxtime = 0;
    private int maxadc = 0;
    private int maxnumhits = 0;
    private int maxpads = 0;
     
    
    @Override
    public void processEvent(DataEvent event) {
        
        if (this.getNumberOfEvents() >= super.eventResetTime_current && super.eventResetTime_current > 0){
            resetEventListener();
        }
        
        // process event info and save into data group

        
        if (event.hasBank("RTPC::adc")==true){
            DataBank bankRTPC = (DataBank) event.getBank("RTPC::adc");
            int nRows = bankRTPC.rows();
            int sumADC = 0;
            int rtpcrow = 0;
            int rtpccol = 0;
            int numhitsperpad = 0;
            int prevcol = -1;
            int prevrow = -1;
            int numhitsabovethresh = 0;
            int numpads = 0;
            usedevent = false;
            for (int row = 0; row < nRows; ++row) {
                rtpcrow = bankRTPC.getShort("component",row);
                rtpccol = bankRTPC.getByte("layer",row);
                float time = bankRTPC.getFloat("time",row);
                int ADC = bankRTPC.getInt("ADC", row);
                
                if(nRows > 0){

                    this.getDataGroup().getItem(0,0,0).getH2F("Occupancy").fill(rtpcrow,rtpccol);
         
                    
                    if(ADC > 350){
                        this.getDataGroup().getItem(0,0,0).getH1F("ADC").fill(ADC);
                        if(ADC > maxadc) maxadc = ADC;
                        numhitsabovethresh++;
                        this.getDataGroup().getItem(0,0,0).getH1F("Time Distribution").fill(time);
                        if(time > maxtime) maxtime = time;
                    }

                    normOccupancy(ADC,rtpcrow,rtpccol);
                } 
                if((rtpcrow != prevrow || rtpccol != prevcol) && ADC > 350){
                    prevrow = rtpcrow;
                    prevcol = rtpccol;
                    numpads++;
                }
            }
            this.getDataGroup().getItem(0,0,0).getH1F("Pads per Event").fill(numpads);
            if(numpads > maxpads && numpads - maxpads < 500) maxpads = numpads;
            if(numhitsabovethresh > 0){
                this.getDataGroup().getItem(0,0,0).getH1F("Number of Hits Per Event").fill(numhitsabovethresh);
                if(numhitsabovethresh > maxnumhits && numhitsabovethresh - maxnumhits < 500) maxnumhits = numhitsabovethresh;
            }
            
            for(int row = 0; row < nRows; row++){
                rtpcrow = bankRTPC.getShort("component",row);
                rtpccol = bankRTPC.getByte("layer",row);
                this.getDataGroup().getItem(0,0,0).getH2F("Occupancy ADC/signal").setBinContent(rtpcrow-1,rtpccol-1,getNormOccupancy(rtpcrow,rtpccol));
            }           
        }                              
    }
    
    private HashMap<Integer,Integer> numhitsperpad = new HashMap<>();
    private HashMap<Integer,Integer> totADCperpad = new HashMap<>();
    private boolean usedevent = false;
    
    private void normOccupancy(int ADC, int row, int col){
        int cellid = (row-1)*96 + col;
        
        if(!numhitsperpad.containsKey(cellid)) numhitsperpad.put(cellid, 1);
        else{
            if(!usedevent){
                numhitsperpad.put(cellid, numhitsperpad.get(cellid)+1);
                usedevent = true;
            }
        }
        
        if(!totADCperpad.containsKey(cellid)) totADCperpad.put(cellid, 0);
        totADCperpad.put(cellid, totADCperpad.get(cellid)+ADC);
       
    }    
    
    private float getNormOccupancy(int row, int col){
        int cellid = (row-1)*96 + col;
        return (float) totADCperpad.get(cellid)/(float) numhitsperpad.get(cellid);
    }

    @Override
    public void timerUpdate() {
        this.getDetectorCanvas().getCanvas("Summary").getPad(2).getAxisX().setRange(0,maxtime + 50);
        this.getDetectorCanvas().getCanvas("Summary").getPad(3).getAxisX().setRange(0,maxadc + 50);
        this.getDetectorCanvas().getCanvas("Summary").getPad(4).getAxisX().setRange(0,maxnumhits + 50);
        this.getDetectorCanvas().getCanvas("Summary").getPad(5).getAxisX().setRange(0,maxpads + 50);
    }
        
}
