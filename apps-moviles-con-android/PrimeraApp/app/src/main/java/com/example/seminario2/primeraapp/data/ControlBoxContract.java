package com.example.seminario2.primeraapp.data;

import android.provider.BaseColumns;


public final class ControlBoxContract {

    public static final class SalidasEntry implements BaseColumns {

        // Table name
        public static final String TABLE_NAME = "salidas";
        //column (field) names
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_SALIDAS = "salidas";

    }

    public static final class CelularesEntry implements BaseColumns {

        // Table name
        public static final String TABLE_NAME = "celulares";
        //column names
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_CELULARES = "celulares";
    }

    public static final class TagsEntry implements BaseColumns {

        // Table name
        public static final String TABLE_NAME = "tags";
        //column names
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TAGS = "tags";
    }
}

