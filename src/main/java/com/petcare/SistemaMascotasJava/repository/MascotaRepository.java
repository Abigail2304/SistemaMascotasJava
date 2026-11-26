package com.petcare.SistemaMascotasJava.repository;

import com.petcare.SistemaMascotasJava.model.Mascota;
import com.petcare.SistemaMascotasJava.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MascotaRepository
        extends JpaRepository<Mascota, Long> {

    long countByEspecieIgnoreCase(String especie);

    long countByVacunacionIgnoreCase(String vacunacion);

    void delete(Mascota mascota);
}