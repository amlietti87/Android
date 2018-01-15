package com.macana.loshermanos.seminario.data;

/**
 * Created by andreslietti on 10/12/17.
 */

public class Celulares {

    private String CelularId;
    private String CelularNumero;
    private String CelularCategoria;
    private String CelularNombre;

    public Celulares() {
    }

    public Celulares(String celularId, String celularNumero, String celularCategoria, String celularNombre) {
        CelularId = celularId;
        CelularNumero = celularNumero;
        CelularCategoria = celularCategoria;
        CelularNombre = celularNombre;
    }

    @Override
    public String toString() {
        return "Celulares{" +
                "CelularId='" + CelularId + '\'' +
                ", CelularNumero='" + CelularNumero + '\'' +
                ", CelularCategoria='" + CelularCategoria + '\'' +
                ", CelularNombre='" + CelularNombre + '\'' +
                '}';
    }

    public String getCelularId() {
        return CelularId;
    }

    public void setCelularId(String celularId) {
        CelularId = celularId;
    }

    public String getCelularNumero() {
        return CelularNumero;
    }

    public void setCelularNumero(String celularNumero) {
        CelularNumero = celularNumero;
    }

    public String getCelularCategoria() {
        return CelularCategoria;
    }

    public void setCelularCategoria(String celularCategoria) {
        CelularCategoria = celularCategoria;
    }

    public String getCelularNombre() {
        return CelularNombre;
    }

    public void setCelularNombre(String celularNombre) {
        CelularNombre = celularNombre;
    }
}
