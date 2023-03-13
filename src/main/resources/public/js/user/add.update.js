layui.use(['form', 'layer', 'formSelects'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;
    var formSelects = layui.formSelects;

    /**
     * 关闭弹出层
     */
    $("#closeBtn").click(function () {
        //当你在iframe页面关闭自身时
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    });

    /**
     * 表单submit监听
     */
    form.on('submit(addOrUpdateUser)', function (data) {
        // 提价数据时的加载层
        var index = top.layer.msg("数据提交中，请稍等...", {
            icon:16,    // 图标
            time:false,  // 不关闭
            shade:0.8   // 设置遮罩的透明度
        });

        // 得到所有的表单元素的值
        var formData = data.field;
        // 请求的地址
        var url = ctx + "/user/add";

        // 判断用户ID是否为空，如果不为空则为更新操作
        if ($("[name='id']").val()) {
            // 更新操作
            url = ctx + "/user/update";
        }

        $.post(url, data.field, function (result) {
            // 判断操作是否执行成功 200=成功，否则失败
            if (result.code == 200) {
                // 成功
                // 提示成功
                top.layer.msg("操作成功！", {icon:6});
                // 关闭加载层
                top.layer.close(index);
                // 关闭弹出层
                layer.closeAll('iframe');
                // 刷新父窗口，重新加载数据
                parent.location.reload();
            } else {
                // 失败
                layer.msg(result.msg, {icon:5});
            }
        });

        // 阻止表单提交
        return false;
    });

    /**
     * 加载下拉框
     *  * 配置远程搜索, 请求头, 请求参数, 请求类型等
     *  *
     *  * formSelects.config(ID, Options, isJson);
     *  *
     *  * @param ID        xm-select的值
     *  * @param Options   配置项
     *  * @param isJson    是否传输json数据, true将添加请求头 Content-Type: application/json; charset=UTF-8
     */
    var userId = $("[name='id']").val();
    formSelects.config('selectId', {
        type:'post', // 请求方式
        searchUrl: ctx + '/role/queryAllRoles?userId='+userId, // 请求地址
        keyName:'roleName', // 下拉框文本值，要与返回的数据中，对应的key一致
        keyVal:'id',   // 下拉框的
    }, true);
});