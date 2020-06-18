package factory;

import dao.ICartDao;
import dao.IFavorDao;
import dao.INoticeDao;
import dao.IOrderDao;
import dao.IOrderItemDao;
import dao.IProductDao;
import dao.ISaleDao;
import dao.IUserDao;
import dao.IUserReceiptDao;
import dao.impl.CartDaoImpl;
import dao.impl.FavorDaoImpl;
import dao.impl.NoticeDaoImpl;
import dao.impl.OrderDaoImpl;
import dao.impl.OrderItemDaoImpl;
import dao.impl.ProductDaoImpl;
import dao.impl.SaleDaoImpl;
import dao.impl.UserDaoImpl;
import dao.impl.UserReceiptDaoImpl;

/**
 * Dao层工厂提供类
 */
public class DaoFactory {

	// 获取商品Dao
	public static IProductDao getProductDao() {
		return new ProductDaoImpl();
	}

	// 获取销售Dao
	public static ISaleDao getSaleDao() {
		return new SaleDaoImpl();
	}

	// 获取订单Dao
	public static IOrderDao getOrderDao() {
		return new OrderDaoImpl();
	}

	// 获取订单商品详情Dao
	public static IOrderItemDao getOrderItemDao() {
		return new OrderItemDaoImpl();
	}

	// 获取用户地址Dao
	public static IUserReceiptDao getUserReceiptDao() {
		return new UserReceiptDaoImpl();
	}

	// 获取用户Dao
	public static IUserDao getUserDao() {
		return new UserDaoImpl();
	}

	// 获取收藏夹Dao
	public static IFavorDao getFavorDao() {
		return new FavorDaoImpl();
	}

	// 获取公告Dao
	public static INoticeDao getNoticeDao() {
		return new NoticeDaoImpl();
	}

	// 获取购物车Dao
	public static ICartDao getCartDao() {
		return new CartDaoImpl();
	}

}
