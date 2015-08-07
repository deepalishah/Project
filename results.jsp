
<%@page import="com.search.services.DatabaseService"%>
<%@page import="com.search.common.Result"%>
<%@page import="java.util.List"%>
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
        
        <script type="text/javascript" src=<%=request.getContextPath() + "/js/jquery-1.3.2.js"%>></script>

        <script lang="Javascript" type="text/javascript">

            function createRequestObject() {
                var tmpXmlHttpObject;
                if (window.XMLHttpRequest) {
                    tmpXmlHttpObject = new XMLHttpRequest();
                } else if (window.ActiveXObject) {
                    tmpXmlHttpObject = new ActiveXObject("Microsoft.XMLHTTP");
                }
                return tmpXmlHttpObject;
            }
            http = createRequestObject();
            
            function updateUserLink(url,queryString) {
                http.open('get', './UpdateUserNavigation?url='+ url + '&queryString=' + queryString);
                    
                http.onreadystatechange = function() {
                    if (http.readyState == 4) {
                        var response = http.responseText;
                    }
                }
                http.send(null);
            }
        </script>

        <title>Search Result Page</title>
    </head>
    <body style="background-color:lightskyblue">

        <%
            List<Result> resultList = (List<Result>) request.getAttribute("resultList");
            boolean oddOrEven = true;
            String queryString = (String) request.getAttribute("queryString");
            String username=(String) request.getAttribute("username");
        %>

        <div class="container-fluid">
            <div class="row-fluid">
                <h1>Search Engine</h1>
	<!--   <center><img src="./images/search.jpg" width="80" height="30" border="0"></center>
	  -->  
		<div style="float: right;margin-top: -5%;margin-right: 1%;">
		<h5>Welcome <%=(String)session.getAttribute("username")%></h5>
		</div>

		<div id='cssmenu'>
		<ul>
			<li class='active'><a href='homePage.jsp'><span>Home</span></a></li>
			<li><a href='ViewNavigationHistory.jsp'><span>View Favorite Links</span></a></li>
			<%if(session.getAttribute("username").toString().equals("admin"))
			{%>
			<li><a href='UserList.jsp'><span>Users History</span></a></li>
			<%} %>
			<li  class='last' style="float:right"><a href='index.jsp'><span>Logout</span></a></li>
		</ul>
		</div>
	   
		<div>
        <form id="searchbox" action="SearchServlet" method="post">
    		<input id="search" type="text" placeholder="Type here" name="queryString">
    		<input id="submit" type="submit" value="Search">
		</form>
        </div>
     </div>
   </div>
        <div class="container-fluid">
            <div class="row-fluid">
                <div class="span2"></div>
                <div class="span8">
                    <%
                        if (resultList.size() > 0) {
                    %>
                    <h6>Records returned : <%=resultList.size()%></h6>
                    <table>
                        <%
                        for (Result result : resultList) {
                        %>
                        <tr <%if (oddOrEven) {%>class="rowEven" <%} else {%> class="rowOdd"<%}%> >
                            <td style="padding: 25px">
                                <table style="border: solid black;width: 900px;">
                                    <tr>
                                        <td style="font-family: verdana;font-size: 12px"><a href="<%=result.getUrl()%>" onclick="return updateUserLink('<%=result.getUrl()%>','<%=queryString%>');" target="_blank"><%=result.getTitle()%></a>    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="<%=result.getUrl()%>" target="_blank" style="color: green" onclick="return updateUserLink('<%=result.getUrl()%>','<%=queryString%>');"><%=result.getUrl()%></a></td>
                                    </tr>
                                    <tr>
                                        <td style="font-family: verdana;font-size: 10px"><%=result.getDescription()%></td>
                                    </tr>
                                
                                </table>
                            </td>
                        </tr>
                        <%
                                oddOrEven = !oddOrEven;
                                }
                        %>
                    </table>
                    <% }%>
                </div>
                <div class="span2"></div>
            </div>
        </div>
        <div style="padding-top: 20px"></div>
    </body>
</html>
