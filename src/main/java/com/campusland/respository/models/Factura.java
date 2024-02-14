package com.campusland.respository.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.campusland.utils.Formato;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class Factura {

    private int numeroFactura;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fecha;
    private Cliente cliente;
    private List<ItemFactura> items;
    private List<Descuentos> descuentos;
    private static int nextNumeroFactura;

    public Factura() {

    }

    public Factura(int numeroFactura, LocalDateTime fecha, Cliente cliente) {
        this.numeroFactura = numeroFactura;
        this.fecha = fecha;
        this.cliente = cliente;
        this.items = new ArrayList<>();
    }

    public Factura(LocalDateTime fecha, Cliente cliente) {
        this.numeroFactura = ++nextNumeroFactura;
        this.fecha = fecha;
        this.cliente = cliente;
        this.items = new ArrayList<>();
    }

    public double getTotalSinIva(){
        double totalSinIva = 0;
        for (ItemFactura item : items) {
            totalSinIva += item.getImporte();
        }
        return totalSinIva;
    }

    public double getTotalIva(){
        double totalConIva = 0;
        totalConIva = getTotalSinIva() *0.19;
        return totalConIva;
    }

    public double getTotal(){
        double total = 0;
        total = getTotalSinIva() + getTotalIva();
        return total;
    }

    public void agregarItem(ItemFactura item) {
        this.items.add(item);
    }

    public void display() {
        System.out.println();
        System.out.println("Factura: " + this.numeroFactura);
        System.out.println("Cliente: " + this.cliente.getFullName());
        System.out.println("Fecha: " + Formato.formatoFechaHora(this.getFecha()));
        System.out.println("-----------Detalle Factura----------------------");
        for (ItemFactura item : this.items) {
            System.out.println(item.getProducto().getCodigoNombre());
            System.out.println("Cantidad: "  + item.getCantidad());
            System.out.println("Valor de las cantidades del Producto: " + Formato.formatoMonedaPesos(item.getImporte()));
            System.out.println();
        }
    
        System.out.println("Total sin IVA: " + Formato.formatoMonedaPesos(getTotalSinIva()));
        System.out.println("Total Impuesto: " + Formato.formatoMonedaPesos(getTotalIva()));
        System.out.println("Total a Pagar: " + Formato.formatoMonedaPesos(getTotal()));
        System.out.println();
    }
    
    
}
