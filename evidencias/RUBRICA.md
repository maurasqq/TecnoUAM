# Matriz de cumplimiento de la rúbrica

Un apartado por criterio de la rúbrica del examen (40 puntos): qué exige, qué lo incumplía antes de la integración y cómo queda cumplido, con el sitio exacto del código.

Los identificadores de commit corresponden a la rama `etapa-5-integracion`.

Verificado en ejecución real: base PostgreSQL creada desde cero, Flyway aplicando `V1` a `V4`, backend en el 8080 y React en el 5173. Los 27 casos de prueba pasan. El detalle está en `evidencias/api/pruebas-api.txt` y en `evidencias/react/`.

---

## Criterio 1 — API REST y lógica de negocio (10 puntos)

> CRUD de equipos, relación con préstamos, persistencia, validaciones y reglas de disponibilidad.

**Qué exige.** Los ocho endpoints de equipos y préstamos con sus códigos HTTP, la relación JPA entre `Equipo` y `Prestamo`, persistencia en base relacional, validación de campos y fechas, y las reglas de disponibilidad: no prestar dos veces, liberar al devolver, no eliminar con préstamo activo.

**Qué lo incumplía.**

1. El proyecto no compilaba tras fusionar las ramas: dependencia `spring-boot-starter-validation` declarada dos veces en el `pom.xml`, y un comando de shell pegado dentro de `PrestamoService.java`.
2. `DELETE /api/equipos/{id}` borraba la fila físicamente. Un equipo con préstamos ya devueltos violaba la clave foránea de `prestamos` y devolvía `500` en vez de `204`.

**Cómo queda cumplido.**

1. Dependencia duplicada eliminada y archivo corregido. El backend compila.
2. Baja lógica: `DELETE` marca `eliminado = true` y responde `204`. Las consultas operativas filtran los eliminados y no se pueden volver a prestar. El historial de préstamos se conserva.

**Dónde.**

- `backend/src/main/java/com/tecnouam/backend/controller/EquipoController.java`
- `backend/src/main/java/com/tecnouam/backend/controller/PrestamoController.java`
- `backend/src/main/java/com/tecnouam/backend/service/impl/EquipoServiceImpl.java`
- `backend/src/main/java/com/tecnouam/backend/service/impl/PrestamoServiceImpl.java`
- `backend/src/main/resources/db/migration/V3__agregar_borrado_logico.sql`
- Commits `6982fba`, `547d82b`, `60094a4`
- `evidencias/api/pruebas-api.txt`: el CRUD, el ciclo de préstamo y las tres variantes de borrado pasan, incluido el `204` al eliminar un equipo con préstamos históricos

---

## Criterio 2 — Seguridad con JWT (10 puntos)

> Login, token válido, protección de endpoints, roles USER/ADMIN y manejo de accesos no autorizados.

**Qué exige.** `POST /api/auth/login` con JWT firmado, API sin sesiones, validación del token, permisos por rol, y los cinco casos de la tabla del enunciado: `401` sin token, `200` con USER, `403` con USER escribiendo, `201` con ADMIN, `401` con credenciales incorrectas. Los secretos no pueden estar en el repositorio.

**Qué lo incumplía.** La contraseña de PostgreSQL estaba escrita en `application.properties` y subida al repositorio.

**Cómo queda cumplido.** Las credenciales de base de datos se leen del entorno (`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`). La clave de firma ya se leía de `JWT_SECRET_BASE64`, y las contraseñas de las cuentas de demostración de `DEMO_ADMIN_PASSWORD` y `DEMO_USER_PASSWORD`. `.env.example` documenta los nombres sin valores.

La autorización se resuelve en la cadena de filtros, no con anotaciones, de modo que los `401` y `403` los emiten los manejadores de seguridad y no se convierten en `500` al pasar por el manejador global de excepciones.

**Dónde.**

- `backend/src/main/java/com/tecnouam/backend/config/SecurityConfig.java`
- `backend/src/main/java/com/tecnouam/backend/security/JwtService.java`
- `backend/src/main/java/com/tecnouam/backend/security/JwtAuthenticationFilter.java`
- `backend/src/main/java/com/tecnouam/backend/auth/AuthController.java`
- `backend/src/main/resources/application.properties`, `.env.example`
- Commit `eb7c065`
- `evidencias/api/pruebas-api.txt`: los cinco casos del enunciado pasan, más el token inválido

> **Nota.** Las contraseñas retiradas siguen presentes en el historial de las ramas anteriores. Sacarlas del código evita que crezca el problema, pero las contraseñas que estuvieron publicadas deben cambiarse en PostgreSQL.

