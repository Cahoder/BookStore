package service.impl;

import java.util.List;
import java.util.stream.Collectors;

import dao.IFavorDao;
import dao.IUserDao;
import dao.IUserReceiptDao;
import exception.UserExistException;
import factory.DaoFactory;
import model.Favor;
import model.User;
import model.UserReceipt;
import service.IUserService;
import utils.JDBCUtils;

public class UserServiceImpl implements IUserService {

	@Override
	public List<User> getAdmins(User operator) throws UserExistException {
		if(operator == null)
			throw new UserExistException("查询失败,操作管理者信息不明!");
		
		IUserDao userDao = DaoFactory.getUserDao();
		
		User op = userDao.getUserById(operator.getId());
		if(!"超级管理员".equals(op.getRole())) 
			throw new UserExistException("查询失败,您无权力查询管理员列表!");
		
		List<User> data = userDao.getUsersData("管理员");
		
		if(data == null)
			throw new UserExistException("查询管理员列表失败!");
		return data.stream().filter(user -> user.getIs_del()==0).collect(Collectors.toList());
	}

	@Override
	public List<User> getAdminsByPage(User operator, Integer pageNo, Integer pageSize) 
			throws UserExistException {
		if(operator == null)
			throw new UserExistException("查询失败,操作管理者信息不明!");
		if(pageNo == null)
			throw new UserExistException("查询失败,分页页号参数不明!");
		if(pageSize == null)
			throw new UserExistException("查询失败,每页数量参数不明!");
		
		IUserDao userDao = DaoFactory.getUserDao();
		
		User op = userDao.getUserById(operator.getId());
		if(!"超级管理员".equals(op.getRole())) 
			throw new UserExistException("查询失败,您无权力查询管理员列表!");
		List<User> data = userDao.getUsersData("管理员",pageNo,pageSize);

		if(data == null)
			throw new UserExistException("分页查询管理员列表失败!");
		return data.stream().filter(user -> user.getIs_del()==0).collect(Collectors.toList());
	}

	@Override
	public Integer addAdmin(User operator, User admin) throws UserExistException {
		if(operator == null)
			throw new UserExistException("添加失败,操作管理者信息不明!");
		if(admin == null)
			throw new UserExistException("添加失败,待添加管理员信息不明!");
		
		IUserDao userDao = DaoFactory.getUserDao();
		User op = userDao.getUserById(operator.getId());
		if(!"超级管理员".equals(op.getRole())) 
			throw new UserExistException("添加失败,您无权力添加新的管理员!");
		
		User checkExist = DaoFactory.getUserDao().verifyUser("管理员",admin.getUsername(), admin.getPassword());
		if(checkExist != null)
			throw new UserExistException("添加管理员失败,相同账号名和密码的管理员已存在!");
		
		admin.setGender("");
		admin.setIntroduce("");
		admin.setRole("管理员");
		admin.setAvatar("");
		admin.setRegister_time(JDBCUtils.getCurrentTimestamp());
		
		Integer res = userDao.addUser(admin);
		if(res < 1)
			throw new UserExistException("添加管理员失败!");
		return res;
	}

	@Override
	public User getAdmin(String adminname, String password) throws UserExistException {
		if("".equals(adminname) || "".equals(password))
			throw new UserExistException("验证失败,用户名或者密码为空!");

		User admin = DaoFactory.getUserDao().verifyUser(adminname, password);
		if(admin == null)
			throw new UserExistException("验证失败,用户名或密码错误或用户不存在!");
		
		if(!admin.getRole().contains("管理员"))
			throw new UserExistException("验证失败,您不是管理员!");
		
		return admin;
	}

	@Override
	public Integer delAdmin(User operator, String admin_id) throws UserExistException {
		if(operator == null)
			throw new UserExistException("删除失败,操作管理者信息不明!");
		if("".equals(admin_id))
			throw new UserExistException("删除失败,待删除管理员信息不明!");
		
		IUserDao userDao = DaoFactory.getUserDao();
		User op = userDao.getUserById(operator.getId());
		if(!"超级管理员".equals(op.getRole())) 
			throw new UserExistException("删除失败,您无权力删除管理员!");
		
		User admin = userDao.getUserById(Integer.parseInt(admin_id));
		if(admin == null)
			throw new UserExistException("删除失败,待删除管理员不存在!");
		
		Integer res = userDao.delUser(admin);
		if(res < 1)
			throw new UserExistException("删除管理员失败!");
		return res;
	}

