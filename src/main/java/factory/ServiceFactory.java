package factory;

import service.*;
import service.impl.*;

/**
 * Service层工厂提供类
 */
public class ServiceFactory {
	
	// 获取公告Service
	public static INoticeService getNoticeService() {
		return new NoticeServiceImpl();
	}
	
	// 获取用户Service
	public static IUserService getUserService() {
		return new UserServiceImpl();
	}
	
	// 获取商品Service
	public static IProductService getProductService() {
		return new ProductServiceImpl();
	}
	
	// 获取购车Service
	public static ICartService getCartService() {
		return new CartServiceImpl();
	}

	// 获取订单Service
	public static IOrderService getOrderService() {
		return new OrderServiceImpl();
	}

	// 获取字符常量Service
	public static IStrService getStrService() {
		return new StrServiceImpl();
	}

	// 获取销售榜单Service
    public static ISaleService getSaleService() {
		return new SaleServiceImpl();
    }
}
