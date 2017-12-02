package org.clas.detectors;

import java.util.ArrayList;
import org.clas.viewer.DetectorMonitor;
import org.jlab.detector.base.DetectorType;
import org.jlab.detector.view.DetectorShape2D;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.fitter.DataFitter;
import org.jlab.groot.group.DataGroup;
import org.jlab.groot.math.F1D;
import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;


public class TRIGGERmonitor extends DetectorMonitor {
    
	int trigger=0;
    int trigFC=0 ;
    int trigCD=0 ;
    
    public TRIGGERmonitor(String name) {
        super(name);
        this.setDetectorTabNames("Trigger beam", "Trigger cosmic", "EC peak trigger", "EC cluster trigger", "HTCC cluster trigger", "FTOF Cluster trigger");
        this.useSectorButtons(true);
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
        H1F trig_cos = new H1F("trigger_cosmic","trigger_cosmic", 6,0.5,6.5);
        trig_cos.setTitleX("trigger cosmic (1=FD  2=HTCC  3=SVT  4=CTOF  5=CND  6=MVT)");
        trig_cos.setTitleY("Counts");
        

        DataGroup dg = new DataGroup(4,3);
        
        
        for(int sec=1; sec <= 6; sec++) {
            
            /// ECAL peak
            
            H1F hist_ecpeak_energy = new H1F("ecpeak_energy"+sec, "ecpeak_energy"+sec, 120, 0, 12);
            hist_ecpeak_energy.setTitleX("EC peak energy");
            hist_ecpeak_energy.setTitleY("counts");
            hist_ecpeak_energy.setTitle("Sector "+sec);
            
            H1F hist_ecpeak_time = new H1F("ecpeak_time"+sec, "ecpeak_time"+sec, 1000, 0, 1000);
            hist_ecpeak_time.setTitleX("EC peak time");
            hist_ecpeak_time.setTitleY("counts");
            hist_ecpeak_time.setTitle("Sector "+sec);
            
            H1F hist_ecpeak_coord = new H1F("ecpeak_coord"+sec, "ecpeak_coord"+sec, 200, 0., 200.);
            hist_ecpeak_coord.setTitleX("EC peak coordinate");
            hist_ecpeak_coord.setTitleY("counts");
            hist_ecpeak_coord.setTitle("Sector "+sec);

            // ECAL cluster
            
            H1F hist_eccluster_energy = new H1F("eccluster_energy"+sec, "eccluster_energy"+sec, 120, 0, 12);
            hist_eccluster_energy.setTitleX("EC cluster energy");
            hist_eccluster_energy.setTitleY("counts");
            hist_eccluster_energy.setTitle("Sector "+sec);
            
            H1F hist_eccluster_time = new H1F("eccluster_time"+sec, "eccluster_time"+sec, 1000, 0, 1000);
            hist_eccluster_time.setTitleX("EC cluster time");
            hist_eccluster_time.setTitleY("counts");
            hist_eccluster_time.setTitle("Sector "+sec);
            
            H2F hist_eccluster_coord = new H2F("eccluster_coord"+sec, "eccluster_coord"+sec, 200, 0, 200, 200, 0, 200);
            hist_eccluster_coord.setTitleX("EC cluster coordinate");
            hist_eccluster_coord.setTitleY("counts");
            hist_eccluster_coord.setTitle("Sector "+sec);
            
            
             /// HTTC
        
            H1F hist_httc_cluster_mask_high = new H1F("httc_cluster_mask_high"+sec,"httc_cluster_mask_high"+sec, 200,0,200);
            hist_httc_cluster_mask_high.setTitleX("httc_cluster_mask_high");
            hist_httc_cluster_mask_high.setTitleY("Counts");
            hist_httc_cluster_mask_high.setTitle("Sector "+sec);
            H1F hist_httc_cluster_mask_low = new H1F("httc_cluster_mask_low"+sec,"httc_cluster_mask_low"+sec, 200,0,200);
            hist_httc_cluster_mask_low.setTitleX("httc_cluster_mask_low");
            hist_httc_cluster_mask_low.setTitleY("Counts");
            hist_httc_cluster_mask_low.setTitle("Sector "+sec);
        
            /// FTOF
        
            H1F hist_ftof_cluster_mask_high = new H1F("ftof_cluster_mask_high"+sec,"ftof_cluster_mask_high"+sec, 200,0,200);
            hist_ftof_cluster_mask_high.setTitleX("ftof_cluster_mask_high");
            hist_ftof_cluster_mask_high.setTitleY("Counts");
            hist_ftof_cluster_mask_high.setTitle("Sector "+sec);
            H1F hist_ftof_cluster_mask_low = new H1F("ftof_cluster_mask_low"+sec,"ftof_cluster_mask_low"+sec, 200,0,200);
            hist_ftof_cluster_mask_low.setTitleX("ftof_cluster_mask_low");
            hist_ftof_cluster_mask_low.setTitleY("Counts");
            hist_ftof_cluster_mask_low.setTitle("Sector "+sec);
            
            
            dg.addDataSet(hist_ecpeak_energy, 0);
            dg.addDataSet(hist_ecpeak_time, 1);
            dg.addDataSet(hist_ecpeak_coord, 2);
            dg.addDataSet(hist_eccluster_energy, 3);
            dg.addDataSet(hist_eccluster_time, 4);
            dg.addDataSet(hist_eccluster_coord, 5);
            dg.addDataSet(hist_httc_cluster_mask_high, 6);
            dg.addDataSet(hist_httc_cluster_mask_low, 7);
            dg.addDataSet(hist_ftof_cluster_mask_high, 8);
            dg.addDataSet(hist_ftof_cluster_mask_low, 9);

            this.getDataGroup().add(dg,sec,0,0);

        }
        
        dg.addDataSet(trig, 10);
        dg.addDataSet(trig_cos, 11);

        this.getDataGroup().add(dg, 0,0,0);
    }
        
    
    @Override
        public void drawDetector() {

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
        
        this.getDetectorCanvas().getCanvas("EC peak trigger").divide(3, 1);
        this.getDetectorCanvas().getCanvas("EC peak trigger").setGridX(false);
        this.getDetectorCanvas().getCanvas("EC peak trigger").setGridY(false);
        this.getDetectorCanvas().getCanvas("EC cluster trigger").divide(3, 1);
        this.getDetectorCanvas().getCanvas("EC cluster trigger").setGridX(false);
        this.getDetectorCanvas().getCanvas("EC cluster trigger").setGridY(false);
        this.getDetectorCanvas().getCanvas("HTCC cluster trigger").divide(1, 2);
        this.getDetectorCanvas().getCanvas("HTCC cluster trigger").setGridX(false);
        this.getDetectorCanvas().getCanvas("HTCC cluster trigger").setGridY(false);
        this.getDetectorCanvas().getCanvas("FTOF Cluster trigger").divide(1, 2);
        this.getDetectorCanvas().getCanvas("FTOF Cluster trigger").setGridX(false);
        this.getDetectorCanvas().getCanvas("FTOF Cluster trigger").setGridY(false);
        
        
        for(int sec=1; sec<7; sec++) {
            if(getActiveSector()==sec) {
                this.getDetectorCanvas().getCanvas("EC peak trigger").cd(0);
                this.getDetectorCanvas().getCanvas("EC peak trigger").draw(this.getDataGroup().getItem(0,0,0).getH1F("ecpeak_energy"+sec));
                this.getDetectorCanvas().getCanvas("EC peak trigger").cd(1);
                this.getDetectorCanvas().getCanvas("EC peak trigger").draw(this.getDataGroup().getItem(0,0,0).getH1F("ecpeak_time"+sec));
                this.getDetectorCanvas().getCanvas("EC peak trigger").cd(2);
                this.getDetectorCanvas().getCanvas("EC peak trigger").draw(this.getDataGroup().getItem(0,0,0).getH1F("ecpeak_coord"+sec));
                this.getDetectorCanvas().getCanvas("EC peak trigger").update();
                
                this.getDetectorCanvas().getCanvas("EC cluster trigger").cd(0);
                this.getDetectorCanvas().getCanvas("EC cluster trigger").draw(this.getDataGroup().getItem(0,0,0).getH1F("eccluster_energy"+sec));
                this.getDetectorCanvas().getCanvas("EC cluster trigger").cd(1);
                this.getDetectorCanvas().getCanvas("EC cluster trigger").draw(this.getDataGroup().getItem(0,0,0).getH1F("eccluster_time"+sec));
                this.getDetectorCanvas().getCanvas("EC cluster trigger").cd(2);
                this.getDetectorCanvas().getCanvas("EC cluster trigger").draw(this.getDataGroup().getItem(0,0,0).getH2F("eccluster_coord"+sec));
                this.getDetectorCanvas().getCanvas("EC cluster trigger").update();
        
                this.getDetectorCanvas().getCanvas("HTCC cluster trigger").cd(0);
                this.getDetectorCanvas().getCanvas("HTCC cluster trigger").draw(this.getDataGroup().getItem(0,0,0).getH1F("httc_cluster_mask_high"+sec));
                this.getDetectorCanvas().getCanvas("HTCC cluster trigger").cd(1);
                this.getDetectorCanvas().getCanvas("HTCC cluster trigger").draw(this.getDataGroup().getItem(0,0,0).getH1F("httc_cluster_mask_low"+sec));
                this.getDetectorCanvas().getCanvas("HTCC cluster trigger").update();

                this.getDetectorCanvas().getCanvas("FTOF Cluster trigger").cd(0);
                this.getDetectorCanvas().getCanvas("FTOF Cluster trigger").draw(this.getDataGroup().getItem(0,0,0).getH1F("ftof_cluster_mask_high"+sec));
                this.getDetectorCanvas().getCanvas("FTOF Cluster trigger").cd(1);
                this.getDetectorCanvas().getCanvas("FTOF Cluster trigger").draw(this.getDataGroup().getItem(0,0,0).getH1F("ftof_cluster_mask_low"+sec));
                this.getDetectorCanvas().getCanvas("FTOF Cluster trigger").update();
            }
        }
        
    }

