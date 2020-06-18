package exception;

/**
 * 	购物车是否存在异常
 * @author CAIHONGDE
 *
 */
public class CartExistException extends Exception{

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private String message;

	public CartExistException(String message) {
		super(message);
		this.message = message;
	}
	
}
