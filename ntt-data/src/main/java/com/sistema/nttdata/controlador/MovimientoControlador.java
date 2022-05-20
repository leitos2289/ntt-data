package com.sistema.nttdata.controlador;

import com.sistema.nttdata.servicio.MovimientoServicio;
import com.sistema.nttdata.common.ApplicationDates;
import com.sistema.nttdata.modelo.CuentaPk;
import com.sistema.nttdata.modelo.EstadoCuenta;
import com.sistema.nttdata.modelo.Respuesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Set;

@RestController
@RequestMapping(value = "/movimientos/")
public class MovimientoControlador {
    @Autowired
    MovimientoServicio movimientoServicio;

    @GetMapping(value = "/save")
    public Respuesta save(@RequestParam(value = "ccuenta") Integer ccuenta, @RequestParam(value = "valor") BigDecimal valor) {
        CuentaPk cuentaPk = new CuentaPk();
        cuentaPk.setCcuenta(ccuenta);
        cuentaPk.setFhasta(ApplicationDates.DEFAULT_EXPIRY_TIMESTAMP);
        return movimientoServicio.save(cuentaPk, valor);
    }

    @GetMapping(value = "/estadocuenta")
    public Set<EstadoCuenta> estadocuenta(@RequestParam(value = "ccuenta") Integer ccuenta, @RequestParam(value = "fecha1") Date fecha1, @RequestParam(value = "fecha2") Date fecha2) {
        return movimientoServicio.estadoCuenta(ccuenta, fecha1, fecha2);
    }
}
