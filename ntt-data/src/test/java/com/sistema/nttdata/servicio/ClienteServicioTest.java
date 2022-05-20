package com.sistema.nttdata.servicio;

import com.sistema.nttdata.modelo.*;
import com.sistema.nttdata.repositorio.ClienteRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ClienteServicioTest {

    @Mock
    ClienteRepositorio clienteRepository;

    @InjectMocks
    ClienteServicio clienteServicio;

    private Cliente newCliente;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        newCliente = new Cliente();
        newCliente.setEstado(true);
        newCliente.setContrasena("123456");
        newCliente.setNombre("PRUEBA");
        newCliente.setDireccion("Direccion");
        newCliente.setEdad(25);
        newCliente.setGenero("F");
        newCliente.setIdentificaion("0525984522");
        newCliente.setTelefono("0558758569");
    }


    @Test
    void save() {
        Respuesta respuesta = new Respuesta();
        when(clienteRepository.save(any(Cliente.class))).thenReturn(newCliente);
        assertNotNull(clienteServicio.save(new Cliente()));
    }

    @Test
    void getAll() {
    }
}