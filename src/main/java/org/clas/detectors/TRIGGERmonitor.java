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
    
    private double tdc2Time = 0.023436;
    
    public TRIGGERmonitor(String name) {
        super(name);
        this.setDetectorTabNames("Trigger");
        this.init(false);
    }

    
    @Override
    public void createHistos() {
        // create histograms
        this.setNumberOfEvents(0);
        H1F summary = new H1F("summary","summary",100,0.0,1.0);
        summary.setTitleX("trigger");
        summary.setTitleY("counts");
        summary.setFillColor(33);
        DataGroup sum = new DataGroup(1,1);
        sum.addDataSet(summary, 0);
        this.setDetectorSummary(sum);
        
        H1F trig = new H1F("trig","trig", 100,0.0,1.0);
        trig.setTitleX("trigger");
        trig.setTitleY("Counts");
        trig.setFillColor(33);
        
        DataGroup dg = new DataGroup(1,1);
        dg.addDataSet(trig, 0);

        this.getDataGroup().add(dg, 0,0,0);
    }
        
    @Override
    public void plotHistos() {
        // initialize canvas and plot histograms
        this.getDetectorCanvas().getCanvas("Trigger").divide(1, 1);
        this.getDetectorCanvas().getCanvas("Trigger").setGridX(false);
        this.getDetectorCanvas().getCanvas("Trigger").setGridY(false);
        this.getDetectorCanvas().getCanvas("Trigger").cd(0);
        this.getDetectorCanvas().getCanvas("Trigger").draw(this.getDataGroup().getItem(0,0,0).getH1F("trig"));
        
    }

    @Override
    public void processEvent(DataEvent event) {

    }

    @Override
    public void timerUpdate() {

    }

    
}
