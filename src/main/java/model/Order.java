package model;

import java.sql.Timestamp;
import java.util.List;

/**
 *	书城订单	JavaBean
 */
public class Order {
	
	private String id; //订单号
	private Double money; //订单总价
	private String receiverAddress;	//送货地址
	private String receiverName; //收货人姓名
	private	String receiverPhone; //收货人电话
	private Integer payState; //订单状态
	private Timestamp orderTime; //订单创建时间
	private User user;	//订单所属用户
	private List<OrderItem> orderItems; //订单详情
	
	public Order() {}
	
	public Order(String id, Double money, String receiverAddress, String receiverName, String receiverPhone,
			Integer payState, Timestamp orderTime, User user) {
		this.id = id;
		this.money = money;
		this.receiverAddress = receiverAddress;
		this.receiverName = receiverName;
		this.receiverPhone = receiverPhone;
		this.payState = payState;
		this.orderTime = orderTime;
		this.user = user;
	}
	
	public Order(String id, Double money, String receiverAddress, String receiverName, String receiverPhone,
			Integer payState, Timestamp orderTime) {
		this.id = id;
		this.money = money;
		this.receiverAddress = receiverAddress;
		this.receiverName = receiverName;
		this.receiverPhone = receiverPhone;
		this.payState = payState;
		this.orderTime = orderTime;
	}
	
	public Order(String id, Double money, String receiverAddress, String receiverName, String receiverPhone,
			Integer payState, Timestamp orderTime, User user, List<OrderItem> orderItems) {
		this.id = id;
		this.money = money;
		this.receiverAddress = receiverAddress;
		this.receiverName = receiverName;
		this.receiverPhone = receiverPhone;
		this.payState = payState;
		this.orderTime = orderTime;
		this.user = user;
		this.orderItems = orderItems;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public Integer getPayState() {
		return payState;
	}

	public void setPayState(Integer payState) {
		this.payState = payState;
	}

	public Timestamp getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
}
