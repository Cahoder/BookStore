package model;

/**
 *	书城订单详情	JavaBean
 */
public class OrderItem {
	
	private Order order; //所属订单号
	private Product product; //购买商品
	private Integer buynum;	//购买件数
	
	public OrderItem() {}

	public OrderItem(Order order, Product product, Integer buynum) {
		this.order = order;
		this.product = product;
		this.buynum = buynum;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getBuynum() {
		return buynum;
	}

	public void setBuynum(Integer buynum) {
		this.buynum = buynum;
	}
	
}
