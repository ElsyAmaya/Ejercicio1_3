package com.example.ejercicio1_3.transacciones;

public class Transacciones {
    public static final String NameDatabase = "Ejercicio3";
    //Tabla de la base de datos
    public static final String tablapersonas = "personas";

    public static final String id = "id";
    public static final String nombres = "nombres";
    public static final String apellidos = "apellidos";
    public static final String edad= "edad";
    public static final String correo= "correo";
    public static final String direccion = "direccion";


    //Transacciones de la base de datos EX01DB
    public static final String CreateTBPersonas=
            "CREATE TABLE personas (id INTEGER PRIMARY KEY AUTOINCREMENT, nombres TEXT, "+"apellidos TEXT, edad INTEGER, correo TEXT, direccion TEXT)";

    public static final  String DropTablePersonas= "DROP TABLE IF EXISTS personas";

    //Helpers
    public static final  String Empty= "";
}


