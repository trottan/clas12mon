package org.clas.detectors;

import org.clas.viewer.DetectorMonitor;
import org.jlab.groot.base.GStyle;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.graphics.EmbeddedCanvas;
import org.jlab.groot.group.DataGroup;
import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;

/**
 *
 * @author gotra
 */

public class BSTmonitor extends DetectorMonitor {

    private final int NREGIONS = 3;
    private final int NLAYERS = 6;
    private final int[] sectors = new int[]{10, 10, 14, 14, 18, 18};
    private final double[] occupNorm = new double[]{215.04, 25.6, 25.6, 35.84, 35.84, 46.08, 46.08}; // #strips / 100 (to convert to %)

    public BSTmonitor(String name) {
        super(name);
        this.setDetectorTabNames("Hit Maps", "Layer Hits", "Hit Multiplicity");
        addCanvas("Hit Maps", NREGIONS, 2, false, true);
        addCanvas("Layer Hits", NREGIONS, 2, true, false);
        addCanvas("Hit Multiplicity", NREGIONS + 1, 2, false, false);
        this.init(false);
    }

    @Override
    public void createHistos() {
        // create histograms
        this.setNumberOfEvents(0);
        H2F summary = new H2F("summary", "summary", 256, 0.5, 256.5, 84, 0.5, 84.5);
        summary.setTitleX("Strip");
        summary.setTitleY("Module");
        summary.setTitle("BST");
        DataGroup sum = new DataGroup(1, 1);
        sum.addDataSet(summary, 0);
        this.setDetectorSummary(sum);

        H2F[] hhitmap = new H2F[NLAYERS];
        for (int i = 0; i < NLAYERS; ++i) {
            int nBins = sectors[i];
            hhitmap[i] = new H2F("hitmap_l" + (i + 1), "BST Layer " + (i + 1), 256, 0.5, 256.5, nBins, 0.5,
                    nBins + 0.5);
            hhitmap[i].setTitleX("Strip");
            hhitmap[i].setTitleY("Sector");
        }

        H1F[] hhitl = new H1F[NLAYERS];
        for (int i = 0; i < NLAYERS; ++i) {
            int nBins = sectors[i] * 256;
            hhitl[i] = new H1F("hits_l" + (i + 1), "BST Layer " + (i + 1), nBins, 0.5, nBins + 0.5);
            hhitl[i].setTitleX("Channel");
            hhitl[i].setTitleY("Counts");
        }

        H1F hoccup = new H1F("occup", "", 7, -0.5, 6.5);
        hoccup.setTitleX("BST All Layers");
        hoccup.setTitleY("BST Occupancy (%)");
        hoccup.setLineWidth(2);
        hoccup.setFillColor(33);

        double hitallhigh = 999.5;
        double hithigh = 199.5;
        double hitlow = -0.5;
        H1F[] hmulti = new H1F[7];
        int nbinshit = 100;
        int nbinshitall = 100;

        hmulti[0] = new H1F("bstmulti", "", nbinshitall, hitlow, hitallhigh);
        hmulti[0].setTitleX("BST All Layers Multiplicity");
        hmulti[0].setTitleY("Counts");
        hmulti[0].setLineWidth(2);
        hmulti[0].setFillColor(33);
        hmulti[0].setOptStat("111110");
        for (int i = 1; i < 7; ++i) {
            hmulti[i] = new H1F("bstmulti_l" + i, nbinshit, hitlow, hithigh);
            hmulti[i].setTitleX("BST Layer " + i + " Multiplicity");
            hmulti[i].setTitleY("Counts");
            hmulti[i].setLineWidth(2);
            hmulti[i].setFillColor(34);
            hmulti[i].setOptStat("111110");
        }

        DataGroup dg = new DataGroup("");
        DataGroup hitmapGroup = new DataGroup("");
        for (int i = 0; i < NLAYERS; ++i) {
            hitmapGroup.addDataSet(hhitmap[i], 0);
            this.getDataGroup().add(hitmapGroup, 0, i, 1);
        }
        DataGroup hitlGroup = new DataGroup("");
        for (int i = 0; i < NLAYERS; ++i) {
            hitlGroup.addDataSet(hhitl[i], 0);
            this.getDataGroup().add(hitlGroup, 0, i, 2);
        }
        DataGroup hmultiGroup = new DataGroup("");
        for (int i = 0; i <= NLAYERS; ++i) {
            hmultiGroup.addDataSet(hmulti[i], 0);
            this.getDataGroup().add(hmultiGroup, 0, i, 3);
        }

        dg.addDataSet(hoccup, 0);
        this.getDataGroup().add(dg, 0, 0, 0);
    }

