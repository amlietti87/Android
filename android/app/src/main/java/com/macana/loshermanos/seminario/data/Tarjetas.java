package com.macana.loshermanos.seminario.data;

/**
 * Created by andreslietti on 10/12/17.
 */

public class Tarjetas {
    private String TagId;
    private String TagNumero;
    private String TagCategoria;
    private String TagNombre;

    public Tarjetas() {
    }

    public Tarjetas(String tagId, String tagNumero, String tagCategoria, String tagNombre) {
        TagId = tagId;
        TagNumero = tagNumero;
        TagCategoria = tagCategoria;
        TagNombre = tagNombre;
    }

    @Override
    public String toString() {
        return "Tarjetas{" +
                "TagId='" + TagId + '\'' +
                ", TagNumero='" + TagNumero + '\'' +
                ", TagCategoria='" + TagCategoria + '\'' +
                ", TagNombre='" + TagNombre + '\'' +
                '}';
    }

    public String getTagId() {
        return TagId;
    }

    public void setTagId(String tagId) {
        TagId = tagId;
    }

    public String getTagNumero() {
        return TagNumero;
    }

    public void setTagNumero(String tagNumero) {
        TagNumero = tagNumero;
    }

    public String getTagCategoria() {
        return TagCategoria;
    }

    public void setTagCategoria(String tagCategoria) {
        TagCategoria = tagCategoria;
    }

    public String getTagNombre() {
        return TagNombre;
    }

    public void setTagNombre(String tagNombre) {
        TagNombre = tagNombre;
    }
}
