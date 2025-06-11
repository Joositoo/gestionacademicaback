package org.example.gestionAcademica.service;

import org.example.gestionAcademica.controller.dto.PorcentajesRaDto;
import org.example.gestionAcademica.controller.mapper.PorcentajesRaMapper;
import org.example.gestionAcademica.modelo.Modulo;
import org.example.gestionAcademica.modelo.PorcentajesRa;
import org.example.gestionAcademica.repository.ModuloRepository;
import org.example.gestionAcademica.repository.PorcentajesRaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PorcentajesRaService {
    private final PorcentajesRaRepository porcentajesRaRepository;
    private final PorcentajesRaMapper porcentajesRaMapper;
    private final ModuloRepository moduloRepository;

    @Autowired
    public PorcentajesRaService(PorcentajesRaRepository porcentajesRaRepository, PorcentajesRaMapper porcentajesRaMapper, ModuloRepository moduloRepository) {
        this.porcentajesRaRepository = porcentajesRaRepository;
        this.porcentajesRaMapper = porcentajesRaMapper;
        this.moduloRepository = moduloRepository;
    }

    public List<PorcentajesRaDto> getPorcentajesRa() {
        Iterable<PorcentajesRa> listaPorcentajes = porcentajesRaRepository.findAll();
        List<PorcentajesRaDto> porcentajesRa = new ArrayList<>();

        for (PorcentajesRa p : listaPorcentajes) {
            porcentajesRa.add(porcentajesRaMapper.getDto(p));
        }
        return porcentajesRa;
    }

    public PorcentajesRaDto getPorcentajesRaById(int id) {
        Optional<PorcentajesRa> optionalPorcentajesRa = porcentajesRaRepository.findById(id);
        if (optionalPorcentajesRa.isPresent()) {
            return porcentajesRaMapper.getDto(optionalPorcentajesRa.get());
        }
        else{
            throw new RuntimeException("No existe el porcentaje con id " +id);
        }
    }

    public void savePorcentajeRa(PorcentajesRa porcentajesRa) {
        porcentajesRaRepository.save(porcentajesRa);
    }

    public void updatePorcentajeRa(int id, PorcentajesRaDto porcentajesRaDto) {
        Optional<PorcentajesRa> optionalPorcentajesRa = porcentajesRaRepository.findById(id);
        if (optionalPorcentajesRa.isPresent()) {
            PorcentajesRa porcentajesRa = optionalPorcentajesRa.get();

            if (porcentajesRaDto.getNombreModulo() == null || porcentajesRaDto.getNombreModulo().isEmpty()) {
                throw new RuntimeException("El nombre del módulo no  puede ser nulo o estar vacío");
            }
            if (porcentajesRaDto.getDescripcion() == null || porcentajesRaDto.getDescripcion().isEmpty()) {
                throw new RuntimeException("La descripción no puede estar vacía");
            }
            else {
                porcentajesRa.setDescripcion(porcentajesRaDto.getDescripcion());
            }
            if (porcentajesRaDto.getNombre() == null || porcentajesRaDto.getNombre().isEmpty()) {
                throw new RuntimeException("El nombre no puede estar vacío");
            }
            else{
                porcentajesRa.setNombre(porcentajesRaDto.getNombre());
            }
            if (porcentajesRaDto.getPorcentaje() == null || porcentajesRaDto.getPorcentaje() < 1 || porcentajesRaDto.getPorcentaje() > 99) {
                throw new RuntimeException("El porcentaje debe estar entre 1 y 99");
            } else {
                porcentajesRa.setPorcentaje(porcentajesRaDto.getPorcentaje());
            }

            porcentajesRaRepository.save(porcentajesRa);
        }
        else{
            throw new RuntimeException("No existen el porcentaje con id " +id);
        }
    }

    public void deletePorcentajeRa(int id) {
        if (porcentajesRaRepository.existsById(id)) {
            porcentajesRaRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("No existen los porcentajes para este id");
        }
    }

    public PorcentajesRa getPorcentajesRaByNombreModulo(PorcentajesRa porcentajesRa) {
        Modulo modulo = moduloRepository.findModuloByNombre(porcentajesRa.getModulo().getNombre());
        return porcentajesRaRepository.findPorcentajesRaByModulo(modulo);
    }

    public boolean existeModulo(PorcentajesRaDto porcentajesRaDto) {
        if (moduloRepository.existsModuloByNombre(porcentajesRaDto.getNombreModulo())) {
            if (!porcentajesRaRepository.existsPorcentajesRaByDescripcion(porcentajesRaDto.getDescripcion())) {
                return true;
            }
            else{
                throw new RuntimeException("Ya existe ese RA");
            }
        }
        else{
            throw new RuntimeException("No existe el módulo " +porcentajesRaDto.getNombreModulo());
        }
    }

    public boolean existePorcentaje(PorcentajesRaDto porcentajesRaDto) {
        Modulo modulo = moduloRepository.findModuloByNombre(porcentajesRaDto.getNombreModulo());

        return porcentajesRaRepository.existsPorcentajesRaByModulo(modulo);
    }
}
