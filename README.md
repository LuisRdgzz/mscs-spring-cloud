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


## 🛠️ Guía paso a paso para construir los microservicios

Este proyecto se compone de varios microservicios construidos con **Spring Boot 3**, **Spring Cloud 2025**, y **Netflix Eureka**, todos registrados en un **Service Registry** y comunicándose a través de un **API Gateway**. A continuación, se detalla cómo crear e integrar cada uno:

---

### 📘 1. Service Registry (Eureka Server)

**Objetivo**: Registrar y descubrir servicios automáticamente.

**Dependencias necesarias:**
```groovy
implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-server'
implementation 'org.springframework.boot:spring-boot-starter-web'
```
**Pasos:**
Anotar la clase principal con @EnableEurekaServer. Configurar  **application.properties**
```properties
server.port=8761
eureka.instance.hostname=localhost
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```
Ejecutar con Gradle:

```groovy
./gradlew bootRun
```
Acceder a: [http://localhost:8761](http://localhost:8761)

### 🎞️ 2. Movie Catalog Service
**Objetivo**: Gestionar el catálogo de películas y su metadata.

**Dependencias necesarias**:

```groovy
implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-client'
implementation 'org.springframework.boot:spring-boot-starter-web'
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
runtimeOnly 'com.mysql:mysql-connector-j'
```
**Pasos**:
Anotar la clase principal con @EnableDiscoveryClient.
Configurar **application.properties**:

```properties
server.port=8091
eureka.client.service-url.defaultZone=http://localhost:8761/eureka
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.datasource.url=jdbc:mysql://localhost:3306/micro?createDatabaseIfNotExist=true
spring.datasource.username=luis
spring.datasource.password=luis
spring.jpa.hibernate.ddl-auto=update
```
Implementar modelo, repositorio y controlador REST.

[Opcional] Crear base de datos con Docker:
Crear el archivo yaml **docker-compose**
```yaml
version: '3.8'
services:
  db:
    image: mysql:8.0
    restart: always
    environment:
      - MYSQL_ROOT_PASSWORD=root
      - MYSQL_USER=youruser
      - MYSQL_PASSWORD=yourpassword
    ports:
      - '3306:3306'
    volumes:
      - mysql_data:/var/lib/mysql
      - ./init:/docker-entrypoint-initdb.d

volumes:
  mysql_data:
Archivo init.sql:
```

###📺 3. Movie Streaming Service
**Objetivo**: Simular la transmisión de contenido en video.

**Dependencias necesarias**:
```groovy
implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-client'
implementation 'org.springframework.boot:spring-boot-starter-web'
```
**Pasos**: Anotar la clase principal con @EnableDiscoveryClient. Configurar **application.properties**
```properties
server.port=8092
eureka.client.service-url.defaultZone=http://localhost:8761/eureka
```
Crear controlador MovieStreamController que devuelva archivos multimedia.

### 🚪 4. API Gateway
**Objetivo**: Centralizar las peticiones hacia los microservicios internos.

**Dependencias necesarias**:
```groovy
implementation 'org.springframework.cloud:spring-cloud-starter-gateway-mvc'
implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-client'
```
**Pasos**:

Anotar la clase principal con @EnableDiscoveryClient. Configurar **application.properties**
```properties
server.port=8080
eureka.client.service-url.defaultZone=http://localhost:8761/eureka
spring.cloud.gateway.mvc.routes[0].id=movie-catalog-service
spring.cloud.gateway.mvc.routes[0].uri=http://localhost:8091
spring.cloud.gateway.mvc.routes[0].predicates[0]=Path=/movie-info/**
spring.cloud.gateway.mvc.routes[1].id=movie-streaming-service
spring.cloud.gateway.mvc.routes[1].uri=http://localhost:8092
spring.cloud.gateway.mvc.routes[1].predicates[0]=Path=/stream/**
```

Acceder a: [http://localhost:8092/stream/Pose.mp4](http://localhost:8092/stream/Pose.mp4)

Vía gateway: [http://localhost:8080/stream/Pose.mp4](http://localhost:8080/stream/Pose.mp4)

### 🔗 5. Comunicación entre microservicios
Objetivo: Conectar servicios como movie-streaming-service con movie-catalog-service.

**Pasos**:
En movie-streaming-service, agregar:
```groovy
implementation 'org.springframework.cloud:spring-cloud-starter-loadbalancer'
```
Usar RestClient o RestTemplate para consumir endpoints desde otro microservicio.

Endpoints de ejemplo:

http://localhost:8080/movie-info/list

http://localhost:8080/stream/with-id/4

### 🌐 6. Aplicación Web para Streaming
Se puede desarrollar una página HTML que consuma el endpoint http://localhost:8080/movie-info/list:

🎬 Título (arriba)

📹 Video en el centro (con miniatura desde el segundo 2)

📝 Descripción (abajo)

📱 Diseño moderno, responsive y profesional

### ⚙️ 7. Configuración Centralizada (Config Server)
**Objetivo**: Unificar configuración para todos los microservicios.

Nuevo microservicio: config-server

**Dependencias necesarias**:

```groovy
implementation 'org.springframework.cloud:spring-cloud-config-server'
implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-client'
```
**Pasos**:

Anotar clase principal con:
@EnableDiscoveryClient
@EnableConfigServer

Configurar **application.properties**:

```properties
server.port=8888
eureka.client.service-url.defaultZone=http://localhost:8761/eureka
spring.cloud.config.server.native.search-locations=D:\\Config
spring.profiles.active=native
```
En cada microservicio, agregar:
```groovy
implementation 'org.springframework.cloud:spring-cloud-starter-config'
```
Crear archivos **.properties** por microservicio en **D:\Config**
Dentro del microservicio, mantener solo:
properties


