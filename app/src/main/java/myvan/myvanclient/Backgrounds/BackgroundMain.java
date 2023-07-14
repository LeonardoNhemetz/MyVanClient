package myvan.myvanclient.Backgrounds;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

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

import myvan.myvanclient.ActivityPrincipal;


public class BackgroundMain extends AsyncTask<String,Void,String> {

    Context context;
    ProgressDialog pd;
    AlertDialog ad;
    String user_name,password,table;
    User user;

    public BackgroundMain(Context context, String user_name, String password, String table) {
        this.context = context;
        this.user_name = user_name;
        this.password = password;
        this.table = table;

        user = new User();
        user.setUser_name(user_name);
        user.setPassword(password);
        user.setTable(table);
    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(context);
        pd.setTitle("Verificando Login");
        pd.setMessage("Espere...");

        pd.show();
    }

    @Override
    protected String doInBackground(String... params) {

        String login_url = "http://35.231.239.84/myvan/Client/login.php";


        try {
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"+
                    URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"+
                    URLEncoder.encode("table","UTF-8")+"="+URLEncoder.encode(table,"UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String result = "";
            String line="";

            while((line = bufferedReader.readLine())!= null)
            {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return result;


            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }


        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.i("DAEEEE",result);

        if(result.equals("Logado com Sucesso!")) {
            pd.dismiss();
            context.startActivity(new Intent(context, ActivityPrincipal.class));


        }
        else
        {
            pd.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Algo deu errado!");
            builder.setMessage("Login/Senha ou Van incorretos");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            ad = builder.create();
            ad.show();
        }

    }
}
