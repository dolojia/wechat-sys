package com.dolo.wechat.business;

import java.io.IOException;

import com.dolo.wechat.common.util.LuceneUtil;
import com.dolo.wechat.propertie.AppConfigProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

    /**
     * 功能描述：根据上行消息返回下行回消息的messageId
     *
     * @return
     * @throws IOException
     */
    public String getMessageId(String content, String accountId) throws IOException {
        logger.info("******luceneIndexPath:" + AppConfigProperties.getLucenePath() + "/" + accountId);
        return LuceneUtil.queryByLucene(AppConfigProperties.getLucenePath() + "/" + accountId, content, false);
    }
}
