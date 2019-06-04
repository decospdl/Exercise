package br.sc.itens_list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class MainActivity extends AppCompatActivity {
    ArrayList<City> cities = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    SpinnerDialog spinnerDialog;
    Button btnSearchCity;
    TextView tvId, tvName, tvFundation, tvPopulation, tvDensity, tvArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initThread();
    }

    private void initListinner(){
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                tvId.setText(cities.get(position).getId());
                tvName.setText(cities.get(position).getName());
                tvFundation.setText(cities.get(position).getFundation());
                tvPopulation.setText(cities.get(position).getPopulation());
                tvDensity.setText(cities.get(position).getDensity());
                tvArea.setText(cities.get(position).getArea());
            }
        });

        btnSearchCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerDialog.showSpinerDialog();
            }
        });
    }
    private void initComponent() {
        tvId = findViewById(R.id.tvId);
        tvName = findViewById(R.id.tvName);
        tvFundation = findViewById(R.id.tvFundation);
        tvPopulation = findViewById(R.id.tvPopulation);
        tvDensity = findViewById(R.id.tvDensity);
        tvArea = findViewById(R.id.tvArea);
        spinnerDialog = new SpinnerDialog(MainActivity.this, names, "Selecione um Cidade");
        btnSearchCity = findViewById(R.id.button);

    }

    private void initThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getDataTableWikipedia();
                createArrayNames();
                initComponent();
                initListinner();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void createArrayNames() {
        for (City city : cities) {
            names.add(city.getName());
        }
    }

    private void createArrayCities(Elements table) {
        for (Element text : table) {
            Elements tuple = text.select("td");
            City city = new City();
            city.setId(tuple.get(0).text());
            city.setName(tuple.get(1).text());
            city.setFundation(tuple.get(2).text());
            city.setPopulation(tuple.get(3).text());
            city.setArea(tuple.get(4).text());
            city.setDensity(tuple.get(5).text());
            cities.add(city);
        }
    }

    private void getDataTableWikipedia() {
        try {
            Document doc = Jsoup.connect("https://http://localhost:8080/Service/usuario")
                    .get();
            Elements table = doc.select("table.wikitable tr:gt(0)");
            createArrayCities(table);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
