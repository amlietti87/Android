package com.macana.loshermanos.seminario.data;

/**
 * Created by andreslietti on 10/12/17.
 */

public class Salidas {
    private String SalidasId;
    private String SalidasHab;
    private String SalidasNemonico;
    private String SalidasNombre;
    private String SalidasTiempo;


    public Salidas() {
    }

//    public Salidas(String salidasId, String salidasNombre) {
//        SalidasId = salidasId;
//        SalidasNombre = salidasNombre;
//    }


    public Salidas(String salidasId, String salidasHab, String salidasNemonico, String salidasNombre, String salidasTiempo) {
        SalidasId = salidasId;
        SalidasHab = salidasHab;
        SalidasNemonico = salidasNemonico;
        SalidasNombre = salidasNombre;
        SalidasTiempo = salidasTiempo;
    }

    @Override
    public String toString() {
        return "Salidas{" +
                "SalidasId='" + SalidasId + '\'' +
                ", SalidasHab='" + SalidasHab + '\'' +
                ", SalidasNemonico='" + SalidasNemonico + '\'' +
                ", SalidasNombre='" + SalidasNombre + '\'' +
                ", SalidasTiempo='" + SalidasTiempo + '\'' +
                '}';
    }

    public String getSalidasId() {
        return SalidasId;
    }

    public void setSalidasId(String salidasId) {
        SalidasId = salidasId;
    }

    public String getSalidasHab() {
        return SalidasHab;
    }

    public void setSalidasHab(String salidasHab) {
        SalidasHab = salidasHab;
    }

    public String getSalidasNemonico() {
        return SalidasNemonico;
    }

    public void setSalidasNemonico(String salidasNemonico) {
        SalidasNemonico = salidasNemonico;
    }

    public String getSalidasNombre() {
        return SalidasNombre;
    }

    public void setSalidasNombre(String salidasNombre) {
        SalidasNombre = salidasNombre;
    }

    public String getSalidasTiempo() {
        return SalidasTiempo;
    }

    public void setSalidasTiempo(String salidasTiempo) {
        SalidasTiempo = salidasTiempo;
    }
}
