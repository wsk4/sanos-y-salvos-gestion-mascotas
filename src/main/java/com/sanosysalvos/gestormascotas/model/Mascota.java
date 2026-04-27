package com.sanosysalvos.gestormascotas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "mascotas")
@Data // Lombok: Genera automáticamente getters, setters, equals, hashCode y toString
@NoArgsConstructor // Lombok: Constructor vacío (requerido por JPA)
@AllArgsConstructor // Lombok: Constructor con todos los argumentos
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

    @Column(name = "foto_url")
    private String fotoUrl;

    @NotBlank(message = "El estado (PERDIDA/ENCONTRADA) es obligatorio")
    private String estado;
    
    @Column(name = "fecha_reporte")
    private LocalDateTime fechaReporte;

    @Column(name = "contacto_info")
    private String contactoInfo;

    // Se ejecuta automáticamente antes de guardar en la base de datos
    @PrePersist
    protected void onCreate() {
        this.fechaReporte = LocalDateTime.now();
    }
}