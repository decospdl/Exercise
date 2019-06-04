package br.sc.ecommerce.classes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class Item {

    public static ArrayList<Item> items = new ArrayList<>();

    private int id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static ArrayList<String> getListNamesItems(){
        ArrayList<String> itemsNames = new ArrayList<>();
        for(Item item : items){
            itemsNames.add(item.nome);
        }
        return itemsNames;
    }

    public static void refreshListItem() {
        Runnable runnable = () -> {
            String result = JsonManager.sendGet( "http://10.7.25.222:8080/Service/items");
            Gson gson = new GsonBuilder().create();
            items =  gson.fromJson(result,new TypeToken<ArrayList<Item>>() {}.getType());
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public static Item getItemByNome(String nome){
        for(Item item : items){
            if(item.getNome().equals(nome)){
                return item;
            }
        }
        return null;
    }
}
