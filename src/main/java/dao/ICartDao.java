package dao;

import java.util.List;

import model.Cart;

public interface ICartDao {
	
	/**
	 * 	根据用户ID获取其所属的购物车列表
	 * @param user_id
	 * @return
	 */
	public List<Cart> getCartsByUserId(Integer user_id);
	
	/**
	 * 	根据购物车ID获取指定条目
	 * @param cart_id
	 * @return
	 */
	public Cart getCartByCartId(Integer cart_id);
	
	/**
	 * 	给用户购物车添加条目
	 * @param cart
	 * @return
	 */
	public Integer addCart(Cart cart);
	
	/**
	 * 	批量给用户购物车添加条目
	 * @param carts
	 * @return
	 */
	public Integer addCarts(List<Cart> carts);
	
	/**
	 * 	删除用户购物车条目
	 * @param cart_id
	 * @return
	 */
	public Integer delCart(Integer cart_id);
	
	/**
	 * 	批量删除用户购物车条目
	 * @param cart_ids
	 * @return
	 */
	public Integer delCarts(Integer...cart_ids);
	
	/**
	 * 	更新用户购物车条目
	 * @param cart
	 * @return
	 */
	public Integer updateCart(Cart cart);
	
	/**
	 * 	批量更新用户购物车条目
	 * @param carts
	 * @return
	 */
	public Integer updateCarts(List<Cart> carts);
	
}
