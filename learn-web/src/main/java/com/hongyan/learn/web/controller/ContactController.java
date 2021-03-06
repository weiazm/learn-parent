/**
 * Baijiahulian.com Inc. Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.web.controller;

import com.hongyan.learn.sal.dto.ContactDto;
import com.hongyan.learn.sal.service.ContactService;
import com.hongyan.learn.web.dto.ErrorDetail;
import com.hongyan.learn.web.dto.ErrorDetail.ErrorCode;
import com.hongyan.learn.web.dto.ResponseStatus;
import com.hongyan.learn.web.dto.WebResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

/**
 * @title ContactController
 * @desc 1+1=2
 * @author hongyan
 * @date Aug 27, 2016
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @RequestMapping("/getByName.json")
    @ResponseBody
    public WebResponse<ContactDto> getContactByName(String contactName) {
        log.info("name={}", contactName);
        WebResponse<ContactDto> resp = new WebResponse<ContactDto>();
        try {
            ContactDto result = contactService.getContactDtoByName(contactName);
            resp.setStatus(ResponseStatus.ok);
            resp.setData(result);
            return resp;
        } catch (Exception e) {
            log.info(e.getMessage());
            resp.setError(new ErrorDetail(ErrorCode.system_error, e.getMessage()));
            return resp;
        }
    }

}
