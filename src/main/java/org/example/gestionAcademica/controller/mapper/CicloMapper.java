package org.example.gestionAcademica.controller.mapper;

import org.example.gestionAcademica.controller.dto.CicloDto;
import org.example.gestionAcademica.modelo.Ciclo;
import org.springframework.stereotype.Service;

@Service
public class CicloMapper {
    public CicloDto getDto(Ciclo ciclo) {
        return new CicloDto(
                ciclo.getId(),
                ciclo.getNombre()
        );
    }

    public Ciclo getCicloByDto( CicloDto cicloDto) {
        Ciclo ciclo = new Ciclo();
        ciclo.setNombre(cicloDto.getNombre());

        return ciclo;
    }
}
