package backtesting.view;

import java.awt.Color;

public class Renderer {

	final static int candleWidth=5;
	final static Color BULL_COLOR=new Color(0,255,0,255);
	final static Color BEAR_COLOR=new Color(255,0,0,255);
	final static Color WEAK_BULL_COLOR=new Color(0,255,0,100);
	final static Color WEAK_BEAR_COLOR=new Color(255,0,0,100);
	protected static float map(float query, float min, float max) {
		return (query-min)/(max-min);
	}
}
