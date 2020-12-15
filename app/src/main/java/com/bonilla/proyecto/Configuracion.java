package com.bonilla.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static android.os.FileUtils.copy;

public class Configuracion extends AppCompatActivity {

    Button btnCerrar;
    Button btnInicio,btnFavoritos,btnRecomendados,btnFoto;
    private Bitmap bitmap;
    private ImageView imgFoto;
    private String KEY_IMAGEN = "foto";
    private int tipo;
    TextView correo,nombre,tele,dire;


    private View.OnClickListener lisFot=new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            tomarFoto();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        btnCerrar= findViewById(R.id.btnCerrarSesion);
        btnInicio= findViewById(R.id.btnInicio);
        btnFavoritos= findViewById(R.id.btnFavoritos);
        btnRecomendados= findViewById(R.id.btnRecomendado);
        correo= findViewById(R.id.txtCorreo);
        nombre= findViewById(R.id.txtNombre);
        tele=findViewById(R.id.txtTelefono);
        dire=findViewById(R.id.txtDireccion);
        imgFoto= findViewById(R.id.imgFoto);

        btnFoto=findViewById(R.id.btnFoto);
        btnFoto.setOnClickListener(lisFot);
        mostarUsuario("http://proyectofinalhotel.000webhostapp.com/listarUsu.php");
        new GetImageToURL().execute("https://proyectofinalhotel.000webhostapp.com/uploads/"+String.valueOf(Utilidades.getCorreo())+".png");



        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                FirebaseAuth.getInstance().signOut();
                finish();

            }
        });

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MenuPrincipal.class);
                startActivity(intent);
            }
        });

        btnFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Favoritos.class);
                startActivity(intent);
            }
        });

        btnRecomendados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Recomendados.class);
                startActivity(intent);
            }
        });

    }

    private class GetImageToURL extends AsyncTask< String, Void, Bitmap > {

        @Override
        protected Bitmap doInBackground(String...params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                // Log exception
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap myBitMap) {
            if (myBitMap!=null){
                imgFoto.setImageBitmap(myBitMap);
            }

        }
    }


    private void uploadImage() {
        //Mostrar el diálogo de progreso
        final ProgressDialog loading = ProgressDialog.show(this, "Subiendo...", "Espere por favor...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://proyectofinalhotel.000webhostapp.com/subirFoto.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Descartar el diálogo de progreso
                        loading.dismiss();
                        //Mostrando el mensaje de la respuesta
                        Toast.makeText(Configuracion.this, s, Toast.LENGTH_LONG).show();

                        new GetImageToURL().execute("https://proyectofinalhotel.000webhostapp.com/uploads/"+String.valueOf(Utilidades.getCorreo())+".png");

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Descartar el diálogo de progreso
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(Configuracion.this, String.valueOf(volleyError.getMessage()), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Convertir bits a cadena
                String imagen = getStringImagen(bitmap);

                //Creación de parámetros
                Map<String, String> params = new Hashtable<String, String>();

                //Agregando de parámetros
                params.put(KEY_IMAGEN, imagen);
                params.put("correo", String.valueOf(Utilidades.getCorreo()));

                //Parámetros de retorno
                return params;
            }
        };

        //Creación de una cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Agregar solicitud a la cola
        requestQueue.add(stringRequest);
    }

    public String getStringImagen(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Cómo obtener el mapa de bits de la Galería
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                //Configuración del mapa de bits en ImageView
                uploadImage();
                //imgFoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                try {
                    Bundle extras = data.getExtras();
                    bitmap = (Bitmap) extras.get("data");
                    uploadImage();
                } catch (Exception ex) {
                    Log.e("ERROR REAL", e.getMessage());
                    e.printStackTrace();
                }
            }


        }
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

    private void mostarUsuario(String URL){

        if (Utilidades.getTipoSesion()==1){
            StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (!response.isEmpty()){
                        try {
                            JSONArray jsonArr = new JSONArray(response);

                            JSONObject objeto = jsonArr.getJSONObject(0);
                            String usuario = objeto.getString("usuario");
                            String nombreCompleto = objeto.getString("nombreCompleto");
                            String foto = objeto.getString("foto");
                            String direccion = objeto.getString("direccion");
                            String telefono = objeto.getString("telefono");

                            Log.e("txt","pruebita");
                            correo.setText(usuario);
                            nombre.setText(nombreCompleto);
                            tele.setText(telefono);
                            dire.setText(direccion);


                            Log.e("txt2","pruebita2");

                        } catch (JSONException e) {
                            Log.e("catch",e.getMessage());
                        }

                    } else {
                        Toast.makeText(Configuracion.this, "Error al mostrar usuario", Toast.LENGTH_SHORT).show();
                    }

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Configuracion.this,error.toString(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> parametros=new HashMap<String,String>();
                    parametros.put("usu",Utilidades.getCorreo());
                    return parametros;
                }
            };

            RequestQueue requestQueue= Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } else{
            FirebaseUser auth= FirebaseAuth.getInstance().getCurrentUser();
            correo.setText(auth.getEmail());
            nombre.setText(auth.getDisplayName());
            tele.setText(auth.getPhoneNumber());



        }


    }


}