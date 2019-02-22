package com.example.el_muslim.wareny.categorymodel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.el_muslim.wareny.LoginActivity;
import com.example.el_muslim.wareny.helper.HttpJsonParser;
import com.example.el_muslim.wareny.storeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CategoryHelperAdd extends AsyncTask <String , String , String>  {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_CATEGORY_NAME = "category_name";
    private static final String KEY_SUPPLIER_ID = "sup_id";
    private static final String BASE_URL = LoginActivity.UserLoginTask.BASE_URL;
    private String categoryName;
    private String supId;
    private int success;
    private ProgressDialog pDialog;
    private Context context ;

     public CategoryHelperAdd(Context context)
     {
         this.context = context;

     }

    public void  AddCategory( String catName , String supId){
         this.categoryName = catName ;
         this.supId = supId;
         if(!categoryName.isEmpty()&&!supId.isEmpty())
         {execute();}
         else {
             Toast.makeText(context,
                     "missing mandatory parameters", Toast.LENGTH_LONG).show();
         }

     }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Adding category. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String , String> map = new HashMap<String, String>() ;
        map.put(KEY_CATEGORY_NAME ,categoryName);
        map.put(KEY_SUPPLIER_ID,supId);
        JSONObject jsonObject = httpJsonParser.makeHttpRequest(BASE_URL + "add_category.php","POST" ,map );
        try {
            success = jsonObject.getInt(KEY_SUCCESS);
            storeActivity.categoriesId.add(String.valueOf(jsonObject.getInt("categoryId")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected void onPostExecute(String s) {
     //   super.onPostExecute(s);
        pDialog.dismiss();
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (success == 1)
                {
                    Toast.makeText(context,
                            "New Category Added", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(context,
                            "Some error occurred while adding Your Category",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
