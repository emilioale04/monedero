document.addEventListener("DOMContentLoaded", () => {
    const buttons = document.querySelectorAll(".tab-button");
    const panes = document.querySelectorAll(".tab-pane");

    buttons.forEach((button) => {
        button.addEventListener("click", () => {
            // Quitar clases activas
            buttons.forEach((btn) => btn.classList.remove("active"));
            panes.forEach((pane) => pane.classList.add("hidden"));

            // Activar pestaña seleccionada
            button.classList.add("active");
            const targetPane = document.getElementById(button.dataset.target);
            targetPane.classList.remove("hidden");
        });
    });
});