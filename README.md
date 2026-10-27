# TecnoUAM — Sistema de préstamo de equipos tecnológicos

Examen práctico integrador de Servicios Web, segundo corte.

API REST en Spring Boot con PostgreSQL, autenticación JWT con roles, e interfaz React que consulta los equipos reales de la API.

```text
backend/     API REST (Java 21, Spring Boot 4.1.2, Maven)
frontend/    Interfaz React (Vite, Axios)
postman/     Colección de pruebas
evidencias/  Capturas y matriz de cumplimiento
```

## Requisitos

- Java 21
- PostgreSQL 14 o superior
- Node.js 20 o superior

## 1. Base de datos

Crear una base vacía. Flyway crea las tablas y carga los datos de demostración al arrancar el backend.

```sql
CREATE DATABASE tecnouam;
```

Migraciones en `backend/src/main/resources/db/migration/`:

| Archivo | Qué hace |
|---|---|
| `V1__crear_tablas.sql` | Tablas `equipos` y `prestamos` |
| `V2__crear_usuarios.sql` | Tabla `usuarios` |
| `V3__agregar_borrado_logico.sql` | Columna `eliminado` de `equipos` |
| `V4__datos_demostracion.sql` | Cuatro equipos y un préstamo activo |

## 2. Variables de entorno

Ninguna contraseña está escrita en el repositorio. Hay que definir estas variables antes de arrancar el backend. `.env.example` lista los nombres sin valores.

| Variable | Para qué | Ejemplo |
|---|---|---|
| `DB_URL` | Conexión a PostgreSQL | `jdbc:postgresql://localhost:5432/tecnouam` |
| `DB_USERNAME` | Usuario de PostgreSQL | `postgres` |
| `DB_PASSWORD` | Su contraseña | — |
| `JWT_SECRET_BASE64` | Clave de firma del JWT, en Base64 y de 32 bytes como mínimo | — |
| `DEMO_ADMIN_PASSWORD` | Contraseña de la cuenta `admin` | — |
| `DEMO_USER_PASSWORD` | Contraseña de la cuenta `operador` | — |

Generar una clave de firma válida:

```bash
openssl rand -base64 32
```

En PowerShell:

```powershell
$env:DB_PASSWORD="..."
$env:JWT_SECRET_BASE64="..."
$env:DEMO_ADMIN_PASSWORD="..."
$env:DEMO_USER_PASSWORD="..."
```

Si faltan `DEMO_ADMIN_PASSWORD` o `DEMO_USER_PASSWORD`, el backend arranca igual pero no siembra las cuentas y el login no funcionará.

## 3. Backend

```bash
cd backend
./mvnw spring-boot:run
```

Queda en `http://localhost:8080`.

## 4. Frontend

```bash
cd frontend
npm install
npm run dev
```

Queda en `http://localhost:5173`. El puerto es fijo: si está ocupado, Vite falla en vez de cambiarlo, porque el backend solo permite ese origen por CORS.

Variable opcional en `frontend/.env`:

| Variable | Valor por defecto |
|---|---|
| `VITE_API_BASE_URL` | `http://localhost:8080/api` |

## Cuentas de prueba

| Usuario | Rol | Puede |
|---|---|---|
| `admin` | `ADMIN` | Consultar y modificar |
| `operador` | `USER` | Solo consultar |

Sus contraseñas salen de `DEMO_ADMIN_PASSWORD` y `DEMO_USER_PASSWORD`.

## Endpoints

| Método | Ruta | Acceso | Respuestas |
|---|---|---|---|
| `POST` | `/api/auth/login` | Público | `200` con el token, `401` si falla |
| `GET` | `/api/equipos` | USER y ADMIN | `200` |
| `GET` | `/api/equipos/{id}` | USER y ADMIN | `200`, `404` |
| `POST` | `/api/equipos` | ADMIN | `201`, `400`, `409` |
| `PUT` | `/api/equipos/{id}` | ADMIN | `200`, `400`, `404`, `409` |
| `DELETE` | `/api/equipos/{id}` | ADMIN | `204`, `404`, `409` |
| `GET` | `/api/prestamos` | USER y ADMIN | `200` |
| `POST` | `/api/prestamos` | ADMIN | `201`, `400`, `404`, `409` |
| `PUT` | `/api/prestamos/{id}/devolver` | ADMIN | `200`, `404`, `409` |

Sin token: `401`. Con rol USER sobre una operación de escritura: `403`.

El token se envía en la cabecera `Authorization: Bearer <token>`.

## Reglas de negocio

- El código patrimonial es único.
- Un equipo prestado no se puede volver a prestar: `409`.
- Registrar un préstamo deja el equipo en `PRESTADO`; devolverlo lo deja en `DISPONIBLE`.
- Devolver dos veces el mismo préstamo: `409`.
- No se puede eliminar un equipo con un préstamo activo: `409`.
- Eliminar es una **baja lógica**: marca `eliminado = true` y responde `204`. El equipo desaparece de las consultas y no se puede volver a prestar, pero su historial de préstamos se conserva.

## Pruebas

La colección está en `postman/TecnoUAM.postman_collection.json`, con 22 peticiones en cuatro carpetas: autenticación, seguridad, equipos y préstamos.

No guarda contraseñas ni tokens. Antes de ejecutarla hay que rellenar las variables `adminPassword` y `userPassword` de la colección. Los tokens se guardan solos al iniciar sesión.

## Demostración

1. Arrancar PostgreSQL, backend y frontend.
2. Entrar en `http://localhost:5173` con `admin`.
3. Ver la tabla con los equipos reales.
4. En Postman, crear un equipo como ADMIN.
5. Recargar React: aparece el equipo nuevo.
6. En Postman, registrar un préstamo de ese equipo.
7. Recargar React: el equipo está `PRESTADO`.
8. En Postman, devolver el préstamo.
9. Recargar React: el equipo vuelve a `DISPONIBLE`.

## Evidencias

En `evidencias/`. La correspondencia entre la rúbrica y el código está en `evidencias/RUBRICA.md`.
