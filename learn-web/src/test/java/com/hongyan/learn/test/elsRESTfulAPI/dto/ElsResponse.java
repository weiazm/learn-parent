package com.hongyan.learn.test.elsRESTfulAPI.dto;

/**
 * @author weihongyan
 * @description TODO
 * @date 14/02/2017
 */

import lombok.Data;

@Data
public class ElsResponse {
    @Data
    public static class Shards {
        private Integer total;
        private Integer successful;
        private Integer failed;
    }

    private String _index;
    private String _type;
    private String _id;
    private Integer _version;
    private String result;
    private Shards _shards;
    private Boolean created;
}
