package service;

import java.util.List;

import exception.UserExistException;
import model.Favor;
import model.User;

/**
 * 	用户业务逻辑层接口
 * @author CAIHONGDE
 *
 */
public interface IUserService {
	
	/**
	 * 	查找所有非超级权限的管理员
	 * @param operator 操作人员,只有超级管理员才有权力查看
	 * @return
	 * @throws UserExistException
	 */
	public List<User> getAdmins(User operator) throws UserExistException;
	
	/**
	 * 	分页查找所有非超级权限的管理员
	 * @param operator 操作人员,只有超级管理员才有权力查看
	 * @param pageNo 分页页号
	 * @param pageSize 每页数量
	 * @return
	 * @throws UserExistException
	 */
	public List<User> getAdminsByPage(User operator, Integer pageNo, Integer pageSize) throws UserExistException;
	
	/**
	 * 	查找所有非超级权限的管理员总数
	 * @param operator 操作人员,只有超级管理员才有权力查看
	 * @return
	 * @throws UserExistException
	 */
	public Integer getAdminsCount(User operator) throws UserExistException;
	
	/**
	 * 	根据管理员ID获取管理员的详细信息
	 * @param operator
	 * @param admin_id
	 * @return
	 * @throws UserExistException
	 */
	public User getAdminDetailsById(User operator, String admin_id) throws UserExistException;

	/**
	 * 	根据条件过滤管理员用户
	 * @param operator 只有超级管理员才有资格获取管理员信息
	 * @param like 目前只用到了 "用户名查询......"
	 * @return
	 * @throws UserExistException
	 */
	public List<User> filterAdmins(User operator, String ...like) throws UserExistException;
	
	/**
	 * 	添加管理员
	 * @param operator 只有超级管理员才有权限添加
	 * @param admin	不可以再添加超级管理员
	 * @return
	 * @throws UserExistException
	 */
	public Integer addAdmin(User operator, User admin) throws UserExistException;
	
	/**
	 * 	根据用户名和密码获取指定管理员
	 * @param adminname
	 * @param password
	 * @throws IUserService 验证用户名或密码失败
	 * @return
	 * @throws UserExistException
	 */
	public User getAdmin(String adminname,String password) throws UserExistException;
	
	/**
	 * 	删除管理员
	 * @param operator 只有超级管理员才有权力删除
	 * @param admin_id 超级管理员不可以被删除,且不可以删除自身
	 * @return
	 * @throws UserExistException
	 */
	public Integer delAdmin(User operator, String admin_id) throws UserExistException;
	
	/**
	 * 	更新管理员信息
	 * @param operator
	 * @param admin 管理员的信息只可以更新自己的,超级管理员有权更新非超级管理员
	 * @return
	 * @throws UserExistException
	 */
	public Integer updateAdmin(User operator, User admin) throws UserExistException;
	
	/**
	 * 	查找所有普通用户
	 * @param operator 只有管理员才有资格获取用户信息
	 * @return
	 * @throws UserExistException
	 */
	public List<User> getUsers(User operator) throws UserExistException;
	
	/**
	 * 	根据条件过滤普通用户
	 * @param operator 只有管理员才有资格获取用户信息
	 * @param like 目前只用到了 "用户名查询......"
	 * @return
	 * @throws UserExistException
	 */
	public List<User> filterUsers(User operator, String ...like) throws UserExistException;

	/**
	 * 	分页查找所有普通用户
	 * @param operator 只有管理员才有资格获取用户信息
	 * @param pageNo 分页页号
	 * @param pageSize 每页数量
	 * @return
	 * @throws UserExistException
	 */
	public List<User> getUsersByPage(User operator,Integer pageNo,Integer pageSize) throws UserExistException;
	
	/**
	 * 	添加普通用户
	 * @return <b>0</b> if insert the new user failed.
	 * @throws UserExistException
	 */
	public Integer addUser(User user) throws UserExistException;
	
	/**
	 * 	根据用户名和密码获取用户信息
	 * @param username
	 * @param password
	 * @return
	 * @throws UserExistException 用户名或密码验证失败
	 */
	public User getUser(String username, String password) throws UserExistException;
	
	/**
	 * 	更新用户信息
	 * @param user
	 * @return
	 * @throws UserExistException 更新失败
	 */
	public Integer updateUser(User user) throws UserExistException;
	
	/**
	 * 	获取所有用户的数据总量
	 * @param operator 只有管理员才有资格获取
	 * @return
	 * @throws UserExistException
	 */
	public Integer getUsersCount(User operator) throws UserExistException;
	
	/**
	 * 	根据用户ID获取用户的包含收件地址等详细信息
	 * @param operator
	 * @param user_id
	 * @return
	 * @throws UserExistException
	 */
	public User getUserDetailsById(User operator, String user_id) throws UserExistException;
	
	/**
	 * 	根据用户ID获取用户的收藏夹所有条目详细信息
	 * @param operator
	 * @param user_id
	 * @return
	 * @throws UserExistException
	 */
	public List<Favor> getUserFavorsById(User operator, String user_id) throws UserExistException;
	
}
