package br.sc.ecommerce.classes;

import java.time.LocalDate;
import java.util.ArrayList;

public class PedidoCompra {

    private LocalDate data;
    private ArrayList<ItemPedido> itemPedidos;
    private Usuario usuario;

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
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
