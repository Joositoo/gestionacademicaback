package org.example.gestionAcademica.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "ciclo")
public class Ciclo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @Column(name = "nombre", nullable = false, length = 100)
    @NotNull(message = "El nombre del ciclo no puede estar vacío")
    @NotEmpty(message = "El nombre del ciclo no puede ser nulo")
    @Pattern(regexp = "^[\\wñÑáéíóúÁÉÍÓÚ ]{5,}$", message = "El nombre del ciclo debe tener mínimo 5 caracteres alfanuméricos")
    private String nombre;

    @OneToMany(mappedBy = "idCiclo")
    private Set<Matricula> matriculas = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idCiclo")
    private Set<Modulo> modulos = new LinkedHashSet<>();

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

    public Set<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(Set<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    public Set<Modulo> getModulos() {
        return modulos;
    }

    public void setModulos(Set<Modulo> modulos) {
        this.modulos = modulos;
    }

}