<%@ page import="it.soundmate.bean.searchbeans.UserResultBean" %>
<%@ page import="it.soundmate.bean.searchbeans.SoloResultBean" %>
<%@ page import="it.soundmate.bean.searchbeans.BandResultBean" %>
<%@ page import="it.soundmate.bean.searchbeans.RoomRenterResultBean" %><%--
  ~ Copyright (c) 2021.
  ~ Created by Lorenzo Pantano on 09/02/21, 12:29
  ~ Last edited: 09/02/21, 12:29
  --%>

<%--
  Created by IntelliJ IDEA.
  User: lpant
  Date: 09/02/2021
  Time: 12:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html lang="it">
<head>
    <title>Visit Profile</title>
</head>
<body>

    <%
        UserResultBean visitedUser = (UserResultBean) session.getAttribute("visitedUser");
        SoloResultBean solo;
        BandResultBean band;
        RoomRenterResultBean roomRenter;
        switch (visitedUser.getUserType()) {
            case SOLO:
                solo = (SoloResultBean) visitedUser;
                session.setAttribute("soloVisitedUser", solo);
                response.sendRedirect("soloProfileVisited.jsp");
                break;
            case BAND:
                band = (BandResultBean) visitedUser;
                session.setAttribute("bandVisitedUser", band);
                response.sendRedirect("bandProfileVisited.jsp");
                break;
            case ROOM_RENTER:
                roomRenter = (RoomRenterResultBean) visitedUser;
                session.setAttribute("renterVisitedUser", roomRenter);
                response.sendRedirect("renterProfileVisited.jsp");
                break;
        }
    %>

</body>
</html>
