export function mensajeError(error, textoPorDefecto) {
  if (!error.response) {
    return 'No se pudo conectar con la API. Verifica que el backend esté en ejecución.'
  }
  const { status, data } = error.response
  if (status === 401) {
    return 'Credenciales o token inválidos.'
  }
  if (status === 403) {
    return 'El rol actual no tiene permiso para esta operación.'
  }
  return data?.message ?? textoPorDefecto
}
