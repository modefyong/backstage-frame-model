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
    
    function initInfo() {
        var queryArgs = $tool.getQueryParam();//获取查询参数
        var id = queryArgs['id'];        
        var req = {
            id:id
        };
        $api.doGet($tool.getContext()+'hb/project/getProject.do',req,function (res) {
            var data = res.data;
            $("[name='company']").val(data.companyId);
            $("[name='projectName']").val(data.projectName);           
            $("[name='projectCode']").val(data.projectCode);
            $("input[name=projectProp][value="+data.projectProp+"]").attr("checked",true);
            if(null != data.projectProp || "" != data.projectProp){
            	$("input[name=projectProp]").attr("disabled","disabled");
            }
            $("input[name=documentType][value="+data.documentType+"]").attr("checked",true);
            if(null != data.documentType || "" != data.documentType){
            	$("input[name=documentType]").attr("disabled","disabled");
            }
            $("input[name=categoryManagementList][value="+data.categoryManagementList+"]").attr("checked",true);
            if(null != data.categoryManagementList || "" != data.categoryManagementList){
            	$("input[name=categoryManagementList]").attr("disabled","disabled");
            }
            $("[name='nationalEconomicCode']").val(data.nationalEconomicCode);           
            $("input[name=projectType][value="+data.projectType+"]").attr("checked",true);
            if(null != data.projectType || "" != data.projectType){
            	$("input[name=projectType]").attr("disabled","disabled");
            }
            $("[name='locationConstruction']").val(data.locationConstruction);
            $("[name='centralCoordinates']").val(data.centralCoordinates);
            $("[name='approvalOffice']").val(data.approvalOffice);
            $("[name='approvalSymbol']").val(data.approvalSymbol);           
            $("[name='permitNumber']").val(data.permitNumber);
            $("[name='emergencyPlanUrl']").val(data.emergencyPlanUrl);
            if($("[name='emergencyPlanUrl']").val() == ''){
            	$("#emergencyPlanDiv").show();
            	$("#emergencyPlan").val("暂无备案")
                $("#upEmergencyPlan").hide();
                $("#viewEmergencyPlan").hide();
            }else{
            	$("#viewEmergencyPlan").show();
                $("#upEmergencyPlan").hide();
            } 
            $("[name='wasteManagementUrl']").val(data.wasteManagementUrl);
            if($("[name='wasteManagementUrl']").val() == ''){
            	$("#wasteManagementDiv").show();
            	$("#wasteManagement").val("暂无协议")
                $("#upWasteManagement").hide();
                $("#viewWasteManagement").hide();
            }else{
            	$("#viewWasteManagement").show();
                $("#upWasteManagement").hide();
            } 
            $("input[name=projectStatus][value="+data.projectStatus+"]").attr("checked",true);
            if(null != data.projectStatus || "" != data.projectStatus){
            	$("input[name=projectStatus]").attr("disabled","disabled");
            }
            $("[name='hbInvestmentAll']").val(data.hbInvestmentAll);           
            $("[name='hbInvestment']").val(data.hbInvestment);
            $("[name='organizationCompany']").val(data.organizationCompany);
            $("[name='completedTime']").val(data.completedTime);
            if(null != data.completedTime || "" != data.completedTime){
            	$("input[name=completedTime]").attr("disabled","disabled");
            }
            $("[name='projectScale']").val(data.projectScale);
            $("input[name=projectChanges][value="+data.projectChanges+"]").attr("checked",true);
            if(null != data.projectChanges || "" != data.projectChanges){
            	$("input[name=projectChanges]").attr("disabled","disabled");
            }
            $("input[name=majorChanges][value="+data.majorChanges+"]").attr("checked",true);
            if(null != data.majorChanges || "" != data.majorChanges){
            	$("input[name=majorChanges]").attr("disabled","disabled");
            }
            $("input[name=reportingApproval][value="+data.reportingApproval+"]").attr("checked",true);
            if(null != data.reportingApproval || "" != data.reportingApproval){
            	$("input[name=reportingApproval]").attr("disabled","disabled");
            }
            $("[name='technologyImgUrl']").val(data.technologyImgUrl);
            if($("[name='technologyImgUrl']").val() == ''){
            	$("#technologyImgDiv").show();
            	$("#technologyImg").val("暂无流程图")
                $("#upTechnologyImg").hide();
                $("#viewTechnologyImg").hide();
            }else{
            	$("#viewTechnologyImg").show();
                $("#upTechnologyImg").hide();
            } 
            $("[name='materialCience']").val(data.materialCience);
            $("[name='productUrl']").val(data.productUrl);
            if($("[name='productUrl']").val() == ''){
            	$("#productDiv").show();
            	$("#product").val("暂无产品")
                $("#upProduct").hide();
                $("#viewProduct").hide();
            }else{
            	$("#viewProduct").show();
                $("#upProduct").hide();
            }
            $("[name='output']").val(data.output);
            $("[name='equipmentList']").val(data.equipmentList);
            form.render();//重新绘制表单，让修改生效
        });
    }
    //初始化信息
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
		$("#emergencyPlanDiv").hide();
		$("#wasteManagementDiv").hide();
		$("#technologyImgDiv").hide();
		$("#productDiv").hide();
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
});
window.selectCompany = function() {
	var table = layui.table,
	$ = layui.jquery,
	$tool = layui.$tool;
	layer.open({
        type : 2,
        area : [ "900px", '700px' ],
        title : "请选择企业",
        maxmin : false,
        content : '../searchCompanys.html',
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