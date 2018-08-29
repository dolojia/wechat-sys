package com.dolo.wechat.recmsg;

import com.dolo.wechat.common.xml.XStreamCDATA;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

/**
* 描述: 被动响应消息的文本消息类
* 作者: dolojia
* 修改日期: 2018/8/29 下午10:23
* E-mail: dolojia@gmail.com
**/
@XStreamAlias("xml")
public class ResArticleMessage extends ResBaseMessage
{
    private Integer ArticleCount;
    
    @XStreamCDATA
    private List<ArticleItem> Articles;

    public Integer getArticleCount()
    {
        return ArticleCount;
    }

    public void setArticleCount(Integer articleCount)
    {
        ArticleCount = articleCount;
    }

    public List<ArticleItem> getArticles()
    {
        return Articles;
    }

    public void setArticles(List<ArticleItem> articles)
    {
        Articles = articles;
    }
}
