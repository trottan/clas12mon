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
public class FTHODOmonitor  extends DetectorMonitor {        
    
    public FTHODOmonitor(String name) {
        super(name);
        
        this.setDetectorTabNames("FADC Occupancies layer 1", "FADC Occupancies layer 2");
        this.init(false);
    }

    @Override
    public void createHistos() {
        // initialize canvas and create histograms
        this.setNumberOfEvents(0);
        
        H1F summary = new H1F("summary","summary",116,1,117);
        summary.setTitleX("PMT");
        summary.setTitleY("FTHODO hits");
        summary.setFillColor(38);
        DataGroup sum = new DataGroup(1,1);
        sum.addDataSet(summary, 0);
        this.setDetectorSummary(sum);
        
        H2F occFADC2Dl1 = new H2F("occFADC_2D_l1", "occFADC_2D_l1", 22, 1, 23, 9, 1, 10);
        occFADC2Dl1.setTitleX("PMT IDX");
        occFADC2Dl1.setTitleY("PMT IDY");
        
        H1F occFADCl1 = new H1F("occFADC_l1", "occFADC_l1", 116, 1, 117);
        occFADCl1.setTitleX("PMT");
        occFADCl1.setTitleY("Counts");
        occFADCl1.setFillColor(38);
        
        H2F fadcl1 = new H2F("fadc_l1", "fadc_l1", 50, 0, 5000, 116, 1, 117);
        fadcl1.setTitleX("FADC - amplitude");
        fadcl1.setTitleY("PMT");
        H2F fadc_timel1 = new H2F("fadc_time_l1", "fadc_time_l1", 50, 0, 50000, 116, 1, 117);
        fadc_timel1.setTitleX("FADC - time");
        fadc_timel1.setTitleY("PMT");
        
        H2F occFADC2Dl2 = new H2F("occFADC_2D_l2", "occFADC_2D_l2", 22, 1, 23, 9, 1, 10);
        occFADC2Dl2.setTitleX("PMT IDX");
        occFADC2Dl2.setTitleY("PMT IDY");
        
        H1F occFADCl2 = new H1F("occFADC_l2", "occFADC_l2", 116, 1, 117);
        occFADCl2.setTitleX("PMT");
        occFADCl2.setTitleY("Counts");
        occFADCl2.setFillColor(38);
        
        H2F fadcl2 = new H2F("fadc_l2", "fadc_l2", 50, 0, 5000, 116, 1, 117);
        fadcl2.setTitleX("FADC - amplitude");
        fadcl2.setTitleY("PMT");
        H2F fadc_timel2 = new H2F("fadc_time_l2", "fadc_time_l2", 50, 0, 50000, 116, 1, 117);
        fadc_timel2.setTitleX("FADC - time");
        fadc_timel2.setTitleY("PMT");
        
        DataGroup dg = new DataGroup(2,4);
        dg.addDataSet(occFADC2Dl1, 0);
        dg.addDataSet(occFADCl1, 1);
        dg.addDataSet(fadcl1, 2);
        dg.addDataSet(fadc_timel1, 3);
        dg.addDataSet(occFADC2Dl2, 4);
        dg.addDataSet(occFADCl2, 5);
        dg.addDataSet(fadcl2, 6);
        dg.addDataSet(fadc_timel2, 7);
        
        this.getDataGroup().add(dg,0,0,0);
    }
        
