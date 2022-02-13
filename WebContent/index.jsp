<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<style>
form {
	border: 5px solid #ff9900;
	box-shadow: 20px green;
	height: 65%;
}

input[type=text], input[type=password] {
	width: 65%;
	padding: 10px 10px;
	margin: 0 5px;
	display: compact;
	border: 1px solid thick;
}

body {
	margin: 0;
	padding: 0;
	background-color: #6abba;
	font-family: 'Arial';
	font-variant: small-caps;
}

.centered {
   position: absolute;
  top: 55%;
  left: 85%;
  
   transform: translate(-75%, -50%);  
   
  background-color:white;
  filter:blur(0.2px);
  padding: 20px;
  box-shadow: 0 0 5px 4px white;
  height: 300px;
  width: 460px;
  max-width: 1000px;
  margin: auto;
}
.quote {
  background-color:red;
  top:170%;
  left: 85%;
  transform: translate(0%, 900%);
  padding-left: 30px;
  box-shadow: 0 0 20px 19px red;
}
.header {
  background-color:red;
   font-size: 150%;
   box-shadow: 0 0 24px 23px red;
}
body {
	background-image: url('images/backImg2.jpg');
  background-repeat: no-repeat;
  background-attachment: fixed;
   background-size: 100% 100%;
}
</style>
<meta charset="ISO-8859-1">
<title>Airport Login</title>
</head>
<body style="align-content: center; align-items: center;">
<div class = header align="center"><h1>Airport Login</h1></div>
<div class = "quote"><h1>You are just a click away from your flight !!</h1></div>
	<div class="centered" >
		<h3 align="center">Please Enter Credentials</h3>
		<form action="Login">
			<h5 align="center">
				Username <input type="text" name="uname">
			</h5>
			<br>
			<h5 align="center">
				Password <input type="password" name="pass">
			</h5>
			<br>
			<h5 align="center">
				<input type="image" src="images/Login.gif" alt="login"
					style="float: center" width="85" height="35">
			</h5>
		</form>
		<div id="message" align="center">

			<c:if test="${not empty MESSAGE}">
				<b style="color: red"><c:out value="${MESSAGE}"></c:out> </b>
			</c:if>

		</div>
	</div>
</body>
</html>