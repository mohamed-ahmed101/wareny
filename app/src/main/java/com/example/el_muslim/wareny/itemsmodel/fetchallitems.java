package com.example.el_muslim.wareny.itemsmodel;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.el_muslim.wareny.LoginActivity;
import com.example.el_muslim.wareny.catItemsActivity;
import com.example.el_muslim.wareny.helper.HttpJsonParser;
import com.example.el_muslim.wareny.storeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class fetchallitems extends AsyncTask<String , String , String> {

    private static final String KEY_DATA = "data";
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_CATEGORY_ID = "cat_id";
    private static final String KEY_ITEM_NAME = "item_name";
    private static final String KEY_ITEM_ID = "item_id";
    private static final String BASE_URL = LoginActivity.UserLoginTask.BASE_URL;
    private String categoryId;
    //private String itemName;
    private int success;
    private ProgressDialog pDialog;
    private Context context ;

    public  fetchallitems (Context c, String cat_id)
    {
        this.context=c;
        this.categoryId=cat_id;

    }
    @Override
    protected String doInBackground(String... strings) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String , String> map = new HashMap<String, String>() ;
        map.put(KEY_CATEGORY_ID,categoryId);
        JSONObject jsonObject = httpJsonParser.makeHttpRequest(BASE_URL + "fetch_all_items_of_category.php","GET" ,map );

        try {
            success = jsonObject.getInt(KEY_SUCCESS);
            JSONArray items;
            if(success==1)
            {
                items=jsonObject.getJSONArray(KEY_DATA);

                for (int i = 0; i < items.length(); i++) {
                    JSONObject item = items.getJSONObject(i);
                    // categoriesName.add();
                    //  categoriesId.add(category.getString(KEY_CATEGORY_ID));
                    catItemsActivity.itemsName.add(item.getString(KEY_ITEM_NAME));
                    catItemsActivity.itemsId.add(item.getString(KEY_ITEM_ID));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Fetching All Items. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        pDialog.dismiss();
    }
}
