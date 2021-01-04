<%@ page import="it.soundmate.model.User" %>
<%@ page import="it.soundmate.model.UserType" %><%--
  ~ Copyright (c) 2020.
  ~ This file was created by Soundmate organization Lorenzo Pantano & Matteo D'Alessandro
  ~ Last Modified: 01/12/20, 22:15
  --%>

<%--
  Created by IntelliJ IDEA.
  User: lpant
  Date: 01/12/2020
  Time: 22:15
  To change this template use File | Settings | File Templates.
--%>


<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="IdeaProjects/SoundmateFX/src/main/web/css/register.scss">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&display=swap" rel="stylesheet">
    <title>Register to Soundmate</title>
</head>


<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="registerBean" scope="request" class="it.soundmate.bean.RegisterBean"/>
<jsp:useBean id="registerController" scope="request" class="it.soundmate.controller.RegisterController"/>
<jsp:setProperty name="registerBean" property="*"/>
<jsp:useBean id="user" scope="session" class="it.soundmate.model.User"/>

<!-- Register Request -->
<%
    if (request.getParameter("continue")!=null) {

        switch (request.getParameter("register")) {
                case "Band Room":
                if (!registerController.checkName()) {
%>
                        <div style="align-content: center; color: red; justify-content: center; align-items: center;">
                            <p style="font-weight: bold; color: white; background: red; margin: 0 auto; padding: 1em; text-align: center">Please insert a valid Name</p>
                        </div>
<%
                } else {
                    user = registerController.registerUser(UserType.BAND_ROOM_MANAGER);
                    if (user != null) {
                        //Forward to page with user parameters
                    } else {
                        //Display error message
%>
                            <div style="align-content: center; color: red; justify-content: center; align-items: center;">
                                <p style="font-weight: bold; color: white; background: red; margin: 0 auto; padding: 1em; text-align: center">Something went wrong during registration process</p>
                            </div>
<%
                    }
                }
                break;

                case "Band":
                    if (!registerController.checkName()) {
%>
                            <div style="align-content: center; color: red; justify-content: center; align-items: center;">
                                <p style="font-weight: bold; color: white; background: red; margin: 0 auto; padding: 1em; text-align: center">Please insert a valid Name</p>
                            </div>
<%
                    } else {
                        user = registerController.registerUser(UserType.BAND_MANAGER);
                        if (user != null) {
                            //Forward to page with user parameters
                        } else {
                            //Display error message
%>
                            <div style="align-content: center; color: red; justify-content: center; align-items: center;">
                                <p style="font-weight: bold; color: white; background: red; margin: 0 auto; padding: 1em; text-align: center">Something went wrong during registration process</p>
                            </div>
<%
                        }
                    }
                    break;
                case "Solo":
                    user = registerController.registerUser(UserType.SOLO);
                    if (user != null) {
                        //Forward
                    } else {
%>
                        <div style="align-content: center; color: red; justify-content: center; align-items: center;">
                            <p style="font-weight: bold; color: white; background: red; margin: 0 auto; padding: 1em; text-align: center">Something went wrong during registration process</p>
                        </div>
<%
                    }
                    break;

            }
    }
%>

<body>

<div class="main">
    <img src="../../resources/soundmate/images/logo-v.svg" class="main-img" alt="logo">
    <form action="" method="post" name="register-form">
        <div class="common-form">
            <div class="common-fields">
                <div class="email-and-password">
                    <div class="field">
                        <label for="email">Email</label>
                        <input type="text" name="email" id="email" class="text-field-style">
                    </div>
                    <div class="field">
                        <label for="password-register">Password</label>
                        <input type="text" name="password" id="password-register" class="text-field-style">
                    </div>
                </div>
                <div class="names">
                    <div class="field">
                        <label for="firstName">First Name</label>
                        <input type="text" name="firstName" id="firstName" class="text-field-style">
                    </div>
                    <div class="field">
                        <label for="lastName">Last Name</label>
                        <input type="text" name="lastName" id="lastName" class="text-field-style">
                    </div>
                </div>
            </div>
        </div>

        <%
            switch (request.getParameter("register")) {
                case "Band":
        %>
        <div class="specific-form">
            <input type="text" name="name" id="bandName" value="" class="band-name-form"
                   placeholder="Band Name...">
        </div>
        <%
                break;
            case "Band Room":
        %>
        <div class="specific-form">
            <input type="text" name="name" id="bandRoomName" value="" class="band-name-form"
                   placeholder="Band Room Name...">
        </div>
        <%
                break;
            case "Solo":
                break;
            }
        %>

        <input type="submit" value="Continue" class="btn continue-btn" name="continue">
    </form>
</div>

</body>