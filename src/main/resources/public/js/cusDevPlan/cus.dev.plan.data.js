layui.use(['table', 'layer'], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    /**
     * 加载计划项数据表格
     */
    let tableIns = table.render({
        id: 'cusDevPlanTable'
        // elem 表示容器元素的id属性值
        , elem: '#cusDevPlanList'
        // 容器的高度 full-差值（详情见api文档）
        , height: 'full-125'
        // 单元格最小宽度
        , cellMinWidth: 95
        // 访问数据的URL（后台的数据接口）
        , url: ctx + '/cus_dev_plan/list?saleChanceId=' + $("[name='id']").val() //数据接口
        , page: true //开启分页
        // 每页默认显示的数量
        , limit: 10
        // 每页行数的可选项
        , limits: [10, 20, 30, 40, 50]
        // 开启头部工具栏
        , toolbar: '#toolbarDemo'
        , cols: [[ //表头
            /**
             * field：要求对应的field属性值与返回的数据中对应的属性字段名一致
             * title：设置列的标题
             * sort：是否允许排序（默认false）
             * fixed：固定列（默认left/true）
             * toolbarDemo
             */
            {type: 'checkbox', fixed: 'center'}
            , {field: 'id', title: '编号', sort: true, fixed: 'left'}
            , {field: 'planItem', title: '计划项', align: 'center'}
            , {field: 'planDate', title: '计划时间', align: 'center'}
            , {field: 'exeAffect', title: '执行效果', align: 'center'}
            , {field: 'createDate', title: '创建时间', align: 'center'}
            , {field: 'updateDate', title: '更新时间', align: 'center'}
            , {title: '操作', templet: '#cusDevPlanListBar', fixed: 'right', align: 'center', minWidth: '150'}
        ]]
    });

    /**
     * 监听行工具栏
     */
    table.on('tool(cusDevPlans)', function (data) {
        if (data.event == 'edit') {
            // 更新操作
            // 打开添加或修改计划项的页面
            openAddOrUpdateCusDevPlanDialog(data.data.id);
        }
    });

    /**
     * 监听头部工具栏
     */
    table.on('toolbar(cusDevPlans)', function (data) {
        if (data.event == 'add') {
            // 添加计划项
            // 打开添加或修改计划项的页面

            openAddOrUpdateCusDevPlanDialog();
        } else if (data.event == 'success') {
            // 开发成功

        } else if (data.event == 'false') {
            // 开发失败

        }
    });

    function openAddOrUpdateCusDevPlanDialog(id) {
        var title = "计划项管理 - 添加计划项";
        var url = ctx + "/cus_dev_plan/toAddOrUpdateCusDevPlanPage?sId=" + $("[name='id']").val();

        // 判断计划项的ID是否为空（如果为空，则表示添加；不为空则表示更新）
        if (id != null && id != "") {
            // 更新操作
            title = "计划项管理 - 更新计划项";
            url += "&id="+id;
        }

        // iframe层
        layui.layer.open({
            // 类型
            type: 2,
            // 标题
            title: title,
            // 宽高
            area: ['500px', '300px'],
            // url地址
            content: url,
            // 可以最大最小化
            maxmin:true
        });
    }
});
