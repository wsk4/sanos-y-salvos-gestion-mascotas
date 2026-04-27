package com.sanosysalvos.gestormascotas.repository;

import com.sanosysalvos.gestormascotas.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Integer> {
    
    // Spring Data JPA crea la consulta SQL automáticamente solo con nombrar bien el método
    List<Mascota> findByEstado(String estado);
}