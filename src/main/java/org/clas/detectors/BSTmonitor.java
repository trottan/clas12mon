package org.clas.detectors;

import org.clas.viewer.DetectorMonitor;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.group.DataGroup;
import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;


public class BSTmonitor extends DetectorMonitor {
    

    public BSTmonitor(String name) {
        super(name);
        this.setDetectorTabNames("Occupancies");
        this.init(false);
    }
    
    @Override
    public void createHistos() {
        // create histograms
        this.setNumberOfEvents(0);
        H2F summary = new H2F("summary","summary",256,0.5,256.5, 84,0.5,84.5);
        summary.setTitleX("strip");
        summary.setTitleY("module");
        summary.setTitle("SVT");
        DataGroup sum = new DataGroup(1,1);
        sum.addDataSet(summary, 0);
        this.setDetectorSummary(sum);

        H2F occ_reg1l1 = new H2F("occ_reg1_l1", "occ_reg1_l1", 256, 0.5, 256.5, 10, 0.5, 10.5);
        occ_reg1l1.setTitleX("strip");
        occ_reg1l1.setTitleY("sector");
        occ_reg1l1.setTitle("layer 1 - region 1");

        H2F occ_reg2l1 = new H2F("occ_reg2_l1", "occ_reg2_l1", 256, 0.5, 256.5, 14, 0.5, 14.5);
        occ_reg2l1.setTitleX("strip");
        occ_reg2l1.setTitleY("sector");
        occ_reg2l1.setTitle("layer 1 - region 2");
        
        H2F occ_reg3l1 = new H2F("occ_reg3_l1", "occ_reg3_l1", 256, 0.5, 256.5, 18, 0.5, 18.5);
        occ_reg3l1.setTitleX("strip");
        occ_reg3l1.setTitleY("sector");
        occ_reg3l1.setTitle("layer 1 - region 3");

        H2F occ_reg1l2 = new H2F("occ_reg1_l2", "occ_reg1_l2", 256, 0.5, 256.5, 10, 0.5, 10.5);
        occ_reg1l2.setTitleX("strip");
        occ_reg1l2.setTitleY("sector");
        occ_reg1l2.setTitle("layer 2 - region 1");

        H2F occ_reg2l2 = new H2F("occ_reg2_l2", "occ_reg2_l2", 256, 0.5, 256.5, 14, 0.5, 14.5);
        occ_reg2l2.setTitleX("strip");
        occ_reg2l2.setTitleY("sector");
        occ_reg2l2.setTitle("layer 2 - region 2");
        
        H2F occ_reg3l2 = new H2F("occ_reg3_l2", "occ_reg3_l2", 256, 0.5, 256.5, 18, 0.5, 18.5);
        occ_reg3l2.setTitleX("strip");
        occ_reg3l2.setTitleY("sector");
        occ_reg3l2.setTitle("layer 2 - region 3");
        
        
        DataGroup dg = new DataGroup(3,2);
        dg.addDataSet(occ_reg1l1, 0);
        dg.addDataSet(occ_reg2l1, 1);
        dg.addDataSet(occ_reg3l1, 2);
        dg.addDataSet(occ_reg1l2, 3);
        dg.addDataSet(occ_reg2l2, 4);
        dg.addDataSet(occ_reg3l2, 5);
        this.getDataGroup().add(dg,0,0,0);
        
    }
        
    @Override
    public void plotHistos() {
        // initialize canvas and plot histograms
        this.getDetectorCanvas().getCanvas("Occupancies").divide(3, 2);
        this.getDetectorCanvas().getCanvas("Occupancies").setGridX(false);
        this.getDetectorCanvas().getCanvas("Occupancies").setGridY(false);
        this.getDetectorCanvas().getCanvas("Occupancies").cd(0);
        this.getDetectorCanvas().getCanvas("Occupancies").draw(this.getDataGroup().getItem(0,0,0).getH2F("occ_reg1_l1"));
        this.getDetectorCanvas().getCanvas("Occupancies").cd(1);
        this.getDetectorCanvas().getCanvas("Occupancies").draw(this.getDataGroup().getItem(0,0,0).getH2F("occ_reg2_l1"));
        this.getDetectorCanvas().getCanvas("Occupancies").cd(2);
        this.getDetectorCanvas().getCanvas("Occupancies").draw(this.getDataGroup().getItem(0,0,0).getH2F("occ_reg3_l1"));
        this.getDetectorCanvas().getCanvas("Occupancies").cd(3);
        this.getDetectorCanvas().getCanvas("Occupancies").draw(this.getDataGroup().getItem(0,0,0).getH2F("occ_reg1_l2"));
        this.getDetectorCanvas().getCanvas("Occupancies").cd(4);
        this.getDetectorCanvas().getCanvas("Occupancies").draw(this.getDataGroup().getItem(0,0,0).getH2F("occ_reg2_l2"));
        this.getDetectorCanvas().getCanvas("Occupancies").cd(5);
        this.getDetectorCanvas().getCanvas("Occupancies").draw(this.getDataGroup().getItem(0,0,0).getH2F("occ_reg3_l2"));
        this.getDetectorCanvas().getCanvas("Occupancies").update();

    }

    @Override
    public void processEvent(DataEvent event) {
        
        // process event info and save into data group
        if(event.hasBank("BST::adc")==true){
            DataBank  bank = event.getBank("BST::adc");
            this.getDetectorOccupancy().addTDCBank(bank);
            int rows = bank.rows();
            for(int i = 0; i < rows; i++){
                int    sector = bank.getByte("sector",i);
                int     layer = bank.getByte("layer",i);
                int      comp = bank.getShort("component",i);
                int       ADC = bank.getInt("ADC",i);
                float    time = bank.getFloat("time",i);
                int     order = bank.getByte("order",i);
             
                if(ADC > 0){
                    if(order == 0) this.getDataGroup().getItem(0,0,0).getH2F("occ_reg1_l1").fill(comp, sector);
                    if(order == 0) this.getDataGroup().getItem(0,0,0).getH2F("occ_reg2_l1").fill(comp, sector);
                    if(order == 0) this.getDataGroup().getItem(0,0,0).getH2F("occ_reg3_l1").fill(comp, sector);
                    if(order == 1) this.getDataGroup().getItem(0,0,0).getH2F("occ_reg1_l2").fill(comp, sector);
                    if(order == 1) this.getDataGroup().getItem(0,0,0).getH2F("occ_reg2_l2").fill(comp, sector);
                    if(order == 1) this.getDataGroup().getItem(0,0,0).getH2F("occ_reg3_l2").fill(comp, sector);
                    this.getDetectorSummary().getH2F("summary").fill(comp, sector*1.0);
                }
            }
       }
                
    }
    

    @Override
    public void timerUpdate() {
        
    }

}
