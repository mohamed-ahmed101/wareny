package com.example.el_muslim.wareny;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Base64;

import com.example.el_muslim.wareny.helper.HttpJsonParser;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

import static com.example.el_muslim.wareny.LoginActivity.UserLoginTask.BASE_URL;

public class addimage{
Context context ;
    private static final String KEY_SUCCESS = "image_name";
    private static final String KEY_CATEGORY_ID = "item_id";
    private static final String KEY_IMAGE_SRC = "image_src";
    String itemID ;
    Bitmap photo ;



     public  addimage(Context c,String IID,Bitmap bit)
 {
     this.context = c;
     this.itemID = IID;
     this.photo = bit;


       // Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.kp);



        String imageSrc = ImageBase64.encode(photo);

        HashMap<String,String> data = new HashMap<>();
        data.put(KEY_IMAGE_SRC, imageSrc);
        data.put(KEY_CATEGORY_ID,itemID );

        PostResponseAsyncTask task = new PostResponseAsyncTask(context, data, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                itemDetails.imagesName.add(s);
                //ya 5wl
            }
        });

        task.execute(LoginActivity.UserLoginTask.BASE_URL+"/add_image_to_data_base.php");
        task.setEachExceptionsHandler(new EachExceptionsHandler() {
            @Override
            public void handleIOException(IOException e) {

            }

            @Override
            public void handleMalformedURLException(MalformedURLException e) {

            }

            @Override
            public void handleProtocolException(ProtocolException e) {

            }

            @Override
            public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {

            }
        });
 }


}
