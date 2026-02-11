#  Entrega Examen: Registro de Devolución y Reporte de Daños

**Estudiante:** Joan Jaimes
**Funcionalidad:** Gestión de devoluciones con y sin daños.

---

##  1. Nuevo Endpoint Implementado
Este endpoint permite al **Proveedor** finalizar una reserva y, opcionalmente, registrar un reporte de daños con costo asociado.

- **Método:** `POST`
- **URL:** `http://localhost:8080/api/v1/bookings/{ID_RESERVA}/registrar-devolucion`
- **Auth:** Requiere Token Bearer (Rol: PROVIDER o ADMIN).

---

## 2. Ejemplos de Prueba (JSON Body)

### Escenario A: Devolución con Daños (Crea reporte en BD)
```json
{
    "estado": "DAÑOS",
    "descripcionDano": "El motor presenta fallas y carcasa rota",
    "costoReparacion": 120000.0
}