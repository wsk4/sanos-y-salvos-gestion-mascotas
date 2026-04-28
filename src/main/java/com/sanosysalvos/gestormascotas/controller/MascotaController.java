package com.sanosysalvos.gestormascotas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanosysalvos.gestormascotas.model.Mascota;
import com.sanosysalvos.gestormascotas.service.MascotaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/mascotas")
@RequiredArgsConstructor
public class MascotaController {

    private final MascotaService mascotaService;

    @PostMapping
    public ResponseEntity<Mascota> crearMascota(@Valid @RequestBody Mascota mascota) {
        Mascota nuevaMascota = mascotaService.registrarMascota(mascota);
        return new ResponseEntity<>(nuevaMascota, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Mascota>> listarMascotas(
            @RequestParam(required = false) String estado) {

        List<Mascota> mascotas;
        if (estado != null) {
            mascotas = mascotaService.obtenerPorEstado(estado);
        } else {
            mascotas = mascotaService.obtenerTodas();
        }

        return ResponseEntity.ok(mascotas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mascota> obtenerMascota(@PathVariable Integer id) {
        Mascota mascota = mascotaService.obtenerPorId(id);
        return ResponseEntity.ok(mascota);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Mascota> actualizarMascotaParcial(
            @PathVariable Integer id,
            @RequestBody Mascota mascotaParcial) {

        Mascota mascotaActualizada = mascotaService.actualizarMascotaParcial(id, mascotaParcial);
        return ResponseEntity.ok(mascotaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMascota(@PathVariable Integer id) {
        mascotaService.eliminarMascota(id);

        return ResponseEntity.noContent().build();
    }
}
