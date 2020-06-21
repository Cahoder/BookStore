package service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import dao.IOrderDao;
import dao.IOrderItemDao;
import dao.IProductDao;
import dao.IUserDao;
import exception.OrderExistException;
import factory.DaoFactory;
import model.Cart;
import model.Order;
import model.OrderItem;
import model.Product;
import model.User;
import model.UserReceipt;
import service.IOrderService;
import utils.JDBCUtils;

public class OrderServiceImpl implements IOrderService{
	
	@Override
	public Integer getOrdersCount(User operator) throws OrderExistException {
		if(operator == null)
			throw new OrderExistException("查询失败,操作者信息不明!");
		User op = DaoFactory.getUserDao().getUserById(operator.getId());
		if(op==null || !op.getRole().contains("管理员")) 
			throw new OrderExistException("查询失败,您无权力查询用户订单总数!");
		
		Integer count = DaoFactory.getOrderDao().getOrdersCount();
		if(count == null)
			throw new OrderExistException("查询失败！");
		
		return count;
	}

	@Override
	public Integer getUserOrdersCount(User operator) throws OrderExistException {
		if(operator == null)
			throw new OrderExistException("查询失败,操作者信息不明!");
		User op = DaoFactory.getUserDao().getUserById(operator.getId());
		if(op==null) 
			throw new OrderExistException("查询失败,操作者信息不明!");
		
		Integer count = DaoFactory.getOrderDao().getOrdersCountByUserId(op.getId());
		if(count == null)
			throw new OrderExistException("查询失败！");
		
		return count;
	}

	@Override
	public List<Order> getOrders(User operator, Integer pageNo, Integer pageSize) throws OrderExistException {
		if(operator == null)
			throw new OrderExistException("查询失败,操作者信息不明!");
		
		if(pageNo==null||pageSize==null)
			throw new OrderExistException("查询失败,参数有误!");
		
		IUserDao userDao = DaoFactory.getUserDao();
		
		User op = userDao.getUserById(operator.getId());
		if(!op.getRole().contains("管理员")) 
			throw new OrderExistException("查询失败,您无权力查询用户订单列表!");
		
		List<Order> ordersData = DaoFactory.getOrderDao().getOrdersData(pageNo, pageSize);
		if(ordersData == null)
			throw new OrderExistException("查询失败！");
		
		return ordersData;
	}

	@Override
	public List<Order> getUserOrders(User operator, String user_id) throws OrderExistException {
		if("".equals(user_id))
			throw new OrderExistException("查询失败,ID参数不可为空!");
		if(operator == null)
			throw new OrderExistException("查询失败,操作者信息不明!");
		IUserDao userDao = DaoFactory.getUserDao();
		
		Integer id = Integer.parseInt(user_id);
		User user = userDao.getUserById(id);
		if(user == null)
			throw new OrderExistException("查询失败,您查询的用户不存在!");
		
		User op = userDao.getUserById(operator.getId());
		if(!op.getRole().contains("管理员") && id!=op.getId()) 
			throw new OrderExistException("查询失败,您无权力查询用户订单列表!");
		
		IOrderDao orderDao = DaoFactory.getOrderDao();
		List<Order> userOrders = orderDao.getOrdersByUserId(user.getId());
		
		if(userOrders == null) 
			throw new OrderExistException("查询失败!");
		
		IOrderItemDao orderItemDao = DaoFactory.getOrderItemDao();
		for (Order order : userOrders) {
			List<OrderItem> orderItem = orderItemDao.getOrderItemByOrder(order);
			if(orderItem == null)
				throw new OrderExistException("查询失败,该用户订单信息有误!");
			order.setOrderItems(orderItem);
		}
		
		return userOrders;		
	}

	@Override
	public List<Order> getUserOrders(User operator, String user_id, Integer pageNo, Integer pageSize)
			throws OrderExistException {
		if("".equals(user_id))
			throw new OrderExistException("查询失败,ID参数不可为空!");
		if(operator == null)
			throw new OrderExistException("查询失败,操作者信息不明!");
		IUserDao userDao = DaoFactory.getUserDao();
		
		Integer id = Integer.parseInt(user_id);
		User user = userDao.getUserById(id);
		if(user == null)
			throw new OrderExistException("查询失败,您查询的用户不存在!");
		
		User op = userDao.getUserById(operator.getId());
		if(!op.getRole().contains("管理员") && id!=op.getId()) 
			throw new OrderExistException("查询失败,您无权力查询用户订单列表!");
		
		IOrderDao orderDao = DaoFactory.getOrderDao();
		List<Order> userOrders = orderDao.getOrdersByUserId(user.getId(),pageNo,pageSize);
		
		if(userOrders == null) 
			throw new OrderExistException("查询失败!");
		
		IOrderItemDao orderItemDao = DaoFactory.getOrderItemDao();
		for (Order order : userOrders) {
			List<OrderItem> orderItem = orderItemDao.getOrderItemByOrder(order);
			if(orderItem == null)
				throw new OrderExistException("查询失败,该用户订单信息有误!");
			order.setOrderItems(orderItem);
		}
		
		return userOrders;	
	}

