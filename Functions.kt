package progra.ucm.functions

import progra.ucm.main.*

/*PUNTO 1 (Concepto Diferencial: Funciones de orden superior y clausuras)
* En lugar de usar estructuras de control imperativas (for/while), delegamos el
* recorrido a funciones de orden superior (filter, any) que reciben funciones como parámetro. */
fun filtrarPorCapacidad(salas: List<Sala>, asistentes: Int): List<Sala> {
    return salas.filter { sala -> sala.capacidad >= asistentes }
}

fun filtrarPorEquipamiento(salas: List<Sala>, equipoRequerido: List<String>): List<Sala> {
    return salas.filter { sala -> sala.equipamiento.containsAll(equipoRequerido) }
}

fun filtrarPorFranja(salas: List<Sala>, franjaSolicitada: Franja, asignadas: List<Asignacion>): List<Sala> {
    return salas.filter { sala ->
        val estaOcupada = asignadas.any { asig ->
            asig.sala.id == sala.id &&
                    asig.solicitud.franja.Inicio == franjaSolicitada.Inicio &&
                    asig.solicitud.franja.Fin == franjaSolicitada.Fin
        }
        !estaOcupada
    }
}

// PUNTO 2 (Concepto Diferencial: Inmutabilidad y estado no nombrado)
// La función no modifica variables globales. Recibe el estado actual y retorna un
// EstadoSistema completamente nuevo, aislando las transformaciones y evitando efectos secundarios.
fun asignar(solicitud: Solicitud, catalogo: List<Sala>, estadoActual: EstadoSistema): EstadoSistema {

    // 1. Primer filtro: Capacidad
    val conCapacidad = filtrarPorCapacidad(catalogo, solicitud.asistentes)
    if (conCapacidad.isEmpty()) {
        val rechazo = Rechazo(solicitud, "Capacidad insuficiente en el catálogo")
        return EstadoSistema(estadoActual.asignadas, estadoActual.rechazadas + rechazo)
    }

    // 2. Segundo filtro: Equipamiento
    val conEquipo = filtrarPorEquipamiento(conCapacidad, solicitud.equipamientoReq)
    if (conEquipo.isEmpty()) {
        val rechazo = Rechazo(solicitud, "Ninguna sala con esa capacidad tiene el equipo requerido")
        return EstadoSistema(estadoActual.asignadas, estadoActual.rechazadas + rechazo)
    }

    // 3. Tercer filtro: Franja horaria
    val disponibles = filtrarPorFranja(conEquipo, solicitud.franja, estadoActual.asignadas)
    val salaIdeal = disponibles.firstOrNull()

    // Si llegamos hasta acá, evaluamos si quedó alguna sala libre
    return if (salaIdeal != null) {
        val nuevaAsig = Asignacion(salaIdeal, solicitud)
        EstadoSistema(estadoActual.asignadas + nuevaAsig, estadoActual.rechazadas)
    } else {
        val rechazo = Rechazo(solicitud, "Las salas adecuadas ya están ocupadas en esa franja")
        EstadoSistema(estadoActual.asignadas, estadoActual.rechazadas + rechazo)
    }
}

// PUNTO 3 (Concepto Diferencial: Recursividad de cola en lugar de iteración)
// Para procesar el flujo secuencial sin mutar variables iteradoras, usamos recursividad,
// pasando el estado "enhebrado" a la siguiente llamada.
tailrec fun procesarFlujo(solicitudes: List<Solicitud>, catalogo: List<Sala>, estado: EstadoSistema): EstadoSistema {
    if (solicitudes.isEmpty()) return estado

    val siguienteEstado = asignar(solicitudes.first(), catalogo, estado)
    return procesarFlujo(solicitudes.drop(1), catalogo, siguienteEstado)
}