<%@ page import="it.soundmate.controller.logic.RegisterController" %>
<%@ page import="it.soundmate.model.Band" %>

<%--
  Created by IntelliJ IDEA.
  User: Matteo D'Alessandro
  Date: 06/02/2021
  Time: 18:56
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/style.scss">
    <link rel="stylesheet" href="../css/register.scss">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&display=swap" rel="stylesheet">
    <script src="../js/smoothScrolling.js"></script>
    <title>Sign Up</title>
</head>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="bandRegBean" scope="request" class="it.soundmate.bean.registerbeans.RegisterBandBean"/>
<jsp:setProperty name="bandRegBean" property="*"/>

<!-- Registration Request -->
<%
    if (request.getParameter("register")!=null) {
        if (!bandRegBean.checkFields())
        {
%>
<div style="align-content: center; color: red; justify-content: center; align-items: center;">
    <p style="font-weight: bold; color: white; background: red; margin: 0 auto; padding: 1em; text-align: center">Some fields are empty</p>
</div>
<%
        } else {
            RegisterController registerController = new RegisterController();
            Band band = registerController.registerBand(bandRegBean);
            session.setAttribute("user", band);
            response.sendRedirect("home.jsp");
        }
    }
%>




<body>

<div class="main">
    <header>
        <a href="#" class="logo-link"><img src="../../resources/soundmate/images/logo.svg" alt="Soundmate Logo" class="header-logo"></a>
        <ul class="links">
            <li><a href="#" class="nav-link">Home</a></li>
            <li><a href="#" class="nav-link" onclick="smoothScroll(document.getElementById('#feature-wrapper'))">Sign In</a></li>
            <li><a href="#" class="nav-link" onclick="smoothScroll(document.getElementById('#footer'))">Contacts</a></li>

        </ul>
    </header>

    <div class="main-wrapper">

        <div class="text-main">
            <h3>Join now as a Band</h3>
            <h3>Manage, promote, search for new musicians.</h3>
        </div>

        <div class="specific-form">

            <form action="" method="post" name="signUpForm">

                <div class="form-group">
                    <label for="email-field">Email</label>  <br>
                    <input type="email" name="email" id="email-field" placeholder="Email">  <br>
                    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                </div>
                <div class="form-group">
                    <label for="password-field">Password</label> <br>
                    <input type="password" name="password" id="password-field" placeholder="Password"> <br>
                </div>
                <div class="form-group">
                    <label for="bName-field">Band Name</label> <br>
                    <input type="text" name="bandName" id="bName-field" placeholder="Band Name"> <br>
                </div>
                <div class="form-group">
                    <label for="city-field">City</label> <br>
                    <input type="text" name="city" id="city-field" placeholder="City"> <br>
                </div>

                <input type="submit" name="register" value="Sign Up" class="btn mb-6">

            </form>

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

</html>
