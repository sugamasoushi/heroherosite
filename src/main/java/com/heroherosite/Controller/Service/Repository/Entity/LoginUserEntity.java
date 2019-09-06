package com.heroherosite.Controller.Service.Repository.Entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity	//エンティティクラスであることを示す
@Table(name="login_user_transaction")
public class LoginUserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//insert時、MySQLでauto_incrementに値を加える場合はGenerationType.IDENTITYを指定。ふつうはやらん
	@Column
	private int id;
	private String login_id;
	private String login_pass;
	private String user_name;
	
	@Column(nullable=true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int admin_flg;
	
//	@Column(nullable=true)
//	private boolean loginFlg = false;
	
	private Date insert_date;
	private Date update_date;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	public String getLogin_pass() {
		return login_pass;
	}
	public void setLogin_pass(String login_pass) {
		this.login_pass = login_pass;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	public int getAdmin_flg() {
		return admin_flg;
	}
	public void setAdmin_flg(int admin_flg) {
		this.admin_flg = admin_flg;
	}
	//	public boolean isLoginFlg() {
//		return loginFlg;
//	}
//	public void setLoginFlg(boolean loginFlg) {
//		this.loginFlg = loginFlg;
//	}
	public Date getInsert_date() {
		return insert_date;
	}
	public void setInsert_date(Date insert_date) {
		this.insert_date = insert_date;
	}
	public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	
	
}
