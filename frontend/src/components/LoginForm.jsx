import { useState } from 'react'
import axiosClient from '../api/axiosClient.js'
import { mensajeError } from '../api/mensajeError.js'
import Mensaje from './Mensaje.jsx'

export default function LoginForm({ onAutenticado }) {
  const [usuario, setUsuario] = useState('')
  const [contrasena, setContrasena] = useState('')
  const [cargando, setCargando] = useState(false)
  const [error, setError] = useState(null)

  async function enviar(evento) {
    evento.preventDefault()
    setCargando(true)
    setError(null)
    try {
      const { data } = await axiosClient.post('/auth/login', { usuario, contrasena })
      onAutenticado({ token: data.token, rol: data.rol })
    } catch (problema) {
      setError(mensajeError(problema, 'No se pudo iniciar sesión.'))
    } finally {
      setCargando(false)
    }
  }

  return (
    <form className="formulario" onSubmit={enviar}>
      <h2>Iniciar sesión</h2>
      <label htmlFor="usuario">Usuario</label>
      <input
        id="usuario"
        type="text"
        autoComplete="username"
        value={usuario}
        onChange={(evento) => setUsuario(evento.target.value)}
        required
      />
      <label htmlFor="contrasena">Contraseña</label>
      <input
        id="contrasena"
        type="password"
        autoComplete="current-password"
        value={contrasena}
        onChange={(evento) => setContrasena(evento.target.value)}
        required
      />
      <button type="submit" disabled={cargando}>
        {cargando ? 'Entrando…' : 'Entrar'}
      </button>
      {error && <Mensaje tipo="error">{error}</Mensaje>}
    </form>
  )
}
