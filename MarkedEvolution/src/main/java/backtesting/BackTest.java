package backtesting;

import backtesting.data.CandleData;
import backtesting.data.DataSeries;
import backtesting.order.OrdersManager;
import backtesting.stats.BackTestStats;
import backtesting.strategy.Strategy;

public class BackTest {  
	DataSeries data; 
	public BackTest() {
	}
	public void loadData(String filename) {
		int l=0;
		data = new DataSeries(l);
	} 
	public BackTestStats runTest(Strategy strategy) {
		BackTestStats stats = new BackTestStats();
		OrdersManager ordersManager = new OrdersManager();
		for(CandleData cd:data) {
			ordersManager.update(cd);
			strategy.update(cd,ordersManager);
		}
		return stats; 
	}
	
}
