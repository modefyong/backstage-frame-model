layui.config({
    base: '../../../common/js/'//定义基目录
}).extend({
    ajaxExtention: 'ajaxExtention',//加载自定义扩展，每个业务js都需要加载这个ajax扩展
    $tool: 'tool',
    $api:'api'
}).use(['form', 'layer', 'table', 'upload','tree','$api', 'jquery','laydate', 'ajaxExtention', '$tool'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        upload = layui.upload,
        laydate = layui.laydate,
        $ = layui.jquery,
        $tool = layui.$tool,
        $api = layui.$api;
    
    //表单验证
    form.verify({
        code: [/^[a-zA-Z0-9-]+$/, '字母数字-'],
        count: [/^[0-9\.]+$/, '整数或小数']
    });
    //信息初始化
    function initInfo() {
        var queryArgs = $tool.getQueryParam();//获取查询参数
        var id = queryArgs['id'];        
        var req = {
            id: id
        };
        $api.doGet($tool.getContext()+'hb/project/getProject.do',req,function (res) {
            var data = res.data;
            $("[name='company']").val(data.companyId);
            $("[name='projectName']").val(data.projectName);  
            if(null != data.projectName || "" != data.projectName){           	
            	$("#projectName").attr("readonly","readonly");
            }
            $("[name='projectCode']").val(data.projectCode);
            if(null != data.projectCode || "" != data.projectCode){           	
            	$("#projectCode").attr("readonly","readonly");
            }
            $("input[name=projectProp][value="+data.projectProp+"]").attr("checked",true);
            $("input[name=documentType][value="+data.documentType+"]").attr("checked",true);
            $("input[name=categoryManagementList][value="+data.categoryManagementList+"]").attr("checked",true);
            $("[name='nationalEconomicCode']").val(data.nationalEconomicCode);           
            $("input[name=projectType][value="+data.projectType+"]").attr("checked",true);
            $("[name='locationConstruction']").val(data.locationConstruction);
            $("[name='centralCoordinates']").val(data.centralCoordinates);
            $("[name='approvalOffice']").val(data.approvalOffice);
            $("[name='approvalSymbol']").val(data.approvalSymbol);           
            $("[name='permitNumber']").val(data.permitNumber);
            $("[name='emergencyPlanUrl']").val(data.emergencyPlanUrl);
            if($("[name='emergencyPlanUrl']").val() == ''){
                $("#upEmergencyPlan").show();
                $("#viewEmergencyPlan").hide();
            }else{
            	$("#viewEmergencyPlan").show();
                $("#upEmergencyPlan").show();
            } 
            $("[name='wasteManagementUrl']").val(data.wasteManagementUrl);
            if($("[name='wasteManagementUrl']").val() == ''){
                $("#upWasteManagement").show();
                $("#viewWasteManagement").hide();
            }else{
            	$("#viewWasteManagement").show();
                $("#upWasteManagement").show();
            }
            $("input[name=projectStatus][value="+data.projectStatus+"]").attr("checked",true);
            $("[name='hbInvestmentAll']").val(data.hbInvestmentAll);           
            $("[name='hbInvestment']").val(data.hbInvestment);
            $("[name='organizationCompany']").val(data.organizationCompany);
            $("[name='completedTime']").val(data.completedTime);
            $("[name='projectScale']").val(data.projectScale);
            $("input[name=projectChanges][value="+data.projectChanges+"]").attr("checked",true);
            $("input[name=majorChanges][value="+data.majorChanges+"]").attr("checked",true);
            $("input[name=reportingApproval][value="+data.reportingApproval+"]").attr("checked",true);
            $("[name='technologyImgUrl']").val(data.technologyImgUrl);
            if($("[name='technologyImgUrl']").val() == ''){
                $("#upTechnologyImg").show();
                $("#viewTechnologyImg").hide();
            }else{
            	$("#viewTechnologyImg").show();
                $("#upTechnologyImg").show();
            } 
            $("[name='materialCience']").val(data.materialCience);
            $("[name='productUrl']").val(data.productUrl);
            if($("[name='productUrl']").val() == ''){
                $("#upProduct").show();
                $("#viewProduct").hide();
            }else{
            	$("#viewProduct").show();
                $("#upProduct").show();
            }
            $("[name='output']").val(data.output);
            $("[name='equipmentList']").val(data.equipmentList);
            form.render();//重新绘制表单，让修改生效
        });
    }
    //页面初始化
	function init() {
		$api.checkLoginUser();	 
		//竣工日期
		laydate.render({
			elem: '#completedTime'
			,trigger: 'click'
		});
		//获取查询参数
		var queryArgs = $tool.getQueryParam();
		var id = queryArgs['id'];
		if(id){
			$("#companyDiv").hide();
			initInfo();
		}
		//隐藏查看按钮
		$("#viewEmergencyPlan").hide();
		$("#viewWasteManagement").hide();
		$("#viewTechnologyImg").hide();
		$("#viewProduct").hide();
	}
	init();  
	//添加应急备案
	upload.render({
		 elem: '#upEmergencyPlan'
		,url: $tool.getContext()+'upload'
		,auto: true
		,accept: 'file'
		,done: function(res){	    		    	
			layer.msg("添加成功，请及时提交！");
			$("#emergencyPlanUrl").val(res.data);//得到文件地址
			$("#viewEmergencyPlan").show();
		}
		,error: function(){
			layer.msg("上传失败")
		}
	});   
	//查看应急备案
	$(document).on('click','#viewEmergencyPlan',function(){
		window.open($("#emergencyPlanUrl").val());
	});
	//添加危废处置协议
	upload.render({
		 elem: '#upWasteManagement'
		,url: $tool.getContext()+'upload'
		,auto: true
		,accept: 'file'
		,done: function(res){	    		    	
			layer.msg("添加成功，请及时提交！");
			$("#wasteManagementUrl").val(res.data);//得到文件地址
			$("#viewWasteManagement").show();
		}
		,error: function(){
			layer.msg("上传失败")
		}
	});   
	//查看危废处置协议
	$(document).on('click','#viewWasteManagement',function(){
		window.open($("#wasteManagementUrl").val());
	});
	//添加工业流程图
	upload.render({
		 elem: '#upTechnologyImg'
		,url: $tool.getContext()+'upload'
		,auto: true
		,accept: 'file'
		,done: function(res){	    		    	
			layer.msg("添加成功，请及时提交！");
			$("#technologyImgUrl").val(res.data);//得到文件地址
			$("#viewTechnologyImg").show();
		}
		,error: function(){
			layer.msg("上传失败")
		}
	});   
	//查看工业流程图
	$(document).on('click','#viewTechnologyImg',function(){
		window.open($("#technologyImgUrl").val());
	});
	//添加产品
	upload.render({
		 elem: '#upProduct'
		,url: $tool.getContext()+'upload'
		,auto: true
		,accept: 'file'
		,done: function(res){	    		    	
			layer.msg("添加成功，请及时提交！");
			$("#productUrl").val(res.data);//得到文件地址
			$("#viewProduct").show();
		}
		,error: function(){
			layer.msg("上传失败")
		}
	});   
	//查看产品
	$(document).on('click','#viewProduct',function(){
		window.open($("#productUrl").val());
	});
    //表单提交   
    form.on("submit(saveOrUpdateVoice)", function (data) {
    	var queryArgs = $tool.getQueryParam();//获取查询参数
      	var id = queryArgs['id'];
      	var userName = data.field.userName;
      	var company = data.field.company;
        var projectName = data.field.projectName;
        var projectCode = data.field.projectCode;
        var projectProp = data.field.projectProp;
        var documentType = data.field.documentType;
        var categoryManagementList = data.field.categoryManagementList;
        var nationalEconomicCode = data.field.nationalEconomicCode;
        var projectType = data.field.projectType;
        var locationConstruction = data.field.locationConstruction;
        var centralCoordinates = data.field.centralCoordinates;
        var approvalOffice = data.field.approvalOffice;
        var approvalSymbol = data.field.approvalSymbol;
        var permitNumber = data.field.permitNumber;
        var emergencyPlanUrl = data.field.emergencyPlanUrl;
        var wasteManagementUrl = data.field.wasteManagementUrl;
        var projectStatus = data.field.projectStatus;
        var hbInvestmentAll = data.field.hbInvestmentAll;
        var hbInvestment = data.field.hbInvestment;
        var organizationCompany = data.field.organizationCompany;
        var completedTime = data.field.completedTime;
        var projectScale = data.field.projectScale;
        var projectChanges = data.field.projectChanges;
        var majorChanges = data.field.majorChanges;
        var reportingApproval = data.field.reportingApproval;
        var technologyImgUrl = data.field.technologyImgUrl;
        var materialCience = data.field.materialCience;
        var productUrl = data.field.productUrl;
        var output = data.field.output;
        var equipmentList = data.field.equipmentList;        
        var req = {
			 id: id,
			 userName: userName,
			 companyId: company,
			 projectName: projectName,
			 projectCode: projectCode,
			 projectProp: projectProp,
			 documentType: documentType,
			 categoryManagementList: categoryManagementList,
			 nationalEconomicCode: nationalEconomicCode,
			 projectType: projectType,
			 locationConstruction: locationConstruction,
			 centralCoordinates: centralCoordinates,
			 approvalOffice: approvalOffice,
			 approvalSymbol: approvalSymbol,
			 permitNumber: permitNumber,
			 emergencyPlanUrl: emergencyPlanUrl,
			 wasteManagementUrl: wasteManagementUrl,
			 projectStatus: projectStatus,
			 hbInvestmentAll: hbInvestmentAll,
			 hbInvestment: hbInvestment,
			 organizationCompany: organizationCompany,
			 completedTime: completedTime,
			 projectScale: projectScale,
			 projectChanges: projectChanges,
			 majorChanges: majorChanges,
			 reportingApproval: reportingApproval,
			 technologyImgUrl: technologyImgUrl,
			 materialCience: materialCience,
			 productUrl: productUrl,
			 output: output,
			 equipmentList: equipmentList			 
        };
        if(null == userName || "" == userName){
        	if(null == company || "" == company){
            	layer.msg("请选择企业");
            }else{
            	$api.doComplexPost($tool.getContext()+'hb/project/addOrUpdateProject.do',JSON.stringify(req),{contentType:"application/json;charset=UTF-8"},function (data) {
    	            var count = data.data;
    	        	if(count == 1){
    		        	layer.msg("成功！", {time: 1000}, function () {
    		                layer.closeAll("iframe");
    		                //刷新父页面
    		                parent.location.reload();
    		            });
    	        	}else{
    	        		layer.msg("失败！", {time: 1000}, function () {
    		                layer.closeAll("iframe");
    		                //刷新父页面
    		                parent.location.reload();
    		            });
    	        	}
    	        });
            }
        }else{
	        $api.doComplexPost($tool.getContext()+'hb/project/addOrUpdateProject.do',JSON.stringify(req),{contentType:"application/json;charset=UTF-8"},function (data) {
	            var count = data.data;
	        	if(count == 1){
		        	layer.msg("成功！", {time: 1000}, function () {
		                layer.closeAll("iframe");
		                //刷新父页面
		                parent.location.reload();
		            });
	        	}else{
	        		layer.msg("失败！", {time: 1000}, function () {
		                layer.closeAll("iframe");
		                //刷新父页面
		                parent.location.reload();
		            });
	        	}
	        });
        }
        return false;
    })
});
window.selectCompany = function() {
	var table = layui.table,
	$ = layui.jquery,
	$tool = layui.$tool;
	layer.open({
        type: 2,
        area: [ "900px", '700px' ],
        title: "请选择企业",
        maxmin: false,
        content: '../searchCompanys.html',
        btn: ['确定', '取消'],
        yes: function(index, layero) {            
             	var companyId = $(layero).find('iframe')[0].contentWindow.companyId.value; //将子窗口中的 companyId 抓过来
             	var companyName = $(layero).find('iframe')[0].contentWindow.companyName.value; //将子窗口中的 companyName 抓过来
             	if(companyId == null || companyId == ""){
             		layer.msg('请选择一个企业!');
             	}else{
             		$("[name='company']").val(companyId);
             		$("[name='companyName']").val(companyName);
             		layer.close(index);
             	}
			 }
    });
}