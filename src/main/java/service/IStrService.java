package service;

import java.util.List;

import exception.StrExistException;
import model.Str;

/**
 * 	字符常量池业务逻辑层接口
 * @author CAIHONGDE
 */
public interface IStrService {
	
	/**
	 * 	获取所有未删除的轮播图
	 * @return
	 * @throws StrExistException
	 */
	public List<Str> getSliders() throws StrExistException;
	
	/**
	 * 	分页获取轮播图
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<Str> getSlidersByPage(Integer pageNo, Integer pageSize) throws StrExistException;

	/**
	 * 	添加新的轮播图
	 * @param slider
	 * @return
	 * @throws StrExistException
	 */
	public Integer addSlider(Str slider) throws StrExistException;
	
	/**
	 * 	改变对客户展示轮播图
	 * @param slider_id
	 * @return
	 * @throws StrExistException
	 */
	public Integer showSlider(String slider_id) throws StrExistException;
	
	/**
	 * 	更新轮播图
	 * @param slider
	 * @return
	 * @throws StrExistException
	 */
	public Integer updateSlider(Str slider) throws StrExistException;
	
	/**
	 * 	批量删除轮播图
	 * @param slider_ids
	 * @return
	 * @throws StrExistException
	 */
	public Integer deleteSliders(String...slider_ids) throws StrExistException;
	
	/**
	 * 	获取轮播图总数量
	 * @return
	 * @throws StrExistException
	 */
	public Integer getSliderCounts() throws StrExistException;

	/**
	 * 	通过ID获取指定的轮播图
	 * @param slider_id
	 */
	public Str getSliderById(String slider_id) throws StrExistException;
	
}
