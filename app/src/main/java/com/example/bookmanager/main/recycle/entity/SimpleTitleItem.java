package com.example.bookmanager.main.recycle.entity;


import com.example.comment.router.Action;

public class SimpleTitleItem {

    private String mainTitle;

    private String subTitle;

    private String iconUrl;

    private String buttonText;

    private Action buttonAction;

    public SimpleTitleItem() {
    }

    public SimpleTitleItem(String mainTitle){
        this.mainTitle = mainTitle;
    }

    public SimpleTitleItem(String mainTitle, String subTitle, String iconUrl, String buttonText, Action buttonAction) {
        this.mainTitle = mainTitle;
        this.subTitle = subTitle;
        this.iconUrl = iconUrl;
        this.buttonText = buttonText;
        this.buttonAction = buttonAction;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public Action getButtonAction() {
        return buttonAction;
    }

    public void setButtonAction(Action buttonAction) {
        this.buttonAction = buttonAction;
    }
}
