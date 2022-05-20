package com.sistema.nttdata.servicio;

import com.sistema.nttdata.common.ApplicationDates;
import com.sistema.nttdata.modelo.Cliente;
import com.sistema.nttdata.modelo.Cuenta;
import com.sistema.nttdata.modelo.CuentaPk;
import com.sistema.nttdata.modelo.Respuesta;
import com.sistema.nttdata.repositorio.ClienteRepositorio;
import com.sistema.nttdata.repositorio.CuentaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CuentaServicioTest {

    @Mock
    CuentaRepositorio cuentaRepository;

    @InjectMocks
    CuentaServicio cuentaServicio;

    private Cliente newCliente;
    private Cuenta newCuenta;
    private CuentaPk cuentaPk;

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

        cuentaPk = new CuentaPk();
        cuentaPk.setCcuenta(123456);
        //cuentaPk.setFhasta(ApplicationDates.DEFAULT_EXPIRY_TIMESTAMP);

        newCuenta = new Cuenta();
        newCuenta.setPrimaryKey(cuentaPk);
        newCuenta.setEstado(true);
        newCuenta.setTipocuenta("AHORRO");
        newCuenta.setSaldoinicial(BigDecimal.TEN);
        newCuenta.setCliente(newCliente);


    }

    @Test
    void save() {
        when(cuentaRepository.save(any(Cuenta.class))).thenReturn(newCuenta);
        assertNotNull(cuentaServicio.save(new Cuenta()));
    }

}