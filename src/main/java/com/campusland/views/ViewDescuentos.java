package com.campusland.views;

import java.util.List;

import com.campusland.exceptiones.descuentoexceptions.DescuentoNullException;
import com.campusland.respository.models.Cliente;
import com.campusland.respository.models.Descuentos;
import com.campusland.respository.models.Producto;

public class ViewDescuentos extends ViewMain {

    public static void startMenu() throws DescuentoNullException {

        int op = 0;

        do {

            op = mostrarMenu();
            switch (op) {
                case 1:
                    crearDescuento();
                    break;
                case 2:
                    listarDescuentos();
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }

        } while (op >= 1 && op < 9);

    }

    public static int mostrarMenu() {
        System.out.println("----Menu--Factura----");
        System.out.println("1. Crear descuento.");
        System.out.println("2. Listar descuento.");
        System.out.println("3. Salir ");
        return leer.nextInt();
    }

    public static void crearDescuento() throws DescuentoNullException {
        leer.nextLine();

        List<Descuentos> descuentos = serviceDescuento.listar();
        for (Descuentos descuento : descuentos) {
            System.out.println(descuento.getId() + ". " + descuento.getTipoDescuento());
        }

        System.out.println("Seleccione el tipo de descuento: ");
        int idDescuentoSeleccionado = leer.nextInt();
        leer.nextLine();

        Descuentos descuentoSeleccionado = null;
        for (Descuentos descuento : descuentos) {
            if (descuento.getId() == idDescuentoSeleccionado) {
                descuentoSeleccionado = descuento;
                break;
            }
        }

        if (descuentoSeleccionado != null) {
            System.out.println("Descuento seleccionado: " + descuentoSeleccionado.getTipoDescuento());
        } else {
            System.out.println("Descuento no encontrado");
        }

        System.out.println("Escriba la condicion: ");
        String condiciones = leer.nextLine();

        Float porcentaje = null;
        while (porcentaje == null) {
            System.out.println("Digite el porcentaje del descuento en puntos (Ejemplo: 25% = 0.25)");
            try {
                String porcentajeStr = leer.nextLine();
                porcentaje = Float.parseFloat(porcentajeStr);
            } catch (NumberFormatException e) {
                System.out.println("Porcentaje inválido. Intente nuevamente.");
            }
        }

        System.out.println("Productos disponibles:");
        List<Producto> productos = serviceProducto.listar();
        for (Producto producto : productos) {
            System.out.println(producto.getCodigo() + ". " + producto.getNombre());
        }

        System.out.println("Seleccione el código del producto al que aplicará el descuento: ");
        int codigoProducto = leer.nextInt();

        // Obtener producto seleccionado
        Producto productoSeleccionado = null;
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigoProducto) {
                productoSeleccionado = producto;
                break;
            }
        }

        if (productoSeleccionado != null) {
            System.out.println("Producto seleccionado: " + productoSeleccionado.getNombre());
        } else {
            System.out.println("Producto no encontrado");
        }

        // Obtener clientes
        List<Cliente> clientes = serviceCliente.listar();
        for (Cliente cliente : clientes) {
            System.out.println(cliente.getId() + ". " + cliente.getNombre());
        }

        System.out.println("Seleccione el ID del cliente al que aplicará el descuento: ");
        int idCliente = leer.nextInt();

        // Obtener cliente seleccionado
        Cliente clienteSeleccionado = null;
        for (Cliente cliente : clientes) {
            if (cliente.getId() == idCliente) {
                clienteSeleccionado = cliente;
                break;
            }
        }

        if (clienteSeleccionado != null) {
            System.out.println("Cliente seleccionado: " + clienteSeleccionado.getNombre());
        } else {
            System.out.println("Cliente no encontrado");
        }

        Descuentos descuento = new Descuentos();
        descuento.setTipoDescuento(descuentoSeleccionado.getTipoDescuento());
        descuento.setCondiciones(condiciones);
        descuento.setPorcentaje(porcentaje);
        descuento.setProductos(productoSeleccionado);
        descuento.setClientes(clienteSeleccionado);
        descuento.setEstado("Activo");
        serviceDescuento.crear(descuento);

        try {
            serviceDescuento.crear(descuento);
            System.out.println("Descuento creado exitosamente.");
        } catch (DescuentoNullException e) {
            System.out.println("Error al crear el descuento: " + e.getMessage());
        }

    }

    public static void listarDescuentos() {
        List<Descuentos> descuentos = serviceDescuento.listar();

        for (Descuentos descuento : descuentos) {
            System.out.println("ID: " + descuento.getId());
            System.out.println("Tipo de descuento: " + descuento.getTipoDescuento());
            System.out.println("Condiciones: " + descuento.getCondiciones());
            System.out.println("Porcentaje: " + descuento.getPorcentaje() + "%");
            System.out.println("Estado: " + descuento.getEstado());
            if (descuento.getProductos() != null) {
                System.out.println("Producto: " + descuento.getProductos().getNombre());
            } else {
                System.out.println("Producto: No asignado");
            }
            if (descuento.getClientes() != null) {
                System.out.println("Cliente: " + descuento.getClientes().getNombre());
            } else {
                System.out.println("Cliente: No asignado");
            }
            System.out.println("-----------------------");
        }

    }

}
