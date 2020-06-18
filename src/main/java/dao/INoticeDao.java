package dao;

import java.util.List;

import model.Notice;

//通用的公告通知dao层接口
public interface INoticeDao {
	
	/**
	 * 	获取所有的公告通知数据
	 * @return
	 */
	public List<Notice> getNotices();
	
	/**
	 * 	获取最新的公告通知
	 * @return
	 */
	public Notice getLastestNotice();
	
	/**
	 * 	获取批量的公告通知数据
	 * @param ids
	 * @return
	 */
	public List<Notice> getNotices(Integer[] ids);
	
	/**
	 * 	根据公告id获取相应的公告通知	
	 * @param id
	 * @return
	 */
	public Notice getNoticeById(Integer id);
	
	/**
	 * 	删除指定的公告通知
	 * @param notice
	 * @return
	 */
	public Integer delNotice(Notice notice);
	
	/**
	 * 	批量删除指定的公告通知
	 * @param notices
	 * @return
	 */
	public Integer delNotice(List<Notice> notices);
	
	/**
	 * 	添加新的公告通知
	 * @param notice
	 * @return
	 */
	public Integer addNotice(Notice notice);
	
	/**
	 * 	更新指定公告通知信息
	 * @param notice
	 * @return
	 */
	public Integer updateNotice(Notice notice);

	/**
	 * 	获取公告总数量
	 * @return
	 */
	public Integer getNoticesCount();

	/**
	 * 	分页获取公告列表
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<Notice> getNotices(Integer pageNo, Integer pageSize);
	
}
