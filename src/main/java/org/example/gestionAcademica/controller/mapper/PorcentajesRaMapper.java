package org.example.gestionAcademica.controller.mapper;

import org.example.gestionAcademica.controller.dto.PorcentajesRaDto;
import org.example.gestionAcademica.modelo.PorcentajesRa;
import org.example.gestionAcademica.repository.ModuloRepository;
import org.springframework.stereotype.Service;

@Service
public class PorcentajesRaMapper {
    private ModuloMapper moduloMapper;
    private ModuloRepository moduloRepository;

    public PorcentajesRaMapper(ModuloMapper moduloMapper, ModuloRepository moduloRepository) {
        this.moduloMapper = moduloMapper;
        this.moduloRepository = moduloRepository;
    }

    public PorcentajesRaDto getDto (PorcentajesRa porcentajesRa) {
        return new PorcentajesRaDto(
                porcentajesRa.getId(),
                moduloMapper.getDto(porcentajesRa.getModulo()),
                porcentajesRa.getDescripcion(),
                porcentajesRa.getNombre(),
                porcentajesRa.getPorcentaje()
        );
    }

    public PorcentajesRa getPorcentajesByDto(PorcentajesRaDto porcentajesRaDto) {
        PorcentajesRa porcentajesRa = new PorcentajesRa();
        porcentajesRa.setModulo(moduloRepository.findModuloByNombre(porcentajesRaDto.getNombreModulo()));
        porcentajesRa.setDescripcion(porcentajesRaDto.getDescripcion());
        porcentajesRa.setNombre(porcentajesRaDto.getNombre());
        porcentajesRa.setPorcentaje(porcentajesRaDto.getPorcentaje());

        return porcentajesRa;
    }
}
