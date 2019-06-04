package br.sc.ecommerce.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import br.sc.ecommerce.R;
import br.sc.ecommerce.classes.Item;
import br.sc.ecommerce.classes.ItemPedido;
import br.sc.ecommerce.classes.PedidoCompra;
import br.sc.ecommerce.classes.Usuario;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class EcommerceActivity extends AppCompatActivity {

    private ArrayList<ItemPedido> itemPedidos;
    private ArrayList<PedidoCompra> compras;
    private TextView txt_user, txt_card, txt_item_name, txt_item_descricao, txt_item_valor, txt_qtd;
    private Button btn_buy, btn_minus, btn_plus, btn_search, btn_car;
    private SpinnerDialog spinnerDialog;
    private Item itemSelected;
    private Intent intentCar, intentPedido, intentConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecommerce);
        initComponent();
        initListinner();
        refreshCarrinho();
    }

    private void initListinner() {
        spinnerDialog.bindOnSpinerListener((item, position) -> {
            itemSelected = Item.getItemByNome(item);
            txt_item_name.setText(itemSelected.getNome());
            txt_item_valor.setText(String.valueOf(itemSelected.getValor()));
            txt_item_descricao.setText(itemSelected.getDetalhes());
        });

        btn_minus.setOnClickListener(v -> {
            int qtd = Integer.parseInt(txt_qtd.getText().toString());
            if (qtd > 1) {
                txt_qtd.setText(String.valueOf(qtd - 1));
            }
        });

        btn_plus.setOnClickListener(v -> {
            int qtd = Integer.parseInt(txt_qtd.getText().toString());
            if (qtd < 9) {
                txt_qtd.setText(String.valueOf(qtd + 1));
            }
        });

        btn_search.setOnClickListener(v -> {
                    spinnerDialog.showSpinerDialog();
                    btn_buy.setEnabled(true);
                }
        );

        btn_buy.setOnClickListener(v -> {
            ItemPedido ip = new ItemPedido();
            ip.setItem(Item.getItemByNome(txt_item_name.getText().toString()));
            ip.setQuantidade(Integer.parseInt(txt_qtd.getText().toString()));
            itemPedidos.add(ip);
            refreshCarrinho();
        });

        btn_car.setOnClickListener(v->{
            startActivity(intentCar);
        });


    }

    private void refreshCarrinho() {
        String aux = "CARRIONHO (" + itemPedidos.size() + ")";
        btn_car.setText(aux);
    }

    private void initComponent() {
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
        btn_car = findViewById(R.id.btn_car);
        intentCar = new Intent(getBaseContext(), CarActivity.class);
        intentPedido = new Intent(getBaseContext(),PedidoActivity.class);
        intentConfig = new Intent(getBaseContext(), ConfigActivity.class);

        txt_user.setText(LoginActivity.userValid.getLogin());
        txt_card.setText(LoginActivity.userValid.getNumeroCartao());
        spinnerDialog = new SpinnerDialog(EcommerceActivity.this, Item.getListNamesItems(), "Selecione um Item");
        itemPedidos = new ArrayList<>();
    }
}
