package com.example.comment.router;

import java.util.HashMap;

public class Action {

    private String action;
    private DataBean data;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private String url;
        private String extInfoApi;
        private HashMap<String, Object> extInfo;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getExtInfoApi() {
            return extInfoApi;
        }

        public void setExtInfoApi(String extInfoApi) {
            this.extInfoApi = extInfoApi;
        }

        public HashMap<String, Object> getExtInfo() {
            return extInfo;
        }

        public void setExtInfo(HashMap<String, Object> extInfo) {
            this.extInfo = extInfo;
        }

    }
}
