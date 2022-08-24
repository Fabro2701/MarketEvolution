package backtesting.strategy;

import java.util.ArrayList;
import java.util.HashMap;

import backtesting.data.DataSeries;
import backtesting.order.FixedTimeOrder;
import backtesting.order.Order;
import backtesting.order.Order.OrderType;
import backtesting.order.OrdersManager;
import model.grammar.Evaluator;

public class Strategy {
	Evaluator evaluator;
	int cont;
	public Strategy() {
		cont=0;
	}
	public Strategy(Strategy copy) {
		evaluator = copy.evaluator;
		cont = copy.cont;
	}
	public void update(DataSeries data, int i, OrdersManager ordersManager, HashMap<String,String> obs) {
		
		this.evaluator.addObservations(obs);
		
		OrderType next = this.evaluator.getNext();
		if(next == OrderType.BUY || next == OrderType.SELL) {
			ordersManager.addOrder(new FixedTimeOrder(cont,1.f,data.getOpen(i),next, 10));
			cont++;
		}
		else {
			
		}
	}
	
	public void setEvaluator(Evaluator evaluator) {
		this.evaluator = evaluator;
	}

}
