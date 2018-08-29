drop table wx_user;
CREATE TABLE wx_user(
	openid varchar(100) PRIMARY KEY,
	subscribe varchar(1) NULL,
	nickname varchar(200) NULL,
	sex varchar(1) NULL,
	city varchar(50) NULL,
	province varchar(50) NULL,
	country varchar(100) NULL,
	language varchar(50) NULL,
	headimgurl varchar(225) NULL,
	subscribe_time datetime NULL,
	unsubscribe_time datetime NULL,
	create_time datetime NULL,
	update_time datetime NULL,
	unionid varchar(100) NULL,
	id_card_number nvarchar(30) NULL,
	subscribe_flag varchar(200) NULL,
	account_id varchar(200) NULL
);


drop table wx_menu_tmp;
CREATE TABLE wx_menu_tmp(
	id int AUTO_INCREMENT PRIMARY KEY,
	menu_id varchar(255) NULL,
	parent_id varchar(255) NULL,
	name nvarchar(500) NOT NULL,
	menu_type nvarchar(500) NOT NULL,
	menu_order bigint NULL,
	role nvarchar(500) NULL,
	properties nvarchar(500) NOT NULL,
	flag nvarchar(500) NOT NULL,
	create_time datetime NULL,
	update_time datetime NULL,
	status nvarchar(20) NULL,
	account_id varchar(200) NULL
);

CREATE TABLE wx_menu_prd(
	id int AUTO_INCREMENT PRIMARY KEY,
	menu_id bigint NOT NULL,
	parent_id bigint NOT NULL,
	name nvarchar(500) NOT NULL,
	menu_type nvarchar(500) NOT NULL,
	menu_order bigint NULL,
	role nvarchar(500) NULL,
	properties nvarchar(500) NOT NULL,
	flag nvarchar(500) NOT NULL,
	create_time datetime NULL,
	update_time datetime NULL
);

drop table wx_location;
CREATE TABLE wx_location(
	id int AUTO_INCREMENT PRIMARY KEY,
	company_name nvarchar(200) NULL,
	company_addr nvarchar(500) NULL,
	company_desc nvarchar(500) NULL,
	location_x nvarchar(30) NULL,
	location_y nvarchar(30) NULL,
	creator nvarchar(10) NULL,
	create_time datetime NULL,
	update_time datetime NULL,
	account_id varchar(200) NULL
);

CREATE TABLE user_bind(
	id int AUTO_INCREMENT PRIMARY KEY,
	id_no varchar(500) NOT NULL,
	phone_no varchar(500) NOT NULL,
	open_id varchar(500) NOT NULL,
	latest_bind_time datetime NOT NULL,
	latest_cancle_bind_time datetime NULL,
	bind_flag varchar(100) NOT NULL
);

CREATE TABLE sms_info(
	id int AUTO_INCREMENT PRIMARY KEY,
	open_id varchar(500) NOT NULL,
	id_no varchar(500) NOT NULL,
	phone_no varchar(500) NOT NULL,
	auth_code varchar(500) NOT NULL,
	create_time datetime NOT NULL
);

drop table res_message;
CREATE TABLE res_message(
	id int AUTO_INCREMENT PRIMARY KEY,
	msg_name varchar(500) NOT NULL,
	msg_type varchar(500) NULL,
	res_type varchar(500) NULL,
	create_time datetime NULL,
	update_time datetime NULL,
	creator varchar(500) NULL,
	properties varchar(2000) NULL,
	key_words varchar(500) NULL,
	status varchar(20) NULL,
	account_id varchar(200) NULL
);

CREATE TABLE region(
	id int AUTO_INCREMENT PRIMARY KEY,
	value varchar(50) NULL,
	text varchar(50) NULL,
	parent varchar(50) NULL
);

drop table onthelist;
CREATE TABLE onthelist(
	openid varchar(100) PRIMARY KEY,
	subscribe varchar(1) NULL,
	nickname varchar(200) NULL,
	sex varchar(1) NULL,
	city varchar(50) NULL,
	province varchar(50) NULL,
	country varchar(100) NULL,
	language varchar(50) NULL,
	headimgurl varchar(225) NULL,
	subscribe_time datetime NULL,
	unsubscribe_time datetime NULL,
	create_time datetime NULL,
	update_time datetime NULL,
	unionid varchar(100) NULL,
	id_card_number nvarchar(30) NULL,
	subscribe_flag varchar(200) NULL,
	account_id varchar(200) NULL
);

