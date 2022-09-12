package backtesting.stats;

import backtesting.order.Order;
import backtesting.order.OrdersManager;

public class BackTestStats {
	float profit;
	
	public BackTestStats() {
		profit = 0.f;
	}

	public void onClose(OrdersManager ordersManager) {
		profit = 0.f;
		for(Order o:ordersManager.getClosedOrders()) {
			profit += o.getResult();
		}
	}
	public float getProfit() {
		return profit;
	}
}
