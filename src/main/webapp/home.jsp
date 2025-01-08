<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <link rel="stylesheet" href="styles/styles.css">
    <link rel="stylesheet" href="styles/home.css">
    <script src="js/home.js"></script>
    <link rel="icon" type="image/png" href="images/dollar.png">
    <script src="https://kit.fontawesome.com/d2aae01839.js" crossorigin="anonymous"></script>
</head>
<body>
<header class="header bg-primary ds-flex jc-sb pd-y-16 pd-x-24 font-secondary text-white align-center">
    <div></div>
    <div>
        <form action="loginServlet" method="post">
            <input type="hidden" name="route" value="logout">
            <button type="submit" class="logout-button">
                <span class="pd-r-8 font-bold font-primary text-base">Cerrar Sesión</span>
                <i class="fa-solid fa-right-from-bracket text-white text-xl"></i>
            </button>
        </form>
    </div>
</header>
<main class="ds-flex jc-sb">
    <nav class="sidemenu bg-light">
        <img src="images/wallet-512px.png" alt="wallet">
        <span class="text-dark font-primary text-center pd-b-16">Chaucherita<br>Web</span>
        <ul class="menu ls-none">
            <li>
                <form action="homeServlet" method="get">
                    <input type="hidden" name="route" value="listar">
                    <button class="menu-button pd-8" type="submit">
                        <i class="fa-solid fa-house text-xl"></i> Inicio
                    </button>
                </form>
            </li>
            <li>
                <form action="homeServlet" method="get">
                    <input type="hidden" name="route" value="crearCuenta">
                    <button class="menu-button pd-8" type="submit">
                        <i class="fa-solid fa-plus text-xl"></i> Crear Cuenta
                    </button>
                </form>
            </li>
            <li>
                <form action="homeServlet" method="get">
                    <input type="hidden" name="route" value="verMovimientos">
                    <button class="menu-button pd-8" type="submit">
                        <i class="fa-regular fa-eye text-xl"></i> Movimientos
                    </button>
                </form>
            </li>
        </ul>
    </nav>
    <!-- Sección principal -->
    <div class="main-content">
        <section class="font-primary pd-t-40 pd-x-40">
            <h2 class="font-primary text-dark">Mis Cuentas</h2>
            <!-- Campo de entrada para el filtro -->
            <div class="pd-y-16">
                <label for="filtro-cuentas" class="text-dark">Filtrar por nombre:</label>
                <input type="text" id="filtro-cuentas" class="input text-base" placeholder="Buscar cuenta...">
            </div>
            <div>
                <table id="tabla-cuentas" class="table border-light mg-y-16">
                    <thead>
                    <tr class="bg-light text-dark">
                        <th class="pd-8">ID</th>
                        <th class="pd-8">Nombre</th>
                        <th class="pd-8">Balance Actual</th>
                        <th class="pd-8">Acciones</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="cuenta" items="${cuentas}">
                        <tr>
                            <td class="pd-8">${cuenta.id}</td>
                            <td class="pd-8">${cuenta.nombre}</td>
                            <td class="pd-8">${cuenta.balance}</td>
                            <td class="pd-8">
                                <form action="homeServlet" method="get">
                                    <input type="hidden" name="route" value="realizarMovimiento">
                                    <input type="hidden" name="cuentaId" value="${cuenta.id}">
                                    <button type="submit" class="button bg-primary text-white">Realizar Movimiento</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>

                </table>
            </div>
            <h2 class="font-primary text-dark">Movimientos Recientes</h2>

            <div class="font-primary">
                <table class="table border-light mg-y-16">
                    <tr>
                        <th class="pd-8">Tipo</th>
                        <th class="pd-8">Fecha</th>
                        <th class="pd-8">Concepto</th>
                        <th class="pd-8">Valor</th>
                    </tr>
                    <c:forEach var="movimiento" items="${movimientos}">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${movimiento.tipo == 'Transferencia' and movimiento.cuentaOrigen.id == cuenta.id}">
                                        Transferencia de Salida
                                    </c:when>
                                    <c:when test="${movimiento.tipo == 'Transferencia' and movimiento.cuentaDestino.id == cuenta.id}">
                                        Transferencia de Entrada
                                    </c:when>
                                    <c:otherwise>
                                        ${movimiento.tipo}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${movimiento.fechaFormateada}</td>
                            <td>${movimiento.concepto}</td>
                            <td>${movimiento.valor}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </section>
    </div>
</main>
</body>
</html>