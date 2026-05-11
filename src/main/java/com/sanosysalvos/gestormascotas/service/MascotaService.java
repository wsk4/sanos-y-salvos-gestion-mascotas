package com.sanosysalvos.gestormascotas.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sanosysalvos.gestormascotas.exception.MascotaNotFoundException;
import com.sanosysalvos.gestormascotas.model.Mascota;
import com.sanosysalvos.gestormascotas.repository.MascotaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class MascotaService {

    private final MascotaRepository mascotaRepository;

    public Mascota registrarMascota(Mascota mascota, MultipartFile archivo) throws IOException {
        if (archivo != null && !archivo.isEmpty()) {
            mascota.setFotoBytes(archivo.getBytes());
            mascota.setFotoUrl(archivo.getOriginalFilename()); 
        }
        mascota.setEstado(mascota.getEstado().toUpperCase());
        return mascotaRepository.save(mascota);
    }

    public List<Mascota> obtenerTodas() {
        return mascotaRepository.findAll();
    }

    public Mascota obtenerPorId(int id) {
        return mascotaRepository.findById(id)
                .orElseThrow(() -> new MascotaNotFoundException("Mascota no encontrada con el ID: " + id));
    }

    public List<Mascota> obtenerPorEstado(String estado) {
        return mascotaRepository.findByEstado(estado.toUpperCase());
    }

    public Mascota actualizarMascotaParcial(Integer id, Mascota mascotaParcial, MultipartFile archivo) throws IOException {
        Mascota mascotaExistente = obtenerPorId(id);

        if (mascotaParcial != null) {
            if (mascotaParcial.getNombre() != null) {
                mascotaExistente.setNombre(mascotaParcial.getNombre());
            }
            if (mascotaParcial.getRaza() != null) {
                mascotaExistente.setRaza(mascotaParcial.getRaza());
            }
            if (mascotaParcial.getColor() != null) {
                mascotaExistente.setColor(mascotaParcial.getColor());
            }
            if (mascotaParcial.getTamano() != null) {
                mascotaExistente.setTamano(mascotaParcial.getTamano());
            }
            if (mascotaParcial.getFotoUrl() != null) {
                mascotaExistente.setFotoUrl(mascotaParcial.getFotoUrl());
            }
            if (mascotaParcial.getEstado() != null) {
                mascotaExistente.setEstado(mascotaParcial.getEstado().toUpperCase());
            }
            if (mascotaParcial.getContactoInfo() != null) {
                mascotaExistente.setContactoInfo(mascotaParcial.getContactoInfo());
            }
        }

        if (archivo != null && !archivo.isEmpty()) {
            mascotaExistente.setFotoBytes(archivo.getBytes());
            mascotaExistente.setFotoUrl(archivo.getOriginalFilename());
        }

        return mascotaRepository.save(mascotaExistente);
    }

    public void eliminarMascota(Integer id) {
        Mascota mascotaExistente = obtenerPorId(id);
        mascotaRepository.delete(mascotaExistente);
    }
}