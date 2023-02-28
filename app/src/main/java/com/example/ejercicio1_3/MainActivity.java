package com.example.ejercicio1_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ejercicio1_3.configuracion.SQLiteConexion;

import com.example.ejercicio1_3.transacciones.Transacciones;

public class MainActivity extends AppCompatActivity {
    SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
    SQLiteDatabase db;
    EditText nombres, apellidos, edad, correo, direccion;
    Button btng, btnr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombres = (EditText) findViewById(R.id.txtnombre);
        apellidos = (EditText) findViewById(R.id.txtapellido);
        edad = (EditText) findViewById(R.id.txtedad);
        correo = (EditText) findViewById(R.id.txtcorreo);
        direccion = (EditText) findViewById(R.id.txtdireccion);
        btng = (Button) findViewById(R.id.btnactualizar);
        btnr = (Button) findViewById(R.id.btnregistros);

        btng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nombres.getText().toString().equals(Transacciones.Empty)) {
                    Toast.makeText(getApplicationContext(), "Debe de escribir un nombre", Toast.LENGTH_LONG).show();
                } else if (apellidos.getText().toString().equals(Transacciones.Empty)) {
                    Toast.makeText(getApplicationContext(), "Debe de escribir un apellido", Toast.LENGTH_LONG).show();
                } else if (edad.getText().toString().equals(Transacciones.Empty)) {
                    Toast.makeText(getApplicationContext(), "Debe de escribir su edad", Toast.LENGTH_LONG).show();
                } else if (correo.getText().toString().equals(Transacciones.Empty)) {
                    Toast.makeText(getApplicationContext(), "Debe de escribir su correo", Toast.LENGTH_LONG).show();
                } else {
                    SalvarPersonas();
                }
            }
        });
        btnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_List_View.class);
                startActivity(intent);
            }
        });


    }

    private void SalvarPersonas() {
        try {
            conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
            db = conexion.getWritableDatabase();


            ContentValues valores = new ContentValues();

            valores.put(Transacciones.nombres, nombres.getText().toString());
            valores.put(Transacciones.apellidos, apellidos.getText().toString());
            valores.put(Transacciones.edad, edad.getText().toString());
            valores.put(Transacciones.correo, correo.getText().toString());
            valores.put(Transacciones.direccion, direccion.getText().toString());

            Long Resultado = db.insert(Transacciones.tablapersonas, Transacciones.id, valores);

            Toast.makeText(getApplicationContext(), "Registro ingreso con exito, Codigo," + Resultado.toString(), Toast.LENGTH_LONG).show();
            db.close();
            ClearScreen();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Se produjo un error", Toast.LENGTH_LONG).show();
        }
    }

    private void ClearScreen() {
        nombres.setText(Transacciones.Empty);
        apellidos.setText(Transacciones.Empty);
        correo.setText(Transacciones.Empty);
        edad.setText(Transacciones.Empty);
        direccion.setText(Transacciones.Empty);
    }
}