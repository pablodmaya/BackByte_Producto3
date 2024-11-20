package com.backbyte.controllers;

import com.backbyte.models.*;
import com.backbyte.repository.AlquilerRepository;
import com.backbyte.repository.ClienteRepository;
import com.backbyte.repository.UsuarioRepository;
import com.backbyte.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

@Controller

public class ReservasVehiculosController {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AlquilerRepository alquilerRepository;

    @Autowired
    private UsuarioRepository UsuarioRepository;

    @GetMapping("/buscar-vehiculos")
    public String buscarVehiculos(@RequestParam("vehicleType") String vehicleType,
                                  @RequestParam("pickupLocation") String pickupLocation,
                                  @RequestParam("startDate") String startDate,
                                  @RequestParam("endDate") String endDate,
                                  Model model) {

        // Mapea el tipo de vehículo a un valor del enum TipoVehiculo
        TipoVehiculo tipoVehiculo = TipoVehiculo.valueOf(vehicleType);

        // Mapea las ciudades a valores del enum Ciudad
        Ciudad ciudadRecogida = Ciudad.valueOf(pickupLocation);

        // Convierte las fechas recibidas en LocalDate
        LocalDate fechaInicio = LocalDate.parse(startDate);
        LocalDate fechaFin = LocalDate.parse(endDate);

        // Convertir las fechas LocalDate a java.sql.Date
        java.sql.Date sqlFechaInicio = java.sql.Date.valueOf(fechaInicio);
        java.sql.Date sqlFechaFin = java.sql.Date.valueOf(fechaFin);

        // Llama al repositorio para obtener los vehículos disponibles
        List<Vehiculo> vehiculosDisponibles = vehiculoRepository.findAvailableVehicles(tipoVehiculo, ciudadRecogida, sqlFechaInicio, sqlFechaFin);

        // Agrega los resultados al modelo
        model.addAttribute("vehiculos", vehiculosDisponibles);
        model.addAttribute("startDate", startDate); // Añadir fecha inicio al modelo
        model.addAttribute("endDate", endDate);

        // En el futuro, puedes usar estos parámetros para filtrar datos
        return "BuscarVehiculos";
    }

    @GetMapping("/reservar/{idVehiculo}")
    public String mostrarFormularioReserva(@PathVariable("idVehiculo") Integer idVehiculo,
                                           @RequestParam("startDate") String startDate,
                                           @RequestParam("endDate") String endDate,
                                           Model model) {



        Long idUsuario = 1L; // Suponemos que el id_Usuario es 1L por ahora

        // Verificar si el usuario ya es cliente
        Usuario usuario = UsuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Comprobar si el usuario es cliente (es_cliente = 1)
        if (usuario.getEs_Cliente() == false) {

            model.addAttribute("idVehiculo", idVehiculo);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
            // Si el usuario no es cliente, redirigimos al formulario de cliente
            return "formularioReserva"; // Ruta del formulario para registrar al cliente
        }

        // Si el usuario es cliente, buscar el cliente asociado a ese usuario
        Cliente clienteExistente = clienteRepository.findByUsuarioId(idUsuario);
        if (clienteExistente == null) {
            throw new RuntimeException("Cliente no encontrado para el usuario");
        }

        // Si el usuario es cliente, agregar los datos de la reserva al modelo
        model.addAttribute("idVehiculo", idVehiculo);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("cliente", clienteExistente);
        Vehiculo vehiculo = vehiculoRepository.findById(Long.valueOf(idVehiculo)).orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        LocalDate fechaInicio = LocalDate.parse(startDate);
        LocalDate fechaFin = LocalDate.parse(endDate);
        java.sql.Date sqlFechaInicio = java.sql.Date.valueOf(fechaInicio);
        java.sql.Date sqlFechaFin = java.sql.Date.valueOf(fechaFin);

        Alquiler alquiler = new Alquiler();
        alquiler.setCliente(clienteExistente);
        alquiler.setVehiculo(vehiculo);
        alquiler.setFecha_Inicio(sqlFechaInicio);
        alquiler.setFecha_Fin(sqlFechaFin);

        // Guardar la reserva (alquiler)
        alquilerRepository.save(alquiler);


        return "redirect:/reservaConfirmada?idCliente=" + clienteExistente.getId_Cliente();
    }

