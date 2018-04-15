package suraj.dev.com.uploadimage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.service.quicksettings.Tile;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ImageView imgView;
    EditText Imgtitle;
    static final int CAM_REQUEST =1;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgView = findViewById(R.id.img);
        Imgtitle = findViewById(R.id.imgtitle);
    }

    public void ChooseImg(View view) {

        SelectImg();

    }

    public void Upload(View view) {
        upoadImage();
    }

    void SelectImg()
    {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAM_REQUEST);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAM_REQUEST && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
                imgView.setImageBitmap(bitmap);
                imgView.setVisibility(View.VISIBLE);
                Imgtitle.setVisibility(View.VISIBLE);


        }

    }

    private String imageToString()
    {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte [] imgByte = byteArrayOutputStream.toByteArray();
       return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }

    void upoadImage()
    {
        String Image = imageToString();
        String Title = Imgtitle.getText().toString();

        RetrofitInterface retrofitInterface = Util.getApiClient().create(RetrofitInterface.class);
        Call<Bean> call =  retrofitInterface.uploadImage(Title,Image);

        call.enqueue(new Callback<Bean>() {
            @Override
            public void onResponse(Call<Bean> call, Response<Bean> response) {

                Bean bean = response.body();
                Toast.makeText(MainActivity.this, "Server Response"+bean.getResponse(), Toast.LENGTH_SHORT).show();
                imgView.setVisibility(View.GONE);
                Imgtitle.setVisibility(View.GONE);
                Imgtitle.setText("");

            }

            @Override
            public void onFailure(Call<Bean> call, Throwable t) {

            }
        });


    }
}
