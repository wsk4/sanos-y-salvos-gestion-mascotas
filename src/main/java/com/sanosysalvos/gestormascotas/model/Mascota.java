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
import jakarta.validation.constraints.Pattern;
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
    @Size(min = 5, max = 12, message = "El nombre debe tener entre 5 y 12 caracteres")
    
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre solo puede contener letras y espacios")
    @Column(nullable = false, length = 12)
    private String nombre;

    @NotBlank(message = "La raza es obligatoria")
    @Size(min = 10, max = 15, message = "La raza debe tener entre 10 y 15 caracteres")
    
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s\\-]+$", message = "La raza solo puede contener letras")
    @Column(nullable = false, length = 15)
    private String raza;

    @NotBlank(message = "El color es obligatorio")
    @Size(min = 10, max = 15, message = "El color debe tener entre 10 y 15 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s,]+$", message = "El color solo debe contener letras")
    @Column(nullable = false, length = 15)
    private String color;
    
    @NotBlank(message = "El tamaño es obligatorio")
    
    @Pattern(regexp = "^(?i)(pequeño|mediano|grande)$", message = "El tamaño debe ser PEQUEÑO, MEDIANO o GRANDE")
    @Column(nullable = false, length = 15)
    private String tamano;

    @Column(name = "foto_bytes", columnDefinition = "bytea")
    private byte[] fotoBytes;

    @Column(name = "foto_url")
    private String fotoUrl;

    @NotBlank(message = "El estado (PERDIDA/ENCONTRADA) es obligatorio")
    @Pattern(regexp = "^(PERDIDA|ENCONTRADA)$", message = "El estado debe ser exactamente PERDIDA o ENCONTRADA")
    @Column(nullable = false, length = 20)
    private String estado;
    
    @Column(name = "fecha_reporte", nullable = false, updatable = false)
    private LocalDateTime fechaReporte;

    @NotBlank(message = "La información de contacto es obligatoria")
    @Size(min = 9, max = 30, message = "La información de contacto debe tener entre 9 y 30 caracteres")
    
    @Pattern(regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ@\\.\\+\\-\\s]+$", message = "El contacto contiene caracteres especiales no permitidos")
    @Column(name = "contacto_info", nullable = false, length = 50)
    private String contactoInfo;

    @PrePersist
    protected void onCreate() {
        this.fechaReporte = LocalDateTime.now();
    }
}