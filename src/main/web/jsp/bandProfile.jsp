<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="it.soundmate.model.Band" %>
<%@ page import="it.soundmate.model.Application" %>
<%@ page import="it.soundmate.controller.logic.ApplicationController" %>
<%@ page import="java.util.List" %>
<%@ page import="it.soundmate.view.uicomponents.InstrumentGraphics" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.ArrayList" %>
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
<jsp:useBean id="applicationBean" class="it.soundmate.model.Application"/>
<jsp:setProperty name="applicationBean" property="message"/>
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
    Band band = (Band) session.getAttribute("loggedUser");
    request.setAttribute("band", band);
    List<Application> applicationList;
    ApplicationController applicationController = new ApplicationController();
    applicationList = applicationController.getApplicationsForBand(band.getId());
    request.setAttribute("applicationList", applicationList);
%>

<!--Search action-->
<%
    if (request.getParameter("search") != null) {
        session.setAttribute("searchString", searchBean.getSearchString());
        response.sendRedirect("search.jsp");
    }
%>

<!-- Create application action -->
<%

    if (request.getParameter("createApplication") != null) {
        applicationBean.setBand(band.getId());
        List<String> instrumentsList = new ArrayList<>();
        String[] selectedInstruments = request.getParameterValues("checkboxes");
        if (selectedInstruments != null && selectedInstruments.length != 0) {
            for (String selectedInstrument : selectedInstruments) {
                if (selectedInstrument != null) {
                    instrumentsList.add(selectedInstrument);
                }
            }
            applicationBean.setInstrumentsList(instrumentsList);
            applicationController.addApplication(applicationBean);
            System.out.println("Selected instruments: "+ instrumentsList);
        } else {
            System.out.println("Select at least one instrument");
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
                        <h4 class="mt-0 mb-0"><c:out value="${band.name}"/></h4>
                        <p class="small mb-4"><c:out value="${band.city}"/></p>
                    </div>
                </div>
            </div>
            <h5>Genres</h5>
            <div class="bg-light p-4 d-flex justify-content-start text-center">
                <ul class="list-inline mb-0">
                    <c:forEach items="${band.genres}" var="genre">
                        <li class="inline-list-item">
                            <c:out value="${genre.toString()}"/>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="px-4 py-3">
                <h5 class="mb-0">Applications</h5>
                <div class="p-4 rounded shadow-sm bg-dark">
                    <c:forEach items="${applicationList}" var="application">
                        <div class="px-4 py-3">
                            <h6>Application ${application.applicationCode}</h6>
                            <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample${application.applicationCode}" role="button" aria-expanded="false" aria-controls="collapseExample">
                                View Application
                            </a>
                        </div>
                        <div class="collapse" id="collapseExample${application.applicationCode}">
                            <div class="card card-body">
                                <form>
                                    <div class="form-group">
                                        <label for="exampleFormControlTextarea4">Message</label>
                                        <p class="form-control" id="exampleFormControlTextarea4">${application.message}</p>
                                    </div>
                                    <div class="form-group">
                                        <label for="exampleFormControlTextarea4">Instruments Wanted</label>
                                        <p class="form-control" id="exampleFormControlTextarea5">${application.instrumentsList}</p>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <!-- Button trigger modal -->
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter">
                Create Application
            </button>

            <!-- Modal -->
            <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalCenterTitle">Create application</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="mx-auto row">
                                    <label for="message-text-area">Message</label>
                                    <textarea name="message" rows="3" id="message-text-area"></textarea>
                                </div>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="checkbox" id="inlineCheckbox1" value="Guitar" name="checkboxes">
                                    <label class="form-check-label" for="inlineCheckbox1">Guitar</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="checkbox" id="inlineCheckbox2" value="Bass" name="checkboxes">
                                    <label class="form-check-label" for="inlineCheckbox2">Bass</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="checkbox" id="inlineCheckbox3" value="Drum" name="checkboxes">
                                    <label class="form-check-label" for="inlineCheckbox3">Drum</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="checkbox" id="inlineCheckbox4" value="Mic" name="checkboxes">
                                    <label class="form-check-label" for="inlineCheckbox4">Mic</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="checkbox" id="inlineCheckbox5" value="Keyboard" name="checkboxes">
                                    <label class="form-check-label" for="inlineCheckbox5">Keyboard</label>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                    <input type="submit" class="btn btn-primary" name="createApplication" value="Create"/>
                                </div>
                            </form>

                        </div>
                    </div>
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
