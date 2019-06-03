package br.sc.ecommerce.classes;

import java.util.ArrayList;

public class Item {
    private double valor;
    private String detalhes;
    private String nome;

    public Item(double valor, String detalhes, String nome) {
        this.valor = valor;
        this.detalhes = detalhes;
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static ArrayList<String> getListNamesItems(){
        ArrayList<String> itemsNames = new ArrayList<>();
        ArrayList<Item> items = getListItems();
        for(Item item : items){
            itemsNames.add(item.nome);
        }
        return itemsNames;
    }

    public static ArrayList<Item> getListItems() {
        Item i1 = new Item(2.55, "Maçã do sul do Pará", "Maçã");
        Item i2 = new Item(3.55, "Laranja dos Monges do Mestre Miague", "Laranja");
        ArrayList<Item> items = new ArrayList<>();
        items.add(i1);
        items.add(i2);
        return items;
    }

    public static Item getItemByNome(String nome){
        ArrayList<Item> items = getListItems();
        for(Item item : items){
            if(item.getNome().equals(nome)){
                return item;
            }
        }
        return null;
    }
}