	@Override
	public Order detailUserOrder(User operator, String order_id) throws OrderExistException {
		//先查用户信息
		if(operator == null)
			throw new OrderExistException("查询失败,操作者信息不明!");
		//再查订单信息
		if(order_id == null || "".equals(order_id)) 
			throw new OrderExistException("查看详情失败，该订单编号有误！");
		
		Order order = DaoFactory.getOrderDao().getOrderById(order_id);
		if(order == null) 
			throw new OrderExistException("查看详情失败，该订单不存在！");
		
		User op = DaoFactory.getUserDao().getUserById(operator.getId());
		if(!op.getRole().contains("管理员") && order.getUser().getId()!=op.getId()) 
			throw new OrderExistException("查询失败,您无权力查询用户订单列表!");
		
		List<OrderItem> orderItem = DaoFactory.getOrderItemDao().getOrderItemByOrder(order);
		if(orderItem == null || orderItem.size() < 1) 
			throw new OrderExistException("查看详情失败，该订单信息有误！");
		
		order.setOrderItems(orderItem);
		return order;
	}

	@Override
	public Integer deleteUserOrder(User operator, String order_id) throws OrderExistException {
		//先查用户信息
		if(operator == null)
			throw new OrderExistException("删除失败,操作者信息不明!");
		//再查订单信息
		if(order_id == null || "".equals(order_id)) 
			throw new OrderExistException("删除失败，该订单编号有误！");
		
		Order order = DaoFactory.getOrderDao().getOrderById(order_id);
		if(order == null) 
			throw new OrderExistException("删除失败，该订单不存在！");
		
		User op = DaoFactory.getUserDao().getUserById(operator.getId());
		if(!op.getRole().contains("管理员") && order.getUser().getId()!=op.getId()) 
			throw new OrderExistException("删除失败,您无权力删除该订单!");
		
		//已支付订单用户不可删 管理员可删
		if(!op.getRole().contains("管理员") && order.getPayState() == 1) 
			throw new OrderExistException("删除失败,已支付订单无法删除!");
		
		Integer res = DaoFactory.getOrderDao().delOrderByIds(order_id);
		if(res<1)
			throw new OrderExistException("删除失败！");
		return res;
	}

	@Override
	public Integer deleteUserOrders(User operator, String...order_ids) throws OrderExistException {
		//先查用户信息
		if(operator == null)
			throw new OrderExistException("删除失败,操作者信息不明!");
		User op = DaoFactory.getUserDao().getUserById(operator.getId());
		if(!op.getRole().contains("管理员")) 
			throw new OrderExistException("删除失败,您无权力删除这些订单!");
		Integer dels = DaoFactory.getOrderDao().delOrderByIds(order_ids);
		if(dels<1)
			throw new OrderExistException("删除失败!");
		return dels;
	}

