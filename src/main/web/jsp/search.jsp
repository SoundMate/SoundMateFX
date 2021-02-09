<%@ page import="it.soundmate.bean.searchbeans.UserResultBean" %>
<%@ page import="java.util.List" %>
<%@ page import="it.soundmate.model.User" %>
<%@ page import="it.soundmate.controller.logic.SearchController" %>
<%@ page import="it.soundmate.bean.searchbeans.SoloResultBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="it.soundmate.bean.searchbeans.BandResultBean" %>
<%@ page import="it.soundmate.bean.searchbeans.RoomRenterResultBean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  ~ Copyright (c) 2021.
  ~ Created by Lorenzo Pantano on 08/02/21, 13:10
  ~ Last edited: 08/02/21, 13:10
  --%>

<%--
  Created by IntelliJ IDEA.
  User: lpant
  Date: 08/02/2021
  Time: 13:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="searchBean" class="it.soundmate.bean.searchbeans.SearchBean" scope="session"/>
<jsp:setProperty name="searchBean" property="searchString"/>
<!DOCTYPE>
<html lang="it">
<head>
    <title>Search</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script></head>
</head>
<body>

    <%
        if (request.getParameter("search") != null) {
            session.setAttribute("searchString", searchBean.getSearchString());
            response.sendRedirect("search.jsp");
        }
    %>

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="#">Soundmate</a>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="home.jsp">Home <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="messages.jsp">Messages</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="profile.jsp">Profile</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="index.jsp">Logout</a>
                </li>
            </ul>
            <form class="form-inline my-2 my-lg-0">
                <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" name="searchString">
                <input class="btn btn-outline-success my-2 my-sm-0" type="submit" value="Search" name="search"/>
            </form>
        </div>
    </nav>

    <%
        String searchString = (String) session.getAttribute("searchString");
        User user = (User) session.getAttribute("loggedUser");
        SearchController searchController = new SearchController();
        List<UserResultBean> userResultBeanList = searchController.performSearch(searchString, new boolean[]{false, false, false}, new String[]{"NONE", "NONE", ""}, user);
        List<SoloResultBean> soloResultBeanList = new ArrayList<>();
        List<BandResultBean> bandResultBeanList = new ArrayList<>();
        List<RoomRenterResultBean> roomRenterResultBeanList = new ArrayList<>();
        for (UserResultBean userResultBean : userResultBeanList) {
            System.out.println(userResultBean.getName());
            switch (userResultBean.getUserType()) {
                case SOLO:
                    soloResultBeanList.add((SoloResultBean) userResultBean);
                    break;
                case BAND:
                    bandResultBeanList.add((BandResultBean) userResultBean);
                    break;
                case ROOM_RENTER:
                    roomRenterResultBeanList.add((RoomRenterResultBean) userResultBean);
                    break;
            }
        }
        request.setAttribute("soloResults", soloResultBeanList);
        request.setAttribute("bandResults", bandResultBeanList);
        request.setAttribute("roomRenterResults", roomRenterResultBeanList);

        //Visit profile action
        for (UserResultBean userResultBean : userResultBeanList) {
            System.out.println(userResultBean.getId());
            System.out.println(request.getParameter("visit"+userResultBean.getId()));
            if (request.getParameter("visit" + userResultBean.getId()) != null) {
                System.out.println(userResultBean.getId() + "not null request");
                session.setAttribute("visitedUser", userResultBean);
                response.sendRedirect("visitProfile.jsp");
            }
        }
    %>

    <h1>Search Results for <%=searchString%></h1>

    <h3>Solos</h3>

    <c:forEach items="${soloResults}" var="solo">
        <div class="row mx-auto">
            <div class="col-md-2 " style="margin-bottom:10px;">
                <div class="card" style="width: 18rem;">
                    <img class="card-img-top" src="" alt="">
                    <div class="card-body">
                        <h5 class="card-title"><c:out value="${solo.name}"/></h5>
                        <form>
                            <input type="submit" name="visit${solo.id}" value="Visit Profile" class="btn btn-primary">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>


    <!-- Musicians you may know -->

    <h3>Bands</h3>
    <c:forEach items="${bandResults}" var="band">
        <div class="row mx-auto">
            <div class="col-md-2 " style="margin-bottom:10px;">
                <div class="card" style="width: 18rem;">
                    <img class="card-img-top" src="" alt="">
                    <div class="card-body">
                        <h5 class="card-title"><c:out value="${band.name}"/></h5>
                        <form>
                            <input type="submit" name="visit${band.id}" value="Visit Profile" class="btn btn-primary">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>


    <!-- Musicians you may know -->

    <h3>Room Renters</h3>
    <c:forEach items="${roomRenterResults}" var="renter">
        <div class="row mx-auto">
            <div class="col-md-2 " style="margin-bottom:10px;">
                <div class="card" style="width: 18rem;">
                    <img class="card-img-top" src="" alt="">
                    <div class="card-body">
                        <h5 class="card-title"><c:out value="${renter.name}"/></h5>
                        <form>
                            <input type="submit" name="visit${renter.id}" value="Visit Profile" class="btn btn-primary">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>

</body>
</html>
