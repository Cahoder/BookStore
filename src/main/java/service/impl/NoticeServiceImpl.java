package service.impl;

import java.util.List;

import exception.NoticeExistException;
import factory.DaoFactory;
import model.Notice;
import service.INoticeService;

public class NoticeServiceImpl implements INoticeService {

	@Override
	public List<Notice> getNotices() {
		return DaoFactory.getNoticeDao().getNotices();
	}

	@Override
	public Notice getLastestNotice() {
		return DaoFactory.getNoticeDao().getLastestNotice();
	}

	@Override
	public Integer addNotice(Notice notice) throws NoticeExistException {
		if(notice == null)
			throw new NoticeExistException("添加失败,公告信息有误!");
		return DaoFactory.getNoticeDao().addNotice(notice);
	}

	@Override
	public Integer editNotice(Notice notice) throws NoticeExistException {
		Notice bean = DaoFactory.getNoticeDao().getNoticeById(Integer.valueOf(notice.getId()));
		if(bean == null)
			throw new NoticeExistException("更新失败,该公告不存在!");
		return DaoFactory.getNoticeDao().updateNotice(notice);
	}

	@Override
	public Integer delNotice(String id) throws NoticeExistException {
		if(id == null || "".equals(id))
			throw new NoticeExistException("删除失败,公告id有误!");
		Notice bean = DaoFactory.getNoticeDao().getNoticeById(Integer.valueOf(id));
		if(bean == null)
			throw new NoticeExistException("删除失败,该公告不存在!");
		Integer res = DaoFactory.getNoticeDao().delNotice(bean);
		if(res < 1)
			throw new NoticeExistException("删除失败!");
		return res;
	}

	@Override
	public Notice getNotice(String id) throws NoticeExistException {
		if(id == null || "".equals(id))
			throw new NoticeExistException("查找失败,公告id有误!");
		return DaoFactory.getNoticeDao().getNoticeById(Integer.valueOf(id));
	}

	@Override
	public Integer delNotices(String[] ids) throws NoticeExistException {
		if(ids == null || ids.length < 1)
			throw new NoticeExistException("删除失败,公告id有误!");
		
		Integer[] iids = new Integer[ids.length];
		for(int i=0;i<ids.length;i++)
			iids[i]=Integer.parseInt(ids[i]);
		
		List<Notice> notices = DaoFactory.getNoticeDao().getNotices(iids);
		Integer res = DaoFactory.getNoticeDao().delNotice(notices);
		
		if(res < 1)
			throw new NoticeExistException("删除失败!");
		return res;
	}

	@Override
	public Integer getNoticesCount() {
		return DaoFactory.getNoticeDao().getNoticesCount();
	}

	@Override
	public List<Notice> getNoticesByPage(Integer pageNo, Integer pageSize) {
		return DaoFactory.getNoticeDao().getNotices(pageNo,pageSize);
	}
	
}
