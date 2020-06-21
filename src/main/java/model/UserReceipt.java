package model;

import java.sql.Timestamp;

/**
 * 	用户签收信息
 * @author CAIHONGDE
 *
 */
public class UserReceipt {
	
	private Integer id;
	private User user;	//信息所属用户
	private String name;
	private String address;
	private String phone;
	private Integer id_del;
	private Timestamp create_time;
	
	public UserReceipt() {}
	
	public UserReceipt(Integer id, User user, String name, String address, String phone, Integer id_del,
			Timestamp create_time) {
		super();
		this.id = id;
		this.user = user;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.id_del = id_del;
		this.create_time = create_time;
	}

	public UserReceipt(Integer id, User user, String name, String address, String phone) {
		super();
		this.id = id;
		this.user = user;
		this.name = name;
		this.address = address;
		this.phone = phone;
	}

	public UserReceipt(User user, String name, String address, String phone) {
		super();
		this.user = user;
		this.name = name;
		this.address = address;
		this.phone = phone;
	}

	public UserReceipt(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getId_del() {
		return id_del;
	}

	public void setId_del(Integer id_del) {
		this.id_del = id_del;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	
}
