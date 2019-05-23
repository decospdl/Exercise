package br.sc.write_xml;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class IntentXml extends AppCompatActivity {
    private String xml;
    private TextView tvXml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_xml);
        xml = getIntent().getStringExtra("stringXml");
        tvXml = findViewById(R.id.tvXml);
        tvXml.setText(xml);
    }
}
