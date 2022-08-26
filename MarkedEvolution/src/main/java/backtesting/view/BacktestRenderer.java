package backtesting.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import backtesting.data.CandleData;
import backtesting.data.DataSeries;

public class BacktestRenderer extends Renderer{
	
	int width, height;
	DataSeries series;
	BufferedImage bufferImage;
	Graphics2D g;
	final int inCandleSpace = 10;
	int ini,end;
	
	public BacktestRenderer(DataSeries series) {
		this.series = series;
		width = 740;
		height = 420;
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
		//g.setColor(new Color(0,0,0,255));
		g.setColor(new Color(255,255,255,255));
		g.fillRect(0, 0, width, height);
		
		float min=series.getLow(ini),max=series.getHigh(ini);
		CandleData cd = null;
		for(int i=ini;i<end;i++) {
			cd = series.get(i);
			min = Math.min(cd.getLow(), min);
			max = Math.max(cd.getHigh(), max);
		}
		
		//render candles
		for(int i=ini;i<end;i++) {
			CandleRenderer.draw(g, series.get(i), (i-ini)*inCandleSpace, min, max, height);
		}
		
		//render indicators
		for(String key:series.getIndicators().keySet()) {
			ArrayList<Float> indicator = series.getIndicators().get(key);
			IndicatorRenderer.draw(g, ini, end, indicator, inCandleSpace,min, max, height);
		}
	}
	public BufferedImage init(int shift) {//0-100
		if(bufferImage == null) {
			bufferImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
			g = bufferImage.createGraphics();
		}
		int l=74;
		shift *= (series.size()-l)/100.f;
		
		System.out.println(shift);
		this.draw(shift+0, shift+l);
		
		return bufferImage;
	}
	public void right(int shift) {
		if(end+shift<=series.size()) {
			this.draw(ini+shift, end+shift);
		}
		
	}
	public void left(int shift) {
		if(ini>=shift) {
			this.draw(ini-shift, end-shift);
		}
	}
}
