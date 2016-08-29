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
import java.util.List;

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

    protected static JavaType getJavaType(Type type, Class<?> contextClass) {
        return (contextClass != null) ? mapper.getTypeFactory().constructType(type, contextClass)
            : mapper.constructType(type);
    }

    public static final String obj2Str(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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

    public static final <T> List<T> str2List(String s, Class<T> valueType)
        throws JsonParseException, JsonMappingException, IOException {
        @SuppressWarnings("deprecation")
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, valueType);
        return mapper.readValue(s, javaType);
    }

    public static final <T> T str2Obj(String s, Class<T> valueType)
        throws JsonParseException, JsonMappingException, IOException {
        JavaType javaType = getJavaType(valueType, null);
        return mapper.readValue(s, javaType);
    }

    public static final <T> T str2Obj(String s, TypeReference<T> valueType)
        throws JsonParseException, JsonMappingException, IOException {
        return mapper.readValue(s, valueType);
    }

    public static final void writeObj(OutputStream out, Object value)
        throws JsonGenerationException, JsonMappingException, IOException {
        mapper.writeValue(out, value);
    }
}
