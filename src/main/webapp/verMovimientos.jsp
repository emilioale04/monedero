<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ingresar</title>
    <link rel="stylesheet" href="styles/styles.css">
    <link rel="stylesheet" href="styles/verMovimiento.css">
    <script src="js/verMovimiento.js"></script>
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
<main class="ds-flex">
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
    <div class="pd-x-40 pd-t-40 flex-2">
        <h2 class="font-primary text-dark">Mis Movimientos</h2>
        <div>
<%--            aqui va el filtro --%>
        </div>
        <div class="font-primary">
            <table class="table border-light mg-y-16">
                <tr>
                    <th class="pd-8">Cuenta</th>
                    <th class="pd-8">Tipo</th>
                    <th class="pd-8">Fecha</th>
                    <th class="pd-8">Concepto</th>
                    <th class="pd-8">Valor</th>
                </tr>
                <c:forEach var="movimiento" items="${movimientos}">
                    <c:choose>
                        <c:when test="${movimiento.tipo == 'Ingreso'}">
                            <tr>
                                <td>${movimiento.cuentaDestino.nombre}</td>
                                <td>Ingreso</td>
                                <td>${movimiento.fechaFormateada}</td>
                                <td>${movimiento.concepto}</td>
                                <td>${movimiento.valor}</td>
                            </tr>
                        </c:when>
                        <c:when test="${movimiento.tipo == 'Egreso'}">
                            <tr>
                                <td>${movimiento.cuentaOrigen.nombre}</td>
                                <td>Egreso</td>
                                <td>${movimiento.fechaFormateada}</td>
                                <td>${movimiento.concepto}</td>
                                <td>${movimiento.valor}</td>
                            </tr>
                        </c:when>
                        <c:when test="${movimiento.tipo == 'Transferencia'}">
                            <tr>
                                <td>${movimiento.cuentaOrigen.nombre}</td>
                                <td>Transferencia de Salida</td>
                                <td>${movimiento.fechaFormateada}</td>
                                <td>${movimiento.concepto}</td>
                                <td>- ${movimiento.valor}</td>
                            </tr>
                            <tr>
                                <td>${movimiento.cuentaDestino.nombre}</td>
                                <td>Transferencia de Entrada</td>
                                <td>${movimiento.fechaFormateada}</td>
                                <td>${movimiento.concepto}</td>
                                <td>${movimiento.valor}</td>
                            </tr>
                        </c:when>
                    </c:choose>
                </c:forEach>
            </table>

        </div>
    </div>

</main>
</body>
</html>
