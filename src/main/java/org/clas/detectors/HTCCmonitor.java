package org.clas.detectors;

import org.clas.viewer.DetectorMonitor;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.group.DataGroup;
import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;

/**
 *
 * @author devita
 */

public class HTCCmonitor  extends DetectorMonitor {
        
    
    public HTCCmonitor(String name) {
        super(name);
        
        this.setDetectorTabNames("Occupancies", "ADC spectra", "timing spectra");
        this.init(false);
    }

    @Override
    public void createHistos() {
        // initialize canvas and create histograms
        this.setNumberOfEvents(0);
        this.getDetectorCanvas().getCanvas("Occupancies").divide(1, 2);
        this.getDetectorCanvas().getCanvas("Occupancies").setGridX(false);
        this.getDetectorCanvas().getCanvas("Occupancies").setGridY(false);
        this.getDetectorCanvas().getCanvas("ADC spectra").divide(3, 2);
        this.getDetectorCanvas().getCanvas("ADC spectra").setGridX(false);
        this.getDetectorCanvas().getCanvas("ADC spectra").setGridY(false);
        this.getDetectorCanvas().getCanvas("timing spectra").divide(3, 2);
        this.getDetectorCanvas().getCanvas("timing spectra").setGridX(false);
        this.getDetectorCanvas().getCanvas("timing spectra").setGridY(false);
        H1F summary = new H1F("summary","summary",6,1,7);
        summary.setTitleX("sector");
        summary.setTitleY("HTCC hits");
        summary.setTitle("HTTC");
        summary.setFillColor(36);
        DataGroup sum = new DataGroup(1,1);
        sum.addDataSet(summary, 0);
        this.setDetectorSummary(sum);
        H2F occADC = new H2F("occADC", "occADC", 6, 0.5, 6.5, 8, 0.5, 8.5);
        occADC.setTitleY("ring-PMT");
        occADC.setTitleX("sector");
        occADC.setTitle("ADC Occupancy");
        H2F occTDC = new H2F("occTDC", "occTDC", 6, 0.5, 6.5, 8, 0.5, 8.5);
        occTDC.setTitleY("ring-PMT");
        occTDC.setTitleX("sector");
        occTDC.setTitle("TDC Occupancy");
        
        H2F adc1 = new H2F("adc_s1", "adc_s1", 100, 0, 5000, 8, 0.5, 8.5);
        adc1.setTitleX("ADC - value");
        adc1.setTitleY("PMT");
        adc1.setTitle("sector 1");
        H2F adc2 = new H2F("adc_s2", "adc_s2", 100, 0, 5000, 8, 0.5, 8.5);
        adc2.setTitleX("ADC - value");
        adc2.setTitleY("PMT");
        adc2.setTitle("sector 2");
        H2F adc3 = new H2F("adc_s3", "adc_s3", 100, 0, 5000, 8, 0.5, 8.5);
        adc3.setTitleX("ADC - value");
        adc3.setTitleY("PMT");
        adc3.setTitle("sector 3");
        H2F adc4 = new H2F("adc_s4", "adc_s4", 100, 0, 5000, 8, 0.5, 8.5);
        adc4.setTitleX("ADC - value");
        adc4.setTitleY("PMT");
        adc4.setTitle("sector 4");
        H2F adc5 = new H2F("adc_s5", "adc_s5", 100, 0, 5000, 8, 0.5, 8.5);
        adc5.setTitleX("ADC - value");
        adc5.setTitleY("PMT");
        adc5.setTitle("sector 5");
        H2F adc6 = new H2F("adc_s6", "adc_s6", 100, 0, 5000, 8, 0.5, 8.5);
        adc6.setTitleX("ADC - value");
        adc6.setTitleY("PMT");
        adc6.setTitle("sector 6");
        
        H2F tdc1 = new H2F("tdc_s1", "tdc_s1", 50, 0, 250, 8, 1, 8.5);
        tdc1.setTitleX("ADC timing");
        tdc1.setTitleY("PMT");
        tdc1.setTitle("sector 1");
        H2F tdc2 = new H2F("tdc_s2", "tdc_s2", 50, 0, 250, 8, 1, 8.5);
        tdc2.setTitleX("ADC timing");
        tdc2.setTitleY("PMT");
        tdc2.setTitle("sector 2");
        H2F tdc3 = new H2F("tdc_s3", "tdc_s3", 50, 0, 250, 8, 1, 8.5);
        tdc3.setTitleX("ADC timing");
        tdc3.setTitleY("PMT");
        tdc3.setTitle("sector 3");
        H2F tdc4 = new H2F("tdc_s4", "tdc_s4", 50, 0, 250, 8, 1, 8.5);
        tdc4.setTitleX("ADC timing");
        tdc4.setTitleY("PMT");
        tdc4.setTitle("sector 4");
        H2F tdc5 = new H2F("tdc_s5", "tdc_s5", 50, 0, 250, 8, 1, 8.5);
        tdc5.setTitleX("ADC timing");
        tdc5.setTitleY("PMT");
        tdc5.setTitle("sector 5");
        H2F tdc6 = new H2F("tdc_s6", "tdc_s6", 50, 0, 250, 8, 1, 8.5);
        tdc6.setTitleX("ADC timing");
        tdc6.setTitleY("PMT");
        tdc6.setTitle("sector 6");
           
        DataGroup dg = new DataGroup(4,4);
        dg.addDataSet(occADC, 0);
        dg.addDataSet(occTDC, 1);
        dg.addDataSet(adc1, 2);
        dg.addDataSet(adc2, 3);
        dg.addDataSet(adc3, 4);
        dg.addDataSet(adc4, 5);
        dg.addDataSet(adc5, 6);
        dg.addDataSet(adc6, 7);
        dg.addDataSet(tdc1, 8);
        dg.addDataSet(tdc2, 9);
        dg.addDataSet(tdc3, 10);
        dg.addDataSet(tdc4, 11);
        dg.addDataSet(tdc5, 12);
        dg.addDataSet(tdc6, 13);
        this.getDataGroup().add(dg,0,0,0);
    }
        
