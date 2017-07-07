package com.lzw.order.dinnerorderapp.Bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/7/6.
 */

public class HotSearchWord {
    @SerializedName("is_highlight")
    private String is_highlight;

    @SerializedName("link")
    private String link;

    public String getIs_highlight() {
        return is_highlight;
    }

    public void setIs_highlight(String is_highlight) {
        this.is_highlight = is_highlight;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSearch_word() {
        return search_word;
    }

    public void setSearch_word(String search_word) {
        this.search_word = search_word;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @SerializedName("search_word")
    private String search_word;

    @SerializedName("source")
    private String source;

    @SerializedName("type")
    private String type;

    @SerializedName("url")
    private String url;

    @SerializedName("word")
    private String word;
}
