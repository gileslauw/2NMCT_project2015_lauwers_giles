package be.howest.googlemapsapi.Loader;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.support.v4.content.AsyncTaskLoader;
import android.util.JsonReader;
import android.util.Log;

import junit.framework.Assert;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by giles on 26/04/2015.
 */
public class FrituurLoader extends AsyncTaskLoader<Cursor> {
    private Cursor mCursor;
    private final String[] mColumnNames = new String[]
            {
                    BaseColumns._ID,
                    Contract.FrituurColumns.COLUMN_NAAM,
                    Contract.FrituurColumns.COLUMN_ADRES,
                    Contract.FrituurColumns.COLUMN_TELEFOON,
                    Contract.FrituurColumns.COLUMN_WEBSITE,
                    Contract.FrituurColumns.COLUMN_FOTO
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
                    int foto = 0;

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
                            foto = getDrawable(getContext(),jsonReader.nextString());

                           // foto = " R.drawable."+jsonReader.nextString();

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
}
