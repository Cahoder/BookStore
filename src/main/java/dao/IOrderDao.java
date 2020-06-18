package dao;

import java.util.List;

import model.Order;

//通用的订单dao层接口
public interface IOrderDao {
	
	/**
	 * 	建立连接数据库创建新的订单数据
	 * @param order
	 * @return
	 */
	public Integer addOrder(Order order);
	
	/**
	 * @return 建立连接数据库获取订单列表的数据
	 */
	public List<Order> getOrdersData();
	
	/**
	 * @param pageNo
	 * @param pageSize
	 * @return 建立连接数据库分页获取订单列表的数据
	 */
	public List<Order> getOrdersData(Integer pageNo, Integer pageSize);
	
	/**
	 * @param order_ids 需要删除的订单ids
	 * @return 建立连接数据库删除订单的影响函数,小于等于0为插入失败,大于0为删除条数
	 */
	public Integer delOrderByIds(String... order_ids);
	
	/**
	 * @param user_id 需要查找的用户ID
	 * @return 建立连接数据库获取指定用户ID的所有订单列表的数据
	 */
	public List<Order> getOrdersByUserId(Integer user_id);
	
	/**
	 * @param order_id
	 * @return 建立连接数据库根据订单id号获取订单,不存在则返回null
	 */
	public Order getOrderById(String order_id);
	
	/**
	 * @param receiverName
	 * @return 建立连接数据库根据订单收货人获取订单
	 */
	public List<Order> getOrderByReceiverName(String receiverName);

	/**
	 * 	建立连接数据库获取订单总数量
	 * @return
	 */
	public Integer getOrdersCount();
	
	/**
	 * 	建立连接数据库更新订单支付状态
	 * @param payState
	 * @param order_id
	 * @return
	 */
	public Integer updateOrder(String order_id, Integer payState);
	
}
