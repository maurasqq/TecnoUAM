import './Mensaje.css'

export default function Mensaje({ error = false, children }) {
  return <p className={error ? 'mensaje error' : 'mensaje'}>{children}</p>
}
