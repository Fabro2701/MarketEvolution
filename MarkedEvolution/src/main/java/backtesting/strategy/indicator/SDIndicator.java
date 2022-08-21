package backtesting.strategy.indicator;

import java.util.ArrayList;

import backtesting.data.DataSeries;

public class SDIndicator extends Indicator{

	public SDIndicator(int period) {
		super(period);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<Float> calculateData(DataSeries data) {
		ArrayList<Float> means = data.getIndicator("ma"+period);
		ArrayList<Float> result = new ArrayList<Float>(data.size());
		for(int i=0;i<period;i++) {
			result.add(0.f);
		}
		
		ArrayList<Float>acc = new ArrayList<Float>(period);
		for(int i=0;i<period;i++) {
			acc.add(0.f);
		}
		float sum = 0.f;
		int m;
		for(int i=0;i<data.size();i++) {
			m= i%period;
			sum-=acc.get(m);
			if(i<period)acc.set(m, (float)Math.pow(data.getClose(i)-means.get(period), 2));
			else acc.set(m, (float)Math.pow(data.getClose(i)-means.get(i), 2));
			sum+=acc.get(m);
			if(i>=period) {
				result.add((float)Math.sqrt(sum/((float)period)));
			}
		}
		
		for(int i=0;i<period;i++) {
			result.set(i,result.get(i+period));
		}
		return result;
	}

}
