package org.clas.detectors;

import org.clas.viewer.DetectorMonitor;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.group.DataGroup;
import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;


public class RECmonitor extends DetectorMonitor {
    

    public RECmonitor(String name) {
        super(name);
        this.setDetectorTabNames("CVT tracking plots");
        this.init(false);
    }
    
    @Override
    public void createHistos() {
        // create histograms
        this.setNumberOfEvents(0);

        H1F trk_mult = new H1F("trk_mult", "trk_mult", 15, 0.5, 15.5);
        trk_mult.setTitleX("track multiplicity");
        trk_mult.setTitleY("counts");
        trk_mult.setTitle("track multiplicity");

        H1F trk_phi = new H1F("trk_phi", "trk_phi", 180, 0, 180);
        trk_phi.setTitleX("track phi");
        trk_phi.setTitleY("counts");
        trk_phi.setTitle("track phi angle");
        
        H1F trk_theta = new H1F("trk_theta", "trk_theta", 180, 0, 180);
        trk_theta.setTitleX("track theta");
        trk_theta.setTitleY("counts");
        trk_theta.setTitle("track theta angle");

        H1F trk_norm_chi2 = new H1F("trk_norm_chi2", "trk_norm_chi2", 100, 0,5);
        trk_norm_chi2.setTitleX("track norm. chi2");
        trk_norm_chi2.setTitleY("counts");
        trk_norm_chi2.setTitle("track normalized chi2");
        
        DataGroup dg = new DataGroup(2,2);
        dg.addDataSet(trk_mult, 0);
        dg.addDataSet(trk_phi, 1);
        dg.addDataSet(trk_theta, 2);
        dg.addDataSet(trk_norm_chi2, 2);
        this.getDataGroup().add(dg,0,0,0);
        
    }
        
    @Override
    public void plotHistos() {
        // initialize canvas and plot histograms
        
        this.getDetectorCanvas().getCanvas("CVT tracking plots").divide(2, 2);
        this.getDetectorCanvas().getCanvas("CVT tracking plots").setGridX(false);
        this.getDetectorCanvas().getCanvas("CVT tracking plots").setGridY(false);
        this.getDetectorCanvas().getCanvas("CVT tracking plots").cd(0);
        this.getDetectorCanvas().getCanvas("CVT tracking plots").draw(this.getDataGroup().getItem(0,0,0).getH1F("trk_mult"));
        this.getDetectorCanvas().getCanvas("CVT tracking plots").cd(1);
        this.getDetectorCanvas().getCanvas("CVT tracking plots").draw(this.getDataGroup().getItem(0,0,0).getH1F("trk_phi"));
        this.getDetectorCanvas().getCanvas("CVT tracking plots").cd(2);
        this.getDetectorCanvas().getCanvas("CVT tracking plots").draw(this.getDataGroup().getItem(0,0,0).getH1F("trk_theta"));
        this.getDetectorCanvas().getCanvas("CVT tracking plots").cd(3);
        this.getDetectorCanvas().getCanvas("CVT tracking plots").draw(this.getDataGroup().getItem(0,0,0).getH1F("trk_norm_chi2"));

    }

    @Override
    public void processEvent(DataEvent event) {
        
        // process event info and save into data group
                
        if (event.hasBank("CVTRec::Cosmics")==true){
            DataBank bankSVTCosmics = (DataBank) event.getBank("CVTRec::Cosmics");
            int nTracks = bankSVTCosmics.rows();
            for (int row = 0; row < nTracks; ++row) {
                float  svtCosmicTrack_phi = bankSVTCosmics.getFloat("phi", row);
                float  svtCosmicTrack_theta = bankSVTCosmics.getFloat("theta", row);
                float  svtCosmicTrack_kfChi2 = bankSVTCosmics.getFloat("chi2", row);
                int    svtCosmicTrack_kfNdf = bankSVTCosmics.getInt("ndf", row);
                
                if(nTracks > 0){
                this.getDataGroup().getItem(0,0,0).getH1F("trk_mult").fill(svtCosmicTrack_kfNdf);
                this.getDataGroup().getItem(0,0,0).getH1F("trk_phi").fill(svtCosmicTrack_phi);
                this.getDataGroup().getItem(0,0,0).getH1F("trk_theta").fill(svtCosmicTrack_theta);
                this.getDataGroup().getItem(0,0,0).getH1F("trk_norm_chi2").fill(svtCosmicTrack_kfChi2);
                }
                
            }
  
        } 
        else if (event.hasBank("CVTRec::Tracks")) {
            DataBank bankSVTTracks = event.getBank("CVTRec::Tracks");
            int nTracks = bankSVTTracks.rows();
            for (int row = 0; row < nTracks; ++row) {
                int svtTrack_q = bankSVTTracks.getInt("q", row);
            }
        } 
                
                
    }
                  

    @Override
    public void timerUpdate() {
        
    }

}
