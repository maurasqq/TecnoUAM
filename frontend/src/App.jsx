import { useState } from 'react'
import LoginForm from './components/LoginForm.jsx'
import { borrarSesion, guardarSesion, leerSesion } from './api/sesion.js'

export default function App() {
  const [sesion, setSesion] = useState(leerSesion)

  function autenticar(nuevaSesion) {
    guardarSesion(nuevaSesion)
    setSesion(nuevaSesion)
  }

  function cerrarSesion() {
    borrarSesion()
    setSesion(null)
  }

  return (
    <main className="contenedor">
      <h1>Préstamo de equipos — TecnoUAM</h1>
      {!sesion && <LoginForm onAutenticado={autenticar} />}
      {sesion && (
        <div className="barra">
          <span>Sesión activa{sesion.rol ? ` · rol ${sesion.rol}` : ''}</span>
          <button type="button" onClick={cerrarSesion}>
            Cerrar sesión
          </button>
        </div>
      )}
    </main>
  )
}
