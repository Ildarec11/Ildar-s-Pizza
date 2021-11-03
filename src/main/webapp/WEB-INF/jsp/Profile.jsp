<%--
  Created by IntelliJ IDEA.
  User: ildar
  Date: 24.10.2021
  Time: 19:35
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Profile</title>
    <link rel="stylesheet" href="css/main-menu.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="blocks/cards.css">
</head>
<body>
<div id="wrapper">
    <div id="header">
        <nav class="top-menu">
            <a class="navbar-logo" href=""><img
                    src="images/kisspng-pizza-delivery-italian-cuisine-chef-clip-art-take-pizza-chef-5a8bf6088237a3.6662452515191219285334.png"
                    height="100" width="100"/></a>
            <ul class="menu-main">
                <li><a href="/menu">Menu</a></li>
                <li><a href="/discounts">Discounts</a></li>
                <li><a href="/aboutUs">About us</a></li>
                <li><a href="/profile">Profile</a></li>
            </ul>
        </nav>

        <c:set var="user" value='${requestScope["user"]}' />

        <h1>${user.fullName}</h1>
        <ul >
            <li><h4>email: ${user.email}</h4></li>
            <li><h4>balance: ${user.money}</h4></li>
        </ul>


    </div>
    <div id="content">
    </div>
    <div id="sidebar">
    </div>
    <div id="footer">
    </div>
</div>
</body>
</html>