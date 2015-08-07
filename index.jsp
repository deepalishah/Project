

<%@page import="java.util.List"%>
<%@page import="com.search.common.Result"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel='stylesheet' type='text/css' HREF="<%=request.getContextPath()%>/css/style.css"/>
        <link rel='stylesheet' type='text/css' HREF="<%=request.getContextPath()%>/css/bootstrap.css"/>
        <link rel='stylesheet' type='text/css' HREF="<%=request.getContextPath()%>/css/bootstrap-responsive.css"/>
        <title>Search-Engine</title>
    </head>
    <body style="background-color:lightskyblue">
      <jsp:forward page="login.jsp" />
    </body>
</html>
