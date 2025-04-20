# Prueba Técnica Backend - Nivel Junior

## Objetivo

Implementar dos microservicios independientes en Java que se comuniquen entre sí usando JSON API, incluyendo gestión de productos e inventario, con contenedores Docker y pruebas unitarias básicas.

---

## Estructura del Proyecto

- **productos-service**: Microservicio para la gestión de productos.
- **inventario-service**: Microservicio para el control de inventario, consulta y actualización.

---

## Tecnologías Utilizadas

- Java 17  
- Spring Boot  
- H2 Database (en memoria)  
- JSON API   
- Docker  
- Swagger  
- JUnit / Mockito  

---

## Instrucciones de Instalación y Ejecución

### Requisitos:

- Docker y Docker Compose instalados  
- Java 17  
- IntelliJ 

### Usando Docker:
1. Clona este repositorio:
   ```bash
   git clone https://github.com/Brayan26072000/tu-repositorio.git
2. Entra al directorio del proyecto:
   ```bash
   cd tu-repositorio
3. Ejecuta los contenedores con Docker Compose:
   ```bash
   docker-compose up --build

### Los servicios estarán disponibles en
- http://localhost:8081/swagger-ui.html
- http://localhost:8082/swagger-ui.html
    
## Endpoints Principales
### Productos
- GET /productos
- POST /productos
- GET /productos/{id}
- PUT /productos/{id}
- DELETE /productos/{id}

### Inventario
- GET /inventario
- POST /inventario
- GET /inventario/{id}
- PUT /inventario/{id}
- DELETE /inventario/{id}

## Pruebas unitarias

Pruebas unitarias básicas implementadas con cobertura al 40%.

Incluyen pruebas de:
- Creación y actualización de productos
- Comunicación entre microservicios
- Manejo de errores

1. Ejecutar Pruebas de forma independiente para productos y inventario:
   ```bash
   ./mvnw test

## Descripción de la Arquitectura

La arquitectura está compuesta por dos microservicios principales: 
el productos-service y el inventario-service. Estos servicios se comunican 
entre sí mediante HTTP y el formato de intercambio de datos es JSON, 
siguiendo la especificación JSON API.

productos-service se encarga de la gestión de productos, 
permitiendo crear, leer, actualizar y eliminar productos.

inventario-service gestiona la disponibilidad de los productos en el inventario, 
permitiendo consultar y actualizar el inventario de productos.

Ambos servicios están implementados con Spring Boot y utilizan una 
base de datos en memoria H2 para simplificar la persistencia de datos.


## Decisiones Técnicas

- Base de Datos H2: Se eligió H2 como base de datos en memoria por su 
simplicidad y rápida implementación. No se requiere persistencia de 
datos para esta prueba, por lo que H2 es suficiente para las necesidades del proyecto.

## Diagrama de Interacción entre Servicios

 +----------------+       HTTP        +--------------------+
 | Producto API   |  <------------->  | Inventario API     |
 | (Spring Boot)  |                   | (Spring Boot)      |
 +----------------+                   +--------------------+
         ↑                                     ↑
      Swagger                             Swagger

## Cumplimiento de Requisitos Junior

- Dos microservicios funcionales e independientes
- Comunicación HTTP con JSON API
- Docker básico para cada servicio
- Pruebas unitarias con > 40% cobertura
- Documentación con Swagger

## Autor
Brayan David Gordo Naranjo
Correo: gordonaranjo123@gmail.com
GitHub: https://github.com/Brayan26072000
