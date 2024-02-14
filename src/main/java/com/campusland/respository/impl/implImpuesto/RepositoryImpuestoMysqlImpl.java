package com.campusland.respository.impl.implImpuesto;
/* package com.campusland.respository.impl.implImpuesto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

import com.campusland.exceptiones.impuestoexceptions.ImpuestoExceptionInsertDataBase;
import com.campusland.respository.RepositoryImpuesto;
import com.campusland.respository.models.Impuesto;
import com.campusland.utils.conexionpersistencia.conexionbdmysql.ConexionBDMysql;

public class RepositoryImpuestoMysqlImpl implements RepositoryImpuesto {

    private static final String INSERT_FACTURA = "INSERT INTO impuesto (clienteId, facturaNumero) VALUES (?, ?)";

    private Connection getConnection() throws SQLException {
        return ConexionBDMysql.getInstance();
    }

    @Override
    public List<Impuesto> listar() {
        List<Impuesto> listImpuesto = new ArrayList<>();

        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM impuesto")) {
            while (rs.next()) {
                listImpuesto.add(impuesto);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listImpuesto;
    }

    @Override
    public void crear(Impuesto impuesto) throws ImpuestoExceptionInsertDataBase {
        Connection conn = null;
        try{
            conn = getConnection();
            conn.setAutoCommit(false);
            try (PreparedStatement psImpuesto = conn.prepareStatement(INSERT_FACTURA,
                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, impuesto.getClientes().getId());
            stmt.setInt(2, impuesto.getFactura().getId());
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        }
        
    }

}
 */