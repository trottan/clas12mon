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
        
        H1F summary = new H1F("summary","summary",332,0.5,332.5);
        summary.setTitleX("PMT");
        summary.setTitleY("FTCAL hits");
        summary.setTitle("FTCAL");
        summary.setFillColor(38);
        DataGroup sum = new DataGroup(1,1);
        sum.addDataSet(summary, 0);
        this.setDetectorSummary(sum);
        
        H2F occFADC2D = new H2F("occFADC_2D", "occFADC_2D", 22, 0.5, 22.5, 22, 0.5, 22.5);
        occFADC2D.setTitleX("crzstal x");
        occFADC2D.setTitleY("crystal y");
        
        H1F occFADC = new H1F("occFADC", "occFADC", 332, 0.5, 332.5);
        occFADC.setTitleX("crzstal number");
        occFADC.setTitleY("Counts");
        occFADC.setFillColor(38);
        
        H2F fadc = new H2F("fadc", "fadc", 50, 0, 5000, 332, 0.5, 332.5);
        fadc.setTitleX("FADC - amplitude");
        fadc.setTitleY("crzstal");
        H2F fadc_time = new H2F("fadc_time", "fadc_time", 50, 0, 500, 332, 0.5, 332.5);
        fadc_time.setTitleX("FADC - time");
        fadc_time.setTitleY("crystal");
        
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
        this.getDetectorCanvas().getCanvas("FADC Occupancies").getPad(0).getAxisZ().setLog(getLogZ());
        this.getDetectorCanvas().getCanvas("FADC Occupancies").draw(this.getDataGroup().getItem(0,0,0).getH2F("occFADC_2D"));
        this.getDetectorCanvas().getCanvas("FADC Occupancies").cd(1);
        this.getDetectorCanvas().getCanvas("FADC Occupancies").draw(this.getDataGroup().getItem(0,0,0).getH1F("occFADC"));
        this.getDetectorCanvas().getCanvas("FADC Occupancies").cd(2);
        this.getDetectorCanvas().getCanvas("FADC Occupancies").getPad(2).getAxisZ().setLog(getLogZ());
        this.getDetectorCanvas().getCanvas("FADC Occupancies").draw(this.getDataGroup().getItem(0,0,0).getH2F("fadc"));
        this.getDetectorCanvas().getCanvas("FADC Occupancies").cd(3);
        this.getDetectorCanvas().getCanvas("FADC Occupancies").getPad(3).getAxisZ().setLog(getLogZ());
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
                        
                        if(comp >= 8 && comp <= 13){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 7);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 7);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 7);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 7);
                        }
                        if(comp >= 27 && comp <= 38){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 20);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 20);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 20);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 20);
                        }
                        if(comp >= 48 && comp <= 61){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 29);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 29);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 29);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 29);
                        }
                        if(comp >= 69 && comp <= 84){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 36);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 36);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 36);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 36);
                        }
                        if(comp >= 90 && comp <= 107){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 41);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 41);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 41);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 41);
                        }
                        if(comp >= 111 && comp <= 130){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 44);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 44);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 44);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 44);
                        }
                        if(comp >= 133 && comp <= 152){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 46);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 46);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 46);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 46);
                        }
                        if(comp >= 155 && comp <= 162){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 48);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 48);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 48);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 48);
                        }
                        if(comp >= 167 && comp <= 174){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 52);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 52);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 52);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 52);
                        }
                        if(comp >= 176 && comp <= 183){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 53);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 53);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 53);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 53);
                        }
                        if(comp >= 190 && comp <= 197){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 59);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 59);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 59);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 59);
                        }
                        if(comp >= 198 && comp <= 204){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 59);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 59);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 59);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 59);
                        }
                        if(comp >= 213 && comp <= 219){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 67);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 67);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 67);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 67);
                        }
                        if(comp >= 220 && comp <= 226){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 67);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 67);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 67);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 67);
                        }
                        if(comp >= 235 && comp <= 241){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 75);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 75);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 75);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 75);
                        }
                        if(comp >= 242 && comp <= 248){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 75);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 75);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 75);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 75);
                        }
                        if(comp >= 257 && comp <= 263){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 83);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 83);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 83);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 83);
                        }
                        if(comp >= 264 && comp <= 270){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 83);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 83);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 83);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 83);
                        }
                        if(comp >= 279 && comp <= 285){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 91);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 91);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 91);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 91);
                        }
                        if(comp >= 286 && comp <= 393){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 91);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 91);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 91);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 91);
                        }
                        if(comp >= 300 && comp <= 307){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 97);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 97);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 97);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 97);
                        }
                        if(comp >= 309 && comp <= 316){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 98);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 98);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 98);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 98);
                        }
                        if(comp >= 321 && comp <= 328){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 102);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 102);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 102);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 102);
                        }
                        if(comp >= 331 && comp <= 350){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 104);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 104);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 104);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 104);
                        }
                        if(comp >= 353 && comp <= 372){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 106);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 106);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 106);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 106);
                        }
                        if(comp >= 376 && comp <= 393){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 109);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 109);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 109);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 109);
                        }
                        if(comp >= 399 && comp <= 414){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 114);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 114);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 114);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 114);
                        }
                        if(comp >= 422 && comp <= 435){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 121);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 121);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 121);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 121);
                        }
                        if(comp >= 445 && comp <= 456){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 130);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 130);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 130);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 130);
                        }
                        if(comp >= 470 && comp <= 475){ 
                            this.getDataGroup().getItem(0,0,0).getH1F("occFADC").fill(comp - 143);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc").fill(adc*1.0,comp - 143);
                            this.getDataGroup().getItem(0,0,0).getH2F("fadc_time").fill(time*1.0,comp - 143);
                            this.getDetectorSummary().getH1F("summary").fill(comp - 143);
                        }
                          
                }
                
	    }
    	}
            
    }

    @Override
    public void timerUpdate() {

    }


}