    @Override
    public void plotHistos() {
        // plotting histos
        this.getDetectorCanvas().getCanvas("Occupancies").cd(0);
        this.getDetectorCanvas().getCanvas("Occupancies").draw(this.getDataGroup().getItem(0,0,0).getH2F("occADC"));
        this.getDetectorCanvas().getCanvas("Occupancies").cd(1);
        this.getDetectorCanvas().getCanvas("Occupancies").draw(this.getDataGroup().getItem(0,0,0).getH2F("occTDC"));
        this.getDetectorCanvas().getCanvas("Occupancies").update();
        
        this.getDetectorCanvas().getCanvas("ADC spectra").cd(0);
        this.getDetectorCanvas().getCanvas("ADC spectra").draw(this.getDataGroup().getItem(0,0,0).getH2F("adc_s1"));
        this.getDetectorCanvas().getCanvas("ADC spectra").cd(1);
        this.getDetectorCanvas().getCanvas("ADC spectra").draw(this.getDataGroup().getItem(0,0,0).getH2F("adc_s2"));
        this.getDetectorCanvas().getCanvas("ADC spectra").cd(2);
        this.getDetectorCanvas().getCanvas("ADC spectra").draw(this.getDataGroup().getItem(0,0,0).getH2F("adc_s3"));
        this.getDetectorCanvas().getCanvas("ADC spectra").cd(3);
        this.getDetectorCanvas().getCanvas("ADC spectra").draw(this.getDataGroup().getItem(0,0,0).getH2F("adc_s4"));
        this.getDetectorCanvas().getCanvas("ADC spectra").cd(4);
        this.getDetectorCanvas().getCanvas("ADC spectra").draw(this.getDataGroup().getItem(0,0,0).getH2F("adc_s5"));
        this.getDetectorCanvas().getCanvas("ADC spectra").cd(5);
        this.getDetectorCanvas().getCanvas("ADC spectra").draw(this.getDataGroup().getItem(0,0,0).getH2F("adc_s6"));
        this.getDetectorCanvas().getCanvas("ADC spectra").update();
        
        this.getDetectorCanvas().getCanvas("timing spectra").cd(0);
        this.getDetectorCanvas().getCanvas("timing spectra").draw(this.getDataGroup().getItem(0,0,0).getH2F("tdc_s1"));
        this.getDetectorCanvas().getCanvas("timing spectra").cd(1);
        this.getDetectorCanvas().getCanvas("timing spectra").draw(this.getDataGroup().getItem(0,0,0).getH2F("tdc_s2"));
        this.getDetectorCanvas().getCanvas("timing spectra").cd(2);
        this.getDetectorCanvas().getCanvas("timing spectra").draw(this.getDataGroup().getItem(0,0,0).getH2F("tdc_s3"));
        this.getDetectorCanvas().getCanvas("timing spectra").cd(3);
        this.getDetectorCanvas().getCanvas("timing spectra").draw(this.getDataGroup().getItem(0,0,0).getH2F("tdc_s4"));
        this.getDetectorCanvas().getCanvas("timing spectra").cd(4);
        this.getDetectorCanvas().getCanvas("timing spectra").draw(this.getDataGroup().getItem(0,0,0).getH2F("tdc_s5"));
        this.getDetectorCanvas().getCanvas("timing spectra").cd(5);
        this.getDetectorCanvas().getCanvas("timing spectra").draw(this.getDataGroup().getItem(0,0,0).getH2F("tdc_s6"));
        this.getDetectorCanvas().getCanvas("timing spectra").update();
        this.getDetectorView().getView().repaint();
        this.getDetectorView().update();
    }

