package com.heroherosite.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.heroherosite.Controller.Service.ItemListService;
import com.heroherosite.Controller.Service.Repository.CartInfoRepository;
import com.heroherosite.Controller.Service.Repository.UserBuyItemRepository;
import com.heroherosite.Controller.Service.Repository.Entity.CartEntity;
import com.heroherosite.Controller.Service.Repository.Entity.ItemEntity;

@Controller
public class BuyItemController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	CartInfoRepository cartrepo;
	
	@Autowired
	private ItemListService service;

	//商品詳細
	@RequestMapping(value="/ItemDetails",method = RequestMethod.POST)
	public ModelAndView ItemDetails(ModelAndView mav,
			@RequestParam("itemdetailID")int product_id) {
		mav.setViewName("index");
		mav.addObject("title","商品詳細");
		mav.addObject("msg","ITEMDETAILS");
		
		ItemEntity ItemDetails =service.getItemDetails(product_id) ;

		mav.addObject("ItemDetails",ItemDetails);
		
		return  mav;
	}
	
	//購入
	@RequestMapping(value="/buyItemConfirm",method=RequestMethod.GET)
	public ModelAndView BuyItemAction(ModelAndView mav) {
		session.setAttribute("nextpage", "buyItemConfirm");

		if(session.getAttribute("loginUserId") != null) {
			mav.addObject("Login",true);
			String userId = session.getAttribute("loginUserId").toString();

			List<CartEntity>list=cartrepo.findCartByUserId(userId);
			int total = 0;
			for(CartEntity i : list) {
				total +=i.getProduct_count() * i.getItem_price();
			}
			mav.addObject("carttotalprice",total);
			mav.addObject("cartinfo",list);
		}else {
			mav.addObject("Login",false);
		}

		mav.setViewName("index");
		mav.addObject("title","決済確認画面");
		mav.addObject("msg","BUYITEMCONFIRM");
	
		return mav;
	}
	//購入
	@Autowired
	UserBuyItemRepository userrepo;

	@RequestMapping(value="/buyItemComplete",method=RequestMethod.POST)
	public ModelAndView BuyItemCompleteAction(ModelAndView mav,
			@RequestParam("pay")String pay) {

		String userId = session.getAttribute("loginUserId").toString();
		List<CartEntity>list=cartrepo.findCartByUserId(userId);
		
		for(int i=0; i<list.size(); i++) {//購入履歴へ保存
			userrepo.BuyItemAction(
					list.get(i).getProduct_id(),
					list.get(i).getItem_price(),
					list.get(i).getProduct_count(),
					userId,
					pay);
		}
		
		cartrepo.DeleteCartAll(userId);
		mav.setViewName("index");
		mav.addObject("title","決済完了");
		mav.addObject("msg","BUYITEMCOMPLETE");
		
		return mav;
	}
}
