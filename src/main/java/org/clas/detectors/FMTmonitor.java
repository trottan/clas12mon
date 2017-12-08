package org.clas.detectors;

import org.clas.viewer.DetectorMonitor;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.group.DataGroup;
import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;

public class FMTmonitor extends DetectorMonitor {
	
	int numberOfSamples;
	int timeSampling;

	int maxNumberLayer;
	int maxNumberSector;
	int maxNumberStrips;
	int numberOfStripsPerChip;
	int numberOfChips;
	int binDivision;
	int numberStrips[];

	double hitNumber[][][];
	int dreamHit [][][];
	int timeMax[][][][];
	int layerHit[][];
	boolean mask[][][];
	float tMax[][][];
	
	public FMTmonitor(String name) {
		super(name);
		
		numberOfSamples = 16;
		timeSampling = 40;
		maxNumberLayer = 6;
		maxNumberSector = 1;
		maxNumberStrips = 1024;
		numberOfStripsPerChip = 64 ;
		numberOfChips = 48;
		mask = new boolean[maxNumberSector + 1][maxNumberLayer + 1][maxNumberStrips + 1];
		
		numberStrips = new int[maxNumberLayer + 1];
		dreamHit = new int[maxNumberSector + 1][maxNumberLayer + 1][maxNumberStrips/numberOfStripsPerChip+1];
		tMax = new float [maxNumberSector + 1][maxNumberLayer + 1][maxNumberStrips/numberOfStripsPerChip+1];
		
		numberStrips[1] = 1024;
		numberStrips[2] = 1024;
		numberStrips[3] = 1024;
		numberStrips[4] = 1024;
		numberStrips[5] = 1024;
		numberStrips[6] = 1024;
		
		
		for (int sector = 1; sector <= maxNumberSector; sector++) {
			for (int layer = 1; layer <= maxNumberLayer; layer++) {
				for (int component = 1 ; component <= numberStrips[layer]; component++){
					mask[sector][layer][component]=true;
				}
			}
		}
		
		this.setDetectorTabNames("Occupancies", "TimeOfMax", "Occupancy", "Multiplicity");
		this.init(false);
	}

	@Override
	public void createHistos() {

                H2F summary = new H2F("summary","summary",maxNumberStrips, 0, maxNumberStrips, maxNumberLayer*maxNumberSector,0,maxNumberLayer*maxNumberSector);
		summary.setTitleX("strips");
		summary.setTitleY("detector");
		summary.setTitle("FMT");
		DataGroup sum = new DataGroup(1,1);
		sum.addDataSet(summary, 0);
		this.setDetectorSummary(sum);

		H2F occupancyHisto = new H2F("Occupancies","Occupancies",maxNumberStrips, 0, maxNumberStrips, maxNumberLayer*maxNumberSector,0,maxNumberLayer*maxNumberSector);
		occupancyHisto.setTitleX("strips");
		occupancyHisto.setTitleY("detector");
                H1F histmulti = new H1F("multi", "multi", 100, -0.5, 99.5);
                histmulti.setTitleX("hit multiplicity");
                histmulti.setTitleY("counts");
                histmulti.setTitle("Multiplicity of BMT channels"); 
		DataGroup occupancyGroup = new DataGroup("");
		occupancyGroup.addDataSet(occupancyHisto, 0);
                occupancyGroup.addDataSet(histmulti, 0);
		this.getDataGroup().add(occupancyGroup, 0, 0, 0);
		
		H1F timeOfMaxHisto = new H1F("TimeOfMax","TimeOfMax",numberOfChips,0,numberOfChips);
		timeOfMaxHisto.setTitleX("electronic chip");
		timeOfMaxHisto.setTitleY("time of max adc");
		timeOfMaxHisto.setFillColor(4);
		DataGroup timeOfMaxGroup = new DataGroup("");
		timeOfMaxGroup.addDataSet(timeOfMaxHisto, 0);
		this.getDataGroup().add(timeOfMaxGroup, 0, 0, 1);
		
		for (int sector = 1; sector <= maxNumberSector; sector++) {
			for (int layer = 1; layer <= maxNumberLayer; layer++) {
				H1F hitmapHisto = new H1F("Occupancy Layer " + layer + " Sector " + sector, "Occupancy Layer " + layer + " Sector " + sector,
						(numberStrips[layer])+1, 0., (double) (numberStrips[layer])+1);
				hitmapHisto.setTitleX("strips (Layer " + layer  + " Sector " + sector+")");
				hitmapHisto.setTitleY("Nb of hits");
				hitmapHisto.setFillColor(4);
				DataGroup hitmapGroup = new DataGroup("");
				hitmapGroup.addDataSet(hitmapHisto, 0);
				this.getDataGroup().add(hitmapGroup, sector, layer,2);
			}
		}		
	}

