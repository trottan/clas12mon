package org.clas.detectors;

import org.clas.viewer.DetectorMonitor;
import org.jlab.detector.base.DetectorType;
import org.jlab.detector.base.GeometryFactory;
import org.jlab.detector.geant4.DCGeant4Factory;
import org.jlab.detector.view.DetectorShape2D;
import org.jlab.geom.base.ConstantProvider;
import org.jlab.groot.base.GStyle;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.group.DataGroup;
import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;

/**
 *
 * @author devita
 */

public class DCmonitor extends DetectorMonitor {
    

    public DCmonitor(String name) {
        super(name);
        this.setDetectorTabNames("Raw Occupancies","Normalized Occupancies log", "Normalized Occupancies lin", "Region Occupancies", "TDC raw spectra", "TDC spectra per Sector/Superlayer", "Hit Multiplicity");
        this.useSectorButtons(true);
        this.init(false);
    }

    
    @Override
    public void createHistos() {
        // create histograms
        this.setNumberOfEvents(0);
        H1F summary = new H1F("summary","summary",6,0.5,6.5);
        summary.setTitleX("sector");
        summary.setTitleY("DC hits");
        summary.setTitle("DC");
        summary.setFillColor(33);
        DataGroup sum = new DataGroup(1,1);
        sum.addDataSet(summary, 0);
        this.setDetectorSummary(sum);
        
        for(int sector=1; sector <= 6; sector++) {
            H2F raw = new H2F("raw_sec" + sector, "Sector " + sector + " Occupancy", 112, 0.5, 112.5, 36, 0.5, 36.5);
            raw.setTitleX("wire");
            raw.setTitleY("layer");
            raw.setTitle("sector "+sector);
            
            H2F occ = new H2F("occ_sec" + sector, "Sector " + sector + " Occupancy", 112, 0.5, 112.5, 36, 0.5, 36.5);
            occ.setTitleX("wire");
            occ.setTitleY("layer");
            occ.setTitle("sector "+sector);
            
            H1F reg_occ = new H1F("reg_occ_sec" + sector, "Sector " + sector + " region Occupancy", 3, 0.5, 3.5);
            reg_occ.setTitleX("region");
            reg_occ.setTitleY("occupancy %");
            reg_occ.setTitle("sector "+sector);
            
            H1F raw_reg_occ = new H1F("raw_reg_occ_sec" + sector, "Sector " + sector + " region Occupancy", 3, 0.5, 3.5);
            raw_reg_occ.setTitleX("region");
            raw_reg_occ.setTitleY("counts");
            raw_reg_occ.setTitle("sector "+sector);
            
            H2F tdc_raw = new H2F("tdc_raw" + sector, "Sector " + sector + " TDC raw distribution", 404, 0, 2020, 36, 0.5, 36.5);
            tdc_raw.setTitleX("tdc raw");
            tdc_raw.setTitleY("layer");
            tdc_raw.setTitle("sector "+sector);
            
            for(int sl=1; sl<=6; sl++) {
                H1F tdc_sl_raw = new H1F("tdc_sl_raw" + sector+ sl, "Sector " + sector + " Superlayer " + sl + " TDC spectrum", 404, 0, 2020);
                DataGroup dg_sl = new DataGroup(1,1);
                dg_sl.addDataSet(tdc_sl_raw, 0);
                this.getDataGroup().add(dg_sl, sector, sl, 0);
            }
            
            H1F mult = new H1F("multiplicity_sec"+ sector, "Multiplicity sector "+ sector, 200, 0., 1000);
            mult.setTitleX("hit multiplicity");
            mult.setTitleY("counts");
            mult.setTitle("multiplicity sector " + sector);
            
            DataGroup dg = new DataGroup(5,1);
            dg.addDataSet(raw, 0);
            dg.addDataSet(occ, 1);
            dg.addDataSet(reg_occ, 2);
            dg.addDataSet(raw_reg_occ, 3);
            dg.addDataSet(tdc_raw, 4);
            dg.addDataSet(mult, 5);
            this.getDataGroup().add(dg, sector,0,0);
        }
        
       
        
    }

