package org.example.gestionAcademica.repository;

import jakarta.validation.constraints.NotNull;
import org.example.gestionAcademica.modelo.Alumno;
import org.example.gestionAcademica.modelo.Ciclo;
import org.example.gestionAcademica.modelo.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Integer> {
    Matricula findMatriculaById(Integer id);

    List<Matricula> findMatriculaByIdAlumno(@NotNull Alumno idAlumno);

    boolean existsMatriculaByIdAlumnoAndIdCiclo(@NotNull Alumno idAlumno, @NotNull Ciclo idCiclo);
}
