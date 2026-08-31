package progra.ucm.main

import progra.ucm.functions.*

class Sala(val id: Int, val capacidad: Int, val equipamiento: List<String>)
class Franja(val Inicio: Int, val Fin: Int)
class Solicitud(val id: Int, val franja: Franja, val asistentes: Int, val equipamientoReq: List<String>)
class Asignacion(val sala: Sala, val solicitud: Solicitud)
class Rechazo(val solicitud: Solicitud, val motivo: String)
class EstadoSistema(val asignadas: List<Asignacion>, val rechazadas: List<Rechazo>)

fun main() {
    println("--Sistema de Asignacion de Salas--")

    val catalogo = listOf(
        Sala(1, 30, listOf("Proyector", "Pizarra")),
        Sala(2, 15, listOf("Pizarra")),
        Sala(3, 50, listOf("Proyector", "Pizarra", "Audio")),
        Sala(4, 20, listOf("Pizarra interactiva")),
        Sala(5, 40, listOf("Proyector", "Pizarra", "Computador")),
        Sala(6, 10, listOf("Pizarra")),
        Sala(7, 25, listOf("Proyector", "Pizarra")),
        Sala(8, 60, listOf("Proyector", "Pizarra", "Audio", "Micrófono")),
        Sala(9, 35, listOf("Pizarra", "Computador")),
        Sala(10, 15, listOf("Proyector", "Pizarra")),
        Sala(11, 45, listOf("Proyector", "Pizarra", "Laboratorio")),
        Sala(12, 12, listOf("Pizarra")),
        Sala(13, 80, listOf("Proyector", "Pizarra", "Audio", "Computador", "Micrófono")),
        Sala(14, 25, listOf("Proyector", "Pizarra")),
        Sala(15, 30, listOf("Proyector", "Pizarra", "Computador"))
    )

    val solicitudes = listOf(
        Solicitud(1, Franja(8, 10), 25, listOf("Proyector")),
        Solicitud(2, Franja(8, 10), 45, listOf("Proyector", "Computador")),
        Solicitud(3, Franja(10, 12), 12, listOf("Pizarra")),
        Solicitud(4, Franja(8, 10), 60, listOf("Audio", "Micrófono")),
        Solicitud(5, Franja(14, 16), 20, listOf("Pizarra interactiva")),
        Solicitud(6, Franja(10, 12), 30, listOf("Proyector", "Pizarra")),
        Solicitud(7, Franja(16, 18), 10, listOf("Pizarra")),
        Solicitud(8, Franja(14, 16), 80, listOf("Proyector", "Audio")),
        Solicitud(9, Franja(8, 10), 35, listOf("Computador")),
        Solicitud(10, Franja(12, 14), 15, listOf("Proyector")),
        Solicitud(11, Franja(16, 18), 40, listOf("Laboratorio")),
        Solicitud(12, Franja(10, 12), 25, listOf("Proyector", "Pizarra")),
        Solicitud(13, Franja(14, 16), 50, listOf("Audio", "Computador")),
        Solicitud(14, Franja(12, 14), 20, listOf("Pizarra")),
        Solicitud(15, Franja(8, 10), 100, listOf("Proyector"))
    )

    val estadoInicial = EstadoSistema(emptyList(), emptyList())
    val estadoFinal = procesarFlujo(solicitudes, catalogo, estadoInicial)

    println("\n--- ACEPTADAS ---")
    estadoFinal.asignadas.forEach {
        println("Sol ${it.solicitud.id} -> Sala ${it.sala.id} (Franja: ${it.solicitud.franja.Inicio}-${it.solicitud.franja.Fin})")
    }

    println("\n--- RECHAZADAS ---")
    estadoFinal.rechazadas.forEach {
        println("Sol ${it.solicitud.id} -> Motivo: ${it.motivo}")
    }
}