# 🐾 Sanos y Salvos — Microservicio: Gestión de Mascotas

Este microservicio es el componente central de la plataforma **Sanos y Salvos**, encargado de la administración y persistencia de los reportes de mascotas perdidas y encontradas. Diseñado bajo una arquitectura de microservicios escalable, utilizando **Spring Boot 3.5.14** y **Java 21**.

---

## 🚀 Stack Tecnológico y Decisiones Técnicas

- **Lenguaje:** Java 21 (JDK 21) — Aprovechamiento de Virtual Threads para alta concurrencia.
- **Framework:** Spring Boot 3.5.14.
- **Persistencia:** Spring Data JPA con Hibernate.
- **Base de Datos:** PostgreSQL 15 + **PostGIS** — Preparado para futuras consultas geográficas por radio de búsqueda.
- **Gestión de Dependencias:** Maven (Wrapper `./mvnw` incluido).
- **Contenerización:** Docker (Estrategia de *Multi-stage build* para imágenes ligeras).
- **Observabilidad:** Spring Boot Actuator para monitoreo de salud (Health Checks).

---

## 🏛️ Arquitectura del Proyecto (Layered Architecture)

El servicio sigue el principio de Responsabilidad Única (SRP) mediante capas desacopladas:

1. **Capa de Presentación (`controller`):** Gestiona la comunicación REST y la recepción de archivos mediante `multipart/form-data`.
2. **Capa de Lógica (`service`):** Orquesta las reglas de negocio, validaciones y el procesamiento de imágenes en formato binario.
3. **Capa de Acceso a Datos (`repository`):** Implementación del Repository Pattern para abstracción de consultas.
4. **Capa de Dominio (`model`):** Entidades JPA con persistencia de imágenes en `bytea`.
5. **Capa de Excepciones (`exception`):** Centralización de errores mediante `@RestControllerAdvice` para respuestas estandarizadas.

---

## 🛠️ Guía de Ejecución Rápida

### 1. Infraestructura de Datos (Docker)

Levanta la instancia de PostgreSQL con soporte PostGIS:

```bash
docker-compose up -d
```

**Configuración:** Host: `localhost:5432`, DB: `sanosysalvos_mascotas`, Usuario: `postgres`, Pass: `admin123`.

### 2. Ejecución del Microservicio

```bash
./mvnw spring-boot:run
```

**Acceso base:** [http://localhost:8080/api/v1/mascotas](http://localhost:8080/api/v1/mascotas)

---

## 📡 Documentación de la API

| Método | Endpoint | Content-Type | Descripción |
|--------|----------|--------------|-------------|
| `POST` | `/api/v1/mascotas` | `multipart/form-data` | Registro de mascota + imagen opcional. |
| `GET` | `/api/v1/mascotas` | `application/json` | Listado total o filtrado por `?estado=`. |
| `GET` | `/api/v1/mascotas/{id}` | `application/json` | Detalle extendido de una mascota. |
| `PATCH` | `/api/v1/mascotas/{id}` | `multipart/form-data` | Actualización parcial de datos o fotografía. |
| `DELETE` | `/api/v1/mascotas/{id}` | `N/A` | Eliminación del reporte. |

### Estructura de Petición (Multipart)

Para `POST` y `PATCH`, se requieren dos partes en el body:

- `mascota`: Objeto JSON (`Content-Type: application/json`).
- `archivo`: Binario de la imagen (opcional).

---

## 🗂️ Entidad Principal (Mascota)

| Atributo | Tipo | Descripción |
|----------|------|-------------|
| `id` | `Integer` | Identificador autoincremental. |
| `nombre` | `String` | Nombre (Validado, máx 100 caracteres). |
| `raza` | `String` | Atributo clave para el motor de coincidencias. |
| `fotoBytes` | `bytea` | Persistencia binaria de la imagen en base de datos. |
| `estado` | `String` | `PERDIDA` o `ENCONTRADA`. |
| `fechaReporte` | `LocalDateTime` | Asignada automáticamente al persistir. |

---

## 🐳 Despliegue en Producción

El `Dockerfile` utiliza un proceso de construcción optimizado:

```bash
# Construcción de la imagen local
docker build -t sanos-y-salvos-gestion-mascotas .

# Ejecución de contenedor
docker run -p 8080:8080 sanos-y-salvos-gestion-mascotas
```

---

## 🔍 Salud y Monitoreo

- **Health Check:** `GET /actuator/health`.
- **Pruebas Unitarias:** `./mvnw test`.

---

## 👥 Equipo de Ingeniería

- Renato Barriga
- Matías González
- Cristóbal Véliz

Proyecto desarrollado para el caso semestral: **"Sanos y Salvos – Plataforma Inteligente para la recuperación de mascotas"**.
