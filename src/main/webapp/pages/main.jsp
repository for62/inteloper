<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html style="width:100%;height:100%;overflow:hidden">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>五常优质水稻溯源监管平台</title>
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
<div region="north" title="" split="false" class="mainTop">
    <a href="${pageContext.request.contextPath}/main"
       style="float:left;color:#000;font-size:30px;font-weight:bold;text-decoration:none;color:#666666;padding-left:10px;">五常优质水稻溯源监管平台</a>
    <div style="float:right;font-size:16px;padding-right:10px;">
        hi,<c:out value="${loginUser.userName}"></c:out> <a href="${pageContext.request.contextPath}/pages/login.jsp"
                                                            target="_self"
                                                            style="text-decoration:none;color:#449942">退出登陆</a>
    </div>
</div>
<div region="west" split="true" title="管理后台" style="width:250px;">
    <ul id="menu_tree">
        <c:forEach items="${functions}" var="e">
            <li class="menuTitleLi">
                <span class="menuTitle">${e.key}</span>
                <ul>
                    <c:forEach items="${e.value}" var="f">
                        <li iconCls="icon-add"><span><a
                                onClick="addTab('${f.functionName}','${pageContext.request.contextPath}/${f.functionURL}')">${f.functionName}</a></span>
                        </li>
                    </c:forEach>
                    </li>
                </ul>
            </li>
        </c:forEach>

        <%--        <li class="menuTitleLi">--%>
        <%--            <span class="menuTitle">我的操控台</span>--%>
        <%--            <ul>--%>

        <%--                <li iconCls="icon-ok"><span><a onClick="addTab('修改密码','user/updatePwd.html')">修改密码</a></span></li>--%>

        <%--                <li iconCls="icon-add"><span><a onClick="addTab('待办事项','user/toDoView.html')">待办事项</a></span></li>--%>

        <%--                <li iconCls="icon-add"><span><a onClick="addTab('个人信息维护','user/basicInfoEdit.html')">个人信息维护</a></span>--%>
        <%--                </li>--%>

        <%--            </ul>--%>
        <%--        </li>--%>

        <%--        <li class="menuTitleLi">--%>
        <%--            <span class="menuTitle">土地备案</span>--%>
        <%--            <ul>--%>

        <%--                <li iconCls=""><span><a--%>
        <%--                        onClick="addTab('普通土地备案申请','genelandreg/geneLandRegEdit.html')">普通土地备案申请</a></span></li>--%>

        <%--                <li iconCls=""><span><a--%>
        <%--                        onClick="addTab('普通土地备案一览','genelandreg/geneLandRegList.html')">普通土地备案一览</a></span></li>--%>

        <%--                <li iconCls=""><span><a--%>
        <%--                        onClick="addTab('特殊土地备案申请','speclandreg/specLandRegEdit.html')">特殊土地备案申请</a></span></li>--%>

        <%--                <li iconCls=""><span><a--%>
        <%--                        onClick="addTab('特殊土地备案一览','speclandreg/specLandRegList.html')">特殊土地备案一览</a></span></li>--%>

        <%--                <li iconCls=""><span><a--%>
        <%--                        onClick="addTab('特殊土地备案审核','speclandreg/specLandRegListQuery.html')">特殊土地备案审核</a></span></li>--%>

        <%--                <li iconCls=""><span><a--%>
        <%--                        onClick="addTab('土地备案变更申请','landchange/landChangeEdit.html')">土地备案变更申请</a></span></li>--%>

        <%--                <li iconCls=""><span><a--%>
        <%--                        onClick="addTab('土地备案变更申请一览','landchange/landChangeList.html')">土地备案变更申请一览</a></span></li>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('土地备案变更处理','landchange/landChangeListQuery.html')">土地备案变更处理</a></span>--%>
        <%--                </li>--%>

        <%--            </ul>--%>
        <%--        </li>--%>

        <%--        <li class="menuTitleLi">--%>
        <%--            <span class="menuTitle">购种备案</span>--%>
        <%--            <ul>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('购种备案申请','tmp')">购种备案申请</a></span></li>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('购种备案信息一览','tmp')">购种备案信息一览</a></span></li>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('购种认证备案审批','tmp')">购种认证备案审批</a></span></li>--%>

        <%--            </ul>--%>
        <%--        </li>--%>

        <%--        <li class="menuTitleLi">--%>
        <%--            <span class="menuTitle">投入品备案</span>--%>
        <%--            <ul>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('投入品备案登记','inputreg/inputRegEdit.html')">投入品备案登记</a></span></li>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('投入品备案一览','inputreg/inputRegList.html')">投入品备案一览</a></span></li>--%>

        <%--            </ul>--%>
        <%--        </li>--%>

        <%--        <li class="menuTitleLi">--%>
        <%--            <span class="menuTitle">五常环境监测</span>--%>
        <%--            <ul>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('大气监测数据管理','airmoni/airMoniList.html')">大气监测数据管理</a></span></li>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('水质监测数据管理','watermoni/waterMoniList.html')">水质监测数据管理</a></span>--%>
        <%--                </li>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('土壤监测数据管理','soilmoni/soilMoniList.html')">土壤监测数据管理</a></span>--%>
        <%--                </li>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('PM2.5监测数据采集','pmmoni/pmMoniList.html')">PM2.5监测数据采集</a></span>--%>
        <%--                </li>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('五常环境监测','gismap/view')">五常环境监测</a></span></li>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('监测点管理','monipoint/moniPointList.html')">监测点管理</a></span></li>--%>

        <%--            </ul>--%>
        <%--        </li>--%>

        <%--        <li class="menuTitleLi">--%>
        <%--            <span class="menuTitle">收粮备案</span>--%>
        <%--            <ul>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('收粮预报','graireg/graiForeList.html')">收粮预报</a></span></li>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('收粮预报统计','graireg/graiForeListQuery.html')">收粮预报统计</a></span>--%>
        <%--                </li>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('收粮备案登记','graireg/graiRegEdit.html')">收粮备案登记</a></span></li>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('收粮备案一览','graireg/graiRegList.html')">收粮备案一览</a></span></li>--%>

        <%--            </ul>--%>
        <%--        </li>--%>

        <%--        <li class="menuTitleLi">--%>
        <%--            <span class="menuTitle">过程管理</span>--%>
        <%--            <ul>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('过程监控资料管理','procmoni/procMoniEdit.html')">过程监控资料管理</a></span>--%>
        <%--                </li>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('过程监控','procmoni/procMoniList.html')">过程监控</a></span></li>--%>

        <%--            </ul>--%>
        <%--        </li>--%>

        <%--        <li class="menuTitleLi">--%>
        <%--            <span class="menuTitle">产量评估</span>--%>
        <%--            <ul>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('种源评估参数维护','proveval/provEvalList.html')">种源评估参数维护</a></span>--%>
        <%--                </li>--%>

        <%--                <li iconCls=""><span><a--%>
        <%--                        onClick="addTab('种源产量评估管理','proveval/provEvalListQuery.html')">种源产量评估管理</a></span></li>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('粮食评估参数维护','graieval/graiEvalList.html')">粮食评估参数维护</a></span>--%>
        <%--                </li>--%>

        <%--                <li iconCls=""><span><a--%>
        <%--                        onClick="addTab('粮食产量评估管理','graieval/graiEvalListQuery.html')">粮食产量评估管理</a></span></li>--%>

        <%--            </ul>--%>
        <%--        </li>--%>

        <%--        <li class="menuTitleLi">--%>
        <%--            <span class="menuTitle">防伪码管理</span>--%>
        <%--            <ul>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('企业产量预报','yieldpredict/yieldPredictReg.html')">企业产量预报</a></span>--%>
        <%--                </li>--%>

        <%--                <li iconCls=""><span><a--%>
        <%--                        onClick="addTab('企业产量预报一览','yieldpredict/yieldPredictList.html')">企业产量预报一览</a></span></li>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('企业产量预报分析','tmp')">企业产量预报分析</a></span></li>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('企业防伪码申请记录','securitycode/applyList.html')">企业防伪码申请记录</a></span>--%>
        <%--                </li>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('防伪码','securitycode/seclist.html')">防伪码</a></span></li>--%>

        <%--            </ul>--%>
        <%--        </li>--%>

        <%--        <li class="menuTitleLi">--%>
        <%--            <span class="menuTitle">质检管理</span>--%>
        <%--            <ul>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('抽检记录','sample/list.html')">抽检记录</a></span></li>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('质检记录','quality/list.html')">质检记录</a></span></li>--%>

        <%--            </ul>--%>
        <%--        </li>--%>

        <%--        <li class="menuTitleLi">--%>
        <%--            <span class="menuTitle">黑名单管理</span>--%>
        <%--            <ul>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('企业投诉登记','companycplt/companyCPLTEdit.html')">企业投诉登记</a></span>--%>
        <%--                </li>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('企业投诉处理','companycplt/companyCPLTList.html')">企业投诉处理</a></span>--%>
        <%--                </li>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('种子公司投诉登记','seedcplt/seedCPLTEdit.html')">种子公司投诉登记</a></span>--%>
        <%--                </li>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('种子公司投诉处理','seedcplt/seedCPLTList.html')">种子公司投诉处理</a></span>--%>
        <%--                </li>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('召回一览','recall/recallList.html')">召回一览</a></span></li>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('企业黑名单一览','blackList/blackList.html')">企业黑名单一览</a></span></li>--%>

        <%--            </ul>--%>
        <%--        </li>--%>

        <%--        <li class="menuTitleLi">--%>
        <%--            <span class="menuTitle">权限管理</span>--%>
        <%--            <ul>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('用户管理','user/userList.html')">用户管理</a></span></li>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('角色管理','role/roleList.html')">角色管理</a></span></li>--%>

        <%--            </ul>--%>
        <%--        </li>--%>

        <%--        <li class="menuTitleLi">--%>
        <%--            <span class="menuTitle">档案管理</span>--%>
        <%--            <ul>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('企业档案管理','company/companyList.html')">企业档案管理</a></span></li>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('企业土地资料管理','landpic/landPicList.html')">企业土地资料管理</a></span></li>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('产品管理','product/productList.html')">产品管理</a></span></li>--%>

        <%--                <li iconCls=""><span><a onClick="addTab('年度管理','yearcode/yearcodeList.html')">年度管理</a></span></li>--%>

        <%--            </ul>--%>
        <%--        </li>--%>
    </ul>
