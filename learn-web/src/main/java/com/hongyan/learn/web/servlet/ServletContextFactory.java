package com.hongyan.learn.web.servlet;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * Created with IntelliJ IDEA.
 * User: Victor Weng
 * Date: 16/4/12
 * Time: 下午3:25
 * To change this template use File | Settings | File Templates.
 */
public class ServletContextFactory implements FactoryBean<ServletContext>, ServletContextAware {

    private ServletContext servletContext;

    @Override
    public ServletContext getObject() throws Exception {
        return this.servletContext;
    }

    @Override
    public Class<?> getObjectType() {
        return ServletContext.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

}
