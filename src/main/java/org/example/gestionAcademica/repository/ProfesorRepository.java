package org.example.gestionAcademica.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.gestionAcademica.controller.dto.ProfesorDto;
import org.example.gestionAcademica.modelo.Profesor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfesorRepository extends CrudRepository<Profesor, Integer> {
    Profesor findProfesorByEmail(String emailProfesor);

    boolean existsProfesorByEmail(@Size(max = 255) @NotNull String email);

    Optional<Profesor> findProfesorByEmailAndPassword(String email, String password);
}
