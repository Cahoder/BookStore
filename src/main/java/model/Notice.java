package model;

import java.sql.Timestamp;

public class Notice {
	private Integer id;
	private String title;
	private String details;
	private Integer is_del;
	private Timestamp create_time;
	
	public Notice(Integer id, String title, String details, Integer is_del, Timestamp create_time) {
		super();
		this.id = id;
		this.title = title;
		this.details = details;
		this.is_del = is_del;
		this.create_time = create_time;
	}
	public Notice() {}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
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
	
}
