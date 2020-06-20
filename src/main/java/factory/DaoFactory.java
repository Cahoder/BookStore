package factory;

import dao.*;
import dao.impl.*;

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

	// 获取字符常量Dao
	public static IStrDao getStrDao() {
		return new StrDaoImpl();
	}

}
