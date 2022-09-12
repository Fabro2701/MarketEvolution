package backtesting.strategy.indicator;

import java.util.ArrayList;

import backtesting.data.DataSeries;

public class MAIndicator extends Indicator{

	public MAIndicator(int period) {
		super(period);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<Float> calculateData(DataSeries data) {
		ArrayList<Float> result = new ArrayList<Float>(data.size());
		for(int i=0;i<period;i++) {
			result.add(data.getClose(i));
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
			acc.set(m, data.getClose(i));
			sum+=acc.get(m);
			if(i>=period) {
				result.add(sum/(float)period);
				
			}
		}
		return result;
	}

}
