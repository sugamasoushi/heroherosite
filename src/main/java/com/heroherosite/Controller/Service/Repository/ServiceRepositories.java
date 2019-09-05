package com.heroherosite.Controller.Service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.heroherosite.Controller.Service.Repository.Entity.CartEntity;

@Repository
public interface ServiceRepositories extends JpaRepository<CartEntity,String>{

}
