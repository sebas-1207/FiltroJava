package com.campusland.respository.models;

import lombok.Data;

@Data
public class Descuentos {

    private int id;
    private String tipoDescuento;
    private String condiciones;
    private Float porcentaje;
    private Producto productos;
    private Cliente clientes;
    private String estado;
    private static int nextNumeroDescuento;

    public Descuentos() {
        this.id = ++nextNumeroDescuento;
    }

    public Descuentos(int id, String tipoDescuento, String condiciones, Float porcentaje, Producto productos,
            Cliente clientes, String estado) {
        this.id = id;
        this.tipoDescuento = tipoDescuento;
        this.condiciones = condiciones;
        this.porcentaje = porcentaje;
        this.productos = productos;
        this.clientes = clientes;
        this.estado = estado;
    }

    public Descuentos(String tipoDescuento, String condiciones, Float porcentaje, Producto productos,
            Cliente clientes, String estado) {
        this.id = ++nextNumeroDescuento;
        this.tipoDescuento = tipoDescuento;
        this.condiciones = condiciones;
        this.porcentaje = porcentaje;
        this.productos = productos;
        this.clientes = clientes;
        this.estado = estado;
    }
}
