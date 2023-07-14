package myvan.myvanclient.Rastreio;


import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

import myvan.myvanclient.Backgrounds.Connector;
import myvan.myvanclient.Backgrounds.User;

public class BackgroundRastreio extends AsyncTask<Void, Void, String>
{
    Context ctx;
    User user;
    String table,urlAddress;


    public BackgroundRastreio(Context ctx) {
        this.ctx = ctx;

        user = new User();
        table = user.getTable();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected String doInBackground(Void... params)
    {
        urlAddress = "http://35.231.239.84/myvan/Client/Rastreio.php";
        Object connection= Connector.connect(urlAddress);
        if(connection==null)
        {
            return null;
        }


        try
        {
            HttpURLConnection con = (HttpURLConnection) connection;
            OutputStream os= new BufferedOutputStream(con.getOutputStream());
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

            String registrationData = URLEncoder.encode("table","UTF-8")+"="+URLEncoder.encode(table,"UTF-8");
            bw.write(registrationData);
            bw.flush();
            bw.close();
            os.close();

            //get response
            int responseCode = con.getResponseCode();
            if(responseCode==con.HTTP_OK)
            {
                InputStream is = new BufferedInputStream(con.getInputStream());
                BufferedReader br = new BufferedReader((new InputStreamReader(is)));

                String line;
                StringBuffer response = new StringBuffer();

                while ((line=br.readLine()) != null)
                {
                    response.append(line+"\n");
                }
                br.close();
                is.close();

                return response.toString();
            }



        }
        catch (IOException e)
        {
            e.printStackTrace();
            return "erro no IO";
        }

        return null;
    }

    @Override
    protected void onPostExecute(String response) {
        try {
            JSONArray Json = new JSONArray(response);
            JSONObject jsonObj = Json.getJSONObject(0);


            LatLng latLng = new LatLng(jsonObj.getJSONArray("latlng").getDouble(0),
                    jsonObj.getJSONArray("latlng").getDouble(1));

            RastreioMaps.mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                    .title("A van está aqui!")
                    .position(latLng));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng).zoom(14).build();
            RastreioMaps.mMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));



            new Distancia(ctx).execute(latLng.toString());




        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
}
