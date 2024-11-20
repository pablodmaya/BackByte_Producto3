package com.backbyte.repository;

import com.backbyte.models.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlquilerRepository extends JpaRepository<Alquiler, Long> {
    // Métodos personalizados si los necesitas
    @Query("SELECT a FROM Alquiler a WHERE a.cliente.id = :idCliente")
    List<Alquiler> findByClienteId(@Param("idCliente") Long idCliente);


    // Este query me da el precio total de el alquiler (multiplica los dias de reserva por el precio por dia del vehiculo) //
    @Query(value = """
    SELECT 
        DATEDIFF(a.fecha_fin, a.fecha_inicio) * v.precio_dia AS precio_total
    FROM 
        alquiler a
    JOIN 
        vehiculo v ON a.id_Vehiculo = v.id_Vehiculo
    WHERE 
        a.id_Cliente = :idCliente
    """, nativeQuery = true)
    List<Double> calcularPrecioTotalPorReserva(@Param("idCliente") Long idCliente);
}
