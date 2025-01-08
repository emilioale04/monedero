<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ingresar</title>
    <link rel="stylesheet" href="styles/styles.css">
    <link rel="stylesheet" href="styles/movimiento.css">
    <script src="js/movimiento.js"></script>
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
    <div class="container">
        <div class="content-wrapper">
            <div class="ds-flex-column">
                <!-- Botones de las pestañas -->
                <div class="tabs ds-flex">
                    <button class="tab-button active" data-target="tab1">Ingreso</button>
                    <button class="tab-button" data-target="tab2">Egreso</button>
                    <button class="tab-button" data-target="tab3">Transferencia</button>
                </div>

                <!-- Contenido de las pestañas -->
                <div class="pd-24">
                    <!-- Formulario de Ingreso -->
                    <div id="tab1" class="tab-pane bg-white">
                        <form id="ingreso-form" class="font-primary" action="movimientoServlet" method="post">
                            <input type="hidden" name="route" value="ingresar">
                            <input type="hidden" name="cuentaId" value="${cuenta.id}">
                            <div class="form-group pd-y-16">
                                <label for="valor-ingreso">Valor:</label>
                                <input type="number" id="valor-ingreso" name="valor" class="input text-base"
                                       placeholder="Ingrese el valor" required>
                            </div>
                            <div class="form-group pd-y-16">
                                <label for="concepto-ingreso">Concepto:</label>
                                <input id="concepto-ingreso" name="concepto" class="input text-base"
                                       placeholder="Concepto del ingreso">
                            </div>
                            <div class="pd-y-16">
                                <button type="submit" class="button bg-primary text-white">Registrar Ingreso</button>
                            </div>
                        </form>
                    </div>

                    <!-- Formulario de Egreso -->
                    <div id="tab2" class="tab-pane hidden bg-white">
                        <form id="egreso-form" class="font-primary" action="movimientoServlet" method="post">
                            <input type="hidden" name="route" value="egresar">
                            <input type="hidden" name="cuentaId" value="${cuenta.id}">
                            <div class="form-group pd-y-16">
                                <label for="valor-egreso">Valor:</label>
                                <input type="number" id="valor-egreso" name="valor" class="input text-base"
                                       placeholder="Ingrese el valor" required>
                            </div>
                            <div class="form-group pd-y-16">
                                <label for="concepto-egreso">Concepto:</label>
                                <input id="concepto-egreso" name="concepto" class="input text-base"
                                       placeholder="Concepto del egreso">
                            </div>
                            <div class="pd-y-16">
                                <button type="submit" class="button bg-primary text-white">Registrar Egreso</button>
                            </div>
                        </form>
                    </div>

                    <!-- Formulario de Transferencia -->
                    <div id="tab3" class="tab-pane hidden bg-white">
                        <form id="transferencia-form" class="font-primary" action="movimientoServlet" method="post">
                            <input type="hidden" name="route" value="transferir">
                            <input type="hidden" name="cuentaOrigenId" value="${cuenta.id}">
                            <div class="form-group pd-y-16">
                                <label for="valor-transferencia">Valor:</label>
                                <input type="number" id="valor-transferencia" name="valor" class="input text-base"
                                       placeholder="Ingrese el valor" required>
                            </div>
                            <div class="form-group pd-y-16">
                                <label for="concepto-transferencia">Concepto:</label>
                                <input id="concepto-transferencia" name="concepto" class="input text-base"
                                       placeholder="Concepto de la transferencia">
                            </div>
                            <div class="form-group pd-y-16">
                                <label for="cuenta-destino">Cuenta Destino:</label>
                                <select id="cuenta-destino" name="cuentaDestinoId" class="input text-base" required>
                                    <option value="">Seleccione una cuenta</option>
                                    <c:forEach var="cuenta" items="${cuentas}">
                                        <option value="${cuenta.id}">${cuenta.nombre}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="pd-y-16">
                                <button type="submit" class="button bg-primary text-white">Realizar Transferencia
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="account-info">
                <h2 class="text-dark font-primary">Cuenta: ${cuenta.nombre}</h2>
                <p class="text-dark font-primary">Balance: ${cuenta.balance}</p>
            </div>
        </div>
    </div>
</main>
</body>
</html>
