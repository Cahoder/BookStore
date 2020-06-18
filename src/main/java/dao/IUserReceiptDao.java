package dao;

import java.util.List;

import model.User;
import model.UserReceipt;

public interface IUserReceiptDao {
	
	/**
	 * 	根据用户获取其所有未删除的收货地址
	 * @param user
	 * @return
	 */
	public List<UserReceipt> getUserReceipts(User user);
	
	/**
	 * 	根据id删除其指定的收货地址
	 * @param user
	 * @param receipt
	 * @return
	 */
	public Integer delUserReceiptById(Integer id);
	
	/**
	 * 	根据用户添加新的收货地址
	 * @param receipt
	 * @return
	 */
	public Integer addUserReceipt(UserReceipt receipt);
	
	/**
	 * 	根据id获取指定的收货地址
	 * @param user
	 * @param id
	 * @return
	 */
	public UserReceipt getUserReceiptById(Integer id);
	
	/**
	 * 	更新用户指定的收货地址
	 * @param receipt
	 * @return
	 */
	public Integer updateUserReceipt(UserReceipt receipt);
	
}
