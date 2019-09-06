package com.heroherosite.Controller.Service.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;

import com.heroherosite.Controller.Service.Repository.Entity.CartEntity;

public interface CartInfoRepository  extends JpaRepository<CartEntity,String>{
	@Query(value = "SELECT"
			+ " ci.id,"
			+ " ci.user_id,"
			+ " it.product_id,"
			+ " it.item_name,"
			+ " it.item_price,"
			+ " it.image_file_name,"
			+ " ci.product_count,"
			+ " ci.insert_date,"
			+ " ci.update_date"
			+ " FROM cart_info ci"
			+ " JOIN item_info_transaction it"
			+ " ON ci.product_id=it.product_id"
			+ " WHERE user_id= :user_id"
			+ " ORDER BY ci.update_date DESC,ci.insert_date DESC", nativeQuery = true) 
	// 結局SQL文を入れたほうが楽、しかしリポジトリやエンティティへの保存はSpringが自動でやっているので融通が利かない
	//必要の無い日付等のデータも引っ張らなければspringでエラーとなる
	List<CartEntity> findCartByUserId(@Param("user_id")String userId);
	//CriteriaAPIでは様々なメソッドが用意されているが結合等の操作をする場合はエンティティ同士を結ぶ必要があり、面倒
	
	@Query(value = "select * from cart_info where user_id =:user_id and product_id = :product_id", nativeQuery = true) 
	public String CartSelect(
			@Param("product_id")int product_id,
			@Param("user_id")String userId);
	
	@Modifying//updateに必要
	@Transactional//updateに必要
	@Query(value = "INSERT INTO cart_info("
			+ "user_id,product_id,product_count,insert_date,update_date"
			+ ")VALUES(:user_id,:product_id,:count,now(),now())", nativeQuery = true) 
	public void AddCartNew(
			@Param("product_id")int product_id,
			@Param("count")int count,
			@Param("user_id")String userId);
	
	@Modifying
	@Transactional
	@Query(value = "update cart_info set product_count = product_count + :count,"
			+ "update_date = now() where user_id = :user_id and product_id = :product_id", nativeQuery = true) 
	public void AddCartSelect(
			@Param("product_id")int product_id,
			@Param("count")int count,
			@Param("user_id")String userId);
	
	@Modifying
	@Transactional
	@Query(value ="DELETE FROM cart_info WHERE id=:id and user_id=:user_id", nativeQuery = true) 
	public void DeleteCart(
			@Param("id")int id,
			@Param("user_id")String userId);
	
	@Modifying
	@Transactional
	@Query(value ="DELETE FROM cart_info WHERE user_id=:user_id", nativeQuery = true) 
	public void DeleteCartAll(@Param("user_id")String userId);
	
	
}
