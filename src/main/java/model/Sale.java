package model;

/**
 *	书城销售商品	JavaBean
 */
public class Sale {
	private Integer buynum;
	private String product_id;
	private String product_name;
	private String category;
	
	public Sale() {}
	
	public Sale(Integer buynum, String product_id, String product_name, String category) {
		this.buynum = buynum;
		this.product_id = product_id;
		this.product_name = product_name;
		this.category = category;
	}

	public Integer getBuynum() {
		return buynum;
	}
	public void setBuynum(Integer buynum) {
		this.buynum = buynum;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
}
