<%--
  Created by IntelliJ IDEA.
  User: ildar
  Date: 24.10.2021
  Time: 19:35
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Profile</title>
    <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
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


        <div id="form">
            <input id="name" type="text" name="name" placeholder="Dish name"/>
            <input id="cost" type="number" name="cost" placeholder="$"/>
            <input id="description" type="text" name="description" placeholder="Description"/>
            <button id="sendProduct" onclick="sendDish()">Send!</button>
        </div>

    </div>
</div>
</div>



<script>
    function sendDish(){
        let name = document.getElementById('name').value
        let cost = document.getElementById('cost').value
        let description = document.getElementById('description').value

        var dish = {
            name: name,
            cost: cost,
            description: description
        }
        $.ajax({
            url: '/add-dish',           /* Куда пойдет запрос */
            method: 'post',             /* Метод передачи (post или get) */
            dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
            data: {
                dish: JSON.stringify(dish) /* Параметры передаваемые в запросе. */
            },
            success: function(data){   /* функция которая будет выполнена после успешного запроса.  */
                alert(data);            /* В переменной data содержится ответ от /products. */
            }
        })
    }
</script>

</body>
</html>