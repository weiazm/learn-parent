package com.hongyan.learn.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

/**
 * Handles requests for the application home page.
 */
@Slf4j
@Controller
public class HomeController {
    
    @RequestMapping(value = "/")
    public ModelAndView home(){
        return home("欢迎来到OST!");
    }

    @RequestMapping(value = "/index.do")
    public ModelAndView home(String msg) {
        log.info("Welcome home! The client locale is {}.");
        ModelAndView view = new ModelAndView("home");
        view.addObject("msg", msg);
        return view;
    }
    
}