	@Override
	public Integer updateAdmin(User operator, User admin) throws UserExistException {
		if(operator == null)
			throw new UserExistException("更新失败,操作管理者信息不明!");
		if(admin == null)
			throw new UserExistException("更新失败,待更新管理员信息不明!");
		
		IUserDao userDao = DaoFactory.getUserDao();
		User op = userDao.getUserById(operator.getId());
		if(!op.getRole().contains("管理员"))
			throw new UserExistException("更新失败,您不是管理者身份!");
		
		User upd = userDao.getUserById(admin.getId());
		
		if(upd == null)
			throw new UserExistException("更新失败,待修改管理者不存在!");
		
		if(!upd.getRole().contains("管理员"))
			throw new UserExistException("更新失败,待修改用户不是管理者身份!");
		
		if(!"超级管理员".equals(op.getRole()) && op.getId() != upd.getId())
			throw new UserExistException("更新失败,您无权修改其它管理者身份!");
		
		if(admin.getGender()==null)
			admin.setGender(upd.getGender());
		if(admin.getAvatar()==null)
			admin.setAvatar(upd.getAvatar());
		if(admin.getIntroduce() == null)
			admin.setIntroduce(upd.getIntroduce());
		if(admin.getRole() == null)
			admin.setRole(upd.getRole());
		
		Integer res = userDao.updateUser(admin);
		if(res < 1)
			throw new UserExistException("更新管理员信息失败!");
		return res;
	}

	@Override
	public List<User> filterAdmins(User operator, String... like) throws UserExistException {
		if(operator == null)
			throw new UserExistException("查询失败,操作管理者信息不明!");
		
		IUserDao userDao = DaoFactory.getUserDao();
		
		User op = userDao.getUserById(operator.getId());
		if(!"超级管理员".equals(op.getRole())) 
			throw new UserExistException("查询失败,您无权力查询管理员!");
		
		if(like==null||like.length<1)
			throw new UserExistException("查询失败,查询参数有误!");
		
		List<User> data = null;
		
		//约定第一个参数参数是用户名
		String username = like[0];
		if(!"".equals(username)) {
			data = DaoFactory.getUserDao().nameFilterUsersData("管理员", username);
		}
		/**TODO else if**/
		
		
		if(data == null)
			throw new UserExistException("查询失败!");
		return data;
	}

	@Override
	public Integer getAdminsCount(User operator) throws UserExistException {
		if(operator == null)
			throw new UserExistException("查询失败,操作管理者信息不明!");
		
		IUserDao userDao = DaoFactory.getUserDao();
		
		User op = userDao.getUserById(operator.getId());
		if(!"超级管理员".equals(op.getRole())) 
			throw new UserExistException("查询失败,您无权力查询管理员数量!");
		
		return userDao.getUsersCount("管理员");
	}

	@Override
	public User getAdminDetailsById(User operator, String admin_id)  throws UserExistException {
		if("".equals(admin_id))
			throw new UserExistException("查询失败,ID参数不可为空!");
		if(operator == null)
			throw new UserExistException("查询失败,操作管理者信息不明!");
		IUserDao userDao = DaoFactory.getUserDao();
		
		User op = userDao.getUserById(operator.getId());
		if(!"超级管理员".equals(op.getRole())) 
			throw new UserExistException("查询失败,您无权力查询管理员详细信息!");
		
		User user = userDao.getUserById(Integer.parseInt(admin_id));
		if(user == null)
			throw new UserExistException("查询失败,您查询的管理员不存在!");
		
		return user;
	}

	@Override
	public List<User> getUsers(User operator) throws UserExistException {
		if(operator == null)
			throw new UserExistException("查询失败,操作管理者信息不明!");
		
		IUserDao userDao = DaoFactory.getUserDao();
		
		User op = userDao.getUserById(operator.getId());
		if(!op.getRole().contains("管理员")) 
			throw new UserExistException("查询失败,您无权力查询用户列表!");
		
		List<User> data = userDao.getUsersData("普通用户");
		
		if(data == null)
			throw new UserExistException("查询用户列表失败!");
		return data;
	}

	@Override
	public List<User> getUsersByPage(User operator, Integer pageNo, Integer pageSize) throws UserExistException {
		if(operator == null)
			throw new UserExistException("查询失败,操作管理者信息不明!");
		if(pageNo == null)
			throw new UserExistException("查询失败,分页页号参数不明!");
		if(pageSize == null)
			throw new UserExistException("查询失败,每页数量参数不明!");
		
		IUserDao userDao = DaoFactory.getUserDao();
		
		User op = userDao.getUserById(operator.getId());
		if(!op.getRole().contains("管理员")) 
			throw new UserExistException("查询失败,您无权力查询用户列表!");
		
		List<User> data = userDao.getUsersData("普通用户",pageNo,pageSize);
		
		if(data == null)
			throw new UserExistException("分页查询用户列表失败!");
		return data;
	}

