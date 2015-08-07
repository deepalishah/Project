

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; UTF-8">
        <title>Login</title>
        <link rel="stylesheet" type="text/css" href="css/login.css" />
        <link rel="stylesheet" type="text/css" href="css/body.css" />
    </head>
    
    <body style="background-color:lightskyblue">
      <!--   <img src="./images/search.jpg" alt="Company Logo" width="250" height="80" border="0" /> -->
        <div id="wrapper">
            <form name="login-form" class="login-form" action="LoginServlet" method="post" ">
                <div class="header">
                    <span>
                        <%
                        String msg = (String) session.getAttribute("msg");
                        if (msg != null) {
                    %>
                    <span style="color: red"><%=msg%></span>
                    <%
                    session.setAttribute("msg", null);
                        }
                    %>
                    </span>
                </div>
                <div class="content">
                    <input name="username" type="text" required="required" autofocus="autofocus" class="input username" style="font-family: verdana;font-size: 15px" placeholder="Username" value=""/>
                    <div class="user-icon"></div>
                    <input name="password" type="password" required="required" class="input password" style="font-family: verdana;font-size: 15px" placeholder="Password" value=""/>
                    <div class="pass-icon"></div>		
                </div>

                <div class="footer">
                    <input type="submit" name="submit" value="Login" style="font-family: verdana;font-size: 15px" class="button" />
                    <input type="button" value="Register" style="font-family: verdana;font-size: 15px" class="register" onclick='window.location="register.jsp";'/>
                </div>
            </form>
            <div style="padding-top: 5px"></div>
            <h5>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Search results by&nbsp;&nbsp;<img src="./images/google.jpg" alt="Company Logo" width="60" height="30" border="0" /> &nbsp; &nbsp; </h5>
        </div>
        <div class="gradient"></div>
          
    </body>
</html>