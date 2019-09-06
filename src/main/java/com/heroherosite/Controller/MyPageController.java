package com.heroherosite.Controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.heroherosite.Controller.Service.Repository.MyPageRepository;


@Controller
public class MyPageController {
	@Autowired
	HttpSession session;
	
	@Autowired
	MyPageRepository mypagerepo;
	
	//マイページ
		@RequestMapping(value="/myPageDelete",method= RequestMethod.GET )
		public String MyPageDeleteAction() {

			mypagerepo.DeleteMyBuyItemList(session.getAttribute("loginUserId").toString());

			return "redirect:/myPage";
		}

}
