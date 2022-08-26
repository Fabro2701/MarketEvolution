package backtesting.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import backtesting.data.CandleData;
import backtesting.data.DataSeries;

public class BacktestRenderer {
	
	int width, height;
	DataSeries series;
	BufferedImage bufferImage;
	Graphics2D g;
	final int inCandleSpace = 10;
	int ini,end;
	
	public BacktestRenderer(DataSeries series) {
		this.series = series;
		width = 740;
		height = 500;
	}
	public void draw(int ini, int end) {
		this.ini = ini;
		this.end = end;
//		width = series.size();
//		height = 0;
//		if(this.bufferImage == null) {
//			bufferImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
//			bufferGraphics = bufferImage.createGraphics();
//		}
		g.setColor(new Color(0,0,0,255));
		//g.setColor(new Color(255,255,255,255));
		g.fillRect(0, 0, width, height);
		
		float min=series.getClose(ini),max=series.getClose(ini);
		for(CandleData cd:series) {
			min = Math.min(cd.getLow(), min);
			max = Math.max(cd.getHigh(), max);
		}
		
		
		for(int i=ini;i<end;i++) {
			CandleRenderer.draw(g, series.get(i), (i-ini)*inCandleSpace, min, max, height-5);
		}
	}
	public BufferedImage init() {
		bufferImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		g = bufferImage.createGraphics();
		
		this.draw(0, 74);
		
		return bufferImage;
	}
	public void right(int shift) {
		this.draw(ini+shift, end+shift);
		
	}
	public void left(int shift) {
		if(ini>=shift) {
			this.draw(ini-shift, end-shift);
		}
	}
}