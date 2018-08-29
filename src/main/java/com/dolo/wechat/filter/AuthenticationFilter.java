package com.dolo.wechat.filter;

import com.alibaba.fastjson.JSON;
import com.dolo.wechat.common.response.RequestWrapper;
import com.dolo.wechat.common.response.ResponseWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * 描述: IP白名单过滤、 签名校验、入参、出产日志打印<br>
 * 作者: dolojia
 * 修改日期: 2018/8/29 14:35
 * E-mail: dolojia@gmail.com
 **/
@Component
public class AuthenticationFilter implements Filter {

    Logger logger = LogManager.getLogger(AuthenticationFilter.class);

    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(Arrays
            .asList("swagger-ui.html", "swagger-resources", "v2/api-docs", "webjars/springfox-swagger-ui", "druid/"
                    , "clChannelManage", "clProcotolManage", "clProductMatchingConditionManage")));

    private static final String REQUEST_PREFIX = "Request: ";
    private static final String RESPONSE_PREFIX = "Response: ";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @SuppressWarnings("unchecked")
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        long start = System.currentTimeMillis();
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        if (servletRequest instanceof HttpServletRequest) {
            request = (HttpServletRequest) servletRequest;
        }
        if (servletResponse instanceof HttpServletResponse) {
            response = (HttpServletResponse) servletResponse;
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        // 忽略Swagger资源文件
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        for (String allowedPath : ALLOWED_PATHS) {
            if (path.contains(allowedPath)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        String params = "";
        String requestURl = request.getRequestURL().toString();
        String method = request.getMethod();

        Map<String, Object> pramMap = new HashMap<>();
        ServletRequest requestWrapper = new RequestWrapper(request);
        if ("POST".equalsIgnoreCase(method)) {
            params = this.readByString(requestWrapper);
            pramMap = JSON.parseObject(params, Map.class);
        } else {
            Enumeration<String> enu = request.getParameterNames();
            while (enu.hasMoreElements()) {
                String paramName = enu.nextElement().trim();
                pramMap.put(paramName, request.getParameter(paramName));
            }
            params = pramMap.toString();
        }
        logger.info(REQUEST_PREFIX + "[" + method + "]" + requestURl + "," + params);
        try {
            // 请求头签名校验
//			String appid = request.getHeader("appid");
//			String sign = request.getHeader("sign");
//			logger.info(REQUEST_PREFIX + "[sign]" + sign + ",[appid]" + appid);
//			ClChannel clChannelDb = clChannelService.selectClChannelByAppid(appid);
//			Map<String, Object> result = new HashMap<>();
//			if (clChannelDb == null || "0".equalsIgnoreCase(clChannelDb.getStatus())) {
//				result = ResponseCode.APPID_INVALID;
//				this.writeToResponse(response, JSON.toJSONString(result));
//				return;
//			} else {
//				if (!"2".equalsIgnoreCase(clChannelDb.getStatus())) {
//					String ip = RequestUtils.getRealIPAddress(request);
//					if (!clChannelDb.getIpList().contains(ip)) {
//						result = ResponseCode.IP_COMMON;
//						this.writeToResponse(response, JSON.toJSONString(result));
//						return;
//					}
//					if (!pramMap.isEmpty()) {
//						pramMap.put(SignUtil.FIELD_SIGN, sign);
//						if (StringUtils.isEmpty(sign)
//								|| !SignUtil.isSignatureValid(pramMap, clChannelDb.getAppid())) {
//							result = ResponseCode.SIGN_INVALID;
//							this.writeToResponse(response, JSON.toJSONString(result));
//							return;
//						}
//					}
//				}
//			}

        } catch (Exception e) {
            logger.error("拦截器拦截异常", e);
            return;
        }

        ResponseWrapper responseWrapper = new ResponseWrapper(response);
        filterChain.doFilter(requestWrapper, responseWrapper);
        String result = responseWrapper.getResponseData(response.getCharacterEncoding());
        // 获取response返回的内容并重新写入response
        this.writeToResponse(response, result);
        long exeCost = System.currentTimeMillis() - start;
        logger.info("[" + method + "]" + requestURl + "," + REQUEST_PREFIX + params + "," + RESPONSE_PREFIX + result
                + ",exeCost[" + exeCost + "]");
    }

    @Override
    public void destroy() {

    }

    public void writeToResponse(HttpServletResponse response, String writeData) throws IOException {
        response.getOutputStream().write(writeData.getBytes());
    }

    public String readByString(ServletRequest request) {
        StringBuffer sb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                sb.append(StringUtils.trim(line));
            }
            reader.close();
        } catch (Exception e) {
            logger.info("readJSONString方法异常:[{}]", request, e);
        }
        return sb.toString();
    }

}
