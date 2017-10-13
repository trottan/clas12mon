/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class FTTRKmonitor  extends DetectorMonitor {        
    
    public FTTRKmonitor(String name) {
        super(name);
        
        this.setDetectorTabNames("ADC Occupancies", "TDC Occupancies");
        this.init(false);
    }

    @Override
    public void createHistos() {
        // initialize canvas and create histograms
        this.setNumberOfEvents(0);
        
        H1F summary = new H1F("summary","summary",332,1,333);
        summary.setTitleX("sector");
        summary.setTitleY("FTTRK hits");
        summary.setFillColor(38);
        DataGroup sum = new DataGroup(1,1);
        sum.addDataSet(summary, 0);
        this.setDetectorSummary(sum);
        
        H1F occADC = new H1F("occADC", "occADC", 332, 1, 333);
        occADC.setTitleX("PMT");
        occADC.setTitleY("Counts");
        occADC.setFillColor(38);
        H1F occTDC = new H1F("occTDC", "occTDC", 332, 1, 333);
        occTDC.setTitleX("PMT");
        occTDC.setTitleY("Counts");
        occTDC.setFillColor(38);
        
        H2F adc = new H2F("adc", "adc", 50, 0, 5000, 332, 1, 333);
        adc.setTitleX("ADC - amplitude");
        adc.setTitleY("PMT");
        H2F tdc = new H2F("tdc", "tdc", 50, 0, 50000, 332, 1, 333);
        tdc.setTitleX("TDC - amplitude");
        tdc.setTitleY("PMT");
        
        DataGroup dg = new DataGroup(2,2);
        dg.addDataSet(occADC, 0);
        dg.addDataSet(adc, 1);
        dg.addDataSet(occTDC, 2);
        dg.addDataSet(tdc, 3);
        
        this.getDataGroup().add(dg,0,0,0);
    }
        
    @Override
    public void plotHistos() {        
        // plotting histos
        this.getDetectorCanvas().getCanvas("ADC Occupancies").divide(2, 2);
        this.getDetectorCanvas().getCanvas("ADC Occupancies").setGridX(false);
        this.getDetectorCanvas().getCanvas("ADC Occupancies").setGridY(false);
        this.getDetectorCanvas().getCanvas("ADC Occupancies").cd(0);
        this.getDetectorCanvas().getCanvas("ADC Occupancies").draw(this.getDataGroup().getItem(0,0,0).getH1F("occADC"));
        this.getDetectorCanvas().getCanvas("ADC Occupancies").cd(1);
        this.getDetectorCanvas().getCanvas("ADC Occupancies").draw(this.getDataGroup().getItem(0,0,0).getH1F("occTDC"));
        this.getDetectorCanvas().getCanvas("ADC Occupancies").cd(2);
        this.getDetectorCanvas().getCanvas("ADC Occupancies").draw(this.getDataGroup().getItem(0,0,0).getH2F("adc"));
        this.getDetectorCanvas().getCanvas("ADC Occupancies").cd(3);
        this.getDetectorCanvas().getCanvas("ADC Occupancies").draw(this.getDataGroup().getItem(0,0,0).getH2F("tdc"));
        this.getDetectorCanvas().getCanvas("ADC Occupancies").update();
    }

    @Override
    public void processEvent(DataEvent event) {
        // process event info and save into data group
        if(event.hasBank("FTTRK::ADC")==true){
	    DataBank bank = event.getBank("FTTRK::ADC");
	    int rows = bank.rows();
	    for(int loop = 0; loop < rows; loop++){
                int sector  = bank.getByte("sector", loop);
                int layer   = bank.getByte("layer", loop);
                int comp    = bank.getShort("component", loop);
                int adc     = bank.getInt("ADC", loop);
//                System.out.println("ROW " + loop + " SECTOR = " + sector + " LAYER = " + layer + " COMPONENT = " + comp + " ORDER + " + order +
//                      " ADC = " + adc + " TIME = " + time); 
                if(adc>0) {
                        this.getDataGroup().getItem(0,0,0).getH1F("occADC").fill(comp*1.0);
                        this.getDataGroup().getItem(0,0,0).getH2F("adc").fill(adc*1.0,comp*1.0);
                }
                this.getDetectorSummary().getH1F("summary").fill(sector*1.0);
	    }
    	}
        
        if(event.hasBank("FTTRK::TDC")==true){
            DataBank  bank = event.getBank("FTTRK::TDC");
            int rows = bank.rows();
            for(int i = 0; i < rows; i++){
                int    sector = bank.getByte("sector",i);
                int     layer = bank.getByte("layer",i);
                int      comp = bank.getShort("component",i);
                int       tdc = bank.getInt("TDC",i);
//                           System.out.println("ROW " + i + " SECTOR = " + sector
//                                 + " LAYER = " + layer + " PADDLE = "
//                                 + paddle + " TDC = " + TDC);    
                if(tdc>0) {
                        this.getDataGroup().getItem(0,0,0).getH1F("occTDC").fill(comp*1.0);
                        this.getDataGroup().getItem(0,0,0).getH2F("tdc").fill(tdc*1.0,comp*1.0);
                }
            }
        }        
    }

    @Override
    public void timerUpdate() {

    }


}
