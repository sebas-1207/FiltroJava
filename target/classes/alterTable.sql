ALTER TABLE descuentos
    ADD CONSTRAINT `fk_codigo_producto` FOREIGN KEY (codigoProducto)
        REFERENCES producto(codigo);

ALTER TABLE descuentos
    ADD CONSTRAINT `fk_cliente_id` FOREIGN KEY (clienteId)
        REFERENCES cliente(id); 

ALTER TABLE impuesto
    ADD CONSTRAINT `fk_id_cliente` FOREIGN KEY (clienteId)
        REFERENCES cliente(id);

ALTER TABLE impuesto
    ADD CONSTRAINT `fk_factura_numero` FOREIGN KEY (facturaNumero)
        REFERENCES factura(numeroFactura); 

