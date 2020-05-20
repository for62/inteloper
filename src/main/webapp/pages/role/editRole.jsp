<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html style="width:100%;height:100%;overflow:hidden">
<head>
    <title>修改角色-五常优质水稻溯源监管平台</title>
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
        <form id="updroleform" action="${pageContext.request.contextPath}/role/updateRole.do?id=${roleEdit.id}" class="easyui-form"
              method="post" data-options="novalidate:true">
            <table class="table_common_style">
                <tr>
                    <td class="table_common_td_label_style">角色编号:</td>
                    <td class="table_common_td_txt_style">
                        <input class="easyui-textbox" type="text" id="roleCode" name="roleCode"
                               style="width:200px;" value="${roleEdit.roleCode}"></input>
                        <span class="span_common_mustinput_style">*</span>
                    </td>
                </tr>
                <tr>
                    <td class="table_common_td_label_style">角色名:</td>
                    <td class="table_common_td_txt_style">
                        <input class="easyui-textbox" type="text" id="roleName" name="roleName"
                               style="width:200px;" value="${roleEdit.roleName}"></input>
                        <span class="span_common_mustinput_style">*</span>
                    </td>
                </tr>
                <tr>
                    <td class="table_common_td_label_comment_style">备注:</td>
                    <td class="table_common_td_txt_style">
                        <textarea name="remark" id="remark" rows="2" style="width:250px;">${roleEdit.remark }</textarea>
                    </td>
                </tr>
                <tr height="50">
                    <td colspan="2" align="center">
                        <a href="#" class="easyui-linkbutton" onclick="editForm_check()"
                           data-options="iconCls:'icon-save'">保存</a>
                        <a href="#" class="easyui-linkbutton" style="margin-left:40px;" onclick="closeDialog()"
                           data-options="iconCls:'icon-cancel'">取消</a>
                    </td>
                </tr>

            </table>
        </form>
    </fieldset>
</div>
<script type="text/javascript">

    function editForm_check() {
        $('#updroleform').submit();
        $('#addDialog').dialog('close');
    }

    function closeDialog() {
        $('#addDialog').dialog('close');
    }
</script>
</body>
</html>