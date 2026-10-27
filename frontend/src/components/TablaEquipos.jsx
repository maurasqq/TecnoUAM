import { useCallback, useEffect, useState } from 'react'
import axiosClient from '../api/axiosClient.js'
import { mensajeError } from '../api/mensajeError.js'
import Mensaje from './Mensaje.jsx'
import './TablaEquipos.css'

export default function TablaEquipos({ onSesionInvalida }) {
  const [equipos, setEquipos] = useState([])
  const [cargando, setCargando] = useState(true)
  const [error, setError] = useState(null)

  const cargarEquipos = useCallback(async () => {
    setCargando(true)
    setError(null)
    try {
      const { data } = await axiosClient.get('/equipos')
      setEquipos(Array.isArray(data) ? data : [])
    } catch (problema) {
      setEquipos([])
      setError(mensajeError(problema, 'No se pudieron obtener los equipos.'))
      if (problema.response?.status === 401) {
        onSesionInvalida()
      }
    } finally {
      setCargando(false)
    }
  }, [onSesionInvalida])

  useEffect(() => {
    cargarEquipos()
  }, [cargarEquipos])

  return (
    <section>
      <div className="encabezado">
        <h2>Equipos</h2>
        <button type="button" onClick={cargarEquipos} disabled={cargando}>
          Recargar
        </button>
      </div>

      {cargando && <Mensaje>Cargando equipos…</Mensaje>}
      {!cargando && error && <Mensaje error>{error}</Mensaje>}
      {!cargando && !error && equipos.length === 0 && (
        <Mensaje>No hay equipos registrados.</Mensaje>
      )}
      {!cargando && !error && equipos.length > 0 && (
        <table className="tabla">
          <thead>
            <tr>
              <th scope="col">Código patrimonial</th>
              <th scope="col">Nombre</th>
              <th scope="col">Tipo</th>
              <th scope="col">Estado</th>
            </tr>
          </thead>
          <tbody>
            {equipos.map((equipo) => (
              <tr key={equipo.id}>
                <td>{equipo.codigoPatrimonial}</td>
                <td>{equipo.nombre}</td>
                <td>{equipo.tipo}</td>
                <td>{equipo.estado}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </section>
  )
}
