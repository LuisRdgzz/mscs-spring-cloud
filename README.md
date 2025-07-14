# 🎬 Movie Microservices System - Spring Cloud Architecture

Este proyecto es una demostración práctica de una arquitectura basada en **microservicios**, construida con **Spring Boot 3**, **Spring Cloud** y **Netflix OSS**. Representa un sistema distribuido para la gestión y transmisión de películas, aplicando descubrimiento de servicios, gateway, configuración centralizada y comunicación entre servicios.

---

## 🧱 Arquitectura General

El sistema está compuesto por cinco módulos principales:

| Microservicio               | Funcionalidad                                                                 |
|----------------------------|------------------------------------------------------------------------------|
| `service-registry`         | Registro de servicios con **Eureka Server**.                                 |
| `config-server`            | Configuración centralizada para todos los servicios.                         |
| `api-gateway`              | Enrutador de peticiones externas a los microservicios registrados.           |
| `movie-catalog-service`    | Gestiona y expone el catálogo de películas disponibles.                      |
| `movie-streaming-service`  | Simula el proceso de transmisión de contenido para usuarios.                 |

---

## 🚀 Tecnologías Utilizadas

- ✅ **Java 17**
- ✅ **Spring Boot 3.5.x**
- ✅ **Spring Cloud 2025.0.0**
- ✅ **Netflix Eureka Server/Client**
- ✅ **Spring Cloud Gateway MVC**
- ✅ **Spring Config Server (modo nativo)**
- ✅ **Gradle** como gestor de dependencias

---

## 🔁 Flujo de Trabajo

1. **Descubrimiento de Servicios**: Todos los microservicios se registran automáticamente en `service-registry` usando Eureka.
2. **Configuración Centralizada**: A través de `config-server`, cada servicio carga su configuración desde archivos locales.
3. **Routing por Gateway**: El `api-gateway` intercepta las peticiones externas y las redirige a los servicios correspondientes.
4. **Servicio de Catálogo**: `movie-catalog-service` responde con detalles del catálogo de películas.
5. **Servicio de Streaming**: `movie-streaming-service` simula la experiencia de reproducción de contenido.

---

## 🛠️ Configuración Local

Asegúrate de tener los siguientes puertos libres:

| Servicio               | Puerto |
|------------------------|--------|
| Service Registry       | 8761   |
| Config Server          | 8888   |
| API Gateway            | 8080   |
| Movie Catalog Service  | 8091   |
| Movie Streaming Service| 8092   |

---