CREATE TABLE msg_info(
	ID varchar(40) PRIMARY KEY,
	MAKEDATE datetime NOT NULL,
	MAKETIME varchar(8) NOT NULL,
	MSGTYPE varchar(2) NOT NULL,
	MSGDIRECTION varchar(2) NOT NULL,
	USERID varchar(30) NOT NULL,
	USERNAME varchar(30) NULL,
	MSGTILE varchar(30) NULL,
	CONTENT BLOB NULL,
	CONTENTPRE varchar(100) NULL,
	SYSTEMCODE varchar(30) NULL,
	SERVICECODE varchar(30) NULL,
	DATATYPE varchar(1) NULL,
	SENDWAY varchar(1) NULL,
	FIXEDDATE datetime NULL,
	FIXEDTIME varchar(8) NULL,
	UNITCODE varchar(30) NULL,
	OPERCODE varchar(30) NULL,
	SYSOPERDATE datetime NULL,
	SYSOPERTIME varchar(8) NULL,
	TEMP1 varchar(20) NULL,
	TEMP2 varchar(50) NULL,
	TEMP3 varchar(20) NULL,
	CUSTOMERID varchar(30) NULL,
	REPORTDATE datetime NULL,
	REPORTTIME varchar(8) NULL,
	DEALORDER varchar(1) NULL,
	LASTSENDDATE datetime NULL,
	LASTSENDTIME varchar(8) NULL,
	SENDTIMES decimal(5, 0) NULL,
	MSGSTATE varchar(1) NOT NULL,
	FILTERTYPE varchar(3) NULL,
	TEMP4 varchar(20) NULL,
	TEMP5 varchar(20) NULL
);


CREATE TABLE message_journal(
	id bigint AUTO_INCREMENT PRIMARY KEY,
	to_user_name varchar(500) NULL,
	from_user_name varchar(500) NULL,
	create_time datetime NULL,
	msg_type varchar(500) NULL,
	msg_id bigint NULL,
	properties varchar(2000) NULL,
	send_flag varchar(200) NULL
);



CREATE TABLE logdaily(
	record_id bigint AUTO_INCREMENT PRIMARY KEY,
	operator_name varchar(225) NOT NULL,
	record_type varchar(225) NULL,
	description varchar(225) NULL,
	create_time datetime NULL,
	expend_time varchar(50) NULL
);


CREATE TABLE event_message_journal(
	id bigint AUTO_INCREMENT PRIMARY KEY,
	to_user_name varchar(500) NULL,
	from_user_name varchar(500) NULL,
	create_time datetime NULL,
	event varchar(500) NULL,
	event_key varchar(500) NULL,
	ticket varchar(500) NULL,
	properties varchar(2000) NULL,
	send_flag varchar(500) NULL
);

CREATE TABLE award(
	id int AUTO_INCREMENT PRIMARY KEY,
	award_name varchar(255) NULL,
	card_id varchar(255) NULL,
	card_password varchar(255) NULL,
	award_price int NULL,
	flag varchar(1) NULL,
	code varchar(10) NULL,
	gailv varchar(10) NULL,
	count int NULL
);

CREATE TABLE article_message_item(
	id bigint AUTO_INCREMENT PRIMARY KEY,
	article_msg_id bigint NOT NULL,
	title varchar(500) NULL,
	description varchar(2000) NULL,
	pic_url varchar(500) NULL,
	content varchar(2000) NULL,
	url varchar(500) NULL,
	create_time datetime NULL,
	update_time datetime NULL
);

CREATE TABLE annual_rate(
	annual_rate_id int AUTO_INCREMENT PRIMARY KEY,
	annual_rate_year varchar(4) NOT NULL,
	year_up_down varchar(1) NOT NULL,
	annual_rate varchar(10) NOT NULL,
	create_time datetime NOT NULL
);

drop table admin_user;
CREATE TABLE admin_user(
	id bigint AUTO_INCREMENT PRIMARY KEY,
	user_name varchar(500) NOT NULL,
	password varchar(500) NOT NULL,
	create_time datetime NULL,
	update_time datetime NULL,
	role varchar(500) NOT NULL,
	account_id varchar(200) NULL,
	department_name varchar(200) NULL
);

CREATE TABLE admin_role(
	id int AUTO_INCREMENT PRIMARY KEY,
	name varchar(500) NOT NULL,
	code varchar(500) NOT NULL,
	description varchar(500) NULL,
	create_time datetime NULL,
	update_time datetime NULL
);

CREATE TABLE admin_menu(
	id bigint AUTO_INCREMENT PRIMARY KEY,
	menu_id int NOT NULL,
	parent_id int NOT NULL,
	name varchar(500) NOT NULL,
	url varchar(500) NULL,
	menu_order int NOT NULL,
	create_time datetime NULL,
	update_time datetime NULL,
	role varchar(500) NULL,
	description varchar(500) NULL
);

CREATE TABLE access_token(
	id int AUTO_INCREMENT PRIMARY KEY,
	access_token varchar(255) NULL,
	create_time datetime NULL,
	account_id varchar(200) NULL
);
