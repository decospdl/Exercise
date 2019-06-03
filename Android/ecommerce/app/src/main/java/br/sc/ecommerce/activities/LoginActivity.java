package br.sc.ecommerce.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import br.sc.ecommerce.R;
import br.sc.ecommerce.classes.Usuario;

public class LoginActivity extends AppCompatActivity {
    public static final String USER_INVALID = "Usuário inválido!";
    public static final String PASS_INVALID = "Senha inválida!";

    private ArrayList<Usuario> users = Usuario.getListUsuario();
    private Usuario userValid;
    private EditText edtUser, edtPass;
    private TextView txtMsg;
    private Button btnLogin;
    private Intent intentCommerce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
        initListinner();
    }

    private void initComponent(){
        edtUser = findViewById(R.id.edt_login_user);
        edtPass = findViewById(R.id.edt_login_pass);
        btnLogin = findViewById(R.id.btn_login);
        txtMsg = findViewById(R.id.txt_login_msg);
        intentCommerce = new Intent(getBaseContext(), EcommerceActivity.class);
    }

    private void initListinner(){
        btnLogin.setOnClickListener((v -> {
            if(!existUser()){
                txtMsg.setText(USER_INVALID);
            }else if(!validPassword()){
                txtMsg.setText(PASS_INVALID);
            }else{
                System.out.println(userValid.getCodigoSeguranca());
                intentCommerce.putExtra("userCode", String.valueOf(userValid.getCodigoSeguranca()));
                startActivity(intentCommerce);
            }
        }));
    }

    private boolean existUser(){
        for(Usuario user : users){
            if(user.getLogin().equals(edtUser.getText().toString())){
                userValid = user;
                return true;
            }
        }
        return false;
    }

    private boolean validPassword(){
        return userValid.getSenha().equals(edtPass.getText().toString());
    }
}
