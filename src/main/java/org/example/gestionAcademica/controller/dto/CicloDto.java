package org.example.gestionAcademica.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.example.gestionAcademica.modelo.Ciclo;

public class CicloDto {
    private Integer id;

    @Size(max = 100)
    @NotNull(message = "El nombre del ciclo no puede estar vacío")
    @NotEmpty(message = "El nombre del ciclo no puede ser nulo")
    @Pattern(regexp = "^[\\wñÑáéíóúÁÉÍÓÚ ]{5,}$", message = "El nombre del cciclo debe tener mínimo 5 caracteres alfanuméricos")
    private String nombre;

    public CicloDto(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public CicloDto(String nombre) {
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
