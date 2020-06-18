package exception;

/**
 * 	产品是否存在异常
 * @author CAIHONGDE
 *
 */
public class ProductExistException extends Exception{

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private String message;

	public ProductExistException(String message) {
		super(message);
		this.message = message;
	}
	
}
