package service;

import java.util.List;

import exception.NoticeExistException;
import model.Notice;

/**
 * 	公告业务逻辑层接口
 * @author CAIHONGDE
 */
public interface INoticeService {
	
	/**
	 * 	获取所有公告通知列表
	 * @return
	 */
	public List<Notice> getNotices();
	
	/**
	 * 	分页获取公告通知列表
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<Notice> getNoticesByPage(Integer pageNo, Integer pageSize);
	
	/**
	 * 	根据id获取指定公告通知
	 * @return
	 */
	public Notice getNotice(String id) throws NoticeExistException;
	
	/**
	 * 	获取最新的公告通知
	 * @return
	 */
	public Notice getLastestNotice();
	
	/**
	 * 	新增公告通知
	 * @param notice
	 * @return
	 */
	public Integer addNotice(Notice notice) throws NoticeExistException;
	
	/**
	 * 	编辑公告通知
	 * @param notice
	 * @return
	 */
	public Integer editNotice(Notice notice) throws NoticeExistException;
	
	/**
	 * 	删除公告通知
	 * @param id
	 * @return
	 */
	public Integer delNotice(String id) throws NoticeExistException;
	
	/**
	 * 	批量删除公告通知
	 * @param id
	 * @return
	 */
	public Integer delNotices(String[] ids) throws NoticeExistException;

	/**
	 * 	获取公告总数量
	 * @return
	 */
	public Integer getNoticesCount();
}
