package myvan.myvanclient.Backgrounds;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by Leonardo on 15/12/2016.
 */

public class RegistrationData {

    User user;

    public RegistrationData(User user) {
        this.user = user;
    }



    public String packRegistrationData()
    {
        JSONObject jo = new JSONObject();
        StringBuffer jsonData = new StringBuffer();

        try
        {
            jo.put("user_name",user.getUser_name());
            jo.put("password",user.getPassword());

            Boolean isFirstValue = true;
            Iterator it=jo.keys();

            do
            {
                String key = it.next().toString();
                String value = jo.get(key).toString();

                if(isFirstValue)
                {
                    isFirstValue = false;
                }
                else
                {
                    jsonData.append("&");
                }

                jsonData.append(URLEncoder.encode(key,"UTF-8"));
                jsonData.append("=");
                jsonData.append(URLEncoder.encode(value,"UTF-8"));

            }
            while(it.hasNext());

            return   jsonData.toString();

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
