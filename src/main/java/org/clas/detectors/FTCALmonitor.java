package org.clas.detectors;

import org.clas.viewer.DetectorMonitor;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.group.DataGroup;
import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;


public class FTCALmonitor  extends DetectorMonitor {        
    
    public FTCALmonitor(String name) {
        super(name);
        
        this.setDetectorTabNames("FADC Occupancies");
        this.init(false);
    }

    @Override
    public void createHistos() {
        // initialize canvas and create histograms
        this.setNumberOfEvents(0);
        
        H1F summary = new H1F("summary","summary",484,0.5,484.5);
        summary.setTitleX("PMT");
        summary.setTitleY("FTCAL hits");
        summary.setTitle("FTCAL");
        summary.setFillColor(38);
        DataGroup sum = new DataGroup(1,1);
        sum.addDataSet(summary, 0);
        this.setDetectorSummary(sum);
        
        H2F occFADC2D = new H2F("occFADC_2D", "occFADC_2D", 22, 0.5, 22.5, 22, 0.5, 22.5);
        occFADC2D.setTitleX("PMT IDX");
        occFADC2D.setTitleY("PMT IDY");
        
        H1F occFADC = new H1F("occFADC", "occFADC", 484, 0.5, 484.5);
        occFADC.setTitleX("PMT");
        occFADC.setTitleY("Counts");
        occFADC.setFillColor(38);
        
        H2F fadc = new H2F("fadc", "fadc", 50, 0, 5000, 484, 0.5, 484.5);
        fadc.setTitleX("FADC - amplitude");
        fadc.setTitleY("PMT");
        H2F fadc_time = new H2F("fadc_time", "fadc_time", 50, 0, 50000, 484, 0.5, 484.5);
        fadc_time.setTitleX("FADC - time");
        fadc_time.setTitleY("PMT");
        
        DataGroup dg = new DataGroup(2,2);
        dg.addDataSet(occFADC2D, 0);
        dg.addDataSet(occFADC, 1);
        dg.addDataSet(fadc, 2);
        dg.addDataSet(fadc_time, 3);
        
        this.getDataGroup().add(dg,0,0,0);
    }
        
    @Override
    public void plotHistos() {        
        // plotting histos
        this.getDetectorCanvas().getCanvas("FADC Occupancies").divide(2, 2);
        this.getDetectorCanvas().getCanvas("FADC Occupancies").setGridX(false);
        this.getDetectorCanvas().getCanvas("FADC Occupancies").setGridY(false);
        this.getDetectorCanvas().getCanvas("FADC Occupancies").cd(0);
        this.getDetectorCanvas().getCanvas("FADC Occupancies").draw(this.getDataGroup().getItem(0,0,0).getH2F("occFADC_2D"));
        this.getDetectorCanvas().getCanvas("FADC Occupancies").cd(1);
        this.getDetectorCanvas().getCanvas("FADC Occupancies").draw(this.getDataGroup().getItem(0,0,0).getH1F("occFADC"));
        this.getDetectorCanvas().getCanvas("FADC Occupancies").cd(2);
        this.getDetectorCanvas().getCanvas("FADC Occupancies").draw(this.getDataGroup().getItem(0,0,0).getH2F("fadc"));
        this.getDetectorCanvas().getCanvas("FADC Occupancies").cd(3);
        this.getDetectorCanvas().getCanvas("FADC Occupancies").draw(this.getDataGroup().getItem(0,0,0).getH2F("fadc_time"));
        this.getDetectorCanvas().getCanvas("FADC Occupancies").update();
    }

    @Override
    public void processEvent(DataEvent event) {
        // process event info and save into data group
        if(event.hasBank("FTCAL::adc")==true){
	    DataBank bank = event.getBank("FTCAL::adc");
	    int rows = bank.rows();
	    for(int loop = 0; loop < rows; loop++){
                int sector  = bank.getByte("sector", loop);
                int layer   = bank.getByte("layer", loop);
                int comp    = bank.getShort("component", loop);
                int order   = bank.getByte("order", loop);
                int adc     = bank.getInt("ADC", loop);
                float time    = bank.getFloat("time", loop);
                
                int IDY = ((int) comp/22) + 1;
                int IDX = comp + 1 - (IDY -1)*22;    
                
             //   System.out.println("ROW " + loop + " SECTOR = " + sector + " LAYER = " + layer + " COMPONENT = " + comp + " ORDER + " + order +
              //        " ADC = " + adc + " TIME = " + time); 
                if(adc>0) {
                        this.getDataGroup().getItem(0,0,0).getH2F("occFADC_2D").fill(IDX*1.0,IDY*1.0);
                        this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp*1.0);
                        this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp*1.0);
                        this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp*1.0);
                }
                this.getDetectorSummary().getH1F("summary").fill(comp*1.0);
	    }
    	}
            
    }

    @Override
    public void timerUpdate() {

    }


}
