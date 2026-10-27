# Evidencias

Capturas y resultados de la ejecución real del sistema: PostgreSQL con las migraciones aplicadas desde cero, backend Spring Boot en el puerto 8080 y React en el 5173.

## Interfaz React — `react/`

| Captura | Qué demuestra |
|---|---|
| `01-inicio-sesion.jpg` | Formulario de inicio de sesión |
| `02-tabla-datos-reales.jpg` | Tabla con los cuatro equipos de la base, con código patrimonial, nombre, tipo y estado |
| `03-equipo-creado-desde-la-api.jpg` | Tras crear `PAT-005` fuera de React, al recargar aparece en la tabla |
| `04-equipo-prestado.jpg` | Tras registrar el préstamo, `PAT-005` pasa a `PRESTADO` |
| `05-equipo-devuelto.jpg` | Tras la devolución, `PAT-005` vuelve a `DISPONIBLE` |
| `06-lista-vacia.jpg` | Estado de lista vacía cuando la API no devuelve equipos |
| `07-error-sin-api.jpg` | Estado de error con el backend detenido |

Las capturas 3, 4 y 5 son la prueba de integración que pide el enunciado: la operación se hace fuera de React y la tabla refleja el dato real al recargar. Ningún dato está simulado.

## API — `api/`

`pruebas-api.txt` recoge la ejecución de los 27 casos del examen contra la base recién creada: autenticación, los cinco casos de seguridad del enunciado, el CRUD de equipos, el ciclo de préstamo y devolución, y las tres variantes de borrado.

Los mismos casos están en la colección de Postman, en `postman/TecnoUAM.postman_collection.json`.

## Cómo reproducirlo

Los pasos están en el `README.md` de la raíz. La base se puede borrar y recrear: Flyway vuelve a aplicar `V1` a `V4` y deja los mismos datos de partida.
