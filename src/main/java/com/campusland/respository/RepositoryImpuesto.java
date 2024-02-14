package com.campusland.respository;

import java.time.Year;
import java.util.List;

import com.campusland.exceptiones.impuestoexceptions.ImpuestoExceptionInsertDataBase;
import com.campusland.respository.models.Impuesto;

public interface RepositoryImpuesto {
    
    List<Impuesto> listar();

    void crear(Impuesto impuesto) throws ImpuestoExceptionInsertDataBase;

    List<Impuesto> listarPorAnio(Year año) throws ImpuestoExceptionInsertDataBase;
}
