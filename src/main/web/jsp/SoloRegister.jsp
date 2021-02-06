<%@ page import="it.soundmate.controller.logic.RegisterController" %>
<%@ page import="it.soundmate.bean.registerbeans.RegisterSoloBean" %>
<%@ page import="it.soundmate.bean.registerbeans.RegisterBean" %>
<%@ page import="it.soundmate.model.User" %>
<%@ page import="it.soundmate.bean.LoginBean" %>
<%@ page import="it.soundmate.controller.logic.LoginController" %>
<%@ page import="it.soundmate.model.Solo" %>

<%--
  Created by IntelliJ IDEA.
  User: Matteo D'Alessandro
  Date: 06/02/2021
  Time: 14:45
  To change this template use File | Settings | File Templates.
--%>


<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&display=swap" rel="stylesheet">
    <script src="../js/smoothScrolling.js"></script>
    <title>Sign Up</title>
</head>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="soloRegBean" scope="request" class="it.soundmate.bean.registerbeans.RegisterSoloBean"/>
<jsp:setProperty name="soloRegBean" property="*"/>

<!-- Registration Request -->
<%
    if (request.getParameter("register")!=null) {
        if (!soloRegBean.checkFields())
                {
%>
<div style="align-content: center; color: red; justify-content: center; align-items: center;">
    <p style="font-weight: bold; color: white; background: red; margin: 0 auto; padding: 1em; text-align: center">Some fields are empty</p>
</div>
<%
    } else {
        RegisterController registerController = new RegisterController();
        Solo solo = registerController.registerSolo(soloRegBean);
        session.setAttribute("soloUser", solo);
        response.sendRedirect("home.jsp");
    }
    }
%>




<body>

        <div class="last-wrapper">

            <div class="last-content">
                <h2 class="last-h2">Join now and start playing</h2>

                <div class="form-and-register">


                    <div>
                        <form action="" method="post" name="signUpForm">
                            <label for="email-field">Email</label> <br>
                            <input type="email" name="email" id="email-field"> <br>
                            <label for="password">Password</label> <br>
                            <input type="password" name="password" id="password"> <br>
                            <label for="fName">First Name</label> <br>
                            <input type="text" name="firstName" id="fName"> <br>
                            <label for="lName">Last Name</label> <br>
                            <input type="text" name="lastName" id="lName"> <br>
                            <label for="city">City</label> <br>
                            <input type="text" name="city" id="city"> <br>
                            <input type="submit" name="register" value="Sign Up" class="sign-up-form-button">

                        </form>

                    </div>

                </div>
            </div>
        </div>

</body>

