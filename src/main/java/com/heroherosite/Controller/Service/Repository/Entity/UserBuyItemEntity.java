package com.heroherosite.Controller.Service.Repository.Entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity	//エンティティクラスであることを示す
@Table(name="user_buy_item_transanction")
public class UserBuyItemEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//insert時、MySQLでauto_incrementに値を加える場合はGenerationType.IDENTITYを指定。ふつうはやらん
	@Column
	private int id;
	
	private int transaction_id;
	private int total_price;
	private int total_count;
	private String user_master_id;
	private String pay;
	private Date insert_date;
	private Date update_date;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}
	public int getTotal_price() {
		return total_price;
	}
	public void setTotal_price(int total_price) {
		this.total_price = total_price;
	}
	public int getTotal_count() {
		return total_count;
	}
	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}
	public String getUser_master_id() {
		return user_master_id;
	}
	public void setUser_master_id(String user_master_id) {
		this.user_master_id = user_master_id;
	}
	public String getPay() {
		return pay;
	}
	public void setPay(String pay) {
		this.pay = pay;
	}
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
