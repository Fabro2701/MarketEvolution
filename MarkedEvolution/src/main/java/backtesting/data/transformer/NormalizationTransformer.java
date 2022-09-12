package backtesting.data.transformer;

import backtesting.data.CandleData;
import backtesting.data.DataSeries;

public class NormalizationTransformer extends Transformer{
	int referencePeriod ;
	public NormalizationTransformer(int period) {
		this.referencePeriod = period;
	}
	@Override
	public void apply(DataSeries data) {
		
		for(int i=0;i<data.size();i++) {
			data.get(i).setClose((data.getClose(i)-data.getIndicator("ma"+referencePeriod).get(i))/data.getIndicator("sd"+referencePeriod).get(i));
			data.get(i).setOpen((data.getOpen(i)-data.getIndicator("ma"+referencePeriod).get(i))/data.getIndicator("sd"+referencePeriod).get(i));
			data.get(i).setHigh((data.getHigh(i)-data.getIndicator("ma"+referencePeriod).get(i))/data.getIndicator("sd"+referencePeriod).get(i));
			data.get(i).setLow((data.getLow(i)-data.getIndicator("ma"+referencePeriod).get(i))/data.getIndicator("sd"+referencePeriod).get(i));
		}
	}

}
