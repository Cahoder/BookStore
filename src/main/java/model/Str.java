package model;

import java.sql.Timestamp;

/**
 *	书城字符常量	JavaBean
 */
public class Str {
	
	private Integer id; //常量ID
	private Integer str_cid; //常量所属类,按需分类
	private String str_name; //常量名
	private String str_value; //常量值
	private Integer str_order; //常量所在类的排序
	private String str_tips; //常量描述信息
	private Integer is_show; //是否对用户显示状态 0/显示,1/不显示
	private Integer str_range; //常量的应用范围 null无限制,0/前台,1/后台
	private Integer is_del; //是否被删除状态 0/未删除,1/已删除
	private Timestamp update_time; //更新时间
	private Timestamp create_time; //创建时间
	
	public Str() {}

	public Str(Integer str_cid, String str_name, String str_value, Integer str_order, String str_tips,
			Integer str_range,Integer is_show) {
		this.str_cid = str_cid;
		this.str_name = str_name;
		this.str_value = str_value;
		this.str_order = str_order;
		this.str_tips = str_tips;
		this.str_range = str_range;
		this.is_show = is_show;
	}

	public Str(Integer id, Integer str_cid, String str_name, String str_value, Integer str_order, String str_tips,
			Integer is_show, Integer str_range, Integer is_del, Timestamp update_time, Timestamp create_time) {
		super();
		this.id = id;
		this.str_cid = str_cid;
		this.str_name = str_name;
		this.str_value = str_value;
		this.str_order = str_order;
		this.str_tips = str_tips;
		this.is_show = is_show;
		this.str_range = str_range;
		this.is_del = is_del;
		this.update_time = update_time;
		this.create_time = create_time;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStr_cid() {
		return str_cid;
	}

	public void setStr_cid(Integer str_cid) {
		this.str_cid = str_cid;
	}

	public String getStr_name() {
		return str_name;
	}

	public Timestamp getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public void setStr_name(String str_name) {
		this.str_name = str_name;
	}

	public String getStr_value() {
		return str_value;
	}

	public void setStr_value(String str_value) {
		this.str_value = str_value;
	}

	public Integer getStr_order() {
		return str_order;
	}

	public void setStr_order(Integer str_order) {
		this.str_order = str_order;
	}

	public String getStr_tips() {
		return str_tips;
	}

	public void setStr_tips(String str_tips) {
		this.str_tips = str_tips;
	}

	public Integer getStr_range() {
		return str_range;
	}

	public void setStr_range(Integer str_range) {
		this.str_range = str_range;
	}

	public Integer getIs_show() {
		return is_show;
	}

	public void setIs_show(Integer is_show) {
		this.is_show = is_show;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}
	
}
