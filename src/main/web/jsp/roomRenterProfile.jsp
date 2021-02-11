<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="it.soundmate.model.RoomRenter" %>
<%@ page import="it.soundmate.controller.logic.profiles.RoomRenterProfileController" %>
<%@ page import="it.soundmate.model.Booking" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="it.soundmate.controller.graphic.profiles.RoomRenterProfileGraphicController" %>
<%@ page import="it.soundmate.model.UserType" %>
<%@ page import="it.soundmate.controller.logic.MessagesController" %>
<%--
  ~ Copyright (c) 2021.
  ~ Created by Lorenzo Pantano on 02/02/21, 18:31
  ~ Last edited: 02/02/21, 18:31
  --%>

<%--
  Created by IntelliJ IDEA.
  User: lpant
  Date: 02/02/2021
  Time: 18:31
  To change this template use File | Settings | File Templates.
--%>
<jsp:useBean id="searchBean" class="it.soundmate.bean.searchbeans.SearchBean" scope="session"/>
<jsp:setProperty name="searchBean" property="searchString"/>
<jsp:useBean id="messageBean" class="it.soundmate.model.Message"/>
<jsp:setProperty name="messageBean" property="body"/>
<jsp:useBean id="roomBean" class="it.soundmate.bean.AddRoomBean"/>
<jsp:setProperty name="roomBean" property="description"/>
<jsp:setProperty name="roomBean" property="name"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html lang="it">
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>
</head>
<body>
<%
    RoomRenter roomRenter = (RoomRenter) session.getAttribute("loggedUser");
    request.setAttribute("roomRenter", roomRenter);
    RoomRenterProfileGraphicController roomRenterProfileController = new RoomRenterProfileGraphicController();
    List<Booking> bookingList = roomRenterProfileController.getBookings(roomRenter);
    request.setAttribute("bookingList", bookingList);
%>

<!--Search Action-->
<%
    if (request.getParameter("search") != null) {
        session.setAttribute("searchString", searchBean.getSearchString());
        response.sendRedirect("search.jsp");
    }
%>

<!-- Add room action -->
<%
    if (request.getParameter("addRoom") != null) {
        String price = request.getParameter("price");
        int priceInt = Integer.parseInt(price);
        roomBean.setPrice(priceInt);
        roomRenterProfileController.addRoom(roomBean, roomRenter);
    }
%>


<!--Send message action-->
<%

    MessagesController messagesController = new MessagesController();
    for (Booking booking : bookingList) {
        if (request.getParameter("sendMessage"+booking.getCode()) != null) {
            messageBean.setSubject("Booking #"+booking.getCode());
            messageBean.setIdSender(roomRenter.getId());
            messageBean.setIdReceiver(booking.getBookerUserId());
            messageBean.setSenderUserType(UserType.ROOM_RENTER);
            messagesController.sendMessage(messageBean);
        }
    }

%>

<!--Accept booking action-->
<%

    for (Booking booking : bookingList) {
        if (request.getParameter("acceptBooking"+booking.getCode()) != null) {
            roomRenterProfileController.acceptBooking(booking);
        }
    }

%>


<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">Soundmate</a>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="home.jsp">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="messages.jsp">Messages</a>
            </li>
            <li class="nav-item">
                <a class="nav-link active" href="profile.jsp">Profile<span class="sr-only">(current)</span></a>
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
                        <p class="small mb-4"><c:out value="${roomRenter.city}"/> <c:out value="${roomRenter.address}"/></p>
                    </div>
                </div>
            </div>
            <div class="px-4 py-3">
                <h5 class="mb-0">Rooms</h5>
                <div class="p-4 rounded shadow-sm bg-dark">
                    <c:forEach items="${roomRenter.rooms}" var="room">
                        <div class="px-4 py-3">
                            <h6>${room.name}</h6>
                            <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample${room.code}" role="button" aria-expanded="false" aria-controls="collapseExample">
                                View Room
                            </a>
                        </div>
                        <div class="collapse" id="collapseExample${room.code}">
                            <div class="card card-body">
                                <form>
                                    <div class="form-group">
                                        <label for="exampleFormControlTextarea4">Price</label>
                                        <p class="form-control" id="exampleFormControlTextarea4">${room.price}</p>
                                    </div>
                                    <div class="form-group">
                                        <label for="exampleFormControlTextarea4">Description</label>
                                        <p class="form-control" id="exampleFormControlTextarea5">${room.description}</p>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </c:forEach>

                </div>
            </div>

            <!-- Button trigger modal -->
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter">
                Add Room
            </button>

            <!-- Modal -->
            <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalCenterTitle">Add new Room</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="mx-auto row">
                                    <label for="name-text-area">Name</label>
                                    <textarea name="name" rows="1" id="name-text-area"></textarea>
                                </div>
                                <div class="mx-auto row">
                                    <label for="price-text-area">Price</label>
                                    <textarea name="price" rows="1" id="price-text-area"></textarea>
                                </div>
                                <div class="mx-auto row">
                                    <label for="desc-text-area">Description</label>
                                    <textarea name="description" rows="3" id="desc-text-area"></textarea>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                    <input type="submit" class="btn btn-primary" name="addRoom" value="Add"/>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>


            <!-- BOOKINGS -->
            <div class="px-4 py-3">
                <h5 class="mb-0">Bookings</h5>
                <div class="p-4 rounded shadow-sm bg-dark">
                    <c:forEach items="${bookingList}" var="booking">
                        <div class="px-4 py-3">
                            <h6>Booking ${booking.code}</h6>
                            <!-- Button trigger modal -->
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenterBooking${booking.code}">
                                View Booking
                            </button>

                            <!-- Modal -->
                            <div class="modal fade" id="exampleModalCenterBooking${booking.code}" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="exampleModalCenterTitleBooking">Booking #${booking.code}</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <form>
                                                <div class="mx-auto row">
                                                    <label for="room-text-area">Room</label>
                                                    <p id="room-text-area">${booking.room.name}</p>
                                                </div>
                                                <div class="mx-auto row">
                                                    <label for="booker-text-area">Booker</label>
                                                    <p id="booker-text-area">${booking.booker.name}</p>
                                                </div>
                                                <div class="mx-auto row">
                                                    <label for="date-text-area">Date</label>
                                                    <p id="date-text-area">${booking.date.toString()}</p>
                                                </div>
                                                <div class="mx-auto row">
                                                    <label for="starting-text-area">Start time</label>
                                                    <p id="starting-text-area">${booking.startTime}</p>
                                                </div>
                                                <div class="mx-auto row">
                                                    <label for="ending-text-area">End time</label>
                                                    <p id="ending-text-area">${booking.endTime}</p>
                                                </div>
                                                <div class="mx-auto row">
                                                    <label for="message-text-area">Send message</label>
                                                    <input type="text" name="body" id="message-text-area"/>
                                                    <input type="submit" class="btn btn-primary" name="sendMessage${booking.code}" value="Send Message"/>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                                    <input type="submit" class="btn btn-primary" name="acceptBooking${booking.code}" value="Accept"/>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
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
