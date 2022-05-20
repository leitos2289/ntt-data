package com.sistema.nttdata.modelo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "movimiento")
@IdClass(MovimientoPk.class)
public class Movimiento {

    @Id
    @Column(name = "cmovimiento")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cmovimiento;

    @Id
    @Column(name = "ccuenta", nullable = false)
    private Integer ccuenta;

    @Column(name = "fecha")
    private Timestamp fecha;

    @Column(name = "tipomovimiento", length = 1)
    private String tipomovimiento;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "saldo")
    private BigDecimal saldo;

    /*@ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "ccuenta", insertable = false, updatable = false),
            @JoinColumn(name = "fhasta", insertable = false, updatable = false)
    })
    private Cuenta cuenta;*/

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "ccuenta", insertable = false, updatable = false)
    private CuentaId cuentaId;

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

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getTipomovimiento() {
        return tipomovimiento;
    }

    public void setTipomovimiento(String tipomovimiento) {
        this.tipomovimiento = tipomovimiento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public CuentaId getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(CuentaId cuentaId) {
        this.cuentaId = cuentaId;
    }
}
