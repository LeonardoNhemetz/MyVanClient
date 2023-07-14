package myvan.myvanclient.Rastreio;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Leonardo on 28/11/2017.
 */

public class Distancia extends AsyncTask<String,Void,String>
{
    Context ctx;
    Location m_location;
    LocationManager m_LocationManager;
    String m_provider = LocationManager.GPS_PROVIDER;
    private static final int REQUEST_PERMISSIONS_CODE = 1;

    public Distancia(Context context)
    {
        ctx = context;
        m_LocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        //permições
        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != (PackageManager.PERMISSION_GRANTED) ||
                (ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION)
                        != (PackageManager.PERMISSION_GRANTED)))
        {
            //mostra o erro e/ou quit
            return;
        }

        m_LocationManager.requestLocationUpdates(m_provider, 15, 100, new LocationListener()
        {
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}

            public void onLocationChanged(Location location)
            {
                m_location = location;
            }
        });




    }



    @Override
    protected String doInBackground(String... params) {

        Location location = getLocation();
        Double latVan = location.getLatitude();
        Double lngVan = location.getLongitude();
        lngVan.toString();
        latVan.toString();
        Log.d( "************LOCALIZACAO DA VAN ************", latVan+" , "+lngVan );


        try {
            String latLng = params[0];
            latLng = latLng.replaceAll( "[lat/lng:()]","" );
            Log.d( "************LATLNG************", latLng );

            URL url = new URL( "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + latVan +","+ lngVan + "&destinations=" + latLng + "&mode=driving&language=pt-BR&key=AIzaSyBK-xWbzSBZt_wm0WNos4adXKysq9QW8h8" );
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statuscode=con.getResponseCode();
            if(statuscode==HttpURLConnection.HTTP_OK)
            {
                BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb=new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String json=sb.toString();
                Log.d( "AEEEEEEEEEEEEEEEE", json );
                JSONObject root=new JSONObject(json);
                JSONArray array_rows=root.getJSONArray("rows");
                JSONObject object_rows=array_rows.getJSONObject(0);
                JSONArray array_elements=object_rows.getJSONArray("elements");
                JSONObject  object_elements=array_elements.getJSONObject(0);
                JSONObject object_duration=object_elements.getJSONObject("duration");
                String duracao = object_duration.getString( "text" );
                JSONObject object_distance =object_elements.getJSONObject("distance");
                String distanciaKM = object_distance.getString( "text" );
                String distanciaM = object_distance.getString( "value" );

                int Mdistancia = Integer.valueOf(distanciaM);

                if(Mdistancia <= 999)
                {

                    String resultado = "Sua van está a " +distanciaM+ " metros e chega em aproximadamente "+duracao;
                    return resultado;
                }
                else
                {
                    String resultado = "Sua van está a " +distanciaKM+ " e chega em aproximadamente "+duracao;
                    return resultado;
                }






            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }




    @Override
    protected void onPostExecute(String resultado) {
        AlertDialog.Builder alert = new AlertDialog.Builder(ctx);
        alert.setMessage(resultado);
        alert.setPositiveButton("OK",null);
        alert.show();;
    }


    Location getLocation()
    // pegar localização do celular
    {


        if(m_location == null)
        {
            if(ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION)
                    != (PackageManager.PERMISSION_DENIED) ||
                    (ActivityCompat.checkSelfPermission(ctx,Manifest.permission.ACCESS_COARSE_LOCATION)
                            != (PackageManager.PERMISSION_DENIED)))
            {

                m_location = m_LocationManager.getLastKnownLocation(m_provider);
            }




            if(m_location == null)
            {
                m_location = new Location(m_provider);
                m_location.setLatitude(0);
                m_location.setLongitude(0);

            }

        }

        return m_location;


    }


}
