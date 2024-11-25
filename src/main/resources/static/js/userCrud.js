document.addEventListener('DOMContentLoaded', function () {
    // Modal de Edición
    var editClientModal = document.getElementById('editClientModal');

    if (editClientModal) {
        // Evento para abrir el modal y cargar los datos
        editClientModal.addEventListener('show.bs.modal', function (event) {
            var button = event.relatedTarget; // El botón que activó el modal

            if (!button) {
                console.error("Error: botón que disparó el evento no encontrado.");
                return;
            }

            // Obtener los elementos del formulario en el modal
            var editClientId = document.getElementById('editClientId');
            var editNombre = document.getElementById('editNombre');
            var editApellido = document.getElementById('editApellido');
            var editDni = document.getElementById('editDni');
            var editDireccion = document.getElementById('editDireccion');
            var editTelefono = document.getElementById('editTelefono');

            // Obtener valores desde los atributos `data-*` del botón y asignarlos a los campos del formulario
            editClientId.value = button.getAttribute('data-id');
            editNombre.value = button.getAttribute('data-nombre');
            editApellido.value = button.getAttribute('data-apellido');
            editDni.value = button.getAttribute('data-dni');
            editDireccion.value = button.getAttribute('data-direccion');
            editTelefono.value = button.getAttribute('data-telefono');

            // Establecer la acción del formulario para enviar la solicitud al endpoint correcto
            var editClientForm = document.getElementById('editClientForm');
            editClientForm.action = '/clientes/editar/' + editClientId.value;
        });
    }

    // Confirmación para la eliminación de un cliente
    var deleteClientButtons = document.querySelectorAll('.deleteClientBtn');

    deleteClientButtons.forEach(function (button) {
        button.addEventListener('click', function (event) {
            var clientId = button.getAttribute('data-id');
            var confirmDelete = confirm('¿Estás seguro de que deseas eliminar este cliente?');
            if (!confirmDelete) {
                event.preventDefault(); // Si el usuario no confirma, cancelar la eliminación
            }
        });
    });
});
