package com.handietron.tampilsourcecode;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Handietron on 21/10/2017.
 */

public class Connnect extends AsyncTaskLoader<String> {
    private String url;

    public Connnect(Context context, String url) {
        super(context);
        this.url=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        InputStream in;

        try {
            URL myUrl = new URL(url);
            HttpURLConnection Connex = (HttpURLConnection) myUrl.openConnection();
            Connex.setReadTimeout(10000);
            Connex.setConnectTimeout(20000);
            Connex.setRequestMethod("GET");
            Connex.connect();

            in = Connex.getInputStream();

            BufferedReader myBuf = new BufferedReader(new InputStreamReader(in));
            StringBuilder st = new StringBuilder();
            String line="";

            while ((line = myBuf.readLine()) != null) {
                st.append(line+" \n");
            }

            myBuf.close();
            in.close();

            return st.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error Get Page Source";
    }
}
