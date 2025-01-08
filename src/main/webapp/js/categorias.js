document.addEventListener('DOMContentLoaded', function() {
    cargarCategorias();
});

function cargarCategorias() {
    var categorias = JSON.parse(localStorage.getItem('categorias')) || [];
    var lista = document.getElementById('lista-categorias');
    lista.innerHTML = '';

    categorias.forEach(function(categoria, index) {
        var row = document.createElement('tr');

        var nombreTd = document.createElement('td');
        nombreTd.classList.add('pd-8');
        nombreTd.textContent = categoria.nombre;

        var accionesTd = document.createElement('td');
        accionesTd.classList.add('pd-8');

        var editarBtn = document.createElement('button');
        editarBtn.textContent = 'Editar';
        editarBtn.classList.add('button', 'bg-secondary', 'text-white', 'mg-r-8');
        editarBtn.onclick = function() {
            editarCategoria(index);
        };

        var eliminarBtn = document.createElement('button');
        eliminarBtn.textContent = 'Eliminar';
        eliminarBtn.classList.add('button', 'bg-error', 'text-white');
        eliminarBtn.onclick = function() {
            eliminarCategoria(index);
        };

        accionesTd.appendChild(editarBtn);
        accionesTd.appendChild(eliminarBtn);

        row.appendChild(nombreTd);
        row.appendChild(accionesTd);

        lista.appendChild(row);
    });
}

function agregarCategoria() {
    var nombre = prompt('Ingrese el nombre de la nueva categoría:');
    if (nombre) {
        var categorias = JSON.parse(localStorage.getItem('categorias')) || [];
        categorias.push({ nombre: nombre });
        localStorage.setItem('categorias', JSON.stringify(categorias));
        cargarCategorias();
    }
}

function editarCategoria(index) {
    var categorias = JSON.parse(localStorage.getItem('categorias')) || [];
    var nombre = prompt('Ingrese el nuevo nombre de la categoría:', categorias[index].nombre);
    if (nombre) {
        categorias[index].nombre = nombre;
        localStorage.setItem('categorias', JSON.stringify(categorias));
        cargarCategorias();
    }
}

function eliminarCategoria(index) {
    var categorias = JSON.parse(localStorage.getItem('categorias')) || [];
    if (confirm('¿Está seguro de eliminar esta categoría?')) {
        categorias.splice(index, 1);
        localStorage.setItem('categorias', JSON.stringify(categorias));
        cargarCategorias();
    }
}