/**
 * api接口列表
 */
layui.define(['$tool','jquery'], function (exports) {

    var $tool = layui.$tool,
        $ = layui.jquery;// 拿到模块变量

    /**
     * 封装一个post
     * */
    function doPost(url,req,successCallback,errorCallback) {
        $.ajax({
            url:url,
            data:req,
            method:"post",
            success:function (data) {
                successCallback(data);
            },
            error:function (error) {
                errorCallback(error);
            }
        });
    }
    
    function GetCheckboxValues(Name) {
        var result = [];
        $("[name='" + Name + "']:checkbox").each(function () {
            if ($(this).is(":checked")) {
                result.push($(this).attr("value"));
            }
        });
        return result.join(",");
    };
    
    function SetCheckboxChecked(array,checkboxObj) {
         for (var j = 0; j < array.length; j++) {
             for (var i = 0; i < checkboxObj.length; i++) {
                 if (checkboxObj[i].value == array[j]) {
                	 checkboxObj[i].checked = true;
                 }
             }
         }
    };
    
    function getCompanyNameById(id,successCallback){
    	doGet($tool.getContext()+'common/company/getCompNameById.do',{id:id},function (res) {
        	if(res.data && res.data.length>0){
        		cname = res.data;
        		successCallback(cname);
        	}
        });
    }
    
    //通过角色控制显示按钮
    function isEnvProtection(){
    	var flag = false;
    	if(window.sessionStorage.getItem("roles")){
    		var rolesStr = window.sessionStorage.getItem("roles");
    		if(rolesStr.indexOf('普通用户')>=0 && rolesStr.indexOf('主管')<0){
    			flag = true;
        		$(".approval_btn").hide();
        	}
    	}
    	return flag;
    }
    
    //用户信息
    function checkLoginUser(){
    	var loginName = window.sessionStorage.getItem("sysUser");
    	$("#loginName").val(loginName);
    	if(window.sessionStorage.getItem("roles")){   		
    		var rolesStr = window.sessionStorage.getItem("roles");
    		if(rolesStr.indexOf('普通用户')>=0 && rolesStr.indexOf('主管')<0){
        		$("#roleFlag").val("0");
        	} else if(rolesStr.indexOf('主管')>=0){
        		$("#roleFlag").val("1");
        	} else {
        		$("#roleFlag").val("2");
        	}
    	}
    }

    /**
     * 封装一个get
     * */
    function doGet(url,req,successCallback,errorCallback) {
        $.ajax({
            url:url,
            data:req,
            method:"get",
            success:function (data) {
                successCallback(data);
            },
            error:function (error) {
                errorCallback(error);
            }
        });
    }

    /**
     * 封装一个支持更多参数的post
     * */
    function doComplexPost(url,req,config,successCallback,errorCallback) {
        var defaultConfig = {
            url:url,
            data:req,
            method:"post",
            success:function (data) {
                successCallback(data);
            },
            error:function (error) {
                errorCallback(error);
            }
        };
        var ajaxConfig = $.extend({},config,defaultConfig); // 合并参数

        $.ajax(ajaxConfig);
    }
    
    

    // API列表,工程庞大臃肿后可以将API拆分到单独的模块中
    var API = {
    		
    		doPost:doPost,
    		doComplexPost:doComplexPost,
    		doGet:doGet,
    		GetCheckboxValues:GetCheckboxValues,
    		SetCheckboxChecked:SetCheckboxChecked,
    		getCompanyNameById:getCompanyNameById,
    		isEnvProtection:isEnvProtection,
    		checkLoginUser:checkLoginUser,
    		
        Login: function(req,successCallback,errorCallback){ // 登录
            doPost($tool.getContext() + "login",req,successCallback,errorCallback);
        },
        LogOut:function(req,successCallback,errorCallback){ // 登出
            doPost($tool.getContext() + 'logout.do',req,successCallback,errorCallback);
        },
        ChangePwd:function(req,successCallback,errorCallback){ // 更改密码
            doPost($tool.getContext() + 'personCenter/changePwd.do',req,successCallback,errorCallback);
        },
        GetRoleList:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'personCenter/roleList.do',req,successCallback,errorCallback);
        },
        DeleteLog:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'log/delete.do',req,successCallback,errorCallback);
        },
        BatchDeleteLog:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'log/batchDelete.do',req,config,successCallback,errorCallback);
        },
        GetFirstClassMenus:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'menu/firstClassMenus.do',req,successCallback,errorCallback);
        },
        AddMenu:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'menu/add.do',req,successCallback,errorCallback);
        },
        DeleteMenu:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'menu/delete.do',req,successCallback,errorCallback);
        },
        GetMenu:function(req,successCallback,errorCallback){
            doPost($tool.getContext()+'menu/get.do',req,successCallback,errorCallback);
        },
        UpdateMenu:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext()+'menu/update.do',req,config,successCallback,errorCallback);
        },
        GetAllOrg:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'org/getAll.do',req,successCallback,errorCallback);
        },
        GetOrg:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'org/get.do',req,successCallback,errorCallback);
        },
        AddOrg:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'org/add.do',req,successCallback,errorCallback);
        },
        UpdateOrg:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'org/update.do',req,successCallback,errorCallback);
        },
        DeleteOrg:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'org/delete.do',req,successCallback,errorCallback);
        },
        AddRole:function(req,successCallback,errorCallback){
            doPost($tool.getContext()+'role/add.do',req,successCallback,errorCallback);
        },
        DeleteRole:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'role/delete.do',req,successCallback,errorCallback);
        },
        GetRole:function(req,successCallback,errorCallback){
            doPost($tool.getContext()+'role/get.do',req,successCallback,errorCallback);
        },
        UpdateRole:function(req,successCallback,errorCallback){
            doPost($tool.getContext()+'role/update.do',req,successCallback,errorCallback);
        },
        AddUser:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'sysUser/add.do',req,config,successCallback,errorCallback);
        },
        DeleteUser:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'sysUser/delete.do',req,config,successCallback,errorCallback);
        },
        InitPwd:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'sysUser/initPwd.do',req,successCallback,errorCallback);
        },
        GetUser:function(req,successCallback,errorCallback){
            doPost($tool.getContext()+'sysUser/get.do',req,successCallback,errorCallback);
        },
        UpdateUser:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'sysUser/update.do',req,config,successCallback,errorCallback);
        },
        GetUserInfo:function(req,successCallback,errorCallback){
            doPost($tool.getContext()+'personCenter/get.do',req,successCallback,errorCallback);
        },
        UpdateUserInfo:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'personCenter/update.do',req,config,successCallback,errorCallback);
        },
        AddPermission:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'sysPermission/add.do',req,config,successCallback,errorCallback);
        },
        DeletePermission:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'sysPermission/delete.do',req,successCallback,errorCallback);
        },
        UpdatePermission:function(req,config,successCallback,errorCallback){
            doComplexPost($tool.getContext() + 'sysPermission/update.do',req,config,successCallback,errorCallback);
        },
        GetPermissionInfo:function(req,successCallback,errorCallback){
            doPost($tool.getContext()+'sysPermission/get.do',req,successCallback,errorCallback);
        },
        AddIntergral:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'intergral/add.do',req,successCallback,errorCallback);
        },
        UpdateIntergral:function(req,successCallback,errorCallback){
        	doComplexPost($tool.getContext() + 'intergral/update.do',req,successCallback,errorCallback);
        },
        DeleteIntergral:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'intergral/delete.do',req,config,successCallback,errorCallback);
        },
        GetIntergral:function(req,successCallback,errorCallback){
            doPost($tool.getContext()+'intergral/get.do',req,successCallback,errorCallback);
        },
        AddInterDetailKind:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'interDetailKind/addDetailKind.do',req,successCallback,errorCallback);
        },
        GetInterDetailKind:function(req,successCallback,errorCallback){
            doPost($tool.getContext()+'interDetailKind/get.do',req,successCallback,errorCallback);
        },
        DeleteInterDetailKind:function(req,config,successCallback,errorCallback){
            doPost($tool.getContext() + 'interDetailKind/delete.do',req,config,successCallback,errorCallback);
        },
        AddIntergralDetail:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'interDetail/addDetail.do',req,successCallback,errorCallback);
        },
        GetInterDetail:function(req,successCallback,errorCallback){
            doPost($tool.getContext()+'interDetail/getInterDetail.do',req,successCallback,errorCallback);
        },
        DeleteInterDetail:function(req,config,successCallback,errorCallback){
        	doPost($tool.getContext() + 'interDetail/deleteInterDetail.do',req,config,successCallback,errorCallback);
        },
        GetInterList:function(req,successCallback,errorCallback){
            doPost($tool.getContext()+'intergralList/getInterList.do',req,successCallback,errorCallback);
        },
        DeleteInterList:function(req,config,successCallback,errorCallback){
        	doPost($tool.getContext() + 'intergralList/deleteInterList.do',req,config,successCallback,errorCallback);
        },
        AddIntergralPartList:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'intergralPart/addPartList.do',req,successCallback,errorCallback);
        },
        DeleteInterPartList:function(req,config,successCallback,errorCallback){
        	doPost($tool.getContext() + 'intergralPart/deleteInterPartList.do',req,config,successCallback,errorCallback);
        },
        GetInterPartList:function(req,successCallback,errorCallback){
            doPost($tool.getContext()+'intergralPart/getInterPartList.do',req,successCallback,errorCallback);
        },
        AddInterAccuseAdvice:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'interComSug/addAccuseAdvice.do',req,successCallback,errorCallback);
        },
        DeleteInterAccuseAdvice:function(req,config,successCallback,errorCallback){
        	doPost($tool.getContext() + 'interComSug/deleteAccuseAdvice.do',req,config,successCallback,errorCallback);
        },
        UpdateAccuseAdvice:function(req,successCallback,errorCallback){
        	doPost($tool.getContext() + 'interComSug/updateState.do',req,successCallback,errorCallback);
        },
        AddInterPersonApply:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'interItem/addSelfApply.do',req,successCallback,errorCallback);
        },
        AddInterItemAdd:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'interItemAdd/addItemAdd.do',req,successCallback,errorCallback);
        },
        AddManageAuthority:function(req,successCallback,errorCallback){
            doPost($tool.getContext() + 'interMagAuth/addMagAuth.do',req,successCallback,errorCallback);
        },
    };




    //输出扩展模块
    exports('$api', API);
});