    @Override
    public void drawDetector() {
        // Load the Constants
//        Constants.Load(true, true, 0);
//        GeometryLoader.Load(10, "default");
        ConstantProvider  provider = GeometryFactory.getConstants(DetectorType.DC, 10, "default");
	DCGeant4Factory   dcDetector = new DCGeant4Factory(provider);
        
        for(int s =0; s< 6; s++)
            for(int slrnum = 5; slrnum > -1; slrnum--) {
                //DetectorShape2D module = new DetectorShape2D(DetectorType.DC,s,slrnum,0);

//                DetectorShape2D module = new DetectorShape2D();
//                module.getDescriptor().setType(DetectorType.DC);
//                module.getDescriptor().setSectorLayerComponent((s+1), (slrnum+1), 1);
//
//                module.getShapePath().addPoint(GeometryLoader.dcDetector..getSector(0).getSuperlayer(slrnum).getLayer(0).getComponent(0).getLine().origin().x(),  
//                        -GeometryLoader.dcDetector.getSector(0).getSuperlayer(slrnum).getLayer(0).getComponent(0).getLine().origin().y(),  0.0);
//                module.getShapePath().addPoint(GeometryLoader.dcDetector.getSector(0).getSuperlayer(slrnum).getLayer(0).getComponent(0).getLine().end().x(),  
//                        -GeometryLoader.dcDetector.getSector(0).getSuperlayer(slrnum).getLayer(0).getComponent(0).getLine().end().y(),  0.0);
//                module.getShapePath().addPoint(GeometryLoader.dcDetector.getSector(0).getSuperlayer(slrnum).getLayer(0).getComponent(111).getLine().end().x(),  
//                        -GeometryLoader.dcDetector.getSector(0).getSuperlayer(slrnum).getLayer(0).getComponent(111).getLine().end().y(),  0.0);
//                module.getShapePath().addPoint(GeometryLoader.dcDetector.getSector(0).getSuperlayer(slrnum).getLayer(0).getComponent(111).getLine().origin().x(),  
//                        -GeometryLoader.dcDetector.getSector(0).getSuperlayer(slrnum).getLayer(0).getComponent(111).getLine().origin().y(),  0.0);
//
//
//                if(slrnum%2==1)
//                        module.setColor(180-slrnum*15,180,255);
//                if(slrnum%2==0)
//                        module.setColor(255-slrnum*15,182,229, 200);
//
//                module.getShapePath().translateXYZ(110.0+((int)(slrnum/2))*50, 0, 0);
//                module.getShapePath().rotateZ(s*Math.toRadians(60.));
//
//                this.getDetectorView().getView().addShape("DC",module);			

            }
        this.getDetectorView().setName("DC"); 
        //detectorViewDC.updateBox();
    }
        
