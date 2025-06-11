package org.example.gestionAcademica.controller.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProfesorDto {
    private Integer id;

    @Size(max = 100)
    @NotNull(message = "El nombre no puede ser nulo")
    @NotEmpty(message = "El nombre no puede estar vacío")
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ ]{3,}$", message = "El nombre solo debe contener letras y espacios")
    private String nombre;

    @Size(max = 150)
    @NotNull(message = "Los apellidos no pueden ser nulos")
    @NotEmpty(message = "Los apellidos no pueden estar vacíos")
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ ]{3,}$", message = "El apellido solo debe contener letras y espacios")
    private String apellidos;

    @Size(max = 255)
    @NotNull(message = "El correo no puede ser nulo")
    @NotEmpty(message = "El correo no puede estar vacío")
    @Pattern(regexp = "^[\\wñÑ._%+-]{5,30}@gmail\\.com$", message = "El email debe ser una dirección de Gmail válida y contar con mínimo 5 y máximo 30 caracteres antes del dominio")
    private String email;

    @Size(max = 255)
    @NotNull(message = "La contraseña no puede ser nula")
    @NotEmpty(message = "La contraseña no puede estar vacía")
    @Pattern(regexp = "^[\\wñÑáéíóúÁÉÍÓÚ.-]{5,}$", message = "La contraseña debe tener caracteres alfanuméricos y mínimo 5 caracteres")
    private String password;

    @Lob
    @NotNull(message = "El rol no puede ser nulo")
    @NotEmpty(message = "El rol no puede estar vacío")
    @Pattern(regexp = "^(admin|profesor)$", message = "El rol debe ser 'Admin' o 'Profesor'")
    private String rol;

    private byte[] img;

    public ProfesorDto(Integer id, String nombre, String apellidos, String email, String password, String rol, byte[] img) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.img = img;
    }

    public ProfesorDto(String nombre, String apellidos, String email, String password, String rol, byte[] img) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.img = img;
    }

    public ProfesorDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public ProfesorDto(){}

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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
