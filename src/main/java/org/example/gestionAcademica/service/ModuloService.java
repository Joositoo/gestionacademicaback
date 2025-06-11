package org.example.gestionAcademica.service;

import org.example.gestionAcademica.controller.dto.ModuloDto;
import org.example.gestionAcademica.controller.mapper.ModuloMapper;
import org.example.gestionAcademica.modelo.Ciclo;
import org.example.gestionAcademica.modelo.Modulo;
import org.example.gestionAcademica.modelo.PorcentajesRa;
import org.example.gestionAcademica.modelo.Profesor;
import org.example.gestionAcademica.repository.CicloRepository;
import org.example.gestionAcademica.repository.ModuloRepository;
import org.example.gestionAcademica.repository.PorcentajesRaRepository;
import org.example.gestionAcademica.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ModuloService {
    private final ModuloRepository moduloRepository;
    private final ModuloMapper moduloMapper;
    private final CicloRepository cicloRepository;
    private final ProfesorRepository profesorRepository;
    private final PorcentajesRaRepository porcentajesRaRepository;

    @Autowired
    public ModuloService(ModuloRepository moduloRepository, ModuloMapper moduloMapper, CicloRepository cicloRepository, ProfesorRepository profesorRepository, PorcentajesRaRepository porcentajesRaRepository) {
        this.moduloRepository = moduloRepository;
        this.moduloMapper = moduloMapper;
        this.cicloRepository = cicloRepository;
        this.profesorRepository = profesorRepository;
        this.porcentajesRaRepository = porcentajesRaRepository;
    }

    public List<ModuloDto> getModulos() {
        Iterable<Modulo> modulos = moduloRepository.findAll();
        List<ModuloDto> modulosDto = new ArrayList<>();

        for (Modulo modulo : modulos) {
            modulosDto.add(moduloMapper.getDto(modulo));
        }
        return modulosDto;
    }

    public ModuloDto getModuloById(int id) {
        Optional<Modulo> modulo = moduloRepository.findById(id);
        if (modulo.isPresent()){
            return moduloMapper.getDto(modulo.get());
        }
        throw new RuntimeException("Modulo no encontrado");
    }

    public void saveModulo(Modulo modulo) {
        moduloRepository.save(modulo);
    }

    public void updateModulo(int id, ModuloDto moduloDto) {
        Modulo modulo = moduloRepository.findById(id).get();
        if (moduloDto.getNombreCiclo() != null) {
            if (cicloRepository.existsCicloByNombre(moduloDto.getNombreCiclo())) {
                Ciclo ciclo = cicloRepository.findCicloByNombre(moduloDto.getNombreCiclo());
                modulo.setIdCiclo(ciclo);
            }
            else{
                throw new RuntimeException("Ciclo no encontrado");
            }

            if (moduloDto.getEmailProfesor() != null) {
                if (profesorRepository.existsProfesorByEmail(moduloDto.getEmailProfesor())) {
                    Profesor profesor = profesorRepository.findProfesorByEmail(moduloDto.getEmailProfesor());
                    modulo.setIdProfesor(profesor);
                }
                else{
                    throw new RuntimeException("Profesor no encontrado");
                }
            }

            if (moduloDto.getNombre() != null){
                modulo.setNombre(moduloDto.getNombre());
            }
            else{
                throw new RuntimeException("Indica el nombre del módulo");
            }

        }

        moduloRepository.save(modulo);
    }

    public void deleteModuloById(int id){
        if (moduloRepository.existsById(id)){
            moduloRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("Modulo no encontrado");
        }
    }

    public boolean validaLista(List<ModuloDto> listaModulos){
        for (ModuloDto moduloDto : listaModulos) {
            if (!profesorRepository.existsProfesorByEmail(moduloDto.getEmailProfesor()) || !cicloRepository.existsCicloByNombre(moduloDto.getNombreCiclo())){
                return false;
            }
        }
        return true;
    }

    public void saveListaModulos (List<ModuloDto> listaModulos){
        for (ModuloDto moduloDto : listaModulos) {
            Modulo modulo = new Modulo();
            modulo.setIdCiclo(cicloRepository.findCicloByNombre(moduloDto.getNombreCiclo()));
            modulo.setIdProfesor(profesorRepository.findProfesorByEmail(moduloDto.getEmailProfesor()));
            modulo.setNombre(moduloDto.getNombre());

            moduloRepository.save(modulo);
        }
    }

    public List<ModuloDto> getModulosByLista (List<ModuloDto> listaModulos){
        List<ModuloDto> listaModulosDto = new ArrayList<>();

        for (ModuloDto moduloDto : listaModulos) {
            Modulo modulo = moduloRepository.findModuloByNombre(moduloDto.getNombre());
            ModuloDto moduloDtoDto = moduloMapper.getDto(modulo);

            listaModulosDto.add(moduloDtoDto);
        }
        return listaModulosDto;
    }

    public Modulo getModuloByDto(ModuloDto moduloDto) {
        if (cicloRepository.existsCicloByNombre(moduloDto.getNombreCiclo()) && profesorRepository.existsProfesorByEmail(moduloDto.getEmailProfesor())){
            Modulo modulo = new Modulo();
            modulo.setIdCiclo(cicloRepository.findCicloByNombre(moduloDto.getNombreCiclo()));
            modulo.setIdProfesor(profesorRepository.findProfesorByEmail(moduloDto.getEmailProfesor()));
            modulo.setNombre(moduloDto.getNombre());

            return modulo;
        }
        else{
            throw new RuntimeException("Ciclo y/o profesor no encontrado");
        }
    }
}
