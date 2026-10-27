# Pull requests preparados

Cada enlace abre GitHub con el titulo y la descripcion ya rellenados. Solo hay que pulsar Create pull request.

Fusionar en este orden, porque cada etapa se apoya en la anterior.

## 1. Etapa 2: CRUD, servicios y reglas de negocio

Rama `feature/logica-negocio`

https://github.com/maurasqq/TecnoUAM/compare/main...feature/logica-negocio?quick_pull=1&title=Etapa+2%3A+CRUD%2C+servicios+y+reglas+de+negocio&body=Implementa+la+capa+REST+sobre+el+modelo+JPA+de+la+etapa+1.%0A%0A-+DTOs+de+solicitud+y+respuesta+para+equipos+y+prestamos%2C+con+validaciones.%0A-+Servicios+con+las+reglas+de+disponibilidad%3A+no+prestar+dos+veces%2C+liberar+al+devolver%2C+no+eliminar+con+prestamo+activo.%0A-+Controladores+con+los+ocho+endpoints+del+enunciado+y+sus+codigos+HTTP.%0A-+Manejador+global+de+excepciones.%0A%0ACubre+el+criterio+1+de+la+rubrica.

## 2. Etapa 3: Spring Security, JWT y roles

Rama `etapa3-Alexander_Seguridad`

https://github.com/maurasqq/TecnoUAM/compare/main...etapa3-Alexander_Seguridad?quick_pull=1&title=Etapa+3%3A+Spring+Security%2C+JWT+y+roles&body=Protege+la+API+por+completo.%0A%0A-+POST+%2Fapi%2Fauth%2Flogin+con+JWT+firmado%3B+la+clave+se+lee+de+JWT_SECRET_BASE64.%0A-+Seguridad+sin+sesiones%2C+con+filtro+de+validacion+del+token.%0A-+Permisos+por+rol+en+la+cadena+de+filtros%3A+GET+para+USER+y+ADMIN%2C+escritura+solo+ADMIN.%0A-+Respuestas+401+y+403+con+manejadores+propios.%0A-+CORS+para+http%3A%2F%2Flocalhost%3A5173.%0A-+Tabla+de+usuarios+y+cuentas+de+demostracion+con+contrasenas+desde+el+entorno.%0A%0AIncluye+el+trabajo+del+quinto+integrante.+Cubre+el+criterio+2+de+la+rubrica.

## 3. Etapa 4: interfaz React con Axios

Rama `etapa-4-davide`

https://github.com/maurasqq/TecnoUAM/compare/main...etapa-4-davide?quick_pull=1&title=Etapa+4%3A+interfaz+React+con+Axios&body=Interfaz+React+que+consume+la+API+protegida.%0A%0A-+Cliente+Axios+con+baseURL+configurable+e+interceptor+que+anade+Authorization%3A+Bearer.%0A-+Formulario+de+inicio+de+sesion%3B+el+token+se+guarda+en+sessionStorage.%0A-+Tabla+de+equipos+con+codigo+patrimonial%2C+nombre%2C+tipo+y+estado.%0A-+Estados+de+carga%2C+lista+vacia+y+error%2C+con+recarga+manual.%0A-+Sin+datos+simulados%3A+todo+viene+de+la+API.%0A%0ACubre+el+criterio+3+de+la+rubrica.

## 4. Etapa 5: integracion, correcciones y evidencias

Rama `etapa-5-integracion`

https://github.com/maurasqq/TecnoUAM/compare/main...etapa-5-integracion?quick_pull=1&title=Etapa+5%3A+integracion%2C+correcciones+y+evidencias&body=Fusiona+las+cuatro+etapas+y+resuelve+lo+que+faltaba+para+la+rubrica.%0A%0A%2A%2AQue+impedia+que+funcionara%2A%2A%0A%0A-+El+proyecto+no+compilaba+al+fusionar%3A+spring-boot-starter-validation+declarado+dos+veces+en+el+pom%2C+y+un+comando+de+shell+pegado+dentro+de+PrestamoService.java.%0A-+DELETE+%2Fapi%2Fequipos%2F%7Bid%7D+borraba+la+fila+fisicamente.+Un+equipo+con+prestamos+ya+devueltos+violaba+la+clave+foranea+y+devolvia+500+en+vez+de+204.%0A-+La+contrasena+de+PostgreSQL+estaba+escrita+en+application.properties+y+subida+al+repositorio.%0A-+No+habia+datos+de+demostracion%2C+ni+coleccion+de+pruebas%2C+ni+README%2C+ni+evidencias.%0A%0A%2A%2AComo+queda%2A%2A%0A%0A-+Compila+y+empaqueta.+Backend+y+frontend+verificados+en+ejecucion+real.%0A-+Baja+logica+de+equipos%3A+DELETE+marca+eliminado+y+responde+204%2C+las+consultas+filtran+los+eliminados+y+el+historial+de+prestamos+se+conserva.+Migracion+V3.%0A-+Credenciales+de+base+de+datos+desde+el+entorno%2C+con+.env.example+sin+valores.%0A-+Migracion+V4+con+cuatro+equipos+y+un+prestamo+activo%2C+para+que+los+casos+de+409+sean+reproducibles+desde+cero.%0A-+Coleccion+de+Postman+con+22+peticiones+que+cubren+los+nueve+escenarios+del+Reto+4%2C+mas+la+misma+coleccion+en+formato+Bruno.%0A-+README+con+requisitos%2C+variables%2C+arranque%2C+cuentas+de+prueba%2C+endpoints+y+pasos+de+la+demostracion.%0A-+evidencias%2F+con+las+capturas+de+React%2C+la+ejecucion+de+la+coleccion+y+la+matriz+de+cumplimiento+de+la+rubrica.%0A%0A%2A%2AVerificado+en+ejecucion%2A%2A%0A%0ABase+recreada+desde+cero%2C+Flyway+aplicando+V1+a+V4%2C+backend+en+el+8080+y+React+en+el+5173.+La+coleccion+pasa+entera%3A+22+peticiones%2C+25+comprobaciones%2C+0+fallos.+Las+capturas+de+React+demuestran+la+secuencia+de+operar+desde+fuera+y+ver+el+dato+real+al+recargar.%0A%0A%2A%2APendiente%2A%2A%0A%0ALa+contrasena+de+PostgreSQL+que+estuvo+publicada+sigue+en+el+historial+de+los+commits+anteriores%3A+hay+que+cambiarla+en+el+servidor.
