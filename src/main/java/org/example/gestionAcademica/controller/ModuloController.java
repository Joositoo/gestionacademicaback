package org.example.gestionAcademica.controller;

import jakarta.validation.Valid;
import org.example.gestionAcademica.controller.dto.ModuloDto;
import org.example.gestionAcademica.controller.mapper.ModuloMapper;
import org.example.gestionAcademica.modelo.Modulo;
import org.example.gestionAcademica.service.ModuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/modulos")
public class ModuloController {
    @Autowired
    private ModuloService moduloService;
    @Autowired
    private ModuloMapper moduloMapper;

    @GetMapping
    public ResponseEntity<List<ModuloDto>> getAll(){
        return ResponseEntity.ok(moduloService.getModulos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModuloDto> getModuloById(@PathVariable int id){
        return ResponseEntity.ok(moduloService.getModuloById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<ModuloDto> saveModulo(@RequestParam("file")MultipartFile file){
        List<ModuloDto> listaModulos = moduloMapper.getModulosByFile(file);

        if (moduloService.validaLista(listaModulos)){
            moduloService.saveListaModulos(listaModulos);
            return moduloService.getModulosByLista(listaModulos);
        }
        else{
            throw new RuntimeException("El archivo contiene emails de profesores que no existen y/o ciclos que no existen");
        }
    }

    @PutMapping("/{id}")
    public ModuloDto updateModulo(@PathVariable int id,@Valid @RequestBody ModuloDto moduloDto){
        moduloService.updateModulo(id, moduloDto);

        return moduloService.getModuloById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteModulo(@PathVariable int id){
        moduloService.deleteModuloById(id);
        return ResponseEntity.ok("Modulo con id " +id+" eliminado.");
    }
}
