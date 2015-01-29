package com.getnerdify.android.notifier.model;

public class RetrofitUser {

    public int id;
    public String nombre;
    public String celular;
    public String email;
    public String password;

    public int codigo;
    public String metodo;
    public String descripcion;

    public RetrofitUser(String email, String password) {
        this.email = email;
        this.password = password;
        this.metodo = "login";
    }

    public RetrofitUser(String nombre, String celular, String email, String password) {
        this.nombre = nombre;
        this.celular = celular;
        this.email = email;
        this.password = password;
        this.metodo = "signup";
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCelular() {
        return celular;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

}
