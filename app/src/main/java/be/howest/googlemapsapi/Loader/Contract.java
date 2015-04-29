package be.howest.googlemapsapi.Loader;

import android.provider.BaseColumns;

/**
 * Created by giles on 26/04/2015.
 */
public class Contract {
    public interface FrituurColumns extends BaseColumns
    {
        public static final String COLUMN_NAAM = "Naam";
        public static final String COLUMN_ADRES = "adres";
        public static final String COLUMN_TELEFOON = "telefoon";
        public static final String COLUMN_WEBSITE = "website";
        public static final String COLUMN_FOTO = "foto";
    }
}
