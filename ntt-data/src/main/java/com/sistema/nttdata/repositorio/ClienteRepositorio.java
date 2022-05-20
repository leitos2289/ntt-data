package com.sistema.nttdata.repositorio;

import com.sistema.nttdata.modelo.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepositorio extends CrudRepository<Cliente, Integer> {
}
