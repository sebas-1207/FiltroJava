package com.campusland.utils.conexionpersistencia.conexionbdjson;

import com.campusland.respository.models.Impuesto;

public class ConexionBDjsonImpuesto extends ConexionBDJsonBase<Impuesto>{

    private static ConexionBDjsonImpuesto conexionImpuesto;

    private ConexionBDjsonImpuesto() {
        super("impuesto.json");
    }

    public static ConexionBDjsonImpuesto getConnexion(){
        if (conexionImpuesto!= null) {
            return conexionImpuesto;
        } else {
            conexionImpuesto = new ConexionBDjsonImpuesto();
            return conexionImpuesto;
        }
    }
    
}
