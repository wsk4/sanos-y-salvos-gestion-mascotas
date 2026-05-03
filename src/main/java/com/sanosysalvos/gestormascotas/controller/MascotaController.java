package com.sanosysalvos.gestormascotas.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sanosysalvos.gestormascotas.model.Mascota;
import com.sanosysalvos.gestormascotas.service.MascotaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/mascotas")
@RequiredArgsConstructor
public class MascotaController {

    private final MascotaService mascotaService;

    // Se cambia a MULTIPART_FORM_DATA_VALUE y se usan @RequestPart
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Mascota> crearMascota(
            @RequestPart("mascota") @Valid Mascota mascota,
            @RequestPart(value = "archivo", required = false) MultipartFile archivo) throws IOException {
        
        Mascota nuevaMascota = mascotaService.registrarMascota(mascota, archivo);
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

    // Se adapta para recibir actualizaciones parciales que incluyan la imagen
    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Mascota> actualizarMascotaParcial(
            @PathVariable Integer id,
            @RequestPart(value = "mascota", required = false) Mascota mascotaParcial,
            @RequestPart(value = "archivo", required = false) MultipartFile archivo) throws IOException {

        Mascota mascotaActualizada = mascotaService.actualizarMascotaParcial(id, mascotaParcial, archivo);
        return ResponseEntity.ok(mascotaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMascota(@PathVariable Integer id) {
        mascotaService.eliminarMascota(id);
        return ResponseEntity.noContent().build();
    }
}