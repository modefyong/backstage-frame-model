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
    var url;
    //表格初始化
    function defineTable() {
    	$api.checkLoginUser();	 	
        tableIns = table.render({
        	elem: '#itemAddList-data'
           ,toolbar: '#toolbarDemo'
           ,height: 600
           ,url: $tool.getContext() + 'approval/getItemAddList.do' //数据接口
           ,method: 'post'
           ,where: {
        	   'loginName': $("#loginName").val(),
        	   'roleFlag': $("#roleFlag").val()
           }
           ,page: true //开启分页
           ,cols: [[ //表头
        	   {type: 'numbers', title: '序号', fixed: 'left'}
          	  ,{field: 'addPerson', title: '申请人', width: 120}
          	  ,{field: 'description', title: '申请摘要', width: 240}
          	  ,{field: 'checkState', title: '状态', width: 180, templet: '#checkStateTemplate'}
          	  ,{field: 'attachmentUrl', title: '附件', width: 180, templet: '#attachmentTemplate'}
          	  ,{field: 'addTime', title: '发起时间', width: 180, templet: "#timeTemplate"}
          	  ,{fixed: 'right', title: '操作', width: 260, align: 'center', toolbar: '#barDemo'} //这里的toolbar值是模板元素的选择器
          	]]
           ,done: function (res, curr) {//请求完毕后的回调
                //如果是异步请求数据方式，res即为你接口返回的信息.curr：当前页码
            	$api.isEnvProtection();
            }   
        });        
        //为toolbar添加事件响应
        table.on('tool(itemAddListFilter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var row = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象
            //区分事件
            if(layEvent === 'view'){ //查看
            	view(row.id);
            }else if(layEvent === 'approval'){  //审批
            	approval(row.id);
            }else if(layEvent === 'revoke'){  //撤销
            	revoke(row.id);
            }
        });
    }
    defineTable();        
    //查询
    form.on("submit(queryMenu)", function (data) {
    	var loginName = $("#loginName").val();
    	var roleFlag = $("#roleFlag").val();
    	var checkType = $('#checkType option:selected').val();
        //表格重新加载
        tableIns.reload({
            where:{
            	loginName: loginName,
            	roleFlag: roleFlag,
            	checkType: checkType
            }
        });
        return false;
    });
    //清空查询条件
    $(".clear_search").click(function(){
    	$("#checkType").val("");
    	$("#checkType").find("option[value='']").attr("selected","selected");
    	layui.form.render("select");
    });
    //查看
    function view(id){
        parent.layer.open({
            title: "项目添加项详情",
            type: 2,
            maxmin: true,
            area: ['1000px', '600px'], //自定义文本域宽高
            content: "page/approval/itemAdd/viewItemAdd.html?id="+id,
            success: function (layero, index) {
            }
        });
    }
    //审批
    function approval(id){
        parent.layer.open({
            title: "项目添加项审批",
            type: 2,
            maxmin: true,
            area: ['800px', '600px'], //自定义文本域宽高
            content: "page/approval/itemAdd/approval.html?id="+id,
            success: function (layero, index) {
            }
        });
    }
    //撤销
    function revoke(id){
        layer.confirm('确认撤销吗？', function (confirmIndex) {
            layer.close(confirmIndex);//关闭confirm
            var req = {
        		id: id
            };
            $api.doPost($tool.getContext() + 'approval/revokeItemAdd.do',req,function (data) {
            	if(data.data==1){
            		 layer.msg("撤销成功",{time:1000},function(){           			 
                         //重新加载表格
                         tableIns.reload();
                         form.render();
                     });
            	}else{
            		 layer.msg("撤销失败",{time:1000},function(){
                         //重新加载表格
                         tableIns.reload();
                         form.render();
                     });
            	}
            });
        });
    }    
});