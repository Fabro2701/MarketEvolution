package backtesting.data;

import java.util.ArrayList;
import java.util.HashMap;

public class DataSeries extends ArrayList<CandleData>{
	HashMap<String,ArrayList<Float>>indicators;
	public DataSeries() {
		this(1);
	}
	public DataSeries(int l) {
		super(l);
		indicators = new HashMap<String,ArrayList<Float>>();
	}
	public HashMap<String,ArrayList<Float>> getIndicators(){
		return this.indicators;
	}
	public void addIndicator(String key, ArrayList<Float>values) {
		this.indicators.put(key, values);
	}
	public ArrayList<Float> getIndicator(String key){
		return this.indicators.get(key);
	}
	public float getClose(int i) {
		return this.get(i).getClose();
	}
	public float getOpen(int i) {
		return this.get(i).getOpen();
	}
	public float getHigh(int i) {
		return this.get(i).getHigh();
	}
	public float getLow(int i) {
		return this.get(i).getLow();
	}
	
}
