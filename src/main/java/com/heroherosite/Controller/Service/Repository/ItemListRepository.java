package com.heroherosite.Controller.Service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.heroherosite.Controller.Service.Repository.Entity.ItemEntity;

@Repository
public interface ItemListRepository extends JpaRepository<ItemEntity,Integer>{

	//この辺の細かいクエリ関係はリポジトリに作る
	//	Query query = entityManager.createQuery("from ItemEntity");	
//	@SuppressWarnings("unchecked")
//	List<ItemEntity>list = query.getResultList();
//	entityManager.close();
}
