# 🏗️ ConstruRenta API - Backend

![Java](https://img.shields.io/badge/Java-17%2B-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.2-green)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![Security](https://img.shields.io/badge/Spring_Security-JWT-red)

**ConstruRenta** es una plataforma integral diseñada para optimizar el alquiler de maquinaria y herramientas de construcción. Este sistema conecta a **Proveedores** (dueños de equipos) con **Clientes** (contratistas o particulares), gestionando todo el ciclo de vida del alquiler: desde la publicación del inventario hasta la devolución y facturación.

El backend está construido bajo una **Arquitectura Hexagonal (Puertos y Adaptadores)**, lo que garantiza que la lógica de negocio permanezca aislada de detalles técnicos externos (como la base de datos o la interfaz web), facilitando el mantenimiento y las pruebas.

## 🔗 Enlaces del Proyecto
- **🖥️ Repositorio Frontend:** [GitHub - Proyecto Front](https://github.com/victormoreno-2007/proyecto-spring-boot-front.git)
- **📄 Documentación API (Swagger):** `http://localhost:8080/swagger-ui/index.html` _(Accesible al ejecutar el proyecto)_

---

## 💡 ¿Para qué sirve esta aplicación?

El sector de la construcción sufre a menudo de procesos manuales e informales para el alquiler de equipos. **ConstruRenta** digitaliza este proceso ofreciendo:

### 🌟 Funcionalidades Clave
1.  **Gestión de Inventario (Proveedores):** Permite subir herramientas con fotos, descripciones técnicas, stock real y precios por día.
2.  **Motor de Reservas Inteligente:** Calcula costos automáticamente basándose en fechas, verifica disponibilidad en tiempo real y evita conflictos de reservas (overbooking).
3.  **Control de Calidad (Devoluciones):** Sistema para reportar daños o averías al momento de devolver una herramienta, calculando costos de reparación si aplica.
4.  **Panel Administrativo:** Dashboard con métricas de ingresos, usuarios más activos y herramientas más rentables.
5.  **Seguridad Robusta:** Protección de rutas mediante Roles (RBAC) y Tokens JWT.

---

## 📂 Arquitectura del Proyecto (Hexagonal)

El código sigue estrictamente los principios **SOLID** y **Clean Architecture**, dividiendo el sistema en capas concéntricas:

```text
src/main/java/com/construrrenta/api_gateway/
│
├── application/      # CASOS DE USO (Application Hexagon)
│   └── services/     # Orquestación de la lógica (ej: BookingService, ToolService)
│
├── domain/           # NÚCLEO (Domain Hexagon)
│   ├── model/        # Entidades puras (Tool, User, Booking)
│   ├── ports/        # Interfaces (Puertos de Entrada/Salida)
│   └── exceptions/   # Reglas de negocio y errores de dominio
│
├── infrastructure/   # ADAPTADORES (Infrastructure Hexagon)
│   ├── adapters/     # Implementaciones reales
│   │   ├── in/web/   # Controladores REST (Entrada)
│   │   └── out/      # Repositorios JPA, Seguridad, Mappers (Salida)
│   └── security/     # Configuración de JWT y Spring Security
│
└── ApiGatewayApplication.java

```

---

## 🛠️ Stack Tecnológico

* **Lenguaje:** Java 17 (LTS).
* **Framework:** Spring Boot 4.0.2.
* **Base de Datos:** MySQL 8 (Persistencia Relacional).
* **Seguridad:** Spring Security + JWT (Autenticación Stateless).
* **Herramientas:**
* **Lombok:** Para reducir código repetitivo (Boilerplate).
* **MapStruct:** Para el mapeo eficiente entre Entidades y DTOs.
* **OpenAPI (Swagger):** Documentación viva de la API.
* **Maven:** Gestión de dependencias.



---

## 🚀 Instrucciones de Instalación y Ejecución

Sigue estos pasos para levantar el servidor localmente:

### 1. Prerrequisitos

* Tener instalado **Java JDK 17**.
* Tener **MySQL Server** corriendo (Por defecto configurado en puerto `3307`).
* Tener **Maven** instalado (o usar el wrapper `./mvnw` incluido).

### 2. Clonar el repositorio

```bash
git clone [https://github.com/victormoreno-2007/proyecto-spring-boot.git](https://github.com/victormoreno-2007/proyecto-spring-boot.git)
cd proyecto-spring-boot

```

### 3. Configuración de Base de Datos

Asegúrate de crear una base de datos vacía llamada `sst_db`.

* Si usas credenciales diferentes a `root` / `123456`, edita el archivo:
`src/main/resources/application.properties`

### 4. Carga de Datos (Automática) ✨

El sistema incluye un script inteligente (`data.sql`) y un `DataSeeder` que se ejecutan al iniciar. **No necesitas insertar datos manualmente.**
El sistema iniciará con:

* **Usuarios:** 1 Admin, 2 Proveedores, 2 Clientes.
* **Catálogo:** 20 Herramientas con imágenes y descripciones reales.
* **Historial:** Reservas pasadas para visualizar reportes.

### 5. Ejecutar la Aplicación

```bash
./mvnw spring-boot:run

```

*El servidor iniciará en el puerto `8080`.*

---

## 📡 Documentación de Endpoints (Ejemplos Rápidos)

Aunque la documentación completa está en Swagger, aquí tienes los flujos principales:

### 🔐 1. Autenticación (Login)

* **POST** `/auth/login`
* **Request:**
```json
{
  "email": "admin@construrenta.com",
  "password": "123456"
}

```



### 🛠️ 2. Crear Herramienta (Solo Proveedores)

* **POST** `/api/v1/tools`
* **Header:** `Authorization: Bearer <TOKEN>`
* **Request:**
```json
{
  "name": "Taladro Percutor",
  "description": "Taladro industrial 20V",
  "pricePerDay": 45000,
  "imageUrl": "/herramientas/taladro.jpeg",
  "stock": 5
}

```



### 📅 3. Crear Reserva (Clientes)

* **POST** `/api/v1/bookings`
* **Request:**
```json
{
  "toolId": "uuid-de-la-herramienta",
  "startDate": "2024-02-10T08:00:00",
  "endDate": "2024-02-12T18:00:00"
}

```



---

## 🧪 Testing

Para ejecutar las pruebas unitarias y de integración del sistema:

```bash
./mvnw test

```

---

## 👥 Equipo de Desarrollo

Este proyecto fue desarrollado como parte del ciclo de formación en desarrollo de software skill SpringBoot.

| Desarrollador | Rol |
| --- | --- |
| **Marcela Albarracin** | Proveedor - frontend - backend |
| **Sebastian Jaimes** | Cleintes - frontend - backend |
| **Victor Moreno** | Usiarios, admin - frontend - backend |

---

*© 2024 ConstruRenta - Todos los derechos reservados.*

```

```