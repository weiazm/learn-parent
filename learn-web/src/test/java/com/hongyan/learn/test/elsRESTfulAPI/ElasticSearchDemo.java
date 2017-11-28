package com.hongyan.learn.test.elsRESTfulAPI;

import com.google.common.collect.Lists;
import com.hongyan.learn.test.elsRESTfulAPI.dto.Employee;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

/**
 * @author weihongyan
 * @description TODO
 * @date 14/02/2017
 */
public class ElasticSearchDemo {
    private static final String ELASTIC_SEARCH_URL = "http://localhost:9200";
    private static RestTemplate restTemplate = new RestTemplate();

    @Test
    public void indexData() {
        Employee hongyanEmployee = new Employee();
        hongyanEmployee.setFirstName("hongyan");
        hongyanEmployee.setLastName("wei");
        hongyanEmployee.setAge(23);
        hongyanEmployee.setAbout("what's this fuck?");
        hongyanEmployee.setInterests(Lists.newArrayList("eat", "drink", "shit", "pee"));
        Employee hongyanEmployee2 = new Employee();
        hongyanEmployee2.setFirstName("hongyan2");
        hongyanEmployee2.setLastName("wei2");
        hongyanEmployee2.setAge(23);
        hongyanEmployee2.setAbout("what's this fuck?2");
        hongyanEmployee2.setInterests(Lists.newArrayList("eat2", "drink2", "shit2", "pee2"));
        Employee hongyanEmployee3 = new Employee();
        hongyanEmployee3.setFirstName("hongyan3");
        hongyanEmployee3.setLastName("wei3");
        hongyanEmployee3.setAge(23);
        hongyanEmployee3.setAbout("what's this fuck?3");
        hongyanEmployee3.setInterests(Lists.newArrayList("eat3", "drink3", "shit3", "pee3"));

        String resp = restTemplate
            .postForEntity(ELASTIC_SEARCH_URL + "/hongyan/employee/1", hongyanEmployee, String.class).getBody();
        System.out.println(resp);
        resp = restTemplate.postForEntity(ELASTIC_SEARCH_URL + "/hongyan/employee/2", hongyanEmployee2, String.class)
            .getBody();
        System.out.println(resp);
        resp = restTemplate.postForEntity(ELASTIC_SEARCH_URL + "/hongyan/employee/3", hongyanEmployee3, String.class)
            .getBody();
        System.out.println(resp);

    }

    @Test
    public void selectExectly() {
        String resp = restTemplate.getForEntity(ELASTIC_SEARCH_URL + "/hongyan/employee/1", String.class).getBody();
        System.out.println(resp);
        resp = restTemplate.getForEntity(ELASTIC_SEARCH_URL + "/hongyan/employee/2", String.class).getBody();
        System.out.println(resp);
        resp = restTemplate.getForEntity(ELASTIC_SEARCH_URL + "/hongyan/employee/3", String.class).getBody();
        System.out.println(resp);
    }

    @Test
    public void searchAll() {
        String resp =
            restTemplate.getForEntity(ELASTIC_SEARCH_URL + "/hongyan/employee/_search", String.class).getBody();
        System.out.println(resp);
    }

    @Test
    public void searchByQueryString() {
        String resp = restTemplate
            .getForEntity(ELASTIC_SEARCH_URL + "/hongyan/employee/_search?q=firstName:hongyan", String.class).getBody();
        System.out.println(resp);
    }

    @Test
    public void searchByDSL() {
        String resp = restTemplate.postForEntity(ELASTIC_SEARCH_URL + "/hongyan/employee/_search",
            "{\"query\": {\"match\": {\"firstName\": \"hongyan2\"}}}", String.class).getBody();
        System.out.println(resp);
    }

    @Test
    public void searchByDSL2() {
        String resp = restTemplate.postForEntity(ELASTIC_SEARCH_URL + "/hongyan/employee/_search",
            "{\"query\": {\"match\": {\"about\": \"your fuck\"}}}", String.class).getBody();
        System.out.println(resp);
    }

}
