package py.com.test.ventas.controller;

import py.com.test.cobranza.controller.CobranzasController;
import py.com.test.ventas.boundary.VentaManager;
import py.com.test.ventas.ec.Venta;

public class VentasController {

    public void vender(Integer id){
        System.out.println("Vender ahorita "+id);
    }

    public boolean isSold(){
        CobranzasController cc = new CobranzasController();
        cc.cobrar(5);
        return true;
    }

    public long agregarFactura(){
        return 15l;
    }

    public Venta nuevaVenta(Venta venta){
        VentaManager ventaManager = new VentaManager();
        return ventaManager.add(venta);
    }

}
