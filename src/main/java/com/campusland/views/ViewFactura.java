package com.campusland.views;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.campusland.exceptiones.facturaexceptions.FacturaExceptionInsertDataBase;
import com.campusland.exceptiones.impuestoexceptions.ImpuestoExceptionInsertDataBase;
import com.campusland.exceptiones.productoexceptions.ProductoNullException;
import com.campusland.respository.models.Cliente;
import com.campusland.respository.models.Factura;
import com.campusland.respository.models.Impuesto;
import com.campusland.respository.models.ItemFactura;
import com.campusland.respository.models.Producto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ViewFactura extends ViewMain {

    public static void startMenu() throws ImpuestoExceptionInsertDataBase, ProductoNullException {

        int op = 0;

        do {

            op = mostrarMenu();
            switch (op) {
                case 1:
                    crearFactura();
                    break;
                case 2:
                    listarFactura();
                    break;
                case 3:
                    generarArchivoDIANPorAnio();
                    break;
                case 4:
                    listarInformeTotal();
                    break;
                case 5:
                    listarClientesPorCompra();
                    break;  
                case 6:
                    listarProductoMasVendido();
                    break;      
                default:
                    System.out.println("Opcion no valida");
                    break;
            }

        } while (op >= 1 && op < 7);

    }

    public static int mostrarMenu() {
        System.out.println("----Menu--Factura----");
        System.out.println("1. Crear factura.");
        System.out.println("2. Listar factura.");
        System.out.println("3. Generar Archivo DIAN por año");
        System.out.println("4. Informe total de ventas, descuentos e impuestos");
        System.out.println("5. Listado descendente clientes por compra");
        System.out.println("6. Listado descendente producto mas vendido");
        System.out.println("7. Salir ");
        return leer.nextInt();
    }

    public static void listarFactura() {
        System.out.println("Lista de Facturas");
        for (Factura factura : serviceFactura.listar()) {
            factura.display();
            System.out.println();
        }
    }

    public static void crearFactura() {
        System.out.println("-- Creación de Factura ---");

        Cliente cliente;
        do {
            cliente = ViewCliente.buscarGetCliente();
        } while (cliente == null);

        Factura factura = new Factura(LocalDateTime.now(), cliente);
        System.out.println("-- Se creó la factura -----------------");
        System.out.println("-- Seleccione los productos a comprar por código");

        List<ItemFactura> items = new ArrayList<>();
        do {
            Producto producto = ViewProducto.buscarGetProducto();

            if (producto != null) {
                System.out.print("Cantidad: ");
                int cantidad = leer.nextInt();
                ItemFactura item = new ItemFactura(cantidad, producto);
                items.add(item);

                System.out.println("Agregar otro producto: si o no");
                String otroItem = leer.next();
                if (!otroItem.equalsIgnoreCase("si")) {
                    break;
                }
            }

        } while (true);

        factura.setItems(items);
        double totalIva = factura.getTotalIva();
        Impuesto impuesto = new Impuesto(Year.now(), totalIva, cliente, factura);

        try {
            serviceImpuesto.crear(impuesto);
            serviceFactura.crear(factura);
            System.out.println("Se creó la factura");
            factura.display();
        } catch (FacturaExceptionInsertDataBase e) {
            System.out.println(e.getMessage());
        } catch (ImpuestoExceptionInsertDataBase e) {
            System.out.println("Error al crear el impuesto: " + e.getMessage());
        }
    }

    public static void generarArchivoDIANPorAnio() throws ImpuestoExceptionInsertDataBase {
        System.out.println("Ingrese el año para el cual desea generar el archivo DIAN:");
        int year = leer.nextInt();

        List<Impuesto> impuestos = serviceImpuesto.listarPorAnio(null);
        double totalImpuesto = impuestos.stream().mapToDouble(Impuesto::getValor).sum();

        File file = new File("impuestos_" + year + ".json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            mapper.writeValue(file, impuestos);
            System.out.println(
                    "Se generó el archivo DIAN para el año " + year + " con un total de impuesto de $" + totalImpuesto);
        } catch (IOException e) {
            System.out.println("Error al generar el archivo DIAN: " + e.getMessage());
        }
    }

    public static void listarClientesPorCompra() {
        System.out.println("-- Listado de Clientes por Compra --");
        List<Cliente> clientes = serviceCliente.listar();
        Map<Cliente, Double> totalComprasPorCliente = new HashMap<>();
    
        for (Cliente cliente : clientes) {
            double totalCompras = 0;
            List<Factura> facturasCliente = serviceFactura.listar().stream()
                    .filter(factura -> factura.getCliente().getId() == cliente.getId())
                    .collect(Collectors.toList());
            for (Factura factura : facturasCliente) {
                totalCompras += factura.getTotal();
            }
            totalComprasPorCliente.put(cliente, totalCompras);
        }

        List<Map.Entry<Cliente, Double>> listaClientesOrdenada = totalComprasPorCliente.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());

        for (Map.Entry<Cliente, Double> entry : listaClientesOrdenada) {
            Cliente cliente = entry.getKey();
            double totalCompras = entry.getValue();
            System.out.println(cliente.getNombre() + " - Total de Compras: " + totalCompras);
        }
    }

    public static void listarInformeTotal() {
        System.out.println("-- Informe Total de Ventas, Descuentos e Impuestos --");
    
        double totalVentas = 0;
        double totalDescuentos = 0;
        double totalImpuestos = 0;
    
        List<Factura> facturas = serviceFactura.listar();
        for (Factura factura : facturas) {
            totalVentas += factura.getTotalSinIva();
            List<Impuesto> impuestos = serviceImpuesto.listar().stream()
                    .filter(impuesto -> impuesto.getFactura().getNumeroFactura() == factura.getNumeroFactura())
                    .collect(Collectors.toList());
            for (Impuesto impuesto : impuestos) {
                totalImpuestos += impuesto.getValor();
            }
        }
    
        System.out.println("Total Ventas: " + totalVentas);
        System.out.println("Total Descuentos: " + totalDescuentos);
        System.out.println("Total Impuestos: " + totalImpuestos);
    }

    public static void listarProductoMasVendido() throws ProductoNullException {
        System.out.println("-- Listado Descendente Producto Más Vendido --");
    
        Map<Integer, Integer> mapaProductosVendidos = new HashMap<>();
        List<Factura> facturas = serviceFactura.listar();
        for (Factura factura : facturas) {
            for (ItemFactura item : factura.getItems()) {
                int codigoProducto = item.getProducto().getCodigo();
                int cantidadVendida = item.getCantidad();
                mapaProductosVendidos.put(codigoProducto, mapaProductosVendidos.getOrDefault(codigoProducto, 0) + cantidadVendida);
            }
        }
    
        List<Map.Entry<Integer, Integer>> listaProductosVendidos = new ArrayList<>(mapaProductosVendidos.entrySet());
        listaProductosVendidos.sort((a, b) -> b.getValue().compareTo(a.getValue()));
    
        for (Map.Entry<Integer, Integer> entry : listaProductosVendidos) {
            Producto producto = serviceProducto.porCodigo(entry.getKey());
            System.out.println("Producto: " + producto.getNombre() + " - Cantidad Vendida: " + entry.getValue());
        }
    }
    
    
    

}