document.addEventListener("DOMContentLoaded", () => {
    const filtroInput = document.getElementById("filtro-cuentas");
    const tablaCuentas = document.getElementById("tabla-cuentas");
    const filas = tablaCuentas.getElementsByTagName("tr");

    filtroInput.addEventListener("input", () => {
        const filtro = filtroInput.value.toLowerCase();

        // Recorre las filas de la tabla
        for (let i = 1; i < filas.length; i++) { // Empieza en 1 para saltar el encabezado
            const celdas = filas[i].getElementsByTagName("td");
            if (celdas.length > 0) {
                const nombreCuenta = celdas[1].textContent.toLowerCase();
                if (nombreCuenta.includes(filtro)) {
                    filas[i].style.display = ""; // Mostrar la fila
                } else {
                    filas[i].style.display = "none"; // Ocultar la fila
                }
            }
        }
    });
});
