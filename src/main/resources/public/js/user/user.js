layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    /**
     * 加载数据表格
     */
    let tableIns = table.render({
        id:'userTable'
        // elem 表示容器元素的id属性值
        ,elem: '#userList'
        // 容器的高度 full-差值（详情见api文档）
        ,height: 'full-125'
        // 单元格最小宽度
        ,cellMinWidth:95
        // 访问数据的URL（后台的数据接口）
        ,url: ctx + '/user/list' //数据接口
        ,page: true //开启分页
        // 每页默认显示的数量
        ,limit:10
        // 每页行数的可选项
        ,limits:[10,20,30,40,50]
        // 开启头部工具栏
        ,toolbar:'#toolbarDemo'
        ,cols: [[ //表头
            /**
             * field：要求对应的field属性值与返回的数据中对应的属性字段名一致
             * title：设置列的标题
             * sort：是否允许排序（默认false）
             * fixed：固定列（默认left/true）
             * toolbarDemo
             */
            {type:'checkbox', fixed: 'center'}
            ,{field: 'id', title: '编号', sort: true, fixed: 'left'}
            ,{field: 'userName', title: '用户名', align:'center'}
            ,{field: 'trueName', title: '真实姓名', align:'center'}
            ,{field: 'email', title: '邮箱', align:'center'}
            ,{field: 'phone', title: '用户号码', align:'center'}
            ,{field: 'createDate', title: '创建时间', align:'center'}
            ,{field: 'updateDate', title: '更新时间', align:'center'}
            ,{title:'操作', templet:'#userListBar', fixed: 'right', align: 'center', minWidth:'150'}
        ]]
    });

    /**
     * 搜索按钮的点击事件
     */
    $(".search_btn").click(function () {
        /**
         * 表格重载
         *  多条件查询
         */
        tableIns.reload({
            // where：需要传递给后端的参数
            where: { //设定异步数据接口的额外参数，任意设
                // 通过文本框和下拉框的值设置传递的参数
                userName: $("[name='userName']").val() // 用户名称
                ,email: $("[name='email']").val() // 邮箱
                ,phone: $("[name='phone']").val()  // 电话
                //…
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    });

});