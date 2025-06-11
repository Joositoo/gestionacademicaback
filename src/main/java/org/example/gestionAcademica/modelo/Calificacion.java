package org.example.gestionAcademica.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Table(name = "calificacion")
public class Calificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_modulo", nullable = false)
    private Modulo idModulo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_alumno", nullable = false)
    private Alumno idAlumno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ra")
    private PorcentajesRa idRa;

    @Column(name = "nota", precision = 5, scale = 2)
    private BigDecimal nota;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Modulo getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(Modulo idModulo) {
        this.idModulo = idModulo;
    }

    public Alumno getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Alumno idAlumno) {
        this.idAlumno = idAlumno;
    }

    public PorcentajesRa getIdRa() {
        return idRa;
    }

    public void setIdRa(PorcentajesRa idRa) {
        this.idRa = idRa;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }
}