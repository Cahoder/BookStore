package dao;

import java.util.List;

import model.Order;
import model.OrderItem;

//通用的订单商品详情dao层接口
public interface IOrderItemDao {
	
	/**
	 * @param order
	 * @return 根据订单获取其订单详情
	 */
	public List<OrderItem> getOrderItemByOrder(Order order);
	
	/**
	 * @param order_id
	 * @return 建立连接数据库删除订单详情的影响函数,小于等于0为插入失败,大于0为删除条数
	 */
	public Integer delOrderItemByOrderId(String order_id);
	
	/**
	 * 	为订单添加购买条目
	 * @param order
	 * @param orderItems
	 * @return
	 */
	public Integer addOrderItemByOrder(Order order, List<OrderItem> orderItems);

}
