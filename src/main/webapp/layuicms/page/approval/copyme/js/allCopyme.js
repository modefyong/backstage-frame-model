layui.config({
    base: '../../../common/js/'//定义基目录
}).extend({
    ajaxExtention: 'ajaxExtention',//加载自定义扩展，每个业务js都需要加载这个ajax扩展
    $tool: 'tool',
    $api:'api'
}).use(['form', 'layer', '$api','jquery', 'upload','table','laydate', 'laypage','laytpl', 'ajaxExtention', '$tool'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery,
        laypage = layui.laypage,
        upload = layui.upload,
        laytpl = layui.laytpl,
        $tool = layui.$tool,
        table = layui.table,
        laydate = layui.laydate,
        $api = layui.$api;
    	
    //定义表格实例
    var tableIns;
    //表格初始化
    function defineTable() {   	
    	$api.checkLoginUser();  	 	
        tableIns = table.render({
        	elem: '#projectList-data'
           ,toolbar: '#toolbarDemo'
           ,height: 600
           ,url: $tool.getContext() + 'hb/project/getProjectPageList.do' //数据接口
           ,method: 'post'
           ,where: {'userName': $("#userName").val()}
           ,page: true //开启分页
           ,cols: [[ //表头
        	   {type: 'numbers', title: '序号', fixed: 'left'}
          	  ,{field: 'projectName', title: '项目名称', width: 180}
          	  ,{field: 'projectProp', title: '项目性质', width: 240, templet: '#projectPropTemplate' }
          	  ,{field: 'projectType', title: '项目类型', width: 180, templet: '#projectTypeTemplate'}
          	  ,{field: 'projectStatus', title: '项目运行状态', width: 300, templet: '#projectStatusTemplate'}
          	  ,{field: 'technologyImgUrl', title: '工艺流程图', width: 240, templet: '#technologyImgTemplate'}
          	  ,{fixed: 'right', title: '操作', width: 360, align: 'center', toolbar: '#barDemo'} //这里的toolbar值是模板元素的选择器
          	]]
           ,done: function (res, curr) {//请求完毕后的回调
                //如果是异步请求数据方式，res即为你接口返回的信息.curr：当前页码
            	$api.isEnvProtection();
            }   
        });        
        //为toolbar添加事件响应
        table.on('tool(projectListFilter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var row = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象
            //区分事件
            if(layEvent === 'del'){ //删除
            	del(row.id);
            }else if(layEvent === 'edit'){ //编辑
            	edit(row.id);
            }else if(layEvent === 'view'){ //查看
            	view(row.id);
            }
        });
    }
    defineTable();        
    //查询
    form.on("submit(queryMenu)", function (data) {
    	var userName = data.field.userName;
    	var company = data.field.company; 
        var projectName = data.field.projectName; 
        //表格重新加载
        tableIns.reload({
            where:{
            	userName: userName,
            	company: company,
            	projectName: projectName
            }
        });
        return false;
    }); 
    //清空查询条件
    $(".clear_search").click(function(){
    	$("#company").val("");
    	$("#companyName").val("");
    	$("#projectName").val("");
    });
    //添加
    $(".add_btn").click(function () {
    	parent.layer.open({
            title: "添加项目",
            type: 2,
            maxmin: true,
            area: ['800px', '600px'], //自定义文本域宽高
            content: "page/hb/project/saveOrUpdateProject.html",
            success: function (layero, index) {
            }
        });
    });
    //编辑
    function edit(id){
        parent.layer.open({
            title: "修改项目",
            type: 2,
            maxmin: true,
            area: ['800px', '600px'], //自定义文本域宽高
            content: "page/hb/project/saveOrUpdateProject.html?id="+id,
            success: function (layero, index) {
            }
        });
    }
    //查看
    function view(id){
        parent.layer.open({
            title: "查看项目",
            type: 2,
            maxmin: true,
            area: ['800px', '600px'], //自定义文本域宽高
            content: "page/hb/project/viewProject.html?id="+id,
            success: function (layero, index) {
            }
        });
    }
    //删除
    function del(id){
        layer.confirm('确认删除吗？', function (confirmIndex) {
            layer.close(confirmIndex);//关闭confirm
            var req = {
        		id: id
            };
            $api.doPost($tool.getContext() + 'hb/project/delProject.do',req,function (data) {
            	if(data.data==1){
            		 layer.msg("删除成功",{time:1000},function(){           			 
                         //重新加载表格
                         tableIns.reload();
                         form.render();
                     });
            	}else{
            		 layer.msg("删除失败",{time:1000},function(){
                         //重新加载表格
                         tableIns.reload();
                         form.render();
                     });
            	}
            });
        });
    }    
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