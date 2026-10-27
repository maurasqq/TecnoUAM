# Frontend — Préstamo de equipos (TecnoUAM)

Interfaz React (Vite + Axios) que consume la API protegida del backend.

## Requisitos

- Node.js 20 o superior.
- Backend en ejecución con los endpoints `/api/auth/login` y `/api/equipos`, y CORS abierto para `http://localhost:5173`.
- El puerto 5173 libre: Vite está configurado con `strictPort`, de modo que falla al arrancar en vez de cambiar de puerto y romper el CORS del backend.

## Configuración

```bash
cp .env.example .env
```

| Variable | Descripción | Valor de desarrollo |
|---|---|---|
| `VITE_API_BASE_URL` | URL base de la API | `http://localhost:8080/api` |

Si la variable no está definida se usa `http://localhost:8080/api`.

## Ejecución

```bash
npm install
npm run dev
```

La aplicación queda en `http://localhost:5173`.

Para compilar la versión de producción: `npm run build`.

## Uso

1. Iniciar sesión con las cuentas de prueba del backend: `admin` (rol ADMIN) u `operador` (rol USER). Sus contraseñas las define el backend con las variables `DEMO_ADMIN_PASSWORD` y `DEMO_USER_PASSWORD`.
2. El token JWT se guarda en `sessionStorage` y Axios lo envía en la cabecera `Authorization: Bearer <token>` de cada petición.
3. La tabla muestra código patrimonial, nombre, tipo y estado de los equipos reales devueltos por `GET /api/equipos`.
4. El botón **Recargar** y el refresco de la página vuelven a consultar la API, de modo que los cambios hechos desde Postman se reflejan en la tabla.
5. **Cerrar sesión** borra el token de la sesión del navegador.

## Estados visibles

| Estado | Cuándo aparece |
|---|---|
| Cargando | Mientras está pendiente la petición a `/api/equipos` |
| Lista vacía | La API responde `200` sin equipos |
| Error | Sin conexión con la API, token inválido o expirado (`401`), permiso insuficiente (`403`) u otro error del servidor |

Ante un `401` se cierra la sesión y se vuelve al formulario de inicio de sesión.

## Estructura

```text
src/
  api/axiosClient.js    instancia de Axios y cabecera Authorization
  api/sesion.js         token y rol en sessionStorage
  api/mensajeError.js   traducción de errores HTTP a mensajes
  components/LoginForm.jsx
  components/TablaEquipos.jsx
  components/Mensaje.jsx
  App.jsx
  App.css
```

No se usan datos simulados: toda la información proviene de la API.
