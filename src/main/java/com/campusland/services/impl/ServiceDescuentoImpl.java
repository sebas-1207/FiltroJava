package com.campusland.services.impl;

import java.util.List;

import com.campusland.exceptiones.descuentoexceptions.DescuentoNullException;
import com.campusland.respository.RepositoryDescuento;
import com.campusland.respository.models.Descuentos;
import com.campusland.services.ServiceDescuento;

public class ServiceDescuentoImpl implements ServiceDescuento{
    
    private final RepositoryDescuento crudRepositoryDescuento;
    
    public ServiceDescuentoImpl(RepositoryDescuento crudRepositoryDescuento){
        this.crudRepositoryDescuento=crudRepositoryDescuento;
    }

    @Override
    public List<Descuentos> listar() {
        return this.crudRepositoryDescuento.listar(); 
    }

    @Override
    public void crear(Descuentos descuentos) throws DescuentoNullException {
        this.crudRepositoryDescuento.crear(descuentos);
    }
    
}
