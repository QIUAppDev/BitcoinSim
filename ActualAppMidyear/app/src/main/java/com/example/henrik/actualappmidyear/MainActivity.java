package com.example.henrik.actualappmidyear;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    static TextView BTCLTC,BTCXRP,BTCETH,wallet,LTCNum,XRPNum,ETHNum;
    Button refresh;
    EditText LTCSell, LTCBuy,XRPSell, XRPBuy,ETHSell, ETHBuy;
    double btcOwned = 1;
    double LTCOwned,XRPOwned,ETHOwned;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BTCLTC = findViewById(R.id.BTC_LTC);
        BTCXRP = findViewById(R.id.BTC_XRP);
        BTCETH = findViewById(R.id.BTC_ETH);
        refresh = findViewById(R.id.refresh);
        LTCSell = findViewById(R.id.LTCSell);
        LTCBuy = findViewById(R.id.LTCBuy);
        XRPSell = findViewById(R.id.XRPSell);
        XRPBuy = findViewById(R.id.XRPBuy);
        ETHSell = findViewById(R.id.ETHSell);
        ETHBuy = findViewById(R.id.ETHBuy);
        wallet = findViewById(R.id.Wallet);
        LTCNum = findViewById(R.id.LTCOwned);
        XRPNum = findViewById(R.id.XRPOwned);
        ETHNum = findViewById(R.id.ETHOwned);
        wallet.setText(Double.toString(btcOwned));
        refresh.setOnClickListener(this);
        String url = "https://bittrex.com/api/v1.1/public/getticker?market=btc-ltc";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        BTCLTC.setText(response.toString().substring(response.toString().indexOf("Last") + 6, response.toString().length() - 2));
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsObjRequest);
        url = "https://bittrex.com/api/v1.1/public/getticker?market=btc-xrp";
        jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        BTCXRP.setText(response.toString().substring(response.toString().indexOf("Last") + 6, response.toString().length() - 2));
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });
        queue.add(jsObjRequest);
        url = "https://bittrex.com/api/v1.1/public/getticker?market=btc-eth";
        jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        BTCETH.setText(response.toString().substring(response.toString().indexOf("Last") + 6, response.toString().length() - 2));
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });
        queue.add(jsObjRequest);
    }
    public void update() {
        double remaining = Double.parseDouble(sciNotToDouble(wallet.getText().toString()))
                + Double.parseDouble(sciNotToDouble(BTCLTC.getText().toString())) * (Double.parseDouble(sciNotToDouble(LTCSell.getText().toString())) - Double.parseDouble(sciNotToDouble(LTCBuy.getText().toString())))
                + Double.parseDouble(sciNotToDouble(BTCXRP.getText().toString())) * (Double.parseDouble(sciNotToDouble(XRPSell.getText().toString())) - Double.parseDouble(sciNotToDouble(XRPBuy.getText().toString())))
                + Double.parseDouble(sciNotToDouble(BTCETH.getText().toString())) * (Double.parseDouble(sciNotToDouble(ETHSell.getText().toString())) - Double.parseDouble(sciNotToDouble(ETHBuy.getText().toString())));
        if (remaining < 0) {
            Toast toast = Toast.makeText(getApplicationContext(), "Not Enough Bitcoins", Toast.LENGTH_SHORT);
            toast.show();
        } else if (Double.valueOf(LTCSell.getText().toString()) > LTCOwned || Double.valueOf(XRPSell.getText().toString()) > XRPOwned || Double.valueOf(ETHSell.getText().toString()) > ETHOwned) {
            Toast toast = Toast.makeText(getApplicationContext(), "Not Enough Currency to Sell", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            btcOwned = remaining;
            LTCOwned = LTCOwned - Double.parseDouble(LTCSell.getText().toString()) + Double.parseDouble(LTCBuy.getText().toString());
            XRPOwned = XRPOwned - Double.parseDouble(XRPSell.getText().toString()) + Double.parseDouble(XRPBuy.getText().toString());
            ETHOwned = ETHOwned - Double.parseDouble(ETHSell.getText().toString()) + Double.parseDouble(ETHBuy.getText().toString());
            LTCSell.setText("0");
            LTCBuy.setText("0");
            XRPSell.setText("0");
            XRPBuy.setText("0");
            ETHSell.setText("0");
            ETHBuy.setText("0");
            LTCNum.setText(Double.toString(LTCOwned));
            ETHNum.setText(Double.toString(ETHOwned));
            XRPNum.setText(Double.toString(XRPOwned));
            String url = "https://bittrex.com/api/v1.1/public/getticker?market=btc-ltc";
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            BTCLTC.setText(response.toString().substring(response.toString().indexOf("Last") + 6, response.toString().length() - 2));
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub

                        }
                    });
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(jsObjRequest);
            url = "https://bittrex.com/api/v1.1/public/getticker?market=btc-xrp";
            jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            BTCXRP.setText(response.toString().substring(response.toString().indexOf("Last") + 6, response.toString().length() - 2));
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub

                        }
                    });
            queue.add(jsObjRequest);
            url = "https://bittrex.com/api/v1.1/public/getticker?market=btc-eth";
            jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            BTCETH.setText(response.toString().substring(response.toString().indexOf("Last") + 6, response.toString().length() - 2));
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub

                        }
                    });
            queue.add(jsObjRequest);
            wallet.setText( Double.toString(btcOwned));
        }
    }
    @Override
    public void onClick(View view){
        update();
    }
    public String sciNotToDouble(String s){
        String temp = s;
        if (s.contains("E")){
            temp = Double.toString(Math.pow(Double.parseDouble(s.substring(0,s.indexOf("E"))),Double.parseDouble(s.substring(s.indexOf("E")+1))));
        }
        return s;
    }
}
