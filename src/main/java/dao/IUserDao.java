package dao;

import java.util.List;

import model.User;

public interface IUserDao {
	
	/**
	 * @return 建立数据库连接获取所有指定角色用户总量
	 * @param role "超级管理员"  "管理员"  "普通用户"
	 */
	public Integer getUsersCount(String role);
	
	/**
	 * @return 建立数据库连接获取所有指定角色用户信息列表
	 * @param role "超级管理员"  "管理员"  "普通用户"
	 */
	public List<User> getUsersData(String role);
	
	/**
	 * @param role "超级管理员"  "管理员"  "普通用户"
	 * @param username 建立数据库连接根据用户名模糊查询获取所有符合角色用户列表
	 * @return
	 */
	public List<User> nameFilterUsersData(String role, String username);

	/**
	 * @return 建立数据库连接分页获取所有指定角色用户信息列表
	 * @param role "超级管理员"  "管理员"  "普通用户"
	 * @param pageNo 分页页号
	 * @param pageSize 每页数量
	 * @return
	 */
	public List<User> getUsersData(String role, Integer pageNo, Integer pageSize);

	/**
	 * 	根据id获取指定用户
	 * @param id
	 * @return <code>null</code> if no special user exist
	 */
	public User getUserById(Integer id);
	
	/**
	 * 	根据用户名和密码验证用户是否存在
	 * @param username
	 * @param password
	 * @return <code>null</code> if no special user exist
	 */
	public User verifyUser(String username,String password);
	
	/**
	 * 	根据用户名和密码和角色验证用户是否存在
	 * @param role "超级管理员"  "管理员"  "普通用户"
	 * @param username
	 * @param password
	 * @return <code>null</code> if no special user exist
	 */
	public User verifyUser(String role,String username,String password);
	
	/**
	 * 	删除用户
	 * @param user
	 * @return <code>0</code> if delete user failed
	 */
	public Integer delUser(User user);
	
	/**
	 * 	更新用户
	 * @param user
	 * @return <code>null</code> if no special user exist
	 */
	public Integer updateUser(User user);
	
	/**
	 * 	添加用户
	 * @param user
	 * @return <code>0</code> if add user failed
	 */
	public Integer addUser(User user);

	
}
