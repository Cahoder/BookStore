package service;

import java.util.List;

import exception.CartExistException;
import model.Cart;
import model.User;

/**
 * 	购物车业务逻辑层接口
 * @author CAIHONGDE
 */
public interface ICartService {

	/**
	 * 	更新用户购物车
	 * @param user
	 * @param carts
	 */
	public void updateUserCarts(User user, List<Cart> carts) throws CartExistException;
	
	/**
	 * 	清空用户购物车
	 * @param user
	 * @return 已经删除条目
	 */
	public Integer clearUserCarts(User user);
	
}
