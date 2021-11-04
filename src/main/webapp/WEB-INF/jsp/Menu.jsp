<%--
  Created by IntelliJ IDEA.
  User: ildar
  Date: 24.10.2021
  Time: 15:38
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Menu</title>
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

        <div class="cards">
        <c:forEach items="${menu}" var="dish">
            <div class="card">
                <img class="card__image" src="https://fakeimg.pl/400x300/252c6a/fff/" alt="">
                <div class="card__content">
                    <p>
                        ${dish.description}
                    </p>
                    <h2>
                        ${dish.name}
                    </h2>
                </div>
                <div class="card__info">
                    <div>
                        <i class="material-icons">attach_money</i>
                        ${dish.cost}
                    </div>
                    <button id="buy_${dish.id}" onclick="buyDish(${dish.id}, '${dish.name}', '${dish.description}', ${dish.cost})">Buy</button>
                </div>
            </div>
        </c:forEach>
        </div>
    </div>
    <div id="content">
    </div>
    <div id="sidebar">
    </div>
    <div id="footer">
    </div>
</div>

<script>

    function buyDish(id, name, description, cost){
        var dish = {
          id: id,
          name: name,
          description: description,
          cost: cost
        }
        $.ajax({
            url: '/buy-pizza',           /* Куда пойдет запрос */
            method: 'post',             /* Метод передачи (post или get) */
            dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
            data: {
               productCost: JSON.stringify(dish.cost),
                boughtDish: JSON.stringify(dish) /* Параметры передаваемые в запросе. */
            },
            success: function(data){   /* функция которая будет выполнена после успешного запроса.  */
                alert(data);            /* В переменной data содержится ответ от /products. */
            }
        })
    }
</script>

</body>
</html>