package exception;

/**
 * 	公告是否存在异常
 * @author CAIHONGDE
 *
 */
public class NoticeExistException extends Exception{

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private String message;

	public NoticeExistException(String message) {
		super(message);
		this.message = message;
	}
	
}
