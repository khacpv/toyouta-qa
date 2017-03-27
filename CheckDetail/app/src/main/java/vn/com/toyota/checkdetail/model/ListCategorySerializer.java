package vn.com.toyota.checkdetail.model;

import com.activeandroid.serializer.TypeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eo_cuong on 3/15/17.
 */

public class ListCategorySerializer extends TypeSerializer {

    private final static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    @Override
    public Class<?> getDeserializedType() {
        return List.class;
    }

    @Override
    public Class<?> getSerializedType() {
        return CategoyCheck.class;
    }

    @Override
    public String serialize(Object data) {
        if (null == data )
            return null;

        return gson.toJson(data);

    }

    @Override
    public List<CategoyCheck> deserialize(Object data) {
        if (null == data)
            return null;

        List<CategoyCheck> stringList = new ArrayList<CategoyCheck>();
        stringList =  gson.fromJson(data.toString(), List.class);
        return stringList;
    }
}
