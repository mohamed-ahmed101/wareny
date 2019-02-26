package com.example.el_muslim.wareny;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.el_muslim.wareny.imagehelper.addimage;
import com.example.el_muslim.wareny.imagehelper.fetchitemimages;
import com.example.el_muslim.wareny.itemsmodel.getitemdetails;
import com.example.el_muslim.wareny.itemsmodel.updateitem;
import com.kosalgeek.android.photoutil.ImageLoader;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class itemDetails extends AppCompatActivity implements View.OnClickListener {
    public static ArrayList<Bitmap> arrayList;
    public static imageadapter imgadapter;
    public static ArrayList<String> imagesName ;
    static ViewPager viewPager;
  public static   String itemId;
    String itemName;
    int itemPosition;
    EditText editText;
   public static TextView pricetxt;
    public  static  TextView describtiontxt;
    public static TextView sizetxt;
    public  static  TextView nametxt;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!customerActivity.USER) {
            getMenuInflater().inflate(R.menu.cameraicon, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        //photoPickerIntent.setType("image/*");
        // startActivityForResult(photoPickerIntent, 0);
        // Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
        //      startActivityForResult(takePictureIntent, 0);
   // }
       openBackCamera();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        final Intent intent = getIntent();
        itemId = intent.getStringExtra("itemID");
        itemName = intent.getStringExtra("itemName");
        itemPosition = intent.getIntExtra("itemPosition",-1);
        getSupportActionBar().setTitle(itemName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imagesName = new ArrayList<String>();




        pricetxt = (TextView) findViewById(R.id.pricetextview);
        describtiontxt = (TextView) findViewById(R.id.descriptiontextview);
        sizetxt = (TextView) findViewById(R.id.sizestextview);
        nametxt = (TextView) findViewById(R.id.nametextview);

        try {
            String str = new getitemdetails(itemDetails.this,itemId).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        arrayList = new ArrayList<Bitmap>();
        imgadapter = new imageadapter(this);
        viewPager.setAdapter(imgadapter);
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
          @Override
             public void transformPage(@NonNull View view, float v) {
              view.setRotationY(v * -30);
              }
            });
       // arrayList.add(BitmapFactory.decodeResource(getResources(),R.drawable.x));
       // imgadapter.notifyDataSetChanged();
        if(!customerActivity.USER) {
            pricetxt.setOnClickListener(this);
            describtiontxt.setOnClickListener(this);
            sizetxt.setOnClickListener(this);
            nametxt.setOnClickListener(this);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager, true);

        try {
            String strr = new fetchitemimages(itemDetails.this,itemId).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private String pictureImagePath = "";
    private void openBackCamera() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + ".jpg";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        pictureImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
        File file = new File(pictureImagePath);
        Uri outputFileUri = Uri.fromFile(file);
        Intent c = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        c.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(c, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            try {
              //  final Uri imageUri = data.getData();
               // final InputStream imageStream = getContentResolver().openInputStream(imageUri);
              //  final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
              //  Bundle extras = data.getExtras();
              //  Bitmap imageBitmap = (Bitmap) extras.get("data");
              //  arrayList.add(imageBitmap);
              //  imgadapter.notifyDataSetChanged();

                File imgFile = new  File(pictureImagePath);
                    if(imgFile.exists()){
                   // Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        Bitmap myBitmap = ImageLoader.init().from(pictureImagePath).requestSize(1024,1024).getBitmap();
                        Bitmap myBitmap2 = ImageLoader.init().from(pictureImagePath).requestSize(1024,1024).getBitmap();



                       // new addimage(itemDetails.this,itemId,myBitmap);
                       // Bitmap myBitmap2 = BitmapFactory.decodeResource(getResources(),R.drawable.kp);


                            arrayList.add(myBitmap);
                            imgadapter.notifyDataSetChanged();
                            new addimage(itemDetails.this, itemId, myBitmap2);


                      imgFile.delete();

                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(itemDetails.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(itemDetails.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.pricetextview || v.getId() == R.id.descriptiontextview || v.getId() == R.id.sizestextview ||v.getId() == R.id.nametextview) {
            AlertDialog.Builder alert = new AlertDialog.Builder(itemDetails.this);
            editText = new EditText(this);
            alert.setView(editText);

            if (v.getId() == R.id.pricetextview) {
                editText.setText(((TextView) v).getText().toString().split(" ")[0]);
            }
            else{
                editText.setText(((TextView) v).getText().toString());
            }

            editText.setSelection(editText.getText().length());
            alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (v.getId() == R.id.pricetextview) {
                        ((TextView) v).setText(editText.getText()+" EGP");
                    }
                    else {
                        ((TextView) v).setText(editText.getText());
                    }
                    new updateitem(itemDetails.this,itemId,nametxt.getText().toString(),pricetxt.getText().toString(),describtiontxt.getText().toString(),sizetxt.getText().toString()).execute();
                    getSupportActionBar().setTitle(nametxt.getText().toString());
                    catItemsActivity.itemsName.set(itemPosition,nametxt.getText().toString());
                    catItemsActivity.arrayAdapter.notifyDataSetChanged();
                }
            });
            alert.setNegativeButton("cancel", null);
            alert.setTitle("Edit");
            alert.create();
            alert.show();

        }
    }
}