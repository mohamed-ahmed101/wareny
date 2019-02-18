package com.example.el_muslim.wareny.categorymodel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.el_muslim.wareny.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class updateOrDeleteCategory extends AsyncTask<String , String , String> {



    private static final String KEY_SUCCESS = "success";
    private static final String KEY_CATEGORY_NAME = "category_name";
    private static final String KEY_CATEGORY_ID = "category_id";
    private static final String BASE_URL = "http://192.168.1.3/warenyphp/";
    private String categoryName;
    private String categoryId;
    private int success;
    private ProgressDialog pDialog;
    private Context context ;
    JSONObject jsonObject;

   public updateOrDeleteCategory(Context c,String categoryName , String categoryId){
       this.categoryId = categoryId;
       this.categoryName = categoryName;
       this.context = c;
   }

    @Override
    protected String doInBackground(String... strings) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String , String> map = new HashMap<String, String>() ;
        if(!categoryName.isEmpty()) {
            map.put(KEY_CATEGORY_NAME, categoryName);
            map.put(KEY_CATEGORY_ID, categoryId);
            jsonObject = httpJsonParser.makeHttpRequest(BASE_URL + "update_category.php", "POST", map);
        }
        else {
            map.put(KEY_CATEGORY_ID, categoryId);
            jsonObject = httpJsonParser.makeHttpRequest(BASE_URL + "delete_category.php", "POST", map);
        }
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
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        pDialog.dismiss();
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (success == 1 && categoryName.isEmpty())
                {
                    Toast.makeText(context,
                            "Delete Category Successfully", Toast.LENGTH_LONG).show();
                }

                else if(success == 1 && !categoryName.isEmpty()) {
                    Toast.makeText(context,
                            "Updated Category Successfully", Toast.LENGTH_LONG).show();
                }

                else {
                    Toast.makeText(context,
                            "Action Not Occured On DataBase",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
