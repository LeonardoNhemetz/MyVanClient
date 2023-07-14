package myvan.myvanclient.Backgrounds;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Leonardo on 24/01/2018.
 */

public class BackgroundInsert extends AsyncTask<String,Void,String> {

    Context ctx;
    ProgressDialog pd;


    protected String result = "";
    protected String line = "";

    public BackgroundInsert(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog( ctx );
        pd.setTitle( "Analisando" );
        pd.setMessage( "Espere..." );
        pd.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String reg_url = "http://35.231.239.84/myvan/Client/register.php";
        String van = params[0];
        String nome_pass = params[1];
        String senha_pass = params[2];
        String email_pass = params[3];
        String end1_pass = params[4];
        String end2_pass = params[5];
        String end3_pass = params[6];
        String end4_pass = params[7];
        String num1_end_pass = params[8];
        String num2_end_pass = params[9];
        String num3_end_pass = params[10];
        String num4_end_pass = params[11];
        String cid1_pass = params[12];
        String cid2_pass = params[13];
        String cid3_pass = params[14];
        String cid4_pass = params[15];
        String tel_pass = params[16];
        double lat_pass1, lng_pass1, lat_pass2, lng_pass2, lat_pass3, lng_pass3, lat_pass4, lng_pass4;


        if (num1_end_pass.toString().isEmpty() || end1_pass.toString().isEmpty() || cid1_pass.toString().isEmpty()) {
            lat_pass1 = 0;
            lng_pass1 = 0;

        } else {
            Geocoder geo1 = new Geocoder( ctx );
            List<Address> add1 = null;
            try {
                add1 = geo1.getFromLocationName( num1_end_pass + "," + end1_pass + "," + cid1_pass, 1 );
            } catch (IOException e) {
                e.printStackTrace();
            }
            lat_pass1 = add1.get( 0 ).getLatitude();
            lng_pass1 = add1.get( 0 ).getLongitude();
        }

        if (num2_end_pass.toString().isEmpty() || end2_pass.toString().isEmpty() || cid2_pass.toString().isEmpty()) {
            lat_pass2 = 0;
            lng_pass2 = 0;
        } else {
            Geocoder geo2 = new Geocoder( ctx );
            List<Address> add2 = null;
            try {
                add2 = geo2.getFromLocationName( num2_end_pass + "," + end2_pass + "," + cid2_pass, 1 );
            } catch (IOException e) {
                e.printStackTrace();
            }
            lat_pass2 = add2.get( 0 ).getLatitude();
            lng_pass2 = add2.get( 0 ).getLongitude();
        }

        if (num3_end_pass.toString().isEmpty() || end3_pass.toString().isEmpty() || cid3_pass.toString().isEmpty()) {
            lat_pass3 = 0;
            lng_pass3 = 0;
        } else {
            Geocoder geo3 = new Geocoder( ctx );
            List<Address> add3 = null;
            try {
                add3 = geo3.getFromLocationName( num3_end_pass + "," + end3_pass + "," + cid3_pass, 1 );
            } catch (IOException e) {
                e.printStackTrace();
            }
            lat_pass3 = add3.get( 0 ).getLatitude();
            lng_pass3 = add3.get( 0 ).getLongitude();
        }

        if (num4_end_pass.toString().isEmpty() || end4_pass.toString().isEmpty() || cid4_pass.toString().isEmpty()) {
            lat_pass4 = 0;
            lng_pass4 = 0;
        } else {
            Geocoder geo4 = new Geocoder( ctx );
            List<Address> add4 = null;
            try {
                add4 = geo4.getFromLocationName( num4_end_pass + "," + end4_pass + "," + cid4_pass, 1 );
            } catch (IOException e) {
                e.printStackTrace();
            }
            lat_pass4 = add4.get( 0 ).getLatitude();
            lng_pass4 = add4.get( 0 ).getLongitude();
        }

        try {
            URL url = new URL( reg_url );
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod( "POST" );
            httpURLConnection.setDoInput( true );
            OutputStream OS = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter( new OutputStreamWriter( OS, "UTF-8" ) );
            String data = URLEncoder.encode( "van", "UTF-8" ) + "=" + URLEncoder.encode( van, "UTF-8" ) + "&" +
                    URLEncoder.encode( "nome_pass", "UTF-8" ) + "=" + URLEncoder.encode( nome_pass, "UTF-8" ) + "&" +
                    URLEncoder.encode( "senha_pass", "UTF-8" ) + "=" + URLEncoder.encode( senha_pass, "UTF-8" ) + "&" +
                    URLEncoder.encode( "email_pass", "UTF-8" ) + "=" + URLEncoder.encode( email_pass, "UTF-8" ) + "&" +
                    URLEncoder.encode( "end_pass1", "UTF-8" ) + "=" + URLEncoder.encode( end1_pass, "UTF-8" ) + "&" +
                    URLEncoder.encode( "end_pass2", "UTF-8" ) + "=" + URLEncoder.encode( end2_pass, "UTF-8" ) + "&" +
                    URLEncoder.encode( "end_pass3", "UTF-8" ) + "=" + URLEncoder.encode( end3_pass, "UTF-8" ) + "&" +
                    URLEncoder.encode( "end_pass4", "UTF-8" ) + "=" + URLEncoder.encode( end4_pass, "UTF-8" ) + "&" +
                    URLEncoder.encode( "num_end_pass1", "UTF-8" ) + "=" + URLEncoder.encode( num1_end_pass, "UTF-8" ) + "&" +
                    URLEncoder.encode( "num_end_pass2", "UTF-8" ) + "=" + URLEncoder.encode( num2_end_pass, "UTF-8" ) + "&" +
                    URLEncoder.encode( "num_end_pass3", "UTF-8" ) + "=" + URLEncoder.encode( num3_end_pass, "UTF-8" ) + "&" +
                    URLEncoder.encode( "num_end_pass4", "UTF-8" ) + "=" + URLEncoder.encode( num4_end_pass, "UTF-8" ) + "&" +
                    URLEncoder.encode( "cid_pass1", "UTF-8" ) + "=" + URLEncoder.encode( cid1_pass, "UTF-8" ) + "&" +
                    URLEncoder.encode( "cid_pass2", "UTF-8" ) + "=" + URLEncoder.encode( cid2_pass, "UTF-8" ) + "&" +
                    URLEncoder.encode( "cid_pass3", "UTF-8" ) + "=" + URLEncoder.encode( cid3_pass, "UTF-8" ) + "&" +
                    URLEncoder.encode( "cid_pass4", "UTF-8" ) + "=" + URLEncoder.encode( cid4_pass, "UTF-8" ) + "&" +
                    URLEncoder.encode( "tel_pass", "UTF-8" ) + "=" + URLEncoder.encode( tel_pass, "UTF-8" ) + "&" +
                    URLEncoder.encode( "lat_pass1", "UTF-8" ) + "=" + URLEncoder.encode( String.valueOf( lat_pass1 ), "UTF-8" ) + "&" +
                    URLEncoder.encode( "lng_pass1", "UTF-8" ) + "=" + URLEncoder.encode( String.valueOf( lng_pass1 ), "UTF-8" ) + "&" +
                    URLEncoder.encode( "lat_pass2", "UTF-8" ) + "=" + URLEncoder.encode( String.valueOf( lat_pass2 ), "UTF-8" ) + "&" +
                    URLEncoder.encode( "lng_pass2", "UTF-8" ) + "=" + URLEncoder.encode( String.valueOf( lng_pass2 ), "UTF-8" ) + "&" +
                    URLEncoder.encode( "lat_pass3", "UTF-8" ) + "=" + URLEncoder.encode( String.valueOf( lat_pass3 ), "UTF-8" ) + "&" +
                    URLEncoder.encode( "lng_pass3", "UTF-8" ) + "=" + URLEncoder.encode( String.valueOf( lng_pass3 ), "UTF-8" ) + "&" +
                    URLEncoder.encode( "lat_pass4", "UTF-8" ) + "=" + URLEncoder.encode( String.valueOf( lat_pass4 ), "UTF-8" ) + "&" +
                    URLEncoder.encode( "lng_pass4", "UTF-8" ) + "=" + URLEncoder.encode( String.valueOf( lng_pass4 ), "UTF-8" );

            bufferedWriter.write( data );
            bufferedWriter.flush();
            bufferedWriter.close();
            OS.close();
            InputStream IS = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( IS, "iso-8859-1" ) );

            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            IS.close();
            httpURLConnection.disconnect();
            return result;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }




    @Override
    protected void onPostExecute(String result) {
        pd.dismiss();
        Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
    }
}
