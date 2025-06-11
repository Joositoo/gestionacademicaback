package org.example.gestionAcademica.service;

import org.example.gestionAcademica.controller.dto.AlumnoDto;
import org.example.gestionAcademica.controller.mapper.AlumnoMapper;
import org.example.gestionAcademica.modelo.Alumno;
import org.example.gestionAcademica.modelo.Profesor;
import org.example.gestionAcademica.repository.AlumnoRepository;
import org.example.gestionAcademica.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AlumnoService {
    private final AlumnoRepository alumnoRepository;
    private final AlumnoMapper alumnoMapper;
    private final ProfesorRepository profesorRepository;

    @Autowired
    public AlumnoService(AlumnoRepository alumnoRepository, AlumnoMapper alumnoMapper, ProfesorRepository profesorRepository) {
        this.alumnoRepository = alumnoRepository;
        this.alumnoMapper = alumnoMapper;
        this.profesorRepository = profesorRepository;
    }

    public List<AlumnoDto> getAlumnos() {
        Iterable<Alumno> alumnos = alumnoRepository.findAll();
        List<AlumnoDto> alumnosDto = new ArrayList<>();

        for (Alumno alumno : alumnos) {
            alumnosDto.add(alumnoMapper.getDto(alumno));
        }
        return alumnosDto;
    }

    public AlumnoDto getAlumnoById(int id) {
        Optional<Alumno> optionalAlumno = alumnoRepository.findById(id);
        if (optionalAlumno.isPresent()) {
            return alumnoMapper.getDto(optionalAlumno.get());
        }
        throw new RuntimeException("No existe el alumno con id " + id);
    }

    public void saveAlumno(Alumno alumno) {
        alumnoRepository.save(alumno);
    }

    public void updateAlumno(int id, AlumnoDto alumnoDto) {
        Alumno alumno = alumnoRepository.findById(id).get();

        if (alumnoDto.getNombre() != null) {
            alumno.setNombre(alumnoDto.getNombre());
        }
        if (alumnoDto.getApellidos() != null) {
            alumno.setApellidos(alumnoDto.getApellidos());
        }
        if (alumnoDto.getEmail() != null) {
            if (!Objects.equals(alumnoDto.getEmail(), alumno.getEmail())) {
                if (alumnoRepository.existsAlumnoByEmail(alumnoDto.getEmail())) {
                    throw new RuntimeException("El email ya existe");
                }
                alumno.setEmail(alumnoDto.getEmail());
            }
        }
        if (alumnoDto.getEmailProfesor() != null) {
            if (profesorRepository.existsProfesorByEmail(alumnoDto.getEmailProfesor())) {
                Profesor profesor = profesorRepository.findProfesorByEmail(alumnoDto.getEmailProfesor());
                alumno.setIdProfesor(profesor);
            }
            else{
                throw new RuntimeException("El email del profesor no existe ");
            }
        }

        alumnoRepository.save(alumno);
    }

    public void deleteAlumnoById(int id) {
        if (alumnoRepository.existsById(id)) {
            alumnoRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("No existe el alumno con id " + id);
        }
    }

    public Alumno getAlumnoByDto(AlumnoDto alumnoDto) {
        if (profesorRepository.existsProfesorByEmail(alumnoDto.getEmailProfesor()) && !alumnoRepository.existsAlumnoByEmail(alumnoDto.getEmail())) {
            Alumno alumno = new Alumno();
            alumno.setIdProfesor(profesorRepository.findProfesorByEmail(alumnoDto.getEmailProfesor()));
            alumno.setNombre(alumnoDto.getNombre());
            alumno.setApellidos(alumnoDto.getApellidos());
            alumno.setEmail(alumnoDto.getEmail());

            return alumno;
        }
        else{
            throw new RuntimeException("El email del profesor no existe y el del alumno ya está cogido");
        }
    }

    public List<Alumno> getAlumnosByDtos(List<AlumnoDto> alumnoDtos){
        List<Alumno> alumnos = new ArrayList<>();
        for (AlumnoDto alumnoDto : alumnoDtos) {
            alumnos.add(getAlumnoByDto(alumnoDto));
        }
        return alumnos;
    }

    public boolean validaLista(List<AlumnoDto> listaAlumnos){
        for (AlumnoDto alumnoDto : listaAlumnos) {
            if (alumnoRepository.existsAlumnoByEmail(alumnoDto.getEmail()) || !profesorRepository.existsProfesorByEmail(alumnoDto.getEmailProfesor())) {
                return false;
            }
        }
        return true;
    }

    public void saveListaAlumnos(List<AlumnoDto> listaAlumnos) {
        for (AlumnoDto alumnoDto : listaAlumnos) {
            Alumno alumno = new Alumno();
            alumno.setNombre(alumnoDto.getNombre());
            alumno.setApellidos(alumnoDto.getApellidos());
            alumno.setEmail(alumnoDto.getEmail());
            alumno.setIdProfesor(profesorRepository.findProfesorByEmail(alumnoDto.getEmailProfesor()));
            alumnoRepository.save(alumno);
        }
    }

    public List<AlumnoDto> getAlumnosByLista(List<AlumnoDto> listaAlumnos) {
        List<AlumnoDto> listaAlumnosDto = new ArrayList<>();

        for (AlumnoDto a : listaAlumnos) {
            Alumno alumno = alumnoRepository.findAlumnoByEmail(a.getEmail());
            AlumnoDto alumnoDto = alumnoMapper.getDto(alumno);

            listaAlumnosDto.add(alumnoDto);
        }

        return listaAlumnosDto;
    }
}
