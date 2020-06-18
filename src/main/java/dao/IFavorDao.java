package dao;

import java.util.List;

import model.Favor;

public interface IFavorDao {
	
	/**
	 * 	通过用户id获取其收藏夹列表
	 * @param user_id
	 * @return
	 */
	public List<Favor> getFavorsByUserId(Integer user_id);
	
	/**
	 * 	根据收藏夹id获取指定收藏夹
	 * @param favor_id
	 * @return
	 */
	public Favor getFavorById(Integer favor_id);
	
	/**
	 * 	给用户添加收藏夹条目
	 * @param favor
	 * @return
	 */
	public Integer addFavor(Favor favor);
	
	/**
	 * 	更新用户收藏夹条目
	 * @param favor
	 * @return
	 */
	public Integer updateFavor(Favor favor);
	
	/**
	 * 	删除用户收藏夹条目
	 * @param favor_id
	 * @return
	 */
	public Integer delFavor(Integer favor_id);
	
	/**
	 * 	批量删除用户收藏夹条目
	 * @param favor_ids
	 * @return
	 */
	public Integer delFavors(Integer... favor_ids);
	
}
