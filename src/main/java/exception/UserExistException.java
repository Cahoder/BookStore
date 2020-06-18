package exception;

/**
 * 	操作用户是否存在异常
 * @author CAIHONGDE
 *
 */
public class UserExistException extends Exception{
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private String message;

	public UserExistException(String message) {
		super(message);
		this.message = message;
	}
}
