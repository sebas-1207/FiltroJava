package com.campusland.respository.impl.impldescuento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.campusland.respository.RepositoryDescuento;
import com.campusland.respository.models.Cliente;
import com.campusland.respository.models.Descuentos;
import com.campusland.respository.models.Producto;
import com.campusland.utils.conexionpersistencia.conexionbdmysql.ConexionBDMysql;

public class RepositoryDescuentoMysqlImpl implements RepositoryDescuento {

    private Connection getConnection() throws SQLException {
        return ConexionBDMysql.getInstance();
    }

    @Override
    public List<Descuentos> listar() {
        List<Descuentos> listDescuento = new ArrayList<>();

        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM descuentos")) {
            while (rs.next()) {
                
                listDescuento.add(crearDescuento(rs, null, null));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listDescuento;
    }

    @Override
    public void crear(Descuentos descuentos) {
        String sql = "INSERT INTO descuentos(tipoDescuento, condiciones, porcentaje, codigoProducto, clienteId, estado) VALUES (?,?,?,?,?,?)";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, descuentos.getTipoDescuento());
            stmt.setString(2, descuentos.getCondiciones());
            stmt.setFloat(3, descuentos.getPorcentaje());
            stmt.setInt(4, descuentos.getProductos().getCodigo());
            stmt.setInt(5, descuentos.getClientes().getId());
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Descuentos crearDescuento(ResultSet rs, Producto producto, Cliente cliente) throws SQLException {
        Descuentos descuento = new Descuentos();
        descuento.setId(rs.getInt("id"));
        descuento.setTipoDescuento(rs.getString("tipoDescuento"));
        descuento.setCondiciones(rs.getString("condiciones"));
        descuento.setPorcentaje(rs.getFloat("porcentaje"));
        descuento.setProductos(producto);
        descuento.setClientes(cliente);
        descuento.setEstado(rs.getString("estado"));
        return descuento;
    }

}