	@Override
	public void plotHistos() {
		
		this.getDetectorCanvas().getCanvas("Occupancies").setGridX(false);
		this.getDetectorCanvas().getCanvas("Occupancies").setGridY(false);
		this.getDetectorCanvas().getCanvas("Occupancies").setAxisTitleSize(12);
		this.getDetectorCanvas().getCanvas("Occupancies").setAxisLabelSize(12);
                this.getDetectorCanvas().getCanvas("Occupancies").getPad(0).getAxisZ().setLog(getLogZ());
		this.getDetectorCanvas().getCanvas("Occupancies").draw(this.getDataGroup().getItem(0, 0, 0).getH2F("Occupancies"));
		this.getDetectorCanvas().getCanvas("Occupancies").update();

		this.getDetectorCanvas().getCanvas("TimeOfMax").setGridX(false);
		this.getDetectorCanvas().getCanvas("TimeOfMax").setGridY(false);
		this.getDetectorCanvas().getCanvas("TimeOfMax").setAxisTitleSize(12);
		this.getDetectorCanvas().getCanvas("TimeOfMax").setAxisLabelSize(12);
		this.getDetectorCanvas().getCanvas("TimeOfMax").draw(this.getDataGroup().getItem(0, 0, 1).getH1F("TimeOfMax"));
		this.getDetectorCanvas().getCanvas("TimeOfMax").update();

		this.getDetectorCanvas().getCanvas("Occupancy").divide(maxNumberSector*2, maxNumberLayer/2);
		this.getDetectorCanvas().getCanvas("Occupancy").setGridX(false);
		this.getDetectorCanvas().getCanvas("Occupancy").setGridY(false);
		this.getDetectorCanvas().getCanvas("Occupancy").setAxisTitleSize(12);
		this.getDetectorCanvas().getCanvas("Occupancy").setAxisLabelSize(12);
		
		for (int sector = 1; sector <= maxNumberSector; sector++) {
			for (int layer = 1; layer <= maxNumberLayer; layer++) {
				int column=maxNumberSector - sector;
				int row;
				int numberOfColumns=maxNumberSector;
				switch (layer) {
				case 1: row=5; break;
				case 2: row=4; break;
				case 3: row=3; break;
				case 4: row=2; break;
				case 5: row=1; break;
				case 6: row=0; break;
				default:row=-1;break;
				}
				this.getDetectorCanvas().getCanvas("Occupancy").cd(column + numberOfColumns * row);
				this.getDetectorCanvas().getCanvas("Occupancy").draw(
						this.getDataGroup().getItem(sector, layer, 2).getH1F("Occupancy Layer " + layer + " Sector " + sector));
			}
		}
                
                this.getDetectorCanvas().getCanvas("Occupancy").update();
                
                this.getDetectorCanvas().getCanvas("Multiplicity").divide(1, 1);
                this.getDetectorCanvas().getCanvas("Multiplicity").setGridX(false);
                this.getDetectorCanvas().getCanvas("Multiplicity").setGridY(false);
                this.getDetectorCanvas().getCanvas("Multiplicity").cd(0);
                this.getDetectorCanvas().getCanvas("Multiplicity").draw(this.getDataGroup().getItem(0,0,0).getH1F("multi"));
                this.getDetectorCanvas().getCanvas("Multiplicity").update();
                
		
	}

	public void processEvent(DataEvent event) {
            
            	if (this.getNumberOfEvents() >= super.eventResetTime_current[6] && super.eventResetTime_current[6] > 0){
		    resetEventListener();
		}
                
                if(    !isGoodTrigger1() && !isGoodTrigger2() && !isGoodTrigger3() && !isGoodTrigger4() 
                    && !isGoodTrigger5() && !isGoodTrigger6() && !isGoodTrigger7() && !isGoodTrigger8()
                    && !isGoodTrigger9() && !isGoodTrigger10() && !isGoodTrigger11() && !isGoodTrigger12()  
                    && !isGoodTrigger13() && !isGoodTrigger14() && !isGoodTrigger15() && !isGoodTrigger16()  
                    && !isGoodTrigger17() && !isGoodTrigger18() && !isGoodTrigger19() && !isGoodTrigger20()
                    && !isGoodTrigger21() && !isGoodTrigger22() && !isGoodTrigger23() && !isGoodTrigger24()
                    && !isGoodTrigger25() && !isGoodTrigger26() && !isGoodTrigger27() && !isGoodTrigger28()  
                    && !isGoodTrigger29() && !isGoodTrigger30() && !isGoodTrigger31() && !isGoodTrigger32()) return;
		
		if (event.hasBank("FMT::adc") == true) {
			DataBank bank = event.getBank("FMT::adc");
			for (int i = 0; i < bank.rows(); i++) {
				
                                this.getDataGroup().getItem(0,0,0).getH1F("multi").fill(bank.rows());
                            
				int sectorNb = bank.getByte("sector", i);
				int layerNb = bank.getByte("layer", i);
				int strip = bank.getShort("component", i);
				float timeNb = bank.getFloat("time", i);
				
				if (strip < 0 || !mask[sectorNb][layerNb][strip]){
					continue;
				}
				
				int dreamNb = (strip - 1) / numberOfStripsPerChip + 1;
				
				dreamHit[sectorNb][layerNb][dreamNb]++;
				tMax[sectorNb][layerNb][dreamNb]=( tMax[sectorNb][layerNb][dreamNb]*(dreamHit[sectorNb][layerNb][dreamNb]-1) + timeNb )/ dreamHit[sectorNb][layerNb][dreamNb];
				this.getDataGroup().getItem(sectorNb, layerNb, 2).getH1F("Occupancy Layer " + layerNb + " Sector " + sectorNb)
				.fill(strip);
				this.getDataGroup().getItem(0, 0, 0).getH2F("Occupancies").fill(strip,maxNumberSector*(layerNb-1)+(sectorNb-1),1);
                                this.getDetectorSummary().getH2F("summary").fill(strip,maxNumberSector*(layerNb-1)+(sectorNb-1),1);
			}
			int compt=0;
			if (getNumberOfEvents() % 1000 == 0) {
				for (int sector = 1; sector <= maxNumberSector; sector++) {
					for (int layer = 1; layer <= maxNumberLayer; layer++) {
						for (int dream = 1; dream <= numberStrips[layer] / numberOfStripsPerChip; dream++) {
							this.getDataGroup().getItem(0, 0, 1).getH1F("TimeOfMax").setBinContent(compt,tMax[sector][layer][dream]);
							compt++;
						}
					}
				}
			}
		}
	}
}