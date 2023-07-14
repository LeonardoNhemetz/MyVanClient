package myvan.myvanclient;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import myvan.myvanclient.Backgrounds.BackgroundMain;

public class Main extends Activity {

    EditText txt_Login,txt_Password,txt_Table;

    String username,password,table;
    AdView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_Login = (EditText) findViewById(R.id.txtLogin);
        txt_Password = (EditText) findViewById(R.id.txtPassword);
        txt_Table = (EditText) findViewById(R.id.txtTable);

        adView = (AdView) findViewById(R.id.adBannerMain);
        MobileAds.initialize(this, "ca-app-pub-7204661155560964~6681556133");
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        SharedPreferences sharedPreferences =  getSharedPreferences("LoginADM",MODE_PRIVATE);
        String Login = sharedPreferences.getString("Login","");
        String Pass = sharedPreferences.getString("Pass","");
        String Table = sharedPreferences.getString("Table","");

        txt_Login.setText(Login);
        txt_Password.setText(Pass);
        txt_Table.setText(Table);

    }

    public void Onlogin(View view)
    {

        String Login = txt_Login.getText().toString();
        String Pass = txt_Password.getText().toString();
        String Table = txt_Table.getText().toString();

        SharedPreferences sharedPreferences =  getSharedPreferences("LoginADM",MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("Login",Login);
        editor.putString("Pass",Pass);
        editor.putString("Table",Table);


        editor.commit();



        username = txt_Login.getText().toString();
        password = txt_Password.getText().toString();
        table = txt_Table.getText().toString();

        BackgroundMain backgroundMain = new BackgroundMain(Main.this,username,password,table);
        backgroundMain.execute();
    }

    public void OnCad(View view)
    {
        startActivity(new Intent(this, Cadastrar.class));
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
