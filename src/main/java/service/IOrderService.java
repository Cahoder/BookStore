package service;

import java.util.List;

import exception.OrderExistException;
import model.Cart;
import model.Order;
import model.User;
import model.UserReceipt;

/**
 * 	订单业务逻辑层接口
 * @author CAIHONGDE
 */
public interface IOrderService {
	
	/**
	 * 	获取所有订单总数
	 * @param operator 只有管理员有权利查看
	 * @return
	 * @throws OrderExistException
	 */
	public Integer getOrdersCount(User operator) throws OrderExistException;
	
	/**
	 * 	获取用户所有订单总数
	 * @param user 只有用户本身和管理员有权利查看
	 * @return
	 */
	public Integer getUserOrdersCount(User user) throws OrderExistException;

	/**
	 * 	分页获取所有订单列表
	 * @param operator 只有管理员有权利查看
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws OrderExistException
	 */
	public List<Order> getOrders(User operator,Integer pageNo,Integer pageSize) throws OrderExistException;
	
	/**
	 * 	根据用户ID获取用户的所有订单详细信息
	 * @param operator 只有管理员或用户本身可以查看
	 * @param user_id 想查看的用户
	 * @return
	 * @throws OrderExistException
	 */
	public List<Order> getUserOrders(User operator, String user_id) throws OrderExistException;
	
	/**
	 * 	根据用户ID分页获取用户的所有订单详细信息
	 * @param operator
	 * @param user_id
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws OrderExistException
	 */
	public List<Order> getUserOrders(User operator, String user_id, Integer pageNo, Integer pageSize)
			throws OrderExistException;
	
	/**
	 * 	查看订单详情
	 * @param operator 只有管理员或用户本身可以查看
	 * @param order_id 订单号
	 * @throws OrderExistException
	 */
	public Order detailUserOrder(User operator, String order_id) throws OrderExistException;
	
	/**
	 * 	删除订单
	 * @param operator 只有管理员或用户本身可以删除
	 * @param order_id 订单号
	 * @return 小于1 删除失败
	 * @throws OrderExistException
	 */
	public Integer deleteUserOrder(User operator, String order_id) throws OrderExistException;
	
	/**
	 * 	批量删除订单
	 * @param operator 只有管理员可以批量删除
	 * @param order_ids 订单号
	 * @return 小于1 删除失败
	 * @throws OrderExistException
	 */
	public Integer deleteUserOrders(User operator, String...order_ids) throws OrderExistException;

	/**
	 * 	为用户新增一条订单
	 * @param user
	 * @param cartItems
	 * @param receipt
	 * @return 新增订单号
	 * @throws OrderExistException 订单新增出错
	 */
	public String addNewOrder(User user, List<Cart> cartItems, UserReceipt receipt) throws OrderExistException;
	
	/**
	 * 	用户订单支付
	 * @param user
	 * @param order_id
	 * @throws OrderExistException
	 */
	public void payUserOrder(User user, String order_id) throws OrderExistException;
	
}
