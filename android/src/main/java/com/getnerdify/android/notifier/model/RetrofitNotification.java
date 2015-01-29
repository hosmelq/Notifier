package com.getnerdify.android.notifier.model;

public class RetrofitNotification {

    public String titulo;
    public String empresa;
    public String mensaje;
    public String fecha_creacion;

    public int codigo;

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
