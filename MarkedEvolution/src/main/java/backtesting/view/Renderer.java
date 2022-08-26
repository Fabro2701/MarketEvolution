package backtesting.view;

import java.awt.Color;

public class Renderer {

	final static int candleWidth=5;
	final static Color bullColor=new Color(0,255,0,255);
	final static Color bearColor=new Color(255,0,0,255);
	protected static float map(float query, float min, float max) {
		return (query-min)/(max-min);
	}
}
