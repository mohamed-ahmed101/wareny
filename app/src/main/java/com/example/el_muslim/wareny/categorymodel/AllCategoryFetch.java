package com.example.el_muslim.wareny.categorymodel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.el_muslim.wareny.LoginActivity;
import com.example.el_muslim.wareny.customAdapter;
import com.example.el_muslim.wareny.helper.HttpJsonParser;
import com.example.el_muslim.wareny.storeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.el_muslim.wareny.storeActivity.arrayAdapter;

public class AllCategoryFetch extends AsyncTask<String,String,String> {

    private static final String KEY_DATA = "data";
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_CATEGORY_NAME = "category_name";
    private static final String KEY_SUPPLIER_ID = "sup_id";
    private static final String KEY_CATEGORY_ID= "category_id";
    private static final String BASE_URL = LoginActivity.UserLoginTask.BASE_URL;
    private String categoryName;
    private int supId;
    private String categoryId;
    private int success;
    private ProgressDialog pDialog;
    private Context context ;
    public static ArrayList<String> categoriesName;
    public static ArrayList<String> categoriesId;



    public  AllCategoryFetch(Context context,int supId)
    {
        this.context=context;
        this.supId = supId;
        categoriesName = new ArrayList<String>();
        categoriesId = new ArrayList<String>();
    }
    @Override
    protected String doInBackground(String... strings) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String , String> map = new HashMap<String, String>() ;
        map.put(KEY_SUPPLIER_ID,String.valueOf(supId));
        JSONObject jsonObject = httpJsonParser.makeHttpRequest(BASE_URL + "fetch_all_categories_of_supplier.php","GET" ,map );

        try {
            success = jsonObject.getInt(KEY_SUCCESS);
            JSONArray categories;
            if(success==1)
            {
                categories=jsonObject.getJSONArray(KEY_DATA);

                for (int i = 0; i < categories.length(); i++) {
                    JSONObject category = categories.getJSONObject(i);
                   // categoriesName.add();
                  //  categoriesId.add(category.getString(KEY_CATEGORY_ID));
                    storeActivity.categoriesName.add(category.getString(KEY_CATEGORY_NAME));
                    storeActivity.categoriesId.add(category.getString(KEY_CATEGORY_ID));
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
        pDialog.setMessage("Loading Your Categories. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        //super.onPostExecute(o);
        pDialog.dismiss();


               // storeActivity.arrayAdapter.notifyDataSetChanged();


    }
}