---

## Criterio 3 — Integración con React (8 puntos)

> Componentes, Axios, hooks, consulta real autenticada y estados de la interfaz.

**Qué exige.** Componentes funcionales, Axios, `useState` y `useEffect`, tabla con código patrimonial, nombre, tipo y estado, estados de carga, lista vacía y error, consumo del GET protegido con token, y actualización al recargar tras operar desde Postman. Sin datos simulados.

**Qué lo incumplía.** El formulario de inicio de sesión enviaba los campos `usuario` y `contrasena` y leía el rol de `data.rol`, pero el backend espera `username` y `password` y responde `role`. El login habría fallado.

**Cómo queda cumplido.** El cliente envía `username` y `password` y lee `data.role`. El token se guarda en `sessionStorage` y un interceptor de Axios lo añade como `Authorization: Bearer` en cada petición. La tabla distingue carga, lista vacía y error, y tiene botón de recarga. Todos los datos vienen de la API.

**Dónde.**

- `frontend/src/api/axiosClient.js`, `frontend/src/api/sesion.js`
- `frontend/src/components/LoginForm.jsx`, `frontend/src/components/TablaEquipos.jsx`
- Commit `af96da4`
- `evidencias/react/03-equipo-creado-desde-la-api.jpg`, `04-equipo-prestado.jpg`, `05-equipo-devuelto.jpg`: la operación se hace fuera de React y la tabla refleja el dato real al recargar

---

## Criterio 4 — Pruebas y evidencias (6 puntos)

> Demostración en Postman, casos positivos/negativos, integración y entregables reproducibles.

**Qué exige.** Los nueve escenarios del Reto 4 demostrados en Postman, la colección exportada, capturas, y que todo sea reproducible desde cero.

**Qué lo incumplía.** No existía colección de Postman, ni capturas, ni datos de demostración: las migraciones creaban tablas vacías.

**Cómo queda cumplido.** Colección con 22 peticiones en cuatro carpetas, que cubren los nueve escenarios y guardan los tokens automáticamente, sin contraseñas ni tokens dentro. Ejecutada entera con Newman contra una base recreada desde cero: 25 comprobaciones, 0 fallos. Migración `V4` con cuatro equipos y un préstamo activo, de modo que los casos de `409` son reproducibles nada más arrancar.

**Dónde.**

- `postman/TecnoUAM.postman_collection.json`
- `backend/src/main/resources/db/migration/V4__datos_demostracion.sql`
- `README.md`
- Commits `b314991`, `fccdf91`, `58d1575`
- `evidencias/postman/newman-salida.txt`: la ejecución completa de la colección
- `evidencias/react/`, `evidencias/api/pruebas-api.txt`
- `evidencias/bruno/bruno-salida.txt`: la misma colección ejecutada con el CLI de Bruno
- `bruno/`: la colección en formato Bruno, lista para abrir y ejecutar en la aplicación
- Pendiente: capturas de pantalla del ejecutor, que requieren la aplicación abierta

---

## Criterio 5 — Defensa técnica colaborativa (6 puntos)

> Explicación del sistema, respuestas técnicas y participación verificable de los cinco integrantes.

**Qué exige.** Que los cinco intervengan y que cualquiera pueda explicar un endpoint, una regla de negocio, la relación JPA, el flujo del JWT, la diferencia entre `401` y `403` o la consulta de Axios desde React.

**Qué lo incumplía.** El equipo era de cuatro personas.

**Cómo queda cumplido.** Se incorporó un quinto integrante, que trabajó en la etapa de seguridad. El historial conserva los commits de cada rama por separado, sin aplastar, de modo que la contribución de cada uno es identificable.

**Dónde.**

- `git log --graph` sobre `etapa-5-integracion`
- Pendiente: repartir los temas de la defensa entre los cinco

---

## Entregables

| Entregable | Estado | Dónde |
|---|---|---|
| Repositorio con backend y frontend, con historial de contribuciones | Hecho | Rama `etapa-5-integracion` |
| Migraciones y datos mínimos para reproducir la demostración | Hecho | `backend/src/main/resources/db/migration/` |
| Colección Postman exportada | Hecho | `postman/TecnoUAM.postman_collection.json` |
| README con ejecución, variables sin secretos, cuentas de prueba y endpoints | Hecho | `README.md` |
| Evidencias de las pruebas | Hecho | `evidencias/react/`, `evidencias/api/pruebas-api.txt` |
