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

public class FCUPmonitor extends DetectorMonitor {
    
    
    public FCUPmonitor(String name) {
        super(name);
        this.setDetectorTabNames("Faraday Cup");
        this.init(false);
    }

    
    @Override
    public void createHistos() {
        // create histograms
        this.setNumberOfEvents(0);
        H1F summary = new H1F("summary","summary",100,0.0,1.0);
        summary.setTitleX("Faraday Cup");
        summary.setTitleY("counts");
        summary.setFillColor(33);
        DataGroup sum = new DataGroup(1,1);
        sum.addDataSet(summary, 0);
        this.setDetectorSummary(sum);
        H1F fcup = new H1F("fcup","fcup", 100,0.0,1.0);
        fcup.setTitleX("helicity");
        fcup.setTitleY("Counts");
        fcup.setFillColor(33);
        
        DataGroup dg = new DataGroup(1,1);
        dg.addDataSet(fcup, 0);

        this.getDataGroup().add(dg, 0,0,0);
    }
        
    @Override
    public void plotHistos() {
        // initialize canvas and plot histograms
        this.getDetectorCanvas().getCanvas("Faraday Cup").divide(1, 1);
        this.getDetectorCanvas().getCanvas("Faraday Cup").setGridX(false);
        this.getDetectorCanvas().getCanvas("Faraday Cup").setGridY(false);
        this.getDetectorCanvas().getCanvas("Faraday Cup").cd(0);
        this.getDetectorCanvas().getCanvas("Faraday Cup").draw(this.getDataGroup().getItem(0,0,0).getH1F("fcup"));
        
    }

    @Override
    public void processEvent(DataEvent event) {

    }

    @Override
    public void timerUpdate() {

    }
    
}
