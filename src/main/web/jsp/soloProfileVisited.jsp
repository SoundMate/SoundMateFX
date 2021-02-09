<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="it.soundmate.model.User" %>
<%@ page import="it.soundmate.model.Solo" %>
<%@ page import="it.soundmate.model.Band" %>
<%@ page import="it.soundmate.model.RoomRenter" %>
<%@ page import="it.soundmate.bean.searchbeans.UserResultBean" %>
<%@ page import="it.soundmate.bean.searchbeans.SoloResultBean" %>
<%@ page import="it.soundmate.bean.searchbeans.BandResultBean" %>
<%@ page import="it.soundmate.bean.searchbeans.RoomRenterResultBean" %>
<%@ page import="it.soundmate.controller.logic.MessagesController" %><%--
  ~ Copyright (c) 2021.
  ~ Created by Lorenzo Pantano on 09/02/21, 11:49
  ~ Last edited: 09/02/21, 11:49
  --%>

<%--
  Created by IntelliJ IDEA.
  User: lpant
  Date: 09/02/2021
  Time: 11:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="searchBean" class="it.soundmate.bean.searchbeans.SearchBean" scope="session"/>
<jsp:setProperty name="searchBean" property="searchString"/>
<jsp:useBean id="messageBean" class="it.soundmate.model.Message"/>
<jsp:setProperty name="messageBean" property="body"/>
<jsp:setProperty name="messageBean" property="subject"/>
<!DOCTYPE>
<html lang="it">
<head>
    <title>Solo Profile Visited</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>
</head>
<body>

<%
    SoloResultBean solo = (SoloResultBean) session.getAttribute("soloVisitedUser");
    request.setAttribute("solo", solo);
    User loggedUser = (User) session.getAttribute("loggedUser");
    request.setAttribute("loggedUser", loggedUser);
%>


<!--Search Action-->
<%
    if (request.getParameter("search") != null) {
        session.setAttribute("searchString", searchBean.getSearchString());
        response.sendRedirect("search.jsp");
    }
%>

<%
    if (request.getParameter("sendMessage") != null) {
        MessagesController messagesController = new MessagesController();
        messageBean.setSenderUserType(loggedUser.getUserType());
        messageBean.setIdSender(loggedUser.getId());
        messageBean.setIdReceiver(solo.getId());
        messagesController.sendMessage(messageBean);
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
            <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" name="searchString">
            <input class="btn btn-outline-success my-2 my-sm-0" type="submit" value="Search" name="search"/>
        </form>
    </div>
</nav>

<div class="row py-5 px-4">
    <div class="col-md-5 mx-auto">
        <!-- Profile widget -->
        <div class="bg-black shadow rounded overflow-hidden">
            <div class="px-4 pt-0 pb-4 cover">
                <div class="media align-items-end profile-head">
                    <div class="profile mr-3 bg-black"><img src="../../resources/soundmate/images/user-default.png" alt="..." width="130" class="rounded mb-2 img-thumbnail bg-black"></div>
                    <div class="media-body mb-5 text-black">
                        <h4 class="mt-0 mb-0"><c:out value="${solo.name}"/></h4>
                        <p class="small mb-4"><c:out value="${solo.city}"/></p>
                    </div>
                </div>
            </div>
            <h5>Instruments</h5>
            <div class="bg-light p-4 d-flex justify-content-start text-center">
                <ul class="list-inline mb-0">
                    <c:forEach items="${solo.instrumentList}" var="instrument">
                        <li class="inline-list-item">
                            <c:out value="${instrument}"/>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <h5>Genres</h5>
            <div class="bg-light p-4 d-flex justify-content-start text-center">
                <ul class="list-inline mb-0">
                    <c:forEach items="${solo.genreList}" var="genre">
                        <li class="inline-list-item">
                            <c:out value="${genre}"/>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="px-4 py-3">
                <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
                    Send Message
                </a>
            </div>
            <div class="collapse" id="collapseExample">
                <div class="card card-body">
                    <form>
                        <div class="form-group">
                            <label for="exampleFormControlTextarea1">Subject</label>
                            <textarea class="form-control" id="exampleFormControlTextarea1" rows="1" name="subject"></textarea>
                            <label for="exampleFormControlTextarea1">Body</label>
                            <textarea class="form-control" id="exampleFormControlTextarea2" rows="3" name="body"></textarea>
                        </div>
                        <input type="submit" class="btn btn-primary" value="Send" name="sendMessage"/>>
                    </form>
                </div>
            </div>
            <div class="px-4 py-3">
                <h5 class="mb-0">Bands</h5>
                <div class="p-4 rounded shadow-sm bg-dark">

                </div>
            </div>
            <div class="py-4 px-4">
                <div class="d-flex align-items-center justify-content-between mb-3">
                    <h5 class="mb-0">Photos</h5><a href="#" class="btn btn-link text-muted">Show all</a>
                </div>
                <div class="row">
                    <div class="col-lg-6 mb-2 pr-lg-1"><img src="https://images.unsplash.com/photo-1469594292607-7bd90f8d3ba4?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80" alt="" class="img-fluid rounded shadow-sm"></div>
                    <div class="col-lg-6 mb-2 pl-lg-1"><img src="https://images.unsplash.com/photo-1493571716545-b559a19edd14?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80" alt="" class="img-fluid rounded shadow-sm"></div>
                    <div class="col-lg-6 pr-lg-1 mb-2"><img src="https://images.unsplash.com/photo-1453791052107-5c843da62d97?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80" alt="" class="img-fluid rounded shadow-sm"></div>
                    <div class="col-lg-6 pl-lg-1"><img src="https://images.unsplash.com/photo-1475724017904-b712052c192a?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80" alt="" class="img-fluid rounded shadow-sm"></div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
