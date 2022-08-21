package backtesting.strategy.indicator;

import java.util.ArrayList;

import backtesting.data.DataSeries;

public abstract class Indicator {
	int period;
	public Indicator(int period) {
		this.period = period;
	}
	public int getPeriod() {
		return period;
	}
	public abstract ArrayList<Float> calculateData(DataSeries data);
}
