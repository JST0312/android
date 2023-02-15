package com.blockent.tabbar.model;

import java.util.List;

public class PostingList {

    private String result;
    private List<Posting> items;
    private int count;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Posting> getItems() {
        return items;
    }

    public void setItems(List<Posting> items) {
        this.items = items;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
