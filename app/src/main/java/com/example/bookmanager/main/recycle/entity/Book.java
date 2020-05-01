package com.example.bookmanager.main.recycle.entity;


import java.util.List;

public class Book {

    /**
     * author : ["（美）Brian W. Kernighan","（美）Dennis M. Ritchie"]
     * origin_title : The C Programming Language
     * image : https://img9.doubanio.com/view/subject/m/public/s1106934.jpg
     * id : 1139336
     * isbn10 : 7111128060
     * isbn13 : 9787111128069
     * title : C程序设计语言
     * price : 30.00元
     */

    private String origin_title;
    private String image;
    private String id;
    private String isbn10;
    private String isbn13;
    private String title;
    private String price;
    private List<String> author;

    public String getOrigin_title() {
        return origin_title;
    }

    public void setOrigin_title(String origin_title) {
        this.origin_title = origin_title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }
}
