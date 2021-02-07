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
<%--
  ~ Copyright (c) 2021.
  ~ Created by Lorenzo Pantano on 01/02/21, 22:12
  ~ Last edited: 01/02/21, 22:12
  --%>

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
                <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>
        </div>
    </nav>


    <%
        HomeController homeController = new HomeController(user);
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        List<SoloResultBean> soloList = homeController.searchHomeSolos(user.getCity());
        List<BandResultBean> bandList = homeController.searchHomeBands(user.getCity());
        List<RoomRenterResultBean> roomRenterResultBeanList = homeController.searchHomeRenters(user.getCity());
        request.setAttribute("soloList",soloList);
        request.setAttribute("bandList", bandList);
        request.setAttribute("roomRenterList", roomRenterResultBeanList);
    %>

    <!-- Musicians you may know -->

    <h3>Musicians you may know</h3>

    <c:forEach items="${soloList}" var="solo">
        <tr>
            <td>Name: <c:out value="${solo.name}"/></td>
            <td>Email: <c:out value="${solo.email}"/></td>
        </tr>
    </c:forEach>

    <div class="row mx-auto">

        <div class="col-md-2 " style="margin-bottom:10px;">
            <div class="card" style="width: 18rem;">
                <img class="card-img-top" src="" alt="">
                <div class="card-body">
                    <h5 class="card-title">Lorenzo Pantano (eg)</h5>
                    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                    <input type="submit" name="visit" value="Visit Profile" class="btn btn-primary">
                </div>
            </div>
        </div>

    </div>

    <!-- Musicians you may know -->

    <h3>Bands in your city</h3>
    <div class="row mx-auto">

        <div class="col-md-2 " style="margin-bottom:10px;">
            <div class="card" style="width: 18rem;">
                <img class="card-img-top" src="" alt="">
                <div class="card-body">
                    <h5 class="card-title">Dream Theatre</h5>
                    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                    <input type="submit" name="visit" value="Visit Profile" class="btn btn-primary">
                </div>
            </div>
        </div>

    </div>


    <!-- Musicians you may know -->

    <h3>Room Renters near you</h3>
    <div class="row mx-auto">
        <div class="col-md-2 " style="margin-bottom:10px;">
            <div class="card" style="width: 18rem;">
                <img class="card-img-top" src="" alt="">
                <div class="card-body">
                    <h5 class="card-title">Music Station</h5>
                    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                    <input type="submit" name="visit" value="Visit Profile" class="btn btn-primary">
                </div>
            </div>
        </div>
    </div>

</body>
</html>
