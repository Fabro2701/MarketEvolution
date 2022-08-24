package backtesting.order;

import backtesting.data.CandleData;

public class Order {
	protected int id;
	protected float positionSize,openPrice,closePrice;
	protected boolean open;
	protected OrderType type;
	protected float result;
	
	public Order(int id, float positionSize, float openPrice, OrderType type) {
		this.open = false;
		this.id = id;
		this.positionSize = positionSize;
		this.openPrice = openPrice;
		this.type = type;
	}
	public static enum OrderType{BUY,SELL,NOTHING}
	public void close() {
		if(type == OrderType.BUY) {
			this.result = this.closePrice-this.openPrice;
		}
		else if(type == OrderType.SELL){
			this.result = this.openPrice-this.closePrice;
		}
	}
	public boolean update(CandleData newCandle) {
		return false;
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getPositionSize() {
		return positionSize;
	}
	public void setPositionSize(float positionSize) {
		this.positionSize = positionSize;
	}
	public float getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(float openPrice) {
		this.openPrice = openPrice;
	}
	public float getClosePrice() {
		return closePrice;
	}
	public void setClosePrice(float closePrice) {
		this.closePrice = closePrice;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public OrderType getType() {
		return type;
	}
	public void setType(OrderType type) {
		this.type = type;
	}
	public float getResult() {
		return result;
	}
	public void setResult(float result) {
		this.result = result;
	}

}
