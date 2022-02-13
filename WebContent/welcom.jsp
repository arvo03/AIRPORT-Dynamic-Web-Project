<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<style>
table, th, td {
	border: 1px solid black;
}

th, td {
	padding-left: 45px;
	padding-right: 45px;
	padding-bottom: 10px;
	padding-top: 10px;
}

body {
	font-family: 'Arial';
	font-variant: small-caps;
}

.blue {
	border: 5px outset #abfe11;
	background-color: #fe5d11;
	text-align: center
}

body {
	background-image: url('images/welcomImg3.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: 100% 100%;
	font-size: large;
}
</style>
<meta charset="ISO-8859-1">
<title>Search Airports</title>
<script>
	var globalAirportName;
	function getAirportNameByAirportCode() {
		var val = document.getElementById('airportCode').value;
		var xhttp;
		if (window.XMLHttpRequest) {

			xhttp = new XMLHttpRequest();

		} else if (window.ActiveXObject) {

			xhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xhttp.onreadystatechange = function() {
			document.getElementById('airportName').innerHTML = xhttp.responseText;
		};

		xhttp.open("POST",
				"AirportSearchOperationsServlet?getAirportNameByAirportCode="
						+ val, true);
		xhttp.send();
	}

	function getCountryNameByCountryCode() {
		var val = document.getElementById('countryCode').value;
		var xhttp;
		if (window.XMLHttpRequest) {

			xhttp = new XMLHttpRequest();

		} else if (window.ActiveXObject) {

			xhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xhttp.onreadystatechange = function() {
			document.getElementById('countryName').innerHTML = xhttp.responseText;
		};

		xhttp.open("POST",
				"AirportSearchOperationsServlet?getCountryNameByCountryCode="
						+ val, true);
		xhttp.send();
	}

	function filterAirportCodeDropdown() {
		var val = document.getElementById('countryCode').value;
		var xhttp;
		if (window.XMLHttpRequest) {

			xhttp = new XMLHttpRequest();

		} else if (window.ActiveXObject) {

			xhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xhttp.onreadystatechange = function() {
			//			var responseList = xhttp.responseText;
			/*			while (node.hasChildNodes()) {
			 node.removeChild(node.firstChild);
			 }**/
			document.getElementById('airportCode').innerHTML = xhttp.responseText;
		};
		if (val != "SELECT") {

			xhttp.open("POST",
					"AirportSearchOperationsServlet?filterAirportCodeDropdown="
							+ val, true);
			xhttp.send();
		} else {
			xhttp.open("POST",
					"AirportSearchOperationsServlet?getAllAirportCodes=" + val,
					true);
			xhttp.send();
		}
	}

	function clearAirportName() {
		var val = document.getElementById('countryCode').value;
		document.getElementById('airportName').innerHTML = "";
	}

	function clearContent() {
		document.getElementById('countryCode').selectedIndex = 0;
		document.getElementById('countryName').innerHTML = "";
		document.getElementById('airportCode').selectedIndex = 0;
		document.getElementById('airportName').innerHTML = "";
		document.getElementById('searchResult').innerHTML = "";
	}
</script>
</head>
<body>

	<c:if test="${empty sessionScope['username']}">
		<c:redirect url="/index.jsp" />
	</c:if>
	<jsp:useBean id="obj" class="com.services.AirportSearchService"
		scope="session" />
	<div class="blue">
		<div align="right">
			<label style="font-size: 80%; padding-right: 5px">Welcome</label> <b
				style="font-size: 120%; padding-right: 15px"><c:out
					value="${sessionScope['fullname']}" /></b>
			<form action="Logout" style="padding-right: 10px">
				<input type="image" src="images/logout.png" alt="logout"
					style="float: center" width="25" height="25" title="Logout">
			</form>
		</div>
	</div>
	<form action="AirportSearchOperationsServlet" method="post">
		<div align="center">
			<table>
				<tr>
					<th>Country Code</th>
					<th>Country Name</th>
					<th>Airport Code</th>
					<th>Airport Name</th>
				</tr>
				<tr>
					<td><select id="countryCode" name="countryCode"
						onchange="getCountryNameByCountryCode(),filterAirportCodeDropdown(),clearAirportName()">
							<option value="SELECT">SELECT</option>
							<c:forEach var="countryCodes" items="${obj.countryCodes}">
								<option value="${countryCodes}">${countryCodes}</option>
							</c:forEach>
					</select></td>
					<td>
						<div id="countryName"></div>
					</td>
					<td><select id="airportCode" name="airportCode"
						onchange="getAirportNameByAirportCode()">
							<option>SELECT</option>
							<c:forEach var="airportCodes" items="${obj.airportCodes}">
								<option value="${airportCodes}">${airportCodes}</option>
							</c:forEach>
					</select></td>
					<td>
						<div id="airportName"></div>
					</td>
				</tr>
			</table>
			<input type="submit" name="searchSubmit" value="SEARCH">
		</div>
	</form>
	<div align="center">
		<button onclick="clearContent()">RESET</button>
	</div>
	<div id="searchResult" align="center">


		<c:choose>
			<c:when test="${SEARCHED_AIRPORT_RESPONSE=='Y'}">
				<div class="blue">
					<h4>FILTERS APPLIED</h4>
					<div style="padding-bottom: 10px;">
						<c:if test="${not empty SEARCHED_COUNTRY_CODE}">
							<label>Country Code : <b><c:out
										value="${SEARCHED_COUNTRY_CODE}" /></b> ,
							</label>
						</c:if>
						<c:if test="${not empty SEARCHED_COUNTRY_NAME}">
							<label>Country Name : <b><c:out
										value="${SEARCHED_COUNTRY_NAME}" /></b> ,
							</label>
						</c:if>
						<c:if test="${not empty SEARCHED_AIRPORT_CODE}">
							<label>Airport Code : <b><c:out
										value="${SEARCHED_AIRPORT_CODE}" /></b> ,
							</label>
						</c:if>
						<c:if test="${not empty SEARCHED_AIRPORT_NAME}">
							<label>Airport Code : <b><c:out
										value="${SEARCHED_AIRPORT_NAME}" /> </b>
							</label>
						</c:if>
					</div>
					<br />
				</div>
				<br />
				<c:set var="index" value="1" />
				<c:set var="checkRow" value="0" />
				<c:set var="firstTime" value="1" />
				<table border="1">
					<tr>
						<th>SEQUENCE NO</th>
						<th>AIRPORT CODE</th>
						<th>AIRPORT NAME</th>
						<th>COUNTRY CODE</th>
						<th>COUNTRY NAME</th>
					</tr>
					<c:forEach items="${SEARCHED_AIRPORT_LIST}" var="airport">

						<tr>
							<td><c:out value="${index}" /></td>
							<td><c:out value="${airport.icao}" /></td>
							<td><c:out value="${airport.name}" /></td>
							<td><c:out value="${airport.code}" /></td>
							<td><c:out value="${airport.country}" /></td>
						</tr>
						<c:set var="index" value="${index+1}" />
					</c:forEach>
				</table>
			</c:when>

		</c:choose>

	</div>

</body>
</html>