package backtesting.order;

public abstract class Order {
	protected int id;
	protected float positionSize,openPrice,closePrice;
	protected boolean open;
	protected OrderType type;
	
	public Order(int id, float positionSize, float openPrice, OrderType type) {
		this.open = false;
		this.id = id;
		this.positionSize = positionSize;
		this.openPrice = openPrice;
		this.type = type;
	}
	public static enum OrderType{BUY,SELL}

}
