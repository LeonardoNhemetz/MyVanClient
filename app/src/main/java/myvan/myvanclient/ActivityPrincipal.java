package myvan.myvanclient;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import myvan.myvanclient.Backgrounds.BackgroundWorker;
import myvan.myvanclient.Backgrounds.SelectEnd.DownloaderEnd;
import myvan.myvanclient.Rastreio.RastreioMaps;

public class ActivityPrincipal extends AppCompatActivity {



    RadioGroup rg_Ir,rg_Voltar,rg_End,rg_End2;
    String Ir="",Voltar="";
    public static String lat_pass1,lng_pass1,lat_pass2,lng_pass2,lat_pass3,lng_pass3,lat_pass4,lng_pass4;
    String IrLatAtivo,IrLngAtivo,VoltarLatAtivo,VoltarLngAtivo;
    String urlAddress= "http://35.231.239.84/myvan/Client/End1.php";
    AdView adView;
    public Button EnviarRel;
    RadioButton rbIrAtivo1,rbIrAtivo3,rbVoltarAtivo2,rbVoltarAtivo4;
    private static final int REQUEST_PERMISSIONS_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_principal);

        ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_PERMISSIONS_CODE );


        rg_Ir = (RadioGroup) findViewById(R.id.rgIr);
        rg_Voltar = (RadioGroup) findViewById(R.id.rgVoltar);
        rg_End = (RadioGroup) findViewById(R.id.rg_End);
        rg_End2 = (RadioGroup) findViewById(R.id.rg_End2);
        EnviarRel = findViewById( R.id.btnEnviar );



        adView = (AdView) findViewById(R.id.adBannerPrincipal);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);




        //**************************************************CHAMADA DE END**************************************************

          final ListView lv1 = (ListView) findViewById(R.id.lv1);


          DownloaderEnd d = new DownloaderEnd(ActivityPrincipal.this, urlAddress, lv1);
          d.execute();




        //******************************************************************************************************************


        rg_Ir.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId)
                {
                    case R.id.rbIrSim:
                        Ir = "sim";

                        break;

                    case R.id.rbIrNao:
                        Ir = "nao";
                        IrLatAtivo = "NULL";
                        IrLngAtivo = "NULL";

                        break;
                }
            }
        });

        rg_Voltar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId)
                {
                    case R.id.rbVoltarSim:
                        Voltar = "sim";

                        break;

                    case R.id.rbVoltarNao:
                        Voltar = "nao";
                        VoltarLatAtivo = "NULL";
                        VoltarLngAtivo = "NULL";

                        break;
                }
            }
        });


        rg_End.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId)
                {
                    case R.id.rbIrAtivo1:
                        IrLatAtivo = lat_pass1;
                        IrLngAtivo = lng_pass1;
                        break;

                    case R.id.rbIrAtivo3:
                        IrLatAtivo = lat_pass2;
                        IrLngAtivo = lng_pass2;
                        break;


                }
            }
        });

        rg_End2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId)
                {
                    case R.id.rbVoltarAtivo2:
                        VoltarLatAtivo = lat_pass3;
                        VoltarLngAtivo = lng_pass3;
                        break;
                    case R.id.rbVoltarAtivo4:
                        VoltarLatAtivo = lat_pass4;
                        VoltarLngAtivo = lng_pass4;
                        break;

                }
            }
        });


    }



    public void Onclick(View view)
    {

        String type = "Register";

        BackgroundWorker BW = new BackgroundWorker(this);
        BW.execute(type,Ir,Voltar,IrLatAtivo,IrLngAtivo,VoltarLatAtivo,VoltarLngAtivo);



    }

    public void Rastr(View view)
    {

        startActivity(new Intent(this, RastreioMaps.class));

    }

    public void PermitionLocale()
    {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION))
            {

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_PERMISSIONS_CODE);

            }
            else
            {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_PERMISSIONS_CODE);
            }
        }

    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(ActivityPrincipal.this, "Permission denied to read your Fine Location", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void onPause()
    {
        if(adView != null)
        {
            adView.pause();
        }
        super.onPause();
    }

    public void onResume()
    {
        super.onResume();
        if(adView != null)
        {
            adView.resume();
        }
    }

    public void onDestroy()
    {
        if(adView != null)
        {
            adView.destroy();
        }
        super.onDestroy();
    }

}
