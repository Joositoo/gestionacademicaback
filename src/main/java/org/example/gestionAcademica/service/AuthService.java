package org.example.gestionAcademica.service;

import org.example.gestionAcademica.controller.dto.ProfesorDto;
import org.example.gestionAcademica.controller.mapper.ProfesorMapper;
import org.example.gestionAcademica.modelo.Profesor;
import org.example.gestionAcademica.repository.ProfesorRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    ProfesorRepository profesorRepository;
    ProfesorMapper profesorMapper;

    @Autowired
    public AuthService(ProfesorRepository profesorRepository, ProfesorMapper profesorMapper) {
        this.profesorRepository = profesorRepository;
        this.profesorMapper = profesorMapper;
    }

    public ProfesorDto getProfesorByEmailAndPassword(ProfesorDto profesorDto) {
        Optional<Profesor> profesorOpt = Optional.ofNullable(profesorRepository.findProfesorByEmail(profesorDto.getEmail()));

        if (profesorOpt.isPresent()){
            Profesor profesor = profesorOpt.get();
            if (BCrypt.checkpw(profesorDto.getPassword(), profesor.getPassword())){
                return profesorMapper.getDto(profesorRepository.findProfesorByEmail(profesorDto.getEmail()));
            }
        }
        throw new RuntimeException("Credenciales incorrectas.");
    }
}
