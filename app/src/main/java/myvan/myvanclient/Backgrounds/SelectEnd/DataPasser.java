package myvan.myvanclient.Backgrounds.SelectEnd;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import myvan.myvanclient.ActivityPrincipal;
import myvan.myvanclient.R;

/**
 * Created by Leonardo on 15/12/2016.
 */

public class DataPasser extends AsyncTask<Void,Void,Integer> {

    Context c;
    ListView lv1;
    String jsonData;
    ActivityPrincipal ac = new ActivityPrincipal();
    VariaveisSelectEnd var = new VariaveisSelectEnd();
    ActivityPrincipal activityPrincipal;


    ArrayList<VariaveisSelectEnd> variaveisSelectEnds = new ArrayList<>();

    public DataPasser(Context c, ListView lv1, String jsonData) {
        this.c = c;
        this.lv1 = lv1;
        this.jsonData = jsonData;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        return this.parseData();
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);


        if(result == 0)
        {
            Toast.makeText(c,"Não retornou nenhum dado",Toast.LENGTH_SHORT).show();
        }
        else
        {
            //call adapter

            AdapterEnd adapter = new AdapterEnd(c, variaveisSelectEnds);
            lv1.setAdapter(adapter);


            ac.lat_pass1 = var.getLat_pass1();
            ac.lng_pass1 = var.getLng_pass1();
            ac.lat_pass2 = var.getLat_pass2();
            ac.lng_pass2 = var.getLng_pass2();
            ac.lat_pass3 = var.getLat_pass3();
            ac.lng_pass3 = var.getLng_pass3();
            ac.lat_pass4 = var.getLat_pass4();
            ac.lng_pass4 = var.getLng_pass4();

            activityPrincipal = new ActivityPrincipal();



        }


    }

    private int parseData()
    {
        try {
            JSONArray ja=new JSONArray(jsonData);
            JSONObject jo = null;

            variaveisSelectEnds.clear();
            VariaveisSelectEnd s = null;

            for(int i=0;i<ja.length();i++)
            {
                jo=ja.getJSONObject(i);
                int id=jo.getInt("id_pass");
                String end_pass1=jo.getString("end_pass1");
                String end_pass2=jo.getString("end_pass2");
                String end_pass3=jo.getString("end_pass3");
                String end_pass4=jo.getString("end_pass4");
                String lat_pass1=jo.getString("lat_pass1");
                String lng_pass1=jo.getString("lng_pass1");
                String lat_pass2=jo.getString("lat_pass2");
                String lng_pass2=jo.getString("lng_pass2");
                String lat_pass3=jo.getString("lat_pass3");
                String lng_pass3=jo.getString("lng_pass3");
                String lat_pass4=jo.getString("lat_pass4");
                String lng_pass4=jo.getString("lng_pass4");



                s=new VariaveisSelectEnd();
                s.setId(id);
                s.setEnd_pass1(end_pass1);
                s.setEnd_pass2(end_pass2);
                s.setEnd_pass3(end_pass3);
                s.setEnd_pass4(end_pass4);
                s.setLat_pass1(lat_pass1);
                s.setLng_pass1(lng_pass1);
                s.setLat_pass2(lat_pass2);
                s.setLng_pass2(lng_pass2);
                s.setLat_pass3(lat_pass3);
                s.setLng_pass3(lng_pass3);
                s.setLat_pass4(lat_pass4);
                s.setLng_pass4(lng_pass4);


                variaveisSelectEnds.add(s);

            }

            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }
}

