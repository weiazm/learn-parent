package com.hongyan.learn.web.servlet;

import com.mitchellbosecke.pebble.spring.PebbleView;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * freemarker 模板返回专用 render=json是否返回data格式
 * <p/>
 * Created by wengshengli on 16/1/11.
 */
@Slf4j
public class PebbleJsonView extends PebbleView {

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        String render = request.getParameter("render");
        if ("json".equals(render)) {
            log.debug("render json datamodel to response");
            response.setContentType("application/json;charset=UTF-8 ");
            JacksonUtil.writeObj(response.getOutputStream(), model.get("tplData"));
            return;
        }
        super.render(model, request, response);

    }

}
