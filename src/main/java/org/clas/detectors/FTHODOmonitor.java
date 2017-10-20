package org.clas.detectors;

import org.clas.viewer.DetectorMonitor;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.group.DataGroup;
import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;


public class FTHODOmonitor  extends DetectorMonitor {        
    
    public FTHODOmonitor(String name) {
        super(name);
        
        this.setDetectorTabNames("FADC Occupancies", "FADC spectra");
        this.init(false);
    }

    @Override
    public void createHistos() {
        // initialize canvas and create histograms
        this.setNumberOfEvents(0);
        
        H1F summary = new H1F("summary","summary",232,0.5,232.5);
        summary.setTitleX("PMT");
        summary.setTitleY("FTHODO hits");
        summary.setTitle("FTHODO");
        summary.setFillColor(38);
        DataGroup sum = new DataGroup(1,1);
        sum.addDataSet(summary, 0);
        this.setDetectorSummary(sum);
        
        H2F occFADC2Dl1 = new H2F("occFADC_2D_l1", "occFADC_2D_l1", 20, 0.5, 20.5, 8, 0.5, 8.5);
        occFADC2Dl1.setTitleX("component");
        occFADC2Dl1.setTitleY("sector");
        occFADC2Dl1.setTitle("layer 1");
        
        H1F occFADCl1 = new H1F("occFADC_l1", "occFADC_l1", 116, 0.5, 116.5);
        occFADCl1.setTitleX("SiPM");
        occFADCl1.setTitleY("Counts");
        occFADCl1.setFillColor(38);
        occFADCl1.setTitle("layer 1");
        
        H2F fadcl1 = new H2F("fadc_l1", "fadc_l1", 65, 0, 65000, 116, 0.5, 116.5);
        fadcl1.setTitleX("FADC - amplitude");
        fadcl1.setTitleY("SiPM");
        fadcl1.setTitle("layer 1");
        
        H2F fadc_timel1 = new H2F("fadc_time_l1", "fadc_time_l1", 50, 0, 50000, 116, 0.5, 116.5);
        fadc_timel1.setTitleX("FADC - time");
        fadc_timel1.setTitleY("SiPM");
        fadc_timel1.setTitle("layer 1");
        
        H2F occFADC2Dl2 = new H2F("occFADC_2D_l2", "occFADC_2D_l2", 20, 0.5, 20.5, 8, 0.5, 8.5);
        occFADC2Dl2.setTitleX("component");
        occFADC2Dl2.setTitleY("sector");
        occFADC2Dl2.setTitle("layer 2");
        
        H1F occFADCl2 = new H1F("occFADC_l2", "occFADC_l2", 116, 0.5, 116.5);
        occFADCl2.setTitleX("SiPM");
        occFADCl2.setTitleY("Counts");
        occFADCl2.setFillColor(38);
        occFADCl2.setTitle("layer 2");
        
        H2F fadcl2 = new H2F("fadc_l2", "fadc_l2", 65, 0, 65000, 116, 0.5, 116.5);
        fadcl2.setTitleX("FADC - amplitude");
        fadcl2.setTitleY("SiPM");
        fadcl2.setTitle("layer 2");
        
        H2F fadc_timel2 = new H2F("fadc_time_l2", "fadc_time_l2", 50, 0, 50000, 116, 0.5, 116.5);
        fadc_timel2.setTitleX("FADC - time");
        fadc_timel2.setTitleY("SiPM");
        fadc_timel2.setTitle("layer 2");
        
        
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
        this.getDetectorCanvas().getCanvas("FADC Occupancies").divide(2, 2);
        this.getDetectorCanvas().getCanvas("FADC Occupancies").setGridX(false);
        this.getDetectorCanvas().getCanvas("FADC Occupancies").setGridY(false);
        this.getDetectorCanvas().getCanvas("FADC Occupancies").cd(0);
        this.getDetectorCanvas().getCanvas("FADC Occupancies").getPad(0).getAxisZ().setLog(getLogZ());
        this.getDetectorCanvas().getCanvas("FADC Occupancies").draw(this.getDataGroup().getItem(0,0,0).getH2F("occFADC_2D_l1"));
        this.getDetectorCanvas().getCanvas("FADC Occupancies").cd(1);
        this.getDetectorCanvas().getCanvas("FADC Occupancies").draw(this.getDataGroup().getItem(0,0,0).getH1F("occFADC_l1"));
        this.getDetectorCanvas().getCanvas("FADC Occupancies").cd(2);
        this.getDetectorCanvas().getCanvas("FADC Occupancies").getPad(2).getAxisZ().setLog(getLogZ());
        this.getDetectorCanvas().getCanvas("FADC Occupancies").draw(this.getDataGroup().getItem(0,0,0).getH2F("occFADC_2D_l2"));
        this.getDetectorCanvas().getCanvas("FADC Occupancies").cd(3);
        this.getDetectorCanvas().getCanvas("FADC Occupancies").draw(this.getDataGroup().getItem(0,0,0).getH1F("occFADC_l2"));
        
        this.getDetectorCanvas().getCanvas("FADC spectra").divide(2, 2);
        this.getDetectorCanvas().getCanvas("FADC spectra").setGridX(false);
        this.getDetectorCanvas().getCanvas("FADC spectra").setGridY(false);
        this.getDetectorCanvas().getCanvas("FADC spectra").cd(0);
        this.getDetectorCanvas().getCanvas("FADC spectra").getPad(0).getAxisZ().setLog(getLogZ());
        this.getDetectorCanvas().getCanvas("FADC spectra").draw(this.getDataGroup().getItem(0,0,0).getH2F("fadc_l1"));
        this.getDetectorCanvas().getCanvas("FADC spectra").cd(1);
        this.getDetectorCanvas().getCanvas("FADC spectra").getPad(1).getAxisZ().setLog(getLogZ());
        this.getDetectorCanvas().getCanvas("FADC spectra").draw(this.getDataGroup().getItem(0,0,0).getH2F("fadc_time_l1"));
        this.getDetectorCanvas().getCanvas("FADC spectra").update();
        this.getDetectorCanvas().getCanvas("FADC spectra").cd(2);
        this.getDetectorCanvas().getCanvas("FADC spectra").getPad(2).getAxisZ().setLog(getLogZ());
        this.getDetectorCanvas().getCanvas("FADC spectra").draw(this.getDataGroup().getItem(0,0,0).getH2F("fadc_l2"));
        this.getDetectorCanvas().getCanvas("FADC spectra").cd(3);
        this.getDetectorCanvas().getCanvas("FADC spectra").getPad(3).getAxisZ().setLog(getLogZ());
        this.getDetectorCanvas().getCanvas("FADC spectra").draw(this.getDataGroup().getItem(0,0,0).getH2F("fadc_time_l2"));
        this.getDetectorCanvas().getCanvas("FADC spectra").update();
        
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
                
//             System.out.println("ROW " + loop + " SECTOR = " + sector + " LAYER = " + layer + " COMPONENT = " + comp + " ORDER + " + order +
//             " ADC = " + adc + " TIME = " + time); 
               
                if(adc>0) {
                    if(layer == 1){
                        
                        this.getDataGroup().getItem(0,0,0).getH2F("occFADC_2D_l1").fill(comp*1.0,sector*1.0);
                        
                        if(sector == 1){
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC_l1").fill(comp+0);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_l1").fill(adc*1.0,comp+0);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time_l1").fill(time*1.0,comp+0);
                            this.getDetectorSummary().getH1F("summary").fill(comp+0);
                        }
                        if(sector == 2){
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC_l1").fill(comp+9);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_l1").fill(adc*1.0,comp+9);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time_l1").fill(time*1.0,comp+9);
                            this.getDetectorSummary().getH1F("summary").fill(comp+9);
                        }
                        if(sector == 3){
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC_l1").fill(comp+29);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_l1").fill(adc*1.0,comp+29);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time_l1").fill(time*1.0,comp+29);
                            this.getDetectorSummary().getH1F("summary").fill(comp+29);
                        }
                        if(sector == 4){
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC_l1").fill(comp+38);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_l1").fill(adc*1.0,comp+38);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time_l1").fill(time*1.0,comp+38);
                            this.getDetectorSummary().getH1F("summary").fill(comp+38);
                        }
                        if(sector == 5){
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC_l1").fill(comp+58);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_l1").fill(adc*1.0,comp+58);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time_l1").fill(time*1.0,comp+58);
                            this.getDetectorSummary().getH1F("summary").fill(comp+58);
                        }
                        if(sector == 6){
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC_l1").fill(comp+67);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_l1").fill(adc*1.0,comp+67);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time_l1").fill(time*1.0,comp+67);
                            this.getDetectorSummary().getH1F("summary").fill(comp+67);
                        }
                        if(sector == 7){
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC_l1").fill(comp+87);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_l1").fill(adc*1.0,comp+87);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time_l1").fill(time*1.0,comp+87);
                            this.getDetectorSummary().getH1F("summary").fill(comp+87);
                        }
                        if(sector == 8){
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC_l1").fill(comp+96);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_l1").fill(adc*1.0,comp+96);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time_l1").fill(time*1.0,comp+96);
                            this.getDetectorSummary().getH1F("summary").fill(comp+96);
                        }
                         
                    }
                    if(layer == 2){
                        this.getDataGroup().getItem(0,0,0).getH2F("occFADC_2D_l2").fill(comp*1.0,sector*1.0);
                        
                        if(sector == 1){
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC_l2").fill(comp+0);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_l2").fill(adc*1.0,comp+0);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time_l2").fill(time*1.0,comp+0);
                            this.getDetectorSummary().getH1F("summary").fill(comp+0+116);
                        }
                        if(sector == 2){
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC_l2").fill(comp+9);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_l2").fill(adc*1.0,comp+9);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time_l2").fill(time*1.0,comp+9);
                            this.getDetectorSummary().getH1F("summary").fill(comp+9+116);
                        }
                        if(sector == 3){
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC_l2").fill(comp+29);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_l2").fill(adc*1.0,comp+29);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time_l2").fill(time*1.0,comp+29);
                            this.getDetectorSummary().getH1F("summary").fill(comp+29+116);
                        }
                        if(sector == 4){
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC_l2").fill(comp+38);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_l2").fill(adc*1.0,comp+38);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time_l2").fill(time*1.0,comp+38);
                            this.getDetectorSummary().getH1F("summary").fill(comp+38+116);
                        }
                        if(sector == 5){
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC_l2").fill(comp+58);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_l2").fill(adc*1.0,comp+58);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time_l2").fill(time*1.0,comp+58);
                            this.getDetectorSummary().getH1F("summary").fill(comp+58+116);
                        }
                        if(sector == 6){
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC_l2").fill(comp+67);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_l2").fill(adc*1.0,comp+67);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time_l2").fill(time*1.0,comp+67);
                            this.getDetectorSummary().getH1F("summary").fill(comp+67+116);
                        }
                        if(sector == 7){
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC_l2").fill(comp+87);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_l2").fill(adc*1.0,comp+87);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time_l2").fill(time*1.0,comp+87);
                            this.getDetectorSummary().getH1F("summary").fill(comp+87+116);
                        }
                        if(sector == 8){
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC_l2").fill(comp+96);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_l2").fill(adc*1.0,comp+96);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time_l2").fill(time*1.0,comp+96);
                            this.getDetectorSummary().getH1F("summary").fill(comp+96+116);
                        }
                        
                        
                    }
                    
                }
                
	    }
    	}
            
    }

    @Override
    public void timerUpdate() {

    }


}
