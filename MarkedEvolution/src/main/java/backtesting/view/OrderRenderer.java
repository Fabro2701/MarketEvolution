package backtesting.view;

import java.awt.Color;
import java.awt.Graphics2D;

import backtesting.data.CandleData;
import backtesting.order.Order;
import backtesting.order.Order.OrderType;

public class OrderRenderer extends Renderer{
	public static void draw(Graphics2D g, Order order, int shift, int candleSpace, float min, float max, int height) {
		
		float open = map(order.getOpenPrice(),max,min)*height;
		
		
		
		if(order.getType()==OrderType.BUY) {
			g.setColor(WEAK_BULL_COLOR);
			g.fillOval(shift*candleSpace, (int) open, 3, 3);
		}
		else if(order.getType()==OrderType.SELL) {
			g.setColor(WEAK_BEAR_COLOR);
			g.fillOval(shift*candleSpace, (int) open, 3, 3);
		}
		
		if(!order.isOpen()) {
			float close = map(order.getClosePrice(),max,min)*height;
			if(order.getType()==OrderType.BUY) {
				g.setColor(WEAK_BULL_COLOR);
				g.drawLine(shift*candleSpace, (int)open, (shift + (order.getFidx()-order.getIdx()))*candleSpace, (int)close);
			}
			else if(order.getType()==OrderType.SELL) {
				g.setColor(WEAK_BEAR_COLOR);
				g.drawLine(shift*candleSpace, (int)open, (shift + (order.getFidx()-order.getIdx()))*candleSpace, (int)close);
			}
		}
		
		
	}
	public static void main2(String args[]) {
		System.out.println(OrderRenderer.map(0f, 0f, 100f));
		System.out.println(OrderRenderer.map(55f, 0f, 100f));
		System.out.println(OrderRenderer.map(100f, 10f, 100f));
		System.out.println(OrderRenderer.map(55f, 10f, 100f));
	}

}
