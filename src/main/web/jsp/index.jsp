<%@ page import="it.soundmate.controller.logic.LoginController" %>
<%@ page import="it.soundmate.model.User" %>

<%--
  ~ Copyright (c) 2020.
  ~ This file was created by Soundmate organization Lorenzo Pantano & Matteo D'Alessandro
  ~ Last Modified: 28/11/20, 13:31
  --%>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/style.scss">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&display=swap" rel="stylesheet">
    <script src="../js/smoothScrolling.js"></script>
    <title>Soundmate</title>
</head>

<%@page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="loginBean" scope="request" class="it.soundmate.bean.LoginBean"/>
<jsp:setProperty name="loginBean" property="*"/>

<!-- Login Request -->
<%
    if (request.getParameter("login")!=null) {
        if (loginBean.getEmail() == null || loginBean.getPassword() == null) {
%>
            <div style="align-content: center; color: red; justify-content: center; align-items: center;">
                <p style="font-weight: bold; color: white; background: red; margin: 0 auto; padding: 1em; text-align: center">Please insert valid data</p>
            </div>
<%
        } else {
            LoginController loginController = new LoginController(loginBean);
            User user = loginController.login();
            session.setAttribute("user", user);
            response.sendRedirect("home.jsp");
        }
    }


    if (request.getParameter("register-solo") != null) {
        session.setAttribute("soloRegister", "solo");
    }
%>


<body>

    <div class="main">
        <header>
            <a href="#" class="logo-link"><img src="../../resources/soundmate/images/logo.svg" alt="Soundmate Logo" class="header-logo"></a>
            <ul class="links">
                <li><a href="#" class="nav-link">Home</a></li>
                <li><a href="#" class="nav-link" onclick="smoothScroll(document.getElementById('#feature-wrapper'))">Features</a></li>
                <li><a href="#" class="nav-link" onclick="smoothScroll(document.getElementById('#footer'))">Contacts</a></li>
                <li><button class="nav-link sign-up-btn" onclick="smoothScroll(document.getElementById('#footer'))">Sign Up</button></li>
            </ul>
        </header>

        <div class="main-wrapper">
            <img src="../../resources/soundmate/images/logo-v.svg" alt="Soundmate Logo" class="img-logo-header">
            <div class="text-main">
                <h3>Join our community</h3>
                <h3>Music begins with you</h3>
            </div>
            <button class="join-now-btn" onclick="smoothScroll(document.getElementById('#footer'))">Join Now</button>
        </div>
    </div>

    <div class="feature-wrapper" id="#feature-wrapper">

        <div class="feature">
            <img src="../../resources/soundmate/images/band.jpg" alt="Band" class="feature-img">
            <div class="text-wrapper-1">
                <h2>Solo musician</h2>
                <p>Start your own band project or join an existing band to start your music career. You can send requests to bands or just wait to be reached out by one of them.</p>
            </div>
        </div>

        <div class="feature">
            <div class="text-wrapper-2">
                <h2>Band manager</h2>
                <p>Manage your bands, promote them, and search for missing musicians. Maybe just for a recording session, or maybe to join permanently.</p>
            </div>
            <img src="../../resources/soundmate/images/recording.jpg" alt="Recording" class="feature-img">
        </div>

        <div class="feature feature-last">
            <img src="../../resources/soundmate/images/band-room-register.jpg" alt="Band" class="feature-img">
            <div class="text-wrapper-1">
                <h2>Band Room Manager</h2>
                <p>Register your band room to let musicians book your room online. You can manage and plan the daily schedule for every room you add to Soundmate.</p>
            </div>
        </div>

    </div>


    <div class="last-wrapper">

        <div class="last-content">
            <h2 class="last-h2">Join now and start playing</h2>

            <div class="form-and-register">

                <!--LOGIN FORM-->
                <form action="" method="post" name="loginForm">
                    <label for="email-field">Email</label> <br>
                    <input type="email" name="email" id="email-field"> <br>
                    <label for="password-field">Password</label> <br>
                    <input type="password" name="password" id="password-field"> <br>
                    <input type="submit" name="login" value="Login" class="sign-in-form-button">
                </form>

                    <div class="register-box">
                        <h3>Sign Up Now</h3>
                        <ul class="band-solo-boxes">
                            <li>
                                <form action="roomRenterSignUp.jsp">
                                    <div class="container-band">
                                        <img src="../../resources/soundmate/images/band-register.png" alt="Band Register" class="register-img-band">
                                        <div class="centered"><input type="submit" value="Band" name="register-band"></div>
                                    </div>
                                </form>
                            </li>

                            <li>
                                <form action="bandSignUp.jsp">
                                    <div class="container-band">
                                        <img src="../../resources/soundmate/images/band-room-register-2.png" alt="Band Room Register" class="register-img-band">
                                        <div class="centered"><input type="submit" value="Room Renter" name="register-renter"></div>
                                    </div>
                                </form>
                            </li>

                            <li>
                                <form action="soloSignUp.jsp">
                                    <div class="container-solo">
                                        <img src="../../resources/soundmate/images/solo-register.png" alt="Solo Register" class="register-img-solo">
                                        <div class="centered"><input type="submit" value="Solo" name="register-solo"></div>
                                    </div>
                                </form>
                            </li>
                        </ul>
                    </div>

            </div>

        </div>

    </div>

    <div class="footer" id="#footer">

        <ul class="contacts-list">
            <li class="contact-item"><a>Lorenzo Pantano</a></li>
            <li class="contact-item"><a>Matteo D'Alessandro</a></li>
            <li class="contact-item"><a>soundmate@email.com</a></li>
            <li class="contact-item"><a>ISPW 2020-2021</a></li>
            <li class="contact-item"><a>Soundmate© 2021</a></li>
        </ul>

    </div>

</body>
