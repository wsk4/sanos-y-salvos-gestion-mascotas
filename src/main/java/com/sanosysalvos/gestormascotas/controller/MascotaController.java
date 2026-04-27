package com.sanosysalvos.gestormascotas.controller;

import com.sanosysalvos.gestormascotas.model.Mascota;
import com.sanosysalvos.gestormascotas.service.MascotaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}