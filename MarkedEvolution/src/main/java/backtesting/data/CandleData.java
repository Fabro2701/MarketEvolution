package backtesting.data;

public class CandleData {
	protected float low,high,close,open;
	
	public CandleData(float open, float high, float low, float close) {
		this.low = low;
		this.high = high;
		this.close = close;
		this.open = open;
	}
	
	public String toString() {
		return open+" "+high+" "+low+" "+close;
	} 
	public float getLow() {
		return low;
	}
	public void setLow(float low) {
		this.low = low;
	}
	public float getHigh() {
		return high;
	}
	public void setHigh(float high) {
		this.high = high;
	}
	public float getClose() {
		return close;
	}
	public void setClose(float close) {
		this.close = close;
	}
	public float getOpen() {
		return open;
	}
	public void setOpen(float open) {
		this.open = open;
	}
	
}
