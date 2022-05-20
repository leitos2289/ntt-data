package com.sistema.nttdata.repositorio;

import com.sistema.nttdata.modelo.Movimiento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;

public interface MovimientoRepositorio extends CrudRepository<Movimiento, Integer> {
    @Query(value = "SELECT saldo " +
            "FROM movimiento WHERE " +
            "    ccuenta = ?1 " +
            "AND cmovimiento= (SELECT MAX(cmovimiento) " +
            "        FROM pruebas.movimiento WHERE " +
            "            ccuenta = ?1) ", nativeQuery = true)
    BigDecimal getSaldo(Integer ccuenta);

    //@Query(value = "select sum(valor) from public.movimiento where fecha>current_date and tipomovimiento = 'D' and ccuenta = ?1 ",nativeQuery = true)
    @Query(value = "select sum(m.valor) from Movimiento m where m.fecha > current_date and m.tipomovimiento = 'D' and m.cuentaId.ccuenta = ?1 ")
    BigDecimal getValorRetiro(Integer ccuenta);

    @Query(value = "select m " +
            "from Movimiento m " +
            "where m.ccuenta = ?1 and DATE(m.fecha) BETWEEN ?2 and ?3 order by m.cmovimiento")
    ArrayList<Movimiento> getEstadoCuenta(Integer ccuenta, Date fechaInicio, Date fechaFin);
}
