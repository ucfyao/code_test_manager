<%@ page language="java" import="java.util.*,com.chainfuture.code.common.WwSystem" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getServletContext().getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <title>首页</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
    <link rel="stylesheet" href="<%=path%>/layui/css/layui.css">
    <script type="text/javascript" src="<%=path%>/js/commons/jquery.min.js"></script>
    <base href="<%=WwSystem.getRoot(request)%>">
    <script src="<%=path%>/layui/layui.js"></script>
    <script>
        var ctx = "<%=path%>";
        var ss= "<%=WwSystem.getRoot(request)%>";
    </script>
    <style type="text/css">
        .layui-layout-body{
            overflow: visible;
        }
        .layui-layer-content{
            height:100%;
        }
        iframe{
            height:100%;
        }
    </style>
</head>
<body class="childrenBody" style="margin: 1%">
<div class="layui-form" style="margin-bottom: 80px;">
    <table class="layui-hide" style="margin-bottom: 50px" id="productList" lay-filter="productList"></table>
    <script type="text/html" id="productToolBar">
        <div class="layui-btn-container">
            <button class="layui-btn layui-btn-sm" lay-event="save">添加</button>
            <%--<button class="layui-btn layui-btn-sm" lay-event="update">编辑</button>--%>
             <%--<button class="layui-btn layui-btn-sm" lay-event="batchDel">批量删除</button>--%>
        </div>
    </script>
</div>
<script type="text/html" id="barDemo">
    <%--<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>--%>
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-xs" lay-event="batchList">批次列表</a>
</script>
<script type="text/html" id="statusTpl">
    {{#  if(d.status === 0){ }}
    <span style="color: #FFB800;">禁用</span>
    {{#  } else if(d.status === 1){ }}
    <span style="color: #01AAED;">启用</span>
    {{#  } else if(d.status === 2){ }}
    <span style="color: #01AAED;">待审核</span>
    {{#  } else{ }}
    <span style="color: #FF5722;">禁用</span>
    {{#  } }}
</script>

<script src="https://cdn.bootcss.com/lrsjng.jquery-qrcode/0.14.0/jquery-qrcode.min.js"></script>
<script src="<%=path%>/js/see/productList.js"></script>
</body>
</html>

