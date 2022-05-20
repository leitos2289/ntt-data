package com.sistema.nttdata.modelo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "cuenta")
public class Cuenta {

    @EmbeddedId
    private CuentaPk primaryKey;

    @Column(name = "tipocuenta")
    private String tipocuenta;

    @Column(name = "saldoinicial")
    private BigDecimal saldoinicial;

    @Column(name = "estado")
    private boolean estado;

    @Column(name = "fmodificacion")
    private Timestamp fmodificacion;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "clienteid")
    private Cliente cliente;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "ccuenta", insertable = false, updatable = false)
    private CuentaId cuentaId;

    public CuentaId getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(CuentaId cuentaId) {
        this.cuentaId = cuentaId;
    }

    public CuentaPk getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(CuentaPk primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getTipocuenta() {
        return tipocuenta;
    }

    public void setTipocuenta(String tipocuenta) {
        this.tipocuenta = tipocuenta;
    }

    public BigDecimal getSaldoinicial() {
        return saldoinicial;
    }

    public void setSaldoinicial(BigDecimal saldoinicial) {
        this.saldoinicial = saldoinicial;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Timestamp getFmodificacion() {
        return fmodificacion;
    }

    public void setFmodificacion(Timestamp fmodificacion) {
        this.fmodificacion = fmodificacion;
    }
}
