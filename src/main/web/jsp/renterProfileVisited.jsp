<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="it.soundmate.controller.logic.MessagesController" %>
<%@ page import="it.soundmate.bean.searchbeans.RoomRenterResultBean" %>
<%@ page import="it.soundmate.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="it.soundmate.model.Room" %>
<%@ page import="it.soundmate.controller.graphic.profiles.RoomRenterProfileGraphicController" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="it.soundmate.exceptions.InputException" %><%--
  ~ Copyright (c) 2021.
  ~ Created by Lorenzo Pantano on 10/02/21, 21:34
  ~ Last edited: 10/02/21, 21:34
  --%>

<%--
  Created by IntelliJ IDEA.
  User: lpant
  Date: 10/02/2021
  Time: 21:34
  To change this template use File | Settings | File Templates.
--%>
<jsp:useBean id="searchBean" class="it.soundmate.bean.searchbeans.SearchBean" scope="session"/>
<jsp:setProperty name="searchBean" property="searchString"/>
<jsp:useBean id="messageBean" class="it.soundmate.model.Message"/>
<jsp:setProperty name="messageBean" property="body"/>
<jsp:setProperty name="messageBean" property="subject"/>
<jsp:useBean id="booking" class="it.soundmate.model.Booking"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html lang="it">
<head>
    <title>Room Renter</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>
</head>
<body>

<%
    RoomRenterResultBean roomRenter = (RoomRenterResultBean) session.getAttribute("renterVisitedUser");
    request.setAttribute("roomRenter", roomRenter);
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

<!-- Message Action -->
<%
    if (request.getParameter("sendMessage") != null) {
        MessagesController messagesController = new MessagesController();
        messageBean.setSenderUserType(loggedUser.getUserType());
        messageBean.setIdSender(loggedUser.getId());
        messageBean.setIdReceiver(roomRenter.getId());
        messagesController.sendMessage(messageBean);
    }
%>

<!--Book Room Action-->
<%
    for (Room room : roomRenter.getRooms()) {
        if (request.getParameter("bookRoom"+room.getCode()) != null) {
            RoomRenterProfileGraphicController roomRenterProfileGraphicController = new RoomRenterProfileGraphicController();
            booking.setRoom(room);
            String dateString = request.getParameter("date");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(dateString, formatter);
            booking.setDate(localDate);
            LocalTime start = LocalTime.parse(request.getParameter("startTime"));
            LocalTime end = LocalTime.parse(request.getParameter("endTime"));
            booking.setStartTime(start);
            booking.setEndTime(end);
            booking.setIdRenter(roomRenter.getId());
            booking.setBookerUserId(loggedUser.getId());
            try {
                roomRenterProfileGraphicController.checkRoomAvailability(localDate, request.getParameter("startTime"), request.getParameter("endTime"), room);
            } catch (InputException inputException) {
                System.out.println("Room occupied");
            }
            roomRenterProfileGraphicController.bookRoom(booking);
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
                        <h4 class="mt-0 mb-0"><c:out value="${roomRenter.name}"/></h4>
                        <p class="small mb-4"><c:out value="${roomRenter.city}"/>, <c:out value="${roomRenter.address}"/></p>
                    </div>
                </div>
            </div>
            <div class="px-4 py-3">
                <a class="btn btn-primary" data-toggle="collapse" href="#collapseExampleMessage" role="button" aria-expanded="false" aria-controls="collapseExample">
                    Send Message
                </a>
            </div>
            <div class="collapse" id="collapseExampleMessage">
                <div class="card card-body">
                    <form>
                        <div class="form-group">
                            <label for="exampleFormControlTextarea1">Subject</label>
                            <textarea class="form-control" id="exampleFormControlTextarea1" rows="1" name="subject"></textarea>
                            <label for="exampleFormControlTextarea2">Body</label>
                            <textarea class="form-control" id="exampleFormControlTextarea2" rows="3" name="body"></textarea>
                        </div>
                        <input type="submit" class="btn btn-primary" value="Send" name="sendMessage"/>>
                    </form>
                </div>
            </div>
            <div class="px-4 py-3">
                <h5 class="mb-0">Rooms</h5>
                <div class="p-4 rounded shadow-sm bg-dark">
                    <c:forEach items="${roomRenter.rooms}" var="room">
                        <div class="px-4 py-3">
                            <h6>Room: ${room.name}</h6>
                            <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample${room.code}" role="button" aria-expanded="false" aria-controls="collapseExample">
                                View Room
                            </a>
                        </div>
                        <div class="collapse" id="collapseExample${room.code}">
                            <div class="card card-body">
                                <form>
                                    <div class="form-group">
                                        <label for="exampleFormControlTextarea1">Description</label>
                                        <p class="form-control" id="exampleFormControlTextarea4">${room.description}</p>
                                    </div>
                                    <div class="form-group">
                                        <label for="exampleFormControlTextarea1">Price</label>
                                        <p class="form-control" id="exampleFormControlTextarea5">${room.price} $/hr</p>
                                    </div>
                                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenterRequest${room.code}">
                                       Book Room
                                    </button>
                                    <div class="modal fade" id="exampleModalCenterRequest${room.code}" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitleRequest" aria-hidden="true">
                                        <div class="modal-dialog modal-dialog-centered" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="exampleModalCenterTitleRequest">Book Room</h5>
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <div class="modal-body">
                                                    <form>
                                                        <div class="mx-auto row">
                                                            <label for="message-request">Date</label>
                                                            <input id="message-request" type="date" name="date">
                                                        </div>
                                                        <div class="mx-auto row">
                                                            <label for="request-time">Start time</label>
                                                            <input id="request-time" type="text" name="startTime">
                                                        </div>
                                                        <div class="mx-auto row">
                                                            <label for="request-time2">End time</label>
                                                            <input id="request-time2" type="text" name="endTime">
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                                            <input type="submit" class="btn btn-primary" name="bookRoom${room.code}" value="Book"/>
                                                        </div>
                                                    </form>

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
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
