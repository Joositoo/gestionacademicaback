package org.example.gestionAcademica.controller.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class CalificacionDto {
    private Integer id;

    private ModuloDto moduloDto;

    private AlumnoDto alumnoDto;

    private PorcentajesRaDto porcentajesRaDto;

    @DecimalMin(value = "0.0", inclusive = true, message = "La nota debe ser como mínimo 0")
    @DecimalMax(value = "10.0", inclusive = true, message = "La nota no puede ser mayor a 10")
    @NotNull(message = "La nota no puede ser nula")
    private BigDecimal nota;

    private int idRa;

    @Size(max = 255)
    @Pattern(regexp = "^[\\wñÑáéíóúÁÉÍÓÚ\\-() ]{5,100}$", message = "Descripción del RA con mínimo 5 caracteres alfanuméricos")
    private String descripcionRa;


    @Size(max = 100)
    @Pattern(regexp = "^[\\wñÑáéíóúÁÉÍÓÚ ]{3,}$", message = "El nombre solo debe contener letras y espacios")
    private String nombreModulo;

    @Size(max = 255)
    @NotNull(message = "El correo no puede ser nulo")
    @NotEmpty(message = "El correo no puede estar vacío")
    @Pattern(regexp = "^[\\wñÑ._%+-]{5,30}@gmail\\.com$", message = "El email debe ser una dirección de Gmail válida y contar con mínimo 5 y máximo 30 caracteres antes del dominio")
    private String emailAlumno;

    public CalificacionDto(Integer id, ModuloDto moduloDto, AlumnoDto alumnoDto, PorcentajesRaDto porcentajesRaDto, BigDecimal nota) {
        this.id = id;
        this.moduloDto = moduloDto;
        this.alumnoDto = alumnoDto;
        this.porcentajesRaDto = porcentajesRaDto;
        this.nota = nota;
    }

    public CalificacionDto(String nombreModulo, String emailAlumno, int idRa, BigDecimal nota) {
        this.nombreModulo = nombreModulo;
        this.emailAlumno = emailAlumno;
        this.idRa = idRa;
        this.nota = nota;
    }

    public CalificacionDto(String nombreModulo, String emailAlumno, String descripcionRa, BigDecimal nota) {
        this.nombreModulo = nombreModulo;
        this.emailAlumno = emailAlumno;
        this.descripcionRa = descripcionRa;
        this.nota = nota;
    }

    public CalificacionDto() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ModuloDto getModuloDto() {
        return moduloDto;
    }

    public void setModuloDto(ModuloDto moduloDto) {
        this.moduloDto = moduloDto;
    }

    public AlumnoDto getAlumnoDto() {
        return alumnoDto;
    }

    public void setAlumnoDto(AlumnoDto alumnoDto) {
        this.alumnoDto = alumnoDto;
    }

    public String getNombreModulo() {
        return nombreModulo;
    }

    public void setNombreModulo(String nombreModulo) {
        this.nombreModulo = nombreModulo;
    }

    public String getEmailAlumno() {
        return emailAlumno;
    }

    public void setEmailAlumno(String emailAlumno) {
        this.emailAlumno = emailAlumno;
    }

    public PorcentajesRaDto getPorcentajesRaDto() {
        return porcentajesRaDto;
    }

    public void setPorcentajesRaDto(PorcentajesRaDto porcentajesRaDto) {
        this.porcentajesRaDto = porcentajesRaDto;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }

    public int getIdRa() {
        return idRa;
    }

    public void setIdRa(int idRa) {
        this.idRa = idRa;
    }

    public PorcentajesRaDto getPorcentajesRa() {
        return porcentajesRaDto;
    }

    public String getDescripcionRa() {
        return descripcionRa;
    }

    public void setDescripcionRa(String descripcionRa) {
        this.descripcionRa = descripcionRa;
    }

    public void setPorcentajesRa(PorcentajesRaDto porcentajesRa) {
        this.porcentajesRaDto = porcentajesRa;
    }
}
