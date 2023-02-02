package backtesting.stats;

import backtesting.order.Order;
import backtesting.order.OrdersManager;

public class BackTestStats {
	float profit;
	int orders;
	
	public BackTestStats() {
		profit = 0.f;
	}

	public void onClose(OrdersManager ordersManager) {
		profit = 0.f;
		for(Order o:ordersManager.getClosedOrders()) {
			profit += o.getResult();
		}
		orders = ordersManager.getClosedOrders().size();
	}
	public float getProfit() {
		return profit;
	}

	public int getOrders() {
		return orders;
	}

	
}
