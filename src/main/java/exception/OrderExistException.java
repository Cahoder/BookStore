package exception;

/**
 * 	订单是否存在异常
 * @author CAIHONGDE
 *
 */
public class OrderExistException extends Exception{

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private String message;

	public OrderExistException(String message) {
		super(message);
		this.message = message;
	}
	
}
