package com.heroherosite;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController{
    @RequestMapping("/")
    public ModelAndView index(ModelAndView mav) {
    	
    	System.out.println("はろー");
    	mav.setViewName("index");

        return mav;
    }
}