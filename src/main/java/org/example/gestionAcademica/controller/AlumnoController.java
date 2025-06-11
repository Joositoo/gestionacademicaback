package org.example.gestionAcademica.controller;

import jakarta.validation.Valid;
import org.example.gestionAcademica.controller.dto.AlumnoDto;
import org.example.gestionAcademica.controller.mapper.AlumnoMapper;
import org.example.gestionAcademica.service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {
    @Autowired
    private AlumnoService alumnoService;
    @Autowired
    private AlumnoMapper alumnoMapper;

    @GetMapping
    public ResponseEntity<List<AlumnoDto>> getAll(){
        return ResponseEntity.ok(alumnoService.getAlumnos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlumnoDto> getAlumnoById(@PathVariable int id){
        return ResponseEntity.ok(alumnoService.getAlumnoById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<AlumnoDto> saveListAlumnos (@RequestParam("file") MultipartFile file) {
        List<AlumnoDto> listaAlumnos = alumnoMapper.getAlumnosByFile(file);

        if (alumnoService.validaLista(listaAlumnos)) {
            alumnoService.saveListaAlumnos(listaAlumnos);
            return alumnoService.getAlumnosByLista(listaAlumnos);
        }
        else{
            throw new RuntimeException("El archivo contiene emails de profesores que no existen y/o de alumnos ya registrados");
        }
    }

    @PutMapping("/{id}")
    public AlumnoDto updateAlumno(@PathVariable int id, @Valid @RequestBody AlumnoDto alumnoDto){
        alumnoService.updateAlumno(id, alumnoDto);
        return alumnoService.getAlumnoById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAlumno(@PathVariable int id){
        alumnoService.deleteAlumnoById(id);
        return ResponseEntity.ok("Alumno con id " +id+" eliminado.");
    }
}
