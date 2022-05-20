package com.sistema.nttdata.servicio;

import com.sistema.nttdata.repositorio.CuentaIdRepositorio;
import com.sistema.nttdata.modelo.CuentaId;
import com.sistema.nttdata.modelo.Respuesta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CuentaIdServicio {
    private static Logger log = LoggerFactory.getLogger(CuentaIdServicio.class.getName());

    @Autowired
    CuentaIdRepositorio cuentaIdRepositorio;

    public Respuesta save(CuentaId cuentaId) {
        Respuesta resp = new Respuesta();
        try {
            if (cuentaId != null) {
                cuentaIdRepositorio.save(cuentaId);
                resp.setCorrecto(true);
                resp.setMensaje("CuentaId Guardada");
                resp.setTitulo("Transacción Procesada");
            } else {
                resp.setCorrecto(false);
                resp.setMensaje("No se pudo crear la cuentaId");
                resp.setTitulo("Transacción No Procesada");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resp.setCorrecto(false);
            resp.setMensaje("No se pudo guardar la cuentaId");
            resp.setTitulo("Transacción No Procesada");
        }
        return resp;
    }
}
