package com.hongyan.learn.web.servlet;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author @author@ (@author-email@)
 * @version @version@, $Date: 2011-3-18$
 */
public abstract class JacksonUtil {
    // can reuse, share globally
    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static final String obj2Str(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final void writeObj(OutputStream out, Object value)
        throws JsonGenerationException, JsonMappingException, IOException {
        mapper.writeValue(out, value);
    }

    public static final <T> T str2Obj(String s, Class<T> valueType)
        throws JsonParseException, JsonMappingException, IOException {
        JavaType javaType = getJavaType(valueType, null);
        return mapper.readValue(s, javaType);
    }

    protected static JavaType getJavaType(Type type, Class<?> contextClass) {
        return (contextClass != null) ? mapper.getTypeFactory().constructType(type, contextClass)
            : mapper.constructType(type);
    }

    public static final <T> T str2Obj(String s, TypeReference<T> valueType)
        throws JsonParseException, JsonMappingException, IOException {
        return mapper.readValue(s, valueType);
    }

    public static final <T> List<T> str2List(String s, Class<T> valueType)
        throws JsonParseException, JsonMappingException, IOException {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, valueType);
        return mapper.readValue(s, javaType);
    }

    public static final <T> T readObj(InputStream in, Class<T> valueType)
        throws JsonParseException, JsonMappingException, IOException {
        return mapper.readValue(in, valueType);
    }

    @SuppressWarnings("unchecked")
    public static final <T> T readObj(InputStream in, JavaType valueType)
        throws JsonParseException, JsonMappingException, IOException {
        return (T) mapper.readValue(in, valueType);
    }

    public static void main(String[] args) throws Exception {
        String resultJson =
            "{\"code\":1,\"msg\":\"\\u63d0\\u73b0\\u5931\\u8d25!\",\"data\":[],\"ts\":1468325338,\"rid\":\"6d6b74a4692b18a028aa5c6b8cdc212d\"}";
        RestfulResult restfulResult = JacksonUtil.str2Obj(resultJson, RestfulResult.class);
        int i = 9;

        Map<String, String> userPwd = new HashMap<>();
        userPwd.put("orgId", 123 + "");
        userPwd.put("pwd", "12312");

        System.out.println(obj2Str(userPwd));
    }

}
