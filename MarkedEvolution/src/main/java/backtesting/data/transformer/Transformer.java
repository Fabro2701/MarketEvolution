package backtesting.data.transformer;

import backtesting.data.DataSeries;

public abstract class Transformer {
	
	public abstract void apply(DataSeries data);
}
