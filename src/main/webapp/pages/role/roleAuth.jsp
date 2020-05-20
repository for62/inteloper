<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html style="width:100%;height:100%;overflow:hidden">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>角色管理-五常优质水稻溯源监管平台</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/index.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/themes/color.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/form2js.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
</head>
<body class="easyui-layout">
<div region="center" border="false" style="padding:5px;">
    <fieldset class="fieldset_common_style">
        <form id="roleform" name="roleform" method="post" action="${pageContext.request.contextPath}/role/list">
            <table class="table_common_style">
                <tr>
                    <td class="table_common_td_label_style">角色编号：</td>
                    <td class="table_common_td_txt_style">
                        <input name="roleCode" id="roleCode" value="${role.roleCode }" class="easyui-textbox"
                               style="width:200px;height:25px;" readonly>
                    </td>
                </tr>
                <tr>
                    <td class="table_common_td_label_style">角色名称：</td>
                    <td class="table_common_td_txt_style">
                        <input name="roleName" value="${role.roleName }" class="easyui-textbox"
                               style="width:200px;height:25px" readonly>
                    </td>
                <tr>
                <tr>
                    <td class="table_common_td_label_comment_style">备注:</td>
                    <td class="table_common_td_txt_style">
                        <textarea name="remark" rows="2" style="width:250px;" readonly>${role.remark }</textarea>
                    </td>
                </tr>
                <tr>
                    <td class="table_common_td_label_comment_style">角色权限:</td>
                    <td heigth="50px">
                        <div class="easyui-panel" style="padding:5px">
                            <ul id="authTree" class="easyui-tree"
                                data-options='data:${jsonData},animate:true,checkbox:true'></ul>
                        </div>
                    </td>
                </tr>
                <tr height="60">
                    <td colspan="2" align="center">
                        <a href="#" id="saveBt" class="easyui-linkbutton" onclick="save()"
                           data-options="iconCls:'icon-save'">保存</a>
                        <a href="#" id="cancalBt" class="easyui-linkbutton" style="margin-left:30px;"
                           onclick="closeDialog()" data-options="iconCls:'icon-cancel'">取消</a>
                    </td>
                </tr>
            </table>
        </form>
    </fieldset>
</div>
<script type="text/javascript">
    <%--alert(${jsonData});--%>
    function save() {
        var role={roleCode:$("#roleCode").val(),functions:[]};
        var funIds = [];
        var nodes = $('#authTree').tree('getChecked');
        for (var i = 0; i < nodes.length; i++) {
            funIds.push(nodes[i].id);
            role.functions.push({functionCode:nodes[i].id});
        }
        // alert(funIds);
        var roleCode = $("#roleCode").val();
        $("#saveBt").linkbutton("disable");
        $("#cancalBt").linkbutton("disable");
        // showLoading();
        var paramRole={roleCode: roleCode, functions: funIds};
        alert(paramRole);
        $.ajax({
            url: "${pageContext.request.contextPath}/role/saveRoleAuth.do",
            // data : JSON.stringify(),
            contentType:"application/json",
            data: JSON.stringify(paramRole),
            // data: {role:role},
            type: "post",
            success: function (e) {
                // hideLoading();
                $("#saveBt").linkbutton("enable");
                $("#cancalBt").linkbutton("enable");
                if (200 == e.status) {
                    $.messager.alert('提示', '操作成功。', 'info');
                    closeDialog();
                } else {
                    $.messager.alert('错误', e.msg, 'error');
                }
            },
            dataType: "json"
        });
        /* 	Public.ajaxGet('


        ${pageContext.request.contextPath}/role/saveRoleAuth', {roleCode : roleCode, funIds : funIds}, function(e) {
		hideLoading();
		$("#saveBt").linkbutton("enable");
		$("#cancalBt").linkbutton("enable");
		if (200 == e.status) {
			$.messager.alert('提示','操作成功。','info');
			closeDialog();
		} else {
			$.messager.alert('错误',e.msg,'error');
		}
	});  */
    }

    /*
     * 通用post请求，返回json url:请求地址， params：传递的参数{...}， callback：请求成功回调
     */
    Public.ajaxPost = function (url, params, callback) {
        $.ajax({
            type: "POST",
            url: url,
            contentType: 'application/json; charset=utf-8',
            data: params,
            dataType: "json",
            async: false,
            success: function (data, status) {
                callback(data);
            },
            error: function (err) {
                /**
                 parent.Public.tips({
				type : 1,
				content : '操作失败，请检查您的网络链接！'
			});
                 **/
                $.messager.alert('错误', "操作失败，请检查您的网络链接！", 'error');
            }
        });
    };

    function closeDialog() {
        $('#addDialog').dialog('close');
    }

</script>
</body>
</html>