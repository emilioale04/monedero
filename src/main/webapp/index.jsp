<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="styles/login.css">
    <link rel="stylesheet" href="styles/styles.css">
    <link rel="icon" type="image/png" href="images/dollar.png">
    <script src="https://kit.fontawesome.com/d2aae01839.js" crossorigin="anonymous"></script>
</head>
<body class="bg-primary">
<div class="login-form">
    <h1 class="font-primary text-primary pd-y-16">Iniciar Sesión</h1>
    <div>
        <form action="loginServlet" method="post">
            <input type="hidden" name="route" value="login">
            <div class="ds-flex align-center pd-y-16">
                <i class="fa-solid fa-user text-primary text-xl pd-r-16"></i>
                <input type="text" class="input text-base" id="usuario" name="usuario" placeholder="Usuario" required><br>
            </div>
            <div class="ds-flex align-center pd-y-16">
                <i class="fa-solid fa-lock text-primary text-xl pd-r-16"></i>
                <input type="password" class="input text-base" id="clave" name="clave" placeholder="Contraseña" required><br>
            </div>
            <button class="button" type="submit">Ingresar</button>
        </form>
    </div>
    <div class="register-link pd-y-16 mg-t-16 font-primary text-base font-bold">
        <span>¿No tienes una cuenta?</span>
        <a class="text-primary" href="registroUsuario.jsp">Regístrate</a>
    </div>
</div>
</body>
</html>
