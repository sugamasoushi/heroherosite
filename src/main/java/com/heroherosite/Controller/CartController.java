package com.heroherosite.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.heroherosite.Controller.Service.Repository.CartInfoRepository;

@Controller
public class CartController {
	@Autowired
	HttpSession session;  
	
	@Autowired
	CartInfoRepository cartrepo;
	//カート追加
	@RequestMapping(value="/AddCart",method=RequestMethod.POST)
	public String AddCart(
			@RequestParam(value="itemdetailID")int product_id,
			@RequestParam(value="itemcount")int itemcount) {
		
		String userId;
		
		//例外処理はGlobalExceptionHandlerで行っている
		if(session.getAttribute("loginUserId")==null) {
			userId = session.getAttribute("tempUserId").toString();
		}else {
			userId = session.getAttribute("loginUserId").toString();
		}
			
		if( cartrepo.CartSelect(product_id,userId)==null) {
			cartrepo.AddCartNew(product_id,itemcount,userId);
		}else {
			cartrepo.AddCartSelect(product_id,itemcount,userId);
		}

		return  "redirect:/itemlist";
	}
	
	//カート削除
	@RequestMapping(value="/deletecart",method=RequestMethod.POST)
	public String DeleteCart(
			@RequestParam(value="checkdelete")int[] id) {
		
		String userId;
		if(session.getAttribute("loginUserId")==null) {
			userId = session.getAttribute("tempUserId").toString();
		}else {
			userId = session.getAttribute("loginUserId").toString();
		}
		
		for(int i=0;i<id.length;i++) {
			cartrepo.DeleteCart(id[i],userId);
		}
		return  "redirect:/cartinfo";
	}

}
