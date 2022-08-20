package backtesting.order;

import java.util.ArrayList;
import java.util.List;

import backtesting.data.CandleData;

public class OrdersManager {
	List<Order>openOrders;
	List<Order>closedOrders;
	public OrdersManager() {
		openOrders = new ArrayList<Order>();
		closedOrders = new ArrayList<Order>();
	}
	public void update(CandleData newCandle) {
		
	}
}
