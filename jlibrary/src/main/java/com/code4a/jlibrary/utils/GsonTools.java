package com.code4a.jlibrary.utils;

/**
 * gson解析工具类
 */
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GsonTools {
    private static final String TAG = GsonTools.class.getName();

    /**
     * 描述 将json转换为一个对象
     * @param json
     *            待解析的json数据
     * @param cls
     *            对象
     * @return
     */
    public static <T> T getBean(String json, Class<T> cls) {
        T t = null;
        Gson gson = new Gson();
        try {
            t = gson.fromJson(json, cls);
        } catch (Exception e) {
            Log.e(TAG, "gson解析出错" + e.getMessage());
            return null;
        }

        return t;
    }

    /**
     * 描述 将json转换为一个list对象
     * @param jsonString
     *            带解析的json数据
     * @param cls
     *            对象
     * @return
     */
    public static <T> List<T> getListWithObject(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<List<T>>() {
            }.getType());

        } catch (Exception e) {
            Log.e(TAG, "gson解析出错" + e.getMessage());
            return null;
        }
        return list;
    }

    /**
     * 将json转换为一个list的字符串
     * @param jsonString
     *            带解析的字符串
     * @return
     */
    public static List<String> getList(String jsonString) {
        List<String> list = new ArrayList<String>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<List<String>>() {
            }.getType());
        } catch (Exception e) {
            Log.e(TAG, "gson解析出错" + e.getMessage());
            return null;
        }
        return list;

    }

    /**
     * 将json转换为一个list《map《String,onject》的集合
     * @param jsonString
     *            待解析的json数据
     * @return
     */
    public static List<Map<String, Object>> listKeyMaps(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<List<Map<String, Object>>>() {
            }.getType());
        } catch (Exception e) {
            Log.e(TAG, "gson解析出错" + e.getMessage());
            return null;
        }
        return list;
    }

    public static String GsonToString(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);

    }

    public static <T> String listToJson(List<T> list) {
        if (list == null || list.size() == 0) {
            return "";
        }
        Gson gson = new Gson();
        try {
            return gson.toJson(list);
        } catch (Exception e) {
            Log.e(TAG, "gson解析出错" + e.getMessage());
            return null;
        }

    }
}
