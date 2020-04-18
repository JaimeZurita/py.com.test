package py.com.test.ventas.ec;

import java.util.Vector;

public class Venta {

    private Long id;
    //    private Customer customer;
    private Double total;
    private Vector<VentaDetalle> detalles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Vector<VentaDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(Vector<VentaDetalle> detalles) {
        this.detalles = detalles;
    }
}
