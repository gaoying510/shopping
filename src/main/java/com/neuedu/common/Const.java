package com.neuedu.common;

public class Const {

    public static final String CURRENTUSER="current_user";
    public static final String TRADE_SUCCESS="TRADE_SUCCESS";
    public static final String AUTOLOGINTOKEN="aotoLoginToken";

    public enum ReponseCodeEnum{

        NEED_LOGIN(2,"需要登录"),
        NO_PRIVILEGE(3,"没有权限")

        ;
        private Integer code;
        private String desc;
        private ReponseCodeEnum(int code,String desc)
        {
            this.code=code;
            this.desc=desc;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public  enum RoleEnum{

        ROLE_ADMIN(0,"管理员"),
        ROLE_CUSTOMER(1,"普通用户")
        ;

        private Integer code;
        private String desc;
        private RoleEnum(int code,String desc)
        {
            this.code=code;
            this.desc=desc;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public  enum ProductStatusEnum{
        PRODUCT_ONLINE(1,"在售"),
        PRODUCT_OFFLINE(2,"下架"),
        PRODUCT_DELETE(3,"删除")
        ;

        private Integer code;
        private String desc;
        private ProductStatusEnum(int code,String desc)
        {
            this.code=code;
            this.desc=desc;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
    public  enum CartCheckedEnum{

        PRODUCT_CHECKED(1,"已勾选"),
        PRODUCT_UNCHECKED(0,"未勾选")
        ;

        private Integer code;
        private String desc;
        private CartCheckedEnum(int code,String desc)
        {
            this.code=code;
            this.desc=desc;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
/*
* 订单状态
* */
    public  enum  OrderStatusEnum{

        ORDER_CANCELED(0,"已取消"),
        ORDER_UP_PAY(10,"未付款"),
        ORDER_PAYED(20,"已付款"),
        ORDER_SEND(40,"已发货"),
        ORDER_SUCCESS(50,"交易成功"),
        ORDER_CLOSED(60,"交易关闭"),
        ;

        private Integer code;
        private String desc;
        private OrderStatusEnum(int code,String desc)
        {
            this.code=code;
            this.desc=desc;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }


        public static  OrderStatusEnum codeOf(Integer code)
        {
            for (OrderStatusEnum orderStatusEnum:values()) {
                if (code==orderStatusEnum.getCode())
                {
                    return orderStatusEnum;
                }
            }
            return null;
        }
    }

    //支付类型
    public  enum  PaymetEnum{

        ONLINE(1,"线上支付"),
        ;

        private Integer code;
        private String desc;
        private PaymetEnum(int code,String desc)
        {
            this.code=code;
            this.desc=desc;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public static  PaymetEnum codeOf(Integer code)
        {
            for (PaymetEnum paymetEnum:values()) {
                if (code==paymetEnum.getCode())
                {
                    return paymetEnum;
                }
            }
            return null;
        }
    }
    public  enum  PaymetPlatformEnum{

        ALIPAY(1,"支付宝"),
        ;

        private Integer code;
        private String desc;
        private PaymetPlatformEnum(int code,String desc)
        {
            this.code=code;
            this.desc=desc;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }



    }
}
