package org.example

import java.util.Scanner

fun main() {
    val clienteManager = ClienteManager()
    val pedidoManager = PedidoManager()
    val scanner = Scanner(System.`in`)

    while (true) {
        println("\nMENU PRINCIPAL:")
        println("1. Clientes")
        println("2. Pedidos")
        println("3. Salir")
        print("Elige una opción: ")
        when (scanner.nextInt()) {
            1 -> menuClientes(scanner, clienteManager)
            2 -> menuPedidos(scanner, pedidoManager)
            3 -> break
            else -> println("Opción no válida.")
        }
    }
}

fun menuClientes(scanner: Scanner, clienteManager: ClienteManager) {
    while (true) {
        println("\nMENU CLIENTES:")
        println("1. Agregar Cliente")
        println("2. Actualizar Cliente")
        println("3. Eliminar Cliente")
        println("4. Listar Clientes")
        println("5. Volver al menú principal")
        print("Elige una opción: ")
        when (scanner.nextInt()) {
            1 -> {
                println("ID: "); val id = scanner.nextInt()
                scanner.nextLine() // Limpiar buffer
                println("Nombre: "); val nombre = scanner.nextLine()
                println("Email: "); val email = scanner.nextLine()
                println("Teléfono: "); val telefono = scanner.nextLine()
                println("Activo (true/false): "); val activo = scanner.nextBoolean()
                clienteManager.agregarCliente(Cliente(id, nombre, email, telefono, activo))
            }
            2 -> {
                println("ID del cliente: "); val id = scanner.nextInt()
                scanner.nextLine()
                println("Nuevo nombre (o Enter para no cambiar): ")
                val nombre = scanner.nextLine().takeIf { it.isNotEmpty() }
                println("Nuevo email (o Enter para no cambiar): ")
                val email = scanner.nextLine().takeIf { it.isNotEmpty() }
                println("Nuevo teléfono (o Enter para no cambiar): ")
                val telefono = scanner.nextLine().takeIf { it.isNotEmpty() }
                println("Nuevo estado (true/false, o Enter para no cambiar): ")
                val activo = if (scanner.hasNext()) scanner.nextBoolean() else null
                clienteManager.actualizarCliente(id, nombre, email, telefono, activo)
            }
            3 -> {
                println("ID del cliente a eliminar: ")
                clienteManager.eliminarCliente(scanner.nextInt())
            }
            4 -> clienteManager.listarClientes()
            5 -> return
            else -> println("Opción no válida.")
        }
    }
}

fun menuPedidos(scanner: Scanner, pedidoManager: PedidoManager) {
    while (true) {
        println("\nMENU PEDIDOS:")
        println("1. Agregar Pedido")
        println("2. Actualizar Pedido")
        println("3. Eliminar Pedido")
        println("4. Listar Pedidos")
        println("5. Volver al menú principal")
        print("Elige una opción: ")
        when (scanner.nextInt()) {
            1 -> {
                println("ID Pedido: "); val id = scanner.nextInt()
                println("ID Cliente: "); val clienteId = scanner.nextInt()
                scanner.nextLine()
                println("Descripción: "); val descripcion = scanner.nextLine()
                println("Estado: "); val estado = scanner.nextLine()
                pedidoManager.agregarPedido(Pedido(id, clienteId, descripcion, estado))
            }
            2 -> {
                println("ID del pedido a actualizar: "); val id = scanner.nextInt()
                scanner.nextLine()
                println("Nueva descripción (o Enter para no cambiar): ")
                val descripcion = scanner.nextLine().takeIf { it.isNotEmpty() }
                println("Nuevo estado (o Enter para no cambiar): ")
                val estado = scanner.nextLine().takeIf { it.isNotEmpty() }
                pedidoManager.actualizarPedido(id, descripcion, estado)
            }
            3 -> {
                println("ID del pedido a eliminar: ")
                pedidoManager.eliminarPedido(scanner.nextInt())
            }
            4 -> pedidoManager.listarPedidos()
            5 -> return
            else -> println("Opción no válida.")
        }
    }
}
