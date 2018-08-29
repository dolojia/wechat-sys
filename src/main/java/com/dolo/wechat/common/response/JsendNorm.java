package com.dolo.wechat.common.response;

/**
 * 描述: JSON数据交换约定
 * 作者: dolojia
 * 修改日期: 2018/8/29 14:46
 * E-mail: dolojia@gmail.com
 **/
public final class JsendNorm {

    public static final String STATUS_KEY = "status";

    public enum Status {
        /**
         * success
         */
        SUCCESS("success"),

        /**
         * fail
         */
        FAIL("fail"),

        /**
         * error
         */
        ERROR("error");

        private String value;

        private Status(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

    public static final String MESSAGE_KEY = "message";

    public static final String CODE_KEY_STRING = "code";

    public enum Code {
        /**
         * PARAM_ERROR
         */
        PARAM_ERROR("1"),

        /**
         * PWD_ERROR
         */
        PWD_ERROR("2");

        private String value;

        private Code(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

    public static final String DATA_KEY = "data";

    public static final String TITLE_KEY = "title";

    public static final String TITLE_TOOLTIP = "错误信息";

}
