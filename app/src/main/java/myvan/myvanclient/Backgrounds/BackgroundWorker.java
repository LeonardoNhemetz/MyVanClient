package myvan.myvanclient.Backgrounds;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

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


public class BackgroundWorker extends AsyncTask<String,Void,String> {

    Context ctx;
    ProgressDialog pd;
    AlertDialog ad;
    String user_name,password,table;
    User user;

    public BackgroundWorker(Context ctx)
    {
        this.ctx = ctx;

        user = new User();
        user_name = user.getUser_name();
        password = user.getPassword();
        table = user.getTable();
    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(ctx);
        pd.setTitle("Enviando dados");
        pd.setMessage("Espere...");
        pd.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String reg_url = "http://35.231.239.84/myvan/Client/IrVoltar.php";
        String type = params[0];
        if(type.equals("Register"))
        {
            String statusIr = params[1];
            String statusVoltar = params[2];
            String IrLatAtivo = params[3];
            String IrLngAtivo = params[4];
            String VoltarLatAtivo = params[5];
            String VoltarLngAtivo = params[6];

            try
            {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("nome_pass","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"+
                        URLEncoder.encode("senha_pass","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"+
                        URLEncoder.encode("statusIr","UTF-8")+"="+URLEncoder.encode(statusIr,"UTF-8")+"&"+
                        URLEncoder.encode("statusVoltar","UTF-8")+"="+URLEncoder.encode(statusVoltar,"UTF-8")+"&"+
                        URLEncoder.encode("table","UTF-8")+"="+URLEncoder.encode(table,"UTF-8")+"&"+
                        URLEncoder.encode("lat_ir_ativo_pass","UTF-8")+"="+URLEncoder.encode(IrLatAtivo,"UTF-8")+"&"+
                        URLEncoder.encode("lng_ir_ativo_pass","UTF-8")+"="+URLEncoder.encode(IrLngAtivo,"UTF-8")+"&"+
                        URLEncoder.encode("lat_voltar_ativo_pass","UTF-8")+"="+URLEncoder.encode(VoltarLatAtivo,"UTF-8")+"&"+
                        URLEncoder.encode("lng_voltar_ativo_pass","UTF-8")+"="+URLEncoder.encode(VoltarLngAtivo,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS,"iso-8859-1"));
                String result = "";
                String line="";

                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();
                return result;

            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("Registrado com sucesso!!!")) {
            pd.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle("Registrado");
            builder.setMessage("Registrado com sucesso!!!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            ad = builder.create();
            ad.show();


        }

        else
        {
            pd.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle("Algo deu errado!");
            builder.setMessage("Login/Senha ou Van incorretos, Verifique se o IR e o VOLTAR estão corretos ");
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
