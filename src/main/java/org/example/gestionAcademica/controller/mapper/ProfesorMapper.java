package org.example.gestionAcademica.controller.mapper;

import org.example.gestionAcademica.controller.dto.ProfesorDto;
import org.example.gestionAcademica.modelo.Profesor;
import org.springframework.stereotype.Service;

@Service
public class ProfesorMapper {
    public ProfesorDto getDto(Profesor profesor) {
        return new ProfesorDto(
                profesor.getId(),
                profesor.getNombre(),
                profesor.getApellidos(),
                profesor.getEmail(),
                profesor.getPassword(),
                profesor.getRol(),
                profesor.getImg());
    }

    public Profesor getProfesorByDto(ProfesorDto profesorDto) {
        Profesor profesor = new Profesor();
        profesor.setNombre(profesorDto.getNombre());
        profesor.setApellidos(profesorDto.getApellidos());
        profesor.setEmail(profesorDto.getEmail());
        profesor.setPassword(profesorDto.getPassword());
        profesor.setRol(profesorDto.getRol());
        profesor.setImg(profesorDto.getImg());

        return profesor;
    }
}
