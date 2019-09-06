package com.heroherosite.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.heroherosite.Controller.Service.ItemListService;
import com.heroherosite.Controller.Service.Repository.Entity.CartEntity;
import com.heroherosite.Controller.Service.Repository.Entity.ItemEntity;
import com.heroherosite.Controller.Service.Repository.Entity.MyPageEntity;
import com.heroherosite.Controller.Service.Repository.CartInfoRepository;
import com.heroherosite.Controller.Service.Repository.MyPageRepository;
//import com.heroherosite.Controller.Service.ItemList;
//import com.heroherosite.Controller.Service.Repository.Entity.*;

@Controller
public class HeaderController {
	@Autowired
	HttpSession session;  
	
	@Autowired
	CartInfoRepository cartrepo;
	
	@Autowired
	MyPageRepository mypagerepo;


	@RequestMapping(value="/",method=RequestMethod.GET)
	public ModelAndView index(ModelAndView mav) {
		if( session.getAttribute("tempUserId")==null && session.getAttribute("loginUserId")==null  ) {
    		String value="";
    		for(int i=0 ; i<16 ; i++){
    			double d = Math.random() * 10;
    			value = value + (int)d;
    		}
    		session.setAttribute("tempUserId", value.toString());//仮ユーザーID
    	}

		mav.setViewName("index");
		mav.addObject("title","TopPage");
		mav.addObject("msg","HOME");
		session.setAttribute("nextpage", "toppage");
		
		return  mav;
	}
	
	//商品一覧
	@Autowired
	private ItemListService service;
	@RequestMapping(value="/itemlist",method=RequestMethod.GET)
	public ModelAndView ItemList(ModelAndView mav) {
		mav.setViewName("/index");
		mav.addObject("title","商品一覧");
		mav.addObject("msg","ITEMLIST");

		Iterable<ItemEntity>list =service.getAll();
				
		mav.addObject("itemlist",list);
				
		return  mav;
	}

	
	//カート表示
	@RequestMapping(value="/cartinfo",method= {RequestMethod.GET ,RequestMethod.POST})
	public ModelAndView CartInfo(ModelAndView mav) {
		mav.setViewName("index");
		mav.addObject("title","カート情報");
		mav.addObject("msg","CARTINFO");
				
		String userId;
		//例外処理はGlobalExceptionHandlerで行っている
		if(session.getAttribute("loginUserId")==null) {
			userId = session.getAttribute("tempUserId").toString();
		}else {
			userId = session.getAttribute("loginUserId").toString();
		}
					
		if(cartrepo.findCartByUserId(userId).size() < 1) {
			mav.addObject("cartzero",false);
		}else {
			mav.addObject("cartzero",true);
			List<CartEntity>list=cartrepo.findCartByUserId(userId);
			int total = 0;
			for(CartEntity i : list) {
				total +=i.getProduct_count() * i.getItem_price();
			}
			mav.addObject("carttotalprice",total);
			mav.addObject("cartinfo",list);
		}
		return  mav;
	}

	
	//マイページ
	@RequestMapping(value="/myPage",method= RequestMethod.GET )
	public ModelAndView MyPage(ModelAndView mav) {
		mav.setViewName("/index");
		mav.addObject("title","マイページ");
		mav.addObject("msg","MYPAGE");
		session.setAttribute("nextpage", "myPage");
		
		if(session.getAttribute("loginUserId")==null) {
			mav.addObject("Login",false);
			session.setAttribute("thistitle", "mypage");
			session.setAttribute("thispage", "MYPAGE");
		}else {
			mav.addObject("Login",true);
			
			mav.addObject("nakami",false);
			List<MyPageEntity>list = mypagerepo.UserBuyItemList(session.getAttribute("loginUserId").toString());		
			if(list.isEmpty()) {
				mav.addObject("nakami",false);
			}
			mav.addObject("mypagelist",list);
		}
		return  mav;
	}
	
    
	//ログインページ
    @RequestMapping(value="/login",method = RequestMethod.GET)
    public ModelAndView LoginPage(){
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("index");
		mav.addObject("title","LoginPage");
		mav.addObject("msg","LOGIN");
		session.setAttribute("nextpage", "login");
		
        return  mav;
     }
    
	//その他
	@RequestMapping(value="/sonota",method = RequestMethod.GET)
    public ModelAndView SonotaPage(){
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("index");
		mav.addObject("title","Sonota");
		mav.addObject("msg","SONOTA");
		session.setAttribute("nextpage", "login");
		
		//////////////////////
        return  mav;
     }
	
}
