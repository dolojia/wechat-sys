package com.dolo.wechat.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.InputSource;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class CommonUtil
{
	private static final Logger logger = LogManager.getLogger(CommonUtil.class);

	private static final SAXReader saxReader = new SAXReader();
    /**
     * 
     * 功能描述：从Request对象中获取请求的 xml数据
     * 
     * @param request
     * @return
     * @throws IOException
     */
    public static String getRequestXmlData(HttpServletRequest request) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "UTF-8"));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null)
        {
            sb.append(line);
        }
        String recievestr = sb.toString();
        return recievestr;
    }


    /**
     * 
     * 功能描述：从biz中根据上行消息返回应答消息，找不到时也要返回
     * 
     * @param keyword
     * @return
     */
    public static String getResponseFromBiz(String keyword)
    {
        return null;
    }
    

	/**根据节点返回内容
	 * @param requestXmlMsg 报文
	 * @param nodeTag 报文节点
	 * @return
	 */
	public static String getSigleNode(String requestXmlMsg,String nodeTag)  {
		String cownid = "";
		StringReader read = new StringReader(requestXmlMsg);
		InputSource source = new InputSource(read);
		try {
			Document doc = saxReader.read(source);
			Node node = doc.selectSingleNode(nodeTag);
			if (node != null) {
				cownid = node.getText();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getSigleNode Method", e);
		}
		return cownid;
	}
	
	/** 根据节点返回下面的子标签集合
	 * @param requestXmlMsg 报文
	 * @param nodeTag 节点
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Node> getNodeList(String requestXmlMsg,String nodeTag){
		List<Node> list = new ArrayList<Node>();
		StringReader read = new StringReader(requestXmlMsg);
		InputSource source = new InputSource(read);
		Document doc;
		try {
			doc = saxReader.read(source);
			list = doc.selectNodes(nodeTag);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			logger.error("error in getNodeList Method", e);
		}
		
		return list;
		
	}
	

}
