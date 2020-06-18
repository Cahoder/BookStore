package model;

import java.sql.Timestamp;
import java.util.List;

/**
 *	书城用户	JavaBean
 */
public class User {
	
	private Integer id;
	private String username;
	private String password;
	private String gender;
	private String email;
	private String telephone;
	private String introduce;
	private String role;
	private String avatar;
	private List<UserReceipt> user_receipt;
	private List<Order> user_order;
	private Timestamp register_time;
	private Integer is_del;
	
	public User() {}
	
	public User(Integer id, String username, String password, String gender, String email, String telephone,
			String introduce, String role, Timestamp register_time, Integer is_del) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.gender = gender;
		this.email = email;
		this.telephone = telephone;
		this.introduce = introduce;
		this.role = role;
		this.register_time = register_time;
		this.is_del = is_del;
	}

	public User(Integer id, String username, String password, String gender, String email, String telephone,
			String introduce, String role, String avatar, List<UserReceipt> user_receipt, List<Order> user_order,
			Timestamp register_time, Integer is_del) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.gender = gender;
		this.email = email;
		this.telephone = telephone;
		this.introduce = introduce;
		this.role = role;
		this.avatar = avatar;
		this.user_receipt = user_receipt;
		this.user_order = user_order;
		this.register_time = register_time;
		this.is_del = is_del;
	}

	public User(Integer id, String username, String password, String gender, String email, String telephone,
			String introduce, String role, String avatar, List<UserReceipt> user_receipt, Timestamp register_time,
			Integer is_del) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.gender = gender;
		this.email = email;
		this.telephone = telephone;
		this.introduce = introduce;
		this.role = role;
		this.avatar = avatar;
		this.user_receipt = user_receipt;
		this.register_time = register_time;
		this.is_del = is_del;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Timestamp getRegister_time() {
		return register_time;
	}

	public void setRegister_time(Timestamp register_time) {
		this.register_time = register_time;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public List<UserReceipt> getUser_receipt() {
		return user_receipt;
	}

	public void setUser_receipt(List<UserReceipt> user_receipt) {
		this.user_receipt = user_receipt;
	}

	public List<Order> getUser_order() {
		return user_order;
	}

	public void setUser_order(List<Order> user_order) {
		this.user_order = user_order;
	}
	
}
