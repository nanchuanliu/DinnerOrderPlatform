package com.lzw.weixin.message.resp;

import java.util.List;

/**
 * Description:
 * Author: lzw
 * Date:  2017/6/23.
 */
public class ArticleMessage extends BaseMessage {

    private int articleCount;

    private List<Article> articles;

    public int getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(int articleCount) {
        this.articleCount = articleCount;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
