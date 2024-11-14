package com.backbyte.repository;

import com.backbyte.models.Vehiculo;
import com.backbyte.models.Ciudad;
import com.backbyte.models.TipoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    // Consulta para filtrar vehículos por ciudad, tipo y precio entre un rango
    List<Vehiculo> findByLocalizacionAndTipoVehiculoAndPrecioDiaBetween(Ciudad localizacion, TipoVehiculo tipoVehiculo, Double minPrecio, Double maxPrecio);

    // Consulta para filtrar vehículos por ciudad y tipo
    List<Vehiculo> findByLocalizacionAndTipoVehiculo(Ciudad localizacion, TipoVehiculo tipoVehiculo);

    // Consulta para filtrar vehículos por solo precio entre un rango
    List<Vehiculo> findByPrecioDiaBetween(Double minPrecio, Double maxPrecio);

    // Consulta para filtrar vehículos solo por ciudad
    List<Vehiculo> findByLocalizacion(Ciudad localizacion);

    // Consulta para filtrar vehículos solo por tipo
    List<Vehiculo> findByTipoVehiculo(TipoVehiculo tipo);

    // Consulta para filtrar vehículos por precio mínimo
    List<Vehiculo> findByPrecioDiaGreaterThanEqual(Double precio);

    // Consulta para filtrar vehículos por precio máximo
    List<Vehiculo> findByPrecioDiaLessThanEqual(Double precio);
}