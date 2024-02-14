package com.campusland.respository.impl.implImpuesto;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import com.campusland.exceptiones.impuestoexceptions.ImpuestoExceptionInsertDataBase;
import com.campusland.respository.RepositoryImpuesto;
import com.campusland.respository.models.Impuesto;
import com.campusland.utils.conexionpersistencia.conexionbdjson.ConexionBDjsonImpuesto;

public class RepositoryImpuestoJsonImpl implements RepositoryImpuesto {

    ConexionBDjsonImpuesto conexion = ConexionBDjsonImpuesto.getConnexion();

    @Override
    public List<Impuesto> listar() {
        return conexion.getData(Impuesto.class);
    }

    @Override
    public void crear(Impuesto impuesto) throws ImpuestoExceptionInsertDataBase {
        List<Impuesto> listImpuestos = conexion.getData(Impuesto.class);
        listImpuestos.add(impuesto);
        conexion.saveData(listImpuestos);
    }

    @Override
    public List<Impuesto> listarPorAnio(Year año) throws ImpuestoExceptionInsertDataBase {
        List<Impuesto> impuestosPorAnio = new ArrayList<>();
        for (Impuesto impuesto : listar()) {
            if (impuesto.getAño().equals(año)) {
                impuestosPorAnio.add(impuesto);
            }
        }
        return impuestosPorAnio;
    }

}