    @Override
    public void processEvent(DataEvent event) {
        // process event info and save into data group
        if(event.hasBank("HTCC::adc")==true){
	    DataBank bank = event.getBank("HTCC::adc");
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
                if(adc>0 && time>0) {
                    this.getDataGroup().getItem(0,0,0).getH2F("occADC").fill(sector*1.0,((comp-1)*2+layer)*1.0);
                    if(sector == 1) this.getDataGroup().getItem(0,0,0).getH2F("adc_s1").fill(adc*1.0,((comp-1)*2+layer)*1.0);
                    if(sector == 1) this.getDataGroup().getItem(0,0,0).getH2F("tdc_s1").fill(time,((comp-1)*2+layer)*1.0);
                    if(sector == 2) this.getDataGroup().getItem(0,0,0).getH2F("adc_s2").fill(adc*1.0,((comp-1)*2+layer)*1.0);
                    if(sector == 2) this.getDataGroup().getItem(0,0,0).getH2F("tdc_s2").fill(time,((comp-1)*2+layer)*1.0);
                    if(sector == 3) this.getDataGroup().getItem(0,0,0).getH2F("adc_s3").fill(adc*1.0,((comp-1)*2+layer)*1.0);
                    if(sector == 3) this.getDataGroup().getItem(0,0,0).getH2F("tdc_s3").fill(time,((comp-1)*2+layer)*1.0);
                    if(sector == 4) this.getDataGroup().getItem(0,0,0).getH2F("adc_s4").fill(adc*1.0,((comp-1)*2+layer)*1.0);
                    if(sector == 4) this.getDataGroup().getItem(0,0,0).getH2F("tdc_s4").fill(time,((comp-1)*2+layer)*1.0);
                    if(sector == 5) this.getDataGroup().getItem(0,0,0).getH2F("adc_s5").fill(adc*1.0,((comp-1)*2+layer)*1.0);
                    if(sector == 5) this.getDataGroup().getItem(0,0,0).getH2F("tdc_s5").fill(time,((comp-1)*2+layer)*1.0);
                    if(sector == 6) this.getDataGroup().getItem(0,0,0).getH2F("adc_s6").fill(adc*1.0,((comp-1)*2+layer)*1.0);
                    if(sector == 6) this.getDataGroup().getItem(0,0,0).getH2F("tdc_s6").fill(time,((comp-1)*2+layer)*1.0);
                    this.getDetectorSummary().getH1F("summary").fill(sector*1.0);
                }
	    }
    	}
        if(event.hasBank("HTCC::tdc")==true){
            DataBank  bank = event.getBank("HTCC::tdc");
            int rows = bank.rows();
            for(int i = 0; i < rows; i++){
                int    sector = bank.getByte("sector",i);
                int     layer = bank.getByte("layer",i);
                int      comp = bank.getShort("component",i);
                int       tdc = bank.getInt("TDC",i);
                int     order = bank.getByte("order",i); // order specifies left-right for ADC
//                           System.out.println("ROW " + i + " SECTOR = " + sector
//                                 + " LAYER = " + layer + " PADDLE = "
//                                 + paddle + " TDC = " + TDC);    
                if(tdc>0) this.getDataGroup().getItem(0,0,0).getH2F("occTDC").fill(sector*1.0,((comp-1)*2+layer)*1.0);
//                this.getDetectorSummary().getH1F("summary").fill(sector*1.0);                
            }
        }        
    }

    @Override
    public void timerUpdate() {

    }


}
