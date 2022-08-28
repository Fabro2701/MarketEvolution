package backtesting.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import backtesting.data.CandleData;
import backtesting.data.DataSeries;
import backtesting.order.Order;
import backtesting.order.OrdersManager;

public class BacktestRenderer extends Renderer{
	
	int width, height;
	DataSeries series;
	BufferedImage bufferImage;
	Graphics2D g;
	final int inCandleSpace = 10;
	int ini,end;
	Integer cursor;
	OrdersManager ordersManager;
	
	
	public BacktestRenderer(DataSeries series) {
		this.series = series;
		width = 740;
		height = 420;
	}
	public void update(int cursor) {
		this.cursor = cursor;
		draw(ini,end);
	}
	public void draw(int ini, int end) {
		this.ini = ini;
		this.end = end;
		g.setColor(new Color(0,0,0,255));
		g.fillRect(0, 0, width, height);
		//g.setColor(new Color(0,0,0,100));
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
		
		if(this.ordersManager != null) {
			for(Order order:this.ordersManager.getOpenOrders()) {
				if(ini <= order.getIdx() && order.getIdx() < end) {
					OrderRenderer.draw(g, order, (order.getIdx()-ini), inCandleSpace, min, max, height);
				}
			}
			for(Order order:this.ordersManager.getClosedOrders()) {
				if(ini <= order.getIdx() && order.getIdx() < end) {
					OrderRenderer.draw(g, order, (order.getIdx()-ini), inCandleSpace, min, max, height);
				}
			}
		}
		
		if(this.cursor!=null) {
			if(ini<=this.cursor && this.cursor<end) {
				g.setColor(Color.red);
				g.drawLine((cursor-ini)*inCandleSpace+1, 0, (cursor-ini)*inCandleSpace+1, height-1);
			}
		}
	}
	/**
	 * Initialize the backtesting viewer based on the shift parameter
	 * @param shift [0-100]
	 * @return
	 */
	public BufferedImage init(int shift) {//0-100
		if(shift<0||shift>100)System.err.println("shift should be between [0 and 100] "+shift);
		if(bufferImage == null) {
			bufferImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
			g = bufferImage.createGraphics();
		}
		int l=74;
		shift *= (series.size()-l)/100.f;
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
	public void setCursor(Integer cursor) {
		this.cursor = cursor;
	}
	public void setOrdersManager(OrdersManager ordersManager) {
		this.ordersManager = ordersManager;
	}
}
