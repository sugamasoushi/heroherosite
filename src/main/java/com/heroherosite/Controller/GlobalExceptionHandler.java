package com.heroherosite.Controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	//@org.springframework.web.bind.annotation.ExceptionHandler({ Exception.class })
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView HandleException(Exception e) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("sessionError");
		mav.addObject("message","セッションタイムアウト");

        return mav;
    }

}
