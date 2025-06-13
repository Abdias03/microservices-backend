# 🛠️ ServiciosApp - Backend (Microservices Architecture)

**ServiciosApp** es una plataforma tipo marketplace de servicios desarrollada con una arquitectura de microservicios usando **Spring Boot**. Esta aplicación permite a usuarios registrarse como **clientes** o **prestadores**, y publicar o buscar servicios por ubicación, categoría y precio.

Este repositorio contiene el **backend** del sistema.

---

## ⚙️ Arquitectura General

                    [ FRONTEND: Angular / Ionic ]
                               |
                           [API Gateway]
                               |
      -----------------------------------------------------
     |                          |                           |

**Notas:**
- Cada microservicio tiene su propia base de datos (principio de autonomía).
- Se puede escalar a más microservicios como `reviews`, `pagos`, `notificaciones`, etc.
- Gateway y Eureka son opcionales para despliegue distribuido.

---

## 🧱 Microservicios incluidos

### 1. 📇 Users-Service
Gestión de usuarios (registro, verificación, login, tipo de usuario).

- **Endpoints principales:**
  - `POST /usuarios`: Crear usuario
  - `GET /usuarios/{id}`: Obtener información de usuario
  - `PUT /usuarios/{id}`: Editar usuario
  - `POST /auth/login`: Iniciar sesión

- **Tecnologías:**
  - Spring Boot
  - Spring Data JPA
  - MySQL
  - Spring Security (en progreso)

---

### 2. 🛎️ Services-Service
Gestión de servicios que los usuarios prestadores publican.

- **Endpoints principales:**
  - `POST /servicios`: Crear servicio
  - `GET /servicios`: Listar servicios con filtros
    - Ejemplo: `?categoria=hogar&lat=19.43&lng=-99.13`
  - `GET /servicios/{id}`: Ver detalle
  - `DELETE /servicios/{id}`: Eliminar servicio

- **Tecnologías:**
  - Spring Boot
  - Spring Data JPA
  - MySQL
  - Filtros por geolocalización y categoría

---

## 🗃️ Estructura del proyecto
microservices-backend/
│
├── users-service/ # Microservicio para usuarios
│ └── src/main/java/com/serviciosapp/users
│
├── services-service/ # Microservicio para servicios publicados
│ └── src/main/java/com/serviciosapp/services
│
├── common/ # Clases y DTOs compartidos (futuro)

 Pruebas
Recomendamos utilizar Postman para probar los endpoints mientras se desarrolla el frontend. Puedes importar la colección desde postman_collection.json (si se incluye más adelante).

📌 Próximas mejoras
 Microservicio de autenticación con JWT

 Spring Cloud Gateway + Eureka (discovery)

 Dockerizar los microservicios

 Testing automatizado con JUnit y Postman

 Microservicio de pagos o integración con Stripe
