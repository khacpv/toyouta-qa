package vn.com.toyota.checkdetail.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

/**
 * Created by eo_cuong on 3/16/17.
 */

public class GsonUtils {

    public static String Object2String(Object obj) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(obj);
    }

    public static <T> T String2Object(String json, Class<T> clzz) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.fromJson(json, clzz);
    }

    public static <T> List<T> String2ListObject(String json, Class<T[]> clazz) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        T[] t = gson.fromJson(json, clazz);
        return Arrays.asList(t);
    }
}
