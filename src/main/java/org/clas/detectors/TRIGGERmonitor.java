package org.clas.detectors;

import java.util.ArrayList;
import org.clas.viewer.DetectorMonitor;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.fitter.DataFitter;
import org.jlab.groot.group.DataGroup;
import org.jlab.groot.math.F1D;
import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;


public class TRIGGERmonitor extends DetectorMonitor {
    
    
    public TRIGGERmonitor(String name) {
        super(name);
        this.setDetectorTabNames("Trigger beam", "Trigger cosmic");
        this.init(false);
    }

    
    @Override
    public void createHistos() {
        // create histograms
        this.setNumberOfEvents(0);
        H1F summary = new H1F("summary","summary",100,0.5,10000);
        summary.setTitleX("trigger");
        summary.setTitleY("counts");
        summary.setFillColor(33);
        DataGroup sum = new DataGroup(1,1);
        sum.addDataSet(summary, 0);
        this.setDetectorSummary(sum);
        
        H1F trig = new H1F("trigger_beam","trigger_beam", 32,0.5,32.5);
        trig.setTitleX("trigger beam");
        trig.setTitleY("Counts");
        H1F trig_cos = new H1F("trigger_cosmic","trigger_cosmic", 5,0.5,5.5);
        trig_cos.setTitleX("trigger cosmic (1 = FD, 2 = SVT, 3 = CTOF, 4 = CND, 5 = MVT)");
        trig_cos.setTitleY("Counts");
        
        DataGroup dg = new DataGroup(1,2);
        dg.addDataSet(trig, 0);
        dg.addDataSet(trig_cos, 1);

        this.getDataGroup().add(dg, 0,0,0);
    }
        
    @Override
    public void plotHistos() {
        // initialize canvas and plot histograms
        this.getDetectorCanvas().getCanvas("Trigger beam").divide(1, 1);
        this.getDetectorCanvas().getCanvas("Trigger beam").setGridX(false);
        this.getDetectorCanvas().getCanvas("Trigger beam").setGridY(false);
        this.getDetectorCanvas().getCanvas("Trigger beam").cd(0);
        this.getDetectorCanvas().getCanvas("Trigger beam").draw(this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam"));
        this.getDetectorCanvas().getCanvas("Trigger beam").update();
        
        this.getDetectorCanvas().getCanvas("Trigger cosmic").divide(1, 1);
        this.getDetectorCanvas().getCanvas("Trigger cosmic").setGridX(false);
        this.getDetectorCanvas().getCanvas("Trigger cosmic").setGridY(false);
        this.getDetectorCanvas().getCanvas("Trigger cosmic").cd(0);
        this.getDetectorCanvas().getCanvas("Trigger cosmic").draw(this.getDataGroup().getItem(0,0,0).getH1F("trigger_cosmic"));
        this.getDetectorCanvas().getCanvas("Trigger cosmic").update();
        
        
    }

    @Override
    public void processEvent(DataEvent event) {

    // process event info and save into data group
        
        if(event.hasBank("RUN::config")==true){
	    DataBank bank = event.getBank("RUN::config");
            
            
	    int rows = bank.rows();
            
	    for(int loop = 0; loop < rows; loop++){
                
                int trigger  = bank.getInt("trigger", loop);

                if(trigger == 1) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(1);
                if(trigger == 2) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(2);
                if(trigger == 3) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(3);
                if(trigger == 4) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(4);
                if(trigger == 5) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(5);
                if(trigger == 6) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(6);
                if(trigger == 7) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(7);
                if(trigger == 8) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(8);
                if(trigger == 9) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(9);
                if(trigger == 10) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(10);
                if(trigger == 11) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(11);
                if(trigger == 12) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(12);
                if(trigger == 13) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(13);
                if(trigger == 14) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(14);
                if(trigger == 15) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(15);
                if(trigger == 16) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(16);
                if(trigger == 17) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(17);
                if(trigger == 18) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(18);
                if(trigger == 19) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(19);
                if(trigger == 20) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(20);
                if(trigger == 21) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(21);
                if(trigger == 22) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(22);
                if(trigger == 23) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(23);
                if(trigger == 24) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(24);
                if(trigger == 25) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(25);
                if(trigger == 26) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(26);
                if(trigger == 27) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(27);
                if(trigger == 28) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(28);
                if(trigger == 29) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(29);
                if(trigger == 30) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(30);
                if(trigger == 31) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(31);
                if(trigger == 32) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(32);
                
                if(trigger == 31428) this.getDataGroup().getItem(0,0,0).getH1F("trigger_cosmic").fill(1);  // forward carriage   0x3F000000
                if(trigger == 256) this.getDataGroup().getItem(0,0,0).getH1F("trigger_cosmic").fill(2);    // SVT  0x00000100
                if(trigger == 512) this.getDataGroup().getItem(0,0,0).getH1F("trigger_cosmic").fill(3);    // CTOF 0x00000200
                if(trigger == 1024) this.getDataGroup().getItem(0,0,0).getH1F("trigger_cosmic").fill(4);   // CND  0x00000400
                if(trigger == 2048) this.getDataGroup().getItem(0,0,0).getH1F("trigger_cosmic").fill(5);   // MVT  0x00000800
                
            }
	}
                
         
    }

    @Override
    public void timerUpdate() {

    }

    
}
