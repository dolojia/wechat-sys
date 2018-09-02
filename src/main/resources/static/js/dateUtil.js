/*******************************************************************************
 * 日期工具类<br>
 * 编码为UTF-8 若有需要请自行转换
 * @author Leeyao
 * y: 年<br>
 * M：月 1~12<br>
 * d: 日 1~31<br>
 * w: 周 0~6<br>
 * W: 中文周 日~六<br>
 * h：时(12) 0~12 1~11<br>
 * H：时(24) 0~23<br>
 * p：上下午标识英文 am|pm P：上下午标识中文 上午|下午 m: 分 0~59<br>
 * s: 秒 0~59<br>
 ******************************************************************************/
var DateUtil = function() {
	this.week = new Array("日", "一", "二", "三", "四", "五", "六");
}

/**
 * 定义常用的日期格式的常量
 */
DateUtil.prototype = {
	DEFAULT_DATE_FORMAT : 'yyyy-MM-dd',
	DEFAULT_DATE_FORMAT_CHN : 'yyyy年MM月dd日',
	DEFAULT_DATEWEEK_FORMAT_CHN : 'yyyy年MM月dd日 星期W',
	DEFAULT_MONTH_FORMAT : 'yyyy-MM',
	DEFAULT_MONTH_FORMAT_CHN : 'yyyy年MM月',
	DEFAULT_YEAR_FORMAT : 'yyyy',
	DEFAULT_YEAR_FORMAT_CHN : 'yyyy年',
	DEFAULT_TIME_FORMAT : 'HH:mm:ss',
	DEFAULT_DATETIME_FORMAT : 'yyyy-MM-dd HH:mm:ss',
	DEFAULT_DATETIME_FORMAT_CHN : 'yyyy年MM月dd日 HH:mm:ss',
	DEFAULT_DATETIME12_FORMAT : 'yyyy-MM-dd hh:m:ss p',
	DEFAULT_DATETIME12_FORMAT_CHN : 'yyyy年MM月dd日 Phh:mm:ss',
	DEFAULT_YEAR : 'yyyy',
	DEFAULT_MONTH : 'M',
	DEFAULT_DATE : 'd',
	DEFAULT_DAY : 'w', // 周
	DEFAULT_DAY_CHN : '周W', // 中文周

	/**
	 * 在控制台输出日志
	 * 
	 * @params message 要输出的日志信息
	 */
	debug : function(message) {
		if (!window.console) {
			return;
		}
		window.console.log(message + ' ');
	},

	/**
	 * 格式化日期
	 * 
	 * @params date 要格式化的日期,如格式化当前日期请留空
	 * @params strFormat 要得到的日期的格式的格式化字符串 默认：yyyy-MM-dd HH:mm:ss
	 * @return 返回根据给定格式的字符串表示的时间日期格式<br>
	 *         如果传入不合法的格式，则返回日期的字符串格式
	 */
	format : function(date, strFormat) {
		try {
			if (date == undefined) {
				date = new Date();
			} else if (!(date instanceof Date)) {
				this.debug('你输入的date:' + date + '不是日期类型');
				return date;
			}
			var str = strFormat === undefined ? this.DEFAULT_DATETIME_FORMAT : strFormat;
			str = str.replace(/yyyy|YYYY/, date.getFullYear());
			str = str.replace(/yy|YY/, (date.getYear() % 100) > 9 ? (date.getYear() % 100).toString() : '0' + (date.getYear() % 100));
			str = str.replace(/MM/, (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : '0' + (date.getMonth() + 1));
			str = str.replace(/M/g, date.getMonth() + 1);
			str = str.replace(/w/g, date.getDay());
			str = str.replace(/W/g, this.week[date.getDay()]);

			str = str.replace(/dd+|DD+/, date.getDate() > 9 ? date.getDate().toString() : '0' + date.getDate());
			str = str.replace(/d|D/g, date.getDate());

			// 12小时制
			var hours12 = date.getHours() > 12 ? (date.getHours() - 12) : date.getHours() > 12;
			str = str.replace(/hh+/, hours12 > 9 ? hours12.toString() : '0' + hours12);
			str = str.replace(/h/g, hours12);
			str = str.replace(/HH+/, date.getHours() > 9 ? date.getHours().toString() : '0' + date.getHours());
			str = str.replace(/H/g, date.getHours());
			str = str.replace(/mm+/, date.getMinutes() > 9 ? date.getMinutes().toString() : '0' + date.getMinutes());
			str = str.replace(/m/g, date.getMinutes());

			str = str.replace(/ss+|SS+/, date.getSeconds() > 9 ? date.getSeconds().toString() : '0' + date.getSeconds());
			str = str.replace(/s|S/g, date.getSeconds());

			str = str.replace(/P/, date.getHours() < 11 ? '上午' : '下午');
			str = str.replace(/p/, date.getHours() < 11 ? 'am' : 'pm');
			return str;
		} catch (e) {
			this.debug('格式化日期出现异常：' + e.message);
			return date;
		}
	},

	/**
	 * 把指定格式的字符串转换为日期对象
	 * 暂不支持短日期格式 如: yy-M-d h:m:s 13-2-3 5:3:5
	 * @param dateStr String 要转换的日期字符串
	 * @param formatStr String 日期格式 默认yyyy-MM-dd HH:mm:ss
	 * @return Date 转化的日期对象
	 */
	strToDate : function(dateStr, formatStr) {
		formatStr = arguments[1] || this.DEFAULT_DATETIME_FORMAT;
		var start = -1;
		var len = dateStr.length;
		var year = 0;
		if (((start = formatStr.indexOf('yyyy')) > -1 || (start = formatStr.indexOf('YYYY')) > -1) && start < len) {
			year = dateStr.substr(start, 4);
		}
		var month = 0;
		if ((start = formatStr.indexOf('MM')) > -1 && start < len) {
			month = parseInt(dateStr.substr(start, 2)) - 1;
		}
		var day = 0;
		if (((start = formatStr.indexOf('dd')) > -1 || (start = formatStr.indexOf('DD')) > -1) && start < len) {
			day = parseInt(dateStr.substr(start, 2));
		}
		var hour = 0;
		if ((start = formatStr.indexOf('HH')) > -1 && start < len) {
			hour = parseInt(dateStr.substr(start, 2));
		}else if((start = formatStr .indexOf('hh')) > -1 && start < len){
			hour = parseInt(dateStr.substr(start, 2));
			if ((dateStr.indexOf('pm') > -1 || dateStr.indexOf('下午') > -1) && hour < 12){
				hour = (hour + 12) == 12 ? 0 : (hour + 12);
			}
		}
		var minute = 0;
		if ((start = formatStr.indexOf('mm')) > -1 && start < len) {
			minute = dateStr.substr(start, 2);
		}
		var second = 0;
		if ((start = formatStr.indexOf('ss')) > -1 && start < len) {
			second = dateStr.substr(start, 2);
		}
		return new Date(year, month, day, hour, minute, second);
	},
	
	/**
	 * 判断是否为闰年
	 * 
	 * @params year 要判断的年份
	 * @return 是：true, 否：false
	 */
	isLeapYear : function(date) {
		return (0 == date.getYear() % 4 && ((date.getYear() % 100 != 0) || (date.getYear() % 400 == 0)));
	},

	/**
	 * 日期计算
	 * 
	 * @param strInterval string 可选值 y 年 m月 d日 w星期 h时 n分 s秒 不可为空
	 * @param num int 可为负数 不可为空
	 * @param date Date 日期对象 无传值则默认当前时间
	 * @return Date 返回日期对象
	 */
	compute : function(strInterval, num, date) {
		date = arguments[2] || new Date();
		if (strInterval == undefined || num == undefined) {
			return new Date();
		}
		switch (strInterval) {
		case 's':
			return new Date(date.getTime() + (1000 * num));
		case 'n':
			return new Date(date.getTime() + (60000 * num));
		case 'h':
			return new Date(date.getTime() + (3600000 * num));
		case 'd':
			return new Date(date.getTime() + (86400000 * num));
		case 'w':
			return new Date(date.getTime() + ((86400000 * 7) * num));
		case 'm':
			var temp = new Date(date.getFullYear(), (date.getMonth() + num), 1);
			if(date.getDate() > this.getMaxDay(temp)){
				return new Date(date.getFullYear(), (date.getMonth() + num), this.getMaxDay(temp), 
						date.getHours(), date.getMinutes(), date.getSeconds());
			}
			return new Date(date.getFullYear(), (date.getMonth() + num), date.getDate(),
						date.getHours(), date.getMinutes(), date.getSeconds());
			//return new Date(date.getTime() + this.getMaxDay(temp) * 86400000 * (num > 0 ? 1 : -1));
		case 'y':
			return new Date((date.getFullYear() + num), date.getMonth(), date.getDate(), 
						date.getHours(), date.getMinutes(), date.getSeconds());
		}
	},

	/**
	 * 比较日期差
	 * 
	 * @param strInterval string 可选值 y 年 m月 d日 w星期 ww周 h时 n分 s秒
	 * @param dtStart Date 格式为日期型或者有效日期格式字符串
	 * @param dtEnd Date 格式为日期型或者有效日期格式字符串
	 * @return 日期差
	 */
	diff : function(strInterval, dtStart, dtEnd) {
		dtStart = arguments[1] || new Date();
		dtEnd = arguments[2] || new Date();
		if(dtStart > dtEnd){
			//互换两值
			dtStart = [dtEnd,dtEnd = dtStart][0];
		}
		switch (strInterval) {
		case 's':
			return parseInt((dtEnd - dtStart) / 1000);
		case 'n':
			return parseInt((dtEnd - dtStart) / 60000);
		case 'h':
			return parseInt((dtEnd - dtStart) / 3600000);
		case 'd':
			return parseInt((dtEnd - dtStart) / 86400000);
		case 'w':
			return parseInt((dtEnd - dtStart) / (86400000 * 7));
		case 'm':
			return (dtEnd.getMonth() + 1)
					+ ((dtEnd.getFullYear() - dtStart.getFullYear()) * 12)
					- (dtStart.getMonth() + 1);
		case 'y':
			return dtEnd.getFullYear() - dtStart.getFullYear();
		}
	},
	
	/**
	 * 计算较时间跨度
	 * 
	 * @param dtStart Date 格式为（yyyy-MM-dd）日期型或者有效日期格式字符串
	 * @param dtEnd Date 格式为（yyyy-MM-dd）日期型或者有效日期格式字符串
	 * @return 跨度值
	 */
	kuadu : function(dtStart, dtEnd) {
		var OneMonth = dtStart.substring(5,7); 
		var OneDay = dtStart.substring(8,10); 
		var OneYear = dtStart.substring(0,4); 
		var TwoMonth = dtEnd.substring(5,7); 
		var TwoDay = dtEnd.substring(8,10); 
		var TwoYear = dtEnd.substring(0,4); 
		var numOne = OneYear+OneMonth+OneDay;
		var numTwo = TwoYear+TwoMonth+TwoDay;
		var cha=((Date.parse(TwoMonth+'/'+TwoDay+'/'+TwoYear)-Date.parse(OneMonth+'/'+OneDay+'/'+OneYear))/86400000);
		return cha;
	},
	
	
	/**
	 * 获得传入日期的当月最大天数
	 * @param date Date 日期
	 * @return int 最大天数
	 */
	getMaxDay: function(date){
		date = arguments[0] || new Date();
		var temp = new Date(date);
		temp.setDate(1);  
		temp.setMonth(date.getMonth() + 1);  
        var time = temp.getTime() - 24 * 60 * 60 * 1000;  
        var newDate = new Date(time);  
        return newDate.getDate();
	}
}