    @Override
    public void plotHistos() {
        // initialize canvas and plot histograms
    	    
        this.getDetectorCanvas().getCanvas("Normalized Occupancies log").divide(2, 3);
        this.getDetectorCanvas().getCanvas("Normalized Occupancies log").setGridX(false);
        this.getDetectorCanvas().getCanvas("Normalized Occupancies log").setGridY(false);
        this.getDetectorCanvas().getCanvas("Normalized Occupancies lin").divide(2, 3);
        this.getDetectorCanvas().getCanvas("Normalized Occupancies lin").setGridX(false);
        this.getDetectorCanvas().getCanvas("Normalized Occupancies lin").setGridY(false);
        this.getDetectorCanvas().getCanvas("Raw Occupancies").divide(2, 3);
        this.getDetectorCanvas().getCanvas("Raw Occupancies").setGridX(false);
        this.getDetectorCanvas().getCanvas("Raw Occupancies").setGridY(false);
        this.getDetectorCanvas().getCanvas("Region Occupancies").divide(2, 3);
        this.getDetectorCanvas().getCanvas("Region Occupancies").setGridX(false);
        this.getDetectorCanvas().getCanvas("Region Occupancies").setGridY(false);
        this.getDetectorCanvas().getCanvas("TDC raw spectra").divide(2, 3);
        this.getDetectorCanvas().getCanvas("TDC raw spectra").setGridX(false);
        this.getDetectorCanvas().getCanvas("TDC raw spectra").setGridY(false);
        this.getDetectorCanvas().getCanvas("TDC spectra per Sector/Superlayer").divide(2, 3);
        this.getDetectorCanvas().getCanvas("TDC spectra per Sector/Superlayer").setGridX(false);
        this.getDetectorCanvas().getCanvas("TDC spectra per Sector/Superlayer").setGridY(false);
        this.getDetectorCanvas().getCanvas("Hit Multiplicity").divide(2, 3);
        this.getDetectorCanvas().getCanvas("Hit Multiplicity").setGridX(false);
        this.getDetectorCanvas().getCanvas("Hit Multiplicity").setGridY(false);
        
        for(int sector=1; sector <=6; sector++) {
            this.getDetectorCanvas().getCanvas("Normalized Occupancies log").getPad(sector-1).getAxisZ().setRange(0.01, max_occ);
            this.getDetectorCanvas().getCanvas("Normalized Occupancies log").getPad(sector-1).getAxisZ().setLog(getLogZ());
            this.getDetectorCanvas().getCanvas("Normalized Occupancies log").cd(sector-1);
            this.getDetectorCanvas().getCanvas("Normalized Occupancies log").draw(this.getDataGroup().getItem(sector,0,0).getH2F("occ_sec"+sector));
            this.getDetectorCanvas().getCanvas("Normalized Occupancies lin").getPad(sector-1).getAxisZ().setRange(0.01, max_occ);
            this.getDetectorCanvas().getCanvas("Normalized Occupancies lin").getPad(sector-1).getAxisZ().setLog(!getLogZ());
            this.getDetectorCanvas().getCanvas("Normalized Occupancies lin").cd(sector-1);
            this.getDetectorCanvas().getCanvas("Normalized Occupancies lin").draw(this.getDataGroup().getItem(sector,0,0).getH2F("occ_sec"+sector));
            this.getDetectorCanvas().getCanvas("Raw Occupancies").getPad(sector-1).getAxisZ().setLog(getLogZ());
            this.getDetectorCanvas().getCanvas("Raw Occupancies").cd(sector-1);
            this.getDetectorCanvas().getCanvas("Raw Occupancies").draw(this.getDataGroup().getItem(sector,0,0).getH2F("raw_sec"+sector));
            this.getDetectorCanvas().getCanvas("Region Occupancies").cd(sector-1);
            this.getDetectorCanvas().getCanvas("Region Occupancies").draw(this.getDataGroup().getItem(sector,0,0).getH1F("reg_occ_sec"+sector));
            this.getDetectorCanvas().getCanvas("TDC raw spectra").getPad(sector-1).getAxisZ().setLog(getLogZ());
            this.getDetectorCanvas().getCanvas("TDC raw spectra").cd(sector-1);
            this.getDetectorCanvas().getCanvas("TDC raw spectra").draw(this.getDataGroup().getItem(sector,0,0).getH2F("tdc_raw" + sector));
            this.getDetectorCanvas().getCanvas("Hit Multiplicity").cd(sector-1);
            this.getDetectorCanvas().getCanvas("Hit Multiplicity").draw(this.getDataGroup().getItem(sector,0,0).getH1F("multiplicity_sec"+ sector));
            if(getActiveSector()==sector) {
               for(int sl=1; sl <=6; sl++) {
                   this.getDetectorCanvas().getCanvas("TDC spectra per Sector/Superlayer").cd(sl-1);
                   this.getDetectorCanvas().getCanvas("TDC spectra per Sector/Superlayer").draw(this.getDataGroup().getItem(sector,sl,0).getH1F("tdc_sl_raw" + sector+ sl));
               }
            }
        }

        this.getDetectorCanvas().getCanvas("Normalized Occupancies log").update();
        this.getDetectorCanvas().getCanvas("Normalized Occupancies lin").update();
        this.getDetectorCanvas().getCanvas("Raw Occupancies").update();
        this.getDetectorCanvas().getCanvas("Region Occupancies").update();
        this.getDetectorCanvas().getCanvas("TDC raw spectra").update();
        this.getDetectorCanvas().getCanvas("Hit Multiplicity").update();
        
    }

