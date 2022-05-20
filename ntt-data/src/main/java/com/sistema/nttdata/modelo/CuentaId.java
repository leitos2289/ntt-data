package com.sistema.nttdata.modelo;

import javax.persistence.*;

@Entity
@Table(name = "cuentaid")
public class CuentaId {

    @Id
    @Column(name = "ccuenta")
    private Integer ccuenta;

    public Integer getCcuenta() {
        return ccuenta;
    }

    public void setCcuenta(Integer ccuenta) {
        this.ccuenta = ccuenta;
    }
}
