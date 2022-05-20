package com.sistema.nttdata.modelo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

@Embeddable
public class CuentaPk implements Serializable {

    @Column(name = "ccuenta", nullable = false)
    private Integer ccuenta;
    @Column(name = "fhasta", nullable = false)
    private Timestamp fhasta;

    public Integer getCcuenta() {
        return ccuenta;
    }

    public void setCcuenta(Integer ccuenta) {
        this.ccuenta = ccuenta;
    }

    public Timestamp getFhasta() {
        return fhasta;
    }

    public void setFhasta(Timestamp fhasta) {
        this.fhasta = fhasta;
    }
}
