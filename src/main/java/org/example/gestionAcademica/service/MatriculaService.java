package org.example.gestionAcademica.service;

import org.example.gestionAcademica.controller.dto.MatriculaDto;
import org.example.gestionAcademica.controller.mapper.MatriculaMapper;
import org.example.gestionAcademica.modelo.Matricula;
import org.example.gestionAcademica.repository.AlumnoRepository;
import org.example.gestionAcademica.repository.CicloRepository;
import org.example.gestionAcademica.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MatriculaService {
    private final MatriculaRepository matriculaRepository;
    private final MatriculaMapper matriculaMapper;
    private final CicloRepository cicloRepository;
    private final AlumnoRepository alumnoRepository;

    @Autowired
    public MatriculaService(MatriculaRepository matriculaRepository, MatriculaMapper matriculaMapper, CicloRepository cicloRepository, AlumnoRepository alumnoRepository) {
        this.matriculaRepository = matriculaRepository;
        this.matriculaMapper = matriculaMapper;
        this.cicloRepository = cicloRepository;
        this.alumnoRepository = alumnoRepository;
    }

    public List<MatriculaDto> getMatriculas(){
        Iterable<Matricula> matriculas = matriculaRepository.findAll();
        List<MatriculaDto> matriculasDto = new ArrayList<>();

        for (Matricula matricula : matriculas) {
            matriculasDto.add(matriculaMapper.getDto(matricula));
        }
        return matriculasDto;
    }

    public MatriculaDto getMatriculaById(int id){
        Optional<Matricula> matricula = matriculaRepository.findById(id);
        if (matricula.isPresent()){
            return matriculaMapper.getDto(matricula.get());
        }
        throw new RuntimeException("Matricula no encontrada");
    }

    public void saveMatricula(Matricula matricula){
        matriculaRepository.save(matricula);
    }

    public void updateMatricula(int id, MatriculaDto matriculaDto) {
        Matricula matricula = matriculaRepository.findById(id).get();

        if (matriculaDto.getEmailAlumno() != null && matriculaDto.getNombreCiclo() != null) {
            if (cicloRepository.existsCicloByNombre(matriculaDto.getNombreCiclo()) && alumnoRepository.existsAlumnoByEmail(matriculaDto.getEmailAlumno())){
                matricula.setIdAlumno(alumnoRepository.findAlumnoByEmail(matriculaDto.getEmailAlumno()));
                matricula.setIdCiclo(cicloRepository.findCicloByNombre(matriculaDto.getNombreCiclo()));
            }
            else{
                throw new RuntimeException("Ciclo y/o alumno no encontrado");
            }
        }

        matriculaRepository.save(matricula);
    }

    public void deleteMatriculaById(int id){
        if (matriculaRepository.existsById(id)){
            matriculaRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("Matricula no encontrada");
        }
    }

    public Matricula getMatriculaByDto(MatriculaDto matriculaDto) {
        if(cicloRepository.existsCicloByNombre(matriculaDto.getNombreCiclo()) && alumnoRepository.existsAlumnoByEmail(matriculaDto.getEmailAlumno())){
            Matricula matricula = new Matricula();
            matricula.setIdCiclo(cicloRepository.findCicloByNombre(matriculaDto.getNombreCiclo()));
            matricula.setIdAlumno(alumnoRepository.findAlumnoByEmail(matriculaDto.getEmailAlumno()));

            return matricula;
        }
        else{
            throw new RuntimeException("Email del alumno y/o ciclo no encontrado");
        }
    }

    public boolean validaLista(List<MatriculaDto> listaMatriculas){
        for (MatriculaDto matriculaDto : listaMatriculas) {
            if (!alumnoRepository.existsAlumnoByEmail(matriculaDto.getEmailAlumno()) || !cicloRepository.existsCicloByNombre(matriculaDto.getNombreCiclo())){
                return false;
            }
        }
        return true;
    }

    public void saveListaMatriculas(List<MatriculaDto> listaMatriculas){
        for (MatriculaDto matriculaDto : listaMatriculas) {
            Matricula matricula = new Matricula();
            matricula.setIdAlumno(alumnoRepository.findAlumnoByEmail(matriculaDto.getEmailAlumno()));
            matricula.setIdCiclo(cicloRepository.findCicloByNombre(matriculaDto.getNombreCiclo()));

            matriculaRepository.save(matricula);
        }
    }
}
