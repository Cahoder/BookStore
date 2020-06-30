package service.impl;

import java.sql.Timestamp;
import java.util.List;

import exception.SaleExistException;
import factory.DaoFactory;
import model.Sale;
import service.ISaleService;

public class SaleServiceImpl implements ISaleService{

	@Override
	public List<Sale> getSales() throws SaleExistException {
		return DaoFactory.getSaleDao().getSalesData();
	}

	@Override
	public List<Sale> getOnSales() throws SaleExistException {
		return DaoFactory.getSaleDao().getOnSalesData();
	}

	@Override
	public List<Sale> getSales(Integer pageNo, Integer pageSize) throws SaleExistException {
		if(pageNo == null || pageSize == null )
			throw new SaleExistException("查询失败，参数有误！");
		return DaoFactory.getSaleDao().getSalesData(pageNo, pageSize);
	}

	@Override
	public List<Sale> getOnSales(Integer pageNo, Integer pageSize) throws SaleExistException {
		if(pageNo == null || pageSize == null)
			throw new SaleExistException("查询失败，参数有误！");
		return DaoFactory.getSaleDao().getOnSalesData(pageNo, pageSize);
	}

	@Override
	public List<Sale> getBetweenSales(Timestamp start, Timestamp end) throws SaleExistException {
		if(start == null || end == null)
			throw new SaleExistException("查询失败，参数有误！");
		return DaoFactory.getSaleDao().getSalesBetweenData(start, end);
	}

	@Override
	public List<Sale> getBetweenOnSales(Timestamp start, Timestamp end) throws SaleExistException {
		if(start == null || end == null)
			throw new SaleExistException("查询失败，参数有误！");
		return DaoFactory.getSaleDao().getOnSalesBetweenData(start, end);
	}

	@Override
	public Integer getSalesCount() {
		return DaoFactory.getSaleDao().getSalesCount();
	}

	@Override
	public Integer getOnSalesCount() {
		return DaoFactory.getSaleDao().getOnSalesCount();
	}
	
}
