document.addEventListener("DOMContentLoaded", () => {
    const cuentaSelect = document.getElementById("cuenta");
    const balanceDisplay = document.getElementById("balance");

    cuentaSelect.addEventListener("change", () => {
        const selectedOption = cuentaSelect.options[cuentaSelect.selectedIndex];
        const balance = selectedOption.getAttribute("data-balance");
        if (balance) {
            balanceDisplay.textContent = `$${parseFloat(balance).toFixed(2)}`;
        } else {
            balanceDisplay.textContent = "Seleccione una cuenta";
        }
    });
});