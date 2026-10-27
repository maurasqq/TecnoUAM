const CLAVE_TOKEN = 'tecnouam_token'
const CLAVE_ROL = 'tecnouam_rol'

export function leerSesion() {
  const token = sessionStorage.getItem(CLAVE_TOKEN)
  if (!token) {
    return null
  }
  return { token, rol: sessionStorage.getItem(CLAVE_ROL) }
}

export function guardarSesion({ token, rol }) {
  sessionStorage.setItem(CLAVE_TOKEN, token)
  sessionStorage.setItem(CLAVE_ROL, rol ?? '')
}

export function borrarSesion() {
  sessionStorage.removeItem(CLAVE_TOKEN)
  sessionStorage.removeItem(CLAVE_ROL)
}
