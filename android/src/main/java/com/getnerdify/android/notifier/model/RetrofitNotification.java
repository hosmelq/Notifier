package com.getnerdify.android.notifier.model;

import java.io.Serializable;

public class RetrofitNotification implements Serializable {

    public int id;
    public String titulo;
    public String empresa;
    public String mensaje;
    public String fecha_creacion;

    public int codigo;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return titulo;
    }

    public String getCompany() {
        return empresa;
    }

    public String getMessage() {
        return mensaje;
    }

    public String getCreatedAt() {
        return fecha_creacion;
    }
}
