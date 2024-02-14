package com.campusland.services;

import java.util.List;

import com.campusland.exceptiones.descuentoexceptions.DescuentoNullException;
import com.campusland.respository.models.Descuentos;

public interface ServiceDescuento {
    
    List<Descuentos> listar();

    void crear(Descuentos descuentos) throws DescuentoNullException;
    
}