    @Override
    public void plotHistos() {
        EmbeddedCanvas canvas = this.getDetectorCanvas().getCanvas("Hit Maps");
        for (int i = 0; i < canvas.getCanvasPads().size() / 2; ++i) {
            canvas.cd(i);
            int j = 2 * i + 1;
            canvas.draw(this.getDataGroup().getItem(0, j - 1, 1).getH2F("hitmap_l" + (j + 1)));
            canvas.cd(i + NREGIONS);
            canvas.draw(this.getDataGroup().getItem(0, j - 1, 1).getH2F("hitmap_l" + j));
        }
        canvas.update();

        canvas = this.getDetectorCanvas().getCanvas("Layer Hits");
        for (int i = 0; i < canvas.getCanvasPads().size() / 2; ++i) {
            canvas.cd(i);
            int j = 2 * i + 1;
            canvas.draw(this.getDataGroup().getItem(0, j - 1, 2).getH1F("hits_l" + (j + 1)));
            canvas.cd(i + NREGIONS);
            canvas.draw(this.getDataGroup().getItem(0, j - 1, 2).getH1F("hits_l" + j));
        }
        canvas.update();

        canvas = this.getDetectorCanvas().getCanvas("Hit Multiplicity");
        for (int p = 0; p < NREGIONS; ++p) {
            canvas.cd(p);
            int l = 2 * (p + 1);
            canvas.draw(this.getDataGroup().getItem(0, l, 3).getH1F("bstmulti_l" + l));
            canvas.cd(p + NREGIONS + 1);
            l = 2 * p + 1;
            canvas.draw(this.getDataGroup().getItem(0, l, 3).getH1F("bstmulti_l" + l));
        }
        canvas.cd(3);
        canvas.draw(this.getDataGroup().getItem(0, 0, 3).getH1F("bstmulti"));
        canvas.cd(7);
        canvas.draw(this.getDataGroup().getItem(0, 0, 0).getH1F("occup"));
        canvas.update();
    }

    @Override
    public void processEvent(DataEvent event) {

        if (this.getNumberOfEvents() >= super.eventResetTime_current && super.eventResetTime_current > 0) {
            resetEventListener();
        }

        if (!testTriggerMask()) {
            return;
        }

        boolean[] trigger_bits1 = new boolean[32];
        boolean[] trigger_bits2 = new boolean[32];
        if (event.hasBank("RUN::trigger")) {
            DataBank bank = event.getBank("RUN::trigger");
            int TriggerWord1 = bank.getInt("trigger", 0); // first bank
            int TriggerWord2 = bank.getInt("trigger", 1); // second bank
            for (int i = 31; i >= 0; i--) {
                trigger_bits1[i] = (TriggerWord1 & (1 << i)) != 0;
                trigger_bits2[i] = (TriggerWord2 & (1 << i)) != 0;
            }
        }

        if (trigger_bits2[7] || trigger_bits1[31]) {
            return; // random trigger, skip event
        }
        int bsthits = 0;
        int[] hits = {0, 0, 0, 0, 0, 0};

        if (event.hasBank("BST::adc") == true) {
            DataBank bank = event.getBank("BST::adc");
            this.getDetectorOccupancy().addTDCBank(bank);
            int rows = bank.rows();

            for (int i = 0; i < rows; i++) {
                int adc = bank.getInt("ADC", i);
                if (adc < 0) {
                    continue; // TDC hits not counted
                }
                int sector = bank.getByte("sector", i);
                int layer = bank.getByte("layer", i);
                int comp = bank.getShort("component", i);

                bsthits++;
                hits[layer - 1]++;
                this.getDataGroup().getItem(0, layer - 1, 1).getH2F("hitmap_l" + layer).fill(comp, sector);
                this.getDataGroup().getItem(0, layer - 1, 2).getH1F("hits_l" + layer)
                        .fill((sector - 1) * 256 + comp);
                int shift = 0;
                for (int l = 0; l < layer - 1; ++l) {
                    shift += sectors[l];
                }
                this.getDetectorSummary().getH2F("summary").fill(comp, sector + shift);
            } // adc loop
        } // BST::adc
        this.getDataGroup().getItem(0, 0, 3).getH1F("bstmulti").fill(bsthits);
        double occup = this.getDataGroup().getItem(0, 0, 3).getH1F("bstmulti").getMean();
        occup /= occupNorm[0];
        this.getDataGroup().getItem(0, 0, 0).getH1F("occup").setBinContent(0, occup);
        for (int l = 1; l <= 6; ++l) {
            this.getDataGroup().getItem(0, l, 3).getH1F("bstmulti_l" + l).fill(hits[l - 1]);
            occup = this.getDataGroup().getItem(0, l, 3).getH1F("bstmulti_l" + l).getMean();
            occup /= occupNorm[l];
            this.getDataGroup().getItem(0, 0, 0).getH1F("occup").setBinContent(l, occup);
        }
    }

    private void addCanvas(String title, int rows, int columns, boolean yLog, boolean zLog) {
        EmbeddedCanvas canvas = this.getDetectorCanvas().getCanvas(title);
        canvas.divide(rows, columns);
        canvas.setGridX(false);
        canvas.setGridY(false);
        canvas.setTitleSize(18);
        canvas.setAxisTitleSize(24);
        canvas.setAxisLabelSize(18);
        canvas.setStatBoxFontSize(18);
        if (yLog) {
            for (int i = 0; i < canvas.getCanvasPads().size(); ++i) {
                canvas.getPad(i).getAxisY().setLog(true);
            }
        }
        if (zLog) {
            for (int i = 0; i < canvas.getCanvasPads().size(); ++i) {
                canvas.getPad(i).getAxisZ().setLog(true);
            }
        }
    }

    @Override
    public void timerUpdate() {
    }

}
