package com.dolo.wechat.business;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.coyote.http2.StreamException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
* 描述: 手机前端页面的消息处理Service类，主要用来处理接收到的用户上行消息和下发返回消息
* 作者: dolojia
* 修改日期: 2018/8/29 下午10:28
* E-mail: dolojia@gmail.com
**/
@Service
public class MessageService
{
    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * 
     * 功能描述：处理上行消息，入库和返回
     * 
     * @param requestXmlData
     * @return
     * @throws StreamException
     * @throws IOException
     */
    public String processRequestMessage(String requestXmlData) throws StreamException, IOException
    {
        if (StringUtils.isEmpty(requestXmlData))
        {
            logger.error("requestXmlData is empty!");
            return null;
        }
//        String responseMessage = APIUtil.getResponseMessage(resMsgUrl, requestXmlData);
        String responseMessage ="";
        return responseMessage;
    }
    
    
    /**
     * 
     * 功能描述：返回图文消息正文
     * @param id
     * @return
     * @throws IOException 
     */
    public String getArticleMessageContent(Integer id) throws IOException{
//        String content = APIUtil.getResponseMessage(articleMessageContentUrl, ""+id);
        String content ="";
        //将图片地址替换为图片服务器地址
        return content;
    }



    /**
     * 测试Map转字符串的结果 功能描述：返回格式为{"content":"color","flag":1}
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("content", "color");
        map.put("flag", 1);
        JSONObject json = JSONObject.parseObject(map.toString());
        System.out.println(json);
        System.out.println(System.currentTimeMillis());
        System.out.println(System.currentTimeMillis() / 1000);
        
        
        String s = "<p>eqeqeqwe</p><p><img src=\"http://10.140.130.43:8082/wxadmin/upload/1413188113278032138.png\" title=\"1413188113278032138.png\" alt=\"test2.png\"/></p><p>eqeqeqweqw</p><p><img src=\"http://10.140.130.43:8082/wxadmin/upload/1413188122207095322.png\" title=\"1413188122207095322.png\" alt=\"x.png\"/></p>";
        System.out.println(StringUtils.replace(s, "http://10.140.130.43:8082/wxadmin", "http://210.5.30.215/wxpic"));
    }

}
