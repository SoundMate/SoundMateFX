<%--
  ~ Copyright (c) 2021.
  ~ Created by Lorenzo Pantano on 09/02/21, 11:45
  ~ Last edited: 09/02/21, 11:45
  --%>

<%--
  Created by IntelliJ IDEA.
  User: lpant
  Date: 08/02/2021
  Time: 21:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html lang="it">
<head>
    <title>Register</title>
</head>
<body>

    <%
        if (session.getAttribute("soloRegister") != null) {
            response.sendRedirect("soloSignUp.jsp");
        }
    %>

</body>
</html>
