package br.sc.write_xml;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.jsoup.Jsoup;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> names = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ArrayList<Order> orders = new ArrayList<>();
    private ArrayList<Purchase> purchases = new ArrayList<>();
    private StringWriter sWriter;
    private Intent intentXml;

    private LinearLayout llName, llQtd;
    private EditText etName, etAdress, etPhone;
    private TextView tvQtd, tvTime;
    private Spinner spinner;
    private Button btnAdd, btnClean, btnMinus, btnPlus, btnSee, btnAddOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runThread();
    }

    private void initComponent(){
        llName = findViewById(R.id.llName);
        llQtd = findViewById(R.id.llQtd);
        etName = findViewById(R.id.etName);
        etAdress = findViewById(R.id.etAdress);
        etPhone = findViewById(R.id.etPhone);
        tvQtd = findViewById(R.id.tvQtd);
        tvTime = findViewById(R.id.tvTime);
        spinner = findViewById(R.id.spinner);
        btnAdd = findViewById(R.id.btnAdd);
        btnClean = findViewById(R.id.btnClean);
        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnSee = findViewById(R.id.btnSee);
        btnAddOrder = findViewById(R.id.btnAddOrder);
        intentXml = new Intent(getBaseContext(), IntentXml.class);
    }

    private void initListinner(){
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qtd = Integer.parseInt(tvQtd.getText().toString());
                if(qtd > 1){
                    tvQtd.setText(String.valueOf(qtd - 1));
                }
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qtd = Integer.parseInt(tvQtd.getText().toString());
                if(qtd < 5){
                    tvQtd.setText(String.valueOf(qtd + 1));
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(purchases.size() < 5){
                    Purchase purchase = new Purchase();
                    purchase.setFood(spinner.getSelectedItem().toString());
                    purchase.setQtd(tvQtd.getText().toString());
                    purchases.add(purchase);

                    refreshTable();
                    btnAddOrder.setEnabled(true);
                }else{
                    btnAdd.setEnabled(false);
                }
            }
        });

        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purchases.clear();
                refreshTable();
                btnAdd.setEnabled(true);
                btnAddOrder.setEnabled(false);
            }
        });

        btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order order = new Order();
                order.setName(etName.getText().toString());
                order.setAdress(etAdress.getText().toString());
                order.setPhone(etPhone.getText().toString());
                order.setTime(tvTime.getText().toString());
                order.setPurchases((ArrayList<Purchase>) purchases.clone());
                orders.add(order);

                purchases.clear();
                refreshTable();
                btnAddOrder.setEnabled(false);
                btnSee.setEnabled(true);
            }
        });

        btnSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createXml();
                intentXml.putExtra("stringXml",sWriter.toString());
                startActivity(intentXml);
                btnSee.setEnabled(false);
            }
        });
    }

    private void createXml(){
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            formatXmlExport(doc);
            fileXml(doc);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void formatXmlExport(Document doc){
        Element xml = doc.createElement("xml");
        for(Order order : orders){
            Element object = doc.createElement("order");
            object.setAttribute("created",order.getTime());
            object.setAttribute("name",order.getName());
            object.setAttribute("adress",order.getAdress());
            object.setAttribute("phone_number", order.getPhone());
            for(Purchase purchase : order.getPurchases()){
                Element item = doc.createElement("item");
                item.setAttribute("name", purchase.getFood());
                item.setAttribute("quantity", purchase.getQtd());
                object.appendChild(item);
            }
            xml.appendChild(object);
        }
        doc.appendChild(xml);
    }

    private void fileXml(Document doc){
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            DOMSource domSource = new DOMSource(doc);
            sWriter = new StringWriter();
            StreamResult sResult = new StreamResult(sWriter);
            transformer.transform(domSource, sResult);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    private void refreshTable(){
        llName.removeAllViews();
        llQtd.removeAllViews();
        dynamicTextViewName();
        dynamicTextViewQtd();
    }

    private void dynamicTextViewName(){
        for(Purchase purchase : purchases){
            TextView tv = new TextView(this);
            tv.setText(purchase.getFood());
            llName.addView(tv);
        }
    }

    private void dynamicTextViewQtd(){
        for(Purchase purchase : purchases){
            TextView tv = new TextView(this);
            tv.setText(String.valueOf(purchase.getQtd()));
            llQtd.addView(tv);
        }
    }

    private void runUiThread() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initComponent();
                initListinner();
                initItemsSpinner();
                runThreadTime();
            }
        });
    }

    private void runThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getXmlMenu();
                runUiThread();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void runThreadTime(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                DateFormat formatter = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
                String date = formatter.format(new GregorianCalendar().getTime());
                tvTime.setText(date);
            }
        }, 0, 1000);

    }


    private void initItemsSpinner(){
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, names);
        spinner.setAdapter(adapter);
    }

    private void creatArrayName(org.jsoup.select.Elements table) {
        for (org.jsoup.nodes.Element e : table) {
            names.add(e.text());
        }
    }

    private void getXmlMenu() {
        try {
            org.jsoup.nodes.Document doc = Jsoup.connect("https://www.w3schools.com/xml/simple.xml").get();
            org.jsoup.select.Elements table = doc.select("food name");
            creatArrayName(table);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
