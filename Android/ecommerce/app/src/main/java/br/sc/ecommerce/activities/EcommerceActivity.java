package br.sc.ecommerce.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import br.sc.ecommerce.R;
import br.sc.ecommerce.classes.Item;
import br.sc.ecommerce.classes.Usuario;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class EcommerceActivity extends AppCompatActivity {

    private String codigoSeguranca;
    private Usuario user;
    private TextView txt_user, txt_card, txt_item_name, txt_item_descricao, txt_item_valor, txt_qtd;
    private Button btn_buy, btn_minus, btn_plus, btn_search;
    private SpinnerDialog spinnerDialog;
    private Item itemSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecommerce);
        codigoSeguranca = getIntent().getStringExtra("userCode");
        user = Usuario.getUsarioByCodigoSeguranca(Integer.parseInt(codigoSeguranca));
        initComponent();
        initListinner();
    }

    private void initListinner(){
        spinnerDialog.bindOnSpinerListener((item,position) -> {
                itemSelected = Item.getItemByNome(item);
                txt_item_name.setText(itemSelected.getNome());
                txt_item_valor.setText(String.valueOf(itemSelected.getValor()));
                txt_item_descricao.setText(itemSelected.getDetalhes());
        });

        btn_minus.setOnClickListener(v -> {
            int qtd = Integer.parseInt(txt_qtd.getText().toString());
            if(qtd > 1){
                txt_qtd.setText(String.valueOf(qtd - 1));
            }
        });

        btn_plus.setOnClickListener(v -> {
            int qtd = Integer.parseInt(txt_qtd.getText().toString());
            if(qtd < 9){
                txt_qtd.setText(String.valueOf(qtd + 1));
            }
        });

        btn_search.setOnClickListener(v -> spinnerDialog.showSpinerDialog());

        btn_buy.setOnClickListener(v ->{

        });
    }

    private void initComponent(){
        txt_card = findViewById(R.id.txt_card);
        txt_user = findViewById(R.id.txt_user);
        txt_qtd = findViewById(R.id.txt_qtd);
        txt_item_descricao = findViewById(R.id.txt_item_descricao);
        txt_item_name = findViewById(R.id.txt_item_name);
        txt_item_valor = findViewById(R.id.txt_item_valor);
        btn_search = findViewById(R.id.btn_search);
        btn_buy = findViewById(R.id.btn_buy);
        btn_minus = findViewById(R.id.btn_minus);
        btn_plus = findViewById(R.id.btn_plus);
        txt_user.setText(user.getLogin());
        txt_card.setText(user.getNumeroCartao());
        spinnerDialog = new SpinnerDialog(EcommerceActivity.this, Item.getListNamesItems(), "Selecione um Item");
    }
}