	@Override
	public String addNewOrder(User user, List<Cart> cartItems, UserReceipt receipt)
			throws OrderExistException {
		//先检查用户信息
		if(user == null) 
			throw new OrderExistException("订单创建失败，用户信息有误！");
		User u = DaoFactory.getUserDao().getUserById(user.getId());
		if(u == null) 
			throw new OrderExistException("订单创建失败，用户信息有误！");
		
		//然后检查购物清单
		if(cartItems == null || cartItems.size() < 1)
			throw new OrderExistException("订单创建失败，购物清单信息有误！");
		
		//最后检查收货地址 ID不存在则创建新
		Integer res = 0;
		if(receipt == null) 
			throw new OrderExistException("订单收货地址填写有误，请检查一下噢！");
		if(receipt.getId()==null || receipt.getId() == 0)
			res = DaoFactory.getUserReceiptDao().addUserReceipt(receipt);
		else 
			receipt = DaoFactory.getUserReceiptDao().getUserReceiptById(receipt.getId());
		
		if(res == 0 && receipt == null) 
			throw new OrderExistException("订单收货地址填写有误，请检查一下噢！");
		
		//生产一张新的订单
		Double totalMoney = 0.0;
		for (Cart cart : cartItems) 
			totalMoney+=cart.getAddnum()*cart.getProduct().getPrice();
		
		String order_id = UUID.randomUUID().toString();
		Order newOrder = new Order(order_id,totalMoney,receipt.getAddress(),
				receipt.getName(),receipt.getPhone(),0,JDBCUtils.getCurrentTimestamp());
		newOrder.setUser(user);
		
		Integer orderRes = DaoFactory.getOrderDao().addOrder(newOrder);
		if(orderRes != 1) 
			throw new OrderExistException("抱歉，订单生成失败了，请稍后再试一下呗！");
		
		//添加订单购买的产品详情
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		for (Cart cart : cartItems) 
			orderItems.add(new OrderItem(newOrder, cart.getProduct(), cart.getAddnum()));
		
		Integer orderItemRes = DaoFactory.getOrderItemDao().addOrderItemByOrder(newOrder, orderItems);
		if(orderItemRes < 1) {
			DaoFactory.getOrderDao().delOrderByIds(order_id);
			throw new OrderExistException("抱歉，订单生成失败了，请稍后再试一下呗！");
		}
		
		return order_id;
	}

	@Override
	public synchronized void payUserOrder(User user, String order_id) throws OrderExistException {
		//先检查用户信息
		if(user == null) 
			throw new OrderExistException("订单支付失败，用户信息有误！");
		User u = DaoFactory.getUserDao().getUserById(user.getId());
		if(u == null) 
			throw new OrderExistException("订单支付失败，用户信息有误！");
		
		//再检查订单详情
		if(order_id == null || "".equals(order_id)) 
			throw new OrderExistException("订单支付失败，该订单编号有误！");
		
		IOrderDao orderDao = DaoFactory.getOrderDao();
		Order order = orderDao.getOrderById(order_id);
		
		if(order==null) 
			throw new OrderExistException("订单支付失败，该订单不存在！");
		
		if(order.getUser().getId()!=u.getId())
			throw new OrderExistException("订单支付失败，订单信息有误！");
		
		if(order.getPayState() == 1) 
			throw new OrderExistException("订单支付失败，该订单已支付！");
		
		//查订单购买清单详情
		List<OrderItem> orderItem = DaoFactory.getOrderItemDao().getOrderItemByOrder(order);
		if(orderItem == null || orderItem.size() < 1) 
			throw new OrderExistException("订单支付失败，该订单信息有误！");
		
		//查购买的商品详情
		IProductDao productDao = DaoFactory.getProductDao();
		List<String> orderItemProductIds = orderItem.stream()
				.map(OrderItem::getProduct).map(Product::getId).collect(Collectors.toList());
		
		String[] pids = orderItemProductIds.toArray(new String[orderItemProductIds.size()]);
		
		List<Product> products = productDao.getProductByIdsData(pids);
		if(products.size()!=orderItem.size()) 
			throw new OrderExistException("订单支付失败，该订单信息有误！");
		
		//判断库存是否支持订单数
		boolean isEnough = true;
		for (Product product : products) {
			long count = orderItem.stream()
					.filter((p)->{
					return p.getProduct().getId().equals(product.getId())&&product.getPnum() >= p.getBuynum();}
					).count();
			if(count != 1) {
				isEnough = false;
				break;
			}
		}
		if(!isEnough) 
			throw new OrderExistException("订单支付失败，订单的商品库存不足！");
		
		//修改订单支付状态
		Integer updateOrder = orderDao.updateOrder(order_id, 1);
		if(updateOrder!=1) 
			throw new OrderExistException("订单支付失败，请尝试重新支付！");
		
		//修改库存数量
		for (Product p : products) {
			for (OrderItem i : orderItem) {
				if(i.getProduct().getId().equals(p.getId())) {
					p.setPnum(p.getPnum()-i.getBuynum());
					break;
				}
			}
		}
		Integer updateProduct = productDao.updateProduct(products);
		if(updateProduct!=products.size()) {
			orderDao.delOrderByIds(order_id);		//只是删掉订单 TODO recover products pnum
			throw new OrderExistException("订单支付失败，订单的商品库存有误，自动删除此订单！");
		}
		
	}
	
}
