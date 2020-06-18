package factory;

import service.ICartService;
import service.INoticeService;
import service.IProductService;
import service.IUserService;
import service.impl.CartServiceImpl;
import service.impl.NoticeServiceImpl;
import service.impl.ProductServiceImpl;
import service.impl.UserServiceImpl;

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
}
