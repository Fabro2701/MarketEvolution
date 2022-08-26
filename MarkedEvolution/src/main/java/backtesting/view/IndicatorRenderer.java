package backtesting.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class IndicatorRenderer extends Renderer{
	private final static Color linecolor = new Color(255,0,0,255);
	public static void draw(Graphics2D g, int ini, int end, ArrayList<Float> indicator, int shift, float min, float max, int height) {
		g.setColor(linecolor);
		int last = (int)(map(indicator.get(ini),max,min)*height);
		int aux;
		for(int i = ini+1; i<end; i++) {
			aux = (int)(map(indicator.get(i),max,min)*height);
			g.drawLine((i-ini)*shift + candleWidth/2, last, (i-ini+1)*shift + candleWidth/2, aux);
			last = aux;
		}
		
	}

}
