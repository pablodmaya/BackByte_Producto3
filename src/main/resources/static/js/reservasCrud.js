document.addEventListener('DOMContentLoaded', () => {
    const editReservationModal = document.getElementById('editReservationModal');

    if (editReservationModal) {
        editReservationModal.addEventListener('show.bs.modal', (event) => {
            const button = event.relatedTarget; // Botón que activó el modal

            // Verifica si el botón tiene los datos correctos
            if (button) {
                const id = button.getAttribute('data-id');
                const clienteId = button.getAttribute('data-cliente');
                const vehiculoId = button.getAttribute('data-vehiculo');
                const fechaInicio = button.getAttribute('data-fechainicio');
                const fechaFin = button.getAttribute('data-fechafin');

                // Precarga los valores en el formulario
                document.getElementById('editReservaId').value = id;
                document.getElementById('editCliente').value = clienteId;
                document.getElementById('editVehiculo').value = vehiculoId;
                document.getElementById('editFechaInicio').value = fechaInicio;
                document.getElementById('editFechaFin').value = fechaFin;
            }
        });
    }
});
