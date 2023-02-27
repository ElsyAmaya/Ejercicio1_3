package com.example.ejercicio1_3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ejercicio1_3.configuracion.SQLiteConexion;

import java.util.ArrayList;

import transacciones.Personas;
import transacciones.Transacciones;

public class Activity_List_View extends AppCompatActivity {
    SQLiteConexion conexion;
    EditText buscar;
    ListView listView;
    Button btneliminar, btnmodificar;
    ArrayList<Personas> listapersonas;
    ArrayList<String> arreglopersonas;
    private Boolean SelectedRow = false;
    private String Id;
    private String Nombres;
    private String Apellidos;
    private String Edad;
    private String Correo;
    private String Direccion;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        conexion= new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
        buscar = (EditText) findViewById(R.id.txtbuscar);
        listView= (ListView) findViewById(R.id.txtlista);
        btneliminar = (Button) findViewById(R.id.btneliminar);
        ObtenerListaPersonas();
        ArrayAdapter adp= new ArrayAdapter( this, android.R.layout.simple_list_item_checked, arreglopersonas);
        listView.setAdapter(adp);

        buscar = (EditText) findViewById(R.id.txtbuscar);
        buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SelectedRow = false;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                adp.getFilter().filter(charSequence);
                SelectedRow = false;

            }

            @Override
            public void afterTextChanged(Editable editable) {
                SelectedRow = false;
            }
        });

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                Id ="" + listapersonas.get(position).getId();
                Nombres= "" + listapersonas.get(position).getNombres();
                Apellidos = "+" + listapersonas.get(position).getApellidos();
                Edad = "" + listapersonas.get(position).getEdad();
                Correo = "" + listapersonas.get(position).getCorreo();
                Direccion = "" + listapersonas.get(position).getDireccion();
                SelectedRow = true;
            }
        });

        btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SelectedRow == true) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_List_View.this);
                    builder.setMessage("¡ESTA SEGURO QUE DESEA ELIMINAR ESTE REGISTRO!");
                    builder.setTitle("Eliminar");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            eliminar();
                            Intent intent = new Intent(Activity_List_View.this, Activity_List_View.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    Toast.makeText(Activity_List_View.this, "Seleccione un contacto de la lista", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void ObtenerListaPersonas(){
        SQLiteDatabase db = conexion.getReadableDatabase();
        Personas person = null;
        listapersonas = new ArrayList<Personas>();
        //cursor
        Cursor cursor=db.rawQuery("SELECT * FROM "+ Transacciones.tablapersonas,null);

        while (cursor.moveToNext())
        {
            person= new Personas();
            person.setId(cursor.getInt(0));
            person.setNombres(cursor.getString(1));
            person.setApellidos(cursor.getString(2));
            person.setEdad(cursor.getInt(3));
            person.setCorreo(cursor.getString(4));
            person.setDireccion(cursor.getString(5));
            listapersonas.add(person);
        }
        cursor.close();
        Fillist();

    }
    private  void Fillist()
    {
        arreglopersonas = new ArrayList<String>();
        for (int i = 0; i < listapersonas.size(); i++){

            arreglopersonas.add(listapersonas.get(i).getId()+" | "+
                    listapersonas.get(i).getNombres()+" | "+
                    listapersonas.get(i).getApellidos() +" | "+
                    listapersonas.get(i).getEdad() +" | "+
                    listapersonas.get(i).getDireccion());

        }

    }
    private void eliminar () {
        SQLiteDatabase db = conexion.getWritableDatabase();
        String[] params = {Id};
        String wherecond = Transacciones.id + "=?";
        db.delete(Transacciones.tablapersonas, wherecond, params);
        Toast.makeText(getApplicationContext(), "Dato eliminado correctamente", Toast.LENGTH_LONG).show();
    }

}