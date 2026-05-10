<div align="center">

# 🐾 Sanos y Salvos — Microservicio: Gestión de Mascotas

[![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.14-brightgreen?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15%20+%20PostGIS-blue?style=for-the-badge&logo=postgresql)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Multi--stage-2496ED?style=for-the-badge&logo=docker)](https://www.docker.com/)
[![Maven](https://img.shields.io/badge/Maven-Wrapper-C71A36?style=for-the-badge&logo=apachemaven)](https://maven.apache.org/)

</div>

---

Este microservicio es el componente central de la plataforma **Sanos y Salvos**, encargado de la administración y persistencia de los reportes de mascotas perdidas y encontradas. Está diseñado bajo una **arquitectura de microservicios escalable**, preparada para integrarse con el API Gateway (BFF) y el Motor de Geolocalización.

---

## 🚀 Stack Tecnológico y Decisiones Técnicas

| Componente | Tecnología | Detalle |
|---|---|---|
| **Lenguaje** | Java 21 (JDK 21) | Aprovechamiento de Virtual Threads para alta concurrencia |
| **Framework** | Spring Boot 3.5.14 | — |
| **Persistencia** | Spring Data JPA + Hibernate | — |
| **Base de Datos** | PostgreSQL 15 + PostGIS | Preparado para consultas geográficas por radio de búsqueda |
| **Gestión de Archivos** | Binario `bytea` en DB | Persistencia de imágenes directa en base de datos |
| **Build** | Maven Wrapper (`./mvnw`) | — |
| **Contenerización** | Docker (Multi-stage build) | Imágenes de producción ligeras |
| **Observabilidad** | Spring Boot Actuator | Health Checks para monitoreo de salud |

---

## 🏛️ Arquitectura del Proyecto (Layered Architecture)

El servicio sigue el principio de **Responsabilidad Única (SRP)** mediante capas altamente desacopladas:

src/main/java/

├── controller/ → Capa de Presentación: REST + multipart/form-data

├── service/ → Capa de Lógica: Reglas de negocio y procesamiento de imágenes

├── repository/ → Capa de Acceso a Datos: Repository Pattern (PostgreSQL)

├── model/ → Capa de Dominio: Entidades JPA mapeadas a la base de datos

└── exception/ → Centralización de errores (@RestControllerAdvice) → JSON estandarizado

---

## 📋 Requisitos Previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- **Docker** y **Docker Compose**
- **Java 21** *(solo si deseas correrlo localmente sin Docker)*

> **Configuración de Variables de Entorno:**
> Copia el archivo de ejemplo para crear tu configuración local:
> ```bash
> cp .env.example .env
> ```
> Configura dentro del `.env`: `DB_USERNAME`, `DB_PASSWORD` y `SERVER_PORT=8080`.

---

## 🛠️ Guía de Ejecución Rápida

### 1. Levantar la Infraestructura de Datos (Docker)

Inicia la instancia de PostgreSQL con soporte PostGIS:

```bash
docker-compose up -d postgres-gestion
```

> **Credenciales por defecto:**
> - Host: `localhost:5432`
> - Base de datos: `sanosysalvos_mascotas`
> - Usuario: `postgres`
> - Contraseña: `admin123`

### 2. Ejecutar el Microservicio localmente

```bash
./mvnw spring-boot:run
```

> Acceso base: `http://localhost:8080/api/v1/mascotas`

---

## 📡 Documentación de la API

| Método | Endpoint | Content-Type | Descripción |
|---|---|---|---|
| `POST` | `/api/v1/mascotas` | `multipart/form-data` | Registra una nueva mascota. Soporta imagen opcional. |
| `GET` | `/api/v1/mascotas` | `application/json` | Obtiene la lista completa o filtrada por `?estado=`. |
| `GET` | `/api/v1/mascotas/{id}` | `application/json` | Obtiene el detalle de una mascota específica. |
| `PATCH` | `/api/v1/mascotas/{id}` | `multipart/form-data` | Actualización parcial de datos o fotografía. |
| `DELETE` | `/api/v1/mascotas/{id}` | N/A | Elimina el reporte de la mascota. |

### Estructura de Petición (Multipart)

Para los endpoints `POST` y `PATCH`, envía el body configurado como `form-data`:

| Campo | Tipo | Descripción |
|---|---|---|
| `mascota` | `application/json` | Objeto JSON con los datos de la mascota |
| `archivo` | Binario | Imagen de la mascota (`.jpg`, `.png`) |

---

## 🗂️ Entidad Principal (`Mascota`)

| Atributo | Tipo Java | Columna (DB) | Descripción |
|---|---|---|---|
| `id` | `Integer` | `SERIAL` | Identificador único autoincremental |
| `nombre` | `String` | `VARCHAR` | Nombre de la mascota (Máx. 100 caracteres) |
| `raza` | `String` | `VARCHAR` | Atributo clave para el futuro motor de coincidencias |
| `fotoBytes` | `byte[]` | `BYTEA` | Persistencia binaria de la imagen subida |
| `estado` | `String` | `VARCHAR` | Estado del reporte: `PERDIDA` o `ENCONTRADA` |
| `fechaReporte` | `LocalDateTime` | `TIMESTAMP` | Asignada automáticamente al momento de la creación |

---

## 🐳 Despliegue en Producción (Dockerización Completa)

El `Dockerfile` incluido utiliza un proceso de construcción **Multi-stage**: compila dentro del contenedor y descarta el código fuente para generar una imagen de producción ligera.

```bash
# 1. Construcción de la imagen
docker build -t sanos-y-salvos-gestion-mascotas .

# 2. Ejecución del contenedor (en la red compartida del proyecto)
docker run --network sanos-network -p 8080:8080 sanos-y-salvos-gestion-mascotas
```

---

## 🔍 Salud y Monitoreo

| Recurso | URL |
|---|---|
| **Health Check** | `http://localhost:8080/actuator/health` |

**Ejecución de Pruebas Unitarias:**

```bash
./mvnw test
```

---

## 👥 Equipo de Ingeniería — LMC S.A.

| Integrante |
|---|
| Renato Barriga |
| Matías González |
| Cristóbal Véliz |

> Proyecto desarrollado para el caso semestral: **"Sanos y Salvos – Plataforma Inteligente para la recuperación de mascotas"**.
