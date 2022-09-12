package backtesting.data.transformer;

import java.util.ArrayList;

import backtesting.data.DataSeries;
import backtesting.strategy.indicator.Indicator;

public class AdditionTransformer extends Transformer{
	int period;
	Indicator indicator;
	String identifier;
	public AdditionTransformer(String id, int period, Indicator indicator) {
		this.identifier = id+period;
		this.period = period;
		this.indicator = indicator;
	}
	@Override
	public void apply(DataSeries data) {
		data.addIndicator(identifier, this.calculateNewData(data));
	}

	protected ArrayList<Float> calculateNewData(DataSeries data){
		return indicator.calculateData(data);
	}

}
