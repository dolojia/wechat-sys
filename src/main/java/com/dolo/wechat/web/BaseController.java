package com.dolo.wechat.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;


/**
 * 控制器基类
 */
public class BaseController {

	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * 获取session中的属性值
	 */
	protected Object getSessionAttribute(HttpServletRequest request, String name) {
		return WebUtils.getSessionAttribute(request, name);
	}

	/**
	 * 写出数据
	 * 
	 * @param res
	 *            输出的字符串
	 * @throws Exception
	 */
	protected void write(String resp, HttpServletResponse response) {
		Writer writer = null;
		try {
			resp = (null == resp ? "" : resp);
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			writer = response.getWriter();
			logger.debug("输出JSON字符串：" + resp);
			writer.write(resp);
		} catch (IOException e) {
			logger.error("输出JSON字符串异常", e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error("关闭输出流异常,无法关闭会导致内存溢出");
				}
			}
		}
	}

	protected String getRealIPAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		logger.debug("==> x-forwarded-for = " + ip);

		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("Proxy-Client-IP");
			logger.debug("==> Proxy-Client-IP = " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			logger.debug("==> WL-Proxy-Client-IP = " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			logger.debug("==> RemoteAddr-IP = " + ip);
		}

		if (!StringUtils.isEmpty(ip)) {
			// 返回多个IP时取第一个
			ip = ip.split(",")[0];
		}

		return ip;
	}
	
	/**
	 * 转换日志信息为String
	 * @param ex
	 * @return
	 */
	protected String exceptionInfo2String(Exception ex) {
		try (StringWriter sw = new StringWriter();PrintWriter pw = new PrintWriter(sw);){
			 ex.printStackTrace(pw);
	         return "\r\n" + sw.toString() + "\r\n";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("转换日志信息为String异常", e);
		}
		return "";
	}
}

