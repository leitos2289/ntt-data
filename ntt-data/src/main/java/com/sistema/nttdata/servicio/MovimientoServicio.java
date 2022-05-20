package com.sistema.nttdata.servicio;

import com.sistema.nttdata.repositorio.CuentaIdRepositorio;
import com.sistema.nttdata.repositorio.CuentaRepositorio;
import com.sistema.nttdata.repositorio.MovimientoRepositorio;
import com.sistema.nttdata.common.ApplicationDates;
import com.sistema.nttdata.modelo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class MovimientoServicio {
    private static Logger log = LoggerFactory.getLogger(MovimientoServicio.class.getName());

    @Autowired
    MovimientoRepositorio movimientoRepositorio;

    @Autowired
    CuentaRepositorio cuentaRepositorio;
    @Autowired
    CuentaIdRepositorio cuentaIdRepositorio;

    private static final BigDecimal LIMITEDIARIO = new BigDecimal(1000);
    private boolean correcto = true;
    private String mensaje = "";

    public Respuesta save(CuentaPk ccuentaPk, BigDecimal valor) {
        Respuesta resp = new Respuesta();

        if (valor.compareTo(BigDecimal.ZERO) == 0) {
            resp.setCorrecto(false);
            resp.setMensaje("Valor del movimiento debe ser diferente de 0");
            resp.setTitulo("Transacción No Procesada");
            return resp;
        }
        try {
            if (ccuentaPk != null) {
                Optional<Cuenta> cuenta = cuentaRepositorio.findById(ccuentaPk);
                if (cuenta.isPresent()) {
                    if(this.comprobarCuenta(cuenta.get())) {
                        Movimiento movimiento = new Movimiento();
                        BigDecimal saldo = getSaldo(ccuentaPk);
                        if (valor.compareTo(BigDecimal.ZERO) < 0) {
                            this.procesarRetiro(cuenta.get(), valor, movimiento, saldo);

                        } else {
                            debitoCredito(movimiento, valor, cuenta.get(), "C", saldo);
                            movimientoRepositorio.save(movimiento);
                            correcto = true;
                            mensaje = "Transacción realizada correctamente";
                        }
                    }
                } else {
                    correcto = false;
                    mensaje = "Cuenta no Existe";
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            correcto = false;
            mensaje = "No se pudo guardar el movimiento";
        } finally {
            resp.setCorrecto(correcto);
            resp.setMensaje(mensaje);
            resp.setTitulo(correcto ? "Transacción Procesada" : "Transacción No Procesada");
        }
        return resp;
    }

    private void debitoCredito(Movimiento movimiento, BigDecimal valor, Cuenta cuenta, String tipoMovimiento,
                               BigDecimal saldo) {
        saldo = saldo.add(valor);
        movimiento.setCcuenta(cuenta.getPrimaryKey().getCcuenta());
        movimiento.setFecha(ApplicationDates.getDBTimestamp());
        movimiento.setSaldo(saldo);
        movimiento.setTipomovimiento(tipoMovimiento);
        movimiento.setValor(valor);
    }

    private BigDecimal getSaldo(CuentaPk ccuentaPk) {
        BigDecimal saldo = movimientoRepositorio.getSaldo(ccuentaPk.getCcuenta());
        return saldo == null ? BigDecimal.ZERO : saldo;
    }

    private boolean validarRetiroDiario(Integer ccuenta, BigDecimal valor) {
        BigDecimal valorRetiro = movimientoRepositorio.getValorRetiro(ccuenta);
        valorRetiro = valorRetiro == null ? BigDecimal.ZERO : valorRetiro;
        valorRetiro = valorRetiro.add(valor);
        valorRetiro = valorRetiro.abs();
        return LIMITEDIARIO.compareTo(valorRetiro) >= 0;
    }

    private void procesarRetiro(Cuenta cuenta, BigDecimal valor, Movimiento movimiento, BigDecimal saldo) {
        if (validarRetiroDiario(cuenta.getPrimaryKey().getCcuenta(), valor)) {
            if (saldo.compareTo(BigDecimal.ZERO) == 0 || valor.abs().compareTo(saldo) > 0) {
                correcto = false;
                mensaje = "Saldo no disponible, saldo actual:" + saldo + ", valor a retirar: " + valor.abs();
            } else {
                debitoCredito(movimiento, valor, cuenta, "D", saldo);
                movimientoRepositorio.save(movimiento);
                correcto = true;
                mensaje = "Transacción realizada correctamente";

            }
        } else {
            correcto = false;
            mensaje = "Cupo diario Excedido: " + LIMITEDIARIO;
        }
    }

    private boolean comprobarCuenta(Cuenta cuenta){
        if(!cuenta.isEstado()){
            correcto = cuenta.isEstado();
            mensaje = "Cuenta no está activa";
            return cuenta.isEstado();
        }
        return cuenta.isEstado();

    }

    public Set<EstadoCuenta> estadoCuenta(Integer ccuenta, Date fechaInicio, Date fechaFin) {
        ArrayList<Movimiento> resultado = movimientoRepositorio.getEstadoCuenta(ccuenta, fechaInicio, fechaFin);
        Set<EstadoCuenta> listadoEstadoCuenta = new HashSet<>();
        for (Movimiento obj : resultado) {
            EstadoCuenta estadoCuenta = new EstadoCuenta();
            CuentaPk cuentaPk = new CuentaPk();
            cuentaPk.setCcuenta(obj.getCcuenta());
            cuentaPk.setFhasta(ApplicationDates.DEFAULT_EXPIRY_TIMESTAMP);
            Optional<Cuenta> cuenta = cuentaRepositorio.findById(cuentaPk);
            estadoCuenta.setFecha(obj.getFecha());
            estadoCuenta.setTipomovimiento(obj.getTipomovimiento());
            estadoCuenta.setValor(obj.getValor());
            estadoCuenta.setSaldo(obj.getSaldo());
            estadoCuenta.setNombre(cuenta.get().getCliente().getNombre());
            estadoCuenta.setEstado(cuenta.get().getCliente().isEstado());
            estadoCuenta.setTipo(cuenta.get().getTipocuenta());
            estadoCuenta.setSaldoInicial(cuenta.get().getSaldoinicial());
            listadoEstadoCuenta.add(estadoCuenta);
        }
        return listadoEstadoCuenta;
    }
}
