package org.example.gestionAcademica.modelo;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "porcentajes_ra")
public class PorcentajesRa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_modulo", nullable = false)
    private Modulo modulo;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "porcentaje")
    private Byte porcentaje;

    @OneToMany(mappedBy = "idRa")
    private Set<Calificacion> calificacions = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo idModulo) {
        this.modulo = idModulo;
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

    public Set<Calificacion> getCalificacions() {
        return calificacions;
    }

    public void setCalificacions(Set<Calificacion> calificacions) {
        this.calificacions = calificacions;
    }

}