//package com.dolo.wechat.business;
//
//import com.dolojia.wxadmin.bo.ArticleMessage;
//import com.dolojia.wxadmin.bo.ArticleMessageItem;
//import com.dolojia.wxadmin.bo.ResMessage;
//import com.dolojia.wxadmin.common.ApprovedStatusEnum;
//import com.dolojia.wxadmin.common.Operator;
//import com.dolojia.wxadmin.dao.ArticleMessageItemDao;
//import com.dolojia.wxadmin.exception.BusinessException;
//import com.dolojia.wxadmin.utils.LuceneUtil;
//import com.jcraft.jsch.JSchException;
//import com.jcraft.jsch.SftpException;
//import org.apache.commons.beanutils.BeanUtils;
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
//import java.util.Date;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//
///**
//* 描述: 图文消息的Service
//* 作者: dolojia
//* 修改日期: 2018/8/30 下午11:07
//* E-mail: dolojia@gmail.com
//**/
//@Service
//public class ArticleMessageService
//{
//
//    protected final Log logger = LogFactory.getLog(getClass());
//
//    @Autowired
//    private ResMessagService resMessagService;
//
//    @Autowired
//    private ImageUploadService imageUploadService;
//
//    @Value("${lucene.index.path}")
//    private String luceneIndexPath;
//
//    @Value("${wxpic.url}")
//    private String wxpicUrl;
//
//
//    @Operator(operator = "增加或更新图文消息")
//    public void saveArticleMessage(String userName, ArticleMessage articleMessage, String path, String accountId) throws BusinessException, IOException, SftpException, JSchException
//    {
//        ResMessage resMessage = new ResMessage();
//        resMessage.setId(articleMessage.getId());
//        resMessage.setMsgName(articleMessage.getMsgName());
//        resMessage.setResType(articleMessage.getResType());
//        resMessage.setCreator(userName);
//        resMessage.setStatus(ApprovedStatusEnum.UNSUBMIT.name());
//        resMessage.setMsgType("news");
//        resMessage.setAccountId(accountId);
//        resMessage.setKeyWords(articleMessage.getKeyWords());
//        // 检查消息名称和关键字是否重复
//        this.resMessagService.checkResMessage(articleMessage.getId() == null ? null : "" + articleMessage.getId(), articleMessage.getMsgName(), articleMessage.getKeyWords(), accountId);
//        // 新增
//        if (articleMessage.getId() == null)
//        {
//            resMessage.setCreateTime(new Date());
//            this.resMessagService.saveResMessage(resMessage);
//
//        }
//        else
//        {
//            // 修改
//            resMessage.setUpdateTime(new Date());
//            this.resMessagService.updateResMessage(resMessage);
//            // 删除原有的item
//            this.articleMessageItemDao.deleteByArticleMsgId(articleMessage.getId());
//        }
//        // 统一插入到图文消息表
//        this.insertArticleMessageItems(resMessage.getId(), articleMessage.getArticleMessageItems());
//        // 清除Lucene文件夹中的内容，然后重新生成搜索内容
//        List<ResMessage> resMessageList = this.resMessagService.getResAllMessage();
//        LuceneUtil.generatorLuceneIndex(resMessage.getAccountId(),luceneIndexPath,resMessageList);
//        // ftp上传图片
//        this.uploadArticleMessageImage(path, articleMessage.getArticleMessageItems());
//    }
//
//
//    /**
//     *
//     * 功能描述：上传图片，将百度富文本编辑器中编辑的正文内容图片上传到图片服务器
//     *
//     * @param path
//     * @param articleMessageItems
//     * @throws FileNotFoundException
//     * @throws SftpException
//     * @throws JSchException
//     */
//    private void uploadArticleMessageImage(String path, List<ArticleMessageItem> articleMessageItems) throws FileNotFoundException, SftpException, JSchException
//    {
//        Pattern imgPattern = Pattern.compile("title=\"[0-9]{19}.(jpg|png|bmp|jpeg|gif)\"");
//        for (ArticleMessageItem articleMessageItem : articleMessageItems)
//        {
//            String content = articleMessageItem.getContent();
//            if (StringUtils.isNotEmpty(content))
//            {
//                Matcher m = imgPattern.matcher(content);
//                while (m.find())
//                {
//                    String s = m.group();
//                    String fileName = StringUtils.substring(s, 7, s.length() - 1);
//                    File targetFile = this.imageUploadService.getTargetFile(path, fileName);
//                    this.imageUploadService.uploadImage(fileName, targetFile.getPath());
//                }
//            }
//        }
//    }
//
//
//    /**
//     *
//     * 功能描述：保存到messageItem中
//     *
//     * @param id
//     * @param articleMessageItems
//     */
//    private void insertArticleMessageItems(Long id, List<ArticleMessageItem> articleMessageItems)
//    {
//        for (ArticleMessageItem articleMessageItem : articleMessageItems)
//        {
//            String picUrl = articleMessageItem.getPicUrl();
//            if (StringUtils.isNotEmpty(picUrl))
//            {
//                articleMessageItem.setPicUrl(StringUtils.replace(picUrl, "/wxadmin", wxpicUrl));
//            }
//            String content = articleMessageItem.getContent();
//            if (StringUtils.isNotEmpty(content))
//            {
//                articleMessageItem.setContent(StringUtils.replace(content, "/wxadmin", wxpicUrl));
//            }
//            articleMessageItem.setArticleMsgId(id);
//            articleMessageItem.setCreateTime(new Date());
//            articleMessageItem.setUpdateTime(new Date());
//            this.articleMessageItemDao.insert(articleMessageItem);
//        }
//    }
//
//
//    public ArticleMessage getArticleMessageById(String id) throws IllegalAccessException, InvocationTargetException
//    {
//        ResMessage resMessage = this.resMessagService.getResMessageById(id);
//        ArticleMessage articleMessage = new ArticleMessage();
//        BeanUtils.copyProperties(articleMessage, resMessage);
//        List<ArticleMessageItem> articleMessageItems = this.articleMessageItemDao.getArticleMessageItemsById(id);
//        articleMessage.setArticleMessageItems(articleMessageItems);
//        return articleMessage;
//    }
//
//
//    public static void main(String[] args)
//    {
//        String content = "<p>mikettmikettmikett</p><p><img src=\"http://10.140.130.43:8082/wxadmin/ueditor/jsp/upload/image/20141009/1412790955336001151.jpg\" title=\"1412790955336001151.jpg\" alt=\"Tulips.jpg\"/></p><p><br/></p><p>dadasdasd</p><p>dada</p><p>mikettmikettmikett</p><p><img src=\"http://10.140.130.43:8082/wxadmin/ueditor/jsp/upload/image/20141009/1412790955336001144.jpg\" title=\"1412790955336001144.jpg\" alt=\"Tulips.jpg\"/></p>";
//        if (StringUtils.isNotEmpty(content))
//        {
//            Pattern p1 = Pattern.compile("title=\"[0-9]{19}.jpg\"");
//            Matcher m1 = p1.matcher(content);
//            while (m1.find())
//            {
//                String s = m1.group();
//                String fileName = StringUtils.substring(s, 7, s.length() - 1);
//                System.out.println("fileName:" + fileName);
//            }
//        }
//
//    }
//
//}
