package com.heroherosite.Controller.Service.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.heroherosite.Controller.Service.Repository.Entity.MyPageEntity;


public interface MyPageRepository extends JpaRepository<MyPageEntity,String>{
	@Query(value = "SELECT"
			+ " ubit.id,"
			+ " iit.item_name,"
			+ " iit.item_price,"
			+ " ubit.total_count,"
			+ " ubit.pay,"
			+ " ubit.insert_date"
			+ " FROM user_buy_item_transanction ubit"
			+ " JOIN item_info_transaction iit"
			+ " ON ubit.item_transaction_id=iit.product_id"
			+ " WHERE ubit.user_master_id= :user_id"
			+ " ORDER BY ubit.insert_date DESC", nativeQuery = true) 

	List<MyPageEntity> UserBuyItemList(@Param("user_id")String userId);
	
	@Modifying
	@Transactional
	@Query(value ="DELETE FROM user_buy_item_transanction WHERE user_master_id=:user_id", nativeQuery = true) 
	public void DeleteMyBuyItemList(@Param("user_id")String userId);

}
