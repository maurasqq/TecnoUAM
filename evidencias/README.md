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

## Colección de Postman — `postman/`

`newman-salida.txt` es la ejecución de la colección `postman/TecnoUAM.postman_collection.json` con Newman, el ejecutor oficial de Postman. Muestra cada petición con su método, su URL, su código HTTP y el resultado de cada comprobación.

Resultado: 22 peticiones, 25 comprobaciones, 0 fallos, contra una base recreada desde cero.

Cubre los nueve escenarios del Reto 4: alta de equipo, consulta, actualización, registro de préstamo, intento de prestar dos veces, devolución y recuperación del estado, borrado permitido y borrado bloqueado, y las respuestas 401 y 403.

El informe en JSON que Newman puede generar no se guarda aquí: incluye las contraseñas y los tokens JWT completos de la ejecución, y el enunciado prohíbe exportarlos. La salida de texto no contiene ninguno.

Para la defensa, la colección se abre en Postman y se ejecuta con el Collection Runner: las capturas de esa pantalla se añaden a esta carpeta.

## Comprobación adicional — `api/`

`pruebas-api.txt` es una segunda pasada de los mismos casos, hecha con peticiones directas, útil como comprobación rápida y reproducible desde la terminal.

## Cómo reproducirlo

Los pasos están en el `README.md` de la raíz. La base se puede borrar y recrear: Flyway vuelve a aplicar `V1` a `V4` y deja los mismos datos de partida.
