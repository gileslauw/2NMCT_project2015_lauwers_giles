package be.howest.googlemapsapi;

import android.app.Activity;
import android.content.Intent;
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


public class OverzichtFragment extends Fragment {

    private Button btnToon;
    private TextView tvOverzichtNaam,tvOverzichtAdres,tvOverzichtTelefoon,tvOverzichtWebsite;
    private ImageView imgOverzichtFoto;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overzicht, container, false);
        btnToon = (Button)view.findViewById(R.id.btnToon);
        tvOverzichtNaam = (TextView)view.findViewById(R.id.tvOverzichtNaam);
        tvOverzichtAdres = (TextView)view.findViewById(R.id.tvOverzichtAdres);
        tvOverzichtTelefoon = (TextView)view.findViewById(R.id.tvOverzichtTelefoon);
        tvOverzichtWebsite = (TextView)view.findViewById(R.id.tvOverzichtWebsite);
        imgOverzichtFoto = (ImageView) view.findViewById(R.id.imgOverzichtFoto);
        Bundle test = getArguments();
        String naam=getArguments().getString("EXTRA_MESSAGE");
        final String adres=getArguments().getString("EXTRA_MESSAGE2");
        String telefoon=getArguments().getString("EXTRA_MESSAGE3");
        String website=getArguments().getString("EXTRA_MESSAGE4");
        String foto=getArguments().getString("EXTRA_MESSAGE5");

        int fotoId = Integer.parseInt(foto);
        imgOverzichtFoto.setImageResource(fotoId);
        tvOverzichtNaam.setText(naam);
        tvOverzichtAdres.setText(adres);
        tvOverzichtTelefoon.setText(telefoon);
        tvOverzichtWebsite.setText(website);

        btnToon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getActivity() , MainActivity.class);
                intent.putExtra("adres",adres);
                startActivity(intent);
            }
        });

        return view;
    }

}