    @PostMapping("/registrarClienteReserva")
    public String registrarClienteYAlquiler(@ModelAttribute Cliente cliente, @RequestParam Long idVehiculo, @RequestParam String startDate,
                                            @RequestParam String endDate) {


        // Verificar si el cliente ya existe
        Cliente clienteExistente = clienteRepository.findBydni(cliente.getDni());


        if (clienteExistente == null) {

                                         // esto habra que cambiarlo!! //
            Usuario usuario = UsuarioRepository.findById(1L).orElseThrow(() -> new RuntimeException("Usuario no encontrado")); // en lugar de 1L habra que meter el id del usuario logeado //
                                         // esto habra que cambiarlo!! //

            usuario.setEs_Cliente(true);
            UsuarioRepository.save(usuario);

            cliente.setUsuario(usuario);
            // Si el cliente no existe, guardarlo en la base de datos
            clienteExistente = clienteRepository.save(cliente);
        }

        Vehiculo vehiculo = vehiculoRepository.findById(idVehiculo).orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        LocalDate fechaInicio = LocalDate.parse(startDate);
        LocalDate fechaFin = LocalDate.parse(endDate);
        java.sql.Date sqlFechaInicio = java.sql.Date.valueOf(fechaInicio);
        java.sql.Date sqlFechaFin = java.sql.Date.valueOf(fechaFin);

        Alquiler alquiler = new Alquiler();
        alquiler.setCliente(clienteExistente);
        alquiler.setVehiculo(vehiculo);
        alquiler.setFecha_Inicio(sqlFechaInicio);
        alquiler.setFecha_Fin(sqlFechaFin);

        // Guardar la reserva (alquiler)
        alquilerRepository.save(alquiler);

        // Redirigir a la página de confirmación
        return "redirect:/reservaConfirmada?idCliente=" + clienteExistente.getId_Cliente();
    }

    @GetMapping("/reservaConfirmada")
    public String mostrarReservaConfirmada(@RequestParam Long idCliente, Model model) {
        // Puedes agregar datos al modelo si es necesario, por ejemplo:
        model.addAttribute("mensaje", "Tu reserva ha sido confirmada exitosamente.");
        model.addAttribute("idCliente", idCliente);

        // Retorna la vista reservaConfirmada.html
        return "reservaConfirmada";
    }

    @GetMapping("/mis-reservas/{idCliente}")
    public String mostrarReservas(@PathVariable("idCliente") Long idCliente, @RequestParam(value = "success", required = false) String success,
                                  Model model) {

                                  // ESTO HABRA QUE CAMBIARLO !! //
        Long idUsuario = 1L; // Supongamos que el usuario logueado tiene el ID 1.
                                  // ESTO HABRA QUE CAMBIARLO !! //

        List<Double> preciosTotales = alquilerRepository.calcularPrecioTotalPorReserva(idCliente);

        // Agregar los precios al modelo para pasarlos a la vista
        model.addAttribute("preciosTotales", preciosTotales);

        // Buscar cliente asociado al usuario
        Cliente cliente = clienteRepository.findByUsuarioId(idUsuario);
        if (cliente == null) {
            throw new RuntimeException("No se encontró un cliente asociado al usuario.");
        }

        // Buscar alquileres asociados al cliente
        List<Alquiler> alquileres = alquilerRepository.findByClienteId(cliente.getId_Cliente().longValue());

        model.addAttribute("cliente", cliente);
        // Pasar los datos al modelo
        model.addAttribute("alquileres", alquileres);

        // Agregar mensaje de éxito si existe
        if ("true".equals(success)) {
            model.addAttribute("mensajeExito", "Reserva eliminada exitosamente.");
        }

        return "misReservas";
    }

    @GetMapping("/eliminarReserva/{id}")
    public String eliminarReserva(@PathVariable Long id, @RequestParam Long idCliente) {
        // Buscar el alquiler por su ID
        Alquiler alquiler = alquilerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        // Eliminar el alquiler
        alquilerRepository.delete(alquiler);


        return "redirect:/mis-reservas/" + idCliente + "?success=true";
    }


}