	@Override
	public Integer addUser(User user) throws UserExistException {
		if(user == null)
			throw new UserExistException("添加失败,待添加用户信息不明!");
		
		IUserDao userDao = DaoFactory.getUserDao();
		
		User checkExist = userDao.verifyUser("普通用户",user.getUsername(), user.getPassword());
		if(checkExist != null)
			throw new UserExistException("添加失败,待添加用户已存在!");
		
		user.setRole("普通用户");
		Integer res = userDao.addUser(user);
		if(res < 1)
			throw new UserExistException("添加用户失败!");
		return res;
	}

	@Override
	public User getUser(String username, String password) throws UserExistException {
		if("".equals(username) || "".equals(password))
			throw new UserExistException("验证失败,用户名或者密码为空!");
		
		User user = DaoFactory.getUserDao().verifyUser("普通用户",username, password);
		if(user == null)
			throw new UserExistException("验证失败,用户名或密码错误或用户不存在!");
		
		return user;
	}

	@Override
	public List<User> filterUsers(User operator, String... like) throws UserExistException {
		if(operator == null)
			throw new UserExistException("查询失败,操作管理者信息不明!");
		
		IUserDao userDao = DaoFactory.getUserDao();
		
		User op = userDao.getUserById(operator.getId());
		if(!op.getRole().contains("管理员")) 
			throw new UserExistException("查询失败,您无权力查询用户列表!");
		
		if(like==null||like.length<1)
			throw new UserExistException("查询失败,查询参数有误!");
		
		List<User> data = null;
		
		//约定第一个参数参数是用户名
		String username = like[0];
		if(!"".equals(username)) {
			data = DaoFactory.getUserDao().nameFilterUsersData("普通用户", username);
		}
		/**TODO else if**/
		
		
		if(data == null)
			throw new UserExistException("查询失败!");
		return data;
	}

	@Override
	public Integer updateUser(User user) throws UserExistException {
		if(user == null)
			throw new UserExistException("更新失败,待更新用户信息不明!");
		
		IUserDao userDao = DaoFactory.getUserDao();
		
		user.setRole("普通用户");
		if (user.getAvatar()==null)
			user.setAvatar("");
		Integer res = userDao.updateUser(user);
		if(res < 1)
			throw new UserExistException("更新用户信息失败!");
		return res;
	}

	@Override
	public Integer getUsersCount(User operator) throws UserExistException {
		if(operator == null)
			throw new UserExistException("查询失败,操作管理者信息不明!");
		
		IUserDao userDao = DaoFactory.getUserDao();
		
		User op = userDao.getUserById(operator.getId());
		if(!op.getRole().contains("管理员")) 
			throw new UserExistException("查询失败,您无权力查询用户数量!");
		
		return userDao.getUsersCount("普通用户");
	}

	@Override
	public User getUserDetailsById(User operator, String user_id) throws UserExistException {
		if("".equals(user_id))
			throw new UserExistException("查询失败,ID参数不可为空!");
		if(operator == null)
			throw new UserExistException("查询失败,操作管理者信息不明!");
		IUserDao userDao = DaoFactory.getUserDao();
		
		Integer id = Integer.parseInt(user_id);
		User user = userDao.getUserById(id);
		if(user == null)
			throw new UserExistException("查询失败,您查询的用户不存在!");
		
		User op = userDao.getUserById(operator.getId());
		if(!op.getRole().contains("管理员") && id!=op.getId()) 
			throw new UserExistException("查询失败,您无权力查询用户详情!");
		
		IUserReceiptDao userReceiptDao = DaoFactory.getUserReceiptDao();
		List<UserReceipt> userReceipts = userReceiptDao.getUserReceipts(user);
		
		user.setUser_receipt(userReceipts);
		return user;
	}

	@Override
	public List<Favor> getUserFavorsById(User operator, String user_id) throws UserExistException {
		if("".equals(user_id))
			throw new UserExistException("查询失败,ID参数不可为空!");
		if(operator == null)
			throw new UserExistException("查询失败,操作管理者信息不明!");
		IUserDao userDao = DaoFactory.getUserDao();
		
		Integer id = Integer.parseInt(user_id);
		User user = userDao.getUserById(id);
		if(user == null)
			throw new UserExistException("查询失败,您查询的用户不存在!");
		
		User op = userDao.getUserById(operator.getId());
		if(!op.getRole().contains("管理员") && id!=op.getId()) 
			throw new UserExistException("查询失败,您无权力查询用户详情!");
	
		IFavorDao favorDao = DaoFactory.getFavorDao();
		List<Favor> userFavors = favorDao.getFavorsByUserId(user.getId());
		
		if(userFavors == null) 
			throw new UserExistException("查询失败!");
		
		return userFavors;
	}
	
}
