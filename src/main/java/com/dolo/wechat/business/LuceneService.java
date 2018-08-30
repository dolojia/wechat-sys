package com.dolo.wechat.business;

import java.io.IOException;

import com.dolo.wechat.common.util.LuceneUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 描述: 关键词匹配服务，从指定文件夹中查找匹配到的词条索引，索引即数据库中回消息表的id主键
 * 作者: dolojia
 * 修改日期: 2018/8/30 17:09
 * E-mail: dolojia@gmail.com
 **/
@Service
public class LuceneService {
    protected final Log logger = LogFactory.getLog(getClass());

    @Value("${lucene.index.path}")
    private String luceneIndexPath;

    /**
     * 功能描述：根据上行消息返回下行回消息的messageId
     *
     * @param message
     * @return
     * @throws IOException
     */
    public String getMessageId(String content, String accountId) throws IOException {
        logger.info("******luceneIndexPath:" + luceneIndexPath + "/" + accountId);
        return LuceneUtil.queryByLucene(luceneIndexPath + "/" + accountId, content, false);
    }
}
