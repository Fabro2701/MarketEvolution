package backtesting.order;

import java.util.ArrayList;
import java.util.List;

import backtesting.data.CandleData;
import backtesting.order.Order.OrderType;

public class OrdersManager {
	List<Order>openOrders;
	List<Order>closedOrders;
	public OrdersManager() {
		openOrders = new ArrayList<Order>();
		closedOrders = new ArrayList<Order>();
	}
	public void update(CandleData newCandle) {
		boolean delete = false;
		for(int i=0;i<openOrders.size();i++) {
			delete = openOrders.get(i).update(newCandle);
			if(delete) {
				openOrders.remove(i);
				i--;
			}
		}
	}
	public void close(CandleData candle) {
		float lastPrice = candle.getClose();
		for(Order order:openOrders) {
			order.setClosePrice(lastPrice);
			closedOrders.add(order);
		}
		closedOrders.stream().forEach(o->o.close());
		openOrders.clear();
	}
	public void addOrder(Order o) {
		for(Order order:openOrders) {
			if((order.getType()==OrderType.BUY && o.getType()==OrderType.SELL) || (o.getType()==OrderType.BUY && order.getType()==OrderType.SELL)) {
				closedOrders.add(order);
				openOrders.remove(order);
				order.setClosePrice(o.getOpenPrice());
				return;
			}
		}
		openOrders.add(o);
	}
	public List<Order> getOpenOrders() {
		return openOrders;
	}
	public List<Order> getClosedOrders() {
		return closedOrders;
	}
}
