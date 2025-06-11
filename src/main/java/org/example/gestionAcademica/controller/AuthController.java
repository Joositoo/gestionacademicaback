package org.example.gestionAcademica.controller;

import org.example.gestionAcademica.controller.dto.ProfesorDto;
import org.example.gestionAcademica.modelo.Profesor;
import org.example.gestionAcademica.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ProfesorDto login(@RequestBody ProfesorDto profesorDto) {
        return authService.getProfesorByEmailAndPassword(profesorDto);
    }
}