    @Override
    public void plotHistos() {        
        // plotting histos
        this.getDetectorCanvas().getCanvas("FADC Occupancies layer 1").divide(2, 2);
        this.getDetectorCanvas().getCanvas("FADC Occupancies layer 1").setGridX(false);
        this.getDetectorCanvas().getCanvas("FADC Occupancies layer 1").setGridY(false);
        this.getDetectorCanvas().getCanvas("FADC Occupancies layer 1").cd(0);
        this.getDetectorCanvas().getCanvas("FADC Occupancies layer 1").draw(this.getDataGroup().getItem(0,0,0).getH2F("occFADC_2D_l1"));
        this.getDetectorCanvas().getCanvas("FADC Occupancies layer 1").cd(1);
        this.getDetectorCanvas().getCanvas("FADC Occupancies layer 1").draw(this.getDataGroup().getItem(0,0,0).getH1F("occFADC_l1"));
        this.getDetectorCanvas().getCanvas("FADC Occupancies layer 1").cd(2);
        this.getDetectorCanvas().getCanvas("FADC Occupancies layer 1").draw(this.getDataGroup().getItem(0,0,0).getH2F("fadc_l1"));
        this.getDetectorCanvas().getCanvas("FADC Occupancies layer 1").cd(3);
        this.getDetectorCanvas().getCanvas("FADC Occupancies layer 1").draw(this.getDataGroup().getItem(0,0,0).getH2F("fadc_time_l1"));
        this.getDetectorCanvas().getCanvas("FADC Occupancies layer 1").update();
        this.getDetectorCanvas().getCanvas("FADC Occupancies layer 2").divide(2, 2);
        this.getDetectorCanvas().getCanvas("FADC Occupancies layer 2").setGridX(false);
        this.getDetectorCanvas().getCanvas("FADC Occupancies layer 2").setGridY(false);
        this.getDetectorCanvas().getCanvas("FADC Occupancies layer 2").cd(0);
        this.getDetectorCanvas().getCanvas("FADC Occupancies layer 2").draw(this.getDataGroup().getItem(0,0,0).getH2F("occFADC_2D_l2"));
        this.getDetectorCanvas().getCanvas("FADC Occupancies layer 2").cd(1);
        this.getDetectorCanvas().getCanvas("FADC Occupancies layer 2").draw(this.getDataGroup().getItem(0,0,0).getH1F("occFADC_l2"));
        this.getDetectorCanvas().getCanvas("FADC Occupancies layer 2").cd(2);
        this.getDetectorCanvas().getCanvas("FADC Occupancies layer 2").draw(this.getDataGroup().getItem(0,0,0).getH2F("fadc_l2"));
        this.getDetectorCanvas().getCanvas("FADC Occupancies layer 2").cd(3);
        this.getDetectorCanvas().getCanvas("FADC Occupancies layer 2").draw(this.getDataGroup().getItem(0,0,0).getH2F("fadc_time_l2"));
        this.getDetectorCanvas().getCanvas("FADC Occupancies layer 2").update();
    }

    @Override
    public void processEvent(DataEvent event) {
        // process event info and save into data group
        if(event.hasBank("FTHODO::adc")==true){
	    DataBank bank = event.getBank("FTHODO::adc");
	    int rows = bank.rows();
	    for(int loop = 0; loop < rows; loop++){
                int sector  = bank.getByte("sector", loop);
                int layer   = bank.getByte("layer", loop);
                int comp    = bank.getShort("component", loop);
                int order   = bank.getByte("order", loop);
                int adc     = bank.getInt("ADC", loop);
                float time    = bank.getFloat("time", loop);
                
                //int IDY = ((int) comp/22) + 1;
                //int IDX = comp + 1 - (IDY -1)*22;    
                
//                System.out.println("ROW " + loop + " SECTOR = " + sector + " LAYER = " + layer + " COMPONENT = " + comp + " ORDER + " + order +
 //                     " ADC = " + adc + " TIME = " + time); 
                if(adc>0) {
                    if(layer == 1){
                        this.getDataGroup().getItem(0,0,0).getH2F("occFADC_2D_l1").fill(comp*1.0,sector*1.0);
                        this.getDataGroup().getItem(0,0,0).getH1F("occFADC_l1").fill(comp*1.0);
                        this.getDataGroup().getItem(0,0,0).getH2F("fadc_l1").fill(adc*1.0,comp*1.0);
                        this.getDataGroup().getItem(0,0,0).getH2F("fadc_time_l1").fill(time*1.0,comp*1.0);
                    }
                    if(layer == 2){
                        this.getDataGroup().getItem(0,0,0).getH2F("occFADC_2D_l2").fill(comp*1.0,sector*1.0);
                        this.getDataGroup().getItem(0,0,0).getH1F("occFADC_l2").fill(comp*1.0);
                        this.getDataGroup().getItem(0,0,0).getH2F("fadc_l2").fill(adc*1.0,comp*1.0);
                        this.getDataGroup().getItem(0,0,0).getH2F("fadc_time_l2").fill(time*1.0,comp*1.0);
                    }
                }
                this.getDetectorSummary().getH1F("summary").fill(comp*1.0);
	    }
    	}
            
    }

    @Override
    public void timerUpdate() {

    }


}
