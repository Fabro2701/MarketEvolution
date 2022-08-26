package backtesting.view;

import java.awt.Color;
import java.awt.Graphics2D;

import backtesting.data.CandleData;

public class CandleRenderer {
	final static int candleWidth=5;
	final static Color bullColor=new Color(0,255,0,255);
	final static Color bearColor=new Color(255,0,0,255);
	public static void draw(Graphics2D g, CandleData cd, int f, float min, float max, int height) {
		//System.out.println("mm: "+min+" "+max);
		//System.out.println("candle1: "+cd.getOpen()+" "+cd.getClose());
		float open = map(cd.getOpen(),max,min)*height;
		float close = map(cd.getClose(),max,min)*height;
		float high = map(cd.getHigh(),max,min)*height;
		float low = map(cd.getLow(),max,min)*height;
		float candleBody = Math.abs(open-close);
		//System.out.println("candle2: "+open+" "+close);
		
		g.setColor(Color.black);
		g.drawLine(f+candleWidth/2, (int)low, f+candleWidth/2, (int)high);
		if(cd.getClose() >= cd.getOpen()){//bull
			g.setColor(bullColor);
			g.fillRect(f, (int)(close), candleWidth, (int)candleBody);
			
		}
		else {//bear
			g.setColor(bearColor);
			g.fillRect(f, (int)(open), candleWidth, (int)candleBody);

		}
	}
	private static float map(float query, float min, float max) {
		return (query-min)/(max-min);
	}
	public static void main2(String args[]) {
		System.out.println(CandleRenderer.map(0f, 0f, 100f));
		System.out.println(CandleRenderer.map(55f, 0f, 100f));
		System.out.println(CandleRenderer.map(100f, 10f, 100f));
		System.out.println(CandleRenderer.map(55f, 10f, 100f));
	}

}
