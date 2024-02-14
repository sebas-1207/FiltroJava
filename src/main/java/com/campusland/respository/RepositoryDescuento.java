package com.campusland.respository;

import java.util.List;

import com.campusland.respository.models.Descuentos;

public interface RepositoryDescuento {

    List<Descuentos> listar();

    void crear(Descuentos descuentos);    
    
}
