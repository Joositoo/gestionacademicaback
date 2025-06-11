package org.example.gestionAcademica.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatriculaDto {
    private Integer id;

    private CicloDto cicloDto;

    private AlumnoDto alumnoDto;

    @Size(max = 100)
    @NotNull(message = "El nombre del ciclo no puede estar vacío")
    @NotEmpty(message = "El nombre del ciclo no puede ser nulo")
    @Pattern(regexp = "^[\\wñáéíóúÁÉÍÓÚ ]{5,}$", message = "El nombre del ciclo debe tener mínimo 5 caracteres alfanuméricos")
    private String nombreCiclo;

    @Size(max = 255)
    @NotNull(message = "El correo no puede ser nulo")
    @NotEmpty(message = "El correo no puede estar vacío")
    @Pattern(regexp = "^[\\wñÑ._%+-]{5,30}@gmail\\.com$", message = "El email debe ser una dirección de Gmail válida y contar con mínimo 5 y máximo 30 caracteres antes del dominio")
    private String emailAlumno;

    public MatriculaDto(Integer id, CicloDto cicloDto, AlumnoDto alumnoDto) {
        this.id = id;
        this.cicloDto = cicloDto;
        this.alumnoDto = alumnoDto;
    }

    public MatriculaDto(String emailAlumno, String nombreCiclo) {
        this.emailAlumno = emailAlumno;
        this.nombreCiclo = nombreCiclo;
    }

    public MatriculaDto() {}

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

    public AlumnoDto getAlumnoDto() {
        return alumnoDto;
    }

    public void setAlumnoDto(AlumnoDto alumnoDto) {
        this.alumnoDto = alumnoDto;
    }

    public String getNombreCiclo() {
        return nombreCiclo;
    }

    public void setNombreCiclo(String nombreCiclo) {
        this.nombreCiclo = nombreCiclo;
    }

    public String getEmailAlumno() {
        return emailAlumno;
    }

    public void setEmailAlumno(String emailAlumno) {
        this.emailAlumno = emailAlumno;
    }
}
