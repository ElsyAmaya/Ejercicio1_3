package com.example.ejercicio1_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.ejercicio1_3.configuracion.SQLiteConexion;
import com.example.ejercicio1_3.transacciones.Transacciones;

public class ActivityActualizar extends AppCompatActivity {

    SQLiteConexion conexion;

    EditText nombres,apellidos, edad, correo, direccion;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);

        conexion = new SQLiteConexion(this, Transacciones.NameDatabase,null, 1);

        Intent i = getIntent();
        id= getIntent().getExtras().getString("Id");
        String Name = getIntent().getExtras().getString("Nombres");
        String lastname = getIntent().getExtras().getString("Apellidos");
        String Age = getIntent().getExtras().getString("Edad");
        String mail = getIntent().getExtras().getString("Correo");
        String address = getIntent().getExtras().getString("Direccion");
        Button btnUpdate = (Button) findViewById(R.id.btnactualizar);
        ImageButton btnatras = (ImageButton) findViewById(R.id.btnatras);


        nombres = (EditText) findViewById(R.id.txtnombre);
        apellidos = (EditText) findViewById(R.id.txtapellido);
        edad = (EditText) findViewById(R.id.txtedad);
        correo =  (EditText) findViewById(R.id.txtcorreo);
        direccion =(EditText) findViewById(R.id.txtdireccion);

        nombres.setText(Name);
        apellidos.setText(lastname);
        edad.setText(Age);
        correo.setText(mail);
        direccion.setText(address);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nombres.getText().toString().equals(Transacciones.Empty)){
                    Toast.makeText(getApplicationContext(), "Debe de escribir un nombre" , Toast.LENGTH_LONG).show();
                }else if (apellidos.getText().toString().equals(Transacciones.Empty)){
                    Toast.makeText(getApplicationContext(), "Debe de escribir un apellido" ,Toast.LENGTH_LONG).show();
                }else if (edad.getText().toString().equals(Transacciones.Empty)){
                    Toast.makeText(getApplicationContext(), "Debe de escribir su edad" ,Toast.LENGTH_LONG).show();
                }else if (correo.getText().toString().equals(Transacciones.Empty)){
                    Toast.makeText(getApplicationContext(), "Debe de escribir su correo" ,Toast.LENGTH_LONG).show();
                }
                else {
                    ActualizarDatos();
                }
            }
        });


        btnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_List_View.class);
                startActivity(intent);
            }
        });
        }

    private void ActualizarDatos() {
        try {
            SQLiteDatabase db = conexion.getWritableDatabase();
            String [] parametros = {id};

            ContentValues valores = new ContentValues();
            valores.put(Transacciones.nombres, nombres.getText().toString());
            valores.put(Transacciones.apellidos, apellidos.getText().toString());
            valores.put(Transacciones.edad, edad.getText().toString());
            valores.put(Transacciones.correo, correo.getText().toString());
            valores.put(Transacciones.direccion, direccion.getText().toString());
            db.update(Transacciones.tablapersonas, valores, Transacciones.id +"=?", parametros);
            Toast.makeText(getApplicationContext(),"Dato actualizados Correctante", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, Activity_List_View.class);
            startActivity(i);
        } catch(Exception ex){
            Toast.makeText(this, "No se pudo insertar el Dato", Toast.LENGTH_LONG).show();
        }

    }

}
