package dao;

import java.util.List;

import model.Str;

public interface IStrDao {
	
	/**
	 * 	获取所有的字符串常量
	 * @return
	 */
	public List<Str> getStrs();
	
	/**
	 * 	分页获取所有的字符串常量
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<Str> getStrs(Integer pageNo,Integer pageSize);
	
	/**
	 * 	获取所有的字符串常量总数量
	 * @return
	 */
	public Integer getStrsCount();
	
	/**
	 * 	通过id主键获取指定的字符串常量
	 * @param id
	 * @return
	 */
	public Str getStrById(Integer id);

	/**
	 * 	获取所属类别相同的字符串常量
	 * @param cid (cid=1 轮播图 ， )
	 * @return
	 */
	public List<Str> getStrsByCid(Integer cid);
	
	/**
	 * 	分页获取所属类别相同的字符串常量
	 * @param cid
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<Str> getStrsByCid(Integer cid,Integer pageNo,Integer pageSize);
	
	/**
	 * 	获取所属类别相同的字符串常量总数量
	 * @param cid (cid=1 轮播图 ， )
	 * @return
	 */
	public Integer getStrsCountByCid(Integer cid);
	
	/**
	 * 	获取应用范围相同的字符串常量
	 * @param range
	 * @return
	 */
	public List<Str> getStrsByRange(Integer range);
	
	/**
	 * 	删除所属类别相同的字符串常量
	 * @param cid (cid=1 轮播图 ， )
	 * @return
	 */
	public Integer deleteByCid(Integer cid);
	
	/**
	 * 	根据主键id批量删除指定的字符串常量
	 * @param ids
	 * @return
	 */
	public Integer deleteByIds(Integer...ids);
	
	/**
	 * 	插入新的字符串常量
	 * @param str
	 * @return
	 */
	public Integer insertStr(Str str);
	
	/**
	 * 	批量插入新的字符串常量
	 * @param strs
	 * @return
	 */
	public Integer insertStrs(List<Str> strs);
	
	/**
	 * 	更新字符串常量
	 * @param str
	 * @return
	 */
	public Integer updateStr(Str str);
	
	/**
	 * 	批量更新字符串常量
	 * @param strs
	 * @return
	 */
	public Integer updateStrs(List<Str> strs);
	
	/**
	 * 	批量更新字符串常量展示状态
	 * @param ids
	 * @return
	 */
	public Integer showChangeStrByIds(Integer...ids);
	
}
