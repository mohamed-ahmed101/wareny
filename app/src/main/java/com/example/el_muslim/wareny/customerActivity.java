package com.example.el_muslim.wareny;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.el_muslim.wareny.helper.HttpJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class customerActivity extends AppCompatActivity {

    public static boolean USER = true;
    ListView lst_supplier;
    ArrayList<String> suppliersNames;
    ArrayList<String> suppliersIds;
    ArrayAdapter<String>arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        lst_supplier = (ListView) findViewById(R.id.list_view_supplier);
        suppliersNames = new ArrayList<>();
        suppliersIds = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(customerActivity.this,android.R.layout.simple_list_item_1,suppliersNames);
        lst_supplier.setAdapter(arrayAdapter);

        new customer().execute();


        lst_supplier.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),storeActivity.class);
                intent.putExtra("supplierID",Integer.parseInt(suppliersIds.get(position)));
                intent.putExtra("supplierName",suppliersNames.get(position));
                startActivity(intent);
            }
        });

    }

    public class customer extends AsyncTask<Void, Void, Boolean> {

        private static final String KEY_SUCCESS = "success";
        private static final String KEY_DATA = "data";
        private  String URL = "http://192.168.1.2/warenyphp/";
        private int success;
        private ProgressDialog pDialog;





        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            HttpJsonParser httpJsonParser = new HttpJsonParser();
            JSONObject jsonObject;
            jsonObject = httpJsonParser.makeHttpRequest(URL + "fetch_all_suppliers.php", "GET", new HashMap<String, String>());
            try {
                success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray items;
                if(success==1)
                {
                    items=jsonObject.getJSONArray(KEY_DATA);

                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = items.getJSONObject(i);
                        suppliersNames.add(item.getString("supplier_name"));
                        suppliersIds.add(item.getString("supplier_id"));

                    }

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean b) {
            pDialog.dismiss();
            ((Activity) customerActivity.this).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (success == 1)
                    {
                        Toast.makeText(customerActivity.this,
                                "Get all suppliers successfully", Toast.LENGTH_LONG).show();
                        arrayAdapter.notifyDataSetChanged();
                    }
                    else {
                        Toast.makeText(customerActivity.this,
                                "no suppliers found",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(customerActivity.this);
            pDialog.setMessage("Fetching all suppliers. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
    }

}
