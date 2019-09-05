package com.heroherosite;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController{
    @RequestMapping("/test")
    public ModelAndView index(ModelAndView mav) {
    	
    	mav.setViewName("index");

        return mav;
    }
}

/*
 * Contoroller内のメソッドはそのままHTMLに出力出来ない
 * メソッドと文字列を返す以外の処理が無いから
 * 
 * そのため、必要となるのがテンプレートエンジンとなる
 * テンプレートエンジンはHTML出力を可能にしてくれるもので、
 * サーバサイドプログラムと連携してwebページを構築する
 * 逆にこれ無しではHTMLは表示できない
 * 
 * 主に使用するのは「thymeleaf」
 */