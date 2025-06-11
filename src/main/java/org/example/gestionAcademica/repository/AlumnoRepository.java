package org.example.gestionAcademica.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.gestionAcademica.modelo.Alumno;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends CrudRepository<Alumno, Integer> {

    boolean existsAlumnoByEmail(@Size(max = 255) @NotNull String email);

    Alumno findAlumnoByEmail(@Size(max = 255) @NotNull String email);
}
