# 🐾 Sanos y Salvos — Microservicio: Gestión de Mascotas

Este microservicio es el componente central de la plataforma **Sanos y Salvos**, encargado de la administración y persistencia de los reportes de mascotas perdidas y encontradas. Ha sido diseñado bajo una arquitectura de microservicios escalable y elástica, siguiendo los requerimientos técnicos del proyecto.

***

## 🚀 Tecnologías y Herramientas

- **Lenguaje:** Java 21 (JDK 21)
- **Framework:** Spring Boot 3.5.14
- **Persistencia:** Spring Data JPA con Hibernate
- **Base de Datos:** PostgreSQL 15 + **PostGIS** (extensión para soporte de datos geográficos)
- **Gestión de Dependencias:** Maven (wrapper `./mvnw` incluido)
- **Contenerización:** Docker y Docker Compose
- **Librerías Extra:**
  - **Lombok:** Para un código limpio y sin boilerplate
  - **Validation:** Para asegurar la integridad de los datos de entrada
  - **Actuator:** Para monitoreo y health checks del servicio

***

## 🏛️ Arquitectura del Proyecto

El microservicio implementa una **Arquitectura en Capas** para garantizar el desacoplamiento y la facilidad de mantenimiento:

1. **Capa de Presentación (`controller`):** Define los endpoints REST y gestiona la comunicación HTTP con el cliente o API Gateway.
2. **Capa de Lógica (`service`):** Contiene las reglas de negocio, validaciones y orquestación de datos.
3. **Capa de Acceso a Datos (`repository`):** Implementa el **Repository Pattern** para interactuar de forma eficiente con PostgreSQL.
4. **Capa de Dominio (`model`):** Contiene las entidades JPA que mapean las tablas de la base de datos (`mascotas`).
5. **Capa de Excepciones (`exception`):** Implementa un `GlobalExceptionHandler` para respuestas de error estandarizadas.

***

## 🛠️ Instalación y Configuración

### Requisitos Previos

- **Docker Desktop** instalado y en ejecución
- **Java 21** instalado (solo si se ejecuta sin Docker)

### 1. Levantar la Infraestructura (Base de Datos)

Para iniciar el contenedor de PostgreSQL con soporte espacial (PostGIS), ejecuta en la raíz del proyecto:

```bash
docker-compose up -d
```

Esto crea el contenedor `sanosysalvos_db` con la siguiente configuración:

| Parámetro | Valor |
|---|---|
| Host | `localhost:5432` |
| Base de datos | `DB_NAME` (variable de entorno) |
| Usuario | `DB_USER` (variable de entorno) |
| Contraseña | `DB_PASSWORD` (variable de entorno) |

> ⚠️ Los datos persisten en el volumen `postgres_mascotas_data` y no se pierden al detener los contenedores.

### 2. Ejecutar la Aplicación

Desde tu IDE (VS Code / IntelliJ) o mediante la terminal:

```bash
./mvnw spring-boot:run
```

La API estará disponible en: `http://localhost:8080/api/v1/mascotas`

***

## 📡 Documentación de la API (Endpoints)

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/api/v1/mascotas` | Obtiene la lista completa de mascotas reportadas |
| `GET` | `/api/v1/mascotas/{id}` | Obtiene el detalle de una mascota específica por su ID |
| `POST` | `/api/v1/mascotas` | Registra una nueva mascota (`application/json`) |
| `GET` | `/api/v1/mascotas?estado={E}` | Filtra reportes por estado (`PERDIDA` o `ENCONTRADA`) |

### Ejemplo de cuerpo para `POST /api/v1/mascotas`

```json
{
  "nombre": "Max",
  "raza": "Labrador",
  "color": "Dorado",
  "tamano": "Grande",
  "fotoUrl": "https://ejemplo.com/foto.jpg",
  "estado": "PERDIDA",
  "contactoInfo": "+56 9 1234 5678"
}
```

***

## 🗂️ Modelo de Datos

La entidad principal del dominio es `Mascota`, mapeada a la tabla `mascotas` en PostgreSQL:

| Campo | Tipo | Descripción |
|---|---|---|
| `id` | `Integer` | Identificador único (autoincremental) |
| `nombre` | `String` | Nombre de la mascota (requerido, máx. 100 chars) |
| `raza` | `String` | Raza (requerida para el motor de coincidencias) |
| `color` | `String` | Color del pelaje |
| `tamano` | `String` | Tamaño (`Pequeño`, `Mediano`, `Grande`) |
| `fotoUrl` | `String` | URL de la fotografía |
| `estado` | `String` | Estado del reporte (`PERDIDA` / `ENCONTRADA`) |
| `fechaReporte` | `LocalDateTime` | Fecha generada automáticamente al persistir |
| `contactoInfo` | `String` | Información de contacto del reportante |

***

## 💡 Decisiones de Diseño Clave

- **Independencia:** Este servicio es autónomo y posee su propio ciclo de vida, permitiendo su despliegue independiente.
- **Validaciones:** Se utilizan anotaciones de Bean Validation (`@NotBlank`, `@Size`) directamente en la entidad para garantizar integridad de datos.
- **Preparación Espacial:** La base de datos está configurada con `postgis/postgis:15-3.3` para ser consumida por el futuro Motor de Geolocalización.
- **Resiliencia:** El uso de códigos de estado HTTP semánticos (`201 Created`, `200 OK`) garantiza una integración fluida con el Frontend.
- **Fecha automática:** El campo `fechaReporte` se asigna automáticamente mediante `@PrePersist`, evitando que el cliente deba enviarlo.

***

## 🔍 Health Check

Spring Boot Actuator está habilitado. Puedes verificar el estado del servicio en:

```
GET http://localhost:8080/actuator/health
```

***

## 🧪 Ejecutar Tests

```bash
./mvnw test
```

***

## 🌿 Ramas

| Rama | Descripción |
|---|---|
| `main` | Versión estable en producción |
| `develop` | Rama activa de desarrollo |

***

## 👥 Equipo de Desarrollo

- Renato Barriga
- Matías González
- Cristóbal Véliz

Este proyecto es parte del caso semestral: **"Sanos y Salvos – Plataforma Inteligente para la recuperación de mascotas perdidas"**.
