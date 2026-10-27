export default function Mensaje({ tipo = 'aviso', children }) {
  return <p className={`mensaje mensaje--${tipo}`}>{children}</p>
}
