package com.example.el_muslim.wareny.itemsmodel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.el_muslim.wareny.LoginActivity;
import com.example.el_muslim.wareny.catItemsActivity;
import com.example.el_muslim.wareny.helper.HttpJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class deleteitem extends AsyncTask<String , String , String> {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_ITEM_ID = "item_id";
    private static final String BASE_URL = LoginActivity.UserLoginTask.BASE_URL;
    private String itemId;
    private int success;
    private ProgressDialog pDialog;
    private Context context ;


    public  deleteitem(Context c , String itemid)
    {
        this.context = c;
        this.itemId = itemid;
    }

    @Override

    protected String doInBackground(String... strings) {

        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String , String> map = new HashMap<String, String>() ;
        map.put(KEY_ITEM_ID,itemId);
        JSONObject jsonObject = httpJsonParser.makeHttpRequest(BASE_URL + "delete_item.php","POST" ,map );

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
                if (success == 1 )
                {
                    Toast.makeText(context,
                            "Delete item Successfully", Toast.LENGTH_LONG).show();
                }


            }
        });
    }
}
