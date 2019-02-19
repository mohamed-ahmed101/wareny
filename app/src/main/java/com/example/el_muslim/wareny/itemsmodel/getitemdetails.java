package com.example.el_muslim.wareny.itemsmodel;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.el_muslim.wareny.LoginActivity;
import com.example.el_muslim.wareny.catItemsActivity;
import com.example.el_muslim.wareny.helper.HttpJsonParser;
import com.example.el_muslim.wareny.itemDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class getitemdetails extends AsyncTask<String , String , String> {

    private static final String KEY_DATA = "data";
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_ITEM_ID = "item_id";
    private static final String KEY_ITEM_NAME = "item_name";
    private static final String KEY_ITEM_PRICE = "price";
    private static final String KEY_ITEM_DESCREIPTION = "description";
    private static final String KEY_ITEM_SIZES = "sizes";
    private static final String BASE_URL = LoginActivity.UserLoginTask.BASE_URL;
    private String itemId;
    private String itemName;
    private String price;
    private String description;
    private String sizes;
    private int success;
    private ProgressDialog pDialog;
    private Context context ;
    JSONObject jsonObject;


    public  getitemdetails (Context c, String item_id)
    {
        this.context=c;
        this.itemId=item_id;

    }
    @Override
    protected String doInBackground(String... strings) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String , String> map = new HashMap<String, String>() ;
        map.put(KEY_ITEM_ID,itemId);
         jsonObject = httpJsonParser.makeHttpRequest(BASE_URL + "fetch_item_details.php","GET" ,map );

        try {
            success = jsonObject.getInt(KEY_SUCCESS);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Fetching Item Details. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        pDialog.dismiss();
        JSONArray item;
        if(success==1)
        {
            try {
                item= jsonObject.getJSONArray(KEY_DATA);
                JSONObject j = item.getJSONObject(0);
                itemDetails.nametxt.setText(j.getString(KEY_ITEM_NAME));
                itemDetails.describtiontxt.setText(j.getString(KEY_ITEM_DESCREIPTION));
                itemDetails.pricetxt.setText(j.getString(KEY_ITEM_PRICE) +" EGP");
                itemDetails.sizetxt.setText(j.getString(KEY_ITEM_SIZES));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