    @Override
    public void processEvent(DataEvent event) {

        if (this.getNumberOfEvents() >= super.eventResetTime_current[18] && super.eventResetTime_current[18] > 0){
            resetEventListener();
        }
        
        // process event info and save into data group
        
        if(event.hasBank("RUN::config")==true){
        	
	        DataBank bank = event.getBank("RUN::config");
                        
            trigger  = bank.getInt("trigger", 0);
            
            trigFC = getFCTrigger(); //Forward Carriage and HTCC
            trigCD = getCDTrigger(); //Central Detector
            
//            for (int i=1; i<33; i++) if(trigger==i) this.getDataGroup().getItem(0,0,0).getH1F("trigger_beam").fill(i);
                           
            if(isGoodFC())  this.getDataGroup().getItem(0,0,0).getH1F("trigger_cosmic").fill(1);  
            if(isGoodHTCC())this.getDataGroup().getItem(0,0,0).getH1F("trigger_cosmic").fill(2);
            if(isGoodSVT()) this.getDataGroup().getItem(0,0,0).getH1F("trigger_cosmic").fill(3);   
            if(isGoodCTOF())this.getDataGroup().getItem(0,0,0).getH1F("trigger_cosmic").fill(4);   
            if(isGoodCND()) this.getDataGroup().getItem(0,0,0).getH1F("trigger_cosmic").fill(5);   
            if(isGoodMVT()) this.getDataGroup().getItem(0,0,0).getH1F("trigger_cosmic").fill(6);   
                          
	    }       
        
        for(int sec=1; sec<=6; sec++) {
            if (isGoodTrigger(sec)) {
                this.getDataGroup().getItem(sec,0,0).getH1F("ecpeak_energy"+sec).fill(0);
                this.getDataGroup().getItem(sec,0,0).getH1F("ecpeak_time"+sec).fill(0);
                this.getDataGroup().getItem(sec,0,0).getH1F("ecpeak_coord"+sec).fill(0);
                
                this.getDataGroup().getItem(sec,0,0).getH1F("eccluster_energy"+sec).fill(0);
                this.getDataGroup().getItem(sec,0,0).getH1F("eccluster_time"+sec).fill(0);
                this.getDataGroup().getItem(sec,0,0).getH2F("eccluster_coord"+sec).fill(0,0);
                
                this.getDataGroup().getItem(sec,0,0).getH1F("httc_cluster_mask_high"+sec).fill(0);
                this.getDataGroup().getItem(sec,0,0).getH1F("httc_cluster_mask_low"+sec).fill(0);
                
                this.getDataGroup().getItem(sec,0,0).getH1F("ftof_cluster_mask_high"+sec).fill(0);
                this.getDataGroup().getItem(sec,0,0).getH1F("ftof_cluster_mask_low"+sec).fill(0);
            }
        }
              
         
    }
    public boolean isGoodFC() {
    	return (this.trigFC>=256&&this.trigFC<=8196);
    }
    
    public boolean isGoodHTCC() {
    	return (this.trigFC==1);
    }
    
    public boolean isGoodSVT() {
    	return (this.trigCD==256);
    }
    
    public boolean isGoodCTOF() {
    	return (this.trigCD==512);
    }
    
    public boolean isGoodCND() {
    	return (this.trigCD==1024);
    }
    
    public boolean isGoodMVT() {
    	return (this.trigCD==2048);
    }
    
    public int getFCTriggerSector() {  	    
	    return (int) ((this.trigFC >= 256) ? Math.log10(this.trigFC>>8)/0.301+1:0);
    }
    
    
    public int getFCTrigger() {    	    
	    return (this.trigger>>16)&0x0000ffff;
    }
      
    public int getCDTrigger() {
	    return this.trigger&0x00000fff;
    } 
    
    @Override
    public void timerUpdate() {

    }

    
}
