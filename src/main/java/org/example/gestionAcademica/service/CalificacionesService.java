package org.example.gestionAcademica.service;

import org.example.gestionAcademica.controller.dto.CalificacionDto;
import org.example.gestionAcademica.controller.mapper.CalificacionMapper;
import org.example.gestionAcademica.modelo.*;
import org.example.gestionAcademica.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CalificacionesService {
    private final CalificacionRepository calificacionesRepository;
    private final CalificacionMapper calificacionMapper;
    private final ModuloRepository moduloRepository;
    private final AlumnoRepository alumnoRepository;
    private final MatriculaRepository matriculaRepository;
    private final PorcentajesRaRepository porcentajesRaRepository;

    @Autowired
    public CalificacionesService(CalificacionRepository calificacionesRepository, CalificacionMapper calificacionMapper, ModuloRepository moduloRepository, AlumnoRepository alumnoRepository, CicloRepository cicloRepository, MatriculaRepository matriculaRepository, PorcentajesRaRepository porcentajesRaRepository) {
        this.calificacionesRepository = calificacionesRepository;
        this.calificacionMapper = calificacionMapper;
        this.moduloRepository = moduloRepository;
        this.alumnoRepository = alumnoRepository;
        this.matriculaRepository = matriculaRepository;
        this.porcentajesRaRepository = porcentajesRaRepository;
    }

    public List<CalificacionDto> getCalificaciones() {
        Iterable<Calificacion> calificaciones = calificacionesRepository.findAll();
        List<CalificacionDto> calificacionesDto = new ArrayList<>();

        for (Calificacion calificacion : calificaciones) {
            calificacionesDto.add(calificacionMapper.getDto(calificacion));
        }

        return calificacionesDto;
    }

    public CalificacionDto getCalificacionById(int id) {
        Optional<Calificacion> calificacion = calificacionesRepository.findById(id);
        if (calificacion.isPresent()) {
            return calificacionMapper.getDto(calificacion.get());
        }
        throw new RuntimeException("Calificacion no encontrada");
    }

    public void saveCalificacion(Calificacion calificaciones) {
        calificacionesRepository.save(calificaciones);
    }

    public void updateCalificacion(int id, CalificacionDto calificacionDto) {
        Optional<Calificacion> optCalificacion = calificacionesRepository.findById(id);
        if (optCalificacion.isPresent()) {
            Calificacion calificacion = optCalificacion.get();

            if (!calificacionDto.getNombreModulo().isBlank() && calificacionDto.getNombreModulo() !=null && moduloRepository.existsModuloByNombre(calificacionDto.getNombreModulo())) {
                calificacion.setIdModulo(moduloRepository.findModuloByNombre(calificacionDto.getNombreModulo()));
            }
            else{
                throw new RuntimeException("Modulo no encontrado");
            }
            if (!calificacionDto.getEmailAlumno().isBlank() && calificacionDto.getEmailAlumno() != null && alumnoRepository.existsAlumnoByEmail(calificacionDto.getEmailAlumno())) {
                calificacion.setIdAlumno(alumnoRepository.findAlumnoByEmail(calificacionDto.getEmailAlumno()));
            }
            else{
                throw new RuntimeException("Alumno no encontrado");
            }
            if (porcentajesRaRepository.existsPorcentajesRaById(calificacionDto.getIdRa())){
                calificacion.setIdRa(porcentajesRaRepository.findPorcentajesRaById((calificacionDto.getIdRa())));
            }
            else{
                throw new RuntimeException("RA no encontrado");
            }
            if (calificacionDto.getNota() == null ||
                calificacionDto.getNota().compareTo(BigDecimal.ZERO) < 0 ||
                calificacionDto.getNota().compareTo(new BigDecimal("10")) > 0) {
                throw new RuntimeException("La nota debe estar comprendida entre 0 y 10");
            }
            else{
                calificacion.setNota(calificacionDto.getNota());
            }

            calificacionesRepository.save(calificacion);
        }
    }

    public void deleteCalificacionById(int id) {
        if (calificacionesRepository.existsById(id)){
            calificacionesRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("Calificacion no encontrada");
        }
    }

    public Calificacion getCalificacionByDto(CalificacionDto calificacionDto) {
        Alumno alumno = alumnoRepository.findAlumnoByEmail(calificacionDto.getEmailAlumno());
        Modulo modulo = moduloRepository.findModuloByNombre(calificacionDto.getNombreModulo());
        if (!calificacionesRepository.existsCalificacionByIdAlumnoAndIdModulo(alumno, modulo)){
            if(moduloRepository.existsModuloByNombre(calificacionDto.getNombreModulo()) && alumnoRepository.existsAlumnoByEmail(calificacionDto.getEmailAlumno())){
                Calificacion calificacion = new Calificacion();
                calificacion.setIdAlumno(alumnoRepository.findAlumnoByEmail(calificacionDto.getEmailAlumno()));
                calificacion.setIdModulo(moduloRepository.findModuloByNombre(calificacionDto.getNombreModulo()));
                calificacion.setIdRa(porcentajesRaRepository.findPorcentajesRaByModulo(moduloRepository.findModuloByNombre(calificacionDto.getNombreModulo())));
                calificacion.setNota(calificacionDto.getNota());
                return calificacion;
            }
            else{
                throw new RuntimeException("Alumno y/o módulo no encontrado");
            }
        }
        else{
            throw new RuntimeException("La calificación ya existe en el sistema");
        }

    }

    public boolean validaLista(List<CalificacionDto> listaCalificaciones) {
        for (CalificacionDto calificacionDto : listaCalificaciones) {
            if(!moduloRepository.existsModuloByNombre(calificacionDto.getNombreModulo()) ||
               !alumnoRepository.existsAlumnoByEmail(calificacionDto.getEmailAlumno()) ||
               !porcentajesRaRepository.existsPorcentajesRaByDescripcion(calificacionDto.getDescripcionRa())){
                return false;
            }

            Alumno alumno = alumnoRepository.findAlumnoByEmail(calificacionDto.getEmailAlumno());
            Modulo modulo = moduloRepository.findModuloByNombre(calificacionDto.getNombreModulo());
            PorcentajesRa porcentajesRa = porcentajesRaRepository.findPorcentajesRaByDescripcion(calificacionDto.getDescripcionRa());

            Ciclo ciclo = modulo.getIdCiclo();

            if (porcentajesRa.getModulo() != modulo) {
                throw new RuntimeException("Estás asignando una nota a un RA que no pertence al módulo " +calificacionDto.getNombreModulo());
            }

            if (!matriculaRepository.existsMatriculaByIdAlumnoAndIdCiclo(alumno,ciclo)){
                throw new RuntimeException("Alumno no matriculado en el ciclo " +ciclo.getNombre());
            }

            if (calificacionesRepository.existsCalificacionByIdAlumnoAndIdRa(alumno, porcentajesRa)){
                throw new RuntimeException("ya existe una nota para " +calificacionDto.getEmailAlumno()+ " en el RA" + calificacionDto.getDescripcionRa());
            }
        }
        return true;
    }

    public void saveListaCalificaciones(List<CalificacionDto> listaCalificaciones) {
        for (CalificacionDto calificacionDto : listaCalificaciones) {
            Calificacion calificacion = new Calificacion();
            calificacion.setIdModulo(moduloRepository.findModuloByNombre(calificacionDto.getNombreModulo()));
            calificacion.setIdAlumno(alumnoRepository.findAlumnoByEmail(calificacionDto.getEmailAlumno()));
            calificacion.setIdRa(porcentajesRaRepository.findPorcentajesRaByDescripcion(calificacionDto.getDescripcionRa()));
            calificacion.setNota(calificacionDto.getNota());

            calificacionesRepository.save(calificacion);
        }
    }

    public List<CalificacionDto> getCalificacionesByLista(List<CalificacionDto> listaCalificaciones) {
        List<CalificacionDto> listaCalificacionesDto = new ArrayList<>();

        for (CalificacionDto calificacionDto : listaCalificaciones) {
            Alumno alumno = alumnoRepository.findAlumnoByEmail(calificacionDto.getEmailAlumno());
            Modulo modulo = moduloRepository.findModuloByNombre(calificacionDto.getNombreModulo());
            Calificacion calificacion = calificacionesRepository.findCalificacionByIdAlumnoAndIdModulo(alumno, modulo);
            CalificacionDto calif = calificacionMapper.getDto(calificacion);

            listaCalificacionesDto.add(calif);
        }

        return listaCalificacionesDto;
    }
}