# Ride Hailing - Patron State

Este proyecto demuestra el patron State aplicado al ciclo de vida de un viaje: solicitud, asignacion de conductor, llegada, viaje en curso, completado o cancelado.

El repositorio esta organizado como monorepo:

- backend/: API REST con Spring Boot, JPA y H2.
- frontend/: interfaz en HTML, CSS y JavaScript vanilla.

Como ejecutar:

1. Inicia backend desde la raiz:

   ./mvnw.cmd -f backend/pom.xml spring-boot:run

2. Sirve el frontend en un puerto permitido por CORS (ejemplo):

   npx serve frontend -l 3000

3. Abre en el navegador:

   <http://localhost:3000>

API base esperada por el frontend:

<http://localhost:8080/api/rides>
