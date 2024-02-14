package com.campusland.respository.models;

import java.time.Year;

import lombok.Data;

@Data
public class Impuesto {

    private int id;
    private Year año;
    private double valor;
    private Cliente clientes;
    private Factura factura;
    private static int nexxtImpuesto;

    public Impuesto() {
        
    }

    public Impuesto(int id, Year año, double valor, Cliente clientes, Factura factura) {
        this.id = id;
        this.año = año;
        this.valor = valor;
        this.clientes = clientes;
        this.factura = factura;
    }

    public Impuesto(Year año, double valor, Cliente clientes, Factura factura) {
        this.id = ++nexxtImpuesto;
        this.año = año;
        this.valor = valor;
        this.clientes = clientes;
        this.factura = factura;
    }

    
    
}
