package br.sc.ecommerce.classes;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class PedidoCompra {

    private GregorianCalendar data;
    private ArrayList<ItemPedido> itemPedidos;
    private Usuario usuario;

    public GregorianCalendar getData() {
        return data;
    }

    public void setData(GregorianCalendar data) {
        this.data = data;
    }

    public ArrayList<ItemPedido> getItemPedidos() {
        return itemPedidos;
    }

    public void setItemPedidos(ArrayList<ItemPedido> itemPedidos) {
        this.itemPedidos = itemPedidos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
