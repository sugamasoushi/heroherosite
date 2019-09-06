package com.heroherosite.Controller.Service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;

import com.heroherosite.Controller.Service.Repository.Entity.LoginUserEntity;

public interface LoginRepository  extends JpaRepository<LoginUserEntity,String>{
	
	@Query(value = "SELECT * FROM login_user_transaction where login_id=:loginUserId AND login_pass=:loginPassword", nativeQuery = true) 
	public String UserIDCheck(
			@Param("loginUserId")String loginid,
			@Param("loginPassword")String loginpass);
	
	
	@Modifying//updateに必要
	@Transactional//updateに必要
	@Query(value = "INSERT INTO login_user_transaction("
			+ "login_id,login_pass,user_name,admin_flg,insert_date,update_date"
			+ ")VALUES(:login_id,:login_pass,:user_name,0,now(),now())", nativeQuery = true) 
	public void UserCreate(
			@Param("login_id")String login_id,
			@Param("login_pass")String login_pass,
			@Param("user_name")String user_name);
	
	@Query(value = "SELECT * FROM login_user_transaction where login_id=:loginUserId AND login_pass=:loginPassword", nativeQuery = true) 
	LoginUserEntity UserLoginEntity(
			@Param("loginUserId")String loginid,
			@Param("loginPassword")String loginpass);

	@Modifying
	@Transactional
	@Query(value ="update cart_info set user_id = :user_id, update_date = now() where user_id = :tempUserId", nativeQuery = true) 
	public void LoginIdChangeCart(
			@Param("user_id")String loginid,
			@Param("tempUserId")String TempUserId);
}
