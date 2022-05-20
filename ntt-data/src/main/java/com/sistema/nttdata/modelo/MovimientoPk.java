package com.sistema.nttdata.modelo;

import java.io.Serializable;

public class MovimientoPk implements Serializable {
    private Integer cmovimiento;
    private Integer ccuenta;

    public Integer getCmovimiento() {
        return cmovimiento;
    }

    public void setCmovimiento(Integer cmovimiento) {
        this.cmovimiento = cmovimiento;
    }

    public Integer getCcuenta() {
        return ccuenta;
    }

    public void setCcuenta(Integer ccuenta) {
        this.ccuenta = ccuenta;
    }
}
