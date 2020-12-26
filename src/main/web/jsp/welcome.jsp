<%--
  ~ Copyright (c) 2020.
  ~ This file was created by Soundmate organization Lorenzo Pantano & Matteo D'Alessandro
  ~ Last Modified: 28/11/20, 13:33
  --%>

<%--
  Created by IntelliJ IDEA.
  User: lpant
  Date: 28/11/2020
  Time: 12:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<head>
    <title>Welcome to Soundmate</title>
    <link rel="stylesheet" href="../css/welcome.css">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>

    <div class="sidenav">
        <div class="user-data">
            <a href="#"><img src="../../resources/soundmate/images/user-default.png" alt="logo" class="user-image"></a>
<%
        if (session.getAttribute("userID") == null) {
%>
            <jsp:forward page="index.jsp"/>
<%
        } else {
%>
            <a href="#"><%out.print(session.getAttribute("firstName"));%> <%out.print(session.getAttribute("lastName"));%></a>
<%
        }
%>
        </div>
        <div class="nav-links">
            <a href="#">Home</a>
            <a href="#">Search</a>
            <a href="#">Messages</a>
            <a href="#">Profile</a>
            <a href="#">Settings</a>
        </div>
        <div class="logout-row">
            <a href="#">Logout</a>
        </div>
    </div>

    <div class="main">

    </div>

</body>
