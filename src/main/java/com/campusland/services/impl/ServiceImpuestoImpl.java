package com.campusland.services.impl;

import java.util.List;

import com.campusland.exceptiones.impuestoexceptions.ImpuestoExceptionInsertDataBase;
import com.campusland.respository.RepositoryImpuesto;
import com.campusland.respository.impl.implImpuesto.RepositoryImpuestoJsonImpl;
import com.campusland.respository.models.Impuesto;
import com.campusland.services.ServiceImpuesto;

public class ServiceImpuestoImpl implements ServiceImpuesto {

    private final RepositoryImpuesto repositoryImpuestoMysql;
    private final RepositoryImpuesto repositoryImpuestoJson;

    public ServiceImpuestoImpl(RepositoryImpuesto repositoryImpuestoMysql, RepositoryImpuesto repositoryImpuestoJson) {
        this.repositoryImpuestoMysql = repositoryImpuestoMysql;
        this.repositoryImpuestoJson = repositoryImpuestoJson != null ? repositoryImpuestoJson
                : new RepositoryImpuestoJsonImpl();
    }

    @Override
    public List<Impuesto> listar() {
        return this.repositoryImpuestoMysql.listar();
    }

    @Override
    public void crear(Impuesto impuesto) throws ImpuestoExceptionInsertDataBase {
        try {
            guardarEnMysqlYJson(impuesto);
        } catch (ImpuestoExceptionInsertDataBase e) {
            e.printStackTrace();
        }
    }

    private void guardarEnMysqlYJson(Impuesto impuesto) throws ImpuestoExceptionInsertDataBase {
        guardarEnMysql(impuesto);
        guardarEnJson(impuesto);
    }

    private void guardarEnMysql(Impuesto impuesto) throws ImpuestoExceptionInsertDataBase {
        this.repositoryImpuestoMysql.crear(impuesto);
    }

    private void guardarEnJson(Impuesto impuesto) throws ImpuestoExceptionInsertDataBase {
        try {
            this.repositoryImpuestoJson.crear(impuesto);
        } catch (ImpuestoExceptionInsertDataBase eJson) {
            eJson.printStackTrace();
        }
    }

    @Override
    public List<Impuesto> listarPorAnio(Impuesto impuesto) throws ImpuestoExceptionInsertDataBase {
        return this.repositoryImpuestoJson.listarPorAnio(null);
    }

}
