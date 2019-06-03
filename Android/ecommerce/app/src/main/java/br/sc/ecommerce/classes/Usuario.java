package br.sc.ecommerce.classes;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Usuario {
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

    public static ArrayList<Usuario> getListUsuario() {
        try {
            String resourceURI="http://localhost:8080/Service/usuario";
//            String httpParameters = "?id="+ URLEncoder.encode("123","UTF-8") + "&titulo="+ URLEncoder.encode("Aula sobre REST", "UTF-8");
            String formatedURL=resourceURI+ httpParameters;;
            URL url = new URL(formatedURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("accept", "XML");
            con.setRequestMethod("PUT");
            InputStream is=con.getInputStream();
            String respose = convertStreamToString(is);
            System.out.println(respose);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Usuario u1 = new Usuario(1, "11/12/2019", "deco", "55599", "1234", null);
        Usuario u2 = new Usuario(2, "14/09/2019", "kah", "99555", "4321", null);
        ArrayList<Usuario> users = new ArrayList<>();
        users.add(u1);
        users.add(u2);
        return users;
    }

    public static Usuario getUsarioByCodigoSeguranca(int codigoSeguranca){
        ArrayList<Usuario> users = getListUsuario();
        for(Usuario user : users){
            if(user.getCodigoSeguranca() == codigoSeguranca){
                return user;
            }
        }
        return null;
    }
}
