<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="it.soundmate.model.User" %>
<%@ page import="it.soundmate.model.Solo" %>
<%@ page import="it.soundmate.model.Band" %>
<%@ page import="it.soundmate.model.RoomRenter" %>
<%@ page import="it.soundmate.controller.logic.HomeController" %>
<%@ page import="java.util.List" %>
<%@ page import="it.soundmate.bean.searchbeans.SoloResultBean" %>
<%@ page import="it.soundmate.bean.searchbeans.BandResultBean" %>
<%@ page import="it.soundmate.bean.searchbeans.RoomRenterResultBean" %>
<%@ page import="it.soundmate.bean.searchbeans.UserResultBean" %>
<%@ page import="java.util.ArrayList" %>
<%--
  ~ Copyright (c) 2021.
  ~ Created by Lorenzo Pantano on 01/02/21, 22:12
  ~ Last edited: 01/02/21, 22:12
  --%>
<jsp:useBean id="searchBean" class="it.soundmate.bean.searchbeans.SearchBean" scope="session"/>
<jsp:setProperty name="searchBean" property="searchString"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Homepage</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>

    <%
        User user = (User) session.getAttribute("user");
        if (session.getAttribute("user") != null) {
            switch (user.getUserType()) {
                case SOLO:
                    Solo solo = (Solo) user;
                    System.out.println(solo.getFirstName() + solo.getLastName());
                    session.setAttribute("loggedUser", solo);
                    break;
                case BAND:
                    Band band = (Band) user;
                    System.out.println(band.getBandName());
                    session.setAttribute("loggedUser", band);
                case ROOM_RENTER:
                    RoomRenter roomRenter;
                    if (user instanceof RoomRenter) {
                        roomRenter = (RoomRenter) user;
                        session.setAttribute("loggedUser", roomRenter);
                        System.out.println(roomRenter.getName());
                    }
            }
        }
    %>

    <!--Search action-->
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
                    <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
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
                <input id="searchBox" class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" name="searchString">
                <input id="searchBtn" class="btn btn-outline-success my-2 my-sm-0" type="submit" value="Search" name="search"/>
            </form>
        </div>
    </nav>


    <%
        HomeController homeController = new HomeController(user);
        List<SoloResultBean> soloList = homeController.searchHomeSolos(user.getCity());
        List<BandResultBean> bandList = homeController.searchHomeBands(user.getCity());
        List<RoomRenterResultBean> roomRenterResultBeanList = homeController.searchHomeRenters(user.getCity());
        List<UserResultBean> allResults = new ArrayList<>();
        allResults.addAll(soloList);
        allResults.addAll(bandList);
        allResults.addAll(roomRenterResultBeanList);
        request.setAttribute("soloList",soloList);
        request.setAttribute("bandList", bandList);
        request.setAttribute("roomRenterList", roomRenterResultBeanList);

        //Visit profile action
        for (UserResultBean userResultBean : allResults) {
            System.out.println(userResultBean.getId());
            System.out.println(request.getParameter("visit"+userResultBean.getId()));
            if (request.getParameter("visit" + userResultBean.getId()) != null) {
                System.out.println(userResultBean.getId() + "not null request");
                session.setAttribute("visitedUser", userResultBean);
                response.sendRedirect("visitProfile.jsp");
            }
        }
    %>

    <h1 id="welcomeUser">Welcome, <c:out value="${loggedUser.name}"/></h1>

    <!-- Musicians you may know -->

    <h3>Musicians you may know</h3>

    <c:forEach items="${soloList}" var="solo">
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

    <h3>Bands in your city</h3>
    <c:forEach items="${bandList}" var="band">
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

    <h3>Room Renters near you</h3>
    <c:forEach items="${roomRenterList}" var="renter">
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
