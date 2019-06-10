package br.sc.ecommerce.classes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class Usuario {
    public static ArrayList<Usuario> users = new ArrayList<>();

    private int codigoSeguranca;
    private String dataValidade;
    private String login;
    private String numeroCartao;
    private String senha;
    private ArrayList<PedidoCompra> pedidos;

    public Usuario(int codigoSeguranca, String dataValidade, String login, String numeroCartao, String senha, ArrayList<PedidoCompra> pedidos) {
        this.codigoSeguranca = codigoSeguranca;
        this.dataValidade = dataValidade;
        this.login = login;
        this.numeroCartao = numeroCartao;
        this.senha = senha;
        this.pedidos = pedidos;
    }

    public int getCodigoSeguranca() {
        return codigoSeguranca;
    }

    public void setCodigoSeguranca(int codigoSeguranca) {
        this.codigoSeguranca = codigoSeguranca;
    }

    public String getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(String dataValidade) {
        this.dataValidade = dataValidade;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public static void refreshListUsuario() {
        Runnable runnable = () -> {
            String result = JsonManager.sendGet( "http://192.168.15.11:8080/Service/usuarios");
            Gson gson = new GsonBuilder().create();
            users =  gson.fromJson(result,new TypeToken<ArrayList<Usuario>>() {}.getType());
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public static Usuario getUsarioByCodigoSeguranca(int codigoSeguranca){
        for(Usuario user : users){
            if(user.getCodigoSeguranca() == codigoSeguranca){
                return user;
            }
        }
        return null;
    }
}
