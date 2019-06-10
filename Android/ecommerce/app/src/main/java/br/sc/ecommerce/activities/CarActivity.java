package br.sc.ecommerce.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.GregorianCalendar;

import br.sc.ecommerce.R;
import br.sc.ecommerce.classes.ItemPedido;
import br.sc.ecommerce.classes.PedidoCompra;

public class CarActivity extends AppCompatActivity {

    private Button btn_cancel, btn_confirm;
    private TextView txt_qtd, txt_total;
    private LinearLayout ll_car;
    private Intent intentEcommerce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        initComponent();
        dynamicTextViewItem();
        sumQtdTotal();
        initListnner();
    }

    private void initComponent() {
        btn_cancel = findViewById(R.id.btn_car_cancel);
        btn_confirm = findViewById(R.id.btn_car_confim);
        txt_qtd = findViewById(R.id.txt_car_qtd);
        txt_total = findViewById(R.id.txt_car_total);
        ll_car = findViewById(R.id.ll_pedido);
        intentEcommerce = new Intent(getBaseContext(), EcommerceActivity.class);
    }


    private void initListnner() {
        btn_cancel.setOnClickListener(v -> {
            EcommerceActivity.itemPedidos.clear();
            startActivity(intentEcommerce);
        });

        btn_confirm.setOnClickListener(v -> {
            PedidoCompra pc = new PedidoCompra();
            pc.setUsuario(LoginActivity.userValid);
            pc.setData(new GregorianCalendar());
            pc.setItemPedidos(EcommerceActivity.itemPedidos);
            EcommerceActivity.itemPedidos.clear();
            EcommerceActivity.compras.add(pc);
        });
    }


    private void dynamicTextViewItem() {
        for (ItemPedido ip : EcommerceActivity.itemPedidos) {
            TextView tvTitle = new TextView(this);
            tvTitle.setText(ip.getItem().getNome());
            tvTitle.setTextSize((float) 18.0);
            ll_car.addView(tvTitle);

            TextView tvDetail = new TextView(this);
            String txt = "R$ " + ip.getItem().getValor() + " X " + ip.getQuantidade() + " = R$ " + String.format("%.2f", (ip.getItem().getValor() * ip.getQuantidade())) + "\n";
            tvDetail.setText(txt);
            tvDetail.setTextSize((float) 14.0);
            ll_car.addView(tvDetail);
        }
    }

    private void sumQtdTotal() {
        double total = 0.0;
        int qtd = 0;
        for (ItemPedido ip : EcommerceActivity.itemPedidos) {
            total += ip.getItem().getValor() * ip.getQuantidade();
            qtd += ip.getQuantidade();
        }
        String totalTxt = String.format("R$ %.2f", total);
        txt_total.setText(totalTxt);
        txt_qtd.setText(String.valueOf(qtd));
    }
}
