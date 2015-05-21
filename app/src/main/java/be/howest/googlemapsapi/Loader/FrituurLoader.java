package be.howest.googlemapsapi.Loader;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.location.Address;
import android.location.Geocoder;
import android.provider.BaseColumns;
import android.support.v4.content.AsyncTaskLoader;
import android.util.JsonReader;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import junit.framework.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;

import be.howest.googlemapsapi.GPSTracker;

/**
 * Created by giles on 26/04/2015.
 */
public class FrituurLoader extends AsyncTaskLoader<Cursor> {
    private Cursor mCursor;
    GPSTracker gps ;
    LatLng loc;
    int kmInDec;
    double meter;
    int meterInDec;
    LatLng eigenLoc = HuidigeLocatieOphalen();


    private final String[] mColumnNames = new String[]
            {
                    BaseColumns._ID,
                    Contract.FrituurColumns.COLUMN_NAAM,
                    Contract.FrituurColumns.COLUMN_ADRES,
                    Contract.FrituurColumns.COLUMN_TELEFOON,
                    Contract.FrituurColumns.COLUMN_WEBSITE,
                    Contract.FrituurColumns.COLUMN_FOTO,
                    Contract.FrituurColumns.COLUMN_AFSTAND
            };
    private String url = "http://student.howest.be/giles.lauwers/frituren.json";
    private static Object lock = new Object();
    public static MatrixCursor matrixCursor;
    public FrituurLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading()
    {
        if(mCursor != null)
            deliverResult(mCursor);

        if(takeContentChanged() || mCursor == null)
            forceLoad();
    }
    private void loadCursor()
    {
        synchronized (lock)
        {
            if(mCursor != null)
                return;

            matrixCursor = new MatrixCursor(mColumnNames);
            InputStream inputStream = null;
            JsonReader jsonReader = null;

            try
            {
                inputStream = new URL(url).openStream();
                jsonReader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

                int id = 1;
                jsonReader.beginArray();
                while(jsonReader.hasNext())
                {
                    jsonReader.beginObject();

                    String adres = "";
                    String naam = "";
                    String telefoon = "";
                    String website = "";
                    String foto = "";
                    String afstand = "";

                    while(jsonReader.hasNext())
                    {
                        String name = jsonReader.nextName();
                        if(name.equals(Contract.FrituurColumns.COLUMN_NAAM))
                        {
                            naam = jsonReader.nextString();
                        }

                        else if(name.equals(Contract.FrituurColumns.COLUMN_ADRES))
                        {

                            adres = jsonReader.nextString();
                            LatLng frietLoc = LocatieFrituurOphalen(adres);
                            afstand = AfstandBerekenen(eigenLoc,frietLoc);


                        }

                        else if(name.equals(Contract.FrituurColumns.COLUMN_TELEFOON))
                        {

                            telefoon = jsonReader.nextString();
                        }

                        else if(name.equals(Contract.FrituurColumns.COLUMN_WEBSITE))
                        {
                            website = jsonReader.nextString();
                        }

                        else if(name.equals(Contract.FrituurColumns.COLUMN_FOTO))
                        {
                            foto = jsonReader.nextString();



                        }

                        else
                            jsonReader.skipValue();
                    }

                    MatrixCursor.RowBuilder row = matrixCursor.newRow();
                    row.add(id);
                    row.add(naam);
                    row.add(adres);
                    row.add(telefoon);
                    row.add(website);
                    row.add(foto);
                    row.add(afstand);
                    id++;

                    jsonReader.endObject();
                }
                jsonReader.endArray();

                mCursor = matrixCursor;
            }

            catch(Exception ex)
            {
                Log.d("Exception occured: ", ex.getMessage());
            }

            finally
            {
                try
                {
                    jsonReader.close();
                }

                catch(Exception ex)
                {
                    Log.d("Exception occured: ", ex.getMessage());
                }

                try
                {
                    inputStream.close();
                }

                catch(Exception ex)
                {
                    Log.d("Exception occured: ", ex.getMessage());
                }
            }
        }
    }



    @Override
    public Cursor loadInBackground() {

        if(mCursor == null)
            loadCursor();

        return mCursor;
    }

    public static int getDrawable(Context context, String name)
    {
        Assert.assertNotNull(context);
        Assert.assertNotNull(name);

        return context.getResources().getIdentifier(name,
                "drawable", context.getPackageName());
    }



    private LatLng HuidigeLocatieOphalen(){
        gps = new GPSTracker(getContext());

        double lon = gps.getLongitude();
        double lat = gps.getLatitude();
        LatLng huidigeLoc = new LatLng(lat,lon);
        return huidigeLoc;
    }

    private LatLng LocatieFrituurOphalen(String adr){
        Geocoder gc = new Geocoder(getContext());
        try{
            if(gc.isPresent()) {
                List<Address> list = gc.getFromLocationName(adr,1);

                Address address = list.get(0);

                double latitude = address.getLatitude();
                double longitude = address.getLongitude();
                loc = new LatLng(latitude, longitude);




            }

        }catch(IOException e){

        }
        return loc;
    }

    private String AfstandBerekenen(LatLng huidig , LatLng frituur){
        int Radius=6371;//radius of earth in Km
        double dLat = Math.toRadians(frituur.latitude-huidig.latitude);
        double dLon = Math.toRadians(frituur.longitude-huidig.longitude);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(frituur.latitude)) * Math.cos(Math.toRadians(huidig.latitude)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult= Radius*c;
        double km=valueResult/1;
        DecimalFormat newFormat = new DecimalFormat("####");
        kmInDec =  Integer.valueOf(newFormat.format(km));
        meter=valueResult%1000;
        meterInDec= Integer.valueOf(newFormat.format(meter));
        String aantalKm = String.format("%.2f", km)+" Km";

        return aantalKm;
    }

}
