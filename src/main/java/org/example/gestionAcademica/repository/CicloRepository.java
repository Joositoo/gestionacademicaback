package org.example.gestionAcademica.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.gestionAcademica.modelo.Ciclo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CicloRepository extends CrudRepository<Ciclo, Integer> {
    boolean existsCicloByNombre(@Size(max = 100) @NotNull String nombre);

    Ciclo findCicloByNombre(String nombreCiclo);
}
