package com.sanosysalvos.gestormascotas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sanosysalvos.gestormascotas.model.Mascota;
import com.sanosysalvos.gestormascotas.repository.MascotaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // Lombok inyecta las dependencias automáticamente (mejor práctica que @Autowired)
public class MascotaService {

    private final MascotaRepository mascotaRepository;

    public Mascota registrarMascota(Mascota mascota) {
        // Aquí en el futuro podrías agregar lógica extra, como enviar una alerta
        // al motor de coincidencias antes de guardar.
        mascota.setEstado(mascota.getEstado().toUpperCase());
        return mascotaRepository.save(mascota);
    }

    public List<Mascota> obtenerTodas() {
        return mascotaRepository.findAll();
    }

    public Mascota obtenerPorId(int id) {
        return mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con el ID: " + id));
    }

    public List<Mascota> obtenerPorEstado(String estado) {
        return mascotaRepository.findByEstado(estado.toUpperCase());
    }
    public void eliminarMascota(Integer id) {
        // Validamos que exista antes de intentar borrar
        Mascota mascotaExistente = obtenerPorId(id);
        mascotaRepository.delete(mascotaExistente);
    }
}
