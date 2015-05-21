package be.howest.googlemapsapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class OverzichtFragment extends Fragment {

    private Button btnToon;
    private TextView tvOverzichtNaam, tvOverzichtAdres, tvOverzichtTelefoon, tvOverzichtWebsite;
    private ImageView imgOverzichtFoto;
    private Bitmap bitmap;
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {





        View view = inflater.inflate(R.layout.fragment_overzicht, container, false);
        btnToon = (Button) view.findViewById(R.id.btnToon);

        tvOverzichtNaam = (TextView) view.findViewById(R.id.tvOverzichtNaam);
        tvOverzichtAdres = (TextView) view.findViewById(R.id.tvOverzichtAdres);
        tvOverzichtTelefoon = (TextView) view.findViewById(R.id.tvOverzichtTelefoon);
        tvOverzichtWebsite = (TextView) view.findViewById(R.id.tvOverzichtWebsite);
        imgOverzichtFoto = (ImageView) view.findViewById(R.id.imgOverzichtFoto);
        Bundle test = getArguments();
        String naam = getArguments().getString("EXTRA_MESSAGE");
        final String adres = getArguments().getString("EXTRA_MESSAGE2");
        final String telefoon = getArguments().getString("EXTRA_MESSAGE3");
        final String website = getArguments().getString("EXTRA_MESSAGE4");
        String foto = getArguments().getString("EXTRA_MESSAGE5");



        ImageLoader imgLoader = new ImageLoader(context);

        int loader = R.drawable.fries;
        imgLoader.DisplayImage(foto, loader, imgOverzichtFoto);
        //imgOverzichtFoto.setImageBitmap(bitmap);

        tvOverzichtNaam.setText(naam);
        tvOverzichtAdres.setText(adres);
        tvOverzichtTelefoon.setText(telefoon);
        tvOverzichtWebsite.setText(website);

        tvOverzichtWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browse = new Intent(Intent.ACTION_VIEW);
                browse.setData(Uri.parse(website));
                startActivity(browse);
            }
        });
        tvOverzichtTelefoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call = new Intent(Intent.ACTION_DIAL);

                call.setData(Uri.parse("tel:"+telefoon));
                startActivity(call);
            }
        });

        btnToon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("adres", adres);
                startActivity(intent);
            }
        });



        return view;
    }



}

