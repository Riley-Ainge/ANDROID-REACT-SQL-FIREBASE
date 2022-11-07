package com.example.assignment2part2;

import android.app.Activity;
import android.os.Debug;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RemoteUtilities {

    public static RemoteUtilities remoteUtilities = null;
    private Activity uiActivity;
    public RemoteUtilities(Activity uiActivity){

        this.uiActivity = uiActivity;
    }

    public void setUiActivity(Activity uiActivity){
        this.uiActivity = uiActivity;
    }

    public static RemoteUtilities getInstance(Activity uiActivity){
        if(remoteUtilities == null){
            remoteUtilities = new RemoteUtilities(uiActivity);
        }
        remoteUtilities.setUiActivity(uiActivity);
        return remoteUtilities;
    }

    public HttpURLConnection openConnection(String urlString)  {
        Log.d("AAAAAAAAAAAAA", "111111111111");
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(conn == null){
            uiActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(uiActivity,"Check Internet",Toast.LENGTH_LONG).show();
                }
            });

        }
        return conn;
    }

    public boolean isConnectionOkay(HttpURLConnection conn){
        try {
            Log.d("AAAAAAAAAAAAA", conn.toString());
            if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                Log.d("AAAAAAAAAAAAA", "333333333333333");
                return true;
            }
            Log.d("AAAAAAAAAAAAA", "111111111111");
        } catch (IOException e) {
            e.printStackTrace();
            uiActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(uiActivity,"Problem with API Endpoint",Toast.LENGTH_LONG).show();
                }
            });
        }
        return false;
    }

    public String getResponseString(HttpURLConnection conn){
        String data = null;
        try {
            InputStream inputStream = conn.getInputStream();
            byte[] byteData = IOUtils.toByteArray(inputStream);
            data = new String(byteData, StandardCharsets.UTF_8);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return data;
    }
}
