package co.edu.icesi.yeye.camara;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    public final static int ID_REQUEST_CAMERA = 12;

    private ImageView iv_foto;
    private EditText et_descripcion;
    private Button btn_takepic;
    private Button btn_savepic;

    private Bitmap image;

    private PhotoDatabase db;

    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("Prueba1").setValue("Funciona");

        //GuARDAR LAS FOTOS EN LA BASE DE DATOS

        db = new PhotoDatabase(this);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        }, 11);


        iv_foto = findViewById(R.id.iv_foto);
        et_descripcion = findViewById(R.id.et_descripcion);
        btn_savepic = findViewById(R.id.btn_savepic);
        btn_takepic = findViewById(R.id.btn_takepic);


        btn_takepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, ID_REQUEST_CAMERA);
            }
        });


        btn_savepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fotoname = UUID.randomUUID().toString() + ".png";
                String path = Environment.getExternalStorageDirectory() + "/" + fotoname;

                FileOutputStream fos = null;
                try {
                    fos=new FileOutputStream(new File(path));
                    image.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    //Crear la referencia de la foto que acabamos de -guardar
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Foto registro=new Foto(UUID.randomUUID().toString(),
                            fotoname,
                            path,
                            dateFormat.format(Calendar.getInstance().getTime()),
                            et_descripcion.getText().toString());

                    db.createFoto(registro);

                    Log.e("DEBUG","FOTOS"+ db.getAllFotos().size());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ID_REQUEST_CAMERA && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();

            image = (Bitmap) extras.get("data");
            iv_foto.setImageBitmap(image);
        }
    }
}
