package org.clas.detectors;

import org.clas.viewer.DetectorMonitor;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.group.DataGroup;
import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;

public class RICHmonitor  extends DetectorMonitor {
        
    
    public RICHmonitor(String name) {
        super(name);
        
        this.setDetectorTabNames("Occupancies and spectra");
        this.init(false);
    }

    @Override
    public void createHistos() {
        // initialize canvas and create histograms
        this.setNumberOfEvents(0);
        this.getDetectorCanvas().getCanvas("Occupancies and spectra").divide(1, 2);
        this.getDetectorCanvas().getCanvas("Occupancies and spectra").setGridX(false);
        this.getDetectorCanvas().getCanvas("Occupancies and spectra").setGridY(false);
        
        H2F summary = new H2F("summary","summary",192, 0.5, 192.5, 127, 0.5, 127.5);  // x = 224   y = 184
        summary.setTitleX("channel");
        summary.setTitleY("RICH hits");
        summary.setTitle("RICH");
        DataGroup sum = new DataGroup(1,1);
        sum.addDataSet(summary, 0);
        this.setDetectorSummary(sum);
        
        H2F occTDC = new H2F("occTDC", "occTDC", 192, 0.5, 192.5, 127, 0.5, 127.5);
        occTDC.setTitleY("tile number");
        occTDC.setTitleX("channel number");
        occTDC.setTitle("TDC Occupancy");

        H2F tdc = new H2F("tdc", "tdc", 200, 0, 400, 384, 0.5, 384.5);
        tdc.setTitleX("time [ns]");
        tdc.setTitleY("MAPMT number");
        tdc.setTitle("TDC timing");
        
           
        DataGroup dg = new DataGroup(2,1);
        dg.addDataSet(occTDC, 0);
        dg.addDataSet(tdc, 1);
        this.getDataGroup().add(dg,0,0,0);
    }
        
    @Override
    public void plotHistos() {
        // plotting histos
        this.getDetectorCanvas().getCanvas("Occupancies and spectra").cd(0);
        this.getDetectorCanvas().getCanvas("Occupancies and spectra").getPad(0).getAxisZ().setLog(getLogZ());
        this.getDetectorCanvas().getCanvas("Occupancies and spectra").draw(this.getDataGroup().getItem(0,0,0).getH2F("occTDC"));
        this.getDetectorCanvas().getCanvas("Occupancies and spectra").cd(1);
        this.getDetectorCanvas().getCanvas("Occupancies and spectra").getPad(1).getAxisZ().setLog(getLogZ());
        this.getDetectorCanvas().getCanvas("Occupancies and spectra").draw(this.getDataGroup().getItem(0,0,0).getH2F("tdc"));
        this.getDetectorCanvas().getCanvas("Occupancies and spectra").update();
        
        this.getDetectorView().getView().repaint();
        this.getDetectorView().update();
    }

    @Override
    public void processEvent(DataEvent event) {
        
        if (this.getNumberOfEvents() >= super.eventResetTime_current[13] && super.eventResetTime_current[13] > 0){
            resetEventListener();
        }
        
		//if (!testTriggerMask()) return;
        
        // process event info and save into data group
        if(event.hasBank("RICH::adc")==true){
	    DataBank bank = event.getBank("RICH::adc");
	    int rows = bank.rows();
	    for(int loop = 0; loop < rows; loop++){
                int sector  = bank.getByte("sector", loop);
                int layer   = bank.getByte("layer", loop);
                int comp    = bank.getShort("component", loop);
                int order   = bank.getByte("order", loop);
                int adc     = bank.getInt("ADC", loop);
                float time  = bank.getFloat("time", loop);
//                System.out.println("ROW " + loop + " SECTOR = " + sector + " LAYER = " + layer + " COMPONENT = " + comp + " ORDER + " + order +
//                      " ADC = " + adc + " TIME = " + time); 
                if(adc>0) {
                    this.getDataGroup().getItem(0,0,0).getH2F("occADC").fill(comp*1.0,layer*1.0);
                    this.getDataGroup().getItem(0,0,0).getH2F("adc").fill(adc*1.0, (comp-1)*138+layer);
                    
                }
	    }
    	}
        if(event.hasBank("RICH::tdc")==true){
            DataBank  bank = event.getBank("RICH::tdc");
            int rows = bank.rows();
            for(int i = 0; i < rows; i++){
                int    sector = bank.getByte("sector",i);
                int     layer = bank.getByte("layer",i);
                int      comp = bank.getShort("component",i);
                int       tdc = bank.getInt("TDC",i);
                int     order = bank.getByte("order",i); // order specifies left-right for ADC
                         // System.out.println("ROW " + i + " SECTOR = " + sector + " LAYER = " + layer + " COMPONENT = "+ comp + " TDC = " + TDC);    
                if(tdc>0){ 
                    this.getDataGroup().getItem(0,0,0).getH2F("occTDC").fill(comp,layer*1.0);
                    
                    this.getDataGroup().getItem(0,0,0).getH2F("tdc").fill(tdc, comp+ 192*order);
                    
                    this.getDetectorSummary().getH2F("summary").fill(comp,layer*1.0);
                }
            }
        }        
    }

    @Override
    public void timerUpdate() {

    }


}