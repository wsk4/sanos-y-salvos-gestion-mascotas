package com.sanosysalvos.gestormascotas.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mascotas")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre de la mascota no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombre;

    @NotBlank(message = "La raza es obligatoria para el motor de coincidencias")
    private String raza;

    private String color;
    
    private String tamano;

    @Column(name = "foto_bytes", columnDefinition = "bytea")
    private byte[] fotoBytes;

    @Column(name = "foto_url")
    private String fotoUrl;

    @NotBlank(message = "El estado (PERDIDA/ENCONTRADA) es obligatorio")
    private String estado;
    
    @Column(name = "fecha_reporte")
    private LocalDateTime fechaReporte;

    @Column(name = "contacto_info")
    private String contactoInfo;

    @PrePersist
    protected void onCreate() {
        this.fechaReporte = LocalDateTime.now();
    }
}