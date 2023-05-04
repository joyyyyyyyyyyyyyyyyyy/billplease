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
            double newamt = 0.0;
            if(amt.getText().toString().trim().length()!=0 && paxno.getText().toString().trim().length()!=0) {
                double originalamt = Double.parseDouble(amt.getText().toString());
                if (!svc.isChecked() && !gst.isChecked()) {
                    newamt = originalamt;
                } else if (svc.isChecked() && !gst.isChecked()) {
                    newamt = originalamt*1.1;
                } else if (!svc.isChecked() && gst.isChecked()) {
                    newamt = originalamt*1.07;
                } else {
                    newamt = originalamt*1.17;
                }
            }

          //discount
            if (disc.getText().toString().trim().length() !=0) {
              newamt = 1 - Double.parseDouble(disc.getText().toString()) / 100;
          }

            String type = "in cash";
            if (payment.getCheckedRadioButtonId() == R.id.radiobtnpaynow) {
              type = " via PayNow to 9123 4567";
          }

          bill.setText("$" + String.format("%.2f", newamt));
            int totalperson = Integer.parseInt(paxno.getText().toString());
            if (totalperson !=1)
                payable.setText("$" + String.format("%.2f", newamt));
            else
                payable.setText("$" + newamt + type);
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