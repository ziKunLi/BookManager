package com.example.bookmanager.main.recycle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.bookmanager.main.recycle.book.BookListItem;
import com.example.bookmanager.main.recycle.entity.Book;
import com.example.bookmanager.main.recycle.entity.SimpleTitleItem;
import com.example.comment.router.Action;
import com.example.comment.ui.recycler.DataConverter;

import java.util.ArrayList;
import java.util.List;

public class MainFragmentDataConverter extends DataConverter {

    public MainFragmentDataConverter(String jsonData) {
        super(jsonData);
    }

    @Override
    public List convert() {
        List<Object> data = new ArrayList<>();
        JSONObject jsonObject = JSONObject.parseObject(getJsonData());
        JSONArray items = jsonObject.getJSONArray("item");
        for (int i = 0; i < items.size(); i++) {
            JSONObject item = items.getJSONObject(i);
            String type = item.getString("type");
            switch (type) {
                case "book":
                    BookListItem bookListItem = new BookListItem();
                    bookListItem.setSimpleTitleItem(parseTitle(item.getJSONObject("title")));
                    JSONArray books = item.getJSONArray("books");
                    List<Book> bookList = new ArrayList<>();
                    for (int j = 0; j < books.size(); j++) {
                        bookList.add(JSONObject.parseObject(JSON.toJSONString(books.getJSONObject(j)), Book.class));
                    }
                    bookListItem.setBooks(bookList);
                    data.add(bookListItem);
                    break;
                default:
                    break;
            }
        }
        return data;
    }

    private SimpleTitleItem parseTitle(JSONObject jsonObject) {
        SimpleTitleItem simpleTitleItem = new SimpleTitleItem();
        simpleTitleItem.setMainTitle(jsonObject.getString("mainTitle"));
        simpleTitleItem.setSubTitle(jsonObject.getString("subTitle"));
        simpleTitleItem.setButtonText(jsonObject.getString("buttonText"));
        simpleTitleItem.setButtonAction(JSONObject.parseObject(JSON.toJSONString(jsonObject.getJSONObject("action")), Action.class));
        return simpleTitleItem;
    }
}
