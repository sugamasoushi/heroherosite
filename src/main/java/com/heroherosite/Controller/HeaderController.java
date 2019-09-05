package com.heroherosite.Controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class HeaderController {
	@Autowired
	HttpSession session;  


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

	
	//カート表示

	
	//マイページ

    
	//ログインページ
    @RequestMapping(value="/login",method = RequestMethod.GET)
    public ModelAndView LoginPage(){
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("index");
		mav.addObject("title","LoginPage");
		mav.addObject("msg","LOGIN");
		session.setAttribute("nextpage", "login");
		
		//////////////////////
        return  mav;
     }
	
}
