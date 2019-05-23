package br.sc.layouts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll = findViewById(R.id.linearLayout);
        buildLinearLayout();
    }

    private int getHour() {
        GregorianCalendar gc = new GregorianCalendar();
        DateFormat formatter = new SimpleDateFormat("HH");
        String hour = formatter.format(gc.getTime());
        return Integer.parseInt(hour);
    }


    private void dynamicEditText(int hour) {
        EditText et = new EditText(this);
        et.setText(hour + ":00 - ");
        TableLayout.LayoutParams params = new TableLayout.LayoutParams();
        params.setMargins(16,2,16,2);
        et.setLayoutParams(params);
        et.setTextSize(11);
        ll.addView(et);
    }

    private void buildLinearLayout() {
        int range = (getHour() > 18) ? 18 : getHour();
        for (int i = 8; i < range; i++) {
            if (i != 12)
                dynamicEditText(i);
        }
    }
}
