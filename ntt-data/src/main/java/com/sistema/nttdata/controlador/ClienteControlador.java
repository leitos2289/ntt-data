package com.sistema.nttdata.controlador;

import com.sistema.nttdata.servicio.ClienteServicio;
import com.sistema.nttdata.modelo.Cliente;
import com.sistema.nttdata.modelo.Respuesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping(value = "/cliente/")
public class ClienteControlador {

    @Autowired
    private ClienteServicio clienteService;

    @PostMapping("/save")
    public Respuesta save(@RequestBody Cliente cliente) {
        return clienteService.save(cliente);
    }

    @GetMapping(value = "/all")
    public List<Cliente> getAll() {
        return clienteService.getAll();
    }
}
