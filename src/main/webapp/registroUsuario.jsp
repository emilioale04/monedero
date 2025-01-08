<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro</title>
    <link rel="stylesheet" href="styles/registro_usuario.css">
    <link rel="stylesheet" href="styles/styles.css">
    <link rel="icon" type="image/png" href="images/dollar.png">
</head>
<body class="bg-primary">
<div class="register-form">
    <h1 class="font-primary text-primary pd-b-24">Registro</h1>
    <form action="usuarioServlet" method="post">
        <input type="hidden" name="route" value="registrar">
        <div class="form-row">
            <label for="nombre" class="text-dark text-base pd-r-16 font-primary">Nombre:</label>
            <input type="text" id="nombre" name="nombre" class="input" required>
        </div>
        <small class="text-light font-primary">Ejemplo: Juan Pérez</small>
        <div class="form-row">
            <label for="usuario" class="text-dark text-base pd-r-16 font-primary">Usuario:</label>
            <input type="text" id="usuario" name="usuario" class="input" required>
        </div>
        <small class="text-light font-primary">Ejemplo: juan123</small>
        <div class="form-row">
            <label for="clave" class="text-dark text-base pd-r-16 font-primary">Contraseña:</label>
            <input type="password" id="clave" name="clave" class="input" required>
        </div>
        <small class="text-light font-primary">Ejemplo: ContraseñaSegura123!</small>
        <div class="ds-flex jc-center pd-y-16 mg-t-16">
            <button type="submit" class="button">Registrarse</button>
        </div>
    </form>
    <div class="login-link pd-y-16 mg-t-24 font-primary font-base font-bold">
        <span>¿Ya tienes una cuenta?</span>
        <a class="text-primary" href="index.jsp">Inicia Sesión</a>
    </div>
</div>
</body>
</html>
