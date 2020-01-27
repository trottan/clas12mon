package org.clas.detectors;

import java.util.HashMap;
import org.clas.viewer.DetectorMonitor;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.group.DataGroup;
import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;
import org.jlab.clas.physics.Vector3;


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
        
        H2F NormOccupancy = new H2F("Normalized Occupancy","Normalized Occupancy",180,1,181,96,1,97);
        NormOccupancy.setTitleX("Row");
        NormOccupancy.setTitleY("Col");
      
        
        
        H1F TimeDistribution = new H1F("Time Distribution","Time Distribution",200,0,12000);
        TimeDistribution.setTitleX("Time (ns)");
        
        H1F ADC = new H1F("ADC","ADC",100,0,1000);
        ADC.setTitleX("ADC");

        
        DataGroup dg = new DataGroup(2,2);
        dg.addDataSet(Occupancy, 0);
        dg.addDataSet(NormOccupancy, 1);
        dg.addDataSet(TimeDistribution, 2);
        dg.addDataSet(ADC, 3);

        this.getDataGroup().add(dg,0,0,0);
        
        
        
            
        
    }
        
    @Override
    public void plotHistos() {
        // initialize canvas and plot histograms
        
        this.getDetectorCanvas().getCanvas("Summary").divide(2, 2);
        this.getDetectorCanvas().getCanvas("Summary").setGridX(false);
        this.getDetectorCanvas().getCanvas("Summary").setGridY(false);
        this.getDetectorCanvas().getCanvas("Summary").cd(0);
        this.getDetectorCanvas().getCanvas("Summary").draw(this.getDataGroup().getItem(0,0,0).getH2F("Occupancy"));
        this.getDetectorCanvas().getCanvas("Summary").cd(1);
        this.getDetectorCanvas().getCanvas("Summary").draw(this.getDataGroup().getItem(0,0,0).getH2F("Normalized Occupancy"));
        this.getDetectorCanvas().getCanvas("Summary").cd(2);
        this.getDetectorCanvas().getCanvas("Summary").draw(this.getDataGroup().getItem(0,0,0).getH1F("Time Distribution"));
        this.getDetectorCanvas().getCanvas("Summary").cd(3);
        this.getDetectorCanvas().getCanvas("Summary").draw(this.getDataGroup().getItem(0,0,0).getH1F("ADC"));
        this.getDetectorCanvas().getCanvas("Summary").update();

        
        
    }

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
            for (int row = 0; row < nRows; ++row) {
                rtpcrow = bankRTPC.getShort("component",row);
                rtpccol = bankRTPC.getByte("layer",row);
                float time = bankRTPC.getFloat("time",row);
                int ADC = bankRTPC.getInt("ADC", row);
                
                if(nRows > 0){
                    this.getDataGroup().getItem(0,0,0).getH2F("Occupancy").fill(rtpcrow,rtpccol);               
                    this.getDataGroup().getItem(0,0,0).getH1F("Time Distribution").fill(time);
                    this.getDataGroup().getItem(0,0,0).getH1F("ADC").fill(ADC);
                    normOccupancy(ADC,rtpcrow,rtpccol);
                }               
            }
            for(int row = 0; row < nRows; row++){
                rtpcrow = bankRTPC.getShort("component",row);
                rtpccol = bankRTPC.getByte("layer",row);
                this.getDataGroup().getItem(0,0,0).getH2F("Normalized Occupancy").fill(rtpcrow,rtpccol,getNormOccupancy(rtpcrow,rtpccol));
            }
        }       
                        
    }
    
    private HashMap<Integer,Integer> numhitsperpad = new HashMap<>();
    private HashMap<Integer,Integer> totADCperpad = new HashMap<>();
    
    private void normOccupancy(int ADC, int row, int col){
        int cellid = (row-1)*96 + col;
        
        if(!numhitsperpad.containsKey(cellid)) numhitsperpad.put(cellid, 1);
        else{
            numhitsperpad.put(cellid, numhitsperpad.get(cellid)+1);
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
        
    }

}
