package org.clas.detectors;

import org.clas.viewer.DetectorMonitor;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.group.DataGroup;
import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;


public class FTTRKmonitor  extends DetectorMonitor {        
    
    public FTTRKmonitor(String name) {
        super(name);
        
        this.setDetectorTabNames("Occupancies", "ADC and time spectra");
        this.init(false);
    }

    @Override
    public void createHistos() {
        // initialize canvas and create histograms
        this.setNumberOfEvents(0);
        
        H1F summary = new H1F("summary","summary",3392,0.5,3392.5);
        summary.setTitleX("sector");
        summary.setTitleY("FTTRK hits");
        summary.setTitle("FTTRK");
        summary.setFillColor(38);
        DataGroup sum = new DataGroup(1,1);
        sum.addDataSet(summary, 0);
        this.setDetectorSummary(sum);
        
        H1F occADCl1 = new H1F("occADC_layer1", "occADC_layer1", 848, 0.5, 848.5);
        occADCl1.setTitleX("PMT");
        occADCl1.setTitleY("Counts");
        occADCl1.setFillColor(38);
        occADCl1.setTitle("layer 1");
        H1F occADCl2 = new H1F("occADC_layer2", "occADC_layer2", 848, 0.5, 848.5);
        occADCl2.setTitleX("PMT");
        occADCl2.setTitleY("Counts");
        occADCl2.setFillColor(38);
        occADCl2.setTitle("layer 2");
        H1F occADCl3 = new H1F("occADC_layer3", "occADC_layer3", 848, 0.5, 848.5);
        occADCl3.setTitleX("PMT");
        occADCl3.setTitleY("Counts");
        occADCl3.setFillColor(38);
        occADCl3.setTitle("layer 3");
        H1F occADCl4 = new H1F("occADC_layer4", "occADC_layer4", 848, 0.5, 848.5);
        occADCl4.setTitleX("PMT");
        occADCl4.setTitleY("Counts");
        occADCl4.setFillColor(38);
        occADCl4.setTitle("layer 4");
        
        H2F adc = new H2F("adc", "adc", 50, 0, 5000, 3392, 0.5, 3392.5);
        adc.setTitleX("ADC - amplitude");
        adc.setTitleY("PMT");
        H2F tdc = new H2F("tdc", "tdc", 50, 0, 50000, 3392, 0.5, 3392.5);
        tdc.setTitleX("time");
        tdc.setTitleY("PMT");
        
        DataGroup dg = new DataGroup(2,4);
        dg.addDataSet(occADCl1, 0);
        dg.addDataSet(occADCl2, 0);
        dg.addDataSet(occADCl3, 0);
        dg.addDataSet(occADCl4, 0);
        dg.addDataSet(adc, 1);
        dg.addDataSet(tdc, 2);
        
        this.getDataGroup().add(dg,0,0,0);
    }
        
    @Override
    public void plotHistos() {        
        // plotting histos
        this.getDetectorCanvas().getCanvas("Occupancies").divide(2, 2);
        this.getDetectorCanvas().getCanvas("Occupancies").setGridX(false);
        this.getDetectorCanvas().getCanvas("Occupancies").setGridY(false);
        this.getDetectorCanvas().getCanvas("Occupancies").cd(0);
        this.getDetectorCanvas().getCanvas("Occupancies").draw(this.getDataGroup().getItem(0,0,0).getH1F("occADC_layer1"));
        this.getDetectorCanvas().getCanvas("Occupancies").cd(1);
        this.getDetectorCanvas().getCanvas("Occupancies").draw(this.getDataGroup().getItem(0,0,0).getH1F("occADC_layer2"));
        this.getDetectorCanvas().getCanvas("Occupancies").cd(2);
        this.getDetectorCanvas().getCanvas("Occupancies").draw(this.getDataGroup().getItem(0,0,0).getH1F("occADC_layer3"));
        this.getDetectorCanvas().getCanvas("Occupancies").cd(3);
        this.getDetectorCanvas().getCanvas("Occupancies").draw(this.getDataGroup().getItem(0,0,0).getH1F("occADC_layer4"));
        this.getDetectorCanvas().getCanvas("ADC and time spectra").divide(1, 2);
        this.getDetectorCanvas().getCanvas("ADC and time spectra").setGridX(false);
        this.getDetectorCanvas().getCanvas("ADC and time spectra").setGridY(false);
        this.getDetectorCanvas().getCanvas("ADC and time spectra").cd(0);
        this.getDetectorCanvas().getCanvas("ADC and time spectra").draw(this.getDataGroup().getItem(0,0,0).getH2F("adc"));
        this.getDetectorCanvas().getCanvas("ADC and time spectra").cd(1);
        this.getDetectorCanvas().getCanvas("ADC and time spectra").draw(this.getDataGroup().getItem(0,0,0).getH2F("tdc"));
        this.getDetectorCanvas().getCanvas("ADC and time spectra").update();
    }

    @Override
    public void processEvent(DataEvent event) {
        // process event info and save into data group
        
        if(event.hasBank("FTTRK::adc")==true){
	    DataBank bank = event.getBank("FTTRK::adc");
	    int rows = bank.rows();
	    for(int loop = 0; loop < rows; loop++){
                int sector  = bank.getByte("sector", loop);
                int layer   = bank.getByte("layer", loop);
                int comp    = bank.getInt("component", loop);
                int order   = bank.getByte("order", loop);
                int adc     = bank.getInt("ADC", loop);
                float time  = bank.getFloat("time", loop);
//                System.out.println("ROW " + loop + " SECTOR = " + sector + " LAYER = " + layer + " COMPONENT = " + comp +
//                      " ADC = " + adc); 
                if(adc>0) {
                        if(layer == 1) this.getDataGroup().getItem(0,0,0).getH1F("occADC_layer1").fill(comp*1.0);
                        if(layer == 2) this.getDataGroup().getItem(0,0,0).getH1F("occADC_layer2").fill(comp*1.0);
                        if(layer == 3) this.getDataGroup().getItem(0,0,0).getH1F("occADC_layer3").fill(comp*1.0);
                        if(layer == 4) this.getDataGroup().getItem(0,0,0).getH1F("occADC_layer4").fill(comp*1.0);
                        
                        this.getDataGroup().getItem(0,0,0).getH2F("adc").fill(adc*1.0,((comp-1)*4+layer)*1.0);
                        this.getDataGroup().getItem(0,0,0).getH2F("tdc").fill(time*1.0,((comp-1)*4+layer)*1.0);
                }
                this.getDetectorSummary().getH1F("summary").fill(((comp-1)*4+layer)*1.0);
	    }
    	}
                
                
    }

    @Override
    public void timerUpdate() {

    }


}
