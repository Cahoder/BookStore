package exception;

/**
 * 	销售榜单是否存在异常
 * @author CAIHONGDE
 *
 */
public class SaleExistException extends Exception {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private String message;

	public SaleExistException(String message) {
		super(message);
		this.message = message;
	}
	
}
