package org.example.gestionAcademica.controller;

import jakarta.validation.Valid;
import org.example.gestionAcademica.controller.dto.ProfesorDto;
import org.example.gestionAcademica.controller.mapper.ProfesorMapper;
import org.example.gestionAcademica.modelo.Profesor;
import org.example.gestionAcademica.repository.ProfesorRepository;
import org.example.gestionAcademica.service.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/profesores")
public class ProfesorController {
    @Autowired
    private ProfesorService profesorService;

    @Autowired
    private ProfesorMapper profesorMapper;
    @Autowired
    private ProfesorRepository profesorRepository;

    @GetMapping
    public ResponseEntity<List<ProfesorDto>> getAll(){
        return ResponseEntity.ok(profesorService.getProfesores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfesorDto> getProfesorById(@PathVariable int id){
        Optional<Profesor> profesorOptional = profesorService.getProfesorById(id);

        if (profesorOptional.isPresent()) {
            ProfesorDto dto = profesorMapper.getDto(profesorOptional.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ProfesorDto> saveProfesor(
            @RequestPart("nombre") String nombre,
            @RequestPart("apellidos") String apellidos,
            @RequestPart("email") String email,
            @RequestPart("password") String password,
            @RequestPart("rol") String rol,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {
        try {
            ProfesorDto profesorDto = new ProfesorDto();
            profesorDto.setNombre(nombre);
            profesorDto.setApellidos(apellidos);
            profesorDto.setEmail(email);
            profesorDto.setPassword(password);
            profesorDto.setRol(rol);

            if (imagen != null && !imagen.isEmpty()) {
                byte[] bytesImagen = imagen.getBytes();
                profesorDto.setImg(bytesImagen);
            }

            Profesor profesor = profesorMapper.getProfesorByDto(profesorDto);
            Profesor profesorEncriptado = profesorService.encriptaPassword(profesor);
            profesorService.saveProfesor(profesorEncriptado);

            Profesor profesorCreado = profesorRepository.findProfesorByEmail(profesor.getEmail());

            return ResponseEntity.ok(profesorMapper.getDto(profesorCreado));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProfesor(
            @PathVariable int id,
            @RequestPart("nombre") String nombre,
            @RequestPart("apellidos") String apellidos,
            @RequestPart("email") String email,
            @RequestPart("password") String password,
            @RequestPart("rol") String rol,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {
        try {
            ProfesorDto profesorDto = new ProfesorDto();
            profesorDto.setNombre(nombre);
            profesorDto.setApellidos(apellidos);
            profesorDto.setEmail(email);
            profesorDto.setPassword(password);
            profesorDto.setRol(rol);

            if (imagen != null && !imagen.isEmpty()) {
                byte[] bytesImagen = imagen.getBytes();
                profesorDto.setImg(bytesImagen);
                System.out.println("Imagen cargada correctamente: " + imagen.getOriginalFilename());
            }

            profesorService.updateProfesor(id, profesorDto);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar profesor: " + e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProfesor(@PathVariable int id){
        profesorService.deleteProfesorById(id);
        return ResponseEntity.ok("Profesor con id " +id+" eliminado.");
    }
}
