package exception;

/**
 * 	字符串常量池是否存在异常
 * @author CAIHONGDE
 *
 */
public class StrExistException extends Exception{

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private String message;

	public StrExistException(String message) {
		super(message);
		this.message = message;
	}
	
}
