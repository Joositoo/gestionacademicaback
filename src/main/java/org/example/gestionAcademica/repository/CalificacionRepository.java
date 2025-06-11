package org.example.gestionAcademica.repository;

import jakarta.validation.constraints.NotNull;
import org.example.gestionAcademica.modelo.Alumno;
import org.example.gestionAcademica.modelo.Calificacion;
import org.example.gestionAcademica.modelo.Modulo;
import org.example.gestionAcademica.modelo.PorcentajesRa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalificacionRepository extends CrudRepository<Calificacion, Integer> {
    boolean existsCalificacionByIdAlumnoAndIdModulo(@NotNull Alumno idAlumno, @NotNull Modulo idModulo);

    Calificacion findCalificacionByIdAlumnoAndIdModulo(@NotNull Alumno idAlumno, @NotNull Modulo idModulo);

    boolean existsCalificacionByIdAlumnoAndIdRa(Alumno idAlumno, PorcentajesRa idRa);
}
