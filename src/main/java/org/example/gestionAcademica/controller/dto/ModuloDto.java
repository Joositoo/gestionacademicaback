package org.example.gestionAcademica.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModuloDto {
    private Integer id;

    private CicloDto cicloDto;

    private ProfesorDto profesorDto;

    @Size(max = 100)
    @NotNull(message = "El nombre del módulo no puede ser nulo")
    @NotEmpty(message = "El nombre del módulo no puede estar vacío")
    @Pattern(regexp = "^[\\wñÑáéíóúÁÉÍÓÚ ]{3,}$", message = "El nombre solo debe contener letras y espacios")
    private String nombre;

    @Size(max = 255)
    @NotNull(message = "El correo no puede ser nulo")
    @NotEmpty(message = "El correo no puede estar vacío")
    @Pattern(regexp = "^[\\wñÑ._%+-]{5,30}@gmail\\.com$", message = "El email debe ser una dirección de Gmail válida y contar con mínimo 5 y máximo 30 caracteres antes del dominio")
    private String emailProfesor;

    @Size(max = 100)
    @NotNull(message = "El nombre del ciclo no puede estar vacío")
    @NotEmpty(message = "El nombre del ciclo no puede ser nulo")
    @Pattern(regexp = "^[\\wñáéíóúÁÉÍÓÚ ]{5,}$", message = "El nombre del ciclo debe tener mínimo 5 caracteres alfanuméricos")
    private String nombreCiclo;

    public ModuloDto(Integer id, CicloDto cicloDto, ProfesorDto profesorDto, String nombre) {
        this.id = id;
        this.cicloDto = cicloDto;
        this.profesorDto = profesorDto;
        this.nombre = nombre;
    }

    public ModuloDto(String nombreCiclo, String emailProfesor, String nombre) {
        this.nombreCiclo = nombreCiclo;
        this.emailProfesor = emailProfesor;
        this.nombre = nombre;
    }

    public ModuloDto() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CicloDto getCicloDto() {
        return cicloDto;
    }

    public void setCicloDto(CicloDto cicloDto) {
        this.cicloDto = cicloDto;
    }

    public ProfesorDto getProfesorDto() {
        return profesorDto;
    }

    public void setProfesorDto(ProfesorDto profesorDto) {
        this.profesorDto = profesorDto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmailProfesor() {
        return emailProfesor;
    }

    public void setEmailProfesor(String emailProfesor) {
        this.emailProfesor = emailProfesor;
    }

    public String getNombreCiclo() {
        return nombreCiclo;
    }

    public void setNombreCiclo(String nombreCiclo) {
        this.nombreCiclo = nombreCiclo;
    }
}
