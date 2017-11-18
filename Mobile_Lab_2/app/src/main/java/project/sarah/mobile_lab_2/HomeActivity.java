package project.sarah.mobile_lab_2;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import java.io.IOException;

/**
 * Created by sarah on 11/2/17.
 */

public class HomeActivity extends AppCompatActivity {

    private static final int GALLERY_CODE = 12;
    TextView tv, tv_temp;
    int cnt1, cnt2, cnt3=0;
    Button btn_heart1, btn_heart2, btn_home, btn_plus, btn_set, btn_temp;
    ImageView imgv;
    View temp_v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        tv=(TextView)findViewById(R.id.tv_main_home);
        tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/billabong.ttf"));

        tv_temp=(TextView)findViewById(R.id.tv_temp);
        btn_temp=(Button)findViewById(R.id.btn_heart3);
        temp_v=(View)findViewById(R.id.line_imgtemp);

        btn_heart1=(Button)findViewById(R.id.btn_heart1);
        btn_heart2=(Button)findViewById(R.id.btn_heart2);
        btn_home=(Button)findViewById(R.id.btn_home);
        btn_plus=(Button)findViewById(R.id.btn_plus);
        btn_set=(Button)findViewById(R.id.btn_set);
        imgv=(ImageView)findViewById(R.id.imgtemp);

        btn_heart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cnt1++;
                if(cnt1%2!=0)
                    btn_heart1.setBackgroundResource(R.drawable.fheart);
                else
                    btn_heart1.setBackgroundResource(R.drawable.heart);
            }
        });

        btn_heart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cnt2++;
                if(cnt2%2!=0)
                    btn_heart2.setBackgroundResource(R.drawable.fheart);
                else
                    btn_heart2.setBackgroundResource(R.drawable.heart);
            }
        });

        btn_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cnt3++;
                if(cnt3%2!=0)
                    btn_temp.setBackgroundResource(R.drawable.fheart);
                else
                    btn_temp.setBackgroundResource(R.drawable.heart);
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_home=new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent_home);
            }
        });

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in_temp=getIntent();
                String uname=in_temp.getExtras().getString("uname");
                tv_temp.setText(uname);
                Intent intent_pic=new Intent(Intent.ACTION_PICK);
                intent_pic.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent_pic.setType("image/*");
                startActivityForResult(Intent.createChooser(intent_pic, "Select Picture"), GALLERY_CODE);

                imgv.setVisibility(View.VISIBLE);
                tv_temp.setVisibility(View.VISIBLE);
                btn_temp.setVisibility(View.VISIBLE);
                temp_v.setVisibility(View.VISIBLE);
            }
        });

        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu p = new PopupMenu(getApplicationContext(), v);
                getMenuInflater().inflate(R.menu.option_menu, p.getMenu());
                p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent_logout=new Intent(HomeActivity.this, MainActivity.class);
                        finish();
                        startActivity(intent_logout);
                        return false;
                    }
                });
                p.show();
            }
        });
    }

    //This is for uploading image from gallary
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GALLERY_CODE:
                    SendPicture(data);
                    break;
                default:
                    break;
            }

        }
    }

    private void SendPicture(Intent data) {

        Uri imgUri = data.getData();
        String imagePath = getRealPathFromURI(imgUri);
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imgv.setImageBitmap(rotate(bitmap, exifDegree));

    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);

    }

    public int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    public Bitmap rotate(Bitmap src, float degree) {

        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }
}
