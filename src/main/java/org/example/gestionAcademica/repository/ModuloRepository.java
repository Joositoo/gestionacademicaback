package org.example.gestionAcademica.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.gestionAcademica.modelo.Modulo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuloRepository extends CrudRepository<Modulo, Integer> {
    Modulo findModuloByNombre(@Size(max = 100) @NotNull String nombre);

    boolean existsModuloByNombre(@Size(max = 100) @NotNull String nombre);
}
