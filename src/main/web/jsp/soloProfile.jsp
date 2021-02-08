<%@ page import="it.soundmate.model.Solo" %><%--
  ~ Copyright (c) 2021.
  ~ Created by Lorenzo Pantano on 02/02/21, 18:30
  ~ Last edited: 02/02/21, 18:30
  --%>

<%--
  Created by IntelliJ IDEA.
  User: lpant
  Date: 02/02/2021
  Time: 18:30
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
        Solo solo = (Solo) session.getAttribute("loggedUser");
    %>

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a href="#" class="logo-link"><img src="../../resources/soundmate/images/logo.svg" alt="Soundmate Logo" class="header-logo"></a>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="home.jsp">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="messages.jsp">Messages</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="messages.jsp">Join Request</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="profile.jsp">Profile <span class="sr-only">(current)</span></a>
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
    <div class="row py-5 px-4">
        <div class="col-md-5 mx-auto">
            <!-- Profile widget -->
            <div class="bg-black shadow rounded overflow-hidden">
<%--                <div class="px-4 pt-0 pb-4 cover">--%>
<%--                    <div class="media align-items-end profile-head">--%>
<%--                        <div class="profile mr-3"><img src="https://images.unsplash.com/photo-1522075469751-3a6694fb2f61?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=80" alt="..." width="130" class="rounded mb-2 img-thumbnail"><a href="#" class="btn btn-outline-dark btn-sm btn-block">Edit profile</a></div>--%>
<%--                        <div class="media-body mb-5 text-black">--%>
<%--                            <h4 class="mt-0 mb-0"><%System.out.println(solo.getFirstName()+ " "+solo.getLastName());%></h4>  <!--Non funziona così-->--%>
<%--                            <p class="small mb-4"> <em class="fas fa-map-marker-alt mr-2"></em><%System.out.println(solo.getCity());%></p>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
                <div class="bg-dark p-4 d-flex justify-content-end text-center">
                    <ul class="list-inline mb-0">
                        <li class="list-inline-item">
                            <!--Add instruments here-->
                        </li>
                        <li class="list-inline-item">
                        </li>
                        <li class="list-inline-item">
                        </li>
                    </ul>
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
