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
    
    //初始化信息
    function init() {
        var queryArgs = $tool.getQueryParam();//获取查询参数
        var id = queryArgs['id'];        
        var req = {
            id: id
        };
        $api.doGet($tool.getContext()+'approval/getManageAuthority.do',req,function (res) {
            var data = res.data;
            $("[name='approvalHeading']").val(data.staff + '提交的积分-主管权限分申报');
            $("[name='grantScore']").val(data.grantScore);           
            $("[name='shouldScore']").val(data.shouldScore);
            $("[name='residueScore']").val(data.residueScore);
            $("[name='department']").val(data.department);
            $("[name='approvalOpinion']").val(data.approvalOpinion);
            form.render();//重新绘制表单，让修改生效
        });
    }
	init();  
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
});