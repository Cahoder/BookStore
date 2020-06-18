package model;

import java.sql.Timestamp;

/**
 * 	用户购物车
 * @author CAIHONGDE
 *
 */
public class Cart {

	private Integer id;
	private Product product;
	private Integer addnum;
	private User user;
	private Integer is_del;
	private Timestamp create_time;
	private Timestamp update_time;
	
	public Cart() {}

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

	public Integer getAddnum() {
		return addnum;
	}

	public void setAddnum(Integer addnum) {
		this.addnum = addnum;
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

	public Cart(Integer id, Product product, Integer addnum, User user, Integer is_del) {
		super();
		this.id = id;
		this.product = product;
		this.addnum = addnum;
		this.user = user;
		this.is_del = is_del;
	}

	public Timestamp getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}
	
}
