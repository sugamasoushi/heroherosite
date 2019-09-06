package com.heroherosite.Controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.heroherosite.Controller.Service.Repository.LoginRepository;
import com.heroherosite.Controller.Service.Repository.Entity.LoginUserEntity;

@Controller
public class LoginController {
	@Autowired
	HttpSession session;
    
    @Autowired
 	private LoginRepository Logrepo;
    
    @RequestMapping(value="/loginAction",method = {RequestMethod.GET,RequestMethod.POST})
    public String LoginCheck(ModelAndView mav,
    		@RequestParam(name="loginUserId", required = false)String loginid,
     		@RequestParam(name="loginPassword", required = false)String loginpass) {

    	if( loginid != null && loginpass != null) {		
    		if( !loginid.equals("") && !loginpass.equals("") ) {
    			if(Logrepo.UserIDCheck(loginid,loginpass)==null) {	
	 	    	}else {
	 	    		LoginUserEntity logent = new LoginUserEntity();    		
	 	    		logent = Logrepo.UserLoginEntity(loginid, loginpass); 		
	 	    		session.setAttribute("LoginUserEntity", logent );
	 	    		
	 	    		session.setAttribute("loginUserId", loginid);
	 	    		Logrepo.LoginIdChangeCart(loginid,session.getAttribute("tempUserId").toString());
	 	    		
	 	    		session.removeAttribute("loginmsg");
	 	    		if(session.getAttribute("nextpage").equals("login")) {
	 	    			return  "redirect:/";
	 	    		}
	 	    	}
	     	}else {
	     		session.setAttribute("loginmsg", "空白はダメ;");
	     		return "redirect:/loginAction";
	     	}   	
 		}
        return  "redirect:/" + session.getAttribute("nextpage");
    }

    @RequestMapping(value="/usercreate",method = RequestMethod.GET)
    public ModelAndView UserCreate(ModelAndView mav) {
    	mav.setViewName("index");
		mav.addObject("title","新規登録");
		mav.addObject("msg","USERCREATE");
        return  mav;
    }
    
    @RequestMapping(value="/usercreateAction",method = RequestMethod.POST)
    public ModelAndView UserCreateAction(ModelAndView mav,
    		@RequestParam("login_id")String login_id,
    		@RequestParam("login_pass")String login_pass,
    		@RequestParam("user_name")String user_name) {
    	mav.setViewName("index");
		mav.addObject("title","新規登録");
		mav.addObject("msg","USERCREATE");
		
		if( !login_id.equals("") && !login_pass.equals("") && !user_name.equals("")) {
			if( Logrepo.UserIDCheck(login_id,login_pass)!=null ) {//sqlの結果は空であっても文字は存在しないnullなのでequalsはやめておく
				mav.addObject("message","登録済み");
			}else {
				session.setAttribute("newlogin_id", login_id);
				session.setAttribute("newlogin_pass", login_pass);
				session.setAttribute("newuser_name", user_name);
				mav.setViewName("index");
				mav.addObject("title","登録確認");
				mav.addObject("msg","USERCREATECOMFIRM");
			}	
		}else {
			mav.addObject("message","空白はダメ");
		}
        return  mav;
    }
    
    @RequestMapping(value="/userCreateCompleteAction",method = RequestMethod.GET)
    public ModelAndView UserCreateCompleteAction(ModelAndView mav) {
    	
		Logrepo.UserCreate(
				session.getAttribute("newlogin_id").toString(), 
				session.getAttribute("newlogin_pass").toString(), 
				session.getAttribute("newuser_name").toString());
		
			//成功したらセッション内容を消す
			session.removeAttribute("newlogin_id");
			session.removeAttribute("newlogin_pass");
			session.removeAttribute("newuser_name");
		
		mav.setViewName("index");
		mav.addObject("title","登録完了");
		mav.addObject("msg","USERCREATECOMPLETE");
		
        return  mav;
    }
    
    @RequestMapping(value="/logout",method = RequestMethod.GET)
    public String Logout() {
    	
    	session.invalidate();

    	return "redirect:/";

    }
}
