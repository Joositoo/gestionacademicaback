package org.example.gestionAcademica.controller.dto;


import jakarta.validation.constraints.*;

public class PorcentajesRaDto {
    private int id;

    private ModuloDto modulo;

    private String nombreModulo;

    @Size(max = 255)
    @NotNull(message = "La descripción del RA no puede ser nula")
    @NotEmpty(message = "La descripción del RA no puede estar vacía")
    @Pattern(regexp = "^[\\wñÑáéíóúÁÉÍÓÚ\\-() ]{5,100}$", message = "Descripción del RA con mínimo 5 caracteres alfanuméricos")
    private String descripcion;

    @Size(max = 255)
    @NotNull(message = "El nombre del RA no puede ser nulo")
    @NotEmpty(message = "El nombre del RA no puede estar vacío")
    @Pattern(regexp = "^[\\wñÑáéíóúÁÉÍÓÚ ]{2,30}$", message = "Nombre del RA con mínimo 5 caracteres alfanuméricos")
    private String nombre;

    @NotNull(message = "El valor del porcentaje no puede ser nulo")
    @Min(value = 1, message = "El valor del porcentaje debe ser al menos 1")
    @Max(value = 100, message = "El valor del porcentaje no puede ser mayor a 100")
    private Byte porcentaje;


    public PorcentajesRaDto(String nombreModulo, String descripcion, String nombre, Byte porcentaje) {
        this.nombreModulo = nombreModulo;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.porcentaje = porcentaje;
    }

    public PorcentajesRaDto(int id, ModuloDto moduloDto, String descripcion, String nombre, Byte porcentaje) {
        this.id = id;
        this.modulo = moduloDto;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.porcentaje = porcentaje;
    }

    public PorcentajesRaDto() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ModuloDto getModulo() {
        return modulo;
    }

    public void setModulo(ModuloDto modulo) {
        this.modulo = modulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Byte getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Byte porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getNombreModulo() {
        return nombreModulo;
    }

    public void setNombreModulo(String nombreModulo) {
        this.nombreModulo = nombreModulo;
    }
}
