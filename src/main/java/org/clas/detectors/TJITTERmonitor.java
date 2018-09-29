package org.clas.detectors;

import java.util.ArrayList;
import java.util.List;
import org.clas.viewer.DetectorMonitor;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.group.DataGroup;
import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;
import org.jlab.utils.groups.IndexedList;

/**
 *
 * @author devita
 */

public class TJITTERmonitor  extends DetectorMonitor {        
    
    
    private int ctofPaddles = 48;
    private int ftofPaddles = 62;
    private double tdcconv  = 0.023456;
    
    public TJITTERmonitor(String name) {
        super(name);
        
        this.setDetectorTabNames("Time Jitter");
        this.init(false);
    }

    @Override
    public void createHistos() {
        // initialize canvas and create histograms
        this.setNumberOfEvents(0);
        H1F summary = new H1F("summary","summary",96,0.5,96.5);
        summary.setTitleX("PMT");
        summary.setTitleY("CTOF hits");
        summary.setTitle("CTOF");
        summary.setFillColor(38);
        DataGroup sum = new DataGroup(1,1);
        sum.addDataSet(summary, 0);
        this.setDetectorSummary(sum);
        
        H2F hi_ctof_tlphase = new H2F("hi_ctof_tlphase", "hi_ctof_tlphase", 6, 0, 6, 100, 150.0, 250.0); 
        hi_ctof_tlphase.setTitleX("Trigger Phase"); 
        hi_ctof_tlphase.setTitleY("#DeltaT (ns)");
        hi_ctof_tlphase.setTitle("CTOF Upstream PMTs");
        H2F hi_ctof_trphase = new H2F("hi_ctof_trphase", "hi_ctof_trphase", 6, 0, 6, 100, 150.0, 250.0); 
        hi_ctof_trphase.setTitleX("Trigger Phase"); 
        hi_ctof_trphase.setTitleY("#DeltaT (ns)");
        hi_ctof_trphase.setTitle("CTOF Downstream PMTs");

        H2F hi_ftof_tlphase = new H2F("hi_ftof_tlphase", "hi_ftof_tlphase", 6, 0, 6, 100, 50.0, 150.0); 
        hi_ftof_tlphase.setTitleX("Trigger Phase"); 
        hi_ftof_tlphase.setTitleY("#DeltaT (ns)");
        hi_ftof_tlphase.setTitle("FTOF-1B Left PMTs");
        H2F hi_ftof_trphase = new H2F("hi_ftof_trphase", "hi_ftof_trphase", 6, 0, 6, 100, 50.0, 150.0); 
        hi_ftof_trphase.setTitleX("Trigger Phase"); 
        hi_ftof_trphase.setTitleY("#DeltaT (ns)");
        hi_ftof_trphase.setTitle("FTOF-1B Right PMTs");

        DataGroup dg = new DataGroup(2,2);
        dg.addDataSet(hi_ctof_tlphase, 0);
        dg.addDataSet(hi_ctof_trphase, 2);
        dg.addDataSet(hi_ftof_tlphase, 1);
        dg.addDataSet(hi_ftof_trphase, 3);
        this.getDataGroup().add(dg, 0,0,0);

    }
        
    @Override
    public void plotHistos() {        
        // plotting histos
        this.getDetectorCanvas().getCanvas("Time Jitter").divide(2, 2);
        this.getDetectorCanvas().getCanvas("Time Jitter").setGridX(false);
        this.getDetectorCanvas().getCanvas("Time Jitter").setGridY(false);
        this.getDetectorCanvas().getCanvas("Time Jitter").cd(0);
        this.getDetectorCanvas().getCanvas("Time Jitter").getPad(0).getAxisZ().setLog(getLogZ());
        this.getDetectorCanvas().getCanvas("Time Jitter").draw(this.getDataGroup().getItem(0,0,0).getH2F("hi_ctof_tlphase"));
        this.getDetectorCanvas().getCanvas("Time Jitter").cd(1);
        this.getDetectorCanvas().getCanvas("Time Jitter").getPad(1).getAxisZ().setLog(getLogZ());
        this.getDetectorCanvas().getCanvas("Time Jitter").draw(this.getDataGroup().getItem(0,0,0).getH2F("hi_ftof_tlphase"));
        this.getDetectorCanvas().getCanvas("Time Jitter").cd(2);
        this.getDetectorCanvas().getCanvas("Time Jitter").getPad(2).getAxisZ().setLog(getLogZ());
        this.getDetectorCanvas().getCanvas("Time Jitter").draw(this.getDataGroup().getItem(0,0,0).getH2F("hi_ctof_trphase"));
        this.getDetectorCanvas().getCanvas("Time Jitter").cd(3);
        this.getDetectorCanvas().getCanvas("Time Jitter").getPad(3).getAxisZ().setLog(getLogZ());
        this.getDetectorCanvas().getCanvas("Time Jitter").draw(this.getDataGroup().getItem(0,0,0).getH2F("hi_ftof_trphase"));
        this.getDetectorCanvas().getCanvas("Time Jitter").update();
    }

