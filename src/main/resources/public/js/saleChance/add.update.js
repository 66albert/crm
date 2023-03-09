layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;


    /**
     * 监听表单submit事件
     * form.on('submit(<按钮元素lay-filter属性值>)', function (date) {
     *
     *     });
     */
    form.on('submit(addOrUpdateSaleChance)', function (data) {
        // 提价数据时的加载层
        var index = layer.msg("数据提交中，请稍等...", {
            icon:16,    // 图标
            time:false,  // 不关闭
            shade:0.8   // 设置遮罩的透明度
        });

        // 发送Ajax请求，请求成功则关闭加载层
        var url = ctx + "/sale_chance/add"; // 添加操作

        // 通过营销机会的id来判断当前需要执行添加操作还是修改操作
        // 如果营销机会ID为空，则表示添加操作；如果不为空，则表示更新操作
        // 通过获取隐藏域中的id
        var saleChanceId = $("[name='id']").val();
        if (saleChanceId != null && saleChanceId != "") {
            // 更新操作
            url = ctx + "/sale_chance/update"
        }

        $.post(url, data.field, function (result) {
            // 判断操作是否执行成功 200=成功，否则失败
            if (result.code == 200) {
                // 成功
                // 提示成功
                layer.msg("操作成功！", {icon:6});
                // 关闭加载层
                parent.layer.close(index);
                // 关闭弹出层
                parent.layer.closeAll('iframe');
                // 刷新父窗口，重新加载数据
                parent.location.reload();
            } else {
                // 失败
                layer.msg(result.msg, {icon:5});
            }
        })

        // 阻止表单提交
        return false;

    });


    /**
     * 关闭弹出层
     */
    $("#closeBtn").click(function () {
        //当你在iframe页面关闭自身时
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    });


    
});