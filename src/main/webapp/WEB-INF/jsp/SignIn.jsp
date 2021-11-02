<%--
  Created by IntelliJ IDEA.
  User: ildar
  Date: 12.10.2021
  Time: 08:46
  To change this template use File | Settings | File Templates.
--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Sign In</title>
    <link rel="stylesheet" href="../../css/form-validation.css">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">

</head>

<div class="main-content">
    <form class="form-validation" method="post" action="/signIn">

        <div class="form-title-row">
            <h1>Sign In</h1>
        </div>

        <div class="form-row form-input-email-row">

            <label>
                <span>Email</span>
                <input type="email" name="email">
            </label>

        </div>


        <div class="form-row form-input-password-row">

            <label>
                <span>Password</span>
                <input type="password" name="password">
            </label>
        </div>

        <div class="form-row">
            <button type="submit">Enter</button>
        </div>


        <div>
            <h5>Don't have account yet?</h5>
            <button type="button" onclick="location.href='signUp'">Register</button>
        </div>
    </form>

</div>


<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>

<script>

    $(document).ready(function() {

        // Here is how to show an error message next to a form field

        var errorField = $('.form-input-name-row');

        // Adding the form-invalid-data class will show
        // the error message and the red x for that field

        errorField.addClass('form-invalid-data');
        errorField.find('.form-invalid-data-info').text('Please enter your name');


        // Here is how to mark a field with a green check mark

        var successField = $('.form-input-email-row');

        successField.addClass('form-valid-data');
    });

</script>

</body>

</html>

