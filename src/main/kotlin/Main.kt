package org.example

import org.example.empleados.Empleados
import org.example.xmlrepository.Xmlrepository

fun main() {
    val xml = Xmlrepository()
    val lista = xml.leer()
    xml.escribir(lista)
    xml.actualizar("2","33333")
    xml.imprimir()
}