</div>
<div id="region_center" region="center">
    <div id="center_tab" class="easyui-tabs" border="false" fit="true" style="">
    </div>
</div>
<div id="tree_click"></div>
<div id="mm" class="easyui-menu" style="width:135px;">
    <div iconCls="icon-clear" id="mm_current" name="1">关闭当前</div>
    <div id="mm_all" name="2">关闭全部</div>
    <div id="mm_other" name="3">除此之外全部关闭</div>
    <div id="mm_left" name="4">关闭左侧全部</div>
    <div id="mm_right" name="5">关闭右侧全部</div>
</div>
</body>
<script>

    $(".menuTitleLi").eq(0).find("ul").show();
    $(".menuTitleLi").eq(0).addClass("on");

    $(".menuTitleLi").click(function () {
        if ($(this).find("ul").css("display") != "block") {
            $(".menuTitleLi").removeClass("on");
            $(this).addClass("on");
            $(".menuTitleLi ul").slideUp();
            $(this).find("ul").slideDown();
        }
    })

    $(".menuTitleLi li a").click(function () {
        $(".menuTitleLi li").removeClass("bg");
        $(this).parents("li").addClass("bg");
    })

    $(document).ready(function () {
        $('#center_tab').tabs({
            onContextMenu: function (e, title, index) {
                e.preventDefault();
                if (index >= 0) {
                    $('#mm').menu('show', {
                        left: e.pageX,
                        top: e.pageY
                    }).data("tabTitle", title);
                }
            }
        });

        function closeTab(menu, type) {
            var allTabs = $("#center_tab").tabs('tabs');
            var allTabtitle = [];
            $.each(allTabs, function (i, n) {
                var opt = $(n).panel('options');
                if (opt.closable)
                    allTabtitle.push(opt.title);
            });
            switch (type) {
                //关闭当前
                case "1" :
                    var curTabTitle = $(menu).data("tabTitle");
                    $("#center_tab").tabs("close", curTabTitle);
                    return false;
                    break;
                //关闭所有
                case "2" :
                    for (var i = 0; i < allTabtitle.length; i++) {
                        $('#center_tab').tabs('close', allTabtitle[i]);
                    }
                    break;
                //关闭其它
                case "3" :
                    var curTabTitle = $(menu).data("tabTitle");
                    for (var i = 0; i < allTabtitle.length; i++) {
                        if (curTabTitle != allTabtitle[i]) {
                            $('#center_tab').tabs('close', allTabtitle[i]);
                        }
                    }
                    break;
                //关闭左侧全部
                case "4" :
                    var prevall = $('.tabs-selected').prevAll();
                    if (prevall.length == 0) {
                        $.messager.alert('警告', '左侧没有可关闭的标签页！', 'warning');
                        return;
                    }
                    prevall.each(function (i, n) {
                        var t = $('a:eq(0) span', $(n)).text();
                        $('#center_tab').tabs('close', t);
                    });
                    break;
                case "5" :
                    var nextall = $('.tabs-selected').nextAll();
                    if (nextall.length == 0) {
                        $.messager.alert('警告', '右侧没有可关闭的标签页！', 'warning');
                        return;
                    }
                    nextall.each(function (i, n) {
                        var t = $('a:eq(0) span', $(n)).text();
                        $('#center_tab').tabs('close', t);
                    });
                    break;
            }

        }

        $("#mm").menu({
            onClick: function (item) {
                closeTab(this, item.name);
            }
        });
    });

    function addTab(title, url) {
        if ($('#center_tab').tabs('exists', title) == true) {
            $('#center_tab').tabs('select', title);
            return updateTab();
        }
        $('#center_tab').tabs('add', {
            title: title,
            content: '<iframe frameborder="0" scrolling="auto" style="width:100%;height:100%" src="' + url + '"></iframe>',
            closable: true,
            tools: [{
                iconCls: 'icon-mini-refresh',
                handler: function () {
                    updateTab();
                }
            }]
        });
    }

    function updateTab() {
        var tab = $('#center_tab').tabs('getSelected');
        $('#center_tab').tabs('update', {
            tab: tab,
            options: {
                //title:tab.panel('options').title
            }
        });
    }
</script>
</html>
