package com.dolo.wechat.common.response;


/**
* 描述: 返回结果封装类，所有异步请求的数据都用此方法封装后转换为json对象后返回到页面
* 作者: dolojia
* 修改日期: 2018/8/29 下午9:51
* E-mail: dolojia@gmail.com
**/
public class ResultObject {
	
	private int errorCode;
	
	private String errorMsg;
	
	private Object data;
	
	public ResultObject(){
		
	}
	
	public ResultObject(Object data){
		this.data = data;
	}
	
	public ResultObject(int errorCode, String errorMsg){
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public ResultObject(int errorCode){
		this.errorCode = errorCode;
	}
	
	public ResultObject(String errorMsg){
		this.errorMsg = errorMsg;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
