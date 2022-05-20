package com.sistema.nttdata.servicio;

import com.sistema.nttdata.repositorio.ClienteRepositorio;
import com.sistema.nttdata.modelo.Cliente;
import com.sistema.nttdata.modelo.Respuesta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServicio {

    private static Logger log = LoggerFactory.getLogger(ClienteServicio.class.getName());
    @Autowired
    ClienteRepositorio clienteRepository;

    public Respuesta save(Cliente clienteNew) {
        Respuesta resp = new Respuesta();

        try {
            if (clienteNew != null) {
                clienteRepository.save(clienteNew);
                resp.setCorrecto(true);
                resp.setMensaje("Cliente Guardado");
                resp.setTitulo("Transaccion Procesada");
            } else {
                resp.setCorrecto(false);
                resp.setMensaje("No se pudo guardar el cliente");
                resp.setTitulo("Transacción No Procesada");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resp.setCorrecto(false);
            resp.setMensaje("No se pudo guardar el cliente");
            resp.setTitulo("Transacción No Procesada");
        }

        return resp;
    }

    public List<Cliente> getAll() {
        return (List<Cliente>) clienteRepository.findAll();
    }
}
