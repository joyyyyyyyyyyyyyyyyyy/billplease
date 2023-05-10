package sg.edu.rp.c346.id22022096.billplease;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText amt;
    EditText paxno;
    EditText disc;
    TextView bill;
    TextView payable;
    ToggleButton svc;
    ToggleButton gst;
    RadioGroup payment;
    Button split;
    Button reset;


    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        amt = findViewById(R.id.editamt);
        paxno = findViewById(R.id.editpaxno);
        disc = findViewById(R.id.editdisc);
        bill = findViewById(R.id.totalbill);
        payable = findViewById(R.id.payableperpax);
        svc = findViewById(R.id.svccharge);
        gst = findViewById(R.id.gst);
        payment = findViewById(R.id.paymentmode);
        split = findViewById(R.id.split);
        reset = findViewById(R.id.reset);

        split.setOnClickListener(v -> {

          //discount
            double newamt = 0.0;

            if(amt.getText().toString().trim().length()!=0 && paxno.getText().toString().trim().length()!=0) {
                if (!svc.isChecked() && !gst.isChecked()) {
                    newamt = Double.parseDouble(amt.getText().toString());
                } else if (svc.isChecked() && !gst.isChecked()) {
                    newamt = Double.parseDouble(amt.getText().toString())*1.1;
                } else if (!svc.isChecked() && gst.isChecked()) {
                    newamt = Double.parseDouble(amt.getText().toString())*1.07;
                } else {
                    newamt = Double.parseDouble(amt.getText().toString())*1.17;
                }

                if (disc.getText().toString().trim().length() !=0) {
                    newamt *= (1-Double.parseDouble(disc.getText().toString()) / 100);
                }

            }

            String method = " in cash";
            if (payment.getCheckedRadioButtonId() == R.id.radiobtnpaynow) {
                method = " via PayNow to 9123 4567";
            }

          int totalperson = Integer.parseInt(paxno.getText().toString());
          double splitamt = newamt/totalperson;

          bill.setText("Total BIll Amount: $" + String.format("%.2f", newamt));
            if (totalperson !=1)
                payable.setText("Each Person Pays: $" + String.format("%.2f", splitamt) + method);
            else
                payable.setText("$" + newamt + method);
        });

        reset.setOnClickListener(v -> {
            amt.setText("");
            paxno.setText("");
            svc.setChecked(false);
            gst.setChecked(false);
            disc.setText("");
            payment.check(R.id.radiobtncash);
        });

    }
}