    @Override
    public void processEvent(DataEvent event) {
        
        if (this.getNumberOfEvents() >= super.eventResetTime_current[4] && super.eventResetTime_current[4] > 0){
            resetEventListener();
        }
        
//		if (!testTriggerMask()) return;
                
        // process event info and save into data group
        if(event.hasBank("DC::tdc")==true){
            DataBank  bank = event.getBank("DC::tdc");
            this.getDetectorOccupancy().addTDCBank(bank);
            int rows = bank.rows();
            int[] nEventSector = {0,0,0,0,0,0};
            
            for(int i = 0; i < rows; i++){
                int    sector = bank.getByte("sector",i);
                int     layer = bank.getByte("layer",i);
                int      wire = bank.getShort("component",i);
                int       TDC = bank.getInt("TDC",i);
                int     order = bank.getByte("order",i); 
                int    region = (int) (layer-1)/12+1;
                int        sl = (int) (layer-1)/6+1;
                
                this.getDataGroup().getItem(sector,0,0).getH2F("raw_sec"+sector).fill(wire*1.0,layer*1.0);                
                this.getDataGroup().getItem(sector,0,0).getH1F("raw_reg_occ_sec"+sector).fill(region * 1.0);
                this.getDataGroup().getItem(sector,0,0).getH2F("tdc_raw"+sector).fill(TDC,layer*1.0);
                this.getDataGroup().getItem(sector,sl,0).getH1F("tdc_sl_raw" + sector+ sl).fill(TDC,layer*1.0);
                if(TDC > 0) this.getDetectorSummary().getH1F("summary").fill(sector*1.0);

                nEventSector[sector-1]++;
                
//                if(sector == 1 && sec1_check == 0){
//                    this.getDataGroup().getItem(sector,0,0).getH1F("multiplicity_sec"+ sector).fill(rows);
//                    sec1_check += 1;
//                }
//                if(sector == 2 && sec2_check == 0){
//                    this.getDataGroup().getItem(sector,0,0).getH1F("multiplicity_sec"+ sector).fill(rows);
//                    sec2_check += 1;
//                }
//                if(sector == 3 && sec3_check == 0){
//                    this.getDataGroup().getItem(sector,0,0).getH1F("multiplicity_sec"+ sector).fill(rows);
//                    sec3_check += 1;
//                }
//                if(sector == 4 && sec4_check == 0){
//                    this.getDataGroup().getItem(sector,0,0).getH1F("multiplicity_sec"+ sector).fill(rows);
//                    sec4_check += 1;
//                }
//                if(sector == 5 && sec5_check == 0){
//                    this.getDataGroup().getItem(sector,0,0).getH1F("multiplicity_sec"+ sector).fill(rows);
//                    sec5_check += 1;
//                }
//                if(sector == 6 && sec6_check == 0){
//                    this.getDataGroup().getItem(sector,0,0).getH1F("multiplicity_sec"+ sector).fill(rows);
//                    sec6_check += 1;
//                }              
            }
            for(int sec=1; sec<=6; sec++) this.getDataGroup().getItem(sec,0,0).getH1F("multiplicity_sec"+ sec).fill(nEventSector[sec-1]*1.0);
            
       }   
    }

    @Override
    public void timerUpdate() {
//        System.out.println("Updating DC");
        if(this.getNumberOfEvents()>0) {
            for(int sector=1; sector <=6; sector++) {
                H2F raw = this.getDataGroup().getItem(sector,0,0).getH2F("raw_sec"+sector);
                for(int loop = 0; loop < raw.getDataBufferSize(); loop++){
                    this.getDataGroup().getItem(sector,0,0).getH2F("occ_sec"+sector).setDataBufferBin(loop,100*raw.getDataBufferBin(loop)/this.getNumberOfEvents());
                }
            }
        }
     
        
        if(this.getNumberOfEvents()>0) {
            int entries = 0;
            for(int sector=1; sector <=6; sector++) {
              H1F raw_check = this.getDataGroup().getItem(sector,0,0).getH1F("raw_reg_occ_sec"+sector);
              entries += raw_check.getEntries();
            }
                
            H1F raw_summary = this.getDetectorSummary().getH1F("summary");
                
            for(int sector=1; sector <=6; sector++) {
            	H1F raw = this.getDataGroup().getItem(sector,0,0).getH1F("raw_reg_occ_sec"+sector);
                H1F ave = this.getDataGroup().getItem(sector,0,0).getH1F("reg_occ_sec"+sector);
                if(entries>0) {
                for(int loop = 0; loop < 3; loop++){
                    ave.setBinContent(loop, 100*raw.getBinContent(loop)/this.getNumberOfEvents()/112/12);
                }
                }
                this.getDetectorCanvas().getCanvas("Normalized Occupancies log").getPad(sector-1).getAxisZ().setRange(0.01, max_occ);
                this.getDetectorCanvas().getCanvas("Normalized Occupancies lin").getPad(sector-1).getAxisZ().setRange(0.01, max_occ);
                
                if(raw_summary.getEntries()>0) {
                    this.getDetectorSummary().getH1F("summary").setBinContent(sector, 100*raw_summary.getBinContent(sector)/this.getNumberOfEvents()/112/12/3);
                }
            }
        }   
    }

}
