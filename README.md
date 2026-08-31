# Sistema de Asignación de Salas - Paradigma Funcional

Este repositorio contiene la implementación mínima ejecutable para el Estudio de Caso de la Evaluación 1 de la asignatura INF-223 (Programación Avanzada).

El problema se resolvió utilizando el paradigma de programación funcional en Kotlin. Nos apoyamos en funciones de orden superior, clausuras y recursividad, asegurando la inmutabilidad de los datos y evitando el uso de estados nombrados o ciclos imperativos tradicionales.

## Integrantes
* Alonso López Riquelme
* Víctor Molina Cáceres
* Mario Gutiérrez Pérez
* Diego Sáez

## Requisitos Previos
Para poder correr este proyecto en tu máquina, necesitas:
* **Java (JRE o JDK)**
* **Compilador de Kotlin por línea de comandos**

## Compilación y Ejecución
Para probar el código, abre tu terminal en la carpeta donde guardaste los archivos `Main.kt` y `Functions.kt`, y ejecuta estos comandos uno tras otro:

```bash
# 1. Compilar los archivos y generar el ejecutable .jar
kotlinc Main.kt Functions.kt -include-runtime -d sistema_salas.jar

# 2. Ejecutar el programa
java -jar sistema_salas.jar
