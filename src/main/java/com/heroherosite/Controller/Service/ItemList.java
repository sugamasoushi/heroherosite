package com.heroherosite.Controller.Service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heroherosite.Controller.Service.Repository.ItemListRepository;
import com.heroherosite.Controller.Service.Repository.Entity.ItemEntity;

@Service
public class ItemList implements DataCenter<ItemEntity>{
	private static final long serialVersionUID = 1L;
	
	public ItemList() {
		super();
	}
	

	@Autowired
	ItemListRepository itemRepo;
	
	@Override
	public List<ItemEntity>getAll(){
	//	Query query = entityManager.createQuery("from ItemEntity");
		
//		@SuppressWarnings("unchecked")
//		List<ItemEntity>list = query.getResultList();
//		entityManager.close();
		return itemRepo.findAll();
	}

}
