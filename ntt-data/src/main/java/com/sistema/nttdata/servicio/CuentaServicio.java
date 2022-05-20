package com.sistema.nttdata.servicio;

import com.sistema.nttdata.repositorio.CuentaRepositorio;
import com.sistema.nttdata.common.ApplicationDates;
import com.sistema.nttdata.modelo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CuentaServicio {
    private static Logger log = LoggerFactory.getLogger(CuentaServicio.class.getName());

    @Autowired
    CuentaRepositorio cuentaRepositorio;

    @Autowired
    MovimientoServicio movimientoServicio;

    @Autowired
    CuentaIdServicio cuentaIdServicio;
    boolean depositoInicial = true;

    public Respuesta save(Cuenta cuenta) {
        Respuesta resp = new Respuesta();
        try {
            if (cuenta != null) {
                CuentaId cuentaId = this.guardarCuentaId(cuenta);
                CuentaPk cuentaPk = new CuentaPk();
                cuentaPk.setCcuenta(cuenta.getPrimaryKey().getCcuenta());
                cuentaPk.setFhasta(ApplicationDates.DEFAULT_EXPIRY_TIMESTAMP);
                cuenta.setFmodificacion(ApplicationDates.getDBTimestamp());
                cuenta.setPrimaryKey(cuentaPk);
                cuenta.setCuentaId(cuentaId);
                Optional<Cuenta> cuentaold = cuentaRepositorio.findById(cuenta.getPrimaryKey());
                if (cuentaold.isPresent()) {
                    this.depositoInicial = false;
                    this.caducarCuenta(cuentaold.get());
                }
                cuentaRepositorio.save(cuenta);
                if (this.depositoInicial && cuenta.getSaldoinicial().compareTo(BigDecimal.ZERO) > 0) {
                    movimientoServicio.save(cuentaPk, cuenta.getSaldoinicial());
                }
                resp.setCorrecto(true);
                resp.setMensaje("Cuenta Guardada");
                resp.setTitulo("Transacción Procesada");
            } else {
                resp.setCorrecto(false);
                resp.setMensaje("No se pudo crear la cuenta");
                resp.setTitulo("Transacción No Procesada");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resp.setCorrecto(false);
            resp.setMensaje("No se pudo guardar la cuenta");
            resp.setTitulo("Transacción No Procesada");
        }

        return resp;
    }

    private void caducarCuenta(Cuenta cuentaOld) {
        Cuenta cuentaCaducar = new Cuenta();
        cuentaCaducar.setCuentaId(cuentaOld.getCuentaId());
        cuentaCaducar.setCliente(cuentaOld.getCliente());
        cuentaCaducar.setSaldoinicial(cuentaOld.getSaldoinicial());
        cuentaCaducar.setEstado(cuentaOld.isEstado());
        cuentaCaducar.setTipocuenta(cuentaOld.getTipocuenta());
        cuentaCaducar.setFmodificacion(cuentaOld.getFmodificacion());
        CuentaPk cuentaPk = new CuentaPk();
        cuentaPk.setCcuenta(cuentaOld.getPrimaryKey().getCcuenta());
        cuentaPk.setFhasta(ApplicationDates.getDBTimestamp());
        cuentaCaducar.setPrimaryKey(cuentaPk);
        cuentaRepositorio.save(cuentaCaducar);
    }

    private CuentaId guardarCuentaId(Cuenta cuenta) {
        CuentaId cuentaId = new CuentaId();
        cuentaId.setCcuenta(cuenta.getPrimaryKey().getCcuenta());
        cuentaIdServicio.save(cuentaId);
        return cuentaId;
    }

    public List<Cuenta> getAll() {
        return (List<Cuenta>) cuentaRepositorio.findAll();
    }
}
