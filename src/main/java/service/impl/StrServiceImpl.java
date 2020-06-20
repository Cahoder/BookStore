package service.impl;

import java.util.List;

import dao.IStrDao;
import exception.StrExistException;
import factory.DaoFactory;
import model.Str;
import service.IStrService;

public class StrServiceImpl implements IStrService{

	@Override
	public List<Str> getSliders() throws StrExistException {
		List<Str> sliders = DaoFactory.getStrDao().getStrsByCid(1);
		if(sliders==null) throw new StrExistException("查询失败，有误！");
		return sliders;
	}

	@Override
	public List<Str> getSlidersByPage(Integer pageNo, Integer pageSize) throws StrExistException {
		List<Str> sliders = DaoFactory.getStrDao().getStrsByCid(1,pageNo,pageSize);
		if(sliders==null) throw new StrExistException("查询失败，有误！");
		return sliders;
	}

	@Override
	public Integer addSlider(Str slider) throws StrExistException {
		if(slider==null) throw new StrExistException("添加失败，参数有误！");
		slider.setStr_cid(1);
		slider.setStr_tips("首页轮播图");
		
		Integer res = DaoFactory.getStrDao().insertStr(slider);
		if(res == null || res < 1) throw new StrExistException("添加失败！");
		return res;
	}

	@Override
	public Integer showSlider(String slider_id) throws StrExistException {
		if(slider_id==null) throw new StrExistException("更新失败，参数有误！");
		IStrDao strDao = DaoFactory.getStrDao();
		
		Str slider = strDao.getStrById(Integer.valueOf(slider_id));
		if(slider==null || slider.getStr_cid()!=1)
			throw new StrExistException("更新失败,该轮播图不存在！");
		
		Integer res = strDao.showChangeStrByIds(slider.getId());
		if(res == null || res < 1) throw new StrExistException("更新失败！");
		return res;
	}

	@Override
	public Integer updateSlider(Str slider) throws StrExistException {
		if(slider==null) throw new StrExistException("更新失败，参数有误！");
		
		IStrDao strDao = DaoFactory.getStrDao();
		Str s = strDao.getStrById(slider.getId());
		if(s.getStr_cid()!=1)
			throw new StrExistException("更新失败，该轮播图不存在！");
		
		slider.setStr_cid(s.getStr_cid());
		Integer res = strDao.updateStr(slider);
		if(res == null || res < 1) throw new StrExistException("更新失败！");
		return res;
	}

	@Override
	public Integer deleteSliders(String... slider_ids) throws StrExistException {
		if(slider_ids==null || slider_ids.length < 1)
			throw new StrExistException("删除失败，参数有误！");
		
		Integer[] iids = new Integer[slider_ids.length];
		for(int i=0;i<slider_ids.length;i++)
			iids[i]=Integer.parseInt(slider_ids[i]);
		
		Integer res = DaoFactory.getStrDao().deleteByIds(iids);
		if(res == null || res < 1)
			throw new StrExistException("删除失败!");
		return res;
	}

	@Override
	public Integer getSliderCounts() throws StrExistException {
		Integer count = DaoFactory.getStrDao().getStrsCountByCid(1);
		if(count==null) throw new StrExistException("查询失败，有误！");
		return count;
	}

	@Override
	public Str getSliderById(String slider_id) throws StrExistException {
		if(slider_id==null) throw new StrExistException("查找失败，参数有误！");
		Str slider = DaoFactory.getStrDao().getStrById(Integer.valueOf(slider_id));
		if(slider==null||slider.getStr_cid()!=1)
			throw new StrExistException("查找失败，查找的轮播图不存在！");
		return slider;
	}

}
