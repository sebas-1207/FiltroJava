package com.campusland.services;

import java.util.List;

import com.campusland.exceptiones.impuestoexceptions.ImpuestoExceptionInsertDataBase;
import com.campusland.respository.models.Impuesto;

public interface ServiceImpuesto {
    
    List<Impuesto> listar();

    void crear(Impuesto impuesto) throws ImpuestoExceptionInsertDataBase;

    List<Impuesto> listarPorAnio(Impuesto impuesto) throws ImpuestoExceptionInsertDataBase;
}
