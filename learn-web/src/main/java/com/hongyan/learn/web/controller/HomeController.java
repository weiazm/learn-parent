package com.hongyan.learn.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Part;
import java.io.IOException;

/**
 * Handles requests for the application home page.
 */
@Slf4j
@Controller
public class HomeController {

    @RequestMapping(value = "/")
    public ModelAndView home() {
        return home("欢迎来到NHK!");
    }

    @RequestMapping(value = "/index.do")
    public ModelAndView home(String msg) {
        log.info("Welcome home! The client locale is {}.");
        ModelAndView view = new ModelAndView("home");
        view.addObject("msg", msg);
        return view;
    }

    @RequestMapping(value = "/upload.do")
    public ModelAndView upload(@RequestPart Part uploadFile) throws IOException {
        uploadFile.write("/Users/weihongyan/" + uploadFile.getSubmittedFileName());
        ModelAndView view = new ModelAndView("home");
        view.addObject("msg", "牛逼了:" + uploadFile.getSize());
        return view;
    }

}
