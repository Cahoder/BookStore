package service.impl;

import java.util.List;
import java.util.stream.Collectors;

import dao.ICartDao;
import dao.IUserDao;
import exception.CartExistException;
import factory.DaoFactory;
import model.Cart;
import model.User;
import service.ICartService;

public class CartServiceImpl implements ICartService{

	@Override
	public void updateUserCarts(User user,List<Cart> carts) throws CartExistException {
		ICartDao cartDao = DaoFactory.getCartDao();
		
		//先清空用户购物车
		clearUserCarts(user);
		
		//用户往缓存新添加的
		List<Cart> newProducts = carts.stream().filter((c)->{return c.getId()==null;}).collect(Collectors.toList());
		if(newProducts.size()>0)
			cartDao.addCarts(newProducts);
		
		//更新用户缓存里本来就有的
		List<Cart> oldProducts = carts.stream().filter((c)->{return c.getId()!=null;}).collect(Collectors.toList());
		if(oldProducts.size()>0)
			cartDao.updateCarts(oldProducts);
		
	}

	@Override
	public Integer clearUserCarts(User user) throws CartExistException {
		ICartDao cartDao = DaoFactory.getCartDao();
		List<Cart> cartsDB = cartDao.getCartsByUserId(user.getId());
		
		//先清空用户购物车
		List<Integer> ids = cartsDB.stream().map(Cart::getId).collect(Collectors.toList());
		Integer[] iids = (Integer[])ids.toArray(new Integer[ids.size()]);
		return cartDao.delCarts(iids);
	}

	@Override
	public List<Cart> getUserCartsById(User operator, String user_id) throws CartExistException {
		if("".equals(user_id))
			throw new CartExistException("查询失败,ID参数不可为空!");
		if(operator == null)
			throw new CartExistException("查询失败,操作者信息不明!");
		IUserDao userDao = DaoFactory.getUserDao();
		
		Integer id = Integer.parseInt(user_id);
		User user = userDao.getUserById(id);
		if(user == null)
			throw new CartExistException("查询失败,您查询的用户不存在!");
		
		User op = userDao.getUserById(operator.getId());
		if(!op.getRole().contains("管理员") && id!=op.getId()) 
			throw new CartExistException("查询失败,您无权力查询用户详情!");
		
		ICartDao cartDao = DaoFactory.getCartDao();
		List<Cart> userCarts = cartDao.getCartsByUserId(user.getId());
		
		if(userCarts == null) 
			throw new CartExistException("查询失败!");
		
		return userCarts;
	}
	
}
