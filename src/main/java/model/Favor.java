package model;

import java.sql.Timestamp;

/*
 * 	用户收藏夹
 */
public class Favor {
	
	private Integer id;
	private Product product;
	private User user;
	private Integer is_del;
	private Timestamp create_time;
	
	public Favor() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public Favor(Integer id, Product product, User user, Integer is_del, Timestamp create_time) {
		super();
		this.id = id;
		this.product = product;
		this.user = user;
		this.is_del = is_del;
		this.create_time = create_time;
	}

	public Favor(User user, Product product) {
		this.product = product;
		this.user = user;
	}
	
}
