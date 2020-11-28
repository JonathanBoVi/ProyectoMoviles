package com.bonilla.proyecto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class registrarUsu extends AppCompatActivity {

    private Button btnCan,btnRegis,btnHac;
    private EditText usu,cont,nom,dire,telf;
    private String usuario,contra,nombre,direcci,telefo,foto;

    ImageView picture;
    ImageButton openCamera;

    private static final  int REQUEST_PERMISSION_CAMERA=101;
    private static final int REQUEST_IMAGE_CAMERA=101;


    private View.OnClickListener listCan=new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener listHac=new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            tomarFoto();
        }
    };

    private View.OnClickListener listReg=new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            registrarUsuario("http://proyectofinalhotel.000webhostapp.com/registrar.php");
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usu);
        btnCan=findViewById(R.id.btnCancelar);
        btnCan.setOnClickListener(listCan);
        btnRegis=findViewById(R.id.btnRegistrar);
        btnRegis.setOnClickListener(listReg);
        usu=findViewById(R.id.txtUsu);
        cont=findViewById(R.id.txtCont);
        nom=findViewById(R.id.txtNombre);
        dire=findViewById(R.id.txtDireccion);
        telf=findViewById(R.id.txtTelefono);
        btnHac=findViewById(R.id.btnHacer);
        btnHac.setOnClickListener(listHac);


    }

    private void registrarUsuario(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Usuario Registrado")){
                    alerta();
                } else {
                    Toast.makeText(registrarUsu.this, "Ocurrio un error intentalo de nuevo", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("xxd",error.toString());
                Toast.makeText(registrarUsu.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String,String>();
                parametros.put("usuario",usuario);
                parametros.put("contrasena",contra);
                parametros.put("nombre",nombre);
                parametros.put("foto",foto="0");
                parametros.put("direc",direcci);
                parametros.put("tele",telefo);
                return parametros;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private  void alerta(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Titulo");
        builder.setMessage("Mensaje");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void tomarFoto(){
        // Determine Uri of camera image to save.
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "MyDir" + File.separator);
        root.mkdirs();
        final String fname = "Hola";
        final File sdImageMainDirectory = new File(root, fname);
        Uri outputFileUri = Uri.fromFile(sdImageMainDirectory);

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for(ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        startActivityForResult(chooserIntent, 1);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                final boolean isCamera;
                if (data == null) {
                    isCamera = true;
                } else {
                    final String action = data.getAction();
                    if (action == null) {
                        isCamera = false;
                    } else {
                        isCamera = action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
                    }
                }

                Uri selectedImageUri;
                if (isCamera) {
                    Uri outputFileUri = null;
                    selectedImageUri = outputFileUri;
                } else {
                    selectedImageUri = data == null ? null : data.getData();
                }
            }
        }
    }






}