package myvan.myvanclient.Backgrounds.SelectEnd;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

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

/**
 * Created by Leonardo on 15/12/2016.
 */

public class DownloaderEnd extends AsyncTask<Void,Void,String> {

    Context c;
    String urlAddress;
    ListView lv1;
    String user_name,password,table;

    User user;

    public DownloaderEnd(Context c, String urlAddress, ListView lv1) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.lv1 = lv1;

        user = new User();
        user_name = user.getUser_name();
        password = user.getPassword();
        table = user.getTable();
    }

    @Override
    protected String doInBackground(Void... params) {
        return this.downloadData();
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);


        if(s==null)
        {
            Toast.makeText(c,"Sem exito",Toast.LENGTH_SHORT).show();
        }
        else
        {
            //call data parser
            DataPasser parser = new DataPasser(c,lv1,s);
            parser.execute();

        }
    }

    private String downloadData() {
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

            String registrationData = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"+
                    URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"+
                    URLEncoder.encode("table","UTF-8")+"="+URLEncoder.encode(table,"UTF-8");

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


}