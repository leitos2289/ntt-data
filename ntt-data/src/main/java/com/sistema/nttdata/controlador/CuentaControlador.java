package com.sistema.nttdata.controlador;

import com.sistema.nttdata.servicio.CuentaIdServicio;
import com.sistema.nttdata.servicio.CuentaServicio;
import com.sistema.nttdata.modelo.Cuenta;
import com.sistema.nttdata.modelo.Respuesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cuenta/")
public class CuentaControlador {
    @Autowired
    private CuentaServicio cuentaservice;

    @Autowired
    private CuentaIdServicio cuentaIdServicio;

    @PostMapping("/save")
    public Respuesta save(@RequestBody Cuenta cuenta) {
        return cuentaservice.save(cuenta);
    }

    @GetMapping(value = "/all")
    public List<Cuenta> getAll() {
        return cuentaservice.getAll();
    }
}
