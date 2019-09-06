package com.heroherosite.Controller.Service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;

import com.heroherosite.Controller.Service.Repository.Entity.UserBuyItemEntity;

public interface UserBuyItemRepository extends JpaRepository<UserBuyItemEntity,String>{
	
	@Modifying//updateに必要
	@Transactional//updateに必要
	@Query(value = "INSERT INTO user_buy_item_transanction("
			+ "item_transaction_id,"
			+ "total_price,"
			+ "total_count,"
			+ "user_master_id,"
			+ "pay,"
			+ "insert_date,update_date"
			+ ")VALUES(:transaction_id,"
			+ ":total_price,"
			+ ":total_count,"
			+ ":user_master_id,"
			+ ":pay,now(),now())", nativeQuery = true) 
	public void BuyItemAction(
			@Param("transaction_id")int transaction_id,
			@Param("total_price")int total_price,
			@Param("total_count")int total_count,
			@Param("user_master_id")String user_master_id,
			@Param("pay")String pay);

}


