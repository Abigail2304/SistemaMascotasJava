package com.petcare.SistemaMascotasJava.service;

import com.petcare.SistemaMascotasJava.model.Mascota;
import com.petcare.SistemaMascotasJava.model.Mascota;
import com.petcare.SistemaMascotasJava.repository.MascotaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class MascotaService {

    private final MascotaRepository repository;

    public MascotaService(MascotaRepository repository) {
        this.repository = repository;
    }

    public List<Mascota> listarTodas() {
        return repository.findAll();
    }

    public Mascota buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Mascota no encontrada"));
    }

    public Mascota guardar(Mascota mascota) {
        return repository.save(mascota);
    }

    public Mascota actualizar(Long id, Mascota datos) {

        Mascota mascota = buscarPorId(id);

        mascota.setNombre(datos.getNombre());
        mascota.setEspecie(datos.getEspecie());
        mascota.setRaza(datos.getRaza());
        mascota.setEdad(datos.getEdad());
        mascota.setSexo(datos.getSexo());
        mascota.setColor(datos.getColor());
        mascota.setPropietario(datos.getPropietario());
        mascota.setTelefono(datos.getTelefono());
        mascota.setVacunacion(datos.getVacunacion());
        mascota.setObservaciones(datos.getObservaciones());

        return repository.save(mascota);
    }

    public void eliminar(Long id) {
        Mascota mascota = buscarPorId(id);
        repository.delete(mascota);
    }

    public long totalMascotas() {
        return repository.count();
    }

    public long totalPerros() {
        return repository.countByEspecieIgnoreCase("Perro");
    }

    public long totalGatos() {
        return repository.countByEspecieIgnoreCase("Gato");
    }

    public long totalVacunadas() {
        return repository.countByVacunacionIgnoreCase("Vacunado");
    }

}