    @Override
    public void processEvent(DataEvent event) {
        
        DataBank recRun    = null;
        DataBank ctofADC = null;
        DataBank ctofTDC = null;
        DataBank ftofADC = null;
        DataBank ftofTDC = null;
        if(event.hasBank("RUN::config"))            recRun      = event.getBank("RUN::config");
        if(event.hasBank("CTOF::adc"))              ctofADC     = event.getBank("CTOF::adc");
        if(event.hasBank("CTOF::tdc"))              ctofTDC     = event.getBank("CTOF::tdc");
        if(event.hasBank("FTOF::adc"))              ftofADC     = event.getBank("FTOF::adc");
        if(event.hasBank("FTOF::tdc"))              ftofTDC     = event.getBank("FTOF::tdc");

        
	int triggerPhase=0;
        if(recRun!=null) {
           long timestamp = recRun.getLong("timestamp",0);    
           triggerPhase= (int) ((timestamp)%6); // TI derived phase correction due to TDC and FADC clock differences
	}
        if(ctofADC!=null && ctofTDC!=null) {
	   IndexedList<ArrayList<Integer>> tdcs = new IndexedList<ArrayList<Integer>>(3);
	   IndexedList<ArrayList<Double>>  adcs = new IndexedList<ArrayList<Double>>(3);
	   for(int i=0; i<ctofADC.rows(); i++) {
	       int paddle = ctofADC.getShort("component",i);
               int order  = ctofADC.getByte("order",i);
	       int icomp  = (paddle-1)*2+order+1;
	       double adct = (double) ctofADC.getFloat("time",i);
	       if(adct>0) {
                  if(!adcs.hasItem(1,1,icomp)) adcs.add(new ArrayList<Double>(),1,1,icomp);
                  adcs.getItem(1,1,icomp).add(adct);
		}
	   }
	   for(int i=0; i<ctofTDC.rows(); i++) {
	       int paddle = ctofTDC.getShort("component",i);
               int order  = ctofTDC.getByte("order",i)-2;
	       int icomp  = (paddle-1)*2+order+1;
	       int tdc    = ctofTDC.getInt("TDC",i);
	       if(tdc>0) {
	          if(!tdcs.hasItem(1,1,icomp)) tdcs.add(new ArrayList<Integer>(),1,1,icomp);
                  tdcs.getItem(1,1,icomp).add(tdc);
		}
	    }
            for(int icomp=1; icomp<this.ctofPaddles*2+1; icomp++) {
		if(tdcs.hasItem(1,1,icomp) && adcs.hasItem(1,1,icomp)) {
                   List<Integer> listTDC = new ArrayList<Integer>();
                   List<Double>  listADC = new ArrayList<Double>();
                   listTDC = tdcs.getItem(1,1,icomp);
                   listADC = adcs.getItem(1,1,icomp);
		   for(int iadc=0; iadc<listADC.size(); iadc++) {
                       for(int itdc=0; itdc<listTDC.size(); itdc++) {
			   double adc = listADC.get(iadc);
			   int tdc = listTDC.get(itdc);
			   if(icomp%2 ==0) this.getDataGroup().getItem(0,0,0).getH2F("hi_ctof_tlphase").fill(triggerPhase,tdc*tdcconv-adc);
                           else            this.getDataGroup().getItem(0,0,0).getH2F("hi_ctof_trphase").fill(triggerPhase,tdc*tdcconv-adc);
			}
                     }
		 }
	     }
         }
        if(ftofADC!=null && ftofTDC!=null) {
	   IndexedList<ArrayList<Integer>> tdcs = new IndexedList<ArrayList<Integer>>(3);
	   IndexedList<ArrayList<Double>>  adcs = new IndexedList<ArrayList<Double>>(3);
	   for(int i=0; i<ctofADC.rows(); i++) {
	       int layer  = ftofADC.getByte("layer",i);
	       int paddle = ftofADC.getShort("component",i);
               int order  = ftofADC.getByte("order",i);
	       int icomp  = (paddle-1)*2+order+1;
	       double adct = (double) ftofADC.getFloat("time",i);
	       if(adct>0 && layer==2) {
                  if(!adcs.hasItem(1,1,icomp)) adcs.add(new ArrayList<Double>(),1,1,icomp);
                  adcs.getItem(1,1,icomp).add(adct);
		}
	   }
	   for(int i=0; i<ftofTDC.rows(); i++) {
	       int layer  = ftofTDC.getByte("layer",i);
	       int paddle = ftofTDC.getShort("component",i);
               int order  = ftofTDC.getByte("order",i)-2;
	       int icomp  = (paddle-1)*2+order+1;
	       int tdc    = ftofTDC.getInt("TDC",i);
	       if(tdc>0 && layer==2) {
	          if(!tdcs.hasItem(1,1,icomp)) tdcs.add(new ArrayList<Integer>(),1,1,icomp);
                  tdcs.getItem(1,1,icomp).add(tdc);
		}
	    }
            for(int icomp=1; icomp<this.ftofPaddles*2+1; icomp++) {
		if(tdcs.hasItem(1,1,icomp) && adcs.hasItem(1,1,icomp)) {
                   List<Integer> listTDC = new ArrayList<Integer>();
                   List<Double>  listADC = new ArrayList<Double>();
                   listTDC = tdcs.getItem(1,1,icomp);
                   listADC = adcs.getItem(1,1,icomp);
		   for(int iadc=0; iadc<listADC.size(); iadc++) {
                       for(int itdc=0; itdc<listTDC.size(); itdc++) {
			   double adc = listADC.get(iadc);
			   int tdc = listTDC.get(itdc);
			   if(icomp%2 ==0) this.getDataGroup().getItem(0,0,0).getH2F("hi_ftof_tlphase").fill(triggerPhase,tdc*tdcconv-adc);
                           else            this.getDataGroup().getItem(0,0,0).getH2F("hi_ftof_trphase").fill(triggerPhase,tdc*tdcconv-adc);
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
