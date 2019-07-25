package com.kandktech.esewaintegrate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.esewa.android.sdk.payment.ESewaConfiguration;
import com.esewa.android.sdk.payment.ESewaPayment;
import com.esewa.android.sdk.payment.ESewaPaymentActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private int REQUEST_CODE_PAYMENT​ = 1001;
    Button btn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ESewaConfiguration eSewaConfiguration = new ESewaConfiguration()
                .clientId("JB0BBQ4aD0UqIThFJwAKBgAXEUkEGQUBBAwdOgABHD4DChwUAB0R")
                .secretKey("BhwIWQQADhIYSxILExMcAgFXFhcOBwAKBgAXEQ==")
                .environment(ESewaConfiguration.ENVIRONMENT_TEST);

        btn = findViewById(R.id.btnPay);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ESewaPayment eSewaPayment = new ESewaPayment("1000","Book","sa0910","https://ir-user.esewa.com.np/epay/main");

                Intent intent = new Intent(getApplicationContext(), ESewaPaymentActivity.class);
                intent.putExtra(ESewaConfiguration.ESEWA_CONFIGURATION,eSewaConfiguration);
                intent.putExtra(ESewaPayment.ESEWA_PAYMENT,eSewaPayment);
                startActivityForResult(intent,REQUEST_CODE_PAYMENT​);


            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PAYMENT​){
            if (resultCode == RESULT_OK) {
                String message = data.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE);

                if(message != null)
                    try {
                        JSONObject jObj = new JSONObject(message);
                        String productId =  jObj.optString("productId");
                        String totalAmount = jObj.optString("totalAmount");
                        String refID = jObj.optJSONObject("transactionDetails").optString("referenceId");
                        message = jObj.getJSONObject("message").optString("successMessage");
                        System.out.println("Pid : "+productId);
                        System.out.println("refid : "+refID);
                        System.out.println("message : "+message);
                        System.out.println("totlaAmount : "+totalAmount);
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Oops ! something went wrong", Toast.LENGTH_SHORT).show();
                    }



            }else if (resultCode == RESULT_CANCELED){
                Toast.makeText(this, "Cancel By User", Toast.LENGTH_SHORT).show();
            }else if (resultCode == ESewaPayment.RESULT_EXTRAS_INVALID){
                String s = data.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE);
                System.out.println("Proof of payment1 : "+s);
            }
        }
    }

}
