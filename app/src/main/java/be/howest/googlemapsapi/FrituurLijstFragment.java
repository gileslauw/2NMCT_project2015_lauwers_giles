package be.howest.googlemapsapi;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import be.howest.googlemapsapi.Loader.Contract;
import be.howest.googlemapsapi.Loader.FrituurLoader;
import be.howest.googlemapsapi.R;


public class FrituurLijstFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private SimpleCursorAdapter adapter;
    public  static final String EXTRA_MESSAGE1 = "be.howest.googlemapsapi" ;
    public  static final String EXTRA_MESSAGE2 = "be.howest.googlemapsapi" ;
    public  static final String EXTRA_MESSAGE3 = "be.howest.googlemapsapi" ;
    public  static final String EXTRA_MESSAGE4 = "be.howest.googlemapsapi" ;
    public  static final String EXTRA_MESSAGE5 = "be.howest.googlemapsapi" ;
    /*
    ** Events
     */

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        String[] columns = new String[]
                {
                        Contract.FrituurColumns.COLUMN_NAAM,
                        // Contract.FrituurColumns.COLUMN_FOTO,
                        Contract.FrituurColumns.COLUMN_AFSTAND
                        //  Contract.FrituurColumns.COLUMN_ADRES,
                        //  Contract.FrituurColumns.COLUMN_TELEFOON,
                        //  Contract.FrituurColumns.COLUMN_WEBSITE

                };

        int[] viewIds = new int[]
                {
                        R.id.tvNaam,
                        //R.id.imgFoto,
                        R.id.tvKilometer

                };

        this.adapter = new SimpleCursorAdapter(getActivity(), R.layout.frituren_row, null, columns, viewIds);

        setListAdapter(this.adapter);
        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {
        return new FrituurLoader(getActivity());
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> cursorLoader, Cursor cursor)
    {
        this.adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> cursorLoader)
    {
        this.adapter.swapCursor(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent( getActivity() , MainActivity.class);
        Bundle extras = new Bundle();


        String kolom1 = FrituurLoader.matrixCursor.getString(1);
        String kolom2 = FrituurLoader.matrixCursor.getString(2);
        String kolom3 = FrituurLoader.matrixCursor.getString(3);
        String kolom4 = FrituurLoader.matrixCursor.getString(4);
        String kolom5 = FrituurLoader.matrixCursor.getString(5);

        extras.putString("EXTRA_MESSAGE",kolom1);
        extras.putString("EXTRA_MESSAGE2",kolom2);
        extras.putString("EXTRA_MESSAGE3",kolom3);
        extras.putString("EXTRA_MESSAGE4",kolom4);
        extras.putString("EXTRA_MESSAGE5",kolom5);

        intent.putExtras(extras);
        startActivity(intent);

        // Intent intent = new Intent();
        // intent.putExtra(MainActivity.IMAGE_HOROSCOOP,picture);
        // setResult(RESULT_OK,intent);
        // finish();

    }

}

