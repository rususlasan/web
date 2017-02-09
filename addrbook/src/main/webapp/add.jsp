<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Вводим данные, не спим!</h1>
        <form action="/addrbook-1/doadd" type="post">
            <table>
		<tr>
                    <td><label for="name_field"> Имя </label></td>
                    <td><input type="text" id="name_field"></td>
                </tr>
                <tr>
                    <td><label for="phone_field"> Номер телефона </label></td>
                    <td><input type="text" id="phone_field"></td>
		</tr>
		<tr>
                    <td><label for="description_field"> Описание </label></td>
                    <td><input type="text" id="description_field" value="none"></td>
		<tr>
            </table>
            <input type="submit">
	</form>
    </body>
</html>
