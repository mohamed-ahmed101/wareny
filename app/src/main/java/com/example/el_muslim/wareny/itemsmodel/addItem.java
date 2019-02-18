package com.example.el_muslim.wareny.itemsmodel;
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

public class addItem extends AsyncTask<String , String , String> {


    private static final String KEY_SUCCESS = "success";
    private static final String KEY_CATEGORY_ID = "cat_id";
    private static final String KEY_ITEM_NAME = "item_name";
    private static final String BASE_URL = "http://192.168.1.3/warenyphp/";
    private String categoryId;
    private String itemName;
    private int success;
    private ProgressDialog pDialog;
    private Context context ;


    public addItem(Context context,String itemName , String categoryId){
        this.categoryId = categoryId;
        this.context = context;
        this.itemName = itemName;
    }

    @Override
    protected String doInBackground(String... strings) {

        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String , String> map = new HashMap<String, String>() ;
        map.put(KEY_CATEGORY_ID ,categoryId);
        map.put(KEY_ITEM_NAME,itemName);
        JSONObject jsonObject = httpJsonParser.makeHttpRequest(BASE_URL + "add_item.php","POST" ,map);
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
        pDialog.setMessage("Adding Item. Please wait...");
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
                if (success == 1)
                {
                    Toast.makeText(context,
                            "New Item Added", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(context,
                            "Some error occurred while adding Your Item",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
