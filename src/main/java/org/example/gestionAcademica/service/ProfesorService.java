package org.example.gestionAcademica.service;

import org.example.gestionAcademica.controller.dto.ProfesorDto;
import org.example.gestionAcademica.controller.mapper.ProfesorMapper;
import org.example.gestionAcademica.modelo.Profesor;
import org.example.gestionAcademica.repository.ProfesorRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfesorService {
    private final ProfesorRepository profesoresRepository;
    private final ProfesorMapper profesorMapper;

    @Autowired
    public ProfesorService(ProfesorRepository profesoresRepository, ProfesorMapper profesorMapper) {
        this.profesoresRepository = profesoresRepository;
        this.profesorMapper = profesorMapper;
    }

    public List<ProfesorDto> getProfesores() {
        Iterable<Profesor> profesores = profesoresRepository.findAll();
        List<ProfesorDto> profesoresDto = new ArrayList<>();

        for (Profesor profesor : profesores) {
            profesoresDto.add(profesorMapper.getDto(profesor));
        }
        return profesoresDto;
    }

    public Optional<Profesor> getProfesorById(int id) {
        if (profesoresRepository.existsById(id)){
            Profesor profesor = profesoresRepository.findById(id).get();
            return Optional.of(profesor);
        }
        throw new RuntimeException("Profesor no encontrado");
    }

    public void saveProfesor(Profesor profesor) {
        if (!profesoresRepository.existsProfesorByEmail(profesor.getEmail())) {
            profesoresRepository.save(profesor);
        }
        else{
            throw new RuntimeException("Profesor con email " +profesor.getEmail()+ " ya existe");
        }
    }

    public void updateProfesor(int id, ProfesorDto profesorDto) {
        if (profesoresRepository.existsById(id)) {
            Profesor profesor = profesoresRepository.findById(id).get();

            if (profesorDto.getNombre() != null) {
                profesor.setNombre(profesorDto.getNombre());
            }
            if (profesorDto.getApellidos() != null) {
                profesor.setApellidos(profesorDto.getApellidos());
            }
            if (profesorDto.getEmail() != null) {
                if (!profesorDto.getEmail().equals(profesor.getEmail())) {
                    if (profesoresRepository.existsProfesorByEmail(profesorDto.getEmail())) {
                        throw new RuntimeException("El email le pertenece a otro profesor");
                    }
                    profesor.setEmail(profesorDto.getEmail());
                }
            }
            if (profesorDto.getPassword() != null) {
                String passwordEncriptado = BCrypt.hashpw(profesorDto.getPassword(), BCrypt.gensalt());
                profesor.setPassword(passwordEncriptado);
            }
            if (profesorDto.getRol() != null) {
                profesor.setRol(profesorDto.getRol());
            }
            if (profesorDto.getImg() != null && profesorDto.getImg().length > 0) {
                profesor.setImg(profesorDto.getImg());
            }

            profesoresRepository.save(profesor);
        } else {
            throw new RuntimeException("Profesor no encontrado");
        }
    }

    public void deleteProfesorById(int id) {
        if (profesoresRepository.existsById(id)){
            profesoresRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("Profesor no encontrado");
        }
    }

    public Profesor encriptaPassword(Profesor profesor) {
        String passwordEncriptado = BCrypt.hashpw(profesor.getPassword(), BCrypt.gensalt());
        profesor.setPassword(passwordEncriptado);

        return profesor;
    }
}
