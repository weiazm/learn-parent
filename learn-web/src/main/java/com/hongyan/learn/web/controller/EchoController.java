/*
 * Baijiahulian.com Inc. Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by weihongyan on 9/23/16.
 */
@Slf4j
@Controller
public class EchoController {

    @RequestMapping("/echo.json")
    @ResponseBody
    public Object echo(@RequestParam(required = false) String echoStr) {
        log.info("recieve echoStr:{}", echoStr);
        if (null == echoStr) {
            return new Xxxx("tomcat1");
        }
        return new Xxxx(echoStr);
    }

    @Data
    private static class Xxxx {
        private String echoStr;

        public Xxxx(String echoStr) {
            this.echoStr = echoStr;
        }
    }

}
