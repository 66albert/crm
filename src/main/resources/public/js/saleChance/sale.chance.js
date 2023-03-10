layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    /**
     * 加载数据表格
     */
    let tableIns = table.render({
        id:'saleChanceTable'
        // elem 表示容器元素的id属性值
        ,elem: '#saleChanceList'
        // 容器的高度 full-差值（详情见api文档）
        ,height: 'full-125'
        // 单元格最小宽度
        ,cellMinWidth:95
        // 访问数据的URL（后台的数据接口）
        ,url: ctx + '/sale_chance/list' //数据接口
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
            ,{field: 'chanceSource', title: '机会来源', align:'center'}
            ,{field: 'customerName', title: '客户名称', align:'center'}
            ,{field: 'cgjl', title: '成功几率', align:'center'}
            ,{field: 'overview', title: '概要', align:'center'}
            ,{field: 'linkMan', title: '联系人', align:'center'}
            ,{field: 'linkPhone', title: '联系电话', align:'center'}
            ,{field: 'description', title: '描述', align:'center'}
            ,{field: 'createMan', title: '创建人', align:'center'}
            ,{field: 'uname', title: '分配人', align:'center'}
            ,{field: 'assignTime', title: '分配时间', align:'center'}
            ,{field: 'state', title: '分配状态', align:'center', templet: function (d) {
                // 调用函数返回格式化的结果
                return formatState(d.state);
                }}
            ,{field: 'devResult', title: '开发状态', align:'center', templet: function (d) {
                    // 调用函数返回格式化的结果
                return formatDevResult(d.devResult);
                }}
            ,{field: 'createDate', title: '创建时间', align:'center'}
            ,{field: 'updateDate', title: '更新时间', align:'center'}
            ,{title:'操作', templet:'#saleChanceListBar', fixed: 'right', align: 'center', minWidth:'150'}
        ]]
    });
    // console.log(tableIns);

    /**
     * 格式化状态值
     *  0 = 未分配
     *  1 = 已分配
     *  其它 = 未知
     * @param state
     */
    function formatState(state){
        if (state == 0) {
            return "<div style='color: yellowgreen'>未分配</div>"
        } else if (state == 1) {
            return "<div style='color: green'>已分配</div>"
        } else {
            return "<div style='color: red'>未知</div>"
        }
    }

    /**
     * 格式化开发状态值
     * 0 = 未开发
     * 1 = 开发中
     * 2 = 开发成功
     * 3 = 开发失败
     * 其它 = 未知
     * @param devResult
     */
    function formatDevResult(devResult) {
        if (devResult == 0) {
            return "<div style='color: yellowgreen'>未开发</div>"
        } else if (devResult == 1) {
            return "<div style='color: orange'>开发中</div>"
        } else if (devResult == 2) {
            return "<div style='color: green'>开发成功</div>"
        } else if (devResult == 3) {
            return "<div style='color: red'>开发失败</div>"
        } else {
            return "<div style='color: grey'>未知</div>"
        }
    }

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
                customerName: $("[name='customerName']").val() // 客户名称
                ,createMan: $("[name='createMan']").val() // 创建人
                ,state: $("#state").val()  // 状态
                //…
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    });

    /**
     * 监听头部工具栏事件（添加和删除）
     * 格式：table.on('toolbar(<数据表格的lay-filter属性值>)', function (data) {
     *
     *     })
     *
     */
    table.on('toolbar(saleChances)', function (data) {
        // data.event: 对应的元素上设置的lay-event属性值
        // 判断对应的事件类型
        if (data.event == "add") {
            // 添加操作
            openSaleChanceDialog();
        } else if (data.event == "del") {
            // 删除操作
            deleteSaleChance(data);
        }
    });

    /**
     * 批量删除多条营销机会
     * @param date
     */
    function deleteSaleChance(date) {
        // 获取数据表格选中的行数据  table.checkStatus("数据表格的id属性");
        var checkStatus = table.checkStatus("saleChanceTable");
        // 获取所有被选中的记录对应的数据
        var saleChanceData = checkStatus.data;
        // 判断用户是否选择了记录（选中行大于0）
        if (saleChanceData.length < 1) {
            layer.msg("请选择要删除的记录！",{icon:5});
            return;
        }
        // 询问用户是否确认删除
        layer.confirm('确定要删除选中记录吗？', {icon:3, title:'营销机会管理'},function (index) {
            // 关闭确认框
            layer.close(index);

            // 传递的参数是数组   ids=1&ids=2&ids=3
            var ids = "ids=";
            // 循环获取选中行记录的数据
            for(var i = 0; i < saleChanceData.length; i++) {
                if (i < saleChanceData.length - 1) {
                    ids = ids + saleChanceData[i].id + "&ids=";
                } else {
                    ids = ids + saleChanceData[i].id;
                }
            }
            
            // 发送Ajax请求，执行批量删除营销机会
            $.ajax({
               type:"post",
               url:ctx + "/sale_chance/delete",
               data:ids,    // 传递的参数是一个数组  ids=1&ids=2&ids=3
                success:function (result) {
                    // 判断删除结果
                    if (result.code == 200) {
                        // 删除成功
                        layer.msg("删除成功！",{icon:6});
                        tableIns.reload();
                    } else {
                        // 删除失败
                        layer.msg(result.msg, {icon:5});
                    }
                }
            });
        });

    }

    /**
     * 打开添加/修改营销机会数据的窗口
     *      如果营销机会ID为空，则为添加操作
     *      如果不为空，则为修改操作
     */
    function openSaleChanceDialog(saleChaneceId) {
        // 弹出层的标题
        var title = "<h2>营销机会管理 - 添加营销机会</h2>";
        var url = ctx + "/sale_chance/toSaleChancePage";

        // 判断营销机会ID是否为空
        if (saleChaneceId != null && saleChaneceId != '') {
            // 更新操作
            title = "<h2>营销机会管理 - 更新营销机会</h2>";
            // 请求地址传递营销机会的ID
            url += "?saleChanceId=" + saleChaneceId;
        }

        //iframe 层
        layui.layer.open({
            // 弹出层的类型
            type: 2,
            // 标题
            title: title,
            shadeClose: true,
            // 宽高
            area: ['500px', '620px'],
            // url地址
            content: url, //iframe的url
            // 可以最大化最小化
            maxmin:true
        });
    }


    /**
     * 行工具栏监听事件
     * table.on('tool(<数据表格的lay-filter>>)', function (data) {

    });
     */
    table.on('tool(saleChances)', function (data) {
        // 判断类型
        if (data.event == "edit") {
            // 编辑操作
            // 得到对应营销机会的ID
            var saleChanceId = data.data.id;
            // 打开窗口
            openSaleChanceDialog(saleChanceId);
        } else if (data.event == "del") {
            // 删除操作
            // 弹出确认框，询问用户是否确认删除
            layer.confirm('确定要删除该记录吗？', {icon:3, title:'营销机会管理'}, function (index) {
                // 关闭确认框
                layer.close(index);

                // 发送Ajax请求删除记录
                $.ajax({
                   type:"post",
                   url:ctx + "/sale_chance/delete",
                   data:{
                       ids:data.data.id
                   },
                    success:function (result) {
                       // 判断删除结果
                        if (result.code == 200) {
                            // 删除成功
                            layer.msg("删除成功！",{icon:6});
                            tableIns.reload();
                        } else {
                            // 删除失败
                            layer.msg(result.msg, {icon:5});
                        }
                    }
                });
            });
        }
    });


});
