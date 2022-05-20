package com.sistema.nttdata.repositorio;

import com.sistema.nttdata.modelo.Cuenta;
import com.sistema.nttdata.modelo.CuentaPk;
import org.springframework.data.repository.CrudRepository;

public interface CuentaRepositorio extends CrudRepository<Cuenta, CuentaPk> {
}
