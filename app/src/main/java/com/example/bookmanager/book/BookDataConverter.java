package com.example.bookmanager.book;

import com.alibaba.fastjson.JSONObject;
import com.example.comment.ui.recycler.DataConverter;
import java.util.ArrayList;
import java.util.List;

public class BookDataConverter extends DataConverter<Book> {

    public BookDataConverter(String jsonData) {
        super(jsonData);
    }

    @Override
    public List convert() {
        List<Book> data = new ArrayList<>();
        Book book = JSONObject.parseObject(getJsonData(), Book.class);
        data.add(book);
        return data;
    }
}
