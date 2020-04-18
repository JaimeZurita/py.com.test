package py.com.test.ventas.boundary;

import py.com.test.ventas.ec.Venta;
import py.com.test.ventas.ec.VentaDetalle;

import java.sql.*;
import java.util.Properties;
import java.util.Vector;

public class VentaManager {


    public Venta add(Venta venta){
        Connection connection = null;
        PreparedStatement insertVentaPs = null;
        PreparedStatement insertVentaDetallePs = null;
        PreparedStatement getIdPs = null;
        ResultSet getIdRs = null;
        try {
            connection = getDBConnection();
            connection.setAutoCommit(false);
            getIdPs = connection.prepareStatement("select nextval('ventas_seq')");
            getIdRs = getIdPs.executeQuery();
            if(getIdRs.next()) {
                venta.setId(getIdRs.getLong(1));
                insertVentaPs = connection.prepareStatement("Insert into venta(id, total) values (nextval('ventas_seq'), ?)");
                insertVentaPs.setLong(1, venta.getId());
                insertVentaPs.setDouble(2, venta.getTotal());

                StringBuilder sb = new StringBuilder();
                sb.append("Insert into venta_detalle(id,venta_id, descripcion_producto, cantidad, precio) ");
                sb.append("values (nextval('venta_detalle_seq'), ?, ?, ?,?) ");
                insertVentaDetallePs = connection.prepareStatement(sb.toString());
                VentaDetalle detalle;
                Vector<VentaDetalle> detalles = venta.getDetalles();
                for(int i = 0; i < detalles.size();i++){
                    detalle = detalles.get(i);
                    insertVentaDetallePs.setLong(1, venta.getId());
                    insertVentaDetallePs.setString(2, detalle.getDescripcionProducto());
                    insertVentaDetallePs.setDouble(3, detalle.getCantidad());
                    insertVentaDetallePs.setDouble(4, detalle.getPrecio());
                    insertVentaDetallePs.executeUpdate();
                }
            }
            connection.commit();
        }catch(Throwable th){
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {insertVentaPs.close();} catch (SQLException e) {e.printStackTrace();}
            try {insertVentaDetallePs.close();} catch (SQLException e) {e.printStackTrace();}
            try {getIdPs.close();} catch (SQLException e) {e.printStackTrace();}
            try {getIdRs.close();} catch (SQLException e) {e.printStackTrace();}
            th.printStackTrace();
        }finally{
            try{connection.close();}catch (Throwable th){}
        }
        return venta;
    }

    public boolean delete(){
        return true;
    }

    public boolean update(){
        return true;
    }

    public Venta getVenta(Long id){

        Venta venta = null;
        Connection connection = null;
        PreparedStatement getVentaPs = null;
        ResultSet getVentaRs = null;
        PreparedStatement getVentaDetallePs = null;
        ResultSet getVentaDetalleRs = null;
        try {
            connection = getDBConnection();
            connection.setAutoCommit(false);
            getVentaPs = connection.prepareStatement("Select id, total from venta where id = ?");
            getVentaPs.setLong(1, id);
            getVentaRs = getVentaPs.executeQuery();
            if(getVentaRs.next()) {
                venta = new Venta();
                venta.setId(getVentaRs.getLong("id"));
                venta.setTotal(getVentaRs.getDouble("total"));
                VentaDetalle detalle;
                Vector<VentaDetalle> detalles = new Vector<VentaDetalle>();
                venta.setDetalles(detalles);
                getVentaDetallePs = connection.prepareStatement("select id, venta_id, descripcion_producto, cantidad, precio, total from venta_detalle where venta_id = ?");
                getVentaDetallePs.setLong(1, venta.getId());
                getVentaDetalleRs = getVentaDetallePs.executeQuery();
                while(getVentaDetalleRs.next()){
                    detalle = new VentaDetalle();
                    detalle.setId(getVentaDetalleRs.getLong("id"));
                    detalle.setDescripcionProducto(getVentaDetalleRs.getString("descripcion_producto"));
                    detalle.setCantidad(getVentaDetalleRs.getDouble("cantidad"));
                    detalle.setPrecio(getVentaDetalleRs.getDouble("precio"));
                    detalle.setTotal(getVentaDetalleRs.getDouble("total"));
                    detalles.add(detalle);
                }
            }
            connection.commit();
        }catch(Throwable th){
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {getVentaPs.close();} catch (SQLException e) {e.printStackTrace();}
            try {getVentaRs.close();} catch (SQLException e) {e.printStackTrace();}
            try {getVentaDetallePs.close();} catch (SQLException e) {e.printStackTrace();}
            try {getVentaDetalleRs.close();} catch (SQLException e) {e.printStackTrace();}
            th.printStackTrace();
        }finally{
            try{connection.close();}catch (Throwable th){}
        }
        return venta;
    }



    private Connection getDBConnection() throws SQLException {
        Connection conn = null;
        String url = "jdbc:postgresql://localhost/ventas_db";
        Properties props = new Properties();
        props.setProperty("user","ventas");
        props.setProperty("password","ventas1234");
        props.setProperty("ssl","true");
        conn = DriverManager.getConnection(url, props);
        return conn;
    }
}
