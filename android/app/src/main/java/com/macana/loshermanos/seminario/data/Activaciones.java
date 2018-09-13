package com.macana.loshermanos.seminario.data;

public class Activaciones {
    public String ActivacionesId;
    public String ActivacionNom;


    public Activaciones(){

    }

    public Activaciones(String activacionesId, String activacionesNom){
        ActivacionesId = activacionesId;
        ActivacionNom = activacionesNom;
    }


    @Override
    public String toString() {
        return "Activaciones{" +
                "ActivacionesId='" + ActivacionesId + '\'' +
                ", ActivacionNom='" + ActivacionNom + '\'' +
                '}';
    }

    public String getActivacionesId() {
        return ActivacionesId;
    }

    public void setActivacionesId(String activacionesId) {
        ActivacionesId = activacionesId;
    }

    public String getActivacionNom() {
        return ActivacionNom;
    }

    public void setActivacionNom(String activacionNom) {
        ActivacionNom = activacionNom;
    }
}


