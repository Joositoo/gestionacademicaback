package org.example.gestionAcademica.repository;

import org.example.gestionAcademica.modelo.Modulo;
import org.example.gestionAcademica.modelo.PorcentajesRa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PorcentajesRaRepository extends CrudRepository<PorcentajesRa, Integer> {
    PorcentajesRa findPorcentajesRaByModulo(Modulo modulo);

    boolean existsPorcentajesRaByModulo(Modulo modulo);

    boolean existsPorcentajesRaById(Integer id);

    PorcentajesRa findPorcentajesRaById(Integer id);

    PorcentajesRa findPorcentajesRaByDescripcion(String descripcion);

    boolean existsPorcentajesRaByDescripcion(String descripcion);
}
