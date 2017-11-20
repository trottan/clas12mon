package org.clas.detectors;

import org.clas.viewer.DetectorMonitor;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.group.DataGroup;
import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;
import org.jlab.clas.physics.Vector3;


public class RECmonitor extends DetectorMonitor {
    

    public RECmonitor(String name) {
        super(name);
        this.setDetectorTabNames("CVT tracking plots", "DC tracks per event", "DC hits per track", "DC momentum", "DC theta angle");
        this.init(false);
    }
    
    @Override
    public void createHistos() {
        // create histograms
        this.setNumberOfEvents(0);

        H1F summary = new H1F("summary","summary",6,0.5,6.5);
        summary.setTitleX("sector");
        summary.setTitleY("REC");
        summary.setFillColor(33);
        DataGroup sum = new DataGroup(1,1);
        sum.addDataSet(summary, 0);
        this.setDetectorSummary(sum);
        
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
        dg.addDataSet(trk_norm_chi2, 3);
        this.getDataGroup().add(dg,0,0,0);
        
        
        for(int sector=1; sector <= 6; sector++) {
            H1F cd_trk_ev = new H1F("cd_trk_ev_sec" + sector, "number of tracks per events  sector " + sector, 20, 0.5, 20.5);
            cd_trk_ev.setTitleX("number of tracks per event");
            cd_trk_ev.setTitleY("counts");
            cd_trk_ev.setTitle("sector "+sector);
            H1F cd_hits_per_trk = new H1F("cd_hits_per_trk_sec" + sector, "number of hits per track  sector " + sector, 150, 0.5, 150.5);
            cd_hits_per_trk.setTitleX("number of hits per track");
            cd_hits_per_trk.setTitleY("counts");
            cd_hits_per_trk.setTitle("sector "+sector);
            H1F cd_momentum = new H1F("cd_momentum_sec" + sector, "momentum distribution  sector " + sector, 106, 0.0, 10.6);
            cd_momentum.setTitleX("momentum");
            cd_momentum.setTitleY("counts");
            cd_momentum.setTitle("sector "+sector);
            H1F cd_theta = new H1F("cd_theta_sec" + sector, "theta distribution  sector " + sector, 126, 0.0, 3.15);
            cd_theta.setTitleX("theta");
            cd_theta.setTitleY("counts");
            cd_theta.setTitle("sector "+sector);
  
            dg.addDataSet(cd_trk_ev, 0);
            dg.addDataSet(cd_hits_per_trk, 1);
            dg.addDataSet(cd_momentum, 2);
            dg.addDataSet(cd_theta, 3);
            this.getDataGroup().add(dg, sector,0,0);
        }
            
        
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
        
        this.getDetectorCanvas().getCanvas("DC tracks per event").divide(2, 3);
        this.getDetectorCanvas().getCanvas("DC tracks per event").setGridX(false);
        this.getDetectorCanvas().getCanvas("DC tracks per event").setGridY(false);
        this.getDetectorCanvas().getCanvas("DC hits per track").divide(2, 3);
        this.getDetectorCanvas().getCanvas("DC hits per track").setGridX(false);
        this.getDetectorCanvas().getCanvas("DC hits per track").setGridY(false);
        this.getDetectorCanvas().getCanvas("DC momentum").divide(2, 3);
        this.getDetectorCanvas().getCanvas("DC momentum").setGridX(false);
        this.getDetectorCanvas().getCanvas("DC momentum").setGridY(false);
        this.getDetectorCanvas().getCanvas("DC theta angle").divide(2, 3);
        this.getDetectorCanvas().getCanvas("DC theta angle").setGridX(false);
        this.getDetectorCanvas().getCanvas("DC theta angle").setGridY(false);
        
        for(int sector=1; sector <=6; sector++) {
            this.getDetectorCanvas().getCanvas("DC tracks per event").getPad(sector-1).getAxisZ().setLog(getLogZ());
            this.getDetectorCanvas().getCanvas("DC tracks per event").cd(sector-1);
            this.getDetectorCanvas().getCanvas("DC tracks per event").draw(this.getDataGroup().getItem(sector,0,0).getH1F("cd_trk_ev_sec"+sector));
            this.getDetectorCanvas().getCanvas("DC hits per track").getPad(sector-1).getAxisZ().setLog(getLogZ());
            this.getDetectorCanvas().getCanvas("DC hits per track").cd(sector-1);
            this.getDetectorCanvas().getCanvas("DC hits per track").draw(this.getDataGroup().getItem(sector,0,0).getH1F("cd_hits_per_trk_sec" + sector));
            this.getDetectorCanvas().getCanvas("DC momentum").getPad(sector-1).getAxisZ().setLog(getLogZ());
            this.getDetectorCanvas().getCanvas("DC momentum").cd(sector-1);
            this.getDetectorCanvas().getCanvas("DC momentum").draw(this.getDataGroup().getItem(sector,0,0).getH1F("cd_momentum_sec" + sector));
            this.getDetectorCanvas().getCanvas("DC theta angle").getPad(sector-1).getAxisZ().setLog(getLogZ());
            this.getDetectorCanvas().getCanvas("DC theta angle").cd(sector-1);
            this.getDetectorCanvas().getCanvas("DC theta angle").draw(this.getDataGroup().getItem(sector,0,0).getH1F("cd_theta_sec" + sector));
        }
    
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
                this.getDataGroup().getItem(0,0,0).getH1F("trk_mult").fill(row);
                this.getDataGroup().getItem(0,0,0).getH1F("trk_phi").fill(svtCosmicTrack_phi);
                this.getDataGroup().getItem(0,0,0).getH1F("trk_theta").fill(svtCosmicTrack_theta);
                if(svtCosmicTrack_kfNdf > 0)this.getDataGroup().getItem(0,0,0).getH1F("trk_norm_chi2").fill(svtCosmicTrack_kfChi2/svtCosmicTrack_kfNdf);
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
        
        else if (event.hasBank("TimeBasedTrkg::TBTracks")) {
            DataBank bankTBTracks = event.getBank("TimeBasedTrkg::TBTracks");
            int rows = bankTBTracks.rows();
            int sector;
            for(int i = 0; i < rows; i++){
                sector = bankTBTracks.getByte("sector",i);

                Vector3 momentum = new Vector3();
		momentum.setXYZ(bankTBTracks.getFloat("p0_x", i), bankTBTracks.getFloat("p0_y", i),bankTBTracks.getFloat("p0_z", i));
                
                this.getDataGroup().getItem(sector,0,0).getH1F("cd_momentum_sec" + sector).fill(momentum.mag()); 
                this.getDataGroup().getItem(sector,0,0).getH1F("cd_theta_sec" + sector).fill(momentum.theta());   
                this.getDataGroup().getItem(sector,0,0).getH1F("cd_trk_ev_sec" + sector).fill(rows); 

                DataBank bankTBHits = event.getBank("TimeBasedTrkg::TBHits");
                int hits = bankTBHits.rows();
                this.getDataGroup().getItem(sector,0,0).getH1F("cd_hits_per_trk_sec" + sector).fill(hits/rows);
                
            }
                
        } 
                        
    }
                  

    @Override
    public void timerUpdate() {
        
    }

}
