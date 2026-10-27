import axios from 'axios'

const CLAVE_TOKEN = 'tecnouam_token'

export function leerToken() {
  return sessionStorage.getItem(CLAVE_TOKEN)
}

export function guardarToken(token) {
  sessionStorage.setItem(CLAVE_TOKEN, token)
}

export function borrarToken() {
  sessionStorage.removeItem(CLAVE_TOKEN)
}

const axiosClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080/api'
})

axiosClient.interceptors.request.use((config) => {
  const token = leerToken()
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

export default axiosClient
