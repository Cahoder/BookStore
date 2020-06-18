package model;

/**
 *	书城商品	JavaBean
 */
public class Product {
	
	private String id;
	private String name;
	private Double price;
	private String category;
	private Integer pnum;
	private String imgurl;
	private String description;
	private Integer is_del;
	
	public Product() {}

	public Product(String id, String name, Double price, String category, Integer pnum, String imgurl,
			String description, Integer is_del) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.category = category;
		this.pnum = pnum;
		this.imgurl = imgurl;
		this.description = description;
		this.is_del = is_del;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Double getPrice() {
		return price;
	}

	public String getCategory() {
		return category;
	}

	public Integer getPnum() {
		return pnum;
	}

	public String getImgurl() {
		return imgurl;
	}

	public String getDescription() {
		return description;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setPnum(Integer pnum) {
		this.pnum = pnum;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}
	
}
