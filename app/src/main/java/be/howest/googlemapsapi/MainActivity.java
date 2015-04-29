package be.howest.googlemapsapi;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.ActionBarActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


public class MainActivity extends ActionBarActivity  {
    String adres;
    public static FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getFragmentManager();
        Bundle extras = getIntent().getExtras();
         adres= getIntent().getStringExtra("adres");

        if (extras != null && adres == null) {


           // getFragmentManager().beginTransaction().add(R.id.container,frag).addToBackStack(null).commit();
            FragmentTransaction t = getFragmentManager().beginTransaction();
            OverzichtFragment frag=new OverzichtFragment();
            frag.setArguments(extras);
            t.replace(R.id.container, frag);
            t.commit();
        }

        if(adres != null){
            String adr = adres;
            Bundle dataAdr = new Bundle();
            dataAdr.putString("adres",adr);

            FragmentTransaction tt = getFragmentManager().beginTransaction();
            LocationFragment fragt= new LocationFragment();
            fragt.setArguments(dataAdr);
            tt.replace(R.id.container, fragt);
            tt.commit();

        }

        if(savedInstanceState == null && extras==null && adres==null)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new FrituurLijstFragment())
                    .addToBackStack(null).commit();

        }
       // MapFragment mapFragment = (MapFragment) getFragmentManager()
       //         .findFragmentById(R.id.map);
       // mapFragment.getMapAsync(this);
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
