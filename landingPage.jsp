
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
        <link rel="stylesheet" type="text/css" href="css/menu.css" />
		<link rel="stylesheet" type="text/css" href="css/body.css" />
		<link rel="stylesheet" type="text/css" href="css/searchBox.css" />
        <title>Search Engine</title>        
    </head>
     
    <body >
       <h1>Search Engine</h1>
	   <!-- <center><img src="./images/search.jpg" width="80" height="30" border="0"> </center> -->
	   	<div style="float: right;margin-top: -6%;">
		<h5>Welcome <%=(String)session.getAttribute("username")%></h5>
	   	</div>

		<div id='cssmenu'>
			<ul>
   				<li class='active'><a href='landingPage.jsp'><span>Home</span></a></li>
   				<li><a href='ViewNavigationHistory.jsp'><span>View Favorite Links</span></a></li>
    			<li  class='last' style="float:right"><a href='index.jsp'><span>Logout</span></a></li>
			</ul>
		</div>
	   
        <div>
                <form id="searchbox" action="SearchServlet" method="post">
    				<input id="search" type="text" placeholder="Type here" name="queryString">
    				<input id="submit" type="submit" value="Search">
				</form>
        </div>
    </body>
</html>

 