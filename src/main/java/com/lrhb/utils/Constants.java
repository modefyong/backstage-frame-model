package com.lrhb.utils;

import lombok.Data;

/**
 * 常量
 */
public class Constants {

    /**
     * 菜单类型
     * */
    public enum MenuType{
        FIRST_CLASS("1","一级菜单"),
        SECOND_CLASS("2","二级菜单");

        public String value;
        public String name;

        MenuType(String value,String name){
            this.value = value;
            this.name = name;
        }
    }
    
    /**
     * 
     * @ClassName:  ConfirmOrder   
     * @Description:电子联单交接确认  
     * @author: yangyufa 
     * @date:   2019年5月30日 下午3:52:58     
     * @Copyright: 2019 www.dzlshj.com Inc. All rights reserved.
     */
//    public enum ConfirmOrder{
//        FIRST_CONFIRM("1","产废单位创建提交"),
//        SECOND_CONFIRM("2","经营单位确认创建"),
//    	THIRD_CONFIRM("3","运输单位运输确认"),
//    	FOURTH_CONFIRM("4","经营单位接收确认");
//    	
//        public String value;
//        public String name;
//
//        ConfirmOrder(String value,String name){
//            this.value = value;
//            this.name = name;
//        }
//    }
    
    /**
     * 
     * @ClassName:  KuponoProcess   
     * @Description:电子联单流程阶段   
     * @author: yangyufa 
     * @date:   2019年5月30日 下午3:59:09     
     * @Copyright: 2019 www.dzlshj.com Inc. All rights reserved.
     */
//    public enum KuponoProcess{
//    	TEMP_KUPONO("0","产废单位暂存联单"),
//    	SAVE_KUPONO("1","产废单位提交"),
//    	CREATE_OK_KUPONO("21","经营单位确认创建"),
//    	CREATE_CANCEL_KUPONO("20","经营单位取消创建"),
//    	TRANFER_OK_KUPONO("31","运输单位运输确认"),
//    	TRANFER_CANCEL_KUPONO("30","运输单位运输取消"),
//    	ACCEPT_OK_KUPONO("41","经营单位接收"),
//    	ACCEPT_CANCEL_KUPONO("40","经营单位决绝接收");
//    	
//        public String value;
//        public String name;
//
//        KuponoProcess(String value,String name){
//            this.value = value;
//            this.name = name;
//        }
//    }
    

    /**
     * 菜单图标类型,具体值请参考阿里iconfont
     * */
    public enum MenuIcon{

        SHE_ZHI("icon-shezhi1","设置"),
        ZHANG_HU("icon-zhanghu","账户"),
        BIAN_JI("icon-edit","编辑"),
        GONG_GAO("icon-gonggao","公告"),
        WEN_BEN("icon-text","文本");

        public String value;
        public String name;

        MenuIcon(String value,String name){
            this.value = value;
            this.name = name;
        }
    }

    /**
     * 上传附件业务类型
     * */
    public enum AttachmentType{

        USER_FACE("userFace");//用户头像

        public String value;

        AttachmentType(String name){
            this.value = name;
        }
    }

    /**
     * 一级菜单初始CODE
     * */
    public static final String INIT_FIRST_CLASS_MENU_CODE = "1";

    /**
     * 二级菜单初始CODE
     * */
    public static final String INIT_SECOND_CLASS_MENU_CODE = "1";

    /**
     * 组织机构编码规则
     * */
    public static class OrgCodeRule{
        /**根机构编码*/
        public static final String ROOT_CODE = "010001";
        /**初始序号*/
        public static final String INIT_SORT = "0001";
        /**初始前缀*/
        public static final String INIT_LEVEL = "01";
    }

    /**
     * 初始化密码
     * */
    public static final String INIT_PWD = "123456";

}
