<%@ page import="it.soundmate.model.User" %><%--
  ~ Copyright (c) 2021.
  ~ Created by Lorenzo Pantano on 02/02/21, 18:26
  ~ Last edited: 02/02/21, 18:26
  --%>

<%--
  Created by IntelliJ IDEA.
  User: lpant
  Date: 02/02/2021
  Time: 18:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html lang="it">
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>

    <%
        User user = (User) session.getAttribute("loggedUser");
        switch (user.getUserType()) {
            case SOLO:
                response.sendRedirect("soloProfile.jsp");
                break;
            case BAND:
                response.sendRedirect("bandProfile.jsp");
                break;
            case ROOM_RENTER:
                response.sendRedirect("roomRenterProfile.jsp");
                break;
        }
    %>

</body>
</html>
