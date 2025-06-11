package org.example.gestionAcademica.controller;

import jakarta.validation.Valid;
import org.example.gestionAcademica.controller.dto.MatriculaDto;
import org.example.gestionAcademica.controller.mapper.MatriculaMapper;
import org.example.gestionAcademica.modelo.Matricula;
import org.example.gestionAcademica.service.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {
    @Autowired
    private MatriculaService matriculaService;
    @Autowired
    private MatriculaMapper matriculaMapper;

    @GetMapping
    public ResponseEntity<List<MatriculaDto>> getAll(){
        return ResponseEntity.ok(matriculaService.getMatriculas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatriculaDto> getMatriculaById(@PathVariable int id){
        return ResponseEntity.ok(matriculaService.getMatriculaById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public boolean saveMatricula (@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("No se ha enviado ningún archivo o el archivo está vacío");
        }

        List<MatriculaDto> listaMatriculas = matriculaMapper.getMatriculasByFile(file);

        if (matriculaService.validaLista(listaMatriculas)){
            matriculaService.saveListaMatriculas(listaMatriculas);
            return true;
        }
        else{
            throw new RuntimeException("El archivo contiene emails de alumnos y/o ciclos que no existen");
        }
    }

    @PutMapping("/{id}")
    public MatriculaDto updateMatricula(@PathVariable int id,@Valid @RequestBody MatriculaDto matriculaDto){
        matriculaDto.setId(id);
        matriculaService.updateMatricula(id, matriculaDto);

        return matriculaService.getMatriculaById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMatricula(@PathVariable int id){
        matriculaService.deleteMatriculaById(id);
        return ResponseEntity.ok("Matricula con id " +id+" eliminada.");
    }
}
