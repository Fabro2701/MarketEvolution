package backtesting.order;

import backtesting.data.CandleData;

public class FixedTimeOrder extends Order{
	int fixedTime;
	public FixedTimeOrder(int id, float positionSize, float openPrice, OrderType type, int fixedTime) {
		super(id, positionSize, openPrice, type);
		this.fixedTime = fixedTime; 
	}
	public boolean update(CandleData newCandle) {
		fixedTime--;
		return fixedTime<1;
	}

}
