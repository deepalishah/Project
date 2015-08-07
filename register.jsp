

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; UTF-8">
        <title>Register New User</title>
        <link rel="stylesheet" type="text/css" href="css/login.css" />
        <link rel="stylesheet" type="text/css" href="css/body.css" />
        <script type="text/javascript" src="js/validation.js"></script>
        <script>
        function validate(objForm){
        	
        	if(objForm.password.value != objForm.repassword.value) {
                alert("Password Mismatched...!!\n Re-enter the Password...!!");
                objForm.password.value = "";
                objForm.repassword.value = "";
                objForm.password.focus();
                return false;
              }
            return true;
        }
        </script>
    </head>
    <body style="background-color:lightskyblue">
        <%
            String msg = (String) session.getAttribute("msg");
        %>
        <img src="./images/search.jpg" alt="Company Logo" width="250" height="80" border="0" />
        <div id="wrapper">
            <form name="objForm" id="objForm" class="login-form" action="RegistrationServlet" method="post" onsubmit="return validate(this);" style="width:550px !important;margin-left: -34%;">
                <div class="header">
                   <%
                        if (msg != null) {
                    %>
                    <span style="color: red"><%=msg%></span>
                    <%
                    session.setAttribute("msg", null);
                        }
                    %>
                </div>
                <div class="content">
                    <table>
                        <tr>
                            <td style="font-family: verdana;font-size: 15px">Firstname :</td>
                            <td><input name="firstName" type="text" required="required" autofocus="autofocus" onkeypress="return isCharacterKey(this);" class="input firstName" style="font-family: verdana;font-size: 15px" placeholder="FirstName" value=""/><br/>
                            </td>
                        </tr>
                        <tr>
                            <td style="font-family: verdana;font-size: 15px">Lastname :</td>
                            <td><input name="lastName" type="text" required="required" onkeypress="return isCharacterKey(this);" class="input lastName" style="font-family: verdana;font-size: 15px" placeholder="LastName" value=""/><br/>
                            </td>
                        </tr>
                        <tr>
                            <td style="font-family: verdana;font-size: 15px">Username :</td>
                            <td><input name="username" type="text" required="required" onkeypress="return validateUsername(this);" class="input" style="font-family: verdana;font-size: 15px" placeholder="Username" value=""/><br/>
                            </td>
                        </tr>
                        <tr>
                            <td style="font-family: verdana;font-size: 15px">Password :</td>
                            <td><input name="password" type="password" required="required" onkeypress="return validatePassword(this);" class="input" style="font-family: verdana;font-size: 15px" placeholder="Password" value=""/><br/>
                            </td>
                        </tr>
                        <tr>
                            <td style="font-family: verdana;font-size: 15px">Confirm Password :</td>
                            <td><input name="repassword" type="password" required="required"  class="input repassword" style="font-family: verdana;font-size: 15px" placeholder="Re-enter Password" value=""/><br/>
                            </td>
                        </tr>
                        <tr>
                            <td style="font-family: verdana;font-size: 15px">Email :</td>
                            <td><input name="email" type="email" required="required" onkeypress="return validateEmail(this);" class="input email" style="font-family: verdana;font-size: 15px" placeholder="Email" value=""/><br/>
                            </td>
                        </tr>
  						<tr>
                            <td style="font-family: verdana;font-size: 15px">Contact :</td>
                            <td><input name="contact" type="text" required="required" minlength="10" maxlength="10" onkeypress="return validatePhoneNumber(this);" class="input" style="font-family: verdana;font-size: 15px" placeholder="Contact" value=""/><br/>
                            </td>
                        </tr>                     
                        	<td style="font-family: verdana;font-size: 15px">Select Interests :</td>
                        	<td>
                        		<input type="checkbox" name="id" value="Sports"> Sports<BR>
								<input type="checkbox" name="id" value="Entertainment"> Entertainment<BR>
								<input type="checkbox" name="id" value="Books"> Books<BR>
								<input type="checkbox" name="id" value="News"> News<BR>
								<!-- <input type="submit" value="Submit"> -->
                        	</td>
                        </tr>
                       
                    </table>
                </div>
                <div class="footer">
                    <input type="button" value="Login" style="font-family: verdana;font-size: 15px" class="register" onclick='window.location="login.jsp";'/>
                    <input type="submit" style="font-family: verdana;font-size: 15px" name="submit" value="Register" class="button" />
                </div>
            </form>
            <div style="padding-top: 5px"></div>
            <h5>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Search results by&nbsp;&nbsp;<img src="./images/google.jpg" alt="Company Logo" width="60" height="30" border="0" /> </h5>
        </div>
        <div class="gradient"></div>
        <div style="padding-top: 50px"></div>
    </body>
</html>