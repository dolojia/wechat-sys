//package com.dolo.wechat.manage.controller;
//
//import com.dolo.wechat.web.BaseController;
//import com.dolojia.wxadmin.service.ImageUploadService;
//import net.sf.json.JSONObject;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.File;
//import java.io.IOException;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
//
///**
//* 描述: 图片上传
//* 作者: dolojia
//* 修改日期: 2018/8/30 下午11:07
//* E-mail: dolojia@gmail.com
//**/
//@Controller
//public class ImageUploadController  extends BaseController
//{
//
//    @Autowired
//    private ImageUploadService imageUploadService;
//
//
//    @RequestMapping("jump_image_upload.xhtml")
//    public ModelAndView jumpUploadImage(@RequestParam(value = "picUrl", required = false)
//    String picUrl)
//    {
//        return new ModelAndView("image_upload").addObject("fileUrl", picUrl);
//    }
//
//
//    @RequestMapping(value = "image_upload.json", method = RequestMethod.POST)
//    public void upload(@RequestParam(value="uploadFile")MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException
//    {
//        Map<String,String> model = new HashMap<String, String>();
//        String fileName = this.generateFileName(file.getOriginalFilename());
//        String path = request.getSession().getServletContext().getRealPath("upload");
//        File targetFile = this.imageUploadService.getTargetFile(path,fileName);
//        try
//        {
//            file.transferTo(targetFile);
//            this.imageUploadService.uploadImage(fileName, targetFile.getPath());
//            model.put("fileUrl", request.getContextPath() + "/upload/" + fileName);
//            model.put("file", fileName);
//        }
//        catch (Exception e)
//        {
//            logger.error("图片上传失败！", e);
//            model.put("errorMsg", "图片上传失败！");
//        }
//        JSONObject json = JSONObject.fromObject(model);
////        response.setContentType("text/html;charset=UTF-8");
//        response.getWriter().write(json.toString());
//        response.getWriter().close();
//    }
//
//
//    public static void main(String[] args)
//    {
//        System.out.println(System.getProperty("env"));
//    }
//
//
//    private String generateFileName(String fileName)
//    {
//        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
//        String formatDate = format.format(new Date());
//        int random = new Random().nextInt(10000);
//        int position = fileName.lastIndexOf(".");
//        String extension = fileName.substring(position);
//        return formatDate + random + extension;
//    }
//}
