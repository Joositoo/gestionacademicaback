package org.example.gestionAcademica.controller;

import jakarta.validation.Valid;
import org.example.gestionAcademica.controller.dto.PorcentajesRaDto;
import org.example.gestionAcademica.controller.mapper.PorcentajesRaMapper;
import org.example.gestionAcademica.modelo.PorcentajesRa;
import org.example.gestionAcademica.service.PorcentajesRaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/porcentajes")
public class PorcentajesRaController {
    @Autowired
    private PorcentajesRaService porcentajesRaService;

    @Autowired
    private PorcentajesRaMapper porcentajesRaMapper;

    @GetMapping
    public ResponseEntity<List<PorcentajesRaDto>> getListaPorcentajesRa() {
        return ResponseEntity.ok(porcentajesRaService.getPorcentajesRa());
    }

    @GetMapping("/{id}")
    public PorcentajesRaDto getPorcentajesRaById(@PathVariable int id) {
        return porcentajesRaService.getPorcentajesRaById(id);
    }

    @PostMapping
    public boolean savePorcentajesRa(@Valid @RequestBody PorcentajesRaDto porcentajesRaDto) {
        if (porcentajesRaService.existeModulo(porcentajesRaDto)){
                PorcentajesRa porcentajesRa = porcentajesRaMapper.getPorcentajesByDto(porcentajesRaDto);
                porcentajesRaService.savePorcentajeRa(porcentajesRa);
                return true;
        }
        else{
            throw new RuntimeException("No existe el módulo " +porcentajesRaDto.getNombreModulo());
        }
    }

    @PutMapping("/{id}")
    public boolean updatePorcentajesRa(@PathVariable int id,@Valid @RequestBody PorcentajesRaDto porcentajesRaDto) {
        porcentajesRaService.updatePorcentajeRa(id, porcentajesRaDto);
        return true;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>deletePorcentajesRa(@PathVariable int id) {
        porcentajesRaService.deletePorcentajeRa(id);
        return ResponseEntity.ok("PorcentajeRa con id " +id+ " eliminado");
    }
}
