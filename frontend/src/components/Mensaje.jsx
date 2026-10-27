import './Mensaje.css'

export default function Mensaje({ error = false, children }) {
  return (
    <p role="status" className={error ? 'mensaje error' : 'mensaje'}>
      {children}
    </p>
  )
}
