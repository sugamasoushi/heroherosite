package com.heroherosite.Controller.Service.Repository.Entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity	//エンティティクラスであることを示す
@Table(name="item_info_transaction")
public class MyPageEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//insert時、MySQLでauto_incrementに値を加える場合はGenerationType.IDENTITYを指定。ふつうはやらん
	@Column
	private int id;
	
	private String item_name;
	private int item_price;
	private int total_count;
	private String pay;
	private Date insert_date;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	
	public int getItem_price() {
		return item_price;
	}
	public void setItem_price(int item_price) {
		this.item_price = item_price;
	}
	public int getTotal_count() {
		return total_count;
	}
	public void setTotal_count(int total_count) {
		this.total_count = total_count;
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
	
	

}
