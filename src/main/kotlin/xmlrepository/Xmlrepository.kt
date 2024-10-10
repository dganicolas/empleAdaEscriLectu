package org.example.xmlrepository

import org.example.empleados.Empleados
import org.w3c.dom.*
import java.nio.file.Files
import java.nio.file.Path
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.Source
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

class Xmlrepository {

    fun leer(): MutableList<Empleados> {
        val lista = mutableListOf<Empleados>()
        val archivo = Path.of("src/main/resources/empleados.csv")
        val br = Files.newBufferedReader(archivo)
        br.forEachLine {
            val linea = it.split(",")
            if(linea[0] != "ID"){
                lista.add(Empleados(linea[0], linea[1], linea[2], linea[3]))
            }
        }
        br.close()
        return lista
    }

    fun escribir(lista: MutableList<Empleados>) {
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val imp: DOMImplementation = builder.domImplementation
        val document: Document = imp.createDocument(null, "productos", null)
        lista.forEach {
            val empleado: Element = document.createElement("empleado")
            empleado.setAttribute("id", it.id)
            document.documentElement.appendChild(empleado)
            val apellidoProducto = document.createElement("Apellido")
            val textoApellido: Text = document.createTextNode(it.apellido)

            val departamentoProducto = document.createElement("Departamento")
            val textodepartamento: Text = document.createTextNode(it.departamento)

            val salarioProducto = document.createElement("Salario")
            val textosalario: Text = document.createTextNode(it.salario)

            apellidoProducto.appendChild(textoApellido)
            departamentoProducto.appendChild(textodepartamento)
            salarioProducto.appendChild(textosalario)
            empleado.appendChild(apellidoProducto)
            empleado.appendChild(departamentoProducto)
            empleado.appendChild(salarioProducto)
        }
        val source: Source = DOMSource(document)

        val result = StreamResult(Path.of("src/main/resources/empleadosEscrito.xml").toFile())

        val transforme = TransformerFactory.newInstance().newTransformer()

        transforme.setOutputProperty(OutputKeys.INDENT, "yes")

        transforme.transform(source, result)
    }

    fun leerXml():MutableList<Empleados>{
        val listaXml = mutableListOf<Empleados>()
        val bdf = DocumentBuilderFactory.newInstance()
        val db = bdf.newDocumentBuilder()
        val archivo = Path.of("src/main/resources/empleadosEscrito.xml").toFile()
        val documento = db.parse(archivo)
        val raiz = documento.documentElement
        raiz.normalize()
        val listaNodos = raiz.getElementsByTagName("empleado")
        for (i in 0..<listaNodos.length){
            val nodo = listaNodos.item(i)
            if(nodo.nodeType == Node.ELEMENT_NODE){
                val nodoelemento = nodo as Element
                val elementoid = nodoelemento.getAttribute("id")
                val elementoApellido = nodoelemento.getElementsByTagName("Apellido")
                val elementoDepartamento = nodoelemento.getElementsByTagName("Departamento")
                val elementoSalario = nodoelemento.getElementsByTagName("Salario")
                val textContentApellido = elementoApellido.item(0).textContent
                val textContentDepartamento = elementoDepartamento.item(0).textContent
                val textContentSalario = elementoSalario.item(0).textContent
                listaXml.add(Empleados(elementoid,textContentApellido,textContentDepartamento,textContentSalario))
            }

        }
        return listaXml
    }
    fun actualizar(id: String, salario: String) {
        val lista = leerXml()
        val señor = lista.find { it.id == id }
        if(señor != null){
            lista.remove(señor)
            señor.salario=salario
            lista.add(señor)
            lista.sortBy { it.id.toInt() }
            escribir(lista)
        }


    }

    fun imprimir() {
        leerXml().forEach {
            println("empleado: ${it.apellido}, ID: ${it.id}, Salario: ${it.salario}, Departamento: ${it.departamento}")
        }
    }
}