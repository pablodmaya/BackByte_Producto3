package com.backbyte.controllers;

import com.backbyte.models.Ciudad;
import com.backbyte.models.TipoVehiculo;
import com.backbyte.models.Vehiculo;
import com.backbyte.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/buscar-vehiculos")
public class VehiculosController {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @GetMapping
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



        // En el futuro, puedes usar estos parámetros para filtrar datos
        return "BuscarVehiculos";
    }
}
