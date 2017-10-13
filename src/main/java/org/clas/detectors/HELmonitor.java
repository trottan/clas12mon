/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author devita
 */
public class HELmonitor extends DetectorMonitor {
    
    private double tdc2Time = 0.023436;
    
    public HELmonitor(String name) {
        super(name);
        this.setDetectorTabNames("Helicity");
        this.init(false);
    }

    
    @Override
    public void createHistos() {
        // create histograms
        this.setNumberOfEvents(0);
        H1F summary = new H1F("summary","summary",100,0.0,1.0);
        summary.setTitleX("helicity");
        summary.setTitleY("counts");
        summary.setFillColor(33);
        DataGroup sum = new DataGroup(1,1);
        sum.addDataSet(summary, 0);
        this.setDetectorSummary(sum);
        H1F hel = new H1F("hel","hel", 100,0.0,1.0);
        hel.setTitleX("helicity");
        hel.setTitleY("Counts");
        hel.setFillColor(33);
        
        DataGroup dg = new DataGroup(1,1);
        dg.addDataSet(hel, 0);

        this.getDataGroup().add(dg, 0,0,0);
    }
        
    @Override
    public void plotHistos() {
        // initialize canvas and plot histograms
        this.getDetectorCanvas().getCanvas("Helicity").divide(1, 1);
        this.getDetectorCanvas().getCanvas("Helicity").setGridX(false);
        this.getDetectorCanvas().getCanvas("Helicity").setGridY(false);
        this.getDetectorCanvas().getCanvas("Helicity").cd(0);
        this.getDetectorCanvas().getCanvas("Helicity").draw(this.getDataGroup().getItem(0,0,0).getH1F("hel"));
        
    }

    @Override
    public void processEvent(DataEvent event) {

    }

    @Override
    public void timerUpdate() {

    }

    public void fitRF(H1F hirf, F1D f1rf) {
     
    }
    
}
