package hu.ait.android.penz;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hu.ait.android.penz.data.MoneyResult;
import hu.ait.android.penz.network.MoneyAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConvertActivity extends AppCompatActivity {

    private LinearLayout linLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);

        final Spinner spinnerSend = (Spinner) findViewById(R.id.spinnerSend);
        final Spinner spinnerReceive = (Spinner) findViewById(R.id.spinnerReceive);
        final EditText etValue = (EditText) findViewById(R.id.etValue);
        Button btnConvert = (Button) findViewById(R.id.btnConvert);

        linLayout = (LinearLayout) findViewById(
                R.id.linLayout);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.fixer.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final MoneyAPI moneyAPI = retrofit.create(MoneyAPI.class);

       
        btnConvert.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View view) {

                final String send = spinnerSend.getSelectedItem().toString();
                final String receive = spinnerReceive.getSelectedItem().toString();

                if (TextUtils.equals(receive, send)) {
                    Toast.makeText(ConvertActivity.this, etValue.getText(), Toast.LENGTH_LONG).show();
                    finish();
                } else {

                    if (!TextUtils.isEmpty(etValue.getText())) {
                        Call<MoneyResult> call = moneyAPI.getRates(send);

                        call.enqueue(new Callback<MoneyResult>() {
                            @Override
                            public void onResponse(Call<MoneyResult> call, Response<MoneyResult> response) {

                                handleMoneyResult(response, etValue, receive);

                            }

                            @Override
                            public void onFailure(Call<MoneyResult> call, Throwable t) {
                                Toast.makeText(ConvertActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

                    } else {

                        if (TextUtils.isEmpty(etValue.getText())) {
                            etValue.setError("Error! Try Again");
                        }
                    }
                }
            }
        });

    }

    private void handleMoneyResult(Response<MoneyResult> response, EditText etValue, String receive) {
        Editable textValue = etValue.getText();
        String digits = textValue.toString();
        Double money = Double.parseDouble(digits);

        if (TextUtils.equals(receive, "AUD")){
            Double value = money*response.body().getRates().getaUD();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "BGN")) {
            Double value = money*response.body().getRates().getbGN();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "BRL")) {
            Double value = money*response.body().getRates().getbRL();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "CAD")) {
            Double value = money*response.body().getRates().getcAD();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "CHF")) {
            Double value = money*response.body().getRates().getcHF();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "CNY")){
            Double value = money*response.body().getRates().getcNY();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "CZK")){
            Double value = money*response.body().getRates().getcZK();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "DKK")){
            Double value = money*response.body().getRates().getdKK();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "GBP")){
            Double value = money*response.body().getRates().getgBP();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "HKD")){
            Double value = money*response.body().getRates().gethKD();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "HRK")){
            Double value = money*response.body().getRates().gethRK();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "HUF")){
            Double value = money*response.body().getRates().gethUF();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "IDR")){
            Double value = money*response.body().getRates().getiDR();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "ILS")){
            Double value = money*response.body().getRates().getiLS();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "INS")){
            Double value = money*response.body().getRates().getiNR();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "JPY")){
            Double value = money*response.body().getRates().getjPY();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "KRW")){
            Double value = money*response.body().getRates().getkRW();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "MXN")){
            Double value = money*response.body().getRates().getmXN();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "MYR")){
            Double value = money*response.body().getRates().getmYR();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "NOK")){
            Double value = money*response.body().getRates().getnOK();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "NZD")){
            Double value = money*response.body().getRates().getnZD();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "PHP")){
            Double value = money*response.body().getRates().getpHP();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "PLN")){
            Double value = money*response.body().getRates().getpLN();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "RON")){
            Double value = money*response.body().getRates().getrON();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "RUB")){
            Double value = money*response.body().getRates().getrUB();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "SEK")){
            Double value = money*response.body().getRates().getsEK();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "SGD")){
            Double value = money*response.body().getRates().getsGD();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "THB")){
            Double value = money*response.body().getRates().gettHB();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "TRY")){
            Double value = money*response.body().getRates().gettRY();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "ZAR")){
            Double value = money*response.body().getRates().getzAR();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "EUR")) {
            Double value = money*response.body().getRates().geteUR();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }

        else if (TextUtils.equals(receive, "USD")) {
            Double value = money*response.body().getRates().getuSD();
            Toast.makeText(ConvertActivity.this, ""+ value,
                    Toast.LENGTH_LONG).show();
            
        }
        finish();
    }
}
