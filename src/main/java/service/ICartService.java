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
	 * @throws CartExistException
	 */
	public void updateUserCarts(User user, List<Cart> carts) throws CartExistException;
	
	/**
	 * 	清空用户购物车
	 * @param user
	 * @return 已经删除条目
	 * @throws CartExistException
	 */
	public Integer clearUserCarts(User user) throws CartExistException;
	
	/**
	 * 	根据用户ID获取用户的购物车所有条目详细信息
	 * @param operator
	 * @param user_id
	 * @return
	 * @throws CartExistException
	 */
	public List<Cart> getUserCartsById(User operator, String user_id) throws CartExistException;
	
}
