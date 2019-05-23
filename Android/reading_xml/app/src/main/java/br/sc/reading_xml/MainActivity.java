package br.sc.reading_xml;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Menu> menus = new ArrayList<>();
    private ArrayList<Purchase> purchases = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private Spinner spinner;
    private TextView tvFood, tvCalories, tvDescription, tvPrice, tvTotalCalories, tvTotalPrice, tvQtd;
    private Button btnAdd, btnMinus, btnPlus, btnClean;
    private LinearLayout llFood, llQtd, llValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runThread();

    }

    private void runUiThread(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initComponent();
                initItemsSpinner();
                initListinner();
            }
        });
    }

    private void runThread() {
         Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getDataXml();
                createArrayName();
                runUiThread();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void initListinner() {
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qtd = Integer.parseInt(tvQtd.getText().toString());
                if (qtd < 5)
                    tvQtd.setText(String.valueOf(qtd + 1));
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qtd = Integer.parseInt(tvQtd.getText().toString());
                if (qtd > 1)
                    tvQtd.setText(String.valueOf(qtd - 1));
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(purchases.size() < 4) {
                    Purchase purchase = new Purchase();
                    purchase.setFood(tvFood.getText().toString());
                    purchase.setQtd(tvQtd.getText().toString());
                    purchase.setValue(tvPrice.getText().toString());
                    purchase.setCalories(tvCalories.getText().toString());
                    purchases.add(purchase);
                    refreshPurchase();
                }else{
                    btnAdd.setEnabled(false);
                }
            }
        });

        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAdd.setEnabled(true);
                purchases.clear();
                clearPurchase();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvFood.setText(menus.get(position).getName());
                tvCalories.setText(menus.get(position).getCalories());
                tvDescription.setText(menus.get(position).getDescription());
                tvPrice.setText(menus.get(position).getPrice());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    private void clearPurchase(){
        llFood.removeAllViews();
        llQtd.removeAllViews();
        llValue.removeAllViews();
        tvTotalCalories.setText("");
        tvTotalPrice.setText("");
    }

    private void refreshPurchase(){
        clearPurchase();
        dynamicTextViewFood();
        dynamicTextViewQtd();
        dynamicTextViewValue();
        sumCalories();
        sumValues();
    }

    private void dynamicTextViewFood(){
        for(Purchase purchase : purchases){
            TextView tv = new TextView(this);
            tv.setText(purchase.getFood());
            llFood.addView(tv);
        }
    }

    private void dynamicTextViewQtd(){
        for(Purchase purchase : purchases){
            TextView tv = new TextView(this);
            tv.setText(String.valueOf(purchase.getQtd()));
            llQtd.addView(tv);
        }
    }

    private void dynamicTextViewValue(){
        for(Purchase purchase : purchases){
            TextView tv = new TextView(this);
            String aux = "$" + purchase.getValue();
            tv.setText(aux);
            llValue.addView(tv);
        }
    }

    private void sumCalories(){
        int sum = 0;
        for(Purchase purchase : purchases){
            sum += purchase.getCalories();
        }
        tvTotalCalories.setText(String.valueOf(sum));
    }

    private void sumValues(){
        BigDecimal sum = new BigDecimal("0.00");
        for(Purchase purchase : purchases){
            sum = sum.add(new BigDecimal(purchase.getValue()));
        }
        tvTotalPrice.setText("$" + sum);
    }

    private void initComponent() {
        tvFood = findViewById(R.id.tvFood);
        tvCalories = findViewById(R.id.tvCalories);
        tvDescription = findViewById(R.id.tvDescription);
        tvPrice = findViewById(R.id.tvPrice);
        tvTotalCalories = findViewById(R.id.tvTotalCalories);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvQtd = findViewById(R.id.tvQtd);
        btnAdd = findViewById(R.id.btnAdd);
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        btnClean = findViewById(R.id.btnClean);
        spinner = findViewById(R.id.spinner);
        llFood = findViewById(R.id.llFood);
        llQtd = findViewById(R.id.llQtd);
        llValue = findViewById(R.id.llValue);
    }

    private void initItemsSpinner(){
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, names);
        spinner.setAdapter(adapter);
    }


    private void createArrayName() {
        for (Menu menu : menus) {
            names.add(menu.getName());
        }
}

    private void createArrayMenu(Elements table) {
        for (Element e : table) {
            Menu menu = new Menu();
            menu.setName(e.select("name").first().text());
            menu.setPrice(e.select("price").first().text());
            menu.setDescription(e.select("description").first().text());
            menu.setCalories(e.select("calories").first().text());
            menus.add(menu);
        }
    }

    private void getDataXml() {
        try {
            Document doc = Jsoup.connect("https://www.w3schools.com/xml/simple.xml").get();
            Elements table = doc.select("food");
            createArrayMenu(table);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
