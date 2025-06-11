package org.example.gestionAcademica.controller;

import jakarta.validation.Valid;
import org.example.gestionAcademica.controller.dto.CicloDto;
import org.example.gestionAcademica.controller.mapper.CicloMapper;
import org.example.gestionAcademica.modelo.Ciclo;
import org.example.gestionAcademica.service.CicloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/ciclos")
public class CicloController {
    @Autowired
    private CicloService cicloService;
    @Autowired
    private CicloMapper cicloMapper;

    @GetMapping
    public ResponseEntity<Iterable<CicloDto>> getAll(){
        return ResponseEntity.ok(cicloService.getCiclos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CicloDto>> getCicloById(@PathVariable int id){
        return ResponseEntity.ok(Optional.ofNullable(cicloService.getCicloById(id)));
    }

    @PostMapping()
    public ResponseEntity<Ciclo> saveCiclo(@Valid @RequestBody Ciclo ciclo){
        //Ciclo ciclo = cicloMapper.getCicloByDto(cicloDto);
        cicloService.saveCiclo(ciclo);
        return ResponseEntity.ok(ciclo);
    }

    @PutMapping("/{id}")
    public CicloDto updateCiclo(@PathVariable int id,@Valid @RequestBody CicloDto cicloDto){
        cicloService.updateCiclo(id, cicloDto);
        return cicloService.getCicloById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCiclo(@PathVariable int id){
        cicloService.deleteCicloById(id);
        return ResponseEntity.ok("Ciclo con id " +id+" eliminado.");
    }
}
