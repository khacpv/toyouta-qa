package com.example.ngothi.checksheet.ui.model;

import com.activeandroid.serializer.TypeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eo_cuong on 3/15/17.
 */

public class ListGsonSerializer extends TypeSerializer {

    private final static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    @Override
    public Class<?> getDeserializedType() {
        return List.class;
    }

    @Override
    public Class<?> getSerializedType() {
        return String.class;
    }

    @Override
    public String serialize(Object data) {
        if (null == data )
            return null;

        return gson.toJson(data);

    }

    @Override
    public List<String> deserialize(Object data) {
        if (null == data)
            return null;

        List<String> stringList = new ArrayList<>();
        stringList =  gson.fromJson(data.toString(), List.class);
        return stringList;
    }
}
