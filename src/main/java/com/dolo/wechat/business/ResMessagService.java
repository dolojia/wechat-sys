//package com.dolo.wechat.business;
//
//import com.alibaba.fastjson.JSONArray;
//import com.dolojia.wxadmin.bo.MenuMessageVo;
//import com.dolojia.wxadmin.bo.ResMessage;
//import com.dolojia.wxadmin.common.MessageTypeEnum;
//import com.dolojia.wxadmin.common.Operator;
//import com.dolojia.wxadmin.dao.ArticleMessageItemDao;
//import com.dolojia.wxadmin.dao.ResMessageDao;
//import com.dolojia.wxadmin.exception.BusinessException;
//import com.dolojia.wxadmin.utils.LuceneUtil;
//import com.dolojia.wxadmin.utils.Util;
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang.math.NumberUtils;
//import org.apache.commons.lang.time.DateUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.text.ParseException;
//import java.util.*;
//
//@Service
//public class ResMessagService
//{
//
//    protected final Log logger = LogFactory.getLog(getClass());
//
//    @Autowired
//    private ResMessageDao resMessageDao;
//
//    @Autowired
//    private ArticleMessageItemDao articleMessageItemDao;
//
//    @Autowired
//    private AdminUserService adminUserService;
//
//    @Value("${lucene.index.path}")
//    private String luceneIndexPath;
//
//
//    /**
//     *
//     * 功能描述：查询消息列表
//     *
//     * @param pageNumber
//     * @param pageSize
//     * @return
//     */
//    public Map<String, Object> getResMessageList(String userName,Integer pageNumber, Integer pageSize, String status, String msgName,
//    		String beginTime, String endTime, String keyWords, String msgType,String resType,String accountId)
//    {
//        HashMap<String, String> params = new HashMap<String, String>();
//        if (!StringUtils.isEmpty(status))
//        {
//            params.put("status", status);
//        }
//        if (!StringUtils.isEmpty(msgName))
//        {
//            params.put("msgName", msgName);
//        }
//        if (!StringUtils.isEmpty(beginTime))
//        {
//            params.put("beginTime", beginTime);
//        }
//        if (!StringUtils.isEmpty(endTime))
//        {
//            try
//            {
//                endTime = Util.getStringByDate(DateUtils.addDays(DateUtils.parseDate(endTime, new String[]{"yyyy-MM-dd"}), 1));
//            }
//            catch (ParseException e)
//            {
//                logger.error("request on url[load_res_message.json] parseDateExcp", e);
//            }
//            params.put("endTime", endTime);
//        }
//        if (!StringUtils.isEmpty(keyWords))
//        {
//            params.put("keyWords", keyWords);
//        }
//
//        if (!StringUtils.isEmpty(msgType))
//        {
//            params.put("msgType", msgType);
//        }
//        if (!StringUtils.isEmpty(resType))
//        {
//            params.put("resType", resType);
//        }
//        params.put("accountId", accountId);
//        List<ResMessage> resMessageList = this.resMessageDao.getResMessageList(params);
//        resMessageList = this.getProcessedListByRole(userName,resMessageList);
//        Integer fromRecord = (pageNumber - 1) * pageSize;
//        Integer endRecord = resMessageList.size() < fromRecord + pageSize ? resMessageList.size() : fromRecord + pageSize;
//        Map<String, Object> map = new TreeMap<String, Object>();
//        map.put("rows", resMessageList == null ? null : resMessageList.subList(fromRecord, endRecord));
//        map.put("total", resMessageList == null ? 0 : resMessageList.size());
//        return map;
//    }
//
//
//    /**
//     *
//     * 功能描述：根据用户角色显示不同的列表数据
//     * @param userName
//     * @param resMessageList
//     * @return
//     */
//    private List<ResMessage> getProcessedListByRole(String userName,List<ResMessage> resMessageList){
//        List<ResMessage> processedList = new ArrayList<ResMessage>();
//       String role = this.adminUserService.getUserRoleByName(userName);
//        if(role.equalsIgnoreCase("admin")){
//            //admin用户能看到所有的消息
//            processedList = resMessageList;
//        }else if(role.equalsIgnoreCase("assessor")){
//            for(ResMessage resMessage : resMessageList){
//                //审核员不能审核admin创建的
//                if(StringUtils.isNotEmpty(resMessage.getCreator()) && resMessage.getCreator().equalsIgnoreCase("admin")){
//                    continue;
//                }
//                //审核员只能看到发布员已提交的消息
//                if(resMessage.getStatus().equalsIgnoreCase("submit")){
//                    processedList.add(resMessage);
//                }else{
//                    continue;
//                }
//            }
//        }else if(role.equalsIgnoreCase("employee")){
//            //发布员只能看到自己创建的信息
//            for(ResMessage resMessage : resMessageList){
//                if(StringUtils.isNotEmpty(resMessage.getCreator()) && resMessage.getCreator().equalsIgnoreCase(userName)){
//                    processedList.add(resMessage);
//                }
//            }
//        }
//        return processedList;
//    }
//
//
//    /**
//     *
//     * 功能描述：修改消息状态
//     *
//     * @param id
//     * @param status
//     * @return
//     */
//    public String updateMessageStatusById(String id, String status)
//    {
//        ResMessage resMessage = new ResMessage();
//        resMessage.setId(Long.parseLong(id));
//        resMessage.setStatus(status);
//        this.resMessageDao.updateResMessage(resMessage);
//        return status;
//    }
//
//
//    /**
//     *
//     * 功能描述：删除消息，注意文本消息需要关联删除
//     *
//     * @param id
//     * @param msgType
//     * @throws IOException
//     */
//    @Operator(operator="删除消息")
//    public void deleteMessageById(String id, String msgType) throws IOException
//    {
//        ResMessage resMessage = this.resMessageDao.getResMessageById(id);
//        this.resMessageDao.deleteResMessageById(id);
//        if (msgType.equalsIgnoreCase(MessageTypeEnum.NEWS.name()))
//        {
//            this.articleMessageItemDao.deleteByArticleMsgId(NumberUtils.toLong(id));
//        }
//        List<ResMessage> resMessageList = this.resMessageDao.getResAllMessage();
//        // 清除Lucene文件夹中的内容，然后重新生成搜索内容
//        LuceneUtil.generatorLuceneIndex(resMessage.getAccountId(),luceneIndexPath,resMessageList);
//
//    }
//
//
//    /**
//     *
//     * 功能描述：检查消息名称和关键字是否重复
//     *
//     * @param msgName
//     * @param keyWords
//     * @throws BusinessException
//     */
//    public void checkResMessage(String id, String msgName, String keyWords,String accountId) throws BusinessException
//    {
//        List<ResMessage> list = this.resMessageDao.getResMessageListByNameAndKeyWords(msgName,keyWords,accountId);
//        String errorMessage = "";
//        // 新增判断
//        if (StringUtils.isEmpty(id))
//        {
//            if (list.size() > 0)
//            {
//                for (ResMessage resMessage : list)
//                {
//                    if (StringUtils.isNotEmpty(resMessage.getMsgName()) &&
//                    	resMessage.getMsgName().equalsIgnoreCase(msgName) &&
//                    	resMessage.getAccountId().equalsIgnoreCase(accountId))
//                    {
//                        errorMessage = "消息名称已存在！";
//                        break;
//                    }
//                    if(StringUtils.isNotEmpty(keyWords)){
//                        if (StringUtils.isNotEmpty(resMessage.getKeyWords()) &&
//                        	resMessage.getKeyWords().equalsIgnoreCase(keyWords) &&
//                        	resMessage.getAccountId().equalsIgnoreCase(accountId))
//                        {
//                            errorMessage = "关键字已存在！";
//                            break;
//                        }
//                    }
//                }
//                if (StringUtils.isNotEmpty(errorMessage))
//                {
//                    throw new BusinessException(errorMessage);
//                }
//            }
//        }else{
//            if (list.size() > 0)
//            {
//                for (ResMessage resMessage : list)
//                {
//                    if (StringUtils.isNotEmpty(resMessage.getMsgName()) &&
//                    	resMessage.getMsgName().equalsIgnoreCase(msgName) &&
//                    	resMessage.getAccountId().equalsIgnoreCase(accountId) &&
//                    	resMessage.getId().equals(id))
//                    {
//                        errorMessage = "消息名称已存在！";
//                        break;
//                    }
//                    else if (StringUtils.isNotEmpty(resMessage.getKeyWords()) &&
//                    		 resMessage.getKeyWords().equalsIgnoreCase(keyWords) &&
//                    		 resMessage.getAccountId().equalsIgnoreCase(accountId) &&
//                    		 resMessage.getId().equals(id))
//                    {
//                        errorMessage = "关键字已存在！";
//                        break;
//                    }
//                }
//                if (StringUtils.isNotEmpty(errorMessage))
//                {
//                    throw new BusinessException(errorMessage);
//                }
//            }
//        }
//        // List<ResMessage> list2 = this.resMessageDao.getResMessageListByKeyWords(keyWords);
//        // // 新增时判断
//        // if (StringUtils.isEmpty(id))
//        // {
//        //
//        // if (list1.size() > 0)
//        // {
//        // throw new BusinessException("消息名称已存在！");
//        // }
//        //
//        // if (list2.size() > 0)
//        // {
//        // throw new BusinessException("关键字已存在！");
//        // }
//        // }
//        // else
//        // {
//        // boolean flag = false;
//        // // 修改时判断
//        // for(ResMessage tmpResMessage : list1){
//        // if(tmpResMessage.getId() != NumberUtils.toLong(id))
//        // {
//        // flag = true;
//        // break;
//        // }
//        // }
//        // if(flag){
//        // throw new BusinessException("消息名称已存在！");
//        // }
//        // // 修改时判断
//        // for(ResMessage tmpResMessage : list2){
//        // if(tmpResMessage.getId() != NumberUtils.toLong(id))
//        // {
//        // flag = true;
//        // break;
//        // }
//        // }
//        // if(flag){
//        // throw new BusinessException("关键字已存在！");
//        // }
//        // }
//
//    }
//
//
//    /**
//     *
//     * 功能描述：保存回复消息
//     *
//     * @param resMessage
//     */
//    public void saveResMessage(ResMessage resMessage)
//    {
//        this.resMessageDao.saveResMessage(resMessage);
//    }
//
//
//    /**
//     *
//     * 功能描述：更新回复消息
//     *
//     * @param resMessage
//     */
//    @Operator(operator="更新回复消息")
//    public void updateResMessage(ResMessage resMessage)
//    {
//        this.resMessageDao.updateResMessage(resMessage);
//    }
//
//
//    /**
//     *
//     * 功能描述：根据id返回回复消息
//     *
//     * @param id
//     * @return
//     */
//    public ResMessage getResMessageById(String id)
//    {
//        return this.resMessageDao.getResMessageById(id);
//    }
//
//
//    /**
//     *
//     * 功能描述：返回所有回复消息
//     *
//     * @return
//     */
//    public List<ResMessage> getResAllMessage()
//    {
//        return this.resMessageDao.getResAllMessage();
//    }
//
//
//    /**
//     * 获得菜单关联消息
//     * @return
//     */
//    public String getMenuMessage(String accountId){
//    	List<ResMessage> resMessages = this.resMessageDao.getMenuMessage(accountId);
//    	JSONArray jsonArray = new JSONArray();
//    	for(ResMessage resMessage : resMessages) {
//    		MenuMessageVo menuMessage = new MenuMessageVo();
//    		menuMessage.setId(resMessage.getMsgName());
//    		menuMessage.setText(resMessage.getMsgName());
//    		jsonArray.add(menuMessage);
//		}
//    	//System.out.println("jsonArray.toJSONString()=="+jsonArray.toJSONString());
//    	return jsonArray.toJSONString();
//    }
//
//}
