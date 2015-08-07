<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="com.search.services.DatabaseService"%>
    <%@page import="com.search.common.NavigationBean"%>
<%@page import="java.sql.ResultSet"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home-Page</title>
<link rel="stylesheet" type="text/css" href="css/menu.css" />
<link rel="stylesheet" type="text/css" href="css/table.css" />
<link rel="stylesheet" type="text/css" href="css/body.css" />
<link rel="stylesheet" type="text/css" href="css/searchBox.css" />
<script src="js/jquery-1.9.1.js" type="text/javascript"></script>
<script type="text/javascript">

$(document).ready(function() {           
			
		var placeholderText = $('#search').attr('placeholder');
		
		$('#search').attr('value',placeholderText);
		$('#search').addClass('placeholder');
		
		$('#search').focus(function() {				
			if( ($('#search').val() == placeholderText) )
			{
				$('#search').attr('value','');
				$('#search').removeClass('placeholder');
			}
		});
		
		$('#search').blur(function() {				
			if ( ($('#search').val() == placeholderText) || (($('#search').val() == '')) )                      
			{	
				$('#search').addClass('placeholder');					  
				$('#search').attr('value',placeholderText);
			}
		});
	                
});

</script>
</head>
<body>
<h1>Search Engine</h1>
<!-- <center><img src="./images/search.jpg" width="80" height="30" border="0"></center> -->
<div style="float: right;margin-top: -6%;">
<h5>Welcome <%=(String)session.getAttribute("username")%></h5>
</div>
<div id='cssmenu'>
<ul>
   <li><a href='landingPage.jsp'><span>Home</span></a></li>
   <li class='active'><a href='ViewNavigationHistory.jsp'><span>View Favorite Links</span></a></li>
   <li  class='last' style="float:right"><a href='index.jsp'><span>Logout</span></a></li>
</ul>
</div>


<form id="searchbox" action="SearchServlet" method="post">
    <input id="search" type="text" placeholder="Type here" name="queryString">
    <input id="submit" type="submit" value="Search">
</form>
<div class="CSSTableGenerator">
	<table>
	<tr><td>Query</td><td>Link</td><td>DateTime</td><td>Hits</td></tr>
<%
DatabaseService dbm = new DatabaseService();
ArrayList<NavigationBean> list = dbm.navigationList((String)session.getAttribute("username"));
for(int i=0;i<list.size();i++){
	NavigationBean bean= list.get(i);
	%>
	<tr><td><%= bean.getQuery()%></td><td><a target ="_blank" href='<%=bean.getLink()%>'><%= bean.getLink()%></a></td><td><%=bean.getTimestamp() %></td><td><%=bean.getHits() %></td></tr>
	<%
}
	%>
	</table>
</div>
</body>
</html>