package org.example.xmlrepository

import org.example.empleados.Empleados
import org.w3c.dom.Element
import java.io.BufferedReader
import java.nio.file.Files
import java.nio.file.Path
import javax.xml.parsers.DocumentBuilderFactory

class Xmlrepository {
    val lista = mutableListOf<Empleados>()
    fun leer() {
        val archivo = Path.of("src/main/resources/empleados.csv")
        val br = Files.newBufferedReader(archivo)
        br.forEachLine {
            val Linea = it.split(",")
            lista.add(Empleados(Linea[0],Linea[1],Linea[2],Linea[3]))
        }
        br.close()
        println(lista)
    }

    fun escribir() {

    }

    fun actualizar(id: String, salario: String) {
        val bdf = DocumentBuilderFactory.newInstance()
        val db = bdf.newDocumentBuilder()
        val archivo = Path.of("src/main/resources/empleadosEscrito.csv").toFile()
        val documento = db.parse(archivo)
        val raiz = documento.documentElement
        raiz.normalize()
        val listaNodos = raiz.getElementsByTagName()
    }

    fun imprimir() {

    }
}