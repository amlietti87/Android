package com.macana.loshermanos.seminario.data;

import android.provider.BaseColumns;

/**
 * Created by andreslietti on 10/3/17.
 */

public class CBContract {

    // Declaracion de las tablas de la base de datos.
    public static final class SalidasEntry implements BaseColumns {

        // Table name
        public static final String TABLE_NAME = "salidas";
        //column (field) names
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_HABILITADA = "habilitada";
        public static final String COLUMN_NEMONICO = "nemonico";
        public static final String COLUMN_NOMBRE = "nombre";
        public static final String COLUMN_TIEMPO = "tiempo";

    }

    public static final class CelularesEntry implements BaseColumns {

        // Table name
        public static final String TABLE_NAME = "celulares";
        //column names
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_CELNOM = "celnom";
        public static final String COLUMN_CELNUM = "celnum";
        public static final String COLUMN_CELCAT = "celcat";
    }

    public static final class TagsEntry implements BaseColumns {

        // Table name
        public static final String TABLE_NAME = "tags";
        //column names
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TAGNOM = "tagnom";
        public static final String COLUMN_TAGNUM = "tagnum";
        public static final String COLUMN_TAGCAT = "tagcat";
    }
}
