package com.heroherosite.Controller.Service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.heroherosite.Controller.Service.Repository.Entity.ItemEntity;

@Repository
public interface ItemListRepository extends JpaRepository<ItemEntity,Integer>{

}
