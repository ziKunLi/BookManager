package com.example.comment.ui.recycler;


import com.example.comment.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author NewBies
 * @date 2018/9/16
 */

public abstract class DataConverter<T> {

    private final List<T> data = new ArrayList<>();
    private String jsonData = null;

    public DataConverter(String jsonData) {
        this.jsonData = jsonData;
    }

    public abstract List convert();

    public DataConverter setJsonData(String json) {
        this.jsonData = json;
        return this;
    }

    protected String getJsonData() {
        if (StringUtil.isEmpty(this.jsonData)) {
            throw new NullPointerException("json 数据为空");
        }
        return this.jsonData;
    }

    public List<T> getData() {
        return data;
    }
}
